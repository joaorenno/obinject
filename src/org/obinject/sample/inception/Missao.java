/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.inception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author windows
 */
@Persistent

public class Missao {
    
    @Unique    
    private int codigo;
    private String objetivo;
    private boolean cumprido;
    private List<Sonhador> sonhadores = 
            new ArrayList<>();

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public boolean isCumprido() {
        return cumprido;
    }

    public void setCumprido(boolean cumprido) {
        this.cumprido = cumprido;
    }

    public List<Sonhador> getSonhadores() {
        return sonhadores;
    }

    public void setSonhadores(List<Sonhador> sonhadores) {
        this.sonhadores = sonhadores;
    }
    
    public int sizeOfSonhador(){
        return sonhadores.size();
    }
    
    public boolean addSonhador(Sonhador v){
        return sonhadores.add(v);
    }
    
    public Sonhador removeSonhador(int idx){
        return sonhadores.remove(idx);
    }
    
    public Iterator<Sonhador> iteratorSonhador(){
        return sonhadores.iterator();
    }
}
