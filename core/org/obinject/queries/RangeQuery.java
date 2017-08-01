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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import org.obinject.block.MTreeIndex;
import org.obinject.block.MTreeLeaf;
import org.obinject.block.Node;
import org.obinject.block.SequentialNode;
import org.obinject.device.Session;
import org.obinject.meta.Entity;
import org.obinject.meta.Metric;
import org.obinject.storage.MTree;
import org.obinject.storage.Sequential;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class RangeQuery extends Conditional {

    private final Attribute attribute;
    private final Object value;
    private final double range;

    public RangeQuery(Attribute attribute, Object value, double range) {
        this.attribute = attribute;
        this.value = value;
        this.range = range;
    }

    private class Qualify implements Comparable<Qualify> {

        private final long pageId;
        private final double distanceParentToPointQuery;

        public Qualify(long pageId, double distanceParentToPointQuery) {
            this.pageId = pageId;
            this.distanceParentToPointQuery = distanceParentToPointQuery;
        }

        public double getDistanceParentToPointQuery() {
            return distanceParentToPointQuery;
        }

        public long getPageId() {
            return pageId;
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

    /**
     *
     * @return
     */
    @Override
    public Collection process() {
        if (range > 0) {
            Collection result = null;
            Metric metric = null;
            for (Iterator it = attribute.getSchemas().iterator(); it.hasNext();) {
                Schema schema = (Schema) it.next();
                if (schema.getKeyStructure() instanceof MTree) {
                    metric = (Metric) schema.newEntity(value);
                    //find btree
                    result = rangeQueryInMtree(metric);
                    break;
                }
            }
            if (metric == null) {
                if (!attribute.getSchemas().isEmpty()) {
                    Schema schema = (Schema) (attribute.getSchemas().iterator().next());
                    Entity entity = schema.newEntity(value);
                    result = rangeQueryInSequential((Metric) (entity));
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
    public Collection rangeQueryInSequential(Metric valueObj) {
        Sequential sequential = (Sequential) valueObj.getKeyStructure();
        Session se = sequential.getWorkspace().openSession();
        List result = new LinkedList<>();

        long firstNode = sequential.getRootPageId();

        if (firstNode != 0) {
            long actualPageId = firstNode;
            long firstPageId = actualPageId;
            int total;
            Metric obj;
            double dist;

            do {
                SequentialNode actualSeqNode = new SequentialNode(se.load(actualPageId), sequential.getObjectClass());

                total = actualSeqNode.readNumberOfEntitries();
                for (int i = 0; i < total; i++) {
                    obj = (Metric) actualSeqNode.buildEntity(i);
                    dist = obj.distanceTo(valueObj);
                    if (dist <= range) {
                        obj.setPreservedDistance(dist);
                        result.add(obj);
                    }
                }
                actualPageId = actualSeqNode.readNextPageId();
            } while (actualPageId != firstPageId);

        }
        se.close();

        return result;
    }

    /**
     *
     * @param valueObj
     * @return
     */
    public Collection rangeQueryInMtree(Metric valueObj) {
        long pageId;
        double distParentToQuery, dist, cov;
        int total;
        Qualify objQualify;
        Metric obj;

        MTree mtree = (MTree) valueObj.getKeyStructure();
        Session se = mtree.getWorkspace().openSession();
        LinkedList<Metric> result = new LinkedList<>();
        PriorityQueue<Qualify> qualifies = new PriorityQueue<>();
        qualifies.add(new Qualify(mtree.getRootPageId(), Double.MAX_VALUE));

        do {

            objQualify = qualifies.poll();

            pageId = objQualify.getPageId();
            distParentToQuery = objQualify.getDistanceParentToPointQuery();

            Node node = se.load(pageId);

            if (MTreeIndex.matchNodeType(node)) {
                MTreeIndex index = new MTreeIndex(node, mtree.getObjectClass());
                total = index.readNumberOfKeys();

                for (int i = 0; i < total; i++) {
                    cov = index.readCoveringRadius(i);

                    //triangle inequality
                    if (distParentToQuery - index.readDistanceToParent(i) <= cov + range) {
                        obj = index.buildKey(i);
                        dist = obj.distanceTo(valueObj);
                        if (dist <= cov + range) {
                            if (!qualifies.add(new Qualify(index.readSubPageId(i), dist))) {
                                System.out.println("@@@@@ falhou na insercao da qualificacao do range");
                            }
                        }
                    }

                }
            } else {
                MTreeLeaf leaf = new MTreeLeaf(node, mtree.getObjectClass());
                total = leaf.readNumberOfKeys();
                for (int i = 0; i < total; i++) {
                    //triangle inequality
                    if (distParentToQuery - leaf.readDistanceToParent(i) <= range) {
                        obj = leaf.buildKey(i);
                        dist = obj.distanceTo(valueObj);
                        if (dist <= range) {
                            obj.setPreservedDistance(dist);
                            if (result.offer(obj) == false) {
                                System.out.println("@@@@@ falhou na insercao do resultado do range");
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
