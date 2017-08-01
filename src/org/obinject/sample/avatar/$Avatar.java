package org.obinject.sample.avatar; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.device.File; import org.obinject.exception.TransientObjectException; import org.obinject.meta.Entity; import org.obinject.meta.Uuid; import org.obinject.queries.Attribute; import org.obinject.queries.Schema; import org.obinject.storage.BTree; import org.obinject.storage.BTreeEntity; import org.obinject.storage.EntityStructure; import org.obinject.storage.KeyStructure; import org.obinject.storage.Structure; public class $Avatar extends Avatar implements Entity<$Avatar> { protected Uuid uuid; private static Uuid classId; public static Uuid getClassId(){ if ($Avatar.classId == null) {$Avatar.classId = Uuid.fromString("EEE98E2A-76CB-2E82-03F2-A958DEFFB219"); }return $Avatar.classId; } public static final BTreeEntity<$Avatar> entityStructure = new BTreeEntity<$Avatar>(new File("build/classes/org/obinject/sample/avatar/avatar.dbo", 4096)){}; public static final BTree<UniqueOneAvatar> uniqueOneAvatarStructure = new BTree<UniqueOneAvatar>(new File("build/classes/org/obinject/sample/avatar/avatar.dbo", 4096)){}; public static final Attribute<Integer> id = new Attribute<Integer> (){ @Override public Integer valueOfAttribute(Entity entity) { return ((Avatar)entity).getId(); } }; public static final Attribute<Boolean> control = new Attribute<Boolean> (){ @Override public Boolean valueOfAttribute(Entity entity) { return ((Avatar)entity).isControl(); } }; public static final Attribute<org.obinject.sample.avatar.Humano> humano = new Attribute<org.obinject.sample.avatar.Humano> (){ @Override public org.obinject.sample.avatar.Humano valueOfAttribute(Entity entity) { return ((Avatar)entity).getHumano(); } }; public static final Attribute<org.obinject.sample.avatar.Navi> navi = new Attribute<org.obinject.sample.avatar.Navi> (){ @Override public org.obinject.sample.avatar.Navi valueOfAttribute(Entity entity) { return ((Avatar)entity).getNavi(); } }; public static final Attribute<org.obinject.sample.avatar.Pesquisa> pesquisa = new Attribute<org.obinject.sample.avatar.Pesquisa> (){ @Override public org.obinject.sample.avatar.Pesquisa valueOfAttribute(Entity entity) { return ((Avatar)entity).getPesquisa(); } }; public $Avatar(){ this.uuid = Uuid.generator(); } public $Avatar(Avatar obj){ this.setId(obj.getId()); this.setControl(obj.isControl()); this.setHumano(obj.getHumano()); this.setNavi(obj.getNavi()); this.setPesquisa(obj.getPesquisa()); this.uuid = Uuid.generator(); } public $Avatar(Avatar obj, Uuid uuid){ this.setId(obj.getId()); this.setControl(obj.isControl()); this.setHumano(obj.getHumano()); this.setNavi(obj.getNavi()); this.setPesquisa(obj.getPesquisa()); this.uuid = uuid; } public $Avatar($Avatar obj){ this.setId(obj.getId()); this.setControl(obj.isControl()); this.setHumano(obj.getHumano()); this.setNavi(obj.getNavi()); this.setPesquisa(obj.getPesquisa()); this.uuid = obj.getUuid();; } public $Avatar(Uuid uuid){ this.uuid = uuid; } protected Uuid uuidHumano; @Override public Humano getHumano(){ Humano superHumano = super.getHumano(); if (superHumano == null && uuidHumano != null) {superHumano = $Humano.entityStructure.find(uuidHumano); this.setHumano(superHumano); }return superHumano; }private void resetUuidHumano() { if (this.getHumano() != null) { if (this.getHumano() instanceof Entity) { uuidHumano = ((Entity) this.getHumano()).getUuid(); } else { $Humano entity = new $Humano (this.getHumano()); uuidHumano = $Humano.find(entity); if (uuidHumano == null) { throw new TransientObjectException("Avatar", "humano", "Humano"); } } } }protected Uuid uuidNavi; @Override public Navi getNavi(){ Navi superNavi = super.getNavi(); if (superNavi == null && uuidNavi != null) {superNavi = $Navi.entityStructure.find(uuidNavi); this.setNavi(superNavi); }return superNavi; }private void resetUuidNavi() { if (this.getNavi() != null) { if (this.getNavi() instanceof Entity) { uuidNavi = ((Entity) this.getNavi()).getUuid(); } else { $Navi entity = new $Navi (this.getNavi()); uuidNavi = $Navi.find(entity); if (uuidNavi == null) { throw new TransientObjectException("Avatar", "navi", "Navi"); } } } }protected Uuid uuidPesquisa; @Override public Pesquisa getPesquisa(){ Pesquisa superPesquisa = super.getPesquisa(); if (superPesquisa == null && uuidPesquisa != null) {superPesquisa = $Pesquisa.entityStructure.find(uuidPesquisa); this.setPesquisa(superPesquisa); }return superPesquisa; }private void resetUuidPesquisa() { if (this.getPesquisa() != null) { if (this.getPesquisa() instanceof Entity) { uuidPesquisa = ((Entity) this.getPesquisa()).getUuid(); } else { $Pesquisa entity = new $Pesquisa (this.getPesquisa()); uuidPesquisa = $Pesquisa.find(entity); if (uuidPesquisa == null) { throw new TransientObjectException("Avatar", "pesquisa", "Pesquisa"); } } } }@Override public boolean isEqual($Avatar obj) { return (this.getId() == obj.getId()) && (this.isControl() == obj.isControl()); } @Override public Uuid getUuid() { return this.uuid; } @Override public EntityStructure<$Avatar> getEntityStructure() { return entityStructure; } @Override public boolean inject(){ Uuid uuidInject = $Avatar.find(this); if(uuidInject == null){ resetUuidHumano(); resetUuidNavi(); resetUuidPesquisa(); $Avatar.entityStructure.add(this); UniqueOneAvatar.uniqueOneAvatarStructure.add(new UniqueOneAvatar(this, this.getUuid())); return true; }else{ this.uuid = uuidInject; return false; } } @Override public boolean reject(){ Uuid uuidReject = $Avatar.find(this); if(uuidReject != null){ UniqueOneAvatar.uniqueOneAvatarStructure.remove(new UniqueOneAvatar(this, this.getUuid())); $Avatar.entityStructure.remove(this); return true; }else{ return false; } } @Override public boolean modify(){ Uuid uuidOld = $Avatar.find(this); $Avatar entityOld = $Avatar.entityStructure.find(uuidOld); if(entityOld != null){ resetUuidHumano(); resetUuidNavi(); resetUuidPesquisa(); UniqueOneAvatar uniqueOneAvatarOld = new UniqueOneAvatar(entityOld, entityOld.getUuid()); UniqueOneAvatar uniqueOneAvatarNew = new UniqueOneAvatar(this, entityOld.getUuid()); if(uniqueOneAvatarOld.hasSameKey(uniqueOneAvatarNew)){ $Avatar.uniqueOneAvatarStructure.remove(uniqueOneAvatarOld); $Avatar.uniqueOneAvatarStructure.add(uniqueOneAvatarNew); } this.uuid = uuidOld; $Avatar.entityStructure.remove(entityOld); $Avatar.entityStructure.add(this); return true; }else{ return false; } } public static Uuid find($Avatar entity) { UniqueOneAvatar unique = new UniqueOneAvatar (entity, entity.getUuid()); return $Avatar.uniqueOneAvatarStructure.find(unique); } @Override public boolean pullEntity(byte[] array, int position) { PullPage pull = new PullPage(array, position); Uuid storedClass = pull.pullUuid(); if ($Avatar.classId.equals(storedClass) == true){ uuid = pull.pullUuid(); this.setId(pull.pullInteger()); this.setControl(pull.pullBoolean()); int totalHumano = pull.pullInteger(); if(totalHumano > 0){this.uuidHumano = pull.pullUuid(); }int totalNavi = pull.pullInteger(); if(totalNavi > 0){this.uuidNavi = pull.pullUuid(); }int totalPesquisa = pull.pullInteger(); if(totalPesquisa > 0){this.uuidPesquisa = pull.pullUuid(); }return true; } return false; } @Override public void pushEntity(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushUuid($Avatar.classId); push.pushUuid(uuid); push.pushInteger(this.getId()); push.pushBoolean(this.isControl()); if (this.uuidHumano != null) { push.pushInteger(1); push.pushUuid(this.uuidHumano); } else {push.pushInteger(0); } if (this.uuidNavi != null) { push.pushInteger(1); push.pushUuid(this.uuidNavi); } else {push.pushInteger(0); } if (this.uuidPesquisa != null) { push.pushInteger(1); push.pushUuid(this.uuidPesquisa); } else {push.pushInteger(0); } } @Override public int sizeOfEntity() { return Page.sizeOfUuid + Page.sizeOfUuid  + Page.sizeOfInteger + Page.sizeOfBoolean + Page.sizeOfEntity(this.uuidHumano) + Page.sizeOfEntity(this.uuidNavi) + Page.sizeOfEntity(this.uuidPesquisa); } static{ id.getSchemas().add( new Schema<$Avatar, UniqueOneAvatar, Integer>() { @Override public $Avatar newEntity(Integer value) { $Avatar obj = new $Avatar(); obj.setId(value); return obj; } @Override public UniqueOneAvatar newKey(Integer value) { UniqueOneAvatar obj = new UniqueOneAvatar(); obj.setId(value); return obj; } @Override public EntityStructure<$Avatar> getEntityStructure() { return $Avatar.entityStructure; } @Override public KeyStructure<UniqueOneAvatar> getKeyStructure() { return UniqueOneAvatar.uniqueOneAvatarStructure; } } ); } } 