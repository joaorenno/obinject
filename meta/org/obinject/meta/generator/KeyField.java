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

/**
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Maurício Faria de Oliveira <mauricio.foliveira@gmail.com>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 * 
 */
public class KeyField extends ClassField{

    private final String classEntity;
    private final String classKey;

    public KeyField(String get, String set, Field field, String classEntity, String classKey) {
        super(get, set, field);
        this.classEntity = classEntity;
        this.classKey = classKey;
    }

    public String getClassEntity() {
        return classEntity;
    }

    public String getClassKey() {
        return classKey;
    }

    
    
}
