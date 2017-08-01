package org.obinject.sample.color; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.generator.DistanceUtil; import org.obinject.meta.Rectangle; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class RectangleOneCoocTexture extends $CoocTexture implements Rectangle<RectangleOneCoocTexture> { private static Uuid classId; public static Uuid getClassId(){ if (RectangleOneCoocTexture.classId == null) {RectangleOneCoocTexture.classId = Uuid.fromString("F351A489-3667-62B5-CF1E-63B77F26ED57"); }return RectangleOneCoocTexture.classId; } private double preservedDistance; public RectangleOneCoocTexture(){} public RectangleOneCoocTexture($CoocTexture obj){ super(obj); } public RectangleOneCoocTexture(CoocTexture obj, Uuid uuid){ super(obj, uuid); } public RectangleOneCoocTexture(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(RectangleOneCoocTexture obj) { int i=0; while(( i < this.numberOfDimensions()) && (this.getOrigin(i) == obj.getOrigin(i))){ i++; }return i == this.numberOfDimensions(); }@Override public KeyStructure<RectangleOneCoocTexture> getKeyStructure() { return rectangleOneCoocTextureStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ this.setOrigin(i, pull.pullDouble()); this.setExtension(i, pull.pullDouble()); } return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); for (int i = 0; i < this.numberOfDimensions(); i++){ push.pushDouble(this.getOrigin(i)); push.pushDouble(this.getExtension(i)); } } @Override public int sizeOfKey() { return Page.sizeOfDouble * 2 * this.numberOfDimensions();} @Override public double getOrigin(int axis){ switch(axis){ case 0: return getTextures()[0]; case 1: return getTextures()[1]; case 2: return getTextures()[2]; case 3: return getTextures()[3]; case 4: return getTextures()[4]; case 5: return getTextures()[5]; case 6: return getTextures()[6]; case 7: return getTextures()[7]; case 8: return getTextures()[8]; case 9: return getTextures()[9]; case 10: return getTextures()[10]; case 11: return getTextures()[11]; case 12: return getTextures()[12]; case 13: return getTextures()[13]; case 14: return getTextures()[14]; case 15: return getTextures()[15]; default: return 0; } } @Override public void setOrigin(int axis, double value){ switch(axis){ case 0: getTextures()[0] = value; break; case 1: getTextures()[1] = value; break; case 2: getTextures()[2] = value; break; case 3: getTextures()[3] = value; break; case 4: getTextures()[4] = value; break; case 5: getTextures()[5] = value; break; case 6: getTextures()[6] = value; break; case 7: getTextures()[7] = value; break; case 8: getTextures()[8] = value; break; case 9: getTextures()[9] = value; break; case 10: getTextures()[10] = value; break; case 11: getTextures()[11] = value; break; case 12: getTextures()[12] = value; break; case 13: getTextures()[13] = value; break; case 14: getTextures()[14] = value; break; case 15: getTextures()[15] = value; break; } } @Override public int numberOfDimensions(){ return 16;} @Override public double getExtension(int axis){ switch(axis){ case 0: return getExtension()[0]; case 1: return getExtension()[1]; case 2: return getExtension()[2]; case 3: return getExtension()[3]; case 4: return getExtension()[4]; case 5: return getExtension()[5]; case 6: return getExtension()[6]; case 7: return getExtension()[7]; case 8: return getExtension()[8]; case 9: return getExtension()[9]; case 10: return getExtension()[10]; case 11: return getExtension()[11]; case 12: return getExtension()[12]; case 13: return getExtension()[13]; case 14: return getExtension()[14]; case 15: return getExtension()[15]; default: return 0; } } @Override public void setExtension(int axis, double value){ switch(axis){ case 0: getExtension()[0] = value; break; case 1: getExtension()[1] = value; break; case 2: getExtension()[2] = value; break; case 3: getExtension()[3] = value; break; case 4: getExtension()[4] = value; break; case 5: getExtension()[5] = value; break; case 6: getExtension()[6] = value; break; case 7: getExtension()[7] = value; break; case 8: getExtension()[8] = value; break; case 9: getExtension()[9] = value; break; case 10: getExtension()[10] = value; break; case 11: getExtension()[11] = value; break; case 12: getExtension()[12] = value; break; case 13: getExtension()[13] = value; break; case 14: getExtension()[14] = value; break; case 15: getExtension()[15] = value; break; } } @Override public double getPreservedDistance() { return this.preservedDistance; } @Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } @Override public double distanceTo(RectangleOneCoocTexture obj){ return DistanceUtil.euclidean(this, obj); } } 