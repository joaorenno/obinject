package org.obinject.sample.elections.activejdbc;

/*
package br.ufpr.inf.sbbd2014.activejdbc.elections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javalite.activejdbc.Base;

public class AppQuery {

    private static final int numberExperiment = 10;

    private static void startActiveJdbc(String buildDir) {
        File f = new File(buildDir);
        URL u;
        try {
            u = f.toURL();
            URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            Class urlClass = URLClassLoader.class;
            Method method;
            method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            method.invoke(urlClassLoader, new Object[]{u});
        } catch (MalformedURLException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(AppInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.setProperty("outputDirectory", buildDir);
        org.javalite.instrumentation.Main.main(null);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        long start = System.nanoTime();

        startActiveJdbc("build/classes");

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/elections", "elections", "elections");

//        Election election = new Election();
//        election.setYearElection(2012);
//        election.setMonitored(true);
        BufferedReader partidoFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/partyQuery" + numberExperiment + ".txt")));
        while (partidoFile.ready()) {
            StringTokenizer tok = new StringTokenizer(partidoFile.readLine(), "\t");
            String nameParty = tok.nextToken();
            String office = tok.nextToken();
            List<Party> listParty = Party.where("name = '" + nameParty + "'");
            if (!listParty.isEmpty()) {
                if (!(office.equals(listParty.get(0).getString("office")))) {
                    System.out.println("Party Entity not equal");
                }
            } else {
                System.out.println("Party Entity not found");
            }
        }
        try (BufferedReader senadorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("data/elections/senatorQuery" + numberExperiment + ".txt")))) {
            while (senadorFile.ready()) {
                StringTokenizer tok = new StringTokenizer(senadorFile.readLine(), "\t");
                //senator
                int idSenator = Integer.parseInt(tok.nextToken());
                String nameSen = tok.nextToken();
                String adressSen = tok.nextToken();
                String candidateName = tok.nextToken();
                String candidateState = tok.nextToken();
                int mandate = Integer.parseInt(tok.nextToken());

                List<Person> listSenator = Person.where("identityCard = " + idSenator);
                if (!listSenator.isEmpty()) {
                    if (!((nameSen.equals(listSenator.get(0).getString("name")))
                            && (adressSen.equals(listSenator.get(0).getString("adress")))
                            && (candidateName.equals(listSenator.get(0).getString("candidateName")))
                            && (candidateState.equals(listSenator.get(0).getString("candidateState")))
                            && (mandate == listSenator.get(0).getInteger("Mandate")))) {
                        System.out.println("Senator Entity not equal");
                    }
                } else {
                    System.out.println("Senator Entity not found");
                }

                //Campaign
                int regCampaign = Integer.parseInt(tok.nextToken());
                double spending = Double.parseDouble(tok.nextToken());
                double patrimony = Double.parseDouble(tok.nextToken());
                int posParty = Integer.parseInt(tok.nextToken());
                int totalCampaignerByCandidate = Integer.parseInt(tok.nextToken());

                List<Campaign> listCampaign = Campaign.where("register = " + regCampaign);
                if (!listCampaign.isEmpty()) {
                    if (!((spending == listCampaign.get(0).getDouble("spending")))
                            && (patrimony == listCampaign.get(0).getDouble("patrimony"))) {
                        System.out.println("Campaign Entity not equal");
                    }
                } else {
                    System.out.println("Campaign Entity not found");
                }

                //Campaigner
                for (int i = 0; i < totalCampaignerByCandidate; i++) {
                    int idCampaigner = Integer.parseInt(tok.nextToken());
                    String nameCamp = tok.nextToken();
                    String adressCamp = tok.nextToken();
                    String assignment = tok.nextToken();
                    float income = Float.parseFloat(tok.nextToken());

                    List<Person> listCampaigner = Person.where("identityCard = " + idCampaigner);
                    if (!listCampaigner.isEmpty()) {
                        if (!((nameCamp.equals(listCampaigner.get(0).getString("name")))
                                && (adressCamp.equals(listCampaigner.get(0).getString("adress")))
                                && (assignment.equals(listCampaigner.get(0).getString("assignment")))
                                && (income == listCampaigner.get(0).getFloat("income")))) {
                            System.out.println("Campaigner Entity not equal");
                        }
                    } else {
                        System.out.println("Campaigner Entity not found");
                    }
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

                List<Person> listRep = Person.where("identityCard = " + idRepresentative);
                if (!listRep.isEmpty()) {
                    if (!((nameRep.equals(listRep.get(0).getString("name")))
                            && (adressRep.equals(listRep.get(0).getString("adress")))
                            && (candidateName.equals(listRep.get(0).getString("candidateName")))
                            && (candidateState.equals(listRep.get(0).getString("candidateState")))
                            && (prefixName.equals(listRep.get(0).getString("prefixName")))
                            && (isSpeaker == listRep.get(0).getBoolean("isSpeaker")))) {
                        System.out.println("Representative Entity not equal");
                    }
                } else {
                    System.out.println("Representative Entity not found");
                }

                //Campaign
                int regCampaign = Integer.parseInt(tok.nextToken());
                double spending = Double.parseDouble(tok.nextToken());
                double patrimony = Double.parseDouble(tok.nextToken());
                int posParty = Integer.parseInt(tok.nextToken());
                int totalCampaignerByCandidate = Integer.parseInt(tok.nextToken());

                List<Campaign> listCampaign = Campaign.where("register = " + regCampaign);
                if (!listCampaign.isEmpty()) {
                    if (!((spending == listCampaign.get(0).getDouble("spending")))
                            && (patrimony == listCampaign.get(0).getDouble("patrimony"))) {
                        System.out.println("Campaign Entity not equal");
                    }
                } else {
                    System.out.println("Campaign Entity not found");
                }

                //Campaigner
                for (int i = 0; i < totalCampaignerByCandidate; i++) {
                    int idCampaigner = Integer.parseInt(tok.nextToken());
                    String nameCamp = tok.nextToken();
                    String adressCamp = tok.nextToken();
                    String assignment = tok.nextToken();
                    float income = Float.parseFloat(tok.nextToken());

                    List<Person> listCampaigner = Person.where("identityCard = " + idCampaigner);
                    if (!listCampaigner.isEmpty()) {
                        if (!((nameCamp.equals(listCampaigner.get(0).getString("name")))
                                && (adressCamp.equals(listCampaigner.get(0).getString("adress")))
                                && (assignment.equals(listCampaigner.get(0).getString("assignment")))
                                && (income == listCampaigner.get(0).getFloat("income")))) {
                            System.out.println("Campaigner Entity not equal");
                        }
                    } else {
                        System.out.println("Campaigner Entity not found");
                    }
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

                List<Person> listElector = Person.where("identityCard = " + idElector);
                if (!listElector.isEmpty()) {
                    if (!((nameElector.equals(listElector.get(0).getString("name")))
                            && (adressElector.equals(listElector.get(0).getString("adress")))
                            && (race.equals(listElector.get(0).getString("race")))
                            && (age == listElector.get(0).getInteger("age")))) {
                        System.out.println("Elector Entity not equal");
                    }
                } else {
                    System.out.println("Elector Entity not found");
                }

                //voto
                int regVote = Integer.parseInt(tok.nextToken());
                Date instante = new Date(Long.parseLong(tok.nextToken()));
                int posSenator = Integer.parseInt(tok.nextToken());
                int posRepresentative = Integer.parseInt(tok.nextToken());

                List<Vote> list = Vote.where("register = " + regVote);
                if (!list.isEmpty()) {
//                    mysql does not store time
                } else {
                    System.out.println("Vote Entity not found");
                }
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

                List<Counting> listCounting = Counting.where("register = " + regCounting);
                if (!listCounting.isEmpty()) {
                    if (total != listCounting.get(0).getInteger("total")) {
                        System.out.println("Counting Entity not equal");
                    }                    
                }else{
                    System.out.println("Counting Entity not found");
                }
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
                
                List<Counting> listCounting = Counting.where("register = " + regCounting);
                if (!listCounting.isEmpty()) {
                    if (total != listCounting.get(0).getInteger("total")) {
                        System.out.println("Counting Entity not equal");
                    }                    
                }else{
                    System.out.println("Counting Entity not found");
                }
            }
        }

        long termino = System.nanoTime();
    }
}
 */
