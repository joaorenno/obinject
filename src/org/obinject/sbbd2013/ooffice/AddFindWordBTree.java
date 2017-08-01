package org.obinject.sbbd2013.ooffice;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import org.obinject.device.File;
import org.obinject.storage.BTree;

public class AddFindWordBTree
{
    //private final int max = 694762;

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        System.out.println(new Date());
        OrderWord or = new OrderWord();
        //Memory workspace = new Memory(4096);
        File workspace = new File("word1024.dat", 1024);
        BTree<OrderWord> btree = new BTree<OrderWord>(workspace)
        {
        };

        BufferedReader in = new BufferedReader(new FileReader("openofficeUtf8.txt"));

        while (in.ready() == true)
        {
            or.setWord(in.readLine());
            btree.add(or);
        }

        in.close();
        in = null;
        System.gc();

        System.out.println("size of page: " + workspace.sizeOfArray());
        System.out.println("time to insert: " + btree.getAverageForAdd().measuredTime());
        System.out.println("calculos de distancia: " + btree.getAverageForAdd().measuredVerifications());
        System.out.println("acesso a blocos: " + btree.getAverageForAdd().measuredDiskAccess());

        BufferedReader check = new BufferedReader(new FileReader("openofficeUtf8.txt"));

        int notFound = 0;

        while (check.ready() == true)
        {
            or.setWord(check.readLine());
            if (btree.find(or) == null)
            {
                notFound++;
            }
        }

        check.close();
        System.out.println("not found: " + notFound + " objects");
        System.out.println("time to insert: " + btree.getAverageForFind().measuredTime());
        System.out.println("calculos de distancia: " + btree.getAverageForFind().measuredVerifications());
        System.out.println("acesso a blocos: " + btree.getAverageForFind().measuredDiskAccess());


    }
}