package org.obinject.sample.universe; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.Sort; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class UniqueOnePlanet extends $Planet implements Sort<UniqueOnePlanet>, Comparable<UniqueOnePlanet> { private static Uuid classId; public static Uuid getClassId(){ if (UniqueOnePlanet.classId == null) {UniqueOnePlanet.classId = Uuid.fromString("FBDCEB42-2C4D-3E39-95BB-891D657DC595"); }return UniqueOnePlanet.classId; } public UniqueOnePlanet(){} public UniqueOnePlanet($Planet obj){ super(obj); } public UniqueOnePlanet(Planet obj, Uuid uuid){ super(obj, uuid); } public UniqueOnePlanet(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(UniqueOnePlanet obj) {  return (((this.getName() == null) && (obj.getName() == null)) || ((this.getName() != null) && (obj.getName() != null) && (this.getName().equals( obj.getName())))); } @Override public int compareTo(UniqueOnePlanet obj) { if (((this.getName() == null) || (this.getName() != null) && (obj.getName() != null) && (this.getName().compareTo( obj.getName()) < 0))) { return -1; } else if (((this.getName() == null) || (this.getName() != null) && (obj.getName() != null) && (this.getName().compareTo( obj.getName()) == 0))) { return 0; } else {return 1; } } @Override public KeyStructure<UniqueOnePlanet> getKeyStructure() { return uniqueOnePlanetStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); this.setName(pull.pullString()); return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushString(this.getName()); } @Override public int sizeOfKey() { return  + Page.sizeOfString(this.getName()); } } 