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
public class Local
{
    @Unique
    private int codigo;
    private String pais;
    private double lat;
    private double lont;

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLont()
    {
        return lont;
    }

    public void setLont(double lont)
    {
        this.lont = lont;
    }

    public String getPais()
    {
        return pais;
    }

    public void setPais(String pais)
    {
        this.pais = pais;
    }
}
