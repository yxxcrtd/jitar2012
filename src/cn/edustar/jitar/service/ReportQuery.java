package cn.edustar.jitar.service;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 举报查询的包装。
 * 
 * @author mxh
 * 
 */
public class ReportQuery extends BaseQuery {

    /** 举报类型 */
    public String reportType = null;

    /** 举报对象类型 */
    public Integer objType = null;

    /** 关键字 */
    public String k = null;

    /** 查询字段 */
    public String f = null;

    public ReportQuery(String selectFields) {
        super(selectFields);
        if (null == f || f.length() == 0) {
            f = "objTitle";
        }
    }

    @Override
    public void initFromEntities(QueryContext qctx) {
        qctx.addEntity("Report", "r", "");
    }

    public void resolveEntity(QueryContext qctx, String entity) {
        if (entity.equals("u")) {
            qctx.addEntity("User", "u", "r.userId = u.userId");
        } else {
            super.resolveEntity(qctx, entity);
        }
    }

    public void applyWhereCondition(QueryContext qctx) {
        if (null != reportType) {
            qctx.addAndWhere("r.reportType = :reportType");
            qctx.setString("reportType", reportType);
        }

        if (null != objType) {
            qctx.addAndWhere("a.objType = :objType");
            qctx.setInteger("objType", objType);
        }
        if (null != k && !k.equals("")) {
            String newKey = CommonUtil.escapeSQLString(k);
            if (f.equals("objTitle")) {
                qctx.addAndWhere("r.objTitle LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }
        }
    }

    public void applyOrderCondition(QueryContext qctx) {
        qctx.addOrder("r.reportId DESC");
    }

    public void setK(String k) {
        this.k = k;
    }

    public void setF(String f) {
        this.f = f;
    }

}