package org.obinject.sample.avatar;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dany
 */
@Persistent
public class Ser
{

   @Unique
   String nome;
   private String habitat;
   private Conexao conexao;

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public Conexao getConexao()
   {
      return conexao;
   }

   public void setConexao(Conexao conexao)
   {
      this.conexao = conexao;
   }

   public String getHabitat()
   {
      return habitat;
   }

   public void setHabitat(String habitat)
   {
      this.habitat = habitat;
   }
}