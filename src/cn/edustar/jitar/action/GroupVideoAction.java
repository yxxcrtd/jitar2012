package cn.edustar.jitar.action;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.model.VideoModel;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupResource;
import cn.edustar.jitar.pojos.GroupVideo;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.Video;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.GroupVideoQueryParam;
import cn.edustar.jitar.query.VideoQueryParam;
import cn.edustar.jitar.service.VideoService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.pojos.UPunishScore;
import cn.edustar.jitar.pojos.Message;
import cn.edustar.jitar.service.MessageService;
import cn.edustar.jitar.service.UPunishScoreService;
@SuppressWarnings("serial")
public class GroupVideoAction extends BaseGroupAction {
	
	/** 分类服务 */
	private CategoryService cate_svc;
	private VideoService video_svc;
	private UPunishScoreService pun_svc;
	private MessageService msg_svc;
	/**加罚分服务*/
	public void setUPunishScoreService(UPunishScoreService pun_svc) {
		this.pun_svc = pun_svc;
	}	
	/**消息服务*/
	public void setMessageService(MessageService msg_svc) {
		this.msg_svc = msg_svc;
	}	
	/** 分类服务 */
	public void setCategoryService(CategoryService cate_svc) {
		this.cate_svc = cate_svc;
	}
	public void setVideoService(VideoService video_svc) {
		this.video_svc = video_svc;
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
        
		List<Integer> ids = param_util.safeGetIntValues("videoId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的视频");
			return ERROR;
		}

