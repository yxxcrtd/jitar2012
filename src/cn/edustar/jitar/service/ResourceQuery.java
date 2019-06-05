package cn.edustar.jitar.service;

import java.util.List;

import org.springframework.web.context.ContextLoader;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 资源查询 由resource_query.py中的
 * 
 * @author baimindong
 * 
 */
public class ResourceQuery extends BaseQuery {

    public static final Integer ORDER_TYPE_ID_DESC = 0;
    public static final Integer ORDER_TYPE_CREATEDATE_DESC = 1;
    public static final Integer ORDER_TYPE_VIEWCOUNT_DESC = 2;
    public static final Integer ORDER_TYPE_COMMENTCOUNT_DESC = 3;
    public static final Integer ORDER_TYPE_DOWNLOADCOUNT_DESC = 4;
    //最热：下载次数+浏览次数
    public static final Integer ORDER_TYPE_HOT_DESC = 5;
    
    // #多个资源查询
    public String resourceIds = null;
    // #单个资源Id
    public Integer resourceId = null;
    // #发布到资源库
    public Integer publishToZyk = null;

    // # 审核状态, 缺省 = AUDIT_STATE_OK 表示获取审核通过的.
    public Integer auditState = 0;
    // # 是否被删除, 缺省 = false 表示选择未删除的; = true 表示获得删除的; = null 表示不限制此条件.
    public Boolean delState = false;
    // # 是否推荐, 缺省 = null 表示不限制.
    public Boolean rcmdState = null;
    // # 共享模式, 缺省 = SHARE_MODE_FULL 也就是获取完全共享的资源.
    public Integer shareMode = Resource.SHARE_MODE_FULL;

    // # 资源类型, 缺省 = null 不限制.
    public Integer sysCateId = null; // public Integer
                                     // params.getIntParamZeroAsNull("categoryId")
    // # 资源所属学科, 缺省 = null 不限制.
    public Integer subjectId = null; // public Integer
                                     // params.getIntParamZeroAsNull("subjectId")
    // # 资源所属学段
    public Integer gradeId = null; // public Integer
                                   // params.getIntParamZeroAsNull("gradeId")
    // # 资源所属学段是否精确 level=1 精确处理
    public Integer gradelevel = null; // public Integer
                                      // params.getIntParamZeroAsNull("level")
    // #个人分类
    public Integer userCateId = null; // public Integer
                                      // params.getIntParamZeroAsNull("userCateId")

    // # 排序方式, 0 - 按照 id desc, 1 - createDate desc, 2 - viewCount desc, 3 -
    // commentCount desc
    // # 4 - downloadCount desc, ...
    public Integer orderType = 0;
    public String k = null; // public Integer params.getStringParam("k") //#
                            // title, tags 查询关键字.
    public String f = null;// public Integer params.getStringParam("f") //#
                           // title, tags 查询关键字字段.

    public Integer unitId = null; // #public Integer
                                  // params.getIntParamZeroAsNull("unitId") //#
                                  // 用户所属机构.
    public Integer userId = null;// public Integer
                                 // params.getIntParamZeroAsNull("userId")

    public Integer pushState = null;
    // # 默认是精确匹配
    public boolean FuzzyMatch = false;

    // #自定义条件
    public String custormAndWhereClause = null;
    

    /** 分类服务 */
    private CategoryService cate_svc;

