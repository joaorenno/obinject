/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.album;

import java.util.ArrayList;
import org.obinject.annotation.Edition;
import org.obinject.annotation.Order;
import org.obinject.annotation.Number;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Sort;
import org.obinject.annotation.Unique;

/**
 *
 * @author Helaine
 */

@Persistent
public class Pessoa {
    @Unique(number = Number.One, order = Order.First)
    @Sort(number = Number.One, order = Order.First)
    private String email;
    
    @Edition(number = Number.Two, order = Order.First)
    private String nome;
    
    private ArrayList<Album> albuns = new ArrayList<>();
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Album> getAlbuns() {
        return albuns;
    }

    public void setAlbuns(ArrayList<Album> albuns) {
        this.albuns = albuns;
    }
}
