package cn.edustar.jitar.service.impl;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.model.CommonObject;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.PluginAuthorityCheckService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.SpecialSubjectService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UserService;

/**
 * 
 * @author 孟宪会
 * 插件权限验证服务接口实现
 *
 */
public class PluginAuthorityCheckServiceImpl implements PluginAuthorityCheckService {

	//private static final Log log = LogFactory.getLog(PluginAuthorityCheckServiceImpl.class);
	
	/** 用户服务 */
	UserService userService;

	/** 群组服务 */
	GroupService groupService;

	/** 集备服务 */	
	PrepareCourseService prepareCourseService;
	
	UnitService unitService;
	
	AccessControlService accessControlService;
	
	SpecialSubjectService specialSubjectService;
	
	SubjectService subjectService;

	
	
	/**
	 * 插件对象管理权限验证：包括增、删、除。如果有权限，则增删改权限都会具备
	 */
	public boolean canManagePluginInstance(CommonObject commonObject,User currentLoginUser) {
		if(currentLoginUser == null)
		{
			return false;
		}
		if(commonObject == null)
		{
			return false;
		}
		AccessControlService acs = (AccessControlService) JitarContextImpl.getCurrentJitarContext().getSpringContext().getBean("accessControlService");
		
		// 如果是超级管理员，则默认是有权限的。管理员有至高无上的权限。
		if(acs.isSystemAdmin(currentLoginUser))
		{
			return true;
		}
		
		if (CommonObject.OBJECT_USER.equalsIgnoreCase(commonObject.getObjectType())) {
			return this.checkUserCreatePluginAuthority(commonObject,currentLoginUser);
		}
		else if(CommonObject.OBJECT_GROUP.equalsIgnoreCase(commonObject.getObjectType()))
		{
			return this.checkGroupCreatePluginAuthority(commonObject,currentLoginUser);
		}
		else if(CommonObject.OBJECT_PREPARECOURSE.equalsIgnoreCase(commonObject.getObjectType()))
		{
			return this.checkPrepareCourseCreatePluginAuthority(commonObject,currentLoginUser);
		}
		else if(CommonObject.OBJECT_SPECIALSUBJECT.equalsIgnoreCase(commonObject.getObjectType()))
		{
			return this.checkSpecialSubjectPluginAuthority(commonObject,currentLoginUser);
		}
		else if(CommonObject.OBJECT_UNIT.equalsIgnoreCase(commonObject.getObjectType()))
		{
			return this.checkUnitPluginAuthority(commonObject,currentLoginUser);
		}
		else if(CommonObject.OBJECT_SUBJECT.equalsIgnoreCase(commonObject.getObjectType()))
		{
			return this.checkSubjectPluginAuthority(commonObject,currentLoginUser);
		}	
		return false;
	}

	/**
	 * 检查当前登录用户是否有创建个人空间插件的权限
	 * @param commonObject
	 * @param currentLoginUser
	 * @return
	 */
	private boolean checkUserCreatePluginAuthority(CommonObject commonObject,User currentLoginUser) {
		User u = this.userService.getUserByGuid(commonObject.getObjectGuid());
		if(u == null)
		{
			return false;
		}
		// 对于博客，只有博客主人才可以创建
		return u.getUserId() == currentLoginUser.getUserId();
	}
	
	/**
	 * 检查当前登录人员是否有创建协作组插件的权限
	 * @param commonObject
	 * @param currentLoginUser
	 * @return
	 */
	private boolean checkGroupCreatePluginAuthority(CommonObject commonObject,User currentLoginUser) {	
		if(this.groupService == null) return false;
		Group g = this.groupService.getGroupByGuid(commonObject.getObjectGuid());
		
		if(g == null)
		{
			return false;
		}
		
		if(currentLoginUser == null) return false;
		
		// 对于协作组，只有管理员、副管理员才可以创建
		if(g.getGroupId() == 0 || currentLoginUser.getUserId() == 0) return false;
		GroupMember gm = this.groupService.getGroupMemberByGroupIdAndUserId(g.getGroupId(), currentLoginUser.getUserId());
		if(gm == null) return false;
		return gm.getGroupRole() >= GroupMember.GROUP_ROLE_VICE_MANAGER;
	}
	
