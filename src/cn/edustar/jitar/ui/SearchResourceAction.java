package cn.edustar.jitar.ui;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.action.AbstractPageAction;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.util.ParamUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * 搜索资源列表.
 *
 *
 */
@SuppressWarnings("serial")
public class SearchResourceAction extends AbstractPageAction {
	/** 资源服务 */
	private ResourceService res_svc;

	/** 参数对象 */
	private ParamUtil param_util;
	
	/** 资源服务 */
	public void setResourceService(ResourceService res_svc) {
		this.res_svc = res_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute(String cmd) throws Exception {
		this.param_util = new ParamUtil(ActionContext.getContext().getParameters());

		// 执行搜索.
		processSearch();
		
		super.prepareData();
		
		return SUCCESS;
	}
	
	// 也许把这个处理放到 dataBean 里面更合适.
	private void processSearch() {
		// 构造查询参数.
		ResourceQueryParam param = new ResourceQueryParam();
		param.sysCateId = param_util.getIntParamZeroAsNull("sc");
		param.k = param_util.safeGetStringParam("k", "");
		
		// 构造分页参数.
		Pager pager = new Pager();
		int page = param_util.safeGetIntParam("page", 1);
		pager.setCurrentPage(page);
		pager.setPageSize(20);
		pager.setItemNameAndUnit("资源", "个");
		pager.setUrlPattern(param_util.generateUrlPattern());
		
		List<ResourceModelEx> resource_list = res_svc.getResourceList(param, pager);
		
		setData("resource_list", resource_list);
		setData("pager", pager);
	}
}
