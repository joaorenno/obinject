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

public class Armador extends Sonhador{
    
    private int numeroProblemas;

    public int getNumeroProblemas() {
        return numeroProblemas;
    }

    public void setNumeroProblemas(int numeroProblemas) {
        this.numeroProblemas = numeroProblemas;
    }
    
}
