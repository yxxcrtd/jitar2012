package cn.edustar.jitar.ui;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.PythonAction;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.SubjectService;

/**
 * 显示一个学科的页面.
 *
 *
 */
public class ShowSubjectAction extends PythonAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7595376399064208467L;
	
	/** 学科服务 */
	private SubjectService subj_svc;
	
	/**
	 * 构造.
	 */
	public ShowSubjectAction() {
		this.subj_svc = JitarContext.getCurrentJitarContext().getSubjectService();
	}
	
	/** 学科服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.PythonAction#beforeExecute()
	 */
	@Override
	protected String beforeExecute() {
		String result = super.beforeExecute();
		if (result != null) return result;
		
		return prepareSubject();
	}
	
	/**
	 * 准备要显示的分类对象.
	 * @param param_util
	 */
	@SuppressWarnings("deprecation")
	private String prepareSubject() {
		int subjectId = param.getIntParam("subjectId");
		Subject subject = subj_svc.getSubjectById(subjectId);
		
		if (subject == null) {
			addActionError("未找到指定标识的学科, subjectId = " + subjectId);
			return ERROR;
		}
		super.setSubject(subject);
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.action.AbstractPageAction#getContextObject(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	public Object getContextObject(String name) {
		if ("subject".equals(name))
			return super.getSubject();
		return super.getContextObject(name);
	}
}
