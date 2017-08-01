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
public class Costa extends Local
{

    private String cidade;
    private String estado;

    public String getCidade()
    {
        return cidade;
    }

    public void setCidade(String cidade)
    {
        this.cidade = cidade;
    }

    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }
}
