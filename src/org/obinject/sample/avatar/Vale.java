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
public class Vale extends Regiao
{

   private int profundidade;
   private boolean agua;
   private List<Leonopterys> leonopterys = new ArrayList<>();
   private List<Thanators> thanators = new ArrayList<>();

   public void addLeonopterys(Leonopterys p)
   {
      leonopterys.add(p);
   }

   public boolean removeLeonopterys(Leonopterys p)
   {
      return (leonopterys.remove(p) == true);
   }

   public void addThanators(Thanators p)
   {
      thanators.add(p);
   }

   public boolean removeThanators(Thanators p)
   {
      return (thanators.remove(p) == true);
   }

   public boolean isAgua()
   {
      return agua;
   }

   public void setAgua(boolean agua)
   {
      this.agua = agua;
   }

   public int getProfundidade()
   {
      return profundidade;
   }

   public void setProfundidade(int profundidade)
   {
      this.profundidade = profundidade;
   }

   public List<Leonopterys> getLeonopterys()
   {
      return leonopterys;
   }

   public void setLeonopterys(List<Leonopterys> leonopterys)
   {
      this.leonopterys = leonopterys;
   }

   public List<Thanators> getThanators()
   {
      return thanators;
   }

   public void setThanators(List<Thanators> thanators)
   {
      this.thanators = thanators;
   }
}
