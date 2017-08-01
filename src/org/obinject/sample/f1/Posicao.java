package org.obinject.sample.f1;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

@Persistent
public class Posicao
{
    @Unique
    private int codigo;
    private int numero;
    private int tempoCorrida;
    private int qtdParada;
    private Gp gp;
    private Carro carro;

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public Gp getGp()
    {
        return gp;
    }

    public void setGp(Gp gp)
    {
        this.gp = gp;
    }

    public int getNumero()
    {
        return numero;
    }

    public void setNumero(int numero)
    {
        this.numero = numero;
    }

    public int getQtdParada()
    {
        return qtdParada;
    }

    public void setQtdParada(int qtdParada)
    {
        this.qtdParada = qtdParada;
    }

    public int getTempoCorrida()
    {
        return tempoCorrida;
    }

    public void setTempoCorrida(int tempoCorrida)
    {
        this.tempoCorrida = tempoCorrida;
    }

    public Carro getCarro()
    {
        return carro;
    }

    public void setCarro(Carro carro)
    {
        this.carro = carro;
    }
}
