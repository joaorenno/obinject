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

import java.util.ArrayList;
import org.obinject.meta.Entity;
import org.obinject.meta.Uuid;

/**
 * Folha do Hash Extensivel. Sem bit na folha. Entidades encontram-se ordenadas
 * por UUID. 
 *
 * @param <E> 
 * 
 * <blockquote><pre>
 * {@code
 * Design:
 *
 *                                  +--------------------------------------------+
 *                                  |      +--------------------------------+    |
 *                                  |      |      +--------------------+    |    |
 *                                  |      |      |                    |    |    |
 * +--------------+--------------+--|------|------|---+----------------V----V----V----+
 * |node|prev|next| number |local|offset|offset|offset|                |ent2|ent1|ent0|
 * |page|page|page|   of   |depth|------+------+------|                |    |    |    |
 * | Id | Id | Id |entities|     |ent[0]|ent[1]|ent[2]|<--free space-->|    |    |    |
 * +--------------+--------------+------+------+------|                |--------------|
 * |    header    |   features   |      entries       |                |   entities   |
 * +--------------+--------------+--------------------+----------------+--------------+
 * }
 * </pre></blockquote>
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Luiz Olmes Carvalho <olmes@icmc.usp.br>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class EHashBucket<E extends Entity<E>> extends EntityNode<E>
{

    public static final int nodeType = 10;

    /**
     *
     * @param node
     * @param clazz
     */
    public EHashBucket(Node node, Class<E> clazz)
    {
	super(node, clazz);
        initialize(node);
    }

    /**
     *
     * @param entity
     * @return
     */
    public boolean addEntity(E entity)
    {
	int size = entity.sizeOfEntity();

	if (size + this.sizeOfEntry() > this.freeSpace())
	{
	    return false; // There is not free space
	}
	else
	{
	    int total = this.readNumberOfEntitries();

	    if (total == 0) // Add the first entity
	    {
		int off = this.sizeOfArray() - size;
		this.writeOffset(0, off);
		entity.pushEntity(this.getArray(), off);
		this.incrementNumberOfEntities();

		return true;
	    }
	    else
	    {
		int dest = this.readOffset(total - 1) - size;
		int src = this.readOffset(total - 1);
		// Meu Deus, agora as coisas estao piradas...
		int idx = 0;
		int len;
		int off;
		E ent;

		for (int i = total; i > 0; i--)
		{
		    ent = this.buildEntity(i - 1);
		    if (ent.getUuid().compareTo(entity.getUuid()) == -1) // uuid(ent) "is less" than uuid(entity)
		    {
			idx = i;
			break;
		    }
		    else
		    {
			// Move offset
			this.writeOffset(i, (this.readOffset(i - 1) - size));
		    }
		}
		// If it is the first position
		if (idx == 0)
		{
		    len = this.sizeOfArray() - src;
		    off = this.sizeOfArray() - size;
		}
		else
		{
		    len = this.readOffset(idx - 1) - src;
		    off = this.readOffset(idx - 1) - size;
		}

		this.move(dest, src, len); // Move keys				
		this.writeOffset(idx, off); // Offset
		// Push the entity into the block
		entity.pushEntity(this.getArray(), off);
		this.incrementNumberOfEntities();

		return true;
	    }
	}
    }

    /**
     *
     * @param idx
     * @return
     */
    public E buildEntity(int idx)
    {
	if ((idx >= 0) && (idx < this.readNumberOfEntitries()))
	{
	    E entity = this.newGenericType();
	    entity.pullEntity(this.getArray(), this.readOffset(idx));
	    return entity;
	}

	return null;
    }

    /**
     *
     */
    @Override
    public void clear()
    {
	this.writeInteger(Node.sizeOfHeader(), 0);
    }

    /**
     *
     * @param newBucket
     */
    public void copyTo(EHashBucket<E> newBucket)
    {
	newBucket.clear();
	newBucket.writeLocalDepth(this.readLocalDepth());
    }

    /**
     *
     * @param entity
     * @return
     */
    public Uuid findEntity(E entity)
    {
	int total = this.readNumberOfEntitries();
	E ent;

	for (int i = 0; i < total; i++)
	{
	    ent = this.buildEntity(i);
	    if (ent.isEqual(entity))
	    {
		return ent.getUuid();
	    }
	}

	return null;
    }

    /**
     *
     * @param uuid
     * @return
     */
    public E findUuid(Uuid uuid)
    {
	int low = 0;
	int high = this.readNumberOfEntitries() - 1;
	int mid;
	E ent;

	while (low <= high)
	{
	    mid = low + (high - low) / 2;
	    ent = this.buildEntity(mid);
	    if (uuid.compareTo(ent.getUuid()) == -1)
	    {
		high = mid - 1;
	    }
	    else if (uuid.compareTo(ent.getUuid()) == 1)
	    {
		low = mid + 1;
	    }
	    else
	    {
		return ent;
	    }
	}
//		E ent;
//
//		for (int i = 0; i < total; i++)
//		{
//			ent = this.buildEntity(i);
//			if (ent.getUuid().equals(uuid))
//			{
//				return ent;
//			}
//		}

	return null;
    }

    /**
     *
     * @return
     */
    protected int freeSpace()
    {
	int total = this.readNumberOfEntitries();
	if (total == 0)
	{
	    return this.sizeOfArray() - Node.sizeOfHeader() - this.sizeOfFeatures();
	}
	else
	{
	    return this.sizeOfArray() - Node.sizeOfHeader()
		    - this.sizeOfFeatures() - (total * this.sizeOfEntry())
		    - (this.sizeOfArray() - this.readOffset(total - 1));
	}
    }

