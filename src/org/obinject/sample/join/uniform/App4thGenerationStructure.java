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
import org.obinject.storage.MTree;
import org.obinject.storage.RTree;
import org.obinject.storage.Sequential;

/**
 *
 * @author windows
 */
public class App4thGenerationStructure {

    public static final int SIZE_OF_BLOCK = 1024;

    public static void main(String[] args) {
        if (!(new java.io.File("PointOnePonto.mtree").exists())) {
            File f1 = new File("PointOnePonto.mtree", SIZE_OF_BLOCK);
            MTree<PointOnePonto> mtree = new MTree<PointOnePonto>(f1) {
            };
            File f2 = new File("$Point.seq", SIZE_OF_BLOCK);
            Sequential<$Ponto> seq = new Sequential<$Ponto>(f2) {
            };
            File f3 = new File("RectangleOnePonto.rtree", SIZE_OF_BLOCK);
            RTree<RectangleOnePonto> rtree = new RTree<RectangleOnePonto>(f3) {
            };
            PointOnePonto p1;
            RectangleOnePonto p2;
            int x = 0;
            try {
                BufferedReader in = new BufferedReader(new FileReader("resources/uniformRandom.txt"));
                while (in.ready() == true) {
                    StringTokenizer tok = new StringTokenizer(in.readLine(), "\t");
                    p1 = new PointOnePonto();
                    p1.setX(Integer.parseInt(tok.nextToken()));
                    p1.setY(Integer.parseInt(tok.nextToken()));
                    p2 = new RectangleOnePonto(p1);
                    mtree.add(p1);
                    seq.add(p1);
                    rtree.add(p2);
                    if ((x++ % 1000) == 0) {
                        System.out.println(x);
                    }
//                    System.out.format("%d %d%n", p.getX(), p.getY());
                }

                in.close();
                System.out.println("seq time=" + seq.getAverageForAdd().getTotalTime());
                System.out.println("seq verification=" + seq.getAverageForAdd().getTotalVerifications());
                System.out.println("seq disk acess=" + seq.getAverageForAdd().getTotalDiskAcess());
                System.out.println("mtree height=" + mtree.height());
                System.out.println("mtree time=" + mtree.getAverageForAdd().getTotalTime());
                System.out.println("mtree verification=" + mtree.getAverageForAdd().getTotalVerifications());
                System.out.println("mtree disk acess=" + mtree.getAverageForAdd().getTotalDiskAcess());

                System.out.println("rtree height=" + rtree.height());
                System.out.println("rtree time=" + rtree.getAverageForAdd().getTotalTime());
                System.out.println("rtree verification=" + rtree.getAverageForAdd().getTotalVerifications());
                System.out.println("rtree disk acess=" + rtree.getAverageForAdd().getTotalDiskAcess());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(App4thGenerationStructure.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(App4thGenerationStructure.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("MTree file already exists");
        }
    }
}
