/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.album;

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
public class AppPersistir {

    public static void main(String[] args) {

        PersistentManager pm = PersistentManagerFactory.createPersistentManager();
        pm.getTransaction().begin();

        Album a = new Album();
        Foto f = new Foto();
        Local l = new Local();
        Pessoa p = new Pessoa();
        
        try {
            a.setNumero(100);
            a.setNome("Aquario de Ubatuba");
            
            f.setCodigo(10);
            f.setImagem(ImageIO.read(new File("pinguim.png")));
            f.setLatitude(23.4453);
            f.setLongitude(-45.0692);
            
            l.setCodigo(1000);
            l.setNome("Ubatuba");
            l.setLatitudePadrao(23.4337);
            l.setLongitudePadrao(-45.0839);
            
            p.setEmail("ze@dominio.com");
            p.setNome("Jose");
            
        } catch (IOException ex) {
            Logger.getLogger(AppPersistir.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pm.inject(p);
        pm.getTransaction().commit();
    }
}
