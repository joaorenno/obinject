/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sbbd2013.uniprot;

/**
 *
 * @author luiz
 */
public class Protein
{

    private String aminoAcids = "";

    public Protein()
    {
    }

    public Protein(String aminoAcids)
    {
        this.aminoAcids = aminoAcids;
    }

    public String getAminoAcids()
    {
        return aminoAcids;
    }

    public void setAminoAcids(String aminoAcids)
    {
        this.aminoAcids = aminoAcids;
    }
}
