package org.obinject.proof;

import java.util.Random;
import org.obinject.meta.generator.DistanceUtil;

public class ProofVeryLeastDistanceIsMetric1
{

    public static final long max = 10000;
    public static final int upperBoundFeature = 65535;
    public static final int sizeOfFeature = 256;
    public static final int sizeOfDescriptor = 64;
    public static final Random rand = new Random(1000);

    public static float[][] matrixGenerator()
    {
        float[][] matrix = new float[rand.nextInt(64) + 1][sizeOfFeature];
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < sizeOfFeature; j++)
            {
                matrix[i][j] = rand.nextFloat() + rand.nextInt(upperBoundFeature);
            }
        }

        return matrix;
    }

    public static double veryLeastDistance(float[][] matrix1, float[][] matrix2)
    {
        return DistanceUtil.veryLeastDistance(matrix1, matrix2);
    }

    public static void main(String[] args)
    {
        double d12, d21, d13, d31, d23, d32, d11, d22, d33;
        float[][] m1, m2, m3;

        for (long i = 0; i < max; i++)
        {
            m1 = matrixGenerator();
            m2 = matrixGenerator();
            m3 = matrixGenerator();

            d11 = veryLeastDistance(m1, m1);
            d22 = veryLeastDistance(m2, m2);
            d33 = veryLeastDistance(m3, m3);
            d12 = veryLeastDistance(m1, m2);
            d21 = veryLeastDistance(m2, m1);
            d13 = veryLeastDistance(m1, m3);
            d31 = veryLeastDistance(m3, m1);
            d23 = veryLeastDistance(m2, m3);
            d32 = veryLeastDistance(m3, m2);

            // Nao negatividade
            if (!(d12 >= 0 && d21 >= 0 && d13 >= 0 && d31 >= 0 && d23 >= 0 && d32 >= 0))
            {
                System.out.println("Alguem nao eh positivo");
            }
            if (!(d11 == 0 && d22 == 0 && d33 == 0))
            {
                System.out.println("Alguem nao tem dist zero para si proprio");
            }

            // Simetria
            if (!(d12 == d21 && d13 == d31 && d23 == d32))
            {
                System.out.println("Alguem nao eh simetrico");
            }

            // Desigualdade triagular
            if (!(d12 <= d13 + d32
                    && d13 <= d12 + d23
                    && d23 <= d21 + d13))
            {
                System.out.println("Alguem nao obedece a desigualdade triangular");
            }

        }
    }
}
