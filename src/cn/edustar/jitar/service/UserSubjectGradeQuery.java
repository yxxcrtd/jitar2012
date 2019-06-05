package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

public class UserSubjectGradeQuery extends BaseQuery {

	public Integer subjectId = null;
	public Integer gradeId = null;
	public Integer userId = null;
	// 是否模糊匹配
	public boolean FuzzyMatch = false;
	public Integer orderType = 0;

	public UserSubjectGradeQuery(String selectFields) {
		super(selectFields);
	}

	
	@Override
	public void initFromEntities(QueryContext qctx) {
		qctx.addEntity("UserSubjectGrade", "usg", "");
	}

	@Override
	public void applyWhereCondition(QueryContext qctx) {
		if (subjectId != null) {
			qctx.addAndWhere("usg.subjectId = :subjectId");
			qctx.setInteger("subjectId", subjectId);
		}

		if (gradeId != null) {
			if (!FuzzyMatch) {
				qctx.addAndWhere("usg.gradeId = :gradeId");
				qctx.setInteger("gradeId", gradeId);
			} else {
				qctx.addAndWhere("usg.gradeId >= :gradeStartId And usg.gradeId < :gradeEndId");
				qctx.setInteger("gradeStartId", convertRoundMinNumber(gradeId));
				qctx.setInteger("gradeEndId", convertRoundMaxNumber(gradeId));
			}

		}

		if (userId != null) {
			qctx.addAndWhere("usg.userId = :userId");
			qctx.setInteger("userId", userId);
		}

	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		 if (orderType == 0){
			 qctx.addOrder("usg.userSubjectGradeId DESC");
		 }
	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
		super.resolveEntity(qctx, entity);
	}

	
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setFuzzyMatch(boolean fuzzyMatch) {
		FuzzyMatch = fuzzyMatch;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

}
