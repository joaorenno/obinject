/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.piratas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author well
 */
@Persistent
public abstract class Navio
{
    @Unique
    private String nome;
    private String construido;
    private int tamanho;
    private int qtdMastro;
    private int qtdVelas;
    private List<Tripulante> tripulantes = new ArrayList<>();
    private List<Desloca> deslocas = new ArrayList<>();

    public List<Desloca> getDeslocas()
    {
        return deslocas;
    }

    public void setDeslocas(List<Desloca> deslocas)
    {
        this.deslocas = deslocas;
    }

    public List<Tripulante> getTripulantes()
    {
        return tripulantes;
    }

    public void setTripulantes(List<Tripulante> tripulantes)
    {
        this.tripulantes = tripulantes;
    }

    public String getConstruido()
    {
        return construido;
    }

    public void setConstruido(String construido)
    {
        this.construido = construido;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public int getQtdMastro()
    {
        return qtdMastro;
    }

    public void setQtdMastro(int qtdMastro)
    {
        this.qtdMastro = qtdMastro;
    }

    public int getQtdVelas()
    {
        return qtdVelas;
    }

    public void setQtdVelas(int qtdVelas)
    {
        this.qtdVelas = qtdVelas;
    }

    public int getTamanho()
    {
        return tamanho;
    }

    public void setTamanho(int tamanho)
    {
        this.tamanho = tamanho;
    }

    public int sizeOfTripulante()
    {
        return tripulantes.size();
    }

    public Iterator<Tripulante> iteratorTripulantes()
    {
        return tripulantes.iterator();
    }

    public boolean addTripulante(Tripulante t)
    {
        //t.setNavio(this);
        return tripulantes.add(t);
    }

    public boolean removeTripulante(int idx)
    {
        return tripulantes.remove(tripulantes.get(idx));
    }

    public int sizeOfDesloca()
    {
        return deslocas.size();
    }

    public Iterator<Desloca> iteratorDeslocas()
    {
        return deslocas.iterator();
    }

    public boolean addDesloca(Desloca t)
    {
        //t.setNavio(this);
        return deslocas.add(t);
    }

    public boolean removeDesloca(int idx)
    {
        return deslocas.remove(deslocas.get(idx));
    }
}
