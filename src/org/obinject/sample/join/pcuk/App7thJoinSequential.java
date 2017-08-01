/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.join.pcuk;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.device.File;
import org.obinject.queries.ChanneledJoinSequential;
import org.obinject.storage.Sequential;

/**
 *
 * @author windows
 */
public class App7thJoinSequential {

    public static void main(String[] args) {
        if (new java.io.File("$PostCode.seq").exists()) {
            //generation of the file route.txt
            try {
                int idxSerieRoute = 0;
                int idxSeriePoint;
                File f1 = new File("$PostCode.seq");
                Sequential<$PostCode> seq = new Sequential<$PostCode>(f1) {
                };
                List<PointOnePostCode> route = new ArrayList<>();
                PointOnePostCode p;

                ChanneledJoinSequential<PointOnePostCode> seqInternal[][]
                        = new ChanneledJoinSequential[App5thGenerationRoute.AMOUNT_SERIE_ROUTE][App5thGenerationRoute.AMOUNT_SERIE_POINT];

                for (int amountRoute = App5thGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App5thGenerationRoute.TOTAL_ROUTE; amountRoute += App5thGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App5thGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App5thGenerationRoute.TOTAL_POINT; amountPoint += App5thGenerationRoute.INC_POINT_INITIAL) {
                        seqInternal[idxSerieRoute][idxSeriePoint] = new ChanneledJoinSequential<PointOnePostCode>(seq, route, App6thJoin.RANGE_CHANNEL) {
                        };
                        idxSeriePoint++;
                    }
                    idxSerieRoute++;
                }

                //amount of the serie
                idxSerieRoute = 0;
                BufferedReader in;
                for (int amountRoute = App5thGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App5thGenerationRoute.TOTAL_ROUTE; amountRoute += App5thGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App5thGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App5thGenerationRoute.TOTAL_POINT; amountPoint += App5thGenerationRoute.INC_POINT_INITIAL) {
                        in = new BufferedReader(new FileReader("resources/route" + amountRoute + "point" + amountPoint + ".txt"));
                        //amount of the route
//                        for (int i = 0; i < amountRoute; i++) {
                        long start = System.nanoTime();
                        StringTokenizer tok = new StringTokenizer(in.readLine(), "\t");
                        //clean route
                        route.clear();
                        //add route
                        for (int j = 0; j < amountPoint; j++) {
                            p = new PointOnePostCode();
                            p.setLatitude(Double.parseDouble(tok.nextToken()));
                            p.setLongitude(Double.parseDouble(tok.nextToken()));
                            route.add(p);
                        }
                        //
                        seqInternal[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                        Collection<PointOnePostCode> r1 = seqInternal[idxSerieRoute][idxSeriePoint].solve();
                        long end = System.nanoTime();
                        System.out.println("serie=" + idxSeriePoint + " millis=" + (end - start));
//                        }
                        in.close();
                        idxSeriePoint++;
                        // basta apenas uma execução
                        break;
                    }
                    idxSerieRoute++;
                    // basta apenas uma execução
                    break;
                }
                System.out.println("TIME");
                System.out.println("route\tpoint\tseqInternal");
                idxSerieRoute = 0;
                for (int amountRoute = App5thGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App5thGenerationRoute.TOTAL_ROUTE; amountRoute += App5thGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App5thGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App5thGenerationRoute.TOTAL_POINT; amountPoint += App5thGenerationRoute.INC_POINT_INITIAL) {
                        System.out.format(
                                "%d%c%d%c%.2f%n",
                                amountRoute, '\t', amountPoint, '\t',
                                seqInternal[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredTime());
                        idxSeriePoint++;
                    }
                    idxSerieRoute++;
                }
                System.out.println("VERIFICATION");
                System.out.println("route\tpoint\tseqInternal");
                idxSerieRoute = 0;
                for (int amountRoute = App5thGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App5thGenerationRoute.TOTAL_ROUTE; amountRoute += App5thGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App5thGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App5thGenerationRoute.TOTAL_POINT; amountPoint += App5thGenerationRoute.INC_POINT_INITIAL) {
                        System.out.format(
                                "%d%c%d%c%.2f%n",
                                amountRoute, '\t', amountPoint, '\t',
                                seqInternal[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredVerifications());
                        idxSeriePoint++;
                    }
                    idxSerieRoute++;
                }
                System.out.println("DISK ACESS");
                System.out.println("route\tpoint\tseqInternal");
                idxSerieRoute = 0;
                for (int amountRoute = App5thGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App5thGenerationRoute.TOTAL_ROUTE; amountRoute += App5thGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App5thGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App5thGenerationRoute.TOTAL_POINT; amountPoint += App5thGenerationRoute.INC_POINT_INITIAL) {
                        System.out.format(
                                "%d%c%d%c%.2f%n",
                                amountRoute, '\t', amountPoint, '\t',
                                seqInternal[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredDiskAccess());
                        idxSeriePoint++;
                    }
                    idxSerieRoute++;
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(App7thJoinSequential.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(App7thJoinSequential.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Sequential file not exists");
        }
    }
}
