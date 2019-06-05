package cn.edustar.jitar.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import cn.edustar.data.Pager;
import cn.edustar.data.hql.QueryHelper;
import cn.edustar.jitar.dao.PrepareCourseDao;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.PrepareCourseArticle;
import cn.edustar.jitar.pojos.PrepareCourseEdit;
import cn.edustar.jitar.pojos.PrepareCourseMember;
import cn.edustar.jitar.pojos.PrepareCoursePlan;
import cn.edustar.jitar.pojos.PrepareCoursePrivateComment;
import cn.edustar.jitar.pojos.PrepareCourseRelated;
import cn.edustar.jitar.pojos.PrepareCourseResource;
import cn.edustar.jitar.pojos.PrepareCourseStage;
import cn.edustar.jitar.pojos.PrepareCourseTopic;
import cn.edustar.jitar.pojos.PrepareCourseTopicReply;
import cn.edustar.jitar.pojos.PrepareCourseVideo;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.PrepareCourseMemberQueryParam;



public class PrepareCourseDaoHibernate extends BaseDaoHibernate implements PrepareCourseDao {

	/**
	 * 创建一个集备
	 */
	public void createPrepareCourse(PrepareCourse prepareCourse)
	{
		this.getSession().saveOrUpdate(prepareCourse);
		this.getSession().flush();
	}
	
	/**
	 * 根据标识加载集备
	 */
	public PrepareCourse getPrepareCourse(int prepareCourseId)
	{
		return (PrepareCourse)this.getSession().get(PrepareCourse.class, prepareCourseId);		
	}
	
	/**
	 * 根据全球唯一标识符加载集备
	 */
	@SuppressWarnings("unchecked")
	public PrepareCourse getPrepareCourseByGuid(String objectGuid)
	{
		List<PrepareCourse> pclist = (List<PrepareCourse>)this.getSession().createQuery("FROM PrepareCourse Where objectGuid = ?").setString(0, objectGuid).list();
		if(pclist != null && pclist.size() > 0) return (PrepareCourse)pclist.get(0);
		return null;
	}
	
	/**
	 * 根据id删除集备
	 */
	public void deletePrepareCourse(int prepareCourseId)
	{
		String queryString = "DELETE PrepareCourseTopicReply WHERE prepareCourseId = ?";
		this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
		
		queryString = "DELETE PrepareCourseTopic WHERE prepareCourseId = ?";
		this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
		
		queryString = "DELETE PrepareCourseResource WHERE prepareCourseId = ?";
		this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
		
		queryString = "DELETE PrepareCourseArticle WHERE prepareCourseId = ?";
		this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
		
		queryString = "DELETE PrepareCoursePrivateComment WHERE prepareCourseId = ?";
		this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
		
		queryString = "DELETE PrepareCourseEdit WHERE prepareCourseId = ?";
		this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
		
		queryString = "DELETE PrepareCourseStage WHERE prepareCourseId = ?";
		this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
		
		queryString = "DELETE PrepareCourseMember WHERE prepareCourseId = ?";
		this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
		
		queryString = "DELETE PrepareCourse WHERE prepareCourseId = ?";
		this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
	}
	public void updatePrepareCourse(PrepareCourse prepareCourse)
	{
		this.getSession().clear();
		this.getSession().saveOrUpdate(prepareCourse);
		this.getSession().flush();
	}
	
	/* 检查是否是合法的成员 */
	@SuppressWarnings("unchecked")
	public boolean checkUserInPreCourse(int prepareCourseId, int userId)
	{
		String queryString = " FROM PrepareCourseMember Where status = 0 And prepareCourseId = ? And userId = ?";
		List<PrepareCourseMember> pcm = (List<PrepareCourseMember>)this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).setInteger(1, userId).list();
		if(pcm.size() > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * 增加视频
	 */
	@SuppressWarnings("unchecked")
	public void insertVideoToPrepareCourse(int prepareCourseId,int videoId,int userId,String videoTitle)
	{
		String queryString = " FROM PrepareCourseVideo Where prepareCourseId = ? And videoId = ?";
		List<PrepareCourseVideo> pcm = (List<PrepareCourseVideo>)this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).setInteger(1, videoId).list();//this.getSession().find(queryString, new Object[]{ prepareCourseId, videoId });
		if(pcm.size() > 0)
			return;
		
		PrepareCourseVideo pcv=new PrepareCourseVideo();
		pcv.setPrepareCourseId(prepareCourseId);
		pcv.setCreateDate(new java.util.Date());
		pcv.setVideoId(videoId);
		pcv.setUserId(userId);
		pcv.setVideoTitle(videoTitle);
		this.getSession().save(pcv);
		this.getSession().flush();
		
	}
	
	@SuppressWarnings("unchecked")
	public PrepareCourseVideo getPrepareCourseVideo(int prepareCourseVideoId){
		String queryString = " FROM PrepareCourseVideo Where prepareCourseVideoId = ? ";
		List<PrepareCourseVideo> pcm = (List<PrepareCourseVideo>)this.getSession().createQuery(queryString).setInteger(0, prepareCourseVideoId).list();
		if(pcm.size() > 0){
			return pcm.get(0);
		}
		else{
			return null;
		}
	}
	
