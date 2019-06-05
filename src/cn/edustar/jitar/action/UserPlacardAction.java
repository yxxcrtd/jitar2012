package cn.edustar.jitar.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.PlacardQueryParam;
import cn.edustar.jitar.service.PlacardService;
import cn.edustar.jitar.util.PageContent;

/**
 * 个人公告管理.
 * 
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 * 
 */
public class UserPlacardAction extends ManageBaseAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 2702601028335362419L;

	/** 公告服务 */
	private PlacardService pla_svc;

	/** 公告服务 */
	public void setPlacardService(PlacardService pla_svc) {
		this.pla_svc = pla_svc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	public String execute(String cmd) throws Exception {
		// 登录验证.
		if (isUserLogined() == false)
			return LOGIN;
		if (canVisitUser(getLoginUser()) == false)
			return ERROR;

		if (cmd == null || cmd.length() == 0)
			cmd = "list";
		if ("list".equalsIgnoreCase(cmd)) {
			return list();
		} else if ("addPlacard".equalsIgnoreCase(cmd)) {
			return addPlacard();
		} else if ("edit_placard".equalsIgnoreCase(cmd)) {
			return editPlacard();
		} else if ("del_placard".equalsIgnoreCase(cmd)) {
			return delPlacard();
		} else if ("save_placard".equalsIgnoreCase(cmd)) {
			return savePlacard();
		} else if ("hide".equalsIgnoreCase(cmd)) {
			return hidePlacard();
		} else if ("show".equalsIgnoreCase(cmd)) {
			return showPlacard();
		}
		return unknownCommand(cmd);
	}

	/****************************************************************************
	 * 个人公告列表.
	 * 
	 * @return
	 */
	private String list() {
		// 构造查询参数.
		User login_user = super.getLoginUser();
		PlacardQueryParam param = new PlacardQueryParam();
		param.count = -1;
		param.hideState = null;
		param.objType = ObjectType.OBJECT_TYPE_USER;
		param.objId = login_user.getUserId();
		param.orderType = 0;
		List<Placard> placard_list = pla_svc.getPlacardList(param, null);

		setRequestAttribute("user", login_user);
		setRequestAttribute("placard_list", placard_list);

		return LIST_SUCCESS;
	}

	/**
	 * 修改公告.
	 */
	private String editPlacard() {
		int placardId = param_util.safeGetIntParam("placardId");
		Placard placard = pla_svc.getPlacard(placardId);
		if (placard == null) {
			addActionError("未找到标识为 " + placardId + "的公告");
			return ERROR;
		}

		// 权限验证
		if (placard.getObjType() != ObjectType.OBJECT_TYPE_USER.getTypeId()
				|| placard.getObjId() != getLoginUser().getUserId()) {
			addActionError("权限不足,不能编辑他人的公告");
			return ERROR;
		}

		request.setAttribute("placardId", placardId);
		request.setAttribute("placard", placard);

		setRequestAttribute("__referer", getRefererHeader());
		return "Edit_Add_placard";
	}

	/**
	 * 保存公告.
	 */
	private String savePlacard() throws Exception {
		PrintWriter out = response.getWriter();
		// 得到数据
		int placardId = param_util.getIntParam("placardId");

		// 权限验证
		if (placardId > 0) {
			Placard placard = pla_svc.getPlacard(placardId);
			if (placard.getObjType() != ObjectType.OBJECT_TYPE_USER.getTypeId()
					|| placard.getObjId() != getLoginUser().getUserId()) {
				addActionError("权限不足,不能编辑他人的公告");
				return ERROR;
			}
		}

		String redirect = param_util.safeGetStringParam("redirect"); // 返回标识
		String title = param_util.safeGetStringParam("title");
		String content = param_util.safeGetStringParam("placardContent");

		// 验证数据
		if (title == null || title.length() == 0) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('   公告标题不能为空！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}
		if (title != null && title.length() > 255) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('   公告标题长度不能大于255！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}
		if (content == null && content.length() == 0) {

			out.append(PageContent.PAGE_UTF8);
			out.println("<script>alert('   公告内容不能为空！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}

		// 给bean赋值.
		Placard placard = new Placard();
		User login_user = super.getLoginUser();

		placard.setId(placardId);
		placard.setTitle(title);
		placard.setContent(content);
		placard.setCreateDate(new Date());
		placard.setObjType(ObjectType.OBJECT_TYPE_USER.getTypeId());
		placard.setObjId(login_user.getUserId());

		// 保存公告.
		pla_svc.savePlacard(placard);

		// redirect:页面返回标识.
		if (!("".equals(redirect))) {
			response.sendRedirect(super.getRefererHeader());
			return NONE;
		}
		addActionMessage("公告 '" + placard.getTitle() + "' 已保存");
		return SUCCESS;
	}

	/**
	 * 删除公告.
	 */
	private String delPlacard() {
		User login_user = super.getLoginUser();
		List<Integer> ids = param_util.safeGetIntValues("placard");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择要删除的公告");
			return ERROR;
		}
		int oper_count = 0;
		for (Integer id : ids) {
			Placard placard = pla_svc.getPlacard(id);
			if (placard == null) {
				addActionError("未找到标识为 " + id + " 的公告");
				continue;
			}

			// 权限验证.
			if (placard.getObjType() != ObjectType.OBJECT_TYPE_USER.getTypeId() || placard.getObjId() != login_user.getUserId()) {
				addActionError("权限不足,不能删除他(她)人的公告");
				continue;
			}

			// 删除公告.
			pla_svc.deletePlacard(placard);
			addActionMessage("公告 " + placard.getTitle() + " 已删除");
			++oper_count;
		}
		addActionMessage("共删除了 " + oper_count + " 条公告");

		return SUCCESS;
	}

	/**
	 * 添加公告.
	 */
	private String addPlacard() {
		Placard placard = new Placard();
		setRequestAttribute("placard", placard);
		setRequestAttribute("__referer", getRefererHeader());
		return "Edit_Add_placard";
	}

	/**
	 * 隐藏公告.
	 * 
	 * @return
	 */
	private String hidePlacard() {
		User login_user = super.getLoginUser();
		List<Integer> ids = param_util.safeGetIntValues("placard");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择要隐藏的公告");
			return ERROR;
		}
		int oper_count = 0;
		for (Integer id : ids) {
			Placard placard = pla_svc.getPlacard(id);
			if (placard == null) {
				addActionError("未找到标识为" + id + "的公告");
				continue;
			}

			// 权限验证.
			if (placard.getObjType() != ObjectType.OBJECT_TYPE_USER.getTypeId() || placard.getObjId() != login_user.getUserId()) {
				addActionError("权限不足,不能操作他人的公告");
			}

			pla_svc.hidePlacard(placard);
			addActionMessage("公告 " + placard.getTitle() + " 已隐藏");
			++oper_count;
		}
		addActionMessage("共隐藏了 " + oper_count + " 条公告");
		return SUCCESS;
	}

	/**
	 * 显示公告.
	 * 
	 * @return
	 */
	private String showPlacard() {
		User login_user = super.getLoginUser();
		List<Integer> ids = param_util.safeGetIntValues("placard");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择要显示的公告");
			return ERROR;
		}

		int oper_count = 0;
		for (Integer id : ids) {
			Placard placard = pla_svc.getPlacard(id);
			if (placard == null) {
				addActionError("未找到标识为" + placard.getId() + "的公告");
				continue;
			}

			// 权限验证
			if (placard.getObjType() != ObjectType.OBJECT_TYPE_USER.getTypeId() || placard.getObjId() != login_user.getUserId()) {
				addActionError("权限不足,不能操作他人的公告");
			}
			pla_svc.showPlacard(placard);
			addActionMessage("公告 " + placard.getTitle() + " 已隐藏");
			++oper_count;
		}
		addActionMessage("共显示了" + oper_count + "条公告");
		return SUCCESS;
	}
	
}
