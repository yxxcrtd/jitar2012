package cn.edustar.jitar.model;

/**
 * 能够提供 ObjectType, id 信息的模型对象
 * 
 *
 */
public interface TypedModelObject extends ModelObject {
	
	/**
	 * 得到对象标识
	 * 
	 * @return
	 */
	public int getObjectId();

	/**
	 * 得到此对象的对象类型
	 * 
	 * @return
	 */
	public ObjectType getObjectType();
	
}
