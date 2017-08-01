/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.locadora;

import java.util.*;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author Dionísio gr 13273
 */
@Persistent
public class Cliente
{
    @Unique
    private String login;
    private String cpf;
    private String nome;
    private List<Locacao> locacoes = new ArrayList<>();

    public String getCpf()
    {
        return cpf;
    }

    public void setCpf(String v)
    {
        cpf = v;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String v)
    {
        login = v;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String v)
    {
        nome = v;
    }

    public List<Locacao> getLocacoes()
    {
        return locacoes;
    }

    public void setLocacoes(List<Locacao> locacoes)
    {
        this.locacoes = locacoes;
    }

}
