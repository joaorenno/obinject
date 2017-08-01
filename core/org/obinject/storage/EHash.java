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

import java.util.ArrayList;
import java.util.LinkedList;
import org.obinject.block.EHashBucket;
import org.obinject.block.EHashDescriptor;
import org.obinject.block.EHashDirectory;
import org.obinject.block.Node;
import org.obinject.device.Session;
import org.obinject.device.Workspace;
import org.obinject.meta.Entity;
import org.obinject.meta.Uuid;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Luiz Olmes Carvalho <olmes@icmc.usp.br>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 *
 * @param <E>
 */
public abstract class EHash<E extends Entity<E>> extends AbstractEntityStructure<E> {

    private ArrayList<E> entitiesToInsert = new ArrayList<>();

    /**
     *
     * @param workspace
     */
    public EHash(Workspace workspace) {
        super(workspace);
    }

    @Override
    public boolean add(E entity) {
        entitiesToInsert.add(entity);
        return addIntoExtHash();
    }

    private boolean addIntoExtHash() {
        Session se = this.getWorkspace().openSession();
        EHashDescriptor descriptor = null;

        long pageIdDescriptor = se.findPageIdDescriptor(this.getClassUuid());
        descriptor = new EHashDescriptor(se.load(pageIdDescriptor));

        while (!entitiesToInsert.isEmpty()) {
            E entity = entitiesToInsert.remove(0);

            if (descriptor.readRootPageId() == 0) // Hash is empty
            {
                // Add first Bucket
                EHashBucket<E> newBucket = new EHashBucket<>(se.create(), this.getObjectClass());
                newBucket.addEntity(entity);
                // Circular link
                newBucket.writePreviousPageId(newBucket.getPageId());
                newBucket.writeNextPageId(newBucket.getPageId());
                // Adjust root
                descriptor.writeRootPageId(newBucket.getPageId());
                descriptor.incHashHeight();
            } else {
                long[] path = new long[descriptor.readHashHeight()];
                Node node = se.load(descriptor.readRootPageId());
                EHashDirectory directory;

                int level = 1;
                int bitLevel = 0;
                while (EHashDirectory.matchNodeType(node)) {
                    directory = new EHashDirectory(node);
                    path[level] = directory.getPageId();
                    level++;
                    int qualify = directory.indexOfEntry(entity.getUuid(), descriptor.readNumberOfBits(), bitLevel);
                    long sub = directory.readDirectory(qualify);
                    bitLevel += directory.readLocalDepth();
                    node = se.load(sub);
                }

                EHashBucket<E> bucket = new EHashBucket<>(node, this.getObjectClass());

                if (!bucket.addEntity(entity)) {
                    // Store all entities in a temporary list
                    entitiesToInsert.addAll(bucket.getAllEntities());
                    entitiesToInsert.add(entity);
                    bucket.clear();
                    // Increment Bucket Depth
                    bucket.incrementLocalDepth();
                    // Split Bucket
                    EHashBucket<E> newBucket = new EHashBucket<>(se.create(), this.getObjectClass());
                    bucket.copyTo(newBucket);
                    // Check Number of Bits
                    if (bucket.readLocalDepth() > descriptor.readNumberOfBits()) {
                        descriptor.incNumberOfBits();
                    }

                    level = path.length - 1;
                    long parent = path[level];
                    Node fullNode = bucket;
                    Node newNode = newBucket;
                    boolean incrementBits = true;

                    while (incrementBits) {
                        if (level > 0) {
                            directory = new EHashDirectory(se.load(parent));

                            if (level == path.length - 1 && bucket.readLocalDepth() <= directory.readLocalDepth()) {
                                // Nao incrementa bit pq a profundidade do bucket nao eh maior.
                                directory.adjustsAfterDouble(fullNode.getPageId(), newNode.getPageId());
                                incrementBits = false;
                            } else if (directory.incrementLocalDepth()) {
                                directory.adjustsAfterDouble(fullNode.getPageId(), newNode.getPageId());
                                incrementBits = false;
                            } else {
                                EHashDirectory newDirectory = new EHashDirectory(se.create());
                                directory.copyTo(newDirectory);
                                newDirectory.adjustsAfterDouble(fullNode.getPageId(), newNode.getPageId());
                                fullNode = directory;
                                newNode = newDirectory;
                            }

                            level--;
                            parent = path[level];
                        } else {
                            EHashDirectory newRoot = new EHashDirectory(se.create());
                            newRoot.incrementLocalDepth(); // Local Depth sera 1
                            newRoot.writeDirectory(0, fullNode.getPageId());
                            newRoot.writeDirectory(1, newNode.getPageId());
                            newRoot.writePreviousPageId(newRoot.getPageId());
                            newRoot.writeNextPageId(newRoot.getPageId());
                            descriptor.writeRootPageId(newRoot.getPageId());
                            descriptor.incHashHeight();
                            incrementBits = false;
                        }
                    }
                }
            }
        }

        se.close();

        return true;
    }

