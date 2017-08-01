package org.obinject.sample.elections; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.Sort; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class UniqueOneParty extends $Party implements Sort<UniqueOneParty>, Comparable<UniqueOneParty> { private static Uuid classId; public static Uuid getClassId(){ if (UniqueOneParty.classId == null) {UniqueOneParty.classId = Uuid.fromString("F4B4CFC1-8673-47CB-AE72-F1408B5143C7"); }return UniqueOneParty.classId; } public UniqueOneParty(){} public UniqueOneParty($Party obj){ super(obj); } public UniqueOneParty(Party obj, Uuid uuid){ super(obj, uuid); } public UniqueOneParty(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(UniqueOneParty obj) {  return (((this.getName() == null) && (obj.getName() == null)) || ((this.getName() != null) && (obj.getName() != null) && (this.getName().equals( obj.getName())))); } @Override public int compareTo(UniqueOneParty obj) { if (((this.getName() == null) || (this.getName() != null) && (obj.getName() != null) && (this.getName().compareTo( obj.getName()) < 0))) { return -1; } else if (((this.getName() == null) || (this.getName() != null) && (obj.getName() != null) && (this.getName().compareTo( obj.getName()) == 0))) { return 0; } else {return 1; } } @Override public KeyStructure<UniqueOneParty> getKeyStructure() { return uniqueOnePartyStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); this.setName(pull.pullString()); return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushString(this.getName()); } @Override public int sizeOfKey() { return  + Page.sizeOfString(this.getName()); } } 