/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.universe;

import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author Ferro
 */
public class AppSimpleInsert
{
   public static void main(String[] args)
   {
      PersistentManager pm = PersistentManagerFactory.createPersistentManager();
      pm.getTransaction().begin();
   
      Planet p = new Planet();
      p.setName("terra");
      p.setLongitude(0);
      p.setLatitude(0);
   
      pm.inject(p);
      pm.getTransaction().commit();
      
   }
  
}
