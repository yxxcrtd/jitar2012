package cn.edustar.jitar.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.edustar.data.Pager;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.Vote;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.PhotoQuery;
import cn.edustar.jitar.service.PlugInTopicQuery;
import cn.edustar.jitar.service.SpecialSubjectArticleQuery;
import cn.edustar.jitar.service.SpecialSubjectQuery;
import cn.edustar.jitar.service.SpecialSubjectService;
import cn.edustar.jitar.service.VideoQuery;
import cn.edustar.jitar.service.VoteService;

/**
 * 专题
 * 
 * @author renliang
 */
@SuppressWarnings({ "rawtypes", "static-access", "serial" })
public class SpecialSubjectAction extends AbstractBasePageAction {
    private SpecialSubjectService specialSubjectService;
    public Integer specialSubjectId = null;
    public SpecialSubject specialSubject;
    private VoteService voteService;
    private List<Vote> voteList = new ArrayList<Vote>();
 
	/* (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.AbstractBasePageAction#execute(java.lang.String)
	 */
	@Override
    protected String execute(String cmd) throws Exception {
        String sp_type = params.safeGetStringParam("type");
        request.setAttribute("k", params.getStringParam("k"));
        response.setContentType("text/html; charset=UTF-8");
        specialSubjectId = params.safeGetIntParam("specialSubjectId");
        if (specialSubjectId > 0) {
            specialSubject = specialSubjectService.getSpecialSubject(specialSubjectId);
            if (specialSubject == null) {
                addActionError("未能正确加载专题对象");
                return "ERROR";
            } else {
                if (specialSubject.getObjectType().equals("subject")) {
                    Object subjectUrlPattern = request.getAttribute("SubjectUrlPattern");
                    Subject subject = subjectService.getSubjectById(specialSubject.getObjectId());
                    if (subject == null) {
                        addActionError("未能正确加载学科对象");
                        return "ERROR";
                    }
                    if (subjectUrlPattern != null) {
                        subjectUrlPattern = subjectUrlPattern.toString().replace("{subjectCode}", subject.getSubjectCode());
                        subjectUrlPattern = subjectUrlPattern + "py/specialsubject.py?specialSubjectId=" + specialSubject.getSpecialSubjectId();
                    } else {
                        subjectUrlPattern = "k/" + subject.getSubjectCode() + "/py/specialsubject.py?specialSubjectId="
                                + specialSubject.getSpecialSubjectId();
                        response.sendRedirect((String) subjectUrlPattern);
                    }
                }
            }
        }
        SpecialSubjectQuery qry = new SpecialSubjectQuery("ss.specialSubjectId, ss.objectGuid, ss.title,ss.logo,ss.description,ss.createDate,ss.expiresDate");
        qry.setObjectType("system");
        List ss_list = qry.query_map(20);
        if (specialSubject == null) {
            if (ss_list != null && ss_list.size() > 0) {
                specialSubject = new SpecialSubject();
                Map map = (Map) ss_list.get(0);
                specialSubject.setLogo((String) map.get("logo"));
                specialSubject.setTitle((String) map.get("title"));
                specialSubject.setExpiresDate((Timestamp) map.get("expiresDate"));
                specialSubject.setDescription((String) map.get("description"));
                specialSubject.setSpecialSubjectId((Integer) map.get("specialSubjectId"));
                specialSubject.setCreateDate((Timestamp) map.get("createDate"));
                specialSubject.setObjectGuid((String) map.get("objectGuid"));

                specialSubjectId = specialSubject.getSpecialSubjectId();
            }
        }
        // # 有点小问题，暂时先再加载一次。
        /*
         * specialSubject = specialSubjectService
         * .getSpecialSubject(specialSubjectId);
         */
        if (specialSubject == null) {
            return "no_specialsubject_page";
        }
        if (specialSubject != null) {
            PhotoQuery qry1 = new PhotoQuery("p.photoId, p.title, p.userId,p.href, p.userTrueName,u.loginName");
            qry1.k = params.safeGetStringParam("k", null);
            qry1.f = params.safeGetStringParam("f", "0");
            qry1.sysCateId = params.getIntParamZeroAsNull("categoryId");
            request.setAttribute("f", qry1.f);
            request.setAttribute("k", qry1.k);
            
            qry1.setSpecialSubjectId(specialSubjectId);
            qry1.setExtName(".jpg");
            List ssp_list = qry1.query_map(6);
            SpecialSubjectArticleQuery qry2 = new SpecialSubjectArticleQuery("ssa.articleId, ssa.title,ssa.userId, ssa.userTrueName, ssa.createDate, ssa.typeState, a.articleAbstract, a.articleContent");
            qry2.setRequest(request);
            qry2.setSpecialSubjectId(specialSubjectId);
            List ssa_list = qry2.query_map(10);
            
            if (ssp_list != null && ssp_list.size() > 0) {
                request.setAttribute("specialSubjectPhotoList", ssp_list);
            }
            request.setAttribute("specialSubjectArticleList", ssa_list);
            request.setAttribute("specialSubject", specialSubject);
        }
        
        if ("article".equals(sp_type)) {
        	return getAllArticle();
        }

        Integer sp_type_id = params.safeGetIntParam("id");

        if (!"".equals(sp_type.trim())) {
            request.setAttribute("specialSubjectType", sp_type);
            request.setAttribute("specialSubjectTypeId", sp_type_id);
        }
        request.setAttribute("specialSubjectList", ss_list);
        request.setAttribute("head_nav", "special_subject");
        if (super.getLoginUser() != null) {
            request.setAttribute("loginUser", super.getLoginUser());
        }
        video_list();
        // new_special_list();
        voteList = voteService.getVoteList(specialSubject.getObjectGuid(), 5);
        topic_list();
        AccessControlService accessControlService = JitarContext.getCurrentJitarContext().getAccessControlService();
        User user = this.getLoginUser();
        if(user != null ){
            if(accessControlService.isSystemAdmin(user)){
                request.setAttribute("superAdmin", "1");
            }
        }
        return "site_specialsubject";
    }

