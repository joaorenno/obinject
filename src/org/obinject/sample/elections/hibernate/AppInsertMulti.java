package org.obinject.sample.elections.hibernate;

/*
package br.ufpr.inf.sbbd2014.hibernate.elections;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class AppInsertMulti {

    private static final int numberExperiment = 1;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        long start = System.nanoTime();

        HashMap<Integer, Senator> listSenator = new HashMap<>();
        HashMap<Integer, Representative> listRepresentative = new HashMap<>();
        HashMap<Integer, Party> listParty = new HashMap<>();

        EntityManager pm = Persistence.createEntityManagerFactory("electionsPU").createEntityManager();

        Election election = new Election();
        election.setYearElection(2012);
        election.setMonitored(true);
        pm.getTransaction().begin();
        pm.persist(election);
        pm.getTransaction().commit();
        pm.clear();

        int nParty = 0;
        int nSenator = 0;
        int nRepresentative = 0;
        BufferedReader partidoFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/party" + numberExperiment + ".txt")));
        while (partidoFile.ready()) {
            StringTokenizer tok = new StringTokenizer(partidoFile.readLine(), "\t");
            Party p = new Party();
            p.setName(tok.nextToken());
            p.setOffice(tok.nextToken());
            pm.getTransaction().begin();
            pm.persist(p);
            pm.getTransaction().commit();
            pm.clear();

            listParty.put(nParty, p);
            nParty++;
        }
        try (BufferedReader senadorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/senator" + numberExperiment + ".txt")))) {
            while (senadorFile.ready()) {
                StringTokenizer tok = new StringTokenizer(senadorFile.readLine(), "\t");
                //senador
                Senator s = new Senator();
                s.setIdentityCard(Integer.parseInt(tok.nextToken()));
                s.setName(tok.nextToken());
                s.setAdress(tok.nextToken());
                s.setCandidateName(tok.nextToken());
                s.setCandidateState(tok.nextToken());
                s.setMandate(Integer.parseInt(tok.nextToken()));
                pm.getTransaction().begin();
                pm.persist(s);
                pm.getTransaction().commit();
                pm.clear();

                listSenator.put(nSenator, s);
                nSenator++;

                //Campaign
                Campaign cgn = new Campaign();
                cgn.setRegister(Integer.parseInt(tok.nextToken()));
                cgn.setSpending(Double.parseDouble(tok.nextToken()));
                cgn.setPatrimony(Double.parseDouble(tok.nextToken()));
                cgn.setElection(election);
                cgn.setCandidate(s);
                int posParty = Integer.parseInt(tok.nextToken());
                int totalCampaignerByCandidate = Integer.parseInt(tok.nextToken());
                cgn.setParty(listParty.get(posParty));
                //Campaigner
                for (int i = 0; i < totalCampaignerByCandidate; i++) {
                    Campaigner cgner = new Campaigner();
                    cgner.setIdentityCard(Integer.parseInt(tok.nextToken()));
                    cgner.setName(tok.nextToken());
                    cgner.setAdress(tok.nextToken());
                    cgner.setAssignment(tok.nextToken());
                    cgner.setIncome(Float.parseFloat(tok.nextToken()));
                    pm.getTransaction().begin();
                    pm.persist(cgner);
                    pm.getTransaction().commit();
                    pm.clear();
                    //adding
                    cgn.getCampaigners().add(cgner);
                }
                pm.getTransaction().begin();
                pm.persist(cgn);
                pm.getTransaction().commit();
                pm.clear();
            }
        }
        try (BufferedReader representativeFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/representative" + numberExperiment + ".txt")))) {
            while (representativeFile.ready()) {
                StringTokenizer tok = new StringTokenizer(representativeFile.readLine(), "\t");
                //senador
                Representative r = new Representative();
                r.setIdentityCard(Integer.parseInt(tok.nextToken()));
                r.setName(tok.nextToken());
                r.setAdress(tok.nextToken());
                r.setCandidateName(tok.nextToken());
                r.setCandidateState(tok.nextToken());
                r.setPrefixName(tok.nextToken());
                r.setIsSpeaker(Boolean.parseBoolean(tok.nextToken()));
                pm.getTransaction().begin();
                pm.persist(r);
                pm.getTransaction().commit();
                pm.clear();

                listRepresentative.put(nRepresentative, r);
                nRepresentative++;

                //Campaign
                Campaign cgn = new Campaign();
                cgn.setRegister(Integer.parseInt(tok.nextToken()));
                cgn.setSpending(Double.parseDouble(tok.nextToken()));
                cgn.setPatrimony(Double.parseDouble(tok.nextToken()));
                cgn.setElection(election);
                cgn.setCandidate(r);
                int posParty = Integer.parseInt(tok.nextToken());
                int totalCampaignerByCandidate = Integer.parseInt(tok.nextToken());
                cgn.setParty(listParty.get(posParty));
                //Campaigner
                for (int i = 0; i < totalCampaignerByCandidate; i++) {
                    Campaigner cgner = new Campaigner();
                    cgner.setIdentityCard(Integer.parseInt(tok.nextToken()));
                    cgner.setName(tok.nextToken());
                    cgner.setAdress(tok.nextToken());
                    cgner.setAssignment(tok.nextToken());
                    cgner.setIncome(Float.parseFloat(tok.nextToken()));
                    pm.getTransaction().begin();
                    pm.persist(cgner);
                    pm.getTransaction().commit();
                    pm.clear();
                    //adding
                    cgn.getCampaigners().add(cgner);
                }
                pm.getTransaction().begin();
                pm.persist(cgn);
                pm.getTransaction().commit();
                pm.clear();
            }
        }
        try (BufferedReader electorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/elector" + numberExperiment + ".txt")))) {
            while (electorFile.ready()) {
                StringTokenizer tok = new StringTokenizer(electorFile.readLine(), "\t");
                //elector
                Elector e = new Elector();
                e.setIdentityCard(Integer.parseInt(tok.nextToken()));
                e.setName(tok.nextToken());
                e.setAdress(tok.nextToken());
                e.setRace(tok.nextToken());
                e.setAge(Integer.parseInt(tok.nextToken()));
                pm.getTransaction().begin();
                pm.persist(e);
                pm.getTransaction().commit();
                pm.clear();

                //vote
                Vote v = new Vote();
                v.setRegister(Integer.parseInt(tok.nextToken()));
                v.setInstante(new Date(Long.parseLong(tok.nextToken())));
                int posSenator = Integer.parseInt(tok.nextToken());
                int posRepresentative = Integer.parseInt(tok.nextToken());
                v.setSenator(listSenator.get(posSenator));
                v.setRepresentative(listRepresentative.get(posRepresentative));
                v.setElector(e);
                v.setElection(election);
                pm.getTransaction().begin();
                pm.persist(v);
                pm.getTransaction().commit();
                pm.clear();
            }
        }
        try (BufferedReader countSenatorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/countingSenator" + numberExperiment + ".txt")))) {
            while (countSenatorFile.ready()) {
                StringTokenizer tok = new StringTokenizer(countSenatorFile.readLine(), "\t");
                //counting senator
                Counting count = new Counting();
                count.setRegister(Integer.parseInt(tok.nextToken()));
                count.setCandidate(listSenator.get(Integer.parseInt(tok.nextToken())));
                count.setTotal(Integer.parseInt(tok.nextToken()));
                count.setElection(election);
                pm.getTransaction().begin();
                pm.persist(count);
                pm.getTransaction().commit();
                pm.clear();
            }
        }
        try (BufferedReader countRepresentativeFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/countingRepresentative" + numberExperiment + ".txt")))) {
            while (countRepresentativeFile.ready()) {
                StringTokenizer tok = new StringTokenizer(countRepresentativeFile.readLine(), "\t");
                //counting senator
                Counting count = new Counting();
                count.setRegister(Integer.parseInt(tok.nextToken()));
                count.setCandidate(listRepresentative.get(Integer.parseInt(tok.nextToken())));
                count.setTotal(Integer.parseInt(tok.nextToken()));
                count.setElection(election);
                pm.getTransaction().begin();
                pm.persist(count);
                pm.getTransaction().commit();
                pm.clear();
            }
        }

        long end = System.nanoTime();
        System.out.println("time=" + ((end - start) / 1000));
    }
}
 */
