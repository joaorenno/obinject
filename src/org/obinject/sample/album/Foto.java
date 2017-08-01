/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.album;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.obinject.annotation.Coordinate;
import org.obinject.annotation.ExtractionMethod;
import org.obinject.annotation.Feature;
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
public class Foto {
    
    @Unique(number = Number.One, order = Order.First)
    private int codigo;
    
    @Feature(number=Number.One, order=Order.First, method = ExtractionMethod.Histogram)
    @Feature(number=Number.One, order=Order.Second, method = ExtractionMethod.HaralickAssymmetric)
    private BufferedImage imagem;
    
    @Point(number = Number.One, order = Order.First)
    @Coordinate(number = Number.One, order = Order.First)
    @Origin(number = Number.One, order = Order.First)
    private double latitude;
    
    @Point(number = Number.One, order = Order.Second)
    @Coordinate(number = Number.One, order = Order.Second)
    @Origin(number = Number.One, order = Order.Second)
    private double longitude;
    
    private Date data = new Date();
    
    private Local local;
    private List<Pessoa> pessoas = new ArrayList<>();

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public BufferedImage getImagem() {
        return imagem;
    }

    public void setImagem(BufferedImage imagem) {
        this.imagem = imagem;
    }

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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
    
}