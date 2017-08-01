package org.obinject.sample.color; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.generator.DistanceUtil; import org.obinject.meta.Point; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class PointOneColorMoments extends $ColorMoments implements Point<PointOneColorMoments> { private double preservedDistance; private static Uuid classId; public static Uuid getClassId(){ if (PointOneColorMoments.classId == null) {PointOneColorMoments.classId = Uuid.fromString("372B3FC5-AC96-BD3F-696D-38D88DB17222"); }return PointOneColorMoments.classId; } public PointOneColorMoments(){} public PointOneColorMoments($ColorMoments obj){ super(obj); } public PointOneColorMoments(ColorMoments obj, Uuid uuid){ super(obj, uuid); } public PointOneColorMoments(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(PointOneColorMoments obj) { int i=0; while(( i < this.numberOfDimensions()) && (this.getOrigin(i) == obj.getOrigin(i))){ i++; }return i == this.numberOfDimensions(); }@Override public double getOrigin(int axis){ switch(axis){ case 0: return getMoments()[0]; case 1: return getMoments()[1]; case 2: return getMoments()[2]; case 3: return getMoments()[3]; case 4: return getMoments()[4]; case 5: return getMoments()[5]; case 6: return getMoments()[6]; case 7: return getMoments()[7]; case 8: return getMoments()[8]; default: return 0; } } @Override public void setOrigin(int axis, double value){ switch(axis){ case 0: getMoments()[0] = value; break; case 1: getMoments()[1] = value; break; case 2: getMoments()[2] = value; break; case 3: getMoments()[3] = value; break; case 4: getMoments()[4] = value; break; case 5: getMoments()[5] = value; break; case 6: getMoments()[6] = value; break; case 7: getMoments()[7] = value; break; case 8: getMoments()[8] = value; break; } } @Override public double getPreservedDistance() { return this.preservedDistance; } @Override public int numberOfDimensions(){ return 9;} @Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } @Override public double distanceTo(PointOneColorMoments obj){ return DistanceUtil.euclidean(this, obj); } @Override public KeyStructure<PointOneColorMoments> getKeyStructure() { return pointOneColorMomentsStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ this.setOrigin(i, pull.pullDouble()); } return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ push.pushDouble(this.getOrigin(i));} } @Override public int sizeOfKey() { return Page.sizeOfDouble * this.numberOfDimensions();} } 