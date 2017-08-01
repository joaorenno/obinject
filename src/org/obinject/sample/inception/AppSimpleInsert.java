/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.inception;

import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author luiz
 */
public class AppSimpleInsert {

    public static void main(String[] args) {

        PersistentManager pm = PersistentManagerFactory.createPersistentManager();
        pm.getTransaction().begin();

        Pessoa pessoa = new Pessoa();
        pm.inject(pessoa);

        Totem totem = new Totem();
        pm.inject(totem);

        Alvo alvo = new Alvo();
        pm.inject(alvo);

        Armador armador = new Armador();
        pm.inject(armador);

        Arquiteto arquiteto = new Arquiteto();
        pm.inject(arquiteto);

        Extrator extrator = new Extrator();
        pm.inject(extrator);

        Falsificador falsificador = new Falsificador();
        pm.inject(falsificador);

        Missao missao = new Missao();
        pm.inject(missao);

        Pasiv pasiv = new Pasiv();
        pm.inject(pasiv);

        Quimico quimico = new Quimico();
        pm.inject(quimico);

        Sombra sombra = new Sombra();
        pm.inject(sombra);

        Sonhador sonhador = new Sonhador();
        pm.inject(sonhador);

        Sonho sonho = new Sonho();
        pm.inject(sonho);

        Turista turista = new Turista();
        pm.inject(turista);

        Turista t = new Turista();
        pm.inject(t);

        pm.getTransaction().commit();
    }
}
