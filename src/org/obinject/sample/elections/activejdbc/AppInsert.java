package org.obinject.sample.elections.activejdbc;
/*
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
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.javalite.activejdbc.Base;

public class AppInsert {

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

    public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
        long start = System.nanoTime();

        startActiveJdbc("build/classes");

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/elections", "elections", "elections");
        Base.connection().setAutoCommit(false);

        HashMap<Integer, Integer> listSenator = new HashMap<>();
        HashMap<Integer, Integer> listRepresentative = new HashMap<>();
        HashMap<Integer, String> listParty = new HashMap<>();

        Election election = new Election();
        election.set("yearElection", 2012);
        election.set("monitored", true);
        election.save();

        int nParty = 0;
        int nSenator = 0;
        int nRepresentative = 0;
        BufferedReader partidoFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/party" + numberExperiment + ".txt")));
        while (partidoFile.ready()) {
            StringTokenizer tok = new StringTokenizer(partidoFile.readLine(), "\t");
            Party p = new Party();
            String nameParty = tok.nextToken();
            p.set("name", nameParty);
            p.set("office", tok.nextToken());
            p.save();
            listParty.put(nParty, nameParty);
            nParty++;
        }
        try (BufferedReader senadorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/senator" + numberExperiment + ".txt")))) {
            while (senadorFile.ready()) {
                StringTokenizer tok = new StringTokenizer(senadorFile.readLine(), "\t");
                //senador
                Person s = new Person();
                s.set("DTYPE", "Senator");
                int idSenator = Integer.parseInt(tok.nextToken());
                s.set("identityCard", idSenator);
                s.set("name", tok.nextToken());
                s.set("adress", tok.nextToken());
                s.set("candidateName", tok.nextToken());
                s.set("candidateState", tok.nextToken());
                s.set("mandate", Integer.parseInt(tok.nextToken()));
                s.save();

                listSenator.put(nSenator, idSenator);
                nSenator++;

                //Campaign
                Campaign cgn = new Campaign();
                int regCampaign = Integer.parseInt(tok.nextToken());
                cgn.set("register", regCampaign);
                cgn.set("spending", Double.parseDouble(tok.nextToken()));
                cgn.set("patrimony", Double.parseDouble(tok.nextToken()));
                cgn.set("election_yearElection", 2012);
                cgn.set("candidate_identityCard", idSenator);
                int posParty = Integer.parseInt(tok.nextToken());
                int totalCampaignerByCandidate = Integer.parseInt(tok.nextToken());
                cgn.set("party_name", listParty.get(posParty));
                cgn.save();
                //Campaigner
                for (int i = 0; i < totalCampaignerByCandidate; i++) {
                    Person cgner = new Person();
                    int idCampaigner = Integer.parseInt(tok.nextToken());
                    cgner.set("DTYPE", "Campaigner");
                    cgner.set("identityCard", idCampaigner);
                    cgner.set("name", tok.nextToken());
                    cgner.set("adress", tok.nextToken());
                    cgner.set("assignment", tok.nextToken());
                    cgner.set("income", Float.parseFloat(tok.nextToken()));
                    cgner.save();
                    //adding
                    CampaignPerson cp = new CampaignPerson();
                    cp.set("Campaign_register", regCampaign);
                    cp.set("campaigners_identityCard", idCampaigner);
                    cp.save();
                }
            }
        }
        try (BufferedReader representativeFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/representative" + numberExperiment + ".txt")))) {
            while (representativeFile.ready()) {
                StringTokenizer tok = new StringTokenizer(representativeFile.readLine(), "\t");
                //representative
                Person r = new Person();
                int idRepresentative = Integer.parseInt(tok.nextToken());
                r.set("DTYPE", "Senator");
                r.set("identityCard", idRepresentative);
                r.set("name", tok.nextToken());
                r.set("adress", tok.nextToken());
                r.set("candidateName", tok.nextToken());
                r.set("candidateState", tok.nextToken());
                r.set("prefixName", tok.nextToken());
                r.set("isSpeaker", Boolean.parseBoolean(tok.nextToken()));
                r.save();

                listRepresentative.put(nRepresentative, idRepresentative);
                nRepresentative++;

                //Campaign
                Campaign cgn = new Campaign();
                int regCampaign = Integer.parseInt(tok.nextToken());
                cgn.set("register", regCampaign);
                cgn.set("spending", Double.parseDouble(tok.nextToken()));
                cgn.set("patrimony", Double.parseDouble(tok.nextToken()));
                cgn.set("election_yearElection", 2012);
                cgn.set("candidate_identityCard", idRepresentative);
                int posParty = Integer.parseInt(tok.nextToken());
                int totalCampaignerByCandidate = Integer.parseInt(tok.nextToken());
                cgn.set("party_name", listParty.get(posParty));
                //Campaigner
                for (int i = 0; i < totalCampaignerByCandidate; i++) {
                    Person cgner = new Person();
                    int idCampaigner = Integer.parseInt(tok.nextToken());
                    cgner.set("DTYPE", "Campaigner");
                    cgner.set("identityCard", idCampaigner);
                    cgner.set("name", tok.nextToken());
                    cgner.set("adress", tok.nextToken());
                    cgner.set("assignment", tok.nextToken());
                    cgner.set("income", Float.parseFloat(tok.nextToken()));
                    cgner.save();
                    //adding
                    CampaignPerson cp = new CampaignPerson();
                    cp.set("Campaign_register", regCampaign);
                    cp.set("campaigners_identityCard", idCampaigner);
                    cp.save();
                }
                cgn.save();
            }
        }
        try (BufferedReader electorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/elector" + numberExperiment + ".txt")))) {
            while (electorFile.ready()) {
                StringTokenizer tok = new StringTokenizer(electorFile.readLine(), "\t");
                //elector
                Person e = new Person();
                int idElector = Integer.parseInt(tok.nextToken());
                e.set("DTYPE", "Elector");
                e.set("identityCard", idElector);
                e.set("name", tok.nextToken());
                e.set("adress", tok.nextToken());
                e.set("race", tok.nextToken());
                e.set("age", Integer.parseInt(tok.nextToken()));
                e.save();

                //vote
                Vote v = new Vote();
                v.set("register", Integer.parseInt(tok.nextToken()));
                v.set("instante", new Date(Long.parseLong(tok.nextToken())));
                int posSenator = Integer.parseInt(tok.nextToken());
                int posRepresentative = Integer.parseInt(tok.nextToken());
                v.set("senator_identityCard", listSenator.get(posSenator));
                v.set("representative_identityCard", listRepresentative.get(posRepresentative));
                v.set("elector_identityCard", idElector);
                v.set("election_yearElection", 2012);
                v.save();
            }
        }
        try (BufferedReader countSenatorFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/countingSenator" + numberExperiment + ".txt")))) {
            while (countSenatorFile.ready()) {
                StringTokenizer tok = new StringTokenizer(countSenatorFile.readLine(), "\t");
                //counting senator
                Counting count = new Counting();
                count.set("register", Integer.parseInt(tok.nextToken()));
                count.set("candidate_identityCard", listSenator.get(Integer.parseInt(tok.nextToken())));
                count.set("total", Integer.parseInt(tok.nextToken()));
                count.set("election_yearElection", 2012);
                count.save();
            }
        }
        try (BufferedReader countRepresentativeFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/elections/countingRepresentative" + numberExperiment + ".txt")))) {
            while (countRepresentativeFile.ready()) {
                StringTokenizer tok = new StringTokenizer(countRepresentativeFile.readLine(), "\t");
                //counting senator
                Counting count = new Counting();
                count.set("register", Integer.parseInt(tok.nextToken()));
                count.set("candidate_identityCard", listRepresentative.get(Integer.parseInt(tok.nextToken())));
                count.set("total", Integer.parseInt(tok.nextToken()));
                count.set("election_yearElection", 2012);
                count.save();
            }
        }

        Base.commitTransaction();
        long end = System.nanoTime();
        System.out.println("time=" + ((end - start) / 1000));
    }
}
*/