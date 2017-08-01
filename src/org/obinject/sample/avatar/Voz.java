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
class Voz extends Arvore{

   private boolean escutar;

   public boolean isEscutar() {
      return escutar;
   }

   public void setEscutar(boolean escutar) {
      this.escutar = escutar;
   }
}
