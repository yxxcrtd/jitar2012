package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.jitar.model.ArticleModelEx;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.pojos.GroupArticle;
import cn.edustar.jitar.pojos.UPunishScore;
import cn.edustar.jitar.service.ArticleQueryParam;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.GroupArticleQueryParam;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.UPunishScoreService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 群组文章管理.
 * 
 *
 */
public class GroupArticleAction extends BaseGroupAction {

	/** serialVersionUID */
	private static final long serialVersionUID = -3023236680782211146L;
	private CategoryService cate_svc;
	private UPunishScoreService pun_svc;
	private MessageService msg_svc;
	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}
	public void setUPunishScoreService(UPunishScoreService pun_svc) {
		this.pun_svc = pun_svc;
	}	
	public void setMessageService(MessageService msg_svc) {
		this.msg_svc = msg_svc;
	}		
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		
		// 验证登录和在群组中.
		if (isUserLogined() == false)
			return LOGIN;
		
		if (hasCurrentGroupAndMember() == false)
			return ERROR;

		String uuid=group_svc.getGroupCateUuid(group_model);
		if(uuid.equals(CategoryService.GROUP_CATEGORY_GUID_KTYJ)){
			setRequestAttribute("isKtGroup", "1");
		}
		else if(uuid.equals(CategoryService.GROUP_CATEGORY_GUID_JTBK)){
			setRequestAttribute("isKtGroup", "2");
		}
		else{
			setRequestAttribute("isKtGroup", "0");
		}
		
		if (cmd == null || cmd.length() == 0)
			cmd = "list";

		if ("list".equals(cmd))
			return list();
		else if ("unref".equals(cmd))
			return unref();
		else if ("rcmd".equals(cmd))
			return rcmd();
		else if ("unrcmd".equals(cmd))
			return unrcmd();
		else if ("best".equals(cmd))
			return best();
		else if ("unbest".equals(cmd))
			return unbest();
		else if ("stat".equals(cmd))
			return stat();
		else if ("move".equals(cmd))
			return move();
		else if ("addscore".equals(cmd))
			return addscore();
		else if ("minusscore".equals(cmd))
			return minusscore();		
		return unknownCommand(cmd);
	}
	
	private String addscore(){
        int score = param_util.safeGetIntParam("add_score");
        if(score>0){
            score = score*-1;
        }
        String score_reason = param_util.safeGetStringParam("add_score_reason");
		List<Integer> ids = param_util.safeGetIntValues("articleId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的文章");
			return ERROR;
		}

		// 循环每个文章进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到文章.
			GroupArticle ga = group_svc.getGroupArticleByGroupAndArticle(group_model.getGroupId(), id);
			if (ga == null) {
				addActionError("未找到指定标识的文章: " + id);
				continue;
			}
				
			UPunishScore upun =this.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(),ga.getArticleId(),ga.getUserId(),Float.parseFloat(""+score),score_reason,getLoginUser().getUserId(),getLoginUser().getTrueName());
            this.pun_svc.saveUPunishScore(upun)	;	
			
			++oper_count;

			addActionMessage("文章 '" + ga.getTitle() + "' 成功加分");
		}

		addActionMessage("共 " + oper_count + " 篇文章成功加分");

		return SUCCESS;
	}
	
	private String minusscore(){
        int score = param_util.safeGetIntParam("minus_score");
        if(score<0){
            score = score*-1;
        }
        String score_reason = param_util.safeGetStringParam("minus_score_reason");
		List<Integer> ids = param_util.safeGetIntValues("articleId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的文章");
			return ERROR;
		}

		// 循环每个文章进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到文章.
			GroupArticle ga = group_svc.getGroupArticleByGroupAndArticle(group_model.getGroupId(), id);
			if (ga == null) {
				addActionError("未找到指定标识的文章: " + id);
				continue;
			}
				
			UPunishScore upun =this.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_ARTICLE.getTypeId(),ga.getArticleId(),ga.getUserId(),Float.parseFloat(""+score),score_reason,getLoginUser().getUserId(),getLoginUser().getTrueName());
            this.pun_svc.saveUPunishScore(upun)	;	
            
            Message message = new Message();
            message.setSendId(getLoginUser().getUserId());
            message.setReceiveId(ga.getUserId());
            message.setTitle("管理员删除了您的备课文章及扣分信息");
            if(score_reason != null && score_reason.length()>0){
                message.setContent("您的文章 " + ga.getTitle() + " 被删除,扣" + score + "分,原因:" + score_reason);
            }
            else{  
            	message.setContent("您的文章 " + ga.getTitle() + " 被删除,扣" + score + "分");
            }
            msg_svc.sendMessage(message);
           
			addActionMessage("文章 '" + ga.getTitle() + "' 被罚分");

			//删除文章
            Article art= this.art_svc.getArticle(ga.getArticleId());
            //art_svc.deleteArticle(art)
            this.art_svc.crashArticle(art);
            
			++oper_count;

		}

		addActionMessage("共 " + oper_count + " 篇文章被罚分");

		return SUCCESS;		
	}	
	/**
	 * 分页列出群组文章.
	 * 
	 * @return
	 */
	private String list() {
		// 构造分页对象, 准备查询.
		Pager pager = getCurrentPager();
		pager.setItemNameAndUnit("文章", "篇");	

		GroupArticleQueryParam param = new GroupArticleQueryParam();
		param.k = param_util.safeGetStringParam("k", null);
		param.groupCateId = param_util.getIntParamZeroAsNull("gcid");
		
		// 进行查询.
		List<GroupArticle> article_list = group_svc.getGroupArticleList(group_model.getGroupId(), param, pager);

		setRequestAttribute("gcid", param.groupCateId);
		setRequestAttribute("k", param.k);
		
		setRequestAttribute("group", group_model);
		setRequestAttribute("article_list", article_list);
		setRequestAttribute("pager", pager);

		// 列出群组的文章分类树.
		CategoryTreeModel res_cate = cate_svc.getCategoryTree(getGroupArticleCategoryItemType());
		setRequestAttribute("res_cate", res_cate);
		setRequestAttribute("returnPage",param_util.getStringParam("returnPage",null));
		return LIST_SUCCESS;
	}

	private List<Integer> article_ids;
	
	/** 得到要操作的文章标识集合. */
	private boolean getArticleIdsParam() {
		this.article_ids = param_util.safeGetIntValues("articleId");
		if (article_ids == null || article_ids.size() == 0) {
			addActionError("未选择任何要操作的文章");
			return false;
		}
		return true;
	}
	
	private boolean isGroupCategory(int groupCateId) {
		Category cate = cate_svc.getCategory(groupCateId);
		if (cate == null)
			return false;
		
		String itemType = this.getGroupArticleCategoryItemType();
		if (itemType.equals(cate.getItemType()) == false)
			return false;
		
		return true;
	}
	
	private String move() {
		// 得到参数.
		if (getArticleIdsParam() == false) return ERROR;
		Integer groupCateId = param_util.getIntParamZeroAsNull("groupCateId");
		// 验证分类合法性.
		if (groupCateId != null) {
			if (isGroupCategory(groupCateId) == false) {
				addActionError("非法的目标分类");
				return ERROR;
			}
		}
		
		// 循环每个文章进行操作.
		int oper_count = 0;
		for (Integer id : this.article_ids) {
			// 得到文章.
			GroupArticle ga = group_svc.getGroupArticleByGroupAndArticle(group_model.getGroupId(), id);
			if (ga == null) {
				addActionError("未找到指定标识的文章: " + id);
				continue;
			}
			
			group_svc.updateGroupArticleCategory(ga, groupCateId);
			++oper_count;
			
			addActionMessage("文章 '" + ga.getTitle() + "' 移动到了新分类(或设置为未分类)");
		}
		
		addActionMessage("共 " + oper_count + " 个移动到了新分类");
		
		return SUCCESS;
	}
	
	/**
	 * 解除对文章的引用.
	 * 
	 * @return
	 */
	private String unref() {
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("articleId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的文章");
			return ERROR;
		}

		// 循环每个文章进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到文章.
			Tuple<Article, GroupArticle> tuple = group_svc.getGroupArticleByArticleId(group_model._getGroupObject(), id);
			if (tuple == null) {
				addActionError("未找到指定标识的文章: " + id);
				continue;
			}

			// 消除其引用, 也即删除 GroupArticle 对象.
			group_svc.deleteGroupArticle(tuple.getValue());
			++oper_count;

			addActionMessage("文章 '" + tuple.getKey().getTitle() + "' 已经从群组中移除");
		}

		addActionMessage("共 " + oper_count + " 篇文章从群组中移除");

		// 更新群组文章数.
		group_svc.updateGroupArticleCount(group_model._getGroupObject());

		return SUCCESS;
	}

	
	private String rcmd(){
		List<Integer> ids = param_util.safeGetIntValues("articleId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的文章");
			return ERROR;
		}
		// 循环每个文章进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到文章.
			Article article = art_svc.getArticle(id);
			if (article == null) {
				addActionError("未找到指定标识的文章: " + id);
				continue;
			}
	      if (article.getAuditState()!= Article.AUDIT_STATE_OK){
	      	addActionError("文章未通过审核, 不能设置为推荐文章:"+id);
	      	continue;
	      }	      

			// 设置为平台推荐.
	       art_svc.rcmdArticle(article);
			++oper_count;
			addActionMessage("文章 '" + article.getTitle() + "' 成功设置为平台推荐文章");
		}

		addActionMessage("共 " + oper_count + " 篇文章设置为平台推荐文章");

		return SUCCESS;
	}
	
	private String unrcmd(){
		List<Integer> ids = param_util.safeGetIntValues("articleId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的文章");
			return ERROR;
		}
		// 循环每个文章进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到文章.
			Article article = art_svc.getArticle(id);
			if (article == null) {
				addActionError("未找到指定标识的文章: " + id);
				continue;
			}
	      if (article.getRecommendState()== false){
	      	addActionError("文章"+ article.getTitle()  +"未被推荐, 无需取消推荐");
	      	continue;
	      }
			// 取消平台推荐.
	       art_svc.unrcmdArticle(article);
			++oper_count;
			addActionMessage("文章 '" + article.getTitle() + "' 取消平台推荐文章");
		}

		addActionMessage("共 " + oper_count + " 篇文章取消平台推荐文章");

		return SUCCESS;
	}	
	/**
	 * 设置为群组精华文章.
	 * 
	 * @return
	 */
	private String best() {
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("articleId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的文章");
			return ERROR;
		}

		// 循环每个文章进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到文章.
			GroupArticle ga = group_svc.getGroupArticleByGroupAndArticle(group_model.getGroupId(), id);
			if (ga == null) {
				addActionError("未找到指定标识的文章: " + id);
				continue;
			}

			// 设置为精华.
			group_svc.bestGroupArticle(ga);
			++oper_count;

			addActionMessage("文章 '" + ga.getTitle() + "' 成功设置为群组精华文章");
		}

		addActionMessage("共 " + oper_count + " 篇文章设置为群组精华文章");

		return SUCCESS;
	}

	/**
	 * 取消群组精华文章.
	 * 
	 * @return
	 */
	private String unbest() {
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("articleId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的文章");
			return ERROR;
		}

		// 循环每个文章进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到文章.
			GroupArticle ga = group_svc.getGroupArticleByGroupAndArticle(group_model.getGroupId(), id);
			if (ga == null) {
				addActionError("未找到指定标识的文章: " + id);
				continue;
			}

			// 设置为精华.
			group_svc.unbestGroupArticle(ga);
			++oper_count;

			addActionMessage("文章 '" + ga.getTitle() + "' 取消了群组精华文章");
		}

		addActionMessage("共 " + oper_count + " 篇文章被取消了精华设置");

		return SUCCESS;
	}

	/**
	 * 重新统计协作组中文章数量.
	 * 
	 * @return
	 */
	private String stat() {
		// 重新统计.
		group_svc.updateGroupStatInfo(group_model._getGroupObject());

		// 重新获取群组信息.
		Group group = group_svc.getGroup(group_model.getGroupId());

		addActionMessage("协作组 " + group.toDisplayString() + " 的文章数量为 " + group.getArticleCount());

		return SUCCESS;
	}
	
	/** 计算群组文章树分类名字 */
	private String getGroupArticleCategoryItemType() {
		return CommonUtil.toGroupArticleCategoryItemType(group_model.getGroupId());
	}
}