//	public Entity<E>[] getAllEntities()
//	{
//		int total = this.readNumberOfEntities();
//		Entity<E>[] entities = new Entity[total];
//
//		for (int i = 0; i < total; i++)
//		{
//			entities[i] = this.buildEntity(i);
//		}
//
//		return entities;
//	}
    /**
     *
     * @return
     */
    public ArrayList<E> getAllEntities()
    {
	int total = this.readNumberOfEntitries();
	ArrayList<E> entities = new ArrayList<>(total);

	for (int i = 0; i < total; i++)
	{
	    entities.add(this.buildEntity(i));
	}

	return entities;
    }

    /**
     *
     * @return
     */
    @Override
    public int getNodeType()
    {
	return nodeType;
    }

    /**
     *
     */
    public void incrementLocalDepth()
    {
	int depth = this.readLocalDepth() + 1;
	int pos = Node.sizeOfHeader() + Node.sizeOfInteger;
	this.writeInteger(pos, depth);
    }

    /**
     *
     */
    protected void incrementNumberOfEntities()
    {
	int num = this.readNumberOfEntitries() + 1;
	int pos = Node.sizeOfHeader();
	this.writeInteger(pos, num);
    }

    /**
     *
     * @param node
     * @return
     */
    public static boolean matchNodeType(Node node)
    {
	return node.readNodeType() == EHashBucket.nodeType;
    }

    /**
     *
     * @return
     */
    public int readLocalDepth()
    {
	int pos = Node.sizeOfHeader() + Node.sizeOfInteger;
	return this.readInteger(pos);
    }

    /**
     *
     * @return
     */
    public int readNumberOfEntitries()
    {
	return this.readInteger(Node.sizeOfHeader());
    }

    /**
     *
     * @param idx
     * @return
     */
    protected int readOffset(int idx)
    {
	int pos = Node.sizeOfHeader() + this.sizeOfFeatures() + idx * this.sizeOfEntry();
	return this.readInteger(pos);
    }

    /**
     *
     * @return
     */
    public int sizeOfEntry()
    {
	return Node.sizeOfInteger;
    }

    /**
     *
     * @return
     */
    public int sizeOfFeatures()
    {
	return Node.sizeOfInteger + Node.sizeOfInteger;
    }

    private void writeLocalDepth(int depth)
    {
	int pos = Node.sizeOfHeader() + Node.sizeOfInteger;
	this.writeInteger(pos, depth);
    }

    /**
     *
     * @param idx
     * @param off
     */
    protected void writeOffset(int idx, int off)
    {
	int pos = Node.sizeOfHeader() + this.sizeOfFeatures() + idx * this.sizeOfEntry();
	this.writeInteger(pos, off);
    }
}
