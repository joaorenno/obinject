/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.meta.generator;

/**
 *
 * @author windows
 */
public class IndexName implements Comparable<IndexName>{

    private final String classEntity;
    private final String classKey;
    private final String structName;

    public IndexName(String classEntity, String classKey) {
        this.classEntity = classEntity;
        this.classKey = classKey;
        structName = Character.toLowerCase(classKey.charAt(0)) + classKey.substring(1) + "Structure";
    }

    @Override
    public int compareTo(IndexName o) {
        int compare = classEntity.compareTo(o.getClassEntity()) ;
        if(compare == 0){
            return classKey.compareTo(o.getClassKey());
        }else{
            return compare;
        }
    }

    public String getClassKey() {
        return classKey;
    }

    public String getClassEntity() {
        return classEntity;
    }
    
    public String getStructName() {
        return structName;
    }

}
