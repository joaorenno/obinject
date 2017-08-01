/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.inception;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author windows
 */
@Persistent
public class Sonho {
    
    @Unique
    private int codigo;
    private Calendar inicio = new GregorianCalendar();
    private Calendar termino = new GregorianCalendar();
    private Calendar avisoDisparo = new GregorianCalendar();
    private int nivel;
    private boolean limbo;
    private Sonho sonhoAnterior;
    private Sonho sonhoProximo;
    private Pasiv pasiv;
    private Sonhador sonhadorConstroi;
    private List<Sonhador> sonhadores = 
            new ArrayList<>();
    private List<Sombra> sombras = 
            new ArrayList<>();

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Calendar getInicio() {
        return inicio;
    }

    public void setInicio(Calendar inicio) {
        this.inicio = inicio;
    }

    public Calendar getTermino() {
        return termino;
    }

    public void setTermino(Calendar termino) {
        this.termino = termino;
    }

    public Calendar getAvisoDisparo() {
        return avisoDisparo;
    }

    public void setAvisoDisparo(Calendar avisoDisparo) {
        this.avisoDisparo = avisoDisparo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public boolean isLimbo() {
        return limbo;
    }

    public void setLimbo(boolean limbo) {
        this.limbo = limbo;
    }

    public Sonho getSonhoAnterior() {
        return sonhoAnterior;
    }

    public void setSonhoAnterior(Sonho sonhoAnterior) {
        this.sonhoAnterior = sonhoAnterior;
    }

    public Sonho getSonhoProximo() {
        return sonhoProximo;
    }

    public void setSonhoProximo(Sonho sonhoProximo) {
        this.sonhoProximo = sonhoProximo;
    }

    public Pasiv getPasiv() {
        return pasiv;
    }

    public void setPasiv(Pasiv pasiv) {
        this.pasiv = pasiv;
    }

    public Sonhador getSonhadorConstroi() {
        return sonhadorConstroi;
    }

    public void setSonhadorConstroi(Sonhador sonhadorConstroi) {
        this.sonhadorConstroi = sonhadorConstroi;
    }

    public List<Sonhador> getSonhadores() {
        return sonhadores;
    }

    public void setSonhadores(List<Sonhador> sonhadores) {
        this.sonhadores = sonhadores;
    }

    public List<Sombra> getSombras() {
        return sombras;
    }

    public void setSombras(List<Sombra> sombras) {
        this.sombras = sombras;
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
    
    public int sizeOfSombra(){
        return sombras.size();
    }
    
    public boolean addSombra(Sombra v){
        return sombras.add(v);
    }
    
    public Sombra removeSombra(int idx){
        return sombras.remove(idx);
    }
    
    public Iterator<Sombra> iteratorSombra(){
        return sombras.iterator();
    }
    
    
    
}
