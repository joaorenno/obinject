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
public class Counting {

    @Unique
    private int register;
    private int total;
    private Candidate candidate;
    private Election election;

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }
}
