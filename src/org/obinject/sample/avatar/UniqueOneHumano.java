package org.obinject.sample.avatar; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.Sort; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class UniqueOneHumano extends $Humano implements Sort<UniqueOneHumano>, Comparable<UniqueOneHumano> { private static Uuid classId; public static Uuid getClassId(){ if (UniqueOneHumano.classId == null) {UniqueOneHumano.classId = Uuid.fromString("DCBF13C8-470C-93ED-8FF5-F524712820E0"); }return UniqueOneHumano.classId; } public UniqueOneHumano(){} public UniqueOneHumano($Humano obj){ super(obj); } public UniqueOneHumano(Humano obj, Uuid uuid){ super(obj, uuid); } public UniqueOneHumano(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(UniqueOneHumano obj) {  return (((this.getCpf() == null) && (obj.getCpf() == null)) || ((this.getCpf() != null) && (obj.getCpf() != null) && (this.getCpf().equals( obj.getCpf())))); } @Override public int compareTo(UniqueOneHumano obj) { if (((this.getCpf() == null) || (this.getCpf() != null) && (obj.getCpf() != null) && (this.getCpf().compareTo( obj.getCpf()) < 0))) { return -1; } else if (((this.getCpf() == null) || (this.getCpf() != null) && (obj.getCpf() != null) && (this.getCpf().compareTo( obj.getCpf()) == 0))) { return 0; } else {return 1; } } @Override public KeyStructure<UniqueOneHumano> getKeyStructure() { return uniqueOneHumanoStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); this.setCpf(pull.pullString()); return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushString(this.getCpf()); } @Override public int sizeOfKey() { return  + Page.sizeOfString(this.getCpf()); } } 