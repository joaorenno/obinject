package org.obinject.sample.elections; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.device.File; import org.obinject.exception.TransientObjectException; import org.obinject.meta.Entity; import org.obinject.meta.Uuid; import org.obinject.queries.Attribute; import org.obinject.queries.Schema; import org.obinject.storage.BTree; import org.obinject.storage.BTreeEntity; import org.obinject.storage.EntityStructure; import org.obinject.storage.KeyStructure; import org.obinject.storage.Structure; public class $Senator extends Senator implements Entity<$Senator> { protected Uuid uuid; private static Uuid classId; public static Uuid getClassId(){ if ($Senator.classId == null) {$Senator.classId = Uuid.fromString("E519991B-3111-C8E6-0AA2-5A571D4D144A"); }return $Senator.classId; } public static final BTreeEntity<$Senator> entityStructure = new BTreeEntity<$Senator>(new File("build/classes/org/obinject/sample/elections/elections.dbo", 4096)){}; public static final Attribute<Integer> identityCard = new Attribute<Integer> (){ @Override public Integer valueOfAttribute(Entity entity) { return ((Senator)entity).getIdentityCard(); } }; public static final Attribute<java.lang.String> name = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Senator)entity).getName(); } }; public static final Attribute<java.lang.String> adress = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Senator)entity).getAdress(); } }; public static final Attribute<java.lang.String> candidateName = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Senator)entity).getCandidateName(); } }; public static final Attribute<java.lang.String> candidateState = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Senator)entity).getCandidateState(); } }; public static final Attribute<Integer> mandate = new Attribute<Integer> (){ @Override public Integer valueOfAttribute(Entity entity) { return ((Senator)entity).getMandate(); } }; public $Senator(){ this.uuid = Uuid.generator(); } public $Senator(Senator obj){ this.setIdentityCard(obj.getIdentityCard()); this.setName(obj.getName()); this.setAdress(obj.getAdress()); this.setCandidateName(obj.getCandidateName()); this.setCandidateState(obj.getCandidateState()); this.setMandate(obj.getMandate()); this.uuid = Uuid.generator(); } public $Senator(Senator obj, Uuid uuid){ this.setIdentityCard(obj.getIdentityCard()); this.setName(obj.getName()); this.setAdress(obj.getAdress()); this.setCandidateName(obj.getCandidateName()); this.setCandidateState(obj.getCandidateState()); this.setMandate(obj.getMandate()); this.uuid = uuid; } public $Senator($Senator obj){ this.setIdentityCard(obj.getIdentityCard()); this.setName(obj.getName()); this.setAdress(obj.getAdress()); this.setCandidateName(obj.getCandidateName()); this.setCandidateState(obj.getCandidateState()); this.setMandate(obj.getMandate()); this.uuid = obj.getUuid();; } public $Senator(Uuid uuid){ this.uuid = uuid; } @Override public boolean isEqual($Senator obj) { return (this.getIdentityCard() == obj.getIdentityCard()) && (((this.getName() == null) && (obj.getName() == null)) || ((this.getName() != null) && (obj.getName() != null) && (this.getName().equals( obj.getName())))) && (((this.getAdress() == null) && (obj.getAdress() == null)) || ((this.getAdress() != null) && (obj.getAdress() != null) && (this.getAdress().equals( obj.getAdress())))) && (((this.getCandidateName() == null) && (obj.getCandidateName() == null)) || ((this.getCandidateName() != null) && (obj.getCandidateName() != null) && (this.getCandidateName().equals( obj.getCandidateName())))) && (((this.getCandidateState() == null) && (obj.getCandidateState() == null)) || ((this.getCandidateState() != null) && (obj.getCandidateState() != null) && (this.getCandidateState().equals( obj.getCandidateState())))) && (this.getMandate() == obj.getMandate()); } @Override public Uuid getUuid() { return this.uuid; } @Override public EntityStructure<$Senator> getEntityStructure() { return entityStructure; } @Override public boolean inject(){ Uuid uuidInject = $Senator.find(this); if(uuidInject == null){ $Senator.entityStructure.add(this); UniqueOnePerson.uniqueOnePersonStructure.add(new UniqueOnePerson(this, this.getUuid())); return true; }else{ this.uuid = uuidInject; return false; } } @Override public boolean reject(){ Uuid uuidReject = $Senator.find(this); if(uuidReject != null){ UniqueOnePerson.uniqueOnePersonStructure.remove(new UniqueOnePerson(this, this.getUuid())); $Senator.entityStructure.remove(this); return true; }else{ return false; } } @Override public boolean modify(){ Uuid uuidOld = $Senator.find(this); $Senator entityOld = $Senator.entityStructure.find(uuidOld); if(entityOld != null){ UniqueOnePerson uniqueOnePersonOld = new UniqueOnePerson(entityOld, entityOld.getUuid()); UniqueOnePerson uniqueOnePersonNew = new UniqueOnePerson(this, entityOld.getUuid()); if(uniqueOnePersonOld.hasSameKey(uniqueOnePersonNew)){ $Person.uniqueOnePersonStructure.remove(uniqueOnePersonOld); $Person.uniqueOnePersonStructure.add(uniqueOnePersonNew); } this.uuid = uuidOld; $Senator.entityStructure.remove(entityOld); $Senator.entityStructure.add(this); return true; }else{ return false; } } public static Uuid find($Senator entity) { UniqueOnePerson unique = new UniqueOnePerson (entity, entity.getUuid()); return $Person.uniqueOnePersonStructure.find(unique); } @Override public boolean pullEntity(byte[] array, int position) { PullPage pull = new PullPage(array, position); Uuid storedClass = pull.pullUuid(); if ($Senator.classId.equals(storedClass) == true){ uuid = pull.pullUuid(); this.setIdentityCard(pull.pullInteger()); this.setName(pull.pullString()); this.setAdress(pull.pullString()); this.setCandidateName(pull.pullString()); this.setCandidateState(pull.pullString()); this.setMandate(pull.pullInteger()); return true; } return false; } @Override public void pushEntity(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushUuid($Senator.classId); push.pushUuid(uuid); push.pushInteger(this.getIdentityCard()); push.pushString(this.getName()); push.pushString(this.getAdress()); push.pushString(this.getCandidateName()); push.pushString(this.getCandidateState()); push.pushInteger(this.getMandate()); } @Override public int sizeOfEntity() { return Page.sizeOfUuid + Page.sizeOfUuid  + Page.sizeOfInteger + Page.sizeOfString(this.getName()) + Page.sizeOfString(this.getAdress()) + Page.sizeOfString(this.getCandidateName()) + Page.sizeOfString(this.getCandidateState()) + Page.sizeOfInteger; } static{ identityCard.getSchemas().add( new Schema<$Senator, UniqueOnePerson, Integer>() { @Override public $Senator newEntity(Integer value) { $Senator obj = new $Senator(); obj.setIdentityCard(value); return obj; } @Override public UniqueOnePerson newKey(Integer value) { UniqueOnePerson obj = new UniqueOnePerson(); obj.setIdentityCard(value); return obj; } @Override public EntityStructure<$Senator> getEntityStructure() { return $Senator.entityStructure; } @Override public KeyStructure<UniqueOnePerson> getKeyStructure() { return UniqueOnePerson.uniqueOnePersonStructure; } } ); } } 