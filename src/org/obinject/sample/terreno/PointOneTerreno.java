package org.obinject.sample.terreno; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.generator.DistanceUtil; import org.obinject.meta.Point; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class PointOneTerreno extends $Terreno implements Point<PointOneTerreno> { private double preservedDistance; private static Uuid classId; public static Uuid getClassId(){ if (PointOneTerreno.classId == null) {PointOneTerreno.classId = Uuid.fromString("37C1C411-D451-C88F-D95F-CAC67EB433B4"); }return PointOneTerreno.classId; } public PointOneTerreno(){} public PointOneTerreno($Terreno obj){ super(obj); } public PointOneTerreno(Terreno obj, Uuid uuid){ super(obj, uuid); } public PointOneTerreno(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(PointOneTerreno obj) { int i=0; while(( i < this.numberOfDimensions()) && (this.getOrigin(i) == obj.getOrigin(i))){ i++; }return i == this.numberOfDimensions(); }@Override public double getOrigin(int axis){ switch(axis){ case 0: return getCoordenadaOrigem()[0]; case 1: return getCoordenadaOrigem()[1]; default: return 0; } } @Override public void setOrigin(int axis, double value){ switch(axis){ case 0: getCoordenadaOrigem()[0] = (float)value; break; case 1: getCoordenadaOrigem()[1] = (float)value; break; } } @Override public double getPreservedDistance() { return this.preservedDistance; } @Override public int numberOfDimensions(){ return 2;} @Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } @Override public double distanceTo(PointOneTerreno obj){ return DistanceUtil.euclidean(this, obj); } @Override public KeyStructure<PointOneTerreno> getKeyStructure() { return pointOneTerrenoStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ this.setOrigin(i, pull.pullDouble()); } return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ push.pushDouble(this.getOrigin(i));} } @Override public int sizeOfKey() { return Page.sizeOfDouble * this.numberOfDimensions();} } 