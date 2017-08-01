package org.obinject.sample.exame; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.generator.DistanceUtil; import org.obinject.meta.Metric; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; import java.awt.image.BufferedImage; import org.obinject.meta.generator.ExtractionUtil; public class FeatureTwoExame extends $Exame implements Metric<FeatureTwoExame> { private static Uuid classId; public static Uuid getClassId(){ if (FeatureTwoExame.classId == null) {FeatureTwoExame.classId = Uuid.fromString("4D63839E-476C-84BE-B5BD-DA154A15ED8E"); }return FeatureTwoExame.classId; } private double preservedDistance; private double featureVectorFirst[]; private double featureVectorSecond[]; private double featureVectorThird[]; public FeatureTwoExame(){ featureVectorFirst= ExtractionUtil.haralickSymmetric(this.getImagem()); featureVectorSecond= ExtractionUtil.histogramStatistical(this.getImagem()); featureVectorThird= ExtractionUtil.histogram(this.getImagem()); } public FeatureTwoExame($Exame obj){ super(obj); featureVectorFirst= ExtractionUtil.haralickSymmetric(this.getImagem()); featureVectorSecond= ExtractionUtil.histogramStatistical(this.getImagem()); featureVectorThird= ExtractionUtil.histogram(this.getImagem()); } public FeatureTwoExame(Exame obj, Uuid uuid){ super(obj, uuid); featureVectorFirst= ExtractionUtil.haralickSymmetric(this.getImagem()); featureVectorSecond= ExtractionUtil.histogramStatistical(this.getImagem()); featureVectorThird= ExtractionUtil.histogram(this.getImagem()); } public FeatureTwoExame(Uuid uuid){ super(uuid); featureVectorFirst= ExtractionUtil.haralickSymmetric(this.getImagem()); featureVectorSecond= ExtractionUtil.histogramStatistical(this.getImagem()); featureVectorThird= ExtractionUtil.histogram(this.getImagem()); } @Override public boolean hasSameKey(FeatureTwoExame obj) { return Page.matricesAreEqual(this.featureVectorFirst, obj.featureVectorFirst)  && Page.matricesAreEqual(this.featureVectorSecond, obj.featureVectorSecond)  && Page.matricesAreEqual(this.featureVectorThird, obj.featureVectorThird) ; } @Override public double getPreservedDistance() { return this.preservedDistance; } @Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } @Override public double distanceTo(FeatureTwoExame obj){ return DistanceUtil.euclidean(this.featureVectorFirst, obj.featureVectorFirst)  + DistanceUtil.euclidean(this.featureVectorSecond, obj.featureVectorSecond)  + DistanceUtil.euclidean(this.featureVectorThird, obj.featureVectorThird) ; } @Override public KeyStructure<FeatureTwoExame> getKeyStructure() { return featureTwoExameStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); featureVectorFirst = (double[]) pull.pullMatrix(); featureVectorSecond = (double[]) pull.pullMatrix(); featureVectorThird = (double[]) pull.pullMatrix(); return true; } @Override public void setImagem(BufferedImage image) {super.setImagem(image); featureVectorFirst = ExtractionUtil.haralickSymmetric(this.getImagem()); featureVectorSecond = ExtractionUtil.histogramStatistical(this.getImagem()); featureVectorThird = ExtractionUtil.histogram(this.getImagem()); } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushMatrix(featureVectorFirst); push.pushMatrix(featureVectorSecond); push.pushMatrix(featureVectorThird); } @Override public int sizeOfKey() { return Page.sizeOfMatrix(featureVectorFirst) + Page.sizeOfMatrix(featureVectorSecond) + Page.sizeOfMatrix(featureVectorThird); } } 