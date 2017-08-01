/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.avatar;

import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author windows
 */
public class AppSimpleInsert {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args)
   {
      PersistentManager pm = PersistentManagerFactory.createPersistentManager();
      pm.getTransaction().begin();

      Alma alma = new Alma();
      pm.inject(alma);

      Arvore arvore = new Arvore();
      pm.inject(arvore);

      Avatar avatar = new Avatar();
      pm.inject(avatar);

      Banshee banshee = new Banshee();
      pm.inject(banshee);
      
      Batalha batalha = new Batalha();
      pm.inject(batalha);
      
      Cientista cientista = new Cientista();
      pm.inject(cientista);

      Colonia colonia = new Colonia();
      pm.inject(colonia);

      Conexao conexao = new Conexao();
      pm.inject(conexao);

      Container container = new Container();
      pm.inject(container);
      
      Deposito deposito = new Deposito();
      pm.inject(deposito);
      
      Direhorses direhorses = new Direhorses();
      pm.inject(direhorses);
      
      Empresa empresa = new Empresa();
      pm.inject(empresa);
      
      Equipamento equipamento = new Equipamento();
      pm.inject(equipamento);

      Humano humano = new Humano();
      pm.inject(humano);

      Jazida jazida = new Jazida();
      pm.inject(jazida);
      
      Laboratorio laboratorio = new Laboratorio();
      pm.inject(laboratorio);
      
      Lar lar = new Lar();
      pm.inject(lar);
      
      Leonopterys leonopterys = new Leonopterys();
      pm.inject(leonopterys);
      
      Maquinario maquinario = new Maquinario();
      pm.inject(maquinario);

      Militar militar = new Militar();
      pm.inject(militar);

      Minerador minerador = new Minerador();
      pm.inject(minerador);

      Montanha montanha = new Montanha();
      pm.inject(montanha);

      Navi navi = new Navi();
      pm.inject(navi);

      Pesquisa pesquisa = new Pesquisa();
      pm.inject(pesquisa);

      Planicie planicie = new Planicie();
      pm.inject(planicie);
      
      Regiao regiao = new Regiao();
      pm.inject(regiao);
      
      Residencia residencia = new Residencia();
      pm.inject(residencia);
      
      Ser ser = new Ser();
      pm.inject(ser);

      Terapeutica terapeutica = new Terapeutica();
      pm.inject(terapeutica);
      
      Thanators thanators = new Thanators();
      pm.inject(thanators);
      
      Vale vale = new Vale();
      pm.inject(vale);
      
      Voz voz = new Voz();
      pm.inject(voz);

      pm.getTransaction().commit();
   }
    
}
