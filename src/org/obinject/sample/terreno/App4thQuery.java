package org.obinject.sample.terreno;

import java.util.Collection;
import org.obinject.queries.Equal;
import org.obinject.queries.Query;

public class App4thQuery {

    public static void main(String[] args) {
        int n = 0;
        
        Query q1 = new Query();
        q1.where(new Equal($Terreno.registro, 1L)).or(new Equal($Terreno.registro, 2L));
        Collection<Terreno> res1 = q1.execute();
        for (Terreno t1 : res1) {
            System.out.println("===== query 1 : tupla " + n + " ====");
            System.out.println("Registro: " + t1.getRegistro());
            System.out.println("Mapa: " + t1.getMapa().getNome());
            float[] terrenoCoord = t1.getCoordenadaExtensao();
            for (int i = 0; i < terrenoCoord.length; i++) {
                System.out.println("coord" + (i + 1) + ": " + terrenoCoord[i]);
            }
            n++;
        }

        Query q2 = new Query();
        q2.where(new Equal($Mapa.nome, "Itajuba"));
        Collection<Mapa> res2 = q2.execute();
        n = 0;
        for (Mapa mapa : res2) {
            System.out.println("===== query 2 : tupla " + n + " ====");
            System.out.println("Registro: "+mapa.getNome());
            System.out.println("Utilidade: "+mapa.getUtilidade());
            System.out.print("Terreno: ");
            for (Terreno t2 : mapa.getTerrenos()) {
                System.out.print("[registro:" + t2.getRegistro());
                System.out.print(", bairro:" + t2.getBairro() + "]");
            }
            System.out.println();
            n++;
        }
    }
}
