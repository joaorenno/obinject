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

public class Quimico extends Sonhador{
    
    private String formulaDrogra;

    public String getFormulaDrogra() {
        return formulaDrogra;
    }

    public void setFormulaDrogra(String formulaDrogra) {
        this.formulaDrogra = formulaDrogra;
    }
    
}
