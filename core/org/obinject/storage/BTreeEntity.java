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
package org.obinject.storage;

import org.obinject.block.BTreeEntityDescriptor;
import org.obinject.block.BTreeEntityIndex;
import org.obinject.block.BTreeEntityLeaf;
import org.obinject.block.Node;
import org.obinject.device.Session;
import org.obinject.device.Workspace;
import org.obinject.meta.Entity;
import org.obinject.meta.Uuid;
import org.obinject.queries.AveragePerformance;
import org.obinject.queries.PerformanceMeasurement;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Luiz Olmes Carvalho <olmes@icmc.usp.br>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 *
 * @param <E>
 */
public abstract class BTreeEntity<E extends Entity<E>> extends AbstractEntityStructure<E> {
//	private BTreeEntityDescriptor descriptor;

    PerformanceMeasurement averageForAdd = new AveragePerformance();
    PerformanceMeasurement averageForFind = new AveragePerformance();

    /**
     *
     * @param workspace
     */
    public BTreeEntity(Workspace workspace) {
        super(workspace);

        Session se = this.getWorkspace().openSession();
        long pageIdDescriptor = se.findPageIdDescriptor(this.getClassUuid());
        BTreeEntityDescriptor descriptor = new BTreeEntityDescriptor(se.load(pageIdDescriptor));
        se.close();
    }

    @Override
    public boolean add(E entity) {
        long time = System.nanoTime();
        // Open descriptor
        Session se = this.getWorkspace().openSession();
        long diskAccess = se.getBlockAccess();
        long pageIdDescriptor = se.findPageIdDescriptor(this.getClassUuid());
        BTreeEntityDescriptor descriptor = new BTreeEntityDescriptor(se.load(pageIdDescriptor));
        long rootPageId = descriptor.readRootPageId();
        long parent;
        // If root does not exist
        if (rootPageId == 0) {
            // Add first leaf
            BTreeEntityLeaf<E> newLeaf = new BTreeEntityLeaf<>(se.create(), this.getObjectClass());
            newLeaf.addFirstEntity(entity);
            // Circularly link
            newLeaf.writePreviousPageId(newLeaf.getPageId());
            newLeaf.writeNextPageId(newLeaf.getPageId());
            // Adjust rootPageId
            descriptor.writeRootPageId(newLeaf.getPageId());
            averageForAdd.incrementVerification(newLeaf.getCalculatedComparisons());
            descriptor.incTreeHeight();
        } else {
            long[] path = new long[descriptor.readTreeHeight()];
            Node node = se.load(rootPageId);
            BTreeEntityIndex<E> index;

            int level = 1;
            while (BTreeEntityIndex.matchNodeType(node)) {
                index = new BTreeEntityIndex<>(node, this.getObjectClass());
                path[level] = index.getPageId();
                level++;
                long sub = index.readSubPageId(index.indexOfQualify(entity.getUuid()));
                node = se.load(sub);
                averageForAdd.incrementVerification(index.getCalculatedComparisons());
            }

            BTreeEntityLeaf<E> leaf = new BTreeEntityLeaf<>(node, this.getObjectClass());

            if (leaf.addEntity(entity) == false) {
                // Split

                boolean promote = true;
                BTreeEntityIndex<E> newIndex;
                node = leaf;
                // Split leaf
                BTreePromotion objPromote = splitLeaf(leaf, entity, entity.getUuid(), se);

                level = path.length - 1;

                while (level > 0) {
                    parent = path[level];
                    node = se.load(parent);

                    if (promote == true) {
                        //add in index
                        index = new BTreeEntityIndex<>(node, this.getObjectClass());
                        if (index.addEntity(objPromote.getUuid(), objPromote.getSubPageId()) == false) {
                            // Split index
                            objPromote = this.splitIndex(index, objPromote.getUuid(), objPromote.getSubPageId(), se);
                            promote = true;
                        } else {
                            promote = false;
                        }
                        averageForAdd.incrementVerification(index.getCalculatedComparisons());
                    }
                    level--;
                }

                if (promote == true) {
                    // Promote
                    newIndex = new BTreeEntityIndex<>(se.create(), this.getObjectClass());
                    newIndex.addFirstEntity(objPromote.getUuid(), node.getPageId(), objPromote.getSubPageId());
                    // Circularly link
                    newIndex.writePreviousPageId(newIndex.getPageId());
                    newIndex.writeNextPageId(newIndex.getPageId());
                    descriptor.writeRootPageId(newIndex.getPageId());
                    descriptor.incTreeHeight();
                    averageForAdd.incrementVerification(newIndex.getCalculatedComparisons());
                }
            }
            averageForAdd.incrementVerification(leaf.getCalculatedComparisons());
//            } else {
//                return false;
//            }
        }

        se.close();
        //statistic for add
        diskAccess = se.getBlockAccess() - diskAccess;
        this.averageForAdd.incrementDiskAccess(diskAccess);
        time = System.nanoTime() - time;
        this.averageForAdd.incrementTime(time);
        this.averageForAdd.incrementMeasurement();
        return true;
    }

