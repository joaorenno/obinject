/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.color;

import org.obinject.annotation.Extension;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Point;
import org.obinject.annotation.Origin;
import org.obinject.annotation.Unique;

@Persistent(blockSize = 2048)
public class ColorHistogram {
    @Unique
    private int id;
    @Point
    @Origin
    private double colors[] = new double[32];
    @Extension
    private double extension[] = new double[32];

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the colors
     */
    public double[] getColors() {
        return colors;
    }

    /**
     * @param colors the colors to set
     */
    public void setColors(double[] colors) {
        this.colors = colors;
    }

    public double[] getExtension() {
        return extension;
    }

    public void setExtension(double[] extension) {
        this.extension = extension;
    }

}
