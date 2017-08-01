/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.color.clean;

import java.util.Comparator;

/**
 *
 * @author system
 */
public class VectorDouble implements Comparable<VectorDouble> {

    private int id;
    private double vector[] = new double[32];

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double[] getVector() {
        return vector;
    }

    public void setVector(double[] vector) {
        this.vector = vector;
    }

    @Override
    public int compareTo(VectorDouble obj) {
        for (int i = 0; i < 32; i++) {
            if (this.vector[i] != obj.getVector()[i]) {
                if (this.vector[i] < obj.getVector()[i]) {
                    return -1;
                }
                return 1;
            }
        }
        return 0;
    }
}

class ComparatorVectorDouble implements Comparator<double[]> {

    @Override
    public int compare(double[] o1, double[] o2) {
        for (int i = 1; i < 33; i++) {
            if (o1[i] != o2[i]) {
                if (o1[i] < o2[i]) {
                    return -1;
                }
                return 1;
            }
        }
        return 0;
    }
}
