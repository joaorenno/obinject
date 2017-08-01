/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sbbd2013.uniprot;

import org.obinject.meta.generator.DistanceUtil;
import org.obinject.meta.Edition;
import org.obinject.block.PullPage;
import org.obinject.block.PushPage;
import org.obinject.block.Page;
import org.obinject.storage.AbstractKeyStructure;

/**
 *
 * @author luiz
 */
public class StringMetricProtein extends EntityProtein implements Edition<StringMetricProtein>, Comparable<StringMetricProtein>
{

    private static long calcDist = 0;

    public StringMetricProtein()
    {
    }

    public StringMetricProtein(EntityProtein protein)
    {
        byte[] array = new byte[protein.sizeOfEntity()];
        protein.pushEntity(array, 0);
        this.pullEntity(array, 0);
    }

    @Override
    public String getString()
    {
        return this.getAminoAcids();
    }
    
    @Override
    public double distanceTo(StringMetricProtein metric)
    {
        calcDist++;
        return DistanceUtil.protein(this.getString(), metric.getString());
    }

    @Override
    public boolean pullKey(byte[] array, int position)
    {
        PullPage pull = new PullPage(array, position);
        this.setAminoAcids(pull.pullString());
        return true;
    }

    @Override
    public void pushKey(byte[] array, int position)
    {
        PushPage push = new PushPage(array, position);
        push.pushString(this.getAminoAcids());
    }

    @Override
    public int sizeOfKey()
    {
        return Page.sizeOfString(this.getAminoAcids());
    }

    public static long getCalcDist()
    {
        return calcDist;
    }

    public static void resetCalcDist()
    {
        calcDist = 0;
    }

    @Override
    public AbstractKeyStructure<StringMetricProtein> getKeyStructure() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getPreservedDistance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPreservedDistance(double distance) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(StringMetricProtein o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasSameKey(StringMetricProtein key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
