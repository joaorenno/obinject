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
public class Maquinario
{

   @Unique
   private String nome;
   private String tipo;
   private int peso;
   private int potencia;
   private int capacidade;

   public int getCapacidade()
   {
      return capacidade;
   }

   public void setCapacidade(int capacidade)
   {
      this.capacidade = capacidade;
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public int getPeso()
   {
      return peso;
   }

   public void setPeso(int peso)
   {
      this.peso = peso;
   }

   public int getPotencia()
   {
      return potencia;
   }

   public void setPotencia(int potencia)
   {
      this.potencia = potencia;
   }

   public String getTipo()
   {
      return tipo;
   }

   public void setTipo(String tipo)
   {
      this.tipo = tipo;
   }
}
