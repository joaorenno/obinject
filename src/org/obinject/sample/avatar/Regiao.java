package org.obinject.sample.avatar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class Regiao
{

   @Unique
   private String nome;
   List<Arvore> arvores = new ArrayList<>();
   List<Jazida> jazidas = new ArrayList<>();
   List<Batalha> batalha = new ArrayList<>();

   public void addJazida(Jazida p)
   {
      jazidas.add(p);
   }

   public boolean removeJazida(Jazida p)
   {
      return (jazidas.remove(p) == true);
   }

   public void addBatalha(Batalha p)
   {
      batalha.add(p);
   }

   public boolean removeBatalha(Batalha p)
   {
      return (batalha.remove(p) == true);
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public boolean addArvore(Arvore v)
   {
      return arvores.add(v);
   }

   public Arvore removeArvore(int idx)
   {
      return arvores.remove(idx);
   }

   public int sizeOfArvore()
   {
      return arvores.size();
   }

   public Iterator<Arvore> iteratorArvore()
   {
      return arvores.iterator();
   }

   public List<Arvore> getArvores()
   {
      return arvores;
   }

   public void setArvores(List<Arvore> arvores)
   {
      this.arvores = arvores;
   }

   public List<Jazida> getJazidas()
   {
      return jazidas;
   }

   public void setJazidas(List<Jazida> jazidas)
   {
      this.jazidas = jazidas;
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