package cn.edustar.jitar.service;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;

/**
 * 评课计划查询
 * @author baimindong
 *
 */
public class EvaluationPlanQuery extends BaseQuery {

    public Boolean ValidPlan = false ;
    public Boolean enabled = null ;
    public Integer orderType = 0;
    public Integer listType = 1;
    public Integer userId = 0;
    public String title = null;
    public Integer subjectId = null ;
    public Integer gradeId = null ;
    public String teacherName = null ;
    
	public EvaluationPlanQuery(String selectFields) {
		super(selectFields);
	}
    @Override
    public void initFromEntities(QueryContext qctx) {
    	qctx.addEntity("EvaluationPlan", "ev", "");
    }
    
    public void resolveEntity(QueryContext qctx, String entity){
        if("subj".equals(entity)){
            qctx.addJoinEntity("ev", "ev.metaSubject", "subj", "LEFT JOIN");
        }else if("grad".equals(entity)){
            qctx.addJoinEntity("ev", "ev.grade", "grad", "LEFT JOIN");
        }else{
            super.resolveEntity(qctx, entity);
        }
    }
    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
        if(this.enabled != null){
            qctx.addAndWhere("ev.enabled = :enabled");
            qctx.setBoolean("enabled", this.enabled);
        }
        if(this.ValidPlan == true){
            String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            qctx.addAndWhere("(:nowDate >= ev.startDate) And (:nowDate <= ev.endDate)");
            qctx.setString("nowDate", nowDate);
            qctx.setString("nowDate", nowDate);
        }
        if(this.title != null && this.title.length() > 0){
            String newKey = this.title.replace("%","[%]").replace("_","[_]").replace("[","[[]");
            qctx.addAndWhere("ev.evaluationCaption LIKE :title");
            qctx.setString("title", "%" + newKey + "%");
        }
        if(this.subjectId != null){
            qctx.addAndWhere("ev.metaSubjectId = :subjectId");
            qctx.setInteger("subjectId", this.subjectId);
        }
        if(this.gradeId != null){
            qctx.addAndWhere("ev.metaGradeId = :gradeId");
            qctx.setInteger("gradeId", this.gradeId);
        }
        if(this.teacherName != null && this.teacherName.length() > 0){
            String tName = this.teacherName.replace("%","[%]").replace("_","[_]").replace("[","[[]");
            qctx.addAndWhere("ev.teacherName LIKE :teacherName");
            qctx.setString("teacherName", "%" + tName +"%");
        }
        if(this.listType == 0){
            //#已经完成的评课
        	String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            qctx.addAndWhere(":nowDate > ev.endDate");
            qctx.setString("nowDate", nowDate);
            if(this.userId>0){
               qctx.addAndWhere("ev.teacherId = :userId");
               qctx.setInteger("userId", this.userId);
            }
        }else if(this.listType == 1){
            //#进行中的评课
        	String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            qctx.addAndWhere("(:nowDate >= ev.startDate) And (:nowDate <= ev.endDate)");
            qctx.setString("nowDate", nowDate);
            qctx.setString("nowDate", nowDate);
            if(this.userId>0){
               qctx.addAndWhere("ev.teacherId = :userId");
               qctx.setInteger("userId", this.userId);
            }
        }else if(this.listType == 2){
            //#我发起的评课
            qctx.addAndWhere("ev.createrId=:userId");
            qctx.setInteger("userId", this.userId)   ;
        }else if(this.listType == 3){
            //#我参与的评课
            qctx.addAndWhere(" ev.evaluationPlanId IN(SELECT ec.evaluationPlanId FROM EvaluationContent as ec WHERE ec.publishUserId=:userId)");
            qctx.setInteger("userId", this.userId);
        }
     
    }
    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx){
        if(this.orderType == 0){
            qctx.addOrder("ev.evaluationPlanId DESC");
        }else if( this.orderType == 1){
            qctx.addOrder("ev.evaluationPlanId DESC");
        }else if( this.orderType == 2){
            qctx.addOrder("ev.evaluationPlanId DESC");
        }
    }
}
