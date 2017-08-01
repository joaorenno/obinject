package org.obinject.sbbd2013.uniprot;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import java.io.File;
import java.io.IOException;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author luiz
 */
public class ConcatProtein
{

    public static int maxLen = 32;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        Scanner scan = new Scanner(new File("resources/prot.txt"));
        Formatter format = new Formatter(new File("protValid32.txt"));
        HashSet<String> hash = new HashSet<>();

        String str;
        String prot = "";
        int i = 0, k, j;

        char amino[] =
        {
            'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H', 'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y', 'V'
        };

        char c;

        while (scan.hasNext())
        {
            str = scan.nextLine().trim();

            if (str.indexOf('>') > -1)
            {
                if (prot.length() > 0 && prot.length() <= maxLen)
                {
                    for (j = 0; j < prot.length(); j++)
                    {
                        c = prot.charAt(j);


                        if (!(c >= 'A' && c < 'Z' && c != 'B' && c != 'X' && c != 'J' && c != 'O' && c != 'U'))
                        {
                            break;
                        }
                    }

                    // Eliminando proteinas com aminoacidos desconhecidos
                    if (j >= prot.length())
                    {
                        // Eliminando duplicatas
                        if (!hash.contains(prot))
                        {
                            hash.add(prot);
                            format.format("%s\n", prot);

                            if (i % 100 == 0)
                            {
                                System.out.println(i);
                            }
                            i++;
                        }
                    }
                }
                prot = "";
            } else
            {
                prot += str;
            }
        }

        System.out.println("Total: " + i); // 128029713
        scan.close();
        format.flush();
        format.close();

        System.exit(0);
    }
}
