/*
 To change this template, choose Tools | Templates
 and open the template in the editor.
 */
package org.obinject.sample.protein;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import org.obinject.device.File;
import org.obinject.meta.Uuid;
import org.obinject.storage.BTree;
import org.obinject.storage.BTreeEntity;
import org.obinject.storage.MTree;

public class AppInsertion {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //txt
        BufferedReader txtFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/prot.txt")));
        
        File f1 = new File("EntityProtein.btree", 4096);
        BTreeEntity<$Proteina> btreeEntity = new BTreeEntity<$Proteina>(f1) {
        };
        
        File f2 = new File("UniqueOneProtein.btree", 4096);
        BTree<UniqueOneProteina> btree = new BTree<UniqueOneProteina>(f2) {
        };

        File f3 = new File("EditionOneProtein.mtree", 4096);
        MTree<ProteinOneProteina> mtree = new MTree<ProteinOneProteina>(f3) {
        };

        Proteina prot = new Proteina();
        $Proteina entityProt;
        UniqueOneProteina primaryProt;
        ProteinOneProteina editionProt;

        StringTokenizer tok;
        for (int i = 0; i < AppGenerationProtein.maxElement; i++) {            
            if(i % 1000 == 0){
                System.out.print(i + ",");
            }
            tok = new StringTokenizer(txtFile.readLine(), "\t");
            prot.setAminoAcids(tok.nextToken());
            entityProt = new $Proteina(prot, Uuid.fromString(tok.nextToken()));
            btreeEntity.add(entityProt);            
            primaryProt = new UniqueOneProteina(entityProt);
            btree.add(primaryProt);
            editionProt = new ProteinOneProteina(entityProt);
            mtree.add(editionProt);
        }

    }
}
