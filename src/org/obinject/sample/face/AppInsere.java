package org.obinject.sample.face;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.obinject.PersistentManager;
import org.obinject.storage.BTreeEntity;

public class AppInsere {

    private static void addFileInDirectories(File source, List<File> fileList) {
        File[] list = source.listFiles();
        for (File fl : list) {
            if (!fl.isDirectory()) {
                fileList.add(fl);
            } else {
                addFileInDirectories(fl, fileList);
            }
        }
    }

    public static void main(String[] args) {
        int count = 0;
        Face face = new Face();
        List<File> files = new LinkedList<>();
        PersistentManager pm = new PersistentManager();
        addFileInDirectories(new File("/dataset/lfw1"), files);
        long end;
        long start = System.nanoTime();
        for (File f : files) {
            try {
                face.setFace(ImageIO.read(f));
                face.setLabel(f.toString());
                pm.getTransaction().begin();
                pm.inject(face);
                pm.getTransaction().commit();
                count++;
                if(count % 10 == 0){
                    System.out.println("count="+count);
                }
            } catch (IOException ex) {
                Logger.getLogger(AppInsere.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            FileWriter fwrite = new FileWriter(new File("result.txt"));
            fwrite.append("\n\nAltura B-Tree Persiste: "
                    + String.valueOf(((BTreeEntity) $Face.entityStructure).height()));
            fwrite.append("\nMedições B-Tree Persiste: "
                    + String.valueOf($Face.entityStructure.getAverageForAdd().amountOfMeasurements()));
            fwrite.append("\nTempo B-Tree Persiste: "
                    + String.valueOf($Face.entityStructure.getAverageForAdd().measuredTime()));
            fwrite.append("\nAcesso à disco B-Tree Persiste: "
                    + String.valueOf($Face.entityStructure.getAverageForAdd().measuredDiskAccess()));
            fwrite.append("\nComparações na B-Tree Persiste: "
                    + String.valueOf($Face.entityStructure.getAverageForAdd().measuredVerifications()));
            fwrite.append("\n\nAltura B-Tree Unique: "
                    + String.valueOf($Face.uniqueOneFaceStructure.height()));
            fwrite.append("\nnMedições B-Tree Unique: "
                    + String.valueOf($Face.uniqueOneFaceStructure.getAverageForAdd().amountOfMeasurements()));
            fwrite.append("\nTempo B-Tree Unique: "
                    + String.valueOf($Face.uniqueOneFaceStructure.getAverageForAdd().measuredTime()));
            fwrite.append("\nAcesso à disco B-Tree Unique: "
                    + String.valueOf($Face.uniqueOneFaceStructure.getAverageForAdd().measuredDiskAccess()));
            fwrite.append("\nComparações na B-Tree Unique: "
                    + String.valueOf($Face.uniqueOneFaceStructure.getAverageForAdd().measuredVerifications()));
            fwrite.append("\n\nAltura M-Tree HistogramStatistical: "
                    + String.valueOf($Face.featureOneFaceStructure.height()));
            fwrite.append("\nnMedições M-Tree HistogramStatistical: "
                    + String.valueOf($Face.featureOneFaceStructure.getAverageForAdd().amountOfMeasurements()));
            fwrite.append("\nTempo M-Tree HistogramStatistical: "
                    + String.valueOf($Face.featureOneFaceStructure.getAverageForAdd().measuredTime()));
            fwrite.append("\nAcesso à disco M-Tree HistogramStatistical: "
                    + String.valueOf($Face.featureOneFaceStructure.getAverageForAdd().measuredDiskAccess()));
            fwrite.append("\nCálculo de Distância M-Tree HistogramStatistical: "
                    + String.valueOf($Face.featureOneFaceStructure.getAverageForAdd().measuredVerifications()));
//            fwrite.append("\n\nAltura M-Tree HaralickSymmetric: "
//                    + String.valueOf($Face.featureTwoFaceStructure.height()));
//            fwrite.append("\nnMedições M-Tree HaralickSymmetric: "
//                    + String.valueOf($Face.featureTwoFaceStructure.getAverageForAdd().amountOfMeasurements()));
//            fwrite.append("\nTempo M-Tree HaralickSymmetric: "
//                    + String.valueOf($Face.featureTwoFaceStructure.getAverageForAdd().measuredTime()));
//            fwrite.append("\nAcesso à disco M-Tree HaralickSymmetric: "
//                    + String.valueOf($Face.featureTwoFaceStructure.getAverageForAdd().measuredDiskAccess()));
//            fwrite.append("\nCálculo de Distância M-Tree HaralickSymmetric: "
//                    + String.valueOf($Face.featureTwoFaceStructure.getAverageForAdd().measuredVerifications()));
//            fwrite.append("\n\nAltura M-Tree HaralickAssymmetric: "
//                    + String.valueOf($Face.featureThreeFaceStructure.height()));
//            fwrite.append("\nnMedições M-Tree HaralickAssymmetric: "
//                    + String.valueOf($Face.featureThreeFaceStructure.getAverageForAdd().amountOfMeasurements()));
//            fwrite.append("\nTempo M-Tree HaralickAssymmetric: "
//                    + String.valueOf($Face.featureThreeFaceStructure.getAverageForAdd().measuredTime()));
//            fwrite.append("\nAcesso à disco M-Tree HaralickAssymmetric: "
//                    + String.valueOf($Face.featureThreeFaceStructure.getAverageForAdd().measuredDiskAccess()));
//            fwrite.append("\nCálculo de Distância M-Tree HaralickAssymmetric: "
//                    + String.valueOf($Face.featureThreeFaceStructure.getAverageForAdd().measuredVerifications()));

            end = System.nanoTime();
            fwrite.append("\nTempo em Nano do experimento: " + (end - start));
            
            fwrite.flush();
            fwrite.close();
        } catch (IOException ex) {
            Logger.getLogger(AppInsere.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
