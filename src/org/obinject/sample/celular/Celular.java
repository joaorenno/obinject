/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.celular;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Edition;
import org.obinject.annotation.Point;
import org.obinject.annotation.Sort;
import org.obinject.annotation.Unique;

/**
 *
 * @author enzo
 */
@Persistent
public class Celular {
    @Unique
    private long numero;
    private short ddd;
    @Point
    private double latitude;
    @Point
    private double longitude;
    @Edition
    @Sort
    private String dono;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public short getDdd() {
        return ddd;
    }

    public void setDdd(short ddd) {
        this.ddd = ddd;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }
    
    
    
}