    @Override
    public E find(Uuid uuid) {
        long time = System.nanoTime();
        Session se = this.getWorkspace().openSession();
        long diskAccess = se.getBlockAccess();
        long pageIdDescriptor = se.findPageIdDescriptor(this.getClassUuid());
        BTreeEntityDescriptor descriptor = new BTreeEntityDescriptor(se.load(pageIdDescriptor));
        long rootPageId = descriptor.readRootPageId();
        E entityFinded = null;
        BTreeEntityLeaf<E> leaf;
        BTreeEntityIndex<E> index;
        Node node;
        if (rootPageId != 0) {
            node = se.load(rootPageId);
            while (BTreeEntityIndex.matchNodeType(node)) {
                index = new BTreeEntityIndex<>(node, this.getObjectClass());
                long sub = index.readSubPageId(index.indexOfQualify(uuid));
                node = se.load(sub);
                averageForFind.incrementVerification(index.getCalculatedComparisons());
            }//endwhile
            leaf = new BTreeEntityLeaf<>(node, this.getObjectClass());
            entityFinded = leaf.findEntity(uuid);
            averageForFind.incrementVerification(leaf.getCalculatedComparisons());
        }
        se.close();
        //statistic for add
        diskAccess = se.getBlockAccess() - diskAccess;
        this.averageForFind.incrementDiskAccess(diskAccess);
        time = System.nanoTime() - time;
        this.averageForFind.incrementTime(time);
        this.averageForFind.incrementMeasurement();
        return entityFinded;
    }

    /**
     *
     * @return
     */
    public PerformanceMeasurement getAverageForAdd() {
        return averageForAdd;
    }

    /**
     *
     * @return
     */
    public PerformanceMeasurement getAverageForFind() {
        return averageForFind;
    }

    @Override
    public long getRootPageId() {
        Session se = this.getWorkspace().openSession();
        long rootPageId = 0;
        long pageIdDescriptor = se.findPageIdDescriptor(this.getClassUuid());
        BTreeEntityDescriptor descriptor = new BTreeEntityDescriptor(se.load(pageIdDescriptor));
        rootPageId = descriptor.readRootPageId();
        se.close();

        return rootPageId;
    }

    /**
     *
     * Review this method.
     *
     * @param entity
     * @return
     */
    @Override
    public boolean remove(E entity) {
        Node node;
        boolean result = false;
        BTreeEntityLeaf<E> leaf;
        BTreeEntityIndex<E> index;

        Session se = this.getWorkspace().openSession();
        long pageIdDescriptor = se.findPageIdDescriptor(this.getClassUuid());
        BTreeEntityDescriptor descriptor = new BTreeEntityDescriptor(se.load(pageIdDescriptor));
        long rootPageId = descriptor.readRootPageId();

        if (rootPageId != 0) {

            node = se.load(descriptor.readRootPageId());

            while (BTreeEntityIndex.matchNodeType(node)) {
                index = new BTreeEntityIndex<>(node, this.getObjectClass());
                long sub = index.readSubPageId(index.indexOfQualify(entity.getUuid()));
                node = se.load(sub);

            }//endwhile
            leaf = new BTreeEntityLeaf<>(node, this.getObjectClass());
            int idx = leaf.indexOfUuid(entity.getUuid());
            result = leaf.remove(idx);

        }
        se.close();
        return result;
    }

    public int height() {
        int height = 0;

        Session se = this.getWorkspace().openSession();
        long pageIdDescriptor = se.findPageIdDescriptor(this.getClassUuid());
        BTreeEntityDescriptor descriptor = new BTreeEntityDescriptor(se.load(pageIdDescriptor));
        height = descriptor.readTreeHeight();
        se.close();
        return height;
    }

