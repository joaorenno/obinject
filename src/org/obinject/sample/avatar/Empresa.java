package org.obinject.sample.avatar;

import java.util.*;
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
public class Empresa
{

   private String nome;
   @Unique
   private String registro;
   private List<Colonia> colonia = new ArrayList<>();

   public void addColonia(Colonia p)
   {
      colonia.add(p);
   }

   public boolean removeColonia(Colonia p)
   {
      return (colonia.remove(p) == true);
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public String getRegistro()
   {
      return registro;
   }

   public void setRegistro(String registro)
   {
      this.registro = registro;
   }

   public List<Colonia> getColonia()
   {
      return colonia;
   }

   public void setColonia(List<Colonia> colonia)
   {
      this.colonia = colonia;
   }
   
}
