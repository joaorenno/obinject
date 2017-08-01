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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.device.File;
import org.obinject.queries.ChanneledJoinSequential;
import org.obinject.sample.join.uniform.App3rdGenerationRoute;
import org.obinject.storage.Sequential;

/**
 *
 * @author windows
 */
public class App6thJoinSequential {

    public static void main(String[] args) {
        if (new java.io.File("$Point.seq").exists()) {
            //generation of the file route.txt
            try {

                int idxSerieRoute = 0;
                int idxSeriePoint;
                File f1 = new File("$Point.seq");
                Sequential<$Ponto> seq = new Sequential<$Ponto>(f1) {
                };
                List<PointOnePonto> route = new ArrayList<>();
                PointOnePonto p;

                ChanneledJoinSequential<PointOnePonto> seqInternal[][]
                        = new ChanneledJoinSequential[App3rdGenerationRoute.AMOUNT_SERIE_ROUTE][App3rdGenerationRoute.AMOUNT_SERIE_POINT];

                for (int amountRoute = App3rdGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App3rdGenerationRoute.TOTAL_ROUTE; amountRoute += App3rdGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App3rdGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App3rdGenerationRoute.TOTAL_POINT; amountPoint += App3rdGenerationRoute.INC_POINT_INITIAL) {
                        seqInternal[idxSerieRoute][idxSeriePoint] = new ChanneledJoinSequential<PointOnePonto>(seq, route, 1.0d) {
                        };
                        idxSeriePoint++;
                    }
                    idxSerieRoute++;
                }

                BufferedReader in;
                //amount of the serie
                idxSerieRoute = 0;
                for (int amountRoute = App3rdGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App3rdGenerationRoute.TOTAL_ROUTE; amountRoute += App3rdGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App3rdGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App3rdGenerationRoute.TOTAL_POINT; amountPoint += App3rdGenerationRoute.INC_POINT_INITIAL) {
                        in = new BufferedReader(new FileReader("resources/route" + amountRoute + "point" + amountPoint + ".txt"));
                        //amount of the route
//                        for (int i = 0; i < amountRoute; i++) {
                        long start = System.nanoTime();
                        StringTokenizer tok = new StringTokenizer(in.readLine(), "\t");
                        //clean route
                        route.clear();
                        //add route
                        for (int j = 0; j < amountPoint; j++) {
                            p = new PointOnePonto();
                            p.setX(Integer.parseInt(tok.nextToken()));
                            p.setY(Integer.parseInt(tok.nextToken()));
                            route.add(p);
                        }
                        //
                        seqInternal[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                        Collection<PointOnePonto> r1 = seqInternal[idxSerieRoute][idxSeriePoint].solve();
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
                for (int amountRoute = App3rdGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App3rdGenerationRoute.TOTAL_ROUTE; amountRoute += App3rdGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App3rdGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App3rdGenerationRoute.TOTAL_POINT; amountPoint += App3rdGenerationRoute.INC_POINT_INITIAL) {
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
                for (int amountRoute = App3rdGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App3rdGenerationRoute.TOTAL_ROUTE; amountRoute += App3rdGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App3rdGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App3rdGenerationRoute.TOTAL_POINT; amountPoint += App3rdGenerationRoute.INC_POINT_INITIAL) {
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
                for (int amountRoute = App3rdGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App3rdGenerationRoute.TOTAL_ROUTE; amountRoute += App3rdGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App3rdGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App3rdGenerationRoute.TOTAL_POINT; amountPoint += App3rdGenerationRoute.INC_POINT_INITIAL) {
                        System.out.format(
                                "%d%c%d%c%.2f%n",
                                amountRoute, '\t', amountPoint, '\t',
                                seqInternal[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredDiskAccess());
                        idxSeriePoint++;
                    }
                    idxSerieRoute++;
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(App6thJoinSequential.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(App6thJoinSequential.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Sequential file not exists");
        }
    }
}
