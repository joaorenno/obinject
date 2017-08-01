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
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.device.File;
import org.obinject.meta.Metric;
import org.obinject.queries.ChanneledJoinRTree;
import org.obinject.queries.ChanneledJoinMTreeMinimumQualification;
import org.obinject.queries.ChanneledJoinMTreeFirstQualification;
import org.obinject.queries.ChanneledJoinMTreeFirstQualificationPositionRoute;
import org.obinject.queries.ChanneledJoinMTreeMinimumQualificationPositionRoute;
import org.obinject.queries.ChanneledJoinSequential;
import org.obinject.storage.MTree;
import org.obinject.storage.RTree;
import org.obinject.storage.Sequential;

/**
 *
 * @author windows
 */
public class App8thCheckJoin {

    public static void main(String[] args) {
        if (new java.io.File("PointOnePostCode.mtree").exists()) {
            //generation of the file route.txt
            try {
                int idxSerieRoute = 0;
                int idxSeriePoint;
                File f1 = new File("PointOnePostCode.mtree");
                MTree<PointOnePostCode> mtree = new MTree<PointOnePostCode>(f1) {
                };
                File f2 = new File("$PostCode.seq");
                Sequential<$PostCode> seq = new Sequential<$PostCode>(f2) {
                };
                File f3 = new File("RectangleOnePostCode.rtree");
                RTree<RectangleOnePostCode> rtree = new RTree<RectangleOnePostCode>(f3) {
                };

                List<PointOnePostCode> route = new ArrayList<>();
                PointOnePostCode p;

                ChanneledJoinMTreeMinimumQualification<PointOnePostCode> minQuali[][]
                        = new ChanneledJoinMTreeMinimumQualification[App5thGenerationRoute.AMOUNT_SERIE_ROUTE][App5thGenerationRoute.AMOUNT_SERIE_POINT];
                ChanneledJoinMTreeFirstQualification<PointOnePostCode> firstQuali[][]
                        = new ChanneledJoinMTreeFirstQualification[App5thGenerationRoute.AMOUNT_SERIE_ROUTE][App5thGenerationRoute.AMOUNT_SERIE_POINT];
                ChanneledJoinSequential<PointOnePostCode> seqInternal[][]
                        = new ChanneledJoinSequential[App5thGenerationRoute.AMOUNT_SERIE_ROUTE][App5thGenerationRoute.AMOUNT_SERIE_POINT];
                ChanneledJoinMTreeMinimumQualificationPositionRoute<PointOnePostCode> minQualiPosition[][]
                        = new ChanneledJoinMTreeMinimumQualificationPositionRoute[App5thGenerationRoute.AMOUNT_SERIE_ROUTE][App5thGenerationRoute.AMOUNT_SERIE_POINT];
                ChanneledJoinMTreeFirstQualificationPositionRoute<PointOnePostCode> firstQualiPosition[][]
                        = new ChanneledJoinMTreeFirstQualificationPositionRoute[App5thGenerationRoute.AMOUNT_SERIE_ROUTE][App5thGenerationRoute.AMOUNT_SERIE_POINT];
                ChanneledJoinRTree<RectangleOnePostCode> rtreeQuali[][]
                        = new ChanneledJoinRTree[App5thGenerationRoute.AMOUNT_SERIE_ROUTE][App5thGenerationRoute.AMOUNT_SERIE_POINT];

                for (int amountRoute = App5thGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App5thGenerationRoute.TOTAL_ROUTE; amountRoute += App5thGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App5thGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App5thGenerationRoute.TOTAL_POINT; amountPoint += App5thGenerationRoute.INC_POINT_INITIAL) {
                        seqInternal[idxSerieRoute][idxSeriePoint] = new ChanneledJoinSequential<PointOnePostCode>(seq, route, App6thJoin.RANGE_CHANNEL) {
                        };
                        minQuali[idxSerieRoute][idxSeriePoint] = new ChanneledJoinMTreeMinimumQualification<PointOnePostCode>(mtree, route, App6thJoin.RANGE_CHANNEL) {
                        };
                        firstQuali[idxSerieRoute][idxSeriePoint] = new ChanneledJoinMTreeFirstQualification<PointOnePostCode>(mtree, route, App6thJoin.RANGE_CHANNEL) {
                        };
                        minQualiPosition[idxSerieRoute][idxSeriePoint] = new ChanneledJoinMTreeMinimumQualificationPositionRoute<PointOnePostCode>(mtree, route, App6thJoin.RANGE_CHANNEL) {
                        };
                        firstQualiPosition[idxSerieRoute][idxSeriePoint] = new ChanneledJoinMTreeFirstQualificationPositionRoute<PointOnePostCode>(mtree, route, App6thJoin.RANGE_CHANNEL) {
                        };
                        rtreeQuali[idxSerieRoute][idxSeriePoint] = new ChanneledJoinRTree<RectangleOnePostCode>(rtree, route, App6thJoin.RANGE_CHANNEL) {
                        };
                        idxSeriePoint++;
                    }
                    idxSerieRoute++;
                }
                BufferedReader in;
                //amount of the serie
                idxSerieRoute = 0;
                for (int amountRoute = App5thGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App5thGenerationRoute.TOTAL_ROUTE; amountRoute += App5thGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App5thGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App5thGenerationRoute.TOTAL_POINT; amountPoint += App5thGenerationRoute.INC_POINT_INITIAL) {
                        in = new BufferedReader(new FileReader("resources/route" + amountRoute + "point" + amountPoint + ".txt"));
                        //amount of the route
                        for (int i = 0; i < amountRoute; i++) {
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
                            // AQUI NAO TINHA QUE FAZER O PROCESSAMENTO DA SEQUENCIAL?
                            
                            firstQuali[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<PointOnePostCode> r2 = firstQuali[idxSerieRoute][idxSeriePoint].solve();
                            boolean equal;
                            if (r2.size() != r1.size()) {
                                System.out.println("First Qualification failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r2.size() + " route=" + route.size());
                            }
                            for (Iterator<PointOnePostCode> it = r1.iterator(); it.hasNext();) {
                                PointOnePostCode pr1 = it.next();
                                equal = false;
                                for (PointOnePostCode pr2 : r2) {
                                    if (pr1.hasSameKey(pr2)) {
                                        equal = true;
                                        break;
                                    }
                                }
                                if (!equal) {
                                    System.out.println("First Qualification failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                            + " Join=" + r2.size() + " route=" + route.size());
                                    while (it.hasNext()) {
                                        System.out.println("error First Qualification  p.x=" + pr1.getLatitude() + " p.y=" + pr1.getLongitude());
                                        pr1 = it.next();
                                    }
                                }
                            }

                            minQuali[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<PointOnePostCode> r3 = minQuali[idxSerieRoute][idxSeriePoint].solve();
                            if (r3.size() != r1.size()) {
                                System.out.println("Minimum Qualification failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r3.size() + " route=" + route.size());
                            }
                            for (Iterator<PointOnePostCode> it = r1.iterator(); it.hasNext();) {
                                PointOnePostCode pr1 = it.next();
                                equal = false;
                                for (PointOnePostCode pr3 : r3) {
                                    if (pr1.hasSameKey(pr3)) {
                                        equal = true;
                                        break;
                                    }
                                }
                                if (!equal) {
                                    System.out.println("Minimum Qualification failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                            + " Join=" + r3.size() + " route=" + route.size());
                                    while (it.hasNext()) {
                                        System.out.println("error Minimum Qualification  p.x=" + pr1.getLatitude() + " p.y=" + pr1.getLongitude());
                                        pr1 = it.next();
                                    }
                                }
                            }

                            firstQualiPosition[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<PointOnePostCode> r4 = firstQualiPosition[idxSerieRoute][idxSeriePoint].solve();
                            if (r4.size() != r1.size()) {
                                System.out.println("First Qualification Position Route failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r4.size() + " route=" + route.size());
                            }
                            for (Iterator<PointOnePostCode> it = r1.iterator(); it.hasNext();) {
                                PointOnePostCode pr1 = it.next();
                                equal = false;
                                for (PointOnePostCode pr4 : r4) {
                                    if (pr1.hasSameKey(pr4)) {
                                        equal = true;
                                        break;
                                    }
                                }
                                if (!equal) {
                                    System.out.println("First Qualification Position Route failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                            + " res=" + r4.size() + " route=" + route.size());
                                    while (it.hasNext()) {
                                        System.out.println("error First Qualification Position Route p.x=" + pr1.getLatitude() + " p.y=" + pr1.getLongitude());
                                        pr1 = it.next();
                                    }
                                }
                            }

                            minQualiPosition[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<PointOnePostCode> r5 = minQualiPosition[idxSerieRoute][idxSeriePoint].solve();
                            if (r5.size() != r1.size()) {
                                System.out.println("Minimum Qualification Position Route failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r5.size() + " route=" + route.size());
                            }
                            for (Iterator<PointOnePostCode> it = r1.iterator(); it.hasNext();) {
                                PointOnePostCode pr1 = it.next();
                                equal = false;
                                for (PointOnePostCode pr5 : r5) {
                                    if (pr1.hasSameKey(pr5)) {
                                        equal = true;
                                        break;
                                    }
                                }
                                if (!equal) {
                                    System.out.println("Minimum Qualification Position Route failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                            + " res=" + r5.size() + " route=" + route.size());
                                    while (it.hasNext()) {
                                        System.out.println("error Minimum Qualification Position Route p.x=" + pr1.getLatitude() + " p.y=" + pr1.getLongitude());
                                        pr1 = it.next();
                                    }
                                }
                            }

                            // AQUI ESTA DANDO ERRO! NAO ESTA SIMULANDO NA RTREE
                            rtreeQuali[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<RectangleOnePostCode> r6 = rtreeQuali[idxSerieRoute][idxSeriePoint].solve();
                            if (r6.size() != r1.size()) {
                                System.out.println("RTree Qualification failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r6.size() + " route=" + route.size());
                            }
                            for (Iterator<PointOnePostCode> it = r1.iterator(); it.hasNext();) {
                                Metric pr1 = it.next();
                                equal = false;
                                for (Metric pr6 : r6) {
                                    if (pr1.hasSameKey(pr6)) {
                                        equal = true;
                                        break;
                                    }
                                }
                                if (!equal) {
                                    System.out.println("RTree Qualification failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                            + " res=" + r6.size() + " route=" + route.size());
                                    while (it.hasNext()) {
                                        System.out.println("error Minimum Qualification Position Route p.x=" + ((PointOnePostCode)pr1).getLatitude() + " p.y=" + ((PointOnePostCode)pr1).getLongitude());
                                        pr1 = it.next();
                                    }
                                }
                            }

                            long end = System.nanoTime();
                            System.out.println("serie=" + idxSeriePoint + " join=" + i + " millis=" + (end - start) + " res=" + r5.size()
                                    + " route=" + route.size());
                        }
                        in.close();
                        idxSeriePoint++;
                    }
                    idxSerieRoute++;
                }
                System.out.println("TIME");
                System.out.println("route\tpoint\tminQuali\tfirstQuali\tminQualiPos\tfirstQualiPos\trtreeQuali");
                idxSerieRoute = 0;
                for (int amountRoute = App5thGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App5thGenerationRoute.TOTAL_ROUTE; amountRoute += App5thGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App5thGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App5thGenerationRoute.TOTAL_POINT; amountPoint += App5thGenerationRoute.INC_POINT_INITIAL) {
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
                for (int amountRoute = App5thGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App5thGenerationRoute.TOTAL_ROUTE; amountRoute += App5thGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App5thGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App5thGenerationRoute.TOTAL_POINT; amountPoint += App5thGenerationRoute.INC_POINT_INITIAL) {
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
                for (int amountRoute = App5thGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App5thGenerationRoute.TOTAL_ROUTE; amountRoute += App5thGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App5thGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App5thGenerationRoute.TOTAL_POINT; amountPoint += App5thGenerationRoute.INC_POINT_INITIAL) {
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
                Logger.getLogger(App8thCheckJoin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(App8thCheckJoin.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("MTree file not exists");
        }
    }
}
