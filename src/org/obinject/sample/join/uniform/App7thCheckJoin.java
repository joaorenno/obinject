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
import java.util.Iterator;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.device.File;
import org.obinject.queries.ChanneledJoinRTree;
import org.obinject.queries.ChanneledJoinMTreeMinimumQualification;
import org.obinject.queries.ChanneledJoinMTreeFirstQualification;
import org.obinject.queries.ChanneledJoinMTreeFirstQualificationPositionRoute;
import org.obinject.queries.ChanneledJoinMTreeMinimumQualificationPositionRoute;
import org.obinject.queries.ChanneledJoinSequential;
import org.obinject.sample.join.uniform.App3rdGenerationRoute;
import org.obinject.storage.MTree;
import org.obinject.storage.RTree;
import org.obinject.storage.Sequential;

/**
 *
 * @author windows
 */
public class App7thCheckJoin {

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
                File f3 = new File("$Point.seq");
                Sequential<$Ponto> seq = new Sequential<$Ponto>(f3) {
                };
                ArrayList<PointOnePonto> route = new ArrayList<>();
                PointOnePonto p;

                ChanneledJoinSequential<PointOnePonto> seqInternal[][]
                        = new ChanneledJoinSequential[App3rdGenerationRoute.AMOUNT_SERIE_ROUTE][App3rdGenerationRoute.AMOUNT_SERIE_POINT];
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
                        seqInternal[idxSerieRoute][idxSeriePoint] = new ChanneledJoinSequential<PointOnePonto>(seq, route, 1.0d) {
                        };
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