	/**
	 * 删除视频
	 */
	@SuppressWarnings("unchecked")
	public void removePrepareCourseVideo(int prepareCourseId,int videoId)
	{
		String queryString = "DELETE PrepareCourseVideo WHERE prepareCourseId = ? And videoId = ?";
		this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).setInteger(1, videoId).executeUpdate();
		
	}
	/* 检查是否是成员 */
	@SuppressWarnings("unchecked")
	public boolean checkUserExistsInPrepareCourse(int prepareCourseId, int userId)
	{
		String queryString = " FROM PrepareCourseMember Where prepareCourseId = ? And userId = ?";
		List<PrepareCourseMember> pcm = (List<PrepareCourseMember>)this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).setInteger(1, userId).list();
		if(pcm.size() > 0)
			return true;
		else
			return false;
	}
	
	/** 检查是否是管理员 */
	@SuppressWarnings("unchecked")
	public boolean checkUserCanManagePreCourse(int prepareCourseId, User user)
	{
		if (user == null) return false;
		
		
		String queryString = " FROM PrepareCourse Where (createUserId = ? or leaderId = ?) And prepareCourseId = ?";
		List<PrepareCourse> pcm = (List<PrepareCourse>)this.getSession().createQuery(queryString)
				.setInteger(0, user.getUserId())
				.setInteger(1,  user.getUserId())
				.setInteger(2, prepareCourseId)
				.list();
		if(pcm.size() > 0)
			return true;
		else
			return false;
	}
	
	public void addViewCount(int prepareCourseId)
	{
		String queryString = "UPDATE PrepareCourse SET viewCount = viewCount + 1 WHERE prepareCourseId = ?";
		this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public PrepareCourseArticle getPrepareCourseArticle(int prepareCourseArticleId){
		String queryString = " FROM PrepareCourseArticle Where prepareCourseArticleId = ? ";
		List<PrepareCourseArticle> pcm = (List<PrepareCourseArticle>)this.getSession().createQuery(queryString).setInteger(0, prepareCourseArticleId).list();
		if(pcm.size() > 0){
			return pcm.get(0);
		}
		else{
			return null;
		}
	
	}
	
	@SuppressWarnings("unchecked")
	public PrepareCourseResource getPrepareCourseResource(int prepareCourseResourceId){
		String queryString = " FROM PrepareCourseResource Where prepareCourseResourceId = ? ";
		List<PrepareCourseResource> pcm = (List<PrepareCourseResource>)this.getSession().createQuery(queryString).setInteger(0, prepareCourseResourceId).list();
		if(pcm.size() > 0){
			return pcm.get(0);
		}
		else{
			return null;
		}		
	}
	/**
	 * 备课统计信息
	 */
	public void countPrepareCourseData(int prepareCourseId)
	{
		Long memberCount=0L,articleCount=0L,resourceCount=0L,actionCount=0L,topicCount=0L,topicReplyCount = 0L ;
		String queryString = "SELECT COUNT(*) FROM PrepareCourseArticle WHERE prepareCourseId = " + prepareCourseId;
		articleCount = ((Long) this.getSession().createQuery(queryString).iterate().next()).longValue();
		
		queryString = "SELECT COUNT(*) FROM PrepareCourseResource WHERE prepareCourseId = " + prepareCourseId;
		resourceCount = ((Long) this.getSession().createQuery(queryString).iterate().next()).longValue();
	
		queryString = "SELECT COUNT(*) FROM Action WHERE ownerType = 'course' AND ownerId = " + prepareCourseId;
		actionCount =  ((Long) this.getSession().createQuery(queryString).iterate().next()).longValue();
		
		
		queryString = "SELECT COUNT(*) FROM PrepareCourseMember WHERE prepareCourseId = " + prepareCourseId;
		memberCount = ((Long) this.getSession().createQuery(queryString).iterate().next()).longValue();
		
		queryString = "SELECT COUNT(*) FROM PrepareCourseTopic WHERE prepareCourseId = " + prepareCourseId;
		topicCount = ((Long) this.getSession().createQuery(queryString).iterate().next()).longValue();
		
		
		queryString = "SELECT COUNT(*) FROM PrepareCourseTopicReply WHERE prepareCourseId = " + prepareCourseId;
		topicReplyCount = ((Long) this.getSession().createQuery(queryString).iterate().next()).longValue();
		
		
		queryString = "UPDATE PrepareCourse SET topicReplyCount = ?, topicCount = ?,memberCount =?,actionCount=?,resourceCount=?,articleCount=? WHERE prepareCourseId = ?";
		this.getSession().createQuery(queryString)
		.setLong(0, topicReplyCount)
		.setLong(1, topicCount)
		.setLong(2, memberCount)
		.setLong(3, actionCount)
		.setLong(4, resourceCount)
		.setLong(5, articleCount)
		.setLong(6, prepareCourseId)
		.executeUpdate();		
	}
	
	public void addPrepareCourseMember(PrepareCourseMember prepareCourseMember)
	{
		this.getSession().save(prepareCourseMember);
		this.getSession().flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<PrepareCourseMember> getPrepareCourseMemberList(PrepareCourseMemberQueryParam param,Pager pager)
	{
		QueryHelper query = param.createQuery();
		if(pager == null)
		{
			return (List<PrepareCourseMember>)query.queryData(this.getSession(), -1, param.count);
		}
		else
		{
			return (List<PrepareCourseMember>)query.queryDataAndTotalCount(this.getSession(), pager);
		}
	}
	
	public PrepareCourseMember getPrepareCourseMemberByCourseIdAndUserId(int prepareCourseId,int userId)
	{
		String queryString = "FROM PrepareCourseMember WHERE status = 0 And prepareCourseId = :prepareCourseId And userId = :userId";
		List<PrepareCourseMember> ol = this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).setInteger("userId", userId).list();
		if(ol == null || ol.size() == 0) return null;
		return (PrepareCourseMember)ol.get(0);
	}
	
	public PrepareCourseMember getPrepareCourseMemberByCourseIdAndUserIdWithNoStatus(int prepareCourseId,int userId)
	{
		String queryString = "FROM PrepareCourseMember WHERE prepareCourseId = :prepareCourseId And userId = :userId";
		List<PrepareCourseMember> ol = this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).setInteger("userId", userId).list();
		if(ol == null || ol.size() == 0) return null;
		return (PrepareCourseMember)ol.get(0);
	}
	
	public void removePrepareCourseContent(int prepareCourseId,int userId)
	{
		PrepareCourse prepareCourse = this.getPrepareCourse(prepareCourseId);
		if(prepareCourse == null) return;
		String queryString ;
		queryString = "UPDATE PrepareCourseMember SET PrivateContent = null WHERE userId = :userId And prepareCourseId = :prepareCourseId";
		this.getSession().createQuery(queryString).setInteger("userId", userId).setInteger("prepareCourseId", prepareCourseId).executeUpdate();	

	}
	
	/**
	 * 得到个案的数量（非空
	 * @param prepareCourseId
	 * @return
	 */
	public Long getPrepareCourseContentCount(int prepareCourseId)
	{
		PrepareCourse prepareCourse = this.getPrepareCourse(prepareCourseId);
		if(prepareCourse == null) return 0L;
		String queryString ;
		queryString = "SELECT COUNT(prepareCourseMemberId) AS pcNumber FROM PrepareCourseMember WHERE DATALENGTH(privateContent)>0 And prepareCourseId = ?";
		return ((Long) this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).iterate().next()).longValue();
	}
	/**
	 * 得到共案的编辑次数
	 * @param prepareCourseId
	 * @return
	 */
	public Long getPrepareCourseEditCount(int prepareCourseId)
	{
		PrepareCourse prepareCourse = this.getPrepareCourse(prepareCourseId);
		if(prepareCourse == null) return 0L;
		String queryString ;
		queryString = "SELECT COUNT(prepareCourseEditId) AS editNumber FROM PrepareCourseEdit WHERE prepareCourseId = ?";
		return ((Long) this.getSession().createQuery(queryString).setInteger(0, prepareCourseId).iterate().next()).longValue();
	}
	public void setPrepareCourseMemberBest(int prepareCourseId,int userId,boolean status)
	{
		PrepareCourse prepareCourse = this.getPrepareCourse(prepareCourseId);
		if(prepareCourse == null) return;
		String queryString ;
		if(status)
			queryString = "UPDATE PrepareCourseMember SET bestState =1 WHERE userId = :userId And prepareCourseId = :prepareCourseId";
		else
			queryString = "UPDATE PrepareCourseMember SET bestState =0 WHERE userId = :userId And prepareCourseId = :prepareCourseId";
		
		this.getSession().createQuery(queryString).setInteger("userId", userId).setInteger("prepareCourseId", prepareCourseId).executeUpdate();
		
	}
	
	public PrepareCourseMember getPrepareCourseMemberById(int prepareCourseMemberId)
	{
		return (PrepareCourseMember)this.getSession().get(PrepareCourseMember.class, prepareCourseMemberId);
	}
	
	public void deletePrepareCourseMember(int prepareCourseId,int userId)
	{
		//删除回复，删除成员
		//不能删除创建者或者主备人
		PrepareCourse prepareCourse = this.getPrepareCourse(prepareCourseId);
		if(prepareCourse == null) return;
		if(prepareCourse.getCreateUserId().equals(userId) || prepareCourse.getLeaderId().equals(userId))
		{
			return;
		}
		String queryString ;
		List<PrepareCourseStage> pcs = this.getPrepareCourseStageList(prepareCourseId);
		if(pcs != null)
		{
			for(int i = 0;i<pcs.size(); i++)
			{
				queryString = "DELETE FROM PrepareCourseTopicReply WHERE prepareCourseStageId = :prepareCourseStageId And userId = :userId";
				PrepareCourseStage ps = (PrepareCourseStage)pcs.get(i);
				this.getSession().createQuery(queryString)
				.setInteger("prepareCourseStageId", ps.getPrepareCourseId())
				.setInteger("userId", userId)
				.executeUpdate();
			}
		}
		queryString = "DELETE FROM PrepareCourseMember WHERE prepareCourseId = :prepareCourseId And userId = :userId";
		this.getSession().createQuery(queryString)
		.setInteger("prepareCourseId", prepareCourseId)
		.setInteger("userId", userId)
		.executeUpdate();
		
	}
	
	public void updatePrepareCourseMember(PrepareCourseMember prepareCourseMember)
	{
		this.getSession().update(prepareCourseMember);
	}
	
	public void updatePrepareCourseStageIndexOrder(int prepareCoreseStageId,int orderIndex)
	{
		String queryString = "UPDATE PrepareCourseStage SET orderIndex = :orderIndex WHERE prepareCourseStageId = :prepareCourseStageId";
		this.getSession().createQuery(queryString)
		.setInteger("orderIndex", orderIndex)
		.setInteger("prepareCourseStageId", prepareCoreseStageId)
		.executeUpdate();
	}
	
	public void updateReplyCount(int prepareCourseId,int userId)
	{
		String queryString = "SELECT COUNT(*) FROM PrepareCourseStageReply WHERE prepareCourseId = :prepareCourseId AND userId = :userId";
		int count = ((Integer) this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).setInteger("userId", userId).iterate().next() ).intValue();
		queryString = "UPDATE PrepareCourseMember SET replyCount = :replyCount WHERE prepareCourseId = :prepareCourseId AND userId = :userId";
		this.getSession().createQuery(queryString)
		.setInteger("replyCount", count)
		.setInteger("prepareCourseId", prepareCourseId)
		.setInteger("userId", userId)
		.executeUpdate();

	}	
	
	@SuppressWarnings("unchecked")
	public List<PrepareCourseStage> getPrepareCourseStageList(int prepareCourseId)
	{
		String queryString = "FROM PrepareCourseStage WHERE prepareCourseId = :prepareCourseId ORDER BY orderIndex ASC";
		List<PrepareCourseStage> pcs = (List<PrepareCourseStage>)this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).list();
		if (pcs.size() >0)
			return pcs;
		else
			return null;
	}
	
