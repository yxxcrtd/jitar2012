package cn.edustar.jitar.query.sitefactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.dao.hibernate.BaseDaoHibernate;
import cn.edustar.jitar.data.Command;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.model.CategoryModel;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.model.SiteUrlModel;
import cn.edustar.jitar.model.UserMgrClientModel;
import cn.edustar.jitar.module.ModuleIcon;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.ArticleUser;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.HtmlTimer;
import cn.edustar.jitar.pojos.LeaveWord;
import cn.edustar.jitar.pojos.Page;
import cn.edustar.jitar.pojos.PhotoStaple;
import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.pojos.Question;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Vote;
import cn.edustar.jitar.pojos.VoteQuestion;
import cn.edustar.jitar.pojos.VoteQuestionAnswer;
import cn.edustar.jitar.pojos.Widget;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.FriendService;
import cn.edustar.jitar.service.LeavewordQueryParam;
import cn.edustar.jitar.service.LeavewordService;
import cn.edustar.jitar.service.PageService;
import cn.edustar.jitar.service.PhotoStapleService;
import cn.edustar.jitar.service.PlacardQueryParam;
import cn.edustar.jitar.service.PlacardService;
import cn.edustar.jitar.service.PluginService;
import cn.edustar.jitar.service.QuestionAnswerService;
import cn.edustar.jitar.service.TemplateProcessor;
import cn.edustar.jitar.service.VoteService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.Lunar;
import cn.edustar.jitar.service.PageKey;

/**
 * 此类已过时、已废弃
 * @author admin
 *
 */

@Deprecated
@SuppressWarnings({"rawtypes","unchecked"})
public class UserHtmlServiceImpl extends BaseDaoHibernate implements UserHtmlService {

	private JitarContext jitarContext;
	private ConfigService configService;
	private ArticleService articleService;
	private PageService pageService;
	private PlacardService placardService;
	private PluginService pluginService;
	private QuestionAnswerService questionAnswerService;
	private LeavewordService leavewordService;
	private FriendService friendService;
	private CommentService commentService;
	private VoteService voteService;
	private CategoryService categoryService;
	private PhotoStapleService photoStapleService;
	private TemplateProcessor templateProcessor;
	
	private static Logger log = LoggerFactory.getLogger(UserHtmlServiceImpl.class);

	// 各个 ftl 可能使用得到的共享变量
	private String siteUrl = null, userMgrUrl = null, userMgrClientUrl = null,userSiteUrl = null;
	private Page page = null;
	private Integer layoutId = 1;
	private User user = null;
	private StringBuilder sb = null;

	// 生成首页
	public void GenUserIndex(User u) {
		sb = new StringBuilder();
		// 框架处都是一样的。
		String header = this.GenUserHeader(u);
		// 得到文件的存放位置
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		File file = null;
		OutputStreamWriter fw = null;
		file = new File(user_dir + "index.html");

		// 首页默认的布局为3列
		layoutId = page.getLayoutId();
		if (layoutId == null || layoutId == 0) {
			layoutId = 1;
		}
		// 针对不同的布局，产生不同的页面
		sb.append(this.getLayoutRender());
		String footer = this.GenUserHtmlFoot();

		try {
			fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			fw.write(header);
			fw.write(sb.toString());
			fw.write(footer);
			fw.flush();
			fw.close();
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
			return;
		}		
		sb = null;

	}

	private Page getUserIndexPage(User user) {
		PageKey index_pk = new PageKey(ObjectType.OBJECT_TYPE_USER, user.getUserId(), "index");
		return pageService.getPageByKey(index_pk);
	}

	private Page createUserIndexPage(User user) {
		PageKey src_pk = PageKey.SYSTEM_USER_INDEX;
		PageKey dest_pk = new PageKey(ObjectType.OBJECT_TYPE_USER, user.getUserId(), "index");
		String title = user.getBlogName();
		if (title == null || title.equals("")) {
			title = user.getNickName() + " 的工作室";
		}
		this.pageService.duplicatePage(src_pk, dest_pk, title);
		return this.pageService.getPageByKey(dest_pk);
	}

	private String getLayoutRender() {
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		/*for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			//System.out.println("Widget = " + w.getColumnIndex());
		}*/

		StringBuilder ss = new StringBuilder();
		ss.append("<div id='wraper'>");
		switch (layoutId) {		
		case 2:
			ss.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
			ss.append("<tr>");
			ss.append("<td id='column_1' class='col'>");
			for (int i = 0; i < wp.size(); i++) {
				Widget w = (Widget) wp.get(i);
				if (w.getColumnIndex() != 2) {
					ss.append(this.getWidgetWindow(w));
				}
			}
			ss.append("</td>");
			ss.append("<td id='column_2' class='col'>");
			for (int i = 0; i < wp.size(); i++) {
				Widget w = (Widget) wp.get(i);
				if (w.getColumnIndex() == 2) {
					ss.append(this.getWidgetWindow(w));
				}
			}
			ss.append("</td>");
			ss.append("</tr>");
			ss.append("</table>");			
		case 3:			
		case 4:
			break;		
		case 6:
			ss.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
			ss.append("<tr>");
			ss.append("<td id='column_1' class='col'>");
			for (int i = 0; i < wp.size(); i++) {
				Widget w = (Widget) wp.get(i);
				if (w.getColumnIndex() != 2 && w.getColumnIndex() != 3 && w.getColumnIndex() != 4) {
					ss.append(this.getWidgetWindow(w));
				}
			}
			ss.append("</td>");
			ss.append("<td id='column_2' class='col'>");
			for (int i = 0; i < wp.size(); i++) {
				Widget w = (Widget) wp.get(i);
				if (w.getColumnIndex() == 2) {
					ss.append(this.getWidgetWindow(w));
				}
			}
			ss.append("</td>");
			ss.append("<td id='column_3' class='col'>");
			for (int i = 0; i < wp.size(); i++) {
				Widget w = (Widget) wp.get(i);
				if (w.getColumnIndex() == 3) {
					ss.append(this.getWidgetWindow(w));
				}
			}
			ss.append("</td>");
			ss.append("<td id='column_4' class='col'>");
			for (int i = 0; i < wp.size(); i++) {
				Widget w = (Widget) wp.get(i);
				if (w.getColumnIndex() == 4) {
					ss.append(this.getWidgetWindow(w));
				}
			}
			ss.append("</td>");
			ss.append("</tr>");
			ss.append("</table>");
			break;		
		case 8:
			ss.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
			ss.append("<tr>");
			ss.append("<td id='column_1' class='col'>");
			for (int i = 0; i < wp.size(); i++) {
				Widget w = (Widget) wp.get(i);
				ss.append(this.getWidgetWindow(w));
			}
			ss.append("</td>");
			ss.append("</tr>");
			ss.append("</table>");
			break;
		default:
			ss.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
			ss.append("<tr>");
			ss.append("<td id='column_1' class='col'>");
			for (int i = 0; i < wp.size(); i++) {
				Widget w = (Widget) wp.get(i);
				if (w.getColumnIndex() != 2 && w.getColumnIndex() != 3) {
					ss.append(this.getWidgetWindow(w));

				}
			}
			ss.append("</td>");
			ss.append("<td id='column_2' class='col'>");
			for (int i = 0; i < wp.size(); i++) {
				Widget w = (Widget) wp.get(i);
				if (w.getColumnIndex() == 2) {
					ss.append(this.getWidgetWindow(w));

				}
			}
			ss.append("</td>");
			ss.append("<td id='column_3' class='col'>");
			for (int i = 0; i < wp.size(); i++) {
				Widget w = (Widget) wp.get(i);
				if (w.getColumnIndex() == 3) {
					ss.append(this.getWidgetWindow(w));

				}
			}
			ss.append("</td>");
			ss.append("</tr>");
			ss.append("</table>");
			break;
		}

		ss.append("</div>");
		String s = ss.toString();
		ss = null;
		return s;
	}

	private String getWidgetWindow(Widget w) {
		String icon = null;
		icon = w.getIcon();
		if (icon == null) {
			ModuleIcon mi = new ModuleIcon();
			icon = mi.getModuleIcon(w.getModule());
			mi = null;
		}
		StringBuilder sw = new StringBuilder();
		sw.append("<div id='webpart_" + w.getId() + "' class='widgetWindow'>");
		sw.append("<div class='widgetFrame'>");
		sw.append("<table class='widgetTable' border='0' cellpadding='0' cellspacing='0'>");
		sw.append("<thead>");
		sw.append("<tr><td class='widgetHead h_lt'></td><td class='widgetHead h_mt'>");
		sw.append("<div class='widgetHeader'>");
		if (icon != null) {
			sw.append("<div class='ico'><img class='mod_icon' src='" + siteUrl + icon
					+ "' width='16' align='absmiddle' border='0' height='16'></div>");
		}
		sw.append("<div class='title' id='t_" + w.getId() + "'>" + w.getTitle() + "</div>");
		sw.append("</div>");
		sw.append("</td><td class='widgetHead h_rt'></td>");
		sw.append("</tr>");
		sw.append("</thead>");
		sw.append("<tbody>");
		sw.append("<tr><td class='widgetContent c_lt'></td><td class='widgetContent c_mt'><div id='webpart_"
				+ w.getId() + "_c' class='widgetContent'>");
		sw.append(this.getWidgetContent(w));
		sw.append("</div>");
		sw.append("</td><td class='widgetContent c_rt'></td></tr>");
		sw.append("</tbody>");
		sw.append("<tfoot><tr><td class='widgetFoot f_lt'></td><td class='widgetFoot f_mt'></td><td class='widgetFoot f_rt'></td></tr></tfoot>");
		sw.append("</table>");
		sw.append("</div>");
		sw.append("</div>");

		String _c = sw.toString();
		sw = null;
		return _c;
	}

