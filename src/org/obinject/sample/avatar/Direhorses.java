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
public class Direhorses extends Ser {
     private String aparencia;
    private int tam_antenas;
    private int tam_patas;

    public String getAparencia() {
        return aparencia;
    }

    public void setAparencia(String aparencia) {
        this.aparencia = aparencia;
    }

    public int getTam_antenas() {
        return tam_antenas;
    }

    public void setTam_antenas(int tam_antenas) {
        this.tam_antenas = tam_antenas;
    }

    public int getTam_patas() {
        return tam_patas;
    }

    public void setTam_patas(int tam_patas) {
        this.tam_patas = tam_patas;
    }
}