	/**
	 * 检查当前登录人员是否有创建插件的权限
	 * @param commonObject
	 * @param currentLoginUser
	 * @return
	 */
	private boolean checkPrepareCourseCreatePluginAuthority(CommonObject commonObject,User currentLoginUser)
	{
		PrepareCourse prepareCourse = this.prepareCourseService.getPrepareCourseByGuid(commonObject.getObjectGuid());
		if(prepareCourse == null){
			return false;			
		}
		// 对于集备，只有主备人，创建人可以创建
		return (prepareCourse.getCreateUserId() == currentLoginUser.getUserId() || currentLoginUser.getUserId() == prepareCourse.getLeaderId());		
	}	
	
	//专题管理权限
	private boolean checkSpecialSubjectPluginAuthority(CommonObject commonObject,User currentLoginUser)
	{
		/*
		 * 对于网站专题，只有管理员可以进行处理
		 * 对于学科专题，学科管理员和学科内容管理员可以进行处理
		 */
		String sp_type = "";
		SpecialSubject specialSubject = this.specialSubjectService.getSpecialSubjectByGuid(commonObject.getObjectGuid());
		if (specialSubject == null )
		{
			return false;
		}
		sp_type = specialSubject.getObjectType();
		
		/** 被认为是全站的专题 */
		if(sp_type == null)
		{
			return false;
		}
		
		if(sp_type.equalsIgnoreCase("subject"))
		{
			Subject subject = this.subjectService.getSubjectById(specialSubject.getObjectId());
			if(subject == null)
			{
				return false;
			}
			
			//检查当前用户是否具有本学科的管理权限			
			if(accessControlService.userIsSubjectAdmin(currentLoginUser, subject) || accessControlService.userIsSubjectContentAdmin(currentLoginUser, subject))
			{
				return true;
			}
		}		
		
		// 只有管理员可以创建
		return false;
	}
	

	/**
	 * 机构权限管理
	 * @param commonObject
	 * @param currentLoginUser
	 * @return
	 */
	private boolean checkUnitPluginAuthority(CommonObject commonObject,User currentLoginUser)
	{
		Unit unit = this.unitService.getUnitByGuid(commonObject.getObjectGuid());
		if(unit == null)
		{
			return false;
		}
		if(this.accessControlService.checkUserAccessControlIsExists(currentLoginUser.getUserId(), AccessControl.OBJECTTYPE_UNITSYSTEMADMIN, unit.getUnitId())
		|| this.accessControlService.checkUserAccessControlIsExists(currentLoginUser.getUserId(), AccessControl.OBJECTTYPE_UNITCONTENTADMIN, unit.getUnitId())
		|| this.accessControlService.checkUserAccessControlIsExists(currentLoginUser.getUserId(), AccessControl.OBJECTTYPE_SUPERADMIN, 0)
		|| this.accessControlService.checkUserAccessControlIsExists(currentLoginUser.getUserId(), AccessControl.OBJECTTYPE_SYSTEMCONTENTADMIN, 0)
		)	
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 学科页面插件管理验证
	 * @param commonObject
	 * @param currentLoginUser
	 * @return
	 */
	private boolean checkSubjectPluginAuthority(CommonObject commonObject,User currentLoginUser)
	{
		Subject subject = this.subjectService.getSubjectByGuid(commonObject.getObjectGuid());
		if(subject == null)
		{
			return false;
		}
		
		if(this.accessControlService.checkUserAccessControlIsExists(currentLoginUser.getUserId(), AccessControl.OBJECTTYPE_SUBJECTSYSTEMADMIN, subject.getSubjectId())
				|| this.accessControlService.checkUserAccessControlIsExists(currentLoginUser.getUserId(), AccessControl.OBJECTTYPE_SUBJECTCONTENTADMIN, subject.getSubjectId())
				|| this.accessControlService.checkUserAccessControlIsExists(currentLoginUser.getUserId(), AccessControl.OBJECTTYPE_SUPERADMIN, 0)
				|| this.accessControlService.checkUserAccessControlIsExists(currentLoginUser.getUserId(), AccessControl.OBJECTTYPE_SYSTEMCONTENTADMIN, 0)
				)
		{
			return true;
			
		}
		return false;
	}
	
	/*********************** 以下是服务的setter方法 */	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public void setPrepareCourseService(PrepareCourseService prepareCourseService) {
		this.prepareCourseService = prepareCourseService;
	}
	
	
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	
	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}

	public void setSpecialSubjectService(SpecialSubjectService specialSubjectService) {
		this.specialSubjectService = specialSubjectService;
	}
	
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}


}
