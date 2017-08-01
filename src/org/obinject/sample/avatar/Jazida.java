/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.avatar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Unique;

@Persistent
public class Jazida
{

   private int base;
   private int altura;
   @Unique
   private int latitude;
   @Unique
   private int longitude;
   private boolean povoada;
   private boolean explorada;
   private Regiao regiao;
   private List<Colonia> colonias = new ArrayList<>();
   private List<Maquinario> maquinas = new ArrayList<>();
   private List<Humano> humanos = new ArrayList<>();

   public int getAltura()
   {
      return altura;
   }

   public void setAltura(int altura)
   {
      this.altura = altura;
   }

   public int getBase()
   {
      return base;
   }

   public void setBase(int base)
   {
      this.base = base;
   }

   public boolean isExplorada()
   {
      return explorada;
   }

   public void setExplorada(Boolean explorada)
   {
      this.explorada = explorada;
   }

   public int getLatitude()
   {
      return latitude;
   }

   public void setLatitude(int latitude)
   {
      this.latitude = latitude;
   }

   public int getLongitude()
   {
      return longitude;
   }

   public void setLongitude(int longitude)
   {
      this.longitude = longitude;
   }

   public boolean isPovoada()
   {
      return povoada;
   }

   public void setPovoada(Boolean povoada)
   {
      this.povoada = povoada;
   }

   public void addColonia(Colonia p)
   {
      colonias.add(p);
   }

   public boolean removeColonia(Colonia p)
   {
      return (colonias.remove(p) == true);
   }

   public int sizeOfColonia()
   {
      return colonias.size();
   }

   public Iterator<Colonia> iteratorColonia()
   {
      return colonias.iterator();
   }

   public void addColonia(Maquinario p)
   {
      maquinas.add(p);
   }

   public boolean removeColonia(Maquinario p)
   {
      return (maquinas.remove(p) == true);
   }

   public void addColonia(Humano p)
   {
      humanos.add(p);
   }

   public boolean removeColonia(Humano p)
   {
      return (humanos.remove(p) == true);
   }

   public Regiao getRegiao()
   {
      return regiao;
   }

   public void setRegiao(Regiao regiao)
   {
      this.regiao = regiao;
   }

   public List<Colonia> getColonias()
   {
      return colonias;
   }

   public void setColonias(List<Colonia> colonias)
   {
      this.colonias = colonias;
   }

   public List<Maquinario> getMaquinas()
   {
      return maquinas;
   }

   public void setMaquinas(List<Maquinario> maquinas)
   {
      this.maquinas = maquinas;
   }

   public List<Humano> getHumanos()
   {
      return humanos;
   }

   public void setHumanos(List<Humano> humanos)
   {
      this.humanos = humanos;
   }
}
