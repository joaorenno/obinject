package org.obinject.sample.join.multiview;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App2ndGenerationRoute {

    public static final int AMOUNT_SERIE_ROUTE = 1;
    public static final int AMOUNT_ROUTE_INITIAL = 5;
    public static final int INC_ROUTE_INITIAL = 10;
    public static final int TOTAL_ROUTE = AMOUNT_ROUTE_INITIAL + (AMOUNT_SERIE_ROUTE * INC_ROUTE_INITIAL) - INC_ROUTE_INITIAL;
    public static final Random RANDOM_INSERT = new Random(100); // SAME SORTED ORDER IN BASE

    static class ObjectImageComparator implements Comparator<PointOneObjectImage> {

        @Override
        public int compare(PointOneObjectImage o1, PointOneObjectImage o2) {
            String str1 = "" + o1.getPathObject() + o1.getPathImage();
            String str2 = "" + o2.getPathObject() + o2.getPathImage();
            return str1.compareTo(str2);
        }
    };

    public static void main(String[] args) {
        TreeSet<PointOneObjectImage> set = new TreeSet<>(new ObjectImageComparator());
        SortedSet<PointOneObjectImage> subSet;
        Iterator<PointOneObjectImage> it;
        StringBuilder strBuild;
        //generation of the file route.txt
        try {
            int count;
            StringTokenizer tok;
            String bufObj;
            String bufImg;
            String line;
            String nameObject;
            PointOneObjectImage p, first, second;
            boolean choose = true;
            BufferedReader in = new BufferedReader(new FileReader("resources/aloi.txt"));
            while (in.ready() == true) {
                line = in.readLine();
                tok = new StringTokenizer(line, " ");
                p = new PointOneObjectImage();
                for (int k = 0; k < 13; k++) {
                    p.getFeature()[k] = Double.parseDouble(tok.nextToken());
                }
                bufObj = tok.nextToken();
                bufObj = bufObj.substring(1, bufObj.length() - 1);
                p.setPathObject(bufObj);
                bufImg = tok.nextToken();
                bufImg = bufImg.substring(1, bufImg.length() - 1);
                p.setPathImage(bufImg);
                set.add(p);
            }
            in.close();

            //amount of the serie
            BufferedWriter txtFile;
            for (int amountRoute = AMOUNT_ROUTE_INITIAL; amountRoute <= TOTAL_ROUTE; amountRoute += INC_ROUTE_INITIAL) {
                System.out.println("amount" + amountRoute + "point56");
                txtFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                        "resources/route" + amountRoute + "point56.txt")));
                for (int i = 0; i < amountRoute; i++) {
                    p = new PointOneObjectImage();
                    nameObject = "img" + RANDOM_INSERT.nextInt(1000);
                    p.setPathObject(nameObject);
                    it = set.tailSet(p).iterator();
                    if (it.hasNext()) {
                        p = it.next();
                        nameObject = p.getPathObject();
                        choose = true;
                        count = 0;
                        first = null;
                        second = null;
                        strBuild = new StringBuilder();
                        while (it.hasNext()) {
                            if (p.getPathObject().compareTo(nameObject) == 0) {
                                if (choose) {
                                    if (first == null) {
                                        first = p;
                                    } else if (second == null) {
                                        second = p;
                                    }
                                    strBuild.append('\t').append(p.getPathObject()).append('\t').append(p.getPathImage());
                                    for (int j = 0; j < p.getFeature().length; j++) {
                                        strBuild.append('\t').append(p.getFeature()[j]);
                                    }
                                    count++;
                                }
                                choose = !choose;
                            } else {
                                break;
                            }
                            System.out.println(p.getPathObject() + " " + p.getPathImage());
                            p = it.next();
                        }
                        txtFile.write("" + count + '\t' + first.distanceTo(second) + '\t');
                        txtFile.write(strBuild.toString());
                        txtFile.newLine();
                    } else {
                        i--;
                    }
                }

                txtFile.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(App2ndGenerationRoute.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App2ndGenerationRoute.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
