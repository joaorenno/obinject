/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.meta.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import org.obinject.meta.Uuid;

/**
 *
 * @author LuizGuilherme
 */
public class FeatureKeyGenerator extends CodeGenerator<FeatureKeyDefinition> {

    public FeatureKeyGenerator(FeatureKeyDefinition definition) {
        super(definition);
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
        code.append("import org.obinject.meta.Metric; ");
        code.append("import org.obinject.meta.Uuid; ");
        code.append("import org.obinject.storage.KeyStructure; ");
        code.append("import java.awt.image.BufferedImage; ");
        code.append("import org.obinject.meta.generator.ExtractionUtil; ");
    }

    private void generateBeginClass() {
        //defining the class
        code.append("public class ").append(this.getDefinition().getClassName()).append(" extends ").
                append(EntityDefinition.prefix).append(this.getDefinition().getUserClassName()).
                append(" implements Metric<").append(this.getDefinition().getClassName()).append("> { ");
    }

    private void generatePreservedDistance() {
        code.append("private double preservedDistance; ");
    }

    private void generateFeatureVector() {
        for (FeatureKeyField keyField : this.getDefinition().getKeyFields()) {
            code.append("private double featureVector").append(keyField.getFieldOrder()).append("[]; ");

        }
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

    private void generateInstanceFeatureVector() {
        for (FeatureKeyField keyField : this.getDefinition().getKeyFields()) {
            switch (keyField.getExtractionEnum()) {
                case Histogram:
                    code.append("featureVector").append(keyField.getFieldOrder()).
                            append("= ExtractionUtil.histogram(this.").
                            append(keyField.getGet()).append("()); ");
                    break;
                case HistogramStatistical:
                    code.append("featureVector").append(keyField.getFieldOrder()).
                            append("= ExtractionUtil.histogramStatistical(this.").
                            append(keyField.getGet()).append("()); ");
                    break;
                case HaralickSymmetric:
                    code.append("featureVector").append(keyField.getFieldOrder()).
                            append("= ExtractionUtil.haralickSymmetric(this.").
                            append(keyField.getGet()).append("()); ");
                    break;
                case HaralickAssymmetric:
                    code.append("featureVector").append(keyField.getFieldOrder()).
                            append("= ExtractionUtil.haralickAssymmetric(this.").
                            append(keyField.getGet()).append("()); ");
                    break;
            }
        }
    }

    private void generateDefaultConstructor() {
        //defining the default constructor
        code.append("public ").append(this.getDefinition().getClassName()).append("(){ ");
        generateInstanceFeatureVector();
        code.append("} ");
    }

    private void generateEntityConstructor() {
        code.append("public ").append(this.getDefinition().getClassName()).append("(").
                append(EntityDefinition.prefix).append(this.getDefinition().getUserClassName()).append(" obj){ ");
        code.append("super(obj); ");
        generateInstanceFeatureVector();
        code.append("} ");
    }

//    private void generateCopyConstructor() {
//        code.append("public ").append(this.getDefinition().getClassName()).
//                append("(").append(this.getDefinition().getUserClassName()).append(" obj){ ");
//        code.append("super(obj); ");
//        generateInstanceFeatureVector();
//        code.append("} ");
//    }

    private void generateInitializationConstructor() {
        code.append("public ").append(this.getDefinition().getClassName()).
                append("(").append(this.getDefinition().getUserClassName()).
                append(" obj, Uuid uuid){ super(obj, uuid); ");
        generateInstanceFeatureVector();
        code.append("} ");
    }

    private void generateUuidConstructor() {
        code.append("public ").append(this.getDefinition().getClassName()).append("(Uuid uuid){ ");
        code.append("super(uuid); ");
        generateInstanceFeatureVector();
        code.append("} ");
    }

    private void generateHasSameKey() {
        code.append("@Override ");
        code.append("public boolean hasSameKey(").append(this.getDefinition().getClassName()).append(" obj) { ");
        code.append("return ");

        if (this.getDefinition().getKeyFields().isEmpty()) {
            code.append("true; ");
        } else {
            FeatureKeyField keyField;
            Iterator<FeatureKeyField> it = this.getDefinition().getKeyFields().iterator();
            while (it.hasNext()) {
                keyField = it.next();
                code.append("Page.matricesAreEqual(this.featureVector").append(keyField.getFieldOrder()).
                        append(", obj.featureVector").append(keyField.getFieldOrder()).append(") ");
                if (it.hasNext()) {
                    code.append(" && ");
                }
            }
        }
        code.append("; } ");
    }

    private void generateGetPreservedDistance() {
        code.append("@Override public double getPreservedDistance() { return this.preservedDistance; } ");
    }

    private void generateSetPreservedDistance() {
        code.append("@Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } ");
    }

    private void generateDistanceTo() {
        code.append("@Override ");
        code.append("public double distanceTo(").append(this.getDefinition().getClassName()).append(" obj){ ");
        code.append("return ");
        if (this.getDefinition().getKeyFields().isEmpty()) {
            code.append("0.0d");
        } else {
            FeatureKeyField keyField;
            Iterator<FeatureKeyField> it = this.getDefinition().getKeyFields().iterator();
            while (it.hasNext()) {
                keyField = it.next();
                code.append("DistanceUtil.euclidean(this.featureVector").append(keyField.getFieldOrder()).
                        append(", obj.featureVector").append(keyField.getFieldOrder()).append(") ");
                if (it.hasNext()) {
                    code.append(" + ");
                }
            }
        }
        code.append("; } ");
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
        for (FeatureKeyField keyField : this.getDefinition().getKeyFields()) {
            code.append("featureVector").append(keyField.getFieldOrder()).append(" = (double[]) pull.pullMatrix(); ");
        }
        code.append("return true; } ");
    }

    private void generateBufferedImage() {
        FeatureKeyField keyField;
        FeatureKeyField previousKey = null;
        if (!this.getDefinition().getKeyFields().isEmpty()) {
            Comparator<FeatureKeyField> comparator = new Comparator<FeatureKeyField>() {
                @Override
                public int compare(FeatureKeyField o1, FeatureKeyField o2) {
                    return o1.getSet().compareTo(o2.getSet());
                }
            };
            ArrayList<FeatureKeyField> sorted = new ArrayList<>(this.getDefinition().getKeyFields());
            Collections.sort(sorted, comparator);
            Iterator<FeatureKeyField> it = sorted.iterator();
            while (it.hasNext()) {
                keyField = it.next();
                if ((previousKey == null)
                        || (previousKey.getSet().compareTo(keyField.getSet()) != 0)) {
                    if (previousKey != null) {
                        code.append("} ");
                    }
                    code.append("@Override ");
                    code.append("public void ").append(keyField.getSet()).append("(BufferedImage image) {");
                    code.append("super.").append(keyField.getSet()).append("(image); ");
                }
                switch (keyField.getExtractionEnum()) {
                    case Histogram:
                        code.append("featureVector").append(keyField.getFieldOrder()).
                                append(" = ExtractionUtil.histogram(this.").
                                append(keyField.getGet()).append("()); ");
                        break;
                    case HistogramStatistical:
                        code.append("featureVector").append(keyField.getFieldOrder()).
                                append(" = ExtractionUtil.histogramStatistical(this.").
                                append(keyField.getGet()).append("()); ");
                        break;
                    case HaralickSymmetric:
                        code.append("featureVector").append(keyField.getFieldOrder()).
                                append(" = ExtractionUtil.haralickSymmetric(this.").
                                append(keyField.getGet()).append("()); ");
                        break;
                    case HaralickAssymmetric:
                        code.append("featureVector").append(keyField.getFieldOrder()).
                                append(" = ExtractionUtil.haralickAssymmetric(this.").
                                append(keyField.getGet()).append("()); ");
                        break;
                }
                previousKey = keyField;
            }
            code.append("} ");
        }
    }

    private void generatePushKey() {
        code.append("@Override ");
        code.append("public void pushKey(byte[] array, int position){ ");
        code.append("PushPage push = new PushPage(array, position); ");
        for (FeatureKeyField keyField : this.getDefinition().getKeyFields()) {
            code.append("push.pushMatrix(featureVector").append(keyField.getFieldOrder()).append("); ");
        }
        code.append("} ");
    }

    private void generateSizeOfKey() {
        code.append("@Override ");
        code.append("public int sizeOfKey() { ");
        code.append("return ");
        if (this.getDefinition().getKeyFields().isEmpty()) {
            code.append("0.0d");
        } else {
            FeatureKeyField keyField;
            Iterator<FeatureKeyField> it = this.getDefinition().getKeyFields().iterator();
            while (it.hasNext()) {
                keyField = it.next();
                code.append("Page.sizeOfMatrix(featureVector").append(keyField.getFieldOrder()).append(")");
                if (it.hasNext()) {
                    code.append(" + ");
                }
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
        code = new StringBuilder();

        generatePakage();

        generateImport();

        generateBeginClass();

        generateClassId();

        generatePreservedDistance();

        generateFeatureVector();

        generateDefaultConstructor();

        generateEntityConstructor();

//        generateCopyConstructor();
        
        generateInitializationConstructor();

        generateUuidConstructor();

        generateHasSameKey();

        generateGetPreservedDistance();

        generateSetPreservedDistance();

        generateDistanceTo();

        generateGetKeyStructure();

        generatePullKey();

        generateBufferedImage();

        generatePushKey();

        generateSizeOfKey();

        generateEndClass();

        return code.toString();
    }

}
