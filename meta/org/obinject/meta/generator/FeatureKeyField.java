/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.meta.generator;

import java.lang.reflect.Field;
import org.obinject.annotation.ExtractionMethod;
import org.obinject.annotation.Order;

/**
 *
 * @author windows
 */
public class FeatureKeyField extends KeyField {

    private final Order fieldOrder;
    private final ExtractionMethod extractionEnum;

    public FeatureKeyField(String get, String set, Field field, String classEntity, 
            String classKey, Order fieldOrder, ExtractionMethod extractionEnum) {
        super(get, set, field, classEntity, classKey);
        this.fieldOrder = fieldOrder;
        this.extractionEnum = extractionEnum;
    }


    public Order getFieldOrder() {
        return fieldOrder;
    }

    public ExtractionMethod getExtractionEnum() {
        return extractionEnum;
    }

}
