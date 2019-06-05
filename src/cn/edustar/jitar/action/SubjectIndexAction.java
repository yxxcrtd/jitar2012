package cn.edustar.jitar.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.SiteLinks;
import cn.edustar.jitar.pojos.SiteNav;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.SubjectWebpart;
import cn.edustar.jitar.service.ActionQuery;
import cn.edustar.jitar.service.ArticleQuery;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.GroupQuery;
import cn.edustar.jitar.service.PlacardQuery;
import cn.edustar.jitar.service.ResourceQuery;
import cn.edustar.jitar.service.SiteLinksService;
import cn.edustar.jitar.service.SiteNavService;
import cn.edustar.jitar.service.SiteNewsQuery;
import cn.edustar.jitar.service.SpecialSubjectQuery;
import cn.edustar.jitar.service.StatService;
import cn.edustar.jitar.service.TemplateProcessor;
import cn.edustar.jitar.service.TimerCountService;
import cn.edustar.jitar.service.VideoQuery;
import cn.edustar.jitar.service.impl.NoCache;

/**
 * 学科首页
 * @author renliang
 */
@SuppressWarnings("serial")
public class SubjectIndexAction extends AbstractBasePageAction {

	private BaseSubject baseSubject = null;
	private TemplateProcessor templateProcessor = null;
	private SiteLinksService siteLinksService = null;
	private StatService statService = null;
	private String cacheKeyFix = "";
	private Subject subject = null;
	private CacheService cache = null;
	private TimerCountService timerCountService = null;
	private String templateName = null;
	private SiteNavService siteNavService = null;
	private boolean ENABLE_CACHE = false; 
	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		subject = baseSubject.getSubject();
		
		if(ENABLE_CACHE){
			cache = JitarContext.getCurrentJitarContext().getCacheProvider().getCache("subject");
		}else{
			cache = new NoCache();
		}
		
		if (subject == null) {
			addActionError("Object not be found !");
			return ERROR;
		}
		Object cacheCount = cache.get("cacheCount");
		if (cacheCount == null) {
			timerCountService.doSubjectCount(subject);
			subjectService.clearCacheData();
			subject = subjectService.getSubjectById(subject.getSubjectId());
		}

		String shortcutTarget = subject.getShortcutTarget();
		if (shortcutTarget != null) {
			response.sendRedirect(shortcutTarget);
			return null;
		}

