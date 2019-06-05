package cn.edustar.jitar.service;

import javax.servlet.http.HttpServletRequest;
import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ParamUtil;

public class SpecialSubjectArticleQuery extends BaseQuery {

	/** 请求对象 */
	public static HttpServletRequest request;

	public ParamUtil params = null;

	public Integer specialSubjectId = null;

	public Integer orderType = 0;

	public boolean articleState = true;

	public String k = null;

	public String f = null;

	public static void setRequest(HttpServletRequest request) {
		SpecialSubjectArticleQuery.request = request;
	}

	public SpecialSubjectArticleQuery(String selectFields) {
		super(selectFields);
	}

	@Override
	public void initFromEntities(QueryContext qctx) {
		
		ParamUtil params = new ParamUtil(request);
		k = params.getStringParam("k");
		params = new ParamUtil(request);
		request.setAttribute("k", k);
		
		k = params.getStringParam("k");
		f = params.getStringParam("f");
		if (f == null) {
			f = "title";
		}
		if (f == "") {
			f = "title";
		}
		request.setAttribute("k", k);
		request.setAttribute("f", f);
		qctx.addEntity("Article", "a", "");
		qctx.addEntity("SpecialSubjectArticle", "ssa", "a.articleId = ssa.articleId");
	}

	@Override
	public void applyWhereCondition(QueryContext qctx) {
		if (specialSubjectId != null) {
			qctx.addAndWhere("ssa.specialSubjectId = :specialSubjectId");
			qctx.setInteger("specialSubjectId", specialSubjectId);
		}

		if (articleState) {
			qctx.addAndWhere("ssa.articleState = :articleState");
			qctx.setBoolean("articleState", articleState);
		}

		if (k != null && !"".equals(k)) {
			String newKey = CommonUtil.escapeSQLString(k);
			if (f.equals("title")) {
				qctx.addAndWhere("ssa.title LIKE :keyword");
				qctx.setString("keyword", "%" + newKey + "%");
			} else if (f.equals("uname")) {
				qctx.addAndWhere("ssa.loginName LIKE :keyword OR ssa.userTrueName LIKE :keyword");
				qctx.setString("keyword", "%" + newKey + "%");
			} else {
				qctx.addAndWhere("ssa.title LIKE :keyword OR ssa.loginName LIKE :keyword OR ssa.loginName LIKE :keyword");
				qctx.setString("keyword", "%" + newKey + "%");
			}

		}
	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		if (orderType== 0) {
			qctx.addOrder("ssa.specialSubjectArticleId DESC");
		}
	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		super.resolveEntity(qctx, entity);
	}

	public void setSpecialSubjectId(Integer specialSubjectId) {
		this.specialSubjectId = specialSubjectId;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setArticleState(boolean articleState) {
		this.articleState = articleState;
	}

	public void setK(String k) {
		this.k = k;
	}

	public void setF(String f) {
		this.f = f;
	}
}
