package org.obinject.sample.f1;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

@Persistent
public class Gp
{
    @Unique
    private String nome;
    private String cidade;
    private String pais;
    private Set<Posicao> posicoes = new HashSet<>();

    public String getCidade()
    {
        return cidade;
    }

    public void setCidade(String cidade)
    {
        this.cidade = cidade;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getPais()
    {
        return pais;
    }

    public void setPais(String pais)
    {
        this.pais = pais;
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
        return posicoes.size();
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
