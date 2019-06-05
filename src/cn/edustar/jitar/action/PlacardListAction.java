package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.PlacardQuery;

/**
 * 视频
 * 
 * @author renliang
 */
public class PlacardListAction extends  AbstractBasePageAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1743444810468336926L;
	
	private BaseSubject baseSubject = null;

	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		 if (baseSubject.getSubject() == null){
			  addActionError("无法加载指定的学科。");
	            return "ERROR";
		 }
	        String templateName = "template1";
	        if (baseSubject.getSubject().getTemplateName() != null){
	        	templateName = baseSubject.getSubject().getTemplateName();
	        }
	            
	        PlacardQuery qry =new PlacardQuery("pld.id, pld.title, pld.createDate");
	        qry.setObjType(14);  
	        qry.setObjId( baseSubject.getSubject().getSubjectId());
	        Pager pager = params.createPager();
	        pager.setTotalRows(qry.count());
//	        placard_list = qry.query_map(pager);
	        String Page_Title = params.safeGetStringParam("title");
	        if ("".equals(Page_Title.trim())){
	        	Page_Title = "学科公告";
	        }
	        request.setAttribute("Page_Title", Page_Title);
	        request.setAttribute("placard_list", qry.query_map(pager));
	        request.setAttribute("pager", pager);
	        request.setAttribute("subject",baseSubject.getSubject());
	        
	        return templateName;
	}
}
