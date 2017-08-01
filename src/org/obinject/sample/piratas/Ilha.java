/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.piratas;

import org.obinject.annotation.Persistent;

/**
 *
 * @author well
 */
@Persistent
public class Ilha extends Local
{

    private String nome;
    private float area;

    public float getArea()
    {
        return area;
    }

    public void setArea(float area)
    {
        this.area = area;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }
}
