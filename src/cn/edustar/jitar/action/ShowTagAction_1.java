package cn.edustar.jitar.action;

import cn.edustar.jitar.pojos.Tag;
import cn.edustar.jitar.service.TagArticleQuery;
import cn.edustar.jitar.service.TagGroupQuery;
import cn.edustar.jitar.service.TagPhotoQuery;
import cn.edustar.jitar.service.TagPrepareCourseQuery;
import cn.edustar.jitar.service.TagResourceQuery;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.TagVideoQuery;

/**
 * 显示标签
 * 
 * @author renliang
 */
public class ShowTagAction_1 extends AbstractBasePageAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6168373478740439785L;
	private TagService tagService = null;
	private Tag tag = null;

	@Override
	protected String execute(String cmd) throws Exception {
		Integer tagId = params.getIntParam("tagId");
		String tagName = params.safeGetStringParam("tagName");
		if (tagId != 0) {
			tag = tagService.getTag(tagId);
		} else {
			tag = tagService.getTagByName(tagName);
		}
		// #print "tagName = ",tagName
		if (tag == null) {
			String actionErrors = "标签不存在。";
			request.setAttribute("actionErrors", actionErrors);
			return "error";
		}
		tagService.incTagViewCount(tag, 1);
		request.setAttribute("tag", tag);
		request.setAttribute("head_nav", "tags");
		get_tag_article_list();
		get_tag_group_list();
		get_tag_preparecourse_list();
		get_tag_user_list();
		get_tag_resource_list();
		get_tag_photo_list();
		get_tag_video_list();
		return "success";
	}

	private void get_tag_video_list(){
		TagVideoQuery qry = new  TagVideoQuery("v.videoId,v.title,v.flvHref,v.flvThumbNailHref,v.userId");
		qry.setTagId(tag.getTagId());
		request.setAttribute("tag_video_list", qry.query_map(10));
	}
	
	private void get_tag_photo_list() {
		TagPhotoQuery qry = new TagPhotoQuery("p.photoId,p.title,p.userId");
		qry.setTagId(tag.getTagId());
		request.setAttribute("tag_photo_list", qry.query_map(10));
	}

	private void get_tag_resource_list() {
		TagResourceQuery qry = new TagResourceQuery("r.title,r.resourceId");
		qry.setTagId(tag.getTagId());
		request.setAttribute("tag_resource_list", qry.query_map(10));
	}

	private void get_tag_user_list() {
		TagUserQuery qry = new TagUserQuery("u.loginName,u.trueName,u.nickName");
		qry.setTagId(tag.getTagId());
		request.setAttribute("tag_user_list", qry.query_map(10));
	}

	private void get_tag_preparecourse_list() {
		TagPrepareCourseQuery qry = new TagPrepareCourseQuery(
				"pc.prepareCourseId,pc.title");
		qry.setTagId(tag.getTagId());
		request.setAttribute("tag_preparecourse_list", qry.query_map(10));

	}

	private void get_tag_group_list() {
		TagGroupQuery qry = new TagGroupQuery("g.groupName,g.groupTitle");
		qry.setTagId(tag.getTagId());
		request.setAttribute("tag_group_list", qry.query_map(10));

	}

	private void get_tag_article_list() {
		TagArticleQuery qry = new TagArticleQuery("a.title,a.articleId");
		qry.setTagId(tag.getTagId());
		request.setAttribute("tag_article_list", qry.query_map(10));

	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

}
