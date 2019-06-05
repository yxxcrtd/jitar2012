package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.SpecialSubjectQuery;
import cn.edustar.jitar.service.SpecialSubjectService;

/**
 * 专题列表
 * 
 * @author renliang
 */
@SuppressWarnings("serial")
public class ShowMoreSpecialSubjectListAction extends AbstractBasePageAction {

	private SpecialSubject specialSubject = null;
	private SpecialSubjectService specialSubjectService = null;
	private BaseSubject baseSubject = null;
	private String templateName = null;
	private Subject subject = null;
	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		subject = baseSubject.getSubject();
		if (subject == null){
			addActionError("无法加载指定的学科。");
            return "error";
		}
        
        templateName = "template1";
        if (subject.getTemplateName() != null){
        	 templateName = subject.getTemplateName();
        }
        Integer specialSubjectId = params.safeGetIntParam("specialSubjectId");
        if (specialSubjectId == 0){
        	specialSubject = specialSubjectService.getNewestSpecialSubjectByType("subject");
                    if (specialSubject != null){
                    	specialSubjectId = specialSubject.getSpecialSubjectId();
                    }
        }
        if (specialSubjectId > 0){
        	 if (specialSubject == null){
        		 specialSubject = specialSubjectService.getSpecialSubject(specialSubjectId);
        	 }
                 
             if (specialSubject == null){
            	 addActionError("无法加载指定的专题。");
                 return "error";
             }
             return show_specialsubject_list();
        }
        else{
        	 request.setAttribute("subject",subject);
             request.setAttribute("head_nav","specialsubject");
             return "error_"+templateName;
        }
	}
	
	private String show_specialsubject_list() {
		SpecialSubjectQuery qry =new SpecialSubjectQuery("ss.specialSubjectId, ss.objectGuid, ss.title,ss.logo,ss.description,ss.createDate,ss.expiresDate");
        qry.setObjectId(subject.getSubjectId());
        qry.setObjectType("subject");
        Pager pager = params.createPager();
        pager.setItemName("学科专题");
        pager.setItemUnit("个");
        pager.setPageSize(20);
        pager.setTotalRows(qry.count()); 
        request.setAttribute("specialsubject_list", qry.query_map(pager));
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
