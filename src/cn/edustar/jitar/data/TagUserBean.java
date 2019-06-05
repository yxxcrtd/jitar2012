package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.UserQueryParam;

/**
 * 使用指定标签的博客(用户)列表, 缺省 varName = 'tag_user_list'.
 *
 *
 */
public class TagUserBean extends UserBean {
	/** 标签服务 */
	private TagService tag_svc;

	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}

	/**
	 * 构造.
	 */
	public TagUserBean() {
		super.setVarName("tag_user_list");
		this.tag_svc = JitarContext.getCurrentJitarContext().getTagService();
	}
	
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到当前页面的标签.
		Tag tag = (Tag)host.getContextObject("tag");
		if (tag == null) {
			logger.warn("TagUserBean 没有找到环境中绑定的 tag 对象");
			return;
		}
		
		// 构造查询参数.
		UserQueryParam param = super.getQueryParam();
		Pager pager = getUsePager() ? getContextPager(host) : null;
		
		// 得到使用标签的用户列表.
		List<User> user_list = tag_svc.getUserListByTag(
				tag.getTagId(), param, pager);
		
		
		host.setData(getVarName(), user_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
}