		if (baseSubject.getUnitId()!= null && baseSubject.getUnitId() != 0) {
			cacheKeyFix = "_" + baseSubject.getUnitId();
		}
		templateName = subject.getTemplateName();
		if (templateName == null || templateName == "") {
			templateName = "template1";
		}
		String theme = params.safeGetStringParam("theme");
		if (!"".equals(theme.trim())) {
			request.setAttribute("theme", theme);
		}
		List<SubjectWebpart> webpartList = subjectService
				.getSubjectWebpartList(subject.getSubjectId(), true);
		if (!params.existParam("tm")) {
			if (webpartList.size() < 1) {
				genWebparts();
				addSubjectNav();
				response.sendRedirect("?tm=1");
				return null;
			}
		}
		for (SubjectWebpart webpart : webpartList) {
			set_webpart_flag(webpart);
			if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_ARTICLE)) {
				genernate_article_content(webpart);
			} else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_RESOURCE)) {
				genernate_resoure_content(webpart);
			} else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_JIAOYANSHIPIN)) {
				genernate_jiaoyanshipin_content(webpart);
			} else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_PICNEWS)) {
				genernate_picnews_content(webpart);
			} else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_NEWS)) {
				genernate_news_content(webpart);
			} else if (webpart.getModuleName().trim().trim().equals(SubjectWebpart.WEBPART_MODULENAME_NOTICE)) {
				genernate_notice_content(webpart);
			} else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_LINKS)) {
				genernate_links_content(webpart);
			}
			else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_STATISTICS)) {
				genernate_statistics_content(webpart);
			} else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_VOTE)) {
				genernate_vote_content(webpart);
			} else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_JIAOYANYUAN)) {
				genernate_jiaoyanyuan_content(webpart);
			} else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_MINGSHI)) {
				genernate_mingshi_content(webpart);
			} else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_NODULENAME_DAITOUREN)) {
				genernate_daitouren_content(webpart);
			}
			else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_GONGZUOSHI)) {
				genernate_gongzuoshi_content(webpart);
			} else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_XIEZUOZU)) {
				genernate_xiezuozu_content(webpart);
			} else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_JIAOYANHUODONG)) {
				genernate_jiaoyanhuodong_content(webpart);
			} else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_JIAOYANZHUANTI)) {
				genernate_jiaoyanzhuanti_content(webpart);
			}
			else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_WENDA)) {
				genernate_wenda_content(webpart);
			} else if (webpart.getModuleName().trim().equals(SubjectWebpart.WEBPART_MODULENAME_TOPIC)) {
				genernate_topic_content(webpart);
			} else {
				String cache_key = "sbj" + subject.getSubjectId() + "_"
						+ webpart.getSubjectWebpartId() + cacheKeyFix;
				Object content = cache.get(cache_key);
				if (content != null) {
					request.setAttribute(cache_key, content);
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("subject", subject.getSubjectId());
					map.put("webpart", webpart);
					map.put("unitId", baseSubject.getUnitId());
					map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
					content = templateProcessor.processTemplate(map,
							"/WEB-INF/subjectpage/" + templateName
									+ "/custorm.ftl", "utf-8");
					request.setAttribute(cache_key, content);
					cache.put(cache_key, content);
				}

			}

		}

		theme = params.safeGetStringParam("theme");
		request.setAttribute("head_nav", "subject");
		request.setAttribute("subject", subject);
		request.setAttribute("webpartList", webpartList);
		if (!"".equals(theme)) {
			request.setAttribute("theme", theme);
		}
		if (super.getLoginUser() != null) {
			request.setAttribute("loginUser", super.getLoginUser());
			String preview = params.safeGetStringParam("preview");
			if (baseSubject.isAdmin() && !"".equals(preview)) {
				request.setAttribute("role", "admin");
			}
		}

		request.setAttribute("req", request);
		request.setAttribute("unitId", baseSubject.getUnitId());
		return templateName;
	}

	private void genernate_topic_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
		        
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("subject", subject);
        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/topic.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);
	}

	private void genernate_wenda_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
		        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("subject", subject);
        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/wenda.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);

	}

	private void genernate_jiaoyanzhuanti_content(SubjectWebpart webpart) {
		
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
			        
        Map<String,Object> map =new HashMap<String,Object>();
        SpecialSubjectQuery qry =new SpecialSubjectQuery("ss.specialSubjectId, ss.logo, ss.title,ss.createUserId, ss.createDate,ss.expiresDate");
        qry.setObjectId(subject.getSubjectId());
        qry.setObjectType("subject");
        map.put("ss_list", qry.query_map(8));
        map.put("subject", subject);
        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/jiaoyanzhuanti.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);
	}

	private void genernate_jiaoyanhuodong_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
            		        
        Map<String,Object> map =new HashMap<String,Object>();
        ActionQuery qry =new ActionQuery("act.title, act.createDate, act.actionId, act.ownerId, act.ownerType, act.createUserId, act.actionType,act.description, act.userLimit, act.startDateTime,act.finishDateTime, act.attendLimitDateTime, act.place,act.status, act.visibility, act.attendCount");
        qry.setOwnerType("subject");
        qry.setOwnerId(subject.getSubjectId());
        map.put("action_list", qry.query_map(8));        
        map.put("subject", subject);
        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/jiaoyanhuodong.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);
	}

	private void genernate_xiezuozu_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
		        
        Map<String,Object>map =new HashMap<String,Object>();
        GroupQuery qry =new GroupQuery("g.groupName,g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce");
        qry.setSubjectId(baseSubject.getMetaSubjectId());
        qry.setGradeId(baseSubject.getMetaGradeId());
        map.put("new_group_list", qry.query_map(4));
        
        GroupQuery qry1 =new  GroupQuery("g.groupName,g.groupIcon, g.createDate, g.groupId, g.groupTitle, g.groupIntroduce");
        qry.setSubjectId(baseSubject.getMetaSubjectId());
        qry.setGradeId(baseSubject.getMetaGradeId());
        qry.setOrderType(8);
        map.put("hot_group_list", qry.query_map(4));
        GroupQuery Qry2 =new GroupQuery("g.groupName,g.groupId, g.groupIcon, g.groupTitle, g.createDate, g.groupIntroduce");
        qry.setSubjectId(baseSubject.getMetaSubjectId());
        qry.setGradeId(baseSubject.getMetaGradeId());
        qry.setIsRecommend(true);
        map.put("rcmd_group_list", qry.query_map(4));   
        map.put("subject", subject);
        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/xiezuozu.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);

	}

	private void genernate_gongzuoshi_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("new_blog_list",baseSubject.getNewList(4));
        map.put("hot_blog_list", baseSubject.getHotList(4));        
        map.put("rcmd_blog_list",baseSubject.getRcmdList(4));
        map.put("subject", subject);
        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/gongzuoshi.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);
        //#print "qry.metaSubjectId:", qry.metaSubjectId
        //#print "qry.metaGradeId:", qry.metaGradeId
        statService.subjectStat(baseSubject.getMetaSubjectId(), baseSubject.getMetaGradeId(), baseSubject.getMetaGradeId()+ 1000);
	}

	private void genernate_daitouren_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
        Map<String,Object> map =new HashMap<String,Object>();        
        map.put("expert_user_list", baseSubject.getExpertList());
        map.put("subject", subject);
        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/daitouren.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);
	}

	private void genernate_mingshi_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
        Map<String,Object> map =new HashMap<String,Object>();        
        map.put("famous_user_list", baseSubject.getFamousList());
        map.put("subject", subject);
        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/mingshi.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);

	}

	private void genernate_jiaoyanyuan_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
        Map<String,Object>map =new HashMap<String,Object>();
        map.put("jiaoyanyuan", baseSubject.getSubjectComissioner());
        map.put("subject", subject);
        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/jiaoyanyuan.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);

	}

	private void genernate_vote_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
        Map<String,Object> map =new HashMap<String,Object>();
        map.put("subject", subject);
        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/vote.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);

	}

	private void genernate_statistics_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
	    Object content = cache.get(cache_key);
	    if (content != null){
	    	request.setAttribute(cache_key, content);
	        return;
	    }
	    //重新进行统计        
	    Map<String,Object>map =new HashMap<String,Object>();
	    map.put("subject", subject);
	    map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
	    map.put("webpart", webpart);
	    map.put("unitId", baseSubject.getUnitId());
	    content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/count.ftl", "utf-8");
	    request.setAttribute(cache_key, content);
	    cache.put(cache_key, content);
	}

	private void genernate_links_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
        Map<String,Object> map =new HashMap<String,Object>();
        //得到全部的友情链接
        List<SiteLinks> links = siteLinksService.getSiteLinksList("subject", subject.getSubjectId());
        if (links != null){
        	map.put("links", links);
        }
        map.put("subject", subject);
        map.put("SubjectRootUrl",baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/links.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);
	}

	private void genernate_notice_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
        Map<String,Object> map =new HashMap<String,Object>();
        PlacardQuery qry =new PlacardQuery("pld.id, pld.title, pld.content");
        qry.setObjType(14);  
        qry.setObjId(subject.getSubjectId());        
        map.put("placard_list", qry.query_map(6));
        map.put("subject", subject);
        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/notice.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);

	}

	private void genernate_news_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
        Map<String,Object> map =new HashMap<String,Object>();
        SiteNewsQuery qry =new SiteNewsQuery("snews.newsId, snews.title, snews.createDate");
        qry.setSubjectId(subject.getSubjectId());
        qry.setHasPicture(false);
        
        map.put("subject_text_news", qry.query_map(6));
        map.put("subject", subject);
        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/text_news.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);

	}

	private void genernate_picnews_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
        Object content = cache.get(cache_key);
        if (content != null){
        	request.setAttribute(cache_key, content);
            return;
        }
        Map<String,Object>map =new HashMap<String,Object>();
        SiteNewsQuery qry =new SiteNewsQuery("snews.newsId, snews.title, snews.createDate, snews.picture");
        qry.setSubjectId(subject.getSubjectId());
        qry.setHasPicture(true);
    
        map.put("pic_news", qry.query_map(6));
        map.put("subject", subject);
        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
        map.put("webpart", webpart);
        map.put("unitId", baseSubject.getUnitId());
        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/pic_news.ftl", "utf-8");
        request.setAttribute(cache_key, content);
        cache.put(cache_key, content);

	}

	private void genernate_jiaoyanshipin_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
		        Object content = cache.get(cache_key);
		        if (content != null){
		        	request.setAttribute(cache_key, content);
		            return;
		        }
		        
		        Map<String,Object> map =new HashMap<String,Object>();
		        VideoQuery qry =new VideoQuery("v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.nickName, v.summary,v.flvThumbNailHref");
		        qry.setOrderType(VideoQuery.ORDER_TYPE_VIDEOID_DESC);
		        qry.setSubjectId(baseSubject.getMetaSubjectId());
		        qry.setGradeId(baseSubject.getMetaGradeId());
		        if (baseSubject.getUnitId() != null && baseSubject.getUnitId() != 0){
		        	qry.setUnitId(baseSubject.getUnitId());
		        }
		        map.put("new_video_list", qry.query_map(4));
		        map.put("subject", subject);
		        map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
		        map.put("webpart", webpart);
		        map.put("unitId", baseSubject.getUnitId());
		        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/video.ftl", "utf-8");
		        request.setAttribute(cache_key, content);
		        cache.put(cache_key, content);

	}

	private void genernate_resoure_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_" + webpart.getSubjectWebpartId() + cacheKeyFix;
		        Object content = cache.get(cache_key);
		        if (content != null){
		        	 request.setAttribute(cache_key, content);
			         return;
		        }
		        Map<String,Object>map =new HashMap<String,Object>();        
		        ResourceQuery qry =new ResourceQuery("r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount,u.loginName, u.nickName, r.subjectId as subjectId, grad.gradeName, sc.name as scName");
		        qry.setSubjectId(baseSubject.getMetaSubjectId());
		        qry.setGradeId(baseSubject.getMetaGradeId());
		        qry.setFuzzyMatch(true);
		        if (baseSubject.getUnitId() != null && baseSubject.getUnitId() != 0){
		        	qry.custormAndWhereClause = " r.approvedPathInfo LIKE '%/" + baseSubject.getUnitId() + "/%'";
		        }
		        map.put("new_resource_list", qry.query_map(10));
		        
		        ResourceQuery qry1 =new ResourceQuery("r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount,u.loginName, u.nickName, msubj.msubjName, grad.gradeName, sc.name as scName");
		        qry1.setOrderType(4);       //# downloadCount DESC
		        qry1.setSubjectId(baseSubject.getMetaSubjectId());
		        qry1.setGradeId(baseSubject.getMetaGradeId());
		        qry1.setFuzzyMatch(true);
		        if (baseSubject.getUnitId() != null && baseSubject.getUnitId() != 0){
		        	qry.custormAndWhereClause = " r.approvedPathInfo LIKE '%/" + baseSubject.getUnitId() + "/%'";
		        }
		        map.put("hot_resource_list", qry.query_map(10));
		        
		        //#hot_resource_list = viewcount_svc.getViewCountListShared(12,7,10,unit.unitPath,unit.unitDepth);
		        //#map.put("hot_resource_list", hot_resource_list)
		        
		        ResourceQuery qry2 =new ResourceQuery("r.resourceId, r.title, r.href, r.createDate, r.fsize, r.downloadCount,u.loginName, u.nickName, msubj.msubjName, grad.gradeName, sc.name as scName");
