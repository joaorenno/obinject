/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.meta.rectangle;

import java.util.Random;
import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author luiz
 */
public class AppInsertion {

    public static int MAX = 1000;
    public static Random randSizeStr = new Random(10);
    public static Random randDouble = new Random(10);

    private static String stringGenerator() {
        int size = randSizeStr.nextInt(25) + 5;
        char[] ch = new char[size];
        for (int i = 0; i < size; i++) {
            ch[i] = (char) ((randSizeStr.nextInt(26)) + 65);
        }
        return new String(ch);
    }

    public static void main(String[] args) {
        int count = 0;
        PersistentManager pm = PersistentManagerFactory.createPersistentManager();
        for (int i = 0; i < MAX; i++) {
            MultiDimension d = new MultiDimension();
            d.setNome(stringGenerator());
            d.setValue1( randDouble.nextDouble() );
            d.setValue2( randDouble.nextDouble() );
            d.getValue3()[0] = randDouble.nextDouble();
            d.getValue3()[1] = randDouble.nextDouble();
            d.getValue3()[2] = randDouble.nextDouble();
            d.setValue4( randDouble.nextDouble() );
            d.setValue5( randDouble.nextDouble() );
            d.setValueExtension1( randDouble.nextDouble() );
            d.setValueExtension2( randDouble.nextDouble() );
            d.getValueExtension3()[0] = randDouble.nextDouble();
            d.getValueExtension3()[1] = randDouble.nextDouble();
            d.getValueExtension3()[2] = randDouble.nextDouble();
            d.setValueExtension4( randDouble.nextDouble() );
            d.setValueExtension5( randDouble.nextDouble() );
            pm.getTransaction().begin();
            pm.inject(d);
            pm.getTransaction().commit();
//            RTree<RectangleFirstMultiDimension> rtree = EntityMultiDimension.rectangleFirstMultiDimensionStructure;
//            if (rtree.find(new RectangleFirstMultiDimension(d)) == null) {
//                System.out.println("not found: rtree[" + i + "]=" + d.getNome());
//                count++;
//            }
        }
        System.out.println("NOT FOUND :" + count);
    }
}
