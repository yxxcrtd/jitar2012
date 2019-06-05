package cn.edustar.jitar.pojos;

import java.io.Serializable;

import cn.edustar.jitar.util.CommonUtil;

/**
 * 资源实体对象
 * 
 *
 */
public class Resource extends Document implements Serializable, Cloneable {
	/** serialVersionUID */
	private static final long serialVersionUID = 8162530209169750185L;
	
	/** 资源共享模式: 完全共享 = 1000 */
	public static final int SHARE_MODE_FULL = 1000;
	/** 资源共享模式: 社区共享(登录后可见, 当前不使用) = 800 */
	public static final int SHARE_MODE_LOGIN = 800;
	/** 资源共享模式: 群组共享(在群组中可见) = 500 */
	public static final int SHARE_MODE_GROUP = 500;
	/** 资源共享模式: 好友可见(当前不使用) = 300 */
	public static final int SHARE_MODE_FRIEND = 300;
	/** 资源共享模式: 私有 = 0 */
	public static final int SHARE_MODE_PRIVATE = 0;
	
	/** 资源所属的用户分类 */
	private Integer userCateId;
	
	/** 资源所属的系统分类 */
	private Integer sysCateId;
	
	/** 删除状态：false - 正常，true - 已删除（在回收站中）。 */
	private boolean delState;
	
	
	/** 推荐状态: false - 未推荐; true - 推荐 */
	private boolean recommendState;

	/** 下载次数 */
	private int downloadCount;
	
	/** 共享方式 */
	private int shareMode = SHARE_MODE_FULL;
	
	/** 资源类型, 关联到对象 resType */
	private Integer resTypeId;
	
	/** 资源作者 */
	private String author;
	
	/** 出版社 */
	private String publisher;
	
	/** TODO: 资源版本 */
	// private String version;
	
	/** 资源链接地址, 可能链接到外部地址; 在教研系统中给出的路径必须相对于 ContextPath, 如 'user/admin/resource/a.zip' */
	private String href = "";
	
	/** 资源大小, 单位为字节. */
	private int fsize;
	
	/** 添加/上传资源人的 ip 地址 */
	private String addIp;
	
	/** 学段Id */
	private Integer gradeId;

	/** 是否已发布到资源库  */
	private boolean publishToZyk;
	
	/** 推送状态 */
	private int pushState;
	private Integer pushUserId;
	
