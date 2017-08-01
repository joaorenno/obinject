package org.obinject.sample.avatar; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.Sort; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class UniqueOneRegiao extends $Regiao implements Sort<UniqueOneRegiao>, Comparable<UniqueOneRegiao> { private static Uuid classId; public static Uuid getClassId(){ if (UniqueOneRegiao.classId == null) {UniqueOneRegiao.classId = Uuid.fromString("0F7CE82C-2E2B-EB3F-54AD-4B5422A8A953"); }return UniqueOneRegiao.classId; } public UniqueOneRegiao(){} public UniqueOneRegiao($Regiao obj){ super(obj); } public UniqueOneRegiao(Regiao obj, Uuid uuid){ super(obj, uuid); } public UniqueOneRegiao(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(UniqueOneRegiao obj) {  return (((this.getNome() == null) && (obj.getNome() == null)) || ((this.getNome() != null) && (obj.getNome() != null) && (this.getNome().equals( obj.getNome())))); } @Override public int compareTo(UniqueOneRegiao obj) { if (((this.getNome() == null) || (this.getNome() != null) && (obj.getNome() != null) && (this.getNome().compareTo( obj.getNome()) < 0))) { return -1; } else if (((this.getNome() == null) || (this.getNome() != null) && (obj.getNome() != null) && (this.getNome().compareTo( obj.getNome()) == 0))) { return 0; } else {return 1; } } @Override public KeyStructure<UniqueOneRegiao> getKeyStructure() { return uniqueOneRegiaoStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); this.setNome(pull.pullString()); return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushString(this.getNome()); } @Override public int sizeOfKey() { return  + Page.sizeOfString(this.getNome()); } } 