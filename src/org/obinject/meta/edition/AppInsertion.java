/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.meta.edition;

import java.util.Random;
import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author luiz
 */
public class AppInsertion
{

    public static void main(String[] args)
    {
	Random r = new Random();
	PersistentManager pm = PersistentManagerFactory.createPersistentManager();
	pm.getTransaction().begin();
	for (int i = 0; i < 100; i++)
	{
	    Palavra c = new Palavra();
	    byte buff[] = new byte[r.nextInt(20)];
	    r.nextBytes(buff);
	    c.setPalavra(new String(buff));
	    pm.inject(c);
	}
	pm.getTransaction().commit();
    }
}
