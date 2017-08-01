/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.elections;

import org.obinject.annotation.Persistent;

@Persistent
public class Elector extends Person {

    private int age;
    private String race;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }
}
