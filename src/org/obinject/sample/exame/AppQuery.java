/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.exame;

import java.util.Collection;
import java.util.Date;
import org.obinject.queries.Equal;
import org.obinject.queries.Query;
import org.obinject.sample.image.$Exam;

/**
 *
 * @author windows
 */
public class AppQuery {
    public static void main(String[] args) {
        Query q = new Query();
        q.select($Exame.medico);
        q.from($Exam.class);
        q.where(new Equal($Exame.data, new Date()));
        Collection res = q.execute();
    }
}
