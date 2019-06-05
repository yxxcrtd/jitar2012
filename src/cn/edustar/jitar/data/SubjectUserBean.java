package cn.edustar.jitar.data;

import cn.edustar.jitar.pojos.Subject;

/**
 * 列出指定学科的博客(用户), 缺省 varName = 'subj_user_list'.
 *
 *
 */
public class SubjectUserBean extends UserBean {
	public SubjectUserBean() {
		super.setVarName("subj_user_list");
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ArticleBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 获得当前要显示的学科对象, 强制查找的用户所属学科为 subject.
		Subject subject = (Subject)host.getContextObject("subject");
		setUseSubjectId("true");
		setSubjectId(String.valueOf(subject.getSubjectId()));
		
		// 查找文章.
		super.doPrepareData(host);
	}
}