		// 循环每个视频进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到视频.
			GroupVideo gv = group_svc.getGroupVideoByGroupAndVideo(group_model.getGroupId(), id);
			if (gv == null) {
				addActionError("未找到指定标识的视频: " + id);
				continue;
			}
			Video v=this.video_svc.findById(gv.getVideoId());
			if(v==null){
				addActionError("未找到指定标识的视频: " + id);
				continue;
			}
			oper_count++;
			UPunishScore upun =this.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(),gv.getVideoId(),gv.getUserId(),Float.parseFloat(""+score),score_reason,getLoginUser().getUserId(),getLoginUser().getTrueName());
            this.pun_svc.saveUPunishScore(upun)	;	
            addActionMessage("视频 '" + v.getTitle() + "' 成功加分");
		}
		addActionMessage("共 " + oper_count + " 个视频成功加分");

		return SUCCESS;
	}
	
	private String minusscore(){
        int score = param_util.safeGetIntParam("minus_score");
        if(score<0){
            score = score*-1;
        }
        String score_reason = param_util.safeGetStringParam("minus_score_reason");
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("videoId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的视频");
			return ERROR;
		}
		
		// 循环每个资源进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到视频.
			GroupVideo gv = group_svc.getGroupVideoByGroupAndVideo(group_model.getGroupId(), id);
			if (gv == null) {
				addActionError("未找到指定标识的视频: " + id);
				continue;
			}
			Video v=this.video_svc.findById(gv.getVideoId());
			if(v==null){
				addActionError("未找到指定标识的视频: " + id);
				continue;
			}
			oper_count++;
			UPunishScore upun =this.pun_svc.createUPunishScore(ObjectType.OBJECT_TYPE_VIDEO.getTypeId(),gv.getVideoId(),gv.getUserId(),Float.parseFloat(""+score),score_reason,getLoginUser().getUserId(),getLoginUser().getTrueName());
            this.pun_svc.saveUPunishScore(upun)	;	  
            
            Message message = new Message();
            message.setSendId(getLoginUser().getUserId());
            message.setReceiveId(gv.getUserId());
            message.setTitle("管理员删除了您的备课视频及扣分信息");
            if(score_reason != null && score_reason.length()>0){
                message.setContent("您的视频 " + v.getTitle() + " 被删除,扣" + score + "分,原因:" + score_reason);
            }
            else{  
            	message.setContent("您的视频  " + v.getTitle() + " 被删除,扣" + score + "分");
            }
            msg_svc.sendMessage(message);
            
            addActionMessage("视频 '" + v.getTitle() + "' 被罚分");
            
            //删除视频！！！！
            this.video_svc.delVideo(v);
            
		}

		addActionMessage("共 " + oper_count + " 个视频被罚分");

		return SUCCESS;		
	}	
		
	/**
	 * 分页列出群组视频.
	 * 
	 * @return
	 */
	private String list() {
		// 构造分页对象, 准备查询.
		Pager pager = getCurrentPager();
		pager.setItemNameAndUnit("视频", "个");	

		VideoQueryParam param = new VideoQueryParam();
		param.k = param_util.safeGetStringParam("k", null);
		param.retrieveGroupCategory = true;
		param.groupCateId = param_util.getIntParamZeroAsNull("gcid");
		
		// 进行查询.
		
		List<VideoModel> video_list = group_svc.getGroupVideoList(group_model.getGroupId(), param, pager);

		setRequestAttribute("gcid", param.groupCateId);
		setRequestAttribute("k", param.k);
		
		setRequestAttribute("group", group_model);
		setRequestAttribute("video_list", video_list);
		setRequestAttribute("pager", pager);

		// 列出群组的视频分类树.
		CategoryTreeModel res_cate = cate_svc.getCategoryTree(getGroupVideoCategoryItemType());
		setRequestAttribute("res_cate", res_cate);
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
		return LIST_SUCCESS;
	}

	private List<Integer> video_ids;
	
	/** 得到要操作的视频标识集合. */
	private boolean getVideoIdsParam() {
		this.video_ids = param_util.safeGetIntValues("videoId");
		if (video_ids == null || video_ids.size() == 0) {
			addActionError("未选择任何要操作的视频");
			return false;
		}
		return true;
	}
	
	private boolean isGroupCategory(int groupCateId) {
		Category cate = cate_svc.getCategory(groupCateId);
		if (cate == null)
			return false;
		
		String itemType = this.getGroupVideoCategoryItemType();
		if (itemType.equals(cate.getItemType()) == false)
			return false;
		
		return true;
	}
	
	private String move() {
		// 得到参数.
		if (getVideoIdsParam() == false) return ERROR;
		Integer groupCateId = param_util.getIntParamZeroAsNull("groupCateId");
		// 验证分类合法性.
		if (groupCateId != null) {
			if (isGroupCategory(groupCateId) == false) {
				addActionError("非法的目标分类");
				return ERROR;
			}
		}
		
		// 循环每个视频进行操作.
		int oper_count = 0;
		for (Integer id : this.video_ids) {
			// 得到视频.
			GroupVideo gv = group_svc.getGroupVideoByGroupAndVideo(group_model.getGroupId(), id);
			if (gv == null) {
				addActionError("未找到指定标识的视频: " + id);
				continue;
			}
			
			group_svc.updateGroupVideoCategory(gv, groupCateId);
			++oper_count;
			
			addActionMessage("视频 '" + gv.getVideoId() + "' 移动到了新分类(或设置为未分类)");
		}
		
		addActionMessage("共 " + oper_count + " 个移动到了新分类");
		
		return SUCCESS;
	}
	
	/**
	 * 解除对视频的引用.
	 * 
	 * @return
	 */
	private String unref() {
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("videoId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的视频");
			return ERROR;
		}

		// 循环每个视频进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到视频.
			Tuple<Video, GroupVideo> tuple = group_svc.getGroupVideoByVideoId(group_model.getGroupId(), id);
			if (tuple == null) {
				addActionError("未找到指定标识的视频: " + id);
				continue;
			}

			// 消除其引用, 也即删除 GroupArticle 对象.
			group_svc.deleteGroupVideo(tuple.getValue());
			++oper_count;

			addActionMessage("视频 '" + tuple.getKey().getTitle() + "' 已经从群组中移除");
		}

		addActionMessage("共 " + oper_count + " 篇视频从群组中移除");

		// 更新群组视频数.
		//group_svc.updateGroupArticleCount(group_model._getGroupObject());

		return SUCCESS;
	}

	
	private String rcmd(){
		List<Integer> ids = param_util.safeGetIntValues("videoId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的视频");
			return ERROR;
		}
		// 循环每个视频进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到视频.
			Video video = video_svc.findById(id);
			if (video == null) {
				addActionError("未找到指定标识的视频: " + id);
				continue;
			}
		}
		return SUCCESS;
	}
	
	private String unrcmd(){
		List<Integer> ids = param_util.safeGetIntValues("videoId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的视频");
			return ERROR;
		}
		// 循环每个视频进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到视频.
			Video video = video_svc.findById(id);
			if (video == null) {
				addActionError("未找到指定标识的视频: " + id);
				continue;
			}
		}
		addActionMessage("共 " + oper_count + " 篇视频取消平台推荐视频");

		return SUCCESS;
	}	
	
	/**
	 * 设置为群组精华视频.
	 * 
	 * @return
	 */
	private String best() {
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("videoId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的视频");
			return ERROR;
		}

		// 循环每个视频进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到视频.
			GroupVideo gv = group_svc.getGroupVideoByGroupAndVideo(group_model.getGroupId(), id);
			if (gv == null) {
				addActionError("未找到指定标识的视频: " + id);
				continue;
			}

			// 设置为精华.
			//group_svc.bestGroupPhoto(gp);
			++oper_count;

			addActionMessage("视频 '" + gv.getVideoId() + "' 成功设置为群组精华视频");
		}

		addActionMessage("共 " + oper_count + " 篇视频设置为群组精华视频");

		return SUCCESS;
	}

	/**
	 * 取消群组精华视频.
	 * 
	 * @return
	 */
	private String unbest() {
		// 得到参数.
		List<Integer> ids = param_util.safeGetIntValues("videoId");
		if (ids == null || ids.size() == 0) {
			addActionError("未选择任何要操作的视频");
			return ERROR;
		}

		// 循环每个视频进行操作.
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到视频.
			GroupVideo gv = group_svc.getGroupVideoByGroupAndVideo(group_model.getGroupId(), id);
			if (gv == null) {
				addActionError("未找到指定标识的视频: " + id);
				continue;
			}

			// 设置为精华.
			//group_svc.unbestGroupPhoto(gp);
			++oper_count;

			addActionMessage("视频 '" + gv.getVideoId() + "' 取消了群组精华视频");
		}

		addActionMessage("共 " + oper_count + " 篇视频被取消了精华设置");

		return SUCCESS;
	}

	/** 计算群组视频树分类名字 */
	private String getGroupVideoCategoryItemType() {
		return CommonUtil.toGroupVideoCategoryItemType(group_model.getGroupId());
	}	
}
