package cn.edustar.jitar.model;

import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.GroupArticle;

/**
 * 带有扩展属性 发表用户、系统分类、用户分类、群组文章 的文章对象模型, 此模型对象一般用于管理
 *   其提供的信息可能有敏感数据(如用户实名、IP地址等)，因此在前台调用需要小心
 *
 * 
 * 实际上需要动态语言, 可是什么时候 Java 才能支持呢?
 * 
 */
public class ArticleModelEx extends ArticleModel {
	/**
	 * 对指定的 article 对象进行包装, 并返回包装后的 ArticleModelEx 对象.
	 * @param article
	 * @return
	 */
	public static final ArticleModelEx wrap(Article article) {
		if (article == null) return null;
		return new ArticleModelEx(article);
	}
	
	/**
	 * 对指定的 ArticleModel 对象进行包装, 并返回包装后的 ArticleModelEx 对象.
	 * @param article_model
	 * @return
	 */
	public static final ArticleModelEx wrap(ArticleModel article_model) {
		if (article_model == null) return null;
		if (article_model instanceof ArticleModelEx) 
			return (ArticleModelEx)article_model;
		
		return new ArticleModelEx(article_model._getArticleObject());
	}
	
	/**
	 * 私有构造。
	 * @param article
	 */
	private ArticleModelEx(Article article) {
		super(article);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "ArticleModelEx{article=" + super._getArticleObject() +
			",sys_cate=" + sys_cate +
			",user_cate=" + user_cate +
			",group_article=" + group_article +
			"}";
	}
	
	/** 系统分类对象 */
	private Category sys_cate;
	
	/** 系统分类对象 */
	public void setSystemCategory(Category category) {
		this.sys_cate = category;
	}
	
	/** 系统分类对象 */
	public Category getSystemCategory() {
		return this.sys_cate;
	}
	
	/** 用户分类 */
	private Category user_cate;
	
	/** 用户分类 */
	public void setUserCategory(Category user_cate) {
		this.user_cate = user_cate;
	}
	
	/** 用户分类 */
	public Category getUserCategory() {
		return this.user_cate;
	}
	
	/** 发布本文章时的IP来源。 */
	public String getAddIp() {
		return super._getAddIp();
	}

	/** GroupArticle 对象 */
	private GroupArticle group_article;
	
	/** GroupArticle 对象 */
	public void setGroupArticle(GroupArticle group_article) {
		this.group_article = group_article;
	}
	
	/** GroupArticle 对象 */
	public GroupArticle getGroupArticle() {
		return this.group_article;
	}

	/** 获得是否群组中最佳文章 */
	public boolean getIsGroupBest() {
		return group_article.getIsGroupBest();
	}
}
