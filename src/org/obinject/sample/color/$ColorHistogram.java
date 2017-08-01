package org.obinject.sample.color; import org.obinject.block.Page; import org.obinject.block.PullPage; import org.obinject.block.PushPage; import org.obinject.device.File; import org.obinject.exception.TransientObjectException; import org.obinject.meta.Entity; import org.obinject.meta.Uuid; import org.obinject.queries.Attribute; import org.obinject.queries.Schema; import org.obinject.storage.BTree; import org.obinject.storage.BTreeEntity; import org.obinject.storage.EntityStructure; import org.obinject.storage.KeyStructure; import org.obinject.storage.Structure; import org.obinject.storage.MTree; import org.obinject.storage.RTree; public class $ColorHistogram extends ColorHistogram implements Entity<$ColorHistogram> { protected Uuid uuid; private static Uuid classId; public static Uuid getClassId(){ if ($ColorHistogram.classId == null) {$ColorHistogram.classId = Uuid.fromString("6069F9CE-D049-9157-DBD4-EF1AFEBA96B9"); }return $ColorHistogram.classId; } public static final BTreeEntity<$ColorHistogram> entityStructure = new BTreeEntity<$ColorHistogram>(new File("build/classes/org/obinject/sample/color/color.dbo", 2048)){}; public static final BTree<UniqueOneColorHistogram> uniqueOneColorHistogramStructure = new BTree<UniqueOneColorHistogram>(new File("build/classes/org/obinject/sample/color/color.dbo", 2048)){}; public static final RTree<RectangleOneColorHistogram> rectangleOneColorHistogramStructure = new RTree<RectangleOneColorHistogram>(new File("build/classes/org/obinject/sample/color/color.dbo", 2048)){}; public static final MTree<PointOneColorHistogram> pointOneColorHistogramStructure = new MTree<PointOneColorHistogram>(new File("build/classes/org/obinject/sample/color/color.dbo", 2048)){}; public static final Attribute<Integer> id = new Attribute<Integer> (){ @Override public Integer valueOfAttribute(Entity entity) { return ((ColorHistogram)entity).getId(); } }; public static final Attribute<double[]> colors = new Attribute<double[]> (){ @Override public double[] valueOfAttribute(Entity entity) { return ((ColorHistogram)entity).getColors(); } }; public static final Attribute<double[]> extension = new Attribute<double[]> (){ @Override public double[] valueOfAttribute(Entity entity) { return ((ColorHistogram)entity).getExtension(); } }; public $ColorHistogram(){ this.uuid = Uuid.generator(); } public $ColorHistogram(ColorHistogram obj){ this.setId(obj.getId()); this.setColors(obj.getColors()); this.setExtension(obj.getExtension()); this.uuid = Uuid.generator(); } public $ColorHistogram(ColorHistogram obj, Uuid uuid){ this.setId(obj.getId()); this.setColors(obj.getColors()); this.setExtension(obj.getExtension()); this.uuid = uuid; } public $ColorHistogram($ColorHistogram obj){ this.setId(obj.getId()); this.setColors(obj.getColors()); this.setExtension(obj.getExtension()); this.uuid = obj.getUuid();; } public $ColorHistogram(Uuid uuid){ this.uuid = uuid; } @Override public boolean isEqual($ColorHistogram obj) { return (this.getId() == obj.getId()); } @Override public Uuid getUuid() { return this.uuid; } @Override public EntityStructure<$ColorHistogram> getEntityStructure() { return entityStructure; } @Override public boolean inject(){ Uuid uuidInject = $ColorHistogram.find(this); if(uuidInject == null){ $ColorHistogram.entityStructure.add(this); UniqueOneColorHistogram.uniqueOneColorHistogramStructure.add(new UniqueOneColorHistogram(this, this.getUuid())); RectangleOneColorHistogram.rectangleOneColorHistogramStructure.add(new RectangleOneColorHistogram(this, this.getUuid())); PointOneColorHistogram.pointOneColorHistogramStructure.add(new PointOneColorHistogram(this, this.getUuid())); return true; }else{ this.uuid = uuidInject; return false; } } @Override public boolean reject(){ Uuid uuidReject = $ColorHistogram.find(this); if(uuidReject != null){ UniqueOneColorHistogram.uniqueOneColorHistogramStructure.remove(new UniqueOneColorHistogram(this, this.getUuid())); RectangleOneColorHistogram.rectangleOneColorHistogramStructure.remove(new RectangleOneColorHistogram(this, this.getUuid())); PointOneColorHistogram.pointOneColorHistogramStructure.remove(new PointOneColorHistogram(this, this.getUuid())); $ColorHistogram.entityStructure.remove(this); return true; }else{ return false; } } @Override public boolean modify(){ Uuid uuidOld = $ColorHistogram.find(this); $ColorHistogram entityOld = $ColorHistogram.entityStructure.find(uuidOld); if(entityOld != null){ UniqueOneColorHistogram uniqueOneColorHistogramOld = new UniqueOneColorHistogram(entityOld, entityOld.getUuid()); UniqueOneColorHistogram uniqueOneColorHistogramNew = new UniqueOneColorHistogram(this, entityOld.getUuid()); if(uniqueOneColorHistogramOld.hasSameKey(uniqueOneColorHistogramNew)){ $ColorHistogram.uniqueOneColorHistogramStructure.remove(uniqueOneColorHistogramOld); $ColorHistogram.uniqueOneColorHistogramStructure.add(uniqueOneColorHistogramNew); } RectangleOneColorHistogram rectangleOneColorHistogramOld = new RectangleOneColorHistogram(entityOld, entityOld.getUuid()); RectangleOneColorHistogram rectangleOneColorHistogramNew = new RectangleOneColorHistogram(this, entityOld.getUuid()); if(rectangleOneColorHistogramOld.hasSameKey(rectangleOneColorHistogramNew)){ $ColorHistogram.rectangleOneColorHistogramStructure.remove(rectangleOneColorHistogramOld); $ColorHistogram.rectangleOneColorHistogramStructure.add(rectangleOneColorHistogramNew); } PointOneColorHistogram pointOneColorHistogramOld = new PointOneColorHistogram(entityOld, entityOld.getUuid()); PointOneColorHistogram pointOneColorHistogramNew = new PointOneColorHistogram(this, entityOld.getUuid()); if(pointOneColorHistogramOld.hasSameKey(pointOneColorHistogramNew)){ $ColorHistogram.pointOneColorHistogramStructure.remove(pointOneColorHistogramOld); $ColorHistogram.pointOneColorHistogramStructure.add(pointOneColorHistogramNew); } this.uuid = uuidOld; $ColorHistogram.entityStructure.remove(entityOld); $ColorHistogram.entityStructure.add(this); return true; }else{ return false; } } public static Uuid find($ColorHistogram entity) { UniqueOneColorHistogram unique = new UniqueOneColorHistogram (entity, entity.getUuid()); return $ColorHistogram.uniqueOneColorHistogramStructure.find(unique); } @Override public boolean pullEntity(byte[] array, int position) { PullPage pull = new PullPage(array, position); Uuid storedClass = pull.pullUuid(); if ($ColorHistogram.classId.equals(storedClass) == true){ uuid = pull.pullUuid(); this.setId(pull.pullInteger()); this.setColors((double[]) pull.pullMatrix()); this.setExtension((double[]) pull.pullMatrix()); return true; } return false; } @Override public void pushEntity(byte[] array, int position){ PushPage push = new PushPage(array, position); push.pushUuid($ColorHistogram.classId); push.pushUuid(uuid); push.pushInteger(this.getId()); push.pushMatrix(getColors()); push.pushMatrix(getExtension()); } @Override public int sizeOfEntity() { return Page.sizeOfUuid + Page.sizeOfUuid  + Page.sizeOfInteger + Page.sizeOfMatrix(this.getColors()) + Page.sizeOfMatrix(this.getExtension()); } static{ id.getSchemas().add( new Schema<$ColorHistogram, UniqueOneColorHistogram, Integer>() { @Override public $ColorHistogram newEntity(Integer value) { $ColorHistogram obj = new $ColorHistogram(); obj.setId(value); return obj; } @Override public UniqueOneColorHistogram newKey(Integer value) { UniqueOneColorHistogram obj = new UniqueOneColorHistogram(); obj.setId(value); return obj; } @Override public EntityStructure<$ColorHistogram> getEntityStructure() { return $ColorHistogram.entityStructure; } @Override public KeyStructure<UniqueOneColorHistogram> getKeyStructure() { return UniqueOneColorHistogram.uniqueOneColorHistogramStructure; } } ); colors.getSchemas().add( new Schema<$ColorHistogram, RectangleOneColorHistogram, double[]>() { @Override public $ColorHistogram newEntity(double[] value) { $ColorHistogram obj = new $ColorHistogram(); obj.setColors(value); return obj; } @Override public RectangleOneColorHistogram newKey(double[] value) { RectangleOneColorHistogram obj = new RectangleOneColorHistogram(); obj.setColors(value); return obj; } @Override public EntityStructure<$ColorHistogram> getEntityStructure() { return $ColorHistogram.entityStructure; } @Override public KeyStructure<RectangleOneColorHistogram> getKeyStructure() { return RectangleOneColorHistogram.rectangleOneColorHistogramStructure; } } ); colors.getSchemas().add( new Schema<$ColorHistogram, PointOneColorHistogram, double[]>() { @Override public $ColorHistogram newEntity(double[] value) { $ColorHistogram obj = new $ColorHistogram(); obj.setColors(value); return obj; } @Override public PointOneColorHistogram newKey(double[] value) { PointOneColorHistogram obj = new PointOneColorHistogram(); obj.setColors(value); return obj; } @Override public EntityStructure<$ColorHistogram> getEntityStructure() { return $ColorHistogram.entityStructure; } @Override public KeyStructure<PointOneColorHistogram> getKeyStructure() { return PointOneColorHistogram.pointOneColorHistogramStructure; } } ); extension.getSchemas().add( new Schema<$ColorHistogram, RectangleOneColorHistogram, double[]>() { @Override public $ColorHistogram newEntity(double[] value) { $ColorHistogram obj = new $ColorHistogram(); obj.setExtension(value); return obj; } @Override public RectangleOneColorHistogram newKey(double[] value) { RectangleOneColorHistogram obj = new RectangleOneColorHistogram(); obj.setExtension(value); return obj; } @Override public EntityStructure<$ColorHistogram> getEntityStructure() { return $ColorHistogram.entityStructure; } @Override public KeyStructure<RectangleOneColorHistogram> getKeyStructure() { return RectangleOneColorHistogram.rectangleOneColorHistogramStructure; } } ); } } 