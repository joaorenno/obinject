/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.elections;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author system
 */
public class AppGenerationQuery {

    private static final Random rand = new Random();

    private static final float porcentagemQuery = 0.1f;

    public static void main(String[] args) throws FileNotFoundException, IOException {

        for (int numberExperiment = 1; numberExperiment <= 10; numberExperiment++) {

            List<BufferedReader> fileList = new ArrayList<>();
            fileList.add(new BufferedReader(new InputStreamReader(
                    new FileInputStream("resources/elections/party" + numberExperiment + ".txt"))));
            fileList.add(new BufferedReader(new InputStreamReader(
                    new FileInputStream("resources/elections/senator" + numberExperiment + ".txt"))));
            fileList.add(new BufferedReader(new InputStreamReader(
                    new FileInputStream("resources/elections/representative" + numberExperiment + ".txt"))));
            fileList.add(new BufferedReader(new InputStreamReader(
                    new FileInputStream("resources/elections/elector" + numberExperiment + ".txt"))));
            fileList.add(new BufferedReader(new InputStreamReader(
                    new FileInputStream("resources/elections/countingSenator" + numberExperiment + ".txt"))));
            fileList.add(new BufferedReader(new InputStreamReader(
                    new FileInputStream("resources/elections/countingRepresentative" + numberExperiment + ".txt"))));

            List<FileOutputStream> queryList = new ArrayList<>();
            queryList.add(new FileOutputStream("resources/elections/partyQuery" + numberExperiment + ".txt"));
            queryList.add(new FileOutputStream("resources/elections/senatorQuery" + numberExperiment + ".txt"));
            queryList.add(new FileOutputStream("resources/elections/representativeQuery" + numberExperiment + ".txt"));
            queryList.add(new FileOutputStream("resources/elections/electorQuery" + numberExperiment + ".txt"));
            queryList.add(new FileOutputStream("resources/elections/countingSenatorQuery" + numberExperiment + ".txt"));
            queryList.add(new FileOutputStream("resources/elections/countingRepresentativeQuery" + numberExperiment + ".txt"));

            Iterator<FileOutputStream> itQuery = queryList.iterator();
            String buffer = "";
            for (BufferedReader file : fileList) {
                FileOutputStream query = itQuery.next();
                int count = 0;
                while (file.ready()) {
                    buffer = file.readLine();
                    if (rand.nextFloat() < porcentagemQuery) {
                        query.write(buffer.getBytes());
                        query.write('\n');
                        count++;
                    }
                }
                if (count == 0) {
                    query.write(buffer.getBytes());
                    query.write('\n');
                }
            }
        }
    }
}
