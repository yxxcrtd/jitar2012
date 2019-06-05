package cn.edustar.jitar.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasig.cas.client.util.CasConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.jitar2Infowarelab.model.CemUser;
import com.jitar2Infowarelab.service.InfowarelabService;
import com.jitar2Infowarelab.utils.Utils;
import com.octopus.sso.service.UserService;

import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.ProductConfigService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.MD5;
import cn.edustar.sso.model.ResultCode;
import cn.edustar.sso.model.ServiceObject;
import cn.edustar.usermgr.BaseUser;
import cn.edustar.usermgr.Client;

/**
 * <p>
 * 用户注册
 * </p>
 * 
 * 采用 Ajax 方式实现用户注册功能。
 * 
 * @author admin
 * 
 * 2013-5-13 修改
 */
public class RegisterAction extends BaseUserAction {
    private Logger log = LoggerFactory.getLogger(RegisterAction.class);
    /** serialVersionUID */
    private static final long serialVersionUID = -9096117271130156189L;

    /** 分类服务 */
    private CategoryService categoryService;

    /** 配置服务 */
    private ConfigService configService;
    
    /** 权限服务 */
    private AccessControlService accessControlService;

    private ProductConfigService productConfigService;

    /** 配置对象 */
    private Configure config;

    /** 学段对象 */
    private Grade grade;

    /** 学科对象 */
    private Unit unit;

    /** 记录错误或者提示信息 */
    private List<Map<String, String>> infoList = new ArrayList<Map<String, String>>();

    /** 记录是否存在验证错误 */
    private Boolean hasValidateError = false;

    /** 无参构造函数 */
    public RegisterAction() {
    }

    @Override
    protected String execute(String cmd) throws Exception {        
        //登录用户不允许进入到这个页面       
        if(getLoginUser() != null){
            super.response.sendRedirect(request.getContextPath() + "/index.action");
            return NONE;
        }
        
        
        this.config = configService.getConfigure();
        if (cmd == null || cmd.length() == 0) {
            cmd = "create";
        }

        SetRegisterContition();

        if ("create".equalsIgnoreCase(cmd)) {
            return create();
        } else if ("save".equalsIgnoreCase(cmd)) {
            return save();
        } else {
            return Input();
        }
    }

    /**
     * 设置注册中的一些配置
     */
    private void SetRegisterContition() {
        setRequestAttribute("email_need", this.config.getBoolValue(Configure.USER_REGISTER_EMAIL_UNIQUE, true)); // 邮件是否唯一
        setRequestAttribute("subject_need", this.config.getBoolValue(Configure.USER_REGISTER_MUST_SUBJECT, true));// 是否必须选择学科
        setRequestAttribute("grade_need", this.config.getBoolValue(Configure.USER_REGISTER_MUST_GRADE, true));// 是否必须选择学段
    }

    /**
     * 显示'创建新用户'页面
     * 
     * @return
     * @throws Exception
     */
    private String create() throws Exception {
        User user = new User();
        user.setGender((short) 1);
        setRequestAttribute("user", user);
        return Input();
    }

