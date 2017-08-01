/*
 Copyright (C) 2013     Enzo Seraphim

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 or visit <http://www.gnu.org/licenses/>
 */
package org.obinject.queries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import org.obinject.block.MTreeIndex;
import org.obinject.block.MTreeLeaf;
import org.obinject.block.Node;
import org.obinject.device.Session;
import org.obinject.meta.Entity;
import org.obinject.meta.Metric;
import org.obinject.storage.MTree;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class KNearestNeighbor extends Conditional {

    private final Attribute attribute;
    private final Object value;
    private final int k;

    /**
     *
     * @param attribute
     * @param value
     * @param k
     */
    public KNearestNeighbor(Attribute attribute, Object value, int k) {
        this.attribute = attribute;
        this.value = value;
        this.k = k;
    }

    private class Qualify implements Comparable<Qualify> {

        private final long pageId;
        private final double distanceParentToPointQuery;
        private final double coverage;

        public Qualify(long pageId, double distanceParentToPointQuery, double coverage) {
            this.pageId = pageId;
            this.distanceParentToPointQuery = distanceParentToPointQuery;
            this.coverage = coverage;
        }

        public double getDistanceParentToPointQuery() {
            return distanceParentToPointQuery;
        }

        public long getPageId() {
            return pageId;
        }

        public double getCoverage() {
            return coverage;
        }

        @Override
        public int compareTo(Qualify obj) {
            if (this.getDistanceParentToPointQuery() < obj.getDistanceParentToPointQuery()) {
                return -1;
            } else if (this.getDistanceParentToPointQuery() > obj.getDistanceParentToPointQuery()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private class InvertedResult implements Comparator<Metric> {

        @Override
        public int compare(Metric o1, Metric o2) {
            if (o1.getPreservedDistance() > o2.getPreservedDistance()) {
                return -1;
            } else if (o1.getPreservedDistance() == o2.getPreservedDistance()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Collection process() {
        if (k > 0) {
            Collection result = null;
            Metric metric = null;
            for (Iterator it = attribute.getSchemas().iterator(); it.hasNext();) {
                Schema schema = (Schema) it.next();
                if (schema.getKeyStructure() instanceof MTree) {
                    metric = (Metric) schema.newEntity(value);
                    //find btree
                    result = knnQueryInMtree(metric);
                    break;
                }
            }
            if (metric == null) {
                if (!attribute.getSchemas().isEmpty()) {
                    Schema schema = (Schema) (attribute.getSchemas().iterator().next());
                    Entity entity = schema.newEntity(value);
                    result = knnQueryInSequential(entity);
                }
            }
            if (result != null) {
                return result;
            } else {
                return new ArrayList();
            }
        } else {
            return new ArrayList();
        }
    }

    /**
     *
     * @param valueObj
     * @return
     */
    public Collection knnQueryInSequential(Entity valueObj) {
        return null;
    }

    /**
     *
     * @param valueObj
     * @return
     */
    public Collection knnQueryInMtree(Metric valueObj) {
        long pageId;
        double distParentToQuery, covParente, dist, cov;
        int total;
        double range = Double.MAX_VALUE;

        Metric obj;
        Qualify objQualify;
        MTree mtree = (MTree) valueObj.getKeyStructure();
        Session se = mtree.getWorkspace().openSession();

        PriorityQueue<Qualify> qualifies = new PriorityQueue<>();
        PriorityQueue<Metric> result = new PriorityQueue<>(this.k + 10, new InvertedResult());
        LinkedList<Metric> repetedList = new LinkedList<>();

        qualifies.add(new Qualify(mtree.getRootPageId(), Double.MAX_VALUE, Double.MAX_VALUE));

        do {

            objQualify = qualifies.poll();

            pageId = objQualify.getPageId();
            distParentToQuery = objQualify.getDistanceParentToPointQuery();
            covParente = objQualify.getCoverage();

            Node node = se.load(pageId);
            //verifica qualificação novamente pois o range pode ter mudado
            if (distParentToQuery - covParente <= range) {

                if (MTreeIndex.matchNodeType(node)) {
                    MTreeIndex index = new MTreeIndex<>(node, mtree.getObjectClass());
                    total = index.readNumberOfKeys();

                    for (int i = 0; i < total; i++) {
                        cov = index.readCoveringRadius(i);
                        //triangle inequality
                        if (distParentToQuery - index.readDistanceToParent(i) <= cov + range) {
                            obj = index.buildKey(i);
                            dist = obj.distanceTo(valueObj);
                            if (dist <= cov + range) {
                                qualifies.add(new Qualify(index.readSubPageId(i), dist, cov));
                            }
                        }
                    }
                } else {
                    MTreeLeaf leaf = new MTreeLeaf<>(node, mtree.getObjectClass());
                    total = leaf.readNumberOfKeys();

                    for (int i = 0; i < total; i++) {
                        //triangle inequality
                        if (distParentToQuery - leaf.readDistanceToParent(i) <= range) {
                            obj = leaf.buildKey(i);
                            dist = obj.distanceTo(valueObj);
                            if (dist <= range) {
                                obj.setPreservedDistance(dist);
                                result.offer(obj);
                                if (result.size() >= k) {
                                    if (result.size() > k) {
                                        //remove of result list the upper bounds
                                        while ((result.size() > 0)
                                                && (result.peek().getPreservedDistance() == range)) {
                                            repetedList.add(result.poll());
                                        }
                                        // if list has less than k
                                        if (result.size() < k) {
                                            //add list of upper bound											
                                            result.addAll(repetedList);
                                        }
                                        repetedList.clear();
                                    }
                                    // update range
                                    if (!result.isEmpty()) {
                                        range = result.peek().getPreservedDistance();
                                    }
                                }
                            }
                        }
                    }

                }
            }
        } while (!qualifies.isEmpty());
        se.close();

        return result;
    }
}
