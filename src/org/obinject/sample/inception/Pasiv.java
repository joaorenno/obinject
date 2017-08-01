/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.inception;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author windows
 */
@Persistent

public class Pasiv {
    
    @Unique
    private int serial;
    private float quantidadeDroga;

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public float getQuantidadeDroga() {
        return quantidadeDroga;
    }

    public void setQuantidadeDroga(float quantidadeDroga) {
        this.quantidadeDroga = quantidadeDroga;
    }

    
}
