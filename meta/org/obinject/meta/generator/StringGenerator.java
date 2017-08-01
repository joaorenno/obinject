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

package org.obinject.meta.generator;

import java.net.URI;
import javax.tools.SimpleJavaFileObject;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Wellington Openheimer Ribeiro <wellington@unifei.edu.br>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class StringGenerator extends SimpleJavaFileObject {

    private final String code;
    private String packClassName;

    /**
     *
     * @return
     */
    public String getPackClassName() {
        return packClassName;
    }

    private static String getCompleteName(String code) {
        int pkgBegin = code.indexOf("package ");
        int pkgEnd = -1;
        do {
            pkgEnd++;
            pkgEnd = code.indexOf(';', pkgEnd);

        } while (pkgEnd < pkgBegin);
        String packageName = code.substring(pkgBegin + 8, pkgEnd);

        int classBegin = code.indexOf("class ");
        int classEnd = 0;
        int indKey = code.indexOf('{');
        int idxSpace = -1;
        do {
            idxSpace++;
            idxSpace = code.indexOf(' ', idxSpace);
            if (idxSpace < indKey) {
                classEnd = idxSpace;
            } else {
                classEnd = indKey;
            }
        } while (classEnd <= classBegin + 6);
        String className = code.substring(classBegin + 6, classEnd);
        return packageName + '/' + className;

    }

    /**
     *
     * @param code
     */
    public StringGenerator(String code) {
        super(URI.create("string:///" + StringGenerator.getCompleteName(code)
                + Kind.SOURCE.extension), Kind.SOURCE);
        this.packClassName = StringGenerator.getCompleteName(code).replace('/', '.');
        this.code = code;
    }

    /**
     *
     * @param ignoreEncodingErrors
     * @return
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }
}