	private String getWidgetContent(Widget w) {
		String m = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("widget", w);
		map.put("UserSiteUrl", this.userSiteUrl);
		map.put("SiteUrl", this.siteUrl);
		if (w.getModule().equals("profile")) {
			m = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_profile.ftl", "utf-8");
		}
		else if (w.getModule().equals("entries")) {
			m = getUserEntriesWidgetCOntent(w);
		}
		else if (w.getModule().equals("user_placard")) {
			m = getUserPlacardWidgetContent(w);
		}
		else if (w.getModule().equals("user_stats")) {
			m = this.templateProcessor.processTemplate(map, "/WEB-INF/user/html/html_user_stats.ftl", "utf-8");
		}
		else if (w.getModule().equals("user_calendar")) {
			m = this.templateProcessor.processTemplate(map, "/WEB-INF/user/html/html_user_calendar.ftl", "utf-8");
		}
		else if (w.getModule().equals("questionanswer")) {
			m = getUserQuestionWidgetContent(w);
		}
		else if (w.getModule().equals("user_joinedpreparecourse")) {
			m = getUserJoinedPrepareCourseWidgetContent(w);
		}
		else if (w.getModule().equals("user_createdaction")) {
			m = getUserCreateActionWidgetContent(w);
		}
		else if (w.getModule().equals("user_resources")) {
			m = getUserResourceWidgetContent(w);
		}
		else if (w.getModule().equals("user_preparecourse")) {
			m = getUserPrepareCourseWidgetContent(w);
		}
		else if (w.getModule().equals("user_leaveword")) {
			m = getUserLeaveWordWidgetContent(w);

		}
		else if (w.getModule().equals("joined_groups")) {
			m = getUserJoinedGroupWidgetContent(w);
		}
		else if (w.getModule().equals("friendlinks")) {
			m = getUserFriendLinksWidgetContent(w);
		}
		else if (w.getModule().equals("lastest_comments")) {
			m = getUserLastestCommentsWidgetContent(w);
		}
		else if (w.getModule().equals("user_joinedaction")) {
			m = getUserJoinedActionWidgetContent(w);
		}
		else if (w.getModule().equals("vote")) {
			m = getUserVoteWidgetContent(w);

		}
		else if (w.getModule().equals("topic")) {
			m = getUserTopicWidgetContent(w);
		}
		else if (w.getModule().equals("user_photo")) {
			m = getUserPhotoWidgetContent(w);

		}
		else if (w.getModule().equals("user_video")) {
			m = getUserVideoWidgetContent(w);
		}
		else if (w.getModule().equals("user_cate")) {
			m = getUserArticleCategoryTreeWidgetContent(w);
		}
		else if (w.getModule().equals("user_rcate")) {
			m = getUserResourceCategoryTreeWidgetContent(w);
		}
		else if (w.getModule().equals("photo_cate")) {
			m = getUserPhotoCategoryTreeWidgetContent(w);
		}
		else if (w.getModule().equals("blog_search")) {
			m = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/blog_search.ftl", "utf-8");
		}
		else if (w.getModule().equals("simple_text")) {
			m = getUserSimpleTextWidgetContent(w);
		}
		else if (w.getModule().equals("rss")) {
			m = getUserRssWidgetContent(w);
		}
		else if (w.getModule().equals("calendarevent")) {
			m = getUserCalendarEventWidgetContent(w);
		}
		else if (w.getModule().equals("category_article")) {
			m = getUserCategoryArticleWidgetContent(w);
		}

		map.clear();
		map = null;
		return m;
	}


	private String getUserCategoryArticleWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		Integer showCount = getWidgetListShowCount(w, 4);
		String title = "";
		Integer categoryId = 0;
		String data = w.getData();
		data = "{" + data + "}";
		if (data != null && data.equals("") == false) {
			
			JSONObject wdData = (JSONObject) JSONObject.parse(data);
			if (wdData.containsKey("title")) {
				title = wdData.getString("title");
			}
			if (wdData.containsKey("categoryId") && CommonUtil.isInteger(wdData.getString("categoryId"))) {
				categoryId = Integer.valueOf(wdData.getString("categoryId"));
			}
			wdData = null;
		}
		
