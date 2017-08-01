package org.obinject.sample.f1;

import org.obinject.annotation.Persistent;

@Persistent
public class Engenheiro extends Membro
{

    private String crea;
    private Carro carro;

    public Carro getCarro()
    {
        return carro;
    }

    public void setCarro(Carro carro)
    {
        this.carro = carro;
    }

    public String getCrea()
    {
        return crea;
    }

    public void setCrea(String crea)
    {
        this.crea = crea;
    }
}
