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

import java.util.Collection;
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
 * @param <M>
 */
public abstract class RangeQueryMTree<M extends Metric<M> & Entity<? super M>> extends AbstractStrategy<M> {

    private final M object;
    private final double range;

    public RangeQueryMTree(MTree mtree, M object, double range) {
        super(mtree);
        this.object = object;
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
    public Collection<M> solve() {
        long pageId;
        double distParentToQuery, dist, cov;
        int total;
        Qualify objQualify;

        long time = System.nanoTime();
        Session se = this.getStructure().getWorkspace().openSession();
        long diskAccess = se.getBlockAccess();
        LinkedList<M> result = new LinkedList<>();
        PriorityQueue<Qualify> qualifies = new PriorityQueue<>();
        qualifies.add(new Qualify(this.getStructure().getRootPageId(), Double.MAX_VALUE));

        do {

            objQualify = qualifies.poll();

            pageId = objQualify.getPageId();
            distParentToQuery = objQualify.getDistanceParentToPointQuery();

            Node node = se.load(pageId);

            if (MTreeIndex.matchNodeType(node)) {
                MTreeIndex index = new MTreeIndex(node, this.getStructure().getObjectClass());
                total = index.readNumberOfKeys();

                for (int i = 0; i < total; i++) {
                    cov = index.readCoveringRadius(i);

                    //triangle inequality
                    if (distParentToQuery - index.readDistanceToParent(i) <= cov + range) {
                        M build = (M) index.buildKey(i);

                        this.getPerformanceMeasurement().incrementVerification(1);
                        dist = build.distanceTo(object);
                        if (dist <= cov + range) {
                            if (!qualifies.add(new Qualify(index.readSubPageId(i), dist))) {
                                System.out.println("@@@@@ falhou na insercao da qualificacao do range");
                            }
                        }
                    }

                }
            } else {
                MTreeLeaf leaf = new MTreeLeaf(node, this.getStructure().getObjectClass());
                total = leaf.readNumberOfKeys();
                for (int i = 0; i < total; i++) {
                    //triangle inequality
                    if (distParentToQuery - leaf.readDistanceToParent(i) <= range) {
                        M build = (M) leaf.buildKey(i);

                        this.getPerformanceMeasurement().incrementVerification(1);
                        dist = build.distanceTo(object);
                        if (dist <= range) {
                            build.setPreservedDistance(dist);
                            if (result.offer(build) == false) {
                                System.out.println("@@@@@ falhou na insercao do resultado do range");
                            }

                        }
                    }
                }
            }
        } while (!qualifies.isEmpty());
        time = System.nanoTime() - time;
        this.getPerformanceMeasurement().incrementTime(time);
        this.getPerformanceMeasurement().incrementMeasurement();

        se.close();
        return result;
    }
}
