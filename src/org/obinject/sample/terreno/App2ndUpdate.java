/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.terreno;

import java.util.Collection;
import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;
import org.obinject.queries.Equal;
import org.obinject.queries.Query;

/**
 *
 * @author system
 */
public class App2ndUpdate {

    public static void main(String[] args) {

        PersistentManager pm = PersistentManagerFactory.createPersistentManager();

        Query q1 = new Query();
        q1.where(new Equal($Terreno.registro, 1L));
        Collection<Terreno> res1 = q1.execute();
        Terreno t1 = res1.iterator().next();
        System.out.println("uuid:" + (($Terreno) t1).getUuid());
        System.out.println("registro: " + t1.getRegistro());
        System.out.println("bairro: " + t1.getBairro());

        pm.getTransaction().begin();
        t1.setBairro("Boa Vista");
        pm.inject(t1);
        pm.getTransaction().commit();
        
        Query q2 = new Query();
        q2.where(new Equal($Terreno.registro, 1L));
        Collection<Terreno> res2 = q2.execute();
        Terreno t2 = res2.iterator().next();
        System.out.println("uuid:" + (($Terreno) t2).getUuid());
        System.out.println("registro: " + t2.getRegistro());
        System.out.println("bairro: " + t2.getBairro());

        pm.close();

    }
}
