/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.proof;

import java.util.Random;
import org.obinject.meta.generator.DistanceUtil;

/**
 *
 * @author system
 */
public class ProofEuclideanDistanceIsMetric {

    public static final long max = 10000000;
    public static final Random rand = new Random(1000);
    public static final int rad = 6371;
    public static final int size = 13;

    public static float doubleGenerator() {
        Float latit = rand.nextFloat();
        int intPart = rand.nextInt() % (1000000 / 256);
        latit += intPart;
        return latit;
    }

    public static double metricEuclideanDistance(double d1[], double d2[]) {
        return DistanceUtil.euclidean(d1, d2);
    }

    public static void main(String[] args) {
        double d1[], d2[], d3[], d12, d21, d13, d31, d23, d32, d11, d22, d33;

        d1 = d2 = d3 = new double[size];

        for (long i = 0; i < max; i++) {
            for (int j = 0; j < size; j++) {
                d1[j] = doubleGenerator();
                d2[j] = doubleGenerator();
                d3[j] = doubleGenerator();
            }

            d11 = metricEuclideanDistance(d1, d1);
            d22 = metricEuclideanDistance(d2, d2);
            d33 = metricEuclideanDistance(d3, d3);
            d12 = metricEuclideanDistance(d1, d2);
            d21 = metricEuclideanDistance(d2, d1);
            d13 = metricEuclideanDistance(d1, d3);
            d31 = metricEuclideanDistance(d3, d1);
            d23 = metricEuclideanDistance(d2, d3);
            d32 = metricEuclideanDistance(d3, d2);

            // Nao negatividade
            if (!(d12 >= 0 && d21 >= 0 && d13 >= 0 && d31 >= 0 && d23 >= 0 && d32 >= 0)) {
                System.out.println("Alguem nao eh positivo");
            }
            if (!(d11 == 0 && d22 == 0 && d33 == 0)) {
                System.out.println("Alguem nao tem dist zero para si proprio");
            }

            // Simetria
            if (!(d12 == d21 && d13 == d31 && d23 == d32)) {
                System.out.println("Alguem nao eh simetrico");
            }

            // Desigualdade triagular
            if (!(d12 <= d13 + d32
                    && d13 <= d12 + d23
                    && d23 <= d21 + d13)) {
                System.out.println("Alguem nao obedece a desigualdade triangular");
            }

        }
    }
}
