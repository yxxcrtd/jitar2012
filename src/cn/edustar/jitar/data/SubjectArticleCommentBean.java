package cn.edustar.jitar.data;

import cn.edustar.jitar.pojos.Subject;

/**
 * 指定学科下的文章评论列表, varName = 'subj_art_cmt_list'.
 *
 *
 */
public class SubjectArticleCommentBean extends ArticleCommentBean {
	/**
	 * 构造.
	 */
	public SubjectArticleCommentBean() {
		super.setVarName("subj_art_cmt_list");
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ArticleCommentBean#doPrepareData(cn.edustar.jitar.data.DataHost)
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
