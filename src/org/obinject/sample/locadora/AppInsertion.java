/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.locadora;

import java.util.Date;
import java.util.Random;
import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author aluno11
 */
public class AppInsertion {

    public static Random rand = new Random();
    
    public static void main(String[] args) {

        PersistentManager pm = PersistentManagerFactory.createPersistentManager();
        pm.getTransaction().begin();

        //filme
        Filme f[] = new Filme[10];
        for (int i = 0; i < 10; i++) {
            f[i] = new Filme();
            f[i].setCodigo(i);
            f[i].setNome("HP " + (i + 1));
            f[i].setPreco(rand.nextDouble()*3);
            pm.inject(f[i]);
        }

        //Cliente
        String nome[] = {
            "ze", "joao", "maria", "toni", "ana"
        };
        Cliente c[] = new Cliente[5];
        for (int i = 0; i < 5; i++) {
            c[i] = new Cliente();
            c[i].setLogin(nome[i]);
            c[i].setNome(nome[i]);
            c[i].setCpf("" + i);
            pm.inject(c[i]);
        }

        //filme
        for (int i = 0; i < 20; i++) {
            Locacao l = new Locacao();
            l.setId(i);
            l.setDataLocacao(new Date());
            int idx = rand.nextInt(5);
            l.setCliente(c[idx]);
            int total = rand.nextInt(10);
            for (int j = 0; j < total; j++) {
                l.getFilmes().add(f[rand.nextInt(10)]);
            }
            pm.inject(l);

        }

        pm.getTransaction().commit();

    }
}
