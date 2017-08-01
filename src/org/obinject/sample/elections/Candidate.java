/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.elections;

import org.obinject.annotation.Persistent;

@Persistent
public class Candidate extends Person {

    private String candidateName;
    private String candidateState;

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getCandidateState() {
        return candidateState;
    }

    public void setCandidateState(String candidateState) {
        this.candidateState = candidateState;
    }
    
}