    /**
     * 显示'注册'页面
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    private String Input() {
        // 判断是否关闭了注册
        if (config.getBoolValue(Configure.USER_REGISTER_ENABLED, false) == false) {
            this.addActionError("系统配置已经禁止用户注册了。");
            return ERROR;
        }

        // 得到学科列表；年级列表；区县列表；机构列表
        super.putMetaSubjectList();
        super.putGradeList();

        // 将'系统分类树'放到'request'中
        putSysCategoryToRequest();

        // 缺省头像列表
        setRequestAttribute("icon_list", getUserDefaultIconList());

        if (this.config.getBoolValue(Configure.USER_REGISTER_MUST_IDCARD, false) == true) {
            this.setRequestAttribute("idcard", "<span style='color: #FF0000; font-size: 14px; font-weight: bold;'>*</span>");
        }
        if (this.hasActionErrors()) {
            List errors = (List) getRequest().getAttribute("actionErrors");
            if (errors == null) {
                errors = new ArrayList();
                getRequest().setAttribute("actionErrors", errors);
            }
        }
        return INPUT;
    }

    /**
     * 检查输入的密码、确认密码是否相等以及是否输入
     */
    private void checkPasswordValid() {
        String userPassword = param_util.safeGetStringParam("userPassword", "");
        String rePassword = param_util.safeGetStringParam("rePassword", "");
        String userPasswordCheck = null;
        String rePasswordCheck = null;

        // 如果严重出错，则返回到前端
        if (userPassword == null || userPassword.length() == 0) {
            userPasswordCheck = "请输入密码！";
        }
        if (userPassword.length() < 6) {
            userPasswordCheck = "密码长度小于6位！";
        } else if (userPassword.length() > 20) {
            userPasswordCheck = "密码长度大于20位！";
        }
        if (rePassword == null || rePassword.length() == 0) {
            rePasswordCheck = "请输入确认密码！";
        } else if (CommonUtil.stringEquals(userPassword, rePassword) == false) {
            rePasswordCheck = "两次输入的密码不一致！";
        }

        // 记录用户密码检查结果
        if (null == userPasswordCheck) {
            this.addItemInfo("userPassword", null);
        } else {
            this.addItemInfo("userPassword", userPasswordCheck);
        }

        // 记录重复用户密码检查结果
        if (null == rePasswordCheck) {
            this.addItemInfo("rePassword", null);
        } else {
            this.addItemInfo("rePassword", rePasswordCheck);
        }
    }

