/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.terreno;

import java.util.ArrayList;
import java.util.List;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author system
 */
@Persistent
public class Mapa {

    @Unique
    private String nome;
    private String utilidade;
    private List<Terreno> terrenos = new ArrayList<>();

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

    public List<Terreno> getTerrenos() {
        return terrenos;
    }

    public void setTerrenos(List<Terreno> terrenos) {
        this.terrenos = terrenos;
    }
    
    
}
