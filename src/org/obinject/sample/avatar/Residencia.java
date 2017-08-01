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
public class Residencia extends Container{
    private char sigla;
    private int banheiro;
    private int cama;

    public int getBanheiro() {
        return banheiro;
    }

    public void setBanheiro(int banheiro) {
        this.banheiro = banheiro;
    }

    public int getCama() {
        return cama;
    }

    public void setCama(int cama) {
        this.cama = cama;
    }

    public char getSigla() {
        return sigla;
    }

    public void setSigla(char sigla) {
        this.sigla = sigla;
    }
}
