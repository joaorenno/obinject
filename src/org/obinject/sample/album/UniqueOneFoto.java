package org.obinject.sample.album; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.Sort; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class UniqueOneFoto extends $Foto implements Sort<UniqueOneFoto>, Comparable<UniqueOneFoto> { private static Uuid classId; public static Uuid getClassId(){ if (UniqueOneFoto.classId == null) {UniqueOneFoto.classId = Uuid.fromString("B918560C-D1CF-8782-48EB-9F3C639EE871"); }return UniqueOneFoto.classId; } public UniqueOneFoto(){} public UniqueOneFoto($Foto obj){ super(obj); } public UniqueOneFoto(Foto obj, Uuid uuid){ super(obj, uuid); } public UniqueOneFoto(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(UniqueOneFoto obj) { return (this.getCodigo() == obj.getCodigo()); } @Override public int compareTo(UniqueOneFoto obj) { if ((this.getCodigo() < obj.getCodigo())) { return -1; } else if ((this.getCodigo() == obj.getCodigo())) { return 0; } else {return 1; } } @Override public KeyStructure<UniqueOneFoto> getKeyStructure() { return uniqueOneFotoStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); this.setCodigo(pull.pullInteger()); return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushInteger(this.getCodigo()); } @Override public int sizeOfKey() { return  + Page.sizeOfInteger; } } 