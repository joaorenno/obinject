/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.avatar;

import java.util.*;
import org.obinject.annotation.Persistent;

/**
 *
 * @author Dany
 */
@Persistent
public class Navi extends Ser
{

   private String funcao;
   private String cla;
   private String bioluminescencia;
   private float altura;
   private float cauda;
   List<Arvore> arvores = new ArrayList<>();

   public void addArvore(Arvore p)
   {
      arvores.add(p);
   }

   public boolean removeArvore(Arvore p)
   {
      return (arvores.remove(p) == true);
   }

   public float getAltura()
   {
      return altura;
   }

   public void setAltura(float altura)
   {
      this.altura = altura;
   }

   public String getBioluminescencia()
   {
      return bioluminescencia;
   }

   public void setBioluminescencia(String bioluminescencia)
   {
      this.bioluminescencia = bioluminescencia;
   }

   public float getCauda()
   {
      return cauda;
   }

   public void setCauda(float cauda)
   {
      this.cauda = cauda;
   }

   public String getCla()
   {
      return cla;
   }

   public void setCla(String cla)
   {
      this.cla = cla;
   }

   public String getFuncao()
   {
      return funcao;
   }

   public void setFuncao(String funcao)
   {
      this.funcao = funcao;
   }

   @Override
   public String getNome()
   {
      return nome;
   }

   @Override
   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public List<Arvore> getArvores()
   {
      return arvores;
   }

   public void setArvores(List<Arvore> arvores)
   {
      this.arvores = arvores;
   }
}
