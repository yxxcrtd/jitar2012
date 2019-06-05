package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.LeaveWord;
import cn.edustar.jitar.service.LeavewordQueryParam;
import cn.edustar.jitar.service.LeavewordService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 留言获取的 bean.
 *
 *
 */
public class LeaveWordBean extends AbstractPageDataBean {
	/** 留言服务 */
	private LeavewordService lw_svc;
	
	/** 查询参数 */
	private LeavewordQueryParam param = newLeavewordQueryParam();
	
	/**
	 * 构造.
	 */
	public LeaveWordBean() {
		this.lw_svc = JitarContext.getCurrentJitarContext().getLeavewordService();
		super.setVarName("leaveword_list");
		super.setItemName("留言");
		super.setItemUnit("条");
	}
	
	/**
	 * 构造新的 LeavewordQueryParam.
	 * @return
	 */
	protected LeavewordQueryParam newLeavewordQueryParam() {
		return new LeavewordQueryParam();
	}
	
	/** 留言服务 */
	public void setLeavewordService(LeavewordService lw_svc) {
		this.lw_svc = lw_svc;
	}

	/** 获取多少条留言, 仅当不使用 pager 参数的时候有效, 缺省 = 20 */
	public void setCount(int count) {
		param.count = count;
	}
	
	/** 发表留言的人标识, <b>缺省</b> = null 表示不限定 */
	public void setUserId(String userId) {
		param.userId = ParamUtil.safeParseIntegerWithNull(userId);
	}

	/** 被留言的对象类型, 缺省 == null 表示不限定 */
	public void setObjType(String objType) {
		param.objType = ParamUtil.safeParseIntegerWithNull(objType);
	}
	
	/** 被留言的对象标识, 缺省 == null 表示不限定 */
	public void setObjId(String objId) {
		param.objId = ParamUtil.safeParseIntegerWithNull(objId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		LeavewordQueryParam param = getQueryParam(host);
		Pager pager = getUsePager() ? getContextPager(host) : null;
		List<LeaveWord> leaveword_list = lw_svc.getLeaveWordList(param, pager);
		
		host.setData(getVarName(), leaveword_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}

	/**
	 * 得到查询参数.
	 * @return
	 */
	public LeavewordQueryParam getQueryParam(DataHost host) {
		// 群组.
		Group group = (Group)host.getContextObject("group");
		if (group != null) {
			param.objId = group.getGroupId();
			param.objType = ObjectType.OBJECT_TYPE_GROUP.getTypeId();
		}
		
		// TODO: ? 用户.
			
		return this.param;
	}
}
