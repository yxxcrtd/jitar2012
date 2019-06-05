package cn.edustar.jitar.service;

import javax.servlet.http.HttpServletRequest;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.ParamUtil;

public class UserQuery extends BaseQuery {
	public Integer ORDER_TYPE_USERID_DESC = 0;
	public Integer ORDER_TYPE_VISITCOUNT_DESC = 1;
	public Integer ORDER_TYPE_ARTICLECOUNT_DESC = 2;
	public Integer ORDER_TYPE_RESOURCE_COUNT_DESC = 4;
	public Integer ORDER_TYPE_SCORE_DESC = 6;
	public Integer ORDER_TYPE_RANDOM = 100;

	// # 查询某个指定用户的时候使用.
	public String loginName = null;
	public Integer userStatus = null;
	// # 所属学科, == null 表示不限制.
	public Integer subjectId = null;
	// # 所属学段, == null 表示不限制.
	public Integer gradeId = null;
	public String bmd = null;
	public Integer role = null;
	public Integer pushState = null;
	public String unit = null;

	// 模糊匹配
	public boolean FuzzyMatch = false;

	// #自定义过滤条件
	public String custormAndWhere = null;

	// #按用户类型，如名称、教研员查询
	public Integer userTypeId = null;

	public Integer metaSubjectId = null;
	public Integer metaGradeId = null;
	public Integer unitId = null;
	public Integer sysCateId = null;
	// # 0 - userId desc, 1 - visitCount desc, 2 - articleCount desc
	public Integer orderType = 0;
	// # 3 - userScore desc (unimpl), 4 - resourceCount desc
	public String k = null;
	public String f = "";
	public String gm_groupIds = null ;  
	
    /** 请求对象 */
    public HttpServletRequest request;

    public void setRequest(HttpServletRequest request) {
        this.request = request;
        ParamUtil params = new ParamUtil(request);
        k = params.safeGetStringParam("k", null);
        f = params.safeGetStringParam("f", "0");
        request.setAttribute("f", f);
        request.setAttribute("k", k);
    }	

	public UserQuery(String selectFields) {
		super(selectFields);
	}
    
	@Override
	public void initFromEntities(QueryContext qctx) {		
		
		qctx.addEntity("User", "u", "");
	}

	public void applyKeywordFilter(QueryContext qctx) {

	}

	@Override
	public void applyWhereCondition(QueryContext qctx) {
		if (loginName != null) {
			qctx.addAndWhere("u.loginName = :loginName");
			qctx.setString("loginName", loginName);
		}

		if (custormAndWhere != null) {
			qctx.addAndWhere(custormAndWhere);
		}

		if (userStatus != null) {
			qctx.addAndWhere("u.userStatus = :userStatus");
			qctx.setInteger("userStatus", userStatus);
		}

		if (role != null) {
			qctx.addAndWhere("u.positionId = :role");
			qctx.setInteger("role", role);
		}

		if (subjectId != null) {
			qctx.addAndWhere("u.subjectId = :subjectId");
			qctx.setInteger("subjectId", subjectId);
			// print
			// "UserQuery.subjectId current is not supported, please check code"
		}

		if (metaSubjectId != null) {
			qctx.addAndWhere("u.subjectId = :subjectId");
			qctx.setInteger("subjectId", metaSubjectId);
		}

		if (gradeId != null) {
			if (FuzzyMatch == false) {
				qctx.addAndWhere("u.gradeId = :gradeId");
				qctx.setInteger("gradeId", gradeId);
			} else {
				qctx.addAndWhere("u.gradeId >= :gradeStartId AND u.gradeId < :gradeEndId");
				qctx.setInteger("gradeStartId", convertRoundMinNumber(metaGradeId));
				qctx.setInteger("gradeEndId", convertRoundMaxNumber(metaGradeId));
			}

		}

		if (metaGradeId != null) {
			if (FuzzyMatch == false) {
				qctx.addAndWhere("u.gradeId = :gradeId");
				qctx.setInteger("gradeId", metaGradeId);
			} else {
				qctx.addAndWhere("u.gradeId >= :gradeStartId AND u.gradeId < :gradeEndId");
				qctx.setInteger("gradeStartId", convertRoundMinNumber(metaGradeId));
				qctx.setInteger("gradeEndId", convertRoundMaxNumber(metaGradeId));
			}
		}

		if (pushState != null) {
			qctx.addAndWhere("u.pushState = :pushState");
			qctx.setInteger("pushState", pushState);
		}

		if (userTypeId != null) {
			qctx.addAndWhere("u.userType LIKE :userType");
			qctx.setString("userType", "%/" + userTypeId + "/%");
		}

		if (unitId != null) {
			qctx.addAndWhere("u.unitId = :unitId");
			qctx.setInteger("unitId", unitId);
		}

		if (sysCateId != null) {
			qctx.addAndWhere("u.categoryId = :sysCateId");
			qctx.setInteger("sysCateId", sysCateId);
		}

		if(gm_groupIds != null && gm_groupIds.length() >0 ){
			qctx.addAndWhere("gm.groupId IN (" + gm_groupIds + ")");
		}
		
		// 有查询条件
		if (k != null && k != "") {
			String newKey = k.replace("'", "''").replace("%", "[%]")
					.replace("_", "[_]").replace("[", "[[]");

			switch (f) {
			case "email":
				qctx.addAndWhere("u.email LIKE :keyword");
				break;
			case "trueName":
				qctx.addAndWhere("u.trueName LIKE :keyword");
				break;
			case "tags":
				qctx.addAndWhere("u.userTags LIKE :keyword");
				break;
			case "intro":
				qctx.addAndWhere("u.blogName LIKE :keyword OR u.blogIntroduce LIKE :keyword");
				break;
			case "unit":
				qctx.addAndWhere("unit.unitName LIKE :keyword");
				break;
			case "unitTitle":
				qctx.addAndWhere("unit.unitTitle LIKE :keyword");
				break;
			case "loginName":
				qctx.addAndWhere("u.loginName LIKE :keyword");
				break;
			case "name":
				qctx.addAndWhere("u.userId LIKE :keyword OR u.loginName LIKE :keyword OR u.nickName LIKE :keyword OR u.trueName LIKE :keyword");
				break;
			default:
				qctx.addAndWhere("u.userId LIKE :keyword OR u.loginName LIKE :keyword OR u.nickName LIKE :keyword OR u.trueName LIKE :keyword OR u.blogName LIKE :keyword OR u.userTags LIKE :keyword OR u.blogIntroduce LIKE :keyword");
				break;
			}
			qctx.setString("keyword", "%" + newKey + "%");
		}
	}

