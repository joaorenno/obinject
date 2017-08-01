/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.obinject.queries.KNearestNeighbor;

/**
 *
 * @author enzo
 */
public class App {

    public static final String nomeDiretorio = "img";

    public static void main(String[] args) {

        int num = 1;
        int tam=10;
        int falsoPositivo = 0;
        int fpTotal;
        float mediaDir;
        float mediaTotal = 0f;
        int type = 1;
        //int falsoNegativo=0;
        switch (type) {
            case 0:
            try {
                File dir = new File(nomeDiretorio);
                for (File subDir : dir.listFiles()) {
                    for (File file : subDir.listFiles()) {
                        FeatureOneExam ife = new FeatureOneExam();
                        ife.setId(num++);
                        ife.setName(subDir.getName() + '-' + file.getName());
                        ife.setImage(ImageIO.read(file));
                        if (ife.getImage() != null) {
                            FeatureOneExam.featureOneExamStructure.add(ife);
                        }
                    }
                }
                for (int i = 1; i < 41; i++) {
                    fpTotal = 0;
                    for (int j = 1; j < 11; j++) {
                        FeatureOneExam query = new FeatureOneExam();
                        query.setImage(ImageIO.read(new File(
                                "img/s" + i + "/" + j + ".png")));
                        KNearestNeighbor knn = new KNearestNeighbor(null, null, tam);
                        Collection<FeatureOneExam> l = knn.knnQueryInMtree(query);
                        if (!l.isEmpty()) {
                            //System.out.println("===COMPARANDO: [s" + i + "/" + j + ".png]");
                            for (FeatureOneExam res : l) {
                                if (!res.getName().split("-")[0].equals("s"+i)) {
                                    falsoPositivo++;
                                    //System.out.println("*"+res.getName() + "\tdist = " + res.getPreservedDistance());
                                }//else
                                //System.out.println(" "+res.getName() + "\tdist = " + res.getPreservedDistance());
                            }
                        }
                        //System.out.println("\t[falso positivo=" + falsoPositivo+"]");

                        ///Corrigir
                        //System.out.println("falso negativo=" + falsoPositivo);
                        //System.out.println("");
                        fpTotal += falsoPositivo;
                        falsoPositivo = 0;
                    }
                    mediaDir = (fpTotal/10f);
                    System.out.println("Média diretório: "+mediaDir);
                    mediaTotal = mediaTotal + mediaDir;
                }
                mediaTotal = mediaTotal/40f;
                System.out.println("Média total: "+mediaTotal);

                //Aumentar para 15
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case 1:
                try {
                    File dir = new File(nomeDiretorio);
                    for (File subDir : dir.listFiles()) {
                        for (File file : subDir.listFiles()) {
                            FeatureTwoExam ife = new FeatureTwoExam();
                            ife.setId(num++);
                            ife.setName(subDir.getName() + '-' + file.getName());
                            ife.setImage(ImageIO.read(file));
                            if (ife.getImage() != null) {
                                FeatureTwoExam.featureTwoExamStructure.add(ife);
                            }
                        }
                    }
                    for (int i = 1; i < 41; i++) {
                        fpTotal = 0;
                        for (int j = 1; j < 11; j++) {
                            FeatureTwoExam query = new FeatureTwoExam();
                            query.setImage(ImageIO.read(new File(
                                    "img/s" + i + "/" + j + ".png")));
                            KNearestNeighbor knn = new KNearestNeighbor(null, null, tam);
                            Collection<FeatureTwoExam> l = knn.knnQueryInMtree(query);
                            if (!l.isEmpty()) {
                                //System.out.println("Tamanho: "+l.size());
                                
                                if (l.size() > tam) {
                                    // Ordenando para poder truncar (precisa ser lista)
                                    Comparator<FeatureTwoExam> comp = new Comparator<FeatureTwoExam>() {

                                        @Override
                                        public int compare(FeatureTwoExam o1, FeatureTwoExam o2) {
                                            return Double.compare(o2.getPreservedDistance(), o1.getPreservedDistance());
                                        }
                                    };
                                    List<FeatureTwoExam> lst;
                                    if (l instanceof List) {
                                        lst = (List) l;
                                    } else {
                                        lst = new ArrayList(l);
                                    }
                                    Collections.sort(lst, comp);
                                    //Truncando aleatoriamente os maiores e transformando novamente em Collection
                                    l.clear();
                                    int tamanho = 0;
                                    Iterator<FeatureTwoExam> it = lst.iterator();
                                    while (it.hasNext()) {
                                        tamanho++;
                                        if (tamanho > tam) {
                                            it.remove();
                                            it.next();
                                        } else {
                                            l.add(it.next());
                                        }
                                    }
                                    //System.out.println("Tamanho depois: "+l.size());
                                }
                                
                                //System.out.println("===COMPARANDO: [s" + i + "/" + j + ".png]");
                                //System.out.println("s" + i + "/" + j + ".png");
                                for (FeatureTwoExam res : l) {
                                    if (!res.getName().split("-")[0].equals("s" + i)) {
                                        falsoPositivo++;
                                        //System.out.println("*"+res.getName() + "\tdist = " + res.getPreservedDistance());
                                    } else {
                                        //System.out.println(" "+res.getName() + "\tdist = " + res.getPreservedDistance());
                                    }
                                }
                            }
                            //System.out.println(falsoPositivo);

                            ///Corrigir
                            //System.out.println("falso negativo=" + falsoPositivo);
                            //System.out.println("");
                            fpTotal += falsoPositivo;
                            falsoPositivo = 0;
                        }
                    mediaDir = (fpTotal/10f);
                    System.out.println("Média diretório: "+mediaDir);
                    mediaTotal = mediaTotal + mediaDir;
                    }
                mediaTotal = mediaTotal/40f;
                System.out.println("Média total: "+mediaTotal);
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);

                }
                break;
            default:
                break;
        }
    }
}
