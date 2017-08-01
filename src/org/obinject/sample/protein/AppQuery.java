/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.protein;

import java.util.Collection;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;
import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;
import org.obinject.queries.KNearestNeighbor;
import org.obinject.queries.Query;
import org.obinject.queries.RangeQueryMTree;

/**
 *
 * @author system
 */
public class AppQuery {

    private static Random rand = new Random();
    private static int maxSize = 32;
    private static int maxElement = 5000;

    private static String generatedAmino() {
        int size = rand.nextInt(AppQuery.maxSize - 10) + 10;
        char amino[] = {
            'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H', 'I', 'L', 'K', 'M', 'F', 'P', 'S', 'T', 'W', 'Y', 'V'
        };
        char protein[] = new char[size];
        for (int i = 0; i < protein.length; i++) {
            protein[i] = amino[rand.nextInt(amino.length)];
        }
        return String.valueOf(protein);
    }

    private static class ProteinComparator implements Comparator<Proteina> {

        @Override
        public int compare(Proteina o1, Proteina o2) {
            return o1.getAminoAcids().compareTo(o2.getAminoAcids());
        }
    }

    public static void main(String[] args) {

        PersistentManager pm = PersistentManagerFactory.createPersistentManager();
        for (int i = 0; i < maxElement; i++) {
            Proteina p = new Proteina(generatedAmino());
            pm.getTransaction().begin();
            pm.inject(p);
            pm.getTransaction().commit();
        }

        ProteinOneProteina efp1 = new ProteinOneProteina();
        efp1.setAminoAcids("IDADGHIFARQCVQWYILPST");
        RangeQueryMTree<ProteinOneProteina> rqmt = new RangeQueryMTree<ProteinOneProteina>
                (ProteinOneProteina.proteinOneProteinaStructure, efp1, 17) {
        };
        Collection<ProteinOneProteina> l1 = rqmt.solve();
        for (ProteinOneProteina p : l1) {
            System.out.println("RQ  prot=" + p.getAminoAcids() + " dist="
                    + p.getPreservedDistance());
        }

        Query q2 = new Query();
        q2.where(new KNearestNeighbor($Proteina.aminoAcids, "IDADGHIFARQCVQWYILPST", l1.size()));
        Collection<ProteinOneProteina> l2 = q2.execute();
        for (ProteinOneProteina p : l2) {
            System.out.println("KNN prot=" + p.getAminoAcids() + " dist="
                    + ((ProteinOneProteina) p).getPreservedDistance());
        }

        TreeSet<Proteina> set1 = new TreeSet<>(new ProteinComparator());
        set1.addAll(l1);
        TreeSet<Proteina> set2 = new TreeSet<>(new ProteinComparator());
        set2.addAll(l2);

        if (set1.containsAll(set2)) {
            System.out.println("range contém knn");
        } else {
            for (Proteina p : set1) {
                System.out.println("RQ  prot=" + p.getAminoAcids() + " dist="
                        + ((ProteinOneProteina) p).getPreservedDistance());
            }
            for (Proteina p : set2) {
                System.out.println("KNN prot=" + p.getAminoAcids() + " dist="
                        + ((ProteinOneProteina) p).getPreservedDistance());
            }
        }

    }
}
