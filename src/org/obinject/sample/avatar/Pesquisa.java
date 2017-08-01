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
public class Pesquisa
{

   @Unique
   private String nome;
   private boolean resultado;
   private Avatar avatar = new Avatar();
   private List<Cientista> cientistas = new ArrayList<>();
   private List<Equipamento> equipamentos = new ArrayList<>();

   public void addEquipamento(Equipamento p)
   {
      equipamentos.add(p);
   }

   public boolean removeEquipamento(Equipamento p)
   {
      return (equipamentos.remove(p) == true);
   }

   public void addCientista(Cientista p)
   {
      cientistas.add(p);
   }

   public boolean removeCientista(Cientista p)
   {
      return (cientistas.remove(p) == true);
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public boolean isResultado()
   {
      return resultado;
   }

   public void setResultado(boolean resultado)
   {
      this.resultado = resultado;
   }

   public Avatar getAvatar()
   {
      return avatar;
   }

   public List<Cientista> getCientistas()
   {
      return cientistas;
   }

   public void setCientistas(List<Cientista> cientistas)
   {
      this.cientistas = cientistas;
   }

   public List<Equipamento> getEquipamentos()
   {
      return equipamentos;
   }

   public void setEquipamentos(List<Equipamento> equipamentos)
   {
      this.equipamentos = equipamentos;
   }

   public void setAvatar(Avatar avatar)
   {
      this.avatar = avatar;
   }
}