    @Override
    public long getRootPageId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return
     */
    public int height() {
        Session se = this.getWorkspace().openSession();

        long pageIdDescriptor = se.findPageIdDescriptor(this.getClassUuid());
        EHashDescriptor descriptor = new EHashDescriptor(se.load(pageIdDescriptor));
        se.close();
        if (descriptor == null) {
            return 0;
        }
        return descriptor.readHashHeight();
    }

    @Override
    public E find(Uuid uuid) {
        Session se = this.getWorkspace().openSession();
        EHashDescriptor descriptor = null;

        long pageIdDescriptor = se.findPageIdDescriptor(this.getClassUuid());
        descriptor = new EHashDescriptor(se.load(pageIdDescriptor));

        Node node = se.load(descriptor.readRootPageId());
        EHashDirectory directory;

        int bitLevel = 0;
        while (EHashDirectory.matchNodeType(node)) {
            directory = new EHashDirectory(node);
            int qualify = directory.indexOfEntry(uuid, descriptor.readNumberOfBits(), bitLevel);
            long sub = directory.readDirectory(qualify);
            bitLevel += directory.readLocalDepth();
            node = se.load(sub);
        }

        EHashBucket<E> bucket = new EHashBucket<>(node, this.getObjectClass());

        se.close();

        return bucket.findUuid(uuid);
    }

    /**
     *
     */
    public void bfs() {
        LinkedList<Long> fila = new LinkedList<>();
        LinkedList<Long> norep = new LinkedList<>();
        Session se = this.getWorkspace().openSession();
        EHashDescriptor descriptor = null;

        long pageIdDescriptor = se.findPageIdDescriptor(this.getClassUuid());
        descriptor = new EHashDescriptor(se.load(pageIdDescriptor));

        Node node;
        fila.add(descriptor.readRootPageId());

        while (!fila.isEmpty()) {
            long pageId = fila.removeFirst();
            node = se.load(pageId);

            if (!norep.contains(pageId)) // Gambi p/ n imprimir repetido
            {
                if (EHashBucket.matchNodeType(node)) {
                    EHashBucket<E> bucket = new EHashBucket<>(node, this.getObjectClass());
                    System.out.println("========== Bucket Page ID: " + bucket.getPageId() + " # Depth: " + bucket.readLocalDepth() + " ==========");
                    for (Entity e : bucket.getAllEntities()) {
                        System.out.println(e);
                    }
                } else {
                    EHashDirectory dir = new EHashDirectory(node);
                    int depth = dir.readLocalDepth();
                    System.out.println("========== Direct Page ID: " + dir.getPageId() + " # Depth: " + depth + " ==========");
                    int total = (int) Math.pow(2, depth);
                    long dirPageId;
                    for (int i = 0; i < total; i++) {
                        dirPageId = dir.readDirectory(i);
                        System.out.println(i + " -> " + dirPageId);
                        fila.add(dirPageId);
                    }
                }
            }
            norep.add(pageId);
        }
    }

    @Override
    public boolean remove(E key) {
        return false;
    }

}
