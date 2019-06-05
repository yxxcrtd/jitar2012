package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.service.TagQueryParam;
import cn.edustar.jitar.service.TagService;

/**
 * 获取所有标签列表的 bean, 缺省 varName = 'tag_list'.
 *
 *
 */
public class AllTagBean extends AbstractDataBean {
	/** 标签服务 */
	private TagService tag_svc;
	
	/**
	 * 构造.
	 */
	public AllTagBean() {
		super.setVarName("tag_list");
		this.tag_svc = JitarContext.getCurrentJitarContext().getTagService();
	}

	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		TagQueryParam param = new TagQueryParam();
		param.count = -1;			// 获得所有.
		param.orderType = -1;		// 随便排序.
		
		List<Tag> tag_list = tag_svc.getTagList(param, null);
		host.setData(getVarName(), tag_list);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doOpen()
	 */
	@Override
	protected Object doOpen() {
		TagQueryParam param = new TagQueryParam();
		param.count = -1;			// 获得所有.
		param.orderType = -1;		// 随便排序.
		
		List<Tag> tag_list = tag_svc.getTagList(param, null);
		return tag_list;
	}
}
