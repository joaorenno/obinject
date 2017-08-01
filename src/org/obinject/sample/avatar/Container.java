package org.obinject.sample.avatar;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dany
 */
@Persistent
public class Container
{

   @Unique
   private String nome;
   private Laboratorio cont_lab;
   private Residencia cont_res;
   private Deposito cont_dep;

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public Laboratorio getCont_lab()
   {
      return cont_lab;
   }

   public void setCont_lab(Laboratorio cont_lab)
   {
      this.cont_lab = cont_lab;
   }

   public Residencia getCont_res()
   {
      return cont_res;
   }

   public void setCont_res(Residencia cont_res)
   {
      this.cont_res = cont_res;
   }

   public Deposito getCont_dep()
   {
      return cont_dep;
   }

   public void setCont_dep(Deposito cont_dep)
   {
      this.cont_dep = cont_dep;
   }
}
