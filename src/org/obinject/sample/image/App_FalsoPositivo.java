/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.image;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;
import javax.imageio.ImageIO;
import org.obinject.queries.KNearestNeighbor;

/**
 *
 * @author enzo
 */
public class App_FalsoPositivo {

    public static final String nomeDiretorio = "img";

    public static void main(String[] args) throws IOException {

    int num = 1;
    int falsoPositivo = 0;
    int fpTotal;
    float mediaDir;
    float mediaTotal = 0f;
    int type=1;
    //int falsoNegativo=0;

    Scanner s = new Scanner(System.in);

    //Adicionando as imagens na estrutura (MTREE)
    File dir = new File(nomeDiretorio);
    for (File subDir : dir.listFiles()) {
        for (File file : subDir.listFiles()) {
            if(type==0){
                FeatureOneExam featureFirstExam = new FeatureOneExam();
                featureFirstExam.setId(num++);
                featureFirstExam.setName(subDir.getName() + '-' + file.getName());
                featureFirstExam.setImage(ImageIO.read(file));
                if (featureFirstExam.getImage() != null) {
                    FeatureOneExam.featureOneExamStructure.add(featureFirstExam);
                }
            }
            if (type==1) {
                FeatureTwoExam featureSecondExam = new FeatureTwoExam();
                featureSecondExam.setId(num++);
                featureSecondExam.setName(subDir.getName() + '-' + file.getName());
                featureSecondExam.setImage(ImageIO.read(file));
                if (featureSecondExam.getImage() != null) {
                    FeatureTwoExam.featureTwoExamStructure.add(featureSecondExam);
                }
            }
            
            
        }
    }

        System.out.println("Digite:\n0-Histograma.\n1-Histograma Metrico.");
        type = s.nextInt();
    
        switch (type) {
        case 0:
            //Fazendo Comparações
            System.out.println("Histograma");
            for (int i = 1; i < 41; i++) {
                fpTotal = 0;
                for (int j = 1; j < 11; j++) {
                    FeatureOneExam query = new FeatureOneExam();
                    query.setImage(ImageIO.read(new File("img/s" + i + "/" + j + ".png")));
                    KNearestNeighbor knn = new KNearestNeighbor(null, null, 10);
                    Collection<FeatureOneExam> l = knn.knnQueryInMtree(query);
                    if (!l.isEmpty()) {
                        System.out.println("===COMPARANDO: [s" + i + "/" + j + ".png]");
                        for (FeatureOneExam res : l) {
                            if (!res.getName().split("-")[0].equals("s" + i)) {
                                falsoPositivo++;
                                System.out.println("*"+res.getName() + "\tdist = " + res.getPreservedDistance());
                            }else
                                System.out.println(" "+res.getName() + "\tdist = " + res.getPreservedDistance());
                        }
                    }
                    System.out.println("\t[falso positivo=" + falsoPositivo+"]");
                    fpTotal += falsoPositivo;
                    falsoPositivo = 0;
                }
                mediaDir = (fpTotal / 10f);
                System.out.println("Média diretório: " + mediaDir);
                mediaTotal = mediaTotal + mediaDir;
            }
            mediaTotal = mediaTotal / 40f;
            System.out.println("Média total: " + mediaTotal);
        break;

        case 1:
            //Fazendo comparações
            System.out.println("Histograma Métrico");
            for (int i = 1; i < 41; i++) {
                fpTotal = 0;
                for (int j = 1; j < 11; j++) {
                    FeatureTwoExam query = new FeatureTwoExam();
                    query.setImage(ImageIO.read(new File("img/s" + i + "/" + j + ".png")));
                    //O terceiro parametro será a quantidade de imagens que serão buscadas a partir de cada imagem dada.
                    KNearestNeighbor knn = new KNearestNeighbor(null, null, 400);
                    Collection<FeatureTwoExam> l = knn.knnQueryInMtree(query);
                    if (!l.isEmpty()) {
                        System.out.println("===COMPARANDO: [s" + i + "/" + j + ".png]");
                        for (FeatureTwoExam res : l) {
                            if (!res.getName().split("-")[0].equals("s" + i)) {
                                falsoPositivo++;
                                System.out.println("*"+res.getName() + "\tdist = " + res.getPreservedDistance());
                            }else 
                                System.out.println(" "+res.getName() + "\tdist = " + res.getPreservedDistance());
                            
                        }
                    }
                    System.out.println(falsoPositivo);
                    fpTotal += falsoPositivo;
                    falsoPositivo = 0;
                }
                mediaDir = (fpTotal / 10f);
                System.out.println("Média diretório: " + mediaDir);
                mediaTotal = mediaTotal + mediaDir;

            }
            mediaTotal = mediaTotal / 40f;
            System.out.println("Média total: " + mediaTotal);
        break;

        default:
            System.out.println("Selecione uma opção válida");
        break;
    }
    }
}
