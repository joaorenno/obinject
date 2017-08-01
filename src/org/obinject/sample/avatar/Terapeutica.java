/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.avatar;

import org.obinject.annotation.Persistent;

/**
 *
 * @author enzo
 */
@Persistent
class Terapeutica extends Arvore{

   private boolean semente;

   public boolean isSemente() {
      return semente;
   }

   public void setSemente(boolean semente) {
      this.semente = semente;
   }
}
