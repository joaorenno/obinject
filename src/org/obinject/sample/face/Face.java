package org.obinject.sample.face;

import java.awt.image.BufferedImage;
import org.obinject.annotation.ExtractionMethod;
import org.obinject.annotation.Feature;
import org.obinject.annotation.Order;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;
import org.obinject.annotation.Number;

@Persistent
public class Face  {

    @Unique
    private String label;
    
    @Feature(number = Number.One, order = Order.First, method = ExtractionMethod.HistogramStatistical)
//    @Feature(number = Number.Two, order = Order.First, method = ExtractionMethod.HaralickSymmetric)
//    @Feature(number = Number.Three, order = Order.First,
//            method = ExtractionMethod.HaralickAssymmetric)
    private BufferedImage face;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BufferedImage getFace() {
        return face;
    }

    public void setFace(BufferedImage face) {
        this.face = face;
    }
    
    
        
}
