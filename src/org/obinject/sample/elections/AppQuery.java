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
import java.util.Date;
import java.util.StringTokenizer;
import org.obinject.meta.Uuid;

/**
 *
 * @author system
 */
public class AppQuery {

    private static final int numberExperiment = 1;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        long start = System.nanoTime();

//        Election election = new Election();
//        election.setYearElection(2012);
//        election.setMonitored(true);
        BufferedReader partidoFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/partyQuery" + numberExperiment + ".txt")));
        while (partidoFile.ready()) {
            StringTokenizer tok = new StringTokenizer(partidoFile.readLine(), "\t");
            String nameParty = tok.nextToken();
            String office = tok.nextToken();

            UniqueOneParty party = new UniqueOneParty();
            party.setName(nameParty);
            Uuid uuidParty = $Party.uniqueOnePartyStructure.find(party);
            if (uuidParty != null) {
                $Party queryParty = $Party.entityStructure.find(uuidParty);
                if (queryParty != null) {
                    if (!(office.equals(queryParty.getOffice()))) {
                        System.out.println("Party Entity not equal");
                    }
                } else {
                    System.out.println("Party Entity not found: " + uuidParty);
                }
            } else {
                System.out.println("Party Primary Key not found");
            }
        }
        try (BufferedReader senadorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/senatorQuery" + numberExperiment + ".txt")))) {
            while (senadorFile.ready()) {
                StringTokenizer tok = new StringTokenizer(senadorFile.readLine(), "\t");
                //senator
                int idSenator = Integer.parseInt(tok.nextToken());
                String nameSen = tok.nextToken();
                String adressSen = tok.nextToken();
                String candidateName = tok.nextToken();
                String candidateState = tok.nextToken();
                int mandate = Integer.parseInt(tok.nextToken());

                UniqueOnePerson senator = new UniqueOnePerson();
                senator.setIdentityCard(idSenator);
                Uuid uuidSenator = $Person.uniqueOnePersonStructure.find(senator);
                if (uuidSenator != null) {
                    $Senator querySenator = $Senator.entityStructure.find(uuidSenator);
                    if (querySenator != null) {
                        if (!((nameSen.equals(querySenator.getName()))
                                && (adressSen.equals(querySenator.getAdress()))
                                && (candidateName.equals(querySenator.getCandidateName()))
                                && (candidateState.equals(querySenator.getCandidateState()))
                                && (mandate == querySenator.getMandate()))) {
                            System.out.println("Senator Entity not equal");
                        }
                    } else {
                        System.out.println("Senator Entity not found: " + uuidSenator);
                    }
                } else {
                    System.out.println("Senator Primary Key not found");
                }

                //Campaign
                int regCampaign = Integer.parseInt(tok.nextToken());
                double spending = Double.parseDouble(tok.nextToken());
                double patrimony = Double.parseDouble(tok.nextToken());
                int posParty = Integer.parseInt(tok.nextToken());
                int totalCampaignerByCandidate = Integer.parseInt(tok.nextToken());

                UniqueOneCampaign campaign = new UniqueOneCampaign();
                campaign.setRegister(regCampaign);
                Uuid uuidCampaign = $Campaign.uniqueOneCampaignStructure.find(campaign);
                if (uuidCampaign != null) {
                    $Campaign queryCampaign = $Campaign.entityStructure.find(uuidCampaign);
                    if (queryCampaign != null) {
                        if (!((spending == queryCampaign.getSpending())
                                && (patrimony == queryCampaign.getPatrimony()))) {
                            System.out.println("Campaign Entity not equal");
                        }
                    } else {
                        System.out.println("Campaign Entity not found: " + uuidCampaign);
                    }
                } else {
                    System.out.println("Campaign Primary Key not found");
                }

                //Campaigner
                for (int i = 0; i < totalCampaignerByCandidate; i++) {
                    int idCampaigner = Integer.parseInt(tok.nextToken());
                    String nameCamp = tok.nextToken();
                    String adressCamp = tok.nextToken();
                    String assignment = tok.nextToken();
                    float income = Float.parseFloat(tok.nextToken());

                    UniqueOnePerson campaigner = new UniqueOnePerson();
                    campaigner.setIdentityCard(idCampaigner);
                    Uuid uuidCampaigner = $Person.uniqueOnePersonStructure.find(campaigner);
                    if (uuidCampaigner != null) {
                        $Campaigner queryCampaigner = $Campaigner.entityStructure.find(uuidCampaigner);
                        if (queryCampaigner != null) {
                            if (!((nameCamp.equals(queryCampaigner.getName()))
                                    && (adressCamp.equals(queryCampaigner.getAdress()))
                                    && (assignment.equals(queryCampaigner.getAssignment()))
                                    && (income == queryCampaigner.getIncome()))) {
                                System.out.println("Campaigner Entity not equal");
                            }
                        } else {
                            System.out.println("Campaigner Entity not found: " + uuidCampaigner);
                        }
                    } else {
                        System.out.println("Campaigner Primary Key not found");
                    }
                }
            }
        }
        try (BufferedReader deputadoFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/representativeQuery" + numberExperiment + ".txt")))) {
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

                UniqueOnePerson representative = new UniqueOnePerson();
                representative.setIdentityCard(idRepresentative);
                Uuid uuidRep = $Person.uniqueOnePersonStructure.find(representative);
                if (uuidRep != null) {
                    $Representative queryRep = $Representative.entityStructure.find(uuidRep);
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
                        System.out.println("Representative Entity not found: " + uuidRep);
                    }
                } else {
                    System.out.println("Representative Primary Key not found");
                }

