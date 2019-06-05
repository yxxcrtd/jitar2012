package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.Pager;
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

public interface PrepareCourseService {

	public void createPrepareCourse(PrepareCourse prepareCourse);
	public Page createPrepareCoursePageWidthWidgets(PrepareCourse prepareCourse);
	public PrepareCourse getPrepareCourse(int prepareCourseId);
	public PrepareCourse getPrepareCourseByGuid(String objectGuid);
	public void deletePrepareCourse(int prepareCourseId);
	public void updatePrepareCourse(PrepareCourse prepareCourse);
	public boolean checkUserInPreCourse(int prepareCourseId, int userId);
	
	public PrepareCourseVideo getPrepareCourseVideo(int prepareCourseVideoId);
	public PrepareCourseArticle getPrepareCourseArticle(int prepareCourseArticleId);
	public PrepareCourseResource getPrepareCourseResource(int prepareCourseResourceId);
	
	/* 检查是否是成员 */
	public boolean checkUserExistsInPrepareCourse(int prepareCourseId, int userId);
	public boolean checkUserCanManagePreCourse(int prepareCourseId, User user);
	public void addPrepareCourseMember(PrepareCourseMember prepareCourseMember);
	public List<PrepareCourseMember> getPrepareCourseMemberList(PrepareCourseMemberQueryParam param,Pager pager);
	public PrepareCourseMember getPrepareCourseMemberByCourseIdAndUserId(int prepareCourseId,int userId);
	public PrepareCourseMember getPrepareCourseMemberByCourseIdAndUserIdWithNoStatus(int prepareCourseId,int userId);
	public PrepareCourseMember getPrepareCourseMemberById(int prepareCourseMemberId);
	public void updateReplyCount(int prepareCourseId,int userId);
	public void updatePrepareCourseStageIndexOrder(int prepareCoreseStageId,int orderIndex);
	public void updatePrepareCourseMember(PrepareCourseMember prepareCourseMember);
	public void deletePrepareCourseMember(int prepareCourseId,int userId);
	public void createPrepareCourseStage(PrepareCourseStage prepareCourseStage);
	public void updatePrepareCourseStage(PrepareCourseStage prepareCourseStage);
	public void deletePrepareCourseStage(int prepareCourseStageId);
	public List<PrepareCourseStage> getPrepareCourseStageList(int prepareCourseId);
	public PrepareCourseStage getPrepareCourseStage(int prepareCourseStageId);	
	public int getMaxCourseStageOrderIndex(int prepareCourseId);
	
	//设置个案精华或取消精华 status=true精华  status=false取消精华
	public void setPrepareCourseMemberBest(int prepareCourseId,int userId,boolean status);
	//清空个案
	public void removePrepareCourseContent(int prepareCourseId,int userId);
	//向集备中增加视频
	public void insertVideoToPrepareCourse(int prepareCourseId,int videoId,int userId,String videoTitle);
	//从集备中删除视频
	public void removePrepareCourseVideo(int prepareCourseId,int videoId);
	//得到个案的数量（非空）
	public Long getPrepareCourseContentCount(int prepareCourseId);
	//得到共案的编辑次数
	public Long getPrepareCourseEditCount(int prepareCourseId);
	
	/**
	 * 获取集备所在的协作组
	 * @param prepareCourseId
	 * @return
	 */
	public Group getGroupOfPrepareCourse(int prepareCourseId);
	public PrepareCourseStage getCurrentPrepareCourseStage(int prepareCourseId);
	public void addViewCount(int prepareCourseId);
	public void countPrepareCourseData(int prepareCourseId);
	public void deletePrepareCourseTopicReply(int prepareCourseId, int prepareCourseTopicReplyId);
	public void deletePrepareCourseEdit(int prepareCourseEditId);
	
