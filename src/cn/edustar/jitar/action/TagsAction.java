package cn.edustar.jitar.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.TagQuery;
import cn.edustar.jitar.util.ParamUtil;
//import cn.edustar.jitar.JitarConst;

/**
 * 标签
 * @author renliang
 */
public class TagsAction extends AbstractBasePageAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private transient static final Log log = LogFactory.getLog(TagsAction.class);
	
	public String execute(String cmd) throws Exception {
		request.setAttribute("tag_list", getTagList()) ;
		request.setAttribute("head_nav", "tags");
		return "success";
	}
	
	private List getTagList(){
		TagQuery tagquery = new TagQuery("tag.tagId, tag.tagName, tag.refCount, tag.refCount, tag.viewCount");
		Pager pager = new ParamUtil(request).createPager();
	    pager.setItemName("标签");
	    pager.setItemUnit("个");
	    pager.setPageSize(20);
	    pager.setTotalRows(tagquery.count());
	    request.setAttribute("pager", pager);
	    log.info("date:"+new Date()+"method:"+"getTagList");
		return (List) tagquery.query_map(pager);
	}
}
