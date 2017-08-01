/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;
import org.obinject.storage.MTree;
import org.obinject.storage.RTree;

public class AppCoocTexture {

    public static void main(String[] args) {
        try {
            System.out.println("#\t\t\t\t\t\tRTree\t\t\t\t\t\t\t\t\t\tMTree\t\t\t\t\t\t\t\t\t\t\tRTree\t\t\t\t\t\t\t\t\t\t\tMTree");
            System.out.println("#blockSize\tQtdValues\tTotalAccess\t\tTotalVerif\t\tTotalTime\tTotalAccess\t\tTotalVerif\t\tTotalTime\t\tTotalAccess\t\tTotalVerif\t\tTotalTime\t\tTotalAccess\t\tTotalVerif\t\tTotalTime");
            final int blockSize = 16384;

            BufferedReader in;
            PersistentManager pm = PersistentManagerFactory.createPersistentManager();
            MTree<PointOneCoocTexture> mtree;
            RTree<RectangleOneCoocTexture> rtree;
            int contador = 0;

            CoocTexture ct = new CoocTexture();

            // CoocTeexture
            in = new BufferedReader(new FileReader("../CoocTexture.txt"));
            contador = 0;
            while (in.ready() == true) {
                StringTokenizer tok = new StringTokenizer(in.readLine(), " ");
                ct.setId(Integer.parseInt(tok.nextToken()));
                for (int i = 0; i < 16; i++) {
                    ct.getTextures()[i] = Double.parseDouble(tok.nextToken());
                }
                pm.getTransaction().begin();
                pm.inject(ct);
                pm.getTransaction().commit();

                if (++contador % 1000 == 0) {
                    System.out.print(contador / 1000 + ",");
                }
            }
            in.close();
            System.out.println();

            rtree = RectangleOneCoocTexture.rectangleOneCoocTextureStructure;
            mtree = PointOneCoocTexture.pointOneCoocTextureStructure;

            rtree.getAverageForFind().resetMeasurements();
            mtree.getAverageForFind().resetMeasurements();

            RectangleOneCoocTexture rct = new RectangleOneCoocTexture();
            PointOneCoocTexture mct = new PointOneCoocTexture();

            in = new BufferedReader(new FileReader("../CoocTexture.txt"));
            contador = 0;
            long notFoundRTree = 0;
            long notFoundMTree = 0;
            while (in.ready() == true) {
                StringTokenizer tok = new StringTokenizer(in.readLine(), " ");
                int id = Integer.parseInt(tok.nextToken());
                rct.setId(id);
                mct.setId(id);
                for (int i = 0; i < 16; i++) {
                    double valor = Double.parseDouble(tok.nextToken());
                    rct.getTextures()[i] = valor;
                    mct.getTextures()[i] = valor;
                }
                
                if (mtree.find(mct) == null) {
                    notFoundMTree++;
                }
                
                if (rtree.find(rct) == null) {
                    notFoundRTree++;
                }
               
                if (++contador % 1000 == 0) {
                    System.out.print(contador / 1000 + ",");
                }
            }
            in.close();
            System.out.println();
            System.out.println("[MTree] not found:" + notFoundMTree);
            System.out.println("[RTree] not found:" + notFoundRTree);
            System.out.println();
            System.out.println(blockSize + "\t\t"
                    + rtree.getAverageForAdd().amountOfMeasurements() + "\t\t"
                    + rtree.getAverageForAdd().getTotalDiskAcess() + "\t\t\t"
                    + rtree.getAverageForAdd().getTotalVerifications() + "\t\t\t"
                    + rtree.getAverageForAdd().getTotalTime() + "\t\t"
                    + mtree.getAverageForAdd().getTotalDiskAcess() + "\t\t\t"
                    + mtree.getAverageForAdd().getTotalVerifications() + "\t\t\t"
                    + mtree.getAverageForAdd().getTotalTime() + "\t\t\t"
                    + rtree.getAverageForFind().getTotalDiskAcess() + "\t\t\t"
                    + rtree.getAverageForFind().getTotalVerifications() + "\t\t\t"
                    + rtree.getAverageForFind().getTotalTime() + "\t\t\t"
                    + mtree.getAverageForFind().getTotalDiskAcess() + "\t\t\t"
                    + mtree.getAverageForFind().getTotalVerifications() + "\t\t\t"
                    + mtree.getAverageForFind().getTotalTime());

        } catch (Exception ex) {
            Logger.getLogger(org.obinject.sample.color.AppCoocTexture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
