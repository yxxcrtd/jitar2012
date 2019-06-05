package cn.edustar.jitar.model;

import cn.edustar.jitar.pojos.Resource;

/**
 * 资源被包装起来的模型, 其实现在用途不大了
 */
public class ResourceModel extends DocumentModel<Resource> implements ModelObject, TypedModelObject {
	
	/**
	 * 包装一个 resource 对象.
	 */
	public static ResourceModel wrap(Resource resource) {
		return new ResourceModel(resource);
	}

	/**
	 * 构造, 不能从外部调用
	 * 
	 * @param article
	 */
	protected ResourceModel(Resource resource) {
		super(resource);
	}

	/** 对象类型，= ObjectType.OBJECT_TYPE_ARTICLE */
	public static final ObjectType OBJECT_TYPE = ObjectType.OBJECT_TYPE_RESOURCE;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.model.TypedModelObject#getObjectId()
	 */
	public int getObjectId() {
		return document.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.model.TypedModelObject#getObjectType()
	 */
	public ObjectType getObjectType() {
		return OBJECT_TYPE;
	}

	/**
	 * 得到原始被包装的 Resource 对象.
	 * 
	 * @return
	 */
	public Resource _getResourceObject() {
		return document;
	}

	/** 获得资源标识 */
	public int getResourceId() {
		return document.getResourceId();
	}

	/** 资源所属的用户分类 */
	public Integer getUserCateId() {
		return document.getUserCateId();
	}

	/** 资源所属的系统分类 */
	public Integer getSysCateId() {
		return document.getSysCateId();
	}

	/** 下载次数 */
	public int getDownloadCount() {
		return document.getDownloadCount();
	}

	/** 资源作者 */
	public String getAuthor() {
		return document.getAuthor();
	}

	/** 出版社 */
	public String getPublisher() {
		return document.getPublisher();
	}

	/** 资源链接地址, 可能链接到外部地址 */
	public String getHref() {
		return document.getHref();
	}

	/** 资源大小, 单位为字节. */
	public int getFsize() {
		return document.getFsize();
	}

	/** 添加/上传资源人的 ip 地址 */
	public String getAddIp() {
		return document.getAddIp();
	}

	/** 共享方式 */
	public int getShareMode() {
		return document.getShareMode();
	}

	/** 资源类型, 关联到对象 resType */
	public Integer getResTypeId() {
		return document.getResTypeId();
	}

	/** 删除状态：false - 正常，true - 已删除（在回收站中）。 */
	public boolean getDelState() {
		return document.getDelState();
	}

	/** 推荐状态: false - 未推荐; true - 推荐 */
	public boolean getRecommendState() {
		return document.getRecommendState();
	}

	/** 所属学科, 可以为 null */
	public Integer getSubjectId() {
		return document.getSubjectId();
	}

	/** 学段Id, 可能为 null */
	public Integer getGradeId() {
		return document.getGradeId();
	}

	/** 是否发布到资源库 */
	public boolean getPublishToZyk() {
		return document.getPublishToZyk();
	}

}
