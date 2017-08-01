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

public class Falsificador extends Sonhador{
    
    private String transformaPessoa;

    public String getTransformaPessoa() {
        return transformaPessoa;
    }

    public void setTransformaPessoa(String transformaPessoa) {
        this.transformaPessoa = transformaPessoa;
    }
    
}
