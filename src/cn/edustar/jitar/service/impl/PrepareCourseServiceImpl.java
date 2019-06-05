package cn.edustar.jitar.service.impl;

import java.util.Date;
import java.util.List;


import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.PrepareCourseDao;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Page;
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
import cn.edustar.jitar.pojos.Widget;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.PageService;
import cn.edustar.jitar.service.PrepareCourseMemberQueryParam;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.util.CommonUtil;

public class PrepareCourseServiceImpl implements PrepareCourseService {
	
	/** 标签服务。 */
	private TagService tag_svc;
	
	/** 缓存服务。 */
	private CacheService cache_svc;
	
	/** 页面服务 */
	private PageService page_svc;
	
	/** 群组服务 */	
	private GroupService groupService;
	
	private PrepareCourseDao prepareCourseDao;
	
	/*	创建一次备课
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrepareCourseService#createPrepareCourse(cn.edustar.jitar.pojos.PrepareCourse)
	 */
	public void createPrepareCourse(PrepareCourse prepareCourse)
	{
		//1,规范标签 并保存
		String[] tags = tag_svc.parseTagList(prepareCourse.getTags());
		prepareCourse.setTags(CommonUtil.standardTagsString(tags));
		this.prepareCourseDao.createPrepareCourse(prepareCourse);
		
		//2,添加成员:创建者和主备人
		PrepareCourseMember pcm = new PrepareCourseMember();
		pcm.setPrepareCourseId(prepareCourse.getPrepareCourseId());
		//pcm.setPrivateContent(""); //采用默认值
		pcm.setReplyCount(0);
		pcm.setJoinDate(new Date());
		pcm.setUserId(prepareCourse.getCreateUserId());
		pcm.setContentLastupdated(new Date());
		pcm.setStatus(0);	//默认设置为通过审核
		this.prepareCourseDao.addPrepareCourseMember(pcm);
		
		if (prepareCourse.getCreateUserId().equals(prepareCourse.getLeaderId()) == false )
		{
			pcm = new PrepareCourseMember();
			pcm.setPrepareCourseId(prepareCourse.getPrepareCourseId());
			pcm.setReplyCount(0);
			pcm.setJoinDate(new Date());
			pcm.setContentLastupdated(new Date());
			pcm.setUserId(prepareCourse.getLeaderId());
			pcm.setStatus(0);  //默认设置为通过审核
			this.prepareCourseDao.addPrepareCourseMember(pcm);
		}		
		//3,创建页面和内容块
		this.createPrepareCoursePageWidthWidgets(prepareCourse);		
		
		//4，添加标签
		tag_svc.createUpdateMultiTag(prepareCourse.getPrepareCourseId(), ObjectType.OBJECT_TYPE_PREPARECOURSE, tags, null);
		
		//可以完成了		
	}
	
	public PrepareCourseVideo getPrepareCourseVideo(int prepareCourseVideoId){
		return this.prepareCourseDao.getPrepareCourseVideo(prepareCourseVideoId);
	}
	public PrepareCourseArticle getPrepareCourseArticle(int prepareCourseArticleId){
		return this.prepareCourseDao.getPrepareCourseArticle(prepareCourseArticleId);
	}
	public PrepareCourseResource getPrepareCourseResource(int prepareCourseResourceId){
		return this.prepareCourseDao.getPrepareCourseResource(prepareCourseResourceId);
	}
	
