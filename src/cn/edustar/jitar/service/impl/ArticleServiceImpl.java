package cn.edustar.jitar.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;

import cn.edustar.data.DataRow;
import cn.edustar.data.DataSchema;
import cn.edustar.data.DataTable;
import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.dao.ArticleDao;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Grade;
import cn.edustar.jitar.pojos.HtmlArticleBase;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.WeekCommentArticle;
import cn.edustar.jitar.pojos.WeekViewCountArticle;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.EventManager;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.SpecialSubjectService;
import cn.edustar.jitar.service.StatService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.UserArticleQueryParam;
import cn.edustar.jitar.service.ViewCountService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 获取用户文章的服务实现
 * 
 * @author Administrator
 */
public class ArticleServiceImpl extends DocumentServiceImpl implements ArticleService {

	/** 博文的数据访问对象 */
	private ArticleDao art_dao;

	/** 事件服务 */
	private EventManager evt_mgr;

	/** 标签服务 */
	private TagService tag_svc;

	/** 评论服务 */
	private CommentService cmt_svc;
	
	/** 统计服务 */
	private StatService stat_svc;
	
	/** 学科服务 */
	private SubjectService subj_svc;

	/**点击率服务*/
	
	private ViewCountService viewcount_svc;
	
	private PrepareCourseService prepareCourseService;
	
	private CacheService cacheService;
	
	@SuppressWarnings("unused")
	private SpecialSubjectService specialSubjectService;
	
	
	/**
	 * 缺省构造函数.
	 */
	public ArticleServiceImpl() {

	}

	/** 数据访问对象. */
	public void setArticleDao(ArticleDao articleDao) {
		this.art_dao = articleDao;
	}

	/** 事件服务 */
	public void setEventManager(EventManager evt_mgr) {
		this.evt_mgr = evt_mgr;
	}

	/** 标签服务 */
	public void setTagService(TagService tag_svc) {
		this.tag_svc = tag_svc;
	}
	
	
	/** 点击率服务 */
	public void setViewCountService(ViewCountService viewcount_svc) {
		this.viewcount_svc = viewcount_svc;
	}

	/** 评论服务 */
	public void setCommentService(CommentService cmt_svc) {
		this.cmt_svc = cmt_svc;
	}

	/** 统计服务 */
	public void setStatService(StatService stat_svc) {
		this.stat_svc = stat_svc;
	}

	/** 学科服务 */
	public void setSubjectService(SubjectService subj_svc) {
		this.subj_svc = subj_svc;
	}
	
	public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    /**
	 * 得到一篇文章
	 */
	public Article getArticle(int articleId) {
		return art_dao.getArticle(articleId);
	}
	
	public List<Article> getTodayArticles(int userId){
		return art_dao.getTodayArticles(userId);
	}
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.ArticleService#getArticleAndUser(int)
	 */
	public Tuple<Article, User> getArticleAndUser(int articleId) {
		return art_dao.getArticleAndUser(articleId);
	}

