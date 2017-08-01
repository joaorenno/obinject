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
public class App3rdDelete {

    public static void main(String[] args) {

        PersistentManager pm = PersistentManagerFactory.createPersistentManager();

        Query q1 = new Query();
        q1.where(new Equal($Mapa.nome, "Itajuba"));
        Collection<Mapa> res1 = q1.execute();
        Mapa m1 = res1.iterator().next();

        // remoção do terreno 1 do relacionamento 
        Terreno tDel = null;
        for (Terreno t : m1.getTerrenos()) {
            System.out.print("[registro:" + t.getRegistro());
            System.out.print(", bairro:" + t.getBairro() + "]");
            if (t.getRegistro() == 1) {
                tDel = t;
            }
        }
        System.out.println();
        pm.getTransaction().begin();
        m1.getTerrenos().remove(tDel);
        pm.inject(m1);
        pm.getTransaction().commit();

        Query q2 = new Query();
        q2.where(new Equal($Mapa.nome, "Itajuba"));
        Collection<Mapa> res2 = q2.execute();
        Mapa m2 = res2.iterator().next();
        for (Terreno t : m2.getTerrenos()) {
            System.out.print("[registro:" + t.getRegistro());
            System.out.print(", bairro:" + t.getBairro() + "]");
        }
        System.out.println();

        // remoção do terreno 1
        pm.getTransaction().begin();
        pm.reject(tDel);
        pm.getTransaction().commit();

        Query q3 = new Query();
        q3.where(new Equal($Terreno.registro, 1L));
        Collection<Terreno> res3 = q3.execute();
        if (res3.isEmpty()) {
            System.out.println("terreno 1 não encontrado");
        }

        pm.close();

    }
}
