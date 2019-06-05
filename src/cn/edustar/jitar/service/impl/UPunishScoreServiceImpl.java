package cn.edustar.jitar.service.impl;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.dao.UPunishScoreDao;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.UPunishScore;
import cn.edustar.jitar.query.PunishQueryParam;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.PhotoService;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.UPunishScoreService;
import cn.edustar.jitar.service.VideoService;

public class UPunishScoreServiceImpl implements UPunishScoreService {

	private UPunishScoreDao upunishscore_dao;
	private ConfigService categoryService;
	private ArticleService art_service;
	private ResourceService res_service;
	private PhotoService photo_service;
	private CommentService com_service;
	private VideoService video_service;

	/**
	 * 缺省构造函数.
	 */
	public UPunishScoreServiceImpl() {

	}

	/** 数据访问对象. */
	public void setCommentService(CommentService commentService) {
		this.com_service = commentService;
	}

	public void setVideoService(VideoService video_service) {
		this.video_service = video_service;
	}

	public void setResourceService(ResourceService resourceService) {
		this.res_service = resourceService;
	}

	public void setPhotoService(PhotoService photoService) {
		this.photo_service = photoService;
	}

	public void setConfigService(ConfigService configService) {
		this.categoryService = configService;
	}

	public void setArticleService(ArticleService articleService) {
		this.art_service = articleService;
	}

	public void setUPunishScoreDao(UPunishScoreDao upunishscoredao) {
		this.upunishscore_dao = upunishscoredao;
	}

	public UPunishScore getUPunishScore(int id) {
		return this.upunishscore_dao.getUPunishScore(id);
	}

	public UPunishScore getUPunishScore(int objType, int objId) {
		return this.upunishscore_dao.getUPunishScore(objType, objId);
	}

	public void saveUPunishScore(UPunishScore uPunishScore) {
		this.upunishscore_dao.saveUPunishScore(uPunishScore);
	}
	public float getScore(boolean punitive,int userId){
		return this.upunishscore_dao.getScore(punitive,userId);
	}
	public float getScore(boolean punitive,int objType,int userId){
		return this.upunishscore_dao.getScore(punitive,objType,userId);
	}
	public UPunishScore createUPunishScore(int objType, int objId, int userID,int createUserId,String createUserName) {
		// 根据文章资源还是评论图片等类型判断分数
		Configure conf = categoryService.getConfigure();
		Object oValue = null;
		String sTitle = "";
		
		if (objType == ObjectType.OBJECT_TYPE_ARTICLE.getTypeId()) {
			oValue = conf.get("score.article.adminDel");
			Article art = art_service.getArticle(objId);
			sTitle = art.getTitle();
		} else if (objType == ObjectType.OBJECT_TYPE_RESOURCE.getTypeId()) {
			oValue = conf.get("score.resource.adminDel");
			Resource res = res_service.getResource(objId);
			sTitle = res.getTitle();
		} else if (objType == ObjectType.OBJECT_TYPE_PHOTO.getTypeId()) {
			oValue = conf.get("score.photo.adminDel");
			Photo photo = photo_service.findById(objId);
			sTitle = photo.getTitle();
		} else if (objType == ObjectType.OBJECT_TYPE_COMMENT.getTypeId()) {
			oValue = conf.get("score.comment.adminDel");
			//System.out.println("objId=" + objId);
			Comment com = com_service.getComment(objId);
			if (!com.equals(null))
				sTitle = com.getTitle();
		}else if (objType == ObjectType.OBJECT_TYPE_VIDEO.getTypeId()) {
			//System.out.println("objId=" + objId);
			oValue = conf.get("score.video.adminDel");
			Video video = video_service.findById(objId);
			if (!video.equals(null))
				sTitle = video.getTitle();
		}

		float v = 0;
		if (!oValue.equals(null)) {
			v = Float.parseFloat(oValue.toString());
		}
		return this.upunishscore_dao.createUPunishScore(objType, objId, userID,
				v, sTitle,createUserId,createUserName);
	}

	public UPunishScore createUPunishScore(int objType, int objId, int userID,
			float score,String reason,int createUserId,String createUserName) {
		// 根据文章资源还是评论图片等类型判断分数
		Configure conf = categoryService.getConfigure();
		String sTitle = "";
		if (objType == ObjectType.OBJECT_TYPE_ARTICLE.getTypeId()) {
			Article art = art_service.getArticle(objId);
			sTitle = art.getTitle();
		} else if (objType ==  ObjectType.OBJECT_TYPE_RESOURCE.getTypeId()) {
			Resource res = res_service.getResource(objId);
			sTitle = res.getTitle();
		} else if (objType == ObjectType.OBJECT_TYPE_PHOTO.getTypeId()) {
			Photo photo = photo_service.findById(objId);
			sTitle = photo.getTitle();
		} else if (objType == ObjectType.OBJECT_TYPE_COMMENT.getTypeId()) {
			//System.out.println("objId=" + objId);
			Comment com = com_service.getComment(objId);
			if (!com.equals(null))
				sTitle = com.getTitle();
		} else if (objType == ObjectType.OBJECT_TYPE_VIDEO.getTypeId()) {
			//System.out.println("objId=" + objId);
			Video video = video_service.findById(objId);
			if (!video.equals(null))
				sTitle = video.getTitle();
		}
		float v = score;
		return this.upunishscore_dao.createUPunishScore(objType, objId, userID,
				v, sTitle,reason,createUserId,createUserName);
	}

	public void deleteUPunishScore(UPunishScore uPunishScore) {
		if (uPunishScore != null) {
			this.upunishscore_dao.deleteUPunishScore(uPunishScore);
		}
	}

	public List<UPunishScore> getUPunishScoreList(int userId) {
		return this.upunishscore_dao.getUPunishScoreList(userId);
	}

	public List<UPunishScore> getUPunishScoreList(PunishQueryParam param, Pager pager){
		return this.upunishscore_dao.getUPunishScoreList(param,pager);
	}
}
