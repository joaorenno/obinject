/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.student;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.PersistentManager;

/**
 *
 * @author windows
 */
public class AppInsertion {

    public static void main(String[] args) {
        Student s = new Student();
        PersistentManager pm = new PersistentManager();
        try {
            Scanner scan = new Scanner(new File("student.txt"));
            scan.useLocale(Locale.US);
            while (scan.hasNext()) {
                s.setRegistration(scan.nextInt());
                s.setName(scan.next());
                s.setAddress(scan.next());
                s.setCourse(scan.next());
                s.setCoefficient(scan.nextFloat());
                s.setAge(scan.nextInt());
                pm.getTransaction().begin();
                pm.inject(s);
                pm.getTransaction().commit();                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppInsertion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