    public ResourceQuery(String selectFields) {
        super(selectFields);
    }

    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("Resource", "r", "");
    }

    public void resolveEntity(QueryContext qctx, String entity) {
        if ("u".equals(entity)) {
            qctx.addEntity("User", "u", "r.userId = u.userId");
        } else if ("msubj".equals(entity)) {
            qctx.addEntity("MetaSubject", "msubj", "r.subjectId=msubj.msubjId");
        } else if ("subj".equals(entity)) {
            qctx.addJoinEntity("r", "r.subject", "subj", "LEFT JOIN");
        } else if ("grad".equals(entity)) {
            qctx.addJoinEntity("r", "r.grade", "grad", "LEFT JOIN");
        } else if ("rt".equals(entity)) {
            qctx.addJoinEntity("r", "r.resType", "rt", "LEFT JOIN");
        } else if ("sc".equals(entity)) {
            qctx.addJoinEntity("r", "r.sysCate", "sc", "LEFT JOIN");
        } else if ("uc".equals(entity)) {
            qctx.addJoinEntity("r", "r.userCate", "uc", "LEFT JOIN");
        } else if ("vc".equals(entity)) {
            qctx.addEntity("ViewCount", "vc", "r.id = vc.objId And vc.objType = " + ObjectType.OBJECT_TYPE_RESOURCE);
        } else {
            super.resolveEntity(qctx, entity);
        }
    }
    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
        if (userId != null) {
            qctx.addAndWhere("r.userId = :userId");
            qctx.setInteger("userId", userId);
        }
        if (subjectId != null) {
            qctx.addAndWhere("r.subjectId = :subjectId");
            qctx.setInteger("subjectId", subjectId);
        }
        if (custormAndWhereClause != null) {
            qctx.addAndWhere(custormAndWhereClause);
        }
        if (gradeId != null) {
            if (gradelevel != null) {
                if (gradelevel == 1) {
                    qctx.addAndWhere("r.gradeId = :gradeStartId");
                    qctx.setInteger("gradeStartId", gradeId);
                } else {
                    if (FuzzyMatch == false) {
                        qctx.addAndWhere("r.gradeId = :gradeId");
                        qctx.setInteger("gradeId", gradeId);
                    } else {
                        qctx.addAndWhere("r.gradeId >= :gradeStartId AND r.gradeId < :gradeEndId");
                        qctx.setInteger("gradeStartId", calcGradeStartId(gradeId));
                        qctx.setInteger("gradeEndId", calcGradeEndId(gradeId));
                    }
                }
            } else {
                if (FuzzyMatch == false) {
                    qctx.addAndWhere("r.gradeId = :gradeId");
                    qctx.setInteger("gradeId", gradeId);
                } else {
                    qctx.addAndWhere("r.gradeId >= :gradeStartId AND r.gradeId < :gradeEndId");
                    qctx.setInteger("gradeStartId", calcGradeStartId(gradeId));
                    qctx.setInteger("gradeEndId", calcGradeEndId(gradeId));
                }
            }
        }
        if (auditState != null) {
            qctx.addAndWhere("r.auditState = :auditState");
            qctx.setInteger("auditState", auditState);
        }
        if (unitId != null) {
            qctx.addAndWhere("r.unitId = :unitId");
            qctx.setInteger("unitId", unitId);
        }
        if (pushState != null) {
            qctx.addAndWhere("r.pushState = :pushState");
            qctx.setInteger("pushState", pushState);
        }
        if (delState != null) {
            qctx.addAndWhere("r.delState = :delState");
            qctx.setBoolean("delState", delState);
        }
        if (rcmdState != null) {
            qctx.addAndWhere("r.recommendState = :rcmdState");
            qctx.setBoolean("rcmdState", rcmdState);
        }
        if (shareMode != null) {
            if (shareMode >= 1000) {
                qctx.addAndWhere("r.shareMode >= :shareMode");
                qctx.setInteger("shareMode", shareMode);
            } else if (shareMode == 500) {
                // # TODO:测试 这行被修改为 >= 的条件，可能导致一些问题，未测试
                qctx.addAndWhere("r.shareMode >= :shareMode");
                qctx.setInteger("shareMode", shareMode);
            } else if (shareMode == 0) {
                qctx.addAndWhere("r.shareMode >= :shareMode");
                qctx.setInteger("shareMode", shareMode);
            } else {
                qctx.addAndWhere("r.shareMode >= :shareMode");
                qctx.setInteger("shareMode", shareMode);
            }
        }
        if (sysCateId != null) {
            // #只查询分类自己的
            // #qctx.addAndWhere("r.sysCateId = :sysCateId")
            // #qctx.setInteger("sysCateId", sysCateId)
            // #查询自己和子孙分类
            cate_svc = ContextLoader.getCurrentWebApplicationContext().getBean("categoryService", CategoryService.class);
            List<Integer> list = cate_svc.getCategoryIds(sysCateId);
            String cateIds = "";
            for (int i = 0; i < list.size(); i++) {
                if (cateIds.equals("")) {
                    cateIds = "" + list.get(i);
                } else {
                    cateIds = cateIds + "," + list.get(i);
                }
            }
            qctx.addAndWhere("r.sysCateId IN (" + cateIds + ")");
        }
        if (userCateId != null) {
            if(userCateId > 0){
            qctx.addAndWhere("r.userCateId = :userCateId");
            qctx.setInteger("userCateId", userCateId);
            }
            else{                
                qctx.addAndWhere("r.userCateId IS NULL");
            }
        }
        
        if (k != null && k.length() > 0) {
            String newKey = CommonUtil.escapeSQLString(k);
            if (null!=f&&f.equals("title")) {
                qctx.addAndWhere("r.title LIKE :keyword OR r.tags LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (null!=f&&f.equals("intro")) {
                qctx.addAndWhere("r.summary LIKE :keyword ");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (null!=f&&f.equals("uname")) {
                qctx.addAndWhere("u.nickName LIKE :keyword OR u.trueName LIKE :keyword OR u.loginName LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (null!=f&&f.equals("unitName")) {
                qctx.addAndWhere("u.unit.unitName LIKE :keyword ");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (null!=f&&f.equals("unitTitle")) {
                qctx.addAndWhere("u.unit.unitTitle LIKE :keyword ");
                qctx.setString("keyword", "%" + newKey + "%");
            } else if (null!=f&&f.equals("unit")) {
                qctx.addAndWhere("u.unit.unitTitle LIKE :keyword ");
                qctx.setString("keyword", "%" + newKey + "%");
            } else {
                qctx.addAndWhere("r.title LIKE :keyword OR r.tags LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }
        }

        if (resourceId != null) {
            qctx.addAndWhere("r.resourceId = :resourceId");
            qctx.setInteger("resourceId", resourceId);
        }
        if (resourceIds != null) {
            String strWhereClause = " r.resourceId IN (" + resourceIds + ")";
            qctx.addAndWhere(strWhereClause);
        }
        if (publishToZyk != null) {
            qctx.addAndWhere("r.publishToZyk = :publishToZyk");
            qctx.setInteger("publishToZyk", publishToZyk);
        }
    }

    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx) {
        // # 排序方式, 0 - 按照 id desc, 1 - createDate desc, 2 - viewCount desc, 3 -
        // commentCount desc
        // # 4 - downloadCount desc, ...
        if (orderType.equals(ORDER_TYPE_ID_DESC)) {
            qctx.addOrder("r.resourceId DESC");
        } else if (orderType.equals(ORDER_TYPE_CREATEDATE_DESC)) {
            qctx.addOrder("r.createDate DESC");
        } else if (orderType.equals(ORDER_TYPE_VIEWCOUNT_DESC)) {
            qctx.addOrder("r.viewCount DESC");
        } else if (orderType.equals(ORDER_TYPE_COMMENTCOUNT_DESC)) {
            qctx.addOrder("r.commentCount DESC");
        } else if (orderType.equals(ORDER_TYPE_DOWNLOADCOUNT_DESC)) {
            qctx.addOrder("r.downloadCount DESC");
        } else if (orderType.equals(ORDER_TYPE_HOT_DESC)) {
            qctx.addOrder("r.downloadCount+r.viewCount DESC");
        }
    }

    /**
     * 计算指定学段的开始 id, 一般等于 gradeId, 参见 Grade.startId.
     * 
     * @param gradeId
     * @return
     */
    private int calcGradeStartId(int gradeId) {
        return convertRoundMinNumber(gradeId);
    }

    /**
     * 计算指定学段的结束 id, 一般等于 gradeId + 10**, 参见 Grade.endId.
     * 
     * @param gradeId
     * @return
     */

    private int calcGradeEndId(int gradeId) {
        return convertRoundMaxNumber(gradeId);
    }

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public void setPublishToZyk(Integer publishToZyk) {
		this.publishToZyk = publishToZyk;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public void setDelState(Boolean delState) {
		this.delState = delState;
	}

	public void setRcmdState(Boolean rcmdState) {
		this.rcmdState = rcmdState;
	}

	public void setShareMode(Integer shareMode) {
		this.shareMode = shareMode;
	}

	public void setSysCateId(Integer sysCateId) {
		this.sysCateId = sysCateId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public void setGradelevel(Integer gradelevel) {
		this.gradelevel = gradelevel;
	}

	public void setUserCateId(Integer userCateId) {
		this.userCateId = userCateId;
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

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setPushState(Integer pushState) {
		this.pushState = pushState;
	}

	public void setFuzzyMatch(boolean fuzzyMatch) {
		FuzzyMatch = fuzzyMatch;
	}
    
}
