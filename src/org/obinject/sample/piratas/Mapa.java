/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.piratas;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author well
 */
@Persistent
public class Mapa
{
    @Unique
    private String nome;
    private int qtdRota;
    private double escala;
    private Navegador navegador;

    public double getEscala()
    {
        return escala;
    }

    public void setEscala(double escala)
    {
        this.escala = escala;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public int getQtdRota()
    {
        return qtdRota;
    }

    public void setQtdRota(int qtdRota)
    {
        this.qtdRota = qtdRota;
    }

    public Navegador getNavegador()
    {
        return navegador;
    }

    public void setNavegador(Navegador navegador)
    {
        this.navegador = navegador;
    }
}
