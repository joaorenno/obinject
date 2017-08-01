package org.obinject.sample.avatar; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.Sort; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class UniqueOneJazida extends $Jazida implements Sort<UniqueOneJazida>, Comparable<UniqueOneJazida> { private static Uuid classId; public static Uuid getClassId(){ if (UniqueOneJazida.classId == null) {UniqueOneJazida.classId = Uuid.fromString("32BC8B81-B1E8-478D-6CB0-9AF2EC0AA83D"); }return UniqueOneJazida.classId; } public UniqueOneJazida(){} public UniqueOneJazida($Jazida obj){ super(obj); } public UniqueOneJazida(Jazida obj, Uuid uuid){ super(obj, uuid); } public UniqueOneJazida(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(UniqueOneJazida obj) { return (this.getLatitude() == obj.getLatitude()) && (this.getLongitude() == obj.getLongitude()); } @Override public int compareTo(UniqueOneJazida obj) { if ((this.getLatitude() < obj.getLatitude()) && (this.getLongitude() < obj.getLongitude())) { return -1; } else if ((this.getLatitude() == obj.getLatitude()) && (this.getLongitude() == obj.getLongitude())) { return 0; } else {return 1; } } @Override public KeyStructure<UniqueOneJazida> getKeyStructure() { return uniqueOneJazidaStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); this.setLatitude(pull.pullInteger()); this.setLongitude(pull.pullInteger()); return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushInteger(this.getLatitude()); push.pushInteger(this.getLongitude()); } @Override public int sizeOfKey() { return  + Page.sizeOfInteger + Page.sizeOfInteger; } } 