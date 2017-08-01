package org.obinject.sample.join.multiview;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App3rdGenerationShuffle {

    public static final Random RANDOM_INSERT = new Random(100); // SAME SORTED ORDER IN BASE

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        //generation of the file route.txt
        try {
            BufferedReader in = new BufferedReader(new FileReader("resources/aloi.txt"));
            while (in.ready() == true) {
                String str = in.readLine();
                list.add(str);
            }
            in.close();
            //generation unordered of the file uniform.txt
            BufferedWriter txtFile = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("resources/aloiRandom.txt")));
            while (!list.isEmpty()) {
                txtFile.write(list.remove(RANDOM_INSERT.nextInt(list.size())));
                txtFile.newLine();
            }
            txtFile.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(App3rdGenerationShuffle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App3rdGenerationShuffle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
