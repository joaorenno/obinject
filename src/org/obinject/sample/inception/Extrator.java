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

public class Extrator extends Sonhador{
    
    private String informacao;
    private boolean implantouIdeia;

    public boolean isImplantouIdeia() {
        return implantouIdeia;
    }

    public void setImplantouIdeia(boolean implantouIdeia) {
        this.implantouIdeia = implantouIdeia;
    }

    public String getInformacao() {
        return informacao;
    }

    public void setInformacao(String informacao) {
        this.informacao = informacao;
    }
    
}
