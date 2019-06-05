package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.SubjectService;

/**
 * 获得学科列表.
 *
 *
 */
public class SubjectBean extends AbstractDataBean {
	/** 学科服务 */
	private SubjectService subj_svc;

	/**
	 * 构造.
	 */
	public SubjectBean() {
		super.setVarName("subject_list");
		this.subj_svc = JitarContext.getCurrentJitarContext().getSubjectService();
	}
	
	/** 学科服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.AbstractDataBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		List<Subject> subject_list = subj_svc.getSubjectList();
		host.setData(getVarName(), subject_list);
	}
}
