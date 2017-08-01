/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.image;

import java.awt.image.BufferedImage;
import org.obinject.annotation.ExtractionMethod;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Edition;
import org.obinject.annotation.Feature;
import org.obinject.annotation.Order;
import org.obinject.annotation.Number;
import org.obinject.annotation.Unique;

@Persistent
public class Exam {

    @Unique
    private int id;
    @Edition
    private String name;
    @Feature(number = Number.One,
            order = Order.First, method = ExtractionMethod.Histogram)
    @Feature(number = Number.Two,
            order = Order.First, method = ExtractionMethod.HistogramStatistical)
    private BufferedImage image;

    //gets and sets
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
