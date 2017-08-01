package org.obinject.sample.avatar;

import java.util.ArrayList;
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
public class Colonia
{

   @Unique
   private String nome;
   @Unique
   private Empresa empresa;
   private String apelido;
   private boolean pressurizadas;
   private List<Container> containers = new ArrayList<>();

   public void addColonia(Container p)
   {
      containers.add(p);
   }

   public boolean removeColonia(Container p)
   {
      return (containers.remove(p) == true);
   }

   public Empresa getEmpresa()
   {
      return empresa;
   }

   public void setEmpresa(Empresa empresa)
   {
      this.empresa = empresa;
   }

   public String getApelido()
   {
      return apelido;
   }

   public void setApelido(String apelido)
   {
      this.apelido = apelido;
   }

   public String getNome()
   {
      return nome;
   }

   public void setNome(String nome)
   {
      this.nome = nome;
   }

   public boolean isPressurizadas()
   {
      return pressurizadas;
   }

   public void setPressurizadas(boolean pressurizadas)
   {
      this.pressurizadas = pressurizadas;
   }

   public List<Container> getContainers()
   {
      return containers;
   }

   public void setContainers(List<Container> containers)
   {
      this.containers = containers;
   }
}
