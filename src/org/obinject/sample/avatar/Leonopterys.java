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
public class Leonopterys extends Ser {
    private String cores;
    private int asas;
    private int pos_cadeia_alim;

    public int getAsas() {
        return asas;
    }

    public void setAsas(int asas) {
        this.asas = asas;
    }

    public String getCores() {
        return cores;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public int getPos_cadeia_alim() {
        return pos_cadeia_alim;
    }

    public void setPos_cadeia_alim(int pos_cadeia_alim) {
        this.pos_cadeia_alim = pos_cadeia_alim;
    }
}
