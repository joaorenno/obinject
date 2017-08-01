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
public class Montanha extends Regiao
{

   private int altura;
   private boolean movimento;
   private List<Banshee> banshee = new ArrayList<>();

   public void addBanshee(Banshee p)
   {
      banshee.add(p);
   }

   public boolean removeBanshee(Banshee p)
   {
      return (banshee.remove(p) == true);
   }

   public int getAltura()
   {
      return altura;
   }

   public void setAltura(int altura)
   {
      this.altura = altura;
   }

   public boolean isMovimento()
   {
      return movimento;
   }

   public void setMovimento(boolean movimento)
   {
      this.movimento = movimento;
   }

   public List<Banshee> getBanshee()
   {
      return banshee;
   }

   public void setBanshee(List<Banshee> banshee)
   {
      this.banshee = banshee;
   }
}
