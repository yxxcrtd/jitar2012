package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.PrepareCourseArticle;
import cn.edustar.jitar.pojos.PrepareCourseEdit;
import cn.edustar.jitar.pojos.PrepareCourseMember;
import cn.edustar.jitar.pojos.PrepareCourseRelated;
import cn.edustar.jitar.pojos.PrepareCourseVideo;
import cn.edustar.jitar.pojos.PrepareCoursePlan;
import cn.edustar.jitar.pojos.PrepareCoursePrivateComment;
import cn.edustar.jitar.pojos.PrepareCourseResource;
import cn.edustar.jitar.pojos.PrepareCourseStage;
import cn.edustar.jitar.pojos.PrepareCourseTopic;
import cn.edustar.jitar.pojos.PrepareCourseTopicReply;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.PrepareCourseMemberQueryParam;

public interface PrepareCourseDao {
	
	/**
	 * 创建一个集备
	 * @param prepareCourse
	 */
	public void createPrepareCourse(PrepareCourse prepareCourse);
	
	/**
	 * 加载一个集备
	 * @param prepareCourseId
	 * @return
	 */
	public PrepareCourse getPrepareCourse(int prepareCourseId);
	
	/**
	 * 根据 id 删除一个集备
	 * @param prepareCourseId
	 */
	public void deletePrepareCourse(int prepareCourseId);
	
	/**
	 * 更新集备
	 * @param prepareCourse
	 */
	public void updatePrepareCourse(PrepareCourse prepareCourse);
	
	/**
	 * 检查用户是否在一个集备之内
	 * @param prepareCourseId
	 * @param userId
	 * @return
	 */
	public boolean checkUserInPreCourse(int prepareCourseId, int userId);
	
	/**
	 * 增加视频
	 * @param prepareCourseId
	 * @param videoId
	 */
	public void insertVideoToPrepareCourse(int prepareCourseId,int videoId,int userId,String videoTitle);
	/**
	 * 得到集备视频
	 * @param prepareCourseVideoId
	 * @return
	 */
	public PrepareCourseVideo getPrepareCourseVideo(int prepareCourseVideoId);
	/**
	 * 删除视频
	 * @param prepareCourseId
	 * @param videoId
	 */
	public void removePrepareCourseVideo(int prepareCourseId,int videoId);
	/**
	 * 根据 GUID 得到一个集备
	 * @param objectGuid
	 * @return
	 */
	public PrepareCourse getPrepareCourseByGuid(String objectGuid);
	
	/**
	 * 检查是否是有效成员
	 * @param prepareCourseId
	 * @param userId
	 * @return
	 */
	public boolean checkUserExistsInPrepareCourse(int prepareCourseId, int userId);
	
	/**
	 * 检查用户是否可以管理集备
	 * @param prepareCourseId
	 * @param user
	 * @return
	 */
	public boolean checkUserCanManagePreCourse(int prepareCourseId, User user);
	
	/**
	 * 添加集备成员
	 * @param prepareCourseMember
	 */
	public void addPrepareCourseMember(PrepareCourseMember prepareCourseMember);
	
	/**
	 * 得到集备的所有成员
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<PrepareCourseMember> getPrepareCourseMemberList(PrepareCourseMemberQueryParam param,Pager pager);
	
	/**
	 * 根据集备标识和用户标识得到集备成员对象
	 * @param prepareCourseId
	 * @param userId
	 * @return
	 */
	public PrepareCourseMember getPrepareCourseMemberByCourseIdAndUserId(int prepareCourseId,int userId);
	public PrepareCourseMember getPrepareCourseMemberByCourseIdAndUserIdWithNoStatus(int prepareCourseId,int userId);
	public PrepareCourseMember getPrepareCourseMemberById(int prepareCourseMemberId);
	
	/**
	 * 更新一个用户的一个集备回复信息
	 * @param prepareCourseId
	 * @param userId
	 */
	public void updateReplyCount(int prepareCourseId,int userId);
	
	/**
	 * 更新集备顺序
	 * @param prepareCoreseStageId
	 * @param orderIndex
	 */
	public void updatePrepareCourseStageIndexOrder(int prepareCoreseStageId,int orderIndex);
	
	/**
	 * 更新集备成员信息
	 * @param prepareCourseMember
	 */
	public void updatePrepareCourseMember(PrepareCourseMember prepareCourseMember);
	
