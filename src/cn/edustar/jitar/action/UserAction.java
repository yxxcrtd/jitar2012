package cn.edustar.jitar.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.util.CasConst;
import org.springframework.web.context.ContextLoader;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.client.HessianProxyFactory;
import com.jitar2Infowarelab.model.MeetingQueryParam;
import com.jitar2Infowarelab.service.InfowarelabService;
import com.jitar2Infowarelab.utils.Utils;
import com.octopus.sso.service.UserService;






//import cn.edustar.jitar.JitarConst;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.WebSiteManageService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.CropImage;
import cn.edustar.jitar.util.FileCache;
import cn.edustar.jitar.util.PageContent;
import cn.edustar.sso.model.ResultCode;
import cn.edustar.sso.model.ServiceObject;
import cn.edustar.usermgr.BaseUser;
import cn.edustar.usermgr.Client;
///import cn.edustar.jitar.model.SiteUrlModel;

/**
 * 用户管理
 * 
 */
public class UserAction extends BaseUserAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 7605538556431257679L;

	/** 日志 */
	private transient static final Log log = LogFactory.getLog(UserAction.class);

	/** 分类服务 */
	private CategoryService categoryService;
	private UnitService unitService;
	
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	private WebSiteManageService webSiteManageService;

	/** 文件选择框的'name' */
	private List<File> file;

	/** 由属性 file + Filename 固定组成 */
	private List<String> fileFileName;

	/** 由属性 file + ContentType 固定组成 */
	private List<String> fileContentType;

	/** 配置对象 */
	private Configure config;

	/** 配置服务 */
	private ConfigService cfg_svc;

	/** 配置服务的'set'方法 */
	public void setConfigService(ConfigService cfg_svc) {
		this.cfg_svc = cfg_svc;
	}
	
	/** 用户Id */
	private int userId;
	
	/** 用户登录名 */
	private String loginName;
	
	/** 密码 */
	private String password;
	
	/** 新密码 */
	private String newPassword;
		
	/** 真实姓名 */
	private String trueName;
	
	/** 呢称 */
	private String nickName;
	
	/** 角色 */
	private String role;

	/** 学科ID */
	private String subject;
	
	/** 学段ID */
	private String grade;
	
	/** 密码找回时的问题 */
	private String question;

	/** 密码找回时的答案 */
	private String answer;
	
	/** 身份证号码 */
	private String IDCard;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	public String execute(String cmd) throws Exception {
		
		// 得到配置对象
		this.config = cfg_svc.getConfigure();
		
		//上传头像可能也不需要登录，注册的时候也可以
		if ("uploadphotofile".equalsIgnoreCase(cmd)) {
			return uploadphotofile();
		} else if ("savephotofile".equalsIgnoreCase(cmd)) {
			return savephotofile();
		}
		
		// 登录验证
		if (isUserLogined() == false)
			return LOGIN;
		
		if (canVisitUser(getLoginUser()) == false)
			return ERROR;
		if(cmd == null || cmd.length() == 0) cmd="profile";
		if ("profile".equalsIgnoreCase(cmd)) {
			return profile();
		} else if ("save".equalsIgnoreCase(cmd)) {
			return save();
		} else if ("subjectgrade".equalsIgnoreCase(cmd)) {
			return subjectgrade();
		} else if ("updpwd".equalsIgnoreCase(cmd)) {
			return updatePassword();
		} else if ("xmlrpcUpdpwd".equalsIgnoreCase(cmd)) {
			return xmlrpcUpdpwd();
		} else if ("uploadheadimg".equalsIgnoreCase(cmd)) {
			return uploadHeadImg();
		} else if ("stat".equals(cmd)) {
			return stat();
		}else if ("stat_historyarticle".equals(cmd)) {
			return stat_historyarticle();
		} else if ("reset_password".equals(cmd)) {
			return reset_password();
		} else if ("question".equals(cmd)) {
			return question();
		} else if ("answer".equals(cmd)) {
			return answer();
		} else if ("del".equals(cmd)) {
			return delete();
		}

		return ERROR;
	}

	/**
	 * 显示'个人信息'的修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	private String profile() throws Exception {
		User user = regetLoginUser();
		setRequestAttribute("user", user);
		
		// 把 学科列表, 年级列表, 区县列表, 机构列表 放到模板环境中
		putMetaSubjectList();
		putGradeList();

		
		// 将'系统分类树'放到'request'中
		putSysCategoryToRequest();
		
		// 缺省头像列表
		setRequestAttribute("icon_list", getUserDefaultIconList());
		
		// 在'真实姓名'的输入框后面,是否显示'如果修改了真实姓名，则需要管理员审核通过才能登录！'的提示
		if (this.config.getBoolValue(Configure.PROFILE_UPDATE_TRUENAME_NEEDAUDIT, false) == true) {
			trueName = "true";
		} else {
			trueName = "false";
		}
		
		// 在'呢称'的输入框后面,是否显示'真实姓名和呢称必须一致！ 如果修改，则需要管理员审核通过才能登录！'的提示
		if (this.config.getBoolValue(Configure.TRUENAME_EQUALS_NICKNAME, false) == true) {
			nickName = "true";
		} else {
			nickName = "false";
		}
		
		// 在'身份证号码'的输入框后面,是否显示'如果修改了身份证号码，则需要管理员审核通过才能登录！'的提示
		if (this.config.getBoolValue(Configure.USER_REGISTER_MUST_IDCARD, false) == true) {
			IDCard = "true";
		} else {
			IDCard = "false";
		}
		
		// 在'身份'的选择框后面,是否显示'如果修改了身份，则需要管理员审核通过才能登录！'的提示
		// 该配置项已经从数据库中删除
//		if (this.config.getBoolValue(Configure.USER_REGISTER_MUST_ROLE, false) == true) {
//			role = "true";
//		} else {
//			role = "false";
//		}		
				
		return "Update_Profile";
	}
	
	/**
	 * 修改学科学段信息
	 * @return
	 * @throws Exception
	 */
	private String subjectgrade() throws Exception {
		User user = regetLoginUser();
		setRequestAttribute("user", user);
		
		// 把 学科列表, 年级列表,放到模板环境中
		putMetaSubjectList();
		putGradeList();
		
		// 是否显示'如果修改了学科，则需要管理员审核通过才能登录！'的提示
		if (this.config.getBoolValue(Configure.PROFILE_UPDATE_SUBJECT_NEEDAUDIT, false) == true) {
			subject = "true";
		} else {
			subject = "false";
		}
		
		// 是否显示'如果修改了学段，则需要管理员审核通过才能登录！'的提示
		if (this.config.getBoolValue(Configure.PROFILE_UPDATE_GRADE_NEEDAUDIT, false) == true) {
			grade = "true";
		} else {
			grade = "false";
		}
		
				
		return "Update_SubjectGrade";
	}
	

	/**
	 * 保存'个人信息'的修改
	 * 
	 * @return
	 * @throws Exception
	 */
	private String save() throws Exception {
		// 收集页面上面提交的数据
		User oldUser = this.getUserService().getUserById(this.userId);
		
		if(oldUser == null )
		{
			addActionError("不能从数据库加载用户对象。");
			return ERROR;
		}
		
		//System.out.println(oldUser.getLoginName() + ".Action.OldUser.Status:"+oldUser.getUserStatus());
		
		User user = collectUserObject(oldUser, cfg_svc);
		
		//System.out.println(user.getLoginName() + ".Action.User.Status:"+user.getUserStatus());
		
		String email= user.getEmail();
		if(email!=null && email.length()>0){
			if (this.config.getBoolValue(Configure.USER_REGISTER_EMAIL_UNIQUE, false) == true) {
				User _user=super.getUserService().getUserByEmail(email);
				if(_user!=null){
					if(_user.getUserId()!=user.getUserId()){
						addActionError("注册信息中Email已经在数据库中存在！");
						return ERROR;
					}
				}
			}
		}	
		
		/*
		String nickName= user.getNickName();
		if(nickName!=null && nickName.length()>0){
			if (this.config.getBoolValue(Configure.USER_REGISTER_NICKNAME_UNIQUE, false) == true) {
				User _user=super.getUserService().getUserByNickName(nickName);
				if(_user!=null){
					if(_user.getUserId()!=user.getUserId()){
						addActionError("注册信息中昵称已经在数据库中存在！");
						return ERROR;
					}
				}
			}
		}		
		*/
		
		// 这两个字段不从页面上面收集
		/*
		if (user.getUserId() != getLoginUser().getUserId()) {
			addActionError("试图修改他(她)人的注册信息??");
			return ERROR;
		}
		*/
		
		//从数据库加载用户对象，确保单位修改了。
		
		
		user.setUserId(oldUser.getUserId());
		user.setLoginName(oldUser.getLoginName());
		
		//修改单位
		Integer unitId = param_util.getIntParamZeroAsNull("unitId");
		if(unitId == null)
		{
			addActionError("机构不能为空。");
			return ERROR;
		}
		
		Unit userUnit = unitService.getUnitById(unitId);
		if(userUnit == null) 
		{
			addActionError("无法加载你选择的机构。");
			return ERROR;
		}
		
		boolean updateContent = false;
		if(oldUser.getUnitId() == null || oldUser.getUnitId() != userUnit.getUnitId())
		{		
			user.setUnitId(unitId);
			user.setUnitPathInfo(userUnit.getUnitPathInfo());
			updateContent = true;
		}
		
		// 进行修改
		//System.out.println("进行修改.....");
		//System.out.println(user.getLoginName() + ".Action.User.Status:"+user.getUserStatus());
		
		try{
			getUserService().updateUser(user);
			//System.out.println("修改完毕");
		}catch(RuntimeException ex){
			addActionError(ex.getMessage());
			return ERROR;
		}
		// 返回修改成功的提示信息
		addActionMessage(this.getText("groups.profile.update.success"));
		
		// 更新当前登录用户信息
		boolean login=false;
		if(getLoginUser().getUserId() == this.userId){
			login = true;
			//只有更新自己的信息的时候，才清除自己的缓存；如果是当前管理员修改用户信息，则不需要清除当前管理员的缓存啊
			super.clearLoginSession();
		}
		
		//更新文章、资源等的unit信息。
		if(updateContent)
		{
			this.webSiteManageService.updateUnitInfo(user.getUserId(), unitId);
		}
		
		//清空缓存
		FileCache fc = new FileCache();
		fc.deleteUserAllCache(user.getLoginName());
		fc = null;
		String __referer = param_util.safeGetStringParam("__referer");
		if(login){
			this.addActionLink("个人信息已修改，请重新登录",CommonUtil.getSiteUrl(request) + "login.jsp");
		}else{
			if(__referer.length() ==0)
				this.addActionLink("返回",CommonUtil.getSiteUrl(request) + "manage/user.action?cmd=profile");
			else
				this.addActionLink("返回",__referer);
		}
		return SUCCESS;
	}

	/**
	 * 显示'修改密码'页面
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	private String updatePassword() throws Exception {
		User user = regetLoginUser();
		setRequestAttribute("user", user);
		return "Update_Password";
	}
	
	/**
	 * 原来的CAS用户系统更新密码
	 * @return
	 */
	private String xmlrpcUpdpwd_CAS(){
		//更新用户服务器的密码
		Client client = new Client();
		String userMgrUrl = request.getServletContext().getFilterRegistration("CASValidationFilter").getInitParameter("casServerUrlPrefix");
		if(userMgrUrl==null || userMgrUrl.length()==0){
			userMgrUrl=CasConst.getInstance().getCasServerUrlPrefix();
		}
		//System.out.println("（根据用户名修改用户密码）统一用户地址：" + userMgrUrl);
		client.setUserMgrUrl(userMgrUrl);
		String result = client.updatePasswordByUsername(loginName, password, newPassword);
		if ("ok".equals(result)) {
			return SUCCESS;
		} else if ("wrong".equals(result)) {
			//System.out.println("（根据用户名修改用户密码）当前密码错误！");
			return NONE;
		}
		return NONE;
		
	}
	
	/**
	 * 新版本用户系统更新密码
	 * @return
	 */
	private String xmlrpcUpdpwd_SSO(){
		//String clientCode = request.getServletContext().getInitParameter("clientCode");
        //String SSOServerURL = request.getServletContext().getInitParameter("SSOServerURL");
        //if(SSOServerURL.endsWith("/")){
        //	SSOServerURL = SSOServerURL.substring(0, SSOServerURL.length()-1);
        //}
        String userServiceUrl = SSOServerURL2 + request.getServletContext().getFilterRegistration("ssoUserFilter").getInitParameter("userServiceUrl");

        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setConnectTimeout(5000L); // 毫秒
        String result = null;
        
        try {
            UserService userService = (UserService) factory.create(UserService.class, userServiceUrl);
            String md5NewPassword = com.chinaedustar.util.MD5.crypt(newPassword);
            result = userService.updatePwdRemote(getLoginUser().getUserGuid(), md5NewPassword, com.chinaedustar.util.MD5.crypt(password));
            ServiceObject so = JSON.parseObject(result,ServiceObject.class);
            if(so.success){
            	//更新成功
            	
            	//更新教研中的密码 
            	cn.edustar.jitar.pojos.User user = getLoginUser();
            	user.setPassword(md5NewPassword);
            	this.getUserService().updateUser(user);  //里面已经有红杉树 
            	
            	return SUCCESS;
            }else{
            	//得到从服务器返回的错误
            	ResultCode rc = JSON.parseObject(so.returnobject, ResultCode.class);
            	addActionError(rc.failureName);
    			return NONE;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            //错误
            addActionError(ex.getMessage());
            return NONE;
        }			
	}
	/**
	 * 根据用户名修改用户密码
	 * 
	 * @return
	 * @throws Exception
	 */
	private String xmlrpcUpdpwd() {
		if(null != request.getServletContext().getFilterRegistration("CASValidationFilter")){
			return xmlrpcUpdpwd_CAS();
		}else if(null != request.getServletContext().getFilterRegistration("ssoUserFilter")){
			//更新用户服务器的密码
			return xmlrpcUpdpwd_SSO();
		}else{
			return NONE;
		}
	}
	
	/**
	 * 重置选择用户的密码
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public String reset_password() throws Exception {	
		List<Integer> ids = param_util.getIdList("myUserId");
		String password = param_util.getStringParam("reset_password");
		
		java.util.Collection<String> errorMessages = new ArrayList<String>();
		
		if (ids.size() > 0) {			
			for (Integer userId : ids) {
				try{	
					resetPwd(userId, password);
				}catch(RuntimeException ex){
					//this.addActionError(ex.getMessage());
					errorMessages.add(ex.getMessage());
				}			
			}
			if(errorMessages.size() > 0 ){
				this.setActionErrors(errorMessages);
				return ERROR;
			}
			
		} else { // it can run here, by yxx?
			return ERROR;
		}
		// 返回信息
		PrintWriter out = response.getWriter();
		response.reset();
		response.resetBuffer();
		out.print("OK");
		return NONE;
	}

	/**
	 * 保存剪切的头像
	 * 页面上显示的图片的大小并非是原始图片的真实的大小,而传递的截取的像素位置是按照页面上的位置获得,需要把原始图片转换为页面中显示大小,再进行剪切
	 * @return
	 * @throws IOException 
	 */
	private String savephotofile() throws IOException{
        Integer w = param_util.safeGetIntParam("w", 0);
        Integer h = param_util.safeGetIntParam("h",0);
        Integer x = param_util.safeGetIntParam("x1",0);
        Integer y = param_util.safeGetIntParam("y1",0);
        Integer width = param_util.safeGetIntParam("width",0);		//页面中显示的这个图片的宽度
        Integer height = param_util.safeGetIntParam("height",0);	//页面中显示的这个图片的高度
        Integer ww = param_util.safeGetIntParam("ww",0);
        Integer hh = param_util.safeGetIntParam("hh",0);
        
        String photofile = param_util.getStringParam("photofile");  //原始图像文件
        
        //WEB应用程序根路径  
        String webAppPath = getServletContext().getRealPath("/");
        
        String root = getServletContext().getRealPath("/images/headImg/");
        
        if(photofile.lastIndexOf("/") >= 0){
        	photofile = photofile.substring(photofile.lastIndexOf("/")+1);
        }
        /*
        System.out.println(" x = " + x);
        System.out.println(" y = " + y);
        System.out.println(" w = " + w);
        System.out.println(" h = " + h);
        System.out.println(" photofile = " + root+ File.separator +photofile);
        System.out.println(" mini_photofile = " + root+ File.separator +"mini_"+photofile);
        */
        CropImage.cropPhoto(root+File.separator+photofile, root+File.separator+"mini_"+photofile,width,height, x, y, w, h);
        
        String createImgPath = root + File.separator + "mini_"+photofile;
        
        //System.out.println(" createImgPath = " + createImgPath);
        
        String strUserIcon ="";
        File f = new File(createImgPath);
        
        //System.out.println(" createImgPath = " + createImgPath + "是否存在？");
        //System.out.println(f.exists());
        
        if(f.exists()){
        	
        	strUserIcon = "images/headImg/" + "mini_"+photofile;
        	
        	//进行缩放
        	String rd = param_util.safeGetStringParam("rd","");
        	if(rd.length() > 0){
        		String[] wh = rd.split("\\*");
        		if(wh.length == 2){
        			String s_width = wh[0];
        			String s_height = wh[1];
        			int iWidth = 0;
        			int iHeight = 0;
        			if(s_width.length()>0){
        				iWidth = Integer.parseInt(s_width);
        			}
        			if(s_height.length()>0){
        				iHeight = Integer.parseInt(s_height);
        			}
        			if(iWidth>0 && iHeight>0){
        				//缩放
        				String result = root + File.separator + "xmini_" + photofile;
        				CropImage.scale(createImgPath, result, iWidth, iHeight);
        		        File ff = new File(result);                                
        		        if(ff.exists()){
        		        	strUserIcon = "images/headImg/" + "xmini_"+photofile;
        		        }        				
        			}
        		}
        	}
        	
     		//最后保存到数据库的用户头像字符串
            //User user = regetLoginUser();
     		//user.setUserIcon(strUserIcon);
     		//getUserService().updateUser(user);
    		response.setContentType("text/html");
     		PrintWriter out = response.getWriter();
     		out.append(PageContent.PAGE_UTF8);
     		out.println("<script type='text/javascript'>window.parent.CropImageUploadSuccess('" + strUserIcon + "');</script>");
     		out.flush();
     		out.close();
        } 
        
		return NONE;
		
	}
	/**
	 * 上传头像文件
	 * @return
	 * @throws IOException 
	 */
	private String uploadphotofile() throws IOException{
		PrintWriter out = response.getWriter();
		
		// 判断是否上传了文件
		if (file.size() == 0) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>window.alert('     没有上传任何文件！');</script>");
			out.flush();
			out.close();
			return NONE;
		}		
		//User user = regetLoginUser();
		//String loginName = user.getLoginName();
		
		// 得到上传的文件
		File srcFile = file.get(0);
		
		// 计算头像存放地址
		String root = getServletContext().getRealPath("/images/headImg/");

		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fd = sdf.format(c.getTime());

		// 最后生成的文件名的约定
		String fname = this.getFileFileName().get(0);
		String generateFile = "head" + "_" + fd + "_";
		int hzPos = fname.lastIndexOf(".");
		if(hzPos > 0){
			generateFile = generateFile + fname.substring(hzPos);
		}else{
			generateFile = generateFile + this.getFileFileName().get(0);
		}
		File destFile = new File(root, generateFile);
		if (destFile.getParentFile().exists() == false)
			destFile.getParentFile().mkdirs();

		// 移动到目标位置
		if(CommonUtil.renameTo(srcFile, destFile) == false){	
			throw new RuntimeException("无法复制/移动上传文件，请确定文件夹 /images/headImg/存在并且具有可写权限！");
		}
		// 最后保存到数据库的用户头像字符串
		//String strUserIcon = SiteUrlModel.getSiteUrl()+"images/headImg/" + generateFile;
		String strUserIcon = request.getContextPath()+"/images/headImg/" + generateFile;
		//out.append(PageContent.PAGE_UTF8);
		out.println("<script style=\"text/javascript\">window.parent.setUserIcon(\"" + strUserIcon + "\",\"" + generateFile + "\");</script>");
		return NONE;
		
	}
	/**
	 * 上传头像
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	private String uploadHeadImg() throws java.lang.Exception {
		PrintWriter out = response.getWriter();
		
		// 判断是否上传了文件
		if (file.size() == 0) {
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>window.alert('     没有上传任何文件！');</script>");
			out.flush();
			out.close();
			return NONE;
		}

		User user = regetLoginUser();
		String loginName = user.getLoginName();
		
		// 得到上传的文件
		File srcFile = file.get(0);
		
		// 计算头像存放地址
		String root = getServletContext().getRealPath("/images/headImg/");

		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fd = sdf.format(c.getTime());

		// 最后生成的文件名的约定
		String generateFile = loginName + "_" + fd + "_" + this.getFileFileName().get(0);

		File destFile = new File(root, generateFile);
		if (destFile.getParentFile().exists() == false)
			destFile.getParentFile().mkdirs();

		// 移动到目标位置
		if(CommonUtil.renameTo(srcFile, destFile) == false){	
			throw new RuntimeException("无法复制/移动上传文件，请确定文件夹 /images/headImg/存在并且具有可写权限！");
		}

		log.info("用户：" + user.toDisplayString() + "，更换了自己的头像。其头像的详细地址是：" + destFile.getPath());

		// 最后保存到数据库的用户头像字符串
		String strUserIcon = "images/headImg/" + generateFile;
		user.setUserIcon(strUserIcon);
		getUserService().updateUser(user);

		// 提示信息
		this.addFieldError("file", this.getText("groups.profile.headimg.upload.success"));

		out.append(PageContent.PAGE_UTF8);
		out.println("<script>window.alert('     上传成功！');window.parent.location.href='" + "user.action?cmd=profile" + "&tmp=" + UUID.randomUUID().toString() + "';</script>");
		return NONE;
	}

	/**
	 * 重新统计我的各项统计数据
	 * 
	 * @return
	 */
	private String stat() {
		@SuppressWarnings("unused")
		Object result = getUserService().statForUser(getLoginUser()._getUserObject());
		addActionMessage("统计成功完成！");

		// 强迫重新加载'user'信息
		// clearLoginSession();

		addActionLink("", "");
		FileCache fc = new FileCache();
		fc.deleteUserAllCache(getLoginUser().getLoginName());
		fc = null;
		return SUCCESS;
	}
	
	
	private String stat_historyarticle() {
		getUserService().statHistoryArticleForUser(getLoginUser()._getUserObject());
		addActionMessage("统计成功完成！");
		addActionLink("", "");		
		FileCache fc = new FileCache();
		fc.deleteUserAllCache(getLoginUser().getLoginName());
		fc = null;
		return SUCCESS;
	}
	
	/**
	 * 显示'找回密码的问题和答案'的页面
	 *
	 * @return
	 * @throws Exception
	 */
	private String question() throws Exception {
		BaseUser baseUser = getUserService().getUserByUsername(getLoginUser().getLoginName());
		if (null != baseUser) {
			loginName = baseUser.getUsername();
			question = baseUser.getQuestion();
		}else{
			loginName = getLoginUser().getLoginName();
		}
		return "Update_Answer";
	}
	
	/**
	 * 根据用户名重置问题和答案
	 *
	 * @return
	 * @throws Exception
	 */
	private String answer() throws Exception {
		if(null !=request.getServletContext().getFilterRegistration("CASValidationFilter")){
			return answer_CAS();
		}else if(null != request.getServletContext().getFilterRegistration("ssoUserFilter")){
			//更新用户服务器的问题答案
			return answer_SSO();
		}else{
			return NONE;
		}
	}
	/**
	 * 新版本用户系统的更改问题答案
	 * @return
	 * @throws IOException
	 */
	private String answer_SSO() throws IOException{
		PrintWriter out = response.getWriter();
        //String SSOServerURL = request.getServletContext().getInitParameter("SSOServerURL");
        //if(SSOServerURL.endsWith("/")){
        //	SSOServerURL = SSOServerURL.substring(0, SSOServerURL.length()-1);
        //}
        String userServiceUrl = SSOServerURL2 + request.getServletContext().getFilterRegistration("ssoUserFilter").getInitParameter("userServiceUrl");

        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setConnectTimeout(5000L); // 毫秒
        String result = null;
        
        try {
            UserService userService = (UserService) factory.create(UserService.class, userServiceUrl);
            result = userService.updateQaRemote(getLoginUser().getUserGuid(), question, answer);
            ServiceObject so = JSON.parseObject(result,ServiceObject.class);
            if(so.success){
    			out.append(PageContent.PAGE_UTF8);
    			out.println("<script>window.alert('     修改成功！\\n\\n请牢记您的密码问题和答案！');window.history.go(-1);</script>");
    			out.flush();
    			out.close();
    			return question();
            }else{
            	//得到从服务器返回的错误
            	ResultCode rc = JSON.parseObject(so.returnobject, ResultCode.class);
            	addActionError(rc.failureName);
    			out.append(PageContent.PAGE_UTF8);
    			out.println("<script>window.alert('     修改失败！\\n\\n"+rc.failureName+"！');</script>");
    			out.flush();
    			out.close();
    			return NONE;
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            //错误
            addActionError(ex.getMessage());
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>window.alert('     修改失败！\\n\\n"+ex.getMessage()+"！');</script>");
			out.flush();
			out.close();
			return NONE;
        }			
	}	
	/**
	 * CAS用户系统的更改问题答案
	 * @return
	 * @throws Exception 
	 */
	private String answer_CAS() throws Exception{
		PrintWriter out = response.getWriter();
		Client client = new Client();
		String userMgrUrl = request.getServletContext().getFilterRegistration("CASValidationFilter").getInitParameter("casServerUrlPrefix");
		if(userMgrUrl==null || userMgrUrl.length()==0){
			userMgrUrl=CasConst.getInstance().getCasServerUrlPrefix();
		}		
		//System.out.println("（根据用户名重置问题和答案）统一用户地址：" + userMgrUrl);
		client.setUserMgrUrl(userMgrUrl);
		String result = client.resetQuestionAndAnswerByUsername(getLoginUser().getLoginName(), question, answer);
		if ("ok".equals(result)) {
			//System.out.println("（根据用户名重置问题和答案）操作成功！");
			out.append(PageContent.PAGE_UTF8);
			out.println("<script>window.alert('     修改成功！\\n\\n请牢记您的密码问题和答案！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return question();
		} else {
			//System.out.println("（根据用户名重置问题和答案）出现错误！");
			return NONE;
		}
		
	}
	/**
	 * 彻底删除一个用户，如果该用户已经参与了系统业务，则不能删除
	 * 
	 * @return
	 * @throws Exception
	 * @remark 如果该用户已经参与了系统业务，则页面上不会出现错误信息，但是系统的控制台(包括日志)，会抛出异常信息
	 */
	private String delete() throws Exception {
		PrintWriter out = response.getWriter();
		out.append(PageContent.PAGE_UTF8);
		User user = getUserService().getUserById(userId);
		try {
			//删除业务			
			getUserService().deleteUser(userId);
			log.info("成功删除用户：" + user.getLoginName() + "[" + user.getTrueName()+ "]");
			out.println("<script>alert('删除成功！');window.location.href='" + PageContent.getAppPath() + "/manage/admin_user.py?cmd=list&type=deleted';</script>");
			out.flush();
			out.close();
			
			// 删除统一用户中的用户
			Client client = new Client();
			String userMgrUrl = request.getServletContext().getFilterRegistration("CASValidationFilter").getInitParameter("casServerUrlPrefix");
			if(userMgrUrl==null || userMgrUrl.length()==0){
				userMgrUrl=CasConst.getInstance().getCasServerUrlPrefix();
			}			
			//System.out.println("（根据用户名删除用户）统一用户地址：" + userMgrUrl);
			client.setUserMgrUrl(userMgrUrl);
			String result = client.deleteUser(user.getLoginName());
			/*if ("ok".equals(result)) {
				System.out.println("（根据用户名删除用户）操作成功！" + user.getLoginName());
			} else if ("wrong".equals(result)) {
				System.out.println("（根据用户名删除用户）出现错误！" + user.getLoginName());
			}*/
		} catch (Exception e) {
			log.info("不能删除用户：" + user.getLoginName() + "[" + user.getTrueName()+ "]");
			out.println("<script>alert('对不起，无法删除该用户！');window.history.go(-1);</script>");
			out.flush();
			out.close();
		}
		return NONE;
	}
	
	/**
	 * 将'系统分类树：syscate_tree'放到'request'中
	 */
	private void putSysCategoryToRequest() {
		Object syscate_tree = categoryService.getCategoryTree(CategoryService.BLOG_CATEGORY_TYPE);
		setRequestAttribute("syscate_tree", syscate_tree);
	}

	/**
	 * 重新加载当前登录用户的个人用户对象, 用于在编辑等操作中
	 * 
	 * @return
	 */
	private User regetLoginUser() {
		int userId = getLoginUser().getUserId();
		return getUserService().getUserById(userId, false);
	}

	/** 系统分类的set方法 */
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setWebSiteManageService(WebSiteManageService webSiteManageService) {
		this.webSiteManageService = webSiteManageService;
	}

	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public List<String> getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	public List<String> getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}


	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
		
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
		
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getIDCard() {
		return IDCard;
	}

	public void setIDCard(String card) {
		IDCard = card;
	}
	
}