		CommonQuery2 qa = new CommonQuery2(this.getSession());
		qa.setMaxRow(showCount);
		qa.setStartRow(0);
		qa.setHql("SELECT new Map(a.articleId as articleId, a.title as title, a.createDate as createDate, a.typeState as typeState) FROM Article as a");
		qa.setOrderByClause("a.articleId DESC");
		qa.setWhereClause("a.userCateId = "
				+ categoryId
				+ " And a.hideState = 0 And a.draftState = false And a.delState = false And auditState = 0 "
				+ " And a.userId = " + this.user.getUserId());
		List article_list = qa.getQueryList();
		map.put("article_list", article_list);
		map.put("userCateId", categoryId);
		String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/category_article.ftl", "utf-8");
		article_list.clear();
		article_list = null;
		qa = null;
		w.setTitle(CommonUtil.unescape(title));
		map.clear();
		map = null;
		return ret;
	}

	private String getUserCalendarEventWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		map.put("parentGuid", this.user.getUserGuid());
		map.put("parentType", "user");
		String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/mod/calendarevent/listview.ftl", "utf-8");
		return ret;
	}

	private String getUserRssWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		String feedUrl = "";
		Integer showCount = this.getWidgetListShowCount(w, 10);
		String data = w.getData();
		data = "{" + data + "}";
		if (data != null && data.equals("") == false) {
			JSONObject wdData = (JSONObject) JSONObject.parse(data);
			if (wdData.containsKey("feedUrl")) {
				feedUrl = wdData.getString("feedUrl");
			}
			wdData = null;
		}
		map.put("widgetId", w.getId());
		map.put("feedUrl", feedUrl);
		map.put("showCount", showCount);

		String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/htmlrss.ftl", "utf-8");
		map.clear();
		map = null;
		return ret;
	}

	private String getUserSimpleTextWidgetContent(Widget w) {
		String c = "";
		String data = w.getData();
		data = "{" + data + "}";
		if (data != null && data.equals("") == false) {
			JSONObject wdData = (JSONObject)JSONObject.parse(data);
			if (wdData.containsKey("content")) {
				c = wdData.getString("content");
			}
			wdData = null;
		}
		return c;
	}

	private String getUserPhotoCategoryTreeWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		String hql = "select new Map(stap.id as id, stap.title as title) from PhotoStaple stap "
				+ "where stap.isHide = 0 and stap.userId = " + this.user.getUserId() + "ORDER BY stap.orderNum ASC";
		List photocate = new Command(hql).open();
		map.put("photocate", photocate);
		String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/photo_category.ftl", "utf-8");
		photocate.clear();
		photocate = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserResourceCategoryTreeWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		String itemType = CommonUtil.toUserResourceCategoryItemType(user.getUserId());
		CategoryTreeModel usercate_tree = this.categoryService.getCategoryTree(itemType);
		map.put("usercate_tree", usercate_tree);
		String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_res_cate.ftl", "utf-8");
		usercate_tree = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserArticleCategoryTreeWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		String itemType = CommonUtil.toUserArticleCategoryItemType(user.getUserId());
		CategoryTreeModel usercate_tree = this.categoryService.getCategoryTree(itemType);
		map.put("usercate_tree", usercate_tree);
		String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_cate.ftl", "utf-8");
		usercate_tree = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserVideoWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		Integer showCount = getWidgetListShowCount(w, 4);
		CommonQuery2 qa = new CommonQuery2(this.getSession());
		qa.setMaxRow(showCount);
		qa.setStartRow(0);
		qa.setHql("SELECT new Map(v.videoId as videoId, v.title as title, v.flvThumbNailHref as flvThumbNailHref, v.href as href, u.nickName as nickName, u.loginName as loginName, u.userIcon as userIcon) FROM Video as v,User u");
		qa.setOrderByClause("v.videoId DESC");
		qa.setWhereClause("v.userId = u.userId And v.userId=" + this.user.getUserId() + " And v.auditState = 0 ");
		List video_list = qa.getQueryList();
		map.put("video_list", video_list);
		String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_video.ftl", "utf-8");
		video_list.clear();
		video_list = null;
		qa = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserPhotoWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		Integer showCount = getWidgetListShowCount(w, 4);
		CommonQuery2 qa = new CommonQuery2(this.getSession());
		qa.setMaxRow(showCount);
		qa.setStartRow(0);
		qa.setHql("SELECT new Map(p.photoId as photoId, p.title as title, p.href as href,u.loginName as loginName) FROM Photo as p,User u");
		qa.setOrderByClause("p.photoId DESC");
		qa.setWhereClause("p.userId = u.userId And p.userId=" + this.user.getUserId() + " And p.auditState = 0 ");
		List photo_list = qa.getQueryList();
		map.put("photo_list", photo_list);
		String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_photo.ftl", "utf-8");
		photo_list.clear();
		photo_list = null;
		qa = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserTopicWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		CommonQuery2 qa = new CommonQuery2(this.getSession());
		qa.setMaxRow(10);
		qa.setStartRow(0);
		qa.setHql("SELECT new Map(pt.plugInTopicId as plugInTopicId, pt.title as title, pt.createDate as createDate, pt.createUserId as createUserId, pt.createUserName as createUserName) FROM PlugInTopic as pt");
		qa.setOrderByClause("pt.plugInTopicId DESC");
		qa.setWhereClause("pt.parentGuid = '" + this.user.getUserGuid() + "' And pt.parentObjectType = 'user' ");
		List topic_list = qa.getQueryList();
		map.put("topic_list", topic_list);
		map.put("canManage", "false");
		map.put("parentGuid", this.user.getUserGuid());
		map.put("parentType", "user");
		String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/listtopic.ftl", "utf-8");
		topic_list.clear();
		topic_list = null;
		qa = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserVoteWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		String ret;
		Vote vote = this.voteService.getNewestVote(this.user.getUserGuid());
		if (vote != null) {
			String voteHasExpires = this.voteService.voteHasExpires(vote.getVoteId()) ? "true" : "false";
			List<VoteQuestion> vote_question_list = this.voteService.getVoteQuestionList(vote.getVoteId());
			ArrayList<Hashtable<String, Object>> vote_question_answer_list = new ArrayList<Hashtable<String, Object>>();
			for (int i = 0; i < vote_question_list.size(); i++) {
				VoteQuestion q = (VoteQuestion) vote_question_list.get(i);
				List<VoteQuestionAnswer> vote_answer_list = this.voteService.getVoteQuestionAnswerList(q.getVoteQuestionId());
				Hashtable<String, Object> vote_question = new Hashtable<String, Object>();
				vote_question.put("question", q);
				vote_question.put("answer", vote_answer_list);
				vote_question_answer_list.add(vote_question);
			}
			map.put("vote", vote);
			map.put("vote_question_answer_list", vote_question_answer_list);
			map.put("voteHasExpires", voteHasExpires);
			map.put("canManage", "false");
			map.put("parentGuid", this.user.getUserGuid());
			map.put("parentType", "user");
			ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/listvote.ftl", "utf-8");
			map.clear();
			map = null;
			vote_question_answer_list.clear();
			vote_question_answer_list = null;

		}
		else {
			ret = "当前没有投票";
		}
		return ret;
	}

	private String getUserJoinedActionWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		Integer showCount = getWidgetListShowCount(w, 10);
		CommonQuery2 qa = new CommonQuery2(this.getSession());
		qa.setMaxRow(showCount);
		qa.setStartRow(0);
		qa.setHql("SELECT new Map(act.title as title, act.createDate as createDate, act.actionId as actionId, act.ownerId as ownerId, act.ownerType as ownerType) FROM ActionUser as actu,Action as act");
		qa.setOrderByClause("act.actionId DESC");
		qa.setWhereClause("act.status = 0 And actu.actionId = act.actionId And actu.isApprove = 1 And actu.userId = " + this.user.getUserId() + " And act.createUserId <> " + this.user.getUserId());
		List action_list = qa.getQueryList();
		map.put("action_list", action_list);
		String ret = this.templateProcessor
				.processTemplate(map, "/WEB-INF/user/default/user_joinedaction.ftl", "utf-8");
		action_list.clear();
		action_list = null;
		qa = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserLastestCommentsWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		Integer showCount = this.getWidgetListShowCount(w, 5);
		List<Comment> comment_list = this.commentService.getRecentCommentAboutUser(this.user.getUserId(), showCount);
		map.put("comment_list", comment_list);
		String ret = this.templateProcessor.processTemplate(map, "/js/jitar/module/lastest_comments.ftl", "utf-8");
		comment_list.clear();
		comment_list = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserFriendLinksWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		Integer showCount = getWidgetListShowCount(w, 4);
		Object friend_list = this.friendService.getFriendList(user.getUserId());
		map.put("friend_list", friend_list);
		map.put("count", showCount);
		String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_friendlinks.ftl", "utf-8");
		friend_list = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserJoinedGroupWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		Integer showCount = getWidgetListShowCount(w, 10);
		CommonQuery2 qa = new CommonQuery2(this.getSession());
		qa.setMaxRow(showCount);
		qa.setStartRow(0);
		qa.setHql("SELECT new Map(gm.id as id, gm.group as group, g.groupName as groupName, g.groupId as groupId, g.groupTitle as groupTitle, g.groupIcon as groupIcon, g.topicCount as topicCount, g.userCount as userCount, g.articleCount as articleCount, g.resourceCount as resourceCount, g.visitCount as visitCount) FROM GroupMember as gm,Group g");
		qa.setOrderByClause("gm.id DESC");
		qa.setWhereClause("g.groupId = gm.groupId And gm.status = 0 And g.groupState = 0 And gm.userId = "
				+ this.user.getUserId());
		List group_list = qa.getQueryList();
		map.put("group_list", group_list);
		String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/joined_groups.ftl", "utf-8");
		group_list.clear();
		group_list = null;
		qa = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserLeaveWordWidgetContent(Widget w) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		Integer showCount = getWidgetListShowCount(w, 6);
		LeavewordQueryParam param = new LeavewordQueryParam();
		param.count = showCount; // 取最新 count 条.
		param.objType = ObjectType.OBJECT_TYPE_USER.getTypeId();
		param.objId = user.getUserId();
		List<LeaveWord> leaveword_list = this.leavewordService.getLeaveWordList(param, null);
		map.put("leaveword_list", leaveword_list);
		String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_leaveword.ftl", "utf-8");
		param = null;
		leaveword_list.clear();
		map.clear();
		leaveword_list = null;
		map = null;
		return ret;
	}

	private String getUserPrepareCourseWidgetContent(Widget w) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		CommonQuery2 qa = new CommonQuery2(this.getSession());
		qa.setMaxRow(10);
		qa.setStartRow(0);
		qa.setHql("SELECT new Map(pc.createUserId as createUserId,pc.leaderId as leaderId,pc.memberCount as memberCount,pc.articleCount as articleCount,pc.resourceCount as resourceCount, "
				+ "pc.actionCount as actionCount,pc.topicCount as topicCount,pc.topicReplyCount as topicReplyCount,pc.viewCount as viewCount, pc.title as title, pc.prepareCourseId as prepareCourseId, pc.createDate as createDate) FROM PrepareCourse as pc");
		qa.setOrderByClause("pc.prepareCourseId DESC");
		qa.setWhereClause("pc.status = 0 And pc.prepareCourseGenerated = true And pc.createUserId = "
				+ this.user.getUserId());
		List course_list = qa.getQueryList();
		map.put("course_list", course_list);
		String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_preparecourse.ftl",
				"utf-8");
		course_list.clear();
		course_list = null;
		qa = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserResourceWidgetContent(Widget w) {
		String ret;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		CommonQuery2 qa = new CommonQuery2(this.getSession());
		qa.setMaxRow(10);
		qa.setStartRow(0);
		qa.setHql("SELECT new Map(r.createDate as createDate, r.resourceId as resourceId, r.title as title, r.href as href) FROM Resource as r");
		qa.setOrderByClause("r.resourceId DESC");
		qa.setWhereClause("r.auditState = 0 And r.delState = False And shareMode >= " + Resource.SHARE_MODE_FULL
				+ " And r.userId = " + this.user.getUserId());
		List resource_list = qa.getQueryList();
		map.put("resource_list", resource_list);
		ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_resources.ftl", "utf-8");
		resource_list.clear();
		resource_list = null;
		qa = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserCreateActionWidgetContent(Widget w) {
		String ret;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		CommonQuery2 qa = new CommonQuery2(this.getSession());
		qa.setMaxRow(10);
		qa.setStartRow(0);
		qa.setHql("SELECT new Map(act.title as title, act.createDate as createDate, act.actionId as actionId, act.ownerId as ownerId, "
				+ "act.ownerType as ownerType, act.createUserId as createUserId, act.actionType as actionType) FROM Action as act, User as u ");
		qa.setOrderByClause("act.actionId DESC");
		qa.setWhereClause("act.status = 0 And act.createUserId = u.userId And act.createUserId = "
				+ this.user.getUserId());
		List action_list = qa.getQueryList();
		map.put("action_list", action_list);
		ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_createdaction.ftl", "utf-8");
		action_list.clear();
		action_list = null;
		qa = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserJoinedPrepareCourseWidgetContent(Widget w) {
		String ret;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		CommonQuery2 qa = new CommonQuery2(this.getSession());
		qa.setMaxRow(10);
		qa.setStartRow(0);
		qa.setHql("SELECT new Map( pc.prepareCourseId as prepareCourseId,pc.title as title) FROM PrepareCourseMember as pcm, PrepareCourse as pc, User as u ");
		qa.setOrderByClause("pcm.prepareCourseMemberId DESC");
		qa.setWhereClause("pcm.prepareCourseId = pc.prepareCourseId And pcm.status = 0 And pcm.userId = u.userId And pcm.userId = "
				+ this.user.getUserId());
		List course_list = qa.getQueryList();
		map.put("course_list", course_list);
		ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_joinedpreparecourse.ftl", "utf-8");
		course_list.clear();
		course_list = null;
		qa = null;
		map.clear();
		map = null;
		return ret;
	}

	private String getUserQuestionWidgetContent(Widget w) {
		String ret;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		if (!this.pluginService.checkPluginEnabled("questionanswer")) {
			map.put("message", "该插件已经被管理员禁用。");
			ret = this.templateProcessor.processTemplate(map, "/WEB-INF/mod/show_text.ftl", "utf-8");
		}
		else {
			List<Question> q_list = this.questionAnswerService.getQuestionList(this.user.getUserGuid(), "new", 5);
			map.put("q_list", q_list);
			map.put("parentGuid", this.user.getUserGuid());
			map.put("parentType", "user");
			map.put("canManage", "false");
			ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/listquestion.ftl", "utf-8");
			q_list.clear();
			q_list = null;
		}
		map.clear();
		map = null;
		return ret;
	}

	@SuppressWarnings("unused")
	private String getUserCalendarWidgetContent(Widget w) {
		String ret;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		Lunar lc = new Lunar(System.currentTimeMillis());
		map.put("today", lc.getCurrentDate());
		map.put("nongli", lc.getLunarDateString());
		map.put("weekday", lc.getDayOfWeek());
		if (!lc.getTermString().equals(""))
			map.put("jieqi", lc.getTermString());
		ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_calendar.ftl", "utf-8");
		map.clear();
		map = null;
		lc = null;
		return ret;
	}

	private String getUserPlacardWidgetContent(Widget w) {
		String ret;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		PlacardQueryParam param = new PlacardQueryParam();
		param.count = 1; // 获取第一条.
		param.objType = ObjectType.OBJECT_TYPE_USER;
		param.objId = user.getUserId();
		List<Placard> placard_list = this.placardService.getPlacardList(param, null);
		map.put("placard_list", placard_list);
		ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_placard.ftl", "utf-8");
		param = null;
		return ret;
	}

	private String getUserEntriesWidgetCOntent(Widget w) {
		String ret;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		map.put("UserSiteUrl", this.userSiteUrl);
		Integer showCount = getWidgetListShowCount(w, 10);
		ArticleQueryParam para = new ArticleQueryParam();
		para.count = showCount;
		para.userId = this.user.getUserId();
		List<ArticleModelEx> article_list = this.articleService.getArticleList(para, null);
		map.put("article_list", article_list);
		ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/default/user_entries.ftl", "utf-8");
		return ret;
	}

	private Integer getWidgetListShowCount(Widget w, Integer defaultValue) {
		Integer showCount = defaultValue;
		String data = w.getData();
		data = "{" + data + "}";
		if (data != null && data.equals("") == false) {
			JSONObject wdData = (JSONObject) JSONObject.parse(data);
			if (wdData.containsKey("count")) {
				String c = wdData.getString("count");
				if(CommonUtil.isNumber(c))
				{
					try {
						showCount = Integer.parseInt(c);
					} 
					catch (NumberFormatException ex) {
						showCount = defaultValue;
					}		
				}
			}
			wdData = null;
		}
		return showCount;
	}

	/** 生成文章的列表 ，只有第一页是完整的页面，其它页面采用AJAX请求数据。 */
	public void GenUserArticleList(User user) {
		// 文章列表格式：article_分类id_分页.html
		// 全局对象，
		this.user = user;

		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		// 生成文章，按照分类进行。

		// 先生成默认的页面列表，取出全部分类的文章
		this.GenUserCategoryArticle(null, user_dir);

		// 得到用户自定义分类的文章
		String item_type = "user_" + this.user.getUserId();
		List<CategoryModel> c = this.categoryService.getCategoryTree(item_type).getAll();

		// 生成所有分类的文章
		for (int i = 0; i < c.size(); i++) {
			CategoryModel cm = (CategoryModel) c.get(i);
			this.GenUserCategoryArticle(cm, user_dir);
			//log.info("执行次数：" + cm.getCategoryId());
		}

		c = null;
	}

	/** 生成某一个分类的文章列表，如果分类为 null ，则提取的是全部文章 */
	private void GenUserCategoryArticle(CategoryModel cate, String user_dir) {
		// 整个页面的容器
		StringBuilder articleContent = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		articleContent.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		articleContent.append("<tr>");
		articleContent.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				articleContent.append(this.getWidgetWindow(w));
			}
			else if (w.getModule().equalsIgnoreCase("user_cate")) {
				articleContent.append(this.getWidgetWindow(w));
			}
			else if (w.getModule().equalsIgnoreCase("blog_search")) {
				articleContent.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		articleContent.append("</td>");
		articleContent.append("<td id='column_2' class='col'>");
		String left = articleContent.toString();
		String rightBottom = "</td></tr></table>";
		articleContent = null;
		Category category = null;
		// 主体部分，文章列表

		// 得到文章的分类所有文章

		int cateId = 0;
		if (cate != null)
			cateId = cate.getId();
		String queryString = "";
		String whereClause = "a.userId="
				+ this.user.getUserId()
				+ " And a.auditState = 0 And a.hideState = 0 And a.draftState = false And a.delState = false  ";
		if (cateId != 0) {
			category = this.categoryService.getCategory(cateId);
			whereClause = whereClause + "And a.userCateId = " + cateId; // 不选择分类，则列出全部的文章
		}

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "select count(*) as articleCount from Article a Where " + whereClause;
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		int articleCount = count == null?0:Integer.parseInt(count.toString());
		if (articleCount == 0) {
			file = new File(user_dir + "article_" + cateId + "_0.html");
			try {
				fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				fw.write(header);
				fw.write(left);
				String content = "该分类下没有文章。";
				if (category != null) {

					content = category.getName() + " 分类下没有文章。";
				}
				Widget w = new Widget();
				w.setIcon("js/jitar/moduleicon/content.gif");
				if (category == null) {
					w.setTitle("所有文章");
				}
				else {
					w.setTitle(category.getName() + "分类下的所有文章");
				}

				fw.write(this.GetBlankWidget(w, content));
				w = null;

				fw.write(rightBottom);
				fw.write(footer);
				fw.flush();
				fw.close();
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			return;
		}

		final int pageItem = 30;
		int pageCount = articleCount % pageItem == 0 ? articleCount / pageItem : articleCount / pageItem + 1;
		//log.info("articleCount = " + articleCount + " pageCount = " + pageCount);
		for (int i = 0; i < pageCount; i++) {
			final int m = i;
			//log.info("m = " + m);
			final String hql = "From Article a Where " + whereClause + " Order By a.articleId DESC";
			
					Query query = this.getSession().createQuery(hql);
					query.setFirstResult(m * pageItem);
					query.setMaxResults(pageItem);
					List list = query.list();
					

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("article_list", list);
			map.put("UserSiteUrl", this.userSiteUrl);
			map.put("pageCount", pageCount);
			map.put("cateId", cateId);

			// article_cateId_0.html是首页，是一个完整的页面，其他页面则是只有内容部分。
			if (i == 0) {
				file = new File(user_dir + "article_" + cateId + "_" + i + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					fw.write(header);
					fw.write(left);
					String ret = this.templateProcessor.processTemplate(map,
							"/WEB-INF/user/html/html_article_list_index.ftl", "utf-8");
					Widget w = new Widget();
					w.setIcon("js/jitar/moduleicon/content.gif");
					if (category == null) {
						w.setTitle("所有文章");
					}
					else {
						w.setTitle(category.getName() + "下的所有文章");
					}

					fw.write(this.GetBlankWidget(w, ret));
					w = null;

					fw.write(rightBottom);
					fw.write(footer);

					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				file = new File(user_dir + "article_" + cateId + "_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,
							"/WEB-INF/user/html/html_article_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

			}
			else {
				file = new File(user_dir + "article_" + cateId + "_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,
							"/WEB-INF/user/html/html_article_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
			map.clear();
			map = null;
		}
		category = null;

	}

	/** 页面主框架页面部分 各个子页面共用 */
	private String GenUserHeader(User user) {
		this.user = user;
		page = this.getUserIndexPage(this.user);
		if (page == null) {
			page = this.createUserIndexPage(this.user);
		}

		if (page == null) {
			System.out.println("无法得到该用户的页面对象。");
			return "";
		}
		StringBuilder frame = new StringBuilder();

		siteUrl = SiteUrlModel.getSiteUrl();
		// siteThemeUrl =
		// configService.getConfigure().getStringValue("siteThemeUrl");
		userMgrUrl = configService.getConfigure().getStringValue("userMgrUrl");
		userMgrClientUrl=UserMgrClientModel.getUserMgrClientUrl();
		userSiteUrl = this.jitarContext.getServletContext().getInitParameter("userUrlPattern");
		if (userSiteUrl == null || userSiteUrl.equals(""))
			userSiteUrl = this.siteUrl + "u/" + this.user.getLoginName() + "/";

		frame.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		frame.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		frame.append("<head>");
		frame.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		frame.append("<title>");
		if (user.getBlogName() != null && !user.getBlogName().equals("")) {
			frame.append(user.getBlogName());
		}
		else {
			frame.append(user.getNickName());
		}
		frame.append("</title>");
		frame.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + siteUrl + "css/common/common.css\" />");
		if (page.getSkin() == null || page.getSkin().equals("")) {
			frame.append("<link id='skin' rel=\"stylesheet\" type=\"text/css\" href=\"" + siteUrl
					+ "css/skin/default/skin.css\" />");
		}
		else {
			frame.append("<link id='skin' rel=\"stylesheet\" type=\"text/css\" href=\"" + siteUrl + "css/skin/"
					+ page.getSkin() + "/skin.css\" />");
		}

		frame.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + siteUrl + "css/layout/layout_" + layoutId
				+ ".css\" />");
		frame.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + siteUrl + "css/tooltip/tooltip.css\" />");
		frame.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + siteUrl + "css/htmlpager.css\" />");
		frame.append("<script type='text/javascript' src='" + siteUrl + "js/jitar/core.js'></script>");
		frame.append("<script type='text/javascript' src='" + siteUrl + "js/jitar/lang.js'></script>");
		frame.append("<script type=\"text/javascript\" src=\"" + siteUrl + "js/jitar/tooltip.js\"></script>");
		frame.append("<script type=\"text/javascript\" src=\"" + siteUrl + "js/jitar/login.js\"></script>");
		frame.append("<script type=\"text/javascript\" src=\"" + siteUrl + "js/jitar/htmlpager.js\"></script>");
		frame.append("<link rel=\"icon\" href=\"" + siteUrl + "images/favicon.ico\" />");
		frame.append("<link rel=\"shortcut icon\" href=\"" + siteUrl + "images/favicon.ico\" />");
		String custom = page.getCustomSkin();
		if (custom != null && !custom.equals("")) {
			// 存储格式
			// {"logo":"","titletop":"","titleleft":"","titledisplay":"","logoheight":"","bgcolor":"#FF0000"}
			JSONObject customSkin = (JSONObject) JSONObject.parse(custom);
			frame.append("<style type='text/css'>");
			if (!customSkin.getString("logo").trim().equals("")) {
				frame.append("#header { ");
				if (customSkin.getString("logo").trim().substring(0, 6) == "/user/") {
					frame.append("background:url('" + siteUrl + customSkin.getString("logo").trim().substring(1)	+ "') repeat-x top center;");
				}
				else {
					frame.append("background:url('" + customSkin.getString("logo").trim() + "') repeat-x top center;");
				}
			}
			if (customSkin.get("logoheight")!= null && !customSkin.getString("logoheight").trim().equals("")) {
				frame.append("height:" + customSkin.getString("logoheight").trim() + "px;");
			}
			if (customSkin.get("bgcolor")!=null && !customSkin.getString("bgcolor").trim().equals("")) {
				frame.append("html,body{ background:" + customSkin.getString("bgcolor").trim() + "} }");
			}
			if (customSkin.get("titleleft")!=null && !customSkin.getString("titleleft").trim().equals("")) {
				frame.append("#blog_name { padding-left:" + customSkin.getString("titleleft").trim() + "px; }");
			}
			if (customSkin.get("titletop")!= null && !customSkin.getString("titletop").trim().equals("")) {
				frame.append("#blog_name { padding-top:" + customSkin.getString("titletop").trim() + "px; }");
			}
			if (customSkin.get("titledisplay") != null && !customSkin.getString("titledisplay").trim().equals("")) {
				frame.append("#blog_name {display:none;}");
			}

			frame.append("</style>");
		}
		frame.append("<script>var ContainerObject={'type':'user','guid':'" + this.user.getUserGuid() + "'};");
		frame.append("var JITAR_ROOT='" + this.siteUrl + "';");
		frame.append("var USERMGR_ROOT='" + this.userMgrUrl + "';");
		frame.append("var USERMGRCLIENT_ROOT='" + this.userMgrClientUrl + "';");
		frame.append("var USERSITE_ROOT='" + this.userSiteUrl + "';");
		frame.append("var BasePageUrl=window.location.pathname;var visitor=null;var user=null;");
		frame.append("</script>");
		frame.append("</head>");
		frame.append("<body>");
		frame.append("<noscript><h1>本网站需要启用 JavaScript</h1><P>当前浏览器不支持 JavaScript 或阻止了脚本。<br/><br/>若要查看您的浏览器是否支持 JavaScript 或允许使用脚本，请查看浏览器联机帮助。</P></noscript>");
		frame.append("<div id='toolbar'></div>");
		frame.append("<div id='header'>");
		frame.append("<div id='blog_name'><span>" + user.getBlogName() + "</span></div>");
		frame.append("</div>");
		String t = frame.toString();
		frame = null;
		return t;
	}

	/** 生成页面的尾部 */
	private String GenUserHtmlFoot() {
		StringBuilder foot = new StringBuilder();
		foot.append("<div id='page_footer'></div>");
		foot.append("<script src='" + this.siteUrl + "css/tooltip/tooltip_html.js' type='text/javascript'></script>");
		foot.append("<script type='text/javascript' src='" + this.siteUrl + "js/jitar/msgtip2.js'></script>");
		foot.append("<iframe src='" + this.userSiteUrl + "py/login_status.py' style='display:none'></iframe>");
		foot.append("</body>");
		foot.append("</html>");
		String t = foot.toString();
		foot = null;
		return t;
	}

	/** 用户静态文件夹位置 */
	private String getUserHtmlFolder() {
		String path = this.getClass().getResource("/").getPath().toString();
		if (path == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return null;
		}
		File file = null;
		String root_dir = path.substring(0, path.indexOf("WEB-INF"));
		String user_dir = root_dir + "html/user/" + this.user.getLoginName() + "/";
		file = new File(user_dir);
		if (!file.exists()) {
			try
			{
				file.mkdirs();
			}
			catch(Exception ex)
			{
				System.out.println("创建文件夹 " + user_dir + " 失败。" + ex.getLocalizedMessage());
				return null;
			}
		}
		return user_dir;
	}

	private String GetBlankWidget(Widget w, String content) {
		String icon = null;
		if (icon == null) {
			ModuleIcon mi = new ModuleIcon();
			icon = mi.getModuleIcon(w.getModule());
			mi = null;
		}
		StringBuilder sw = new StringBuilder();
		sw.append("<div class='widgetWindow'>");
		sw.append("<div class='widgetFrame'>");
		sw.append("<table class='widgetTable' border='0' cellpadding='0' cellspacing='0'>");
		sw.append("<thead>");
		sw.append("<tr><td class='widgetHead h_lt'></td><td class='widgetHead h_mt'>");
		sw.append("<div class='widgetHeader'>");
		if (icon != null) {
			sw.append("<div class='ico'><img class='mod_icon' src='" + siteUrl + icon
					+ "' width='16' align='absmiddle' border='0' height='16'></div>");
		}
		sw.append("<div class='title'>" + w.getTitle() + "</div>");
		sw.append("</div>");
		sw.append("</td><td class='widgetHead h_rt'></td>");
		sw.append("</tr>");
		sw.append("</thead>");
		sw.append("<tbody>");
		sw.append("<tr><td class='widgetContent c_lt'></td><td class='widgetContent c_mt'><div class='widgetContent'>");
		sw.append(content);
		sw.append("</div>");
		sw.append("</td><td class='widgetContent c_rt'></td></tr>");
		sw.append("</tbody>");
		sw.append("<tfoot><tr><td class='widgetFoot f_lt'></td><td class='widgetFoot f_mt'></td><td class='widgetFoot f_rt'></td></tr></tfoot>");
		sw.append("</table>");
		sw.append("</div>");
		sw.append("</div>");

		String _c = sw.toString();
		sw = null;
		return _c;
	}

	/**
	 * 详细个人档案列表
	 */
	public void GenUserProfile(User user) {
		// 整个页面的容器
		StringBuilder profileContent = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		profileContent.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		profileContent.append("<tr>");
		profileContent.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				profileContent.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		profileContent.append("</td>");
		profileContent.append("<td id='column_2' class='col'>");
		String left = profileContent.toString();
		String rightBottom = "</td></tr></table>";
		profileContent = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);
		CommonQuery2 cq = new CommonQuery2(this.getSession());
		String hql = "Select new Map(u.userId as userId, u.loginName as loginName,u.nickName as nickName,u.trueName as trueName,u.userIcon as userIcon, frd.addTime as addTime) From User u,Friend frd";

		cq.setHql(hql);
		cq.setWhereClause("frd.friendId =  u.userId And frd.userId = " + this.user.getUserId());
		cq.setOrderByClause("frd.id DESC");
		cq.setMaxRow(12);
		cq.setStartRow(0);
		map.put("friend_list", cq.getQueryList());
		hql = "Select new Map(gm.id as id, g.groupName as groupName, g.groupId as groupId, g.groupTitle as groupTitle, g.groupIcon as groupIcon) From GroupMember gm,Group g,User u";
		cq.setHql(hql);
		cq.setWhereClause("g.groupId = gm.groupId And u.userId = gm.userId And gm.userId = " + this.user.getUserId());
		cq.setOrderByClause("gm.id DESC");
		cq.setMaxRow(16);
		cq.setStartRow(0);
		map.put("group_list", cq.getQueryList());
		cq = null;

		String profilePage = this.templateProcessor.processTemplate(map, "/js/jitar/module/full_profile.ftl", "utf-8");
		map.clear();
		map = null;

		String user_dir = this.getUserHtmlFolder();

		File file = null;
		OutputStreamWriter fw = null;
		file = new File(user_dir + "profile.html");
		try {
			fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			fw.write(header);
			fw.write(left);
			Widget w = new Widget();
			w.setTitle("个人档案");

			fw.write(this.GetBlankWidget(w, profilePage));
			fw.write(rightBottom);
			fw.write(footer);
			fw.flush();
			fw.close();
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
			return;
		}
		fw = null;
		file = null;

	}

	public void setTemplateProcessor(TemplateProcessor templateProcessor) {
		this.templateProcessor = templateProcessor;
	}

	public void setJitarContext(JitarContext jitarContext) {
		this.jitarContext = jitarContext;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}


	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}


	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public void setPlacardService(PlacardService placardService) {
		this.placardService = placardService;
	}

	public void setPluginService(PluginService pluginService) {
		this.pluginService = pluginService;
	}

	public void setQuestionAnswerService(QuestionAnswerService questionAnswerService) {
		this.questionAnswerService = questionAnswerService;
	}

	public void setLeavewordService(LeavewordService leavewordService) {
		this.leavewordService = leavewordService;
	}

	public void setFriendService(FriendService friendService) {
		this.friendService = friendService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public void setVoteService(VoteService voteService) {
		this.voteService = voteService;
	}



	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public void setPhotoStapleService(PhotoStapleService photoStapleService) {
		this.photoStapleService = photoStapleService;
	}

	/**
	 * 资源列表
	 */
	public void GenUserResourceList(User user) {
		this.user = user;
		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		// 先生成默认的页面列表，取出全部分类的文章
		this.GenUserCategoryResource(null, user_dir);

		// 得到用户自定义分类的文章
		String item_type = "user_res_" + this.user.getUserId();
		List<CategoryModel> c = this.categoryService.getCategoryTree(item_type).getAll();

		// 生成所有分类的文章
		for (int i = 0; i < c.size(); i++) {
			CategoryModel cm = (CategoryModel) c.get(i);
			this.GenUserCategoryResource(cm, user_dir);
			//log.info("执行次数：" + cm.getCategoryId());
		}
		c = null;

	}

	// 生成资源分类页面
	private void GenUserCategoryResource(CategoryModel cate, String user_dir) {
		StringBuilder leftBar = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		leftBar.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		leftBar.append("<tr>");
		leftBar.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				leftBar.append(this.getWidgetWindow(w));
			}
			else if (w.getModule().equalsIgnoreCase("user_rcate")) {
				leftBar.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		leftBar.append("</td>");
		leftBar.append("<td id='column_2' class='col'>");
		String left = leftBar.toString();
		String rightBottom = "</td></tr></table>";
		leftBar = null;
		Category category = null;

		// 得到资源分类

		int cateId = 0;
		if (cate != null)
			cateId = cate.getId();
		String queryString = "";
		String whereClause = "r.userId=" + this.user.getUserId()
				+ " And r.auditState = 0 And r.delState = false And shareMode > 0 ";
		if (cateId != 0) {
			category = this.categoryService.getCategory(cateId);
			whereClause = whereClause + " And r.userCateId = " + cateId; // 不选择分类，则列出全部的文章
		}

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "select count(*) as count from Resource r Where " + whereClause;
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		int listCount = count == null?0:Integer.parseInt(count.toString());
		if (listCount == 0) {
			file = new File(user_dir + "resource_" + cateId + "_0.html");
			try {
				fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				fw.write(header);
				fw.write(left);
				Widget w = new Widget();
				w.setIcon("js/jitar/moduleicon/content.gif");
				if (category == null) {
					w.setTitle("所有资源");
				}
				else {
					w.setTitle(category.getName() + "下的所有资源");
				}

				String content = "该分类下没有图片。";
				if (category != null) {
					content = category.getName() + " 分类下没有图片。";
				}
				fw.write(this.GetBlankWidget(w, content));
				w = null;

				fw.write(rightBottom);
				fw.write(footer);
				fw.flush();
				fw.close();
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			return;
		}

		final int pageItem = 30;
		int pageCount = listCount % pageItem == 0 ? listCount / pageItem : listCount / pageItem + 1;

		for (int i = 0; i < pageCount; i++) {
			final int m = i;
			//log.info("m = " + m);
			final String hql = "From Resource r Where " + whereClause + " Order By r.resourceId DESC";
			
					Query query = this.getSession().createQuery(hql);
					query.setFirstResult(m * pageItem);
					query.setMaxResults(pageItem);
					List list = query.list();
					

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("resource_list", list);
			map.put("UserSiteUrl", this.userSiteUrl);
			map.put("pageCount", pageCount);
			map.put("cateId", cateId);

			// article_cateId_0.html是首页，是一个完整的页面，其他页面则是只有内容部分。
			if (i == 0) {
				file = new File(user_dir + "resource_" + cateId + "_" + i + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					fw.write(header);
					fw.write(left);
					String ret = this.templateProcessor.processTemplate(map,
							"/WEB-INF/user/html/html_resource_list_index.ftl", "utf-8");
					Widget w = new Widget();
					w.setIcon("js/jitar/moduleicon/content.gif");
					if (category == null) {
						w.setTitle("所有资源");
					}
					else {
						w.setTitle(category.getName() + "下的所有资源");
					}

					fw.write(this.GetBlankWidget(w, ret));
					w = null;

					fw.write(rightBottom);
					fw.write(footer);

					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				file = new File(user_dir + "resource_" + cateId + "_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,
							"/WEB-INF/user/html/html_resource_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

			}
			else {
				file = new File(user_dir + "resource_" + cateId + "_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,
							"/WEB-INF/user/html/html_resource_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
			map.clear();
			map = null;
		}
		category = null;
	}

	public void GenUserVideoList(User user) {
		// TODO Auto-generated method stub

	}

	public void GenUserPhotoList(User user) {
		this.user = user;
		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		this.GenUserCategoryPhoto(null, user_dir);
		List photoStapleList = this.photoStapleService.getPhotoStapleList(this.user.getUserId());

		// 生成所有分类的
		for (int i = 0; i < photoStapleList.size(); i++) {
			PhotoStaple cm = (PhotoStaple) photoStapleList.get(i);
			this.GenUserCategoryPhoto(cm, user_dir);
		}
		photoStapleList = null;

	}

	private void GenUserCategoryPhoto(PhotoStaple cate, String user_dir) {
		StringBuilder leftBar = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		leftBar.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		leftBar.append("<tr>");
		leftBar.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				leftBar.append(this.getWidgetWindow(w));
			}
			else if (w.getModule().equalsIgnoreCase("photo_cate")) {
				leftBar.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		leftBar.append("</td>");
		leftBar.append("<td id='column_2' class='col'>");
		String left = leftBar.toString();
		String rightBottom = "</td></tr></table>";
		leftBar = null;
		PhotoStaple category = null;

		// 得到资源分类

		int cateId = 0;
		if (cate != null)
			cateId = cate.getId();
		String queryString = "";
		String whereClause = "p.userId=" + this.user.getUserId() + " And p.delState = false And p.auditState=0";
		if (cateId != 0) {
			category = this.photoStapleService.findById(cateId);
			whereClause = whereClause + " And p.userStaple = " + cateId; // 不选择分类，则列出全部
		}

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "select count(*) as count from Photo p Where " + whereClause;
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		int listCount = count == null?0:Integer.parseInt(count.toString());
		if (listCount == 0) {
			file = new File(user_dir + "photo_" + cateId + "_0.html");
			try {
				fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				fw.write(header);
				fw.write(left);
				String content = "该分类下没有图片。";
				if (category != null) {
					content = category.getTitle() + " 分类下没有图片。";
				}

				Widget w = new Widget();
				w.setIcon("js/jitar/moduleicon/content.gif");
				if (category == null) {
					w.setTitle("所有图片");
				}
				else {
					w.setTitle(category.getTitle() + "分类下的所有图片");
				}

				fw.write(this.GetBlankWidget(w, content));
				w = null;

				fw.write(rightBottom);
				fw.write(footer);
				fw.flush();
				fw.close();
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			return;
		}

		final int pageItem = 30;
		int pageCount = listCount % pageItem == 0 ? listCount / pageItem : listCount / pageItem + 1;

		for (int i = 0; i < pageCount; i++) {
			final int m = i;
			final String hql = "From Photo p Where " + whereClause + " Order By p.id DESC";
			
					Query query = this.getSession().createQuery(hql);
					query.setFirstResult(m * pageItem);
					query.setMaxResults(pageItem);
					List list = query.list();
				
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("user", this.user);
			map.put("photo_list", list);
			map.put("UserSiteUrl", this.userSiteUrl);
			map.put("pageCount", pageCount);
			map.put("cateId", cateId);

			// article_cateId_0.html是首页，是一个完整的页面，其他页面则是只有内容部分。
			if (i == 0) {
				file = new File(user_dir + "photo_" + cateId + "_" + i + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					fw.write(header);
					fw.write(left);
					String ret = this.templateProcessor.processTemplate(map,
							"/WEB-INF/user/html/html_photo_list_index.ftl", "utf-8");
					Widget w = new Widget();
					w.setIcon("js/jitar/moduleicon/content.gif");
					if (category == null) {
						w.setTitle("所有图片");
					}
					else {
						w.setTitle(category.getTitle() + "下的所有图片");
					}

					fw.write(this.GetBlankWidget(w, ret));
					w = null;

					fw.write(rightBottom);
					fw.write(footer);

					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				file = new File(user_dir + "photo_" + cateId + "_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/html/html_photo_list.ftl",
							"utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

			}
			else {
				file = new File(user_dir + "photo_" + cateId + "_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/html/html_photo_list.ftl",
							"utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
			map.clear();
			map = null;
		}
		category = null;
	}

	public void GenUserCreateActionList(User user) {
		this.user = user;
		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		StringBuilder leftBar = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		leftBar.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		leftBar.append("<tr>");
		leftBar.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				leftBar.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		leftBar.append("</td>");
		leftBar.append("<td id='column_2' class='col'>");
		String left = leftBar.toString();
		String rightBottom = "</td></tr></table>";
		leftBar = null;

		String queryString = "";
		String whereClause = "act.createUserId=" + this.user.getUserId() + " And act.status >=0";

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "select count(*) as count from Action act Where " + whereClause;
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		int listCount = count == null?0:Integer.parseInt(count.toString());
		if (listCount == 0) {
			file = new File(user_dir + "action_0.html");
			try {
				fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				fw.write(header);
				fw.write(left);
				String content = "没有创建活动。";

				Widget w = new Widget();
				w.setIcon("js/jitar/moduleicon/content.gif");
				w.setTitle("我创建的活动");
				fw.write(this.GetBlankWidget(w, content));
				w = null;

				fw.write(rightBottom);
				fw.write(footer);
				fw.flush();
				fw.close();
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			return;
		}

		final int pageItem = 30;
		int pageCount = listCount % pageItem == 0 ? listCount / pageItem : listCount / pageItem + 1;

		for (int i = 0; i < pageCount; i++) {
			final int m = i;
			final String hql = "From Action act Where " + whereClause + " Order By act.actionId DESC";
			
					Query query = this.getSession().createQuery(hql);
					query.setFirstResult(m * pageItem);
					query.setMaxResults(pageItem);
					List list = query.list();
					

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("user", this.user);
			map.put("action_list", list);
			map.put("UserSiteUrl", this.userSiteUrl);
			map.put("pageCount", pageCount);

			if (i == 0) {
				file = new File(user_dir + "action_" + i + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					fw.write(header);
					fw.write(left);
					String ret = this.templateProcessor.processTemplate(map,
							"/WEB-INF/user/html/html_action_list_index.ftl", "utf-8");
					Widget w = new Widget();
					w.setIcon("js/jitar/moduleicon/content.gif");
					w.setTitle("我创建的活动");

					fw.write(this.GetBlankWidget(w, ret));
					w = null;

					fw.write(rightBottom);
					fw.write(footer);

					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				file = new File(user_dir + "action_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/html/html_action_list.ftl",
							"utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

			}
			else {
				file = new File(user_dir + "action_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map, "/WEB-INF/user/html/html_action_list.ftl",
							"utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
			map.clear();
			map = null;
		}

	}

	public void GenUserJoinedActionList(User user) {
		this.user = user;
		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		StringBuilder leftBar = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		leftBar.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		leftBar.append("<tr>");
		leftBar.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				leftBar.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		leftBar.append("</td>");
		leftBar.append("<td id='column_2' class='col'>");
		String left = leftBar.toString();
		String rightBottom = "</td></tr></table>";
		leftBar = null;

		String queryString = "";

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "select count(*) as count from ActionUser au Where au.isApprove = 1 And au.userId=" + this.user.getUserId();
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		int listCount = count == null?0:Integer.parseInt(count.toString());
		if (listCount == 0) {
			file = new File(user_dir + "joinedaction_0.html");
			try {
				fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				fw.write(header);
				fw.write(left);
				String content = "没有参与的活动。";

				Widget w = new Widget();
				w.setIcon("js/jitar/moduleicon/content.gif");
				w.setTitle("我参与的活动");
				fw.write(this.GetBlankWidget(w, content));
				w = null;

				fw.write(rightBottom);
				fw.write(footer);
				fw.flush();
				fw.close();
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			return;
		}

		final int pageItem = 30;
		int pageCount = listCount % pageItem == 0 ? listCount / pageItem : listCount / pageItem + 1;

		for (int i = 0; i < pageCount; i++) {
			final int m = i;
			final String hql = "SELECT new Map(act.title as title, act.createDate as createDate, act.actionId as actionId, act.ownerId as ownerId, act.ownerType as ownerType,act.userLimit as userLimit,act.startDateTime as startDateTime) FROM ActionUser as actu,Action as act Where act.status = 0 And actu.actionId = act.actionId And actu.isApprove = 1 And actu.userId = " + this.user.getUserId() + " And act.createUserId <> " + this.user.getUserId() + " Order By act.actionId DESC";
			
					Query query = this.getSession().createQuery(hql);
					query.setFirstResult(m * pageItem);
					query.setMaxResults(pageItem);
					List list = query.list();
					
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("user", this.user);
			map.put("action_list", list);
			map.put("UserSiteUrl", this.userSiteUrl);
			map.put("pageCount", pageCount);

			// article_cateId_0.html是首页，是一个完整的页面，其他页面则是只有内容部分。
			if (i == 0) {
				file = new File(user_dir + "joinedaction_" + i + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					fw.write(header);
					fw.write(left);
					String ret = this.templateProcessor.processTemplate(map,
							"/WEB-INF/user/html/html_joinedaction_list_index.ftl", "utf-8");
					Widget w = new Widget();
					w.setIcon("js/jitar/moduleicon/content.gif");
					w.setTitle("我创建的活动");

					fw.write(this.GetBlankWidget(w, ret));
					w = null;

					fw.write(rightBottom);
					fw.write(footer);

					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				file = new File(user_dir + "joinedaction_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,
							"/WEB-INF/user/html/html_joinedaction_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

			}
			else {
				file = new File(user_dir + "joinedaction_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,
							"/WEB-INF/user/html/html_joinedaction_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
			map.clear();
			map = null;
		}
	}

	public void GenUserCreatePrepareCourseList(User user) {
		this.user = user;
		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		StringBuilder leftBar = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		leftBar.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		leftBar.append("<tr>");
		leftBar.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				leftBar.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		leftBar.append("</td>");
		leftBar.append("<td id='column_2' class='col'>");
		String left = leftBar.toString();
		String rightBottom = "</td></tr></table>";
		leftBar = null;

		String queryString = "";
		String whereClause = "pc.status = 0 And pc.prepareCourseGenerated = true And pc.createUserId = " + this.user.getUserId();

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "select count(*) as count from PrepareCourse pc Where " + whereClause;
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		int listCount = count == null?0:Integer.parseInt(count.toString());
		if (listCount == 0) {
			file = new File(user_dir + "createpreparecourse_0.html");
			try {
				fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				fw.write(header);
				fw.write(left);
				String content = "发起的集备。";

				Widget w = new Widget();
				w.setIcon("js/jitar/moduleicon/content.gif");
				w.setTitle("我发起的集备");
				fw.write(this.GetBlankWidget(w, content));
				w = null;

				fw.write(rightBottom);
				fw.write(footer);
				fw.flush();
				fw.close();
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			return;
		}

		final int pageItem = 30;
		int pageCount = listCount % pageItem == 0 ? listCount / pageItem : listCount / pageItem + 1;

		for (int i = 0; i < pageCount; i++) {
			final int m = i;
			final String hql = "From PrepareCourse pc Where " + whereClause + " Order By pc.prepareCourseId DESC";
			
					Query query = this.getSession().createQuery(hql);
					query.setFirstResult(m * pageItem);
					query.setMaxResults(pageItem);
					List list = query.list();
					

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("user", this.user);
			map.put("course_list", list);
			map.put("UserSiteUrl", this.userSiteUrl);
			map.put("pageCount", pageCount);

			// article_cateId_0.html是首页，是一个完整的页面，其他页面则是只有内容部分。
			if (i == 0) {
				file = new File(user_dir + "createpreparecourse_" + i + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					fw.write(header);
					fw.write(left);
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_createpreparecourse_list_index.ftl", "utf-8");
					Widget w = new Widget();
					w.setIcon("js/jitar/moduleicon/content.gif");
					w.setTitle("我发起的集备");

					fw.write(this.GetBlankWidget(w, ret));
					w = null;

					fw.write(rightBottom);
					fw.write(footer);

					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				file = new File(user_dir + "createpreparecourse_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_createpreparecourse_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

			}
			else {
				file = new File(user_dir + "createpreparecourse_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_createpreparecourse_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
			map.clear();
			map = null;
		}

	}

	
	public void GenUserJoinedPrepareCourseList(User user) {
		this.user = user;
		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		StringBuilder leftBar = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		leftBar.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		leftBar.append("<tr>");
		leftBar.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				leftBar.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		leftBar.append("</td>");
		leftBar.append("<td id='column_2' class='col'>");
		String left = leftBar.toString();
		String rightBottom = "</td></tr></table>";
		leftBar = null;

		String queryString = "";

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "select count(*) as count from PrepareCourseMember as pcm Where pcm.status = 0 And pcm.userId = " + this.user.getUserId();
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		int listCount = count == null?0:Integer.parseInt(count.toString());
		if (listCount == 0) {
			file = new File(user_dir + "joinedpreparecourse_0.html");
			try {
				fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				fw.write(header);
				fw.write(left);
				String content = "参与的集备。";

				Widget w = new Widget();
				w.setIcon("js/jitar/moduleicon/content.gif");
				w.setTitle("我参与的集备");
				fw.write(this.GetBlankWidget(w, content));
				w = null;

				fw.write(rightBottom);
				fw.write(footer);
				fw.flush();
				fw.close();
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			return;
		}

		final int pageItem = 30;
		int pageCount = listCount % pageItem == 0 ? listCount / pageItem : listCount / pageItem + 1;

		for (int i = 0; i < pageCount; i++) {
			final int m = i;
			final String hql = "SELECT new Map( pc.prepareCourseId as prepareCourseId,pc.title as title,pc.createDate as createDate,pc.memberCount as memberCount,pc.articleCount as articleCount,pc.resourceCount as resourceCount,pc.actionCount as actionCount,pc.topicCount as topicCount,pc.topicReplyCount as topicReplyCount ) FROM PrepareCourse as pc,PrepareCourseMember as pcm Where pcm.status=0 And pcm.prepareCourseId = pc.prepareCourseId And pcm.userId="+this.user.getUserId()+" Order By pcm.prepareCourseMemberId DESC";
			
					Query query = this.getSession().createQuery(hql);
					query.setFirstResult(m * pageItem);
					query.setMaxResults(pageItem);
					List list = query.list();
					

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("user", this.user);
			map.put("course_list", list);
			map.put("UserSiteUrl", this.userSiteUrl);
			map.put("pageCount", pageCount);

			// article_cateId_0.html是首页，是一个完整的页面，其他页面则是只有内容部分。
			if (i == 0) {
				file = new File(user_dir + "joinedpreparecourse_" + i + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					fw.write(header);
					fw.write(left);
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_joinedpreparecourse_list_index.ftl", "utf-8");
					Widget w = new Widget();
					w.setIcon("js/jitar/moduleicon/content.gif");
					w.setTitle("我参与的集备");

					fw.write(this.GetBlankWidget(w, ret));
					w = null;

					fw.write(rightBottom);
					fw.write(footer);

					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				file = new File(user_dir + "joinedpreparecourse_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_joinedpreparecourse_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

			}
			else {
				file = new File(user_dir + "joinedpreparecourse_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_joinedpreparecourse_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
			map.clear();
			map = null;
		}

	}

	public void GenUserQuestionList(User user) {
		this.user = user;
		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		StringBuilder leftBar = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		leftBar.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		leftBar.append("<tr>");
		leftBar.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				leftBar.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		leftBar.append("</td>");
		leftBar.append("<td id='column_2' class='col'>");
		String left = leftBar.toString();
		String rightBottom = "</td></tr></table>";
		leftBar = null;

		String queryString = "";
		String whereClause = "q.parentGuid = '" + this.user.getUserGuid() + "'";

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "select count(*) as count from Question q Where " + whereClause;
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		int listCount = count == null?0:Integer.parseInt(count.toString());
		if (listCount == 0) {
			file = new File(user_dir + "question_0.html");
			try {
				fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				fw.write(header);
				fw.write(left);
				String content = "问题。";

				Widget w = new Widget();
				w.setIcon("js/jitar/moduleicon/content.gif");
				w.setTitle("问题");
				fw.write(this.GetBlankWidget(w, content));
				w = null;

				fw.write(rightBottom);
				fw.write(footer);
				fw.flush();
				fw.close();
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			return;
		}

		final int pageItem = 30;
		int pageCount = listCount % pageItem == 0 ? listCount / pageItem : listCount / pageItem + 1;

		for (int i = 0; i < pageCount; i++) {
			final int m = i;
			final String hql = "From Question q Where " + whereClause + " Order By q.questionId DESC";
			
					Query query = this.getSession().createQuery(hql);
					query.setFirstResult(m * pageItem);
					query.setMaxResults(pageItem);
					List list = query.list();
					

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("user", this.user);
			map.put("q_list", list);
			map.put("UserSiteUrl", this.userSiteUrl);
			map.put("pageCount", pageCount);
			map.put("parentGuid", this.user.getUserGuid());
			map.put("parentType", "user");

			// article_cateId_0.html是首页，是一个完整的页面，其他页面则是只有内容部分。
			if (i == 0) {
				file = new File(user_dir + "question_" + i + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					fw.write(header);
					fw.write(left);
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_question_list_index.ftl", "utf-8");
					Widget w = new Widget();
					w.setIcon("js/jitar/moduleicon/content.gif");
					w.setTitle("问题与解答");

					fw.write(this.GetBlankWidget(w, ret));
					w = null;

					fw.write(rightBottom);
					fw.write(footer);

					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				file = new File(user_dir + "question_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_question_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

			}
			else {
				file = new File(user_dir + "question_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_question_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
			map.clear();
			map = null;
		}

	}

	
	public void GenUserVoteList(User user) {
		this.user = user;
		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		StringBuilder leftBar = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		leftBar.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		leftBar.append("<tr>");
		leftBar.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				leftBar.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		leftBar.append("</td>");
		leftBar.append("<td id='column_2' class='col'>");
		String left = leftBar.toString();
		String rightBottom = "</td></tr></table>";
		leftBar = null;

		String queryString = "";
		String whereClause = "v.parentGuid = '" + this.user.getUserGuid() + "'";

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "select count(*) as count from Vote v Where " + whereClause;
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		int listCount = count == null?0:Integer.parseInt(count.toString());
		if (listCount == 0) {
			file = new File(user_dir + "vote_0.html");
			try {
				fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				fw.write(header);
				fw.write(left);
				String content = "调查投票。";

				Widget w = new Widget();
				w.setIcon("js/jitar/moduleicon/content.gif");
				w.setTitle("调查投票");
				fw.write(this.GetBlankWidget(w, content));
				w = null;

				fw.write(rightBottom);
				fw.write(footer);
				fw.flush();
				fw.close();
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			return;
		}

		final int pageItem = 30;
		int pageCount = listCount % pageItem == 0 ? listCount / pageItem : listCount / pageItem + 1;

		for (int i = 0; i < pageCount; i++) {
			final int m = i;
			final String hql = "From Vote v Where " + whereClause + " Order By v.voteId DESC";
			
					Query query = this.getSession().createQuery(hql);
					query.setFirstResult(m * pageItem);
					query.setMaxResults(pageItem);
					List list = query.list();
				

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("user", this.user);
			map.put("vote_list", list);
			map.put("UserSiteUrl", this.userSiteUrl);
			map.put("pageCount", pageCount);
			map.put("parentGuid", this.user.getUserGuid());
			map.put("parentType", "user");
			

			// article_cateId_0.html是首页，是一个完整的页面，其他页面则是只有内容部分。
			if (i == 0) {
				file = new File(user_dir + "vote_" + i + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					fw.write(header);
					fw.write(left);
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_vote_list_index.ftl", "utf-8");
					Widget w = new Widget();
					w.setIcon("js/jitar/moduleicon/content.gif");
					w.setTitle("调查投票");

					fw.write(this.GetBlankWidget(w, ret));
					w = null;

					fw.write(rightBottom);
					fw.write(footer);

					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				file = new File(user_dir + "vote_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_vote_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

			}
			else {
				file = new File(user_dir + "vote_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_vote_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
			map.clear();
			map = null;
		}

	}

	
	public void GenUserGroupList(User user) {
		this.user = user;
		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		StringBuilder leftBar = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		leftBar.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		leftBar.append("<tr>");
		leftBar.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				leftBar.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		leftBar.append("</td>");
		leftBar.append("<td id='column_2' class='col'>");
		String left = leftBar.toString();
		String rightBottom = "</td></tr></table>";
		leftBar = null;

		String queryString = "";
		

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "select count(*) as count from GroupMember gm Where gm.status=0 And gm.userId=" + this.user.getUserId();
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		int listCount = count == null?0:Integer.parseInt(count.toString());
		if (listCount == 0) {
			file = new File(user_dir + "group_0.html");
			try {
				fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				fw.write(header);
				fw.write(left);
				String content = "加入的协作组。";

				Widget w = new Widget();
				w.setIcon("js/jitar/moduleicon/content.gif");
				w.setTitle("加入的协作组");
				fw.write(this.GetBlankWidget(w, content));
				w = null;

				fw.write(rightBottom);
				fw.write(footer);
				fw.flush();
				fw.close();
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			return;
		}

		final int pageItem = 30;
		int pageCount = listCount % pageItem == 0 ? listCount / pageItem : listCount / pageItem + 1;

		for (int i = 0; i < pageCount; i++) {
			final int m = i;
			final String hql = "select new Map(g.groupName as groupName,g.groupTitle as groupTitle,g.userCount as userCount,g.articleCount as articleCount,g.topicCount as topicCount,g.resourceCount as resourceCount,g.visitCount as visitCount) From Group g,GroupMember gm Where g.groupId=gm.groupId And gm.status=0 And gm.userId=" + this.user.getUserId() + " Order By gm.id DESC";
			
					Query query = this.getSession().createQuery(hql);
					query.setFirstResult(m * pageItem);
					query.setMaxResults(pageItem);
					List list = query.list();
					

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("user", this.user);
			map.put("group_list", list);
			map.put("UserSiteUrl", this.userSiteUrl);
			map.put("pageCount", pageCount);
			map.put("parentGuid", this.user.getUserGuid());
			

			// article_cateId_0.html是首页，是一个完整的页面，其他页面则是只有内容部分。
			if (i == 0) {
				file = new File(user_dir + "group_" + i + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					fw.write(header);
					fw.write(left);
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_group_list_index.ftl", "utf-8");
					Widget w = new Widget();
					w.setIcon("js/jitar/moduleicon/content.gif");
					w.setTitle("加入的协作组");

					fw.write(this.GetBlankWidget(w, ret));
					w = null;

					fw.write(rightBottom);
					fw.write(footer);

					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				file = new File(user_dir + "group_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_group_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

			}
			else {
				file = new File(user_dir + "group_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_group_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
			map.clear();
			map = null;
		}

	}

	
	public void GenUserLeaveWordList(User user) {
		
		this.user = user;
		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		StringBuilder leftBar = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		leftBar.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		leftBar.append("<tr>");
		leftBar.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				leftBar.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		leftBar.append("</td>");
		leftBar.append("<td id='column_2' class='col'>");
		String left = leftBar.toString();
		String rightBottom = "</td></tr></table>";
		leftBar = null;

		String queryString = "";
		String whereClause = "lwd.objType = 1 And lwd.objId = " + this.user.getUserId();

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "select count(*) as count from LeaveWord lwd Where " + whereClause;
		Object count = this.getSession().createQuery(queryString).uniqueResult();
		int listCount = count == null?0: Integer.parseInt(count.toString());
		if (listCount == 0) {
			file = new File(user_dir + "leaveword_0.html");
			try {
				fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				fw.write(header);
				fw.write(left);
				String content = "访客留言。";

				Widget w = new Widget();
				w.setIcon("js/jitar/moduleicon/content.gif");
				w.setTitle("访客留言");
				fw.write(this.GetBlankWidget(w, content));
				w = null;

				fw.write(rightBottom);
				fw.write(footer);
				fw.flush();
				fw.close();
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			return;
		}

		final int pageItem = 30;
		int pageCount = listCount % pageItem == 0 ? listCount / pageItem : listCount / pageItem + 1;

		for (int i = 0; i < pageCount; i++) {
			final int m = i;
			final String hql = "From LeaveWord lwd Where " + whereClause + " Order By lwd.id DESC";
			
					Query query = this.getSession().createQuery(hql);
					query.setFirstResult(m * pageItem);
					query.setMaxResults(pageItem);
					List list = query.list();
					

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("user", this.user);
			map.put("user_leaveword_list", list);
			map.put("UserSiteUrl", this.userSiteUrl);
			map.put("pageCount", pageCount);
			

			// article_cateId_0.html是首页，是一个完整的页面，其他页面则是只有内容部分。
			if (i == 0) {
				file = new File(user_dir + "leaveword_" + i + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					fw.write(header);
					fw.write(left);
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_leaveword_list_index.ftl", "utf-8");
					Widget w = new Widget();
					w.setIcon("js/jitar/moduleicon/content.gif");
					w.setTitle("访客留言");

					fw.write(this.GetBlankWidget(w, ret));
					w = null;

					fw.write(rightBottom);
					fw.write(footer);

					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				file = new File(user_dir + "leaveword_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_leaveword_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

			}
			else {
				file = new File(user_dir + "leaveword_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_leaveword_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
			map.clear();
			map = null;
		}
	}

	
	public void GenUserFriendList(User user) {
		this.user = user;
		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		StringBuilder leftBar = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		leftBar.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		leftBar.append("<tr>");
		leftBar.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				leftBar.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		leftBar.append("</td>");
		leftBar.append("<td id='column_2' class='col'>");
		String left = leftBar.toString();
		String rightBottom = "</td></tr></table>";
		leftBar = null;

		String queryString = "";
		String whereClause = "fl.userId = " + this.user.getUserId() + " And fl.isBlack = false";

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "select count(*) as count from Friend fl Where " + whereClause;
		Object o = this.getSession().createQuery(queryString).uniqueResult();

		int listCount = o == null?0:Integer.parseInt(o.toString());
		if (listCount == 0) {
			file = new File(user_dir + "friendlink_0.html");
			try {
				fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				fw.write(header);
				fw.write(left);
				String content = "暂时没有好友列表。";

				Widget w = new Widget();
				w.setIcon("js/jitar/moduleicon/content.gif");
				w.setTitle("好友列表");
				fw.write(this.GetBlankWidget(w, content));
				w = null;

				fw.write(rightBottom);
				fw.write(footer);
				fw.flush();
				fw.close();
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			return;
		}

		final int pageItem = 30;
		int pageCount = listCount % pageItem == 0 ? listCount / pageItem : listCount / pageItem + 1;

		for (int i = 0; i < pageCount; i++) {
			final int m = i;
			final String hql = "select new Map(fd.addTime as addTime,u.loginName as loginName, u.nickName as nickName, u.trueName as trueName, u.userIcon as userIcon, u.qq as qq) From Friend fd,User u Where fd.friendId = u.userId And fd.userId=" + this.user.getUserId() + " Order By fd.addTime DESC";
			
			Query query = this.getSession().createQuery(hql);
			query.setFirstResult(m * pageItem);
			query.setMaxResults(pageItem);
			List list = query.list();			
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("user", this.user);
			map.put("friend_list", list);
			map.put("UserSiteUrl", this.userSiteUrl);
			map.put("pageCount", pageCount);

			// article_cateId_0.html是首页，是一个完整的页面，其他页面则是只有内容部分。
			if (i == 0) {
				file = new File(user_dir + "friendlink_" + i + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					fw.write(header);
					fw.write(left);
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_friendlink_list_index.ftl", "utf-8");
					Widget w = new Widget();
					w.setIcon("js/jitar/moduleicon/content.gif");
					w.setTitle("好友列表");

					fw.write(this.GetBlankWidget(w, ret));
					w = null;

					fw.write(rightBottom);
					fw.write(footer);

					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				file = new File(user_dir + "friendlink_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_friendlink_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

			}
			else {
				file = new File(user_dir + "friendlink_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_friendlink_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
			map.clear();
			map = null;
		}

	}

	
	public void GenUserSpecialTopic(User user) {
		this.user = user;
		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			System.out.println("无法得到网站路径信息，请与程序开发人员联系。");
			return;
		}

		StringBuilder leftBar = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		leftBar.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		leftBar.append("<tr>");
		leftBar.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				leftBar.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		leftBar.append("</td>");
		leftBar.append("<td id='column_2' class='col'>");
		String left = leftBar.toString();
		String rightBottom = "</td></tr></table>";
		leftBar = null;

		String queryString = "";
		String whereClause = "pt.parentGuid = '" + this.user.getUserGuid() + "'";

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "select count(*) as count from PlugInTopic pt Where " + whereClause;
		Object o = this.getSession().createQuery(queryString).uniqueResult();

		int listCount = o == null?0:Integer.parseInt(o.toString());
		if (listCount == 0) {
			file = new File(user_dir + "topic_0.html");
			try {
				fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				fw.write(header);
				fw.write(left);
				String content = "暂时没有话题讨论。";

				Widget w = new Widget();
				w.setIcon("js/jitar/moduleicon/content.gif");
				w.setTitle("话题讨论");
				fw.write(this.GetBlankWidget(w, content));
				w = null;

				fw.write(rightBottom);
				fw.write(footer);
				fw.flush();
				fw.close();
			}
			catch (IOException ex) {
				System.out.println(ex.getMessage());
				return;
			}
			return;
		}

		final int pageItem = 30;
		int pageCount = listCount % pageItem == 0 ? listCount / pageItem : listCount / pageItem + 1;

		for (int i = 0; i < pageCount; i++) {
			final int m = i;
			final String hql = "From PlugInTopic pt Where " + whereClause + " Order By pt.plugInTopicId DESC";
			
			Query query = this.getSession().createQuery(hql);
			query.setFirstResult(m * pageItem);
			query.setMaxResults(pageItem);
			List list = query.list();
			
				

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("user", this.user);
			map.put("topic_list", list);
			map.put("UserSiteUrl", this.userSiteUrl);
			map.put("pageCount", pageCount);
			map.put("parentGuid", this.user.getUserGuid());
			map.put("parentType", "user");

			// article_cateId_0.html是首页，是一个完整的页面，其他页面则是只有内容部分。
			if (i == 0) {
				file = new File(user_dir + "topic_" + i + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					fw.write(header);
					fw.write(left);
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_topic_list_index.ftl", "utf-8");
					Widget w = new Widget();
					w.setIcon("js/jitar/moduleicon/content.gif");
					w.setTitle("话题讨论");

					fw.write(this.GetBlankWidget(w, ret));
					w = null;

					fw.write(rightBottom);
					fw.write(footer);

					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

				file = new File(user_dir + "topic_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_topic_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}

			}
			else {
				file = new File(user_dir + "topic_" + (i + 1) + ".html");
				try {
					fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
					String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_topic_list.ftl", "utf-8");
					fw.write(ret);
					fw.flush();
					fw.close();
				}
				catch (IOException ex) {
					System.out.println(ex.getMessage());
					return;
				}
			}
			map.clear();
			map = null;
		}
	}
	
	//由于文章的动态属性太多，暂时不对文章进行静态化
	public void GenUserArticle(User user, Article article) throws Exception
	{
		Article preArticle = null, nextArticle = null;
		if(article == null) return;
		this.user = user;
		// 得到当前用户的文件存放目录（物理文件夹），应该是/html/user/admin/
		String user_dir = this.getUserHtmlFolder();
		if (user_dir == null) {
			throw new Exception(" 无法得到网站路径信息，请与程序开发人员联系。");
		}

		
		StringBuilder leftBar = new StringBuilder();
		this.layoutId = 2; // 其他页面都是2列布局，25%-75%
		String header = this.GenUserHeader(user);
		String footer = this.GenUserHtmlFoot();
		// 生成layout2的框架界面
		leftBar.append("<table id='container' border='0' cellpadding='0' cellspacing='0'>");
		leftBar.append("<tr>");
		leftBar.append("<td id='column_1' class='col'>");
		// 左边导航
		List<Widget> wp = this.pageService.getPageWidgets(this.page.getPageId());
		// 列出个人档案信息
		for (int i = 0; i < wp.size(); i++) {
			Widget w = (Widget) wp.get(i);
			if (w.getModule().equalsIgnoreCase("profile")) {
				leftBar.append(this.getWidgetWindow(w));
			}else if (w.getModule().equalsIgnoreCase("user_cate")) {
				leftBar.append(this.getWidgetWindow(w));
			}
		}
		wp.clear();
		wp = null;

		leftBar.append("</td>");
		leftBar.append("<td id='column_2' class='col'>");
		String left = leftBar.toString();
		String rightBottom = "</td></tr></table>";
		leftBar = null;
		
		//下面查询文章的相关信息
		//个人分类
		Category UserCate = null, SysCate = null;
		if(article.getUserCateId() != null)
		{
			UserCate = this.categoryService.getCategory(article.getUserCateId());
		}
		if(article.getSysCateId() != null)
		{
			SysCate = this.categoryService.getCategory(article.getSysCateId());
		}
			
		
		String queryString = "";
		String whereClause = "a.userId="
				+ this.user.getUserId()
				+ " And a.auditState = 0 And a.hideState = 0 And a.draftState = false And a.delState = false  ";

		File file = null;
		OutputStreamWriter fw = null;
		queryString = "From Article a Where " + whereClause + " And a.articleId < " + article.getArticleId();
		List articleList = this.getSession().createQuery(queryString).setFirstResult(0).setMaxResults(1).list() ; 
		
		
		if (articleList.size() > 0) {
			preArticle = ((Article)articleList.get(0));
		}
		
		queryString = "From Article a Where " + whereClause + " And a.articleId > " + article.getArticleId();
		articleList = this.getSession().createQuery(queryString).setFirstResult(0).setMaxResults(1).list() ; 
		if (articleList.size() >0) {
			nextArticle = ((Article)articleList.get(0));
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user", this.user);		
		map.put("article", article);		
		map.put("UserSiteUrl", this.userSiteUrl);
		map.put("next_article", nextArticle);
		map.put("prev_article", preArticle);
		map.put("UserCate", UserCate);
		map.put("SysCate", SysCate);
		map.put("UserSiteUrl", this.userSiteUrl);
		map.put("similarArticleList", this.getSimilarArticle(article));
		Hashtable<String,Long> h = this.getCommentStar(article);
		if(h.containsKey("AveStar"))
		{
			map.put("AveStar", h.get("AveStar"));
			map.put("StarNumber", h.get("StarNumber"));
			map.put("StarCount", h.get("StarCount"));
		}
		
		//判断是否允许匿名访问文章内容
		Boolean needLoginShow = this.configService.getConfigure().getBoolValue("user.site.article_show");
		map.put("needLoginShow", needLoginShow);
		file = new File(user_dir + "article_" + article.getArticleId() + ".html");
		try {
			fw = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			fw.write(header);
			fw.write(left);
			String ret = this.templateProcessor.processTemplate(map,"/WEB-INF/user/html/html_article.ftl", "utf-8");
			Widget w = new Widget();
			w.setIcon("js/jitar/moduleicon/content.gif");
			w.setTitle("文章内容");

			fw.write(this.GetBlankWidget(w, ret));
			w = null;

			fw.write(rightBottom);
			fw.write(footer);

			fw.flush();
			fw.close();
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
			return;
		}

		map.clear();
		map = null;

	}
	public List<Article> getSimilarArticle(Article article)
	{
		//选择10个
		String tags = article.getArticleTags();
		if(tags==null || tags.equals(""))
		{
			return null;
		}
		tags = tags.replaceAll("'", "").replaceAll(" ", ",").replaceAll(";", "");
		String queryString;
		String whereClause = "a.auditState = 0 And a.hideState = 0 And a.draftState = false And a.delState = false ";
		queryString = "From Article a Where " + whereClause + " And a.articleTags='" + tags + "'";
		List<Article> articleList1 = (List<Article>)this.getSession().createQuery(queryString).setFirstResult(0).setMaxResults(10).list() ;
		if(articleList1.size() == 10) return articleList1;
		String[] myArray = tags.split(",");
		String tagQuery  = "";
		for(int i = 0;i<myArray.length;i++)
		{
			tagQuery += " t.tagName = '" + myArray[i] + "' Or";
		}
		if(tagQuery.endsWith("Or"))
		{
			tagQuery = tagQuery.substring(0, tagQuery.length()-3);
		}
		if(tagQuery.length() > 1)
		{
			tagQuery = "t.disabled = false And (" + tagQuery + ")";
		}
		List tagRef = this.getSession().createQuery("From Tag t Where " + tagQuery + " Order By t.refCount DESC").list();
		String tagIDs = "";
		for(int i=0;i<tagRef.size();i++)
		{
			Tag t = (Tag)tagRef.get(i);
			tagIDs += t.getTagId() + ",";
		}
		if(tagIDs.endsWith(","))
		{
			tagIDs = tagIDs.substring(0, tagIDs.length() - 1);
		}
		if(tagIDs.equals("")) return articleList1;
		tagQuery = "tr.objectType = 3 And tr.tagId IN(" + tagIDs + ")";
		List tagRefList = this.getSession().createQuery("select tr.objectId From TagRef tr Where " + tagQuery + " Order By tr.orderNum ASC").setFirstResult(0).setMaxResults(10).list();
		String articleIds = "";
		int tagLength = tagRefList.size();
		for(int i = 0;i<tagLength;i++)
		{
			String aid = tagRefList.get(i).toString();			
			//TagRef tr = (TagRef)tagRefList.get(i);
			articleIds += aid + ",";
		}

		if(articleIds.endsWith(","))
		{
			articleIds = articleIds.substring(0, articleIds.length()-1);
		}

		List<Article> articleList2 = (List<Article>)this.getSession().createQuery("From Article a Where " + whereClause + " And a.articleId In (" + articleIds.replaceAll(" ", "") + ")").setFirstResult(0).setMaxResults(10-articleList1.size()).list();
		for(Article a : articleList2)
		{
			articleList1.add(a);
		}
		return articleList1;
	}
	
	private Hashtable getCommentStar(Article article)
	{
		Long StarCount=0L, StarNumber=0L, AveStar=0L;
		String sql = "SELECT SUM(star) AS StarCount FROM Comment WHERE objId = :articleId";
		Command cmd = new Command(sql);
		cmd.setInteger("articleId", article.getArticleId());
		Object commentStar = cmd.first();
		if(commentStar != null)
		{
			StarCount = (Long)commentStar;
		}
		sql = "SELECT COUNT(star) AS StarNumber FROM Comment WHERE objId = :articleId";
		cmd = new Command(sql);
		cmd.setInteger("articleId", article.getArticleId());
		commentStar = cmd.first();
		if(commentStar != null)
		{
			StarNumber = (Long)commentStar;
		}
		if(StarNumber > 0)
		{
			AveStar = StarCount / StarNumber;
		}
		
		Hashtable<String,Long> r = new Hashtable<String,Long>();
		r.put("StarCount", StarCount);
		r.put("StarNumber", StarNumber);
		r.put("AveStar", AveStar);
		return r;
	}
	
	
	//测试
	public List<ArticleUser> getArticleUser(final int userId)
	{
		Query query = this.getSession().getNamedQuery("findUserArticle");
		query.setInteger(0, userId);
		return query.list();
	}

	@Override
	public void saveOrUpdate(HtmlTimer htmlTimer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HtmlTimer getHtmlTimer(int objectId, int objectType) {
		// TODO Auto-generated method stub
		return null;
	}
}