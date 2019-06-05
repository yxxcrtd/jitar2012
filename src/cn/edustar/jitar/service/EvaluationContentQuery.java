package cn.edustar.jitar.service;

import javax.servlet.http.HttpServletRequest;

import cn.edustar.jitar.data.BaseQuery;
import cn.edustar.jitar.data.QueryContext;
import cn.edustar.jitar.util.ParamUtil;

public class EvaluationContentQuery extends BaseQuery{

    public Integer evaluationPlanId = null ;
    public Integer publishUserId = null; 
    public Integer metaSubjectId = null ; //public Integer params.getIntParamZeroAsNull("subjectId")
    public Integer metaGradeId = null ;	//public Integer params.getIntParamZeroAsNull("gradeId")
    public Integer orderType = 0;
    public Integer gradelevel = null ;//public Integer params.getIntParamZeroAsNull("level")
    //# 默认是精确匹配
    public Boolean FuzzyMatch = true ; // #模糊匹配
	
	
	/** 请求对象 */
	protected HttpServletRequest request;

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public EvaluationContentQuery(String selectFields) {
		super(selectFields);
	}
	
    @Override
    public void initFromEntities(QueryContext qctx) {
    	qctx.addEntity("EvaluationContent", "ec", "");
    	ParamUtil params = new ParamUtil(request);
        this.metaSubjectId = params.getIntParamZeroAsNull("subjectId");
        this.metaGradeId = params.getIntParamZeroAsNull("gradeId");
        this.gradelevel = params.getIntParamZeroAsNull("level");
        request.setAttribute("subjectId",this.metaSubjectId);
        request.setAttribute("gradeId",this.metaGradeId);
    }

    public void resolveEntity(QueryContext qctx, String entity){
        if("subj".equals(entity)){
            qctx.addJoinEntity("ec", "ec.metaSubject", "subj", "LEFT JOIN");
        }else if("grad".equals(entity)){
            qctx.addJoinEntity("ec", "ec.grade", "grad", "LEFT JOIN");
        }else{
            super.resolveEntity(qctx, entity);
        }
    }
    /**
     * 提供 where 条件
     */
    public void applyWhereCondition(QueryContext qctx) {
        if(this.evaluationPlanId != null){
            qctx.addAndWhere("ec.evaluationPlanId = :evaluationPlanId");
            qctx.setInteger("evaluationPlanId", this.evaluationPlanId);
        }
        if(this.publishUserId != null){
            qctx.addAndWhere("ec.publishUserId = :publishUserId");
            qctx.setInteger("publishUserId", this.publishUserId);
        }
        if(this.metaSubjectId != null){
            qctx.addAndWhere("ec.metaSubjectId = :metaSubjectId");
            qctx.setInteger("metaSubjectId", this.metaSubjectId);
        }
        if(this.metaGradeId != null){
            if(this.gradelevel != null){
                if(this.gradelevel == 1){
                      qctx.addAndWhere("ec.metaGradeId = :gradeStartId");
                      qctx.setInteger("gradeStartId", this.metaGradeId);
                }else{
                      if(this.FuzzyMatch == false){
                            qctx.addAndWhere("ec.metaGradeId = :metaGradeId");
                            qctx.setInteger("metaGradeId", this.metaGradeId);
                      }else{
                            qctx.addAndWhere("ec.metaGradeId >= :gradeStartId AND ec.metaGradeId < :gradeEndId");
                            qctx.setInteger("gradeStartId", this.calcGradeStartId(this.metaGradeId));
                            qctx.setInteger("gradeEndId", this.calcGradeEndId(this.metaGradeId));
                      }
                }
            }else{
                if(this.FuzzyMatch == false){
                      qctx.addAndWhere("ec.metaGradeId = :metaGradeId");
                      qctx.setInteger("metaGradeId", this.metaGradeId);
                }else{
                      qctx.addAndWhere("ec.metaGradeId >= :gradeStartId AND ec.metaGradeId < :gradeEndId");
                      qctx.setInteger("gradeStartId", this.calcGradeStartId(this.metaGradeId));
                      qctx.setInteger("gradeEndId", this.calcGradeEndId(this.metaGradeId));
                }
            }
          }
       }
    /**
     * 提供排序 order 条件
     */
    public void applyOrderCondition(QueryContext qctx){
        if (this.orderType == 0){
            qctx.addOrder("ec.evaluationContentId DESC");
        }
    }
    
    /**
     * 计算指定学段的开始 id, 一般等于 gradeId, 参见 Grade.startId.
     * @param gradeId
     * @return
     */
    private int calcGradeStartId(int gradeId){
      return convertRoundMinNumber(gradeId);
    }
    
    /**
     * 计算指定学段的结束 id, 一般等于 gradeId + 10**, 参见 Grade.endId.
     * @param gradeId
     * @return
     */
    
    private int calcGradeEndId(int gradeId){
      return convertRoundMaxNumber(gradeId);
    }    
}