	/**
	 * 根据集备 id 和用户 id 删除集备成员
	 * @param prepareCourseId
	 * @param userId
	 */
	public void deletePrepareCourseMember(int prepareCourseId,int userId);
	
	/**
	 * 设置个案精华或取消精华 status=true精华  status=false取消精华
	 */
	public void setPrepareCourseMemberBest(int prepareCourseId,int userId,boolean status);

	/**
	 * 清空个案
	 * @param prepareCourseId
	 * @param userId
	 */
	public void removePrepareCourseContent(int prepareCourseId,int userId);
	/**
	 * 得到个案的数量（非空
	 * @param prepareCourseId
	 * @return
	 */
	public Long getPrepareCourseContentCount(int prepareCourseId);
	/**
	 * 得到共案的编辑次数
	 * @param prepareCourseId
	 * @return
	 */
	public Long getPrepareCourseEditCount(int prepareCourseId);
	
	/**
	 * 创建集备阶段
	 * @param prepareCourseStage
	 */
	public void createPrepareCourseStage(PrepareCourseStage prepareCourseStage);
	
	/**
	 * 更新集备阶段
	 * @param prepareCourseStage
	 */
	public void updatePrepareCourseStage(PrepareCourseStage prepareCourseStage);
	
	/**
	 * 根据标识删除集备阶段
	 * @param prepareCourseStageId
	 */
	public void deletePrepareCourseStage(int prepareCourseStageId);
	
	/**
	 * 得到一个最大集备阶段的最大顺序号
	 * @param prepareCourseId
	 * @return
	 */
	public int getMaxCourseStageOrderIndex(int prepareCourseId);
	
	/**
	 * 根据集备标识，得到一个集备的所有集备阶段
	 * @param prepareCourseId
	 * @return
	 */
	public List<PrepareCourseStage> getPrepareCourseStageList(int prepareCourseId);
	
	/**
	 * 根据集备阶段标识得到一个集备阶段
	 * @param prepareCourseStageId
	 * @return
	 */
	public PrepareCourseStage getPrepareCourseStage(int prepareCourseStageId);
	
	/**
	 * 根据集备标识，得到当前的集备阶段
	 * @param prepareCourseId
	 * @return
	 */
	public PrepareCourseStage getCurrentPrepareCourseStage(int prepareCourseId);
	
	/**
	 * 根据标识，增加一个集备的浏览次数
	 * @param prepareCourseId
	 */
	public void addViewCount(int prepareCourseId);
	
	/**
	 * 根据集备标识，重新计算集备的数据统计信息
	 * @param prepareCourseId
	 */
	public void countPrepareCourseData(int prepareCourseId);
	
	/**
	 * 删除一个集备的话题回复
	 * @param prepareCourseId
	 * @param prepareCourseTopicReplyId
	 */
	public void deletePrepareCourseTopicReply(int prepareCourseId, int prepareCourseTopicReplyId);
	
	/**
	 * 根据一个集备编辑标识，得到集备编辑对象
	 * @param prepareCourseEditId
	 */
	public void deletePrepareCourseEdit(int prepareCourseEditId);
	
	/**
	 * 根据一个集备标识，得到集备的协作组对象
	 * @param prepareCourseEditId
	 */
	public Group getGroupOfPrepareCourse(int prepareCourseId);
	
	/**
	 * 保存集备文章
	 * @param prepareCourseArticle
	 */
	public void savePrepareCourseArticle(PrepareCourseArticle prepareCourseArticle);	
	
	/**
	 * 更新集备文章
	 * @param article
	 */
	public void updateArticleToPrepareCourseStage(Article article);
	
	/**
	 * 删除集备文章
	 * @param article
	 */
	public void deleteArticleToPrepareCourseStage(Article article);	
	
	/**
	 * 删除集备阶段的文章
	 * @param prepareCourseArticleId
	 */
	public void deleteArticleToPrepareCourseStageById(int prepareCourseArticleId);
	/**
	 * 得到 PrepareCourseArticle
	 * @param prepareCourseArticleId
	 * @return
	 */
	public PrepareCourseArticle getPrepareCourseArticle(int prepareCourseArticleId);
	/**
	 * 将一个文章，移动到另外一个集备阶段
	 * @param prepareCourseArticleId
	 * @param prepareCourseStageId
	 */
	public void moveArticleToPrepareCourseStageById(int prepareCourseArticleId,int prepareCourseStageId);
	
