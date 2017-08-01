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
public class Pirata extends Navio
{

    private int qtdCanhoes;
    private List<Guerra> guerras = new ArrayList<>();
    private List<Mercante> mercantes = new ArrayList<>();

    public List<Guerra> getGuerras()
    {
        return guerras;
    }

    public void setGuerras(List<Guerra> guerras)
    {
        this.guerras = guerras;
    }

    public List<Mercante> getMercantes()
    {
        return mercantes;
    }

    public void setMercantes(List<Mercante> mercantes)
    {
        this.mercantes = mercantes;
    }

    public int getQtdCanhoes()
    {
        return qtdCanhoes;
    }

    public void setQtdCanhoes(int qtdCanhoes)
    {
        this.qtdCanhoes = qtdCanhoes;
    }

    public int sizeOfGuerra()
    {
        return guerras.size();
    }

    public Iterator<Guerra> iteratorGuerra()
    {
        return guerras.iterator();
    }

    public boolean addGuerra(Guerra f)
    {
        return guerras.add(f);
    }

    public boolean removeGuerra(int idx)
    {
        return guerras.remove(guerras.get(idx));
    }

    public int sizeOfMercante()
    {
        return mercantes.size();
    }

    public Iterator<Mercante> iteratorMercante()
    {
        return mercantes.iterator();
    }

    public boolean addMercante(Mercante f)
    {
        return mercantes.add(f);
    }

    public boolean removeMercante(int idx)
    {
        return mercantes.remove(mercantes.get(idx));
    }
}
