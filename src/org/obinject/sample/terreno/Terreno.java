package org.obinject.sample.terreno;

import org.obinject.annotation.Number;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Edition;
import org.obinject.annotation.Extension;
import org.obinject.annotation.Order;
import org.obinject.annotation.Point;
import org.obinject.annotation.Origin;
import org.obinject.annotation.Sort;
import org.obinject.annotation.Coordinate;
import org.obinject.annotation.Unique;

@Persistent
public class Terreno {

    @Unique
    private long registro;
    @Sort
    @Edition
    private String proprietario;
    private String cidade;
    private String bairro;
    private String logradouro;
    private float valorImovel;
    private int numero;
    @Point
    @Origin
    private float coordenadaOrigem[] = new float[2];
    @Extension
    private float coordenadaExtensao[] = new float[2];

    private Mapa mapa;

    public long getRegistro() {
        return registro;
    }

    public void setRegistro(long registro) {
        this.registro = registro;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public float getValorImovel() {
        return valorImovel;
    }

    public void setValorImovel(float valorImovel) {
        this.valorImovel = valorImovel;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public float[] getCoordenadaOrigem() {
        return coordenadaOrigem;
    }

    public void setCoordenadaOrigem(float[] coordenadaOrigem) {
        this.coordenadaOrigem = coordenadaOrigem;
    }

    public float[] getCoordenadaExtensao() {
        return coordenadaExtensao;
    }

    public void setCoordenadaExtensao(float[] coordenadaExtensao) {
        this.coordenadaExtensao = coordenadaExtensao;
    }

    public Mapa getMapa() {
        return mapa;
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

}
