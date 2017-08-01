package org.obinject.scaffold;

import org.obinject.annotation.Persistent;
import org.obinject.annotation.Scaffold;
import org.obinject.annotation.Select;
import org.obinject.annotation.Unique;

@Scaffold
@Persistent
public class Carro {
    
    @Unique
    private String placa;   
    
    @Select(items = {"Fiat", "Ford", "Audiu"})
    private String marca;
    
    //gets e sets
}