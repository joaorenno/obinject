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
public class Auxiliar extends Tripulante
{

    private int horasNavegacao;

    public int getHorasNavegacao()
    {
        return horasNavegacao;
    }

    public void setHorasNavegacao(int horasNavegacao)
    {
        this.horasNavegacao = horasNavegacao;
    }
}
