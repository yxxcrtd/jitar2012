package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

/**
 * 协作组查询 将 py中的 GroupQuery 修改为 java的 GroupQuery
 * 
 * @author baimindong
 * 
 */
public class GroupQuery extends BaseQuery {

    public Integer createUserId = null; // # 创建者, 缺省不限制.
    public Integer channelId = null; // # 某个频道的
    public Integer groupState = 0; // # 协作组状态, 缺省 = 0 表示获取审核通过的.
    public Boolean isBestGroup = null; // # 是否优秀团队, 缺省 = null 不限制.
    public Boolean isRecommend = null; // # 是否推荐协作组, 缺省 = null 不限制.
    public Integer parentId = 0; // #默认不查找子组
    public Integer subjectId = null; // # 所属学科, 缺省 = null 不限制.
    public Integer gradeId = null; // # 所属学段, 缺省 = null 不限制.

    public Integer categoryId = null; // # 所属协作组分类, 缺省 = null 不限制.
    public String k = null; // # 查询关键字, 缺省 = null 不限制.
    public String kk = null; // # 查询关键字, 缺省 = null 不限制.
    public Integer orderType = 0;// # 排序方式, 缺省 0 - id DESC
    public String searchtype = null; //

    /**
     * 查询结构排序
     */
    public Integer ORDER_BY_ID_DESC = 0;// # groupId DESC
    public Integer ORDER_BY_ID_ASC = 1;// # groupId ASC
    public Integer ORDER_BY_CREATEDATE_DESC = 2;// # createDate DESC
    public Integer ORDER_BY_CREATEDATE_ASC = 3;// # createDate ASC
    public Integer ORDER_BY_GROUPNAME_DESC = 4;// # groupName DESC
    public Integer ORDER_BY_GROUPNAME_ASC = 5;// # groupName ASC
    public Integer ORDER_BY_GROUPTITLE_DESC = 6;// # groupTitle DESC
    public Integer ORDER_BY_GROUPTITLE_ASC = 7;// # groupTitle ASC
    public Integer ORDER_BY_VISITCOUNT_DESC = 8;// # visitCount DESC
    public Integer ORDER_BY_TOPICCOUNT_DESC = 9;// # topicCount DESC

