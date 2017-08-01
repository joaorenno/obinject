package org.obinject.sample.join.uniform; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.generator.DistanceUtil; import org.obinject.meta.Rectangle; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class RectangleOnePonto extends $Ponto implements Rectangle<RectangleOnePonto> { private static Uuid classId; public static Uuid getClassId(){ if (RectangleOnePonto.classId == null) {RectangleOnePonto.classId = Uuid.fromString("471A824A-ECB1-29B8-260A-AFC07D9F2D88"); }return RectangleOnePonto.classId; } private double preservedDistance; private double extensions[] = new double[2];public RectangleOnePonto(){} public RectangleOnePonto($Ponto obj){ super(obj); } public RectangleOnePonto(Ponto obj, Uuid uuid){ super(obj, uuid); } public RectangleOnePonto(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(RectangleOnePonto obj) { int i=0; while(( i < this.numberOfDimensions()) && (this.getOrigin(i) == obj.getOrigin(i))){ i++; }return i == this.numberOfDimensions(); }@Override public KeyStructure<RectangleOnePonto> getKeyStructure() { return rectangleOnePontoStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ this.setOrigin(i, pull.pullDouble()); this.setExtension(i, pull.pullDouble()); } return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ push.pushDouble(this.getOrigin(i)); push.pushDouble(this.getExtension(i)); } } @Override public int sizeOfKey() { return Page.sizeOfDouble * 2 * this.numberOfDimensions();} @Override public double getOrigin(int axis){ switch(axis){ case 0: return getX(); case 1: return getY(); default: return 0; } } @Override public void setOrigin(int axis, double value){ switch(axis){ case 0: setX((int)value); break; case 1: setY((int)value); break; } } @Override public int numberOfDimensions(){ return 2;} @Override public double getExtension(int axis){ return extensions[axis]; } @Override public void setExtension(int axis, double value){ extensions[axis]=value; } @Override public double getPreservedDistance() { return this.preservedDistance; } @Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } @Override public double distanceTo(RectangleOnePonto obj){ return DistanceUtil.euclidean(this, obj); } } 