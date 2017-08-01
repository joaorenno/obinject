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

import java.util.Calendar;
import java.util.Date;
import org.obinject.meta.Uuid;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Maurício Faria de Oliveira <mauricio.foliveira@gmail.com>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class EditionKeyGenerator extends CodeGenerator<EditionKeyDefinition>
{
    /**
     *
     * @param definition
     */
    public EditionKeyGenerator(EditionKeyDefinition definition)
    {
        super(definition);
    }

    private void generatePakage()
    {
	//defining the package
	code.append("package ").append(this.getDefinition().getPackClassName()).append("; ");
    }

    private void generateImport()
    {
	//defining the imports
        code.append("import org.obinject.block.Page; ");
	code.append("import org.obinject.block.PullPage; ");
	code.append("import org.obinject.block.PushPage; ");
	code.append("import org.obinject.meta.generator.DistanceUtil; ");
	code.append("import org.obinject.meta.Edition; ");
	code.append("import org.obinject.meta.Uuid; ");
        code.append("import org.obinject.storage.KeyStructure; ");
    }

    private void generateBeginClass()
    {
	//defining the class
	code.append("public class ").append(this.getDefinition().getClassName()).append(" extends ").
		append(EntityDefinition.prefix).append(this.getDefinition().getUserClassName()).
		append(" implements Edition<").append(this.getDefinition().getClassName()).append("> { ");
    }

    private void generatePreservedDistance() {
        code.append("private double preservedDistance; ");
    }

    private void generateClassId() {
        //defining the classId
        code.append("private static Uuid classId; ");
        code.append("public static Uuid getClassId(){ ");
        code.append("if (").append(this.getDefinition().getClassName()).
                append(".classId == null) {");
        code.append(this.getDefinition().getClassName()).
                append(".classId = Uuid.fromString(\"").
                append(Uuid.generator().toString()).append("\"); }");
        code.append("return ").
                append(this.getDefinition().getClassName()).
                append(".classId; } ");
    }
    
    private void generateDefaultConstructor()
    {
	//defining the default constructor
	code.append("public ").append(this.getDefinition().getClassName()).append("(){} ");
    }

    private void generateEntityConstructor()
    {
	code.append("public ").append(this.getDefinition().getClassName()).append("(").
                append(EntityDefinition.prefix).append(this.getDefinition().getUserClassName()).append(" obj){ ");
	code.append("super(obj); } ");
    }

//    private void generateCopyConstructor()
//    {
//	code.append("public ").append(this.getDefinition().getClassName()).
//                append("(").append(this.getDefinition().getUserClassName()).
//                append(" obj){ super(obj); } ");
//    }

    private void generateInitializationConstructor()
    {
	code.append("public ").append(this.getDefinition().getClassName()).
                append("(").append(this.getDefinition().getUserClassName()).
                append(" obj, Uuid uuid){ super(obj, uuid); } ");
    }

    private void generateUuidConstructor()
    {
	code.append("public ").append(this.getDefinition().getClassName()).
                append("(Uuid uuid){ super(uuid); } ");
    }
    
    private void generateHasSameKey(){
        boolean flag = true;
        code.append("@Override ");
        code.append("public boolean hasSameKey(").append(this.getDefinition().getClassName()).append(" obj) { ");
        if (this.getDefinition().getKeyFields().isEmpty()) {
            code.append("return true; } ");
        } else {
            for (ClassField keyField : this.getDefinition().getKeyFields()) {
                if (flag) {
                    if (keyField.getField().getType().isPrimitive()) {
                        code.append("return (this.").append(keyField.getGet()).append("() == obj.").append(keyField.getGet()).append("())");
                        flag = false;
                    } else if ((keyField.getField().getType() == String.class) || (keyField.getField().getType() == Calendar.class) || (keyField.getField().getType() == Date.class)) {
                        code.append(
                                " return (((this.").append(keyField.getGet()).append("() == null) && (obj.").append(keyField.getGet()).append("() == null))").
                                append(" || ((this.").append(keyField.getGet()).append("() != null) && (obj.").append(keyField.getGet()).append("() != null) && (this.").
                                append(keyField.getGet()).append("().equals( obj.").append(keyField.getGet()).append("()))))");
                        flag = false;
                    }
                } else {
                    if (keyField.getField().getType().isPrimitive()) {
                        code.append(" && (this.").append(keyField.getGet()).append("() == obj.").append(keyField.getGet()).append("())");
                    } else if ((keyField.getField().getType() == String.class) || (keyField.getField().getType() == Calendar.class) || (keyField.getField().getType() == Date.class)) {
                        code.append(" && (((this.").append(keyField.getGet()).append("() == null) && (obj.").append(keyField.getGet()).append("() == null))").
                                append(" || ((this.").append(keyField.getGet()).append("() != null) && (obj.").append(keyField.getGet()).append("() != null) && (this.").
                                append(keyField.getGet()).append("().equals( obj.").append(keyField.getGet()).append("()))))");
                    }
                }
            }
            code.append("; } ");
        }
    }

    private void generateGetPreservedDistance() {
        code.append("@Override public double getPreservedDistance() { return this.preservedDistance; } ");
    }
        
    private void generateGetString()
    {
	code.append("@Override ");
	code.append("public String getString(){ ");
	code.append("return \"\"");
        for (ClassField keyField : this.getDefinition().getKeyFields()) {
	    code.append(" + ").append(keyField.getGet()).append("()");            
        }
	code.append(";} ");
    }

    private void generateDistanceTo()
    {
	code.append("@Override ");
	code.append("public double distanceTo(").append(this.getDefinition().getClassName()).append(" obj){ ");
        code.append("return DistanceUtil.levenshtein(this.getString(), obj.getString()); } ");
    }

    private void generateGetKeyStructure(){
        String varName = Character.toLowerCase(this.getDefinition().getClassName().charAt(0)) + 
                this.getDefinition().getClassName().substring(1) + "Structure";
        code.append("@Override public KeyStructure<").append(this.getDefinition().getClassName()).
                append("> getKeyStructure() { return ").append(varName).append("; } ");        
    }
    
    private void generatePullKey()
    {
	code.append("@Override ");
	code.append("public boolean pullKey(byte[] array, int position) { ");
	code.append("PullPage pull = new PullPage(array, position); ");
        for (ClassField keyField : this.getDefinition().getKeyFields()) {
	    if (keyField.getField().getType().isPrimitive())
	    {
                String primitive = primitiveToWapper(keyField.getField().getType().getSimpleName());
		code.append("this.").append(keyField.getSet()).append("(pull.pull").
                        append(primitive).append("()); ");
	    }
	    else if (keyField.getField().getType() == String.class)
	    {
		code.append("this.").append(keyField.getSet()).append("(pull.pullString()); ");
	    }
	}
	code.append("return true; } ");
    }

    private void generatePushKey()
    {
	code.append("@Override ");
	code.append("public void pushKey(byte[] array, int position){ ");
	code.append("PushPage push = new PushPage(array, position); ");
        for (ClassField keyField : this.getDefinition().getKeyFields()) {
	    if (keyField.getField().getType().isPrimitive())
	    {
                String primitive = primitiveToWapper(keyField.getField().getType().getSimpleName());
		code.append("push.push").append(primitive).append("(this.").
                        append(keyField.getGet()).append("()); ");
	    }
	    else if (keyField.getField().getType() == String.class)
	    {
		code.append("push.pushString(this.").append(keyField.getGet()).append("()); ");
	    }

	}
	code.append("} ");
    }

    private void generateSetPreservedDistance() {
        code.append("@Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } ");
    }
    
    private void generateSizeOfKey()
    {
	code.append("@Override ");
	code.append("public int sizeOfKey() { return ");
        for (ClassField keyField : this.getDefinition().getKeyFields()) {
	    if (keyField.getField().getType().isPrimitive())
	    {
                String primitive = primitiveToWapper(keyField.getField().getType().getSimpleName());
		code.append(" + Page.sizeOf").append(primitive);
	    }
	    else if (keyField.getField().getType() == String.class)
	    {
		code.append(" + Page.sizeOfString(this.").append(keyField.getGet()).append("())");
	    }
	}
	code.append("; } ");
    }

    private void generateEndClass()
    {
	code.append("} ");
    }

    /**
     *
     * @param ignoreEncodingErrors
     * @return
     */
    @Override
    public String getCharContent(boolean ignoreEncodingErrors)
    {
	code = new StringBuilder();

	generatePakage();

	generateImport();

	generateBeginClass();

        generatePreservedDistance();
        
        generateClassId();

	generateDefaultConstructor();

	generateEntityConstructor();
        
//        generateCopyConstructor();

        generateInitializationConstructor();

        generateUuidConstructor();

        generateHasSameKey();
        
        generateGetPreservedDistance();

	generateGetString();

	generateDistanceTo();
        
        generateGetKeyStructure();

	generatePullKey();

	generatePushKey();

        generateSetPreservedDistance();

	generateSizeOfKey();

	generateEndClass();

	return code.toString();
    }
}
