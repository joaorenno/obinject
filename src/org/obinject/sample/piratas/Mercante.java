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
public class Mercante extends Navio
{

    private int capacidade;
    private List<Pirata> piratas = new ArrayList<>();
    private List<Mercadoria> mercadorias = new ArrayList<>();

    public List<Mercadoria> getMercadorias()
    {
        return mercadorias;
    }

    public void setMercadorias(List<Mercadoria> mercadorias)
    {
        this.mercadorias = mercadorias;
    }

    public List<Pirata> getPiratas()
    {
        return piratas;
    }

    public void setPiratas(List<Pirata> piratas)
    {
        this.piratas = piratas;
    }

    public int getCapacidade()
    {
        return capacidade;
    }

    public void setCapacidade(int capacidade)
    {
        this.capacidade = capacidade;
    }

    public int sizeOfPirata()
    {
        return piratas.size();
    }

    public Iterator<Pirata> iteratorPirata()
    {
        return piratas.iterator();
    }

    public boolean addPirata(Pirata f)
    {
        return piratas.add(f);
    }

    public boolean removePirata(int idx)
    {
        return piratas.remove(piratas.get(idx));
    }

    public int sizeOfMercadoria()
    {
        return mercadorias.size();
    }

    public Iterator<Mercadoria> iteratorMercadoria()
    {
        return mercadorias.iterator();
    }

    public boolean addMercadoria(Mercadoria f)
    {
        return mercadorias.add(f);
    }

    public boolean removeMercadoria(int idx)
    {
        return mercadorias.remove(mercadorias.get(idx));
    }
}
