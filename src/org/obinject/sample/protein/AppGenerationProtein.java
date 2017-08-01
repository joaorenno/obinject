/*
 To change this template, choose Tools | Templates
 and open the template in the editor.
 */
package org.obinject.sample.protein;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import org.obinject.meta.Uuid;

public class AppGenerationProtein {

    public static final int maxElement = 10000;
    private static final int maxSize = 32;
    private static final Random rand = new Random();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        char amino[] = {
            'a', 'r', 'n', 'd', 'c', 'q', 'e', 'g', 'h', 'i', 'l', 'k', 'm', 'f', 'p', 's', 't', 'w', 'y', 'v'
        }; // b x j o u z
        int size;
        char vect[];
        StringBuilder str;
        Uuid uuid;
        try (BufferedWriter txtFile = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("resources/prot.txt")))) {
            for (int i = 0; i < maxElement; i++) {
                size = rand.nextInt((maxSize - 10)) + 10;
                vect = new char[size];
                for (int j = 0; j < size; j++) {
                    vect[j] = amino[rand.nextInt(20)];
                }
                uuid = Uuid.generator();
                str = new StringBuilder();
                str.append(vect).append('\t').append(uuid.toString());
                txtFile.write(str.toString());
                txtFile.newLine();
            }
        }
    }
}
