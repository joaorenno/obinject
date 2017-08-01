/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.obinject.annotation.commnon.IndexesOfCoordinate;

/**
 *
 * @author windows
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IndexesOfCoordinate.class)
public @interface Coordinate {
    
    public Number number() default Number.One;

    public Order order() default Order.First;

    public AngleUnit angle() default AngleUnit.Degree;

    public double radius() default 6378;

}
