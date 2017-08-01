/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.exame;

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

        Exame p = new Exame();
        try {
            p.setNumero("123abc");
            p.setPaciente("ze");
            p.setMedico("Dr.Tião");
            p.setClinica("Clinica Modelo");
            p.setImagem(ImageIO.read(new File("resources/Tucano.png")));
            p.setLatitudeImagem(-22.43);
            p.setLongitudeImagem(45.45);
        } catch (IOException ex) {
            Logger.getLogger(AppSimpleInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pm.inject(p);
        pm.getTransaction().commit();
    }
}
