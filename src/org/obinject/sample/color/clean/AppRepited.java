/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.color.clean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author system
 */
public class AppRepited {


    public static void main(String[] args) {
        TreeSet<VectorDouble> set = new TreeSet<>();
        double vector[] = new double[33];
        BufferedWriter out;
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader("../ColorHistogram.txt"));
//            in = new BufferedReader(new FileReader("../ColorMoments.txt"));
//            in = new BufferedReader(new FileReader("../CoocTexture.txt"));
            while (in.ready() == true) {
                StringTokenizer tok = new StringTokenizer(in.readLine(), " ");
                VectorDouble v = new VectorDouble(); 
                v.setId(Integer.parseInt(tok.nextToken()));
                for (int i = 0; i < 32; i++) {
                    v.getVector()[i] = Double.parseDouble(tok.nextToken());
                }
                set.add(v);
            }
            in.close();

            out = new BufferedWriter(new FileWriter("../ColorHistogramClean.txt"));
//            out = new BufferedWriter(new FileWriter("../ColorMomentsClean.txt"));
//            out = new BufferedWriter(new FileWriter("../CoocTextureClean.txt"));
            for (VectorDouble v : set) {
                String str1 = String.format(Locale.ENGLISH, "%d ", v.getId());
                out.append(str1.subSequence(0, str1.length()));
                for (int i = 0; i < 32; i++) {
                    String str2 = String.format(Locale.ENGLISH, "%.6f ", v.getVector()[i]);
                    out.append(str2.subSequence(0, str2.length()));

                }
                out.newLine();
            }
            out.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppRepited.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AppRepited.class.getName()).log(Level.SEVERE, null, ex);
        }




    }
}
