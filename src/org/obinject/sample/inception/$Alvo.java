package org.obinject.sample.inception; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.device.File; import org.obinject.exception.TransientObjectException; import org.obinject.meta.Entity; import org.obinject.meta.Uuid; import org.obinject.queries.Attribute; import org.obinject.queries.Schema; import org.obinject.storage.BTree; import org.obinject.storage.BTreeEntity; import org.obinject.storage.EntityStructure; import org.obinject.storage.KeyStructure; import org.obinject.storage.Structure; public class $Alvo extends Alvo implements Entity<$Alvo> { protected Uuid uuid; private static Uuid classId; public static Uuid getClassId(){ if ($Alvo.classId == null) {$Alvo.classId = Uuid.fromString("3F71EFE2-241B-D73A-562A-6A854FED90C8"); }return $Alvo.classId; } public static final BTreeEntity<$Alvo> entityStructure = new BTreeEntity<$Alvo>(new File("build/classes/org/obinject/sample/inception/inception.dbo", 4096)){}; public static final Attribute<Integer> codigo = new Attribute<Integer> (){ @Override public Integer valueOfAttribute(Entity entity) { return ((Alvo)entity).getCodigo(); } }; public static final Attribute<Boolean> vivo = new Attribute<Boolean> (){ @Override public Boolean valueOfAttribute(Entity entity) { return ((Alvo)entity).isVivo(); } }; public static final Attribute<org.obinject.sample.inception.Pessoa> pessoa = new Attribute<org.obinject.sample.inception.Pessoa> (){ @Override public org.obinject.sample.inception.Pessoa valueOfAttribute(Entity entity) { return ((Alvo)entity).getPessoa(); } }; public static final Attribute<org.obinject.sample.inception.Missao> missao = new Attribute<org.obinject.sample.inception.Missao> (){ @Override public org.obinject.sample.inception.Missao valueOfAttribute(Entity entity) { return ((Alvo)entity).getMissao(); } }; public static final Attribute<org.obinject.sample.inception.Sonho> sonho = new Attribute<org.obinject.sample.inception.Sonho> (){ @Override public org.obinject.sample.inception.Sonho valueOfAttribute(Entity entity) { return ((Alvo)entity).getSonho(); } }; public static final Attribute<java.lang.String> informacao = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Alvo)entity).getInformacao(); } }; public static final Attribute<java.lang.String> ideia = new Attribute<java.lang.String> (){ @Override public java.lang.String valueOfAttribute(Entity entity) { return ((Alvo)entity).getIdeia(); } }; public $Alvo(){ this.uuid = Uuid.generator(); } public $Alvo(Alvo obj){ this.setCodigo(obj.getCodigo()); this.setVivo(obj.isVivo()); this.setPessoa(obj.getPessoa()); this.setMissao(obj.getMissao()); this.setSonho(obj.getSonho()); this.setInformacao(obj.getInformacao()); this.setIdeia(obj.getIdeia()); this.uuid = Uuid.generator(); } public $Alvo(Alvo obj, Uuid uuid){ this.setCodigo(obj.getCodigo()); this.setVivo(obj.isVivo()); this.setPessoa(obj.getPessoa()); this.setMissao(obj.getMissao()); this.setSonho(obj.getSonho()); this.setInformacao(obj.getInformacao()); this.setIdeia(obj.getIdeia()); this.uuid = uuid; } public $Alvo($Alvo obj){ this.setCodigo(obj.getCodigo()); this.setVivo(obj.isVivo()); this.setPessoa(obj.getPessoa()); this.setMissao(obj.getMissao()); this.setSonho(obj.getSonho()); this.setInformacao(obj.getInformacao()); this.setIdeia(obj.getIdeia()); this.uuid = obj.getUuid();; } public $Alvo(Uuid uuid){ this.uuid = uuid; } protected Uuid uuidPessoa; @Override public Pessoa getPessoa(){ Pessoa superPessoa = super.getPessoa(); if (superPessoa == null && uuidPessoa != null) {superPessoa = $Pessoa.entityStructure.find(uuidPessoa); this.setPessoa(superPessoa); }return superPessoa; }private void resetUuidPessoa() { if (this.getPessoa() != null) { if (this.getPessoa() instanceof Entity) { uuidPessoa = ((Entity) this.getPessoa()).getUuid(); } else { $Pessoa entity = new $Pessoa (this.getPessoa()); uuidPessoa = $Pessoa.find(entity); if (uuidPessoa == null) { throw new TransientObjectException("Alvo", "pessoa", "Pessoa"); } } } }protected Uuid uuidMissao; @Override public Missao getMissao(){ Missao superMissao = super.getMissao(); if (superMissao == null && uuidMissao != null) {superMissao = $Missao.entityStructure.find(uuidMissao); this.setMissao(superMissao); }return superMissao; }private void resetUuidMissao() { if (this.getMissao() != null) { if (this.getMissao() instanceof Entity) { uuidMissao = ((Entity) this.getMissao()).getUuid(); } else { $Missao entity = new $Missao (this.getMissao()); uuidMissao = $Missao.find(entity); if (uuidMissao == null) { throw new TransientObjectException("Alvo", "missao", "Missao"); } } } }protected Uuid uuidSonho; @Override public Sonho getSonho(){ Sonho superSonho = super.getSonho(); if (superSonho == null && uuidSonho != null) {superSonho = $Sonho.entityStructure.find(uuidSonho); this.setSonho(superSonho); }return superSonho; }private void resetUuidSonho() { if (this.getSonho() != null) { if (this.getSonho() instanceof Entity) { uuidSonho = ((Entity) this.getSonho()).getUuid(); } else { $Sonho entity = new $Sonho (this.getSonho()); uuidSonho = $Sonho.find(entity); if (uuidSonho == null) { throw new TransientObjectException("Alvo", "sonho", "Sonho"); } } } }@Override public boolean isEqual($Alvo obj) { return (this.getCodigo() == obj.getCodigo()) && (this.isVivo() == obj.isVivo()) && (((this.getInformacao() == null) && (obj.getInformacao() == null)) || ((this.getInformacao() != null) && (obj.getInformacao() != null) && (this.getInformacao().equals( obj.getInformacao())))) && (((this.getIdeia() == null) && (obj.getIdeia() == null)) || ((this.getIdeia() != null) && (obj.getIdeia() != null) && (this.getIdeia().equals( obj.getIdeia())))); } @Override public Uuid getUuid() { return this.uuid; } @Override public EntityStructure<$Alvo> getEntityStructure() { return entityStructure; } @Override public boolean inject(){ Uuid uuidInject = $Alvo.find(this); if(uuidInject == null){ resetUuidPessoa(); resetUuidMissao(); resetUuidSonho(); $Alvo.entityStructure.add(this); UniqueOneSonhador.uniqueOneSonhadorStructure.add(new UniqueOneSonhador(this, this.getUuid())); return true; }else{ this.uuid = uuidInject; return false; } } @Override public boolean reject(){ Uuid uuidReject = $Alvo.find(this); if(uuidReject != null){ UniqueOneSonhador.uniqueOneSonhadorStructure.remove(new UniqueOneSonhador(this, this.getUuid())); $Alvo.entityStructure.remove(this); return true; }else{ return false; } } @Override public boolean modify(){ Uuid uuidOld = $Alvo.find(this); $Alvo entityOld = $Alvo.entityStructure.find(uuidOld); if(entityOld != null){ resetUuidPessoa(); resetUuidMissao(); resetUuidSonho(); UniqueOneSonhador uniqueOneSonhadorOld = new UniqueOneSonhador(entityOld, entityOld.getUuid()); UniqueOneSonhador uniqueOneSonhadorNew = new UniqueOneSonhador(this, entityOld.getUuid()); if(uniqueOneSonhadorOld.hasSameKey(uniqueOneSonhadorNew)){ $Sonhador.uniqueOneSonhadorStructure.remove(uniqueOneSonhadorOld); $Sonhador.uniqueOneSonhadorStructure.add(uniqueOneSonhadorNew); } this.uuid = uuidOld; $Alvo.entityStructure.remove(entityOld); $Alvo.entityStructure.add(this); return true; }else{ return false; } } public static Uuid find($Alvo entity) { UniqueOneSonhador unique = new UniqueOneSonhador (entity, entity.getUuid()); return $Sonhador.uniqueOneSonhadorStructure.find(unique); } @Override public boolean pullEntity(byte[] array, int position) { PullPage pull = new PullPage(array, position); Uuid storedClass = pull.pullUuid(); if ($Alvo.classId.equals(storedClass) == true){ uuid = pull.pullUuid(); this.setCodigo(pull.pullInteger()); this.setVivo(pull.pullBoolean()); int totalPessoa = pull.pullInteger(); if(totalPessoa > 0){this.uuidPessoa = pull.pullUuid(); }int totalMissao = pull.pullInteger(); if(totalMissao > 0){this.uuidMissao = pull.pullUuid(); }int totalSonho = pull.pullInteger(); if(totalSonho > 0){this.uuidSonho = pull.pullUuid(); }this.setInformacao(pull.pullString()); this.setIdeia(pull.pullString()); return true; } return false; } @Override public void pushEntity(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushUuid($Alvo.classId); push.pushUuid(uuid); push.pushInteger(this.getCodigo()); push.pushBoolean(this.isVivo()); if (this.uuidPessoa != null) { push.pushInteger(1); push.pushUuid(this.uuidPessoa); } else {push.pushInteger(0); } if (this.uuidMissao != null) { push.pushInteger(1); push.pushUuid(this.uuidMissao); } else {push.pushInteger(0); } if (this.uuidSonho != null) { push.pushInteger(1); push.pushUuid(this.uuidSonho); } else {push.pushInteger(0); } push.pushString(this.getInformacao()); push.pushString(this.getIdeia()); } @Override public int sizeOfEntity() { return Page.sizeOfUuid + Page.sizeOfUuid  + Page.sizeOfInteger + Page.sizeOfBoolean + Page.sizeOfEntity(this.uuidPessoa) + Page.sizeOfEntity(this.uuidMissao) + Page.sizeOfEntity(this.uuidSonho) + Page.sizeOfString(this.getInformacao()) + Page.sizeOfString(this.getIdeia()); } static{ codigo.getSchemas().add( new Schema<$Alvo, UniqueOneSonhador, Integer>() { @Override public $Alvo newEntity(Integer value) { $Alvo obj = new $Alvo(); obj.setCodigo(value); return obj; } @Override public UniqueOneSonhador newKey(Integer value) { UniqueOneSonhador obj = new UniqueOneSonhador(); obj.setCodigo(value); return obj; } @Override public EntityStructure<$Alvo> getEntityStructure() { return $Alvo.entityStructure; } @Override public KeyStructure<UniqueOneSonhador> getKeyStructure() { return UniqueOneSonhador.uniqueOneSonhadorStructure; } } ); } } 