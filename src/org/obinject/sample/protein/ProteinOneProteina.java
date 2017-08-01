package org.obinject.sample.protein; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.generator.DistanceUtil; import org.obinject.meta.Edition; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class ProteinOneProteina extends $Proteina implements Edition<ProteinOneProteina> { private double preservedDistance; private static Uuid classId; public static Uuid getClassId(){ if (ProteinOneProteina.classId == null) {ProteinOneProteina.classId = Uuid.fromString("226AC1C8-2FA4-A93B-F910-4204F6CAA515"); }return ProteinOneProteina.classId; } public ProteinOneProteina(){} public ProteinOneProteina($Proteina obj){ super(obj); } public ProteinOneProteina(Proteina obj, Uuid uuid){ super(obj, uuid); } public ProteinOneProteina(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(ProteinOneProteina obj) {  return (((this.getAminoAcids() == null) && (obj.getAminoAcids() == null)) || ((this.getAminoAcids() != null) && (obj.getAminoAcids() != null) && (this.getAminoAcids().equals( obj.getAminoAcids())))); } @Override public double getPreservedDistance() { return this.preservedDistance; } @Override public String getString(){ return "" + getAminoAcids();} @Override public double distanceTo(ProteinOneProteina obj){ return DistanceUtil.protein(this.getString(), obj.getString()); } @Override public KeyStructure<ProteinOneProteina> getKeyStructure() { return proteinOneProteinaStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); this.setAminoAcids(pull.pullString()); return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushString(this.getAminoAcids()); } @Override public void setPreservedDistance(double distance) { this.preservedDistance = distance; } @Override public int sizeOfKey() { return  + Page.sizeOfString(this.getAminoAcids()); } } 