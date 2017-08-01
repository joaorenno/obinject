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
import org.obinject.meta.Entity;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public abstract class Conditional {

    private Conditional nextConditional;

    /**
     *
     */
    public enum Logical {

        /**
         *
         */
        and,
        /**
         *
         */
        or
    };
    private Logical logical = null;

    /**
     *
     * @param conditional
     * @return
     */
    public Conditional and(Conditional conditional) {
        this.nextConditional = conditional;
        this.logical = Logical.and;
        return this.nextConditional;
    }

    /**
     *
     * @param conditional
     * @return
     */
    public Conditional or(Conditional conditional) {
        this.nextConditional = conditional;
        this.logical = Logical.or;
        return this.nextConditional;
    }

    /**
     *
     * @return
     */
    public Logical getLogical() {
        return logical;
    }

    /**
     *
     * @return
     */
    protected Conditional getNextConditional() {
        return nextConditional;
    }

    /**
     *
     * @return
     */
    public abstract Collection<? extends Entity> process();
}
