package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.CommonUtil;

public class ContentSpaceArticleQuery extends BaseQuery {

    public Integer contentSpaceId = null;
    public Integer ownerType = null;
    public Integer ownerId = null;
    public String k = null, f = null;
    public Integer orderType = 0;

    public ContentSpaceArticleQuery(String selectFields) {
        super(selectFields);
    }

    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("ContentSpaceArticle", "csa", "");
    }

    public void resolveEntity(QueryContext qctx, String entity) {
        if ("cs".endsWith(entity)) {
            qctx.addEntity("ContentSpace", "cs", "csa.contentSpaceId = cs.contentSpaceId");
        } else {
            super.resolveEntity(qctx, entity);
        }
    }

    public void applyWhereCondition(QueryContext qctx) {
        if (this.contentSpaceId != null) {
            qctx.addAndWhere("(cs.contentSpaceId = :contentSpaceId OR cs.parentPath LIKE '%/" + this.contentSpaceId + "/%')");
            qctx.setInteger("contentSpaceId", this.contentSpaceId);
        }
        if (this.ownerType != null) {
            qctx.addAndWhere("cs.ownerType = :ownerType");
            qctx.setInteger("ownerType", this.ownerType);
        }
        if (this.ownerId != null) {
            qctx.addAndWhere("cs.ownerId = :ownerId");
            qctx.setInteger("ownerId", this.ownerId);
        }

        if (this.k != null && this.f != null) {
            String newKey = CommonUtil.escapeSQLString(this.k);
            if (this.f.equals("createUserId")) {
                if (CommonUtil.isInteger(this.k)) {
                    qctx.addAndWhere("csa.createUserId = :createUserId");
                    qctx.setInteger("createUserId", Integer.valueOf(this.k));
                }
            } else {
                qctx.addAndWhere("csa." + this.f + " LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }
        }
    }
    public void applyOrderCondition(QueryContext qctx) {
        if (this.orderType == 0) {
            qctx.addOrder("csa.contentSpaceArticleId DESC");
        }
    }

}
