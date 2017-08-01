package org.obinject.sample.city;

import org.obinject.annotation.Number;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Order;
import org.obinject.annotation.Point;
import org.obinject.annotation.Edition;
import org.obinject.annotation.Origin;
import org.obinject.annotation.Unique;

@Persistent
public class City {

    @Unique(number = Number.One, order = Order.First)
    @Edition(number = Number.One, order = Order.First)
    private String name = "";
    @Point(number = Number.One, order = Order.First)
    @Origin(number = Number.One, order = Order.First)
    private double latitude;
    @Point(number = Number.One, order = Order.First)
    @Origin(number = Number.One, order = Order.First)
    private double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
