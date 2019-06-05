package cn.edustar.jitar.action;

import java.util.List;

import org.jasig.cas.client.util.CasConst;

import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.usermgr.Client;

/**
 * 作为 UserAction, AdminUserAction 的基类
 * 
 *
 */
public abstract class BaseUserAction extends ManageBaseAction {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1678648046155185724L;

	/** 用户服务 */
	private UserService userService;

	/** 学科服务 */
	private SubjectService subjectService;

	/** 区县、机构服务 */
	private UnitService unitService;
	
	/** 真实姓名 */
	private String trueName;
	
	/** 呢称 */
	private String nickName;
	
	/** 角色 */
	private String role;
	
	/** 学科ID */
	private Integer subjectId;
	
	/** 学段ID */
	private Integer gradeId;
	
	
	/** 身份证号码 */
	private String IDCard;

	/** 配置对象 */
	private Configure config;

	/** 配置服务 */
	@SuppressWarnings("unused")
	private ConfigService cfg_svc;
	
	
	/**
	 * 把学科列表放到模板环境中
	 */
	protected void putMetaSubjectList() {
		Object subject_list = subjectService.getMetaSubjectList();
		setRequestAttribute("subject_list", subject_list);
	}
	
	/**
	 * 把学段列表放到模板环境中
	 */
	/*TODO:检查这里，使用了基类的方法
	protected void putGradeList() {
		Object grade_list = subjectService.getGradeList();
		setRequestAttribute("grade_list", grade_list);
	}
	*/
	
	/**
	 * 得到用户缺省可用头像
	 *
	 * @return
	 */
	protected List<String> getUserDefaultIconList() {
		List<String> icon_list = getDefaultIconList(getServletContext());
		icon_list.add(0, "images/default.gif");
		return icon_list;
	}

