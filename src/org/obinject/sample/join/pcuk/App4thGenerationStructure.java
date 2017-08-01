package org.obinject.sample.join.pcuk;

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

public class App4thGenerationStructure {
    
    public static final int SIZE_OF_BLOCK = 1024;
    
    public static void main(String[] args) {
        if (!(new java.io.File("PointOnePostCode.mtree").exists())) {
            File f1 = new File("PointOnePostCode.mtree", SIZE_OF_BLOCK);
            MTree<PointOnePostCode> mtree = new MTree<PointOnePostCode>(f1) {
            };
            File f2 = new File("$PostCode.seq", SIZE_OF_BLOCK);
            Sequential<$PostCode> seq = new Sequential<$PostCode>(f2) {
            };
            File f3 = new File("RectangleOnePostCode.rtree", SIZE_OF_BLOCK);
            RTree<RectangleOnePostCode> rtree = new RTree<RectangleOnePostCode>(f3) {
            };
            PointOnePostCode p1;
            RectangleOnePostCode p2;
            int x = 0;
            try {
                BufferedReader in = new BufferedReader(new FileReader("resources/ukRandom.txt"));
                while (in.ready() == true) {
                    StringTokenizer tok = new StringTokenizer(in.readLine(), "\t");
                    p1 = new PointOnePostCode();
                    p1.setLatitude(Double.parseDouble(tok.nextToken()));
                    p1.setLongitude(Double.parseDouble(tok.nextToken()));
                    p1.setCode(tok.nextToken());
                    p2 = new RectangleOnePostCode(p1);
                    mtree.add(p1);
                    seq.add(p1);
                    rtree.add(p2);
                    if ((x++ % 10000) == 0) {
                        System.out.println(x);
                    }
//                    System.out.format("%.15f %.15f %s%n", p.getLatitude(), p.getLongitude(), p.getCode());
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
