package org.obinject.sample.pessoa; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.generator.DistanceUtil; import org.obinject.meta.Point; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class PointOnePessoa extends $Pessoa implements Point<PointOnePessoa> { private double preservedDistance; private static Uuid classId; public static Uuid getClassId(){ if (PointOnePessoa.classId == null) {PointOnePessoa.classId = Uuid.fromString("49F57833-55D1-981A-5B2D-194EA361EA56"); }return PointOnePessoa.classId; } public PointOnePessoa(){} public PointOnePessoa($Pessoa obj){ super(obj); } public PointOnePessoa(Pessoa obj, Uuid uuid){ super(obj, uuid); } public PointOnePessoa(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(PointOnePessoa obj) { int i=0; while(( i < this.numberOfDimensions()) && (this.getOrigin(i) == obj.getOrigin(i))){ i++; }return i == this.numberOfDimensions(); }@Override public double getOrigin(int axis){ switch(axis){ case 0: return getCaracteristica()[0]; case 1: return getCaracteristica()[1]; case 2: return getCaracteristica()[2]; case 3: return getCaracteristica()[3]; case 4: return getCaracteristica()[4]; case 5: return getCaracteristica()[5]; case 6: return getCaracteristica()[6]; case 7: return getCaracteristica()[7]; case 8: return getCaracteristica()[8]; case 9: return getCaracteristica()[9]; case 10: return getCaracteristica()[10]; case 11: return getCaracteristica()[11]; case 12: return getCaracteristica()[12]; case 13: return getCaracteristica()[13]; case 14: return getCaracteristica()[14]; default: return 0; } } @Override public void setOrigin(int axis, double value){ switch(axis){ case 0: getCaracteristica()[0] = (float)value; break; case 1: getCaracteristica()[1] = (float)value; break; case 2: getCaracteristica()[2] = (float)value; break; case 3: getCaracteristica()[3] = (float)value; break; case 4: getCaracteristica()[4] = (float)value; break; case 5: getCaracteristica()[5] = (float)value; break; case 6: getCaracteristica()[6] = (float)value; break; case 7: getCaracteristica()[7] = (float)value; break; case 8: getCaracteristica()[8] = (float)value; break; case 9: getCaracteristica()[9] = (float)value; break; case 10: getCaracteristica()[10] = (float)value; break; case 11: getCaracteristica()[11] = (float)value; break; case 12: getCaracteristica()[12] = (float)value; break; case 13: getCaracteristica()[13] = (float)value; break; case 14: getCaracteristica()[14] = (float)value; break; } } @Override public double getPreservedDistance() { return this.preservedDistance; } @Override public int numberOfDimensions(){ return 15;} @Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } @Override public double distanceTo(PointOnePessoa obj){ return DistanceUtil.euclidean(this, obj); } @Override public KeyStructure<PointOnePessoa> getKeyStructure() { return pointOnePessoaStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ this.setOrigin(i, pull.pullDouble()); } return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ push.pushDouble(this.getOrigin(i));} } @Override public int sizeOfKey() { return Page.sizeOfDouble * this.numberOfDimensions();} } 