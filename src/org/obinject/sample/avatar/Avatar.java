/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.avatar;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author Dany
 */
@Persistent
public class Avatar
{

   @Unique
   int id;
   private boolean control;
   private Humano humano;
   private Navi navi;
   private Pesquisa pesquisa;

   public Avatar()
   {
   }

   public int getId()
   {
      return id;
   }

   public void setId(int id)
   {
      this.id = id;
   }

   public Humano getHumano()
   {
      return humano;
   }

   public void setHumano(Humano humano)
   {
      this.humano = humano;
   }

   public Navi getNavi()
   {
      return navi;
   }

   public void setNavi(Navi navi)
   {
      this.navi = navi;
   }

   public Pesquisa getPesquisa()
   {
      return pesquisa;
   }

   public void setPesquisa(Pesquisa pesquisa)
   {
      this.pesquisa = pesquisa;
   }

   public boolean isControl()
   {
      return control;
   }

   public void setControl(boolean control)
   {
      this.control = control;
   }
}
