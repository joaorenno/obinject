package org.obinject.sample.f1;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

@Persistent
public class Carro
{
    @Unique
    private int numero;
    private float potencia;
    private float altBico;
    private float altAerofolio;
    private Piloto piloto;
    private Set<Engenheiro> engenheiros = new HashSet<>();
    private Set<Posicao> posicoes = new HashSet<>();

    public float getAltAerofolio()
    {
        return altAerofolio;
    }

    public void setAltAerofolio(float altAerofolio)
    {
        this.altAerofolio = altAerofolio;
    }

    public float getAltBico()
    {
        return altBico;
    }

    public void setAltBico(float altBico)
    {
        this.altBico = altBico;
    }

    public int getNumero()
    {
        return numero;
    }

    public void setNumero(int numero)
    {
        this.numero = numero;
    }

    public Piloto getPiloto()
    {
        return piloto;
    }

    public void setPiloto(Piloto piloto)
    {
        this.piloto = piloto;
    }

    public float getPotencia()
    {
        return potencia;
    }

    public void setPotencia(float potencia)
    {
        this.potencia = potencia;
    }

    public Set<Engenheiro> getEngenheiros()
    {
        return engenheiros;
    }

    public void setEngenheiros(Set<Engenheiro> engenheiros)
    {
        this.engenheiros = engenheiros;
    }

    public int sizeOfEngenheiro()
    {
        return engenheiros.size();
    }

    public Iterator<Engenheiro> iteratorEngenheiro()
    {
        return engenheiros.iterator();
    }

    public boolean addEngenheiro(Engenheiro v)
    {
        return engenheiros.add(v);
    }

    public boolean removeEngenheiro(int idx)
    {
        if (idx > engenheiros.size())
        {
            return false;
        }
        Iterator<Engenheiro> it = engenheiros.iterator();
        Engenheiro l = it.next();
        for (int i = 0; i < idx; i++)
        {
            l = it.next();
        }
        return engenheiros.remove(l);
    }

    public Set<Posicao> getPosicoes()
    {
        return posicoes;
    }

    public void setPosicoes(Set<Posicao> posicoes)
    {
        this.posicoes = posicoes;
    }

    public int sizeOfPosicao()
    {
        return engenheiros.size();
    }

    public Iterator<Posicao> iteratorPosicao()
    {
        return posicoes.iterator();
    }

    public boolean addPosicao(Posicao v)
    {
        return posicoes.add(v);
    }

    public boolean removePosicao(int idx)
    {
        if (idx > posicoes.size())
        {
            return false;
        }
        Iterator<Posicao> it = posicoes.iterator();
        Posicao l = it.next();
        for (int i = 0; i < idx; i++)
        {
            l = it.next();
        }
        return posicoes.remove(l);
    }
}
