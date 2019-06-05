package cn.edustar.jitar.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletRequest;

import org.jasig.cas.client.util.CasConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;
import com.jitar2Infowarelab.model.CemUser;
import com.jitar2Infowarelab.model.CemUsers;
import com.jitar2Infowarelab.model.MeetingQueryParam;
import com.jitar2Infowarelab.service.InfowarelabService;
import com.jitar2Infowarelab.utils.Utils;

import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarRequestContext;
import cn.edustar.jitar.action.CategoryActionHelper;
import cn.edustar.jitar.dao.UserDao;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.UserSubjectGrade;
import cn.edustar.jitar.pojos.UserType;
import cn.edustar.jitar.service.AdminUserQueryParam;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.EventManager;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UserQueryParam;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.sso.model.ResultCode;
import cn.edustar.sso.model.ServiceObject;
import cn.edustar.usermgr.BaseUser;
import cn.edustar.usermgr.Client;

/**
 * 用户服务的实现 *
 */
public class UserServiceCachedImpl implements UserService {
	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(UserServiceCachedImpl.class);

	/** 访问数据库的实现对象 */
	private UserDao user_dao;

	private UnitService unitService;

	/** 学科服务 */
	private SubjectService subj_svc;

	/** 分类服务 */
	private CategoryService cate_svc;

	/** 标签服务 */
	private TagService tag_svc;

	/** 支持缓存的服务 */
	private CacheService cache_svc;

	/** 事件服务 */
	private EventManager evt_mgr;

	/** 消息服务 */
	private MessageService msg_svc;

	private String errorInfo ="";
	/** 用户 id -> loginName 的映射 */
	private Hashtable<Integer, String> id_to_name = new Hashtable<Integer, String>();

