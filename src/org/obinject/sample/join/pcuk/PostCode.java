/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.join.pcuk;

import org.obinject.annotation.Order;
import org.obinject.annotation.Number;
import org.obinject.annotation.Origin;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Point;
import org.obinject.annotation.Unique;

/**
 *
 * @author windows
 */
@Persistent
public class PostCode {
    @Unique
    private String code;
    @Point(number = Number.One, order = Order.First)
    @Origin(number = Number.One, order = Order.First)
    private double latitude;
    @Point(number = Number.One, order = Order.Second)
    @Origin(number = Number.One, order = Order.Second)
    private double longitude;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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


    
    
}
