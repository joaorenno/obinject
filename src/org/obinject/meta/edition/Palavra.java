/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.meta.edition;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Edition;
import org.obinject.annotation.Unique;

/**
 *
 * @author luiz
 */
@Persistent
public class Palavra {

    @Edition
    private String palavra;
    @Edition
    private String antonimo;
    @Edition
    private String sinonimo;
    @Unique
    private int codigo;

    public String getPalavra()
    {
	return palavra;
    }

    public void setPalavra(String palavra)
    {
	this.palavra = palavra;
    }

    public int getCodigo()
    {
	return codigo;
    }

    public void setCodigo(int codigo)
    {
	this.codigo = codigo;
    }

    public String getAntonimo()
    {
	return antonimo;
    }

    public void setAntonimo(String antonimo)
    {
	this.antonimo = antonimo;
    }

    public String getSinonimo()
    {
	return sinonimo;
    }

    public void setSinonimo(String sinonimo)
    {
	this.sinonimo = sinonimo;
    }
    
    
    
}
