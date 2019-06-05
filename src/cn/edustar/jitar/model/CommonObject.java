package cn.edustar.jitar.model;

/**
 * @author 孟宪会
 *
 */
public class CommonObject {
	
	/** 对象类型 */
	private String objectType;
	
	/** 对象标识 */
	private String objectGuid;

	public final static String OBJECT_USER = "user";
	public final static String OBJECT_GROUP = "group";
	public final static String OBJECT_NETCOURSE = "netcourse";
	public final static String OBJECT_PREPARECOURSE = "preparecourse";
	public final static String OBJECT_SPECIALSUBJECT = "specialsubject";
	public final static String OBJECT_SYSTEM = "system";
	public final static String OBJECT_UNIT = "unit";
	public final static String OBJECT_SUBJECT = "subject";
	
	public CommonObject()
	{
	}

	public CommonObject(String objectType, String objectGuid) {
		this.objectType = objectType;
		this.objectGuid = objectGuid;
	}
	
	public String getObjectType() {
		return objectType;
	}

	public void setObjectTyep(String objectType) {
		this.objectType = objectType;
	}


	public String getObjectGuid() {
		return objectGuid;
	}

	public void setObjectGuid(String objectGuid) {
		this.objectGuid = objectGuid;
	}

}
