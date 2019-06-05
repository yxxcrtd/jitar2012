package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 学段管理
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Jun 25, 2008 9:55:27 PM
 */
public class AdminGradeAction extends ManageBaseAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 4992280893430675904L;

	/**
	 * 学段服务
	 */
	private SubjectService subj_svc;

	/**
	 * 学段服务的set方法
	 *
	 * @param subj_svc
	 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	public String execute(String cmd) throws Exception {

		// 验证权限.
		if (this.canAdmin() == false) {
			this.addActionError("没有管理元学段的权限.");
			return ERROR;
		}

		if (cmd == null || cmd.length() == 0)
			cmd = "list";

		if ("list".equals(cmd))
			return List();
		else if ("add".equals(cmd))
			return add();
		else if ("edit".equals(cmd))
			return edit();
		else if ("save".equals(cmd))
			return save();
		else if ("delete".equals(cmd))
			return delete();

		return super.unknownCommand(cmd);
	}

	/**
	 * 学段列表
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	private String List() throws java.lang.Exception {

		List<Grade> grade_list = subj_svc.getGradeList();
		setRequestAttribute("grade_list", grade_list);
		return "List_Success";
	}

	/**
	 * 添加一个新元学科
	 * 
	 * @return
	 */
	private String add() {
		Grade grade = new Grade();
		setRequestAttribute("grade", grade);
		setRequestAttribute("__referer", getRefererHeader());

		return "Add_Or_Edit";
	}

	/**
	 * 编辑/修改元学科
	 * 
	 * @return
	 */
	private String edit() {
		int gradeId = param_util.safeGetIntParam("gradeId");
		Grade grade = subj_svc.getGrade(gradeId);
		if (grade == null) {
			addActionError("未找到指定标识的元学段");
			return ERROR;
		}

		setRequestAttribute("gradeId", gradeId);
		setRequestAttribute("grade", grade);
		setRequestAttribute("__referer", getRefererHeader());

		return "Add_Or_Edit";
	}

	/**
	 * 保存或修改元学段
	 * 
	 * @return
	 */
	private String save() {

		// 获得学段数据.
		String gradeId = param_util.safeGetStringParam("gradeId");
		String oldGradeId = param_util.safeGetStringParam("oldGradeId");
		String gradeName = param_util.safeGetStringParam("gradeName");

		/*
		 * Pattern pattern = Pattern.compile("[0-9]"); Matcher matcher =
		 * pattern.matcher(gradeId); if (!matcher.matches()) {
		 * this.addActionError("学段代码必需为数字"); return ERROR; }
		 */
		if (gradeId == null || "".equalsIgnoreCase(gradeId)
				|| gradeId.length() == 0) {
			this.addActionError("学段代码不能为空！");
			return add();
		}

		if(CommonUtil.isInteger(gradeId) == false)
		{
			this.addActionError("学段代码不是整数！");
			return add();
		}
		
		
		if (gradeName == null || "".equalsIgnoreCase(gradeName)
				|| gradeName.length() == 0) {
			this.addActionError("学段名称不能为空！");
			return add();
		}
		

		// 保存或修改元学段
		Grade grade = new Grade();
		int gid = Integer.parseInt(gradeId);
		int oldgid = 0;
		if (oldGradeId != "" || oldGradeId.length() != 0) {
			oldgid = Integer.parseInt(oldGradeId);
		}
		grade.setGradeId(gid);
		grade.setGradeName(gradeName);

		if (gid % 1000 == 0) {
			grade.setIsGrade(true);
		} else {
			grade.setIsGrade(false);
		}
		if (oldGradeId != "") {
			subj_svc.updateGrade(grade, oldgid);
		} else {
			subj_svc.saveOrUpdateGrade(grade);
		}

		return SUCCESS;
	}

	/**
	 * 删除一个元学段
	 * 
	 * @return
	 */
	private String delete() {
		// 得到学科对象
		int gradeId = param_util.getIntParam("gradeId");
		Grade grade = subj_svc.getGrade(gradeId);
		if (grade == null) {
			this.addActionError("未找到指定标识的学科");
			return ERROR;
		}
		List<Subject> subject_list = subj_svc.getSubjectByGradeId(gradeId);
		if (subject_list != null) {
			this.addActionError("请先删除与元学段相关联的学科！");
			return ERROR;
		}

		// 删除学科
		subj_svc.deleteGrade(grade);

		return SUCCESS;
	}

}
