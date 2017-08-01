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
import java.util.Iterator;
import java.util.LinkedList;
import org.obinject.block.BTreeIndex;
import org.obinject.block.BTreeLeaf;
import org.obinject.block.Node;
import org.obinject.device.Session;
import org.obinject.meta.Entity;
import org.obinject.meta.Uuid;
import org.obinject.storage.BTree;
import org.obinject.storage.EntityStructure;
import org.obinject.meta.Sort;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class Between extends Conditional {

    private final Attribute attribute;
    private final Object lower;
    private final Object upper;

    /**
     *
     * @param attribute
     * @param lower
     * @param upper
     */
    public Between(Attribute attribute, Object lower, Object upper) {
        this.attribute = attribute;
        this.lower = lower;
        this.upper = upper;
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<? extends Entity> process() {
        Sort<? extends Sort> lowerObj = null;
        Sort<? extends Sort> upperObj = null;
        Collection<? extends Entity> result = null;
        for (Iterator it = attribute.getSchemas().iterator(); it.hasNext();) {
            Schema schema = (Schema) it.next();
            if (schema.getKeyStructure() instanceof BTree) {
                lowerObj = (Sort) schema.newEntity(lower);
                upperObj = (Sort) schema.newEntity(upper);
                //find btree
                result = betweenInBtree(lowerObj, upperObj);
                break;
            }
        }
        if (upperObj == null) {
            if (!attribute.getSchemas().isEmpty()) {
                Schema schema = (Schema) (attribute.getSchemas().iterator().next());
                Entity entity = schema.newEntity(lower);
                result = betweenInSequential(entity);
            }
        }
        return result;
    }

    /**
     *
     * @param queryObject
     * @return
     */
    public Collection<? extends Entity> betweenInSequential(Entity queryObject) {
        System.out.println("execute seq");
        return null;
    }

    /**
     *
     * @param lowerObj
     * @param upperObj
     * @return
     */
    public Collection<? extends Entity> betweenInBtree(Sort lowerObj, Sort upperObj) {

        BTreeLeaf leaf;
        BTreeIndex index;
        BTree btree = (BTree) lowerObj.getKeyStructure();
        Session se = btree.getWorkspace().openSession();
        Node node = se.load(btree.getRootPageId());
        LinkedList result = new LinkedList<>();

        while (BTreeIndex.matchNodeType(node)) {
            index = new BTreeIndex(node, btree.getClass());
            long sub = index.readSubPageId(index.indexOfQualify(lowerObj));
            node = se.load(sub);

        }//endwhile

        leaf = new BTreeLeaf<>(node, btree.getObjectClass());
        int idx = 0;
        int total = leaf.readNumberOfKeys();
        Sort objBetween;
        Uuid uuidBetween;
        do {
            if (idx == total) {
                node = se.load(leaf.readNextPageId());
                leaf = new BTreeLeaf<>(node, btree.getObjectClass());
                idx = 0;
                objBetween = leaf.buildKey(idx);
                uuidBetween = leaf.readEntityUuid(idx);
            } else {
                objBetween = leaf.buildKey(idx);
                uuidBetween = leaf.readEntityUuid(idx);
                idx++;
            }
        } while (((Comparable) objBetween).compareTo(lowerObj) < 0);
        while ((((Comparable) objBetween).compareTo(lower) >= 0) && (((Comparable) objBetween).compareTo(upper) <= 0)) {
            if (idx == leaf.readNumberOfKeys() - 1) {
                node = se.load(leaf.readNextPageId());
                leaf = new BTreeLeaf<>(node, btree.getObjectClass());
                uuidBetween = leaf.readEntityUuid(idx);
                idx = 0;
                objBetween = leaf.buildKey(idx);
            } else {
                EntityStructure struct = ((Entity) objBetween).getEntityStructure();
                Entity entity = struct.find(uuidBetween);
                result.add(entity);
                objBetween = leaf.buildKey(idx);
                uuidBetween = leaf.readEntityUuid(idx);
                idx++;
            }
        }

        se.close();

        return result;
    }
}
