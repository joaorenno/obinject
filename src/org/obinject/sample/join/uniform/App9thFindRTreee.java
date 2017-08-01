/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.join.uniform;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.device.File;
import org.obinject.storage.RTree;

/**
 *
 * @author windows
 */
public class App9thFindRTreee {

    public static final int SIZE_OF_BLOCK = 512;

    public static void main(String[] args) {
        if (new java.io.File("RectangleOnePonto.rtree").exists()) {
            File f3 = new File("RectangleOnePonto.rtree");
            RTree<RectangleOnePonto> rtree = new RTree<RectangleOnePonto>(f3) {
            };
            RectangleOnePonto p2;
            int x = 0;
            try {
                BufferedReader in = new BufferedReader(new FileReader("resources/uniformRandom.txt"));
                while (in.ready() == true) {
                    StringTokenizer tok = new StringTokenizer(in.readLine(), "\t");
                    p2 = new RectangleOnePonto();
                    p2.setX(Integer.parseInt(tok.nextToken()));
                    p2.setY(Integer.parseInt(tok.nextToken()));
                    if (rtree.find(p2) == null) {
                        System.out.println("p2.x=" + p2.getOrigin(0)
                                + " p2.y=" + p2.getOrigin(2));
                    }
                    if ((x++ % 1000) == 0) {
                        System.out.println(x);
                    }
                }

                in.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(App9thFindRTreee.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(App9thFindRTreee.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("MTree not exists");
        }
    }
}
