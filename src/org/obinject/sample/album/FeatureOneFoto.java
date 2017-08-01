package org.obinject.sample.album; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.generator.DistanceUtil; import org.obinject.meta.Metric; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; import java.awt.image.BufferedImage; import org.obinject.meta.generator.ExtractionUtil; public class FeatureOneFoto extends $Foto implements Metric<FeatureOneFoto> { private static Uuid classId; public static Uuid getClassId(){ if (FeatureOneFoto.classId == null) {FeatureOneFoto.classId = Uuid.fromString("B79F0443-400C-B781-CC43-FAC03893AF0E"); }return FeatureOneFoto.classId; } private double preservedDistance; private double featureVectorFirst[]; private double featureVectorSecond[]; public FeatureOneFoto(){ featureVectorFirst= ExtractionUtil.histogram(this.getImagem()); featureVectorSecond= ExtractionUtil.haralickAssymmetric(this.getImagem()); } public FeatureOneFoto($Foto obj){ super(obj); featureVectorFirst= ExtractionUtil.histogram(this.getImagem()); featureVectorSecond= ExtractionUtil.haralickAssymmetric(this.getImagem()); } public FeatureOneFoto(Foto obj, Uuid uuid){ super(obj, uuid); featureVectorFirst= ExtractionUtil.histogram(this.getImagem()); featureVectorSecond= ExtractionUtil.haralickAssymmetric(this.getImagem()); } public FeatureOneFoto(Uuid uuid){ super(uuid); featureVectorFirst= ExtractionUtil.histogram(this.getImagem()); featureVectorSecond= ExtractionUtil.haralickAssymmetric(this.getImagem()); } @Override public boolean hasSameKey(FeatureOneFoto obj) { return Page.matricesAreEqual(this.featureVectorFirst, obj.featureVectorFirst)  && Page.matricesAreEqual(this.featureVectorSecond, obj.featureVectorSecond) ; } @Override public double getPreservedDistance() { return this.preservedDistance; } @Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } @Override public double distanceTo(FeatureOneFoto obj){ return DistanceUtil.euclidean(this.featureVectorFirst, obj.featureVectorFirst)  + DistanceUtil.euclidean(this.featureVectorSecond, obj.featureVectorSecond) ; } @Override public KeyStructure<FeatureOneFoto> getKeyStructure() { return featureOneFotoStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); featureVectorFirst = (double[]) pull.pullMatrix(); featureVectorSecond = (double[]) pull.pullMatrix(); return true; } @Override public void setImagem(BufferedImage image) {super.setImagem(image); featureVectorFirst = ExtractionUtil.histogram(this.getImagem()); featureVectorSecond = ExtractionUtil.haralickAssymmetric(this.getImagem()); } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushMatrix(featureVectorFirst); push.pushMatrix(featureVectorSecond); } @Override public int sizeOfKey() { return Page.sizeOfMatrix(featureVectorFirst) + Page.sizeOfMatrix(featureVectorSecond); } } 