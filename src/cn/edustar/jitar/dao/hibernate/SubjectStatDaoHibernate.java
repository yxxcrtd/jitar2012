package cn.edustar.jitar.dao.hibernate;

import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.model.ObjectType;

public class SubjectStatDaoHibernate  extends BaseDaoHibernate{
	private Subject subject;
    private String startday;
    private String endday;
	
    int metaSubjId = 0;
    int startGradeId = 0;
    int endGradeId = 0;    
    
    /**
     * 工作室数
     * @return
     */
	public int GetUserCount(){
		if(null == subject){
			return 0;
		}
        String hql;
        hql = "SELECT COUNT(*) FROM User WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId)";
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }
        if(null != endday && endday.length()>0){
        	hql = hql + "  AND (createDate BETWEEN '" + startday + "' And '" + endday + "')";
        }
        Object o=this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if(null == o){
        	return 0;
        }
        int userCount = ((Long)o).intValue();	
        return userCount;
	}
    /**
     * 工作室总数
     * @return
     */
	public int GetALLUserCount(){
		if(null == subject){
			return 0;
		}
        String hql;
        hql = "SELECT COUNT(*) FROM User WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId)";
        Object o=this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if(null == o){
        	return 0;
        }
        int userCount = ((Long)o).intValue();	
        return userCount;
	}	
	/**
	 * 原创文章数		typeState=0
	 * 
	 * 统计文章条件：hideState=0  		//不隐藏
	 * 			 draftState=false   //草稿不统计
	 * 			 delState=false 	//删除的不统计
	 * 为了和学科表中的统计对应，没加条件 hideState=0 AND draftState=false AND delState=false	 
	 * @return
	 */
	public int GetOriginalArticleCount(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;
        hql = "SELECT COUNT(*) FROM Article WHERE  typeState=0 AND subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId)";
        if(null != endday && endday.length()>0){
        	hql = hql + " AND (createDate BETWEEN '" + startday + "' And '" + endday + "')";
        }        
        Object o=this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if(null == o)
        {
        	return 0;
        }
        
        int articleCount = ((Long) o)
                .intValue();
        return articleCount;
	}
	/**
	 * 原创文章积分
	 * @return
	 */
	public float GetOriginalArticleScore(int articleCount){
		String hql="SELECT value FROM Config WHERE name = 'score.my.article.add'";
		Object o = this.getSession().createQuery(hql).iterate().next();
		if ( o != null){
			return (float)Integer.parseInt(o.toString()) * articleCount;
		}else{
			return 0;
		}		
	}
	/**
	 * 原创文章罚分
	 * @return
	 */
	public float GetOriginalArticlePunishScore(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;
        if(null != endday && endday.length()>0){
        	hql = "SELECT SUM(score) FROM UPunishScore WHERE objType=3 AND objId IN(SELECT id FROM Article WHERE  typeState=0 AND subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId) AND (createDate BETWEEN '" + startday + "' And '" + endday + "'))";
        }else{
        	hql = "SELECT SUM(score) FROM UPunishScore WHERE objType=3 AND objId IN(SELECT id FROM Article WHERE  typeState=0 AND subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId))";
        }
        Object o = this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if ( null == o)
        {
        	return 0;
        }
        float score = Float.parseFloat(o.toString());
        return score;
	}
	/**
	 * 转载文章数		typeState=1
	 * 统计文章条件：hideState=0  		//不隐藏
	 * 			 draftState=false   //草稿不统计
	 * 			 delState=false 	//删除的不统计
	 * 为了和学科表中的统计对应，没加条件 hideState=0 AND draftState=false AND delState=false 
	 * @return
	 */
	public int GetReferencedArticleCount(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;
        hql = "SELECT COUNT(*) FROM Article WHERE typeState=1 AND subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId)";
        if(null != endday && endday.length()>0){
        	hql = hql + " AND (createDate BETWEEN '" + startday + "' And '" + endday + "')";
        }        
        Object o = this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if( null == o)
        {
        	return 0;
        }
        int articleCount = ((Long) o).intValue();
        return articleCount;        
	}
	/**
	 * 转载文章积分
	 */
	public float GetReferencedArticleScore(int articleCount){
		String hql="SELECT value FROM Config WHERE name = 'score.other.article.add'";
		Object o = this.getSession().createQuery(hql).iterate().next();
		if ( o != null){
			return (float)Integer.parseInt(o.toString()) * articleCount;
		}else{
			return 0;
		}			
	}
	/**
	 * 转载文章罚分
	 * @return
	 */
	public float GetReferencedArticlePunishScore(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;	
        if(null != endday && endday.length()>0){
        	hql = "SELECT SUM(score) FROM UPunishScore WHERE objType=3 AND objId IN(SELECT id FROM Article WHERE  typeState=1 AND subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId) AND (createDate BETWEEN '" + startday + "' And '" + endday + "'))";
        }else{
        	hql = "SELECT SUM(score) FROM UPunishScore WHERE objType=3 AND objId IN(SELECT id FROM Article WHERE  typeState=1 AND subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId))";
        }
        Object o = this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if( null == o){
        	return 0;
        }
        float score = Float.parseFloat(o.toString());
        return score;        
	}
	
	/**
	 * 推荐文章数		recommendState=true
	 * 统计文章条件：hideState=0  		//不隐藏
	 * 			 draftState=false   //草稿不统计
	 * 			 delState=false 	//删除的不统计
	 * 为了和学科表中的统计对应，没加条件 hideState=0 AND draftState=false AND delState=false 
	 * @return
	 */
	public int GetRecommendArticleCount(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;	
        hql = "SELECT COUNT(*) FROM Article WHERE recommendState=true AND subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId)";
        if(null != endday && endday.length()>0){
        	hql = hql + " AND (createDate BETWEEN '" + startday + "' And '" + endday + "')";
        }        
        Object o = this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if( null == o){
        	return 0;
        }
        int articleCount = ((Long) o)
                .intValue();
        return articleCount;                
	}
	/**
	 * 推荐文章积分
	 * @return
	 */
	public float GetRecommendArticleScore(int articleCount){
		String hql="SELECT value FROM Config WHERE name = 'score.article.rcmd'";
		Object o = this.getSession().createQuery(hql).iterate().next();
		if ( o != null){
			return (float)Integer.parseInt(o.toString()) * articleCount;
		}else{
			return 0;
		}			
	}
	/**
	 * 推荐文章罚分
	 * @return
	 */
	public float GetRecommendArticlePunishScore(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;		
        if(null != endday && endday.length()>0){
        	hql = "SELECT SUM(score) FROM UPunishScore WHERE objType=3 AND objId IN(SELECT id FROM Article WHERE  recommendState=true AND subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId) AND (createDate BETWEEN '" + startday + "' And '" + endday + "'))";
        }else{
        	hql = "SELECT SUM(score) FROM UPunishScore WHERE objType=3 AND objId IN(SELECT id FROM Article WHERE  recommendState=true AND subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId))";
        }
        Object o =this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if( null == o){
        	return 0;
        }
        float score = Float.parseFloat(o.toString());
        return score;        
	}
	/**
	 * 资源数
	 * 统计条件：
	 * delState=false  //删除的不统计
	 * auditState=0    //审核通过的
	 * 为了和学科表中的统计对应，没加条件 delState=false AND auditState=0 AND 
	 * @return
	 */
	public int GetResourceCount(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;		
        hql = "SELECT COUNT(*) FROM Resource WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId)";
        if(null != endday && endday.length()>0){
        	hql = hql + " And (createDate BETWEEN '" + startday + "' And '" + endday + "')";
        }         
        Object o = this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if( null == o){
        	return 0;
        }
        int resourceCount = ((Long) o)
                .intValue();
        return resourceCount;
	}
	/**
	 * 资源积分
	 * @return
	 */
	public float  GetResourceScore(int resourceCount){
		String hql="SELECT value FROM Config WHERE name = 'score.resource.add'";
		Object o = this.getSession().createQuery(hql).iterate().next();
		if ( o != null){
			return (float)Integer.parseInt(o.toString()) * resourceCount;
		}else{
			return 0;
		}
	}
	/**
	 * 资源罚分
	 * @return
	 */
	public float  GetResourcePunishScore(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;		
        if(null != endday && endday.length()>0){
        	hql = "SELECT SUM(score) FROM UPunishScore WHERE objType=12 AND objId IN(SELECT id FROM Resource WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId) AND (createDate BETWEEN '" + startday + "' And '" + endday + "'))";
        }else{
        	hql = "SELECT SUM(score) FROM UPunishScore WHERE objType=12 AND objId IN(SELECT id FROM Resource WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId))";
        }
        Object o = this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if( null == o){
        	return 0;
        }
        float score = Float.parseFloat(o.toString());
        return score;           
	}
	/**
	 * 推荐资源数
	 * delState=false  //删除的不统计
	 * auditState=0    //审核通过的
	 * 为了和学科表中的统计对应，没加条件 delState=false AND auditState=0 
	 * @return
	 */
	public int GetRecommendResourceCount(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;	
        hql = "SELECT COUNT(*) FROM Resource WHERE recommendState=true AND subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId)";
        if(null != endday && endday.length()>0){
        	hql = hql + " And (createDate BETWEEN '" + startday + "' And '" + endday + "')";
        }        
        Object o = this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if( null ==o){
        	return 0;
        }
        int resourceCount = ((Long) o)
                .intValue();
        return resourceCount;        
	}
	/**
	 * 推荐资源积分
	 * @return
	 */
	public float  GetRecommendResourceScore(int resourceCount){
		String hql="SELECT value FROM Config WHERE name = 'score.resource.rcmd'";
		Object o = this.getSession().createQuery(hql).iterate().next();
		if ( o != null){
			return (float)Integer.parseInt(o.toString()) * resourceCount;
		}else{
			return 0;
		}
		
	}
	/**
	 * 推荐资源罚分
	 * @return
	 */
			
	public float  GetRecommendResourcePunishScore(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;		
        if(null != endday && endday.length()>0){
        	hql = "SELECT SUM(score) FROM UPunishScore WHERE objType=12 AND objId IN(SELECT id FROM Resource WHERE recommendState=true AND subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId) AND (createDate BETWEEN '" + startday + "' And '" + endday + "'))";
        }else{
        	hql = "SELECT SUM(score) FROM UPunishScore WHERE objType=12 AND objId IN(SELECT id FROM Resource WHERE recommendState=true AND subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId))";
        }
        Object o = this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if (null == o){
        	return 0;
        }
        float score = Float.parseFloat(o.toString());
        return score;           
	}
	/**
	 * 评论数
	 * objType  3=文章     12=资源  15=备课  17=视频
	 * 
	 * @return
	 */
	public int GetCommentCount(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;	
        //3 Article  subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId)
        //12 Resource subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId)
        //15 PrepareCourse metaSubjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId)
        //17 Video subjectId = :subjectId And gradeId BETWEEN :startGradeId And :endGradeId
        String sWhereArticle;
        String sWhereResource;
        String sWherePrepareCourse;
        String sWhereVideo;
        if(null != endday && endday.length()>0){
        	sWhereArticle = "objType=3 AND objId IN(SELECT id FROM Article WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId) AND (createDate BETWEEN '" + startday + "' And '" + endday + "'))";
        	sWhereResource = "objType=12 AND objId IN(SELECT id FROM Resource WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId) AND (createDate BETWEEN '" + startday + "' And '" + endday + "'))";
        	sWherePrepareCourse = "objType=15 AND objId IN(SELECT prepareCourseId FROM PrepareCourse WHERE status = 0 And metaSubjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId) AND (createDate BETWEEN '" + startday + "' And '" + endday + "'))";
        	sWhereVideo = "objType=17 AND objId IN(SELECT id FROM Video WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId) AND (createDate BETWEEN '" + startday + "' And '" + endday + "'))";
        }else{
        	sWhereArticle = "objType=3 AND objId IN(SELECT id FROM Article WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId))";
        	sWhereResource = "objType=12 AND objId IN(SELECT id FROM Resource WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId))";
        	sWherePrepareCourse = "objType=15 AND objId IN(SELECT prepareCourseId FROM PrepareCourse WHERE status = 0 And metaSubjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId))";
        	sWhereVideo = "objType=17 AND objId IN(SELECT id FROM Video WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId))";
        }
        
        hql = "SELECT COUNT(*) FROM Comment WHERE ("+sWhereArticle+") OR ("+sWhereResource+") OR ("+sWherePrepareCourse+") OR ("+sWhereVideo+")";
      	
        Object o = this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if(null ==o ){
        	return 0;
        }
        int commentCount = ((Long) o)
                .intValue();
        return commentCount;          
	}
	/**
	 * 评论积分
	 * @return
	 */
	public float GetCommentScore(int commentCount){
		String hql="SELECT value FROM Config WHERE name = 'score.comment.add'";
		Object o = this.getSession().createQuery(hql).iterate().next();
		if ( o != null){
			return (float)Integer.parseInt(o.toString()) * commentCount;
		}else{
			return 0;
		}			
	}
	/**
	 * 评论罚分
	 * @return
	 */
	public float GetCommentPunishScore(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;	
        String sWhereArticle;
        String sWhereResource;
        String sWherePrepareCourse;
        String sWhereVideo;
        if(null != endday && endday.length()>0){
        	sWhereArticle = "objType=3 AND objId IN(SELECT id FROM Article WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId) AND (createDate BETWEEN '" + startday + "' And '" + endday + "'))";
        	sWhereResource = "objType=12 AND objId IN(SELECT id FROM Resource WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId) AND (createDate BETWEEN '" + startday + "' And '" + endday + "'))";
        	sWherePrepareCourse = "objType=15 AND objId IN(SELECT prepareCourseId FROM PrepareCourse WHERE status = 0 And metaSubjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId) AND (createDate BETWEEN '" + startday + "' And '" + endday + "'))";
        	sWhereVideo = "objType=17 AND objId IN(SELECT id FROM Video WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId) AND (createDate BETWEEN '" + startday + "' And '" + endday + "'))";
        }else{
        	sWhereArticle = "objType=3 AND objId IN(SELECT id FROM Article WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId))";
        	sWhereResource = "objType=12 AND objId IN(SELECT id FROM Resource WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId))";
        	sWherePrepareCourse = "objType=15 AND objId IN(SELECT prepareCourseId FROM PrepareCourse WHERE status = 0 And metaSubjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId))";
        	sWhereVideo = "objType=17 AND objId IN(SELECT id FROM Video WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId))";
        }
        
        String CommentHql = "SELECT id FROM Comment WHERE ("+sWhereArticle+") OR ("+sWhereResource+") OR ("+sWherePrepareCourse+") OR ("+sWhereVideo+")";
        hql = "SELECT SUM(score) FROM UPunishScore WHERE objType="+ ObjectType.OBJECT_TYPE_COMMENT.getTypeId() +" AND objId IN ("+CommentHql+")";
        
        Object o = this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if(null ==o){
        	return 0;
        }
        float score =Float.parseFloat(o.toString());
        
        return score;
	}
	/**
	 * 协作组数
	 * groupState  //群组状态：0 - 正常，1 - 待审核，2 - 锁定，3 - 待删除，4 - 隐藏 (使用 GROUP_STATE_XXX 常量)
	 * @return
	 */
	public int GetGroupCount(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;		
        hql = "SELECT COUNT(*) FROM Group WHERE subjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId)";
        if(null != endday && endday.length()>0){
        	hql = hql + " And (createDate BETWEEN '" + startday + "' And '" + endday + "')";
        }    
        Object o = this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if(null ==o){
        	return 0;
        }
        int groupCount = ((Long) o)
                .intValue();  
        return groupCount;
	}
	/**
	 * 备课数
	 * status  // 状态 0:正常，1：待审核，2：锁定，其他未知 
	 * @return
	 */
	public int GetPrepareCourseCountCount(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;	
        hql = "SELECT COUNT(*) FROM PrepareCourse WHERE status = 0 And metaSubjectId = :subjectId And (gradeId BETWEEN :startGradeId And :endGradeId)";
        if(null != endday && endday.length()>0){
        	hql = hql + " And (createDate BETWEEN '" + startday + "' And '" + endday + "')";
        }         
        Object o = this.getSession().createQuery(hql).setInteger("subjectId", metaSubjId)
                .setInteger("startGradeId", startGradeId).setInteger("endGradeId", endGradeId).iterate().next();
        if(null ==o){
        	return 0;
        }
        int prepareCourseCount = ((Long) o)
                .intValue(); 
        return prepareCourseCount;
	}
	/**
	 * 活动数
	 * @return
	 */
	public int GetActionCount(){
		if(null == subject){
			return 0;
		}
        if(null == startday || startday.length()==0){
        	startday = "2000-01-01 00:00:00";
        }		
        String hql;	
        hql = "SELECT COUNT(*) FROM Action WHERE ownerType = 'subject' And ownerId = :ownerId";
        if(null != endday && endday.length()>0){
        	hql = hql + " And (createDate BETWEEN '" + startday + "' And '" + endday + "')";
        }        
        Object o =this.getSession().createQuery(hql).setInteger("ownerId", subject.getSubjectId())
                .iterate().next();
        if(null == o){
        	return 0;
        }
        int actionCount = ((Long) o).intValue();
        return actionCount;
	}
	
	
	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
		if(null != subject){
			this.metaSubjId = subject.getMetaSubject().getMsubjId();
			this.startGradeId = subject.getMetaGrade().getStartId();
			this.endGradeId = subject.getMetaGrade().getEndId() - 1;
		}
	}

	public String getStartday() {
		return startday;
	}

	public void setStartday(String startday) {
		this.startday = startday;
	}

	public String getEndday() {
		return endday;
	}

	public void setEndday(String endday) {
		this.endday = endday;
	}
	
}
