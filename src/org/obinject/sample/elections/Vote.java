/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.obinject.sample.elections;


import java.util.Date;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author aluno
 */
@Persistent
public class Vote  {

    @Unique
    private int register;
    private Date instante;
    private Elector elector;
    private Election election;
    private Representative representative;
    private Senator senator;

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public Date getInstante() {
        return instante;
    }

    public void setInstante(Date instante) {
        this.instante = instante;
    }

    public Elector getElector() {
        return elector;
    }

    public void setElector(Elector elector) {
        this.elector = elector;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public Representative getRepresentative() {
        return representative;
    }

    public void setRepresentative(Representative representative) {
        this.representative = representative;
    }

    public Senator getSenator() {
        return senator;
    }

    public void setSenator(Senator senator) {
        this.senator = senator;
    }   
}
