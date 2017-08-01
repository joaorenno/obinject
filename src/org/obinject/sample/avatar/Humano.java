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
public class Humano
{

   @Unique
   private String cpf;
   private String nome;
   private boolean morte;
   private Militar hum_mil;
   private Minerador hum_min;
   private Cientista hum_cien;

   public String getCpf()
   {
      return cpf;
   }

   public void setCpf(String cpf)
   {
      this.cpf = cpf;
   }

   public boolean isMorte()
   {
      return morte;
   }

   public void setMorte(boolean morte)
   {
      this.morte = morte;
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public Militar getHum_mil()
   {
      return hum_mil;
   }

   public void setHum_mil(Militar hum_mil)
   {
      this.hum_mil = hum_mil;
   }

   public Minerador getHum_min()
   {
      return hum_min;
   }

   public void setHum_min(Minerador hum_min)
   {
      this.hum_min = hum_min;
   }

   public Cientista getHum_cien()
   {
      return hum_cien;
   }

   public void setHum_cien(Cientista hum_cien)
   {
      this.hum_cien = hum_cien;
   }
   
}