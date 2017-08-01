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
import org.obinject.meta.Metric;

/**
 *<p>This class implements an M-Tree Node.</p>
 * 
 * @param <K> 
 * 
 * <blockquote><pre>
 * {@code
 * Design:
 *
 * +--------------+--------+----------------------------------------------+
 * |node|prev|next| number |                                              |
 * |type|page|page|   of   |                                              |
 * |    | Id | Id |  keys  |<---------------- free space ---------------->|
 * |--------------+--------|                                              |
 * |   header     |features|                                              |
 * +--------------+--------+----------------------------------------------+
 * }
 * </pre></blockquote>
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Luiz Olmes Carvalho <olmes@icmc.usp.br>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public abstract class MTreeNode<K extends Metric<K> & Entity<? super K>> extends KeyNode<K>
{
    
    protected long calculatedDistance;

    /**
     *
     * @param node
     * @param keyClass
     */
    public MTreeNode(Node node, Class<K> keyClass)
    {
	super(node, keyClass);
    }
    
    /**
     * Returns the number of distance calculations ({@Code compareTo}) made by this class.
     *
     * @return the number of distance calculations
     */
    public long getCalculatedDistance() {
        return calculatedDistance;
    }

    /**
     *
     * @param idx
     * @return
     */
    public K buildKey(int idx)
    {
	//instantiation by reflection
	if ((idx >= 0) && (idx < this.readNumberOfKeys()))
	{
            K key;
            if(this instanceof MTreeLeaf){
                MTreeLeaf leaf = (MTreeLeaf)this;
                key = this.newGenericType(leaf.readEntityUuid(idx));
            }else{
                key = this.newGenericType();
            }            
	    //pull the key of the page
	    key.pullKey(this.getArray(), this.readOffset(idx));
	    return key;
	}//endif

	return null;
    }

    /**
     *
     * @param idx
     * @param metric
     */
    public void rebuildKey(int idx, K metric)
    {
	//instantiation by reflection
	if ((idx >= 0) && (idx < this.readNumberOfKeys()))
	{
	    //pull the key of the page
	    metric.pullKey(this.getArray(), this.readOffset(idx));
	}//endif
    }

    /**
     *
     */
    @Override
    public final void clear()
    {
	// Zering number of keys.
	this.writeInteger(MTreeNode.sizeOfHeader(), 0);
    }

    /**
     *
     */
    protected final void decrementNumberOfKeys()
    {
	this.writeInteger(MTreeNode.sizeOfHeader(), this.readNumberOfKeys() - 1);
    }

    /**
     *
     * @return
     */
    protected final int freeSpace()
    {
	int total = this.readNumberOfKeys();

	return total == 0
		? this.sizeOfArray() // Total
		- MTreeNode.sizeOfHeader() // Header
		- this.sizeOfFeatures() // Features

		: this.sizeOfArray() // Total
		- MTreeNode.sizeOfHeader() // Header
		- this.sizeOfFeatures() // Features
		- (total * this.sizeOfEntry()) // Entries
		- (this.sizeOfArray() - this.readOffset(total - 1)); // Keys
    }

    /**
     *
     * @param key
     * @return
     */
    public final int indexOfKey(K key)
    {
	K objKey;
	int total = this.readNumberOfKeys();

	for (int i = 0; i < total; i++)
	{
	    objKey = this.buildKey(i);
            calculatedDistance++;
	    if (objKey.distanceTo(key) == 0)
	    {
		return i;
	    }
	}

	return -1;
    }

    /**
     *
     */
    protected final void incrementNumberOfKeys()
    {
	this.writeInteger(MTreeNode.sizeOfHeader(), this.readNumberOfKeys() + 1);
    }

    /**
     *
     * @param idx
     * @return
     */
    public final double readDistanceToParent(int idx)
    {
	int pos = MTreeNode.sizeOfHeader()
		+ this.sizeOfFeatures()
		+ ((idx + 1) * this.sizeOfEntry())
		- MTreeNode.sizeOfInteger // off
		- MTreeNode.sizeOfDouble; // parent distance

	return this.readDouble(pos);
    }

    /**
     *
     * @return
     */
    @Override
    public final int readNumberOfKeys()
    {
	return this.readInteger(MTreeNode.sizeOfHeader());
    }

    /**
     *
     * @param idx
     * @return
     */
    protected final int readOffset(int idx)
    {
	int pos = MTreeNode.sizeOfHeader()
		+ this.sizeOfFeatures()
		+ ((idx + 1) * this.sizeOfEntry())
		- MTreeNode.sizeOfInteger; // offset

	return this.readInteger(pos);
    }

    /**
     *
     * @param idx
     * @return return true if key was removed.
     */
    public abstract boolean remove(int idx);

    /**
     *
     * @param idx
     * @param replaceKey
     * @return
     */
    protected boolean replace(int idx, K replaceKey)
    {
	int total = this.readNumberOfKeys();
	int offLastKey = this.readOffset(total - 1);
	int length = this.readOffset(idx) - offLastKey;
	int sizeOldKey = idx == 0 ? this.sizeOfArray() - this.readOffset(0) : this.readOffset(idx - 1) - this.readOffset(idx);
	int offSize = sizeOldKey - replaceKey.sizeOfKey();
	if (replaceKey.sizeOfKey() <= this.freeSpace() + sizeOldKey)
	{
	    this.move(offLastKey + offSize, offLastKey, length);
	    for (int i = idx; i <= total - 1; i++)
	    {
		this.writeOffset(i, this.readOffset(i) + offSize);
	    }
	    replaceKey.pushKey(this.getArray(), this.readOffset(idx));
	    return true;
	}
	else
	{
	    return false;
	}
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
    protected final int sizeOfFeatures()
    {
	return MTreeNode.sizeOfInteger; // number of keys
    }

    /**
     *
     * @param idx
     * @param dist
     */
    protected void writeDistanceToParent(int idx, double dist)
    {
	int pos = MTreeNode.sizeOfHeader()
		+ this.sizeOfFeatures()
		+ ((idx + 1) * this.sizeOfEntry())
		- MTreeNode.sizeOfInteger // off
		- MTreeNode.sizeOfDouble; // parent distance

	this.writeDouble(pos, dist);
    }

    /**
     *
     * @param idx
     * @param off
     */
    protected final void writeOffset(int idx, int off)
    {
	int pos = MTreeNode.sizeOfHeader()
		+ this.sizeOfFeatures()
		+ ((idx + 1) * this.sizeOfEntry())
		- MTreeNode.sizeOfInteger; // offset

	this.writeInteger(pos, off);
    }
}