    /**
     * 保存注册信息
     * 
     * @return
     * @throws IOException
     */
    private String save() throws IOException {
        if (null != request.getServletContext().getFilterRegistration("CASValidationFilter")) {
            // 支持原来的 CAS的 usermgr3的单点登录系统
            return saveUser_CAS();
        } else if (null != request.getServletContext().getFilterRegistration("ssoUserFilter")) {
            // 支持新版的 SSO
            return saveUser_SSO();
        } else {
            return null;
        }
    }
    /**
     * 支持新版的 SSO
     * 
     * @return
     * @throws IOException
     */
    private String saveUser_SSO() throws IOException {
        response.setContentType("application/json");
        String platformUrl = CommonUtil.getSiteUrl(request);
        if (platformUrl.startsWith("http://")) {
            platformUrl = platformUrl.substring("http://".length());
        }
        if (platformUrl.endsWith("/")) {
            platformUrl = platformUrl.substring(0, platformUrl.length() - 1);
        }

        // 返回JSON格式。构造JSON；
        JSONObject json = new JSONObject();

        // 判断是否关闭了注册，此操作可能是从地址栏输入
        if (config.getBoolValue(Configure.USER_REGISTER_ENABLED, false) == false) {
            json.put("StatusCode", "1");
            json.put("StatusText", "请不要执行非法操作！");
            response.getWriter().write((JSON.toJSONString(json)));
            return NONE;
        }

        // 获取许可证里面的用户数
        String usercount = productConfigService.getUsercount();
        if (null == usercount || usercount.trim().length() == 0 || CommonUtil.isInteger(usercount) == false) {
            usercount = "0";
        }
        List<User> users = null;
        // 0 不限制
        if (0 < Integer.valueOf(usercount)) {
            // 得到用户中的用户总数
            users = this.getUserService().getUserList();
            if (users.size() > Integer.valueOf(usercount)) {
                json.put("StatusCode", "1");
                json.put("StatusText", "对不起，本系统只能注册" + Integer.valueOf(usercount) + "个用户！");
                response.getWriter().write((JSON.toJSONString(json)));
                return NONE;
            }
        }

        // 根据传入的参数，组合教研的用户对象
        User user = collectUser(new User());
        user.setUserId(0);
        // 检测字段
        checkValid(user);
        // 验证完毕，如果没有错误，则不用返回到前端了
        if (hasValidateError) {
            json.put("StatusCode", "2");
            json.put("StatusText", this.infoList);
            response.getWriter().write(JSON.toJSONString(json));
            return NONE;
        }

        // 向用户服务器提交用户信息，
        com.octopus.sso.model.User returnSsoUser = RegisterRemoteUser(json, user);
        if (returnSsoUser == null) {
            return NONE;
        }
        String password = param_util.safeGetStringParam("userPassword", "");
        String md5Password = com.chinaedustar.util.MD5.crypt(password);
        user.setPassword(md5Password);
        user.setUserGuid(returnSsoUser.getPassportId());
        user.setVersion(returnSsoUser.getVersion());
        user.setUserIcon(returnSsoUser.getUserIcon());  //重新设置UserIcon
        Calendar c = Calendar.getInstance();
        BaseUser baseUser = new BaseUser();
        baseUser.setGuid(user.getUserGuid());
        baseUser.setUsername(user.getLoginName());
        baseUser.setTrueName(user.getTrueName());
        baseUser.setPassword(md5Password);
        baseUser.setEmail(user.getEmail());
        // 新用户注册是否需要管理员审核
        if (this.config.getBoolValue(Configure.USER_REGISTER_NEED_AUDIT, false) == true) {
            baseUser.setStatus(1); // 将注册用户设为待审核状态
            user.setUserStatus(1);
        } else {
            baseUser.setStatus(0);
            user.setUserStatus(0);
        }
        baseUser.setLastLoginIp(request.getRemoteAddr());
        baseUser.setLastLoginTime(c.getTime());
        baseUser.setCurrentLoginIp(request.getRemoteAddr());
        baseUser.setCurrentLoginTime(c.getTime());
        baseUser.setLoginTimes(0);
        baseUser.setQuestion(param_util.safeGetStringParam("question", ""));
        baseUser.setAnswer(MD5.toMD5(param_util.safeGetStringParam("answer", "")));
        baseUser.setUsn(1);
        baseUser.setRole(user.getPositionId());
        setRequestAttribute("user", user);

        // 清除记录的信息
        this.infoList.clear();

        // 创建用户
        this.getUserService().createUser(baseUser, user);
        
        //如果是第一个用户，则默认设置为超级管理员
        int userCount = this.getUserService().getUserCount();
        //System.out.println("userCount=" + userCount);
        //System.out.println("user.getUserId()=" + user.getUserId());
        if(userCount < 2){            
            this.getUserService().updateUserStatus(user, 0);
            AccessControl accessControl = new AccessControl();
            accessControl.setObjectType(AccessControl.OBJECTTYPE_SUPERADMIN);
            accessControl.setObjectId(0);
            accessControl.setObjectTitle("系统超级管理员");
            accessControl.setUserId(user.getUserId());            
            this.accessControlService.saveOrUpdateAccessControl(accessControl);
        }
        
        String statusText = "";
        //如果是红杉树视频会议，需要同步到这个视频会议服务器上
        if(Utils.isInfowarelabMeeting()){
        	InfowarelabService infowarelabService = (InfowarelabService)ContextLoader.getCurrentWebApplicationContext().getBean("infowarelabService");
        	if(null!=infowarelabService){
        		String firstName = user.getTrueName().substring(0,1);
        		String lastName = firstName;
        		if(user.getTrueName().length() > 1) {
        			lastName = user.getTrueName().substring(1);
        		}
        		CemUser cemUser =  new CemUser();
        		cemUser.setAddress("");
        		cemUser.setCellphone("");
        		cemUser.setEmail(user.getEmail());
        		cemUser.setNickname(user.getNickName());
        		cemUser.setUserName(user.getLoginName());
        		cemUser.setGender(user.getGender());
        		//cemUser.setPassword(password);			//原始密码
        		cemUser.setPassword(md5Password);			//MD5加密密码
        		cemUser.setFirstName(user.getTrueName());
        		cemUser.setLastName(lastName);
        		cemUser.setEnabled(true);
        		cemUser.setUserType(1);
        		cemUser.setForceCreate(true);
        		cemUser.setCellphone(user.getMobilePhone());  //手机号码
        		String cemUserId = infowarelabService.createCemUser(cemUser);
        		if(null == cemUserId){
        			//出错误了
        			statusText = "教研用户注册成功，用户注册到视频服务器出错:" + infowarelabService.getReason();
        		}
        	}
        }
        if(statusText.length() == 0){
	        json.put("StatusCode", "0");
	        json.put("StatusText", "");
        }else{
	        json.put("StatusCode", "1");
	        json.put("StatusText", statusText);
        }
        response.getWriter().write(JSON.toJSONString(json));
        return NONE;
    }

