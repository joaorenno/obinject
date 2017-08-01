/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.join.pcuk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author windows
 */
public class App2ndCleanRepeated {

    static class PostComparator implements Comparator<PostCode> {

        @Override
        public int compare(PostCode o1, PostCode o2) {
            if (o1.getLatitude() == o2.getLatitude() && o1.getLongitude() == o2.getLongitude()) {
                return 0;
            } else if (o1.getLatitude() >= o2.getLatitude() && o1.getLongitude() >= o2.getLongitude()) {
                return 1;
            } else {
                return -1;
            }
        }
    };

    public static void main(String[] args) {
        try {
            TreeSet<PostCode> set = new TreeSet<>(new PostComparator());
            BufferedReader in = new BufferedReader(new FileReader("resources/uk.txt"));
            PointOnePostCode p;
            while (in.ready() == true) {
                StringTokenizer tok = new StringTokenizer(in.readLine(), "\t");
                p = new PointOnePostCode();
                p.setLatitude(Double.parseDouble(tok.nextToken()));
                p.setLongitude(Double.parseDouble(tok.nextToken()));
                p.setCode(tok.nextToken());
                set.add(p);
            }
            in.close();
            System.out.println("set=" + set.size());
            PrintWriter out = new PrintWriter(new FileWriter("resources/ukClean.txt"));
            for (PostCode postCode : set) {
                out.printf("%.15f\t%.15f\t%s\n", postCode.getLatitude(), postCode.getLongitude(), postCode.getCode());
            }
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(App2ndCleanRepeated.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App2ndCleanRepeated.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
