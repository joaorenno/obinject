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

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Order;
import org.obinject.annotation.Point;
import org.obinject.annotation.Extension;
import org.obinject.annotation.Feature;
import org.obinject.annotation.Number;
import org.obinject.annotation.Edition;
import org.obinject.annotation.Origin;
import org.obinject.annotation.Sort;
import org.obinject.annotation.Coordinate;
import org.obinject.annotation.Protein;
import org.obinject.annotation.Unique;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Maurício Faria de Oliveira <mauricio.foliveira@gmail.com>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 */
public class MetaclassProvider {

    private static final List<CodeGenerator<?>> codeList = new LinkedList<>();
    private static final HashSet<Class<?>> classesHash = new HashSet<>();
    private static final Order fieldOrderValues[] = Order.values();
    private static final Number keyNumberValues[] = Number.values();

    public boolean addMetaClasses(Class<?> clazz) {
        int sizeBlock;
        String get;
        String set;
        Class<?> superclass = clazz;
        if ((!(classesHash.contains(clazz))) && (clazz.isAnnotationPresent(Persistent.class))) {
            Stack<Class<?>> classes = new Stack<>();
            HashSet<Class<?>> classesDependent = new HashSet<>();
            //vector keys
            UniqueKeyDefinition uniqueDef[] = new UniqueKeyDefinition[keyNumberValues.length];
            SortKeyDefinition sortDef[] = new SortKeyDefinition[keyNumberValues.length];
            RectangleKeyDefinition rectangleDef[] = new RectangleKeyDefinition[keyNumberValues.length];
            PointKeyDefinition pointDef[] = new PointKeyDefinition[keyNumberValues.length];
            EditionKeyDefinition editionDef[] = new EditionKeyDefinition[keyNumberValues.length];
            ProteinKeyDefinition proteinDef[] = new ProteinKeyDefinition[keyNumberValues.length];
            CoordinateKeyDefinition[] coordinateDef = new CoordinateKeyDefinition[keyNumberValues.length];
            FeatureKeyDefinition featureDef[] = new FeatureKeyDefinition[keyNumberValues.length];
            sizeBlock = clazz.getAnnotation(Persistent.class).blockSize();

            //entity
            EntityDefinition entityDefinition = new EntityDefinition(clazz, EntityDefinition.prefix, sizeBlock);
            EntityGenerator entityGenerator = new EntityGenerator(entityDefinition);
            classesHash.add(clazz);
            //through all fields in all classes of mesh inheritance
            //all superclass        
            while ((superclass != null) && (!(superclass.getCanonicalName().equals("java.lang.Object")))) {
                classes.push(superclass);
                superclass = superclass.getSuperclass();
            }
            while (!classes.empty()) {
                Class<?> actualClazz = classes.pop();
                if (!(actualClazz.isAnnotationPresent(Persistent.class))) {
                    throw new Error(actualClazz.getCanonicalName() + " class no has @Persistent annotation.");
                }
                //checking classes dependent
                if (actualClazz != clazz) {
                    classesDependent.add(clazz);
                }
                //if actual is metaclass of the added object
                int totalFields = actualClazz.getDeclaredFields().length;
                EntityField entityField[] = new EntityField[totalFields];
                // for each value in fieldOrder
                for (int i = 0; i < fieldOrderValues.length; i++) {
                    // for each value in keyValue
                    for (int j = 0; j < keyNumberValues.length; j++) {
                        // for each attribute in actualClazz
                        for (int k = 0; k < totalFields; k++) {
                            Field f = actualClazz.getDeclaredFields()[k];
                            String strName = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                            if (f.getType() == boolean.class) {
                                get = "is" + strName;
                            } else {
                                get = "get" + strName;
                            }
                            set = "set" + strName;
                            //Creating EntityField a single time
                            if (entityField[k] == null) {
                                //add entity field
                                entityField[k] = new EntityField(get, set, f);
                                entityDefinition.getEntityFields().add(entityField[k]);
                                //support of the types in framework
                                if (((f.getType().isPrimitive())
                                        || (f.getType() == String.class)
                                        || (f.getType() == Calendar.class)
                                        || (f.getType() == Date.class)
                                        || (f.getType().isArray()))) {
                                } else if (MetaclassProvider.isInterface(Collection.class, f.getType())) {
                                    //adding classes dependent
                                    classesDependent.add(((Class<?>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0]));
                                } else {
                                    //adding classes dependent
                                    classesDependent.add(f.getType());
                                }
                            }
//                            //if actual is metaclass of the added object
//                            if (actualClazz.equals(clazz)) {
                            // for each annotation Unique
                            Unique vetUnique[] = f.getAnnotationsByType(Unique.class);
                            for (Unique annoUnique : vetUnique) {
                                if (annoUnique.number() == keyNumberValues[j]
                                        && annoUnique.order() == fieldOrderValues[i]) {
                                    if (uniqueDef[j] == null) {
                                        String prefix = UniqueKeyDefinition.prefix
                                                + keyNumberValues[j].name();
                                        uniqueDef[j] = new UniqueKeyDefinition(actualClazz, prefix,
                                                sizeBlock, entityDefinition.getEntityFields());
                                    }
                                    KeyField keyField = new KeyField(get, set, f,
                                            EntityDefinition.prefix + actualClazz.getSimpleName(),
                                            uniqueDef[j].getClassName());
                                    uniqueDef[j].getKeyFields().add(keyField);
                                    //add key in field
                                    entityField[k].getKeyFields().add(keyField);
                                }
                            }
                            // for each annotation Sort
                            Sort vetSort[] = f.getAnnotationsByType(Sort.class);
                            for (Sort annoSort : vetSort) {
                                if (annoSort.number() == keyNumberValues[j]
                                        && annoSort.order() == fieldOrderValues[i]) {
                                    if (sortDef[j] == null) {
                                        String prefix = SortKeyDefinition.prefix
                                                + keyNumberValues[j].name();
                                        sortDef[j] = new SortKeyDefinition(actualClazz, prefix,
                                                sizeBlock, entityDefinition.getEntityFields());
                                    }
                                    KeyField keyField = new KeyField(get, set, f,
                                            EntityDefinition.prefix + actualClazz.getSimpleName(),
                                            sortDef[j].getClassName());
                                    sortDef[j].getKeyFields().add(keyField);
                                    //add key in field
                                    entityField[k].getKeyFields().add(keyField);
                                }
                            }
                            // for each annotation rectangle
                            Origin vetRectangle[] = f.getAnnotationsByType(Origin.class);
                            for (Origin annoRectange : vetRectangle) {
                                if (annoRectange.number() == keyNumberValues[j]
                                        && annoRectange.order() == fieldOrderValues[i]) {
                                    if (rectangleDef[j] == null) {
                                        String prefix = RectangleKeyDefinition.prefix
                                                + keyNumberValues[j].name();
                                        rectangleDef[j] = new RectangleKeyDefinition(actualClazz, prefix,
                                                sizeBlock, entityDefinition.getEntityFields());
                                    }
                                    KeyField keyField = new KeyField(get, set, f,
                                            EntityDefinition.prefix + actualClazz.getSimpleName(),
                                            rectangleDef[j].getClassName());
                                    rectangleDef[j].getKeyFields().add(keyField);
                                    //add key in field
                                    entityField[k].getKeyFields().add(keyField);
                                }

                            }
                            // for each annotation extension
                            Extension vetExtension[] = f.getAnnotationsByType(Extension.class);
                            for (Extension annoExtension : vetExtension) {
                                if (annoExtension.number() == keyNumberValues[j]
                                        && annoExtension.order() == fieldOrderValues[i]) {
                                    if (rectangleDef[j] != null) {
                                        KeyField keyField = new KeyField(get, set, f,
                                                EntityDefinition.prefix + actualClazz.getSimpleName(),
                                                rectangleDef[j].getClassName());
                                        rectangleDef[j].getExtensionsFields().add(keyField);
                                        //add key in field
                                        entityField[k].getKeyFields().add(keyField);
                                    }
                                }
                            }
                            // for each annotation Point
                            Point vetPoint[] = f.getAnnotationsByType(Point.class);
                            for (Point annoPoint : vetPoint) {
                                if (annoPoint.number() == keyNumberValues[j]
                                        && annoPoint.order() == fieldOrderValues[i]) {
                                    if (pointDef[j] == null) {
                                        String prefix = PointKeyDefinition.prefix
                                                + keyNumberValues[j].name();
                                        pointDef[j] = new PointKeyDefinition(actualClazz, prefix,
                                                sizeBlock, entityDefinition.getEntityFields());
                                    }
                                    KeyField keyField = new KeyField(get, set, f,
                                            EntityDefinition.prefix + actualClazz.getSimpleName(),
                                            pointDef[j].getClassName());
                                    pointDef[j].getKeyFields().add(keyField);
                                    //add key in field
                                    entityField[k].getKeyFields().add(keyField);
                                }
                            }
                            // for each annotation Edition
                            Edition vetEdition[] = f.getAnnotationsByType(Edition.class);
                            for (Edition annoEdition : vetEdition) {
                                if (annoEdition.number() == keyNumberValues[j]
                                        && annoEdition.order() == fieldOrderValues[i]) {
                                    if (editionDef[j] == null) {
                                        String prefix = EditionKeyDefinition.prefix
                                                + keyNumberValues[j].name();
                                        editionDef[j] = new EditionKeyDefinition(actualClazz, prefix,
                                                sizeBlock, entityDefinition.getEntityFields());
                                    }
                                    KeyField keyField = new KeyField(get, set, f,
                                            EntityDefinition.prefix + actualClazz.getSimpleName(),
                                            editionDef[j].getClassName());
                                    editionDef[j].getKeyFields().add(keyField);
                                    //add key in field
                                    entityField[k].getKeyFields().add(keyField);
                                }
                            }
                            // for each annotation Protein
                            Protein vetProtein[] = f.getAnnotationsByType(Protein.class);
                            for (Protein annoProtein : vetProtein) {
                                if (annoProtein.number() == keyNumberValues[j]
                                        && annoProtein.order() == fieldOrderValues[i]) {
                                    if (proteinDef[j] == null) {
                                        String prefix = ProteinKeyDefinition.prefix
                                                + keyNumberValues[j].name();
                                        proteinDef[j] = new ProteinKeyDefinition(actualClazz, prefix,
                                                sizeBlock, entityDefinition.getEntityFields());
                                    }
                                    KeyField keyField = new KeyField(get, set, f,
                                            EntityDefinition.prefix + actualClazz.getSimpleName(),
                                            proteinDef[j].getClassName());
                                    proteinDef[j].getKeyFields().add(keyField);
                                    //add key in field
                                    entityField[k].getKeyFields().add(keyField);
                                }
                            }
                            // for each annotation Coordinate
                            Coordinate[] vetCoordinate = f.getAnnotationsByType(Coordinate.class);
                            for (Coordinate annoCoordinate : vetCoordinate) {
                                if (annoCoordinate.number() == keyNumberValues[j]
                                        && annoCoordinate.order() == fieldOrderValues[i]) {
                                    if (coordinateDef[j] == null) {
                                        String prefix = CoordinateKeyDefinition.prefix
                                                + keyNumberValues[j].name();
                                        coordinateDef[j] = new CoordinateKeyDefinition(actualClazz, prefix,
                                                sizeBlock, entityDefinition.getEntityFields());
                                    }
                                    CoordinateKeyField keyField = new CoordinateKeyField(get, set, f,
                                            EntityDefinition.prefix + actualClazz.getSimpleName(),
                                            coordinateDef[j].getClassName(), annoCoordinate.angle(), annoCoordinate.radius());
                                    coordinateDef[j].getKeyFields().add(keyField);
                                    //add key in field
                                    entityField[k].getKeyFields().add(keyField);
                                }
                            }
                            // for each annotation Feature
                            Feature vetFeature[] = f.getAnnotationsByType(Feature.class);
                            for (Feature annoFeature : vetFeature) {
                                if (annoFeature.number() == keyNumberValues[j]
                                        && annoFeature.order() == fieldOrderValues[i]) {
                                    if (featureDef[j] == null) {
                                        String prefix = FeatureKeyDefinition.prefix
                                                + keyNumberValues[j].name();
                                        featureDef[j] = new FeatureKeyDefinition(actualClazz, prefix,
                                                sizeBlock, entityDefinition.getEntityFields());
                                    }
                                    FeatureKeyField keyField = new FeatureKeyField(get, set, f,
                                            EntityDefinition.prefix + actualClazz.getSimpleName(),
                                            featureDef[j].getClassName(), annoFeature.order(), annoFeature.method());
                                    featureDef[j].getKeyFields().add(keyField);
                                    //add key in field
                                    entityField[k].getKeyFields().add(keyField);
                                }
                            }
//                            }
                        }
                    }
                }
            }
            //add entity class compiled
            codeList.add(entityGenerator);
            //generation code of the Unique Key
            for (int i = 0; i < fieldOrderValues.length; i++) {
                if (uniqueDef[i] != null) {
                    //unique key generated
                    UniqueKeyGenerator uniquetKeyCode = new UniqueKeyGenerator(uniqueDef[i]);
                    //add class compiled
                    codeList.add(uniquetKeyCode);
                }
            }
            //generation code of the Sort Key
            for (int i = 0; i < fieldOrderValues.length; i++) {
                if (sortDef[i] != null) {
                    //sort key generated
                    SortKeyGenerator sortKeyCode = new SortKeyGenerator(sortDef[i]);
                    //add class compiled
                    codeList.add(sortKeyCode);
                }
            }
            //generation code of the Rectangle Key
            for (int i = 0; i < fieldOrderValues.length; i++) {
                if (rectangleDef[i] != null) {
                    //rectangle key generated
                    RectangleKeyGenerator rectangleKeyCode = new RectangleKeyGenerator(rectangleDef[i]);
                    //add class compiled
                    codeList.add(rectangleKeyCode);
                }
            }
            //generation code of the Point Key
            for (int i = 0; i < fieldOrderValues.length; i++) {
                if (pointDef[i] != null) {
                    //rectangle key generated
                    PointKeyGenerator pointKeyCode = new PointKeyGenerator(pointDef[i]);
                    //add class compiled
                    codeList.add(pointKeyCode);
                }
            }
            //generation code of the Edition Key
            for (int i = 0; i < fieldOrderValues.length; i++) {
                if (editionDef[i] != null) {
                    //rectangle key generated
                    EditionKeyGenerator editionKeyCode = new EditionKeyGenerator(editionDef[i]);
                    //add class compiled
                    codeList.add(editionKeyCode);
                }
            }
            //generation code of the Protein Key
            for (int i = 0; i < fieldOrderValues.length; i++) {
                if (proteinDef[i] != null) {
                    //rectangle key generated
                    ProteinKeyGenerator proteinKeyCode = new ProteinKeyGenerator(proteinDef[i]);
                    //add class compiled
                    codeList.add(proteinKeyCode);
                }
            }
            //generation code of the Coordinate Key
            for (int i = 0; i < fieldOrderValues.length; i++) {
                if (coordinateDef[i] != null) {
                    //rectangle key generated
                    CoordinateKeyGenerator coordinateKeyCode = new CoordinateKeyGenerator(coordinateDef[i]);
                    //add class compiled
                    codeList.add(coordinateKeyCode);
                }
            }

            //generation code of the Feature Key
            for (int i = 0; i < fieldOrderValues.length; i++) {
                if (featureDef[i] != null) {
                    FeatureKeyGenerator featureVectorKeyCode = new FeatureKeyGenerator(featureDef[i]);
                    //add class compiled
                    codeList.add(featureVectorKeyCode);
                }
            }
            //adding classes dependent
            for (Class<?> dependent : classesDependent) {
                addMetaClasses(dependent);
            }
            return true;
        }

        return false;
    }

