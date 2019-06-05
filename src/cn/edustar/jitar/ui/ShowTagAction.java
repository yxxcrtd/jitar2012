package cn.edustar.jitar.ui;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.PythonAction;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.service.TagService;

/**
 * 系统页面, 显示指定标识的标签.
 *
 * URL: showTag.action?tagId=$tagId, ?tagName=$tagName
 * 
 */
public class ShowTagAction extends PythonAction {
	/** serialVersionUID */
	private static final long serialVersionUID = 2621013671245502261L;

	/** 标签服务 */
	private TagService tag_svc;
	
	/** 此页面显示的标签对象 */
	private Tag tag;
	
	/**
	 * 构造.
	 */
	public ShowTagAction() {
		this.tag_svc = JitarContext.getCurrentJitarContext().getTagService();
	}
	
	/** 此页面显示的标签对象 */
	public Tag getTag() {
		return this.tag;
	}
	
	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.PythonAction#beforeExecute()
	 */
	@Override
	protected String beforeExecute() {
		String result = super.beforeExecute();
		if (result != null) return result;
		
		return prepareTag();
	}
	
	/** 准备此页面的 tag 数据 */
	@SuppressWarnings("deprecation")
	private String prepareTag() {
		int tagId = param.getIntParam("tagId");
		String tagName = param.safeGetStringParam("tagName");
		if (tagId != 0)
			this.tag = tag_svc.getTag(tagId);
		else {
			this.tag = tag_svc.getTagByName(tagName);
		}
		if (this.tag == null) {
			super.addActionError("未能找到指定标签 tagId = " + tagId + ", tagName = " + tagName);
			return ERROR;
		}
		
		setData("tag", tag);
		tag_svc.incTagViewCount(tag, 1);
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.AbstractPageAction#getContextObject(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	public Object getContextObject(String name) {
		if ("tag".equals(name))
			return this.tag;
		return super.getContextObject(name);
	}
}
