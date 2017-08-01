/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.image;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author system
 */
public class AppTesteOrdenacao {
    public static final String nomeDiretorio = "img";
    
    public static void main(String[] args) {
        int numArq, i=0;
        boolean sair=false;
        Scanner s = new Scanner(System.in);
        while(sair==false){
            System.out.println("Digite um numero entre 0 e 399, ou qualquer outro para sair: ");
            numArq = s.nextInt();
            if(numArq>=0&&numArq<=399){
                i=0;
                try {
                    File dir = new File(nomeDiretorio);
                    for (File subDir : dir.listFiles()) {
                        for (File file : subDir.listFiles()) {
                            if (i == numArq) {
                                FeatureTwoExam ife = new FeatureTwoExam();
                                ife.setId(i);
                                ife.setName(subDir.getName() + '-' + file.getName());
                                ife.setImage(ImageIO.read(file));
                                if (ife.getImage() != null) {
                                    FeatureTwoExam.featureTwoExamStructure.add(ife);
                                }
                            }
                            i++;
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                sair=true;
                s.close();
            }
        }
    }
    
}
