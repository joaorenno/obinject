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
public class Mercadoria
{
    @Unique
    private int codigo;
    private float preco;
    private float peso;

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public float getPeso()
    {
        return peso;
    }

    public void setPeso(float peso)
    {
        this.peso = peso;
    }

    public float getPreco()
    {
        return preco;
    }

    public void setPreco(float preco)
    {
        this.preco = preco;
    }
}