    public GroupQuery(String selectFields) {
        super(selectFields);
    }
    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("Group", "g", "");
    }

    public void resolveEntity(QueryContext qctx, String entity) {
        if (entity.equals("u")) {
            qctx.addEntity("User", "u", "g.createUserId = u.userId");
        } else if (entity.equals("subj")) {
            qctx.addJoinEntity("g", "g.subject", "subj", "LEFT JOIN");
        } else if (entity.equals("sc")) {
            qctx.addJoinEntity("g", "g.sysCate", "sc", "LEFT JOIN");
        } else if (entity.equals("grad")) {
            qctx.addJoinEntity("g", "g.grade", "grad", "LEFT JOIN");
        } else {
            super.resolveEntity(qctx, entity);
        }
    }

    /**
     * 提供 where 条件
     * 
     * 千万注意：qctx.addAndWhere("g.XKXDId LIKE :likeKey");
     *        qctx.setString("likeKey", "%," + gradeId + "/" + subjectId + ",%");
     *        类似这样的查询中，一次查询中不能有相同的likeKey，相同的值怀疑是替换成了相同的内容了
     */
    public void applyWhereCondition(QueryContext qctx) {
        if (createUserId != null) {
            qctx.addAndWhere("g.createUserId = :createUserId");
            qctx.setInteger("createUserId", createUserId);
        }
        if (channelId != null) {
            qctx.addAndWhere("u.channelId = :channelId");
            qctx.setInteger("channelId", channelId);
        }
        if (groupState != null) {
            qctx.addAndWhere("g.groupState = :groupState");
            qctx.setInteger("groupState", groupState);
        }
        if (isBestGroup != null) {
            qctx.addAndWhere("g.isBestGroup = :isBestGroup");
            qctx.setBoolean("isBestGroup", isBestGroup);
        }
        if (isRecommend != null) {
            qctx.addAndWhere("g.isRecommend = :isRecommend");
            qctx.setBoolean("isRecommend", isRecommend);
        }
        if (gradeId != null && subjectId != null) {
            qctx.addAndWhere("g.XKXDId LIKE :likeKey1");
            qctx.setString("likeKey1", "%," + gradeId + "/" + subjectId + ",%");
        } else if (gradeId != null) {
            qctx.addAndWhere("g.XKXDId LIKE :likeKey2");
            qctx.setString("likeKey2", "%," + gradeId + "/%");
        } else if (subjectId != null) {
            qctx.addAndWhere("g.XKXDId LIKE :likeKey3");
            qctx.setString("likeKey3", "%/" + subjectId + ",%");
        }
        if (parentId != null) {
            qctx.addAndWhere("g.parentId = :parentId");
            qctx.setInteger("parentId", parentId);
        }
        if (categoryId != null) {
            qctx.addAndWhere("g.categoryId = :categoryId");
            qctx.setInteger("categoryId", categoryId);
        }
        if (k != null && k.length() > 0) {
            String newKey = k.replace("'", "''").replace("%", "[%]").replace("_", "[_]").replace("[", "[[]");
            qctx.addAndWhere("(g.groupName LIKE :likeKey4) OR (g.groupTitle LIKE :likeKey4) OR (g.groupTags LIKE :likeKey4) OR (g.groupIntroduce LIKE :likeKey4)");
            qctx.setString("likeKey4", "%" + newKey + "%");
        }
        if (kk != null && kk.length() > 0) {
            if (searchtype == null || searchtype.equals("ktname")) {
                String newKey = kk.replace("'", "''").replace("%", "[%]").replace("_", "[_]").replace("[", "[[]");
                qctx.addAndWhere("(g.groupName LIKE :likeKey5) OR (g.groupTitle LIKE :likeKey5)");
                qctx.setString("likeKey5", "%" + newKey + "%");
            } else if (searchtype.equals("ktperson")) {
                // #是课题组查询负责人
                String newKey = kk.replace("'", "''").replace("%", "[%]").replace("_", "[_]").replace("[", "[[]");
                qctx.addAndWhere("g.groupId In (SELECT groupId FROM cn.edustar.jitar.pojos.GroupKTUser WHERE (teacherName LIKE :likeKey6))");
                qctx.setString("likeKey6", "%" + newKey + "%");
            }
        }
    }

    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx) {
        if (this.orderType.equals(ORDER_BY_ID_DESC)) {
            qctx.addOrder("g.groupId DESC");
        } else if (this.orderType.equals(ORDER_BY_ID_ASC)) {
            qctx.addOrder("g.groupId ASC");
        } else if (this.orderType.equals(ORDER_BY_CREATEDATE_DESC)) {
            qctx.addOrder("g.createDate DESC, g.groupId DESC");
        } else if (this.orderType.equals(ORDER_BY_CREATEDATE_ASC)) {
            qctx.addOrder("g.createDate ASC, g.groupId ASC");
        } else if (this.orderType.equals(ORDER_BY_GROUPNAME_DESC)) {
            qctx.addOrder("g.groupName DESC, g.groupId DESC");
        } else if (this.orderType.equals(ORDER_BY_GROUPNAME_ASC)) {
            qctx.addOrder("g.groupName ASC, g.groupId ASC");
        } else if (this.orderType.equals(ORDER_BY_GROUPTITLE_DESC)) {
            qctx.addOrder("g.groupTitle DESC, g.groupId DESC");
        } else if (this.orderType.equals(ORDER_BY_GROUPTITLE_ASC)) {
            qctx.addOrder("g.groupTitle ASC, g.groupId ASC");
        } else if (this.orderType.equals(ORDER_BY_VISITCOUNT_DESC)) {
            qctx.addOrder("g.visitCount DESC, g.groupId DESC");
        } else if (this.orderType.equals(ORDER_BY_TOPICCOUNT_DESC)) {
            qctx.addOrder("g.topicCount DESC, g.groupId DESC");
        }
    }
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }
    public void setGroupState(Integer groupState) {
        this.groupState = groupState;
    }
    public void setIsBestGroup(Boolean isBestGroup) {
        this.isBestGroup = isBestGroup;
    }
    public void setIsRecommend(Boolean isRecommend) {
        this.isRecommend = isRecommend;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }
    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public void setK(String k) {
        this.k = k;
    }
    public void setKk(String kk) {
        this.kk = kk;
    }
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
    public void setSearchtype(String searchtype) {
        this.searchtype = searchtype;
    }

}
