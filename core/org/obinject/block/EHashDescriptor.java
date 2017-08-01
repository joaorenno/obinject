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

/**
 *
 * <blockquote><pre>
 * {@code
 * Design:
 *
 * +--------------+------------------+----------------+
 * |node|prev|next|root|number| hash |                |
 * |type|page|page|page|  of  |height|                |
 * |    | Id | Id | Id | bits |      |<--free space-->|
 * |--------------+------------------|                |
 * |    header    |     features     |                |
 * +--------------+------------------+----------------+
 * }
 * </pre></blockquote>
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Luiz Olmes Carvalho <olmes@icmc.usp.br>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class EHashDescriptor extends DescriptorNode
{

    /**
     *
     */
    public static final int nodeType = 1006;

    /**
     *
     * @param node
     */
    public EHashDescriptor(Node node)
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
	writePreviousPageId(0);
	writeNextPageId(0);
	writeHashHeight(0);
	writeNumberOfBits(0);
	writeRootPageId(0);
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
     *
     */
    public void incHashHeight()
    {
	this.writeHashHeight(this.readHashHeight() + 1);
    }

    /**
     *
     */
    public void incNumberOfBits()
    {
	this.writeNumberOfBits(this.readNumberOfBits() + 1);
    }

    /**
     *
     * @return
     */
    public int readHashHeight()
    {
	int pos = Node.sizeOfHeader() + Node.sizeOfLong + Node.sizeOfInteger;
	return this.readInteger(pos);
    }

    /**
     *
     * @return
     */
    public int readNumberOfBits()
    {
	int pos = Node.sizeOfHeader() + Node.sizeOfLong;
	return this.readInteger(pos);
    }

    /**
     *
     * @return
     */
    public long readRootPageId()
    {
	int pos = Node.sizeOfHeader();
	return this.readLong(pos);
    }

    /**
     *
     * @param height
     */
    protected void writeHashHeight(int height)
    {
	int pos = Node.sizeOfHeader() + Node.sizeOfLong + Node.sizeOfInteger;
	this.writeInteger(pos, height);
    }

    /**
     *
     * @param bits
     */
    protected void writeNumberOfBits(int bits)
    {
	int pos = Node.sizeOfHeader() + Node.sizeOfLong;
	this.writeInteger(pos, bits);
    }

    /**
     *
     * @param pageId
     */
    public void writeRootPageId(long pageId)
    {
	int pos = Node.sizeOfHeader();
	this.writeLong(pos, pageId);
    }
}
