package cn.edustar.jitar;

import java.util.Properties;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

import cn.edustar.jitar.manage.OnlineManage;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.ActionService;
import cn.edustar.jitar.service.ChannelPageService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.BbsService;
import cn.edustar.jitar.service.CacheProvider;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.FriendService;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.LeavewordService;
import cn.edustar.jitar.service.OnlineManager;
import cn.edustar.jitar.service.PageService;
import cn.edustar.jitar.service.PhotoService;
import cn.edustar.jitar.service.PlacardService;
import cn.edustar.jitar.service.PrivilegeManager;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.ScriptManager;
import cn.edustar.jitar.service.StatService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.TemplateProcessor;
import cn.edustar.jitar.service.UnitTypeService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.service.VideoService;
import cn.edustar.jitar.service.ViewCountService;
import cn.edustar.jitar.service.GroupKTUserService;
import cn.edustar.jitar.service.UPunishScoreService;
/**
 * 表示教研系统环境对象接口
 * 
 *
 */
public abstract class JitarContext {
	
	/** 存放在 Web ServletContext 中属性的键 */
	public static final String JITAR_CONTEXT_KEY_NAME = "cn.edustar.jitar.JitarContext";

	/**
	 * 获得当前线程的 ctxt
	 * 
	 * @return
	 */
	public static final JitarContext getCurrentJitarContext() {
		return JitarRequestContext.getRequestContext().getJitarContext();
	}

	/**
	 * 设置当前线程的 ctxt
	 * 
	 * @param ctxt
	 * @return
	 */
	public static final void setCurrentJitarContext(JitarContext ctxt) {
		JitarRequestContext.getRequestContext().setJitarContext(ctxt);
	}

	/**
	 * 得到系统版本
	 * 
	 * @return
	 */
	public abstract String getVersion();

	/**
	 * 得到属性集
	 * 
	 * @return
	 */
	public abstract Properties getProperties();

	/**
	 * 得到 Web 应用环境对象
	 * 
	 * @return
	 */
	public abstract ServletContext getServletContext();

	/**
	 * 得到配置的 Spring ApplicationContext
	 * 
	 * @return
	 */
	public abstract ApplicationContext getSpringContext();

	/**
	 * 得到配置的数据库连接源
	 * 
	 * @return
	 */
	public abstract DataSource getDataSource();

	/**
	 * 得到配置的 hibernate SessionFactory
	 * 
	 * @return
	 */
	public abstract SessionFactory getSessionFactory();

	/**
	 * 得到配置的 spring HibernateTemplate 对象
	 * 
	 * @return
	 */
	//public abstract HibernateTemplate getHibernateTemplate();

	/**
	 * 获得指定名字的属性
	 * 
	 * @param key
	 * @return
	 */
	public abstract Object getAttribute(String key);

	/**
	 * 设置指定名字的属性, 设置是多线程安全的操作
	 * 
	 * @param key
	 * @param value
	 */
	public abstract void setAttribute(String key, Object value);

	/**
	 * 得到配置服务
	 * 
	 * @return
	 */
	public abstract ConfigService getConfigService();

	/**
	 * 得到用户服务
	 * 
	 * @return
	 */
	public abstract UserService getUserService();

	/**
	 * 得到文章服务对象
	 * 
	 * @return
	 */
	public abstract ArticleService getArticleService();
	
	/**
	 * 得到视频服务对象
	 * @return
	 */
	public abstract VideoService getVideoService();

	/**
	 * 得到点击率服务对象
	 * 
	 * @return
	 */
	public abstract ViewCountService getViewCountService();

	/**
	 * 得到页面服务
	 * 
	 * @return
	 */
	public abstract PageService getPageService();

	/**
	 * 得到模板合成器
	 * 
	 * @return
	 */
	public abstract TemplateProcessor getTemplateProcessor();

	/**
	 * 得到标签服务对象
	 * 
	 * @return
	 */
	public abstract TagService getTagService();

	/**
	 * 得到群组服务对象
	 * 
	 * @return
	 */
	public abstract GroupService getGroupService();

	/**
	 * 得到在线服务对象
	 * 
	 * @return
	 */
	public abstract OnlineManager getOnlineManager();

	/**
	 * 得到学科服务对象
	 * 
	 * @return
	 */
	public abstract SubjectService getSubjectService();

	/**
	 * 得到资源服务对象
	 * 
	 * @return
	 */
	public abstract ResourceService getResourceService();

	/**
	 * 得到相册服务对象
	 * 
	 * @return
	 */
	public abstract PhotoService getPhotoService();

	/**
	 * 得到统计服务
	 * 
	 * @return
	 */
	public abstract StatService getStatService();

	/**
	 * 得到公告服务
	 * 
	 * @return
	 */
	public abstract PlacardService getPlacardService();

	/**
	 * 得到群组论坛服务
	 * 
	 * @return
	 */
	public abstract BbsService getBbsService();

	/**
	 * 得到分类服务对象
	 * 
	 * @return
	 */
	public abstract CategoryService getCategoryService();

	/**
	 * 得到评论服务对象
	 * 
	 * @return
	 */
	public abstract CommentService getCommentService();

	/**
	 * 活动服务
	 * 
	 * @return
	 */
	public abstract ActionService getActionService();

	/**
	 * 好友服务
	 * 
	 * @return
	 */
	public abstract FriendService getFriendService();

	/**
	 * 得到脚本服务
	 * 
	 * @return
	 */
	public abstract ScriptManager getScriptManager();

	/**
	 * 得到留言服务
	 * 
	 * @return
	 */
	public abstract LeavewordService getLeavewordService();

	/**
	 * 得到区域服务
	 * 
	 * @return
	 */
	public abstract UnitService getUnitService();
	
	public abstract GroupKTUserService getGroupKTUserService();
	/**
	 * 得到权限管理器
	 * 
	 * @return
	 */
	public abstract PrivilegeManager getPrivilegeManager();

	/**
	 * 得到缓存管理器
	 * 
	 * @return
	 */
	public abstract CacheProvider getCacheProvider();
	
	public abstract ChannelPageService getChannelPageService();
	
	/**
	 * 得到 加分罚分
	 * @return
	 */
	public abstract UPunishScoreService getUPunishScoreService();
	
	
	public abstract AccessControlService getAccessControlService();
	
	/**
	 * 得到单位分类服务
	 * @return
	 */
	public abstract UnitTypeService getUnitTypeService();

	
	/**
	 * 得到用户在线统计的管理
	 * 
	 * @return
	 */
	public abstract OnlineManage getOnlineManage();
	
}
