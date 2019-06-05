package cn.edustar.jitar.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UPunishScoreService;

import cn.edustar.jitar.dao.hibernate.SubjectStatDaoHibernate;
import cn.edustar.jitar.pojos.SubjectStat;
import cn.edustar.jitar.pojos.Subject;

/**
 * 学科统计服务
 * 统计项目  SubjectStat
 * 学科不会太多，不分页处理
 *  
 * 统计文章条件：hideState=0  		//不隐藏
 * 			 draftState=false   //草稿不统计
 * 			 delState=false 	//删除的不统计
 * 
 * 原来学科表中的数据统计见 TimerCountDaoHibernate.java 中的函数 doSubjectCount
 * @author dell
 *
 */
public class SubjectStatServiceImpl {
	/*统计需要的服务**/
	private SubjectService subjectService;
	private ArticleService articleService;
	private ResourceService resourceService;
	private CommentService commentService;
	private UPunishScoreService uPunishScoreService;
	private SubjectStatDaoHibernate subjectStatDaoHibernate;
	/*统计条件*/
	/**
	 * searchKey 只查询学科列表
	 */
	private String searchKey;
	
	/**
	 * 时间条件：是为了统计的时间段
	 */
	private Date beginDate;
	private Date endDate;
	
	/*统计处理过程*/
	public List<SubjectStat> StatSubjects(){
		List<Subject> list = null ;
		if(null == searchKey || searchKey.length()==0){
			list = subjectService.getSubjectList();
		}else{
			list = subjectService.getSubjectList(searchKey);
		}
		if(null == list){
			return null;
		}else{
			List<SubjectStat> listStat = new ArrayList<SubjectStat>();
			for(int i = 0; i < list.size(); i++){
				Subject sub = list.get(i);
				SubjectStat stat = StatSubject(sub);
				listStat.add(stat);
			}
			return listStat;
		}
	}
	
	public SubjectStat StatSubject(Subject sub){
		int subjectId;						//学科ID
		String subjectName;					//学科名称
		int userCount = 0;						//工作室总数
		int originalArticleCount = 0;			//原创文章数		typeState=0
		float originalArticleScore = 0;			//原创文章积分
		int referencedArticleCount = 0;			//转载文章数		typeState=1
		float referencedArticleScore = 0;		//转载文章积分
		int recommendArticleCount = 0;			//推荐文章数		recommendState=true
		float recommendArticleScore = 0;		//推荐文章积分
		int resourceCount = 0;					//资源数
		float resourceScore = 0;				//资源积分
		int recommendResourceCount = 0;			//推荐资源数
		float  recommendResourceScore = 0;		//推荐资源积分
		int commentCount = 0;					//评论数
		float commentScore = 0;					//评论积分
		int groupCount = 0;						//协作组数
		int prepareCourseCountCount = 0;		//协作组数
		int actionCount = 0;					//活动数		
		
		SubjectStat stat = new SubjectStat();
		
		//装载统计数据
		subjectId = sub.getSubjectId();
		subjectName = sub.getSubjectName();
		
		subjectStatDaoHibernate.setSubject(sub);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		if(beginDate != null){
			String startday = sdf.format(beginDate);
			subjectStatDaoHibernate.setStartday(startday+" 00:00:00");
		}
		if(endDate != null){
			String endday = sdf.format(endDate);
			subjectStatDaoHibernate.setEndday(endday+" 23:59:59.999");
		}
		
		commentCount = subjectStatDaoHibernate.GetCommentCount();
		commentScore = subjectStatDaoHibernate.GetCommentScore(commentCount);
		originalArticleCount = subjectStatDaoHibernate.GetOriginalArticleCount();
		originalArticleScore = subjectStatDaoHibernate.GetOriginalArticleScore(originalArticleCount);
		recommendArticleCount = subjectStatDaoHibernate.GetRecommendArticleCount();
		recommendArticleScore = subjectStatDaoHibernate.GetRecommendArticleScore(recommendArticleCount);
		recommendResourceCount = subjectStatDaoHibernate.GetRecommendResourceCount();
		recommendResourceScore = subjectStatDaoHibernate.GetRecommendResourceScore(recommendResourceCount);
		referencedArticleCount = subjectStatDaoHibernate.GetReferencedArticleCount();
		referencedArticleScore = subjectStatDaoHibernate.GetReferencedArticleScore(referencedArticleCount);
		resourceCount = subjectStatDaoHibernate.GetResourceCount();
		resourceScore = subjectStatDaoHibernate.GetResourceScore(resourceCount);
		userCount = subjectStatDaoHibernate.GetALLUserCount();
		groupCount = subjectStatDaoHibernate.GetGroupCount();
		actionCount = subjectStatDaoHibernate.GetActionCount();
		prepareCourseCountCount = subjectStatDaoHibernate.GetPrepareCourseCountCount();
		
		stat.setSubjectId(subjectId);
		stat.setSubjectName(subjectName);
		stat.setUserCount(userCount);
		stat.setOriginalArticleCount(originalArticleCount);
		stat.setOriginalArticleScore(originalArticleScore);
		stat.setReferencedArticleCount(referencedArticleCount);
		stat.setReferencedArticleScore(referencedArticleScore);
		stat.setRecommendArticleCount(recommendArticleCount);
		stat.setRecommendArticleScore(recommendArticleScore);
		stat.setResourceCount(resourceCount);
		stat.setResourceScore(resourceScore);
		stat.setRecommendResourceCount(recommendResourceCount);
		stat.setRecommendResourceScore(recommendResourceScore);
		stat.setCommentCount(commentCount);
		stat.setCommentScore(commentScore);
		stat.setActionCount(actionCount);
		stat.setGroupCount(groupCount);
		stat.setPrepareCourseCountCount(prepareCourseCountCount);	
		return stat;
	}
	public SubjectService getSubjectService() {
		return subjectService;
	}
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	public ArticleService getArticleService() {
		return articleService;
	}
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	public ResourceService getResourceService() {
		return resourceService;
	}
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	public CommentService getCommentService() {
		return commentService;
	}
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	public UPunishScoreService getuPunishScoreService() {
		return uPunishScoreService;
	}
	public void setuPunishScoreService(UPunishScoreService uPunishScoreService) {
		this.uPunishScoreService = uPunishScoreService;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public SubjectStatDaoHibernate getSubjectStatDaoHibernate() {
		return subjectStatDaoHibernate;
	}

	public void setSubjectStatDaoHibernate(SubjectStatDaoHibernate subjectStatDaoHibernate) {
		this.subjectStatDaoHibernate = subjectStatDaoHibernate;
	}
}

