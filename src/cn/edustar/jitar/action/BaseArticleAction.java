package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Tuple;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.PrivilegeContent;

/**
 * 文章管理的基类, 即可以在前台使用, 也可以在后台使用
 *
 * 当前有两个子类: ArticleAction - 前台用户使用的文章管理. AdminArticleAction - 管理员使用的文章管理
 */
public abstract class BaseArticleAction extends ManageDocBaseAction {
	
	/** serialVersionUID */
	private static final long serialVersionUID = -3109609060837705351L;
	
	/** 文章服务 */
	protected ArticleService art_svc;
	
	/** 文章服务的set方法 */
	public void setArticleService(ArticleService art_svc) {
		this.art_svc = art_svc;
	}
	
	public PrepareCourseService prepareCourseService;
	
	/**
	 * 构造
	 */
	protected BaseArticleAction() {
		JitarContext jtar_ctxt = JitarContext.getCurrentJitarContext();
		if (jtar_ctxt != null) {
			this.art_svc = jtar_ctxt.getArticleService();
		}
	}
	
	/** 在 delete,audit 等命令中使用的文章参数集合, 从提交的 'id' 参数中获取 */
	protected List<Integer> art_ids;
	
	/**
	 * 从参数中获取 id 集合, 结果放在 art_ids 成员中
	 * 
	 * @return 返回 true 表示有参数; false 表示未给出
	 */
	protected final boolean getArticleIds() {
		this.art_ids = param_util.safeGetIntValues("articleId");
		if (art_ids == null || art_ids.size() == 0 || (art_ids.size() == 1 && art_ids.get(0).intValue() == 0)) {
			this.addActionError("未选择或给出要操作的文章");
			return false;
		}
		return true;
	}

	/**
	 * 逻辑删除一篇或多篇文章(设置文章的删除标志).
	 */
	protected final String delete() {
		// 得到要操作的文章标识集合.
		if (getArticleIds() == false)
			return ERROR;
		
		// 为每个文章执行.
		int oper_count = 0;		for (Integer aid : this.art_ids) {
			
			// 获得文章对象.
			Article article = art_svc.getArticle(aid);
			if (article == null) {
				addActionError("未找到标识为 " + aid + " 的文章");
				continue;
			}
			
			
			// 检查权限.
			//if (checkRightAndSetError(tuple, "delete", "删除") == false)
			//	continue;
			
			if(article.getUserId() != this.getLoginUser().getUserId())
			{
				addActionError("无权删除 " + aid + " 的文章");
				continue;
			}
			
			// 逻辑检查 - 文章已经被删除, 不要重复删除.
			if (article.getDelState() == true) {
				addActionMessage("文章 " + article.toDisplayString() + " 已经被删除, 您可以在回收站中对其进行管理.");
				continue;
			}
			
			// 删除文章.
			art_svc.deleteArticle(article);
			
			++oper_count;
			
			addActionMessage("文章 " + article.toDisplayString() + "' 删除操作成功完成, 该文章已被放入了回收站.");
		}
		addActionMessage("共删除了 " + oper_count + " 篇文章");
		
		return SUCCESS;
	}

	/**
	 * 审核通过一篇个或多篇文章
	 * 
	 * @return
	 */
	protected final String audit() {
		// 得到要操作的文章标识集合.
		if (getArticleIds() == false)
			return ERROR;
		
		// 为每个文章执行.
		int oper_count = 0;
		for (Integer aid : this.art_ids) {
			// 获得文章对象.
			Tuple<Article, User> tuple = art_svc.getArticleAndUser(aid);
			if (tuple == null) {
				addActionError("未找到标识为 " + aid + " 的文章");
				continue;
			}
			Article article = tuple.getKey();
			
			// 检查权限.
			if (checkRightAndSetError(tuple, "audit", "审核通过") == false)
				continue;

			// 逻辑判断, 文章不能是草稿、已删除状态.
			if (canAudit(article) == false) {
				addActionError("文章 " + article.toDisplayString() + " 因为是草稿或非法或已删除, 从而不能进行审核操作.");
				continue;
			}
			
			if (article.getAuditState() == Article.AUDIT_STATE_OK) {
				addActionError("文章 " + article.toDisplayString() + " 已经审核通过, 没有进行再次审核操作.");
				continue;
			}
			
			// 完成审核操作.
			art_svc.auditArticle(article);
			++oper_count;
			
			addActionMessage("文章 " + article.toDisplayString() + " 审核操作成功完成.");
		}
		addActionMessage("共审核了 " + oper_count + " 篇文章");
		
		return SUCCESS;
	}

