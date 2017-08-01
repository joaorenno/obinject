package org.obinject.sample.protein;

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
        
        Proteina p = new Proteina();
        p.setAminoAcids("ARNDCQEGHILKMFPSTWYV");
        pm.inject(p);
        
        pm.getTransaction().commit();
        pm.close();        
    }
}
