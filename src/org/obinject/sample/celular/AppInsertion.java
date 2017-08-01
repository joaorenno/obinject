/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.celular;

import java.util.Random;
import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author enzo
 */
public class AppInsertion {

    public static void main(String[] args) {
        
        PersistentManager em = PersistentManagerFactory.createPersistentManager();
        em.getTransaction().begin();
        Random r = new Random();
        
        for (int i = 0; i < 100; i++) {
            Celular c = new Celular();
            c.setNumero(i);
            c.setDdd((short)r.nextInt());
            c.setLatitude(r.nextFloat());
            c.setLongitude(r.nextFloat());
            byte b[] = new byte[r.nextInt(10)+10];
            r.nextBytes(b);
            c.setDono(new String(b));
            em.inject(c);            
        }
        em.getTransaction().commit();
        
        
    }
    
}