    private BTreePromotion splitIndex(BTreeEntityIndex<E> fullIndex, Uuid uuid, long pageId, Session se) {
        int i;
        int total = fullIndex.readNumberOfEntries() + 1;
        int half = ((int) (total / 2));
        BTreeEntityIndex<E> newIndex = new BTreeEntityIndex<>(se.create(), this.getObjectClass());

        // Copy orded of entities and subPageId
        long vectSub[] = new long[total + 1];
        Uuid vectUuid[] = new Uuid[total];
        // Copy entity and subPageId
        for (i = 0; i < total - 1; i++) {
            vectUuid[i] = fullIndex.readEntityUuid(i);
            vectSub[i] = fullIndex.readSubPageId(i);
        }
        // Last subPageId
        vectSub[i] = fullIndex.readSubPageId(i);
        // Moving for find position of the entity
        while (i > 0) {
            averageForAdd.incrementVerification(1);
            if (uuid.compareTo(vectUuid[i - 1]) < 0) {
                // Copy the entity and pageId in the last position
                vectUuid[i] = vectUuid[i - 1];
                vectSub[i + 1] = vectSub[i];
                i--;
            } else {
                break;
            }
        }
        // Copy entity and pageId in the correct position
        vectUuid[i] = uuid;
        // Copy the pageId in the correct position
        vectSub[i + 1] = pageId;
        // Copy lower half of entities and subPageId
        fullIndex.addFirstEntity(vectUuid[0], vectSub[0], vectSub[1]);
        for (i = 1; i < half; i++) {
            fullIndex.addEntity(vectUuid[i], vectSub[i + 1]);
        }
        // Copy upper half of entities and subPageId
        newIndex.addFirstEntity(vectUuid[half + 1], vectSub[half + 1], vectSub[half + 2]);

        for (i = half + 1; i < total; i++) {
            newIndex.addEntity(vectUuid[i], vectSub[i + 1]);
        }

        BTreePromotion promote = new BTreePromotion(vectUuid[half], newIndex.getPageId());

        // Circularly link
        newIndex.writeNextPageId(fullIndex.readNextPageId());
        fullIndex.writeNextPageId(newIndex.getPageId());
        newIndex.writePreviousPageId(fullIndex.getPageId());
        long nextPageId = newIndex.readNextPageId();
        if (nextPageId != fullIndex.getPageId()) {
            BTreeEntityIndex<E> nextIndex = new BTreeEntityIndex<>(se.load(nextPageId), this.getObjectClass());
            nextIndex.writePreviousPageId(newIndex.getPageId());
        } else {
            fullIndex.writePreviousPageId(newIndex.getPageId());
        }

        return promote;
    }

    private BTreePromotion splitLeaf(BTreeEntityLeaf<E> fullLeaf, E entity, Uuid uuid, Session se) {
        int i;
        int total = fullLeaf.readNumberOfEntries() + 1;
        int half = ((int) (total / 2));
        BTreeEntityLeaf<E> newLeaf = new BTreeEntityLeaf<>(se.create(), this.getObjectClass());

        // Copy all entities and uuid
        E vectEntity[] = (E[]) new Entity[total];
        // Copy entity and uuid
        for (i = 0; i < total - 1; i++) {
            vectEntity[i] = fullLeaf.buildEntity(i);
        }
        // Moving for find position of the entity
        while (i > 0) {
            averageForAdd.incrementVerification(1);
            if (entity.getUuid().compareTo(vectEntity[i - 1].getUuid()) < 0) {
                // Copy the entity and pageId in the last position
                vectEntity[i] = vectEntity[i - 1];
                i--;
            } else {
                break;
            }
        }
        // Copy entity and pageId in the correct position
        vectEntity[i] = entity;
        // Copy lower half of entities and uuid		
        fullLeaf.addFirstEntity(vectEntity[0]);
        for (i = 1; i < half; i++) {
            fullLeaf.addEntity(vectEntity[i]);
        }
        // Copy upper half of entities and uuid
        newLeaf.addFirstEntity(vectEntity[half]);
        for (i = half + 1; i < total; i++) {
            newLeaf.addEntity(vectEntity[i]);
        }

        BTreePromotion promote = new BTreePromotion(vectEntity[half].getUuid(), newLeaf.getPageId());

        // Circularly link
        newLeaf.writeNextPageId(fullLeaf.readNextPageId());
        fullLeaf.writeNextPageId(newLeaf.getPageId());
        newLeaf.writePreviousPageId(fullLeaf.getPageId());
        long nextPageId = newLeaf.readNextPageId();
        if (nextPageId != fullLeaf.getPageId()) {
            BTreeEntityLeaf<E> nextLeaf = new BTreeEntityLeaf<>(se.load(nextPageId), this.getObjectClass());
            nextLeaf.writePreviousPageId(newLeaf.getPageId());
        } else {
            fullLeaf.writePreviousPageId(newLeaf.getPageId());
        }

        return promote;
    }

    class BTreePromotion {

        private final Uuid uuid;
        private final long subPageId;

        public BTreePromotion(Uuid uuid, long subPageId) {
            this.uuid = uuid;
            this.subPageId = subPageId;
        }

        public Uuid getUuid() {
            return uuid;
        }

        public long getSubPageId() {
            return subPageId;
        }
    }
}
