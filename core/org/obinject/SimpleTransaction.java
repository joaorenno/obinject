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
package org.obinject;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.exception.TransientObjectException;
import org.obinject.meta.Entity;
import org.obinject.meta.generator.EntityDefinition;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Maurício Faria de Oliveira <mauricio.foliveira@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class SimpleTransaction extends AbstractTransaction {

    private boolean start = false;
//    private List<Object> objects = new LinkedList<>();

    /**
     *
     */
    @Override
    public void begin() {
        start = true;
    }

    /**
     *
     */
    @Override
    public void commit() {
//        if (start) {
//            String entityName;
//            for (Object obj : objects) {
//                entityName = obj.getClass().getName().substring(0, obj.getClass().getName().lastIndexOf('.') + 1) + EntityDefinition.entityPrefix + obj.getClass().getSimpleName();
//                try {
//                    Entity newEntity = (Entity) Class.forName(entityName).getDeclaredConstructor(obj.getClass()).newInstance(obj);
//                    // insert structure
//                    newEntity.inject();
//                    obj = newEntity;
//                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
//                    Logger.getLogger(SimpleTransaction.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            //clear
//            objects.clear();
//            
//        }
        if (start) {
            start = false;
        }
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    protected boolean add(Object object) {
//        return objects.add(object);
        boolean res = false;
        if (start) {
            Entity entity = null;
            try {
                if (object instanceof Entity) {
                    entity = (Entity) object;
                } else {
                    String entityName = object.getClass().getName().substring(0, object.getClass().getName().lastIndexOf('.') + 1) + EntityDefinition.prefix + object.getClass().getSimpleName();
                    entity = (Entity) Class.forName(entityName).getDeclaredConstructor(object.getClass()).newInstance(object);
                }
                // insert
                res = entity.inject();
                if (res == false) {
                    //update
                    res = entity.modify();
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                Logger.getLogger(SimpleTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    protected boolean remove(Object object) {
        boolean res = false;
        if (start) {
            if (object instanceof Entity) {
                Entity entity = (Entity) object;
                res = entity.reject();
            } else {
                throw new TransientObjectException(object.getClass().getSimpleName());
            }
        }
        return res;
    }

    /**
     *
     */
    @Override
    public void rollback() {
    }
}
