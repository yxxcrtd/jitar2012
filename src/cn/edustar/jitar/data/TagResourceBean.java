package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.TagService;

/**
 * 使用指定标签的资源列表, 缺省 varName = 'tag_resource_list'.
 *
 *
 */
public class TagResourceBean extends ResourceBean {
	/** 标签服务 */
	private TagService tag_svc;

	/**
	 * 构造.
	 */
	public TagResourceBean() {
		super.setVarName("tag_resource_list");
		this.tag_svc = JitarContext.getCurrentJitarContext().getTagService();
	}
	
	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ResourceBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到当前页面的标签.
		Tag tag = (Tag)host.getContextObject("tag");
		if (tag == null) {
			logger.warn("TagResourceBean 没有找到环境中绑定的 tag 对象");
			return;
		}
		
		// 得到查询参数.
		ResourceQueryParam param = super.contextQueryParam(host);
		Pager pager = getUsePager() ? getContextPager(host) : null;
		List<ResourceModelEx> resource_list = tag_svc.getResourceListByTag(
				tag.getTagId(), param, pager);
		
		host.setData(getVarName(), resource_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
}
