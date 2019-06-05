package cn.edustar.jitar.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.model.SiteUrlModel;
import cn.edustar.jitar.module.ModuleRequest;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.GroupKTUser;
import cn.edustar.jitar.pojos.GroupMutil;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.Widget;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.BbsService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.GroupKTUserService;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.PageKey;
import cn.edustar.jitar.service.PageService;
import cn.edustar.jitar.service.PlacardService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.impl.JitarContextImpl;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.FileCache;

/**
 * 群组管理
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Mar 15, 2008 9:10:22 AM
 */
public class GroupAction extends BaseGroupAction {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1316179351406826708L;

	/** 日志 */
	private static final Log log = LogFactory.getLog(GroupAction.class);
	
	/** 配置服务 */
	private ConfigService configService;
	
	/** 分类服务 */
	private CategoryService cate_svc;
	
	private UnitService unit_svc;
	
	/** 学科服务 */
	private SubjectService subj_svc; 
	
	/** 页面服务 */
	private PageService page_svc;
	
	/** 群组公告服务 */
	@SuppressWarnings("unused")
	private PlacardService pla_svc;
	
	/** 群组论坛服务 */
	@SuppressWarnings("unused")
	private BbsService bbs_svc;
	
	/** 短消息服务 */
	private MessageService messageService;
	
	private AccessControlService accessControlService;
	
