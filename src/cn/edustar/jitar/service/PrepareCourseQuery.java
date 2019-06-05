package cn.edustar.jitar.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.ParamUtil;

public class PrepareCourseQuery extends BaseQuery {

    public Integer prepareCourseId = null;
    public Integer createUserId = null;
    public String k = null;
    public String ktype = null;
    public String unit = null;
    public String course_BeginDate = null;
    public String course_EndDate = null;
    public Integer subjectId = null;
    public Integer gradeId = null;

    public Integer orderType = 0;
    public Integer status = null;
    // #集备执行的阶段，正在进行running；已经完成finishaed；还未进行will ;recommend 推荐的
    public String stage = null;
    // #准确学科的查询
    public String containChild = null;

    public Integer prepareCoursePlanId = null;
    public boolean prepareCourseGenerated = true;
    // #自定义条件查询
    public String custormAndWhere = null;
    String nowDate = null;

    public PrepareCourseQuery(String selectFields) {
        super(selectFields);
    }

    @Override
    public void initFromEntities(QueryContext qctx) {

        nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        qctx.addEntity("PrepareCourse", "pc", "");
    }

    @Override
    public void applyWhereCondition(QueryContext qctx) {
        if (custormAndWhere != null) {
            qctx.addAndWhere(custormAndWhere);
        }

        if (status != null) {
            qctx.addAndWhere("pc.status = :status");
            qctx.setInteger("status", status);
        }

        if (prepareCoursePlanId != null) {
            qctx.addAndWhere("pc.prepareCoursePlanId = :prepareCoursePlanId");
            qctx.setInteger("prepareCoursePlanId", prepareCoursePlanId);
        }

        if (prepareCourseGenerated) {
            qctx.addAndWhere("pc.prepareCourseGenerated = :prepareCourseGenerated");
            qctx.setBoolean("prepareCourseGenerated", prepareCourseGenerated);
        }

        if (stage != null) {
            if ("running".equals(stage.trim())) {
                qctx.addAndWhere("(:stage >= pc.startDate And :stage <= pc.endDate)");
                qctx.setString("stage", nowDate);
            }

            if ("finished".equals(stage.trim())) {
                qctx.addAndWhere("(pc.endDate < :stage)");
                qctx.setString("stage", nowDate);
            }

            if ("new".equals(stage.trim())) {
                qctx.addAndWhere("(pc.startDate > :stage)");
                qctx.setString("stage", nowDate);
            }

            if ("notfinished".equals(stage.trim())) {
                qctx.addAndWhere("(pc.endDate >= :stage)");
                qctx.setString("stage", nowDate);
            }

            if ("recommend".equals(stage.trim())) {
                qctx.addAndWhere("(pc.recommendState = :stage)");
                qctx.setInteger("stage", 1);
            }

        }

        if (prepareCourseId != null) {
            qctx.addAndWhere("pc.prepareCourseId = :prepareCourseId");
            qctx.setInteger("prepareCourseId", prepareCourseId);
        }

        if (createUserId != null) {
            qctx.addAndWhere("pc.createUserId = :createUserId");
            qctx.setInteger("createUserId", createUserId);
        }

        if (k != null && !"".equals(k)) {
            String newKey = k.replace("'", "''").replace("%", "[%]").replace("_", "[_]").replace("[", "[[]");
            if (ktype == null || !"".equals(ktype.trim())) {
                qctx.addAndWhere("pc.title LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }
            // #搜索标题
            else if (ktype.trim().equals("1")) {
                qctx.addAndWhere("pc.title LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }
            // #搜索发起人
            else if (ktype.trim().equals("2")) {
                qctx.addAndWhere("u.nickName LIKE :keyword OR u.trueName LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }
            // #搜索主备人
            else if (ktype.trim().equals("3")) {
                qctx.addAndWhere("ul.nickName LIKE :keyword OR ul.trueName LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }
            // #搜索主备人所属机构
            else if (ktype.equals("4")) {
                qctx.addAndWhere("ul.unit.unitTitle LIKE :keyword");
                qctx.setString("keyword", "%" + newKey + "%");
            }
        }

        if (course_BeginDate != null && !"".equals(course_BeginDate.trim())) {
            qctx.addAndWhere("pc.startDate >= :startdate");
            qctx.setString("startdate", course_BeginDate);
        }
        if (course_EndDate != null && !"".equals(course_EndDate.trim())) {
            qctx.addAndWhere("pc.startDate <= :enddate");
            qctx.setString("enddate", course_EndDate);
        }

        if (subjectId != null) {
            qctx.addAndWhere("pc.metaSubjectId = :subjectId");
            qctx.setInteger("subjectId", subjectId);
        }

        if (containChild != null) {
            if (containChild.equals("True")) {
                if (gradeId != null) {
                    qctx.addAndWhere("pc.gradeId >= :gradeIdMin And pc.gradeId < :gradeIdMax");
                    qctx.setInteger("gradeIdMin", convertRoundMinNumber(gradeId));
                    qctx.setInteger("gradeIdMax", convertRoundMaxNumber(gradeId));
                }
            } else if (gradeId != null) {
                qctx.addAndWhere("pc.gradeId = :gradeId");
                qctx.setInteger("gradeId", gradeId);
            }

        } else {
            if (gradeId != null) {
                qctx.addAndWhere("pc.gradeId >= :gradeIdMin And pc.gradeId < :gradeIdMax");
                qctx.setInteger("gradeIdMin", convertRoundMinNumber(gradeId));
                qctx.setInteger("gradeIdMax", convertRoundMaxNumber(gradeId));
            }

        }
    }

    @Override
    public void applyOrderCondition(QueryContext qctx) {
        // id
        if (orderType == 0)
            qctx.addOrder("pc.prepareCourseId DESC");
        // # ItemOrder
        else if (orderType == 1)
            qctx.addOrder("pc.itemOrder ASC");
        else
            qctx.addOrder("pc.prepareCourseId DESC");
    }

    @Override
    public void resolveEntity(QueryContext qctx, String entity) {
        if ("u".equals(entity.trim()))
            // #备课发起人
            qctx.addEntity("User", "u", "pc.createUserId = u.userId");
        else if ("ul".equals(entity.trim()))
            // #备课主备人
            qctx.addEntity("User", "ul", "pc.leaderId = ul.userId");
        else
            super.resolveEntity(qctx, entity);
    }

    public void setPrepareCourseId(Integer prepareCourseId) {
        this.prepareCourseId = prepareCourseId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public void setK(String k) {
        this.k = k;
    }

    public void setKtype(String ktype) {
        this.ktype = ktype;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setCourse_BeginDate(String course_BeginDate) {
        this.course_BeginDate = course_BeginDate;
    }

    public void setCourse_EndDate(String course_EndDate) {
        this.course_EndDate = course_EndDate;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setContainChild(String containChild) {
        this.containChild = containChild;
    }

    public void setPrepareCoursePlanId(Integer prepareCoursePlanId) {
        this.prepareCoursePlanId = prepareCoursePlanId;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }
}
