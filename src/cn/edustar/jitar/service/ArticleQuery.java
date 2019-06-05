package cn.edustar.jitar.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 文章查询的包装。
 * 
 * @author mxh
 * 
 */
public class ArticleQuery extends BaseQuery {

    /** 文章元学科标识 */
    public Integer subjectId = null;

    /** 文章元学段标识 */
    public Integer gradeId = null;

    /** 删除标识 */
    public Boolean delState = false;

    /** 查询隐藏状态，== null 表示不区分，缺省 = 0 查询非隐藏的. */
    public Integer hideState = 0;

    /** 文章草稿状态: == null 不筛选。 */
    public Boolean draftState = false;

    /** 是否精华，缺省为 null 表示所有文章; = true 表示获取精华的; = false 表示获取非精华的. */
    public Boolean bestState = false;

    /** 是否是名师文章 */
    public Boolean userIsFamous = null;

    /** 机构标识 */
    public Integer unitId = null;

    /** 用户登录名标识 */
    public String loginName = null;

    /** 不能满足需求时的自定义查询条件 */
    public String custormAndWhereClause = null;

    /** 默认是精确匹配，指的是元学段信息 */
    public Boolean fuzzyMatch = false;

    /** 文章标签 */
    public String articleTags = null;

    /** 文章的Id集合 */
    public String[] newArticleIds = null;

    /** 排序方式 */
    public Integer orderType = 0;
    
    public Integer userId = null; // 用户筛选.
    public Integer sysCateId = null; // 文章文章所属系统分类.
    public Integer userCateId = null;// 文章文章所属用户分类.
    public Boolean typeState = null; //文章的类型，0 原创；1 转载

    public String k = null; // 关键字
    public String f = null; // 查询的字段

