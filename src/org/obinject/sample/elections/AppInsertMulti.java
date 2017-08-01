/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.elections;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author system
 */
public class AppInsertMulti {

    private static final int numberExperiment = 1;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        long start = System.nanoTime();

        HashMap<Integer, Senator> listSenator = new HashMap<>();
        HashMap<Integer, Representative> listRepresentative = new HashMap<>();
        HashMap<Integer, Party> listParty = new HashMap<>();

        PersistentManager pm = PersistentManagerFactory.createPersistentManager();

        Election election = new Election();
        election.setYearElection(2012);
        election.setMonitored(true);
        pm.getTransaction().begin();
        pm.inject(election);
        pm.getTransaction().commit();

        int nParty = 0;
        int nSenator = 0;
        int nRepresentative = 0;
        BufferedReader partidoFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/party" + numberExperiment + ".txt")));
        while (partidoFile.ready()) {
            StringTokenizer tok = new StringTokenizer(partidoFile.readLine(), "\t");
            Party p = new Party();
            p.setName(tok.nextToken());
            p.setOffice(tok.nextToken());
            pm.getTransaction().begin();
            pm.inject(p);
            pm.getTransaction().commit();

            listParty.put(nParty, p);
            nParty++;
        }
        try (BufferedReader senadorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/senator" + numberExperiment + ".txt")))) {
            while (senadorFile.ready()) {
                StringTokenizer tok = new StringTokenizer(senadorFile.readLine(), "\t");
                //senator
                Senator s = new Senator();
                s.setIdentityCard(Integer.parseInt(tok.nextToken()));
                s.setName(tok.nextToken());
                s.setAdress(tok.nextToken());
                s.setCandidateName(tok.nextToken());
                s.setCandidateState(tok.nextToken());
                s.setMandate(Integer.parseInt(tok.nextToken()));
                pm.getTransaction().begin();
                pm.inject(s);
                pm.getTransaction().commit();

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
                    pm.inject(cgner);
                    pm.getTransaction().commit();
                    //adding
                    cgn.getCampaigners().add(cgner);
                }
                pm.getTransaction().begin();
                pm.inject(cgn);
                pm.getTransaction().commit();
            }
        }
        try (BufferedReader representativeFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/representative" + numberExperiment + ".txt")))) {
            while (representativeFile.ready()) {
                StringTokenizer tok = new StringTokenizer(representativeFile.readLine(), "\t");
                //Representative
                Representative r = new Representative();
                r.setIdentityCard(Integer.parseInt(tok.nextToken()));
                r.setName(tok.nextToken());
                r.setAdress(tok.nextToken());
                r.setCandidateName(tok.nextToken());
                r.setCandidateState(tok.nextToken());
                r.setPrefixName(tok.nextToken());
                r.setIsSpeaker(Boolean.parseBoolean(tok.nextToken()));
                pm.getTransaction().begin();
                pm.inject(r);
                pm.getTransaction().commit();

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
                    pm.inject(cgner);
                    pm.getTransaction().commit();
                    //adding
                    cgn.getCampaigners().add(cgner);
                }
                pm.getTransaction().begin();
                pm.inject(cgn);
                pm.getTransaction().commit();
            }
        }
        try (BufferedReader electorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/elector" + numberExperiment + ".txt")))) {
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
                pm.inject(e);
                pm.getTransaction().commit();

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
                pm.inject(v);
                pm.getTransaction().commit();
            }
        }
        try (BufferedReader countSenatorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/countingSenator" + numberExperiment + ".txt")))) {
            while (countSenatorFile.ready()) {
                StringTokenizer tok = new StringTokenizer(countSenatorFile.readLine(), "\t");
                //counting senator
                Counting count = new Counting();
                count.setRegister(Integer.parseInt(tok.nextToken()));
                count.setCandidate(listSenator.get(Integer.parseInt(tok.nextToken())));
                count.setTotal(Integer.parseInt(tok.nextToken()));
                count.setElection(election);
                pm.getTransaction().begin();
                pm.inject(count);
                pm.getTransaction().commit();
            }
        }
        try (BufferedReader countRepresentativeFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/countingRepresentative" + numberExperiment + ".txt")))) {
            while (countRepresentativeFile.ready()) {
                StringTokenizer tok = new StringTokenizer(countRepresentativeFile.readLine(), "\t");
                //counting senator
                Counting count = new Counting();
                count.setRegister(Integer.parseInt(tok.nextToken()));
                count.setCandidate(listRepresentative.get(Integer.parseInt(tok.nextToken())));
                count.setTotal(Integer.parseInt(tok.nextToken()));
                count.setElection(election);
                pm.getTransaction().begin();
                pm.inject(count);
                pm.getTransaction().commit();
            }
        }

        System.out.println("$Campaign time\t"+$Campaign.entityStructure.getAverageForAdd().measuredTime());
        System.out.println("$Campaign acesso a disco\t"+$Campaign.entityStructure.getAverageForAdd().measuredDiskAccess());
        System.out.println("$Campaign verificações\t"+$Campaign.entityStructure.getAverageForAdd().measuredVerifications());
        System.out.println("UniqueOneCampaign time\t"+$Campaign.uniqueOneCampaignStructure.getAverageForAdd().measuredTime());
        System.out.println("UniqueOneCampaign acesso a disco\t"+$Campaign.uniqueOneCampaignStructure.getAverageForAdd().measuredDiskAccess());
        System.out.println("UniqueOneCampaign verificações\t"+$Campaign.uniqueOneCampaignStructure.getAverageForAdd().measuredVerifications());

        
        long end = System.nanoTime();
        System.out.println("time=" + ((end - start) / 1000));
    }
}
