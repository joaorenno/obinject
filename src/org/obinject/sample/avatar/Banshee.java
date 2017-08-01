/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.obinject.sample.avatar;

import org.obinject.annotation.Persistent;

/**
 *
 * @author Dany
 */

@Persistent
public class Banshee extends Ser{
    private String cor;
    private boolean domado;
    private int altura_voo;

    public int getAltura_voo() {
        return altura_voo;
    }

    public void setAltura_voo(int altura_voo) {
        this.altura_voo = altura_voo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public boolean isDomado() {
        return domado;
    }

    public void setDomado(boolean domado) {
        this.domado = domado;
    }
}
