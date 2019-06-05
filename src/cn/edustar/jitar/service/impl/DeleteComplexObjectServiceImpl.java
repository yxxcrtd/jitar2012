package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupArticle;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.UserDeleted;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.SpecialSubjectService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UserDeletedService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.service.DeleteComplexObjectService;
import cn.edustar.jitar.service.VoteService;

public class DeleteComplexObjectServiceImpl implements
		DeleteComplexObjectService {

	public ArticleService articleService;
	public UserService userService;
	public UnitService unitService;
	public CommentService commentService;
	public GroupService groupService;
	public PrepareCourseService prepareCourseService;
	public SpecialSubjectService specialSubjectService;
	public ResourceService resourceService;
	public AccessControlService accessControlService;
	public VoteService voteService;
	public MessageService messageService;
	private UserDeletedService userdeletedService;
	public void setPrepareCourseService(PrepareCourseService prepareCourseService) {
		this.prepareCourseService = prepareCourseService;
	}

	public void deleteArticle(Article article) {
		
		// 删除集备的文章引用		
		prepareCourseService.deleteArticleToPrepareCourseStage(article);		
		// 删除专题的文章
		specialSubjectService.deleteSubjectArticleByArticleId(article.getArticleId());		
		//删除群组文章的引用：
		List<GroupArticle> gaList = groupService.getAllGroupArticleByArticleId(article.getArticleId());
		for(GroupArticle ga : gaList)
		{
			groupService.deleteGroupArticle(ga);
		}
		
		//删除文章的评论
		commentService.deleteCommentByObject(ObjectType.OBJECT_TYPE_ARTICLE,article.getArticleId());
		
		articleService.crashArticle(article);		

	}
	
	public void deleteResource(Resource resource)
	{
		//删除集备资源的引用
		prepareCourseService.deleteResourceToPrepareCourseStage(resource);		
		//删除群组资源的引用
		groupService.deleteGroupResourceByResource(resource);		
		// 删除资源的评论
		commentService.deleteCommentByObject(ObjectType.OBJECT_TYPE_RESOURCE, resource.getResourceId());		
		// 删除资源
		resourceService.crashResource(resource);
		
	}
	
	public void deleteGroup(Group group)
	{
		groupService.crashGroup(group);
	}

	public void deleteUnit(Unit unit) {	
		if(unit == null) return;
		
		// 逻辑删除
		unitService.deleteUnit(unit);
		
//		// 得到该机构的全部用户
//		List<User> userList = userService.getUserUnitList(unit.getUnitId());
//		for(User user:userList)
//		{
//			this.deleteUser(user);
//		}
//		
//		
//		// 删除权限表
//		List<AccessControl> accessControlList = accessControlService.getAllAccessControlByObject(AccessControl.OBJECTTYPE_UNITCONTENTADMIN, unit.getUnitId());
//		for(AccessControl ac : accessControlList)
//		{
//			accessControlService.deleteAccessControl(ac);
//		}
//		
//		accessControlList = accessControlService.getAllAccessControlByObject(AccessControl.OBJECTTYPE_UNITSYSTEMADMIN, unit.getUnitId());
//		for(AccessControl ac : accessControlList)
//		{
//			accessControlService.deleteAccessControl(ac);
//		}
//		
//		accessControlList = accessControlService.getAllAccessControlByObject(AccessControl.OBJECTTYPE_UNITUSERADMIN, unit.getUnitId());
//		for(AccessControl ac : accessControlList)
//		{
//			accessControlService.deleteAccessControl(ac);
//		}		
//		// 删除投票
//		
//		unitService.deleteUnitWebpart(unit);
//		// unitService.deleteUnit(unit);
//		//更新父机构的 hasChild 属性
//		List<Unit> unitList = unitService.getChildUnitListByParenId(unit.getParentId());
//		if(unitList.size() < 1)
//		{
//			Unit parentUnit = unitService.getUnitById(unit.getParentId());
//			parentUnit.setHasChild(false);
//			unitService.saveOrUpdateUnit(parentUnit);
//		}			
//				
	}

	public void deleteUser(User user) {
		if(user == null) return;
		
		//删除权限表
		List<AccessControl> lc = accessControlService.getAllAccessControlByUser(user);
		if(lc != null && lc.size() > 1)
		{
			for(AccessControl al : lc)
			{
				accessControlService.deleteAccessControl(al);
			}
		}
		
		//删除站内消息
		messageService.deleteAllMessageByUserId(user.getUserId());
		
		//删除评论
		commentService.deleteAllCommentByUserId(user.getUserId());
				
		// 删除该人创建的集备
		prepareCourseService.deleteAllPrepareCourseDataOfUser(user.getUserId());
		
		//删除用户的文章
		List<Article> userArticleList = this.articleService.getAllUserArticle(user.getUserId());
		if(userArticleList != null && userArticleList.size() > 0)
		{
			for(Article article:userArticleList)
			{
				this.deleteArticle(article);
			}
		}
		
		//删除资源
		List<Resource> userResourceList = this.resourceService.getUserResourceOfAll(user.getUserId());
		if(userResourceList != null && userResourceList.size() > 0)
		{
			for(Resource resource : userResourceList)
			{
				this.deleteResource(resource);
			}
		}
		//删除用户创建的协作组、删除参与的协作组
		List<GroupMember> userJoinedGroup = this.groupService.getUserJoinedAllGroup(user.getUnitId());
		if(userJoinedGroup != null && userJoinedGroup.size() > 0)
		{
			for(GroupMember gm:userJoinedGroup)
			{
				this.groupService.deleteGroupMember(gm);
			}
		}

		//在删除用户账号前，先把用户账号转移到userDeleted中
		UserDeleted deleteduser = new UserDeleted();
		deleteduser.setAccountId(user.getAccountId());
		deleteduser.setArticleCommentCount(user.getArticleCommentCount());
		deleteduser.setArticleCount(user.getArticleCount());
		deleteduser.setArticleICommentCount(user.getArticleICommentCount());
		deleteduser.setArticlePunishScore(user.getArticlePunishScore());
		deleteduser.setArticleScore(user.getArticleScore());
		deleteduser.setBlogIntroduce(user.getBlogIntroduce());
		deleteduser.setBlogName(user.getBlogName());
		deleteduser.setCategoryId(user.getCategoryId());
		deleteduser.setCommentCount(user.getCommentCount());
		deleteduser.setCommentPunishScore(user.getCommentPunishScore());
		deleteduser.setCommentScore(user.getCommentScore());
		deleteduser.setCourseCount(user.getCourseCount());
		deleteduser.setCreateDate(user.getCreateDate());
		deleteduser.setCreateGroupCount(user.getCreateGroupCount());
		deleteduser.setEmail(user.getEmail());
		deleteduser.setGender(user.getGender());
		deleteduser.setGradeId(user.getGradeId());
		deleteduser.setHistoryMyArticleCount(user.getHistoryMyArticleCount());
		deleteduser.setHistoryOtherArticleCount(user.getHistoryOtherArticleCount());
		deleteduser.setIdCard(user.getIdCard());
		deleteduser.setJionGroupCount(user.getJionGroupCount());
		deleteduser.setLoginName(user.getLoginName());
		deleteduser.setMyArticleCount(user.getMyArticleCount());
		deleteduser.setNickName(user.getNickName());
		deleteduser.setOtherArticleCount(user.getOtherArticleCount());
		deleteduser.setPhotoCount(user.getPhotoCount());
		deleteduser.setPhotoPunishScore(user.getPhotoPunishScore());
		deleteduser.setPhotoScore(user.getPhotoScore());
		deleteduser.setPositionId(user.getPositionId());
		deleteduser.setPrepareCourseCount(user.getPrepareCourseCount());
		deleteduser.setPushState(user.getPushState());
		deleteduser.setPushUserId(user.getPushUserId());
		deleteduser.setQq(user.getQq());
		deleteduser.setRecommendArticleCount(user.getRecommendArticleCount());
		deleteduser.setRecommendResourceCount(user.getRecommendResourceCount());
		deleteduser.setResourceCommentCount(user.getResourceCommentCount());
		deleteduser.setResourceCount(user.getResourceCount());
		deleteduser.setResourceDownloadCount(user.getResourceDownloadCount());
		deleteduser.setResourceICommentCount(user.getResourceICommentCount());
		deleteduser.setResourcePunishScore(user.getResourcePunishScore());
		deleteduser.setResourceScore(user.getResourceScore());
		deleteduser.setSubjectId(user.getSubjectId());
		deleteduser.setTopicCount(user.getTopicCount());
		deleteduser.setTrueName(user.getTrueName());
		deleteduser.setUnitId(user.getUserId());
		deleteduser.setUnitPathInfo(user.getUnitPathInfo());
		deleteduser.setUsedFileSize(user.getUsedFileSize());
		deleteduser.setUserFileFolder(user.getUserFileFolder());
		deleteduser.setUserGroupId(user.getUserGroupId());
		deleteduser.setUserGuid(user.getUserGuid());
		deleteduser.setUserIcon(user.getUserIcon());
		deleteduser.setUserId(user.getUserId());
		deleteduser.setUserScore(user.getUserScore());
		deleteduser.setUserStatus(user.getUserStatus());
		deleteduser.setUserTags(user.getUserTags());
		deleteduser.setUserType(user.getUserType());
		deleteduser.setUsn(user.getUsn());
		deleteduser.setVersion(user.getVersion());
		deleteduser.setVideoCount(user.getVideoCount());
		deleteduser.setVideoPunishScore(user.getVideoPunishScore());
		deleteduser.setVideoScore(user.getVideoScore());
		deleteduser.setVirtualDirectory(user.getVirtualDirectory());
		deleteduser.setVisitCount(user.getVisitCount());
		deleteduser.setVisitResourceCount(user.getVisitResourceCount());
		this.userdeletedService.createUser(deleteduser);
		
		//删除群组，是一个很麻烦的事情。
		this.userService.deleteUser(user.getUserId());
		
		
	}
	
	
	
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public void setSpecialSubjectService(SpecialSubjectService specialSubjectService) {
		this.specialSubjectService = specialSubjectService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}

	public void setVoteService(VoteService voteService) {
		this.voteService = voteService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
	public void setUserDeletedService(UserDeletedService userdeletedService){
		this.userdeletedService = userdeletedService;
	}
	public UserDeletedService getUserDeletedService(){
		return this.userdeletedService;
	}
}
