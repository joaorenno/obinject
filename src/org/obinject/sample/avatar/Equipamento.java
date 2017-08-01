package org.obinject.sample.avatar;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dany
 */
@Persistent
public class Equipamento {
    @Unique
    private String nome;
    private String utilidade;
    private int consumo;

    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUtilidade() {
        return utilidade;
    }

    public void setUtilidade(String utilidade) {
        this.utilidade = utilidade;
    }


}
