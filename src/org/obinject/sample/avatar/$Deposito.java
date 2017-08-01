package org.obinject.sample.avatar; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.device.File; import org.obinject.exception.TransientObjectException; import org.obinject.meta.Entity; import org.obinject.meta.Uuid; import org.obinject.queries.Attribute; import org.obinject.queries.Schema; import org.obinject.storage.BTree; import org.obinject.storage.BTreeEntity; import org.obinject.storage.EntityStructure; import org.obinject.storage.KeyStructure; import org.obinject.storage.Structure; public class $Deposito extends Deposito implements Entity<$Deposito> { protected Uuid uuid; private static Uuid classId; public static Uuid getClassId(){ if ($Deposito.classId == null) {$Deposito.classId = Uuid.fromString("E2624D23-6E0B-5BA5-7B4D-3D30258CA96C"); }return $Deposito.classId; } public static final BTreeEntity<$Deposito> entityStructure = new BTreeEntity<$Deposito>(new File("build/classes/org/obinject/sample/avatar/avatar.dbo", 4096)){}; public static final Attribute<java.lang.String> nome = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Deposito)entity).getNome(); } }; public static final Attribute<org.obinject.sample.avatar.Laboratorio> cont_lab = new Attribute<org.obinject.sample.avatar.Laboratorio> (){ @Override public org.obinject.sample.avatar.Laboratorio valueOfAttribute(Entity entity) { return ((Deposito)entity).getCont_lab(); } }; public static final Attribute<org.obinject.sample.avatar.Residencia> cont_res = new Attribute<org.obinject.sample.avatar.Residencia> (){ @Override public org.obinject.sample.avatar.Residencia valueOfAttribute(Entity entity) { return ((Deposito)entity).getCont_res(); } }; public static final Attribute<org.obinject.sample.avatar.Deposito> cont_dep = new Attribute<org.obinject.sample.avatar.Deposito> (){ @Override public org.obinject.sample.avatar.Deposito valueOfAttribute(Entity entity) { return ((Deposito)entity).getCont_dep(); } }; public static final Attribute<Character> sigla = new Attribute<Character> (){ @Override public Character valueOfAttribute(Entity entity) { return ((Deposito)entity).getSigla(); } }; public static final Attribute<java.lang.String> conteudo = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Deposito)entity).getConteudo(); } }; public $Deposito(){ this.uuid = Uuid.generator(); } public $Deposito(Deposito obj){ this.setNome(obj.getNome()); this.setCont_lab(obj.getCont_lab()); this.setCont_res(obj.getCont_res()); this.setCont_dep(obj.getCont_dep()); this.setSigla(obj.getSigla()); this.setConteudo(obj.getConteudo()); this.uuid = Uuid.generator(); } public $Deposito(Deposito obj, Uuid uuid){ this.setNome(obj.getNome()); this.setCont_lab(obj.getCont_lab()); this.setCont_res(obj.getCont_res()); this.setCont_dep(obj.getCont_dep()); this.setSigla(obj.getSigla()); this.setConteudo(obj.getConteudo()); this.uuid = uuid; } public $Deposito($Deposito obj){ this.setNome(obj.getNome()); this.setCont_lab(obj.getCont_lab()); this.setCont_res(obj.getCont_res()); this.setCont_dep(obj.getCont_dep()); this.setSigla(obj.getSigla()); this.setConteudo(obj.getConteudo()); this.uuid = obj.getUuid();; } public $Deposito(Uuid uuid){ this.uuid = uuid; } protected Uuid uuidCont_lab; @Override public Laboratorio getCont_lab(){ Laboratorio superCont_lab = super.getCont_lab(); if (superCont_lab == null && uuidCont_lab != null) {superCont_lab = $Laboratorio.entityStructure.find(uuidCont_lab); this.setCont_lab(superCont_lab); }return superCont_lab; }private void resetUuidCont_lab() { if (this.getCont_lab() != null) { if (this.getCont_lab() instanceof Entity) { uuidCont_lab = ((Entity) this.getCont_lab()).getUuid(); } else { $Laboratorio entity = new $Laboratorio (this.getCont_lab()); uuidCont_lab = $Laboratorio.find(entity); if (uuidCont_lab == null) { throw new TransientObjectException("Deposito", "cont_lab", "Laboratorio"); } } } }protected Uuid uuidCont_res; @Override public Residencia getCont_res(){ Residencia superCont_res = super.getCont_res(); if (superCont_res == null && uuidCont_res != null) {superCont_res = $Residencia.entityStructure.find(uuidCont_res); this.setCont_res(superCont_res); }return superCont_res; }private void resetUuidCont_res() { if (this.getCont_res() != null) { if (this.getCont_res() instanceof Entity) { uuidCont_res = ((Entity) this.getCont_res()).getUuid(); } else { $Residencia entity = new $Residencia (this.getCont_res()); uuidCont_res = $Residencia.find(entity); if (uuidCont_res == null) { throw new TransientObjectException("Deposito", "cont_res", "Residencia"); } } } }protected Uuid uuidCont_dep; @Override public Deposito getCont_dep(){ Deposito superCont_dep = super.getCont_dep(); if (superCont_dep == null && uuidCont_dep != null) {superCont_dep = $Deposito.entityStructure.find(uuidCont_dep); this.setCont_dep(superCont_dep); }return superCont_dep; }private void resetUuidCont_dep() { if (this.getCont_dep() != null) { if (this.getCont_dep() instanceof Entity) { uuidCont_dep = ((Entity) this.getCont_dep()).getUuid(); } else { $Deposito entity = new $Deposito (this.getCont_dep()); uuidCont_dep = $Deposito.find(entity); if (uuidCont_dep == null) { throw new TransientObjectException("Deposito", "cont_dep", "Deposito"); } } } }@Override public boolean isEqual($Deposito obj) {  return (((this.getNome() == null) && (obj.getNome() == null)) || ((this.getNome() != null) && (obj.getNome() != null) && (this.getNome().equals( obj.getNome())))) && (this.getSigla() == obj.getSigla()) && (((this.getConteudo() == null) && (obj.getConteudo() == null)) || ((this.getConteudo() != null) && (obj.getConteudo() != null) && (this.getConteudo().equals( obj.getConteudo())))); } @Override public Uuid getUuid() { return this.uuid; } @Override public EntityStructure<$Deposito> getEntityStructure() { return entityStructure; } @Override public boolean inject(){ Uuid uuidInject = $Deposito.find(this); if(uuidInject == null){ resetUuidCont_lab(); resetUuidCont_res(); resetUuidCont_dep(); $Deposito.entityStructure.add(this); UniqueOneContainer.uniqueOneContainerStructure.add(new UniqueOneContainer(this, this.getUuid())); return true; }else{ this.uuid = uuidInject; return false; } } @Override public boolean reject(){ Uuid uuidReject = $Deposito.find(this); if(uuidReject != null){ UniqueOneContainer.uniqueOneContainerStructure.remove(new UniqueOneContainer(this, this.getUuid())); $Deposito.entityStructure.remove(this); return true; }else{ return false; } } @Override public boolean modify(){ Uuid uuidOld = $Deposito.find(this); $Deposito entityOld = $Deposito.entityStructure.find(uuidOld); if(entityOld != null){ resetUuidCont_lab(); resetUuidCont_res(); resetUuidCont_dep(); UniqueOneContainer uniqueOneContainerOld = new UniqueOneContainer(entityOld, entityOld.getUuid()); UniqueOneContainer uniqueOneContainerNew = new UniqueOneContainer(this, entityOld.getUuid()); if(uniqueOneContainerOld.hasSameKey(uniqueOneContainerNew)){ $Container.uniqueOneContainerStructure.remove(uniqueOneContainerOld); $Container.uniqueOneContainerStructure.add(uniqueOneContainerNew); } this.uuid = uuidOld; $Deposito.entityStructure.remove(entityOld); $Deposito.entityStructure.add(this); return true; }else{ return false; } } public static Uuid find($Deposito entity) { UniqueOneContainer unique = new UniqueOneContainer (entity, entity.getUuid()); return $Container.uniqueOneContainerStructure.find(unique); } @Override public boolean pullEntity(byte[] array, int position) { PullPage pull = new PullPage(array, position); Uuid storedClass = pull.pullUuid(); if ($Deposito.classId.equals(storedClass) == true){ uuid = pull.pullUuid(); this.setNome(pull.pullString()); int totalCont_lab = pull.pullInteger(); if(totalCont_lab > 0){this.uuidCont_lab = pull.pullUuid(); }int totalCont_res = pull.pullInteger(); if(totalCont_res > 0){this.uuidCont_res = pull.pullUuid(); }int totalCont_dep = pull.pullInteger(); if(totalCont_dep > 0){this.uuidCont_dep = pull.pullUuid(); }this.setSigla(pull.pullCharacter()); this.setConteudo(pull.pullString()); return true; } return false; } @Override public void pushEntity(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushUuid($Deposito.classId); push.pushUuid(uuid); push.pushString(this.getNome()); if (this.uuidCont_lab != null) { push.pushInteger(1); push.pushUuid(this.uuidCont_lab); } else {push.pushInteger(0); } if (this.uuidCont_res != null) { push.pushInteger(1); push.pushUuid(this.uuidCont_res); } else {push.pushInteger(0); } if (this.uuidCont_dep != null) { push.pushInteger(1); push.pushUuid(this.uuidCont_dep); } else {push.pushInteger(0); } push.pushCharacter(this.getSigla()); push.pushString(this.getConteudo()); } @Override public int sizeOfEntity() { return Page.sizeOfUuid + Page.sizeOfUuid  + Page.sizeOfString(this.getNome()) + Page.sizeOfEntity(this.uuidCont_lab) + Page.sizeOfEntity(this.uuidCont_res) + Page.sizeOfEntity(this.uuidCont_dep) + Page.sizeOfCharacter + Page.sizeOfString(this.getConteudo()); } static{ nome.getSchemas().add( new Schema<$Deposito, UniqueOneContainer, java.lang.String>() { @Override public $Deposito newEntity(java.lang.String value) { $Deposito obj = new $Deposito(); obj.setNome(value); return obj; } @Override public UniqueOneContainer newKey(java.lang.String value) { UniqueOneContainer obj = new UniqueOneContainer(); obj.setNome(value); return obj; } @Override public EntityStructure<$Deposito> getEntityStructure() { return $Deposito.entityStructure; } @Override public KeyStructure<UniqueOneContainer> getKeyStructure() { return UniqueOneContainer.uniqueOneContainerStructure; } } ); } } 