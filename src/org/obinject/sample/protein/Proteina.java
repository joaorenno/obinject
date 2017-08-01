/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.protein;

import org.obinject.annotation.Unique;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Protein;


/**
 *
 * @author luiz
 */
@Persistent(blockSize = 8192)
public class Proteina
{

    @Unique
    @Protein
    private String aminoAcids = "";

    public Proteina()
    {
    }

    public Proteina(String aminoAcids)
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