/*	@SuppressWarnings("unchecked")
	public List<PrepareCourseStageReply> getPrepareCourseStageReplyList(int prepareCourseStageId)
	{
		String queryString = "FROM PrepareCourseStageReply WHERE prepareCourseStageId = ? ORDER BY prepareCourseStageReplyId ASC";
		List<PrepareCourseStageReply> pcsr = (List<PrepareCourseStageReply>)this.getSession().find(queryString, prepareCourseStageId);
		System.out.println(pcsr.size());
		
		if (pcsr.size() >0)
			return pcsr;
		else
			return null;
	}*/
	
	public PrepareCourseStage getPrepareCourseStage(int prepareCourseStageId)
	{
		return (PrepareCourseStage)this.getSession().get(PrepareCourseStage.class, prepareCourseStageId);
	}
	
	/**
	 * 获取集备所在的协作组
	 */
	public Group getGroupOfPrepareCourse(int prepareCourseId)
	{
		PrepareCourse p = this.getPrepareCourse(prepareCourseId);
		if(p == null)
		{
			return null;
		}
		
		PrepareCoursePlan pn = this.getPrepareCoursePlan(p.getPrepareCoursePlanId());
		if(pn == null)
		{
			return null;
		}
		
		Group g = (Group)this.getSession().get(Group.class, pn.getGroupId());
		return g;
	}
	
	/**
	 * 删除讨论回复
	 */
	public void deletePrepareCourseTopicReply(int prepareCourseId, int prepareCourseTopicReplyId)
	{
		String queryString = "DELETE FROM PrepareCourseTopicReply WHERE prepareCourseTopicReplyId = :prepareCourseTopicReplyId";
		this.getSession().createQuery(queryString).setInteger("prepareCourseTopicReplyId", prepareCourseTopicReplyId).executeUpdate();
		//更新统计信息
		this.countPrepareCourseData(prepareCourseId);		
	}		
	
	public void deletePrepareCourseEdit(int prepareCourseEditId)
	{
		String queryString = "DELETE FROM PrepareCourseEdit WHERE prepareCourseEditId = :prepareCourseEditId";
		this.getSession().createQuery(queryString).setInteger("prepareCourseEditId", prepareCourseEditId).executeUpdate();
		
	}
	/* 备课文章 */
	public void savePrepareCourseArticle(PrepareCourseArticle prepareCourseArticle)
	{
		this.getSession().save(prepareCourseArticle);
	}

	public void updateArticleToPrepareCourseStage(Article article)
	{
		String queryString = "UPDATE PrepareCourseArticle SET title = :title WHERE articleId = :articleId";
		this.getSession().createQuery(queryString).setString("title", article.getTitle()).setInteger("articleId",  article.getArticleId()).executeUpdate();
	}
	public void deleteArticleToPrepareCourseStage(Article article)
	{
		String queryString = "DELETE FROM PrepareCourseArticle WHERE articleId = :articleId";
		this.getSession().createQuery(queryString).setInteger("articleId", article.getArticleId()).executeUpdate();
	}
	//删除流程文章
	public void deleteArticleToPrepareCourseStageById(int prepareCourseArticleId)
	{
		String queryString = "DELETE PrepareCourseArticle WHERE prepareCourseArticleId = :prepareCourseArticleId";
		this.getSession().createQuery(queryString).setInteger("prepareCourseArticleId", prepareCourseArticleId).executeUpdate();
		
	}
	//修改文章的流程
	public void moveArticleToPrepareCourseStageById(int prepareCourseArticleId,int prepareCourseStageId)
	{
		String queryString = "UPDATE PrepareCourseArticle SET prepareCourseStageId = :prepareCourseStageId WHERE prepareCourseArticleId = :prepareCourseArticleId";
		this.getSession().createQuery(queryString).setInteger("prepareCourseStageId", prepareCourseStageId).setInteger("prepareCourseArticleId", prepareCourseArticleId).executeUpdate();
		
		
	}
	public void createPrepareCourseStage(PrepareCourseStage prepareCourseStage)
	{
		this.getSession().save(prepareCourseStage);
	}
	
	public void updatePrepareCourseStage(PrepareCourseStage prepareCourseStage)
	{
		this.getSession().update(prepareCourseStage);
	}
	
	@SuppressWarnings("unchecked")
	public void deletePrepareCourseStage(int prepareCourseStageId)
	{
		String queryString = "DELETE FROM PrepareCourseArticle WHERE prepareCourseStageId = :prepareCourseStageId";
		this.getSession().createQuery(queryString).setInteger("prepareCourseStageId", prepareCourseStageId).executeUpdate();
		
		queryString = "DELETE FROM PrepareCourseResource WHERE prepareCourseStageId = :prepareCourseStageId";
		this.getSession().createQuery(queryString).setInteger("prepareCourseStageId", prepareCourseStageId).executeUpdate();
		
		queryString = "DELETE FROM PrepareCourseResource WHERE prepareCourseStageId = :prepareCourseStageId";
		this.getSession().createQuery(queryString).setInteger("prepareCourseStageId", prepareCourseStageId).executeUpdate();
		
		//删除讨论
		queryString = "FROM PrepareCourseTopic WHERE prepareCourseStageId = :prepareCourseStageId";
		List<PrepareCourseTopic> pctr = (List<PrepareCourseTopic>)this.getSession().createQuery(queryString).setInteger("prepareCourseStageId", prepareCourseStageId).list();
		for(int i =0;i<pctr.size();i++)
		{
			PrepareCourseTopic pct = (PrepareCourseTopic)pctr.get(i);
			int pctId = pct.getPrepareCourseTopicId();
			queryString = "DELETE FROM PrepareCourseTopicReply WHERE prepareCourseTopicId = :prepareCourseTopicId";
			this.getSession().createQuery(queryString).setInteger("prepareCourseTopicId", pctId).executeUpdate();
			
			
			queryString = "DELETE FROM PrepareCourseTopic WHERE prepareCourseTopicId = :prepareCourseTopicId";
			this.getSession().createQuery(queryString).setInteger("prepareCourseTopicId", pctId).executeUpdate();
		}
		
		queryString = "DELETE FROM PrepareCourseStage WHERE prepareCourseStageId = :prepareCourseStageId";
		this.getSession().createQuery(queryString).setInteger("prepareCourseStageId", prepareCourseStageId).executeUpdate();
	}
	
