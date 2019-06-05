package cn.edustar.jitar.action;

import java.util.HashMap;
import java.util.Map;
import cn.edustar.data.Pager;
import cn.edustar.jitar.service.PageFrameService;

/**
 * 提出问题
 * 
 * @author renliang
 */
public class MoreVoteAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1213211887857583255L;
	private CommonData commonData = null;
    private PageFrameService pageFrameService = null;
	@Override
	protected String execute(String cmd) throws Exception {
		commonData = new CommonData();
		pageFrameService = commonData.getPageFrameService();
		if (commonData.getParentGuid().trim().equals("") || commonData.getParentType().trim().equals("")){
			addActionError("无效的标识。");
            return "error";
		}
        
        String pageIndex = params.safeGetStringParam("page");
        if (!pageIndex.matches("^[0-9]+$")){
        	 pageIndex = "1";
        }
        
        VoteQuery qry =new VoteQuery("vote.voteId,vote.title,vote.createDate,vote.endDate");   
        qry.setParentGuid(commonData.getParentGuid());
        qry.setParentObjectType(commonData.getParentType());
        Pager pager = params.createPager();
        pager.setItemName("调查");
        pager.setItemUnit("个");
        pager.setPageSize(20);
        pager.setCurrentPage(Integer.parseInt(pageIndex));
        pager.setTotalRows(qry.count());
        Object vote_list = qry.query_map(pager);
        
        Map <String,Object>map =new HashMap<String,Object>();
        map.put("SiteUrl",pageFrameService.getSiteUrl());
        map.put("UserMgrUrl",pageFrameService.getUserMgrUrl());
        map.put("vote_list",vote_list);
        map.put("pager",pager);
        map.put("loginUser",super.getLoginUser());
        map.put("parentGuid",commonData.getParentGuid());
        map.put("parentType",commonData.getParentType());

        String pagedata = pageFrameService.transformTemplate(map, "/WEB-INF/mod/vote/morevote.ftl");      
        
        String page_frame = pageFrameService.getFramePage(commonData.getParentGuid(),commonData.getParentType());
        page_frame = page_frame.replace("[placeholder_content]",pagedata);
        page_frame = page_frame.replace("[placeholder_title]","全部调查投票");        
        commonData.writeToResponse(page_frame);
		return null;
	}
}
