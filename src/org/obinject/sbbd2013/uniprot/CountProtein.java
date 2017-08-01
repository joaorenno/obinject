package org.obinject.sbbd2013.uniprot;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author luiz
 */
public class CountProtein
{

    public static int maxLen = 64;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        Scanner scan = new Scanner(new File("resources/prot.txt"));

        String str;
        String prot = "";
        int i = 0;


        while (scan.hasNext())
        {
            str = scan.nextLine().trim();

            if (str.indexOf('>') > -1)
            {
                if (prot.length() > 0 && prot.length() <= maxLen)
                {
                    i++;
                    if (i % 1000 == 0)
                    {
                        System.out.println(i);
                    }
                }
                prot = "";
            } else
            {
                prot += str;
            }
        }

        System.out.println("Total: " + i); // 1261854
        scan.close();

        System.exit(0);
    }
}
