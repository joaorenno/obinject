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
public class Guerra extends Navio
{

    private int qtdCanhoes;
    private List<Pirata> piratas = new ArrayList<>();

    public List<Pirata> getPiratas()
    {
        return piratas;
    }

    public void setPiratas(List<Pirata> piratas)
    {
        this.piratas = piratas;
    }

    public int getQtdCanhoes()
    {
        return qtdCanhoes;
    }

    public void setQtdCanhoes(int qtdCanhoes)
    {
        this.qtdCanhoes = qtdCanhoes;
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
}
