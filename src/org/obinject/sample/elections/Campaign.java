/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.elections;

import java.util.ArrayList;
import java.util.List;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author aluno
 */
@Persistent
public class Campaign{

    @Unique
    private int register;
    private double spending;
    private double patrimony;
    private Candidate candidate;
    private List<Campaigner> campaigners = new ArrayList<>();
    private Election election;
    private Party party;

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public double getSpending() {
        return spending;
    }

    public void setSpending(double spending) {
        this.spending = spending;
    }

    public double getPatrimony() {
        return patrimony;
    }

    public void setPatrimony(double patrimony) {
        this.patrimony = patrimony;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public List<Campaigner> getCampaigners() {
        return campaigners;
    }

    public void setCampaigners(List<Campaigner> campaigners) {
        this.campaigners = campaigners;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
    
}
