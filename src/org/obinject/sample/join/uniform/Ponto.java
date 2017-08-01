/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.join.uniform;

import org.obinject.annotation.Order;
import org.obinject.annotation.Number;
import org.obinject.annotation.Origin;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Point;
import org.obinject.annotation.Unique;

/**
 *
 * @author RenZo
 * 
 * Classe Modelo Antenna para o Espaço Euclidiano
 */
@Persistent
public class Ponto {    
    // Identificador da Antena
    @Unique
    private String identifier;
    // Latitude e Longitude da Antena
    @Point(number = Number.One, order = Order.First)
    @Origin(number = Number.One, order = Order.First)
    private int x;
    @Point(number = Number.One, order = Order.Second)
    @Origin(number = Number.One, order = Order.Second)
    private int y;

    // GETTERS AND SETTERS
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
