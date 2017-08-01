package org.obinject.sample.city; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.generator.DistanceUtil; import org.obinject.meta.Edition; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class EditionOneCity extends $City implements Edition<EditionOneCity> { private double preservedDistance; private static Uuid classId; public static Uuid getClassId(){ if (EditionOneCity.classId == null) {EditionOneCity.classId = Uuid.fromString("8531BE18-6028-2D95-7241-85317BC3E781"); }return EditionOneCity.classId; } public EditionOneCity(){} public EditionOneCity($City obj){ super(obj); } public EditionOneCity(City obj, Uuid uuid){ super(obj, uuid); } public EditionOneCity(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(EditionOneCity obj) {  return (((this.getName() == null) && (obj.getName() == null)) || ((this.getName() != null) && (obj.getName() != null) && (this.getName().equals( obj.getName())))); } @Override public double getPreservedDistance() { return this.preservedDistance; } @Override public String getString(){ return "" + getName();} @Override public double distanceTo(EditionOneCity obj){ return DistanceUtil.levenshtein(this.getString(), obj.getString()); } @Override public KeyStructure<EditionOneCity> getKeyStructure() { return editionOneCityStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); this.setName(pull.pullString()); return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushString(this.getName()); } @Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } @Override public int sizeOfKey() { return  + Page.sizeOfString(this.getName()); } } 