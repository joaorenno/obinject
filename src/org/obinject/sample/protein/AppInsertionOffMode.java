/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.obinject.sample.protein;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author enzo
 */
public class AppInsertionOffMode {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader txtFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/prot.txt")));
        Proteina prot = new Proteina();
        String str;
        StringTokenizer tok;
        PersistentManager pm = PersistentManagerFactory.createPersistentManager();
        pm.getTransaction().begin();
        for (int i = 0; i < AppGenerationProtein.maxElement; i++) {            
            if(i % 1000 == 0){
                System.out.print(i + ",");
            }
            tok = new StringTokenizer(txtFile.readLine(), "\t");
            prot.setAminoAcids(tok.nextToken());
            pm.inject(prot);
        }
        pm.getTransaction().commit();
        
        
    }
    
}
