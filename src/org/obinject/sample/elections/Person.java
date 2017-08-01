/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.obinject.sample.elections;


import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

@Persistent
public class Person {

    @Unique
    private int identityCard;
    private String name;
    private String adress;

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public int getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(int identityCard) {
        this.identityCard = identityCard;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
