package org.obinject.sample.locadora; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.device.File; import org.obinject.exception.TransientObjectException; import org.obinject.meta.Entity; import org.obinject.meta.Uuid; import org.obinject.queries.Attribute; import org.obinject.queries.Schema; import org.obinject.storage.BTree; import org.obinject.storage.BTreeEntity; import org.obinject.storage.EntityStructure; import org.obinject.storage.KeyStructure; import org.obinject.storage.Structure; public class $Locacao extends Locacao implements Entity<$Locacao> { protected Uuid uuid; private static Uuid classId; public static Uuid getClassId(){ if ($Locacao.classId == null) {$Locacao.classId = Uuid.fromString("55D5EE4B-A0DC-50AF-8F12-C47ED78A53B6"); }return $Locacao.classId; } public static final BTreeEntity<$Locacao> entityStructure = new BTreeEntity<$Locacao>(new File("build/classes/org/obinject/sample/locadora/locadora.dbo", 4096)){}; public static final BTree<UniqueOneLocacao> uniqueOneLocacaoStructure = new BTree<UniqueOneLocacao>(new File("build/classes/org/obinject/sample/locadora/locadora.dbo", 4096)){}; public static final Attribute<Integer> id = new Attribute<Integer> (){ @Override public Integer valueOfAttribute(Entity entity) { return ((Locacao)entity).getId(); } }; public static final Attribute<java.util.Date> dataLocacao = new Attribute<java.util.Date> (){ @Override public java.util.Date valueOfAttribute(Entity entity) { return ((Locacao)entity).getDataLocacao(); } }; public static final Attribute<java.util.Date> dataDevolucao = new Attribute<java.util.Date> (){ @Override public java.util.Date valueOfAttribute(Entity entity) { return ((Locacao)entity).getDataDevolucao(); } }; public static final Attribute<java.util.List> filmes = new Attribute<java.util.List> (){ @Override public java.util.List valueOfAttribute(Entity entity) { return ((Locacao)entity).getFilmes(); } }; public static final Attribute<org.obinject.sample.locadora.Cliente> cliente = new Attribute<org.obinject.sample.locadora.Cliente> (){ @Override public org.obinject.sample.locadora.Cliente valueOfAttribute(Entity entity) { return ((Locacao)entity).getCliente(); } }; public $Locacao(){ this.uuid = Uuid.generator(); } public $Locacao(Locacao obj){ this.setId(obj.getId()); this.setDataLocacao(obj.getDataLocacao()); this.setDataDevolucao(obj.getDataDevolucao()); this.setFilmes(obj.getFilmes()); this.setCliente(obj.getCliente()); this.uuid = Uuid.generator(); } public $Locacao(Locacao obj, Uuid uuid){ this.setId(obj.getId()); this.setDataLocacao(obj.getDataLocacao()); this.setDataDevolucao(obj.getDataDevolucao()); this.setFilmes(obj.getFilmes()); this.setCliente(obj.getCliente()); this.uuid = uuid; } public $Locacao($Locacao obj){ this.setId(obj.getId()); this.setDataLocacao(obj.getDataLocacao()); this.setDataDevolucao(obj.getDataDevolucao()); this.setFilmes(obj.getFilmes()); this.setCliente(obj.getCliente()); this.uuid = obj.getUuid();; } public $Locacao(Uuid uuid){ this.uuid = uuid; } protected java.util.List<Uuid> uuidFilmes = new java.util.ArrayList<>(); @Override public java.util.List<org.obinject.sample.locadora.Filme> getFilmes() {java.util.List<org.obinject.sample.locadora.Filme> superFilmes = super.getFilmes(); if ((superFilmes.isEmpty()) && (!uuidFilmes.isEmpty())) {for (Uuid uuid : uuidFilmes) {superFilmes.add($Filme.entityStructure.find(uuid)); } }return superFilmes; }private void resetUuidFilmes(){ uuidFilmes.clear(); if (this.getFilmes() != null) {for (Filme obj : this.getFilmes()) { if (obj instanceof Entity) { uuidFilmes.add(((Entity) obj).getUuid()); } else { $Filme entity = new $Filme (obj); uuidFilmes.add($Filme.find(entity)); if (uuidFilmes == null) { throw new TransientObjectException("Locacao", "filmes", "Filme"); } } } } }protected Uuid uuidCliente; @Override public Cliente getCliente(){ Cliente superCliente = super.getCliente(); if (superCliente == null && uuidCliente != null) {superCliente = $Cliente.entityStructure.find(uuidCliente); this.setCliente(superCliente); }return superCliente; }private void resetUuidCliente() { if (this.getCliente() != null) { if (this.getCliente() instanceof Entity) { uuidCliente = ((Entity) this.getCliente()).getUuid(); } else { $Cliente entity = new $Cliente (this.getCliente()); uuidCliente = $Cliente.find(entity); if (uuidCliente == null) { throw new TransientObjectException("Locacao", "cliente", "Cliente"); } } } }@Override public boolean isEqual($Locacao obj) { return (this.getId() == obj.getId()) && (((this.getDataLocacao() == null) && (obj.getDataLocacao() == null)) || ((this.getDataLocacao() != null) && (obj.getDataLocacao() != null) && (this.getDataLocacao().equals( obj.getDataLocacao())))) && (((this.getDataDevolucao() == null) && (obj.getDataDevolucao() == null)) || ((this.getDataDevolucao() != null) && (obj.getDataDevolucao() != null) && (this.getDataDevolucao().equals( obj.getDataDevolucao())))); } @Override public Uuid getUuid() { return this.uuid; } @Override public EntityStructure<$Locacao> getEntityStructure() { return entityStructure; } @Override public boolean inject(){ Uuid uuidInject = $Locacao.find(this); if(uuidInject == null){ resetUuidFilmes(); resetUuidCliente(); $Locacao.entityStructure.add(this); UniqueOneLocacao.uniqueOneLocacaoStructure.add(new UniqueOneLocacao(this, this.getUuid())); return true; }else{ this.uuid = uuidInject; return false; } } @Override public boolean reject(){ Uuid uuidReject = $Locacao.find(this); if(uuidReject != null){ UniqueOneLocacao.uniqueOneLocacaoStructure.remove(new UniqueOneLocacao(this, this.getUuid())); $Locacao.entityStructure.remove(this); return true; }else{ return false; } } @Override public boolean modify(){ Uuid uuidOld = $Locacao.find(this); $Locacao entityOld = $Locacao.entityStructure.find(uuidOld); if(entityOld != null){ resetUuidFilmes(); resetUuidCliente(); UniqueOneLocacao uniqueOneLocacaoOld = new UniqueOneLocacao(entityOld, entityOld.getUuid()); UniqueOneLocacao uniqueOneLocacaoNew = new UniqueOneLocacao(this, entityOld.getUuid()); if(uniqueOneLocacaoOld.hasSameKey(uniqueOneLocacaoNew)){ $Locacao.uniqueOneLocacaoStructure.remove(uniqueOneLocacaoOld); $Locacao.uniqueOneLocacaoStructure.add(uniqueOneLocacaoNew); } this.uuid = uuidOld; $Locacao.entityStructure.remove(entityOld); $Locacao.entityStructure.add(this); return true; }else{ return false; } } public static Uuid find($Locacao entity) { UniqueOneLocacao unique = new UniqueOneLocacao (entity, entity.getUuid()); return $Locacao.uniqueOneLocacaoStructure.find(unique); } @Override public boolean pullEntity(byte[] array, int position) { PullPage pull = new PullPage(array, position); Uuid storedClass = pull.pullUuid(); if ($Locacao.classId.equals(storedClass) == true){ uuid = pull.pullUuid(); this.setId(pull.pullInteger()); this.setDataLocacao(pull.pullDate()); this.setDataDevolucao(pull.pullDate()); int totalFilmes = pull.pullInteger(); for (int i = 0; i < totalFilmes; i++) {this.uuidFilmes.add(pull.pullUuid()); }int totalCliente = pull.pullInteger(); if(totalCliente > 0){this.uuidCliente = pull.pullUuid(); }return true; } return false; } @Override public void pushEntity(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushUuid($Locacao.classId); push.pushUuid(uuid); push.pushInteger(this.getId()); push.pushDate(this.getDataLocacao()); push.pushDate(this.getDataDevolucao()); push.pushInteger(this.uuidFilmes.size()); for (Uuid uuidPush : this.uuidFilmes) {push.pushUuid(uuidPush); }if (this.uuidCliente != null) { push.pushInteger(1); push.pushUuid(this.uuidCliente); } else {push.pushInteger(0); } } @Override public int sizeOfEntity() { return Page.sizeOfUuid + Page.sizeOfUuid  + Page.sizeOfInteger + Page.sizeOfDate + Page.sizeOfDate + Page.sizeOfEntityCollection(this.uuidFilmes) + Page.sizeOfEntity(this.uuidCliente); } static{ id.getSchemas().add( new Schema<$Locacao, UniqueOneLocacao, Integer>() { @Override public $Locacao newEntity(Integer value) { $Locacao obj = new $Locacao(); obj.setId(value); return obj; } @Override public UniqueOneLocacao newKey(Integer value) { UniqueOneLocacao obj = new UniqueOneLocacao(); obj.setId(value); return obj; } @Override public EntityStructure<$Locacao> getEntityStructure() { return $Locacao.entityStructure; } @Override public KeyStructure<UniqueOneLocacao> getKeyStructure() { return UniqueOneLocacao.uniqueOneLocacaoStructure; } } ); } } 