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

public class Totem {
    
    @Unique
    private int codigo;
    private String objeto;
    private String acao;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }


}
