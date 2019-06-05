package cn.edustar.jitar.action;

import cn.edustar.jitar.pojos.SiteNews;
import cn.edustar.jitar.pojos.Subject;


//import cn.edustar.jitar.JitarConst;

/**
 * 视频
 * 
 * @author renliang
 */
public class ShowSubjectNewsAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3140916842592168769L;
	
	private BaseSubject baseSubject = null;

	private Subject subject = null;
	
	private String templateName = null; 
	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		subject = baseSubject.getSubject();
		 if (subject == null){
			 addActionError("无法加载指定的学科。");
	         return "ERROR";
		 }
	        Integer newsId = params.safeGetIntParam("newsId");
	        SiteNews news = subjectService.getSiteNews(newsId);
	        if (news == null){
	        	addActionError("该条新闻不存在。");
	            return "ERROR";
	        }
	        if (!baseSubject.isAdmin()){
	        	if (news.getStatus() != 0){
	        		addActionError("该条新闻还未审核。");
	                return "ERROR";
	        	}
	        }
	        templateName = "template1";
	        if (subject.getTemplateName() != null){
	        	templateName = subject.getTemplateName();
	        }
	        request.setAttribute("subject",subject);
	        request.setAttribute("news",news);
	        return templateName;
	}
}
