/*
Copyright (C) 2013     Enzo Seraphim

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
or visit <http://www.gnu.org/licenses/>
 */
package org.obinject.queries;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;
import org.obinject.meta.Entity;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class Query {

    private final PerformanceMeasurement performanceMeasurement = new AveragePerformance();
    protected Attribute[] selectColumns;
    private Class<? extends Entity>[] fromEntities;
    private Conditional whereConditional;
    private Attribute[] groupByColumns;
    private Conditional havingConditional;
    private Attribute[] orderByColumns;

    public PerformanceMeasurement getMeasurement() {
        return performanceMeasurement;
    }

    /**
     *
     * @param selectColumns
     */
    public void select(Attribute... selectColumns) {
        this.selectColumns = selectColumns;
    }

    /**
     *
     * @param fromEntities
     */
    public void from(Class<? extends Entity>... fromEntities) {
        this.fromEntities = fromEntities;
    }

    /**
     *
     * @param conditional
     * @return
     */
    public Conditional where(Conditional conditional) {
        this.whereConditional = conditional;
        return this.whereConditional;
    }

    /**
     *
     * @param groupByColumns
     */
    public void groupBy(Attribute... groupByColumns) {
        this.groupByColumns = groupByColumns;
    }

    /**
     *
     * @param conditional
     * @return
     */
    public Conditional having(Conditional conditional) {
        this.havingConditional = conditional;
        return this.havingConditional;
    }

    /**
     *
     * @param orderByColumns
     */
    public void orderBy(Attribute... orderByColumns) {
        this.orderByColumns = orderByColumns;
    }

    private class EntityComparable implements Comparator<Entity>{
        @Override
        public int compare(Entity o1, Entity o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    }
    
    public Collection execute() {
        Conditional actual = whereConditional;
        Collection resultProcess = null;
        TreeSet <? extends Entity> resultWhere = new TreeSet( new EntityComparable());
        Collection<Object> result = new LinkedList<>();

        if (actual == null) {
            
        } else {
            // processamento do where (operadore and / or)
            Conditional.Logical logical = null;
            while (actual != null) {

                resultProcess = actual.process();

                if(logical == null){
                    resultWhere.addAll(resultProcess);
                }else if (logical == Conditional.Logical.and) {
                    resultWhere.retainAll(resultProcess);
                } else if (logical == Conditional.Logical.or) {
                    resultWhere.addAll(resultProcess);
                }
                resultProcess = null;
                logical = actual.getLogical();
                actual = actual.getNextConditional();
            }
        }
        // processamento do select 
        if (selectColumns != null) {
            if (selectColumns.length == 1) {
                for (Entity entity : resultWhere) {
                    Object attrib = this.selectColumns[0].valueOfAttribute(entity);
                    if (attrib != null) {
                        result.add(attrib);
                    }
                }
            } else if (selectColumns.length > 1) {
                for (Entity entity : resultWhere) {
                    Object[] vector = new Object[this.selectColumns.length];
                    int i = 0;
                    for (Attribute att : this.selectColumns) {
                        vector[i++] = att.valueOfAttribute(entity);
                    }
                    result.add(vector);
                }
            }
            return result;
        } else {
            return resultWhere;
        }

    }
}
