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

public class AppColorMoments {

    public static void main(String[] args) {
        try {
            System.out.println("#\t\t\t\t\t\tRTree\t\t\t\t\t\t\t\t\t\tMTree\t\t\t\t\t\t\t\t\t\t\tRTree\t\t\t\t\t\t\t\t\t\t\tMTree");
            System.out.println("#blockSize\tQtdValues\tTotalAccess\t\tTotalVerif\t\tTotalTime\tTotalAccess\t\tTotalVerif\t\tTotalTime\t\tTotalAccess\t\tTotalVerif\t\tTotalTime\t\tTotalAccess\t\tTotalVerif\t\tTotalTime");
            final int blockSize = 2048;

            BufferedReader in;
            PersistentManager pm = PersistentManagerFactory.createPersistentManager();
            MTree<PointOneColorMoments> mtree;
            RTree<RectangleOneColorMoments> rtree;
            int contador = 0;

            ColorMoments cm = new ColorMoments();

            // ColorMoments
            in = new BufferedReader(new FileReader("../ColorMoments.txt"));
            contador = 0;
            while (in.ready() == true) {
                StringTokenizer tok = new StringTokenizer(in.readLine(), " ");
                cm.setId(Integer.parseInt(tok.nextToken()));
                for (int i = 0; i < 9; i++) {
                    cm.getMoments()[i] = Double.parseDouble(tok.nextToken());
                }
                pm.getTransaction().begin();
                pm.inject(cm);
                pm.getTransaction().commit();

                if (++contador % 1000 == 0) {
                    System.out.print(contador / 1000 + ",");
                }
            }
            in.close();
            System.out.println();

            rtree = RectangleOneColorMoments.rectangleOneColorMomentsStructure;
            mtree = PointOneColorMoments.pointOneColorMomentsStructure;

            rtree.getAverageForFind().resetMeasurements();
            mtree.getAverageForFind().resetMeasurements();

            RectangleOneColorMoments rcm = new RectangleOneColorMoments();
            PointOneColorMoments mcm = new PointOneColorMoments();

            in = new BufferedReader(new FileReader("../ColorMoments.txt"));
            contador = 0;
            long notFoundRTree = 0;
            long notFoundMTree = 0;
            while (in.ready() == true) {
                StringTokenizer tok = new StringTokenizer(in.readLine(), " ");
                int id = Integer.parseInt(tok.nextToken());
                rcm.setId(id);
                mcm.setId(id);
                for (int i = 0; i < 9; i++) {
                    double valor = Double.parseDouble(tok.nextToken());
                    rcm.getMoments()[i] = valor;
                    mcm.getMoments()[i] = valor;
                }
                
                if (mtree.find(mcm) == null) {
                    notFoundMTree++;
                }
                
                if (rtree.find(rcm) == null) {
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
            Logger.getLogger(org.obinject.sample.color.AppColorMoments.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
