/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.album;

import org.obinject.annotation.Coordinate;
import org.obinject.annotation.Edition;
import org.obinject.annotation.Order;
import org.obinject.annotation.Number;
import org.obinject.annotation.Origin;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Point;
import org.obinject.annotation.Unique;

/**
 *
 * @author Helaine
 */

@Persistent
public class Local {
    @Unique(number = Number.One, order = Order.First)
    private int codigo;
    
    @Edition(number = Number.One, order = Order.First)
    private String nome;
    
    private String estado;
    private String pais;

    @Point(number = Number.One, order = Order.Third)
    @Coordinate(number = Number.One, order = Order.Third)
    @Origin(number = Number.One, order = Order.Third)
    private double latitudePadrao;

    @Point(number = Number.One, order = Order.Fourth)
    @Coordinate(number = Number.One, order = Order.Fourth)
    @Origin(number = Number.One, order = Order.Fourth)
    private double longitudePadrao;
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public double getLatitudePadrao() {
        return latitudePadrao;
    }

    public void setLatitudePadrao(double latitudePadrao) {
        this.latitudePadrao = latitudePadrao;
    }

    public double getLongitudePadrao() {
        return longitudePadrao;
    }

    public void setLongitudePadrao(double longitudePadrao) {
        this.longitudePadrao = longitudePadrao;
    }
}
