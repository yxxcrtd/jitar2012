package cn.edustar.jitar.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.edustar.data.Pager;
import cn.edustar.jitar.service.SpecialSubjectQuery;

//import cn.edustar.jitar.JitarConst;

/**
 * 视频
 * 
 * @author renliang
 */
public class MorespecialSubject extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1743444810468336926L;

	/** serialVersionUID */

	private transient static final Log log = LogFactory
			.getLog(MorespecialSubject.class);

	@Override
	protected String execute(String cmd) throws Exception {
		response.setContentType("text/html; charset=UTF-8");

		Pager pager = params.createPager();
		pager.setItemName("专题");
		pager.setItemUnit("个");
		pager.setPageSize(20);
		SpecialSubjectQuery qry = new SpecialSubjectQuery(
				"ss.specialSubjectId, ss.title, ss.logo, ss.description, ss.createDate, ss.expiresDate, ss.objectType, ss.objectId");
		
		pager.setTotalRows(qry.count());
		request.setAttribute("specialsubject_list", qry.query_map(pager));
		request.setAttribute("pager", pager);
		return "success";
	}

}
