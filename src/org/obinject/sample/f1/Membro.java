package org.obinject.sample.f1;

import java.util.Date;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

@Persistent
public class Membro
{
    @Unique
    private String nome;
    private Date contrato;

    public Date getContrato()
    {
        return contrato;
    }

    public void setContrato(Date contrato)
    {
        this.contrato = contrato;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }
}
