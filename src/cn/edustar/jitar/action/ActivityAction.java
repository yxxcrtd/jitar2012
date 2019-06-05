package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.ActionQuery;

/**
 * 活动 课程
 * 
 * @author renliang
 */
public class ActivityAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6168373478740439785L;
	private BaseSubject baseSubject = null;
	private String templateName = null;

	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		get_action_list();
		templateName = "template1";
		if (baseSubject.getSubject().getTemplateName() != null) {
			templateName = baseSubject.getSubject().getTemplateName();
		}
		request.setAttribute("subject", baseSubject.getSubject());
		request.setAttribute("head_nav", "activity");
		return templateName;
	}

	private void get_action_list() {
		String showtype = params.getStringParam("type");
		request.setAttribute("type", showtype);

		Pager pager = params.createPager();
		pager.setItemName("活动");
		pager.setItemUnit("个");
		pager.setPageSize(20);
		ActionQuery qry = new ActionQuery(
				"act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,act.status, act.visibility, act.attendCount,u.loginName,u.trueName");
		
		qry.setOwnerType("subject");
		qry.setOwnerId(baseSubject.getSubject().getSubjectId());
		qry.setK(request.getParameter("k"));
		if (showtype.trim().equals("running")) {
			// #正在进行
			qry.setQryDate(1);
		} else if (showtype.trim().equals("finish")) {
			// #已经完成的活动
			qry.setQryDate(2);
		} else if (showtype.trim().equals("new")) {
			// #正在报名的活动
			qry.setQryDate(0);
		}
		pager.setTotalRows(qry.count());
		// action_list =
		request.setAttribute("action_list", qry.query_map(pager));
		request.setAttribute("pager", pager);
	}
}
