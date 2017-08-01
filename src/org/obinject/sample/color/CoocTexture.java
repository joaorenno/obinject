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
public class CoocTexture {
    @Unique
    private int id;
    @Point
    @Origin
    private double textures[] = new double[16];
    @Extension
    private double extension[] = new double[16];

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
     * @return the textures
     */
    public double[] getTextures() {
        return textures;
    }

    /**
     * @param textures the textures to set
     */
    public void setTextures(double[] textures) {
        this.textures = textures;
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
