package org.obinject.sample.student;

import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.PersistentManager;

public class AppFind {

    public static void main(String[] args) {
        int i = 0;
        $Student entity = new $Student();
        PersistentManager pm = new PersistentManager();
        try {
            Scanner scan = new Scanner(new java.io.File("student.txt"));
            scan.useLocale(Locale.US);
            while (scan.hasNext()) {
                i++;
                if(i % 100 == 0){
                    System.out.print("["+ i +"]");
                }
                entity.setRegistration(scan.nextInt());
                entity.setName(scan.next());
                entity.setAddress(scan.next());
                entity.setCourse(scan.next());
                entity.setCoefficient(scan.nextFloat());
                entity.setAge(scan.nextInt());                
                UniqueOneStudent pks = new UniqueOneStudent(entity);
                if ($Student.uniqueOneStudentStructure.find(pks) == null){
                    System.out.println("PK="+pks.getRegistration());
                }                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppInsertion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
