package org.obinject.sample.avatar; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.device.File; import org.obinject.exception.TransientObjectException; import org.obinject.meta.Entity; import org.obinject.meta.Uuid; import org.obinject.queries.Attribute; import org.obinject.queries.Schema; import org.obinject.storage.BTree; import org.obinject.storage.BTreeEntity; import org.obinject.storage.EntityStructure; import org.obinject.storage.KeyStructure; import org.obinject.storage.Structure; public class $Minerador extends Minerador implements Entity<$Minerador> { protected Uuid uuid; private static Uuid classId; public static Uuid getClassId(){ if ($Minerador.classId == null) {$Minerador.classId = Uuid.fromString("B7FDE51E-A34C-0A97-3AB5-D57A5CFB736D"); }return $Minerador.classId; } public static final BTreeEntity<$Minerador> entityStructure = new BTreeEntity<$Minerador>(new File("build/classes/org/obinject/sample/avatar/avatar.dbo", 4096)){}; public static final Attribute<java.lang.String> cpf = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Minerador)entity).getCpf(); } }; public static final Attribute<java.lang.String> nome = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Minerador)entity).getNome(); } }; public static final Attribute<Boolean> morte = new Attribute<Boolean> (){ @Override public Boolean valueOfAttribute(Entity entity) { return ((Minerador)entity).isMorte(); } }; public static final Attribute<org.obinject.sample.avatar.Militar> hum_mil = new Attribute<org.obinject.sample.avatar.Militar> (){ @Override public org.obinject.sample.avatar.Militar valueOfAttribute(Entity entity) { return ((Minerador)entity).getHum_mil(); } }; public static final Attribute<org.obinject.sample.avatar.Minerador> hum_min = new Attribute<org.obinject.sample.avatar.Minerador> (){ @Override public org.obinject.sample.avatar.Minerador valueOfAttribute(Entity entity) { return ((Minerador)entity).getHum_min(); } }; public static final Attribute<org.obinject.sample.avatar.Cientista> hum_cien = new Attribute<org.obinject.sample.avatar.Cientista> (){ @Override public org.obinject.sample.avatar.Cientista valueOfAttribute(Entity entity) { return ((Minerador)entity).getHum_cien(); } }; public static final Attribute<java.lang.String> funcao = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Minerador)entity).getFuncao(); } }; public $Minerador(){ this.uuid = Uuid.generator(); } public $Minerador(Minerador obj){ this.setCpf(obj.getCpf()); this.setNome(obj.getNome()); this.setMorte(obj.isMorte()); this.setHum_mil(obj.getHum_mil()); this.setHum_min(obj.getHum_min()); this.setHum_cien(obj.getHum_cien()); this.setFuncao(obj.getFuncao()); this.uuid = Uuid.generator(); } public $Minerador(Minerador obj, Uuid uuid){ this.setCpf(obj.getCpf()); this.setNome(obj.getNome()); this.setMorte(obj.isMorte()); this.setHum_mil(obj.getHum_mil()); this.setHum_min(obj.getHum_min()); this.setHum_cien(obj.getHum_cien()); this.setFuncao(obj.getFuncao()); this.uuid = uuid; } public $Minerador($Minerador obj){ this.setCpf(obj.getCpf()); this.setNome(obj.getNome()); this.setMorte(obj.isMorte()); this.setHum_mil(obj.getHum_mil()); this.setHum_min(obj.getHum_min()); this.setHum_cien(obj.getHum_cien()); this.setFuncao(obj.getFuncao()); this.uuid = obj.getUuid();; } public $Minerador(Uuid uuid){ this.uuid = uuid; } protected Uuid uuidHum_mil; @Override public Militar getHum_mil(){ Militar superHum_mil = super.getHum_mil(); if (superHum_mil == null && uuidHum_mil != null) {superHum_mil = $Militar.entityStructure.find(uuidHum_mil); this.setHum_mil(superHum_mil); }return superHum_mil; }private void resetUuidHum_mil() { if (this.getHum_mil() != null) { if (this.getHum_mil() instanceof Entity) { uuidHum_mil = ((Entity) this.getHum_mil()).getUuid(); } else { $Militar entity = new $Militar (this.getHum_mil()); uuidHum_mil = $Militar.find(entity); if (uuidHum_mil == null) { throw new TransientObjectException("Minerador", "hum_mil", "Militar"); } } } }protected Uuid uuidHum_min; @Override public Minerador getHum_min(){ Minerador superHum_min = super.getHum_min(); if (superHum_min == null && uuidHum_min != null) {superHum_min = $Minerador.entityStructure.find(uuidHum_min); this.setHum_min(superHum_min); }return superHum_min; }private void resetUuidHum_min() { if (this.getHum_min() != null) { if (this.getHum_min() instanceof Entity) { uuidHum_min = ((Entity) this.getHum_min()).getUuid(); } else { $Minerador entity = new $Minerador (this.getHum_min()); uuidHum_min = $Minerador.find(entity); if (uuidHum_min == null) { throw new TransientObjectException("Minerador", "hum_min", "Minerador"); } } } }protected Uuid uuidHum_cien; @Override public Cientista getHum_cien(){ Cientista superHum_cien = super.getHum_cien(); if (superHum_cien == null && uuidHum_cien != null) {superHum_cien = $Cientista.entityStructure.find(uuidHum_cien); this.setHum_cien(superHum_cien); }return superHum_cien; }private void resetUuidHum_cien() { if (this.getHum_cien() != null) { if (this.getHum_cien() instanceof Entity) { uuidHum_cien = ((Entity) this.getHum_cien()).getUuid(); } else { $Cientista entity = new $Cientista (this.getHum_cien()); uuidHum_cien = $Cientista.find(entity); if (uuidHum_cien == null) { throw new TransientObjectException("Minerador", "hum_cien", "Cientista"); } } } }@Override public boolean isEqual($Minerador obj) {  return (((this.getCpf() == null) && (obj.getCpf() == null)) || ((this.getCpf() != null) && (obj.getCpf() != null) && (this.getCpf().equals( obj.getCpf())))) && (((this.getNome() == null) && (obj.getNome() == null)) || ((this.getNome() != null) && (obj.getNome() != null) && (this.getNome().equals( obj.getNome())))) && (this.isMorte() == obj.isMorte()) && (((this.getFuncao() == null) && (obj.getFuncao() == null)) || ((this.getFuncao() != null) && (obj.getFuncao() != null) && (this.getFuncao().equals( obj.getFuncao())))); } @Override public Uuid getUuid() { return this.uuid; } @Override public EntityStructure<$Minerador> getEntityStructure() { return entityStructure; } @Override public boolean inject(){ Uuid uuidInject = $Minerador.find(this); if(uuidInject == null){ resetUuidHum_mil(); resetUuidHum_min(); resetUuidHum_cien(); $Minerador.entityStructure.add(this); UniqueOneHumano.uniqueOneHumanoStructure.add(new UniqueOneHumano(this, this.getUuid())); return true; }else{ this.uuid = uuidInject; return false; } } @Override public boolean reject(){ Uuid uuidReject = $Minerador.find(this); if(uuidReject != null){ UniqueOneHumano.uniqueOneHumanoStructure.remove(new UniqueOneHumano(this, this.getUuid())); $Minerador.entityStructure.remove(this); return true; }else{ return false; } } @Override public boolean modify(){ Uuid uuidOld = $Minerador.find(this); $Minerador entityOld = $Minerador.entityStructure.find(uuidOld); if(entityOld != null){ resetUuidHum_mil(); resetUuidHum_min(); resetUuidHum_cien(); UniqueOneHumano uniqueOneHumanoOld = new UniqueOneHumano(entityOld, entityOld.getUuid()); UniqueOneHumano uniqueOneHumanoNew = new UniqueOneHumano(this, entityOld.getUuid()); if(uniqueOneHumanoOld.hasSameKey(uniqueOneHumanoNew)){ $Humano.uniqueOneHumanoStructure.remove(uniqueOneHumanoOld); $Humano.uniqueOneHumanoStructure.add(uniqueOneHumanoNew); } this.uuid = uuidOld; $Minerador.entityStructure.remove(entityOld); $Minerador.entityStructure.add(this); return true; }else{ return false; } } public static Uuid find($Minerador entity) { UniqueOneHumano unique = new UniqueOneHumano (entity, entity.getUuid()); return $Humano.uniqueOneHumanoStructure.find(unique); } @Override public boolean pullEntity(byte[] array, int position) { PullPage pull = new PullPage(array, position); Uuid storedClass = pull.pullUuid(); if ($Minerador.classId.equals(storedClass) == true){ uuid = pull.pullUuid(); this.setCpf(pull.pullString()); this.setNome(pull.pullString()); this.setMorte(pull.pullBoolean()); int totalHum_mil = pull.pullInteger(); if(totalHum_mil > 0){this.uuidHum_mil = pull.pullUuid(); }int totalHum_min = pull.pullInteger(); if(totalHum_min > 0){this.uuidHum_min = pull.pullUuid(); }int totalHum_cien = pull.pullInteger(); if(totalHum_cien > 0){this.uuidHum_cien = pull.pullUuid(); }this.setFuncao(pull.pullString()); return true; } return false; } @Override public void pushEntity(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushUuid($Minerador.classId); push.pushUuid(uuid); push.pushString(this.getCpf()); push.pushString(this.getNome()); push.pushBoolean(this.isMorte()); if (this.uuidHum_mil != null) { push.pushInteger(1); push.pushUuid(this.uuidHum_mil); } else {push.pushInteger(0); } if (this.uuidHum_min != null) { push.pushInteger(1); push.pushUuid(this.uuidHum_min); } else {push.pushInteger(0); } if (this.uuidHum_cien != null) { push.pushInteger(1); push.pushUuid(this.uuidHum_cien); } else {push.pushInteger(0); } push.pushString(this.getFuncao()); } @Override public int sizeOfEntity() { return Page.sizeOfUuid + Page.sizeOfUuid  + Page.sizeOfString(this.getCpf()) + Page.sizeOfString(this.getNome()) + Page.sizeOfBoolean + Page.sizeOfEntity(this.uuidHum_mil) + Page.sizeOfEntity(this.uuidHum_min) + Page.sizeOfEntity(this.uuidHum_cien) + Page.sizeOfString(this.getFuncao()); } static{ cpf.getSchemas().add( new Schema<$Minerador, UniqueOneHumano, java.lang.String>() { @Override public $Minerador newEntity(java.lang.String value) { $Minerador obj = new $Minerador(); obj.setCpf(value); return obj; } @Override public UniqueOneHumano newKey(java.lang.String value) { UniqueOneHumano obj = new UniqueOneHumano(); obj.setCpf(value); return obj; } @Override public EntityStructure<$Minerador> getEntityStructure() { return $Minerador.entityStructure; } @Override public KeyStructure<UniqueOneHumano> getKeyStructure() { return UniqueOneHumano.uniqueOneHumanoStructure; } } ); } } 