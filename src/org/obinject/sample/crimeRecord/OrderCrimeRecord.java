/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.obinject.sample.crimeRecord;

import org.obinject.block.Page;
import org.obinject.block.PullPage;
import org.obinject.block.PushPage;
import org.obinject.meta.Uuid;
import org.obinject.storage.KeyStructure;
import org.obinject.meta.Sort;

/**
 *
 * @author Mariana
 */
public class OrderCrimeRecord extends EntityCrimeRecord implements Sort<OrderCrimeRecord>, Comparable<OrderCrimeRecord> {

    /**
     *
     */
    public static final Uuid classId = Uuid.fromString("30705B04-20FD-5DB4-1A8B-7F78502959D8");
    
    public OrderCrimeRecord() {
    }


    public OrderCrimeRecord(EntityCrimeRecord entity) {
        super(entity);
    }
    
    @Override
    public Uuid getClassID() {
        return OrderCrimeRecord.classId;
    }

    @Override
    public boolean hasSameKey(OrderCrimeRecord key) {
        return (((this.getCaseNumber() == null) && (key.getCaseNumber() == null)) || 
                ((this.getCaseNumber() != null) && (key.getCaseNumber() != null) && 
                (this.getCaseNumber().equals(key.getCaseNumber()))));
    }

    @Override
    public boolean pullKey(byte[] array, int position) {
        PullPage pull = new PullPage(array, position);
        this.setCaseNumber(pull.pullString());
        return true;
    }

    @Override
    public void pushKey(byte[] array, int position) {
        PushPage push = new PushPage(array, position);
        push.pushString(this.getCaseNumber());
    }

    @Override
    public int sizeOfKey() {
        return Page.sizeOfString(this.getCaseNumber());
    }

    @Override
    public KeyStructure<OrderCrimeRecord> getKeyStructure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(OrderCrimeRecord o) {
        return this.getCaseNumber().compareToIgnoreCase(o.getCaseNumber());
    }
    
};