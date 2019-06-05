package cn.edustar.jitar.module;

import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * tag_article, tag_resource 等模块基类.
 *
 *
 */
public abstract class TagModuleBase extends AbstractModuleWithTP {
	/** 标签服务 */
	protected TagService tag_svc;
	
	/**
	 * 构造.
	 * @param moduleName
	 * @param moduleTitle
	 */
	protected TagModuleBase(String moduleName, String moduleTitle) {
		super(moduleName, moduleTitle);
	}
	
	/** 标签服务 */
	public TagService getTagService() {
		return this.tag_svc;
	}
	
	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}
	
	/** 从参数中获得 tag 对象 */
	protected Tag getTag(ModuleRequest request) {
		// 1. 直接在 request 中传递过来则直接使用.
		Tag tag = (Tag)request.getAttribute(ModuleRequest.TAG_MODEL_KEY);
		if (tag != null) return tag;
		
		// 2. 获得参数中 tagId 的
		ParamUtil param_util = new ParamUtil(request.getParameters());
		int tagId = param_util.getIntParam("tagId");
		if (tagId != 0) {
			tag = tag_svc.getTag(tagId);
			if (tag != null) return tag;
		}
		
		// ?? 更多方式.
		
		return null;
	}
}
