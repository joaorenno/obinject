/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.student;

import org.obinject.PersistentManager;

/**
 *
 * @author windows
 */
public class AppSimpleInsert {

    public static void main(String[] args) {
        Student s = new Student();
        PersistentManager pm = new PersistentManager();
        s.setRegistration(1);
        s.setName("ze");
        s.setAddress("R. sem nome");
        s.setCourse("ECO");
        s.setCoefficient(7);
        s.setAge(18);
        pm.getTransaction().begin();
        pm.inject(s);
        pm.getTransaction().commit();
    }
}
