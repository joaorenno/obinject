package org.obinject.sample.elections.hibernate;

/*
package br.ufpr.inf.sbbd2014.hibernate.elections;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.StringTokenizer;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class AppQuery {

    private static final int numberExperiment = 1;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        long start = System.nanoTime();

        EntityManager em = Persistence.createEntityManagerFactory("electionsPU").createEntityManager();

//        Election election = new Election();
//        election.setYearElection(2012);
//        election.setMonitored(true);
        BufferedReader partidoFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/partyQuery" + numberExperiment + ".txt")));
        while (partidoFile.ready()) {
            StringTokenizer tok = new StringTokenizer(partidoFile.readLine(), "\t");
            String nameParty = tok.nextToken();
            String office = tok.nextToken();

            Party queryParty = em.find(Party.class, nameParty);
            if (queryParty != null) {
                if (!(office.equals(queryParty.getOffice()))) {
                    System.out.println("Party Entity not equal");
                }
            } else {
                System.out.println("Party Entity not found");
            }
            em.clear();

        }
        try (BufferedReader senadorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/senatorQuery" + numberExperiment + ".txt")))) {
            while (senadorFile.ready()) {
                StringTokenizer tok = new StringTokenizer(senadorFile.readLine(), "\t");
                //senator
                int idsenator = Integer.parseInt(tok.nextToken());
                String nameSen = tok.nextToken();
                String adressSen = tok.nextToken();
                String candidateName = tok.nextToken();
                String candidateState = tok.nextToken();
                int mandate = Integer.parseInt(tok.nextToken());

                Senator querySenator = em.find(Senator.class, idsenator);
                if (querySenator != null) {
                    if (!((nameSen.equals(querySenator.getName()))
                            && (adressSen.equals(querySenator.getAdress()))
                            && (candidateName.equals(querySenator.getCandidateName()))
                            && (candidateState.equals(querySenator.getCandidateState()))
                            && (mandate == querySenator.getMandate()))) {
                        System.out.println("Senator Entity not equal");
                    }
                } else {
                    System.out.println("Senator Entity not found");
                }
                em.clear();

                //Campaign
                int regCampaign = Integer.parseInt(tok.nextToken());
                double spending = Double.parseDouble(tok.nextToken());
                double patrimony = Double.parseDouble(tok.nextToken());
                int posParty = Integer.parseInt(tok.nextToken());
                int totalCampaignerByCandidate = Integer.parseInt(tok.nextToken());

                Campaign queryCampaign = em.find(Campaign.class, regCampaign);
                if (queryCampaign != null) {
                    if (!((spending == queryCampaign.getSpending())
                            && (patrimony == queryCampaign.getPatrimony()))) {
                        System.out.println("Campaign Entity not equal");
                    }
                } else {
                    System.out.println("Campaign Entity not found");
                }
                em.clear();

                //Campaigner
                for (int i = 0; i < totalCampaignerByCandidate; i++) {
                    int idCampaigner = Integer.parseInt(tok.nextToken());
                    String nameCamp = tok.nextToken();
                    String adressCamp = tok.nextToken();
                    String assignment = tok.nextToken();
                    float income = Float.parseFloat(tok.nextToken());

                    Campaigner queryCampaigner = em.find(Campaigner.class, idCampaigner);
                    if (queryCampaigner != null) {
                        if (!((nameCamp.equals(queryCampaigner.getName()))
                                && (adressCamp.equals(queryCampaigner.getAdress()))
                                && (assignment.equals(queryCampaigner.getAssignment()))
                                && (income == queryCampaigner.getIncome()))) {
                            System.out.println("Campaigner Entity not equal");
                        }
                    } else {
                        System.out.println("Campaigner Entity not found");
                    }
                    em.clear();

                }
            }
        }
        try (BufferedReader deputadoFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/representativeQuery" + numberExperiment + ".txt")))) {
            while (deputadoFile.ready()) {
                StringTokenizer tok = new StringTokenizer(deputadoFile.readLine(), "\t");
                //Representative
                int idRepresentative = Integer.parseInt(tok.nextToken());
                String nameRep = tok.nextToken();
                String adressRep = tok.nextToken();
                String candidateName = tok.nextToken();
                String candidateState = tok.nextToken();
                String prefixName = tok.nextToken();
                boolean isSpeaker = Boolean.parseBoolean(tok.nextToken());

                Representative queryRep = em.find(Representative.class, idRepresentative);
                if (queryRep != null) {
                    if (!((nameRep.equals(queryRep.getName()))
                            && (adressRep.equals(queryRep.getAdress()))
                            && (candidateName.equals(queryRep.getCandidateName()))
                            && (candidateState.equals(queryRep.getCandidateState()))
                            && (prefixName.equals(queryRep.getPrefixName()))
                            && (isSpeaker == queryRep.isIsSpeaker()))) {
                        System.out.println("Representative Entity not equal");
                    }
                } else {
                    System.out.println("Representative Entity not found");
                }
                em.clear();

                //Campaign
                int regCampaign = Integer.parseInt(tok.nextToken());
                double spending = Double.parseDouble(tok.nextToken());
                double patrimony = Double.parseDouble(tok.nextToken());
                int posParty = Integer.parseInt(tok.nextToken());
                int totalCampaignerByCandidate = Integer.parseInt(tok.nextToken());

                Campaign queryCampaign = em.find(Campaign.class, regCampaign);
                if (queryCampaign != null) {
                    if (!((spending == queryCampaign.getSpending())
                            && (patrimony == queryCampaign.getPatrimony()))) {
                        System.out.println("Campaign Entity not equal");
                    }
                } else {
                    System.out.println("Campaign Entity not found");
                }
                em.clear();

                //Campaigner
                for (int i = 0; i < totalCampaignerByCandidate; i++) {
                    int idCampaigner = Integer.parseInt(tok.nextToken());
                    String nameCamp = tok.nextToken();
                    String adressCamp = tok.nextToken();
                    String assignment = tok.nextToken();
                    float income = Float.parseFloat(tok.nextToken());

                    Campaigner queryCampaigner = em.find(Campaigner.class, idCampaigner);
                    if (queryCampaigner != null) {
                        if (!((nameCamp.equals(queryCampaigner.getName()))
                                && (adressCamp.equals(queryCampaigner.getAdress()))
                                && (assignment.equals(queryCampaigner.getAssignment()))
                                && (income == queryCampaigner.getIncome()))) {
                            System.out.println("Campaigner Entity not equal");
                        }
                    } else {
                        System.out.println("Campaigner Entity not found");
                    }
                    em.clear();

                }
            }
        }
        try (BufferedReader eleitorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/electorQuery" + numberExperiment + ".txt")))) {
            while (eleitorFile.ready()) {
                StringTokenizer tok = new StringTokenizer(eleitorFile.readLine(), "\t");
                //elector
                int idElector = Integer.parseInt(tok.nextToken());
                String nameElector = tok.nextToken();
                String adressElector = tok.nextToken();
                String race = tok.nextToken();
                int age = Integer.parseInt(tok.nextToken());

                Elector queryElector = em.find(Elector.class, idElector);
                if (queryElector != null) {
                    if (!((nameElector.equals(queryElector.getName()))
                            && (adressElector.equals(queryElector.getAdress()))
                            && (race.equals(queryElector.getRace()))
                            && (age == queryElector.getAge()))) {
                        System.out.println("Elector Entity not equal");
                    }
                } else {
                    System.out.println("Elector Entity not found");
                }
                em.clear();

                //voto
                int regVote = Integer.parseInt(tok.nextToken());
                Date instante = new Date(Long.parseLong(tok.nextToken()));
                int posSenator = Integer.parseInt(tok.nextToken());
                int posRepresentative = Integer.parseInt(tok.nextToken());

                Vote queryVote = em.find(Vote.class, regVote);
                if (queryVote != null) {
//                    mysql does not store time
//                    if (instante.compareTo(queryVote.getInstante()) != 0) {
//                        System.out.println("Vote Entity not equal");
//                    }
                } else {
                    System.out.println("Vote Entity not found");
                }
                em.clear();

            }
        }
        try (BufferedReader apuracaoSenFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/countingSenatorQuery" + numberExperiment + ".txt")))) {
            while (apuracaoSenFile.ready()) {
                StringTokenizer tok = new StringTokenizer(apuracaoSenFile.readLine(), "\t");
                //counting senator
                int regCounting = Integer.parseInt(tok.nextToken());
                int idCandidate = Integer.parseInt(tok.nextToken());
                int total = Integer.parseInt(tok.nextToken());
                Counting queryConting = em.find(Counting.class, regCounting);
                if (queryConting != null) {
                    if (total != queryConting.getTotal()) {
                        System.out.println("Counting Entity not equal");
                    }
                } else {
                    System.out.println("Counting Entity not found");
                }
                em.clear();
            }
        }
        try (BufferedReader apuracaoDepFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/countingRepresentativeQuery" + numberExperiment + ".txt")))) {
            while (apuracaoDepFile.ready()) {
                StringTokenizer tok = new StringTokenizer(apuracaoDepFile.readLine(), "\t");
                //counting senator
                int regCounting = Integer.parseInt(tok.nextToken());
                int idCandidate = Integer.parseInt(tok.nextToken());
                int total = Integer.parseInt(tok.nextToken());
                Counting queryConting = em.find(Counting.class, regCounting);
                if (queryConting != null) {
                    if (total != queryConting.getTotal()) {
                        System.out.println("Counting Entity not equal");
                    }
                } else {
                    System.out.println("Counting Entity not found");
                }
                em.clear();
            }
        }

        long termino = System.nanoTime();
        System.out.println("tempo=" + ((termino - start) / 1000));
    }
}
 */
