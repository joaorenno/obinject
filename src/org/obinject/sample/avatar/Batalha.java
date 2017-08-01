/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.avatar;

import java.util.*;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

/**
 *
 * @author Dany
 */
@Persistent
public class Batalha
{

   @Unique
   private int num;
   private float duracao;
   private List<Humano> humano = new ArrayList<>();
   private List<Batalha> batalha = new ArrayList<>();

   public void addColonia(Humano p)
   {
      humano.add(p);
   }

   public boolean removeColonia(Humano p)
   {
      return (humano.remove(p) == true);
   }

   public void addBatalha(Batalha p)
   {
      batalha.add(p);
   }

   public boolean removeBatalha(Batalha p)
   {
      return (batalha.remove(p) == true);
   }

   public float getDuracao()
   {
      return duracao;
   }

   public void setDuracao(float duracao)
   {
      this.duracao = duracao;
   }

   public int getNum()
   {
      return num;
   }

   public void setNum(int num)
   {
      this.num = num;
   }

   public List<Humano> getHumano()
   {
      return humano;
   }

   public void setHumano(List<Humano> humano)
   {
      this.humano = humano;
   }

   public List<Batalha> getBatalha()
   {
      return batalha;
   }

   public void setBatalha(List<Batalha> batalha)
   {
      this.batalha = batalha;
   }
   
}
