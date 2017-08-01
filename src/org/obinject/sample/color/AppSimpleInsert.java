/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.color;

import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author windows
 */
public class AppSimpleInsert {

    public static void main(String[] args) {
        PersistentManager pm = PersistentManagerFactory.createPersistentManager();
        pm.getTransaction().begin();
        ColorHistogram c1 = new ColorHistogram();
        pm.inject(c1);
        ColorMoments c2 = new ColorMoments();
        pm.inject(c2);
        CoocTexture c3 = new CoocTexture();
        pm.inject(c3);
        pm.getTransaction().commit();
    }
}
