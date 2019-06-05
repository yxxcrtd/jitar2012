package cn.edustar.jitar.service;

import java.util.List;

import org.springframework.web.context.ContextLoader;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.CommonUtil;

public class PhotoQuery extends BaseQuery {

    public PhotoQuery(String selectFields) {
        super(selectFields);
    }
    // default.
    public final static Integer ORDER_TYPE_ID_DESC = 0;
    public final static Integer ORDER_TYPE_PHOTOID_DESC = 0;
    public final static Integer ORDER_TYPE_CREATEDATE_DESC = 1;
    public final static Integer ORDER_TYPE_VIEWCOUNT_DESC = 2;
    public final static Integer ORDER_TYPE_COMMENT = 4;
    public final static Integer COUNT = 3;

    // 要查询的图片所属用户, == null 表示不限制.
    public Integer userId = null;
    // 是否审核通过, 缺省 = 0 表示审核通过的.
    public Integer auditState = 0;
    // 默认显示没有删除的照片
    public Boolean delState = false;
    // 所属系统分类, 缺省 = null 表示不限制.
    public Integer sysCateId = null;
    // 所属用户分类, 缺省 = null 表示不限制.
    public Integer userStapleId = null;
    // 标签查询
    public String tags = null;

    // 排除photoid为指定值得记录
    public Integer removePhotoId = null;

    public void setRemovePhotoId(Integer removePhotoId) {
        this.removePhotoId = removePhotoId;
    }

    public void setSysCateId(Integer sysCateId) {
        this.sysCateId = sysCateId;
    }
    // 默认在所有地方显示照片，不局限于个人空间
    public Boolean isPrivateShow = false;
    public Integer unitId = null;
    public Integer specialSubjectId = null;
    public Integer unitPath = null;
    public Integer sharedDepth = null;
    public Integer rcmdDepth = null;
    // 默认是精确匹配
    public boolean FuzzyMatch = false;
    public Integer channelId = null;
    public String channelCate = null;
    // 默认是精确匹配 FuzzyMatch = False
    // 简单实现按扩展名查询 extName = None

