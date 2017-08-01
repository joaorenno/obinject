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

public class Sombra {
    
    @Unique
    private int codigo;
    private char sexo;
    private boolean treinamento;
    private Sonhador sonhador;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public boolean isTreinamento() {
        return treinamento;
    }

    public void setTreinamento(boolean treinamento) {
        this.treinamento = treinamento;
    }

    public Sonhador getSonhador() {
        return sonhador;
    }

    public void setSonhador(Sonhador sonhador) {
        this.sonhador = sonhador;
    }

    
    
}
