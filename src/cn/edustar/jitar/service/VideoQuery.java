package cn.edustar.jitar.service;

import java.util.List;

import org.springframework.web.context.ContextLoader;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.CommonUtil;

public class VideoQuery extends BaseQuery {

    // default
    public final static Integer ORDER_TYPE_VIDEOID_DESC = 0;
    public final static Integer ORDER_TYPE_CREATEDATE_DESC = 1;
    public final static Integer ORDER_TYPE_VIEWCOUNT_DESC = 2;
    public final static Integer ORDER_TYPE_COMMENTCOUNT = 3;

    // 要查询的视频所属用户, == null 表示不限制.
    private Integer userId = null;
    private Integer unitId = null;
    private Integer categoryId = null;
    private Integer gradeId = null;
    private Integer subjectId = null;
    // 视频的自定义分类
    private Integer userCateId = null;

    // 是否审核通过, 缺省 = 0 表示审核通过的.
    public Integer auditState = 0;
    // 默认显示没有删除的照片
    private Boolean delState = false;
    // 所属状态, 缺省 = null 表示不限制. 0引用 1原创
    private String typeState = null;
    private Integer specialSubjectId = null;
    public String videoIds = null;
    // 排序方式, 0 - videoId DESC, 1 - createDate DESC, 2 - viewCount DESC, 3 -
    // commentCount DESC
    public Integer orderType = 0;

    // 默认是精确匹配
    private Boolean FuzzyMatch = false;
    // 查询关键字.
    public String k = null;
    // 查询字段. 0 标题 1简介 2用户
    public String f = null;

    public VideoQuery(String selectFields) {
        super(selectFields);
    }

    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("Video", "v", "");
    }

    public void applyKeywordFilter(QueryContext qctx) {
        String newKey = CommonUtil.escapeSQLString(k);
        int f1 = null!=f?Integer.parseInt(f):4;
        switch (f1) {
            case 0 :
                qctx.addAndWhere("(v.title LIKE :likeKey) OR (v.tags LIKE :likeKey) OR (v.addIp LIKE :likeKey)");
                qctx.setString("likeKey", "%" + newKey + "%");
                break;
            case 1 :
                qctx.addAndWhere(" v.summary LIKE :likeKey");
                qctx.setString("likeKey", "%" + newKey + "%");
                break;
            case 2 :
                qctx.addAndWhere("(u.nickName LIKE :likeKey) OR (u.trueName LIKE :likeKey) OR (u.loginName LIKE :likeKey)");
                qctx.setString("likeKey", "%" + newKey + "%");
                break;
            case 3 :
                // 只查标题
                qctx.addAndWhere("v.title LIKE :likeKey");
                qctx.setString("likeKey", "%" + newKey + "%");
                break;
            default :
                qctx.addAndWhere("(v.title LIKE :likeKey) OR (v.tags LIKE :likeKey) OR (v.addIp LIKE :likeKey)");
                qctx.setString("likeKey", "%" + newKey + "%");
                break;
        }

    }

    @Override
    public void applyWhereCondition(QueryContext qctx) {
        if (k != null && k != "") {
            applyKeywordFilter(qctx);
        }

        if (userId != null) {
            qctx.addAndWhere("v.userId = :userId");
            qctx.setInteger("userId", userId);
        }

        if (auditState != null) {
            qctx.addAndWhere("v.auditState = :auditState");
            qctx.setInteger("auditState", auditState);
        }

        if (unitId != null) {
            qctx.addAndWhere("v.unitId = :unitId");
            qctx.setInteger("unitId", unitId);
        }

        if (categoryId != null) {
            // 只查询分类自己的
            // qctx.addAndWhere("v.categoryId = :categoryId")
            // qctx.setInteger("categoryId", self.categoryId)
            // 查询自己和子孙分类
            List list = ContextLoader.getCurrentWebApplicationContext().getBean("categoryService", CategoryService.class).getCategoryIds(categoryId);
            String cateIds = "";
            for (Object c : list) {
                if (cateIds == "") {
                    cateIds = cateIds + c;
                } else {
                    cateIds = cateIds + "," + c;
                }
            }
            qctx.addAndWhere("v.categoryId IN (" + cateIds + ")");
        }

        if (userCateId != null) {
            if(userCateId == 0){
                qctx.addAndWhere("v.userCateId IS NULL");
            } else {
                qctx.addAndWhere("v.userCateId = :userCateId");
                qctx.setInteger("userCateId", userCateId);
            }
        }

        if (subjectId != null) {
            qctx.addAndWhere("v.subjectId = :subjectId");
            qctx.setInteger("subjectId", subjectId);
        }

        if (videoIds != null) {
            qctx.addAndWhere(" v.videoId IN (" + videoIds + ")");
        }

        if (gradeId != null) {
            qctx.addAndWhere("v.gradeId >= :gradeId1 and v.gradeId < :gradeId2");
            qctx.setInteger("gradeId1", CommonUtil.convertRoundMinNumber(gradeId));
            qctx.setInteger("gradeId2", CommonUtil.convertRoundMinNumber(gradeId));
        }

        if (specialSubjectId != null) {
            qctx.addAndWhere("v.specialSubjectId = :specialSubjectId");
            qctx.setInteger("specialSubjectId", specialSubjectId);
        }

    }

    @Override
    public void applyOrderCondition(QueryContext qctx) {
        // 排序方式, 0 - videoId DESC, 1 - createDate DESC, 2 - viewCount DESC, 3 -
        // commentCount DESC
        switch (orderType) {
            case 0 :
                qctx.addOrder("v.videoId DESC");
                break;
            case 1 :
                qctx.addOrder("v.createDate DESC");
                break;
            case 2 :
                qctx.addOrder("v.viewCount DESC");
                break;
            case 3 :
                qctx.addOrder("v.commentCount DESC");
                break;
            default :
                break;
        }
    }

    @Override
    public void resolveEntity(QueryContext qctx, String entity) {
        if ("u".equals(entity.trim())) {
            qctx.addEntity("User", "u", "v.userId = u.userId");
        } else if ("sc".equals(entity.trim())) {
            qctx.addJoinEntity("v", "v.sysCate", "sc", "LEFT JOIN");
        } else {
            super.resolveEntity(qctx, entity);
        }
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public void setUserCateId(Integer userCateId) {
        this.userCateId = userCateId;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public void setDelState(Boolean delState) {
        this.delState = delState;
    }

    public void setTypeState(String typeState) {
        this.typeState = typeState;
    }

    public void setSpecialSubjectId(Integer specialSubjectId) {
        this.specialSubjectId = specialSubjectId;
    }

    public void setVideoIds(String videoIds) {
        this.videoIds = videoIds;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public void setFuzzyMatch(boolean fuzzyMatch) {
        FuzzyMatch = fuzzyMatch;
    }

    public void setK(String k) {
        this.k = k;
    }

    public void setF(String f) {
        this.f = f;
    }
}
