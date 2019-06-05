package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.service.TagQueryParam;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 按照指定条件列表标签的数据 bean, 缺省 varName = 'tag_list'.
 *
 *
 */
public class TagBean extends AbstractDataBean {
	/** 标签服务 */
	private TagService tag_svc;
	
	/** 查询参数 */
	private TagQueryParam param = new TagQueryParam();
	
	/**
	 * 构造.
	 */
	public TagBean() {
		super.setVarName("tag_list");
		this.tag_svc = JitarContext.getCurrentJitarContext().getTagService();
	}
	
	/** 查询数量, 当未指定分页参数时候生效 */
	public void setCount(int count) {
		param.count = count;
	}
	
	/** 是否获取被禁用的, 缺省 = false 表示获取非禁用的; null 表示不限定; true 表示获取被禁用的 */
	public void setTagDisabled(String disabled) {
		param.disabled = ParamUtil.safeParseBooleanWithNull(disabled, null);
	}
	
	/** 排序方式, 缺省 = 0 为按照 id 逆序排列, = 1 按照 refCount 逆序排列, =2 按照 viewCount 逆序排列 */
	public void setOrderType(int orderType) {
		param.orderType = orderType;
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
		TagQueryParam param = getQueryParam();
		List<Tag> tag_list = tag_svc.getTagList(param, null);
		
		host.setData(getVarName(), tag_list);
	}

	public TagQueryParam getQueryParam() {
		return this.param;
	}
}