	@Override
	public void applyOrderCondition(QueryContext qctx) {
		switch (orderType) {
		case 0:
			qctx.addOrder("u.userId DESC");
			break;
		case 1:
			qctx.addOrder("u.visitCount DESC");
			break;
		case 2:
			qctx.addOrder("u.articleCount DESC");
			break;
		case 4:
			qctx.addOrder("u.resourceCount DESC");
			break;
		case 5:
			qctx.addOrder("u.userId ASC");
			break;
		case 6:
			qctx.addOrder("u.userScore desc");
			break;
		case 100:
			qctx.addOrder("newid()");
			break;
		default:
			break;
		}
	}

	@Override
	public void resolveEntity(QueryContext qctx, String entity) {
        if (entity.trim().equals("gm")) {
            qctx.addEntity("GroupMember", "gm", "gm.userId = u.userId");
        }else if (entity.trim().equals("subj")) {
			qctx.addJoinEntity("u", "u.subject", "subj", "LEFT JOIN");
		} else if (entity.trim().equals("grad")) {
			qctx.addJoinEntity("u", "u.grade", "grad", "LEFT JOIN");
		} else if (entity.trim().equals("unit")) {
			qctx.addJoinEntity("u", "u.unit", "unit", "LEFT JOIN");
		} else if (entity.trim().equals("sc")) {
			qctx.addJoinEntity("u", "u.sysCate", "sc", "LEFT JOIN");
		} else {
			super.resolveEntity(qctx, entity);
		}
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public void setBmd(String bmd) {
		this.bmd = bmd;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public void setPushState(Integer pushState) {
		this.pushState = pushState;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setFuzzyMatch(boolean fuzzyMatch) {
		FuzzyMatch = fuzzyMatch;
	}

	public void setCustormAndWhere(String custormAndWhere) {
		this.custormAndWhere = custormAndWhere;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	public void setMetaSubjectId(Integer metaSubjectId) {
		this.metaSubjectId = metaSubjectId;
	}

	public void setMetaGradeId(Integer metaGradeId) {
		this.metaGradeId = metaGradeId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public void setSysCateId(Integer sysCateId) {
		this.sysCateId = sysCateId;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setK(String k) {
		this.k = k;
	}

	public void setF(String f) {
		this.f = f;
	}

	public void setGm_groupIds(String gm_groupIds) {
		this.gm_groupIds = gm_groupIds;
	}
}
