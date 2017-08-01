/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.universe;

import org.obinject.annotation.Order;
import org.obinject.annotation.Number;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Sort;
import org.obinject.annotation.Unique;

/**
 *
 * @author Ferro
 */
@Persistent
public class Planet
{

   @Unique
   @Sort(number = Number.One, order = Order.First)
   @Sort(number = Number.Two, order = Order.Third)
   private String name;
   @Sort(number = Number.One, order = Order.Second)
   @Sort(number = Number.Two, order = Order.Second)
   private float latitude;
   @Sort(number = Number.One, order = Order.Third)
   @Sort(number = Number.Two, order = Order.First)
   private float longitude;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public float getLatitude()
   {
      return latitude;
   }

   public void setLatitude(float latitude)
   {
      this.latitude = latitude;
   }

   public float getLongitude()
   {
      return longitude;
   }

   public void setLongitude(float longitude)
   {
      this.longitude = longitude;
   }
   
   
}
