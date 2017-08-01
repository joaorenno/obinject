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
public class App_FeatureVector_Histogram {

    public static final String nomeDiretorio = "img";

    public static void main(String[] args) {

        int num = 1;
        int tam=10;

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
                
                FeatureOneExam query = new FeatureOneExam();
                query.setImage(ImageIO.read(new File("img/s0" + "/" + "1" + ".jpg")));
                


                //Aumentar para 15
            } catch (IOException ex) {
                Logger.getLogger(App_FeatureVector_Histogram.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
}