                //Campaign
                int regCampaign = Integer.parseInt(tok.nextToken());
                double spending = Double.parseDouble(tok.nextToken());
                double patrimony = Double.parseDouble(tok.nextToken());
                int posParty = Integer.parseInt(tok.nextToken());
                int totalCampaignerByCandidate = Integer.parseInt(tok.nextToken());

                UniqueOneCampaign campaign = new UniqueOneCampaign();
                campaign.setRegister(regCampaign);
                Uuid uuidCampaign = $Campaign.uniqueOneCampaignStructure.find(campaign);
                if (uuidCampaign != null) {
                    $Campaign queryCampaign = $Campaign.entityStructure.find(uuidCampaign);
                    if (queryCampaign != null) {
                        if (!((spending == queryCampaign.getSpending())
                                && (patrimony == queryCampaign.getPatrimony()))) {
                            System.out.println("Campaign Entity not equal");
                        }
                    } else {
                        System.out.println("Campaign Entity not found: " + uuidCampaign);
                    }
                } else {
                    System.out.println("Campaign Primary Key not found");
                }

                //Campaigner
                for (int i = 0; i < totalCampaignerByCandidate; i++) {
                    int idCampaigner = Integer.parseInt(tok.nextToken());
                    String nameCamp = tok.nextToken();
                    String adressCamp = tok.nextToken();
                    String assignment = tok.nextToken();
                    float income = Float.parseFloat(tok.nextToken());

                    UniqueOnePerson campaigner = new UniqueOnePerson();
                    campaigner.setIdentityCard(idCampaigner);
                    Uuid uuidCampaigner = $Person.uniqueOnePersonStructure.find(campaigner);
                    if (uuidCampaigner != null) {
                        $Campaigner queryCampaigner = $Campaigner.entityStructure.find(uuidCampaigner);
                        if (queryCampaigner != null) {
                            if (!((nameCamp.equals(queryCampaigner.getName()))
                                    && (adressCamp.equals(queryCampaigner.getAdress()))
                                    && (assignment.equals(queryCampaigner.getAssignment()))
                                    && (income == queryCampaigner.getIncome()))) {
                                System.out.println("Campaigner Entity not equal");
                            }
                        } else {
                            System.out.println("Campaigner Entity not found: " + uuidCampaigner);
                        }
                    } else {
                        System.out.println("Campaigner Primary Key not found");
                    }
                }
            }
        }
        try (BufferedReader eleitorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/electorQuery" + numberExperiment + ".txt")))) {
            while (eleitorFile.ready()) {
                StringTokenizer tok = new StringTokenizer(eleitorFile.readLine(), "\t");
                //elector
                int idElector = Integer.parseInt(tok.nextToken());
                String nameElector = tok.nextToken();
                String adressElector = tok.nextToken();
                String race = tok.nextToken();
                int age = Integer.parseInt(tok.nextToken());

                UniqueOnePerson elector = new UniqueOnePerson();
                elector.setIdentityCard(idElector);
                Uuid uuidElector = $Person.uniqueOnePersonStructure.find(elector);
                if (uuidElector != null) {
                    $Elector queryElector = $Elector.entityStructure.find(uuidElector);
                    if (queryElector != null) {
                        if (!((nameElector.equals(queryElector.getName()))
                                && (adressElector.equals(queryElector.getAdress()))
                                && (race.equals(queryElector.getRace()))
                                && (age == queryElector.getAge()))) {
                            System.out.println("Elector Entity not equal");
                        }
                    } else {
                        System.out.println("Elector Entity not found: " + uuidElector);
                    }
                } else {
                    System.out.println("Elector Primary Key not found");
                }

                //voto
                int regVote = Integer.parseInt(tok.nextToken());
                Date instante = new Date(Long.parseLong(tok.nextToken()));
                int posSenator = Integer.parseInt(tok.nextToken());
                int posRepresentative = Integer.parseInt(tok.nextToken());

                UniqueOneVote vote = new UniqueOneVote();
                vote.setRegister(regVote);
                Uuid uuidVote = $Vote.uniqueOneVoteStructure.find(vote);
                if (uuidVote != null) {
                    $Vote queryVote = $Vote.entityStructure.find(uuidVote);
                    if (queryVote != null) {
//                        Since it does not allow mysql compare is not fair to compare the obinject
//                        if (instante.compareTo(queryVote.getInstante()) != 0) {
//                            System.out.println("Vote Entity not equal");
//                        }
                    } else {
                        System.out.println("Vote Entity not found: " + uuidVote);
                    }
                } else {
                    System.out.println("Vote Primary Key not found");
                }
            }
        }
        try (BufferedReader apuracaoSenFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/countingSenatorQuery" + numberExperiment + ".txt")))) {
            while (apuracaoSenFile.ready()) {
                StringTokenizer tok = new StringTokenizer(apuracaoSenFile.readLine(), "\t");
                //counting senator
                int regCounting = Integer.parseInt(tok.nextToken());
                int idCandidate = Integer.parseInt(tok.nextToken());               
                int total = Integer.parseInt(tok.nextToken());

                UniqueOneCounting counting = new UniqueOneCounting();
                counting.setRegister(regCounting);
                Uuid uuidCounting = $Counting.uniqueOneCountingStructure.find(counting);
                if (uuidCounting != null) {
                    $Counting queryConting = $Counting.entityStructure.find(uuidCounting);
                    if (queryConting != null) {
                        if (total != queryConting.getTotal()) {
                            System.out.println("Counting Entity not equal");
                        }
                    } else {
                        System.out.println("Counting Entity not found: " + uuidCounting);
                    }
                } else {
                    System.out.println("Counting Primary Key not found");
                }
            }
        }
        try (BufferedReader apuracaoDepFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/countingRepresentativeQuery" + numberExperiment + ".txt")))) {
            while (apuracaoDepFile.ready()) {
                StringTokenizer tok = new StringTokenizer(apuracaoDepFile.readLine(), "\t");
                //counting senator
                int regCounting = Integer.parseInt(tok.nextToken());
                int idCandidate = Integer.parseInt(tok.nextToken());               
                int total = Integer.parseInt(tok.nextToken());

                UniqueOneCounting counting = new UniqueOneCounting();
                counting.setRegister(regCounting);
                Uuid uuidCounting = $Counting.uniqueOneCountingStructure.find(counting);
                if (uuidCounting != null) {
                    $Counting queryConting = $Counting.entityStructure.find(uuidCounting);
                    if (queryConting != null) {
                        if (total != queryConting.getTotal()) {
                            System.out.println("Counting Entity not equal");
                        }
                    } else {
                        System.out.println("Counting Entity not found: " + uuidCounting);
                    }
                } else {
                    System.out.println("Counting Primary Key not found");
                }
            }
        }

        long termino = System.nanoTime();
        System.out.println("tempo=" + ((termino - start) / 1000));
    }
}
