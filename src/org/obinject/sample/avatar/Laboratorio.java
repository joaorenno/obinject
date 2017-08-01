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
public class Laboratorio extends Container
{

   private String finalidade;
   private char sigla;

   public String getFinalidade()
   {
      return finalidade;
   }

   public void setFinalidade(String finalidade)
   {
      this.finalidade = finalidade;
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
