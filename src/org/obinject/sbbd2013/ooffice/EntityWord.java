/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sbbd2013.ooffice;

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
public class EntityWord extends Word implements Entity<EntityWord>
{

    public static final Uuid classId = Uuid.fromString("934E73C0-6BD9-46C0-8B7E-D25260DE693A");
    private Uuid uuid = Uuid.generator();

    public EntityWord()
    {
    }

    public EntityWord(Word word)
    {
        this.setWord(word.getWord());
    }

    @Override
    public boolean isEqual(EntityWord entity)
    {
        return this.getWord().equals(entity.getWord());
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
            this.setWord(pull.pullString());
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
        push.pushString(this.getWord());
    }

    @Override
    public int sizeOfEntity()
    {
        return 2 * Page.sizeOfUuid
                + Page.sizeOfString(this.getWord());
    }

    @Override
    public boolean inject() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AbstractEntityStructure<EntityWord> getEntityStructure() {
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
