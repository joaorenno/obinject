package org.obinject.sample.universe; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.device.File; import org.obinject.exception.TransientObjectException; import org.obinject.meta.Entity; import org.obinject.meta.Uuid; import org.obinject.queries.Attribute; import org.obinject.queries.Schema; import org.obinject.storage.BTree; import org.obinject.storage.BTreeEntity; import org.obinject.storage.EntityStructure; import org.obinject.storage.KeyStructure; import org.obinject.storage.Structure; public class $Planet extends Planet implements Entity<$Planet> { protected Uuid uuid; private static Uuid classId; public static Uuid getClassId(){ if ($Planet.classId == null) {$Planet.classId = Uuid.fromString("C24DC6A6-E95F-BB6E-0C18-4B81B23756DB"); }return $Planet.classId; } public static final BTreeEntity<$Planet> entityStructure = new BTreeEntity<$Planet>(new File("build/classes/org/obinject/sample/universe/universe.dbo", 4096)){}; public static final BTree<SortOnePlanet> sortOnePlanetStructure = new BTree<SortOnePlanet>(new File("build/classes/org/obinject/sample/universe/universe.dbo", 4096)){}; public static final BTree<SortTwoPlanet> sortTwoPlanetStructure = new BTree<SortTwoPlanet>(new File("build/classes/org/obinject/sample/universe/universe.dbo", 4096)){}; public static final BTree<UniqueOnePlanet> uniqueOnePlanetStructure = new BTree<UniqueOnePlanet>(new File("build/classes/org/obinject/sample/universe/universe.dbo", 4096)){}; public static final Attribute<java.lang.String> name = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Planet)entity).getName(); } }; public static final Attribute<Float> latitude = new Attribute<Float> (){ @Override public Float valueOfAttribute(Entity entity) { return ((Planet)entity).getLatitude(); } }; public static final Attribute<Float> longitude = new Attribute<Float> (){ @Override public Float valueOfAttribute(Entity entity) { return ((Planet)entity).getLongitude(); } }; public $Planet(){ this.uuid = Uuid.generator(); } public $Planet(Planet obj){ this.setName(obj.getName()); this.setLatitude(obj.getLatitude()); this.setLongitude(obj.getLongitude()); this.uuid = Uuid.generator(); } public $Planet(Planet obj, Uuid uuid){ this.setName(obj.getName()); this.setLatitude(obj.getLatitude()); this.setLongitude(obj.getLongitude()); this.uuid = uuid; } public $Planet($Planet obj){ this.setName(obj.getName()); this.setLatitude(obj.getLatitude()); this.setLongitude(obj.getLongitude()); this.uuid = obj.getUuid();; } public $Planet(Uuid uuid){ this.uuid = uuid; } @Override public boolean isEqual($Planet obj) {  return (((this.getName() == null) && (obj.getName() == null)) || ((this.getName() != null) && (obj.getName() != null) && (this.getName().equals( obj.getName())))) && (this.getLatitude() == obj.getLatitude()) && (this.getLongitude() == obj.getLongitude()); } @Override public Uuid getUuid() { return this.uuid; } @Override public EntityStructure<$Planet> getEntityStructure() { return entityStructure; } @Override public boolean inject(){ Uuid uuidInject = $Planet.find(this); if(uuidInject == null){ $Planet.entityStructure.add(this); SortOnePlanet.sortOnePlanetStructure.add(new SortOnePlanet(this, this.getUuid())); SortTwoPlanet.sortTwoPlanetStructure.add(new SortTwoPlanet(this, this.getUuid())); UniqueOnePlanet.uniqueOnePlanetStructure.add(new UniqueOnePlanet(this, this.getUuid())); return true; }else{ this.uuid = uuidInject; return false; } } @Override public boolean reject(){ Uuid uuidReject = $Planet.find(this); if(uuidReject != null){ SortOnePlanet.sortOnePlanetStructure.remove(new SortOnePlanet(this, this.getUuid())); SortTwoPlanet.sortTwoPlanetStructure.remove(new SortTwoPlanet(this, this.getUuid())); UniqueOnePlanet.uniqueOnePlanetStructure.remove(new UniqueOnePlanet(this, this.getUuid())); $Planet.entityStructure.remove(this); return true; }else{ return false; } } @Override public boolean modify(){ Uuid uuidOld = $Planet.find(this); $Planet entityOld = $Planet.entityStructure.find(uuidOld); if(entityOld != null){ SortOnePlanet sortOnePlanetOld = new SortOnePlanet(entityOld, entityOld.getUuid()); SortOnePlanet sortOnePlanetNew = new SortOnePlanet(this, entityOld.getUuid()); if(sortOnePlanetOld.hasSameKey(sortOnePlanetNew)){ $Planet.sortOnePlanetStructure.remove(sortOnePlanetOld); $Planet.sortOnePlanetStructure.add(sortOnePlanetNew); } SortTwoPlanet sortTwoPlanetOld = new SortTwoPlanet(entityOld, entityOld.getUuid()); SortTwoPlanet sortTwoPlanetNew = new SortTwoPlanet(this, entityOld.getUuid()); if(sortTwoPlanetOld.hasSameKey(sortTwoPlanetNew)){ $Planet.sortTwoPlanetStructure.remove(sortTwoPlanetOld); $Planet.sortTwoPlanetStructure.add(sortTwoPlanetNew); } UniqueOnePlanet uniqueOnePlanetOld = new UniqueOnePlanet(entityOld, entityOld.getUuid()); UniqueOnePlanet uniqueOnePlanetNew = new UniqueOnePlanet(this, entityOld.getUuid()); if(uniqueOnePlanetOld.hasSameKey(uniqueOnePlanetNew)){ $Planet.uniqueOnePlanetStructure.remove(uniqueOnePlanetOld); $Planet.uniqueOnePlanetStructure.add(uniqueOnePlanetNew); } this.uuid = uuidOld; $Planet.entityStructure.remove(entityOld); $Planet.entityStructure.add(this); return true; }else{ return false; } } public static Uuid find($Planet entity) { UniqueOnePlanet unique = new UniqueOnePlanet (entity, entity.getUuid()); return $Planet.uniqueOnePlanetStructure.find(unique); } @Override public boolean pullEntity(byte[] array, int position) { PullPage pull = new PullPage(array, position); Uuid storedClass = pull.pullUuid(); if ($Planet.classId.equals(storedClass) == true){ uuid = pull.pullUuid(); this.setName(pull.pullString()); this.setLatitude(pull.pullFloat()); this.setLongitude(pull.pullFloat()); return true; } return false; } @Override public void pushEntity(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushUuid($Planet.classId); push.pushUuid(uuid); push.pushString(this.getName()); push.pushFloat(this.getLatitude()); push.pushFloat(this.getLongitude()); } @Override public int sizeOfEntity() { return Page.sizeOfUuid + Page.sizeOfUuid  + Page.sizeOfString(this.getName()) + Page.sizeOfFloat + Page.sizeOfFloat; } static{ name.getSchemas().add( new Schema<$Planet, UniqueOnePlanet, java.lang.String>() { @Override public $Planet newEntity(java.lang.String value) { $Planet obj = new $Planet(); obj.setName(value); return obj; } @Override public UniqueOnePlanet newKey(java.lang.String value) { UniqueOnePlanet obj = new UniqueOnePlanet(); obj.setName(value); return obj; } @Override public EntityStructure<$Planet> getEntityStructure() { return $Planet.entityStructure; } @Override public KeyStructure<UniqueOnePlanet> getKeyStructure() { return UniqueOnePlanet.uniqueOnePlanetStructure; } } ); name.getSchemas().add( new Schema<$Planet, SortOnePlanet, java.lang.String>() { @Override public $Planet newEntity(java.lang.String value) { $Planet obj = new $Planet(); obj.setName(value); return obj; } @Override public SortOnePlanet newKey(java.lang.String value) { SortOnePlanet obj = new SortOnePlanet(); obj.setName(value); return obj; } @Override public EntityStructure<$Planet> getEntityStructure() { return $Planet.entityStructure; } @Override public KeyStructure<SortOnePlanet> getKeyStructure() { return SortOnePlanet.sortOnePlanetStructure; } } ); name.getSchemas().add( new Schema<$Planet, SortTwoPlanet, java.lang.String>() { @Override public $Planet newEntity(java.lang.String value) { $Planet obj = new $Planet(); obj.setName(value); return obj; } @Override public SortTwoPlanet newKey(java.lang.String value) { SortTwoPlanet obj = new SortTwoPlanet(); obj.setName(value); return obj; } @Override public EntityStructure<$Planet> getEntityStructure() { return $Planet.entityStructure; } @Override public KeyStructure<SortTwoPlanet> getKeyStructure() { return SortTwoPlanet.sortTwoPlanetStructure; } } ); latitude.getSchemas().add( new Schema<$Planet, SortOnePlanet, Float>() { @Override public $Planet newEntity(Float value) { $Planet obj = new $Planet(); obj.setLatitude(value); return obj; } @Override public SortOnePlanet newKey(Float value) { SortOnePlanet obj = new SortOnePlanet(); obj.setLatitude(value); return obj; } @Override public EntityStructure<$Planet> getEntityStructure() { return $Planet.entityStructure; } @Override public KeyStructure<SortOnePlanet> getKeyStructure() { return SortOnePlanet.sortOnePlanetStructure; } } ); latitude.getSchemas().add( new Schema<$Planet, SortTwoPlanet, Float>() { @Override public $Planet newEntity(Float value) { $Planet obj = new $Planet(); obj.setLatitude(value); return obj; } @Override public SortTwoPlanet newKey(Float value) { SortTwoPlanet obj = new SortTwoPlanet(); obj.setLatitude(value); return obj; } @Override public EntityStructure<$Planet> getEntityStructure() { return $Planet.entityStructure; } @Override public KeyStructure<SortTwoPlanet> getKeyStructure() { return SortTwoPlanet.sortTwoPlanetStructure; } } ); longitude.getSchemas().add( new Schema<$Planet, SortTwoPlanet, Float>() { @Override public $Planet newEntity(Float value) { $Planet obj = new $Planet(); obj.setLongitude(value); return obj; } @Override public SortTwoPlanet newKey(Float value) { SortTwoPlanet obj = new SortTwoPlanet(); obj.setLongitude(value); return obj; } @Override public EntityStructure<$Planet> getEntityStructure() { return $Planet.entityStructure; } @Override public KeyStructure<SortTwoPlanet> getKeyStructure() { return SortTwoPlanet.sortTwoPlanetStructure; } } ); longitude.getSchemas().add( new Schema<$Planet, SortOnePlanet, Float>() { @Override public $Planet newEntity(Float value) { $Planet obj = new $Planet(); obj.setLongitude(value); return obj; } @Override public SortOnePlanet newKey(Float value) { SortOnePlanet obj = new SortOnePlanet(); obj.setLongitude(value); return obj; } @Override public EntityStructure<$Planet> getEntityStructure() { return $Planet.entityStructure; } @Override public KeyStructure<SortOnePlanet> getKeyStructure() { return SortOnePlanet.sortOnePlanetStructure; } } ); } } 