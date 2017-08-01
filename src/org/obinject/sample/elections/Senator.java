/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.obinject.sample.elections;

import org.obinject.annotation.Persistent;

@Persistent
public class Senator extends Candidate{

    private int mandate;

    public int getMandate() {
        return mandate;
    }

    public void setMandate(int mandate) {
        this.mandate = mandate;
    }
}
