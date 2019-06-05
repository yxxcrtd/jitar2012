package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.service.GroupQueryParam;
import cn.edustar.jitar.service.TagService;

/**
 * 使用指定标签的群组的数据提供, 缺省 varName = 'tag_group_list'
 *
 *
 */
public class TagGroupBean extends GroupBean {
	/** 标签服务 */
	private TagService tag_svc;
	
	/**
	 * 构造.
	 */
	public TagGroupBean() {
		super.setVarName("tag_group_list");
		this.tag_svc = JitarContext.getCurrentJitarContext().getTagService();
	}
	
	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.GroupBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到当前页面的标签.
		Tag tag = (Tag)host.getContextObject("tag");
		if (tag == null) {
			logger.warn("TagGroupBean 没有找到环境中绑定的 tag 对象");
			return;
		}
		
		// 得到查询参数.
		GroupQueryParam param = getQueryParam();
		Pager pager = getUsePager() ? getContextPager(host) : null;
		
		List<Group> group_list = tag_svc.getGroupListByTag(
				tag.getTagId(), param, pager);
		
		host.setData(getVarName(), group_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}		
}
