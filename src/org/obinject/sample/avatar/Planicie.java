/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.avatar;

import java.util.ArrayList;
import java.util.List;
import org.obinject.annotation.Persistent;

/**
 *
 * @author Dany
 */
@Persistent
public class Planicie extends Regiao
{

   private boolean rios;
   private boolean lagos;
   private List<Direhorses> direhorses = new ArrayList<>();

   public void addDirehorses(Direhorses p)
   {
      direhorses.add(p);
   }

   public boolean removeDirehorses(Direhorses p)
   {
      return (direhorses.remove(p) == true);
   }

   public boolean isLagos()
   {
      return lagos;
   }

   public void setLagos(boolean lagos)
   {
      this.lagos = lagos;
   }

   public boolean isRios()
   {
      return rios;
   }

   public void setRios(boolean rios)
   {
      this.rios = rios;
   }

   public List<Direhorses> getDirehorses()
   {
      return direhorses;
   }

   public void setDirehorses(List<Direhorses> direhorses)
   {
      this.direhorses = direhorses;
   }
}
