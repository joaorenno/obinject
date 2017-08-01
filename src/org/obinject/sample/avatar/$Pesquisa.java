package org.obinject.sample.avatar; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.device.File; import org.obinject.exception.TransientObjectException; import org.obinject.meta.Entity; import org.obinject.meta.Uuid; import org.obinject.queries.Attribute; import org.obinject.queries.Schema; import org.obinject.storage.BTree; import org.obinject.storage.BTreeEntity; import org.obinject.storage.EntityStructure; import org.obinject.storage.KeyStructure; import org.obinject.storage.Structure; public class $Pesquisa extends Pesquisa implements Entity<$Pesquisa> { protected Uuid uuid; private static Uuid classId; public static Uuid getClassId(){ if ($Pesquisa.classId == null) {$Pesquisa.classId = Uuid.fromString("4E3B8CBC-C7A8-60BB-C24C-9A1F1EE10955"); }return $Pesquisa.classId; } public static final BTreeEntity<$Pesquisa> entityStructure = new BTreeEntity<$Pesquisa>(new File("build/classes/org/obinject/sample/avatar/avatar.dbo", 4096)){}; public static final BTree<UniqueOnePesquisa> uniqueOnePesquisaStructure = new BTree<UniqueOnePesquisa>(new File("build/classes/org/obinject/sample/avatar/avatar.dbo", 4096)){}; public static final Attribute<java.lang.String> nome = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Pesquisa)entity).getNome(); } }; public static final Attribute<Boolean> resultado = new Attribute<Boolean> (){ @Override public Boolean valueOfAttribute(Entity entity) { return ((Pesquisa)entity).isResultado(); } }; public static final Attribute<org.obinject.sample.avatar.Avatar> avatar = new Attribute<org.obinject.sample.avatar.Avatar> (){ @Override public org.obinject.sample.avatar.Avatar valueOfAttribute(Entity entity) { return ((Pesquisa)entity).getAvatar(); } }; public static final Attribute<java.util.List> cientistas = new Attribute<java.util.List> (){ @Override public java.util.List valueOfAttribute(Entity entity) { return ((Pesquisa)entity).getCientistas(); } }; public static final Attribute<java.util.List> equipamentos = new Attribute<java.util.List> (){ @Override public java.util.List valueOfAttribute(Entity entity) { return ((Pesquisa)entity).getEquipamentos(); } }; public $Pesquisa(){ this.uuid = Uuid.generator(); } public $Pesquisa(Pesquisa obj){ this.setNome(obj.getNome()); this.setResultado(obj.isResultado()); this.setAvatar(obj.getAvatar()); this.setCientistas(obj.getCientistas()); this.setEquipamentos(obj.getEquipamentos()); this.uuid = Uuid.generator(); } public $Pesquisa(Pesquisa obj, Uuid uuid){ this.setNome(obj.getNome()); this.setResultado(obj.isResultado()); this.setAvatar(obj.getAvatar()); this.setCientistas(obj.getCientistas()); this.setEquipamentos(obj.getEquipamentos()); this.uuid = uuid; } public $Pesquisa($Pesquisa obj){ this.setNome(obj.getNome()); this.setResultado(obj.isResultado()); this.setAvatar(obj.getAvatar()); this.setCientistas(obj.getCientistas()); this.setEquipamentos(obj.getEquipamentos()); this.uuid = obj.getUuid();; } public $Pesquisa(Uuid uuid){ this.uuid = uuid; } protected Uuid uuidAvatar; @Override public Avatar getAvatar(){ Avatar superAvatar = super.getAvatar(); if (superAvatar == null && uuidAvatar != null) {superAvatar = $Avatar.entityStructure.find(uuidAvatar); this.setAvatar(superAvatar); }return superAvatar; }private void resetUuidAvatar() { if (this.getAvatar() != null) { if (this.getAvatar() instanceof Entity) { uuidAvatar = ((Entity) this.getAvatar()).getUuid(); } else { $Avatar entity = new $Avatar (this.getAvatar()); uuidAvatar = $Avatar.find(entity); if (uuidAvatar == null) { throw new TransientObjectException("Pesquisa", "avatar", "Avatar"); } } } }protected java.util.List<Uuid> uuidCientistas = new java.util.ArrayList<>(); @Override public java.util.List<org.obinject.sample.avatar.Cientista> getCientistas() {java.util.List<org.obinject.sample.avatar.Cientista> superCientistas = super.getCientistas(); if ((superCientistas.isEmpty()) && (!uuidCientistas.isEmpty())) {for (Uuid uuid : uuidCientistas) {superCientistas.add($Cientista.entityStructure.find(uuid)); } }return superCientistas; }private void resetUuidCientistas(){ uuidCientistas.clear(); if (this.getCientistas() != null) {for (Cientista obj : this.getCientistas()) { if (obj instanceof Entity) { uuidCientistas.add(((Entity) obj).getUuid()); } else { $Cientista entity = new $Cientista (obj); uuidCientistas.add($Cientista.find(entity)); if (uuidCientistas == null) { throw new TransientObjectException("Pesquisa", "cientistas", "Cientista"); } } } } }protected java.util.List<Uuid> uuidEquipamentos = new java.util.ArrayList<>(); @Override public java.util.List<org.obinject.sample.avatar.Equipamento> getEquipamentos() {java.util.List<org.obinject.sample.avatar.Equipamento> superEquipamentos = super.getEquipamentos(); if ((superEquipamentos.isEmpty()) && (!uuidEquipamentos.isEmpty())) {for (Uuid uuid : uuidEquipamentos) {superEquipamentos.add($Equipamento.entityStructure.find(uuid)); } }return superEquipamentos; }private void resetUuidEquipamentos(){ uuidEquipamentos.clear(); if (this.getEquipamentos() != null) {for (Equipamento obj : this.getEquipamentos()) { if (obj instanceof Entity) { uuidEquipamentos.add(((Entity) obj).getUuid()); } else { $Equipamento entity = new $Equipamento (obj); uuidEquipamentos.add($Equipamento.find(entity)); if (uuidEquipamentos == null) { throw new TransientObjectException("Pesquisa", "equipamentos", "Equipamento"); } } } } }@Override public boolean isEqual($Pesquisa obj) {  return (((this.getNome() == null) && (obj.getNome() == null)) || ((this.getNome() != null) && (obj.getNome() != null) && (this.getNome().equals( obj.getNome())))) && (this.isResultado() == obj.isResultado()); } @Override public Uuid getUuid() { return this.uuid; } @Override public EntityStructure<$Pesquisa> getEntityStructure() { return entityStructure; } @Override public boolean inject(){ Uuid uuidInject = $Pesquisa.find(this); if(uuidInject == null){ resetUuidAvatar(); resetUuidCientistas(); resetUuidEquipamentos(); $Pesquisa.entityStructure.add(this); UniqueOnePesquisa.uniqueOnePesquisaStructure.add(new UniqueOnePesquisa(this, this.getUuid())); return true; }else{ this.uuid = uuidInject; return false; } } @Override public boolean reject(){ Uuid uuidReject = $Pesquisa.find(this); if(uuidReject != null){ UniqueOnePesquisa.uniqueOnePesquisaStructure.remove(new UniqueOnePesquisa(this, this.getUuid())); $Pesquisa.entityStructure.remove(this); return true; }else{ return false; } } @Override public boolean modify(){ Uuid uuidOld = $Pesquisa.find(this); $Pesquisa entityOld = $Pesquisa.entityStructure.find(uuidOld); if(entityOld != null){ resetUuidAvatar(); resetUuidCientistas(); resetUuidEquipamentos(); UniqueOnePesquisa uniqueOnePesquisaOld = new UniqueOnePesquisa(entityOld, entityOld.getUuid()); UniqueOnePesquisa uniqueOnePesquisaNew = new UniqueOnePesquisa(this, entityOld.getUuid()); if(uniqueOnePesquisaOld.hasSameKey(uniqueOnePesquisaNew)){ $Pesquisa.uniqueOnePesquisaStructure.remove(uniqueOnePesquisaOld); $Pesquisa.uniqueOnePesquisaStructure.add(uniqueOnePesquisaNew); } this.uuid = uuidOld; $Pesquisa.entityStructure.remove(entityOld); $Pesquisa.entityStructure.add(this); return true; }else{ return false; } } public static Uuid find($Pesquisa entity) { UniqueOnePesquisa unique = new UniqueOnePesquisa (entity, entity.getUuid()); return $Pesquisa.uniqueOnePesquisaStructure.find(unique); } @Override public boolean pullEntity(byte[] array, int position) { PullPage pull = new PullPage(array, position); Uuid storedClass = pull.pullUuid(); if ($Pesquisa.classId.equals(storedClass) == true){ uuid = pull.pullUuid(); this.setNome(pull.pullString()); this.setResultado(pull.pullBoolean()); int totalAvatar = pull.pullInteger(); if(totalAvatar > 0){this.uuidAvatar = pull.pullUuid(); }int totalCientistas = pull.pullInteger(); for (int i = 0; i < totalCientistas; i++) {this.uuidCientistas.add(pull.pullUuid()); }int totalEquipamentos = pull.pullInteger(); for (int i = 0; i < totalEquipamentos; i++) {this.uuidEquipamentos.add(pull.pullUuid()); }return true; } return false; } @Override public void pushEntity(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushUuid($Pesquisa.classId); push.pushUuid(uuid); push.pushString(this.getNome()); push.pushBoolean(this.isResultado()); if (this.uuidAvatar != null) { push.pushInteger(1); push.pushUuid(this.uuidAvatar); } else {push.pushInteger(0); } push.pushInteger(this.uuidCientistas.size()); for (Uuid uuidPush : this.uuidCientistas) {push.pushUuid(uuidPush); }push.pushInteger(this.uuidEquipamentos.size()); for (Uuid uuidPush : this.uuidEquipamentos) {push.pushUuid(uuidPush); }} @Override public int sizeOfEntity() { return Page.sizeOfUuid + Page.sizeOfUuid  + Page.sizeOfString(this.getNome()) + Page.sizeOfBoolean + Page.sizeOfEntity(this.uuidAvatar) + Page.sizeOfEntityCollection(this.uuidCientistas) + Page.sizeOfEntityCollection(this.uuidEquipamentos); } static{ nome.getSchemas().add( new Schema<$Pesquisa, UniqueOnePesquisa, java.lang.String>() { @Override public $Pesquisa newEntity(java.lang.String value) { $Pesquisa obj = new $Pesquisa(); obj.setNome(value); return obj; } @Override public UniqueOnePesquisa newKey(java.lang.String value) { UniqueOnePesquisa obj = new UniqueOnePesquisa(); obj.setNome(value); return obj; } @Override public EntityStructure<$Pesquisa> getEntityStructure() { return $Pesquisa.entityStructure; } @Override public KeyStructure<UniqueOnePesquisa> getKeyStructure() { return UniqueOnePesquisa.uniqueOnePesquisaStructure; } } ); } } 