/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.meta.generator;

import java.lang.reflect.Field;
import org.obinject.annotation.AngleUnit;

/**
 *
 * @author windows
 */
public class CoordinateKeyField extends KeyField {

    private final AngleUnit angleUnit;
    private final double radius;

    public CoordinateKeyField(String get, String set, Field field, String classEntity, 
            String classKey, AngleUnit angleUnit, double radius) {
        super(get, set, field, classEntity, classKey);
        this.angleUnit = angleUnit;
        this.radius = radius;
    }

    public AngleUnit getAngleUnit() {
        return angleUnit;
    }

    public double getRadius() {
        return radius;
    }

}