                            seqInternal[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<PointOnePonto> r1 = seqInternal[idxSerieRoute][idxSeriePoint].solve();
                            int countInt = 0;
                            for (PointOnePonto pRes : r1) {
                                boolean found = false;
                                for (ListIterator<PointOnePonto> it = route.listIterator(); it.hasNext();) {
                                    PointOnePonto pRoute = it.next();
                                    if ((pRoute.getX() == pRes.getX() && pRoute.getY() == pRes.getY())
                                            || (pRoute.getX() + 1 == pRes.getX() && pRoute.getY() == pRes.getY())
                                            || (pRoute.getX() - 1 == pRes.getX() && pRoute.getY() == pRes.getY())
                                            || (pRoute.getX() == pRes.getX() && pRoute.getY() + 1 == pRes.getY())
                                            || (pRoute.getX() == pRes.getX() && pRoute.getY() - 1 == pRes.getY())
                                            || ((!it.hasPrevious() || !it.hasNext()) && pRoute.getX() + 1 == pRes.getX() && pRoute.getY() == pRes.getY())
                                            || ((!it.hasPrevious() || !it.hasNext()) && pRoute.getX() - 1 == pRes.getX() && pRoute.getY() == pRes.getY())
                                            || ((!it.hasPrevious() || !it.hasNext()) && pRoute.getX() == pRes.getX() && pRoute.getY() + 1 == pRes.getY())
                                            || ((!it.hasPrevious() || !it.hasNext()) && pRoute.getX() == pRes.getX() && pRoute.getY() - 1 == pRes.getY())) {
                                        countInt++;
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    System.out.println("not found x=" + pRes.getX() + " y=" + pRes.getY());
                                }
                            }
                            if ((countInt != (amountPoint * 3) + 2)
                                    || (r1.size() != (amountPoint * 3) + 2)) {
                                System.out.println("Join Sequential failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r1.size() + " route=" + route.size() + " count=" + countInt);
                            }

                            firstQuali[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<PointOnePonto> r2 = firstQuali[idxSerieRoute][idxSeriePoint].solve();
                            if (r2.size() != (amountPoint * 3) + 2) {
                                System.out.println("First Qualification failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r2.size() + " route=" + route.size());
                            }
                            boolean equal;
                            for (Iterator<PointOnePonto> it = r1.iterator(); it.hasNext();) {
                                PointOnePonto pr1 = it.next();
                                equal = false;
                                for (PointOnePonto pr2 : r2) {
                                    if (pr1.hasSameKey(pr2)) {
                                        equal = true;
                                        break;
                                    }
                                }
                                if (!equal) {
                                    System.out.println("First Qualification failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                            + " res=" + r2.size() + " route=" + route.size());
                                    while (it.hasNext()) {
                                        System.out.println("Error First Qualification p.x=" + pr1.getX() + " p.y=" + pr1.getY());
                                        pr1 = it.next();
                                    }
                                }
                            }

                            minQuali[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<PointOnePonto> r3 = minQuali[idxSerieRoute][idxSeriePoint].solve();
                            if (r3.size() != (amountPoint * 3) + 2) {
                                System.out.println("Minimum Qualification failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r3.size() + " route=" + route.size());
                            }
                            for (Iterator<PointOnePonto> it = r1.iterator(); it.hasNext();) {
                                PointOnePonto pr1 = it.next();
                                equal = false;
                                for (PointOnePonto pr3 : r3) {
                                    if (pr1.hasSameKey(pr3)) {
                                        equal = true;
                                        break;
                                    }
                                }
                                if (!equal) {
                                    System.out.println("Minimum Qualification failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                            + " res=" + r3.size() + " route=" + route.size());
                                    while (it.hasNext()) {
                                        System.out.println("error Minimum Qualification p.x=" + pr1.getX() + " p.y=" + pr1.getY());
                                        pr1 = it.next();
                                    }
                                }
                            }

                            firstQualiPosition[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<PointOnePonto> r4 = firstQualiPosition[idxSerieRoute][idxSeriePoint].solve();
                            if (r4.size() != (amountPoint * 3) + 2) {
                                System.out.println("First Qualification Position Route failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r4.size() + " route=" + route.size());
                            }
                            for (Iterator<PointOnePonto> it = r1.iterator(); it.hasNext();) {
                                PointOnePonto pr1 = it.next();
                                equal = false;
                                for (PointOnePonto pr4 : r4) {
                                    if (pr1.hasSameKey(pr4)) {
                                        equal = true;
                                        break;
                                    }
                                }
                                if (!equal) {
                                    System.out.println("First Qualification Position Route failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                            + " res=" + r4.size() + " route=" + route.size());
                                    while (it.hasNext()) {
                                        System.out.println("error First Qualification Position Route p.x=" + pr1.getX() + " p.y=" + pr1.getY());
                                        pr1 = it.next();
                                    }
                                }
                            }

                            minQualiPosition[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<PointOnePonto> r5 = minQualiPosition[idxSerieRoute][idxSeriePoint].solve();
                            if (r5.size() != (amountPoint * 3) + 2) {
                                System.out.println("Minimum Qualification Position Route failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r5.size() + " route=" + route.size());
                            }
                            for (Iterator<PointOnePonto> it = r1.iterator(); it.hasNext();) {
                                PointOnePonto pr1 = it.next();
                                equal = false;
                                for (PointOnePonto pr5 : r5) {
                                    if (pr1.hasSameKey(pr5)) {
                                        equal = true;
                                        break;
                                    }
                                }
                                if (!equal) {
                                    System.out.println("Minimum Qualification Position Route failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                            + " res=" + r5.size() + " route=" + route.size());
                                    while (it.hasNext()) {
                                        System.out.println("error Minimum Qualification Position Route p.x=" + pr1.getX() + " p.y=" + pr1.getY());
                                        pr1 = it.next();
                                    }
                                }
                            }

                            rtreeQuali[idxSerieRoute][idxSeriePoint].setRouteOfPoint(route);
                            Collection<RectangleOnePonto> r6 = rtreeQuali[idxSerieRoute][idxSeriePoint].solve();
                            if (r6.size() != (amountPoint * 3) + 2) {
                                System.out.println("RTree Qualification failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r6.size() + " route=" + route.size());
                            }
                            for (Iterator<PointOnePonto> it = r1.iterator(); it.hasNext();) {
                                PointOnePonto pr1 = it.next();
                                equal = false;
                                for (RectangleOnePonto pr6 : r6) {
                                    int j = 0;
                                    while ((j < pr1.numberOfDimensions())
                                            && (pr1.getOrigin(j) == pr6.getOrigin(j))) {
                                        j++;
                                    }
                                    equal = (j == pr1.numberOfDimensions());
                                    if (equal) {
                                        break;
                                    }
                                }
                                if (!equal) {
                                    System.out.println("RTree Qualification failed i=" + i + ". Expected=" + ((amountPoint * 3) + 2)
                                            + " res=" + r6.size() + " route=" + route.size());
                                    while (it.hasNext()) {
                                        System.out.println("error RTree Qualification p.x=" + pr1.getX() + " p.y=" + pr1.getY());
                                        pr1 = it.next();
                                    }
                                }
                            }

                            long end = System.nanoTime();
                            System.out.println("serie=" + idxSeriePoint + " join=" + i + " millis=" + (end - start) + " res=" + r6.size()
                                    + " route=" + route.size());
                        }
                        in.close();
                        idxSeriePoint++;
                    }
                    idxSerieRoute++;
                }
                System.out.println("TIME");
                System.out.println("route\tpoint\tsequential\tminQuali\tfirstQuali\tminQualiPos\tfirstQualiPos\trtreeQuali");
                idxSerieRoute = 0;
                for (int amountRoute = App3rdGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App3rdGenerationRoute.TOTAL_ROUTE; amountRoute += App3rdGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App3rdGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App3rdGenerationRoute.TOTAL_POINT; amountPoint += App3rdGenerationRoute.INC_POINT_INITIAL) {
                        System.out.format(
                                "%d%c%d%c%.2f%c%.2f%c%.2f%c%.2f%c%.2f%c%.2f%n",
                                amountRoute, '\t', amountPoint, '\t',
                                seqInternal[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredTime(), '\t',
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
                System.out.println("route\tpoint\tsequential\tminQuali\tfirstQuali\tminQualiPos\tfirstQualiPos\trtreeQuali");
                idxSerieRoute = 0;
                for (int amountRoute = App3rdGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App3rdGenerationRoute.TOTAL_ROUTE; amountRoute += App3rdGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App3rdGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App3rdGenerationRoute.TOTAL_POINT; amountPoint += App3rdGenerationRoute.INC_POINT_INITIAL) {
                        System.out.format(
                                "%d%c%d%c%.2f%c%.2f%c%.2f%c%.2f%c%.2f%c%.2f%n",
                                amountRoute, '\t', amountPoint, '\t',
                                seqInternal[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredVerifications(), '\t',
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
                System.out.println("route\tpoint\tsequential\tminQuali\tfirstQuali\tminQualiPos\tfirstQualiPos\trtreeQuali");
                idxSerieRoute = 0;
                for (int amountRoute = App3rdGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App3rdGenerationRoute.TOTAL_ROUTE; amountRoute += App3rdGenerationRoute.INC_ROUTE_INITIAL) {
                    idxSeriePoint = 0;
                    for (int amountPoint = App3rdGenerationRoute.AMOUNT_POINT_INITIAL; amountPoint <= App3rdGenerationRoute.TOTAL_POINT; amountPoint += App3rdGenerationRoute.INC_POINT_INITIAL) {
                        System.out.format(
                                "%d%c%d%c%.2f%c%.2f%c%.2f%c%.2f%c%.2f%c%.2f%n",
                                amountRoute, '\t', amountPoint, '\t',
                                seqInternal[idxSerieRoute][idxSeriePoint].getPerformanceMeasurement().measuredDiskAccess(), '\t',
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
                Logger.getLogger(App7thCheckJoin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(App7thCheckJoin.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("MTree file not exists");
        }
    }
}