	/**
	 * 取消审核一篇或多篇文章
	 * 
	 * @return
	 */
	protected final String unaudit() {
		// 得到要操作的文章标识集合.
		if (getArticleIds() == false)
			return ERROR;
		
		// 为每个文章执行.
		int oper_count = 0;
		for (Integer aid : this.art_ids) {
			// 获得文章对象.
			Tuple<Article, User> tuple = art_svc.getArticleAndUser(aid);
			if (tuple == null) {
				addActionError("未找到标识为 " + aid + " 的文章");
				continue;
			}
			Article article = tuple.getKey();

			// 检查权限.
			if (checkRightAndSetError(tuple, "unaudit", "取消审核") == false)
				continue;

			// 逻辑判断.
			if (canUnaudit(article) == false) {
				addActionError("文章 " + article.toDisplayString() + " 因为状态不正确, 而不能进行取消审核操作.");
				continue;
			}
			
			if (article.getAuditState() == Article.AUDIT_STATE_WAIT_AUDIT) {
				addActionError("文章 " + article.toDisplayString() + " 已经是未审核的, 从而没有进行操作.");
				continue;
			}
			
			// 完成取消审核操作.
			art_svc.unauditArticle(article);
			++oper_count;
			
			addActionMessage("文章 " + article.toDisplayString() + " 取消审核操作成功完成.");
		}
		addActionMessage("共对 " + oper_count + " 篇文章进行了取消审核操作");
		
		return SUCCESS;
	}

	/**
	 * 设置精华一篇或多篇文章
	 * 
	 * @return
	 */
	protected final String best() {
		// 得到要操作的文章标识集合.
		if (getArticleIds() == false)
			return ERROR;
		
		// 为每个文章执行.
		int oper_count = 0;
		for (Integer aid : this.art_ids) {
			// 获得文章对象
			Tuple<Article, User> tuple = art_svc.getArticleAndUser(aid);
			if (tuple == null) {
				addActionError("未找到标识为 " + aid + " 的文章");
				continue;
			}
			Article article = tuple.getKey();

			// 检查权限.
			if (checkRightAndSetError(tuple, "best", "设置精华") == false)
				continue;
			
			// 逻辑判断.
			if (article.getBestState() == true) {
				addActionError("文章 " + article.toDisplayString() + " 已经设置为精华文章, 没有进行操作.");
				continue;
			}
			
			// 进行操作
			art_svc.bestArticle(article);
			
			addActionMessage("文章 " + article.toDisplayString() + " 设置为精华操作成功完成");
		}
		addActionMessage("共对 " + oper_count + " 篇文章进行了设置精华操作");
		
		return SUCCESS;
	}

	/**
	 * 取消精华一篇或多篇文章
	 * 
	 * @return
	 */
	protected final String unbest() {
		// 得到要操作的文章标识集合.
		if (getArticleIds() == false)
			return ERROR;
		
		// 为每个文章执行.
		int oper_count = 0;
		for (Integer aid : this.art_ids) {
			// 获得文章对象
			Tuple<Article, User> tuple = art_svc.getArticleAndUser(aid);
			if (tuple == null) {
				addActionError("未找到标识为 " + aid + " 的文章");
				continue;
			}
			Article article = tuple.getKey();

			// 检查权限.
			if (checkRightAndSetError(tuple, "unbest", "取消精华") == false) 
				continue;
			
			// 逻辑判断.
			if (article.getBestState() == false) {
				addActionError("文章 " + article.toDisplayString() + " 不是精华文章, 没有进行操作.");
				continue;
			}
			
			// 进行操作.
			art_svc.unbestArticle(article);
			++oper_count;
			
			addActionMessage("文章 " + article.toDisplayString() + " 取消精华操作成功完成.");
		}
		addActionMessage("共对 " + oper_count + " 篇文章进行了取消精华操作");
		
		return SUCCESS;
	}

	/**
	 * 隐藏一篇或多篇文章
	 * 
	 * @return
	 */
	protected final String hide() {
		// 得到要操作的文章标识集合.
		if (getArticleIds() == false)
			return ERROR;

		// 为每个文章执行.
		int oper_count = 0;
		for (Integer aid : this.art_ids) {

			// 获得文章对象.
			Article article = art_svc.getArticle(aid);
			if (article == null) {
				this.addActionError("未找到标识为 " + aid + " 的文章");
				continue;
			}
			
			// 权限判断.
			//if (checkRightAndSetError(tuple, "hide", "隐藏") == false)
			//	continue;
			if(article.getUserId() != this.getLoginUser().getUserId())
			{
				this.addActionError("不能隐藏标识为 " + aid + " 的文章，自己的文章可以进行隐藏。");
				continue;
			}

			// 逻辑判断.
			if (article.getHideState() == Article.HIDE_STATE_HIDE) {
				this.addActionError("文章 " + article.toDisplayString() + " 已经被隐藏, 没有再次进行隐藏操作");
				continue;
			}

			// 完成隐藏操作.
			art_svc.hideArticle(article);
			++oper_count;
			
			this.addActionMessage("文章 " + article.toDisplayString() + " 隐藏操作成功完成");
		}
		addActionMessage("共对 " + oper_count + " 篇文章进行了隐藏操作");

		return SUCCESS;
	}