	/**
	 * 创建备课页面
	 * @param prepareCourse
	 */
	public Page createPrepareCoursePageWidthWidgets(PrepareCourse prepareCourse)
	{
		Page page= new Page();	
		page.setCreateDate(new Date());
		page.setLayoutId(1);
		page.setName("index");
		//PageKey prepareCourse_index_pk = new PageKey(ObjectType.OBJECT_TYPE_PREPARECOURSE, prepareCourse.getPrepareCourseId(), "index");
		page.setObjId(prepareCourse.getPrepareCourseId());
		page.setObjType(15);
		page.setSkin("skin1024");
		page.setTitle(prepareCourse.getTitle());
		this.page_svc.addPage(page);
		if (page.getPageId() < 1)
		{
			return null;
		}
		
		String[] widgets_title = {"备课基本信息","备课流程","当前流程文章","当前流程资源","当前流程讨论","教研活动","参与人员","统计信息","个案列表","共案摘要","集备视频","相关集备"};
		String[] widgets_name = {"show_preparecourse_info","show_preparecourse_stage","show_preparecourse_stage_article","show_preparecourse_stage_resource","show_preparecourse_stage_topic","show_preparecourse_action","show_preparecourse_member","show_preparecourse_statis","show_preparecourse_private_content","show_preparecourse_common_abstract","show_preparecourse_video","show_preparecourse_related"};
		int[] widgets_rowindex = {1,2,1,2,3,1,2,3,4,5,3,1};
		int[] widgets_colindex = {1,1,2,2,2,3,3,3,2,2,2,3};
		
		//创建Widget
		for (int i = 0; i < widgets_title.length; ++i) {	
			Widget dest_w = new Widget();
			dest_w.setName(widgets_name[i]);
			dest_w.setTitle(widgets_title[i]);
			dest_w.setCreateDate(new Date());
			dest_w.setPageId(page.getPageId());
			//dest_w.setData();
			dest_w.setIsHidden(false);
			dest_w.setItemOrder(0);
			dest_w.setColumnIndex(widgets_colindex[i]);
			dest_w.setRowIndex(widgets_rowindex[i]);
			//dest_w.setCustomTemplate();
			dest_w.setModule(widgets_name[i]);
			this.page_svc.saveWidget(dest_w);
		}		
		return page;
	}	
		
	/*
	 * 根据备课id加载备课
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrepareCourseService#getPrepareCourse(int)
	 */
	public PrepareCourse getPrepareCourse(int prepareCourseId)
	{
		return this.prepareCourseDao.getPrepareCourse(prepareCourseId);		
	}
	
	/**
	 * 根据guid加载对象
	 */
	public PrepareCourse getPrepareCourseByGuid(String objectGuid)
	{
		return this.prepareCourseDao.getPrepareCourseByGuid(objectGuid);
	}
	/**
	 * 修改备课
	 * @param prepareCourse
	 */
	public void updatePrepareCourse(PrepareCourse prepareCourse)
	{
		this.prepareCourseDao.updatePrepareCourse(prepareCourse);		
	}
	
	/* 检查是否是成员 */
	public boolean checkUserExistsInPrepareCourse(int prepareCourseId, int userId)
	{
		return this.prepareCourseDao.checkUserExistsInPrepareCourse(prepareCourseId, userId);
	}
	/*
	 * 判断用户是否在当前的备课组中
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrepareCourseService#checkUserInPreCourse(int, int)
	 */	 
	public boolean checkUserInPreCourse(int prepareCourseId, int userId)
	{
		return this.prepareCourseDao.checkUserInPreCourse(prepareCourseId, userId);
	}
	
	/**
	 * 增加视频
	 */
	public void insertVideoToPrepareCourse(int prepareCourseId,int videoId,int userId,String videoTitle)
	{
		this.prepareCourseDao.insertVideoToPrepareCourse(prepareCourseId, videoId,userId,videoTitle);
	}
	/**
	 * 删除视频
	 */
	public void removePrepareCourseVideo(int prepareCourseId,int videoId)
	{
		this.prepareCourseDao.removePrepareCourseVideo(prepareCourseId, videoId);
	}
	/*
	 * 判断用户是否是管理员
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrepareCourseService#checkUserCanManagePreCourse(int, cn.edustar.jitar.pojos.User)
	 */
	public boolean checkUserCanManagePreCourse(int prepareCourseId, User user)
	{
		return this.prepareCourseDao.checkUserCanManagePreCourse(prepareCourseId, user);
	}
	/*
	 * 将用户添加到备课成员表之中
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrepareCourseService#addPrepareCourseMember(int, int)
	 */
	public void addPrepareCourseMember(PrepareCourseMember prepareCourseMember)
	{
		if(this.checkUserInPreCourse(prepareCourseMember.getPrepareCourseId(), prepareCourseMember.getUserId()) == false)
		{
			this.prepareCourseDao.addPrepareCourseMember(prepareCourseMember);
		}
	}
	
	/*
	 * 成员列表
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrepareCourseService#getPrepareCourseMemberList(cn.edustar.jitar.service.PrepareCourseMemberQueryParam, cn.edustar.data.Pager)
	 */
	public List<PrepareCourseMember> getPrepareCourseMemberList(PrepareCourseMemberQueryParam param,Pager pager)
	{
		return this.prepareCourseDao.getPrepareCourseMemberList(param,pager);
	}
	
