package org.obinject.sample.color; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.generator.DistanceUtil; import org.obinject.meta.Point; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class PointOneColorHistogram extends $ColorHistogram implements Point<PointOneColorHistogram> { private double preservedDistance; private static Uuid classId; public static Uuid getClassId(){ if (PointOneColorHistogram.classId == null) {PointOneColorHistogram.classId = Uuid.fromString("087F04FD-1BFA-FD19-D074-3722FCB1A147"); }return PointOneColorHistogram.classId; } public PointOneColorHistogram(){} public PointOneColorHistogram($ColorHistogram obj){ super(obj); } public PointOneColorHistogram(ColorHistogram obj, Uuid uuid){ super(obj, uuid); } public PointOneColorHistogram(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(PointOneColorHistogram obj) { int i=0; while(( i < this.numberOfDimensions()) && (this.getOrigin(i) == obj.getOrigin(i))){ i++; }return i == this.numberOfDimensions(); }@Override public double getOrigin(int axis){ switch(axis){ case 0: return getColors()[0]; case 1: return getColors()[1]; case 2: return getColors()[2]; case 3: return getColors()[3]; case 4: return getColors()[4]; case 5: return getColors()[5]; case 6: return getColors()[6]; case 7: return getColors()[7]; case 8: return getColors()[8]; case 9: return getColors()[9]; case 10: return getColors()[10]; case 11: return getColors()[11]; case 12: return getColors()[12]; case 13: return getColors()[13]; case 14: return getColors()[14]; case 15: return getColors()[15]; case 16: return getColors()[16]; case 17: return getColors()[17]; case 18: return getColors()[18]; case 19: return getColors()[19]; case 20: return getColors()[20]; case 21: return getColors()[21]; case 22: return getColors()[22]; case 23: return getColors()[23]; case 24: return getColors()[24]; case 25: return getColors()[25]; case 26: return getColors()[26]; case 27: return getColors()[27]; case 28: return getColors()[28]; case 29: return getColors()[29]; case 30: return getColors()[30]; case 31: return getColors()[31]; default: return 0; } } @Override public void setOrigin(int axis, double value){ switch(axis){ case 0: getColors()[0] = value; break; case 1: getColors()[1] = value; break; case 2: getColors()[2] = value; break; case 3: getColors()[3] = value; break; case 4: getColors()[4] = value; break; case 5: getColors()[5] = value; break; case 6: getColors()[6] = value; break; case 7: getColors()[7] = value; break; case 8: getColors()[8] = value; break; case 9: getColors()[9] = value; break; case 10: getColors()[10] = value; break; case 11: getColors()[11] = value; break; case 12: getColors()[12] = value; break; case 13: getColors()[13] = value; break; case 14: getColors()[14] = value; break; case 15: getColors()[15] = value; break; case 16: getColors()[16] = value; break; case 17: getColors()[17] = value; break; case 18: getColors()[18] = value; break; case 19: getColors()[19] = value; break; case 20: getColors()[20] = value; break; case 21: getColors()[21] = value; break; case 22: getColors()[22] = value; break; case 23: getColors()[23] = value; break; case 24: getColors()[24] = value; break; case 25: getColors()[25] = value; break; case 26: getColors()[26] = value; break; case 27: getColors()[27] = value; break; case 28: getColors()[28] = value; break; case 29: getColors()[29] = value; break; case 30: getColors()[30] = value; break; case 31: getColors()[31] = value; break; } } @Override public double getPreservedDistance() { return this.preservedDistance; } @Override public int numberOfDimensions(){ return 32;} @Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } @Override public double distanceTo(PointOneColorHistogram obj){ return DistanceUtil.euclidean(this, obj); } @Override public KeyStructure<PointOneColorHistogram> getKeyStructure() { return pointOneColorHistogramStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ this.setOrigin(i, pull.pullDouble()); } return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ push.pushDouble(this.getOrigin(i));} } @Override public int sizeOfKey() { return Page.sizeOfDouble * this.numberOfDimensions();} } 