/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.obinject.meta.generator;

import java.util.List;

/**
 *
 * @author LuizGuilherme
 */
public class FeatureKeyDefinition extends KeyDefinition<FeatureKeyField> {
    
    public static final String prefix = "Feature";
    
    public FeatureKeyDefinition(Class<?> userClazz, String prefixClassName, 
            int sizeBlock, List<EntityField> entityFields) {
        super(userClazz, prefixClassName, sizeBlock, entityFields);
    }

}
