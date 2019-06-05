package cn.edustar.jitar.data;

import cn.edustar.jitar.pojos.Subject;

/**
 * 指定学科具有特定用户条件的文章列表, 缺省 varName = 'subj_user_article_list'.
 *
 *
 */
public class SubjectUserArticleBean extends UserArticleBean {
	public SubjectUserArticleBean() {
		super.setVarName("subj_user_article_list");
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ArticleBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		// 获得当前要显示的学科对象, 强制文章学科为 subject.
		Subject subject = (Subject)host.getContextObject("subject");
		setUseSubjectId("true");
		setSubjectId(String.valueOf(subject.getSubjectId()));
		
		// 查找文章.
		super.doPrepareData(host);
	}
}
