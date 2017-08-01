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
public class ColorMoments {
    @Unique
    private int id;
    @Point
    @Origin
    private double moments[] = new double[9];
    @Extension
    private double extension[] = new double[9];

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
     * @return the moments
     */
    public double[] getMoments() {
        return moments;
    }

    /**
     * @param moments the moments to set
     */
    public void setMoments(double[] moments) {
        this.moments = moments;
    }

    /**
     * @return the extension
     */
    public double[] getExtension() {
        return extension;
    }

    /**
     * @param extension the extension to set
     */
    public void setExtension(double[] extension) {
        this.extension = extension;
    }
    
    
}
