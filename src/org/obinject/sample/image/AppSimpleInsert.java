/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.image;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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

        Exam e = new Exam();
        e.setId(1);
        e.setName("ze");
        try {
            e.setImage(ImageIO.read(new File("resources/tucano.png")));
        } catch (IOException ex) {
            Logger.getLogger(AppSimpleInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
        pm.inject(e);

        pm.getTransaction().commit();
    }
}
