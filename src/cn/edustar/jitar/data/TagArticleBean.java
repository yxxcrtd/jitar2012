package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.TagService;

/**
 * 使用指定标签的文章数据, 缺省 varName = 'tag_article_list'.
 *
 *
 */
public class TagArticleBean extends ArticleBean {
	/** 标签服务 */
	private TagService tag_svc;
	
	/**
	 * 构造.
	 */
	public TagArticleBean() {
		super.setVarName("tag_article_list");
		this.tag_svc = JitarContext.getCurrentJitarContext().getTagService();
	}
	
	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ArticleBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 得到当前页面的标签.
		Tag tag = (Tag)host.getContextObject("tag");
		if (tag == null) {
			logger.warn("TagArticleBean 没有找到环境中绑定的 tag 对象");
			return;
		}
		
		// 得到查询参数.
		ArticleQueryParam param = super.getQueryParam(host);
		Pager pager = getUsePager() ? getContextPager(host) : null;
		List<ArticleModelEx> article_list = tag_svc.getArticleListByTag(
				tag.getTagId(), param, pager);
		
		host.setData(getVarName(), article_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
}