	/** 访问数据库的实现对象 */
	public void setUserDao(UserDao userDao) {
		this.user_dao = userDao;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	/** 学科服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}

	/** 分类服务 */
	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}

	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}

	/** 支持缓存的服务 */
	public void setCacheService(CacheService cache_svc) {
		this.cache_svc = cache_svc;
	}

	/** 事件服务 */
	public void setEventManager(EventManager evt_mgr) {
		this.evt_mgr = evt_mgr;
	}

	/** 消息服务 */
	public void setMessageService(MessageService msg_svc) {
		this.msg_svc = msg_svc;
	}

	private final String GetUserMgrUrl(ServletRequest request){
		if(null !=request.getServletContext().getFilterRegistration("CASValidationFilter")){
			String userMgrUrl = request.getServletContext().getFilterRegistration("CASValidationFilter").getInitParameter("casServerUrlPrefix");
			if(userMgrUrl==null || userMgrUrl.length()==0){
				userMgrUrl=CasConst.getInstance().getCasServerUrlPrefix();
			}	
			return userMgrUrl;
		}else{
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.UserService#resetPassword(java.lang.String, java.lang.String)
	 */
	public void resetPassword(String username, String password) {
		ServletRequest request = null;
		if(JitarRequestContext.getRequestContext() != null){
			request = JitarRequestContext.getRequestContext().getRequest();
		}
		if(null !=request.getServletContext().getFilterRegistration("CASValidationFilter")){
			resetPassword_CAS(username,password,request);
		}else if(null !=request.getServletContext().getFilterRegistration("ssoUserFilter")){
			try{
			resetPassword_SSO(username,password,request);
			}catch(RuntimeException ex){
				throw ex;
			}
		}else{
			
		}
	}
	/**
	 * SSO重新设置密码
	 * @param username
	 * @param password
	 * @param request
	 */
	public void resetPassword_SSO(String username, String password,ServletRequest request) {
		User u = this.getUserByLoginName(username);
		if(null == u){
			return;
		}
        String SSOServerURL = request.getServletContext().getInitParameter("SSOServerURL");
        String SSOServerURL1 = SSOServerURL;
        String SSOServerURL2 = SSOServerURL;
        if(SSOServerURL.indexOf(";")>-1){
        	String[] arrayUrl = SSOServerURL.split("\\;");
        	SSOServerURL1 = arrayUrl[0];
        	SSOServerURL2 = arrayUrl[1];
        }         
        if(SSOServerURL2.endsWith("/")){
        	SSOServerURL2 = SSOServerURL2.substring(0, SSOServerURL2.length()-1);
        }
        String userServiceUrl = SSOServerURL2 + request.getServletContext().getFilterRegistration("ssoUserFilter").getInitParameter("userServiceUrl");

        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setConnectTimeout(5000L); // 毫秒
        String result = null;
        ServiceObject so = null;
        String md5NewPassword = com.chinaedustar.util.MD5.crypt(password);
        try {
        	com.octopus.sso.service.UserService ssoUserService = (com.octopus.sso.service.UserService) factory.create(com.octopus.sso.service.UserService.class, userServiceUrl);
            result = ssoUserService.resetPwdRemote(u.getUserGuid(),md5NewPassword );
            so = JSON.parseObject(result,ServiceObject.class);
        }catch (Exception ex) {
            ex.printStackTrace();
            //错误
            logger.info("重置密码失败：" + ex.getMessage());
            errorInfo = "用户服务器重置用户："+u.getTrueName() +"密码失败：" + ex.getMessage();
            throw new RuntimeException("用户服务器重置用户："+u.getTrueName() +"密码失败：" + ex.getMessage());
            //return;
            
        }			
         if(null != so){   
            if(so.success){
            	//更新成功
            	//更新教研中的密码 
            	u.setPassword(md5NewPassword);
            	this.user_dao.updateUser(u);
                //如果是红杉树视频会议，需要同步到这个视频会议服务器上
                if(Utils.isInfowarelabMeeting()){
                	InfowarelabService infowarelabService = (InfowarelabService)ContextLoader.getCurrentWebApplicationContext().getBean("infowarelabService");
                	if(null!=infowarelabService){
                		MeetingQueryParam queryparam = new MeetingQueryParam();
                		queryparam.setType(1);
                		queryparam.setValue(u.getLoginName());
                		boolean bupdateOk = infowarelabService.resetUserPassword(queryparam,md5NewPassword);
                		if(!bupdateOk){
                			//失败
                			System.out.println("向视频服务器上更新用户"+ u.getLoginName() +"密码出现错误");
                			System.out.println(infowarelabService.getReason());
                			                			
                		}
                	}
                }            	
            	return;
            }else{
            	//得到从服务器返回的错误
            	ResultCode rc = JSON.parseObject(so.returnobject, ResultCode.class);
            	logger.info("用户服务器重置用户："+u.getTrueName() +"密码失败：" + rc.failureName);
            	errorInfo = "用户服务器重置用户："+u.getTrueName() +"密码失败：" + rc.failureName;
            	throw new RuntimeException("用户服务器重置用户："+u.getTrueName() +"密码失败：" + rc.failureName);
    			//return;
            }
         }
		return;
	}
	
	/**
	 * CAS重新设置密码 
	 * @param username
	 * @param password
	 * @param request
	 */
	public void resetPassword_CAS(String username, String password,ServletRequest request) {
		Client client = new Client();
		String userMgrUrl = GetUserMgrUrl(request);
		if(userMgrUrl != null){
			logger.info("（根据用户名重置密码）统一用户地址：" + userMgrUrl);
			client.setUserMgrUrl(userMgrUrl);
			String result = client.resetPasswordByUsername(username, password);
			if ("ok".equals(result)) {
				logger.info("（根据用户名重置密码）操作成功！" + username);
			} else if ("wrong".equals(result)) {
				logger.info("（根据用户名重置密码）出现错误！" + username);
			}
		}	
	}
	/* 
	 * 缺省，先从缓存获取
	 *
	 * @see cn.edustar.jitar.service.UserService#getUserById(int)
	 */
	public User getUserById(int userId) {
		return getUserById(userId, true);
	}
	
	/**
	 * 
	 * 根据Guid得到用户对象
	 * 
	 * @param guid
	 * @return
	 */
	public User getUserByGuid(String guid)
	{
		return user_dao.getUserByGuid(guid);
	}

	public User getUserByAccountId(String accountId)
	{
		return user_dao.getByAccountId(accountId);
	}
	/* 
	 * 缺省，先从缓存获取
	 *
	 * @see cn.edustar.jitar.service.UserService#getUserByLoginName(java.lang.String)
	 */
	public User getUserByLoginName(String loginName) {
		return getUserByLoginName(loginName, true);
	}
	
	// 根据用户名得到用户对象
	public BaseUser getUserByUsername(String username) {
		ServletRequest request = null;
		if(JitarRequestContext.getRequestContext() != null){
			request = JitarRequestContext.getRequestContext().getRequest();
		}
		Client client = new Client();
		String userMgrUrl = GetUserMgrUrl(request);
		if(userMgrUrl != null){
			logger.info("（根据用户名得到用户对象）统一用户地址：" + userMgrUrl);
			client.setUserMgrUrl(userMgrUrl);
			return client.getUserByUsername(username);
		}else{
			//User user = user_dao.getByLoginName(username);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.UserServiceCached#getUserById(int, boolean)
	 */
	public User getUserById(int userId, boolean canFromCache) {
		User user_model = null;
		if (canFromCache == false) {
			return internalGetUserById(userId);
		}

		// 从 id -> loginName
		String loginName = this.id_to_name.get(userId);
		if (loginName != null) {
			return this.getUserByLoginName(loginName, true);
		}

		// 没在 id_to_name 找到, 则现在加载, 并放到 id_to_name 映射中
		user_model = internalGetUserById(userId);
		if (user_model != null) {
			id_to_name.put(user_model.getUserId(), user_model.getLoginName());
		}
		return user_model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.UserService#getUserFromCache(int)
	 */
	public User getUserFromCache(int userId) {
		String loginName = this.id_to_name.get(userId);
		User user = (User) this.cache_svc.get(keyForCache(loginName));
		return user;
	}

	/**
	 * 根据'用户ID'得到用户对象
	 * 
	 * @param userId
	 * @return
	 */
	private User internalGetUserById(int userId) {
		return user_dao.getByUserId(userId); // 如果缓存中没有，则将第一次从数据库中得到的数据放到缓存中
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.UserServiceCached#getUserByLoginName(java.lang.String, boolean)
	 */
	public User getUserByLoginName(String loginName, boolean canFromCache) {
		User user_model = null;
		if (canFromCache == false) {
			// 直接获取
			user_model = internalGetUserByLoginName(loginName);
		} else {
			User user = (User) this.cache_svc.get(keyForCache(loginName));

			// 如果缓存中有，则用缓存中的数据, 不更新 cache
			if (user != null)
				return user;

			// 如果缓存中没有，则将第一次从数据库中得到的数据
			user_model = internalGetUserByLoginName(loginName);
		}

		if (user_model != null) {
			putUserToCache(user_model._getUserObject()); // 将用户数据放到缓存中
		}
		return user_model;
	}

	public User getUserByEmail(String email){
		return user_dao.getUserByEmail(email);
	}
	public User getUserByNickName(String nickName){
		return user_dao.getUserByNickName(nickName);
	}
	private User internalGetUserByLoginName(String loginName) {
		return user_dao.getByLoginName(loginName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.FriendService#findByLoginName(java.lang.String)
	 */
	public List<User> findLoginUserExist(String loginName) {
		return user_dao.findByLoginName(loginName);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.UserServiceEx#getUserByIds(java.util.List)
	 */
	public List<User> getUserByIds(List<Integer> user_ids) {
		return user_dao.getUserByIds(user_ids);
	}

	/**
	 * 根据用户登录名标识计算一个用户在缓存中的键，注意不能和其它对象重复了
	 *
	 * @param userLoginName
	 * @return
	 */
	private static final String keyForCache(String userLoginName) {
		return "u.name." + userLoginName + ".user";
	}

	/**
	 * 把用户对象放到缓存中, 同时放一个 id_to_name 映射
	 *
	 * @param user
	 */
	private void putUserToCache(User user) {
	    if(null == user) return;
		id_to_name.put(user.getUserId(), user.getLoginName());
		this.cache_svc.put(keyForCache(user.getLoginName()), user);
	}

	/**
	 * 更新指定用户的缓存, 一般在更新了用户信息时候使用
	 *
	 * @param user
	 */
	private void refreshCache(User user) {
	    if(null == user) return;
		cache_svc.remove(keyForCache(user.getLoginName()));
	}

	public void createUser(BaseUser baseUser, User user) {
		user.setLoginName(baseUser.getUsername());
		user.setUserGuid(baseUser.getGuid());
		user.setEmail(baseUser.getEmail());
		user.setCreateDate(baseUser.getCreateDate());
		user.setUsn(baseUser.getUsn());
		user.setTrueName(baseUser.getTrueName());
		user.setNickName(baseUser.getTrueName());
		this.createUser(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.UserService#registerUser(cn.edustar.jitar.pojos.User)
	 */
	public void createUser(User user) {
		createUser(user,true);
	}
	
	public void createUser(User user,Boolean checkLoginName) {
		evt_mgr.publishEvent(EVENT_USER_CREATING, this, user);

		// 1. 合法性检测
		if(checkLoginName)
		{
			if (CommonUtil.isValidName(user.getLoginName()) == false) {
				throw new RuntimeException("未给出用户登录名 loginName 或该登录名非法");
			}
		}
		fillDefaultFields(user);
		checkUserRefObject(user);

		// 2. 创建用户
		user_dao.createUser(user);

		// 创建标签
		String[] tags = tag_svc.parseTagList(user.getUserTags());
		tag_svc.createUpdateMultiTag(user.getUserId(), ObjectType.OBJECT_TYPE_USER, tags, null);

		// 向该用户发送一条欢迎短消息
		sendWelcomeMessage(user);

		// 发布用户创建事件，其它系统在此事件中会创建必要的内容
		evt_mgr.publishEvent(EVENT_USER_CREATED, this, user);
		
		//焦作版本  创建用户的默认自定义文章分类 
		createDefaultUserCategory(user);
	}
	
	/**
	 * 焦作版本  默认增加用户的自定义文章分类 
	 * @param user
	 */
	private void createDefaultUserCategory(User user){
		String itemType = CommonUtil.toUserArticleCategoryItemType(user.getUserId());
		
		Category category = new Category();
		category.setName("专题研讨");
		category.setItemType(itemType);
		category.setParentId(null);
		category.setDescription("专题研讨");
		category.setIsSystem(true);
		this.cate_svc.createCategory(category);
		
		category = new Category();
		category.setName("互动教学");
		category.setItemType(itemType);
		category.setParentId(null);
		category.setDescription("互动教学");
		category.setIsSystem(true);
		this.cate_svc.createCategory(category);
		
		category = new Category();
		category.setName("教师培训");
		category.setItemType(itemType);
		category.setParentId(null);
		category.setDescription("教师培训");
		category.setIsSystem(true);
		this.cate_svc.createCategory(category);
		
	}

	/**
	 * 根据业务逻辑填入缺省的字段值，如果没有给出的话；也进行 tag 的规范化处理
	 * 
	 * @param user
	 */
 	private void fillDefaultFields(User user) {
		//if (CommonUtil.isEmptyString(user.getNickName())) {
		//	user.setNickName(user.getLoginName());
		//}
		if (CommonUtil.isEmptyString(user.getTrueName())) {
			user.setTrueName(user.getLoginName());
		}
		if (CommonUtil.isEmptyString(user.getVirtualDirectory())) {
			user.setVirtualDirectory("u");
		}
		if (CommonUtil.isEmptyString(user.getUserFileFolder())) {
			user.setUserFileFolder("");
		}
		if (CommonUtil.isEmptyString(user.getBlogName())) {
			user.setBlogName(user.getTrueName() + " 的工作室");
		}
		if (CommonUtil.isEmptyString(user.getUserTags())) {
			user.setUserTags("");
		} else {
			String[] tags = tag_svc.parseTagList(user.getUserTags());
			user.setUserTags(CommonUtil.standardTagsString(tags));
		}
	}

	/**
	 * 检查指定用户对象填写的  unitId, categoryId, subjectId, gradeId 等的合法性
	 * 
	 * @param user
	 */
	private void checkUserRefObject(User user) {

		// 2. 检查 subjectId, gradeId
		if (user.getSubjectId() != null) {
			//System.out.print("user.getSubjectId()="+user.getSubjectId()); 
			Subject subject = null;
			//System.out.print("user.getGradeId()="+user.getGradeId());
			if (user.getGradeId() == null) {
				//subject = subj_svc.getSubjectById(user.getSubjectId());
				List<Subject> subjects = subj_svc.getSubjectByMetaSubjectId(user.getSubjectId());
				if(subjects == null)
					subject=null;
				else if (subjects.size()>0) 
					subject=subjects.get(0);
				else
					subject=null;
			} else {
				subject = subj_svc.getSubjectByMetaData(user.getSubjectId(), user.getGradeId());
			}
			if (subject == null)
				throw new RuntimeException("非法的学科标识：" + user.getSubjectId() + "，该学段学科不存在。");
		}

		// 3. 检查 categoryId
		if (user.getCategoryId() != null) {
			Category category = cate_svc.getCategory(user.getCategoryId());
			if (category == null)
				throw new RuntimeException("非法的工作室分类标识：" + user.getCategoryId() + "，该分类不存在。");
			if (CategoryService.BLOG_CATEGORY_TYPE.equals(category.getItemType()) == false)
				throw new RuntimeException("非法的工作室分类标识：" + user.getCategoryId() + "，该分类不是一个工作室分类。");
		}
	}

	/**
	 *更新用户信息，默认提交到用户服务器 
	 */
	public void updateUser(User user) {
		try{
			updateUser(user,true);
		}catch(RuntimeException ex){
			errorInfo =  ex.getMessage();
			throw ex;
		}
	}
	
	public String getErrorInfo()
	{
		return errorInfo;
	}
	/**
	 * 更新用户信息
	 * 
	 * @param user   用户对象
	 * @param updateToSSO  是否向用户服务器提交更新 true=更新到用户服务器 false=不提交
	 */
	public void updateUser(User user,boolean updateToSSO) {
		
		//System.out.println(user.getLoginName() + "user11111.Status:"+user.getUserStatus());
		
		User old_user = user_dao.getByUserId(user.getUserId());
		if (old_user == null) {
			throw new RuntimeException("致命错误: 未能找到用户：" + user.toDisplayString() + "，的信息，更新失败！");
		}
		
		//System.out.println(user.getLoginName() +"old_user22222.Status:"+old_user.getUserStatus());
		
		user_dao.evict(old_user);

		// 检查填入的各个字段是否合法
		if (user.getLoginName() == null || user.getLoginName().length() == 0)
			throw new RuntimeException("非法的登录名");

		// 填写缺省字段
		fillDefaultFields(user);

		// 如下字段不从页面提交, 用 old_user 中的值覆盖 user 中的
		user.setVirtualDirectory(old_user.getVirtualDirectory());
		user.setUserFileFolder(old_user.getUserFileFolder());
		user.setUserStatus(old_user.getUserStatus());
		user.setUserGroupId(old_user.getUserGroupId());
		user.setUsn(old_user.getUsn());	
		
		//System.out.println(user.getLoginName() +"user33333.Status:"+user.getUserStatus());
		
		// 检查  unitId, categoryId, subjectId, gradeId 等合法性
		checkUserRefObject(user);
		// 更新标签
		if (user.getUserTags().equals(old_user.getUserTags()) == false) {
			String[] tags = tag_svc.parseTagList(user.getUserTags());
			String[] origin_tags = tag_svc.parseTagList(old_user.getUserTags());
			tag_svc.createUpdateMultiTag(user.getUserId(), ObjectType.OBJECT_TYPE_USER, tags, origin_tags);
		}
		com.octopus.sso.model.User ssoUser =null;
		if(updateToSSO){
			// 同步统一用户的真是姓名，邮件地址，角色Id
			ServletRequest request = null;
			if(JitarRequestContext.getRequestContext() != null){
				request = JitarRequestContext.getRequestContext().getRequest();
			}
			if(null != request.getServletContext().getFilterRegistration("CASValidationFilter")){
				//支持原来的 CAS的 usermgr3的单点登录系统
				updateUserInfoByUsername(user.getLoginName(), user.getTrueName(), user.getEmail(), user.getPositionId());
			}
			else if(null != request.getServletContext().getFilterRegistration("ssoUserFilter")){
				//支持新版的 SSO
				try{
					user.setVersion(old_user.getVersion()+1);	//设置版本+1
					ssoUser = updateSSOUser(request,user);
					if(null == ssoUser){
						//更新失败
						throw new RuntimeException("向用户服务器上更新用户信息失败！");
					}
					
					if(null != ssoUser.getUserIcon() && ssoUser.getUserIcon().length()>0){
						user.setUserIcon(ssoUser.getUserIcon());
					}
					user.setVersion(ssoUser.getVersion());
					
				}catch(RuntimeException ex){
					ex.printStackTrace();
					throw ex;
				}
			}
		}
		
		//System.out.println(user.getLoginName() +"user-----.Status:"+user.getUserStatus());
		// 更新用户信息
		user_dao.updateUser(user);
		
		//System.out.println(user.getLoginName() +"user++++++.Status:"+user.getUserStatus());
		
		user_dao.flush();
		
		//如果是红杉树视频会议，需要同步到这个视频会议服务器上
        if(Utils.isInfowarelabMeeting()){
        	InfowarelabService infowarelabService = (InfowarelabService)ContextLoader.getCurrentWebApplicationContext().getBean("infowarelabService");
        	if(null!=infowarelabService){
        		//去视频服务器上查询用户
        		List<MeetingQueryParam> queryparams = new ArrayList<MeetingQueryParam>();
        		MeetingQueryParam queryparam = new MeetingQueryParam();
        		queryparam.setType(1);
        		queryparam.setValue(user.getLoginName());
        		queryparams.add(queryparam);
        		CemUsers cemUsers = infowarelabService.getCemUserBatch(queryparams);
        		if(null != cemUsers){
        			if(null != cemUsers.getUsers() && cemUsers.getUsers().size() > 0){
        				CemUser cemUser = cemUsers.getUsers().get(0);
                		String firstName = user.getTrueName().substring(0,1);
                		String lastName = firstName;
                		if(user.getTrueName().length() > 1) {
                			lastName = user.getTrueName().substring(1);
                		}
                		
                		cemUser.setEmail(user.getEmail());
                		cemUser.setNickname(user.getNickName());
                		cemUser.setUserName(user.getLoginName());
                		cemUser.setGender(user.getGender());
                		cemUser.setFirstName(user.getTrueName());
                		cemUser.setLastName(lastName);
                		cemUser.setPassword(user.getPassword());
                		cemUser.setCellphone(user.getMobilePhone());  //手机号码
                		boolean bupdateOk = infowarelabService.updateCemUser(cemUser);
                		if(!bupdateOk){
                			System.out.println("更新视频服务器用户信息出现错误:");
                			System.out.println(infowarelabService.getReason());
                		}
                		
        			}
        		}
        	}
        }
        

		// 更新缓存
		refreshCache(user);
		
		//System.out.println(user.getLoginName() +"user0000099999+.Status:"+user.getUserStatus());
	}	
	
	/**
	 * 更新用户信息到用户服务器
	 */
	private com.octopus.sso.model.User updateSSOUser(ServletRequest request,User user){
        String SSOServerURL = request.getServletContext().getInitParameter("SSOServerURL");
        String SSOServerURL1 = SSOServerURL;
        String SSOServerURL2 = SSOServerURL;
        if(SSOServerURL.indexOf(";")>-1){
        	String[] arrayUrl = SSOServerURL.split("\\;");
        	SSOServerURL1 = arrayUrl[0];
        	SSOServerURL2 = arrayUrl[1];
        }         
        if(SSOServerURL2.endsWith("/")){
        	SSOServerURL2 = SSOServerURL2.substring(0, SSOServerURL2.length()-1);
        }		
		String userServiceUrl = SSOServerURL2 + request.getServletContext().getFilterRegistration("ssoUserFilter").getInitParameter("userServiceUrl");
		
		//得到远程用户服务对象
		com.octopus.sso.service.UserService ssoUserService = null;
        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setConnectTimeout(5000L); // 毫秒
        try {
        	ssoUserService = (com.octopus.sso.service.UserService) factory.create(com.octopus.sso.service.UserService.class, userServiceUrl);
        }catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }	
        if(null == ssoUserService){
        	throw new RuntimeException("用户服务器上用户服务为空");
        	//return null;
        }
        
        //得到用户信息
        com.octopus.sso.model.User ssoUser = ssoUserService.getUserById(user.getUserGuid());
        if(null == ssoUser){
        	//用户服务器上没有此用户
        	throw new RuntimeException("用户服务器上没有此用户");
        	//return null;
        }
        Unit unit = unitService.getUnitById(user.getUnitId());
        //处理头像文件
        String iconFilePath = "";
		String iconFileName = "";
		String base64UserIcon = "";
		if(null != user.getUserIcon() && user.getUserIcon().length() > 0){
			iconFilePath = request.getServletContext().getRealPath("/"+user.getUserIcon());
			File iconFile = new File(iconFilePath);
			if (iconFile.exists() == true){
				base64UserIcon = com.chinaedustar.util.PicProcessUtil.getPicBASE64(iconFilePath);
				iconFileName = iconFile.getName();
				
		        ssoUser.setBase64UserIcon(base64UserIcon);
		        //icon规则：
		        if(iconFileName.indexOf(".") > -1){
		            iconFileName = ssoUser.getPassportId() +  iconFileName.substring(iconFileName.lastIndexOf("."));
		        }		        
		        ssoUser.setUserIcon(iconFileName);	
			}
		}
        //设置更新的属性
        ssoUser.setTrueName(user.getTrueName());
        ssoUser.setNickName(user.getNickName());
        ssoUser.setUnitId(user.getUnitId());
        ssoUser.setUnitName(unit.getUnitName());
        ssoUser.setEmail(user.getEmail());
        ssoUser.setGender(Integer.parseInt(""+user.getGender()));
        ssoUser.setVersion(ssoUser.getVersion()+1);  
        ssoUser.setQq(user.getQq());
        ssoUser.setMobile(user.getMobilePhone());  //手机号码
        ssoUser.setCardId(user.getIdCard());
        ssoUser.setCardType(1);
        String userJson = JSON.toJSONString(ssoUser);
        //更新用户	
        String result = null;
        result = ssoUserService.updateUserRemote(userJson);
        ServiceObject so = JSON.parseObject(result,ServiceObject.class);
        if(so.success){
        	//更新成功，得到从服务器返回的用户对象
        	ssoUser = JSON.parseObject(so.returnobject, com.octopus.sso.model.User.class);
        	return ssoUser;
        }else{
        	//得到从服务器返回的错误
        	ResultCode rc = JSON.parseObject(so.returnobject, ResultCode.class);
			System.out.println("更新用户信息失败："+rc.failureName);
			throw new RuntimeException("更新用户信息失败："+rc.failureName);
			//return null;
        }
        
	}
	/**
	 * 根据用户名修改用户的真实姓名、邮件地址和角色Id
	 * 
	 * @param username
	 * @param trueName
	 * @param email
	 * @param role
	 */
	private void updateUserInfoByUsername(String username, String trueName, String email, int role) {
		ServletRequest request = null;
		if(JitarRequestContext.getRequestContext() != null){
			request = JitarRequestContext.getRequestContext().getRequest();
		}
		Client client = new Client();
		String userMgrUrl = GetUserMgrUrl(request);
		if( userMgrUrl !=null ){
			logger.info("（根据用户名修改用户真实姓名、邮件地址和用户角色）统一用户地址：" + userMgrUrl);
			client.setUserMgrUrl(userMgrUrl);
			String result = client.updateUserInfoByUsername(username, trueName, email, role);
			if ("ok".equals(result)) {
				logger.info("（根据用户名修改用户真实姓名、邮件地址和用户角色）操作成功！" + username);
			} else if ("wrong".equals(result)) {
				logger.info("（根据用户名修改用户真实姓名、邮件地址和用户角色）出现错误！" + username);
			}
		}
	}
	

	/**
	 * 给新注册的用户发送一条欢迎短消息
	 *
	 * @param user
	 */
	private void sendWelcomeMessage(User user) {
		if (msg_svc == null) {
			logger.info("未配置消息服务，因此没有给用户：" + user + "，发送欢迎短消息");
			return;
		}

		// 从配置中读取欢迎消息的 title, content 设置
		Message message = new Message();
		message.setSendId(1); // system
		message.setReceiveId(user.getUserId()); // 接收者
		message.setTitle("欢迎您来到教研系统"); // 标题.
		message.setContent("欢迎您.(此消息为系统欢迎消息，您不需要回复此消息)");
		msg_svc.sendMessage(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.UserService#getUserList(cn.edustar.jitar.service.UserQueryParam, cn.edustar.data.Pager)
	 */
	public List<User> getUserList(UserQueryParam param, Pager pager) {
		return user_dao.getUserList(param, pager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.UserService#getUserList()
	 */
	public List<User> getUserList() {
		return user_dao.getUserList();
	}
	
	public Object statForUser(User user) {
		Object result = user_dao.statForUser(user);
		
		// 更新缓存
		refreshCache(user);
		return result;
	}
	
	public void statHistoryArticleForUser(User user){
		user_dao.statHistoryArticleForUser(user);
		refreshCache(user);
	}
	
	public Object statAllUser() {
		return user_dao.statAllUser();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.UserService#getUserAdminDataTable(cn.edustar.jitar.service.AdminUserQueryParam, cn.edustar.data.Pager)
	 */
	public DataTable getUserAdminDataTable(AdminUserQueryParam param, Pager pager) {		
		List<Object[]> list = user_dao.getUserAdminDataTable(param, pager); // 原始数据		
		DataTable dt = new DataTable(new DataSchema(param.selectFields)); // 组装为 DataTable
		dt.addList(list);
		return dt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.UserService#removeUser(cn.edustar.jitar.pojos.User)
	 */
	public void removeUser(User user) {
		this.updateUserStatus(user, User.USER_STATUS_DELETED);
		refreshCache(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.UserService#isDuplicateLoginName(java.lang.String)
	 */
	public boolean isDuplicateLoginName(String strLoginName) {
		return this.user_dao.isDuplicateLoginName(strLoginName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.UserService#isDuplicateEmail(java.lang.String)
	 */
	public boolean isDuplicateEmail(String strEmail) {
		return this.user_dao.isDuplicateEmail(strEmail);
	}

	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.UserService#updateUserStatus(cn.edustar.jitar.pojos.User, int)
	 */
	public void updateUserStatus(User user, int status) {
		ServletRequest request = null;
		if(JitarRequestContext.getRequestContext() != null){
			request = JitarRequestContext.getRequestContext().getRequest();
		}
		user_dao.updateUserStatus(user, status);
		user.setUserStatus(status);
		refreshCache(user);
		try{
		Client client = new Client();
		String userMgrUrl  = GetUserMgrUrl(request);
		if(userMgrUrl!=null){
			logger.info("（根据用户名修改用户状态）统一用户地址：" + userMgrUrl);
			client.setUserMgrUrl(userMgrUrl);
			String result = client.updateStatusByUsername(user.getLoginName(), status);
			if ("ok".equals(result)) {
				logger.info("（根据用户名修改用户状态）操作成功！" + user.getLoginName());
			} else if ("wrong".equals(result)) {
				logger.info("（根据用户名修改用户状态）出现错误！" + user.getLoginName());
			}
		}
		}catch(Exception ex){
		    
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.UserService#deleteUser(int)
	 */
	public void deleteUser(int userId) {
		ServletRequest request = null;
		if(JitarRequestContext.getRequestContext() != null){
			request = JitarRequestContext.getRequestContext().getRequest();
		}
		User user = getUserById(userId);
		user_dao.deleteUser(user);
		refreshCache(user);

		Client client = new Client();
		String userMgrUrl = GetUserMgrUrl(request);
		if(userMgrUrl != null){
			logger.info("（根据用户名删除用户）统一用户地址：" + userMgrUrl);
			client.setUserMgrUrl(userMgrUrl);
			String result = client.deleteUser(user.getLoginName());
			if ("ok".equals(result)) {
				logger.info("（根据用户名删除用户）操作成功！" + user.getLoginName());
			} else if ("wrong".equals(result)) {
				logger.info("（根据用户名删除用户）出现错误！" + user.getLoginName());
			}
		}
	}

	public String getPassword(String userloginname) {
		ServletRequest request = null;
		if(JitarRequestContext.getRequestContext() != null){
			request = JitarRequestContext.getRequestContext().getRequest();
		}
		Client client = new Client();
		String userMgrUrl = GetUserMgrUrl(request);
		if( userMgrUrl !=null ){
			logger.info("（根据用户名得到用户对象）统一用户地址：" + userMgrUrl);
			client.setUserMgrUrl(userMgrUrl);
			return client.getUserByUsername(userloginname).getPassword();
		}else{
			return null;
		}
	}
	
		
	/* (non-Javadoc)
	 *
	 * @see cn.edustar.jitar.service.UserService#getUserUnitList(int)
	 */
	public List<User> getUserUnitList(int unitId) {
		return user_dao.findByUnitId(unitId);
	}

	/**
	 * 得到用户机构
	 * @param user
	 * @return
	 */
	public Unit getUnitByUser(User user){
		if(user==null){
			return null;
		}
		if(user.getUnitId()==null){
			return null;
		}
		return unitService.getUnitById(user.getUnitId());
	}

	/**
	 * 设置为待推送
	 * @param user
	 * @param operateUser
	 */
	public void setToPush(User user,User operateUser)
	{
		this.user_dao.setToPush(user, operateUser);
	}
	
	/**
	 * 设置为已经推送
	 * @param user
	 */
	public void setPushed(User user)
	{
		this.user_dao.setPushed(user);
	}
	
	/**
	 * 取消推送
	 * @param user
	 */
	public void setUnPush(User user)
	{
		this.user_dao.setUnPush(user);
	}
	
	public void updateUserUnit(User user)
	{
		this.user_dao.updateUser(user);
		refreshCache(user);
	}
	
	public void saveOrUpdateUserSubjectGrade(UserSubjectGrade userSubjectGrade)
	{
		this.user_dao.saveOrUpdateUserSubjectGrade(userSubjectGrade);
	}
	public void deleteUserSubjectGrade(UserSubjectGrade userSubjectGrade)
	{
		this.user_dao.deleteUserSubjectGrade(userSubjectGrade);
	}
	public UserSubjectGrade getUserSubjectGradeById(int userSubjectGradeId)
	{
		return this.user_dao.getUserSubjectGradeById(userSubjectGradeId);
	}
	public List<UserSubjectGrade> getAllUserSubjectGradeListByUserId(int userId)
	{		
		User u = this.user_dao.getByUserId(userId);
		if(u != null)
		{
			List<UserSubjectGrade> usgList = new ArrayList<UserSubjectGrade>();
			UserSubjectGrade usg = new UserSubjectGrade();
			usg.setUserId(u.getUserId());
			usg.setSubjectId(u.getSubjectId());
			usg.setGradeId(u.getGradeId());
			usgList.add(usg);
			usgList.addAll(this.getUserSubjectGradeListByUserId(userId));			
			return usgList;
		}
		return null;
	}
	
	public List<UserSubjectGrade> getUserSubjectGradeListByUserId(int userId)
	{
		return this.user_dao.getUserSubjectGradeListByUserId(userId);
	}
	
	
	public int getMaxUserId()
	{
		return this.user_dao.getMaxUserId();
	}
	public int getMinUserId()
	{
		return this.user_dao.getMinUserId();
	}
	
	public void addVisitCount(int userId)
	{
		this.user_dao.addVisitCount(userId);
	}
	
	/** 用户类型：如名师、教研员、专家等 */
	private final String USERTYPEKEY = "UserTypeKey";
	public void saveOrUpdateUserType(UserType userType)
	{
		this.user_dao.saveOrUpdateUserType(userType);
		cache_svc.remove(USERTYPEKEY);
	}
	public void deleteUserType(UserType userType){
		this.user_dao.deleteUserType(userType);
		cache_svc.remove(USERTYPEKEY);
	}
	public void updateUserType(int userId, String userType){
		this.user_dao.updateUserType(userId, userType);
	}
	@SuppressWarnings("unchecked")
	public List<UserType> getAllUserType(){
		List<UserType> ls = (List<UserType>)cache_svc.get(USERTYPEKEY);
		if(ls == null){
			ls = this.user_dao.getAllUserType();
			cache_svc.put(USERTYPEKEY, ls);
		}
		if(ls == null || ls.size() == 0) return null;
		return ls;
	}
	public UserType getUserTypeById(int userTypeId){
		List<UserType> ls = this.getAllUserType();
		if(ls == null || ls.size() == 0)
		{
			return null;
		}
		for(UserType ut : ls)
		{
			if(ut.getTypeId() == userTypeId)
			{
				return ut;
			}
		}
		return null;
	}
	public UserType getUserTypeByName(String userTypeName)
	{
		List<UserType> ls = this.getAllUserType();
		if(ls == null || ls.size() == 0)
		{
			return null;
		}
		for(UserType ut : ls)
		{
			if(ut.getTypeName().equalsIgnoreCase(userTypeName))
			{
				return ut;
			}
		}
		return null;
	}
	
	public List<Integer> getAllUserIdByUserType(int userType){
	    return this.user_dao.getAllUserIdByUserType(userType);
	}
	
	public int getUserCount(){
	    return this.user_dao.getUserCount();
	}
	public void updateUserIconTemp(User user){
	    this.user_dao.updateUserIconTemp(user);
	}
}
