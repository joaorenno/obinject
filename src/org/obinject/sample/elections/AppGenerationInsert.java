/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.elections;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 *
 * @author system
 */
public class AppGenerationInsert {

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

        for (int numberExperiment = 1; numberExperiment <= 10; numberExperiment++) {

            int maxParty = 10 * numberExperiment;
            int maxElector = 100000 * numberExperiment;
            int maxRandRepresentativeByParty = 20 * numberExperiment;
            int maxRandSenatorByParty = 2 * numberExperiment;
            int maxRandCampaignerByCandidate = 2 * numberExperiment;

            int maxRace = 50;

            int totalSenator = 0;
            int totalRepresentative = 0;
            int totalCampaigner = 0;
            int totalElector = 0;
            int totalParty = 0;
            int totalCampaign = 0;
            int totalVote = 0;
            int totalCounting = 0;
            int idCard = 0;
            StringBuilder buffer;
            
            List<Senator> listSenator = new  ArrayList<>();
            List<Representative> listRepresentative = new  ArrayList<>();
            
            //const
            Election election = new Election();
            election.setYearElection(2012);
            election.setMonitored(true);

            String races[] = new String[maxRace];
            for (int i = 0; i < maxRace; i++) {
                races[i] = stringAlfabeticGeneration(5, 15);
            }
            FileOutputStream senatorFile;
            FileOutputStream representativeFile;
            try (FileOutputStream partyFile = new FileOutputStream("resources/elections/party" + numberExperiment + ".txt")) {
                senatorFile = new FileOutputStream("resources/elections/senator" + numberExperiment + ".txt");
                representativeFile = new FileOutputStream("resources/elections/representative" + numberExperiment + ".txt");
                for (int i = 0; i < maxParty; i++) {
                    Party p = new Party();
                    p.setName(stringAlfabeticGeneration(20, 30));
                    p.setOffice(stringAlfabeticGeneration(20, 30));

                    buffer = new StringBuilder();
                    buffer.append(stringAlfabeticGeneration(20, 30)).append('\t')
                            .append(stringAlfabeticGeneration(20, 30)).append('\n');
                    partyFile.write(buffer.toString().getBytes());

                    int senatorByParty = random.nextInt(maxRandSenatorByParty) + 1;
                    totalParty++;
                    for (int j = 0; j < senatorByParty; j++) {
                        Senator s = new Senator();
                        s.setIdentityCard(++idCard);
                        s.setName(stringAlfabeticGeneration(20, 30));
                        s.setAdress(stringAlfabeticGeneration(20, 30));
                        s.setCandidateName(stringAlfabeticGeneration(20, 30));
                        s.setCandidateState(stringAlfabeticGeneration(20, 30));
                        s.setMandate(2008 + random.nextInt(6));
                        
                        listSenator.add(s);
                        
                        Campaign cgn = new Campaign();
                        cgn.setRegister(++totalCampaign);
                        cgn.setSpending(1000000 + random.nextInt(1000000));
                        cgn.setPatrimony(10000000 + random.nextInt(10000000));
                        cgn.setElection(election);
                        cgn.setCandidate(s);
                        cgn.setParty(p);

                        int totalCampaignerByCandidate = random.nextInt(maxRandCampaignerByCandidate) + 1;
                        buffer = new StringBuilder();
                        //Senator
                        buffer.append(idCard).append('\t')
                                .append(stringAlfabeticGeneration(20, 30)).append('\t')
                                .append(stringAlfabeticGeneration(20, 30)).append('\t')
                                .append(stringAlfabeticGeneration(20, 30)).append('\t')
                                .append(stringAlfabeticGeneration(20, 30)).append('\t')
                                .append(2008 + random.nextInt(6)).append('\t')
                                //Campaign
                                .append(totalCampaign).append('\t')
                                .append(1000000 + random.nextInt(1000000)).append('\t')
                                .append(10000000 + random.nextInt(10000000)).append('\t')
                                .append(i).append('\t')
                                .append(totalCampaignerByCandidate);
                        senatorFile.write(buffer.toString().getBytes());

                        totalSenator++;

                        for (int k = 0; k < totalCampaignerByCandidate; k++) {
                            Campaigner cgner = new Campaigner();
                            cgner.setIdentityCard(++idCard);
                            cgner.setName(stringAlfabeticGeneration(20, 30));
                            cgner.setAdress(stringAlfabeticGeneration(20, 30));
                            cgner.setAssignment(stringAlfabeticGeneration(10, 15));
                            cgner.setIncome(1000 + random.nextInt(1000));
                            cgn.getCampaigners().add(cgner);
                            buffer = new StringBuilder();
                            buffer.append('\t').append(idCard).append('\t')
                                    .append(stringAlfabeticGeneration(20, 30)).append('\t')
                                    .append(stringAlfabeticGeneration(20, 30)).append('\t')
                                    .append(stringAlfabeticGeneration(10, 15)).append('\t')
                                    .append(1000 + random.nextInt(1000));
                            senatorFile.write(buffer.toString().getBytes());
                            totalCampaigner++;
                        }
                        senatorFile.write('\n');
                    }

                    int totalRepresentativeByParty = random.nextInt(maxRandRepresentativeByParty) + 1;
                    for (int j = 0; j < totalRepresentativeByParty; j++) {
                        Representative r = new Representative();
                        r.setIdentityCard(++idCard);
                        r.setName(stringAlfabeticGeneration(20, 30));
                        r.setAdress(stringAlfabeticGeneration(20, 30));
                        r.setCandidateName(stringAlfabeticGeneration(20, 30));
                        r.setCandidateState(stringAlfabeticGeneration(20, 30));
                        r.setPrefixName(stringAlfabeticGeneration(3, 7));
                        r.setIsSpeaker(random.nextBoolean());
                        
                        listRepresentative.add(r);
                        
                        Campaign cgn = new Campaign();
                        cgn.setRegister(++totalCampaign);
                        cgn.setSpending(1000000 + random.nextInt(1000000));
                        cgn.setPatrimony(10000000 + random.nextInt(10000000));
                        cgn.setElection(election);
                        cgn.setCandidate(r);
                        cgn.setParty(p);
                        int totalCampaignerByCandidate = random.nextInt(maxRandCampaignerByCandidate) + 1;
                        buffer = new StringBuilder();
                        //Representative
                        buffer.append(idCard).append('\t')
                                .append(stringAlfabeticGeneration(20, 30)).append('\t')
                                .append(stringAlfabeticGeneration(20, 30)).append('\t')
                                .append(stringAlfabeticGeneration(20, 30)).append('\t')
                                .append(stringAlfabeticGeneration(20, 30)).append('\t')
                                .append(stringAlfabeticGeneration(3, 7)).append('\t')
                                .append(random.nextBoolean()).append('\t')
                                //Campaign
                                .append(totalCampaign).append('\t')
                                .append(1000000 + random.nextInt(1000000)).append('\t')
                                .append(10000000 + random.nextInt(10000000)).append('\t')
                                .append(i).append('\t')
                                .append(totalCampaignerByCandidate);
                        representativeFile.write(buffer.toString().getBytes());

                        totalRepresentative++;

                        for (int k = 0; k < totalCampaignerByCandidate; k++) {
                            Campaigner cgner = new Campaigner();
                            cgner.setIdentityCard(++idCard);
                            cgner.setName(stringAlfabeticGeneration(20, 30));
                            cgner.setAdress(stringAlfabeticGeneration(20, 30));
                            cgner.setAssignment(stringAlfabeticGeneration(10, 15));
                            cgner.setIncome(1000 + random.nextInt(1000));
                            cgn.getCampaigners().add(cgner);
                            buffer = new StringBuilder();
                            buffer.append('\t').append(idCard).append('\t')
                                    .append(stringAlfabeticGeneration(20, 30)).append('\t')
                                    .append(stringAlfabeticGeneration(20, 30)).append('\t')
                                    .append(stringAlfabeticGeneration(10, 15)).append('\t')
                                    .append(1000 + random.nextInt(1000));
                            representativeFile.write(buffer.toString().getBytes());
                            totalCampaigner++;
                        }
                        representativeFile.write('\n');
                    }
                }
            }

            int[] countingSenator;
            int[] countingRepresentative;
            try (FileOutputStream electorFile = new FileOutputStream("resources/elections/elector" + numberExperiment + ".txt")) {
                countingSenator = new int[totalSenator];
                countingRepresentative = new int[totalRepresentative];
                for (int i = 0; i < maxElector; i++) {
                    int posRace = random.nextInt(maxRace);
                    int posSenator = random.nextInt(totalSenator);
                    int posRepresentative = random.nextInt(totalRepresentative);

                    Elector e = new Elector();
                    e.setIdentityCard(++idCard);
                    e.setName(stringAlfabeticGeneration(20, 30));
                    e.setAdress(stringAlfabeticGeneration(20, 30));
                    e.setRace(races[posRace]);
                    e.setAge(20 + random.nextInt(20));

                    Vote v = new Vote();
                    v.setRegister(++totalVote);
                    v.setInstante(timeGeneration(2012, 11, 6));
                    v.setRepresentative(listRepresentative.get(posRepresentative));
                    v.setSenator(listSenator.get(posSenator));
                    v.setElector(e);
                    v.setElection(election);
                    buffer = new StringBuilder();
                    buffer.append(idCard).append('\t')
                            .append(stringAlfabeticGeneration(20, 30)).append('\t')
                            .append(stringAlfabeticGeneration(20, 30)).append('\t')
                            .append(races[posRace]).append('\t')
                            .append(20 + random.nextInt(20)).append('\t')
                            .append(totalVote).append('\t')
                            .append(timeGeneration(2012, 11, 6).getTime()).append('\t')
                            .append(posSenator).append('\t')
                            .append(posRepresentative).append('\n');
                    electorFile.write(buffer.toString().getBytes());

                    countingSenator[posSenator]++;
                    countingRepresentative[posRepresentative]++;

                    totalElector++;
                }
            }
            try (FileOutputStream countSenatorFile = new FileOutputStream("resources/elections/countingSenator" + numberExperiment + ".txt")) {
                for (int i = 0; i < totalSenator; i++) {
                    Counting count = new Counting();
                    count.setRegister(++totalCounting);
                    count.setCandidate(listSenator.get(i));
                    count.setTotal(countingSenator[i]);
                    count.setElection(election);
                    buffer = new StringBuilder();
                    buffer.append(totalCounting).append('\t').
                            append(i).append('\t').
                            append(countingSenator[i]).append('\n');
                    countSenatorFile.write(buffer.toString().getBytes());
                }
            }
            try (FileOutputStream countRepresentativeFile = new FileOutputStream("resources/elections/countingRepresentative" + numberExperiment + ".txt")) {
                for (int i = 0; i < totalRepresentative; i++) {
                    Counting count = new Counting();
                    count.setRegister(++totalCounting);
                    count.setCandidate(listRepresentative.get(i));
                    count.setTotal(countingRepresentative[i]);
                    count.setElection(election);
                    buffer = new StringBuilder();
                    buffer.append(totalCounting).append('\t').
                            append(i).append('\t').
                            append(countingRepresentative[i]).append('\n');
                    countRepresentativeFile.write(buffer.toString().getBytes());
                }
            }

            System.out.println("Experimento: " + numberExperiment);
            System.out.println("senators=" + totalSenator);
            System.out.println("representatives=" + totalRepresentative);
            System.out.println("campaigners=" + totalCampaigner);
            System.out.println("electors=" + totalElector);
            System.out.println("parties=" + totalParty);
            System.out.println("campaigns=" + totalCampaign);
            System.out.println("votes=" + totalVote);
            System.out.println("counting=" + totalCounting);
            System.out.println("total=" + (totalSenator + totalRepresentative + totalCampaigner + totalElector + totalParty + totalCampaign + totalVote + totalCounting) + "\n\n");
        }
    }
}
