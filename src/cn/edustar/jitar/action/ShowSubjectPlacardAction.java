package cn.edustar.jitar.action;

import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.PlacardService;

/**
 * 学科公告
 * @author renliang
 */
public class ShowSubjectPlacardAction extends  AbstractBasePageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6474498690677374041L;
	
	private BaseSubject baseSubject = null;

	private Subject subject = null;
	
	public PlacardService placardService;
	
	private String templateName = null;
	
	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		subject = baseSubject.getSubject();
		if (subject == null){
			addActionError("无法加载指定的学科。");
            return "ERROR";
		}
        Integer placardId = params.safeGetIntParam("placardId");
        Placard placard = placardService.getPlacard(placardId);
        if (placard == null){
        	 addActionError("该条公告不存在。");
             return "ERROR";
        }
        if (!baseSubject.isAdmin()){
        	if (placard.getHide()){
        		 addActionError("该条公告不是公开的。");
                 return "ERROR";
        	}
        }
        templateName = "template1";
        if (subject.getTemplateName() != null){
        	templateName = subject.getTemplateName();
        }
        request.setAttribute("subject",subject);
        request.setAttribute("placard",placard);
        return templateName;
	}
	
	public void setPlacardService(PlacardService placardService) {
		this.placardService = placardService;
	}
}