//		        qry2.rcmdState = True
		        qry2.setSubjectId(baseSubject.getMetaSubjectId());
		        qry2.setGradeId(baseSubject.getMetaGradeId());
		        qry2.setFuzzyMatch(true) ;
		        
		        if (baseSubject.getUnitId() != null && baseSubject.getUnitId() != 0){
		        	qry.custormAndWhereClause = " r.rcmdPathInfo LIKE '%/" + baseSubject.getUnitId() + "/%'";
		        }else{
		        	qry.custormAndWhereClause = " r.rcmdPathInfo is not null";
		        }
		            
		        map.put("rcmd_resource_list", qry.query_map(10));
		        map.put("subject", subject);
		        map.put("SubjectRootUrl",baseSubject.getSubjectRootUrl());
		        map.put("webpart", webpart);
		        map.put("unitId", baseSubject.getUnitId());
		        content = templateProcessor.processTemplate(map, "/WEB-INF/subjectpage/" + templateName + "/resource.ftl", "utf-8");
		        request.setAttribute(cache_key, content);
		        cache.put(cache_key, content);
	}

	private void genernate_article_content(SubjectWebpart webpart) {
		String cache_key = "sbj" + subject.getSubjectId() + "_"
				+ webpart.getSubjectWebpartId() + cacheKeyFix;
		Object content = cache.get(cache_key);
		if (content != null) {
			request.setAttribute(cache_key, content);
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		ArticleQuery qry = new ArticleQuery(
				"a.articleId, a.title, a.createDate, a.typeState, a.userId, a.loginName, a.userTrueName");
		qry.setSubjectId(baseSubject.getMetaSubjectId());
		qry.setGradeId(baseSubject.getMetaGradeId());
		qry.setFuzzyMatch(true);
		if (baseSubject.getUnitId() != null && baseSubject.getUnitId() != 0) {
			qry.setCustormAndWhereClause(" a.approvedPathInfo LIKE '%/" + baseSubject.getUnitId()
					+ "/%'");
		}
		List newest_article_list = qry.query_map(10);
		map.put("newest_article_list", newest_article_list);
		// #hot_article_list=viewcount_svc.getViewCountListShared(3,7,10,unit.unitPath,unit.unitDepth);
		// #map.put("hot_article_list",hot_article_list)

		ArticleQuery qry1 = new ArticleQuery(
				"a.articleId, a.title, a.createDate,a.typeState, a.userId, a.loginName, a.userTrueName");
		qry1.setSubjectId(baseSubject.getMetaSubjectId());
		qry1.setGradeId(baseSubject.getMetaGradeId());
		qry1.setFuzzyMatch(true);
		qry1.setOrderType(2);
		if (baseSubject.getUnitId() != null && baseSubject.getUnitId() != 0) {
			qry1.setCustormAndWhereClause(" a.approvedPathInfo LIKE '%/" + baseSubject.getUnitId()
					+ "/%'"); 
		}
		List hot_article_list = qry1.query_map(10);
		map.put("hot_article_list", hot_article_list);

		ArticleQuery qry2 = new ArticleQuery(
				"a.articleId, a.title, a.createDate, a.typeState, a.userId, a.loginName, a.userTrueName");
		qry2.setSubjectId(baseSubject.getMetaSubjectId());
		qry2.setGradeId(baseSubject.getMetaGradeId());
		qry2.setFuzzyMatch(true);
		if (baseSubject.getUnitId() != null && baseSubject.getUnitId() != 0) {
			qry2.setCustormAndWhereClause(" a.rcmdPathInfo LIKE '%/" + baseSubject.getUnitId()
					+ "/%'");
		} else {// 这里和前面有点变化,只要这里判断推荐信息里面有单位号的时候就然后被推荐,否则该文章没被推荐
			qry2.setCustormAndWhereClause(" a.rcmdPathInfo is not null");
		}
		List rcmd_article_list = qry2.query_map(10);
		map.put("rcmd_article_list", rcmd_article_list);

		map.put("subject", subject);
		map.put("webpart", webpart);
		map.put("unitId", baseSubject.getUnitId());
		map.put("SubjectRootUrl", baseSubject.getSubjectRootUrl());
		content = templateProcessor.processTemplate(map,
				"/WEB-INF/subjectpage/" + templateName + "/article.ftl",
				"utf-8");
		request.setAttribute(cache_key, content);
		cache.put(cache_key, content);

	}

	private void addSubjectNav() {
		List<String> siteNameArray = SubjectWebpart.SUBJECT_NAVNAME;
		List<String> siteUrlArray = SubjectWebpart.SUBJECT_NAVURL;   
		List<String> siteHightlightArray = SubjectWebpart.SUBJECT_NAVHIGHLIGHT;
        int i = 0;
        for (String name : siteNameArray){
        	SiteNav siteNav =new SiteNav();
            siteNav.setSiteNavName(siteNameArray.get(i));
            siteNav.setIsExternalLink(false);
            siteNav.setSiteNavUrl(siteUrlArray.get(i));
            siteNav.setSiteNavIsShow(true);
            siteNav.setSiteNavItemOrder(i);
            siteNav.setCurrentNav(siteHightlightArray.get(i));
            siteNav.setOwnerType(2);
            siteNav.setOwnerId(subject.getSubjectId());
            siteNavService.saveOrUpdateSiteNav(siteNav);
            i++ ;
        }            
		clearcache();
	}
	
	private void clearcache(){
		cache =JitarContext.getCurrentJitarContext().getCacheProvider().getCache("sitenav");
        if (cache != null){
    	List<String> cache_list = cache.getAllKeys();
        String cache_key = "subject_nav_" + subject.getSubjectId();
        for (String c : cache_list){
        	 if (c.trim().equals(cache_key.trim())){
        		 cache.remove(c);
        	 }
        }
        }                    
	}

	private void genWebparts() {
		 //#subject_list = subjectService.getSubjectList()
			        //#for subject in subject_list:
		List<SubjectWebpart> subject_webpart_list = subjectService.getSubjectWebpartList(subject.getSubjectId(), null);
	        if (subject_webpart_list.size() == 0){
	        	int i = 0;
		            for (String m : SubjectWebpart.MODULE_NAME){
		            	i++;
		                SubjectWebpart subjectWebpart =new SubjectWebpart();
		                subjectWebpart.setModuleName(m);
		                subjectWebpart.setDisplayName(m);
		                subjectWebpart.setRowIndex(i);
		                subjectWebpart.setVisible(true);
		                subjectWebpart.setSystemModule(true);//TODO 这里py里面设置为1
		                subjectWebpart.setSubjectId(subject.getSubjectId());
		                if (i < 7){
		                	subjectWebpart.setWebpartZone(3);
		                }
		                else if (i < 13){
		                	subjectWebpart.setWebpartZone(4);
		                }
		                else{
		                	subjectWebpart.setWebpartZone(5);
		                }
		                subjectService.saveOrUpdateSubjectWebpart(subjectWebpart);
		            }
			                
	        }                

	}
	
	public String getCurrentSiteUrl(HttpServletRequest request){
		String root = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80){
        	root = root + ":" + request.getServerPort();
        }
        root = root + request.getContextPath() + "/";
        return root;
	}

	private void set_webpart_flag(SubjectWebpart webpart) {
		switch (webpart.getWebpartZone()) {
		case SubjectWebpart.WEBPART_TOP:
			request.setAttribute("hasTopWebpart", "1");
			break;
		case SubjectWebpart.WEBPART_BOTTOM:
			request.setAttribute("hasBottomWebpart", "1");
			break;
		case SubjectWebpart.WEBPART_LEFT:
			request.setAttribute("hasLeftWebpart", "1");
			break;
		case SubjectWebpart.WEBPART_MIDDLE:
			request.setAttribute("hasMiddleWebpart", "1");
			break;
		case SubjectWebpart.WEBPART_RIGHT:
			request.setAttribute("hasRightWebpart", "1");
			break;
		default:
			break;
		}
	}

	public void setSiteLinksService(SiteLinksService siteLinksService) {
		this.siteLinksService = siteLinksService;
	}

	public void setStatService(StatService statService) {
		this.statService = statService;
	}

	public void setTimerCountService(TimerCountService timerCountService) {
		this.timerCountService = timerCountService;
	}

	public void setSiteNavService(SiteNavService siteNavService) {
		this.siteNavService = siteNavService;
	}

	public void setTemplateProcessor(TemplateProcessor templateProcessor) {
		this.templateProcessor = templateProcessor;
	}
}
