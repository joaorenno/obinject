/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.inception;

import org.obinject.annotation.Persistent;

/**
 *
 * @author windows
 */
@Persistent

public class Arquiteto extends Sonhador{
    
    private String Estilo;

    public String getEstilo() {
        return Estilo;
    }

    public void setEstilo(String Estilo) {
        this.Estilo = Estilo;
    }
    
    
    
}
