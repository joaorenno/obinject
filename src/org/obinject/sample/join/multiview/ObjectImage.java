package org.obinject.sample.join.multiview;

import org.obinject.annotation.Origin;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Point;
import org.obinject.annotation.Unique;

/**
 *
 * @author windows
 */
@Persistent
public class ObjectImage {
    @Point
    @Origin
    private double feature[] = new double[13];
    private String pathObject;
    @Unique
    private String pathImage;

    public double[] getFeature() {
        return feature;
    }

    public void setFeature(double[] feature) {
        this.feature = feature;
    }

    public String getPathObject() {
        return pathObject;
    }

    public void setPathObject(String pathObject) {
        this.pathObject = pathObject;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }


}
