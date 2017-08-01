package org.obinject.sample.pessoa; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.meta.Sort; import org.obinject.meta.Uuid; import org.obinject.storage.KeyStructure; public class SortOnePessoa extends $Pessoa implements Sort<SortOnePessoa>, Comparable<SortOnePessoa> { private static Uuid classId; public static Uuid getClassId(){ if (SortOnePessoa.classId == null) {SortOnePessoa.classId = Uuid.fromString("06F3F1AA-BA98-F004-9BCB-E780749BE659"); }return SortOnePessoa.classId; } public SortOnePessoa(){} public SortOnePessoa($Pessoa obj){ super(obj); } public SortOnePessoa(Pessoa obj, Uuid uuid){ super(obj, uuid); } public SortOnePessoa(Uuid uuid){ super(uuid); } @Override public boolean hasSameKey(SortOnePessoa obj) {  return (((this.getNome() == null) && (obj.getNome() == null)) || ((this.getNome() != null) && (obj.getNome() != null) && (this.getNome().equals( obj.getNome())))); } @Override public int compareTo(SortOnePessoa obj) { if (((this.getNome() == null) || (this.getNome() != null) && (obj.getNome() != null) && (this.getNome().compareTo( obj.getNome()) < 0))) { return -1; } else if (((this.getNome() == null) || (this.getNome() != null) && (obj.getNome() != null) && (this.getNome().compareTo( obj.getNome()) == 0))) { return 0; } else {return 1; } }@Override public KeyStructure<SortOnePessoa> getKeyStructure() { return sortOnePessoaStructure; } @Override public boolean pullKey(byte[] array, int position) { PullPage pull = new PullPage(array, position); this.setNome(pull.pullString()); return true; } @Override public void pushKey(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushString(this.getNome()); } @Override public int sizeOfKey() { return  + Page.sizeOfString(this.getNome()); } } 