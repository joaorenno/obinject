/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.student;

import java.io.File;
import java.io.IOException;
import java.util.Formatter;
import java.util.Random;

/**
 *
 * @author luiz
 */
public class AppGeneratorFile
{

    public static final int maxIn = 100;
    private static final Random random = new Random(1000);

    public static String gerador()
    {
        int size = random.nextInt(29) + 5;
        char[] ch = new char[size];
        for (int i = 0; i < size; i++)
        {
            ch[i] = (char) (random.nextInt(26) + 65);
        }
        return new String(ch);
    }

    public static void main(String[] args) throws IOException
    {
        Formatter f = new Formatter(new File("student.txt"));

        for (int i = 0; i < maxIn; i++)
        {
            f.format("%s ", ""+i);
            f.format("%s ", gerador());
            f.format("%s ", gerador());
            f.format("%s ", gerador());
            f.format("%s ", ""+random.nextFloat());
            f.format("%s ", ""+random.nextInt(40));            
        }

        f.flush();
        f.close();
    }
}