	/**
	 * 取消隐藏一篇或多篇文章
	 * 
	 * @return
	 */
	protected final String unhide() {
		// 得到要操作的文章标识集合.
		if (getArticleIds() == false)
			return ERROR;

		// 为每个文章执行.
		int oper_count = 0;
		for (Integer aid : this.art_ids) {
			// 获得文章对象.
			Article article = art_svc.getArticle(aid);
			if (article == null) {
				addActionError("未找到标识为 " + aid + " 的文章");
				continue;
			}
			
			// 权限判断.
			//if (checkRightAndSetError(tuple, "unhide", "取消隐藏") == false)
			//	continue;
			if(article.getUserId() != this.getLoginUser().getUserId())
			{
				this.addActionError("不能取消隐藏标识为 " + aid + " 的文章，自己的文章可以进行取消隐藏。");
				continue;
			}
			// 逻辑判断.
			if (article.getHideState() == Article.HIDE_STATE_SHOW) {
				addActionError("文章 " + article.toDisplayString() + " 未被隐藏, 没有进行取消隐藏操作");
				continue;
			}

			// 完成解除隐藏操作.
			art_svc.unhideArticle(article);
			++oper_count;

			addActionMessage("文章 " + article.toDisplayString() + " 取消隐藏操作成功完成");
		}
		addActionMessage("共对 " + oper_count + " 篇文章进行了取消隐藏操作");

		return SUCCESS;
	}
	
	/**
	 * 设为草稿一篇或多篇文章
	 * 
	 * @return
	 */
	protected final String draft() {
		// 得到要操作的文章标识集合.
		if (getArticleIds() == false)
			return ERROR;

		// 为每个文章执行.
		int oper_count = 0;
		for (Integer aid : this.art_ids) {
			// 获得文章对象.
			Article article = art_svc.getArticle(aid);
			if (article == null) {
				addActionError("未找到标识为 " + aid + " 的文章");
				continue;
			}
			

			// 权限判断.
			//if (checkRightAndSetError(tuple, "draft", "设为草稿") == false)
			//	continue;
			
			if(article.getUserId() != this.getLoginUser().getUserId())
			{
				this.addActionError("不能对标识为 " + aid + " 的文章设置为草稿，自己的文章可以设置为草稿。");
				continue;
			}
			// 逻辑判断.
			if (article.getDraftState() == true) {
				addActionError("文章 " + article.toDisplayString() + " 已经是草稿状态了, 未进行设为草稿操作");
				continue;
			}

			// 完成设为草稿操作.
			art_svc.draftArticle(article);
			++oper_count;

			addActionMessage("文章 " + article.toDisplayString() + " 设为草稿状态成功完成");
		}
		addActionMessage("共对 " + oper_count + " 篇文章进行了设为草稿操作");

		return SUCCESS;
	}

