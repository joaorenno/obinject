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

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.meta.Uuid;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Maurício Faria de Oliveira <mauricio.foliveira@gmail.com>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class PointKeyGenerator extends CodeGenerator<PointKeyDefinition> {

    private int dimensions = 0;

    /**
     *
     * @param definition
     */
    public PointKeyGenerator(PointKeyDefinition definition) {
        super(definition);
        Object newObj;
        Object returnMethod;
        char typeReturn;
        for (ClassField keyField : this.getDefinition().getKeyFields()) {
            if (keyField.getField().getType().isArray()) {
                //field is array
                try {
                    newObj = Class.forName(this.getDefinition().getPackClassName() + "."
                            + this.getDefinition().getUserClassName()).newInstance();
                    returnMethod = newObj.getClass().getDeclaredMethod(keyField.getGet()).invoke(newObj);
                    typeReturn = returnMethod.getClass().getName().charAt(1);
                    switch (typeReturn) {
                        case 'Z':
                            dimensions = ((boolean[]) returnMethod).length;
                            break;
                        case 'B':
                            dimensions = ((byte[]) returnMethod).length;
                            break;
                        case 'C':
                            dimensions = ((char[]) returnMethod).length;
                            break;
                        case 'D':
                            dimensions = ((double[]) returnMethod).length;
                            break;
                        case 'F':
                            dimensions = ((float[]) returnMethod).length;
                            break;
                        case 'I':
                            dimensions = ((int[]) returnMethod).length;
                            break;
                        case 'J':
                            dimensions = ((long[]) returnMethod).length;
                            break;
                        case 'S':
                            dimensions = ((short[]) returnMethod).length;
                            break;
                    }
                } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                    Logger.getLogger(RectangleKeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // field is not array
                this.dimensions++;
            }
        }
    }

    private void generatePakage() {
        //defining the package
        code.append("package ").append(this.getDefinition().getPackClassName()).append("; ");
    }

    private void generateImport() {
        //defining the imports
        code.append("import org.obinject.block.Page; ");
        code.append("import org.obinject.block.PullPage; ");
        code.append("import org.obinject.block.PushPage; ");
        code.append("import org.obinject.meta.generator.DistanceUtil; ");
        code.append("import org.obinject.meta.Point; ");
        code.append("import org.obinject.meta.Uuid; ");
        code.append("import org.obinject.storage.KeyStructure; ");
    }

    private void generateBeginClass() {
        //defining the class
        code.append("public class ").append(this.getDefinition().getClassName()).append(" extends ").
                append(EntityDefinition.prefix).append(this.getDefinition().getUserClassName()).
                append(" implements Point<").append(this.getDefinition().getClassName()).append("> { ");
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

    private void generateDefaultConstructor() {
        //defining the default constructor
        code.append("public ").append(this.getDefinition().getClassName()).append("(){} ");
    }

    private void generateEntityConstructor() {
        code.append("public ").append(this.getDefinition().getClassName()).append("(").
                append(EntityDefinition.prefix).append(this.getDefinition().getUserClassName()).append(" obj){ ");
        code.append("super(obj); } ");
    }

//    private void generateCopyConstructor() {
//        code.append("public ").append(this.getDefinition().getClassName()).
//                append("(").append(this.getDefinition().getUserClassName()).
//                append(" obj){ super(obj); } ");
//    }

    private void generateInitializationConstructor()
    {
	code.append("public ").append(this.getDefinition().getClassName()).
                append("(").append(this.getDefinition().getUserClassName()).
                append(" obj, Uuid uuid){ super(obj, uuid); } ");
    }

    private void generateUuidConstructor() {
        code.append("public ").append(this.getDefinition().getClassName()).
                append("(Uuid uuid){ super(uuid); } ");
    }

    private void generateHasSameKey() {
        code.append("@Override ");
        code.append("public boolean hasSameKey(").append(this.getDefinition().getClassName()).append(" obj) { ");
        code.append("int i=0; ");
        code.append("while(( i < this.numberOfDimensions()) && (this.getOrigin(i) == obj.getOrigin(i))){ ");
        code.append("i++; }");
        code.append("return i == this.numberOfDimensions(); }");
    }

    private void generateOrigin() {
        Object newObj;
        int dim = 0;
        int sizeVector = 0;
        Object returnMethod;
        char typeReturn;
        code.append("@Override ");
        code.append("public double getOrigin(int axis){ ");
        code.append("switch(axis){ ");
        StringBuilder codeSetOrigin = new StringBuilder();
        codeSetOrigin.append("@Override ");
        codeSetOrigin.append("public void setOrigin(int axis, double value){ ");
        codeSetOrigin.append("switch(axis){ ");
        for (ClassField keyField : this.getDefinition().getKeyFields()) {
            if (keyField.getField().getType().isArray()) {
                //field is array
                try {
                    newObj = Class.forName(this.getDefinition().getPackClassName() + "."
                            + this.getDefinition().getUserClassName()).newInstance();
                    returnMethod = newObj.getClass().getDeclaredMethod(keyField.getGet()).invoke(newObj);
                    typeReturn = returnMethod.getClass().getName().charAt(1);
                    switch (typeReturn) {
                        case 'Z':
                            sizeVector = ((boolean[]) returnMethod).length;
                            break;
                        case 'B':
                            sizeVector = ((byte[]) returnMethod).length;
                            break;
                        case 'C':
                            sizeVector = ((char[]) returnMethod).length;
                            break;
                        case 'D':
                            sizeVector = ((double[]) returnMethod).length;
                            break;
                        case 'F':
                            sizeVector = ((float[]) returnMethod).length;
                            break;
                        case 'I':
                            sizeVector = ((int[]) returnMethod).length;
                            break;
                        case 'J':
                            sizeVector = ((long[]) returnMethod).length;
                            break;
                        case 'S':
                            sizeVector = ((short[]) returnMethod).length;
                            break;
                    }
                    //for each vector element
                    for (int i = 0; i < sizeVector; i++) {
                        //get generation
                        code.append("case ").append(dim + i).append(": return ").
                                append(keyField.getGet()).append("()[").append(i).append("]; ");
                        //set generation
                        switch (typeReturn) {
                            case 'Z':
                                codeSetOrigin.append("case ").append(dim + i).append(": ").
                                        append(keyField.getGet()).append("()[").append(i).append("] = (boolean)value; break; ");
                                break;
                            case 'B':
                                codeSetOrigin.append("case ").append(dim + i).append(": ").
                                        append(keyField.getGet()).append("()[").append(i).append("] = (byte)value; break; ");
                                break;
                            case 'C':
                                codeSetOrigin.append("case ").append(dim + i).append(": ").
                                        append(keyField.getGet()).append("()[").append(i).append("] = (char)value; break; ");
                                break;
                            case 'D':
                                codeSetOrigin.append("case ").append(dim + i).append(": ").
                                        append(keyField.getGet()).append("()[").append(i).append("] = value; break; ");
                                break;
                            case 'F':
                                codeSetOrigin.append("case ").append(dim + i).append(": ").
                                        append(keyField.getGet()).append("()[").append(i).append("] = (float)value; break; ");
                                break;
                            case 'I':
                                codeSetOrigin.append("case ").append(dim + i).append(": ").
                                        append(keyField.getGet()).append("()[").append(i).append("] = (int)value; break; ");
                                break;
                            case 'J':
                                codeSetOrigin.append("case ").append(dim + i).append(": ").
                                        append(keyField.getGet()).append("()[").append(i).append("] = (long)value; break; ");
                                break;
                            case 'S':
                                codeSetOrigin.append("case ").append(dim + i).append(": ").
                                        append(keyField.getGet()).append("()[").append(i).append("] = (short)value; break; ");
                                break;
                        }
                    }
                    //increment dimensions
                    dim += sizeVector;
                } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                    Logger.getLogger(RectangleKeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // field is not array
                //get generation
                code.append("case ").append(dim).append(": return ").
                        append(keyField.getGet()).append("(); ");
                //set generation
                if (keyField.getField().getType() == double.class) {
                    //field is double
                    codeSetOrigin.append("case ").append(dim).append(": ").
                            append(keyField.getSet()).append("(value); break; ");
                } else if (keyField.getField().getType() == float.class) {
                    //field is float
                    codeSetOrigin.append("case ").append(dim).append(": ").
                            append(keyField.getSet()).append("((float)value); break; ");
                } else if (keyField.getField().getType() == byte.class) {
                    //field is byte
                    codeSetOrigin.append("case ").append(dim).append(": ").
                            append(keyField.getSet()).append("((byte)value); break; ");
                } else if (keyField.getField().getType() == short.class) {
                    //field is short
                    codeSetOrigin.append("case ").append(dim).append(": ").
                            append(keyField.getSet()).append("((short)value); break; ");
                } else if (keyField.getField().getType() == int.class) {
                    //field is int
                    codeSetOrigin.append("case ").append(dim).append(": ").
                            append(keyField.getSet()).append("((int)value); break; ");
                } else if (keyField.getField().getType() == long.class) {
                    //field is long
                    codeSetOrigin.append("case ").append(dim).append(": ").
                            append(keyField.getSet()).append("((long)value); break; ");
                } else if (keyField.getField().getType() == char.class) {
                    //field is char
                    codeSetOrigin.append("case ").append(dim).append(": ").
                            append(keyField.getSet()).append("((char)value); break; ");
                }
                dim++;
            }
        }
        code.append("default: return 0; ");
        code.append("} } ");
        codeSetOrigin.append("} } ");
        //adding set in code
        code.append(codeSetOrigin);
    }

    private void generateGetPreservedDistance() {
        code.append("@Override public double getPreservedDistance() { return this.preservedDistance; } ");
    }

    private void generateNumberOfDimensions() {
        code.append("@Override ");
        code.append("public int numberOfDimensions(){ ");
        code.append("return ").append(this.dimensions).append(";} ");
    }

    private void generateSetPreservedDistance() {
        code.append("@Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } ");
    }

    private void generateDistanceTo() {
        code.append("@Override ");
        code.append("public double distanceTo(").append(this.getDefinition().getClassName()).append(" obj){ ");
        code.append("return DistanceUtil.euclidean(this, obj); } ");
    }

    private void generateGetKeyStructure() {
        String varName = Character.toLowerCase(this.getDefinition().getClassName().charAt(0))
                + this.getDefinition().getClassName().substring(1) + "Structure";
        code.append("@Override public KeyStructure<").append(this.getDefinition().getClassName()).
                append("> getKeyStructure() { return ").append(varName).append("; } ");
    }

    private void generatePullKey() {
        code.append("@Override ");
        code.append("public boolean pullKey(byte[] array, int position) { ");
        code.append("PullPage pull = new PullPage(array, position); ");
        code.append("for (int i = 0; i < this.numberOfDimensions(); i++){ ");
        code.append("this.setOrigin(i, pull.pullDouble()); ");
        code.append("} return true; } ");
    }

    private void generatePushKey() {
        code.append("@Override ");
        code.append("public void pushKey(byte[] array, int position){ ");
        code.append("PushPage push = new PushPage(array, position); ");
        code.append("for (int i = 0; i < this.numberOfDimensions(); i++){ ");
        code.append("push.pushDouble(this.getOrigin(i));} } ");
    }

    private void generateSizeOfKey() {
        code.append("@Override ");
        code.append("public int sizeOfKey() { ");
        code.append("return Page.sizeOfDouble * this.numberOfDimensions();} ");
    }

    private void generateEndClass() {
        code.append("} ");
    }

    /**
     *
     * @param ignoreEncodingErrors
     * @return
     */
    @Override
    public String getCharContent(boolean ignoreEncodingErrors) {
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

        generateOrigin();

        generateGetPreservedDistance();

        generateNumberOfDimensions();

        generateSetPreservedDistance();

        generateDistanceTo();

        generateGetKeyStructure();

        generatePullKey();

        generatePushKey();

        generateSizeOfKey();

        generateEndClass();

        return code.toString();
    }
}
