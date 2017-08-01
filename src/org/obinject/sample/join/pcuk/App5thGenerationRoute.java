package org.obinject.sample.join.pcuk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.device.File;
import org.obinject.queries.KNearestNeighborMTree;
import org.obinject.storage.MTree;

public class App5thGenerationRoute {

    public static final int AMOUNT_K = 50;
    public static final int AMOUNT_SERIE_ROUTE = 1;
    public static final int AMOUNT_SERIE_POINT = 10;
    public static final int AMOUNT_ROUTE_INITIAL = 50;
    public static final int AMOUNT_POINT_INITIAL = 50;
    public static final int INC_ROUTE_INITIAL = 100;
    public static final int INC_POINT_INITIAL = 100;
    public static final int TOTAL_ROUTE = AMOUNT_ROUTE_INITIAL + (AMOUNT_SERIE_ROUTE * INC_ROUTE_INITIAL) - INC_ROUTE_INITIAL;
    public static final int TOTAL_POINT = AMOUNT_POINT_INITIAL + (AMOUNT_SERIE_POINT * INC_POINT_INITIAL) - INC_POINT_INITIAL;
    public static final Random RANDOM_JOIN = new Random();
    public static final Random RANDOM_INSERT = new Random(100); // SAME SORTED ORDER IN BASE

    public static void main(String[] args) {
        BufferedWriter txtFile;
        if (new java.io.File("PointOnePostCode.mtree").exists()) {
            File f1 = new File("PointOnePostCode.mtree");
            MTree<PointOnePostCode> mtree = new MTree<PointOnePostCode>(f1) {
            };
            List<PointOnePostCode> list = new ArrayList<>();
            PointOnePostCode p;
            //generation of the file route.txt
            try {
                BufferedReader in = new BufferedReader(new FileReader("resources/ukRandom.txt"));
                while (in.ready() == true) {
                    StringTokenizer tok = new StringTokenizer(in.readLine(), "\t");
                    p = new PointOnePostCode();
                    p.setLatitude(Double.parseDouble(tok.nextToken()));
                    p.setLongitude(Double.parseDouble(tok.nextToken()));
                    p.setCode(tok.nextToken());
//                    System.out.format("%.15f %.15f %s%n", p.getLatitude(), p.getLongitude(), p.getCode());
                    list.add(p);
                }
                in.close();
                //amount of the serie
                for (int amountRoute = AMOUNT_ROUTE_INITIAL; amountRoute <= TOTAL_ROUTE; amountRoute += INC_ROUTE_INITIAL) {
                    for (int amountPoint = AMOUNT_POINT_INITIAL; amountPoint <= TOTAL_POINT; amountPoint += INC_POINT_INITIAL) {
                        txtFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                                "resources/route" + amountRoute + "point" + amountPoint + ".txt")));
                        //amount of the route
                        //SNAKE GAME TECNIQUE OF THE ROUTE GENERATION 
                        for (int i = 0; i < amountRoute; i++) {
                            p = list.remove(RANDOM_JOIN.nextInt(list.size()));
                            int opt = RANDOM_JOIN.nextInt(4);
                            for (int j = 0; j < amountPoint; j++) {
                                txtFile.write(p.getLatitude() + "\t" + p.getLongitude() + "\t");
                                KNearestNeighborMTree<PointOnePostCode> knn
                                        = new KNearestNeighborMTree<PointOnePostCode>(mtree, p, AMOUNT_K) {
                                };
                                Collection<PointOnePostCode> res = knn.solve();
                                for (PointOnePostCode actual : res) {
                                    switch (opt) {
                                        case 0:
                                            if (actual.getLatitude() <= p.getLatitude()) {
                                                p = actual;
                                            }
                                            break;
                                        case 1:
                                            if (actual.getLatitude() > p.getLatitude()) {
                                                p = actual;
                                            }
                                            break;
                                        case 2:
                                            if (actual.getLongitude() <= p.getLongitude()) {
                                                p = actual;
                                            }
                                            break;
                                        case 3:
                                        default:
                                            if (actual.getLongitude() > p.getLongitude()) {
                                                p = actual;
                                            }
                                            break;
                                    }
                                }
                            }
                            txtFile.newLine();
                        }
                        txtFile.close();
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(App5thGenerationRoute.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(App5thGenerationRoute.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("PointOnePostCode.mtree not exists");
        }
    }
}
