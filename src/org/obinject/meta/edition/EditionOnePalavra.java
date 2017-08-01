package org.obinject.meta.edition; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.generator.DistanceUtil; import org.obinject.meta.Edition; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class EditionOnePalavra extends $Palavra implements Edition<EditionOnePalavra> { private double preservedDistance; private static Uuid classId; public static Uuid getClassId(){ if (EditionOnePalavra.classId == null) {EditionOnePalavra.classId = Uuid.fromString("35CAD72C-963E-EA8B-0160-9B843D97F6ED"); }return EditionOnePalavra.classId; } public EditionOnePalavra(){} public EditionOnePalavra($Palavra obj){ super(obj); } public EditionOnePalavra(Palavra obj, Uuid uuid){ super(obj, uuid); } public EditionOnePalavra(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(EditionOnePalavra obj) {  return (((this.getPalavra() == null) && (obj.getPalavra() == null)) || ((this.getPalavra() != null) && (obj.getPalavra() != null) && (this.getPalavra().equals( obj.getPalavra())))) && (((this.getAntonimo() == null) && (obj.getAntonimo() == null)) || ((this.getAntonimo() != null) && (obj.getAntonimo() != null) && (this.getAntonimo().equals( obj.getAntonimo())))) && (((this.getSinonimo() == null) && (obj.getSinonimo() == null)) || ((this.getSinonimo() != null) && (obj.getSinonimo() != null) && (this.getSinonimo().equals( obj.getSinonimo())))); } @Override public double getPreservedDistance() { return this.preservedDistance; } @Override public String getString(){ return "" + getPalavra() + getAntonimo() + getSinonimo();} @Override public double distanceTo(EditionOnePalavra obj){ return DistanceUtil.levenshtein(this.getString(), obj.getString()); } @Override public KeyStructure<EditionOnePalavra> getKeyStructure() { return editionOnePalavraStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); this.setPalavra(pull.pullString()); this.setAntonimo(pull.pullString()); this.setSinonimo(pull.pullString()); return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushString(this.getPalavra()); push.pushString(this.getAntonimo()); push.pushString(this.getSinonimo()); } @Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } @Override public int sizeOfKey() { return  + Page.sizeOfString(this.getPalavra()) + Page.sizeOfString(this.getAntonimo()) + Page.sizeOfString(this.getSinonimo()); } } 