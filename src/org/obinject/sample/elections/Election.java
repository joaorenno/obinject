/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.elections;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author aluno
 */
@Persistent
public class Election {

    @Unique
    private int yearElection;
    private boolean monitored;

    public int getYearElection() {
        return yearElection;
    }

    public void setYearElection(int yearElection) {
        this.yearElection = yearElection;
    }

    public boolean isMonitored() {
        return monitored;
    }

    public void setMonitored(boolean monitored) {
        this.monitored = monitored;
    }

}
