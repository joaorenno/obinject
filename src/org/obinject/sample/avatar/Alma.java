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
class Alma extends Arvore
{

   private boolean Eywa;

   public boolean isEywa()
   {
      return Eywa;
   }

   public void setEywa(boolean Eywa)
   {
      this.Eywa = Eywa;
   }
}
