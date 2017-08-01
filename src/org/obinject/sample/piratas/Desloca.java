/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.piratas;

import java.util.Date;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author well
 */
@Persistent
public class Desloca
{
    @Unique
    private int codigo;
    private Date dtOrigem;
    private Date dtDestino;
    private float distancia;
    private Navio navio;
    private Local localOrigem;
    private Local localDestino;

    public Local getLocalDestino()
    {
        return localDestino;
    }

    public void setLocalDestino(Local localDestino)
    {
        this.localDestino = localDestino;
    }

    public Local getLocalOrigem()
    {
        return localOrigem;
    }

    public void setLocalOrigem(Local localOrigem)
    {
        this.localOrigem = localOrigem;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public float getDistancia()
    {
        return distancia;
    }

    public void setDistancia(float distancia)
    {
        this.distancia = distancia;
    }

    public Date getDtDestino()
    {
        return dtDestino;
    }

    public void setDtDestino(Date dtDestino)
    {
        this.dtDestino = dtDestino;
    }

    public Date getDtOrigem()
    {
        return dtOrigem;
    }

    public void setDtOrigem(Date dtOrigem)
    {
        this.dtOrigem = dtOrigem;
    }
    
    public Navio getNavio() {
        return navio;
    }

    public void setNavio(Navio navio) {
        this.navio = navio;
    }
}
