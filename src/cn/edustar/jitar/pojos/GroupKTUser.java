package cn.edustar.jitar.pojos;
import java.io.Serializable;
public class GroupKTUser implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -737741513696936343L;

	/** 此对象的标识 */
	private int id;
	
	/** 群组标识 */
	private int groupId;
	
	/** 课题负责人Id */
	private int teacherId;
	
	/** 课题负责人Name */
	private String teacherName;

	/** 课题负责人性别 */
	private String teacherGender;

	/** 课题负责人单位 */
	private String teacherUnit;

	/** 课题负责人行政职务 */
	private String teacherXZZW;
	
	/** 课题负责人专业职务 */
	private String teacherZYZW;

	/** 课题负责人学历 */
	private String teacherXL;

	/** 课题负责人学位 */
	private String teacherXW;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherGender() {
		return teacherGender;
	}

	public void setTeacherGender(String teacherGender) {
		this.teacherGender = teacherGender;
	}

	public String getTeacherUnit() {
		return teacherUnit;
	}

	public void setTeacherUnit(String teacherUnit) {
		this.teacherUnit = teacherUnit;
	}

	public String getTeacherXZZW() {
		return teacherXZZW;
	}

	public void setTeacherXZZW(String teacherXZZW) {
		this.teacherXZZW = teacherXZZW;
	}

	public String getTeacherZYZW() {
		return teacherZYZW;
	}

	public void setTeacherZYZW(String teacherZYZW) {
		this.teacherZYZW = teacherZYZW;
	}

	public String getTeacherXL() {
		return teacherXL;
	}

	public void setTeacherXL(String teacherXL) {
		this.teacherXL = teacherXL;
	}

	public String getTeacherXW() {
		return teacherXW;
	}

	public void setTeacherXW(String teacherXW) {
		this.teacherXW = teacherXW;
	}

	public String getTeacherYJZC() {
		return teacherYJZC;
	}

	public void setTeacherYJZC(String teacherYJZC) {
		this.teacherYJZC = teacherYJZC;
	}

	public String getTeacherXKXDId() {
		return teacherXKXDId;
	}

	public void setTeacherXKXDId(String teacherXKXDId) {
		this.teacherXKXDId = teacherXKXDId;
	}

	public String getTeacherXKXDName() {
		return teacherXKXDName;
	}

	public void setTeacherXKXDName(String teacherXKXDName) {
		this.teacherXKXDName = teacherXKXDName;
	}

	/** 课题负责人研究专长 */
	private String teacherYJZC;

	/** 课题负责人学科学段 gradeid/subjectid  ,多个之间用逗号分开*/
	private String teacherXKXDId;

	/** 课题负责人学科学段 grade/subject  ,多个之间用逗号分开*/
	private String teacherXKXDName;
}
