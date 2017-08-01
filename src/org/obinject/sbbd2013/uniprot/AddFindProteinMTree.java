package org.obinject.sbbd2013.uniprot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.obinject.device.File;
import org.obinject.storage.MTree;

public class AddFindProteinMTree
{

    private static final int sizeOfNode = 2048;

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        StringMetricProtein metric = new StringMetricProtein();
//		Memory workspace = new Memory(sizeOfNode);
        File workspace = new File("mtreeProtein32-" + sizeOfNode + ".dat", sizeOfNode);
        MTree<StringMetricProtein> mtree = new MTree<StringMetricProtein>(workspace)
        {
        };

        BufferedReader in = new BufferedReader(new FileReader("protValid32.txt"));

//		int count = 0;
        while (in.ready() == true)
        {
            metric.setAminoAcids(in.readLine());
            mtree.add(metric);
//			count++;
//			if(count % 1000 == 0) System.out.println(count);
        }

        in.close();
        in = null;
        System.gc();

        System.out.println("size of page: " + workspace.sizeOfArray());
        System.out.println("time to insert: " + mtree.getAverageForAdd().measuredTime());
        System.out.println("calculos de distancia: " + mtree.getAverageForAdd().measuredVerifications());
        System.out.println("acesso a blocos: " + mtree.getAverageForAdd().measuredDiskAccess());

        BufferedReader check = new BufferedReader(new FileReader("protValid32.txt"));

        int notFound = 0;

//		count = 0;
        while (check.ready() == true)
        {
            metric.setAminoAcids(check.readLine());
            if (mtree.find(metric) == null)
            {
                notFound++;
            }
//			count++;
//			if(count % 1000 == 0) System.out.println(count);
        }

        check.close();
        System.out.println("not found: " + notFound + " objects");
        System.out.println("time to insert: " + mtree.getAverageForFind().measuredTime());
        System.out.println("calculos de distancia: " + mtree.getAverageForFind().measuredVerifications());
        System.out.println("acesso a blocos: " + mtree.getAverageForFind().measuredDiskAccess());
    }
}
