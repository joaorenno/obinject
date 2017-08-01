package org.obinject.sample.f1;

import org.obinject.annotation.Persistent;

@Persistent
public class Piloto extends Membro
{

    private double peso;
    private double altura;
    public Carro carro;

    public double getAltura()
    {
        return altura;
    }

    public void setAltura(double altura)
    {
        this.altura = altura;
    }

    public Carro getCarro()
    {
        return carro;
    }

    public void setCarro(Carro carro)
    {
        this.carro = carro;
    }

    public double getPeso()
    {
        return peso;
    }

    public void setPeso(double peso)
    {
        this.peso = peso;
    }
}
