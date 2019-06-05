package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.SpecialSubjectArticleQuery;
import cn.edustar.jitar.service.SpecialSubjectService;

/**
 * 文章列表
 * 
 * @author renliang
 */
@SuppressWarnings("serial")
public class ShowArticleListAction extends AbstractBasePageAction {
	
	private SpecialSubjectService specialSubjectService = null;
	private SpecialSubject specialSubject = null;
	private BaseSubject baseSubject = null;
	private Subject subject = null;
	private String templateName = null;
	
	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		subject = baseSubject.getSubject();
		if (subject == null) {
			addActionError("无法加载指定的学科。");
			return "error";
		}
		templateName = "template1";
		if (subject.getTemplateName() != null) {
			templateName = subject.getTemplateName();
		}
		Integer specialSubjectId = params.safeGetIntParam("specialSubjectId");
		if (specialSubjectId == 0) {
			specialSubject = specialSubjectService
					.getNewestSpecialSubjectByType("subject");
			if (specialSubject != null) {
				specialSubjectId = specialSubject.getSpecialSubjectId();
			}

		}
		if (specialSubjectId > 0) {
			if (specialSubject == null) {
				specialSubject = specialSubjectService
						.getSpecialSubject(specialSubjectId);
			}
			if (specialSubject == null) {
				addActionError("无法加载指定的专题。");
				return "error";
			}
			return show_list();
		} else {
			request.setAttribute("subject", subject);
			request.setAttribute("head_nav", "specialsubject");
			return "error_" + templateName;
		}
	}

	private String show_list() {
		SpecialSubjectArticleQuery qry =new SpecialSubjectArticleQuery("ssa.articleId, ssa.title, ssa.userId, ssa.userTrueName, ssa.createDate, ssa.loginName, ssa.typeState");
		qry.setRequest(request);
	        qry.setSpecialSubjectId(specialSubject.getSpecialSubjectId());       
	        Pager pager = params.createPager();
	        pager.setItemName("文章");
	        pager.setItemUnit("篇");
	        pager.setPageSize(20);
	        pager.setTotalRows(qry.count());
	        request.setAttribute("article_list", qry.query_map(pager));
	        request.setAttribute("pager", pager);        
	        request.setAttribute("specialSubject", specialSubject);        
	        request.setAttribute("subject",subject);
	        request.setAttribute("head_nav","specialsubject");
	        return templateName;
	}

	public void setSpecialSubjectService(SpecialSubjectService specialSubjectService) {
		this.specialSubjectService = specialSubjectService;
	}
}
