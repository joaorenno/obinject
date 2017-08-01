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

import java.awt.image.BufferedImage;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.obinject.meta.Uuid;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Maurício Faria de Oliveira <mauricio.foliveira@gmail.com>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class EntityGenerator extends CodeGenerator<EntityDefinition> {

    private Set<IndexName> uniqueNames = null;
    private Set<IndexName> sortNames = null;
    private Set<IndexName> spatialNames = null;
    private Set<IndexName> metricNames = null;

    /**
     *
     * @param definition
     */
    public EntityGenerator(EntityDefinition definition) {
        super(definition);
    }

    /**
     *
     */
    public void generateListKey() {
        uniqueNames = new TreeSet<>();
        sortNames = new TreeSet<>();
        spatialNames = new TreeSet<>();
        metricNames = new TreeSet<>();
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            for (KeyField keyField : entityField.getKeyFields()) {
                if (keyField.getClassKey().startsWith(UniqueKeyDefinition.prefix)) {
                    uniqueNames.add(new IndexName(keyField.getClassEntity(), keyField.getClassKey()));
                }
            }
        }
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            for (KeyField keyField : entityField.getKeyFields()) {
                if ((keyField.getClassKey().startsWith(UniqueKeyDefinition.prefix))
                        || (keyField.getClassKey().startsWith(SortKeyDefinition.prefix))) {
                    sortNames.add(new IndexName(keyField.getClassEntity(), keyField.getClassKey()));
                }
            }
        }
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            for (KeyField keyField : entityField.getKeyFields()) {
                if (keyField.getClassKey().startsWith(RectangleKeyDefinition.prefix)) {
                    spatialNames.add(new IndexName(keyField.getClassEntity(), keyField.getClassKey()));
                }
            }
        }
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            for (KeyField keyField : entityField.getKeyFields()) {
                if ((keyField.getClassKey().startsWith(EditionKeyDefinition.prefix))
                        || (keyField.getClassKey().startsWith(ProteinKeyDefinition.prefix))
                        || (keyField.getClassKey().startsWith(CoordinateKeyDefinition.prefix))
                        || (keyField.getClassKey().startsWith(PointKeyDefinition.prefix))
                        || (keyField.getClassKey().startsWith(FeatureKeyDefinition.prefix))) {
                    metricNames.add(new IndexName(keyField.getClassEntity(), keyField.getClassKey()));
                }
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
        code.append("import org.obinject.device.File; ");
        code.append("import org.obinject.exception.TransientObjectException; ");
        code.append("import org.obinject.meta.Entity; ");
        code.append("import org.obinject.meta.Uuid; ");
        code.append("import org.obinject.queries.Attribute; ");
        code.append("import org.obinject.queries.Schema; ");
        code.append("import org.obinject.storage.BTree; ");
        code.append("import org.obinject.storage.BTreeEntity; ");
        code.append("import org.obinject.storage.EntityStructure; ");
        code.append("import org.obinject.storage.KeyStructure; ");
        code.append("import org.obinject.storage.Structure; ");
        if (!metricNames.isEmpty()) {
            code.append("import org.obinject.storage.MTree; ");
        }
        if (!spatialNames.isEmpty()) {
            code.append("import org.obinject.storage.RTree; ");
        }
    }

    private void generateBeginClass() {
        //defining the class
        code.append("public class ").append(this.getDefinition().getClassName()).append(" extends ").
                append(this.getDefinition().getUserClassName()).append(" implements Entity<").
                append(this.getDefinition().getClassName()).append("> { ");
    }

    private void generateUuid() {
        //defining the uuid
        code.append("protected Uuid uuid; ");
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

    private void generateEntityStructure() {
        //defining the entityStructure attribute
        String strNameFile = this.getDefinition().getUserPathName();
        strNameFile = strNameFile.substring(
                strNameFile.lastIndexOf('/', strNameFile.length() - 2) + 1,
                strNameFile.length() - 1) + ".dbo";
        code.append("public static final BTreeEntity<").append(this.getDefinition().getClassName()).
                append("> entityStructure = new BTreeEntity<").append(this.getDefinition().getClassName()).
                append(">(new File(\"build/classes/").append(this.getDefinition().getUserPathName()).
                append(strNameFile).append("\", ").append(this.getDefinition().getSizeBlock()).append(")){}; ");
    }

    private void generateOrderStructure() {
        String strNameFile = this.getDefinition().getUserPathName();
        strNameFile = strNameFile.substring(
                strNameFile.lastIndexOf('/', strNameFile.length() - 2) + 1,
                strNameFile.length() - 1) + ".dbo";
        for (IndexName indexName : sortNames) {
            if (indexName.getClassEntity().equals(this.getDefinition().getClassName())) {
                code.append("public static final BTree<").append(indexName.getClassKey()).append("> ").
                        append(indexName.getStructName()).append(" = new BTree<").append(indexName.getClassKey()).
                        append(">(new File(\"build/classes/").append(this.getDefinition().getUserPathName()).
                        append(strNameFile).append("\", ").append(this.getDefinition().getSizeBlock()).append(")){}; ");

            }
        }
    }

    private void generateSpatialStructure() {
        String strNameFile = this.getDefinition().getUserPathName();
        strNameFile = strNameFile.substring(
                strNameFile.lastIndexOf('/', strNameFile.length() - 2) + 1,
                strNameFile.length() - 1) + ".dbo";
        for (IndexName indexName : spatialNames) {
            if (indexName.getClassEntity().equals(this.getDefinition().getClassName())) {
                code.append("public static final RTree<").append(indexName.getClassKey()).append("> ").
                        append(indexName.getStructName()).append(" = new RTree<").append(indexName.getClassKey()).
                        append(">(new File(\"build/classes/").append(this.getDefinition().getUserPathName()).
                        append(strNameFile).append("\", ").append(this.getDefinition().getSizeBlock()).append(")){}; ");
            }
        }
    }

    private void generateMetricStructure() {
        String strNameFile = this.getDefinition().getUserPathName();
        strNameFile = strNameFile.substring(
                strNameFile.lastIndexOf('/', strNameFile.length() - 2) + 1,
                strNameFile.length() - 1) + ".dbo";
        for (IndexName indexName : metricNames) {
            if (indexName.getClassEntity().equals(this.getDefinition().getClassName())) {
                code.append("public static final MTree<").append(indexName.getClassKey()).append("> ").
                        append(indexName.getStructName()).append(" = new MTree<").append(indexName.getClassKey()).
                        append(">(new File(\"build/classes/").append(this.getDefinition().getUserPathName()).
                        append(strNameFile).append("\", ").append(this.getDefinition().getSizeBlock()).append(")){}; ");
            }
        }
    }

    private void generateAttributes() {
        //defining the generateAttributes method        
        String nameType;
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            if (entityField.getField().getType().isPrimitive()) {
                nameType = primitiveToWapper(entityField.getField().getType().getSimpleName());
            } else {
                nameType = entityField.getField().getType().getCanonicalName();
            }

            if (entityField.getField().getType() == boolean.class) {
                code.append(
                        "public static final Attribute<").append(nameType).append("> ").
                        append(Character.toLowerCase(entityField.getGet().charAt(2))).
                        append(entityField.getGet().substring(3)).append(" = new Attribute<").
                        append(nameType).append("> (){ @Override public ").
                        append(nameType).append(" valueOfAttribute(Entity entity) { return ((").
                        append(this.getDefinition().getUserClassName()).append(")entity).").
                        append(entityField.getGet()).append("(); } }; ");
            } else {
                code.append("public static final Attribute<").append(nameType).append("> ").
                        append(Character.toLowerCase(entityField.getGet().charAt(3))).
                        append(entityField.getGet().substring(4)).append(" = new Attribute<").
                        append(nameType).append("> (){ @Override public ").
                        append(nameType).append(" valueOfAttribute(Entity entity) { return ((").
                        append(this.getDefinition().getUserClassName()).append(")entity).").
                        append(entityField.getGet()).append("(); } }; ");
            }
        }
    }

    private void generateStaticBlock() {
        code.append("static{ ");
        String nameType;
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            for (KeyField keyField : entityField.getKeyFields()) {
                if (keyField.getField().getType().isPrimitive()) {
                    nameType = primitiveToWapper(keyField.getField().getType().getSimpleName());
                } else {
                    nameType = keyField.getField().getType().getCanonicalName();
                }
                if (keyField.getField().getType() == boolean.class) {
                    code.append(Character.toLowerCase(entityField.getGet().charAt(2))).
                            append(entityField.getGet().substring(3));
                } else {
                    code.append(Character.toLowerCase(entityField.getGet().charAt(3))).
                            append(entityField.getGet().substring(4));
                }
                code.append(".getSchemas().add( new Schema<").append(this.getDefinition().getClassName()).
                        append(", ").append(keyField.getClassKey()).append(", ").append(nameType).
                        
                        append(">() { @Override public ").append(this.getDefinition().getClassName()).
                        append(" newEntity(").append(nameType).append(" value) { ").
                        append(this.getDefinition().getClassName()).append(" obj = new ").
                        append(this.getDefinition().getClassName()).append("(); obj.").
                        append(keyField.getSet()).
                        append("(value); return obj; } @Override public ").append(keyField.getClassKey()).
                        append(" newKey(").append(nameType).append(" value) { ").
                        append(keyField.getClassKey()).append(" obj = new ").
                        append(keyField.getClassKey()).append("(); obj.").
                        append(keyField.getSet()).append("(value); return obj; } @Override public EntityStructure<").
                        append(this.getDefinition().getClassName()).append("> getEntityStructure() { return ").
                        append(this.getDefinition().getClassName()).
                        append(".entityStructure; } @Override public KeyStructure<").
                        append(keyField.getClassKey()).append("> getKeyStructure() { return ").
                        append(keyField.getClassKey()).append(".").
                        append(Character.toLowerCase(keyField.getClassKey().charAt(0))).
                        append(keyField.getClassKey().substring(1)).append("Structure; } } ); ");
            }
        }
        code.append("} ");
    }

    private void generateDefaultConstructor() {
        //defining the default constructor
        code.append("public ").append(this.getDefinition().getClassName()).append("(){ ");
        code.append("this.uuid = Uuid.generator(); } ");
    }

    private void generateCopyConstructor() {
        //defining the copy constructor
        code.append("public ").append(this.getDefinition().getClassName()).append("(").
                append(this.getDefinition().getUserClassName()).append(" obj){ ");
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            code.append("this.").append(entityField.getSet()).append("(obj.").append(entityField.getGet()).append("()); ");
        }
        code.append("this.uuid = Uuid.generator(); } ");
    }

    private void generateInitializationConstructor() {
        //defining the initialization constructor
        code.append("public ").append(this.getDefinition().getClassName()).append("(").
                append(this.getDefinition().getUserClassName()).append(" obj, Uuid uuid){ ");
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            code.append("this.").append(entityField.getSet()).append("(obj.").append(entityField.getGet()).append("()); ");
        }
        code.append("this.uuid = uuid; } ");
    }

    private void generateEntityConstructor() {
        //defining the entity constructor
        code.append("public ").append(this.getDefinition().getClassName()).append("(").
                append(EntityDefinition.prefix).append(this.getDefinition().getUserClassName()).append(" obj){ ");
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            code.append("this.").append(entityField.getSet()).append("(obj.").append(entityField.getGet()).append("()); ");
        }
        code.append("this.uuid = obj.getUuid();; ");
        code.append("} ");
    }

    private void generateUuidConstructor() {
        //defining the uuid constructor
        code.append("public ").append(this.getDefinition().getClassName()).
                append("(Uuid uuid){ this.uuid = uuid; } ");
    }

    private void generateUuidReference() {
        //defining the uuid
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            String typeName = entityField.getField().getType().getSimpleName();
            String strReference = entityField.getGet().substring(3);
            String strReferenceField = Character.toLowerCase(strReference.charAt(0))
                    + strReference.substring(1);
            int posDot = typeName.lastIndexOf('.');
            String structName = typeName.substring(0, posDot + 1)
                    + EntityDefinition.prefix
                    + typeName.substring(posDot + 1);
            if (entityField.getField().getType().isPrimitive()
                    || entityField.getField().getType() == String.class
                    || entityField.getField().getType() == Calendar.class
                    || entityField.getField().getType() == Date.class
                    || entityField.getField().getType() == BufferedImage.class
                    || entityField.getField().getType().isArray()) {
                //noting
            } else if (MetaclassProvider.isInterface(Collection.class, entityField.getField().getType())) {
                String collectionType = ((ParameterizedType) entityField.getField().getGenericType()).getTypeName();
                typeName = (((ParameterizedType) entityField.getField().getGenericType()).
                        getActualTypeArguments()[0]).getTypeName();
                typeName = typeName.substring(typeName.lastIndexOf('.') + 1);
                code.append("protected java.util.List<Uuid> uuid").append(strReference).append(" = new java.util.ArrayList<>(); ");
                code.append("@Override ");
                code.append("public ").append(collectionType).append(" get").append(strReference).append("() {");
                code.append(collectionType).append(" super").append(strReference).append(" = super.").
                        append(entityField.getGet()).append("(); ");
                code.append("if ((super").append(strReference).append(".isEmpty()) && (!uuid").
                        append(strReference).append(".isEmpty())) {");
                code.append("for (Uuid uuid : uuid").append(strReference).append(") {");
                code.append("super").append(strReference).append(".add(").append(EntityDefinition.prefix).
                        append(typeName).append(".entityStructure.find(uuid)); ");
                code.append("} }");
                code.append("return super").append(strReference).append("; ");
                code.append("}");
                code.append("private void resetUuid").append(strReference).append("(){ ");
                code.append("uuid").append(strReference).append(".clear(); ");
                code.append("if (this.").append(entityField.getGet()).append("() != null) {");
                code.append("for (").append(typeName).append(" obj : this.").
                        append(entityField.getGet()).append("()) { ");
                code.append("if (obj instanceof Entity) { ");
                code.append("uuid").append(strReference).
                        append(".add(((Entity) obj).getUuid()); ");
                code.append("} else { ");
                code.append(EntityDefinition.prefix).append(typeName).append(" entity = new ").
                        append(EntityDefinition.prefix).append(typeName).append(" (obj); ");
                code.append("uuid").append(strReference).append(".add(").append(EntityDefinition.prefix).
                        append(typeName).append(".find(entity)); ");
                code.append("if (").append("uuid").append(strReference).append(" == null) { ");
                code.append("throw new TransientObjectException(\"").
                        append(this.getDefinition().getUserClassName()).append("\", \"").
                        append(strReferenceField).append("\", \"").
                        append(typeName).append("\"); ");
                code.append("} } } } }");
            } else {
                // type is entity

                code.append("protected Uuid uuid").append(strReference).append("; ");
                code.append("@Override public ").append(typeName).append(" ").
                        append(entityField.getGet()).append("(){ ");
                code.append(typeName).append(" super").append(strReference).append(" = super.").
                        append(entityField.getGet()).append("(); ");
                code.append("if (super").append(strReference).append(" == null && uuid").
                        append(strReference).append(" != null) {");
                code.append("super").append(strReference).append(" = ").
                        append(structName).append(".entityStructure").
                        append(".find(uuid").append(strReference).append("); ");
                code.append("this.").append(entityField.getSet()).append("(super").
                        append(strReference).append("); ");
                code.append("}");
                code.append("return super").append(strReference).append("; ");
                code.append("}");

                code.append("private void resetUuid").append(strReference).
                        append("() { ");
                code.append("if (this.").append(entityField.getGet()).
                        append("() != null) { ");
                code.append("if (this.").append(entityField.getGet()).
                        append("() instanceof Entity) { ");
                code.append("uuid").append(strReference).append(" = ((Entity) this.").
                        append(entityField.getGet()).append("()).getUuid(); ");
                code.append("} else { ");
                code.append(EntityDefinition.prefix).append(typeName).append(" entity = new ").
                        append(EntityDefinition.prefix).append(typeName).append(" (this.").
                        append(entityField.getGet()).append("()); ");
                code.append("uuid").append(strReference).append(" = ").append(EntityDefinition.prefix).
                        append(typeName).append(".find(entity); ");
                code.append("if (").append("uuid").append(strReference).append(" == null) { ");
                code.append("throw new TransientObjectException(\"").
                        append(this.getDefinition().getUserClassName()).append("\", \"").
                        append(strReferenceField).append("\", \"").
                        append(typeName).append("\"); ");
                code.append("} } } }");
            }
        }
    }

    private void generateIsEqual() {
        boolean flag = true;
        //defining the equalToEntity method
        code.append("@Override ");
        code.append("public boolean isEqual(").append(this.getDefinition().getClassName()).append(" obj) { ");
        if (this.getDefinition().getEntityFields().isEmpty()) {
            code.append("return true; } ");
        } else {
            for (EntityField entityField : this.getDefinition().getEntityFields()) {
                if (flag) {
                    if (entityField.getField().getType().isPrimitive()) {
                        code.append("return (this.").append(entityField.getGet()).append("() == obj.").append(entityField.getGet()).append("())");
                        flag = false;
                    } else if ((entityField.getField().getType() == String.class) || (entityField.getField().getType() == Calendar.class) || (entityField.getField().getType() == Date.class)) {
                        code.append(
                                " return (((this.").append(entityField.getGet()).append("() == null) && (obj.").append(entityField.getGet()).append("() == null))").
                                append(" || ((this.").append(entityField.getGet()).append("() != null) && (obj.").append(entityField.getGet()).append("() != null) && (this.").
                                append(entityField.getGet()).append("().equals( obj.").append(entityField.getGet()).append("()))))");
                        flag = false;
                    }
                } else if (entityField.getField().getType().isPrimitive()) {
                    code.append(" && (this.").append(entityField.getGet()).append("() == obj.").append(entityField.getGet()).append("())");
                } else if ((entityField.getField().getType() == String.class) || (entityField.getField().getType() == Calendar.class) || (entityField.getField().getType() == Date.class)) {
                    code.append(" && (((this.").append(entityField.getGet()).append("() == null) && (obj.").append(entityField.getGet()).append("() == null))").
                            append(" || ((this.").append(entityField.getGet()).append("() != null) && (obj.").append(entityField.getGet()).append("() != null) && (this.").
                            append(entityField.getGet()).append("().equals( obj.").append(entityField.getGet()).append("()))))");
                }
            }
            code.append("; } ");
        }
    }

    private void generateGetUuid() {
        code.append("@Override public Uuid getUuid() { return this.uuid; } ");
    }

    private void generateGetEntityStructure() {
        code.append("@Override public EntityStructure<").append(this.getDefinition().getClassName()).
                append("> getEntityStructure() { return entityStructure; } ");
    }

    private void generateInject() {
        code.append("@Override public boolean inject(){ ");
        code.append("Uuid uuidInject = ").append(this.getDefinition().getClassName()).append(".find(this); ");
        code.append("if(uuidInject == null){ ");
        //defining the uuid
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            String strReference = entityField.getGet().substring(3);
            if (entityField.getField().getType().isPrimitive()
                    || entityField.getField().getType() == String.class
                    || entityField.getField().getType() == Calendar.class
                    || entityField.getField().getType() == Date.class
                    || entityField.getField().getType() == BufferedImage.class
                    || entityField.getField().getType().isArray()) {
                //noting
            } else if (MetaclassProvider.isInterface(Collection.class, entityField.getField().getType())) {
                code.append("resetUuid").append(strReference).append("(); ");
            } else {
                // type is entity
                code.append("resetUuid").append(strReference).append("(); ");
            }
        }
        code.append(this.getDefinition().getClassName()).append(".entityStructure").
                append(".add(this); ");
        for (IndexName indexName : sortNames) {
            code.append(indexName.getClassKey()).append('.').append(indexName.getStructName()).
                    append(".add(new ").append(indexName.getClassKey()).append("(this, this.getUuid())); ");
        }
        for (IndexName indexName : spatialNames) {
            code.append(indexName.getClassKey()).append('.').append(indexName.getStructName()).
                    append(".add(new ").append(indexName.getClassKey()).append("(this, this.getUuid())); ");
        }
        for (IndexName indexName : metricNames) {
            code.append(indexName.getClassKey()).append('.').append(indexName.getStructName()).
                    append(".add(new ").append(indexName.getClassKey()).append("(this, this.getUuid())); ");
        }
        code.append("return true; ");
        code.append("}else{ ");
        code.append("this.uuid = uuidInject; ");
        code.append("return false; ");
        code.append("} } ");
    }

    private void generateReject() {
        code.append("@Override public boolean reject(){ ");
        code.append("Uuid uuidReject = ").append(this.getDefinition().getClassName()).append(".find(this); ");
        code.append("if(uuidReject != null){ ");
        for (IndexName indexName : sortNames) {
            code.append(indexName.getClassKey()).append('.').append(indexName.getStructName()).
                    append(".remove(new ").append(indexName.getClassKey()).append("(this, this.getUuid())); ");
        }
        for (IndexName indexName : spatialNames) {
            code.append(indexName.getClassKey()).append('.').append(indexName.getStructName()).
                    append(".remove(new ").append(indexName.getClassKey()).append("(this, this.getUuid())); ");
        }
        for (IndexName indexName : metricNames) {
            code.append(indexName.getClassKey()).append('.').append(indexName.getStructName()).
                    append(".remove(new ").append(indexName.getClassKey()).append("(this, this.getUuid())); ");
        }
        code.append(this.getDefinition().getClassName()).append(".entityStructure").
                append(".remove(this); ");
        code.append("return true; ");
        code.append("}else{ ");
        code.append("return false; ");
        code.append("} } ");
    }

    private void generateModify() {
        code.append("@Override public boolean modify(){ ");
        code.append("Uuid uuidOld = ").append(this.getDefinition().getClassName()).append(".find(this); ");
        code.append(this.getDefinition().getClassName()).append(" entityOld = ").
                append(this.getDefinition().getClassName()).
                append(".entityStructure.find(uuidOld); ");
        code.append("if(entityOld != null){ ");
        //defining the uuid
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            String strReference = entityField.getGet().substring(3);
            if (entityField.getField().getType().isPrimitive()
                    || entityField.getField().getType() == String.class
                    || entityField.getField().getType() == Calendar.class
                    || entityField.getField().getType() == Date.class
                    || entityField.getField().getType() == BufferedImage.class
                    || entityField.getField().getType().isArray()) {
                //noting
            } else if (MetaclassProvider.isInterface(Collection.class, entityField.getField().getType())) {
                code.append("resetUuid").append(strReference).append("(); ");
            } else {
                // type is entity
                code.append("resetUuid").append(strReference).append("(); ");
            }
        }
        String varName;
        for (IndexName indexName : sortNames) {
            varName = Character.toLowerCase(indexName.getClassKey().charAt(0)) + indexName.getClassKey().substring(1);
            code.append(indexName.getClassKey()).append(" ").append(varName).append("Old = new ").
                    append(indexName.getClassKey()).append("(entityOld, entityOld.getUuid()); ");
            code.append(indexName.getClassKey()).append(" ").append(varName).append("New = new ").
                    append(indexName.getClassKey()).append("(this, entityOld.getUuid()); ");
            code.append("if(").append(varName).append("Old.hasSameKey(").
                    append(varName).append("New)){ ");
            code.append(indexName.getClassEntity()).append(".").append(indexName.getStructName()).
                    append(".remove(").append(varName).append("Old); ");
            code.append(indexName.getClassEntity()).append(".").append(indexName.getStructName()).
                    append(".add(").append(varName).append("New); } ");
        }
        for (IndexName indexName : spatialNames) {
            varName = Character.toLowerCase(indexName.getClassKey().charAt(0)) + indexName.getClassKey().substring(1);
            code.append(indexName.getClassKey()).append(" ").append(varName).append("Old = new ").
                    append(indexName.getClassKey()).append("(entityOld, entityOld.getUuid()); ");
            code.append(indexName.getClassKey()).append(" ").append(varName).append("New = new ").
                    append(indexName.getClassKey()).append("(this, entityOld.getUuid()); ");
            code.append("if(").append(varName).append("Old.hasSameKey(").
                    append(varName).append("New)){ ");
            code.append(indexName.getClassEntity()).append(".").append(indexName.getStructName()).
                    append(".remove(").append(varName).append("Old); ");
            code.append(indexName.getClassEntity()).append(".").append(indexName.getStructName()).
                    append(".add(").append(varName).append("New); } ");
        }
        for (IndexName indexName : metricNames) {
            varName = Character.toLowerCase(indexName.getClassKey().charAt(0)) + indexName.getClassKey().substring(1);
            code.append(indexName.getClassKey()).append(" ").append(varName).append("Old = new ").
                    append(indexName.getClassKey()).append("(entityOld, entityOld.getUuid()); ");
            code.append(indexName.getClassKey()).append(" ").append(varName).append("New = new ").
                    append(indexName.getClassKey()).append("(this, entityOld.getUuid()); ");
            code.append("if(").append(varName).append("Old.hasSameKey(").
                    append(varName).append("New)){ ");
            code.append(indexName.getClassEntity()).append(".").append(indexName.getStructName()).
                    append(".remove(").append(varName).append("Old); ");
            code.append(indexName.getClassEntity()).append(".").append(indexName.getStructName()).
                    append(".add(").append(varName).append("New); } ");
        }
        code.append("this.uuid = uuidOld; ");
        code.append(this.getDefinition().getClassName()).append(".entityStructure").
                append(".remove(entityOld); ");
        code.append(this.getDefinition().getClassName()).append(".entityStructure").
                append(".add(this); ");
        code.append("return true; ");
        code.append("}else{ ");
        code.append("return false; ");
        code.append("} } ");
    }

    private void generateFind() {
        code.append("public static Uuid find(").
                append(this.getDefinition().getClassName()).append(" entity) { ");
        IndexName indexUnique = uniqueNames.iterator().next();
        code.append(indexUnique.getClassKey()).append(" unique = new ").
                append(indexUnique.getClassKey()).append(" (entity, entity.getUuid()); ");
        code.append("return ").append(indexUnique.getClassEntity()).append(".").
                append(indexUnique.getStructName()).append(".find(unique); ");
        code.append("} ");
    }

    private void generatePullEntity() {
        code.append("@Override ");
        code.append("public boolean pullEntity(byte[] array, int position) { ");
        code.append("PullPage pull = new PullPage(array, position); ");
        code.append("Uuid storedClass = pull.pullUuid(); ");
        code.append("if (").append(this.getDefinition().getClassName());
        code.append(".classId.equals(storedClass) == true){ ");
        code.append("uuid = pull.pullUuid(); ");
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            if (entityField.getField().getType().isPrimitive()) {
                String primitive = primitiveToWapper(entityField.getField().getType().getSimpleName());
                code.append("this.").append(entityField.getSet()).append("(pull.pull").
                        append(primitive).append("()); ");
            } else if (entityField.getField().getType() == String.class) {
                code.append(
                        "this.").append(entityField.getSet()).append("(pull.pullString()); ");
            } else if (entityField.getField().getType() == Calendar.class) {
                code.append(
                        "this.").append(entityField.getSet()).append("(pull.pullCalendar()); ");
            } else if (entityField.getField().getType() == Date.class) {
                code.append(
                        "this.").append(entityField.getSet()).append("(pull.pullDate()); ");
            } else if (entityField.getField().getType() == BufferedImage.class) {
                //notting
            } else if (entityField.getField().getType().isArray()) {
                code.append("this.").append(entityField.getSet()).append("((").append(entityField.getField().getType().getSimpleName()).append(") pull.pullMatrix()); ");
            } else if (MetaclassProvider.isInterface(Collection.class, entityField.getField().getType())) {
                // type is entity
                String typeName = entityField.getGet().substring(3);
                code.append("int total").append(typeName).append(" = pull.pullInteger(); ");
                code.append("for (int i = 0; i < total").append(typeName).append("; i++) {");
                code.append("this.uuid").append(typeName).append(".add(pull.pullUuid()); }");
            } else {
                // type is entity
                String typeName = entityField.getGet().substring(3);
                code.append("int total").append(typeName).append(" = pull.pullInteger(); ");
                code.append("if(total").append(typeName).append(" > 0){");
                code.append("this.uuid").append(typeName).append(" = pull.pullUuid(); }");
            }
        }
        code.append("return true; } ");
        code.append("return false; } ");
    }

    private void generatePushEntity() {
        code.append("@Override ");
        code.append("public void pushEntity(byte[] array, int position){ ");
        code.append("PushPage push = new PushPage(array, position); ");
        code.append("push.pushUuid(").append(this.getDefinition().getClassName()).append(".classId); ");
        code.append("push.pushUuid(uuid); ");
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            if (entityField.getField().getType().isPrimitive()) {
                String primitive = primitiveToWapper(entityField.getField().getType().getSimpleName());
                code.append("push.push").append(primitive).append("(this.").append(entityField.getGet()).append("()); ");
            } else if (entityField.getField().getType() == String.class) {
                code.append(
                        "push.pushString(this.").append(entityField.getGet()).append("()); ");
            } else if (entityField.getField().getType() == Calendar.class) {
                code.append(
                        "push.pushCalendar(this.").append(entityField.getGet()).append("()); ");
            } else if (entityField.getField().getType() == Date.class) {
                code.append(
                        "push.pushDate(this.").append(entityField.getGet()).append("()); ");
            } else if (entityField.getField().getType() == BufferedImage.class) {
                //notting
            } else if (entityField.getField().getType().isArray()) {
                code.append("push.pushMatrix(").append(entityField.getGet()).append("()); ");
            } else if (MetaclassProvider.isInterface(Collection.class, entityField.getField().getType())) {
                String strReference = entityField.getGet().substring(3);
                code.append("push.pushInteger(this.uuid").append(strReference).append(".size()); ");
                code.append("for (Uuid uuidPush : this.uuid").append(strReference).append(") {");
                code.append("push.pushUuid(uuidPush); }");
            } else {
                // type is entity
                String strReference = entityField.getGet().substring(3);
                code.append("if (this.uuid").append(strReference).append(" != null) { ");
                code.append("push.pushInteger(1); ");
                code.append("push.pushUuid(this.uuid").append(strReference).append("); ");
                code.append("} else {");
                code.append("push.pushInteger(0); ");
                code.append("} ");
            }
        }
        code.append("} ");
    }

    private void generateSizeOfEntity() {
        //defining the size of entity
        code.append("@Override ");
        code.append("public int sizeOfEntity() { return Page.sizeOfUuid + Page.sizeOfUuid ");
        for (EntityField entityField : this.getDefinition().getEntityFields()) {
            if (entityField.getField().getType().isPrimitive()) {
                String primitive = primitiveToWapper(entityField.getField().getType().getSimpleName());
                code.append(" + Page.sizeOf").append(primitive);
            } else if (entityField.getField().getType() == String.class) {
                code.append(
                        " + Page.sizeOfString(this.").append(entityField.getGet()).append("())");
            } else if (entityField.getField().getType() == Calendar.class) {
                code.append(
                        " + Page.sizeOfCalendar");
            } else if (entityField.getField().getType() == Date.class) {
                code.append(
                        " + Page.sizeOfDate");
            } else if (entityField.getField().getType() == BufferedImage.class) {
                //notting
            } else if (entityField.getField().getType().isArray()) {
                code.append(" + Page.sizeOfMatrix(this.").append(entityField.getGet()).append("())");
            } else if (MetaclassProvider.isInterface(Collection.class, entityField.getField().getType())) {
                code.append(
                        " + Page.sizeOfEntityCollection(this.uuid").append(entityField.getGet().substring(3)).append(")");
            } else {
                code.append(
                        " + Page.sizeOfEntity(this.uuid").append(entityField.getGet().substring(3)).append(")");
            }
        }
        code.append("; } ");
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

        generateListKey();

        code = new StringBuilder();

        generatePakage();

        generateImport();

        generateBeginClass();

        generateUuid();

        generateClassId();

        generateEntityStructure();

        generateOrderStructure();

        generateSpatialStructure();

        generateMetricStructure();

        generateAttributes();

        generateDefaultConstructor();

        generateCopyConstructor();

        generateInitializationConstructor();

        generateEntityConstructor();

        generateUuidConstructor();

        generateUuidReference();

        generateIsEqual();

        generateGetUuid();

        generateGetEntityStructure();

        generateInject();

        generateReject();

        generateModify();

        generateFind();

        generatePullEntity();

        generatePushEntity();

        generateSizeOfEntity();

        generateStaticBlock();

        generateEndClass();

        return code.toString();
    }
}
