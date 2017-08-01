/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.meta.rectangle;

import org.obinject.annotation.Extension;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Origin;
import org.obinject.annotation.Unique;

/**
 *
 * @author luiz
 */
@Persistent
public class MultiDimension
{
    @Unique
    private String nome;
    
    @Origin
    private double value1;
    
    @Origin
    private double value2;

    @Origin
    private double value3[] = new double[3];
    
    @Origin
    private double value4;
    
    @Origin
    private double value5;

    @Extension
    private double valueExtension1;
    
    @Extension
    private double valueExtension2;

    @Extension
    private double valueExtension3[] = new double[3];
    
    @Extension
    private double valueExtension4;
    
    @Extension
    private double valueExtension5;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValue1() {
        return value1;
    }

    public void setValue1(double value1) {
        this.value1 = value1;
    }

    public double getValue2() {
        return value2;
    }

    public void setValue2(double value2) {
        this.value2 = value2;
    }

    public double[] getValue3() {
        return value3;
    }

    public void setValue3(double[] value3) {
        this.value3 = value3;
    }

    public double getValue4() {
        return value4;
    }

    public void setValue4(double value4) {
        this.value4 = value4;
    }

    public double getValue5() {
        return value5;
    }

    public void setValue5(double value5) {
        this.value5 = value5;
    }

    public double getValueExtension1() {
        return valueExtension1;
    }

    public void setValueExtension1(double valueExtension1) {
        this.valueExtension1 = valueExtension1;
    }

    public double getValueExtension2() {
        return valueExtension2;
    }

    public void setValueExtension2(double valueExtension2) {
        this.valueExtension2 = valueExtension2;
    }

    public double[] getValueExtension3() {
        return valueExtension3;
    }

    public void setValueExtension3(double[] valueExtension3) {
        this.valueExtension3 = valueExtension3;
    }

    public double getValueExtension4() {
        return valueExtension4;
    }

    public void setValueExtension4(double valueExtension4) {
        this.valueExtension4 = valueExtension4;
    }

    public double getValueExtension5() {
        return valueExtension5;
    }

    public void setValueExtension5(double valueExtension5) {
        this.valueExtension5 = valueExtension5;
    }

    
}
