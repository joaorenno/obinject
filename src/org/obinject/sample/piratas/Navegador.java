/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.piratas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.obinject.annotation.Persistent;

/**
 *
 * @author well
 */
@Persistent
public class Navegador extends Tripulante
{

    private boolean capitao;
    private List<Mapa> mapas = new ArrayList<>();

    public List<Mapa> getMapas()
    {
        return mapas;
    }

    public void setMapas(List<Mapa> mapas)
    {
        this.mapas = mapas;
    }

    public boolean isCapitao()
    {
        return capitao;
    }

    public void setCapitao(boolean capitao)
    {
        this.capitao = capitao;
    }

    public int sizeOfMapa()
    {
        return mapas.size();
    }

    public Iterator<Mapa> iteratorMapas()
    {
        return mapas.iterator();
    }

    public boolean addMapa(Mapa m)
    {
        m.setNavegador(this);
        return mapas.add(m);
    }

    public boolean removeMapa(int idx)
    {
        return mapas.remove(mapas.get(idx));
    }
}
