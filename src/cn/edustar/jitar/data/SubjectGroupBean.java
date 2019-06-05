package cn.edustar.jitar.data;

import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.GroupQueryParam;

/**
 * 列出指定学科的协作组的 bean, 缺省 varName = 'subj_group_list'.
 *
 *
 */
public class SubjectGroupBean extends GroupBean {
	public SubjectGroupBean() {
		super.setVarName("subj_group_list");
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.GroupBean#contextQueryParam(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected GroupQueryParam contextQueryParam(DataHost host) {
		GroupQueryParam param = super.contextQueryParam(host);
		Subject subject = (Subject)host.getContextObject("subject");
		if (subject != null) {
			param.subjectId = subject.getSubjectId();
			param.useSubjectId = true;
		}
		return param;
	}
}
