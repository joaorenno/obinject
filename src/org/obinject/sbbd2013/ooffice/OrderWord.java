/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sbbd2013.ooffice;

import org.obinject.block.PullPage;
import org.obinject.block.PushPage;
import org.obinject.block.Page;
import org.obinject.storage.AbstractKeyStructure;
import org.obinject.meta.Sort;

/**
 *
 * @author luiz
 */
public class OrderWord extends EntityWord implements Sort<OrderWord>, Comparable<OrderWord>
{

    private static long numComp = 0;

    public OrderWord()
    {
    }

    public OrderWord(EntityWord word)
    {
        byte[] array = new byte[word.sizeOfEntity()];
        word.pushEntity(array, 0);
        this.pullEntity(array, 0);
    }

    @Override
    public int compareTo(OrderWord order)
    {
        numComp++;
        return this.getWord().compareTo(order.getWord());
    }

    @Override
    public boolean pullKey(byte[] array, int position)
    {
        PullPage pull = new PullPage(array, position);
        this.setWord(pull.pullString());
        return true;
    }

    @Override
    public void pushKey(byte[] array, int position)
    {
        PushPage push = new PushPage(array, position);
        push.pushString(this.getWord());
    }

    @Override
    public int sizeOfKey()
    {
        return Page.sizeOfString(this.getWord());
    }

    public static long getNumComp()
    {
        return numComp;
    }

    public static void resetNumComp()
    {
        numComp = 0;
    }

    @Override
    public AbstractKeyStructure<OrderWord> getKeyStructure() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasSameKey(OrderWord key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
