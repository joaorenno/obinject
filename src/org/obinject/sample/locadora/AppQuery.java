/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.locadora;

import java.util.Collection;
import org.obinject.queries.Equal;
import org.obinject.queries.Query;

/**
 *
 * @author windows
 */
public class AppQuery {

    public static void main(String[] args) {
        Query q = new Query();
        q.where(new Equal($Locacao.id, 1));
        Collection<Locacao> lista = q.execute();
        for (Locacao l : lista) {
            System.out.println("LOCAÇÃO");
            System.out.println("código: " + l.getId());
            System.out.println("data: " + l.getDataLocacao());
            System.out.println("CLIENTE");
            System.out.println("cpf: " + l.getCliente().getCpf());
            System.out.println("login: " + l.getCliente().getLogin());
            System.out.println("nome: " + l.getCliente().getNome());
            System.out.println("FILME");
            for (Filme f : l.getFilmes()) {
                System.out.println("[" + f.getCodigo() + "] nome: " + f.getNome());
            }
        }
    }
}