	/**
	 * 保存集备资源
	 * @param prepareCourseResource
	 */
	public void savePrepareCourseResource(PrepareCourseResource prepareCourseResource);
	
	/**
	 * 更新集备资源
	 * @param resource
	 */
	public void updateResourceToPrepareCourseStage(Resource resource);
	
	/**
	 * 删除集备资源
	 * @param resource
	 */
	public void deleteResourceToPrepareCourseStage(Resource resource);
	
	/**
	 * 删除集备阶段资源
	 * @param prepareCourseResourceId
	 */
	public void deleteResourceToPrepareCourseStageById(int prepareCourseResourceId);
	/**
	 * 得到集备阶段资源
	 * @param prepareCourseResourceId
	 * @return
	 */
	public PrepareCourseResource getPrepareCourseResource(int prepareCourseResourceId);
	/**
	 * 将一个资源移动到另外一个集备阶段
	 * @param prepareCourseResourceId
	 * @param prepareCourseStageId
	 */
	public void moveResourceToPrepareCourseStageById(int prepareCourseResourceId,int prepareCourseStageId);
	
	/**
	 * 检查集备编辑是否锁定
	 * @param prepareCourseId
	 * @return
	 */
	public PrepareCourseEdit checkPrepareCourseEditIsLocked(int prepareCourseId);
	
	/**
	 * 更新集备编辑
	 * @param prepareCourseEdit
	 */
	public void updatePrepareCourseEdit(PrepareCourseEdit prepareCourseEdit);
	
	/**
	 * 得到最新的集备编辑
	 * @param prepareCourseId
	 * @param editUserId
	 * @return
	 */
	public PrepareCourseEdit getLastestPrepareCourseEdit(int prepareCourseId, int editUserId);
	
	/**
	 * 
	 * @param prepareCourseId
	 * @return
	 */
	public List<PrepareCourseEdit> getPrepareCourseEditList(int prepareCourseId);
	public PrepareCourseEdit getPrepareCourseEdit(int prepareCourseEditId);
	/* 讨论 */
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
	public List<PrepareCourse> getPrepareCourseListOfPlan(int prepareCoursePlanId);
	public void deletePrepareCoursePlan(PrepareCoursePlan prepareCoursePlan);
	public void deleteAllPrepareCourseDataOfUser(int userId, int groupId);
	public void deleteAllPrepareCourseDataOfUser(int userId);
	public PrepareCoursePlan getDefaultPrepareCoursePlanOfGroup(int groupId);
	
	public List<PrepareCourseVideo> getPrepareCourseVideoListByPrepareCourseId(int prepareCourseId);
	public boolean checkVideoInPrepareCourse(int prepareCourseId, int videoId);
	
	/**
	 * 得到相关集备
	 * @param prepareCourseId
	 * @return
	 */
	public List<PrepareCourseRelated> getPrepareCourseRelatedList(int prepareCourseId);
	/**
	 * 得到相关集备
	 * @param prepareCourseId
	 * @return
	 */
	public List<PrepareCourse> getPrepareCourseRelatedListEx(int prepareCourseId);
	/**
	 * 增加相关集备
	 * @param prepareCourseId
	 * @param relatedPrepareCourseId
	 * @param userId
	 */
	public void insertRelatedPrepareCourse(int prepareCourseId,int relatedPrepareCourseId,int userId);
	/**
	 * 删除相关集备
	 * @param prepareCourseId
	 * @param relatedPrepareCourseId
	 */
	public void removeRelatedPrepareCourse(int prepareCourseId,int relatedPrepareCourseId);
	/**
	 * 删除相关集备
	 * @param prepareCourseRelatedId
	 */
	public void removeRelatedPrepareCourse(int prepareCourseRelatedId);
	
	/**
	 * 得到用户发起的备课
	 * @param createUserId
	 * @return
	 */
	public List<PrepareCourse> getPrepareCourseListByCreateUserId(int createUserId);
	
	/**
	 * 得到用户参与的备课
	 * @param joinUserId
	 * @return
	 */
	public List<PrepareCourse> getPrepareCourseListByJoinUserId(int joinUserId);
}
