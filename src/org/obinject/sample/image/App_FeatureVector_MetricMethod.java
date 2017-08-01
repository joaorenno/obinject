/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.image;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author enzo
 */
public class App_FeatureVector_MetricMethod {

    public static final String nomeDiretorio = "img";

    public static void main(String[] args) {

        int num = 1;
        int tam=10;

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
                
                FeatureTwoExam query = new FeatureTwoExam();
                query.setImage(ImageIO.read(new File("img/s0" + "/" + "1" + ".jpg")));

            } catch (IOException ex) {
                Logger.getLogger(App_FeatureVector_MetricMethod.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
}
