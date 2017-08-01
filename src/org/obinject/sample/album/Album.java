/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.album;

import java.util.ArrayList;
import org.obinject.annotation.Edition;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author Helaine
 */

@Persistent
public class Album {
    @Unique
    private int numero;
    @Edition
    private String nome;
    private boolean compartilhar;
    private ArrayList<Foto> fotos = new ArrayList<>();

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isCompartilhar() {
        return compartilhar;
    }

    public void setCompartilhar(boolean compartilhar) {
        this.compartilhar = compartilhar;
    }

    public ArrayList<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<Foto> fotos) {
        this.fotos = fotos;
    }
}
    
