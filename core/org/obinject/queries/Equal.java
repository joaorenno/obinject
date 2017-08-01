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
import org.obinject.meta.Sort;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class Equal extends Conditional {

    private final Attribute attribute;
    private final Object value;
    
    public Equal(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public Collection process() {
        Collection result = null;
        Schema actualSchema;
        Iterator it = attribute.getSchemas().iterator();
        boolean findSchema = false;
        while (it.hasNext()) {
            actualSchema = (Schema) it.next();
            if (actualSchema.getKeyStructure() instanceof BTree) {
                result = equalInBtree(actualSchema);
                findSchema = true;
                break;
            }
        }
        if (!findSchema) {
            if (!attribute.getSchemas().isEmpty()) {
                actualSchema = (Schema) (attribute.getSchemas().iterator().next());
                result = equalInSequential(actualSchema);
            }
        }
        return result;
    }

    public Collection<? extends Entity> equalInSequential(Schema schema) {
        System.out.println("execute seq");
        return null;
    }

    public Collection<? extends Entity> equalInBtree(Schema schema) {
        Sort valueObj = (Sort) schema.newKey(value);
        BTree btree = (BTree) schema.getKeyStructure();
        Session se = btree.getWorkspace().openSession();
        Node node = se.load(btree.getRootPageId());
        LinkedList result = new LinkedList();
        BTreeLeaf leaf;
        BTreeIndex index;
        while (BTreeIndex.matchNodeType(node)) {
            index = new BTreeIndex<>(node, btree.getObjectClass());
            long sub = index.readSubPageId(index.indexOfQualify(valueObj));
            node = se.load(sub);

        }//endwhile

        leaf = new BTreeLeaf<>(node, btree.getObjectClass());
        int idx = leaf.indexOfKey(valueObj);
        if (idx >= 0) {
            Uuid uuid = leaf.readEntityUuid(idx);
            Entity entity = schema.getEntityStructure().find(uuid);
            result.add(entity);
        }
        se.close();
        return result;
    }
}