	private void video_list() {
        VideoQuery qry = new VideoQuery(" v.videoId, v.flvHref, v.flvThumbNailHref, v.title,v.userId,v.createDate");
        qry.setSpecialSubjectId(specialSubject.getSpecialSubjectId());
        List v_list = qry.query_map(5);
        if (null != v_list && v_list.size() > 0) {
            request.setAttribute("video_list", v_list);
        }
    }
    
//    private void new_special_list() {
//    	List new_special_list = specialSubjectService.getAllNewSpecialSubject();
//    	if (0 < new_special_list.size()) {
//    		request.setAttribute("new_special_list", new_special_list);
//    	}
//    }
	
    private void topic_list() {
		Pager pager = params.createPager();
		pager.setItemName("讨论");
		pager.setItemUnit("个");
		pager.setPageSize(10);
		PlugInTopicQuery qry = new PlugInTopicQuery("pt.plugInTopicId, pt.title, pt.createUserId, pt.createUserName, pt.createDate");
		qry.setParentGuid(specialSubject.getObjectGuid());
		pager.setTotalRows(qry.count());
		request.setAttribute("topic_list", qry.query_map(pager));
		request.setAttribute("parentGuid", specialSubject.getObjectGuid());
    }
    
    private String getAllArticle() {
    	Pager pager = params.createPager();
		pager.setItemName("文章");
		pager.setItemUnit("篇");
		pager.setPageSize(10);
		SpecialSubjectArticleQuery query = new SpecialSubjectArticleQuery("ssa.articleId, ssa.title, ssa.userId, ssa.userTrueName,ssa.createDate,ssa.typeState");
		query.setSpecialSubjectId(specialSubject.getSpecialSubjectId());
		pager.setTotalRows(query.count());
		request.setAttribute("article_list", query.query_map(pager));
		request.setAttribute("parentGuid", specialSubject.getObjectGuid());
		request.setAttribute("pager", pager);
        request.setAttribute("head_nav", "special_subject");
    	return "show_article_list";
    }

	public void setSpecialSubjectService(SpecialSubjectService specialSubjectService) {
        this.specialSubjectService = specialSubjectService;
    }

    public void setVoteService(VoteService voteService) {
		this.voteService = voteService;
	}

	public List<Vote> getVoteList() {
		return voteList;
	}
    
}
