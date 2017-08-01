/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.inception;

import org.obinject.annotation.Persistent;

/**
 *
 * @author windows
 */
@Persistent
public class Turista extends Sonhador{
    
    private float pagamento;

    public float getPagamento() {
        return pagamento;
    }

    public void setPagamento(float pagamento) {
        this.pagamento = pagamento;
    }
    
}
