package org.obinject.sample.avatar; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.device.File; import org.obinject.exception.TransientObjectException; import org.obinject.meta.Entity; import org.obinject.meta.Uuid; import org.obinject.queries.Attribute; import org.obinject.queries.Schema; import org.obinject.storage.BTree; import org.obinject.storage.BTreeEntity; import org.obinject.storage.EntityStructure; import org.obinject.storage.KeyStructure; import org.obinject.storage.Structure; public class $Colonia extends Colonia implements Entity<$Colonia> { protected Uuid uuid; private static Uuid classId; public static Uuid getClassId(){ if ($Colonia.classId == null) {$Colonia.classId = Uuid.fromString("AE271304-BA3B-D099-F79B-C686771C013A"); }return $Colonia.classId; } public static final BTreeEntity<$Colonia> entityStructure = new BTreeEntity<$Colonia>(new File("build/classes/org/obinject/sample/avatar/avatar.dbo", 4096)){}; public static final BTree<UniqueOneColonia> uniqueOneColoniaStructure = new BTree<UniqueOneColonia>(new File("build/classes/org/obinject/sample/avatar/avatar.dbo", 4096)){}; public static final Attribute<java.lang.String> nome = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Colonia)entity).getNome(); } }; public static final Attribute<org.obinject.sample.avatar.Empresa> empresa = new Attribute<org.obinject.sample.avatar.Empresa> (){ @Override public org.obinject.sample.avatar.Empresa valueOfAttribute(Entity entity) { return ((Colonia)entity).getEmpresa(); } }; public static final Attribute<java.lang.String> apelido = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Colonia)entity).getApelido(); } }; public static final Attribute<Boolean> pressurizadas = new Attribute<Boolean> (){ @Override public Boolean valueOfAttribute(Entity entity) { return ((Colonia)entity).isPressurizadas(); } }; public static final Attribute<java.util.List> containers = new Attribute<java.util.List> (){ @Override public java.util.List valueOfAttribute(Entity entity) { return ((Colonia)entity).getContainers(); } }; public $Colonia(){ this.uuid = Uuid.generator(); } public $Colonia(Colonia obj){ this.setNome(obj.getNome()); this.setEmpresa(obj.getEmpresa()); this.setApelido(obj.getApelido()); this.setPressurizadas(obj.isPressurizadas()); this.setContainers(obj.getContainers()); this.uuid = Uuid.generator(); } public $Colonia(Colonia obj, Uuid uuid){ this.setNome(obj.getNome()); this.setEmpresa(obj.getEmpresa()); this.setApelido(obj.getApelido()); this.setPressurizadas(obj.isPressurizadas()); this.setContainers(obj.getContainers()); this.uuid = uuid; } public $Colonia($Colonia obj){ this.setNome(obj.getNome()); this.setEmpresa(obj.getEmpresa()); this.setApelido(obj.getApelido()); this.setPressurizadas(obj.isPressurizadas()); this.setContainers(obj.getContainers()); this.uuid = obj.getUuid();; } public $Colonia(Uuid uuid){ this.uuid = uuid; } protected Uuid uuidEmpresa; @Override public Empresa getEmpresa(){ Empresa superEmpresa = super.getEmpresa(); if (superEmpresa == null && uuidEmpresa != null) {superEmpresa = $Empresa.entityStructure.find(uuidEmpresa); this.setEmpresa(superEmpresa); }return superEmpresa; }private void resetUuidEmpresa() { if (this.getEmpresa() != null) { if (this.getEmpresa() instanceof Entity) { uuidEmpresa = ((Entity) this.getEmpresa()).getUuid(); } else { $Empresa entity = new $Empresa (this.getEmpresa()); uuidEmpresa = $Empresa.find(entity); if (uuidEmpresa == null) { throw new TransientObjectException("Colonia", "empresa", "Empresa"); } } } }protected java.util.List<Uuid> uuidContainers = new java.util.ArrayList<>(); @Override public java.util.List<org.obinject.sample.avatar.Container> getContainers() {java.util.List<org.obinject.sample.avatar.Container> superContainers = super.getContainers(); if ((superContainers.isEmpty()) && (!uuidContainers.isEmpty())) {for (Uuid uuid : uuidContainers) {superContainers.add($Container.entityStructure.find(uuid)); } }return superContainers; }private void resetUuidContainers(){ uuidContainers.clear(); if (this.getContainers() != null) {for (Container obj : this.getContainers()) { if (obj instanceof Entity) { uuidContainers.add(((Entity) obj).getUuid()); } else { $Container entity = new $Container (obj); uuidContainers.add($Container.find(entity)); if (uuidContainers == null) { throw new TransientObjectException("Colonia", "containers", "Container"); } } } } }@Override public boolean isEqual($Colonia obj) {  return (((this.getNome() == null) && (obj.getNome() == null)) || ((this.getNome() != null) && (obj.getNome() != null) && (this.getNome().equals( obj.getNome())))) && (((this.getApelido() == null) && (obj.getApelido() == null)) || ((this.getApelido() != null) && (obj.getApelido() != null) && (this.getApelido().equals( obj.getApelido())))) && (this.isPressurizadas() == obj.isPressurizadas()); } @Override public Uuid getUuid() { return this.uuid; } @Override public EntityStructure<$Colonia> getEntityStructure() { return entityStructure; } @Override public boolean inject(){ Uuid uuidInject = $Colonia.find(this); if(uuidInject == null){ resetUuidEmpresa(); resetUuidContainers(); $Colonia.entityStructure.add(this); UniqueOneColonia.uniqueOneColoniaStructure.add(new UniqueOneColonia(this, this.getUuid())); return true; }else{ this.uuid = uuidInject; return false; } } @Override public boolean reject(){ Uuid uuidReject = $Colonia.find(this); if(uuidReject != null){ UniqueOneColonia.uniqueOneColoniaStructure.remove(new UniqueOneColonia(this, this.getUuid())); $Colonia.entityStructure.remove(this); return true; }else{ return false; } } @Override public boolean modify(){ Uuid uuidOld = $Colonia.find(this); $Colonia entityOld = $Colonia.entityStructure.find(uuidOld); if(entityOld != null){ resetUuidEmpresa(); resetUuidContainers(); UniqueOneColonia uniqueOneColoniaOld = new UniqueOneColonia(entityOld, entityOld.getUuid()); UniqueOneColonia uniqueOneColoniaNew = new UniqueOneColonia(this, entityOld.getUuid()); if(uniqueOneColoniaOld.hasSameKey(uniqueOneColoniaNew)){ $Colonia.uniqueOneColoniaStructure.remove(uniqueOneColoniaOld); $Colonia.uniqueOneColoniaStructure.add(uniqueOneColoniaNew); } this.uuid = uuidOld; $Colonia.entityStructure.remove(entityOld); $Colonia.entityStructure.add(this); return true; }else{ return false; } } public static Uuid find($Colonia entity) { UniqueOneColonia unique = new UniqueOneColonia (entity, entity.getUuid()); return $Colonia.uniqueOneColoniaStructure.find(unique); } @Override public boolean pullEntity(byte[] array, int position) { PullPage pull = new PullPage(array, position); Uuid storedClass = pull.pullUuid(); if ($Colonia.classId.equals(storedClass) == true){ uuid = pull.pullUuid(); this.setNome(pull.pullString()); int totalEmpresa = pull.pullInteger(); if(totalEmpresa > 0){this.uuidEmpresa = pull.pullUuid(); }this.setApelido(pull.pullString()); this.setPressurizadas(pull.pullBoolean()); int totalContainers = pull.pullInteger(); for (int i = 0; i < totalContainers; i++) {this.uuidContainers.add(pull.pullUuid()); }return true; } return false; } @Override public void pushEntity(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushUuid($Colonia.classId); push.pushUuid(uuid); push.pushString(this.getNome()); if (this.uuidEmpresa != null) { push.pushInteger(1); push.pushUuid(this.uuidEmpresa); } else {push.pushInteger(0); } push.pushString(this.getApelido()); push.pushBoolean(this.isPressurizadas()); push.pushInteger(this.uuidContainers.size()); for (Uuid uuidPush : this.uuidContainers) {push.pushUuid(uuidPush); }} @Override public int sizeOfEntity() { return Page.sizeOfUuid + Page.sizeOfUuid  + Page.sizeOfString(this.getNome()) + Page.sizeOfEntity(this.uuidEmpresa) + Page.sizeOfString(this.getApelido()) + Page.sizeOfBoolean + Page.sizeOfEntityCollection(this.uuidContainers); } static{ nome.getSchemas().add( new Schema<$Colonia, UniqueOneColonia, java.lang.String>() { @Override public $Colonia newEntity(java.lang.String value) { $Colonia obj = new $Colonia(); obj.setNome(value); return obj; } @Override public UniqueOneColonia newKey(java.lang.String value) { UniqueOneColonia obj = new UniqueOneColonia(); obj.setNome(value); return obj; } @Override public EntityStructure<$Colonia> getEntityStructure() { return $Colonia.entityStructure; } @Override public KeyStructure<UniqueOneColonia> getKeyStructure() { return UniqueOneColonia.uniqueOneColoniaStructure; } } ); empresa.getSchemas().add( new Schema<$Colonia, UniqueOneColonia, org.obinject.sample.avatar.Empresa>() { @Override public $Colonia newEntity(org.obinject.sample.avatar.Empresa value) { $Colonia obj = new $Colonia(); obj.setEmpresa(value); return obj; } @Override public UniqueOneColonia newKey(org.obinject.sample.avatar.Empresa value) { UniqueOneColonia obj = new UniqueOneColonia(); obj.setEmpresa(value); return obj; } @Override public EntityStructure<$Colonia> getEntityStructure() { return $Colonia.entityStructure; } @Override public KeyStructure<UniqueOneColonia> getKeyStructure() { return UniqueOneColonia.uniqueOneColoniaStructure; } } ); } } 