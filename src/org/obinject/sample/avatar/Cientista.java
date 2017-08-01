/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.avatar;

import java.util.*;
import org.obinject.annotation.Persistent;

/**
 *
 * @author Dany
 */
@Persistent
public class Cientista extends Humano
{

   private String formacao;
   private List<Pesquisa> pesquisas = new ArrayList<>();

   public void addPesquisa(Pesquisa p)
   {
      pesquisas.add(p);
   }

   public boolean removePesquisa(Pesquisa p)
   {
      return (pesquisas.remove(p) == true);
   }

   public String getFormacao()
   {
      return formacao;
   }

   public void setFormacao(String formacao)
   {
      this.formacao = formacao;
   }

   public List<Pesquisa> getPesquisas()
   {
      return pesquisas;
   }

   public void setPesquisas(List<Pesquisa> pesquisas)
   {
      this.pesquisas = pesquisas;
   }
   
   
}
