/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.pessoa;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Point;
import org.obinject.annotation.Origin;
import org.obinject.annotation.Sort;
import org.obinject.annotation.Unique;

/**
 *
 * @author windows
 */
@Persistent
public class Pessoa {
    @Unique
    private String cpf;
    @Sort
    private String nome;
    @Origin
    private float latitude;
    @Origin
    private float longitude;
    @Point
    private float caracteristica[] = new float[15];

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float[] getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(float[] caracteristica) {
        this.caracteristica = caracteristica;
    }
    
    
    
}
