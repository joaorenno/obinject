/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.queries;

import java.util.Collection;
import java.util.LinkedList;
import org.obinject.block.BTreeEntityIndex;
import org.obinject.block.BTreeEntityLeaf;
import org.obinject.block.Node;
import org.obinject.device.Session;
import org.obinject.meta.Entity;
import org.obinject.storage.AbstractStructure;
import org.obinject.storage.BTreeEntity;

/**
 *
 * @author windows
 */
public class SequentialJoin<L extends Entity<L>, R extends Entity<R>> 
        extends AbstractStrategy<L> {

    private final AbstractStructure<L> structureLeft;
    private final AbstractStructure<R> structureRight;
    private final Attribute attributeLeft;
    private final Attribute attributeRight;

    public SequentialJoin(BTreeEntity<L> structureLeft, BTreeEntity<R> structureRight,
            Attribute attributeLeft, Attribute attributeRight) {
        super(structureLeft);
        this.structureLeft = structureLeft;
        this.structureRight = structureRight;
        this.attributeLeft = attributeLeft;
        this.attributeRight = attributeRight;
    }

    @Override
    public Collection solve() {
        LinkedList<Object []>  result = new LinkedList<Object []>();
        // Abrindo uma sessao com a workspace do usuario
        Session se = this.getStructure().getWorkspace().openSession();
//        Object res[] = new Object[2];
//        res[0] = new L();
//        res[1] = new R();
//        result.add(res);
        // id de uma página
        long pageIdLeft = structureLeft.getRootPageId();
        long pageIdRight = structureRight.getRootPageId();
        //marcadores
        long time = System.nanoTime();
        long diskAccess = se.getBlockAccess();

        Node nodeLeft = se.load(pageIdLeft);
        BTreeEntityIndex<L> indexLeft;
        while(BTreeEntityIndex.matchNodeType(nodeLeft)){
            indexLeft = new BTreeEntityIndex<>(nodeLeft, this.getStructure().getObjectClass());
            pageIdLeft = indexLeft.readSubPageId(0);
            nodeLeft = se.load(pageIdLeft);
        }
        BTreeEntityLeaf<L> leafLeft = new BTreeEntityLeaf<>(nodeLeft, this.getStructure().getObjectClass());


        
        

        return result;
    }

}