	/*
	 * 得到一个备课成员对象
	 */
	public PrepareCourseMember getPrepareCourseMemberByCourseIdAndUserId(int prepareCourseId,int userId)
	{
		return this.prepareCourseDao.getPrepareCourseMemberByCourseIdAndUserId(prepareCourseId,userId);
	}
	
	public PrepareCourseMember getPrepareCourseMemberById(int prepareCourseMemberId)
	{
		return this.prepareCourseDao.getPrepareCourseMemberById(prepareCourseMemberId);
	}
	public PrepareCourseMember getPrepareCourseMemberByCourseIdAndUserIdWithNoStatus(int prepareCourseId,int userId)
	{
	  return this.prepareCourseDao.getPrepareCourseMemberByCourseIdAndUserIdWithNoStatus(prepareCourseId, userId);
	}
	/**
	 * 删除一个成员
	 */
	public void deletePrepareCourseMember(int prepareCourseId,int userId)
	{
		this.prepareCourseDao.deletePrepareCourseMember(prepareCourseId, userId);
	}
	
	/**
	 * 设置个案精华或取消精华 status=true精华  status=false取消精华
	 */
	public void setPrepareCourseMemberBest(int prepareCourseId,int userId,boolean status)
	{
		this.prepareCourseDao.setPrepareCourseMemberBest(prepareCourseId, userId,status);
	}
	/*
	 * 更新回复计数器
	 */
	public void updateReplyCount(int prepareCourseId,int userId)
	{
		this.prepareCourseDao.updateReplyCount(prepareCourseId, userId);
	}
	
	/*
	 * 更改显示顺序
	 */
	public void updatePrepareCourseStageIndexOrder(int prepareCoreseStageId,int orderIndex)
	{
		this.prepareCourseDao.updatePrepareCourseStageIndexOrder(prepareCoreseStageId, orderIndex);
	}
	
	/*
	 * 请空个案
	 * 
	 */
	public void removePrepareCourseContent(int prepareCourseId,int userId)
	{
		this.prepareCourseDao.removePrepareCourseContent(prepareCourseId,userId);
	}
	
	/**
	 * 得到个案的数量（非空）
	 * @param prepareCourseId
	 * @return
	 */
	public Long getPrepareCourseContentCount(int prepareCourseId)
	{
		return this.prepareCourseDao.getPrepareCourseContentCount(prepareCourseId);
	}

	/**
	 * 得到共案的编辑次数
	 * @param prepareCourseId
	 * @return
	 */
	public Long getPrepareCourseEditCount(int prepareCourseId)
	{
		return this.prepareCourseDao.getPrepareCourseEditCount(prepareCourseId);
	}
	
	/*
	 * 更新成员信息
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrepareCourseService#updatePrepareCourseMember(cn.edustar.jitar.pojos.PrepareCourseMember)
	 */
	public void updatePrepareCourseMember(PrepareCourseMember prepareCourseMember)
	{
		this.prepareCourseDao.updatePrepareCourseMember(prepareCourseMember);
	}
	
	public List<PrepareCourseStage> getPrepareCourseStageList(int prepareCourseId)
	{
		return this.prepareCourseDao.getPrepareCourseStageList(prepareCourseId);
	}
	
	/*
	 * 添加备课阶段
	 */
	public void createPrepareCourseStage(PrepareCourseStage prepareCourseStage)
	{
		this.prepareCourseDao.createPrepareCourseStage(prepareCourseStage);
	}
	
	/**
	 * 加载备课阶段
	 */	
	public PrepareCourseStage getPrepareCourseStage(int prepareCourseStageId)
	{
		return this.prepareCourseDao.getPrepareCourseStage(prepareCourseStageId);
	}
	/**
	 * 更新备课阶段
	 * @param prepareCourseStage
	 */
	public void updatePrepareCourseStage(PrepareCourseStage prepareCourseStage)
	{
		this.prepareCourseDao.updatePrepareCourseStage(prepareCourseStage);
	}
	
	/**
	 * 删除备课阶段
	 * @param prepareCourseStageId
	 */
	public void deletePrepareCourseStage(int prepareCourseStageId)
	{
		this.prepareCourseDao.deletePrepareCourseStage(prepareCourseStageId);
	}
	/**
	 * 获取集备所在的协作组
	 * @param prepareCourseId
	 * @return
	 */
	public Group getGroupOfPrepareCourse(int prepareCourseId)
	{
		return this.prepareCourseDao.getGroupOfPrepareCourse(prepareCourseId);
	}
	