	public void saveArticleToPrepareCourseStage(PrepareCourseStage prepareCourseStage, Article article);
	public void updateArticleToPrepareCourseStage(Article article);
	public void deleteArticleToPrepareCourseStage(Article article);
	public void deleteArticleToPrepareCourseStageById(int prepareCourseArticleId);
	public void moveArticleToPrepareCourseStageById(int prepareCourseArticleId,int prepareCourseStageId);
	public void saveResourceToPrepareCourseStage(PrepareCourseStage prepareCourseStage,Resource resource);
	public void updateResourceToPrepareCourseStage(Resource resource);
	public void deleteResourceToPrepareCourseStage(Resource resource);
	public void deleteResourceToPrepareCourseStageById(int prepareCourseResourceId);
	public void moveResourceToPrepareCourseStageById(int prepareCourseResourceId,int prepareCourseStageId);
	
	/* 共同编写 */	
	public PrepareCourseEdit checkPrepareCourseEditIsLocked(int prepareCourseId);
	public void updatePrepareCourseEdit(PrepareCourseEdit prepareCourseEdit);
	public PrepareCourseEdit getLastestPrepareCourseEdit(int prepareCourseId, int editUserId);
	public List<PrepareCourseEdit> getPrepareCourseEditList(int prepareCourseId);
	public PrepareCourseEdit getPrepareCourseEdit(int prepareCourseEditId);
	
	/* 讨论  */
	public void savePrepareCourseTopic(PrepareCourseTopic prepareCourseTopic);
	public void deletePrepareCourseTopic(int prepareCourseTopicId);
	public PrepareCourseTopic getPrepareCourseTopic(int prepareCourseTopicId);
	public void savePrepareCourseTopicReply(PrepareCourseTopicReply prepareCourseTopicReply);
	
	/* 评论 */	
	public void addPrepareCoursePrivateComment(PrepareCoursePrivateComment prepareCoursePrivateComment);
	public void deletePrepareCoursePrivateComment(int prepareCoursePrivateCommentId);
	
	/* 备课计划 */	
	public PrepareCoursePlan getPrepareCoursePlan(int prepareCoursePlanId);
	public void saveOrUpdatePrepareCoursePlan(PrepareCoursePlan prepareCoursePlan);
	public void setGroupDefaultPrepareCoursePlan(boolean defaultValue, int groupId);
	/**
	 * 获取某一备课计划下的所有集备
	 */
	public List<PrepareCourse> getPrepareCourseListOfPlan(int prepareCoursePlanId);
	
	/**
	 * 删除一个备课计划
	 */
	public void deletePrepareCoursePlan(int prepareCoursePlanId);
	
	/**
	 * 删除用户的所有信息
	 * @param userId
	 */
	public void deleteAllPrepareCourseDataOfUser(int userId);
	public void deleteAllPrepareCourseDataOfUser(int userId,int groupId);
	public PrepareCoursePlan getDefaultPrepareCoursePlanOfGroup(int groupId);
	
	public List<PrepareCourseVideo> getPrepareCourseVideoListByPrepareCourseId(int prepareCourseId);
	public boolean checkVideoInPrepareCourse(int prepareCourseId, int videoId);
	
	/**
	 * 得到相关集备
	 */
	@SuppressWarnings("unchecked")
	public List<PrepareCourse> getPrepareCourseRelatedListEx(int prepareCourseId);
	
	/**
	 * 得到相关集备
	 */
	@SuppressWarnings("unchecked")
	public List<PrepareCourseRelated> getPrepareCourseRelatedList(int prepareCourseId);
	
	/**
	 * 增加相关集备
	 * @param prepareCourseId
	 * @param relatedPrepareCourseId
	 * @param userId
	 */
	@SuppressWarnings("unchecked")
	public void insertRelatedPrepareCourse(int prepareCourseId,int relatedPrepareCourseId,int userId);

	
	/**
	 * 删除相关集备
	 * @param prepareCourseId
	 * @param videoId
	 */
	@SuppressWarnings("unchecked")
	public void removeRelatedPrepareCourse(int prepareCourseId,int relatedPrepareCourseId);
	
	/**
	 * 删除相关集备
	 * @param prepareCourseRelatedId
	 */
	@SuppressWarnings("unchecked")
	public void removeRelatedPrepareCourse(int prepareCourseRelatedId);
	
	
}
