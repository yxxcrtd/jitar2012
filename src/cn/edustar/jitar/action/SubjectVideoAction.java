package cn.edustar.jitar.action;

import java.util.ArrayList;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.CategoryModel;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.VideoQuery;

import com.alibaba.fastjson.JSONObject;

/**
 * 课程视频
 * 
 * @author renliang
 */
public class SubjectVideoAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6168373478740439785L;
	private BaseSubject baseSubject = null;
	private Subject subject = null;
	private CategoryService categoryService = null;

	@Override
	protected String execute(String cmd) throws Exception {
		baseSubject = new BaseSubject();
		subject = baseSubject.getSubject();
		if (subject == null) {
			addActionError("无法加载指定的学科。");
			return "error";
		}

		String templateName = "template1";
		if (subject.getTemplateName() != null) {
			templateName = subject.getTemplateName();
		}

		get_video_category();
		get_video_list();

		String Page_Title = params.safeGetStringParam("title");
		if (Page_Title.trim().equals("")) {
			Page_Title = "全部分类";
		}
		request.setAttribute("Page_Title", Page_Title);
		request.setAttribute("subject", subject);
		request.setAttribute("head_nav", "video");
		return templateName;
	}

	private void get_video_list() {
		VideoQuery qry = new VideoQuery(
				"v.videoId, v.title, v.createDate, v.href, v.flvHref, v.userId, u.loginName, u.nickName, v.summary,v.flvThumbNailHref");
		qry.setOrderType(VideoQuery.ORDER_TYPE_VIDEOID_DESC);
		qry.setSubjectId(subject.getMetaSubject().getMsubjId());
		qry.setGradeId(subject.getMetaGrade().getGradeId());
		if (unitId != null && unitId != 0) {
			qry.setUnitId(unitId);
		}
		Pager pager = createPager();
		pager.setTotalRows(qry.count());
		request.setAttribute("video_list", qry.query_map(pager));
		request.setAttribute("pager", pager);
	}

	private Pager createPager() {
		Pager pager = params.createPager();
		pager.setItemName("视频");
		pager.setItemUnit("个");
		pager.setPageSize(20);
		return pager;
	}

	private void get_video_category() {
		// root_cates = []
		CategoryTreeModel video_categories = categoryService
				.getCategoryTree("video");
		List<JSONObject> objs = new ArrayList<JSONObject>();
		for (CategoryModel c : video_categories.getAll()) {
			JSONObject obj = new JSONObject(); 
			obj.put("categoryId", c.getCategoryId());
			obj.put("categoryName", c.getName());
			obj.put("parentId", c.getParentId());
			objs.add(obj);
		}
		request.setAttribute("root_cates", objs);
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
}
