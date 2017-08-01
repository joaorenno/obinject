package org.obinject.sample.join.multiview;

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

    public static final int SIZE_OF_BLOCK = 2048;

    public static void main(String[] args) {
        if (!(new java.io.File("PointOneObjectImage.mtree").exists())) {
            File f1 = new File("PointOneObjectImage.mtree", SIZE_OF_BLOCK);
            MTree<PointOneObjectImage> mtree = new MTree<PointOneObjectImage>(f1) {
            };
            File f2 = new File("$ObjectImage.seq", SIZE_OF_BLOCK);
            Sequential<$ObjectImage> seq = new Sequential<$ObjectImage>(f2) {
            };
            File f4 = new File("RectangleOneObjectImage.rtree", SIZE_OF_BLOCK);
            RTree<RectangleOneObjectImage> rtree = new RTree<RectangleOneObjectImage>(f4) {
            };
            int x = 0;
            try {
                int sizeSerial = 0;
                StringTokenizer tok;
                String bufObj;
                String bufImg;
                PointOneObjectImage p1;
                RectangleOneObjectImage p2;
                BufferedReader in = new BufferedReader(new FileReader("resources/aloiRandom.txt"));
                long start = System.nanoTime();
                long end;
                while (in.ready() == true) {
                    tok = new StringTokenizer(in.readLine(), " ");
                    p1 = new PointOneObjectImage();
                    for (int k = 0; k < 13; k++) {
                        p1.getFeature()[k] = Double.parseDouble(tok.nextToken());
                    }
                    bufObj = tok.nextToken();
                    bufObj = bufObj.substring(1, bufObj.length() - 1);
                    p1.setPathObject(bufObj);
                    bufImg = tok.nextToken();
                    bufImg = bufImg.substring(1, bufImg.length() - 1);
                    p1.setPathImage(bufImg);
                    p2 = new RectangleOneObjectImage(p1);
                    mtree.add(p1);
                    seq.add(p1);
                    rtree.add(p2);
                    sizeSerial += p1.sizeOfKey();
                    if ((x++ % 10) == 0) {
                        end = System.nanoTime();
                        System.out.println("[" + x + "][MEDIA] time(s)=" + (end-start)/1000 + " serialization=" + (sizeSerial / x));
                        start = System.nanoTime();
                    }
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
