/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.avatar;

import org.obinject.annotation.Persistent;

/**
 *
 * @author Dany
 */
@Persistent
public class Deposito extends Container
{
   private char sigla;
   private String conteudo;

   public String getConteudo()
   {
      return conteudo;
   }

   public void setConteudo(String conteudo)
   {
      this.conteudo = conteudo;
   }

   public char getSigla()
   {
      return sigla;
   }

   public void setSigla(char sigla)
   {
      this.sigla = sigla;
   }
}
