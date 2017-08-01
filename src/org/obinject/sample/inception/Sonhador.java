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

public class Sonhador {
    
    @Unique
    private int codigo;
    private boolean vivo;
    private Pessoa pessoa;
    private Missao missao;
    private Sonho sonho;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Missao getMissao() {
        return missao;
    }

    public void setMissao(Missao missao) {
        this.missao = missao;
    }

    public Sonho getSonho() {
        return sonho;
    }

    public void setSonho(Sonho sonho) {
        this.sonho = sonho;
    }

   
}
