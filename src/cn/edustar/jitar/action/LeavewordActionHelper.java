package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.LeaveWord;
import cn.edustar.jitar.service.LeavewordQueryParam;
import cn.edustar.jitar.service.LeavewordService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 留言管理辅助类, 用于嵌入到别的类里面
 * 
 *
 */
public class LeavewordActionHelper {
	/** 留言服务 */
	private LeavewordService lw_svc;
	
	/** 留言服务 */
	public LeavewordService getLeavewordService() {
		return lw_svc;
	}
	
	/** 留言服务 */
	public void setLeavewordService(LeavewordService lw_svc) {
		this.lw_svc = lw_svc;
	}

	/**
	 * 列出留言, 该函数查询留言, 然后将结果放到 action request 里面.
	 */
	public void list(AbstractServletAction action, Pager pager, LeavewordQueryParam param) {
		// 查询留言列表.
		List<LeaveWord> leaveword_list = lw_svc.getLeaveWordList(param, pager);
		
		action.setRequestAttribute("leaveword_list", leaveword_list);
		action.setRequestAttribute("pager", pager);
	}

	/**
	 * 根据参数创建留言查询参数对象.
	 * @return
	 */
	public LeavewordQueryParam createLeavewordQueryParam(ParamUtil param_util) {
		LeavewordQueryParam param = new LeavewordQueryParam();
		return param;
	}
	
	/**
	 * 根据参数创建分页对象.
	 * @param param_util
	 * @return
	 */
	public Pager createPager(ParamUtil param_util) {
		Pager pager = new Pager();
		int page = param_util.safeGetIntParam("page", 1);
		pager.setCurrentPage(page);
		pager.setPageSize(20);
		pager.setItemNameAndUnit("留言", "条");
		pager.setUrlPattern("?cmd=list&page={page}");
		return pager;
	}
}
