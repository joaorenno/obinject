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
public class Militar extends Humano {
    private String cargo;
    private String especialização;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEspecialização() {
        return especialização;
    }

    public void setEspecialização(String especialização) {
        this.especialização = especialização;
    }
}
