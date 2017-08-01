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

public class AppFind {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader txtFile = null;

        //txt
        txtFile = new BufferedReader(
                new InputStreamReader(new FileInputStream("resources/prot.txt")));
        String str;
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
        ProteinOneProteina proteinProt;
        Uuid uuidResult;
        $Proteina protResult;
        StringTokenizer tok;
        for (int i = 0; i < AppGenerationProtein.maxElement; i++) {
            tok = new StringTokenizer(txtFile.readLine(), "\t");
            prot.setAminoAcids(tok.nextToken());
            entityProt = new $Proteina(prot, Uuid.fromString(tok.nextToken()));

            protResult = btreeEntity.find(entityProt.getUuid());
            if (protResult == null) {
                System.out.println("[BTREEENTITY] not found [" + i + "]=" + entityProt.getAminoAcids());
            }

            primaryProt = new UniqueOneProteina(entityProt);
            uuidResult = btree.find(primaryProt);
            if (uuidResult == null) {
                System.out.println("[BTREE] not found [" + i + "]=" + primaryProt.getAminoAcids());
            }
            proteinProt = new ProteinOneProteina(entityProt);
            uuidResult = mtree.find(proteinProt);
            if (uuidResult == null) {
                System.out.println("[MTREE] not found [" + i + "]=" + primaryProt.getAminoAcids());
            }
        }

    }
}
