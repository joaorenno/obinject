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

import org.obinject.meta.Uuid;

/**
 * Diretorio do Hash Extensivel. 
 * 
 * <blockquote><pre>
 * {@code
 * Design:
 *
 * +--------------+--------+------------------------------------------------+-----+
 * |node|prev|next| local  |direc|direc|direc|direc|   |       direc        |     |
 * |type|page|page| depth  | [0] | [1] | [2] | [3] |...|[2 ^ localDepth - 1]|dead |
 * | Id | Id | Id |        |     |     |     |     |   |                    |space|
 * +--------------+--------+------------------------------------------------|     |
 * |    header    |features|                  directories                   |     |
 * +--------------+--------+------------------------------------------------+-----+
 * }
 * </pre></blockquote>
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Luiz Olmes Carvalho <olmes@icmc.usp.br>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class EHashDirectory extends AbstractNode
{

    public static final int nodeType = 11;

    /**
     *
     * @param node
     */
    public EHashDirectory(Node node)
    {
	super(node);
        initialize(node);
    }

    /**
     *
     */
    @Override
    public void clear()
    {
	// Do nothing
    }

    /**
     *
     * @param newDirectory
     */
    public void copyTo(EHashDirectory newDirectory)
    {
	int init = Node.sizeOfHeader(); // Inicia a copia no Local Depth

	// init = inicio da copia
	// Math.pow * size = todos as referencias do diretorio
	// sizeOfInt = espaco do Local Depth.
	int end = init + Node.sizeOfInteger + ((int) Math.pow(2, this.readLocalDepth())) * this.sizeOfDirectory();

	for (int i = init; i < end; i++)
	{
	    newDirectory.writeByte(i, this.readByte(i));
	}
    }

    /**
     *
     * @return
     */
    protected int deadSpace()
    {
	return this.sizeOfArray() - Node.sizeOfHeader() - this.sizeOfFeatures()
		- ((int) Math.pow(2, this.readLocalDepth()) * this.sizeOfDirectory());
    }

    /**
     *
     * @return
     */
    @Override
    protected int getNodeType()
    {
	return nodeType;
    }

    /**
     * Tries to increment the Local Depth. If Local Depth is incremented,
     * doubles this Directory.
     *
     * @return {@code true} if the Local Depth can be incremented; {@code false}
     * otherwise.
     *
     */
    public boolean incrementLocalDepth()
    {
	int depth = this.readLocalDepth();
	int count = (int) Math.pow(2, depth);
	int size = count * this.sizeOfDirectory();

	if (size > this.deadSpace())
	{
	    return false;
	}
	else
	{
	    // Double directory
	    for (int i = 0; i < count; i++)
	    {
		this.writeDirectory(i + count, this.readDirectory(i));
	    }

	    // Update depth
	    this.writeInteger(Node.sizeOfHeader(), depth + 1);

	    return true;
	}
    }

    /**
     *
     * @param uuid
     * @param numBits 
     * @param level Qtos bits ja andou.
     * @return
     */
    public int indexOfEntry(Uuid uuid, int numBits, int level)
    {
	long entry;
	int depth = this.readLocalDepth();
	int bitsInLong = Node.sizeOfLong * Node.bitsPerByte;

	// Os bits estao apenas em Least
	if (numBits - level < bitsInLong)
	{
	    entry = uuid.getLeastSignificantBits();
	    int nonSignificantLeast = bitsInLong - numBits + level;
	    entry <<= nonSignificantLeast;
	    entry >>>= (bitsInLong - depth);
	    return (int) entry;
	}
	else // Os bits estao em Most e, talvez, em Least
	{
	    entry = uuid.getMostSignificantBits();
	    int nonSignificantMost = bitsInLong - (numBits - bitsInLong) + level;
	    entry <<= nonSignificantMost;

	    // Bits de least: deve concatenar com o least?
	    if (numBits - level - bitsInLong < depth) // era >
	    {
		long least = uuid.getLeastSignificantBits();
		least >>>= bitsInLong - nonSignificantMost;
		entry |= least;
	    }

	    entry >>>= (bitsInLong - depth);
	    return (int) entry;
	}
    }

    /**
     *
     * @param node
     * @return
     */
    public static boolean matchNodeType(Node node)
    {
	return node.readNodeType() == EHashDirectory.nodeType;
    }

    /**
     *
     * @param idx
     * @return
     */
    public long readDirectory(int idx)
    {
	int pos = Node.sizeOfHeader() + this.sizeOfFeatures() + idx * this.sizeOfDirectory();
	return this.readLong(pos);
    }

    /**
     *
     * @return
     */
    public int readLocalDepth()
    {
	return this.readInteger(Node.sizeOfHeader());
    }

//	public int readNumberOfBits()
//	{
//		return this.readInt(Node.sizeOfHeader());
//	}
    /**
     *
     * @return
     */
    public int sizeOfDirectory()
    {
	return Node.sizeOfLong;
    }

    /**
     *
     * @return
     */
    public int sizeOfFeatures()
    {
	return Node.sizeOfInteger;
    }

    /**
     * Atualiza os ponteiros do diretorio. "Todos que apontavam para Full passam
     * a apontar para New..." (a frase que o Enzo diz que eu gosto...)
     *
     * @param fullPageId
     * @param newPageId
     */
    public void adjustsAfterDouble(long fullPageId, long newPageId)
    {
	int depth = this.readLocalDepth();
	int total = (int) Math.pow(2, depth);

	for (int i = depth; i < total; i++)
	{
	    if (this.readDirectory(i) == fullPageId)
	    {
		this.writeDirectory(i, newPageId);
	    }
	}
    }

    /**
     *
     * @param idx
     * @param dir
     */
    public void writeDirectory(int idx, long dir)
    {
	int pos = Node.sizeOfHeader() + this.sizeOfFeatures() + idx * this.sizeOfDirectory();
	this.writeLong(pos, dir);
    }
}