	/**
	 * 删除备课
	 * @param prepareCourseStageId
	 */
	public void deletePrepareCourse(int prepareCourseId)
	{
		this.prepareCourseDao.deletePrepareCourse(prepareCourseId);
	}	
	
	/**
	 * 删除备课历史
	 * @param prepareCourseStageId
	 */
	public void deletePrepareCourseEdit(int prepareCourseEditId)
	{
		this.prepareCourseDao.deletePrepareCourseEdit(prepareCourseEditId);
	}	
	
	
	/*
	 * 添加一个回复
	 */
/*	public void createPrepareCourseStageReply(PrepareCourseStageReply prepareCourseStageReply)
	{
		this.prepareCourseDao.createPrepareCourseStageReply(prepareCourseStageReply);
	}
	
	@SuppressWarnings("unchecked")
	public List<PrepareCourseStageReply> getPrepareCourseStageReplyList(int prepareCourseStageId)
	{
		return this.prepareCourseDao.getPrepareCourseStageReplyList(prepareCourseStageId);
	}	*/
	
	/**
	 * 删除讨论回复
	 */
	public void deletePrepareCourseTopicReply(int prepareCourseId, int prepareCourseTopicReplyId)
	{
		this.prepareCourseDao.deletePrepareCourseTopicReply(prepareCourseId, prepareCourseTopicReplyId);
	}
	
	/**
	 * 将文章保存到备课
	 * @param prepareCourseStage
	 * @param article
	 */
	public void saveArticleToPrepareCourseStage(PrepareCourseStage prepareCourseStage, Article article)
	{
		PrepareCourseArticle prepareCourseArticle = new PrepareCourseArticle();
		prepareCourseArticle.setArticleId(article.getArticleId());
		prepareCourseArticle.setTitle(article.getTitle());
		prepareCourseArticle.setCreateDate(new Date());
		prepareCourseArticle.setPrepareCourseId(prepareCourseStage.getPrepareCourseId());
		prepareCourseArticle.setPrepareCourseStageId(prepareCourseStage.getPrepareCourseStageId());
		prepareCourseArticle.setUserId(article.getUserId());
		this.prepareCourseDao.savePrepareCourseArticle(prepareCourseArticle);
	}
	
	public void updateArticleToPrepareCourseStage(Article article)
	{
		this.prepareCourseDao.updateArticleToPrepareCourseStage(article);
	}
	public void deleteArticleToPrepareCourseStage(Article article)
	{
		this.prepareCourseDao.deleteArticleToPrepareCourseStage(article);
	}
	
	/**
	 * 删除流程文章
	 */
	public void deleteArticleToPrepareCourseStageById(int prepareCourseArticleId)
	{
		this.prepareCourseDao.deleteArticleToPrepareCourseStageById(prepareCourseArticleId);
	}
	/**
	 * 修改流程文章
	 */
	public void moveArticleToPrepareCourseStageById(int prepareCourseArticleId,int prepareCourseStageId)
	{
		this.prepareCourseDao.moveArticleToPrepareCourseStageById(prepareCourseArticleId, prepareCourseStageId);
	}
		
	
	////资源
	public void saveResourceToPrepareCourseStage(PrepareCourseStage prepareCourseStage, Resource resource)
	{
		PrepareCourseResource prepareCourseResource = new PrepareCourseResource();
		prepareCourseResource.setCreateDate(new Date());
		prepareCourseResource.setPrepareCourseId(prepareCourseStage.getPrepareCourseId());
		prepareCourseResource.setPrepareCourseStageId(prepareCourseStage.getPrepareCourseStageId());
		prepareCourseResource.setResourceId(resource.getResourceId());
		prepareCourseResource.setResourceTitle(resource.getTitle());
		prepareCourseResource.setUserId(resource.getUserId());		
		this.prepareCourseDao.savePrepareCourseResource(prepareCourseResource);
	}
	public void updateResourceToPrepareCourseStage(Resource resource)
	{
		this.prepareCourseDao.updateResourceToPrepareCourseStage(resource);
	}
	
	/**
	 * 删除流程资源
	 */
	public void deleteResourceToPrepareCourseStage(Resource resource)
	{
		this.prepareCourseDao.deleteResourceToPrepareCourseStage(resource);
	}
	
