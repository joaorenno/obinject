/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.obinject.sample.elections;

import org.obinject.annotation.Persistent;

@Persistent
public class Representative extends Candidate{

    private String prefixName;
    private boolean isSpeaker;

    public String getPrefixName() {
        return prefixName;
    }

    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
    }

    public boolean isIsSpeaker() {
        return isSpeaker;
    }

    public void setIsSpeaker(boolean isSpeaker) {
        this.isSpeaker = isSpeaker;
    }
}
