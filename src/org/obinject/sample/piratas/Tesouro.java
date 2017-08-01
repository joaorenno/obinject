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
public class Tesouro extends Mapa
{

    private float valorTesouro;
    private Ilha ilha;

    public Ilha getIlha()
    {
        return ilha;
    }

    public void setIlha(Ilha ilha)
    {
        this.ilha = ilha;
    }

    public float getValorTesouro()
    {
        return valorTesouro;
    }

    public void setValorTesouro(float valorTesouro)
    {
        this.valorTesouro = valorTesouro;
    }
}
