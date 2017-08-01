package org.obinject.proof;

import java.util.Random;
import org.obinject.meta.generator.DistanceUtil;
import org.obinject.sample.city.PointOneCity;

public class ProofSphericalDistanceIsMetric
{

    public static final long max = 1000000;
    public static final Random rand = new Random(1000);
    public static final int rad = 6371;

    public static float latitudeGenerator()
    {
        Float latit = rand.nextFloat();
        int intPart = rand.nextInt() % 90;
        latit += intPart;
        return latit;
    }

    public static float longitudeGenerator()
    {
        Float longit = rand.nextFloat();
        int intPart = rand.nextInt() % 180;
        longit += intPart;
        return longit;
    }

    public static double sphericalDistance(double lat1, double lon1, double lat2, double lon2)
    {
        PointOneCity p1 = new PointOneCity();
        p1.setLatitude(lat1);
        p1.setLongitude(lon1);
        PointOneCity p2 = new PointOneCity();
        p2.setLatitude(lat2);
        p2.setLongitude(lon2);

        return DistanceUtil.sphericalDistanceDegree(p1, p2, 6378);
    }

    public static void main(String[] args)
    {
        double lat1, lat2, lat3, lon1, lon2, lon3, d12, d21, d13, d31, d23, d32, d11, d22, d33;

        for (long i = 0; i < max; i++)
        {
            lat1 = latitudeGenerator();
            lat2 = latitudeGenerator();
            lat3 = latitudeGenerator();
            lon1 = longitudeGenerator();
            lon2 = longitudeGenerator();
            lon3 = longitudeGenerator();

            d11 = sphericalDistance(lat1, lon1, lat1, lon1);
            d22 = sphericalDistance(lat2, lon2, lat2, lon2);
            d33 = sphericalDistance(lat3, lon3, lat3, lon3);
            d12 = sphericalDistance(lat1, lon1, lat2, lon2);
            d21 = sphericalDistance(lat2, lon2, lat1, lon1);
            d13 = sphericalDistance(lat1, lon1, lat3, lon3);
            d31 = sphericalDistance(lat3, lon3, lat1, lon1);
            d23 = sphericalDistance(lat2, lon2, lat3, lon3);
            d32 = sphericalDistance(lat3, lon3, lat2, lon2);

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
