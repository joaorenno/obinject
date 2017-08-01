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
package org.obinject.block;

import org.obinject.meta.Entity;

/**
 * <p>
 * This class implements a B+ Tree Node for Entity.</p>
 *
 * @param <E>
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 *
 */
public abstract class BTreeEntityNode<E extends Entity<E>> extends EntityNode<E> {

    protected long calculatedComparisons;

    /**
     *
     * @param node
     * @param entityClass
     */
    public BTreeEntityNode(Node node, Class<E> entityClass) {
        super(node, entityClass);
    }

    /**
     * Returns the number of comparisons ({@Code compareTo}) made by this class.
     *
     * @return the number of comparisons
     */
    public long getCalculatedComparisons() {
        return calculatedComparisons;
    }

    /**
     *
     */
    @Override
    public void clear() {
        //zering numberOfEntities
        int pos = BTreeEntityNode.sizeOfHeader();
        this.writeInteger(pos, 0);
    }

    /**
     *
     */
    protected void decrementNumberOfEntries() {
        int num = this.readNumberOfEntries() - 1;
        int pos = BTreeEntityNode.sizeOfHeader();
        this.writeInteger(pos, num);
    }

    /**
     *
     */
    protected void incrementNumberOfEntries() {
        int num = this.readNumberOfEntries() + 1;
        int pos = BTreeEntityNode.sizeOfHeader();
        this.writeInteger(pos, num);
    }

    /**
     *
     * @return
     */
    public int readNumberOfEntries() {
        int pos = BTreeEntityNode.sizeOfHeader();
        return this.readInteger(pos);
    }

    /**
     *
     * @return
     */
    protected abstract int sizeOfEntry();

    /**
     *
     * @return
     */
    protected abstract int sizeOfFeatures();

}