	/**
	 * 取消草稿(设置为正式发布状态)一篇或多篇文章
	 * 
	 * @return
	 */
	protected final String undraft() {
		// 得到要操作的文章标识集合.
		if (getArticleIds() == false)
			return ERROR;

		// 为每个文章执行.
		int oper_count = 0;
		for (Integer aid : this.art_ids) {
			// 获得文章对象.
			Article article = art_svc.getArticle(aid);
			if (article == null) {
				addActionError("未找到标识为 " + aid + " 的文章");
				continue;
			}
			

			// 权限判断.
			//if (checkRightAndSetError(tuple, "draft", "设为草稿") == false)
			//	continue;
			
			if(article.getUserId() != this.getLoginUser().getUserId())
			{
				this.addActionError("不能对标识为 " + aid + " 的文章发布草稿，自己的文章可以发布草稿。");
				continue;
			}

			// 逻辑判断.
			if (article.getDraftState() == false) {
				addActionError("文章 " + article.toDisplayString() + " 已经是发布状态了, 未进行取消草稿操作");
				continue;
			}

			// 完成取消草稿操作.
			art_svc.undraftArticle(article);
			++oper_count;

			addActionMessage("文章 " + article.toDisplayString() + " 取消草稿操作成功完成");
		}
		addActionMessage("共对 " + oper_count + " 篇文章进行了取消草稿操作");

		return SUCCESS;
	}

	
	/**
	 * 彻底删除掉一篇或多篇文章
	 * 
	 * @return
	 */
	protected final String crash() {
		// 得到要操作的文章标识集合.
		if (getArticleIds() == false)
			return ERROR;

		// 为每个文章执行.
		int oper_count = 0;
		for (Integer aid : this.art_ids) {
			// 获得文章对象.
			Tuple<Article, User> tuple = art_svc.getArticleAndUser(aid);
			if (tuple == null) {
				addActionError("未找到标识为 " + aid + " 的文章");
				continue;
			}
			Article article = tuple.getKey();

			// 权限判断.
			if (checkRightAndSetError(tuple, "crash", "彻底删除") == false)
				continue;

			// 完成操作.
			art_svc.crashArticle(article);			
			
			++oper_count;

			addActionMessage("文章 " + article.toDisplayString() + " 彻底删除操作成功完成");
		}
		addActionMessage("共对 " + oper_count + " 篇文章进行了彻底删除操作");

		return SUCCESS;
	}
	
	/**
	 * 恢复一篇或多篇文章
	 * 
	 * @return
	 */
	protected final String recover() {
		// 得到要操作的文章标识集合.
		if (getArticleIds() == false)
			return ERROR;

		// 为每个文章执行.
		int oper_count = 0;
		for (Integer aid : this.art_ids) {
			// 获得文章对象.
			Tuple<Article, User> tuple = art_svc.getArticleAndUser(aid);
			if (tuple == null) {
				addActionError("未找到标识为 " + aid + " 的文章");
				continue;
			}
			Article article = tuple.getKey();

			// 权限判断.
			if (checkRightAndSetError(tuple, "recover", "恢复") == false)
				continue;

			// 逻辑判断.
			if (article.getDelState() == false) {
				addActionError("文章 " + article.toDisplayString() + " 未被删除, 从而没有进行恢复操作");
				continue;
			}

			// 完成恢复操作.
			art_svc.recoverArticle(article);
			++oper_count;

			addActionMessage("文章 " + article.toDisplayString() + " 取消草稿操作成功完成");
		}
		addActionMessage("共对 " + oper_count + " 篇文章进行了取消草稿操作");

		return SUCCESS;
	}
	
	

	/**
	 * 检查权限, 并当检查失败的时候在 action 中添加一条 error 消息
	 * 
	 * @param article_and_user
	 * @param cmd
	 * @param cmd_desc
	 * @return
	 */
	protected boolean checkRightAndSetError(Tuple<Article, User> article_and_user, String cmd, String cmd_desc) {
		boolean result = this.checkRight(article_and_user, cmd);
		if (result == false)
			super.addActionError("权限不足. 对文章 " + article_and_user.getKey().toDisplayString() + 
					" 的" + cmd_desc + "操作因为权限不足而没有执行.");
		return result;
	}
	
	/**
	 * 判断对指定文档能否进行指定操作, 派生类必须实现此方法
	 * 
	 * @param article
	 * @param cmd
	 * @return
	 */
	protected abstract boolean checkRight(Tuple<Article, User> article_and_user, String cmd);

	/**
	 * 对 Article 实现 PrivilegeContent 的包装类.
	 */
	protected static final class ArticlePrivilegeContent implements PrivilegeContent {
		private Tuple<Article, User> article_and_user;
		public ArticlePrivilegeContent(Tuple<Article, User> article_and_user) {
			this.article_and_user = article_and_user;
		}
		public Integer getContentGrade() {
			return article_and_user.getKey().getGradeId();
		}
		public User getContentPublisher() {
			return article_and_user.getValue();
		}
		public Integer getContentSubject() {
			return article_and_user.getKey().getSubjectId();
		}
	}

	/** 判断一个文章能否被审核 */
	protected boolean canAudit(Article article) {
		// 被删除的文章不能被审核.
		if (article.getDelState())
			return false;
		
		// 还在草稿状态, 不能被审核.
		if (article.getDraftState())
			return false;
		
		
		
		return true;
	}

	/**
	 * 判断一个文章能否被取消审核
	 * 
	 * @param article
	 * @return
	 */
	protected boolean canUnaudit(Article article) {
		return true;
	}

	public void setPrepareCourseService(PrepareCourseService prepareCourseService) {
		this.prepareCourseService = prepareCourseService;
	}
	
	public PrepareCourseService getPrepareCourseService( ) {
		return this.prepareCourseService;
	}
	
}