	/**
	 * 收集用户的修改信息
	 *
	 * @param user
	 * @return
	 */
	protected User collectUserObject(User user, ConfigService cfg_svc) {
		// 以下属性是根据系统配置，如果用户修改了则需要将用户状态设置为：待审核，管理员除外
		this.config = cfg_svc.getConfigure();

		// set 数据
		user.setUserId(param_util.getIntParam("userId"));
		user.setLoginName(param_util.safeGetStringParam("loginName"));
		if(param_util.safeGetStringParam("userIcon").length() > 0){
			if(!param_util.safeGetStringParam("userIcon").equals(param_util.safeGetStringParam("userIconOld"))){
				//头像有修改
				user.setUserIcon(param_util.safeGetStringParam("userIcon"));
			}
		}
		user.setEmail(param_util.safeGetStringParam("email", ""));
		user.setBlogName(param_util.safeGetStringParam("blogName", ""));
		user.setUserTags(CommonUtil.standardTagsString(param_util.safeGetStringParam("userTags", "")));
		user.setBlogIntroduce(param_util.safeGetStringParam("blogIntroduce", ""));
		user.setGender((short)param_util.getIntParam("gender"));
		user.setCategoryId(param_util.getIntParamZeroAsNull("categoryId"));
		//
		user.setQq(param_util.safeGetStringParam("QQ", ""));
		
		String mobilePhone = param_util.safeGetStringParam("MobilePhone", "");
		if(mobilePhone!=null && mobilePhone.length()>0){	//手机号码验证
			if (mobilePhone.length() == 11 && CommonUtil.isNumber(mobilePhone)) {
				
			} else {
				this.addActionError("注册信息中手机号码输入错误！");
			}
		}
		user.setMobilePhone(mobilePhone);  //手机号码
		
		// 角色
		role = String.valueOf(user.getPositionId());
		// 该配置项已经从数据库中删除
		//if (this.config.getBoolValue(Configure.USER_REGISTER_MUST_ROLE, false) == true) {
			if (!param_util.safeGetStringParam("role").equals(role)) {
				if (!"admin".equals(user.getLoginName())) {
					//System.out.println("role="+role+",getparamrole="+param_util.safeGetStringParam("role"));
					userService.updateUserStatus(user, 1);
				}
			}
		//}
		// 管理员不能修改自己的角色
		//if ("admin".equals(user.getLoginName())) {
		//	super.addActionError("请不要试图修改系统管理员的角色！");
		//} else {
			user.setPositionId(param_util.getIntParam("role"));
		//}
		
		// 真实姓名
		trueName = user.getTrueName();
		if (this.config.getBoolValue(Configure.PROFILE_UPDATE_TRUENAME_NEEDAUDIT, false) == true) {
			if (!param_util.safeGetStringParam("trueName").equals(trueName)) {
				if (!"admin".equals(user.getLoginName())) {
					//System.out.println("trueName="+trueName+",getparam trueName="+param_util.safeGetStringParam("trueName"));
					userService.updateUserStatus(user, 1);
				}
			}
		}
		user.setTrueName(param_util.safeGetStringParam("trueName", ""));

		// 呢称
		/*nickName = user.getNickName();
		if (this.config.getBoolValue(Configure.TRUENAME_EQUALS_NICKNAME, false) == true) {
			if (!CommonUtil.stringEquals(user.getTrueName(), user.getNickName())) {
				if (!"admin".equals(user.getLoginName())) {
					userService.updateUserStatus(user, 1);
					this.addFieldError("trueEqualsNick", "真实姓名和呢称必须一致！");
				}
			}
		}
		user.setNickName(param_util.safeGetStringParam("nickName", ""));*/
		user.setNickName(user.getTrueName());
		/*
		 * 此段代码已经放到 user_subject_grade_edit.py
		 * // 学科
		subjectId = user.getSubjectId();
		if (this.config.getBoolValue(Configure.PROFILE_UPDATE_SUBJECT_NEEDAUDIT, false) == true) {
			if (!param_util.safeGetIntParam("subjectId").equals(subjectId)) {
				if (!"admin".equals(user.getLoginName())) {
					userService.updateUserStatus(user, 1);
				}
			}
		}
		user.setSubjectId(param_util.getIntParamZeroAsNull("subjectId"));
		
		// 学段
		gradeId = user.getGradeId();
		if (this.config.getBoolValue(Configure.PROFILE_UPDATE_GRADE_NEEDAUDIT, false) == true) {
			if (!param_util.safeGetIntParam("gradeId").equals(gradeId)) {
				if (!"admin".equals(user.getLoginName())) {
					userService.updateUserStatus(user, 1);
				}
			}
		}
		user.setGradeId(param_util.getIntParamZeroAsNull("gradeId"));
		*/
		
				
		// 身份证
		String sIdCard = param_util.safeGetStringParam("IDCard", "");
		if (this.config.getBoolValue(Configure.USER_REGISTER_MUST_IDCARD, false) == true) {
			if (param_util.safeGetStringParam("IDCard").equals("")) {
				this.addActionError("注册信息中身份证号码必须填写！");
			}
			if ((sIdCard.length() == 15) || (sIdCard.length() == 18)) {
			} else {
				//if (!"admin".equals(user.getLoginName())) {
				//	userService.updateUserStatus(user, 1);
				//}			
				this.addActionError("注册信息中身份证号码输入错误！");
			}
		}
		user.setIdCard(param_util.safeGetStringParam("IDCard", ""));
		
		return user;
	}
	
	/**
	 * 根据'userId'修改用户密码.
	 *
	 * @param userId
	 */
	protected void resetPwd(int userId, String password) {
		User user = userService.getUserById(userId, true);
		String loginName = user.getLoginName();
		try{
			userService.resetPassword(loginName, password);
		}catch(RuntimeException ex){
			throw new RuntimeException(ex.getMessage());
		}
	}

	// 配置表中根据key修改value
	protected void updateUser(String key, String value) {
		Client client = new Client();
		String userMgrUrl = request.getServletContext().getFilterRegistration("CASValidationFilter").getInitParameter("casServerUrlPrefix");
		if(userMgrUrl==null || userMgrUrl.length()==0){
			userMgrUrl=CasConst.getInstance().getCasServerUrlPrefix();
		}
		//System.out.println("（配置表中根据key修改value）统一用户地址：" + userMgrUrl);
		client.setUserMgrUrl(userMgrUrl);
		String result = client.updateValueByKey(key, value);
		/*if ("ok".equals(result)) {
			System.out.println("（配置表中根据key修改value） 操作成功！");
		} else {
			System.out.println("（配置表中根据key修改value） 操作失败！");
		}*/
	}

	/** 用户服务 */
	public UserService getUserService() {
		return this.userService;
	}
	
	/** 用户服务 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/** 学科服务 */
	public SubjectService getSubjectService() {
		return subjectService;
	}

	/** 学科服务 */
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	
	/** 区县、机构服务 */
	public UnitService getUnitService() {
		return unitService;
	}

	/** 区县、机构服务 */
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getIDCard() {
		return IDCard;
	}

	public void setIDCard(String card) {
		IDCard = card;
	}
	
}

