package org.obinject.sample.join.multiview; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.generator.DistanceUtil; import org.obinject.meta.Point; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class PointOneObjectImage extends $ObjectImage implements Point<PointOneObjectImage> { private double preservedDistance; private static Uuid classId; public static Uuid getClassId(){ if (PointOneObjectImage.classId == null) {PointOneObjectImage.classId = Uuid.fromString("8544EA2B-C15F-723C-566D-2FFB1FD355D1"); }return PointOneObjectImage.classId; } public PointOneObjectImage(){} public PointOneObjectImage($ObjectImage obj){ super(obj); } public PointOneObjectImage(ObjectImage obj, Uuid uuid){ super(obj, uuid); } public PointOneObjectImage(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(PointOneObjectImage obj) { int i=0; while(( i < this.numberOfDimensions()) && (this.getOrigin(i) == obj.getOrigin(i))){ i++; }return i == this.numberOfDimensions(); }@Override public double getOrigin(int axis){ switch(axis){ case 0: return getFeature()[0]; case 1: return getFeature()[1]; case 2: return getFeature()[2]; case 3: return getFeature()[3]; case 4: return getFeature()[4]; case 5: return getFeature()[5]; case 6: return getFeature()[6]; case 7: return getFeature()[7]; case 8: return getFeature()[8]; case 9: return getFeature()[9]; case 10: return getFeature()[10]; case 11: return getFeature()[11]; case 12: return getFeature()[12]; default: return 0; } } @Override public void setOrigin(int axis, double value){ switch(axis){ case 0: getFeature()[0] = value; break; case 1: getFeature()[1] = value; break; case 2: getFeature()[2] = value; break; case 3: getFeature()[3] = value; break; case 4: getFeature()[4] = value; break; case 5: getFeature()[5] = value; break; case 6: getFeature()[6] = value; break; case 7: getFeature()[7] = value; break; case 8: getFeature()[8] = value; break; case 9: getFeature()[9] = value; break; case 10: getFeature()[10] = value; break; case 11: getFeature()[11] = value; break; case 12: getFeature()[12] = value; break; } } @Override public double getPreservedDistance() { return this.preservedDistance; } @Override public int numberOfDimensions(){ return 13;} @Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } @Override public double distanceTo(PointOneObjectImage obj){ return DistanceUtil.euclidean(this, obj); } @Override public KeyStructure<PointOneObjectImage> getKeyStructure() { return pointOneObjectImageStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ this.setOrigin(i, pull.pullDouble()); } return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ push.pushDouble(this.getOrigin(i));} } @Override public int sizeOfKey() { return Page.sizeOfDouble * this.numberOfDimensions();} } 