package cn.edustar.jitar.data;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.QueryParam;
import cn.edustar.jitar.service.UserQueryParam;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 选择某种特定人群的文章数据获取, 缺省 varName = 'user_article_list'.
 *
 *
 */
public class UserArticleBean extends ArticleBean {
	/** 用户条件 */
	private UserQueryParam userQueryParam = new UserQueryParam();
	
	/** 要查询的用户登录名, 缺省 = null 表示不限定. */
	public void setLoginName(String loginName) {
		userQueryParam.loginName = loginName;
	}
	
	/** 要查询的用户所属学科标识 */
	public void setUserSubjectId(String userSubjectId) {
		userQueryParam.subjectId = ParamUtil.safeParseIntegerWithNull(userSubjectId, null);
	}
	
	/** 是否限定 subjectId 条件, 缺省 = false 表示不限制 */
	public void setUserUseSubjectId(boolean val) {
		userQueryParam.useSubjectId = val;
	}
	
	/** 是否是名师 */
	public void setUserTypeId(Integer userTypeId) {
		userQueryParam.userTypeId = userTypeId;
	}	
	
	/**
	 * 构造.
	 */
	public UserArticleBean() {
		super.setVarName("user_article_list");
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.data.ArticleBean#doPrepareData(cn.edustar.jitar.data.DataHost)
	 */
	@Override
	protected void doPrepareData(DataHost host) {
		UserArticleQueryParam param = getUserArticleQueryParam(host);
		Pager pager = getUsePager() ? super.getContextPager(host) : null; 
		List<ArticleModelEx> article_list = art_svc.getArticleList(param, pager);
		
		host.setData(getVarName(), article_list);
		if (getUsePager())
			host.setData(getPagerName(), pager);
	}
	
	/** 得到查询参数. */
	private UserArticleQueryParam getUserArticleQueryParam(DataHost host) {
		ArticleQueryParam old_param = super.getQueryParam(host);
		
		UserArticleQueryParam param = new UserArticleQueryParam();
		param.copyFrom(old_param);
		param.userQueryParam = this.userQueryParam;
		
		return param;
	}

	/**
	 * 附加用户属性的查询.
	 */
	private static class UserArticleQueryParam extends ArticleQueryParam implements QueryParam {
		public ArticleQueryParam articleQueryParam;
		public UserQueryParam userQueryParam;
		public void copyFrom(ArticleQueryParam param) {
			this.count = param.count;
			this.auditState = param.auditState;
			this.bestType = param.bestType;
			this.delState = param.delState;
			this.draftState = param.draftState;
			this.hideState = param.hideState;
			this.orderType = param.orderType;
			this.recommendState = param.recommendState;
			this.retrieveSystemCategory = param.retrieveSystemCategory;
			this.retrieveUserCategory = param.retrieveUserCategory;
			this.subjectId = param.subjectId;
			this.sysCateId = param.sysCateId;
			this.userCateId = param.userCateId;
			this.userId = param.userId;
		}
		public QueryHelper createQuery() {
			QueryHelper query = super.createQuery();
			query.selectClause = "SELECT a";
			query.fromClause = " FROM Article a, User u ";
			query.addAndWhere("u.userId = a.userId");
			
			if (userQueryParam.loginName != null) {
				query.addAndWhere("u.loginName = :loginName");
				query.setString("loginName", userQueryParam.loginName);
			}
			if (userQueryParam.useSubjectId) {
				if (userQueryParam.subjectId == null) {
					query.addAndWhere("u.subjectId IS NULL");
				} else {
					query.addAndWhere("u.subjectId = :subjectId");
					query.setInteger("subjectId", this.subjectId);
				}
			}
			// 是否是名师, 专家, 推荐博客.
			if (userQueryParam.userTypeId != null && userQueryParam.userTypeId > 0) {
				query.addAndWhere("u.userType LIKE '%/" + userQueryParam.userTypeId + "/%' ");
			}			
			return query;
		}
	}
}