    public ArticleQuery(String selectFields) {
        super(selectFields);
        if (null == f || f.length() == 0) {
            f = "title";
        }
    }

    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("Article", "a", "");
    }

    public void resolveEntity(QueryContext qctx, String entity) {
        if (entity.equals("u")) {
            qctx.addEntity("User", "u", "a.userId = u.userId");
        } else if ("subj".equals(entity)) {
            qctx.addJoinEntity("a", "a.subject", "subj", "LEFT JOIN");
        } else if ("grad".equals(entity)) {
            qctx.addJoinEntity("a", "a.grade", "grad", "LEFT JOIN");
        } else if ("sc".equals(entity)) {
            qctx.addJoinEntity("a", "a.sysCate", "sc", "LEFT JOIN");
        } else if ("uc".equals(entity)) {
            qctx.addJoinEntity("a", "a.userCate", "uc", "LEFT JOIN");
        } else {
            super.resolveEntity(qctx, entity);
        }
    }

    public void applyWhereCondition(QueryContext qctx) {
        if (null != custormAndWhereClause) {
            qctx.addAndWhere(" " + custormAndWhereClause + " ");
        }
        if(null != typeState){
            qctx.addAndWhere("a.typeState = :typeState");
            qctx.setBoolean("typeState", typeState);
        }

        if (null != delState) {
            qctx.addAndWhere("a.delState = :delState");
            qctx.setBoolean("delState", delState);
        }

        if (null != hideState) {
            qctx.addAndWhere("a.hideState = :hideState");
            qctx.setInteger("hideState", hideState);
        }
        if (null != subjectId) {
            qctx.addAndWhere("a.subjectId = :subjectId");
            qctx.setInteger("subjectId", subjectId);
        }
        if (null != unitId) {
            qctx.addAndWhere("a.unitId = :unitId");
            qctx.setInteger("unitId", unitId);
        }
        if (null != gradeId) {
            if (fuzzyMatch) {
                qctx.addAndWhere("a.gradeId >= :gradeStartId AND a.gradeId < :gradeEndId");
                qctx.setInteger("gradeStartId", CommonUtil.convertRoundMinNumber(gradeId));
                qctx.setInteger("gradeEndId", CommonUtil.convertRoundMaxNumber(gradeId));
            } else {
                qctx.addAndWhere("a.gradeId = :gradeId");
                qctx.setInteger("gradeId", gradeId);
            }
        }
        if (null != draftState) {
            qctx.addAndWhere("a.draftState = :draftState");
            qctx.setBoolean("draftState", draftState);
        }

        if (null != bestState) {
            qctx.addAndWhere("a.bestState = :bestState");
            qctx.setBoolean("bestState", bestState);
        }
        if (null != userId) {
            qctx.addAndWhere("a.userId = :userId");
            qctx.setInteger("userId", userId);
        }
        if (null != loginName) {
            qctx.addAndWhere("a.loginName = :loginName");
            qctx.setString("loginName", loginName);
        }
        if (null != subjectId) {
            qctx.addAndWhere("a.subjectId = :subjectId");
            qctx.setInteger("subjectId", subjectId);
        }
        if (null != sysCateId) {
            // #只查询分类自己的
            // #qctx.addAndWhere("a.sysCateId = :sysCateId")
            // #qctx.setInteger("sysCateId", self.sysCateId)
            // #查询分类自己的和其下级的分类
            CategoryService cate_svc = ContextLoader.getCurrentWebApplicationContext().getBean("categoryService",
                    CategoryService.class);
            List<Integer> list = cate_svc.getCategoryIds(sysCateId);
            String cateIds = "";
            for (Integer c : list) {
                if ("".equals(cateIds)) {
                    cateIds = cateIds + String.valueOf(c);
                } else {
                    cateIds = cateIds + "," + String.valueOf(c);
                }
            }
            qctx.addAndWhere("a.sysCateId IN (" + cateIds + ")");
        }

        if (null != userCateId) {
            if(userCateId == 0){
                qctx.addAndWhere("a.userCateId IS NULL");
            }else{
                qctx.addAndWhere("a.userCateId = :userCateId");
                qctx.setInteger("userCateId", userCateId);
            }
        }

        if (null != userIsFamous) {
            if (userIsFamous) {
                qctx.addAndWhere("u.userType LIKE '%/1/%'");
            } else {
                qctx.addAndWhere("u.userType NOT LIKE '%/1/%'");
            }
        }
        if (null != articleTags) {
            qctx.addAndWhere("a.title LIKE :articleTags");
            qctx.setString("articleTags", "%" + articleTags + "%");
        }

        if (null != newArticleIds) {
            String s = StringUtils.join(newArticleIds, ",");
            qctx.addAndWhere("a.articleId IN (" + s + ")");
        }
        if (null != k && !k.equals("")) {
            String newKey = CommonUtil.escapeSQLString(k);
            if (f.equals("title")) {
                qctx.addAndWhere("a.title LIKE :keyword OR a.articleTags LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (f.equals("intro")) {
                qctx.addAndWhere("a.articleAbstract LIKE :keyword ");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (f.equals("uname")) {
                qctx.addAndWhere("u.trueName LIKE :keyword OR u.loginName LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (f.equals("unitName")) {
                qctx.addAndWhere("u.unit.unitName LIKE :keyword ");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (f.equals("unitTitle")) {
                qctx.addAndWhere("u.unit.unitTitle LIKE :keyword ");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (f.equals("unit")) {
                qctx.addAndWhere("u.unit.unitTitle LIKE :keyword ");
                qctx.setString("keyword", "%" + newKey + "%");
            } else {
                qctx.addAndWhere("a.title LIKE :keyword OR a.articleTags LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }
        }
        // # 对于需要做除法的情况，除法不能为 0
        if (orderType != null && orderType == 6) {
            qctx.addAndWhere("a.commentCount > 0");
        }
    }

    public void applyOrderCondition(QueryContext qctx) {
        // # 排序方式, 0 - 按照 id desc, 1 - createDate desc, 2 - viewCount desc, 3 -
        // # 顶、踩、星级
        if (orderType == 1) {
            qctx.addOrder("a.createDate DESC");
        } else if (orderType == 2) {
            qctx.addOrder("a.viewCount DESC");
        } else if (orderType == 3) {
            qctx.addOrder("a.commentCount DESC");
        } else if (orderType == 4) {
            qctx.addOrder("a.digg DESC");
        } else if (orderType == 5) {
            qctx.addOrder("a.trample DESC");
        } else if (orderType == 6) {
            qctx.addOrder("(a.starCount/a.commentCount) DESC");
        } else {
            qctx.addOrder("a.articleId DESC");
        }
    }

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public void setDelState(Boolean delState) {
		this.delState = delState;
	}

	public void setHideState(Integer hideState) {
		this.hideState = hideState;
	}

	public void setDraftState(Boolean draftState) {
		this.draftState = draftState;
	}

	public void setBestState(Boolean bestState) {
		this.bestState = bestState;
	}

	public void setUserIsFamous(Boolean userIsFamous) {
		this.userIsFamous = userIsFamous;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setFuzzyMatch(Boolean fuzzyMatch) {
		this.fuzzyMatch = fuzzyMatch;
	}

	public void setArticleTags(String articleTags) {
		this.articleTags = articleTags;
	}

	public void setNewArticleIds(String[] newArticleIds) {
		this.newArticleIds = newArticleIds;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setSysCateId(Integer sysCateId) {
		this.sysCateId = sysCateId;
	}

	public void setUserCateId(Integer userCateId) {
		this.userCateId = userCateId;
	}

	public void setTypeState(Boolean typeState) {
		this.typeState = typeState;
	}

	public void setK(String k) {
		this.k = k;
	}

	public void setF(String f) {
		this.f = f;
	}

	public void setCustormAndWhereClause(String custormAndWhereClause) {
		this.custormAndWhereClause = custormAndWhereClause;
	}
	
}