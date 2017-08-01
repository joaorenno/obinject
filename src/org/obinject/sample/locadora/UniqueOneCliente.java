package org.obinject.sample.locadora; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.Sort; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class UniqueOneCliente extends $Cliente implements Sort<UniqueOneCliente>, Comparable<UniqueOneCliente> { private static Uuid classId; public static Uuid getClassId(){ if (UniqueOneCliente.classId == null) {UniqueOneCliente.classId = Uuid.fromString("F5560E9D-50E5-424F-FCC8-77B3FE887066"); }return UniqueOneCliente.classId; } public UniqueOneCliente(){} public UniqueOneCliente($Cliente obj){ super(obj); } public UniqueOneCliente(Cliente obj, Uuid uuid){ super(obj, uuid); } public UniqueOneCliente(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(UniqueOneCliente obj) {  return (((this.getLogin() == null) && (obj.getLogin() == null)) || ((this.getLogin() != null) && (obj.getLogin() != null) && (this.getLogin().equals( obj.getLogin())))); } @Override public int compareTo(UniqueOneCliente obj) { if (((this.getLogin() == null) || (this.getLogin() != null) && (obj.getLogin() != null) && (this.getLogin().compareTo( obj.getLogin()) < 0))) { return -1; } else if (((this.getLogin() == null) || (this.getLogin() != null) && (obj.getLogin() != null) && (this.getLogin().compareTo( obj.getLogin()) == 0))) { return 0; } else {return 1; } } @Override public KeyStructure<UniqueOneCliente> getKeyStructure() { return uniqueOneClienteStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); this.setLogin(pull.pullString()); return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushString(this.getLogin()); } @Override public int sizeOfKey() { return  + Page.sizeOfString(this.getLogin()); } } 