	/** 规范化 article 中某些参数, 如 null 用 "" 安全的代替等. */
	private void canocializeArticle(Article article) {
		// categoryId = 0 当作 null 处理
		if (CommonUtil.isZeroOrNull(article.getSysCateId()))
			article.setSysCateId(null);

		if (CommonUtil.isZeroOrNull(article.getUserCateId()))
			article.setUserCateId(null);


		// 标签规范化
		String[] tags = tag_svc.parseTagList(article.getArticleTags());
		article.setArticleTags(CommonUtil.standardTagsString(tags));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.IArticleService#addArticle(cn.edustar.jitar.model.Article)
	 */
	public void createArticle(Article article) {
		checkArticleParameter(article);

		// 规范化 article 中某些参数, 如 null 用 "" 安全的代替等
		canocializeArticle(article);

		// 检查分类存在和合法
		checkRefObject(article);

		// 物理创建文章对象
		art_dao.createArticle(article);

		// 写入标签, 如果有的话
		String[] tags = tag_svc.parseTagList(article.getArticleTags());
		if (tags.length > 0) {
			tag_svc.createUpdateMultiTag(article.getArticleId(), ObjectType.OBJECT_TYPE_ARTICLE, tags, null);
		}

		// 根据审核状态, 更新一些统计数据
		if (isArticleNeedStat(article))
			updateArticleStatInfo(article, 1);

		Integer userCateId = article.getUserCateId();
        if(userCateId != null){
            this.art_dao.updateUserCategoryArticleCount(userCateId);
        }
        
		// 发布创建事件
		publishEvent(EVENT_ARTICLE_CREATED, article);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.ArticleService#updateArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void updateArticle(Article article) {
		checkArticleParameter(article);
		
		// 得到原始 article 对象.
		Article origin_article = art_dao.getArticle(article.getArticleId());
		if (origin_article == null)
			throw new RuntimeException("无法找到原文章");
		
		art_dao.evict(origin_article);

		// 设置属性, 有些属性不依赖从外部传递.
		article.setLastModified(new Date());
		// / article.setUSN(article.getUSN() + 1);

		// 规范化 article 中某些参数, 如 null 用 "" 安全的代替等.
		canocializeArticle(article);

		// 检查分类存在和合法.
		checkRefObject(article);

		// 物理修改文章对象.
		art_dao.updateArticle(article);

		// 更改 tags.
		if (article.getTags().equals(origin_article.getTags()) == false) {
			String[] old_tags = tag_svc.parseTagList(origin_article.getTags());
			String[] new_tags = tag_svc.parseTagList(article.getTags());
			tag_svc.createUpdateMultiTag(article.getArticleId(), ObjectType.OBJECT_TYPE_ARTICLE, new_tags, old_tags);
		}

		// 根据状态, 更新一些统计数据. TODO: 可能学科发生变化, 导致学科文章数统计不准确.
		if (this.isArticleNeedStat(article)) {
			if (this.isArticleNeedStat(origin_article) == false)
				this.updateArticleStatInfo(article, 1);
		} else {
			if (this.isArticleNeedStat(origin_article))
				this.updateArticleStatInfo(article, -1);
		}
		
		Integer userCateId = article.getUserCateId();
        if(userCateId != null){
            this.art_dao.updateUserCategoryArticleCount(userCateId);
        }
        String cacheKey = "article_" + article.getArticleId();
        this.cacheService.remove(cacheKey);
	}

	/** 检查文章所给分类、学科等相关对象的存在和合法性. */
	private void checkRefObject(Article article) {
		// 验证系统分类存在性
		if (article.getSysCateId() != null) {
			Category sys_cate = cate_svc.getCategory(article.getSysCateId());
			if (sys_cate == null)
				throw new RuntimeException("找不到指定标识的系统分类");
		}

		// 验证用户分类存在, 以及是文章所属用户的.
		if (article.getUserCateId() != null) {
			Category user_cate = cate_svc.getCategory(article.getUserCateId());
			String itemType = CommonUtil.toUserArticleCategoryItemType(article.getUserId());
			if (user_cate == null || itemType.equals(user_cate.getItemType()) == false)
				throw new RuntimeException("不正确的用户分类.");
		}
		
		// 检测元学科是否存在.
		Integer subjectId = article.getSubjectId();
		if (subjectId != null) {
			MetaSubject msubj = subj_svc.getMetaSubjectById(subjectId);
			if (msubj == null)
				throw new RuntimeException("不正确的元学科类型.");
		}
		
		// 检测学段是否存在.
		if (article.getGradeId() != null) {
			Grade grade = subj_svc.getGrade(article.getGradeId());
			if (grade == null)
				throw new RuntimeException("不正确的学段或年级.");
		}
	}

	/**
	 * 更新文章统计数据
	 * 
	 * @param intCount - 增加计数则 +1, 减少计数则为 -1.
	 */
	private void updateArticleStatInfo(Article article, int incCount) {
		if (incCount == 0) return;		
		stat_svc.incArticleCount(article, incCount);
	}

	
	/**
	 * 判断指定的文章是否需要计算其统计数量
	 * 
	 * @param article
	 * @return
	 */
	private boolean isArticleNeedStat(Article article) {
		// 被删除的文章不计入统计
		if (article.getDelState())
			return false;
		// 草稿不计入统计.
		if (article.getDraftState())
			return false;

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ArticleService#deleteArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void deleteArticle(Article article) {
		checkArticleParameter(article);

		prepareCourseService.deleteArticleToPrepareCourseStage(article);
		
		// 如果已经删除了，则不进行操作.
		if (article.getDelState())
			return;

		// 设置其被删除状态.
		int update_count = art_dao.updateArticleDeleteState(article, true);

		// 更新文章数量统计信息, 逻辑为: 如果原来的文章计入统计而现在被删除了，则统计数 -1.
		if (isArticleNeedStat(article))
			this.updateArticleStatInfo(article, -1);
		
		//更改点击率的修改
		viewcount_svc.changeViewCountdDelStatus(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), article.getArticleId(), 1);
		
		Integer userCateId = article.getUserCateId();
		if(userCateId != null){
		    this.art_dao.updateUserCategoryArticleCount(userCateId);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ArticleService#auditArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void auditArticle(Article article) {
		checkArticleParameter(article);

		// 已经审定通过了则不重复进行了.
		if (article.getAuditState() == Article.AUDIT_STATE_OK)
			return;

		// 执行审定通过操作.
		art_dao.updateArticleAuditState(article, Article.AUDIT_STATE_OK);

		// 执行统计更新, 逻辑为: 如果原来的文章不统计, 审核之后统计则统计数 +1.
		if (isArticleNeedStat(article) == false) {
			article.setAuditState(Article.AUDIT_STATE_OK);
			if (isArticleNeedStat(article))
				this.updateArticleStatInfo(article, 1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ArticleService#unauditArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void unauditArticle(Article article) {
		checkArticleParameter(article);

		// 已经是待审定了则不重复.
		if (article.getAuditState() == Article.AUDIT_STATE_WAIT_AUDIT)
			return;

		// 执行操作.
		this.art_dao.updateArticleAuditState(article, Article.AUDIT_STATE_WAIT_AUDIT);

		// 更新文章数量统计信息, 逻辑为: 如果原来的文章计入统计而现在取消了审核，则统计数 -1.
		if (isArticleNeedStat(article))
			this.updateArticleStatInfo(article, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ArticleService#rcmdArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void rcmdArticle(Article article) {
		checkArticleParameter(article);		
		this.art_dao.updateArticleRcmdState(article, true);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ArticleService#unrcmdArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void unrcmdArticle(Article article) {
		checkArticleParameter(article);		
		this.art_dao.updateArticleRcmdState(article, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ArticleService#bestArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void bestArticle(Article article) {
		checkArticleParameter(article);

		if (article.getBestState() == true)
			return;

		// 执行操作
		art_dao.updateArticleBestState(article, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ArticleService#unbestArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void unbestArticle(Article article) {
		checkArticleParameter(article);

		if (article.getBestState() == false)
			return;

		// 执行操作.
		art_dao.updateArticleBestState(article, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ArticleService#hideArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void hideArticle(Article article) {
		checkArticleParameter(article);

		if (article.getHideState() == Article.HIDE_STATE_HIDE)
			return;

		// 执行操作
		art_dao.updateArticleHideState(article, Article.HIDE_STATE_HIDE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ArticleService#unhideArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void unhideArticle(Article article) {
		checkArticleParameter(article);

		if (article.getHideState() == Article.HIDE_STATE_SHOW)
			return;

		// 执行操作
		art_dao.updateArticleHideState(article, Article.HIDE_STATE_SHOW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ArticleService#draftArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void draftArticle(Article article) {
		checkArticleParameter(article);

		if (article.getDraftState() == true)
			return;

		// 执行操作.
		art_dao.updateArticleDraftState(article, true);

		// 更新文章数量统计信息, 逻辑为: 如果原来的文章计入统计而现在设置为草稿，则统计数 -1.
		if (isArticleNeedStat(article))
			this.updateArticleStatInfo(article, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.ArticleService#undraftArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void undraftArticle(Article article) {
		checkArticleParameter(article);

		if (article.getDraftState() == false)
			return;

		art_dao.updateArticleDraftState(article, false);
		
		//以下代码会出错。暂时屏蔽
		if(true) return;
		// 更新文章数量统计信息, 逻辑为: 如果原来的文章不计入统计而现在要计入统计，则统计数 +1.
		if (isArticleNeedStat(article) == false) {
			article.setDraftState(false);
			if (isArticleNeedStat(article))
				this.updateArticleStatInfo(article, +1);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.ArticleService#validArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void validArticle(Article article) {
		checkArticleParameter(article);

		// 更新文章数量统计信息, 逻辑为: 如果原来的文章不计入统计而现在设置为合法，则统计数 +1.
		if (!isArticleNeedStat(article))
			this.updateArticleStatInfo(article, +1);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.ArticleService#invalidArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void invalidArticle(Article article) {
		checkArticleParameter(article);

		// 更新文章数量统计信息, 逻辑为: 如果原来的文章计入统计而现在设置为非法，则统计数 -1.
		if (isArticleNeedStat(article))
			this.updateArticleStatInfo(article, -1);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.ArticleService#recoverArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void recoverArticle(Article article) {
		checkArticleParameter(article);

		// 如果未被删除，则不进行操作.
		if (article.getDelState() == false)
			return;

		// 设置其非删除状态.
		int update_count = art_dao.updateArticleDeleteState(article, false);
		if (update_count != 1) {
			log.warn("recoverArticle id=" + article.getId() + " update_count=" + update_count + ", but MUST be 1");
		}

		// 更新文章数量统计信息, 逻辑为: 如果恢复后要计入统计, 则统计数 +1.
		article.setDelState(false);
		if (isArticleNeedStat(article)) 
			this.updateArticleStatInfo(article, +1);
		
		//更改点击率的修改
		viewcount_svc.changeViewCountdDelStatus(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), article.getArticleId(), 0);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.service.ArticleService#crashArticle(cn.edustar.jitar.pojos.Article)
	 */
	public void crashArticle(Article article) {
		checkArticleParameter(article);
		
		// 更新文章数量统计信息, 逻辑为: 如果以前要计入统计, 则统计数 -1.
		if (isArticleNeedStat(article))
			this.updateArticleStatInfo(article, -1);
		
		// 删除标签.
		tag_svc.deleteTagRefByObject(ObjectType.OBJECT_TYPE_ARTICLE, article.getArticleId());
		
		// 删除其所有评论.
		cmt_svc.deleteCommentByObject(ObjectType.OBJECT_TYPE_ARTICLE, article.getArticleId());
		
		//删除文章的Word文件doc文件以及flash文件
		deleteArticleFiles(article);
		
		// 删除文章.
		art_dao.crashArticle(article);
		
		//删除点击率
		//System.out.println("删除资源点击率");
		viewcount_svc.deleteViewCount(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(), article.getArticleId());
		//System.out.println("删除资源点击率---完成");
	}
	private void deleteArticleFiles(Article article){
		if(null == article){
			return;
		}
		String fileSwf = null;
		String docHref=article.getWordHref();
		if(null != docHref && docHref.length() > 0){
			fileSwf = docHref.substring(0,docHref.lastIndexOf(".")) + ".swf";
		}
		
		if(null != fileSwf && fileSwf.length() > 0){
			//删除swf
			File file = new File(JitarContext.getCurrentJitarContext().getServletContext().getRealPath(fileSwf));
			if(null != file){
				if(file.exists()){
					file.delete();
				}
			}
		}
		
		if(null != docHref && docHref.length() > 0){
			//删除doc
			File file = new File(JitarContext.getCurrentJitarContext().getServletContext().getRealPath(docHref));
			if(null != file){
				if(file.exists()){
					file.delete();
				}
			}
		}
	}
	private static final void checkArticleParameter(Article article) throws IllegalArgumentException {
		if (article == null)
			throw new IllegalArgumentException("article == null");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.service.iface.IArticleService#getArticleDataTable(cn.edustar.jitar.service.ArticleQueryParam)
	 */
	public DataTable getArticleDataTable(ArticleQueryParam para) {
		log.debug("para = " + para);
		throw new java.lang.UnsupportedOperationException("TODO: 暂时不支持");

		/*
		 * logger.debug("getArticleDataTable() para = " + para);
		 *  // 得到博文列表，不含分类名。

		 * 
		 * List list = art_dao.getArticleByParam(para); if (list == null) return
		 * null;
		 *  // 组装为 DataTable DataTable data_table = new DataTable(new
		 * DataSchema(ArticleDao.GET_ARTICLE_FIELDS), list);
		 *  // 合并系统分类名。 categoryName if ((para.getShowCName() == 1) &&
		 * (data_table.size() > 0)) { // 得到里面所使用的所有分类的标识，有可能有 null 的。

		 * 
		 * List<Integer> cateIds = retrieveFields(data_table, "categoryId");
		 *  // 得到这些分类标识的名字。

		 * 
		 * Map<Integer, String> name_map =
		 * categoryDao.getCategoryNames(cateIds);
		 *  // 给 DataTable 添加一个新的字段 cateoryName，并得到其索引。

		 * 
		 * data_table.getSchema().addColumn("categoryName");
		 *  // 合并分类名称进入 DataTable. joinField(data_table, "categoryId",
		 * "categoryName", name_map); }
		 *  // 合并用户分类名。

		 * 
		 * if ((para.getShowSName() == 1) && (data_table.size() > 0)) { //
		 * 得到里面所使用的所有分类的标识，有可能有 null 的。

		 * 
		 * List<Integer> stapleIds = retrieveFields(data_table, "stapleId");
		 * 
		 * Map<Integer, String> name_map = stapleDao.getStapleNames(stapleIds);
		 * 
		 * data_table.getSchema().addColumn("stapleName");
		 * 
		 * joinField(data_table, "stapleId", "stapleName", name_map); }
		 * 
		 * return data_table;
		 */
	}

	/**
	 * 获取指定的数据表中指定字段，并将其组装为一个 List 返回.
	 * 
	 * @param dt
	 * @param column_name
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static final List retrieveFields(DataTable dt, String column_name) {
		int column_index = dt.getSchema().getColumnIndex(column_name);
		if (column_index == -1)
			throw new IndexOutOfBoundsException("指定的数据表没有名为 '" + column_name + "' 的属性。");

		List list = new ArrayList();
		for (int i = 0; i < dt.size(); ++i) {
			list.add(dt.get(i).get(column_index));
		}
		return list;
	}

	/**
	 * 向指定的 DataTable 中合并字段 newField, 根据 keyField 值的比较进行.
	 * 
	 * @param dt 要执行操作的数据表
	 * @param keyField 用于比较的键字段，满足 dt.row.keyField == kvMap.key
	 * @param valueField 要设置的值字段
	 * @param kvMap
	 */
	@SuppressWarnings("rawtypes")
    public static final void joinField(DataTable dt, String keyField,
			String valueField, Map kvMap) {
		if (dt == null || kvMap == null)
			return;

		// 参数准备.
		int key_index = dt.getSchema().getColumnIndex(keyField);
		if (key_index == -1)
			throw new IndexOutOfBoundsException("指定的数据表没有名为 '" + keyField + "' 的属性。");
		int value_index = dt.getSchema().getColumnIndex(valueField);
		if (value_index == -1)
			throw new IndexOutOfBoundsException("指定的数据表没有名为 '" + valueField + "' 的属性。");

		// 为每个记录行设置其 value, 条件是 key 相同.
		for (int i = 0; i < dt.size(); ++i) {
			DataRow row = dt.get(i);
			Object key = row.get(key_index);
			Object value = kvMap.get(key); // 从 Map 中找到对应 Key 的 Value.
			row.set(value_index, value);
		}
	}

	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<ArticleModelEx> getArticleList(ArticleQueryParam param, Pager pager) {
		if (param == null)
			throw new IllegalArgumentException("param == null");
		if (param.retrieveSystemCategory == true
				|| param.retrieveUserCategory == true)
			return (List<ArticleModelEx>) (List) getArticleListEx(param, pager);
		else
			return getSimpleArticleList(param, pager);
	}

	
	public DataTable getUserArticleDataTable(UserArticleQueryParam param, Pager pager) {
		// 获得原始数据.
		List<Object[]> list = art_dao.getUserArticleDataTable(param, pager);
		
		// 组装为 DataTable.
		DataTable dt = new DataTable(new DataSchema(param.selectFields));
		dt.addList(list);
		
		// 合并 retrieveSystemCategory, retrieveUserCategory.
		if (param.retrieveSystemCategory) {
			dt.getSchema().add("systemCategory");
			super.joinCategory(dt, new DtCateSupport("sysCateId", "systemCategory"), null);
		}
		if (param.retrieveUserCategory) {
			dt.getSchema().add("userCategory");
			super.joinCategory(dt, new DtCateSupport("userCateId", "userCategory"), null);
		}
		
		return dt;
	}

	/** 不提取用户、系统分类、用户分类时候的简单查询. */
	private List<ArticleModelEx> getSimpleArticleList(ArticleQueryParam param, Pager pager) {
		List<Article> art_list = this.art_dao.getArticleList(param, pager);

		// 组装为 ArticleModel 对象类型.
		List<ArticleModelEx> new_list = new ArrayList<ArticleModelEx>(art_list.size());
		for (int i = 0; i < art_list.size(); ++i) {
			new_list.add(ArticleModelEx.wrap(art_list.get(i)));
		}
		return new_list;
	}

	@SuppressWarnings("unchecked")
	private List<ArticleModelEx> wrapArticleModelEx(List<Article> list) {
		if (list == null || list.size() == 0)
			return CommonUtil.EMPTY_LIST;

		List<ArticleModelEx> model_list = new ArrayList<ArticleModelEx>(list.size());
		for (Article article : list) {
			model_list.add(ArticleModelEx.wrap(article));
		}
		return model_list;
	}

	
	public List<ArticleModelEx> getArticleListEx(ArticleQueryParam param, Pager pager) {
		// 先查询简单 Article
		List<Article> list = this.art_dao.getArticleList(param, pager);
		List<ArticleModelEx> model_list = wrapArticleModelEx(list);
		if (model_list.size() == 0)
			return model_list;

		// 合并系统分类.
		if (param.retrieveSystemCategory) {
			super.joinCategory(model_list, new AmeSysCateSupport(), null);
		}

		// 合并用户分类.
		if (param.retrieveUserCategory) {
			super.joinCategory(model_list, new AmeUserCateSupport(), null);
		}

		return model_list;
	}

	/** ArticleModelEx System CategorySupport implement. */
	private static final class AmeSysCateSupport implements CategorySupport {
		public Integer getCategoryId(Object o) {
			return ((ArticleModelEx) o).getSysCateId();
		}

		public void setCategory(Object o, Category category) {
			((ArticleModelEx) o).setSystemCategory(category);
		}
	}

	/** ArticleModelEx User CategorySupport implement. */
	private static final class AmeUserCateSupport implements CategorySupport {
		public Integer getCategoryId(Object o) {
			return ((ArticleModelEx) o).getUserCateId();
		}

		public void setCategory(Object o, Category category) {
			((ArticleModelEx) o).setUserCategory(category);
		}
	}

	/** 对外发布一个事件, this 做为发布者 */
	private void publishEvent(String eventName, Object eventObject) {
		if (evt_mgr == null) {
			if (log.isDebugEnabled())
				log.debug("event service is null when publishEvent: " + eventName);
			return;
		}
		evt_mgr.publishEvent(eventName, this, eventObject);
	}

	
	public void createArticleComment(Article article, Comment comment) {
		// 设置必要属性.
		comment.setObjType(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId());
		comment.setObjId(article.getArticleId());

		// 保存评论.
		cmt_svc.saveComment(comment);

		// 添加文章评论数.
		art_dao.incArticleCommentCount(comment, +1);
	}

	public HtmlArticleBase getHtmlArticleBaseByArticleId(int articleId)
	{
		return	this.art_dao.getHtmlArticleBaseByArticleId(articleId);
	}
	public HtmlArticleBase getHtmlArticleBaseByArticleGuid(String articleGuid)
	{
		return	this.art_dao.getHtmlArticleBaseByArticleGuid(articleGuid);
	}
	
	public void batchClearArticleUserCategory(int categoryId) {
		int count = art_dao.batchClearArticleUserCategory(categoryId);
		if (log.isDebugEnabled()) {
			log.debug("batchClearArticleUserCategory categoryId = " + categoryId + ", update_count = " + count);
		}
	}


	public void increaseViewCount(int articleId, int count) {
		art_dao.increaseViewCount(articleId, count);
	}

	
	public void incArticleCommentCount(Comment comment, int count) {
		art_dao.incArticleCommentCount(comment, count);
	}

	
	public Object statForUser(User user) {
		return art_dao.statForUser(user);
	}
	

	public void statComment(User user) {
		art_dao.statComment(user);
	}


	public void moveArticleCategory(Article article) {
		art_dao.updateArticleCategory(article);
	}
	
	public void moveArticleCategory2(int articleId,Integer userCateId,Integer sysCateId)
	{
		art_dao.moveArticleCategory2(articleId, userCateId, sysCateId);
	}
	

	public void moveArticleSysCate(Article article, Integer sysCateId) {
		art_dao.moveArticleSysCate(article, sysCateId);
	}

	public PrepareCourseService getPrepareCourseService() {
		return prepareCourseService;
	}

	public void setPrepareCourseService(PrepareCourseService prepareCourseService) {
		this.prepareCourseService = prepareCourseService;
	}
	

	@SuppressWarnings({"unchecked", "rawtypes"})
	public List getArticleList_Order_By_CommentNums(int days,int topNum){
		return art_dao.getArticleList_Order_By_CommentNums(days, topNum);
	}
	
	public List<WeekCommentArticle> getWeekCommentArticleList()
	{
		return art_dao.getWeekCommentArticleList();
	}
	public List<WeekViewCountArticle> getWeekViewCountArticleList()
	{
		return art_dao.getWeekViewCountArticleList();
	}
	
	public List<WeekViewCountArticle> getWeekViewCountArticleList(int topCount){
	    return art_dao.getWeekViewCountArticleList(topCount);
	}
	
	public void setSpecialSubjectService(SpecialSubjectService specialSubjectService) {
		this.specialSubjectService = specialSubjectService;
	}
	

    public void setPushState(Article article, int pushState)
	{
		this.art_dao.setPushState(article, pushState);
	}
	
	public List<Article> getAllUserArticle(int UserId)
	{
		return this.art_dao.getAllUserArticle(UserId);
	}
	
	public void deleteArticleWithRelativeData(int articleId, int year)
	{
		this.art_dao.deleteArticleWithRelativeData(articleId, year);		
	}
	
	public void addDigg(int articleId){
		this.art_dao.addDigg(articleId);
	}
	public void addTrample(int articleId){
		this.art_dao.addTrample(articleId);
	}
	public int getDigg(int articleId){
		return this.art_dao.getDigg(articleId);
	}
	public int getTrample(int articleId)
	{
		return this.art_dao.getTrample(articleId);
	}
	
	public void updateGroupArticleState(HtmlArticleBase htmlArticleBase)
	{
		this.art_dao.updateGroupArticleState(htmlArticleBase);
	}
	public void updateChannelArticleState(HtmlArticleBase htmlArticleBase)
	{
		this.art_dao.updateChannelArticleState(htmlArticleBase);
	}
	public void updateSpecialSubjectArticleState(HtmlArticleBase htmlArticleBase)
	{
		this.art_dao.updateSpecialSubjectArticleState(htmlArticleBase);
	}
	public void updatePrepareCourseArticleState(HtmlArticleBase htmlArticleBase)
	{
		this.art_dao.updatePrepareCourseArticleState(htmlArticleBase);
	}
	public void updateGroupArticleState(Article article)
	{
		this.art_dao.updateGroupArticleState(article);
	}
	public void updateChannelArticleState(Article article)
	{
		this.art_dao.updateChannelArticleState(article);
	}
	public void updateSpecialSubjectArticleState(Article article)
	{
		this.art_dao.updateSpecialSubjectArticleState(article);
	}
	public void updatePrepareCourseArticleState(Article article)
	{
		this.art_dao.updatePrepareCourseArticleState(article);
	}
	
	public List<Article> getHotArticleList(int topCount){
	    return this.art_dao.getHotArticleList(topCount);
	}
}