	/**
	 * 删除流程资源
	 */	
	public void deleteResourceToPrepareCourseStageById(int prepareCourseResourceId)
	{
		this.prepareCourseDao.deleteResourceToPrepareCourseStageById(prepareCourseResourceId);
	}
	
	/**
	 * 移动资源流程
	 * @param prepareCourseResourceId
	 * @param prepareCourseStageId
	 */
	public void moveResourceToPrepareCourseStageById(int prepareCourseResourceId,int prepareCourseStageId)
	{
		this.prepareCourseDao.moveResourceToPrepareCourseStageById(prepareCourseResourceId, prepareCourseStageId);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.PrepareCourseService#getMaxCourseStageOrderIndex(int)
	 */
	
	public int getMaxCourseStageOrderIndex(int prepareCourseId)
	{
		return this.prepareCourseDao.getMaxCourseStageOrderIndex(prepareCourseId);
	}
	
	public PrepareCourseDao getPrepareCourseDao() {
		return prepareCourseDao;
	}
	
	public void setPrepareCourseDao(PrepareCourseDao prepareCourseDao) {
		this.prepareCourseDao = prepareCourseDao;
	}
	
	public void addViewCount(int prepareCourseId)
	{
		this.prepareCourseDao.addViewCount(prepareCourseId);
	}
	
	public void countPrepareCourseData(int prepareCourseId)
	{
		this.prepareCourseDao.countPrepareCourseData(prepareCourseId);
	}
	
	public PrepareCourseStage getCurrentPrepareCourseStage(int prepareCourseId)
	{
		return this.prepareCourseDao.getCurrentPrepareCourseStage(prepareCourseId);
	}
	
	/* 共同编写 */	
	public PrepareCourseEdit checkPrepareCourseEditIsLocked(int prepareCourseId)
	{
		return this.prepareCourseDao.checkPrepareCourseEditIsLocked(prepareCourseId);
	}
	public void updatePrepareCourseEdit(PrepareCourseEdit prepareCourseEdit)
	{
		this.prepareCourseDao.updatePrepareCourseEdit(prepareCourseEdit);
	}
	public PrepareCourseEdit getLastestPrepareCourseEdit(int prepareCourseId, int editUserId)
	{
		return this.prepareCourseDao.getLastestPrepareCourseEdit(prepareCourseId, editUserId);
	}
	
	public PrepareCourseEdit getPrepareCourseEdit(int prepareCourseEditId)
	{
		return this.prepareCourseDao.getPrepareCourseEdit(prepareCourseEditId);
	}
	
	public List<PrepareCourseEdit> getPrepareCourseEditList(int prepareCourseId)
	{
		return this.prepareCourseDao.getPrepareCourseEditList(prepareCourseId);
	}
	
	
	/* 讨论  */
	public void savePrepareCourseTopic(PrepareCourseTopic prepareCourseTopic)
	{
		this.prepareCourseDao.savePrepareCourseTopic(prepareCourseTopic);
	}
	public void deletePrepareCourseTopic(int prepareCourseTopicId){
		this.prepareCourseDao.deletePrepareCourseTopic(prepareCourseTopicId);
	}
	public PrepareCourseTopic getPrepareCourseTopic(int prepareCourseTopicId)
	{
		return this.prepareCourseDao.getPrepareCourseTopic(prepareCourseTopicId);
	}
	public void savePrepareCourseTopicReply(PrepareCourseTopicReply prepareCourseTopicReply)
	{
		this.prepareCourseDao.savePrepareCourseTopicReply(prepareCourseTopicReply);
	}
	
	/* 评论 */	
	public void addPrepareCoursePrivateComment(PrepareCoursePrivateComment prepareCoursePrivateComment)
	{
		this.prepareCourseDao.addPrepareCoursePrivateComment(prepareCoursePrivateComment);
	}
	
	/**
	 * 删除个人评论
	 */
	public void deletePrepareCoursePrivateComment(int prepareCoursePrivateCommentId)
	{
		this.prepareCourseDao.deletePrepareCoursePrivateComment(prepareCoursePrivateCommentId);
	}
		
	/* 备课计划 */
	
	public PrepareCoursePlan getPrepareCoursePlan(int prepareCoursePlanId)
	{
		return this.prepareCourseDao.getPrepareCoursePlan(prepareCoursePlanId);
	}
	/**
	 * 加载一个组内的默认计划
	 */
	public PrepareCoursePlan getDefaultPrepareCoursePlanOfGroup(int groupId)
	{
		return this.prepareCourseDao.getDefaultPrepareCoursePlanOfGroup(groupId);
	}
	public void saveOrUpdatePrepareCoursePlan(PrepareCoursePlan prepareCoursePlan)
	{
		this.prepareCourseDao.saveOrUpdatePrepareCoursePlan(prepareCoursePlan);
	}
	public void setGroupDefaultPrepareCoursePlan(boolean defaultValue, int groupId)
	{
		this.prepareCourseDao.setGroupDefaultPrepareCoursePlan(defaultValue, groupId);
	}
	
	/**
	 * 获取某一备课计划下的所有集备
	 */
	public List<PrepareCourse> getPrepareCourseListOfPlan(int prepareCoursePlanId)
	{
		return this.prepareCourseDao.getPrepareCourseListOfPlan(prepareCoursePlanId);
	}
	
	/**
	 * 删除一个备课计划
	 */
	public void deletePrepareCoursePlan(int prepareCoursePlanId)
	{
		PrepareCoursePlan p = this.getPrepareCoursePlan(prepareCoursePlanId);
		if(p != null)
		{
			List<PrepareCourse> pc = this.getPrepareCourseListOfPlan(p.getPrepareCoursePlanId());
			for(int i = 0;i<pc.size();i++)
			{
				this.deletePrepareCourse(((PrepareCourse)pc.get(i)).getPrepareCourseId());
			}
			this.prepareCourseDao.deletePrepareCoursePlan(p);
		}
	}
	
	/**
	 * 删除用户的所有信息
	 * @param userId
	 */
	public void deleteAllPrepareCourseDataOfUser(int userId)
	{
		this.prepareCourseDao.deleteAllPrepareCourseDataOfUser(userId);
	}	

	/**
	 *  删除用户的所有信息
	 */
	public void deleteAllPrepareCourseDataOfUser(int userId, int groupId)
	{
		this.prepareCourseDao.deleteAllPrepareCourseDataOfUser(userId, groupId);
	}
	
	public List<PrepareCourseVideo> getPrepareCourseVideoListByPrepareCourseId(int PrepareCourseId)
	{
		return this.prepareCourseDao.getPrepareCourseVideoListByPrepareCourseId(PrepareCourseId);
	}
	
	public boolean checkVideoInPrepareCourse(int prepareCourseId, int videoId)
	{
		return this.prepareCourseDao.checkVideoInPrepareCourse(prepareCourseId, videoId);
	}
	
	
	/**
	 * 得到相关集备
	 */
	public List<PrepareCourse> getPrepareCourseRelatedListEx(int prepareCourseId)
	{
		return this.prepareCourseDao.getPrepareCourseRelatedListEx(prepareCourseId);
	}	
	/**
	 * 得到相关集备
	 */
	public List<PrepareCourseRelated> getPrepareCourseRelatedList(int prepareCourseId)
	{
		return this.prepareCourseDao.getPrepareCourseRelatedList(prepareCourseId);
	}	
	/**
	 * 增加相关集备
	 * @param prepareCourseId
	 * @param relatedPrepareCourseId
	 * @param userId
	 */
	public void insertRelatedPrepareCourse(int prepareCourseId,int relatedPrepareCourseId,int userId)
	{
		this.prepareCourseDao.insertRelatedPrepareCourse(prepareCourseId, relatedPrepareCourseId, userId);	
	}
	
	/**
	 * 删除相关集备
	 * @param prepareCourseId
	 * @param videoId
	 */
	public void removeRelatedPrepareCourse(int prepareCourseId,int relatedPrepareCourseId)
	{
		this.prepareCourseDao.removeRelatedPrepareCourse(prepareCourseId, relatedPrepareCourseId);
	}
	/**
	 * 删除相关集备
	 * @param prepareCourseRelatedId
	 */
	public void removeRelatedPrepareCourse(int prepareCourseRelatedId)
	{
		this.prepareCourseDao.removeRelatedPrepareCourse(prepareCourseRelatedId);		
	}	
	
	
	public TagService getTagService() {
		return tag_svc;
	}

	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}

	public CacheService getCacheService() {
		return cache_svc;
	}

	public void setCacheService(CacheService cache_svc) {
		this.cache_svc = cache_svc;
	}

	public PageService getPageService() {
		return page_svc;
	}

	public void setPageService(PageService page_svc) {
		this.page_svc = page_svc;
	}
	
	public GroupService getGroupService() {
		return this.groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
}