	private int unitId;
	private String unitPathInfo;
	private String orginPathInfo;
	private String approvedPathInfo;
	private String rcmdPathInfo;
		
	
	/**
	 * 缺省构造
	 */
	public Resource() {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Resource{id=" + getId() + 
			",title=" + getTitle() +
			",user=" + getUserId() +
			"}";
	}
	
	/**
	 * 得到友好显示的字符串.
	 * @return
	 */
	public String toDisplayString() {
		return "'" + CommonUtil.htmlEncode(this.getTitle()) + "'(id=" + this.getResourceId() + ")";
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return PojoHelper.hashCode(this.getObjectUuid());
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null || !(o instanceof Resource)) return false;
		
		Resource other = (Resource)o;
		return this.getObjectUuid().equalsIgnoreCase(other.getObjectUuid());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Resource clone() throws CloneNotSupportedException {
		return (Resource)super.clone();
	}
	
	/** 资源所属的用户分类 */
	public Integer getUserCateId() {
		return this.userCateId;
	}

	/** 资源所属的用户分类 */
	public void setUserCateId(Integer userCateId) {
		this.userCateId = userCateId;
	}

	/** 资源所属的系统分类 */
	public Integer getSysCateId() {
		return this.sysCateId;
	}

	/** 资源所属的系统分类 */
	public void setSysCateId(Integer sysCateId) {
		this.sysCateId = sysCateId;
	}
	
	/** 删除状态：false - 正常，true - 已删除（在回收站中）。 */
	public boolean getDelState() {
		return this.delState;
	}

	/** 删除状态：false - 正常，true - 已删除（在回收站中）。 */
	public void setDelState(boolean delState) {
		this.delState = delState;
	}
	
	/** 推荐状态: false - 未推荐; true - 推荐 */
	public boolean getRecommendState() {
		return this.recommendState;
	}

	/** 推荐状态: false - 未推荐; true - 推荐 */
	public void setRecommendState(boolean recommendState) {
		this.recommendState = recommendState;
	}

	/** 资源标识 == id */
	public int getResourceId() {
		return super.getId();
	}
	
	/** 资源标识 == id */
	public void setResourceId(int id) {
		super.setId(id);
	}
	
	/** 下载次数 */
	public int getDownloadCount() {
		return downloadCount;
	}

	/** 下载次数 */
	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	/** 共享方式 */
	public int getShareMode() {
		return this.shareMode;
	}
	
	/** 共享方式 */
	public void setShareMode(int shareMode) {
		this.shareMode = shareMode;
	}

	/** 资源类型, 关联到对象 resType */
	public Integer getResTypeId() {
		return this.resTypeId;
	}
	
	/** 资源类型, 关联到对象 resType */
	public void setResTypeId(Integer val) {
		this.resTypeId = val;
	}
	
	/** 资源作者 */
	public String getAuthor() {
		return author;
	}

	/** 资源作者 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/** 出版社 */
	public String getPublisher() {
		return publisher;
	}

	/** 出版社 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/** 资源链接地址, 可能链接到外部地址; 在教研系统中给出的路径必须相对于 ContextPath, 如 'user/admin/resource/a.zip' */
	public String getHref() {
		return href;
	}

	/** 资源链接地址, 可能链接到外部地址; 在教研系统中给出的路径必须相对于 ContextPath, 如 'user/admin/resource/a.zip' */
	public void setHref(String href) {
		this.href = href;
	}

	/** 资源大小, 单位为字节. */
	public int getFsize() {
		return fsize;
	}

	/** 资源大小, 单位为字节. */
	public void setFsize(int fsize) {
		this.fsize = fsize;
	}

	/** 添加/上传资源人的 ip 地址 */
	public String getAddIp() {
		return this.addIp;
	}

	/** 添加/上传资源人的 ip 地址 */
	public void setAddIp(String addIp) {
		this.addIp = addIp;
	}

	/** 学段Id, 可能为 null */
	public Integer getGradeId() {
		return gradeId;
	}

	/** 学段Id, 可能为 null */
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
		this.setExtGradeId(gradeId);
		
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Subject subject;
	Subject getSubject() {
		return this.subject;
	}
	void setSubject(Subject subject) {
		// this.subject = subject;
	}
	
	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Category sysCate;
	Category getSysCate() {
		return this.sysCate;
	}
	void setSysCate(Category sysCate) {
		// this.sysCate = sysCate;
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Category userCate;
	Category getUserCate() {
		return this.userCate;
	}
	void setUserCate(Category userCate) {
		// this.userCate = sysCate;
	}
	
	
	/** Hibernate 映射所需字段, 外面不需要访问 */
	private Grade grade;
	Grade getGrade() {
		return this.grade;
	}
	void setGrade(Grade grade) {
		// this.grade = grade;
	}

	/** Hibernate 映射所需字段, 外面不需要访问 */
	private ResType resType;
	ResType getResType() {
		return this.resType;
	}
	void setResType(ResType resType) {
		// this.resType = resType;
	}

	public boolean getPublishToZyk() {
		return publishToZyk;
	}

	public void setPublishToZyk(boolean publishToZyk) {
		this.publishToZyk = publishToZyk;
	}

	public int getPushState() {
		return pushState;
	}

	public void setPushState(int pushState) {
		this.pushState = pushState;
	}

	public Integer getPushUserId() {
		return pushUserId;
	}

	public void setPushUserId(Integer pushUserId) {
		this.pushUserId = pushUserId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getUnitPathInfo() {
		return unitPathInfo;
	}

	public void setUnitPathInfo(String unitPathInfo) {
		this.unitPathInfo = unitPathInfo;
	}

	public String getOrginPathInfo() {
		return orginPathInfo;
	}

	public void setOrginPathInfo(String orginPathInfo) {
		this.orginPathInfo = orginPathInfo;
	}

	public String getApprovedPathInfo() {
		return approvedPathInfo;
	}

	public void setApprovedPathInfo(String approvedPathInfo) {
		this.approvedPathInfo = approvedPathInfo;
	}

	public String getRcmdPathInfo() {
		return rcmdPathInfo;
	}

	public void setRcmdPathInfo(String rcmdPathInfo) {
		this.rcmdPathInfo = rcmdPathInfo;
	}

}
