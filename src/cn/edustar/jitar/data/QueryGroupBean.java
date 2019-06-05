package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.GroupQueryParam;

/**
 * 通过查询获得群组信息的 bean, 缺省 varName = 'group_list'.
 *
 *
 */
public class QueryGroupBean extends GroupBean {
	/**
	 * 构造.
	 */
	public QueryGroupBean() {
		super.setVarName("group_list");
		super.setUsePager(true);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.GroupBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		GroupQueryParam param = contextQueryParam(host);
		
		String list_type = "所有协作组";
		
		// 根据 type 参数设置不同选择方式, 当前支持 new(缺省), best.
		String type = host.getParameters().safeGetStringParam("type", "new");
		if (type == null || type.length() == 0) type = "new";
		
		if (type == null || type.length() == 0 || "new".equals(type)) {
			// 最新群组.
			param.orderType = GroupQueryParam.ORDER_BY_ID_DESC;
		} else if ("hot".equals(type)) {							// 热门.
			param.orderType = GroupQueryParam.ORDER_BY_VISITCOUNT_DESC;
			list_type = "热门协作组";
		} else if ("rcmd".equals(type)) { 	// 推荐协作组.
			param.isRecommend = Boolean.TRUE;
			list_type = "推荐协作组";
		} else if ("best".equals(type)) {	// 优秀团队.
			param.bestGroup = Boolean.TRUE;
			list_type = "优秀团队";
		}
		
		String k = host.getParameters().safeGetStringParam("k", null);
		if (k != null && k.length() > 0) {
			param.k = k;
			list_type = "查询关键字为 '" + k + "' 的协作组";
		}
		
		host.setData("list_type", list_type);
		host.setData("type", type);

		// 这些查询原本在基类, 放在这里合适吗?? 
		Pager pager = getUsePager() ? getContextPager(host) : null;
		List<Group> group_list = (List<Group>)group_svc.getGroupList(param, pager);
		
		host.setData(getVarName(), group_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.GroupBean#contextQueryParam(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected GroupQueryParam contextQueryParam(DataHost host) {
		GroupQueryParam param = super.contextQueryParam(host);
		
		// 如果有学科对象则设置学科条件.
		Subject subject = (Subject)host.getContextObject("subject");
		if (subject != null) {
			param.useSubjectId = true;
			param.subjectId = subject.getSubjectId();
		}
		
		// 如果有用户对象则设置用户条件.
		User user = (User)host.getContextObject("user");
		if (user != null) {
			param.createUserId = user.getUserId();
		}
		
		// 如果有分类对象则设置系统分类条件.
		Category category = (Category)host.getContextObject("category");
		if (category != null) {
			param.useCategoryId = true;
			param.categoryId = category.getCategoryId();
		}
		
		
		return param;
	}
}
