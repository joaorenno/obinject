/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.avatar;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

@Persistent
public class Conexao
{
   @Unique
   private String tipo;
   private Ser ser1;
   private Ser ser2;

   public Ser getSer1()
   {
      return ser1;
   }

   public void setSer1(Ser ser1)
   {
      this.ser1 = ser1;
   }

   public Ser getSer2()
   {
      return ser2;
   }

   public void setSer2(Ser ser2)
   {
      this.ser2 = ser2;
   }

   public String getTipo()
   {
      return tipo;
   }

   public void setTipo(String tipo)
   {
      this.tipo = tipo;
   }
}
