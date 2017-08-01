package org.obinject.sample.join.multiview;

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
import org.obinject.meta.Point;
import org.obinject.meta.Rectangle;
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
public class App5thCheckJoin {

    public static void main(String[] args) {
        if (new java.io.File("PointOneObjectImage.mtree").exists()) {
            //generation of the file route.txt
            try {
                String bufObj;
                String bufImg;
                int amountPoint;
                double rangeChannel;
                File f1 = new File("PointOneObjectImage.mtree");
                MTree<PointOneObjectImage> mtree = new MTree<PointOneObjectImage>(f1) {
                };
                File f2 = new File("$ObjectImage.seq");
                Sequential<$ObjectImage> seq = new Sequential<$ObjectImage>(f2) {
                };
                File f3 = new File("RectangleOneObjectImage.rtree");
                RTree<RectangleOneObjectImage> rtree = new RTree<RectangleOneObjectImage>(f3) {
                };

                List<PointOneObjectImage> route = new ArrayList<>();
                PointOneObjectImage p;

                ChanneledJoinMTreeMinimumQualification<PointOneObjectImage> minQuali[]
                        = new ChanneledJoinMTreeMinimumQualification[App2ndGenerationRoute.TOTAL_ROUTE];
                ChanneledJoinMTreeFirstQualification<PointOneObjectImage> firstQuali[]
                        = new ChanneledJoinMTreeFirstQualification[App2ndGenerationRoute.TOTAL_ROUTE];
                ChanneledJoinSequential<PointOneObjectImage> seqInternal[]
                        = new ChanneledJoinSequential[App2ndGenerationRoute.TOTAL_ROUTE];
                ChanneledJoinMTreeMinimumQualificationPositionRoute<PointOneObjectImage> minQualiPosition[]
                        = new ChanneledJoinMTreeMinimumQualificationPositionRoute[App2ndGenerationRoute.TOTAL_ROUTE];
                ChanneledJoinMTreeFirstQualificationPositionRoute<PointOneObjectImage> firstQualiPosition[]
                        = new ChanneledJoinMTreeFirstQualificationPositionRoute[App2ndGenerationRoute.TOTAL_ROUTE];
                ChanneledJoinRTree<RectangleOneObjectImage> rtreeQuali[]
                        = new ChanneledJoinRTree[App2ndGenerationRoute.TOTAL_ROUTE];

                BufferedReader in;
                for (int amountRoute = App2ndGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App2ndGenerationRoute.TOTAL_ROUTE; amountRoute += App2ndGenerationRoute.INC_ROUTE_INITIAL) {
                    in = new BufferedReader(new FileReader("resources/route" + amountRoute + "point56.txt"));

                    //amount of the route
                    for (int idxSerieRoute = 0; idxSerieRoute < amountRoute; idxSerieRoute++) {

                        StringTokenizer tok = new StringTokenizer(in.readLine(), "\t");
                        amountPoint = Integer.parseInt(tok.nextToken());
                        rangeChannel = Double.parseDouble(tok.nextToken());
                        long start = System.nanoTime();
                        //clean route
                        route.clear();
                        //add route
                        for (int j = 0; j < amountPoint; j++) {
                            p = new PointOneObjectImage();
                            bufObj = tok.nextToken();
                            bufObj = bufObj.substring(1, bufObj.length() - 1);
                            p.setPathObject(bufObj);
                            bufImg = tok.nextToken();
                            bufImg = bufImg.substring(1, bufImg.length() - 1);
                            p.setPathImage(bufImg);

                            for (int k = 0; k < 13; k++) {
                                p.getFeature()[k] = Double.parseDouble(tok.nextToken());
                            }
                            route.add(p);
                        }
//                        seqInternal[idxSerieRoute] = new ChanneledJoinSequential<PointOneObjectImage>(seq, route, rangeChannel) {
//                        };
//                        //
//                        seqInternal[idxSerieRoute].setRouteOfPoint(route);
//                        Collection<PointOneObjectImage> r1 = seqInternal[idxSerieRoute].solve();

                        firstQuali[idxSerieRoute] = new ChanneledJoinMTreeFirstQualification<PointOneObjectImage>(mtree, route, rangeChannel) {
                        };
                        firstQuali[idxSerieRoute].setRouteOfPoint(route);
                        Collection<PointOneObjectImage> r2 = firstQuali[idxSerieRoute].solve();
                        boolean equal;
                        if (r2.size() != r2.size()) {
                            System.out.println("First Qualification failed i=" + idxSerieRoute + ". Expected=" + ((amountPoint * 3) + 2)
                                    + " res=" + r2.size() + " route=" + route.size());
                        }
                        for (Iterator<PointOneObjectImage> it = r2.iterator(); it.hasNext();) {
                            PointOneObjectImage pr1 = it.next();
                            equal = false;
                            for (PointOneObjectImage pr2 : r2) {
                                if (pr1.hasSameKey(pr2)) {
                                    equal = true;
                                    break;
                                }
                            }
                            if (!equal) {
                                System.out.println("First Qualification failed i=" + idxSerieRoute + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " Join=" + r2.size() + " route=" + route.size());
                                while (it.hasNext()) {
                                    System.out.println("error First Qualification p.image=" + pr1.getPathImage());
                                    pr1 = it.next();
                                }
                            }
                        }

                        minQuali[idxSerieRoute] = new ChanneledJoinMTreeMinimumQualification<PointOneObjectImage>(mtree, route, rangeChannel) {
                        };
                        minQuali[idxSerieRoute].setRouteOfPoint(route);
                        Collection<PointOneObjectImage> r3 = minQuali[idxSerieRoute].solve();
                        if (r3.size() != r2.size()) {
                            System.out.println("Minimum Qualification failed i=" + idxSerieRoute + ". Expected=" + ((amountPoint * 3) + 2)
                                    + " res=" + r3.size() + " route=" + route.size());
                        }
                        for (Iterator<PointOneObjectImage> it = r2.iterator(); it.hasNext();) {
                            PointOneObjectImage pr1 = it.next();
                            equal = false;
                            for (PointOneObjectImage pr3 : r3) {
                                if (pr1.hasSameKey(pr3)) {
                                    equal = true;
                                    break;
                                }
                            }
                            if (!equal) {
                                System.out.println("Minimum Qualification failed i=" + idxSerieRoute + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " Join=" + r3.size() + " route=" + route.size());
                                while (it.hasNext()) {
                                    System.out.println("error Minimum Qualification p.image=" + pr1.getPathImage());
                                    pr1 = it.next();
                                }
                            }
                        }

                        firstQualiPosition[idxSerieRoute] = new ChanneledJoinMTreeFirstQualificationPositionRoute<PointOneObjectImage>(mtree, route, rangeChannel) {
                        };
                        firstQualiPosition[idxSerieRoute].setRouteOfPoint(route);
                        Collection<PointOneObjectImage> r4 = firstQualiPosition[idxSerieRoute].solve();
                        if (r4.size() != r2.size()) {
                            System.out.println("First Qualification Position Route failed i=" + idxSerieRoute + ". Expected=" + ((amountPoint * 3) + 2)
                                    + " res=" + r4.size() + " route=" + route.size());
                        }
                        for (Iterator<PointOneObjectImage> it = r2.iterator(); it.hasNext();) {
                            PointOneObjectImage pr1 = it.next();
                            equal = false;
                            for (PointOneObjectImage pr4 : r4) {
                                if (pr1.hasSameKey(pr4)) {
                                    equal = true;
                                    break;
                                }
                            }
                            if (!equal) {
                                System.out.println("First Qualification Position Route failed i=" + idxSerieRoute + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r4.size() + " route=" + route.size());
                                while (it.hasNext()) {
                                    System.out.println("error First Qualification Position Route p.image=" + pr1.getPathImage());
                                    pr1 = it.next();
                                }
                            }
                        }

                        minQualiPosition[idxSerieRoute] = new ChanneledJoinMTreeMinimumQualificationPositionRoute<PointOneObjectImage>(mtree, route, rangeChannel) {
                        };
                        minQualiPosition[idxSerieRoute].setRouteOfPoint(route);
                        Collection<PointOneObjectImage> r5 = minQualiPosition[idxSerieRoute].solve();
                        if (r5.size() != r2.size()) {
                            System.out.println("Minimum Qualification Position Route failed i=" + idxSerieRoute + ". Expected=" + ((amountPoint * 3) + 2)
                                    + " res=" + r5.size() + " route=" + route.size());
                        }
                        for (Iterator<PointOneObjectImage> it = r2.iterator(); it.hasNext();) {
                            PointOneObjectImage pr1 = it.next();
                            equal = false;
                            for (PointOneObjectImage pr5 : r5) {
                                if (pr1.hasSameKey(pr5)) {
                                    equal = true;
                                    break;
                                }
                            }
                            if (!equal) {
                                System.out.println("Minimum Qualification Position Route failed i=" + idxSerieRoute + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r5.size() + " route=" + route.size());
                                while (it.hasNext()) {
                                    System.out.println("error Minimum Qualification Position Route p.image=" + pr1.getPathImage());
                                    pr1 = it.next();
                                }
                            }
                        }

                        rtreeQuali[idxSerieRoute] = new ChanneledJoinRTree<RectangleOneObjectImage>(rtree, route, rangeChannel) {
                        };
                        
                        rtreeQuali[idxSerieRoute].setRouteOfPoint(route);
                        Collection<RectangleOneObjectImage> r7 = rtreeQuali[idxSerieRoute].solve();
                        if (r7.size() != r2.size()) {
                            System.out.println("RTree Qualification failed i=" + idxSerieRoute + ". Expected=" + ((amountPoint * 3) + 2)
                                    + " res=" + r7.size() + " route=" + route.size());
                        }
                        for (Iterator<PointOneObjectImage> it = r2.iterator(); it.hasNext();) {
                            Point pr1 = it.next();
                            equal = false;
                            for (Rectangle pr7 : r7) {
                                int idxAxis = 0;
                                while (idxAxis < pr7.numberOfDimensions()
                                        && pr1.getOrigin(idxAxis) == pr7.getOrigin(idxAxis)) {
                                    idxAxis++;
                                }
                                if (idxAxis == pr7.numberOfDimensions()) {
                                    equal = true;
                                    break;
                                }
                            }
                            if (!equal) {
                                System.out.println("RTree Qualification failed i=" + idxSerieRoute + ". Expected=" + ((amountPoint * 3) + 2)
                                        + " res=" + r7.size() + " route=" + route.size());
                                while (it.hasNext()) {
//                                    System.out.println("error Minimum Qualification Position Route p.image=" + pr1.getPathImage());
                                    System.out.println("error Minimum Qualification Position Route p.image=");
                                    pr1 = it.next();
                                }
                            }
                        }

                        long end = System.nanoTime();
                        System.out.println("serie=" + idxSerieRoute + " join=" + idxSerieRoute + " millis=" + (end - start) + " res=" + r7.size()
                                + " route=" + route.size());
                    }
                    in.close();
                }
                System.out.println("TIME");
                System.out.println("route\tpoint\tminQuali\tfirstQuali\tminQualiPos\tfirstQualiPos\trtreeQuali");
                for (int amountRoute = App2ndGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App2ndGenerationRoute.TOTAL_ROUTE; amountRoute += App2ndGenerationRoute.INC_ROUTE_INITIAL) {
                    for (int idxSerieRoute = 0; idxSerieRoute < amountRoute; idxSerieRoute++) {
                        System.out.format(
                                "%d%c%s%c%.2f%c%.2f%c%.2f%c%.2f%c%.2f%n",
                                amountRoute, '\t', "56", '\t',
                                minQuali[idxSerieRoute].getPerformanceMeasurement().measuredTime(), '\t',
                                firstQuali[idxSerieRoute].getPerformanceMeasurement().measuredTime(), '\t',
                                minQualiPosition[idxSerieRoute].getPerformanceMeasurement().measuredTime(), '\t',
                                firstQualiPosition[idxSerieRoute].getPerformanceMeasurement().measuredTime(), '\t',
                                rtreeQuali[idxSerieRoute].getPerformanceMeasurement().measuredTime());
                    }
                }

                System.out.println("VERIFICATION");
                System.out.println("route\tpoint\tminQuali\tfirstQuali\tminQualiPos\tfirstQualiPos\trtreeQuali");
                for (int amountRoute = App2ndGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App2ndGenerationRoute.TOTAL_ROUTE; amountRoute += App2ndGenerationRoute.INC_ROUTE_INITIAL) {
                    for (int idxSerieRoute = 0; idxSerieRoute < amountRoute; idxSerieRoute++) {
                        System.out.format(
                                "%d%c%s%c%.2f%c%.2f%c%.2f%c%.2f%c%.2f%n",
                                amountRoute, '\t', "56", '\t',
                                minQuali[idxSerieRoute].getPerformanceMeasurement().measuredVerifications(), '\t',
                                firstQuali[idxSerieRoute].getPerformanceMeasurement().measuredVerifications(), '\t',
                                minQualiPosition[idxSerieRoute].getPerformanceMeasurement().measuredVerifications(), '\t',
                                firstQualiPosition[idxSerieRoute].getPerformanceMeasurement().measuredVerifications(), '\t',
                                rtreeQuali[idxSerieRoute].getPerformanceMeasurement().measuredVerifications());
                    }
                }
                System.out.println("DISK ACESS");
                System.out.println("route\tpoint\tminQuali\tfirstQuali\tminQualiPos\tfirstQualiPos\trtreeQuali");
                for (int amountRoute = App2ndGenerationRoute.AMOUNT_ROUTE_INITIAL; amountRoute <= App2ndGenerationRoute.TOTAL_ROUTE; amountRoute += App2ndGenerationRoute.INC_ROUTE_INITIAL) {
                    for (int idxSerieRoute = 0; idxSerieRoute < amountRoute; idxSerieRoute++) {
                        System.out.format(
                                "%d%c%s%c%.2f%c%.2f%c%.2f%c%.2f%c%.2f%n",
                                amountRoute, '\t', "56", '\t',
                                minQuali[idxSerieRoute].getPerformanceMeasurement().measuredDiskAccess(), '\t',
                                firstQuali[idxSerieRoute].getPerformanceMeasurement().measuredDiskAccess(), '\t',
                                minQualiPosition[idxSerieRoute].getPerformanceMeasurement().measuredDiskAccess(), '\t',
                                firstQualiPosition[idxSerieRoute].getPerformanceMeasurement().measuredDiskAccess(), '\t',
                                rtreeQuali[idxSerieRoute].getPerformanceMeasurement().measuredDiskAccess());
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(App5thCheckJoin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(App5thCheckJoin.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("MTree file not exists");
        }
    }
}
