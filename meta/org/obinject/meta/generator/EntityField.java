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

/*
 */
package org.obinject.meta.generator;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Maurício Faria de Oliveira <mauricio.foliveira@gmail.com>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 * 
 */
public class EntityField extends ClassField {

    private List<KeyField> KeyFields = new LinkedList<>();

    /**
     *
     * @param get
     * @param set
     * @param field
     */
    public EntityField(String get, String set, Field field) {
        super(get, set, field);
    }

    /**
     *
     * @return
     */
    public List<KeyField> getKeyFields() {
        return KeyFields;
    }
}
