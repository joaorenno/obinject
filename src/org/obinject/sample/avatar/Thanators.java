/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.obinject.sample.avatar;

import org.obinject.annotation.Persistent;

/**
 *
 * @author Dany
 */
@Persistent
class Thanators extends Ser{
    private int força;
    private int velocidade;
    private int pos_cadeia_alim;

        public int getForça() {
            return força;
        }

        public void setForça(int força) {
            this.força = força;
        }

        public int getPos_cadeia_alim() {
            return pos_cadeia_alim;
        }

        public void setPos_cadeia_alim(int pos_cadeia_alim) {
            this.pos_cadeia_alim = pos_cadeia_alim;
        }

        public int getVelocidade() {
            return velocidade;
        }

        public void setVelocidade(int velocidade) {
            this.velocidade = velocidade;
        }
   }
