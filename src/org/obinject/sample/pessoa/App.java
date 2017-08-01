/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.pessoa;

import java.util.Random;
import org.obinject.queries.Equal;
import org.obinject.queries.Query;

/**
 *
 * @author windows
 */
public class App {
    public static Random r = new Random();
    
    public static void main(String[] args) {
//        Wrapper.create("br.edu.unifei.oo");

//        PersistentManager pm = new PersistentManager();
//        pm.getTransaction().begin();
//        for (int i = 0; i < 1000; i++) {
//            Pessoa p = new Pessoa();
//            p.setCpf(""+i);
//            p.setNome("nome"+i);
//            p.setLatitude(1/(i+1));
//            p.setLongitude(2/(i+1));
//            float v[] = new float[15];
//            for (int j = 0; j < v.length; j++) {
//                v[j] = r.nextFloat();
//            }
//            p.setCaracteristica(v);
//            pm.persist(p);
//        }
//        pm.getTransaction().commit();

        Query q = new Query();
        q.from($Pessoa.class);
        q.where(new Equal($Pessoa.nome, "nome1"));
        
        Pessoa p = (Pessoa) q.execute();
        System.out.println(p.getNome());

    }
}