/*	public void createPrepareCourseStageReply(PrepareCourseStageReply prepareCourseStageReply)
	{
		this.getSession().save(prepareCourseStageReply);
	}*/
	
	public int getMaxCourseStageOrderIndex(int prepareCourseId)
	{
		String queryString = "SELECT MAX(orderIndex) FROM PrepareCourseStage Where prepareCourseId = :prepareCourseId";
		Object o = this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).uniqueResult();
		if(o == null) return 0;
		
		return Integer.valueOf(o.toString()).intValue(); // ((Integer) .iterate().next()).intValue();
	}
	
	public PrepareCourseStage getCurrentPrepareCourseStage(int prepareCourseId)
	{
		java.util.Date d = new Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		String stringDate = s.format(d);
		
		String queryString = "FROM PrepareCourseStage WHERE prepareCourseId = :prepareCourseId AND beginDate <= '" + stringDate + "' And finishDate >= '" + stringDate + "' Order BY prepareCourseStageId DESC";
		List<PrepareCourseStage> ls = this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).list();
		if(ls == null || ls.size() == 0) return null;
		PrepareCourseStage currentPrepareCourseStage = (PrepareCourseStage)ls.get(0);
		return currentPrepareCourseStage;
	}
	
	/* 共同编写 */
	public PrepareCourseEdit checkPrepareCourseEditIsLocked(int prepareCourseId)
	{	
		String queryString = "FROM PrepareCourseEdit Where lockStatus = 1 And prepareCourseId = :prepareCourseId ";
		List<PrepareCourseEdit> ls = this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).list();
		if(ls == null || ls.size() == 0) return null;
		PrepareCourseEdit prepareCourseEdit = (PrepareCourseEdit)ls.get(0);
		return prepareCourseEdit;
	}	
	
	public void updatePrepareCourseEdit(PrepareCourseEdit prepareCourseEdit){
		this.getSession().save(prepareCourseEdit);
		this.getSession().flush();
	}
		
	public PrepareCourseEdit getLastestPrepareCourseEdit(int prepareCourseId, int editUserId)
	{
		String queryString = "FROM PrepareCourseEdit Where prepareCourseId = :prepareCourseId Order By prepareCourseEditId DESC";
		List<PrepareCourseEdit> ls = this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).list();
		if(ls == null || ls.size() == 0) return null;
		PrepareCourseEdit prepareCourseEdit = (PrepareCourseEdit)ls.get(0);
		//锁住记录
		if (prepareCourseEdit == null)
		{
			//创建一条
			//this.getSession().setCheckWriteOperations(false);
			PrepareCourseEdit prepareCourseEditNew = new PrepareCourseEdit();
			prepareCourseEditNew.setContent("");
			prepareCourseEditNew.setEditDate(new Date());
			prepareCourseEditNew.setEditUserId(editUserId);
			prepareCourseEditNew.setLockStatus(1);
			prepareCourseEditNew.setPrepareCourseId(prepareCourseId);
			this.getSession().save(prepareCourseEditNew);
			//this.getSession().setCheckWriteOperations(true);
			return prepareCourseEditNew;			
		}
		else
		{
			if(prepareCourseEdit.getLockStatus() == 1)
			{
				if(prepareCourseEdit.getEditUserId() == editUserId)
				{
					return prepareCourseEdit;
				}
				else
				{
					return null;
				}
			}
			else
			{
				//this.getSession().setCheckWriteOperations(false);
				PrepareCourseEdit prepareCourseEditNew2 = new PrepareCourseEdit();
				prepareCourseEditNew2.setContent(prepareCourseEdit.getContent());
				prepareCourseEditNew2.setEditDate(new Date());
				prepareCourseEditNew2.setEditUserId(editUserId);
				prepareCourseEditNew2.setLockStatus(1);
				prepareCourseEditNew2.setPrepareCourseId(prepareCourseId);
				this.getSession().save(prepareCourseEditNew2);
				//this.getSession().setCheckWriteOperations(true);
				return prepareCourseEditNew2;			
			}
		}		
	}
	
	@SuppressWarnings("unchecked")
	public List<PrepareCourseEdit> getPrepareCourseEditList(int prepareCourseId)
	{
		String queryString = "FROM PrepareCourseEdit Where prepareCourseId = :prepareCourseId Order By prepareCourseEditId";
		return (List<PrepareCourseEdit>)this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).list();
	}
	
	public PrepareCourseEdit getPrepareCourseEdit(int prepareCourseEditId)
	{
		return (PrepareCourseEdit)this.getSession().get(PrepareCourseEdit.class, prepareCourseEditId);
	}
	
	//////资源的管理
	public void savePrepareCourseResource(PrepareCourseResource prepareCourseResource)
	{
		this.getSession().save(prepareCourseResource);
	}
	public void updateResourceToPrepareCourseStage(Resource resource)
	{
		String queryString = "UPDATE PrepareCourseResource SET resourceTitle = :resourceTitle WHERE resourceId = :resourceId";		
		this.getSession().createQuery(queryString).setString("resourceTitle", resource.getTitle()).setInteger("resourceId", resource.getResourceId()).executeUpdate();
	}
	public void deleteResourceToPrepareCourseStage(Resource resource)
	{
		String queryString = "DELETE FROM PrepareCourseResource WHERE resourceId = :resourceId";
		this.getSession().createQuery(queryString).setInteger("resourceId", resource.getResourceId()).executeUpdate();
	}
	
	//删除流程资源
	public void deleteResourceToPrepareCourseStageById(int prepareCourseResourceId)
	{
		String queryString = "DELETE FROM PrepareCourseResource WHERE prepareCourseResourceId = :prepareCourseResourceId";
		this.getSession().createQuery(queryString).setInteger("prepareCourseResourceId", prepareCourseResourceId).executeUpdate();
	}
	//移动资源流程
	public void moveResourceToPrepareCourseStageById(int prepareCourseResourceId,int prepareCourseStageId)
	{
		String queryString = "UPDATE PrepareCourseResource SET prepareCourseStageId = :prepareCourseStageId WHERE prepareCourseResourceId = :prepareCourseResourceId";
		this.getSession().createQuery(queryString).setInteger("prepareCourseStageId", prepareCourseResourceId).setInteger("prepareCourseResourceId", prepareCourseResourceId).executeUpdate();
	}
	
	
	////讨论	
	public void savePrepareCourseTopic(PrepareCourseTopic prepareCourseTopic)
	{
		this.getSession().save(prepareCourseTopic);
	}
	public void deletePrepareCourseTopic(int prepareCourseTopicId)
	{
		String queryString = "DELETE FROM PrepareCourseTopic WHERE prepareCourseTopicId = :prepareCourseTopicId";
		this.getSession().createQuery(queryString).setInteger("prepareCourseTopicId", prepareCourseTopicId).executeUpdate();
	}
	public PrepareCourseTopic getPrepareCourseTopic(int prepareCourseTopicId)
	{
		return (PrepareCourseTopic)this.getSession().get(PrepareCourseTopic.class, prepareCourseTopicId);
	}
	public void savePrepareCourseTopicReply(PrepareCourseTopicReply prepareCourseTopicReply)
	{
		this.getSession().save(prepareCourseTopicReply);		
	}
	
	/* 评论 */
	public void addPrepareCoursePrivateComment(PrepareCoursePrivateComment prepareCoursePrivateComment)
	{
		this.getSession().save(prepareCoursePrivateComment);
	}
	public void deletePrepareCoursePrivateComment(int prepareCoursePrivateCommentId)
	{
		String queryString = "DELETE FROM PrepareCoursePrivateComment WHERE prepareCoursePrivateCommentId = :prepareCoursePrivateCommentId";
		this.getSession().createQuery(queryString).setInteger("prepareCoursePrivateCommentId", prepareCoursePrivateCommentId).executeUpdate();
		
	}
	
	/* 备课计划*/
	/** 加载一个备课计划 */
	public PrepareCoursePlan getPrepareCoursePlan(int prepareCoursePlanId)
	{
		return (PrepareCoursePlan)this.getSession().get(PrepareCoursePlan.class, prepareCoursePlanId);
	}
	
	/**
	 * 加载一个组内的默认计划
	 */
	public PrepareCoursePlan getDefaultPrepareCoursePlanOfGroup(int groupId)
	{
		String queryString = "FROM PrepareCoursePlan WHERE groupId= :groupId And defaultPlan=1";
		List<PrepareCoursePlan> ls = this.getSession().createQuery(queryString).setInteger("groupId", groupId).list();
		if(ls == null || ls.size() == 0) return null;
		return (PrepareCoursePlan)ls.get(0);
	}
	/** 保存备课计划 */
	public void saveOrUpdatePrepareCoursePlan(PrepareCoursePlan prepareCoursePlan)
	{
		this.getSession().saveOrUpdate(prepareCoursePlan);
	}
	
	/**
	 * 设置备课计划的默认值。
	 */
	public void setGroupDefaultPrepareCoursePlan(boolean defaultValue, int groupId)
	{
		String queryString = "UPDATE PrepareCoursePlan Set defaultPlan = :defaultPlan Where groupId = :groupId";
		this.getSession().createQuery(queryString).setBoolean("defaultPlan", defaultValue).setInteger("groupId", groupId).executeUpdate();
	}
	
	/**
	 * 获取某一备课计划下的所有集备
	 */
	@SuppressWarnings("unchecked")
	public List<PrepareCourse> getPrepareCourseListOfPlan(int prepareCoursePlanId)
	{
		String queryString = "FROM PrepareCourse Where prepareCoursePlanId = :prepareCoursePlanId";
		return (List<PrepareCourse>)this.getSession().createQuery(queryString).setInteger("prepareCoursePlanId",prepareCoursePlanId).list();
	}
	
	/**
	 * 删除一个备课计划
	 */
	public void deletePrepareCoursePlan(PrepareCoursePlan prepareCoursePlan)
	{
		this.getSession().delete(prepareCoursePlan);
	}
	
	/**
	 * 删除某用户在备课相关的所有数据
	 */
	@SuppressWarnings("unchecked")
	public void deleteAllPrepareCourseDataOfUser(int userId, int groupId)
	{
		String queryString;
		queryString = "FROM PrepareCoursePlan Where createUserId = :createUserId And groupId = :groupId";
		List<PrepareCoursePlan> plan = (List<PrepareCoursePlan>)this.getSession().createQuery(queryString).setInteger("createUserId", userId).setInteger("groupId", groupId).list();
		for(int i = 0;i<plan.size();i++)
		{
			PrepareCoursePlan pl = (PrepareCoursePlan)plan.get(i);
			List<PrepareCourse> pl_pc = this.getPrepareCourseListOfPlan(pl.getCreateUserId());
			for(int j =0;j<pl_pc.size();j++)
			{
				PrepareCourse pl_p = (PrepareCourse)pl_pc.get(j);
				this.deletePrepareCourse(pl_p.getPrepareCourseId());
			}
			this.deletePrepareCoursePlan(pl);
		}	
	}
	/**
	 * 删除某用户在备课相关的所有数据
	 */
	
	@SuppressWarnings("unchecked")
	public void deleteAllPrepareCourseDataOfUser(int userId)
	{
		String queryString;
		queryString = "FROM PrepareCoursePlan Where createUserId = :createUserId";
		List<PrepareCoursePlan> plan = (List<PrepareCoursePlan>)this.getSession().createQuery(queryString).setInteger("createUserId", userId).list();
		for(int i = 0;i<plan.size();i++)
		{
			PrepareCoursePlan pl = (PrepareCoursePlan)plan.get(i);
			List<PrepareCourse> pl_pc = this.getPrepareCourseListOfPlan(pl.getCreateUserId());
			for(int j =0;j<pl_pc.size();j++)
			{
				PrepareCourse pl_p = (PrepareCourse)pl_pc.get(j);
				this.deletePrepareCourse(pl_p.getPrepareCourseId());
			}
			this.deletePrepareCoursePlan(pl);
		}	
	}
	
	@SuppressWarnings("unchecked")
	public List<PrepareCourseVideo> getPrepareCourseVideoListByPrepareCourseId(int prepareCourseId)
	{
		String queryString;
		queryString = "FROM PrepareCourseVideo Where prepareCourseId = :prepareCourseId Order By prepareCourseVideoId DESC";
		return (List<PrepareCourseVideo>)this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).list();
	}
	
	@SuppressWarnings("unchecked")
	public boolean checkVideoInPrepareCourse(int prepareCourseId, int videoId)
	{
		String queryString;
		queryString = "FROM PrepareCourseVideo Where prepareCourseId = :prepareCourseId and videoId = :videoId";
		List<PrepareCourseVideo> pv = (List<PrepareCourseVideo>)this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).setInteger("videoId", videoId).list();//, new Object[]{prepareCourseId,videoId});
		return pv.size() > 0;
	}
	
	/**
	 * 得到相关集备
	 */
	@SuppressWarnings("unchecked")
	public List<PrepareCourse> getPrepareCourseRelatedListEx(int prepareCourseId)
	{
		String queryString = "FROM PrepareCourse Where prepareCourseId In(Select relatedPrepareCourseId FROM PrepareCourseRelated WHERE prepareCourseId = :prepareCourseId) ORDER BY prepareCourseId DESC";
		List<PrepareCourse> pcs = (List<PrepareCourse>)this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).list();
		if (pcs.size() >0)
			return pcs;
		else
			return null;
	}	
	/**
	 * 得到相关集备
	 */
	@SuppressWarnings("unchecked")
	public List<PrepareCourseRelated> getPrepareCourseRelatedList(int prepareCourseId)
	{
		String queryString = "FROM PrepareCourseRelated WHERE prepareCourseId = :prepareCourseId ORDER BY CreateDate DESC";
		List<PrepareCourseRelated> pcs = (List<PrepareCourseRelated>)this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).list();
		if (pcs.size() >0)
			return pcs;
		else
			return null;
	}	
	/**
	 * 增加相关集备
	 * @param prepareCourseId
	 * @param relatedPrepareCourseId
	 * @param userId
	 */
	@SuppressWarnings("unchecked")
	public void insertRelatedPrepareCourse(int prepareCourseId,int relatedPrepareCourseId,int userId)
	{
		String queryString = " FROM PrepareCourseRelated Where prepareCourseId = :prepareCourseId And relatedPrepareCourseId = :relatedPrepareCourseId";
		List<PrepareCourseRelated> pcm = (List<PrepareCourseRelated>)this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).setInteger("relatedPrepareCourseId", relatedPrepareCourseId).list();//(queryString, new Object[]{ prepareCourseId,  });
		if(pcm.size() > 0)
			return;
		
		PrepareCourseRelated pcr=new PrepareCourseRelated();
		pcr.setPrepareCourseId(prepareCourseId);
		pcr.setRelatedPrepareCourseId(relatedPrepareCourseId);
		pcr.setCreateDate(new java.util.Date());
		pcr.setUserId(userId);
		this.getSession().save(pcr);
		this.getSession().flush();
		
	}
	
	/**
	 * 删除相关集备
	 * @param prepareCourseId
	 * @param videoId
	 */
	@SuppressWarnings("unchecked")
	public void removeRelatedPrepareCourse(int prepareCourseId,int relatedPrepareCourseId)
	{
		String queryString = "DELETE PrepareCourseRelated WHERE prepareCourseId = :prepareCourseId And relatedPrepareCourseId = :relatedPrepareCourseId";
		this.getSession().createQuery(queryString).setInteger("prepareCourseId", prepareCourseId).setInteger("relatedPrepareCourseId", relatedPrepareCourseId).executeUpdate();
		
	}
	/**
	 * 删除相关集备
	 * @param prepareCourseRelatedId
	 */
	@SuppressWarnings("unchecked")
	public void removeRelatedPrepareCourse(int prepareCourseRelatedId)
	{
		String queryString = "DELETE PrepareCourseRelated WHERE prepareCourseRelatedId = :prepareCourseRelatedId";
		this.getSession().createQuery(queryString).setInteger("prepareCourseRelatedId", prepareCourseRelatedId).executeUpdate();
	}
	/**
	 * 得到用户发起的备课
	 * @param createUserId
	 * @return
	 */
	public List<PrepareCourse> getPrepareCourseListByCreateUserId(int createUserId){
		List<PrepareCourse> pclist = (List<PrepareCourse>)this.getSession().createQuery("FROM PrepareCourse Where createUserId = ?").setInteger(0, createUserId).list();
		return pclist;
	}
	
	/**
	 * 得到用户参与的备课
	 * @param joinUserId
	 * @return
	 */
	public List<PrepareCourse> getPrepareCourseListByJoinUserId(int joinUserId){
		String queryString = "SELECT pc FROM PrepareCourse as pc ,PrepareCourseMember as pcm WHERE pc.prepareCourseId = pcm.prepareCourseId AND pcm.userId = :userId";
		List<PrepareCourse> pclist = (List<PrepareCourse>)this.getSession().createQuery(queryString).setInteger("userId", joinUserId).list();
		return pclist;
	}
}
