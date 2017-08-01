/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.locadora;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author aluno11
 */
@Persistent
public class Filme
{
    @Unique
    private int codigo;
    private String nome;
    private double preco;

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int v)
    {
        codigo = v;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String v)
    {
        nome = v;
    }

    public double getPreco()
    {
        return preco;
    }

    public void setPreco(double v)
    {
        preco = v;
    }
}