	/**课题组课题负责人*/
	private GroupKTUserService groupKTUserService;
	
	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}

	/** 当前配置 */
	private Configure config;
	
	/** 允许创建的未审核协作组数量, 缺省 = 2 */
	private int unauditGroupCount = 2;

	/* 
	 * (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		
		// 配置
		this.config = configService.getConfigure();

		// 登录验证.
		if (isUserLogined() == false)
			return LOGIN;
		
		if (canVisitUser(getLoginUser()) == false)
			return ERROR;

		if ("create".equals(cmd)) {
			return create();
		} else if ("save".equals(cmd)) {
			return save();
		} else if ("savektuser".equals(cmd)) {
			return savektuser();
		} else if ("info".equals(cmd)) {
			return info();
		} else if ("edit".equals(cmd)) {
			return edit();
		} else if ("edit_group".equals(cmd)) {
			return edit_group();
		} else if ("stat".equals(cmd)) {
			return stat();
		}
		
		return unknownCommand(cmd);
	}

	/**
	 * 显示'创建群组'界面
	 *  
	 * @return
	 */
	private String create() throws Exception {	
		setRequestAttribute("isKtGroup", "0");
		Group group = new Group();
		group.setGroupTitle("");

		Integer parentId= param_util.safeGetIntParam("parentId");
		if(parentId>0){
			//默认设置为课题组,带父分类的，现在都认为是课题组
			setRequestAttribute("isKtGroup", "1");
			//分类设置为课题分类！
			Category KTCate=cate_svc.getCategory(CategoryService.GROUP_CATEGORY_GUID_KTYJ);
			group.setCategoryId(KTCate.getCategoryId());
			Group parentGroup=group_svc.getGroup(parentId);
			if(parentGroup!=null){
				//默认的一些课题属性，直接继承主课题
				group.setKtNo(parentGroup.getKtNo());
				group.setKtLevel(parentGroup.getKtLevel());
				group.setKtStartDate(parentGroup.getKtStartDate());
				group.setKtEndDate(parentGroup.getKtEndDate());
				group.setGradeId(parentGroup.getGradeId());
				group.setSubjectId(parentGroup.getSubjectId());
			}
		}
		
		group.setParentId(parentId);
		group.setGroupName("");
		group.setGradeId(getLoginUser().getGradeId());
		group.setSubjectId(getLoginUser().getSubjectId());
		
		putGroupSystemCategory(); // 系统分类列表
		putMetaSubjectList(); // 元学科列表
		putGradeList(); // 学段列表

		setRequestAttribute("group", group);
		setRequestAttribute("default_icons", getGroupDefaultIconList());

		return "Edit_Success";
	}
	/**
	 * 仅仅更新课题负责人
	 * @return
	 */
	private String savektuser()
	{
		Integer groupId= param_util.safeGetIntParam("groupId");
		Group group=group_svc.getGroup(groupId);
		SetKtUser(group);
		List<GroupKTUser> ktUserlist= groupKTUserService.GetGroupKTUsers(groupId);
		setRequestAttribute("ktUserlist", ktUserlist);
		setRequestAttribute("group", group);
		return "SaveKTUser_Success";
	}
	/**
	 * 保存群组
	 * 
	 * @return
	 */
	private String save() throws Exception {
		Group group = collectGroupData();
		Integer parentId= param_util.safeGetIntParam("parentId");
		group.setParentId(parentId);
		
		//需要验证是否是课题类型的
		Integer categoryId= param_util.safeGetIntParam("categoryId");
		Category cate= cate_svc.getCategory(categoryId);
		if(cate!=null){
			if(cate.getObjectUuid().equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){ 
				//选的分类是课题研究组
				group.setKtNo(param_util.safeGetStringParam("groupKtNo"));
				group.setKtLevel(param_util.safeGetStringParam("groupKtLevel"));
				String startDate=param_util.safeGetStringParam("startDate");
				String endDate=param_util.safeGetStringParam("endDate");
				if(startDate!=""){
					try{
						Date _beginDate= new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
						group.setKtStartDate(_beginDate);
					}catch (ParseException e) {}
					try{
						Date _endDate= new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
						group.setKtEndDate(_endDate);
					}catch (ParseException e) {}
				}
			}
		}
		String ret = "";
		if (group.getGroupId() == 0)
		    ret = createGroup(group);
		else
		    ret = updateGroup(group);
		

		FileCache fc = new FileCache();
		fc.deleteUserAllCache(getLoginUser().getLoginName());
		fc = null;
        return ret;
	}

	/**
	 * 显示'修改群组'页面
	 * 
	 * @return
	 */
	private String edit() throws Exception {
		// 得到协作组, 不经过缓存
		Group group = group_svc.getGroup(group_model.getGroupId());
		setRequestAttribute("group", group);
		setRequestAttribute("default_icons", getGroupDefaultIconList());

		boolean canManage = canEditGroup();
		setRequestAttribute("canManage", canManage);

		// 系统分类, 学科列表.
		putGroupSystemCategory();
		putMetaSubjectList();
		putGradeList();
		putKtUserList(group);
		
		String cateUuid=group_svc.getGroupCateUuid(group);
		if(cateUuid.equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){
			setRequestAttribute("isKtGroup", "1");
		}
		else if(cateUuid.equals(CategoryService.GROUP_CATEGORY_GUID_JTBK)){
			setRequestAttribute("isKtGroup", "2");
		}
		else{
			setRequestAttribute("isKtGroup", "0");
		}
		
		return "Edit_Success";
	}

	/**
	 * 显示'修改群组'页面.
	 * 
	 * @return
	 */
	private String edit_group() throws Exception {
		// 得到协作组, 不经过缓存.
		Group group = group_svc.getGroup(group_model.getGroupId());
		setRequestAttribute("group", group);
		setRequestAttribute("default_icons", getGroupDefaultIconList());
		// 
		// 验证权限.
		if (canEditGroup() == false) {
			addActionError("您没有修改 " + group_model.toDisplayString() + " 信息的权限.");
			return ERROR;
		}
		
		String cateUuid=group_svc.getGroupCateUuid(group);
		if(cateUuid.equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ))
		{
			setRequestAttribute("isKtGroup", "1");
		}else if(cateUuid.equals(CategoryService.GROUP_CATEGORY_GUID_JTBK)){
			setRequestAttribute("isKtGroup", "2");
		}
		else{
			setRequestAttribute("isKtGroup", "0");
		}
		
		boolean canManage = canEditGroup();
		setRequestAttribute("canManage", canManage);

		// 系统分类, 学科列表.
		putGroupSystemCategory();
		putMetaSubjectList();
		putGradeList();
		putKtUserList(group);
		return "Edit_Success";
	}
	
	/** 把系统分类放到 request 环境中, 提供给模板使用. */
	private void putGroupSystemCategory() {
		String item_type = CategoryService.GROUP_CATEGORY_TYPE;
		CategoryTreeModel syscate_tree = cate_svc.getCategoryTree(item_type);
		setRequestAttribute("syscate_tree", syscate_tree);
	}
	
	/** 把学科列表放到 request 环境中, 提供给模板使用. */
	private void putMetaSubjectList() {
		List<MetaSubject> subject_list = subj_svc.getMetaSubjectList();
		setRequestAttribute("subject_list", subject_list);
	}
	
	private void putKtUserList(Group group){
		List<GroupKTUser> ktuser_list=groupKTUserService.GetGroupKTUsers(group.getGroupId());
		setRequestAttribute("ktuser_list", ktuser_list);
	}
	/** 把学段列表放到 request 环境中, 提供给模板使用. */
	/*TODO:检查这里，使用了基类的方法
	private void putGradeList() {
		Object grade_list = subj_svc.getGradeList();
		super.setRequestAttribute("grade_list", grade_list);
	}
	*/
	
	/** 更改群组信息. */
	private String updateGroup(Group group) {
		
		// 验证权限.
		if (canEditGroup() == false) {
			addActionError("您没有修改协作组 " + group_model.toDisplayString() + " 信息的权限.");
			return ERROR;
		}
		
		// 检查是否合法.
		group.setGroupName("dontcheck");
		if (checkValid(group)) {
			return ERROR;
		}
		
		Group dup_g = group_svc.getGroupByTitle(group.getGroupTitle());
		if (dup_g != null)
		{
			if(dup_g.getGroupId()!=group.getGroupId())
			{
				addActionError("协作组中文标题已经由协作组 " + dup_g.toDisplayString() + " 使用了, 您需要给出一个新的唯一标题.");
				return ERROR;
			}		
		}
		Group oldGroup=group_svc.getGroup(group.getGroupId());
		
		// 实际更改.
		group_svc.updateGroup(group);
		
		Integer categoryId= param_util.safeGetIntParam("categoryId");
		Category cate= cate_svc.getCategory(categoryId);
		if(cate!=null){
			if(cate.getObjectUuid().equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){
				//更新创建课题负责人
				SetKtUser(group);
				
				if(oldGroup.getCategoryId()==null){
					//如果原来组没分类，现在设置为课题分类，则需要建立默认课题分类
					SetKtGroupCate(group);
				}else if (oldGroup.getCategoryId()!=cate.getCategoryId()){
					//如果原来组有分类，但现在设置为课题分类，则也需要建立默认课题分类
					SetKtGroupCate(group);
				}
			}
		}
		addActionMessage("修改群组信息成功完成.");
		return SUCCESS;
	}
	
	/**
	 * 设置课题组的负责人 
	 * @param group
	 */
	private void SetKtUser(Group group)
	{

		String KTteacherId=param_util.safeGetStringParam("KTteacherId");
		if(KTteacherId!=""){
			String[] arrTeacherId=KTteacherId.split(",");
			for(int i=0;i<arrTeacherId.length;i++){
				String teacherId=arrTeacherId[i];
				String KTteacherName=param_util.safeGetStringParam("KTteacherName"+teacherId);
				String KTteacherGender=param_util.safeGetStringParam("KTteacherGender"+teacherId);
				String KTteacherUnit=param_util.safeGetStringParam("KTteacherUnit"+teacherId);
				String gktXZZW=param_util.safeGetStringParam("gktXZZW"+teacherId);
				String gktZYZW=param_util.safeGetStringParam("gktZYZW"+teacherId);
				String gktXL=param_util.safeGetStringParam("gktXL"+teacherId);
				String gktXW=param_util.safeGetStringParam("gktXW"+teacherId);
				String gktYJZC=param_util.safeGetStringParam("gktYJZC"+teacherId);
				Integer xkxdNum=param_util.safeGetIntParam("xkxdNum"+teacherId);
				/*
				String xkxdId="";
				String xkxdName="";
				for(int j=1;j<=xkxdNum;j++){
					Integer gkt_gradeId=param_util.safeGetIntParam("gkt_gradeId_"+teacherId+"_"+j);
					Integer gkt_subjectId=param_util.safeGetIntParam("gkt_subjectId_"+teacherId+"_"+j);
					
					if(xkxdId!=""){xkxdId=xkxdId+",";}
					xkxdId=xkxdId+gkt_gradeId+"/"+gkt_subjectId;
					
					Grade grade=subj_svc.getGrade(gkt_gradeId);
					MetaSubject subject= subj_svc.getMetaSubjectById(gkt_subjectId);
					
					if(xkxdName!=""){xkxdName=xkxdName+",";}
					if(grade!=null){
						xkxdName=xkxdName+grade.getGradeName();
					}
					if(subject!=null){
						xkxdName=xkxdName+"/"+subject.getMsubjName();
					}
				}
				*/
				
				GroupKTUser ktUser;
				GroupKTUser ktu=groupKTUserService.GetGroupKTUser(group.getGroupId(),Integer.parseInt(teacherId));
				if(ktu!=null){
					ktUser=ktu;
				}else{
					ktUser=new GroupKTUser();
					ktUser.setGroupId(group.getGroupId());
					ktUser.setTeacherId(Integer.parseInt(teacherId));
				}
				ktUser.setTeacherGender(KTteacherGender);
				ktUser.setTeacherName(KTteacherName);
				ktUser.setTeacherUnit(KTteacherUnit);
				//ktUser.setTeacherXKXDId(xkxdId);
				//ktUser.setTeacherXKXDName(xkxdName);
				ktUser.setTeacherXL(gktXL);
				ktUser.setTeacherXW(gktXW);
				ktUser.setTeacherYJZC(gktYJZC);
				ktUser.setTeacherZYZW(gktZYZW);
				ktUser.setTeacherXZZW(gktXZZW);
				if(ktu==null){
					groupKTUserService.CreateGroupKTUser(ktUser);
				}else{
					groupKTUserService.UpdateGroupKTUser(ktUser);
				}
				//每个负责人都是管理员
				
				GroupMember gmm= group_svc.getGroupMemberByGroupIdAndUserId(group.getGroupId(), Integer.parseInt(teacherId));
				if(gmm==null){
					GroupMember gm=new GroupMember();
					gm.setGroup(group);
					gm.setGroupId(group.getGroupId());
					gm.setGroupRole(GroupMember.GROUP_ROLE_MANAGER);
					gm.setJoinDate(new Date());
					gm.setInviterId(0);
					gm.setStatus(GroupMember.STATUS_NORMAL);
					gm.setUserId(Integer.parseInt(teacherId));
					gm.setActionCount(0);
					gm.setArticleCount(0);
					gm.setCourseCount(0);
					gm.setReplyCount(0);
					gm.setResourceCount(0);
					gm.setTopicCount(0);
					gm.setTeacherUnit(KTteacherUnit);
					gm.setTeacherXL(gktXL);
					gm.setTeacherXW(gktXW);
					gm.setTeacherYJZC(gktYJZC);
					gm.setTeacherZYZW(gktZYZW);
					
					group_svc.createGroupMember(gm);
				}
				
			}
		}
		//创建人是副管理员！！！
		if((","+KTteacherId+",").indexOf(","+getLoginUser().getUserId()+",")<0){
			//如果当前用户不在课题负责人里，则设置为副管理员
			GroupMember gmmm= group_svc.getGroupMemberByGroupIdAndUserId(group.getGroupId(), getLoginUser().getUserId());
			if(gmmm==null){
				GroupMember gm=new GroupMember();
				gm.setGroup(group);
				gm.setGroupId(group.getGroupId());
				gm.setGroupRole(GroupMember.GROUP_ROLE_VICE_MANAGER);
				gm.setJoinDate(new Date());
				gm.setInviterId(0);
				gm.setStatus(GroupMember.STATUS_NORMAL);
				gm.setUserId(getLoginUser().getUserId());
				gm.setActionCount(0);
				gm.setArticleCount(0);
				gm.setCourseCount(0);
				gm.setReplyCount(0);
				gm.setResourceCount(0);
				gm.setTopicCount(0);
				if(getLoginUser().getUnitId()!=null){
					Unit unit=unit_svc.getUnitById(getLoginUser().getUnitId());
					if(unit !=null)
					gm.setTeacherUnit(unit.getUnitTitle());
				}
				group_svc.createGroupMember(gm);
			}
		}
		
	}
	private void UpdateWidgetTitle(Page page,String name,String title){
		Widget widget = this.page_svc.getWidgetByNameAndPageId(name,page.getPageId());
		if(widget!=null){
			if (!title.equals(widget.getTitle())){
				widget.setTitle(title);
				this.page_svc.saveOrUpdate(widget);
			}
		}
	}
	private void SetKtGroupCate(Group group)
	{
		//还要对课题组的自动建立4个分类！
		//自动建立对应的 开题 中期 结题 成果4个页面模块！！！
		String artType=CommonUtil.toGroupArticleCategoryItemType(group.getGroupId());
		String resType=CommonUtil.toGroupResourceCategoryItemType(group.getGroupId());
		String phoType=CommonUtil.toGroupPhotoCategoryItemType(group.getGroupId());
		String vioType=CommonUtil.toGroupVideoCategoryItemType(group.getGroupId());
		
		PageKey group_index_pk = new PageKey(
				ObjectType.OBJECT_TYPE_GROUP, group.getGroupId(), "index");
		Page page = this.page_svc.getPageByKey(group_index_pk);
		
		/**如果原来的群组页面有下面的页面模块，则修改成课题的标题*/
		UpdateWidgetTitle(page,"group_info","课题介绍");
		UpdateWidgetTitle(page,"group_activist","课题活跃成员");
		UpdateWidgetTitle(page,"group_manager","负责人信息");
		UpdateWidgetTitle(page,"group_newbie","参加者信息");
		UpdateWidgetTitle(page,"group_placard","课题公告");
		UpdateWidgetTitle(page,"group_article","课题文章");
		UpdateWidgetTitle(page,"group_leaveword","课题留言");
		UpdateWidgetTitle(page,"recent_topiclist","课题研讨");
		UpdateWidgetTitle(page,"group_resource","课题资源");
		
		Widget widgetA = this.page_svc.getWidgetByNameAndPageId("group_mutilcates_A",page.getPageId());
		Widget widgetB = this.page_svc.getWidgetByNameAndPageId("group_mutilcates_B",page.getPageId());
		Widget widgetC = this.page_svc.getWidgetByNameAndPageId("group_mutilcates_C",page.getPageId());
		Widget widgetD = this.page_svc.getWidgetByNameAndPageId("group_mutilcates_D",page.getPageId());
		
		if(widgetA==null){
			widgetA=new Widget();
			widgetA.setPageId(page.getPageId());
			widgetA.setCustomTemplate("");
			widgetA.setData("");
			widgetA.setIsHidden(false);
			widgetA.setName("group_mutilcates_A");
			widgetA.setTitle("开题");
			widgetA.setModule("group_mutilcates");
			widgetA.setItemOrder(0);
			widgetA.setColumnIndex(2);
			widgetA.setRowIndex(5);
			this.page_svc.saveOrUpdate(widgetA);
		}
		if(widgetB==null){
			widgetB=new Widget();
			widgetB.setPageId(page.getPageId());
			widgetB.setCustomTemplate("");
			widgetB.setData("");
			widgetB.setIsHidden(false);
			widgetB.setName("group_mutilcates_B");
			widgetB.setTitle("中期");
			widgetB.setModule("group_mutilcates");
			widgetB.setItemOrder(0);
			widgetB.setColumnIndex(2);
			widgetB.setRowIndex(6);
			this.page_svc.saveOrUpdate(widgetB);
		}
		if(widgetC==null){
			widgetC=new Widget();
			widgetC.setPageId(page.getPageId());
			widgetC.setCustomTemplate("");
			widgetC.setData("");
			widgetC.setIsHidden(false);
			widgetC.setName("group_mutilcates_C");
			widgetC.setTitle("结题");
			widgetC.setModule("group_mutilcates");
			widgetC.setItemOrder(0);
			widgetC.setColumnIndex(2);
			widgetC.setRowIndex(7);
			this.page_svc.saveOrUpdate(widgetC);
		}
		if(widgetD==null){
			widgetD=new Widget();
			widgetD.setPageId(page.getPageId());
			widgetD.setCustomTemplate("");
			widgetD.setData("");
			widgetD.setIsHidden(false);
			widgetD.setName("group_mutilcates_D");
			widgetD.setTitle("成果");
			widgetD.setModule("group_mutilcates");
			widgetD.setItemOrder(0);
			widgetD.setColumnIndex(2);
			widgetD.setRowIndex(8);
			this.page_svc.saveOrUpdate(widgetD);
		}		
		GroupMutil groupMutilA= new GroupMutil();
		GroupMutil groupMutilB= new GroupMutil();
		GroupMutil groupMutilC= new GroupMutil();
		GroupMutil groupMutilD= new GroupMutil();
		groupMutilA.setWidgetId(widgetA.getId());
		groupMutilB.setWidgetId(widgetB.getId());
		groupMutilC.setWidgetId(widgetC.getId());
		groupMutilD.setWidgetId(widgetD.getId());
		
		int cateId1=createCate(artType,"开题");
		int cateId2=createCate(artType,"中期");
		int cateId3=createCate(artType,"结题");
		int cateId4=createCate(artType,"成果");
		
		groupMutilA.setArticleCateId(cateId1);
		groupMutilB.setArticleCateId(cateId2);
		groupMutilC.setArticleCateId(cateId3);
		groupMutilD.setArticleCateId(cateId4);
		
		cateId1=createCate(resType,"开题");
		cateId2=createCate(resType,"中期");
		cateId3=createCate(resType,"结题");
		cateId4=createCate(resType,"成果");

		groupMutilA.setResourceCateId(cateId1);
		groupMutilB.setResourceCateId(cateId2);
		groupMutilC.setResourceCateId(cateId3);
		groupMutilD.setResourceCateId(cateId4);

		cateId1=createCate(phoType,"开题");
		cateId2=createCate(phoType,"中期");
		cateId3=createCate(phoType,"结题");
		cateId4=createCate(phoType,"成果");

		groupMutilA.setPhotoCateId(cateId1);
		groupMutilB.setPhotoCateId(cateId2);
		groupMutilC.setPhotoCateId(cateId3);
		groupMutilD.setPhotoCateId(cateId4);
		
		cateId1=createCate(vioType,"开题");
		cateId2=createCate(vioType,"中期");
		cateId3=createCate(vioType,"结题");
		cateId4=createCate(vioType,"成果");

		groupMutilA.setVideoCateId(cateId1);
		groupMutilB.setVideoCateId(cateId2);
		groupMutilC.setVideoCateId(cateId3);
		groupMutilD.setVideoCateId(cateId4);
		
		group_svc.saveGroupMutil(groupMutilA);
		group_svc.saveGroupMutil(groupMutilB);
		group_svc.saveGroupMutil(groupMutilC);
		group_svc.saveGroupMutil(groupMutilD);
		
	}
	private int createCate(String itemType,String name)
	{
		//如果存在，则返回
		Category category=cate_svc.getCategory(name,itemType,true);
		if(category!=null){
			return category.getCategoryId();
		}
		//不存在，则创建 
		category=new Category();
		category.setCategoryId(0);
	    category.setName(name);
	    category.setItemType(itemType);
	    category.setIsSystem(true);
	    category.setParentId(null);
	    category.setDescription("");
	    category.setParentPath("/");
	    cate_svc.createCategory(category);
	    
	    return category.getCategoryId();
	    
	}
	
	/** 创建群组. */
	private String createGroup(Group group) {
		// 检查是否合法.
		if (checkValid(group))
			return ERROR;
		
		// 验证群组的中文名称和英文名称是否有重复
		if (canCreateGroup(group) == false)
			return ERROR;
		
		// 设置创建者、创建时间、审核状态(依据配置).
		group.setCreateUserId(getLoginUser().getUserId());
		if(group.getParentId()>0){
			group.setGroupState(Group.GROUP_STATE_NORMAL);
		}else{
			group.setGroupState(configGroupCreateNeedApprove() ? Group.GROUP_STATE_WAIT_AUDIT : Group.GROUP_STATE_NORMAL);
		}
		
		 AccessControlService acs = (AccessControlService) JitarContextImpl.getCurrentJitarContext().getSpringContext().getBean("accessControlService");
		
		if(this.getLoginUser() != null && acs.isSystemAdmin(this.getLoginUser()))
		{
			group.setGroupState(Group.GROUP_STATE_NORMAL);
		}
		// 实际创建.
		group_svc.createGroup(group);
		
		//
		boolean isKTGroup=false;
		Integer categoryId= param_util.safeGetIntParam("categoryId");
		Category cate= cate_svc.getCategory(categoryId);
		if(cate!=null){
			if(cate.getObjectUuid().equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){
				//需要创建课题负责人
				isKTGroup=true;
				
				//在创建组的时候默认当前用户设置为管理员，删除它,待会要设置成副管理员
				GroupMember gmMySelf=group_svc.getGroupMemberByGroupIdAndUserId(group.getGroupId(), getLoginUser().getUserId());
				if(gmMySelf!=null){
					group_svc.destroyGroupMember(gmMySelf);
				}
								
				SetKtUser(group);

				SetKtGroupCate(group);
				
			}
		}
		// 给审核的人(如:系统管理员)发送消息
		Message message = new Message();
		message.setSendId(getLoginUser().getUserId()); // 消息的发送者
		message.setReceiveId(1); // 消息的接收者(group_model.getCreateUserId())
		message.setTitle("请审核：" + getLoginUser().getLoginName() + "[" + getLoginUser().getTrueName() + "]" + " 新建群组：'" + group.getGroupTitle());
		message.setContent("请审核我创建的群组，谢谢！<br /><br /><a href='../manage/admin_group.py?cmd=list'>去看看群组管理！</a>");
		messageService.sendMessage(message);
		
		// 输出日志
		log.info("用户：" + getLoginUser().getLoginName() + "，创建了：'" + group.getGroupTitle() + "'群组。");
		
		// 放到 request 中.		request.setAttribute("group", group);
		
		// 添加一些友好链接.
		if(isKTGroup==true){
			addActionMessage("课题组 " + group.toDisplayString() + " 创建成功.");
		}else{
			addActionMessage("协作组 " + group.toDisplayString() + " 创建成功.");
		}
		if (configGroupCreateNeedApprove())
			if(group.getParentId()==0){
				addActionMessage("请等待管理员审核通过之后, 该组就可以使用了");
			}
		else {
			if(isKTGroup==true){
				addActionLink("访问课题组", SiteUrlModel.getSiteUrl() + "go.action?groupId=" + group.getGroupId());
				addActionLink("进入课题组", "group.py?cmd=manage&groupId=" + group.getGroupId(), "_top");
			}else{
				addActionLink("访问协作组", SiteUrlModel.getSiteUrl() + "go.action?groupId=" + group.getGroupId());
				addActionLink("进入协作组", "group.py?cmd=manage&groupId=" + group.getGroupId(), "_top");
			}
			addDefaultReturnActionLink();
		}
		return SUCCESS;
	}
	
	/**
	 * 检测能否创建指定协作组, 将检测 groupTitle, groupName 是否重复.
	 * 
	 * @param group
	 * @return 返回 true 表示能够创建, false 表示有错误不能创建.
	 */
	private boolean canCreateGroup(Group group) {
		if (group.getGroupId() == 0) {
			// 检查 title, name 是否已经使用了.
			Group dup_g = group_svc.getGroupByTitle(group.getGroupTitle());
			if (dup_g != null)
				addActionError("协作组中文标题已经由协作组 " + dup_g.toDisplayString() + " 使用了, 您需要给出一个新的唯一标题.");
	
			dup_g = group_svc.getGroupByName(group.getGroupName());
			if (dup_g != null)
				addActionError("协作组英文名字已经由协作组 " + dup_g.toDisplayString() + " 使用了, 您需要给出一个新的唯一英文名字.");
		}
		
		// 检查自己创建的未被审核的协作组数量.
		int ugc = group_svc.getUserUnanditGroupCount(getLoginUser()._getUserObject());
		if (ugc >= this.unauditGroupCount)
			addActionError("您有 " + ugc + " 个创建而未审核通过的协作组, 在这些协作组审核通过前, 系统设置为不允许创建更多协作组, 请联系系统管理员尽快审核通过您过去创建的协作组.");

		return this.hasActionErrors() ? false : true;
	}
	
	/**
	 * 检查群组是否合法.	 * @param group
	 * @return 有错误则返回 true, 没有错误返回 false
	 */
	@SuppressWarnings("deprecation")
	private boolean checkValid(Group group) {
		if (CommonUtil.isEmptyString(group.getGroupTitle()))
			addActionError("未给出协作组的中文标题.");
		else if (!group_svc.isValidGroupTitle(group.getGroupTitle())) {
			request.setAttribute("groupTitle_err", "协作组的中文标题不合法.");
			addActionError("协作组的中文标题不合法.");
		}
		
		if (CommonUtil.isEmptyString(group.getGroupName()))
			addActionError("未给出协作组的英文名字.");
		else if (!group_svc.isValidGroupName(group.getGroupName())) {
			request.setAttribute("groupName_err", "协作组的英文名字不合法");
			addActionError("协作组的英文名字不合法.");
		}
		
		// 检测学科.
		if (group.getSubjectId() != null) {
			//Subject subject = subj_svc.getSubjectById(group.getSubjectId());
			//if (subject == null) {
			//	addActionError("所选学科非法."+group.getSubjectId());
			//}
			MetaSubject metaSubject=subj_svc.getMetaSubjectById(group.getSubjectId());
			if (metaSubject == null) {
				addActionError("所选学科非法."+group.getSubjectId());
			}
		}
		
		// 检测分类.
		if (group.getCategoryId() != null) {
			Category category = cate_svc.getCategory(group.getCategoryId());
			if (category == null) {
				addActionError("所选分类非法.");
			} else if (CategoryService.GROUP_CATEGORY_TYPE.equals(category.getItemType()) == false) {
				addActionError("所选协作组分类非法, 其不是一个协作组分类.");
			}
		}		
		return super.hasActionErrors() ? true : false;
	}
	
	/** 得到群组创建是否需要审核的配置 "group.create.needApprove" */
	private boolean configGroupCreateNeedApprove() {
		return config.getBoolValue("group.create.needApprove", true);
	}
	
	/**
	 * 从用户界面输入获得群组信息
	 * 
	 * @return
	 */
	private Group collectGroupData() {
		Group group = new Group();
		
		group.setGroupId(param_util.getIntParam("groupId"));
		group.setGroupTitle(param_util.safeGetStringParam("groupTitle"));
		group.setGroupName(param_util.safeGetStringParam("groupName"));
		group.setGroupIntroduce(param_util.safeGetStringParam("groupIntroduce"));
		group.setGroupIcon(param_util.safeGetStringParam("groupIcon"));
		// 如果没有给出图标, 则设置一个缺省的.
		if (group.getGroupIcon() == null || group.getGroupIcon().length() == 0)
			group.setGroupIcon("images/group_default.gif");
		//group.setSubjectId(param_util.getIntParamZeroAsNull("subjectId"));
		
		group.setCategoryId(param_util.getIntParamZeroAsNull("categoryId"));
		group.setGroupTags(param_util.safeGetStringParam("groupTags"));
		group.setJoinLimit(param_util.safeGetIntParam("joinLimit"));
		group.setJoinScore(param_util.safeGetIntParam("joinScore"));
		//group.setGradeId(param_util.getIntParamZeroAsNull("gradeId"));
		
		group.setGradeId(null);
		group.setSubjectId(null);
		String XKXDName="",XKXDId="";
		
		int xkxdNum=param_util.safeGetIntParam("xkxdNum");
		for(int i=0;i<xkxdNum;i++){
			int gradeId=param_util.safeGetIntParam("gradeId_"+i);
			int subjectId=param_util.safeGetIntParam("subjectId_"+i);
			if(gradeId!=0 && subjectId!=0){
				String gradeName="";
				String subName="";
				if(gradeId!=0){
					Grade grade=subj_svc.getGrade(gradeId);
					if(grade!=null){gradeName=grade.getGradeName();}
				}
				if(subjectId!=0){
					MetaSubject sub= subj_svc.getMetaSubjectById(subjectId);
					if(sub!=null){subName=sub.getMsubjName();}
				}
				if(XKXDId!=""){
					XKXDId=XKXDId+",";
				}
				XKXDId=XKXDId+gradeId+"/"+subjectId;
				if(XKXDName!=""){
					XKXDName=XKXDName+",";
				}
				XKXDName=XKXDName+gradeName+"/"+subName;
			}
		}
		group.setXKXDId(XKXDId);
		group.setXKXDName(XKXDName);
		return group;
	}
	
	/**
	 * 取消对指定用户的邀请.
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String uninvite() {
		if (hasCurrentGroup() == false) return ERROR;
		int userId = param_util.getIntParam("userId");
		
		GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(group_model.getGroupId(), userId);
		if (gm == null) {
			addActionError("未找到对标识为 " + userId + " 的用户的邀请.");
			return ERROR;
		}
		if (gm.getStatus() != GroupMember.STATUS_INVITING) {
			addActionError("用户 " + userId + " 的邀请已经失效.");
			return ERROR;
		}
		
		// 取消邀请.
		group_svc.uninviteGroupMember(gm);
		
		addActionMessage("邀请已经取消.");
		
		return SUCCESS;
	}
	
	/**
	 * 同意别人的加入协作组的邀请.
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String accept_invite() {
		if (hasCurrentGroup() == false) return ERROR;
		
		GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(
				group_model.getGroupId(), getLoginUser().getUserId());
		if (gm == null) {
			addActionError("抱歉: 未找到此加入协作组的邀请.");
			return ERROR;
		}
		if (gm.getStatus() != GroupMember.STATUS_INVITING) {
			addActionError("邀请已经过期或者您已经是此协作组的成员了.");
			return ERROR;
		}
		
		group_svc.acceptGroupMemberInvite(gm);
		
		addActionMessage("您已经接受了此邀请, 加入了协作组 " + group_model.getGroupTitle());
		
		addActionLink("访问协作组", SiteUrlModel.getSiteUrl() + "go.action?groupId=" + group_model.getGroupId(), "_top");
		addActionLink("进入协作组", "group.py?cmd=manage&groupId=" + group_model.getGroupId(), "_top");
		addDefaultReturnActionLink();
		
		return SUCCESS;
	}
	
	/**
	 * 婉拒别人的加入协作组的邀请
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String reject_invite() {
		if (hasCurrentGroup() == false) return ERROR;
		
		GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(
				group_model.getGroupId(), getLoginUser().getUserId());
		if (gm == null) {
			addActionError("抱歉: 未找到此加入协作组的邀请.");
			return ERROR;
		}
		if (gm.getStatus() != GroupMember.STATUS_INVITING) {
			addActionError("邀请已经过期或者您已经是此协作组的成员了.");
			return ERROR;
		}
		
		group_svc.rejectGroupMemberInvite(gm);
		
		addActionMessage("您已经拒绝了此邀请");
		
		return SUCCESS;
	}
	
	/**
	 * 检查当前协作组是否审核通过, 如果未通过则 addActionError.
	 * @return 返回 true 表示审核通过, false 表示未通过.
	 */
	private boolean checkGroupAuditState() {
		if (this.group_model == null) return false;
		if (this.group_model.getGroupState() != Group.GROUP_STATE_NORMAL) {
			addActionError("您试图访问的协作组未审核通过或者已经删除, 锁定, 当前不能被访问.");
			return false;
		}
		return true;
	}
	
	/**
	 * 群组管理
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String manage() {
		// 得到群组对象.
		if (hasCurrentGroup() == false) return ERROR;
		if (checkGroupAuditState() == false) return ERROR;
		
		// 根据传递进来的 url 确定右侧显示内容, 缺省为欢迎页.
		String url = param_util.safeGetStringParam("url");
		if (url == null || url.length() == 0)
			url = "group.py?cmd=home&amp;groupId=" + group_model.getGroupId();
		setRequestAttribute("url", url);
		
		return "Manage_Frame";
	}
	
	/**
	 * 群组管理的上面部分,可选
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String manage_top() {
		// 得到群组对象.
		if (hasCurrentGroup() == false)
			return ERROR;
		if (checkGroupAuditState() == false)
			return ERROR;

		return "Manage_Top";
	}
	
	/**
	 * 群组管理 左边导航
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String manage_left() {
		// 得到群组对象.
		if (hasCurrentGroup() == false) return ERROR;
		if (checkGroupAuditState() == false) return ERROR;
		
		String temp = "000000000000";
		super.setRequestAttribute("temp", temp);
		
		return "Manage_Left";
	}

	/**
	 * 查看群组信息
	 * 
	 * @return
	 */
	private String info() throws Exception {
		// 得到群组对象.
		if (hasCurrentGroup() == false)
			return ERROR;

		super.setRequestAttribute("group", group_model._getGroupObject());

		// 系统分类, 学科列表, 学段列表.
		putGroupSystemCategory();
		putMetaSubjectList();
		putGradeList();
		setRequestAttribute("default_icons", getGroupDefaultIconList());

		return "Group_Info";
	}
	
	/**
	 * 更新指定群组的统计信息
	 * 
	 * @return
	 */
	private String stat() throws Exception  {
		// 统计信息包括: 组内成员数, 组内文章数, 组内资源数, 组内活动数等.
		if (hasCurrentGroup() == false)
			return ERROR;

		Group group = group_model._getGroupObject();
		group_svc.updateGroupStatInfo(group);

		// 重新读取 group 信息.
		group = group_svc.getGroup(group_model.getGroupId());

		addActionMessage("统计群组信息成功完成 ！");
		addActionMessage("群组成员数量：" + group.getUserCount());
		addActionMessage("群组文章数量：" + group.getArticleCount());
		addActionMessage("群组资源数量：" + group.getResourceCount());
		addActionMessage("群组图片数量：" + group.getPhotoCount());
		addActionMessage("群组视频数量：" + group.getVideoCount());
		addActionMessage("群组主题数量：" + group.getTopicCount());
		
		//应该重新保存下
		request.setAttribute(ModuleRequest.GROUP_MODEL_KEY, group);
		
		// 返回
		this.addActionLink("返回协作组管理", SiteUrlModel.getSiteUrl() + "manage/group.py?cmd=home&groupId=" + group.getGroupId());

		return SUCCESS;
	}

	/**
	 * 得到系统提供的缺省可用的协作组图标列表.
	 * 
	 * @return
	 */
	private List<String> getGroupDefaultIconList() {
		List<String> icon_list = getDefaultIconList(getServletContext());
		icon_list.add(0, "images/group_default.gif");
		return icon_list;
	}

	/**
	 * 判定当前用户是否有权限编辑协作组信息.
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	private boolean canEditGroup() {
		if (this.accessControlService.isSystemAdmin(getLoginUser())) {
			return true;
		} else {
			if (group_member == null)
				return false;

			// 只有协作组管理员有权限更改
			if (group_member.getGroupRole() >= GroupMember.GROUP_ROLE_VICE_MANAGER)
				return true;
		}
		return false;
	}
	
	// Get and set
	/** 设置分类服务的set方法 */
	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}

	/** 学科服务的set方法 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/** Page服务的set方法 */
	public void setPageService(PageService page_svc) {
		this.page_svc = page_svc;
	}
	
	/** 配置服务的set方法 */
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
	
	/** 群组公告服务的set方法 */
	public void setPlacardService(PlacardService pla_svc) {
		this.pla_svc = pla_svc;
	}
	
	/** 群组论坛服务的set方法 */
	public void setBbsService(BbsService bbs_svc) {
		this.bbs_svc = bbs_svc;
	}
		
	/** 短消息服务的set方法 */
	public void setMessageService(MessageService messageService) {
		this.messageService  = messageService;
	}

	/** 允许创建的未审核协作组数量, 缺省 = 2 */
	public void setUnauditGroupCount(int val) {
		this.unauditGroupCount = val;
	}

	public GroupKTUserService getGroupKTUserService() {
		return groupKTUserService;
	}

	public void setGroupKTUserService(GroupKTUserService groupKTUserService) {
		this.groupKTUserService = groupKTUserService;
	}

	public UnitService getUnitService() {
		return unit_svc;
	}

	public void setUnitService(UnitService unit_svc) {
		this.unit_svc = unit_svc;
	}
	
}
