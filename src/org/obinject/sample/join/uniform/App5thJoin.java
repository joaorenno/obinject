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
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.device.File;
import org.obinject.queries.ChanneledJoinRTree;
import org.obinject.queries.ChanneledJoinMTreeMinimumQualification;
import org.obinject.queries.ChanneledJoinMTreeFirstQualification;
import org.obinject.queries.ChanneledJoinMTreeFirstQualificationPositionRoute;
import org.obinject.queries.ChanneledJoinMTreeMinimumQualificationPositionRoute;
import org.obinject.sample.join.uniform.App3rdGenerationRoute;
import org.obinject.storage.MTree;
import org.obinject.storage.RTree;

/**
 *
 * @author windows
 */
public class App5thJoin {

    public static void main(String[] args) {
        if (new java.io.File("PointOnePonto.mtree").exists()) {
            //generation of the file route.txt
            try {

                int idxSerieRoute = 0;
                int idxSeriePoint;
                File f1 = new File("PointOnePonto.mtree");
                MTree<PointOnePonto> mtree = new MTree<PointOnePonto>(f1) {
                };
                File f2 = new File("RectangleOnePonto.rtree");
                RTree<RectangleOnePonto> rtree = new RTree<RectangleOnePonto>(f2) {
                };
                ArrayList<PointOnePonto> route = new ArrayList<>();
                PointOnePonto p;

                ChanneledJoinMTreeMinimumQualification<PointOnePonto> minQuali[][]
                        = new ChanneledJoinMTreeMinimumQualification[App3rdGenerationRoute.AMOUNT_SERIE_ROUTE][App3rdGenerationRoute.AMOUNT_SERIE_POINT];
                ChanneledJoinMTreeFirstQualification<PointOnePonto> firstQuali[][]
                        = new ChanneledJoinMTreeFirstQualification[App3rdGenerationRoute.AMOUNT_SERIE_ROUTE][App3rdGenerationRoute.AMOUNT_SERIE_POINT];
                ChanneledJoinMTreeMinimumQualificationPositionRoute<PointOnePonto> minQualiPosition[][]
                        = new ChanneledJoinMTreeMinimumQualificationPositionRoute[App3rdGenerationRoute.AMOUNT_SERIE_ROUTE][App3rdGenerationRoute.AMOUNT_SERIE_POINT];
                ChanneledJoinMTreeFirstQualificationPositionRoute<PointOnePonto> firstQualiPosition[][]
                        = new ChanneledJoinMTreeFirstQualificationPositionRoute[App3rdGenerationRoute.AMOUNT_SERIE_ROUTE][App3rdGenerationRoute.AMOUNT_SERIE_POINT];
                ChanneledJoinRTree<RectangleOnePonto> rtreeQuali[][]
                        = new ChanneledJoinRTree[App3rdGenerationRoute.AMOUNT_SERIE_ROUTE][App3rdGenerationRoute.AMOUNT_SERIE_POINT];

                for (int amountRoute = App3rdGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App3rdGenerationRoute.TOTAL_ROUTE; amountRoute += App3rdGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App3rdGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App3rdGenerationRoute.TOTAL_POINT; amountPoint += App3rdGenerationRoute.INC_POINT_INITIAL) {
                        minQuali[idxSerieRoute][idxSeriePoint] = new ChanneledJoinMTreeMinimumQualification<PointOnePonto>(mtree, route, 1.0d) {
                        };
                        firstQuali[idxSerieRoute][idxSeriePoint] = new ChanneledJoinMTreeFirstQualification<PointOnePonto>(mtree, route, 1.0d) {
                        };
                        minQualiPosition[idxSerieRoute][idxSeriePoint] = new ChanneledJoinMTreeMinimumQualificationPositionRoute<PointOnePonto>(mtree, route, 1.0d) {
                        };
                        firstQualiPosition[idxSerieRoute][idxSeriePoint] = new ChanneledJoinMTreeFirstQualificationPositionRoute<PointOnePonto>(mtree, route, 1.0d) {
                        };
                        rtreeQuali[idxSerieRoute][idxSeriePoint] = new ChanneledJoinRTree<RectangleOnePonto>(rtree, route, 1.0d) {
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
                        for (int i = 0; i < amountRoute; i++) {
//                            long start = System.nanoTime();
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

                            minQuali[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<PointOnePonto> r1 = minQuali[idxSerieRoute][idxSeriePoint].solve();

                            firstQuali[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<PointOnePonto> r2 = firstQuali[idxSerieRoute][idxSeriePoint].solve();

                            minQualiPosition[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<PointOnePonto> r3 = minQualiPosition[idxSerieRoute][idxSeriePoint].solve();

                            firstQualiPosition[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<PointOnePonto> r4 = firstQualiPosition[idxSerieRoute][idxSeriePoint].solve();

                            rtreeQuali[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<RectangleOnePonto> r5 = rtreeQuali[idxSerieRoute][idxSeriePoint].solve();

//                            long end = System.nanoTime();
//                            System.out.println("serie=" + idxSeriePoint + " join=" + i + " millis=" + (end - start));
                        }
                        in.close();
                        idxSeriePoint++;
                    }
                    idxSerieRoute++;
                }
                System.out.println("TIME");
                System.out.println("route\tpoint\tminQuali\tfirstQuali\tminQualiPos\tfirstQualiPos\trtreeQuali");
                idxSerieRoute = 0;
                for (int amountRoute = App3rdGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App3rdGenerationRoute.TOTAL_ROUTE; amountRoute += App3rdGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App3rdGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App3rdGenerationRoute.TOTAL_POINT; amountPoint += App3rdGenerationRoute.INC_POINT_INITIAL) {
                        System.out.format(
                                "%d%c%d%c%.2f%c%.2f%c%.2f%c%.2f%c%.2f%n",
                                amountRoute, '\t', amountPoint, '\t',
                                minQuali[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredTime(), '\t',
                                firstQuali[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredTime(), '\t',
                                minQualiPosition[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredTime(), '\t',
                                firstQualiPosition[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredTime(), '\t',
                                rtreeQuali[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredTime());
                        idxSeriePoint++;
                    }
                    idxSerieRoute++;
                }
                System.out.println("VERIFICATION");
                System.out.println("route\tpoint\tminQuali\tfirstQuali\tminQualiPos\tfirstQualiPos\trtreeQuali");
                idxSerieRoute = 0;
                for (int amountRoute = App3rdGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App3rdGenerationRoute.TOTAL_ROUTE; amountRoute += App3rdGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App3rdGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App3rdGenerationRoute.TOTAL_POINT; amountPoint += App3rdGenerationRoute.INC_POINT_INITIAL) {
                        System.out.format(
                                "%d%c%d%c%.2f%c%.2f%c%.2f%c%.2f%c%.2f%n",
                                amountRoute, '\t', amountPoint, '\t',
                                minQuali[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredVerifications(), '\t',
                                firstQuali[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredVerifications(), '\t',
                                minQualiPosition[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredVerifications(), '\t',
                                firstQualiPosition[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredVerifications(), '\t',
                                rtreeQuali[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredVerifications());
                        idxSeriePoint++;
                    }
                    idxSerieRoute++;
                }
                System.out.println("DISK ACESS");
                System.out.println("route\tpoint\tminQuali\tfirstQuali\tminQualiPos\tfirstQualiPos\trtreeQuali");
                idxSerieRoute = 0;
                for (int amountRoute = App3rdGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App3rdGenerationRoute.TOTAL_ROUTE; amountRoute += App3rdGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App3rdGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App3rdGenerationRoute.TOTAL_POINT; amountPoint += App3rdGenerationRoute.INC_POINT_INITIAL) {
                        System.out.format(
                                "%d%c%d%c%.2f%c%.2f%c%.2f%c%.2f%c%.2f%n",
                                amountRoute, '\t', amountPoint, '\t',
                                minQuali[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredDiskAccess(), '\t',
                                firstQuali[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredDiskAccess(), '\t',
                                minQualiPosition[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredDiskAccess(), '\t',
                                firstQualiPosition[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredDiskAccess(), '\t',
                                rtreeQuali[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredDiskAccess());
                        idxSeriePoint++;
                    }
                    idxSerieRoute++;
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(App5thJoin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(App5thJoin.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("MTree file not exists");
        }
    }
}