    /**
     * 向用户服务器注册用户
     * 
     * @param user
     *            要注册的用户，需要转换为 com.octopus.sso.model.User
     * @return 返回注册成功后的用户对象，里面的passportId,fromSysName,version,nodeCode是服务器更新过的
     * @throws IOException
     */
    private com.octopus.sso.model.User RegisterRemoteUser(JSONObject json, User user) throws IOException {
        // 尝试向用户服务器中增加用户
        String clientCode = request.getServletContext().getInitParameter("clientCode");
        String iconFilePath = getServletContext().getRealPath("/" + user.getUserIcon());
        String iconFileName = "";
        File iconFile = new File(iconFilePath);
        String base64UserIcon = "";
        if (iconFile.exists() == true) {
            base64UserIcon = com.chinaedustar.util.PicProcessUtil.getPicBASE64(iconFilePath);
            iconFileName = iconFile.getName();
        }

        com.octopus.sso.model.User ssoUser = new com.octopus.sso.model.User();
        ssoUser.setUserName(user.getLoginName());
        ssoUser.setTrueName(user.getTrueName());
        ssoUser.setNickName(user.getNickName());
        ssoUser.setPassportId("");
        ssoUser.setUnitId(user.getUnitId());
        ssoUser.setUnitName(unit.getUnitTitle());
        ssoUser.setQq(user.getQq());
        ssoUser.setAddress("");
        ssoUser.setQuestion(param_util.safeGetStringParam("question", ""));
        ssoUser.setAnwser(param_util.safeGetStringParam("answer", ""));
        ssoUser.setCardId(user.getIdCard());
        ssoUser.setCardType(1);
        ssoUser.setMobile(user.getMobilePhone());	//设置手机号吗
        String password = com.chinaedustar.util.MD5.crypt(param_util.safeGetStringParam("userPassword", ""));
        ssoUser.setPassword(password);
        ssoUser.setNodeCode("");
        // 用户服务器上 0=普通 1=学生 2=教师 3=家长
        String userType = "0";
        if (3 == param_util.getIntParam("role")) {
            userType = "2";
        } else if (4 == param_util.getIntParam("role")) {
            userType = "0";
        } else if (5 == param_util.getIntParam("role")) {
            userType = "1";
        }
        ssoUser.setUserType(userType);
        ssoUser.setJobNo("");
        ssoUser.setEmail(user.getEmail());
        ssoUser.setGender(Integer.parseInt("" + user.getGender()));
        ssoUser.setVersion(1);
        ssoUser.setUserStatus(2);
        ssoUser.setFromSysName("教研平台");
        ssoUser.setFromSysCode(clientCode);
        // 目前的做法，如果使用base64UserIcon，则必须传文件名，以后可能不需要。
        ssoUser.setBase64UserIcon(base64UserIcon);
        ssoUser.setUserIcon(iconFileName);

        String userJson = JSON.toJSONString(ssoUser);

        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setConnectTimeout(5000L); // 毫秒
        String result = null;

        //String SSOServerURL = request.getServletContext().getInitParameter("SSOServerURL");
        //if (SSOServerURL.endsWith("/")) {
        //    SSOServerURL = SSOServerURL.substring(0, SSOServerURL.length() - 1);
        //}

        String userServiceUrl = SSOServerURL2 + request.getServletContext().getFilterRegistration("ssoUserFilter").getInitParameter("userServiceUrl");
        try {
            UserService userService = (UserService) factory.create(UserService.class, userServiceUrl);
            result = userService.saveUserRemote(userJson);
            ServiceObject so = JSON.parseObject(result, ServiceObject.class);
            if (so.success) {
                // 更新成功，得到从服务器返回的用户对象
                ssoUser = JSON.parseObject(so.returnobject, com.octopus.sso.model.User.class);
                return ssoUser;
            } else {
                // 得到从服务器返回的错误
                ResultCode rc = JSON.parseObject(so.returnobject, ResultCode.class);
                json.put("StatusCode", "1");
                json.put("StatusText", rc.failureName);
                response.getWriter().write((JSON.toJSONString(json)));
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // 错误
            json.put("StatusCode", "1");
            json.put("StatusText", ex.getMessage());
            response.getWriter().write((JSON.toJSONString(json)));

            return null;
        }
    }

    /**
     * 支持 usermgr3系统的保存用户
     * 
     * @return
     * @throws IOException
     */
    private String saveUser_CAS() throws IOException {
        response.setContentType("application/json");
        String platformUrl = CommonUtil.getSiteUrl(request);
        if (platformUrl.startsWith("http://")) {
            platformUrl = platformUrl.substring("http://".length());
        }
        if (platformUrl.endsWith("/")) {
            platformUrl = platformUrl.substring(0, platformUrl.length() - 1);
        }

        // 返回JSON格式。构造JSON；
        JSONObject json = new JSONObject();

        // 判断是否关闭了注册，此操作可能是从地址栏输入
        if (config.getBoolValue(Configure.USER_REGISTER_ENABLED, false) == false) {
            json.put("StatusCode", "1");
            json.put("StatusText", "请不要执行非法操作！");
            response.getWriter().write((JSON.toJSONString(json)));
            return NONE;
        }

        // 得到统一用户中的用户总数

        Client client = new Client();
        String userMgrUrl = request.getServletContext().getFilterRegistration("CASValidationFilter").getInitParameter("casServerUrlPrefix");
        if (null == userMgrUrl || userMgrUrl.length() == 0) {
            userMgrUrl = CasConst.getInstance().getCasServerUrlPrefix();
        }

        client.setUserMgrUrl(userMgrUrl);
        int userCounts = client.getUserCounts();

        // 获取许可证里面的用户数
        String usercount = productConfigService.getUsercount();
        if (null == usercount || usercount.trim().length() == 0 || CommonUtil.isInteger(usercount) == false) {
            usercount = "0";
        }
        // 0 不限制
        if (0 < Integer.valueOf(usercount) && userCounts >= Integer.valueOf(usercount)) {
            json.put("StatusCode", "1");
            json.put("StatusText", "对不起，本系统只能注册" + Integer.valueOf(usercount) + "个用户！");
            response.getWriter().write((JSON.toJSONString(json)));
            return NONE;
        }

        // 以下验证全部的输入项目，然后统一返回到前端；
        Calendar c = Calendar.getInstance();
        User user = collectUser(new User());
        user.setUserId(0);

        BaseUser baseUser = new BaseUser();
        baseUser.setUsername(user.getLoginName());
        baseUser.setTrueName(user.getTrueName());
        String password = param_util.safeGetStringParam("userPassword", "");
        baseUser.setPassword(MD5.toMD5(password));
        baseUser.setEmail(user.getEmail());
        // 新用户注册是否需要管理员审核
        if (this.config.getBoolValue(Configure.USER_REGISTER_NEED_AUDIT, false) == true) {
            baseUser.setStatus(1); // 将注册用户设为待审核状态
        } else {
            baseUser.setStatus(0);
        }
        baseUser.setLastLoginIp(request.getRemoteAddr());
        baseUser.setLastLoginTime(c.getTime());
        baseUser.setCurrentLoginIp(request.getRemoteAddr());
        baseUser.setCurrentLoginTime(c.getTime());
        baseUser.setLoginTimes(0);
        baseUser.setQuestion(param_util.safeGetStringParam("question", ""));
        baseUser.setAnswer(MD5.toMD5(param_util.safeGetStringParam("answer", "")));
        baseUser.setUsn(1);
        baseUser.setRole(user.getPositionId());

        setRequestAttribute("user", user);
        // 检测字段
        checkValid(user);

        // 验证完毕，如果没有错误，则不用返回到前端了
        if (hasValidateError) {
            json.put("StatusCode", "2");
            json.put("StatusText", this.infoList);
            response.getWriter().write(JSON.toJSONString(json));
            return NONE;
        }

        // 清除记录的信息
        this.infoList.clear();

        // 保存统一用户
        Client client1 = new Client();
        String _userMgrUrl = request.getServletContext().getFilterRegistration("CASValidationFilter").getInitParameter("casServerUrlPrefix");
        if (_userMgrUrl == null || _userMgrUrl.length() == 0) {
            _userMgrUrl = CasConst.getInstance().getCasServerUrlPrefix();
        }
        // 调试完毕，请注释掉下面的语句！！！！
        // System.out.println("（保存统一用户）统一用户地址：" + _userMgrUrl);
        client1.setUserMgrUrl(_userMgrUrl);
        String result = client1.saveNewBaseUser(baseUser);
        log.info("远程用户注册返回的结果： " + result);

        result = result.replace(" ", "").replace("\r", "").replace("\r", "").toLowerCase();

        if (null == client1.getBaseUser() || !result.equals("ok")) {
            json.put("StatusCode", "1");
            json.put("StatusText", "统一用户保存出错！！！");
            response.getWriter().write(JSON.toJSONString(json));
            return NONE;
        }

        // 统一用户成功之后才能进行创建，否则，用户不同步，也无法登陆。
        this.getUserService().createUser(baseUser, user);
        json.put("StatusCode", "0");
        json.put("StatusText", "");
        response.getWriter().write(JSON.toJSONString(json));
        return NONE;
    }
    /**
     * 收集教研用户、统一用户信息
     * 
     * @param user
     * @return
     */
    protected User collectUser(User user) {
        user.setUserId(param_util.getIntParam("userId"));
        user.setLoginName(param_util.safeGetStringParam("loginName").toLowerCase());
        user.setUserIcon(param_util.safeGetStringParam("userIcon", "images/default.gif"));
        user.setEmail(param_util.safeGetStringParam("email", ""));
        user.setBlogName(param_util.safeGetStringParam("blogName", ""));
        user.setIdCard(param_util.safeGetStringParam("IDCard", ""));
        user.setQq(param_util.safeGetStringParam("QQ", ""));
        user.setUserTags(CommonUtil.standardTagsString(param_util.safeGetStringParam("userTags", "")));
        user.setBlogIntroduce(param_util.safeGetStringParam("blogIntroduce", ""));
        user.setGender((short) param_util.getIntParam("gender"));
        user.setCategoryId(param_util.getIntParamZeroAsNull("categoryId"));
        user.setUnitId(param_util.getIntParamZeroAsNull("unitId"));
        user.setTrueName(param_util.safeGetStringParam("trueName", "")); // 真实姓名
        user.setNickName(param_util.safeGetStringParam("trueName", ""));
        user.setSubjectId(param_util.getIntParamZeroAsNull("subjectId")); // 学科
        user.setGradeId(param_util.getIntParamZeroAsNull("gradeId")); // 学段
        user.setPositionId(param_util.getIntParam("role"));
        user.setMobilePhone(param_util.safeGetStringParam("MobilePhone", ""));	//手机号码
        return user;
    }

    /**
     * 检测提交的用户信息是否正确
     * 
     * @param user
     */
    private void checkValid(User user) {

        // 检查登录名
        this.addItemInfo("loginName", checkLoginName(user.getLoginName()));

        // 检测两次输入密码一致
        checkPasswordValid();

        // 检查邮件
        this.addItemInfo("email", checkEmail(user.getEmail()));

        // 检测真实姓名
        this.addItemInfo("trueName", checkTrueName(user.getTrueName()));

        // 检查身份证是否必填 如果没有配置需要验证，则无需验证了
        if (this.config.getBoolValue(Configure.USER_REGISTER_MUST_IDCARD, false) == true) {
            this.addItemInfo("IDCard", checkIDCard(user.getIdCard()));
        }
        if(user.getIdCard() != null && user.getIdCard().length() > 0){
            this.addItemInfo("IDCard", checkIDCard(user.getIdCard()));
        }
        
        if(user.getQq() != null && user.getQq().length() > 0){
            this.addItemInfo("QQ", this.checkQQ(user.getQq()));
        }
        
        if(user.getMobilePhone() != null && user.getMobilePhone().length() > 0){
            this.addItemInfo("MobilePhone", this.checkMobilePhone(user.getMobilePhone()));
        }        

        // 验证问题
        this.addItemInfo("question", this.checkQuestion(param_util.safeGetStringParam("question", null)));

        // 验证问题答案
        this.addItemInfo("answer", this.checkQuestion(param_util.safeGetStringParam("answer", null)));

        // 根据配置检测教师必须选择学科
        String subjectGradeIdCheck = null;
        if (user.getPositionId() == 3) {
            // 教师 检测是否学科必填
            if (this.config.getBoolValue(Configure.USER_REGISTER_MUST_SUBJECT, false) == true) {
                Integer subjectId = user.getSubjectId();
                if (subjectId == null) {
                    subjectGradeIdCheck = "请选择一个学科！";
                    //this.addItemInfo("subject", "请选择一个学科！");
                }
            }
        }
        if (user.getPositionId() == 3 || user.getPositionId() == 5) {
            // 检测是否学段必填
            if (this.config.getBoolValue(Configure.USER_REGISTER_MUST_GRADE, false) == true) {
                Integer gradeId = user.getGradeId();
                if (gradeId == null) {
                    subjectGradeIdCheck = "请选择一个学段！";
                    //this.addItemInfo("grade", "请选择一个学段");
                } else {
                    grade = this.getSubjectService().getGrade(gradeId);
                    if (grade == null) {
                        subjectGradeIdCheck = "没有这个学段！";
                        //this.addItemInfo("grade", "没有这个学段！");
                    }
                }
            }
        }

        if (CommonUtil.isValidName(user.getLoginName()) == false) {
            this.addItemInfo("loginName", "未给出用户登录名 loginName 或该登录名非法");
		}
                
        if (null != subjectGradeIdCheck) {
            this.addItemInfo("subjectGradeId", subjectGradeIdCheck);
        } else {
            this.addItemInfo("subjectGradeId", null);
        }

        // 新用户注册是否需要管理员审核
        if (this.config.getBoolValue(Configure.USER_REGISTER_NEED_AUDIT, false) == true) {
            user.setUserStatus(1); // 将注册用户设为待审核状态.
        }else{
        	user.setUserStatus(0);
        }

        Integer unitId = user.getUnitId();
        String unitIdCheck = null;
        if (unitId == null) {
            unitIdCheck = "请选择一个机构！";
        } else {
            unit = this.getUnitService().getUnitById(unitId);
            if (unit == null) {
                unitIdCheck = "没有这个机构！";
            } else {
                user.setUnitPathInfo(unit.getUnitPathInfo());
            }
        }

        if (null != unitIdCheck) {
            this.addItemInfo("unitId", unitIdCheck);
        } else {
            this.addItemInfo("unitId", null);
        }

        // 检查验证码
        // this.addItemInfo("verifyCode",
        // checkValidateCode(param_util.safeGetStringParam("verifyCode",
        // null)));
    }

    /**
     * 验证'用户登录名'是否重复
     * 
     * @param strLoginName
     * @return
     */
    private boolean isDuplicateLoginName(String strLoginName) {
        if (this.getUserService().isDuplicateLoginName(strLoginName)) {
            return true;
        } else
            return false;
    }

    /**
     * 验证'用户邮箱'是否重复
     * 
     * @param strEmail
     * @return
     */
    private boolean isDuplicateEmail(String strEmail) {
        if (this.getUserService().isDuplicateEmail(strEmail)) {
            return true;
        } else
            return false;
    }

    private String checkLoginName(String loginName) {
        String checkResult = null;
        if (loginName.length() < 6 || loginName.length() > 16) {
            checkResult = "登录名长度必须在 6-16 个字符之间！";
        } else if (CommonUtil.isValidName(loginName) == false) {
            checkResult = "登录名只能用英文字母或数字, 第一个字符必须是字母！";
        } else if (isDuplicateLoginName(loginName)) {
            checkResult = "该登录名已经被使用了！";
        }

        // 禁止注册的字符串，从web.xml配置文件中得到。
        String reservedName = this.servlet_ctxt.getInitParameter("reserved");
        if (null != loginName && loginName.length() > 0 && reservedName.indexOf(loginName.toLowerCase()) > -1) {
            checkResult = "登录名属于受保护的关键字，不能用作登录名，请换一个登录名。";
        }

        // 检查是否重名
        User u = this.getUserService().getUserByLoginName(loginName);
        if (null != u) {
            checkResult = "该用户已经存在，请更换一个登录名。";
        }
        return checkResult;
    }

    /**
     * 简单检查输入的电子邮件是否正确。
     * 
     * @param email
     *            :输入的电子邮箱地址。
     * @return 返回检查结果
     */
    private String checkEmail(String email) {
        String checkResult = null;
        if (this.config.getBoolValue(Configure.USER_REGISTER_EMAIL_UNIQUE, false) == true) {
            if (isDuplicateEmail(email)) {
                checkResult = "该电子邮件地址已经被使用了！";
            }
        }
        if (email == null || email.length() == 0) {
            checkResult = "没有给出邮件地址！";
        } else if (email.indexOf('@') < 0) {
            checkResult = "邮件地址不合法！";
        }

        return checkResult;
    }

    /**
     * 验证输入的用户，是否必填。
     * 
     * @param trueName
     * @return
     */
    private String checkTrueName(String trueName) {
        String checkResult = null;
        if (trueName == null || trueName.length() == 0) {
            checkResult = "请输入真实姓名！";
        } else if (trueName.length() > 10) {
            checkResult = "真实姓名不能多于10个字符！";
        }

        return checkResult;
    }
    
    private String checkQQ(String QQ) {
        String checkResult = null;
        if (QQ == null || QQ.length() == 0) {
            checkResult = "请输入QQ号码！";
        } else if (!CommonUtil.isNumber(QQ)) {  //如果号码过长，isInteger会出错
            checkResult = "QQ号码不是数字！";
        }else if(QQ.length()< 5 || QQ.length() > 20){
            checkResult = "QQ号码长度不正确！";
        }
        return checkResult;
    }

    private String checkMobilePhone(String phone)
    {
        String checkResult = null;
        if (phone == null || phone.length() == 0) {
            checkResult = "请输入手机号码！";
        } else if (!CommonUtil.isNumber(phone)) {
            checkResult = "手机号码不是数字！";
        }else if(phone.length()!= 11){
            checkResult = "手机号码长度不正确,应该是11位！";
        }
        return checkResult;    
    }
    
    private String checkIDCard(String idCard) {
        String checkResult = null;
        if (idCard == null || idCard.length() == 0) {
            checkResult = "请输入身份证号码！";
        } else if (idCard.length() != 15 && idCard.length() != 18) {
            checkResult = "请输入正确的身份证号码！";
        }
        if(!idCard.toLowerCase().matches("(^\\d{15}$)|(^\\d{17}([0-9]|x)$)")){
            checkResult = "身份证号码格式不正确！";
        }

        return checkResult;
    }
    private String checkQuestion(String question) {
        String checkResult = null;
        if (question == null || question.length() == 0) {
            checkResult = "请输入忘记密码时的问题！";
        }
        return checkResult;
    }

    private String checkValidateCode(String v) {
        String checkResult = null;
        Object code = request.getSession().getAttribute("random");
        if (code == null) {
            checkResult = "验证码已失效。";
        }
        if (null == v || v.length() == 0) {
            checkResult = "请输入验证码。";
        } else if (!v.equalsIgnoreCase(code.toString())) {
            checkResult = "验证码不一致。";
        }
        return checkResult;
    }

    /**
     * 将'系统分类树：syscate_tree'放到'request'中
     */
    private void putSysCategoryToRequest() {
        Object syscate_tree = categoryService.getCategoryTree(CategoryService.BLOG_CATEGORY_TYPE);
        setRequestAttribute("syscate_tree", syscate_tree);
    }

    /**
     * 记录验证信息
     * 
     * @param key
     * @param info
     */
    private void addItemInfo(String key, String info) {
        Map<String, String> lst = new HashMap<String, String>();
        lst.put("key", key);
        if (info == null || info.length() == 0) {
            lst.put("status", "0");
        } else {
            lst.put("status", "1");
            hasValidateError = true;
        }
        lst.put("text", info);
        infoList.add(lst);
    }

    /** 分类服务的set方法 */
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /** 配置服务的set方法 */
    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    public void setProductConfigService(ProductConfigService productConfigService) {
        this.productConfigService = productConfigService;
    }

    public void setAccessControlService(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }
}