    /**
     *
     * @return
     */
    public boolean compile() {
        boolean compiled = true;
        if (!(codeList.isEmpty())) {
            JavaCompiler.CompilationTask task = ToolProvider.getSystemJavaCompiler().getTask(
                    null,
                    null,
                    null,
                    Arrays.asList(
                            new String[]{
                                "-d", "build/classes"
                            }),
                    null,
                    codeList);
            compiled = task.call();

            //clear classes not compiled
            codeList.clear();
        }
        return compiled;
    }

    public void save() {
        for (CodeGenerator<?> code : codeList) {
            try {
                String name = System.getProperty("user.dir") + "/src/"
                        + code.getDefinition().getUserPathName()
                        + code.getDefinition().getClassName()
                        + ".java";
                System.out.println(name);
                FileWriter fw = new FileWriter(name);
                fw.append(code.getCharContent(true));
                fw.close();

            } catch (IOException ex) {
                Logger.getLogger(MetaclassProvider.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        //clear classes not compiled
        codeList.clear();
    }

    /**
     *
     * @param superClass
     * @param subClass
     * @return
     */
    public static boolean isDerived(Class<?> superClass, Class<?> subClass) {
        if (subClass == null) {
            return false;
        } else {
            if (subClass == superClass) {
                return true;
            }
            if (isDerived(superClass, subClass.getSuperclass())) {
                return true;
            }
            for (Class<?> inter : subClass.getInterfaces()) {
                if (superClass == inter) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param interfaceClass
     * @param subClass
     * @return
     */
    public static boolean isInterface(Class<?> interfaceClass, Class<?> subClass) {
        if (subClass == null) {
            return false;
        } else {
            for (Class<?> inter : subClass.getInterfaces()) {
                if (interfaceClass == inter) {
                    return true;
                }
            }
            return isInterface(interfaceClass, subClass.getSuperclass());
        }
    }

    /**
     *
     * @param extendsClass
     * @param subClass
     * @return
     */
    public static boolean isExtends(Class<?> extendsClass, Class<?> subClass) {
        if (subClass == null) {
            return false;
        } else {
            if (subClass == extendsClass) {
                return true;
            }
            if (isExtends(extendsClass, subClass.getSuperclass())) {
                return true;
            }
        }
        return false;
    }
}
