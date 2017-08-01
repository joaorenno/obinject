/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sbbd2013.uniprot;

import org.obinject.meta.Entity;
import org.obinject.block.PullPage;
import org.obinject.block.PushPage;
import org.obinject.block.Page;
import org.obinject.meta.Uuid;
import org.obinject.storage.AbstractEntityStructure;

/**
 *
 * @author luiz
 */
public class EntityProtein extends Protein implements Entity<EntityProtein>
{

    public static final Uuid classId = Uuid.fromString("5151145E-6155-4E28-88A0-A227207F2C02");
    private Uuid uuid = Uuid.generator();

    @Override
    public boolean isEqual(EntityProtein entity)
    {
        return this.getAminoAcids().equals(entity.getAminoAcids());
    }

    @Override
    public Uuid getUuid()
    {
        return uuid;
    }

    @Override
    public boolean pullEntity(byte[] array, int position)
    {
        PullPage pull = new PullPage(array, position);
        Uuid storedClass = pull.pullUuid();
        if (classId.equals(storedClass) == true)
        {
            uuid = pull.pullUuid();
            this.setAminoAcids(pull.pullString());
            return true;
        }
        return false;
    }

    @Override
    public void pushEntity(byte[] array, int position)
    {
        PushPage push = new PushPage(array, position);
        push.pushUuid(classId);
        push.pushUuid(uuid);
        push.pushString(this.getAminoAcids());
    }

    @Override
    public int sizeOfEntity()
    {
        return 2 * Page.sizeOfUuid
                + Page.sizeOfString(this.getAminoAcids());
    }

    @Override
    public boolean inject() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AbstractEntityStructure<EntityProtein> getEntityStructure() {
        throw new UnsupportedOperationException("Not supported yet.");
    }    

    @Override
    public boolean reject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean modify() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