    // 简单实现按扩展名查询
    public String extName = null;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }
    public void setDelState(Boolean delState) {
        this.delState = delState;
    }
    public void setUserStapleId(Integer userStapleId) {
        this.userStapleId = userStapleId;
    }
    public void setPrivateShow(Boolean isPrivateShow) {
        this.isPrivateShow = isPrivateShow;
    }
    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }
    public void setSpecialSubjectId(Integer specialSubjectId) {
        this.specialSubjectId = specialSubjectId;
    }
    public void setUnitPath(Integer unitPath) {
        this.unitPath = unitPath;
    }
    public void setSharedDepth(Integer sharedDepth) {
        this.sharedDepth = sharedDepth;
    }
    public void setRcmdDepth(Integer rcmdDepth) {
        this.rcmdDepth = rcmdDepth;
    }
    public void setFuzzyMatch(boolean fuzzyMatch) {
        FuzzyMatch = fuzzyMatch;
    }
    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }
    public void setChannelCate(String channelCate) {
        this.channelCate = channelCate;
    }
    public void setExtName(String extName) {
        this.extName = extName;
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

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }
    public Integer orderType = 0;

    public String k = null;
    public String f = null;
    public Integer photoId = null;

    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("Photo", "p", "");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void applyWhereCondition(QueryContext qctx) {
        if (k != null && k != "") {
            applyKeywordFilter(qctx);
        }
        if (photoId != null && !"-1".equals(photoId)) {
            qctx.addAndWhere("p.photoId = :photoId");
            qctx.setInteger("photoId", photoId);
        }
        if (null != tags) {
            tags = tags.replace("'", "''").replace("%", "[%]").replace("_", "[_]").replace("[", "[[]");
            qctx.addAndWhere("p.tags = :tags");
            qctx.setString("tags", tags);
        }
        if (null != removePhotoId) {
            qctx.addAndWhere("p.photoId != :photoId");
            qctx.setInteger("photoId", removePhotoId);
        }
        if (userId != null) {
            qctx.addAndWhere("p.userId = :userId");
            qctx.setInteger("userId", userId);
        }

        if (auditState != null) {
            qctx.addAndWhere("p.auditState = :auditState");
            qctx.setInteger("auditState", auditState);
        }

        if (null != delState) {
            qctx.addAndWhere("p.delState = :delState");
            qctx.setBoolean("delState", delState);
        }

        if (sysCateId != null) {
            if (sysCateId != -1) {
                // 只查询分类自己的
                // qctx.addAndWhere("p.sysCateId = :sysCateId")
                // qctx.setInteger("sysCateId", sysCateId)
                // 查询自己和子孙分类
                String cateIds = "";
                List list = ContextLoader.getCurrentWebApplicationContext().getBean("categoryService", CategoryService.class).getCategoryIds(sysCateId);
                for (Object c : list) {
                    if (cateIds.trim().equals("")) {
                        cateIds = cateIds + c;
                    } else {
                        cateIds = cateIds + "," + c;
                    }
                }
                qctx.addAndWhere("p.sysCateId IN (" + cateIds + ")");
            } else {
                qctx.addAndWhere("p.sysCateId is null");
            }
        }

        if (userStapleId != null) {
            if (userStapleId != -1) {
                qctx.addAndWhere("p.userStaple = :userStapleId");
                qctx.setInteger("userStapleId", userStapleId);
            } else {
                qctx.addAndWhere("p.userStaple is null");
            }
        }

        if (specialSubjectId != null) {
            qctx.addAndWhere("p.specialSubjectId = :specialSubjectId");
            qctx.setInteger("specialSubjectId", specialSubjectId);
        }

        if (isPrivateShow != null) {
            qctx.addAndWhere("p.isPrivateShow = :isPrivateShow");
            qctx.setBoolean("isPrivateShow", isPrivateShow);
        }

        if (extName != null) {
            qctx.addAndWhere(" p.href LIKE :extName");
            qctx.setString("extName", "%" + extName);
        }

        if (unitId != null) {
            qctx.addAndWhere("p.unitId = :unitId");
            qctx.setInteger("unitId", unitId);
        }

        if (unitPath != null) {
            qctx.addAndWhere("p.unitPath LIKE :unitPath");
            qctx.setString("unitPath", unitPath + "%");
        }

        if (sharedDepth != null) {
            qctx.addAndWhere("p.sharedDepth LIKE :sharedDepth");
            qctx.setString("sharedDepth", "%" + sharedDepth + "%");
        }

        if (rcmdDepth != null) {
            qctx.addAndWhere("p.rcmdDepth LIKE :rcmdDepth");
            qctx.setString("rcmdDepth", "%" + rcmdDepth + "%");
        }

        if (channelId != null) {
            qctx.addAndWhere("p.channelId = :channelId");
            qctx.setInteger("channelId", channelId);
        }

        if (channelCate != null) {
            if (!FuzzyMatch) {
                // 精确匹配
                qctx.addAndWhere("p.channelCate = :channelCate");
                qctx.setString("channelCate", channelCate);
            } else {
                // 模糊匹配
                qctx.addAndWhere("p.channelCate LIKE :channelCate");
                qctx.setString("channelCate", "%" + channelCate + "%");
            }
        }

    }

    private void applyKeywordFilter(QueryContext qctx) {
        String newKey = CommonUtil.escapeSQLString(k);
        if (f.trim().equals("0")) {
            qctx.addAndWhere("(p.title LIKE :likeKey) OR (p.tags LIKE :likeKey) OR (stap.title LIKE :likeKey) OR (p.addIp LIKE :likeKey)");
            qctx.setString("likeKey", "%" + newKey + "%");
        } else if (f.trim().equals("1")) {
            qctx.addAndWhere(" p.summary LIKE :likeKey");
            qctx.setString("likeKey", "%" + newKey + "%");
        } else if (f.trim().equals("2")) {
            qctx.addAndWhere("(p.userNickName LIKE :likeKey) OR (p.userTrueName LIKE :likeKey)");
            qctx.setString("likeKey", "%" + newKey + "%");
        } else if (f.trim().equals("3")) {
            qctx.addAndWhere("p.title LIKE :likeKey");
            qctx.setString("likeKey", "%" + newKey + "%");
        } else {
            qctx.addAndWhere("(p.title LIKE :likeKey) OR (p.tags LIKE :likeKey) OR (stap.title LIKE :likeKey) OR (p.addIp LIKE :likeKey)");
            qctx.setString("likeKey", "%" + newKey + "%");
        }

    }

    @Override
    public void applyOrderCondition(QueryContext qctx) {
        // 排序方式, 0 - photoId DESC, 1 - createDate DESC, 2 - viewCount DESC, 3 -
        // commentCount DESC
        if (orderType == 0) {
            qctx.addOrder("p.photoId DESC");
        } else if (orderType == 1) {
            qctx.addOrder("p.createDate DESC");
        } else if (orderType == 2) {
            qctx.addOrder("p.viewCount DESC");
        } else if (orderType == 3) {
            qctx.addOrder("p.commentCount DESC");
        }

    }

    @Override
    public void resolveEntity(QueryContext qctx, String entity) {
        if ("u".equals(entity.trim())) {
            qctx.addEntity("User", "u", "p.userId = u.userId");
        } else if ("stap".equals(entity.trim())) {
            qctx.addJoinEntity("p", "p.staple", "stap", "LEFT JOIN");
        }

        else if ("sc".equals("entity")) {
            qctx.addJoinEntity("p", "p.sysCate", "sc", "LEFT JOIN");
        } else {
            super.resolveEntity(qctx, entity);
        }
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

}
