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
public class Tripulante
{
    @Unique
    private String nome;
    private String nacionalidade;
    private String apelido;
    private Navio navio;

    public String getApelido()
    {
        return apelido;
    }

    public void setApelido(String apelido)
    {
        this.apelido = apelido;
    }

    public String getNacionalidade()
    {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade)
    {
        this.nacionalidade = nacionalidade;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }
    public Navio getNavio() {
        return navio;
    }

    public void setNavio(Navio navio) {
        this.navio = navio;
    }
}
