/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.elections;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author system
 */
public class AppGeneration {

    private static final Random random = new Random();

    private static String stringAlfabeticGeneration(int min, int max) {
        int size;
        if (min == max) {
            size = min;
        } else {
            size = random.nextInt(max - min + 1) + min;
        }
        char[] ch = new char[size];
        for (int i = 0; i < size; i++) {
            ch[i] = (char) (random.nextInt(26) + 65);
        }
        return new String(ch);
    }

    private static String stringNumberGeneration(int min, int max) {
        if (min == max) {
            return "" + min;
        } else {
            return "" + random.nextInt(max - min + 1) + min;
        }

    }

    private static Date dateGeneration(int minAno, int maxAno) {
        int sizeMes[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int mes = random.nextInt(12) + 1;
        int dia = random.nextInt(sizeMes[mes - 1]) + 1;
        int ano;
        if (minAno == maxAno) {
            ano = minAno;
        } else {
            ano = random.nextInt(maxAno - minAno) + minAno;
        }
        return new GregorianCalendar(ano, mes, dia).getTime();
    }

    private static Date timeGeneration(int dia, int mes, int ano) {
        return new GregorianCalendar(ano, mes, dia, random.nextInt(24), random.nextInt(60), random.nextInt(60)).getTime();
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {

        PersistentManager pm = PersistentManagerFactory.createPersistentManager();
        int idCard = 0;

        pm.getTransaction().begin();

        Election election = new Election();
        election.setYearElection(2012);
        election.setMonitored(true);
        pm.inject(election);

        Party p = new Party();
        p.setName(stringAlfabeticGeneration(20, 30));
        p.setOffice(stringAlfabeticGeneration(20, 30));
        pm.inject(p);
        
        Senator s = new Senator();
        s.setIdentityCard(idCard);
        s.setName(stringAlfabeticGeneration(20, 30));
        s.setAdress(stringAlfabeticGeneration(20, 30));
        s.setCandidateName(stringAlfabeticGeneration(20, 30));
        s.setCandidateState(stringAlfabeticGeneration(20, 30));
        s.setMandate(2008 + random.nextInt(6));                
        pm.inject(s);

        Campaigner cgner = new Campaigner();
        cgner.setIdentityCard(idCard+1);
        cgner.setName(stringAlfabeticGeneration(20, 30));
        cgner.setAdress(stringAlfabeticGeneration(20, 30));
        cgner.setAssignment(stringAlfabeticGeneration(10, 15));
        cgner.setIncome(1000 + random.nextInt(1000));
        pm.inject(cgner);

        Campaign cgn = new Campaign();
        cgn.setRegister(idCard);
        cgn.setSpending(1000000 + random.nextInt(1000000));
        cgn.setPatrimony(10000000 + random.nextInt(10000000));
        cgn.setElection(election);
        cgn.setCandidate(s);
        cgn.setParty(p);
        cgn.getCampaigners().add(cgner);
        pm.inject(cgn);

        Representative r = new Representative();
        r.setIdentityCard(idCard+2);
        r.setName(stringAlfabeticGeneration(20, 30));
        r.setAdress(stringAlfabeticGeneration(20, 30));
        r.setCandidateName(stringAlfabeticGeneration(20, 30));
        r.setCandidateState(stringAlfabeticGeneration(20, 30));
        r.setPrefixName(stringAlfabeticGeneration(3, 7));
        r.setIsSpeaker(random.nextBoolean());
        pm.inject(r);

        Elector e = new Elector();
        e.setIdentityCard(idCard+3);
        e.setName(stringAlfabeticGeneration(20, 30));
        e.setAdress(stringAlfabeticGeneration(20, 30));
        e.setRace(stringAlfabeticGeneration(5, 15));
        e.setAge(20 + random.nextInt(20));        
        pm.inject(e);

        Vote v = new Vote();
        v.setRegister(idCard+3);
        v.setInstante(timeGeneration(2012, 11, 6));
        v.setRepresentative(r);
        v.setSenator(s);
        v.setElector(e);
        v.setElection(election);
        pm.inject(v);

        Counting count1 = new Counting();
        count1.setRegister(idCard);
        count1.setCandidate(s);
        count1.setTotal(1);
        count1.setElection(election);
        pm.inject(count1);

        Counting count2 = new Counting();
        count2.setRegister(idCard+1);
        count2.setCandidate(r);
        count2.setTotal(1);
        count2.setElection(election);
        pm.inject(count2);
        
        pm.getTransaction().commit();

    }
}
