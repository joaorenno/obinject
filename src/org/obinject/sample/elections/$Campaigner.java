package org.obinject.sample.elections; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.device.File; import org.obinject.exception.TransientObjectException; import org.obinject.meta.Entity; import org.obinject.meta.Uuid; import org.obinject.queries.Attribute; import org.obinject.queries.Schema; import org.obinject.storage.BTree; import org.obinject.storage.BTreeEntity; import org.obinject.storage.EntityStructure; import org.obinject.storage.KeyStructure; import org.obinject.storage.Structure; public class $Campaigner extends Campaigner implements Entity<$Campaigner> { protected Uuid uuid; private static Uuid classId; public static Uuid getClassId(){ if ($Campaigner.classId == null) {$Campaigner.classId = Uuid.fromString("DAD8DE02-8B55-B206-1F3D-EB2D6E229AB2"); }return $Campaigner.classId; } public static final BTreeEntity<$Campaigner> entityStructure = new BTreeEntity<$Campaigner>(new File("build/classes/org/obinject/sample/elections/elections.dbo", 4096)){}; public static final Attribute<Integer> identityCard = new Attribute<Integer> (){ @Override public Integer valueOfAttribute(Entity entity) { return ((Campaigner)entity).getIdentityCard(); } }; public static final Attribute<java.lang.String> name = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Campaigner)entity).getName(); } }; public static final Attribute<java.lang.String> adress = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Campaigner)entity).getAdress(); } }; public static final Attribute<java.lang.String> assignment = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Campaigner)entity).getAssignment(); } }; public static final Attribute<Float> income = new Attribute<Float> (){ @Override public Float valueOfAttribute(Entity entity) { return ((Campaigner)entity).getIncome(); } }; public $Campaigner(){ this.uuid = Uuid.generator(); } public $Campaigner(Campaigner obj){ this.setIdentityCard(obj.getIdentityCard()); this.setName(obj.getName()); this.setAdress(obj.getAdress()); this.setAssignment(obj.getAssignment()); this.setIncome(obj.getIncome()); this.uuid = Uuid.generator(); } public $Campaigner(Campaigner obj, Uuid uuid){ this.setIdentityCard(obj.getIdentityCard()); this.setName(obj.getName()); this.setAdress(obj.getAdress()); this.setAssignment(obj.getAssignment()); this.setIncome(obj.getIncome()); this.uuid = uuid; } public $Campaigner($Campaigner obj){ this.setIdentityCard(obj.getIdentityCard()); this.setName(obj.getName()); this.setAdress(obj.getAdress()); this.setAssignment(obj.getAssignment()); this.setIncome(obj.getIncome()); this.uuid = obj.getUuid();; } public $Campaigner(Uuid uuid){ this.uuid = uuid; } @Override public boolean isEqual($Campaigner obj) { return (this.getIdentityCard() == obj.getIdentityCard()) && (((this.getName() == null) && (obj.getName() == null)) || ((this.getName() != null) && (obj.getName() != null) && (this.getName().equals( obj.getName())))) && (((this.getAdress() == null) && (obj.getAdress() == null)) || ((this.getAdress() != null) && (obj.getAdress() != null) && (this.getAdress().equals( obj.getAdress())))) && (((this.getAssignment() == null) && (obj.getAssignment() == null)) || ((this.getAssignment() != null) && (obj.getAssignment() != null) && (this.getAssignment().equals( obj.getAssignment())))) && (this.getIncome() == obj.getIncome()); } @Override public Uuid getUuid() { return this.uuid; } @Override public EntityStructure<$Campaigner> getEntityStructure() { return entityStructure; } @Override public boolean inject(){ Uuid uuidInject = $Campaigner.find(this); if(uuidInject == null){ $Campaigner.entityStructure.add(this); UniqueOnePerson.uniqueOnePersonStructure.add(new UniqueOnePerson(this, this.getUuid())); return true; }else{ this.uuid = uuidInject; return false; } } @Override public boolean reject(){ Uuid uuidReject = $Campaigner.find(this); if(uuidReject != null){ UniqueOnePerson.uniqueOnePersonStructure.remove(new UniqueOnePerson(this, this.getUuid())); $Campaigner.entityStructure.remove(this); return true; }else{ return false; } } @Override public boolean modify(){ Uuid uuidOld = $Campaigner.find(this); $Campaigner entityOld = $Campaigner.entityStructure.find(uuidOld); if(entityOld != null){ UniqueOnePerson uniqueOnePersonOld = new UniqueOnePerson(entityOld, entityOld.getUuid()); UniqueOnePerson uniqueOnePersonNew = new UniqueOnePerson(this, entityOld.getUuid()); if(uniqueOnePersonOld.hasSameKey(uniqueOnePersonNew)){ $Person.uniqueOnePersonStructure.remove(uniqueOnePersonOld); $Person.uniqueOnePersonStructure.add(uniqueOnePersonNew); } this.uuid = uuidOld; $Campaigner.entityStructure.remove(entityOld); $Campaigner.entityStructure.add(this); return true; }else{ return false; } } public static Uuid find($Campaigner entity) { UniqueOnePerson unique = new UniqueOnePerson (entity, entity.getUuid()); return $Person.uniqueOnePersonStructure.find(unique); } @Override public boolean pullEntity(byte[] array, int position) { PullPage pull = new PullPage(array, position); Uuid storedClass = pull.pullUuid(); if ($Campaigner.classId.equals(storedClass) == true){ uuid = pull.pullUuid(); this.setIdentityCard(pull.pullInteger()); this.setName(pull.pullString()); this.setAdress(pull.pullString()); this.setAssignment(pull.pullString()); this.setIncome(pull.pullFloat()); return true; } return false; } @Override public void pushEntity(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushUuid($Campaigner.classId); push.pushUuid(uuid); push.pushInteger(this.getIdentityCard()); push.pushString(this.getName()); push.pushString(this.getAdress()); push.pushString(this.getAssignment()); push.pushFloat(this.getIncome()); } @Override public int sizeOfEntity() { return Page.sizeOfUuid + Page.sizeOfUuid  + Page.sizeOfInteger + Page.sizeOfString(this.getName()) + Page.sizeOfString(this.getAdress()) + Page.sizeOfString(this.getAssignment()) + Page.sizeOfFloat; } static{ identityCard.getSchemas().add( new Schema<$Campaigner, UniqueOnePerson, Integer>() { @Override public $Campaigner newEntity(Integer value) { $Campaigner obj = new $Campaigner(); obj.setIdentityCard(value); return obj; } @Override public UniqueOnePerson newKey(Integer value) { UniqueOnePerson obj = new UniqueOnePerson(); obj.setIdentityCard(value); return obj; } @Override public EntityStructure<$Campaigner> getEntityStructure() { return $Campaigner.entityStructure; } @Override public KeyStructure<UniqueOnePerson> getKeyStructure() { return UniqueOnePerson.uniqueOnePersonStructure; } } ); } } 