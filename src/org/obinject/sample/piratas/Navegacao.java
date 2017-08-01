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
public class Navegacao extends Mapa
{

    private String oceano;
    private Costa costa;

    public Costa getCosta()
    {
        return costa;
    }

    public void setCosta(Costa costa)
    {
        this.costa = costa;
    }

    public String getOceano()
    {
        return oceano;
    }

    public void setOceano(String oceano)
    {
        this.oceano = oceano;
    }
}
