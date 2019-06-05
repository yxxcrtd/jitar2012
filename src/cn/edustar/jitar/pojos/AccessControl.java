package cn.edustar.jitar.pojos;

/**
 * @author admin
 *
 */
public class AccessControl implements java.io.Serializable {

	private static final long serialVersionUID = -8490166522684646983L;
	private int accessControlId;
	private int userId;
	private int objectType = 0;
	private int objectId;
	private String objectTitle;
	
	/** 0:未知的权限名称 */
	public static final int OBJECTTYPE_UNKNOW = 0;
	
	/** 1:超级管理员 */
	public static final int OBJECTTYPE_SUPERADMIN = 1;
	/** 2:系统用户管理员 */
	public static final int OBJECTTYPE_SYSTEMUSERADMIN = 2;
	/** 3:系统内容管理员 */
	public static final int OBJECTTYPE_SYSTEMCONTENTADMIN = 3;
	/** 4:机构系统管理员 */
	public static final int OBJECTTYPE_UNITSYSTEMADMIN = 4;
	/** 5:机构用户管理员 */
	public static final int OBJECTTYPE_UNITUSERADMIN = 5;
	
	/** 6:机构内容管理员 */
	public static final int OBJECTTYPE_UNITCONTENTADMIN = 6;
	
	/** 7:学科系统管理员 */
	public static final int OBJECTTYPE_SUBJECTSYSTEMADMIN = 7;
	
	/** 8:学科用户管理员 */
	public static final int OBJECTTYPE_SUBJECTUSERADMIN = 8;
	
	/** 9:学科内容管理员 */
	public static final int OBJECTTYPE_SUBJECTCONTENTADMIN = 9;
	
	/** 10:元学科内容管理员 */
	public static final int OBJECTTYPE_METASUBJECTCONTENTADMIN = 10;
	
	/** 11:频道系统管理员 */
	public static final int OBJECTTYPE_CHANNELSYSTEMADMIN = 11;
	
	/** 12:频道用户管理员 */
	public static final int OBJECTTYPE_CHANNELUSERADMIN = 12;
	
	/** 13:频道内容管理员 */
	public static final int OBJECTTYPE_CHANNELCONTENTADMIN = 13;
	
	/** 14:特定栏目管理员 */
	public static final int OBJECTTYPE_CUSTORMCOLUMNADMIN = 14;	

	public static final String getAccessControlChinese(int typeId){
	    switch (typeId) {
	        case AccessControl.OBJECTTYPE_METASUBJECTCONTENTADMIN:
                return "元学科内容管理员";
            case AccessControl.OBJECTTYPE_SUBJECTCONTENTADMIN:
                return "学科内容管理员";
            case AccessControl.OBJECTTYPE_SUBJECTSYSTEMADMIN:
                return "学科系统管理员";
            case AccessControl.OBJECTTYPE_SUBJECTUSERADMIN:
                return "学科用户管理员";
            case AccessControl.OBJECTTYPE_SUPERADMIN:
                return "超级管理员";
            case AccessControl.OBJECTTYPE_SYSTEMCONTENTADMIN:
                return "系统内容管理员";
            case AccessControl.OBJECTTYPE_SYSTEMUSERADMIN:
                return "系统用户管理员";
            case AccessControl.OBJECTTYPE_UNITCONTENTADMIN:
                return "机构内容管理员";
            case AccessControl.OBJECTTYPE_UNITSYSTEMADMIN:
                return "机构系统管理员";
            case AccessControl.OBJECTTYPE_UNITUSERADMIN:
                return "机构用户管理员";
            case AccessControl.OBJECTTYPE_CHANNELSYSTEMADMIN:
                return "频道系统管理员";
            case AccessControl.OBJECTTYPE_CHANNELUSERADMIN:
                return "频道用户管理员";
            case AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN:
                return "频道内容管理员";
            case AccessControl.OBJECTTYPE_CUSTORMCOLUMNADMIN:
                return "特定栏目管理员";
            default:
                return "未定义的管理权限";
	        }
	}
	
	
	
	// Constructors

	/** default constructor */
	public AccessControl() {
	}

	public int getAccessControlId() {
		return accessControlId;
	}

	public void setAccessControlId(int accessControlId) {
		this.accessControlId = accessControlId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}	

	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getObjectTitle() {
		return objectTitle;
	}

	public void setObjectTitle(String objectTitle) {
		this.objectTitle = objectTitle;
	}
}