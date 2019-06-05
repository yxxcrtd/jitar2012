package cn.edustar.jitar.ui;

import java.io.IOException;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.action.AbstractServletAction;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.UPunishScore;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ParamUtil;
import cn.edustar.jitar.service.UPunishScoreService;
/**
 * 转跳到其它页面的中转处理.
 *
 * URL: go.action?userId=xxx	转到该用户的首页.
 * URL: go.action?groupId=xxx	转到该群组的首页.
 * URL: go.action?articleId=xxx 转到该文章的页面.
 */
public class GoAction extends AbstractServletAction {
	/**	serialVersionUID */
	private static final long serialVersionUID = 2068463665705872237L;

	/** 用户服务 */
	private UserService user_svc;
	private UPunishScoreService upunishscore_svc;
	/**
	 * 构造.
	 */
	public GoAction() {
		JitarContext jtar_ctxt = JitarContext.getCurrentJitarContext();
		if (jtar_ctxt != null) {
			this.user_svc = jtar_ctxt.getUserService();
			this.upunishscore_svc= jtar_ctxt.getUPunishScoreService();
		}
	}
	
	/** 注入用户服务 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}
	
	public String execute() throws IOException {
		ParamUtil param_util = new ParamUtil(request.getParameterMap());
		if (param_util.existParam("userId")) {
			int userId = param_util.getIntParam("userId");
			User user = user_svc.getUserById(userId);
			if (user == null) {
				addActionError("指定用户不存在");
				return ERROR;
			}
			String userUrlPattern = request.getSession().getServletContext().getInitParameter("userUrlPattern");
			if(userUrlPattern == null)
			{
				response.setStatus(301);
				response.setHeader("Location", user.getLoginName());
				response.setHeader("Connection", "close");
				return NONE;
			}
			else
			{
				userUrlPattern = userUrlPattern.replaceAll("\\{loginName\\}", user.getLoginName());
				response.setStatus(301);
				response.setHeader("Location", userUrlPattern);
				response.setHeader("Connection", "close");
				return NONE;
			}
			
		} else if (param_util.existParam("loginName")) {
			String userUrlPattern = request.getSession().getServletContext().getInitParameter("userUrlPattern");
			String loginName = param_util.safeGetStringParam("loginName");
			// go.action?userId=$userid - 重定向到用户首页.
			User user = user_svc.getUserByLoginName(loginName, false);
			if (user == null) {
				addActionError("指定用户不存在");
				return ERROR;
			}
			if(userUrlPattern == null || userUrlPattern.length() < 1)
			{
				response.sendRedirect(user.getLoginName());	
				return NONE;
			}
			else
			{
				//log.info("userUrlPattern = " + userUrlPattern);
				//log.info("loginName = " + loginName);
				userUrlPattern = userUrlPattern.replaceAll("\\{loginName\\}", loginName);
				response.sendRedirect(userUrlPattern);
				return NONE;
			}
		}
		else if (param_util.existParam("profile")) {
			String userUrlPattern = request.getSession().getServletContext().getInitParameter("userUrlPattern");
			String loginName = param_util.safeGetStringParam("profile");
			// go.action?userId=$userid - 重定向到用户首页.
			User user = user_svc.getUserByLoginName(loginName, false);
			if (user == null) {
				addActionError("指定用户不存在");
				return ERROR;
			}
			if(userUrlPattern == null || userUrlPattern.length() < 1)
			{
				response.sendRedirect(user.getLoginName() + "/profile");	
				return NONE;
			}
			else
			{
				//log.info("userUrlPattern = " + userUrlPattern);
				//log.info("loginName = " + loginName);
				userUrlPattern = userUrlPattern.replaceAll("\\{loginName\\}", loginName) + "/profile";
				response.sendRedirect(userUrlPattern);
				return NONE;
			}
		}
		else if (param_util.existParam("courseId")) {
			int courseId = param_util.getIntParam("courseId");			
			response.sendRedirect("p/" + courseId + "/0/");
			return NONE;
		} 
		
		else if (param_util.existParam("groupId")) {
			// go.action?groupId=$groupid - 重定向到群组首页.
			int groupId = param_util.getIntParam("groupId");
			Group group = JitarContext.getCurrentJitarContext().getGroupService().getGroup(groupId);
			if (group == null) {
				addActionError("指定群组不存在");
				return ERROR;
			}
			
			response.sendRedirect("g/" + group.getGroupName());
			return NONE;
		} else if (param_util.existParam("articleId")) {
			// go.action?articleId=$articleid - 重定向到该文章的页面.
			int articleId = param_util.getIntParam("articleId");
			Article article = JitarContext.getCurrentJitarContext().getArticleService().getArticle(articleId);
			if (article == null) {
				addActionError("指定文章不存在");
				return ERROR;
			}
			
			// 得到文章的拥有用户.
			User user = user_svc.getUserById(article.getUserId());
			if(user == null)
			{
				addActionError("无法加载文章的用户信息");
				return ERROR;
			}
			System.out.print("articleId=================="+articleId);
			//检查本文是否加分
			UPunishScore punshScore= this.upunishscore_svc.getUPunishScore(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId() , articleId);
			if(punshScore!=null){
				if(punshScore.getScore()<0){
					request.setAttribute("scoreCreateUserId", punshScore.getCreateUserId());
					request.setAttribute("scoreCreateUserName", punshScore.getCreateUserName());
					request.setAttribute("score", -1*punshScore.getScore());
					request.setAttribute("scoreReason", punshScore.getReason());
					request.setAttribute("scoreDate", punshScore.getPunishDate());
					request.setAttribute("scoreObjId", punshScore.getObjId());
					request.setAttribute("scoreObjTitle", punshScore.getObjTitle());
				}
			}else{
				
			}
			String userUrlPattern = request.getSession().getServletContext().getInitParameter("userUrlPattern");
			if(userUrlPattern != null)
			{
				userUrlPattern = userUrlPattern.replaceAll("\\{loginName\\}", user.getLoginName());
				response.setStatus(301);
				response.setHeader("Location", userUrlPattern + "article/" + article.getArticleId() + ".html");
				response.setHeader("Connection", "close");
				//response.sendRedirect(userUrlPattern + "article/" + article.getArticleId() + ".html");				
			}
			else
			{	
				response.setStatus(301);
				response.setHeader("Location", user.getLoginName() + "/article/" + article.getArticleId() + ".html");
				response.setHeader("Connection", "close");				
				//response.sendRedirect(user.getLoginName() + "/article/" + article.getArticleId() + ".html");
			}
			return NONE;
		} else if (param_util.existParam("userIconById")) {
			// go.action?userIconById=$userId - 得到该用户的头像.
			int userId = param_util.getIntParam("userIconById");
			User user = user_svc.getUserById(userId);
			return iconForUser(user);
		} else if (param_util.existParam("userIconByName")) {
			// go.action?userIconByName=$loginName - 得到该用户的头像.
			String loginName = param_util.safeGetStringParam("userIconByName");
			User user = user_svc.getUserByLoginName(loginName);
			return iconForUser(user);
		}
		
		
		addActionError("无法识别指定参数");
		return ERROR;
	}
	
	private String iconForUser(User user) throws IOException {
		if (user == null) {
			addActionError("指定用户不存在");
			return ERROR;
		}
		
		String userIcon = user.getUserIcon();
		if (CommonUtil.isEmptyString(userIcon)) userIcon = User.DEFAULT_USER_ICON;
		String url = CommonUtil.calcAbsUrl(userIcon);
		response.sendRedirect(url);
		return NONE;
	}

	public void setUpunishscore_svc(UPunishScoreService upunishscore_svc) {
		this.upunishscore_svc = upunishscore_svc;
	}
}
