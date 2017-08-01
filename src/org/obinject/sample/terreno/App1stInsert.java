/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.terreno;

import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author system
 */
public class App1stInsert {

    public static void main(String[] args) {

        PersistentManager pm = PersistentManagerFactory.createPersistentManager();
        pm.getTransaction().begin();

        Mapa m = new Mapa();
        m.setNome("Itajuba");
        m.setUtilidade("localização");
        pm.inject(m);

        Terreno t1 = new Terreno();
        t1.setRegistro(1);
        t1.setBairro("Centro");
        t1.setCoordenadaExtensao(new float[]{2, 2});
        t1.setMapa(m);
        pm.inject(t1);

        Terreno t2 = new Terreno();
        t2.setRegistro(2);
        t2.setBairro("BPS");
        t2.setCoordenadaExtensao(new float[]{123, 456});
        t2.setMapa(m);
        pm.inject(t2);

        Terreno t3 = new Terreno();
        t3.setRegistro(3);
        t3.setBairro("Pinheirinho");
        t3.setCoordenadaExtensao(new float[]{987, 654});
        t3.setMapa(m);
        pm.inject(t3);

        m.getTerrenos().add(t1);
        m.getTerrenos().add(t2);
        m.getTerrenos().add(t3);
        pm.inject(m);

        pm.getTransaction().commit();
        pm.close();

    }
}
