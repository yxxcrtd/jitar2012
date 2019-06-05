package cn.edustar.jitar.action;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.service.TagArticleQuery;
import cn.edustar.jitar.service.TagGroupQuery;
import cn.edustar.jitar.service.TagPhotoQuery;
import cn.edustar.jitar.service.TagPrepareCourseQuery;
import cn.edustar.jitar.service.TagResourceQuery;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.TagUserQuery;

/**
 * 展示其他的标签
 * 
 * @author renliang
 */
public class ShowMoreTagContentAction extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6168373478740439785L;
	private TagService tagService = null;
	private Tag tag = null;

	@Override
	protected String execute(String cmd) throws Exception {
		/*
		 * site_config = SiteConfig() 不知道什么用 site_config.get_config()
		 */
		Integer tagId = params.getIntParam("tagId");
		String tagType = params.safeGetStringParam("type");
		tag = tagService.getTag(tagId);
		if (tag == null) {
			String actionErrors = "标签不存在。";
			request.setAttribute("actionErrors", actionErrors);
			return "error";
		}
		if (tagType.trim().equals("article")) {
			get_tag_article_list();
		} else if (tagType.trim().equals("photo")) {
			get_tag_photo_list();
		} else if (tagType.trim().equals("user")) {
			get_tag_user_list();
		} else if (tagType.trim().equals("resource")) {
			get_tag_resource_list();
		} else if (tagType.trim().equals("preparecourse")) {
			get_tag_preparecourse_list();
		} else if (tagType.trim().equals("group")) {
			get_tag_group_list();
		} else {
			String actionErrors = "无效的参数。";
			request.setAttribute("actionErrors", actionErrors);
			return "error";
		}
		request.setAttribute("tag", tag);
		return "success";
	}

	private void get_tag_group_list() {
		TagGroupQuery qry = new TagGroupQuery("g.groupName,g.groupTitle");
		qry.setTagId(tag.getTagId());
		Pager pager = params.createPager();
		pager.setItemName("协作组");
		pager.setItemUnit("个");
		pager.setTotalRows(qry.count());
		pager.setPageSize(20);
		request.setAttribute("pager", pager);
		request.setAttribute("tag_group_list", qry.query_map(pager));
	}

	private void get_tag_preparecourse_list() {
		TagPrepareCourseQuery qry = new TagPrepareCourseQuery(
				"pc.prepareCourseId,pc.title");
		qry.setTagId(tag.getTagId());
		Pager pager = params.createPager();
		pager.setItemName("备课");
		pager.setItemUnit("个");
		pager.setTotalRows(qry.count());
		pager.setPageSize(20);
		request.setAttribute("pager", pager);
		request.setAttribute("tag_preparecourse_list", qry.query_map(pager));

	}

	private void get_tag_resource_list() {
		TagResourceQuery qry = new TagResourceQuery("r.title,r.resourceId");
		qry.setTagId(tag.getTagId());
		Pager pager = params.createPager();
		pager.setItemName("资源");
		pager.setItemUnit("个");
		pager.setTotalRows(qry.count());
		pager.setPageSize(20);
		request.setAttribute("pager", pager);
		request.setAttribute("tag_resource_list", qry.query_map(pager));

	}

	private void get_tag_user_list() {
		TagUserQuery qry = new TagUserQuery("u.loginName,u.trueName,u.nickName");
		qry.setTagId(tag.getTagId());
		Pager pager = params.createPager();
		pager.setItemName("用户");
		pager.setItemUnit("个");
		pager.setTotalRows(qry.count());
		pager.setPageSize(20);
		request.setAttribute("pager", pager);
		request.setAttribute("tag_user_list", qry.query_map(pager));

	}

	private void get_tag_photo_list() {
		TagPhotoQuery qry = new TagPhotoQuery("p.photoId,p.title,p.userId");
		qry.setTagId(tag.getTagId());
		Pager pager = params.createPager();
		pager.setItemName("照片");
		pager.setItemUnit("张");
		pager.setTotalRows(qry.count());
		pager.setPageSize(20);
		request.setAttribute("pager", pager);
		request.setAttribute("tag_photo_list", qry.query_map(pager));

	}

	private void get_tag_article_list() {
		TagArticleQuery qry = new TagArticleQuery("a.title,a.articleId");
		qry.setTagId(tag.getTagId());
		Pager pager = params.createPager();
		pager.setItemName("文章");
		pager.setItemUnit("篇");
		pager.setTotalRows(qry.count());
		pager.setPageSize(20);
		request.setAttribute("pager", pager);
		request.setAttribute("tag_article_list", qry.query_map(pager));
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}
	
}
