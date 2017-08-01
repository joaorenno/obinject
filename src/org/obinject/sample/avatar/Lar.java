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
class Lar extends Arvore{

   private int idade;
   private float altura;

   public float getAltura() {
      return altura;
   }

   public void setAltura(float altura) {
      this.altura = altura;
   }

   public int getIdade() {
      return idade;
   }

   public void setIdade(int idade) {
      this.idade = idade;
   }
}
