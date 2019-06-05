package cn.edustar.jitar.model;

import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 文章对象模型包装.
 * 
 *
 *
 */
public class ArticleModel extends DocumentModel<Article>
		implements ModelObject, TypedModelObject {
	/**
	 * 包装一个 article 对象.
	 */
	public static ArticleModel wrap(Article article) {
		return new ArticleModel(article);
	}
	
	/**
	 * 
	 * @param article
	 */
	protected ArticleModel(Article article) {
		super(article);
	}

	/** 对象类型，= ObjectType.OBJECT_TYPE_ARTICLE */
	public static final ObjectType OBJECT_TYPE = ObjectType.OBJECT_TYPE_ARTICLE;
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.model.TypedModelObject#getObjectId()
	 */
	public int getObjectId() {
		return document.getArticleId();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.model.TypedModelObject#getObjectType()
	 */
	public ObjectType getObjectType() {
		return ObjectType.OBJECT_TYPE_ARTICLE;
	}

	/**
	 * 得到包装起来的实际的文章对象。
	 * @return
	 */
	public Article _getArticleObject() {
		return this.document;
	}
	
	/** 得到文章标识。 */
	public int getArticleId() {
		return document.getArticleId();
	}
	
	
	/** 博客文章内容。 */
	public String getArticleContent() {
		return document.getArticleContent();
	}
	
	/** 文章摘要。 */
	public String getArticleAbstract() {
		return document.getArticleAbstract();
	}
	
	/** 此文章的标签 */
	public String getArticleTags() {
		return document.getArticleTags();
	}
	
	/** 所属学科 */
	public Integer getSubjectId() {
		return document.getSubjectId();
	}
	
	/** 文章个人分类标识，可能为 null。 */
	public Integer getUserCateId() {
		return document.getUserCateId();
	}
	
	/** 文章系统分类标识，可能为 null。 */
	public Integer getSysCateId() {
		return document.getSysCateId();
	}
	
	/** 文章是否是隐藏状态, 0 - 表示不隐藏，1 - 表示隐藏。 */
	public short getHideState() {
		return document.getHideState();
	}
	
	/** 文章置顶状态：false - 不置顶，true - 置顶 */
	public boolean getTopState() {
		return document.getTopState();
	}
	
	/** 文章精华状态：false - 普通，true - 精华。 */
	public boolean getBestState() {
		return document.getBestState();
	}
	
	/** 文章草稿状态：false - 正常，true - 草稿。 */
	public boolean getDraftState() {
		return document.getDraftState();
	}
	
	/** 文章删除状态：false - 正常，true - 已删除（在回收站中）。 */
	public boolean getDelState() {
		return document.getDelState();
	}

	/** 文章的原创和转载状态，0 原创；1 转载 */
	public boolean getTypeState() {
		return document.getTypeState();
	}
	
	/** 是否推荐状态： false - 未推荐，true - 推荐。 */
	public boolean getRecommendState() {
		return document.getRecommendState();
	}
	
	/** 文章允许评论状态：false - 不允许评论，true - 允许评论，缺省为 true。 */
	public boolean getCommentState() {
		return document.getCommentState();
	}
	
	
	
	/** 发布本文章时的IP来源。 */
	public String _getAddIp() {
		return document.getAddIp();
	}

	/**
	 * 得到被清除掉 html tag 的部分文章简介或文章内容.
	 * @return
	 */
	public String getAbstractForManage() {
		String t = _getAbstractHtmlErased();
		if (t == null) return "";
		
		if (t.length() < 216) return t;
		return t.substring(0, 216) + "...";
	}
	
	private String _getAbstractHtmlErased() {
		if (document.getArticleAbstract() == null)
			return CommonUtil.eraseHtml(document.getArticleContent());
		if (document.getArticleAbstract().trim().length() == 0)
			return CommonUtil.eraseHtml(document.getArticleContent());
		
		return CommonUtil.eraseHtml(document.getArticleAbstract());
	}
}
