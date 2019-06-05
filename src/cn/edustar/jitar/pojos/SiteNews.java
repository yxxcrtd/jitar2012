package cn.edustar.jitar.pojos;

import java.io.Serializable;

/**
 * 站点级新闻或学科级新闻的实体对象, 一般用于主页上表现教研动态.
 *
 *
 */
public class SiteNews extends News implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = -3296618800075053831L;
	
	/** 学科标识, 如果 = 0 表示是站点级的新闻 */
	private int subjectId;

	/** 学科标识, 如果 = 0 表示是站点级的新闻 */
	public int getSubjectId() {
		return subjectId;
	}

	/** 学科标识, 如果 = 0 表示是站点级的新闻 */
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
}
