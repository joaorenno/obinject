/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.meu;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author windows
 */
public class AppInsertion {
    private static int maxObject = 100000;
    private static Random rand = new Random();
    private static int maxLin = 13;
    private static int maxCol = 7;
    private static int count = 0;

    private static String stringGenerator()
    {
	int size = rand.nextInt(25) + 5;
	char[] ch = new char[size];
	for (int i = 0; i < size; i++)
	{
	    ch[i] = (char) ((rand.nextInt(26)) + 65);
	}
	return new String(ch);
    }

    private static List<MeuExemplo> CollectionGenerator()
    {
	List<MeuExemplo> res = new ArrayList<>(rand.nextInt(5));
	for (int i = 0; i < res.size(); i++)
	{
	    res.add(AllTypesGenerator());
	    count++;
	}
	return res;
    }

    private static MeuExemplo AllTypesGenerator()
    {
	MeuExemplo res = new MeuExemplo();
	res.setCampo1(rand.nextBoolean());
	res.setCampo2((byte) rand.nextInt());
	res.setCampo3((char) rand.nextInt());

	GregorianCalendar gc = new GregorianCalendar();
	gc.setTime(new Date(rand.nextInt()));
	res.setCampo4(gc);

	res.setCampo5(new Date(rand.nextInt()));
	res.setCampo6(rand.nextDouble());
	res.setCampo7(rand.nextFloat());
	res.setCampo8(rand.nextInt());
	res.setCampo9(rand.nextLong());
	res.setCampo10((short) rand.nextInt());
	res.setCampo12(stringGenerator());
        res.getCampo13()[0] = rand.nextDouble();
        res.getCampo13()[1] = rand.nextDouble();


	return res;
    }

    public static void main(String[] args)
    {

	PersistentManager pm = PersistentManagerFactory.createPersistentManager();
	pm.getTransaction().begin();
	for (count = 0; count < maxObject; count++)
	{

	    MeuExemplo ex = AllTypesGenerator();
	    ex.setCampo11(CollectionGenerator());
	    pm.inject(ex);
	}
	pm.getTransaction().commit();

    }

}
