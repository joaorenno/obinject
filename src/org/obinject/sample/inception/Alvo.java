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

public class Alvo extends Sonhador{

    private String informacao;
    private String ideia;

    public String getIdeia() {
        return ideia;
    }

    public void setIdeia(String ideia) {
        this.ideia = ideia;
    }

    public String getInformacao() {
        return informacao;
    }

    public void setInformacao(String informacao) {
        this.informacao = informacao;
    }
    
}
