package cn.edustar.jitar.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.dom4j.Document;
import org.jasig.cas.client.util.CasConst;

import com.alibaba.fastjson.JSONObject;
import com.chinaedustar.fcs.bi.enums.DeleteSrcEnum;
import com.chinaedustar.fcs.bi.enums.ObjectEnum;
import com.chinaedustar.fcs.bi.service.BiServiceImpl;
import com.chinaedustar.fcs.bi.vo.TaskVo;

import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.jitar.JitarConst;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.model.ResourceModel;
import cn.edustar.jitar.model.ResourceModelEx;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.ChannelPhoto;
import cn.edustar.jitar.pojos.ChannelResource;
import cn.edustar.jitar.pojos.ChannelUser;
import cn.edustar.jitar.pojos.ChannelVideo;
import cn.edustar.jitar.pojos.Comment;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.GroupResource;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.PrepareCourseStage;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.query.sitefactory.UserIndexHtmlService;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ChannelPageService;
import cn.edustar.jitar.service.CommentQueryParam;
import cn.edustar.jitar.service.CommentService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.EvaluationService;
import cn.edustar.jitar.service.FileStorage;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.service.ResourceQueryParam;
import cn.edustar.jitar.service.StoreManager;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.UserPowerService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.service.ViewCountService;
import cn.edustar.jitar.user.UserComments;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.FileCache;
import cn.edustar.jitar.util.PageContent;
import cn.edustar.jitar.util.UTF8PostMethod;
import cn.edustar.jitar.util.XmlUtil;

/**
 * 资源管理
 * 
 */
@SuppressWarnings("unused")
public class ResourceAction extends BaseResourceAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3794587022743823639L;

	/** 用户文件存储服务 */
	private StoreManager sto_mgr;

	/** 协作组服务 */
	private GroupService group_svc;

	/** 评论服务 */
	private CommentService cmt_svc;

	/** 点击率服务对象 */
	private ViewCountService viewcount_svc;

	/** 课程服务 */
	private PrepareCourseService pc_svc;

	/** 课程服务 */
	private EvaluationService es_svc;

	/** 标签服务 */
	private TagService tagService;

	/** 用户角色权限服务 */
	private UserPowerService userPowerService;

	/** 配置对象 */
	private Configure config;

	/** 配置服务 */
	private ConfigService cfg_svc;

	/** 显示上传限制 */
	private String limit;

	/** 待操作的单个评论对象 */
	private Comment comment;

	/** 被评论的资源对象 */
	private Resource comment_resource;

	private ChannelPageService channelPageService;

	private UserIndexHtmlService userIndexHtmlService;

	private AccessControlService accessControlService;

	private File file;
	private String fileFileName;
	private String fileContentType;

	/**
	 * Default Constructor
	 */
	public ResourceAction() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		// username = param_util.safeGetStringParam("username");
		// System.out.println("用户信息：" + username);

		// 得到配置对象
		this.config = cfg_svc.getConfigure();

		// cmd 不能为空
		if (cmd == null || cmd.length() == 0) {
			cmd = "list";
		}

		// 无需登录就能够使用的命令
		if ("view".equals(cmd)) {
			return view();
		} else if ("pub_comment".equals(cmd)) {
			return pub_comment();
		} else if ("remote_save".equals(cmd)) {
			return remote_save();
		} else if ("uploadify".equals(cmd)) {
			return uploadify();
		} else if ("userresource".equals(cmd)) {
			return userresource();
		}
		if ("dest_cate".equals(cmd)) {
			return dest_cate();
		} else if ("channel_cate".equals(cmd)) {
			return channel_cate();
		}
		// 需要登录
		if (false == isUserLogined()) {
			return LOGIN;
		}

		if (canVisitUser(getLoginUser()) == false) {
			return ERROR;
		}

		if ("list".equals(cmd)) {
			return list();
		} else if ("comment_list".equals(cmd)) {
			return comment_list();
		} else if ("comment_list_my".equals(cmd)) {
			return comment_list_my();
		} else if ("comment_stat".equals(cmd)) {
			return comment_stat();
		} else if ("publishtozyk".equals(cmd)) {
			return publishtozyk();
		} else if ("select_resource_cate".equals(cmd)) {
			return select_resource_cate();
		} else if ("save_resource_cate".equals(cmd)) {
			return save_resource_cate();
		}

		// 需要能够管理内容权限(未锁定)才能使用的命令
		if (canManageBlog(getLoginUser()) == false) {
			return ERROR;
		} else if ("bk".equals(cmd)) {
			return beike();
		} else if ("bksave".equals(cmd)) {
			return beikeSave();
		} else if ("upload".equals(cmd)) {
			return upload();
		} else if ("save".equals(cmd)) {
			return save();
		} else if ("update".equals(cmd)) {
			return update();
		} else if ("edit".equals(cmd)) {
			return edit();
		} else if ("delete".equals(cmd)) {
			return delete();
		} else if ("set_share_mode".equals(cmd)) {
			return set_share_mode();
		} else if ("move".equals(cmd)) {
			return move("user");
		} else if ("recycle_list".equals(cmd)) {
			return recycle_list();
		} else if ("recover".equals(cmd)) {
			return recover();
		} else if ("crash".equals(cmd)) {
			return crash();
		} else if ("pub_res".equals(cmd)) {
			return pub_res();
		} else if ("reply_comment".equals(cmd)) {
			return reply_comment();
		} else if ("edit_comment".equals(cmd)) {
			return edit_comment();
		} else if ("save_comment_reply".equals(cmd)) {
			return save_comment_reply();
		} else if ("save_edit_comment".equals(cmd)) {
			return save_edit_comment();
		} else if ("audit_comment".equals(cmd)) {
			return audit_comment();
		} else if ("unaudit_comment".equals(cmd)) {
			return unaudit_comment();
		} else if ("delete_comment".equals(cmd)) {
			return delete_comment();
		} else if ("pub_to_channel".equals(cmd)) {
			return pub_to_channel();
		}

		return super.unknownCommand(cmd);
	}

	private String pub_to_channel() {
		// 获得文章标识参数
		if (this.getResourceIds() == false) {
			return ERROR;
		}
		// 得到参数 channelId:要发布的自定义频道; channelCateId：自定义频道资源分类
		int channelId = param_util.safeGetIntParam("channelId");
		if (channelId == 0) {
			addActionError("选择的自定义频道错误。");
			return ERROR;
		}

		Channel c = this.channelPageService.getChannel(channelId);
		if (null == c) {
			addActionError("不能加载所选择的自定义频道信息。");
			return ERROR;
		}

		// 检查协作组成员关系
		ChannelUser cu = this.channelPageService
				.getChannelUserByUserIdAndChannelId(getLoginUser().getUserId(),
						channelId);
		if (cu == null) {
			addActionError("您不是该自定义频道的成员。");
			return ERROR;
		}

		// 得到自定义频道文章分类 /xx/xxx/这样的格式
		String channelCate = param_util.safeGetStringParam("channelCateId",
				null);

		if (null == channelCate) {
			addActionError("没有选择自定义频道资源分类。");
			return ERROR;
		}
		String channelCateId = null;
		String[] cateArray = channelCate.split("/");
		if (cateArray.length > 1) {
			channelCateId = cateArray[cateArray.length - 1];
		}
		if (CommonUtil.isInteger(channelCateId) == false) {
			channelCateId = null;
		}
		if (channelCateId == null) {
			channelCate = null;
		}
		// 对选中的资源进行操作，需要先判断是否在这个分类中
		int oper_count = 0;
		for (int resourceId : this.res_ids) {
			// 获得要操作的文章
			Resource r = this.res_svc.getResource(resourceId);
			if (r == null) {
				addActionError("未找到标识为 " + resourceId + " 的资源");
				continue;
			}
			// 验证是这个用户的, (权限验证)
			if (r.getUserId() != getLoginUser().getUserId()) {
				addActionError("权限被拒绝, 试图操作他人的资源 " + resourceId);
				continue;
			}

			if (r.getShareMode() < Resource.SHARE_MODE_FULL) {
				addActionError("资源  " + resourceId + " 没有完全共享，不能发布到自定义频道。");
				continue;
			}

			Boolean toUpdated = false;
			ChannelResource cr = this.channelPageService
					.getChannelResourceByChannelIdAndResourceId(channelId,
							resourceId);
			if (null != cr) {
				// 更新文章分类
				// 为了方便比较
				if (cr.getChannelCate() == null)
					cr.setChannelCate("");
				if (cr.getChannelCateId() == null)
					cr.setChannelCateId(0);

				if (channelCateId == null) {
					if (!cr.getChannelCate().equals(channelCate)
							|| cr.getChannelCateId() != null) {
						toUpdated = true;
						cr.setChannelCateId(null);
					}
				} else {
					if (!cr.getChannelCate().equals(channelCate)
							|| !cr.getChannelCateId().equals(
									Integer.valueOf(channelCateId))) {
						toUpdated = true;
						cr.setChannelCateId(Integer.valueOf(channelCateId));
					}
				}
				if (toUpdated) {
					cr.setChannelCate(channelCate);
					if (cr.getChannelCate() == null
							|| cr.getChannelCate().equals(""))
						cr.setChannelCate(null);
					if (cr.getChannelCateId() == null
							|| cr.getChannelCateId().equals(0))
						cr.setChannelCateId(null);

					this.channelPageService.saveOrUpdateChannelResource(cr);
					addActionMessage("资源 " + r.getTitle() + " 更新了资源分类.");
				} else {
					addActionError("资源 " + r.getTitle() + " 已经发布到了频道  "
							+ c.getTitle());
				}
				continue;
			}

			// 发布文章到频道
			ChannelResource channelResource = new ChannelResource();
			channelResource.setResourceId(r.getResourceId());
			channelResource.setUnitId(this.getLoginUser().getUnitId());
			channelResource.setChannelCate(channelCate);
			channelResource.setChannelId(channelId);
			channelResource.setChannelCateId(channelCateId == null ? null
					: Integer.valueOf(channelCateId));
			channelResource.setUserId(r.getUserId());
			this.channelPageService
					.saveOrUpdateChannelResource(channelResource);
			// 消息.
			addActionMessage("资源 " + r.getTitle() + " 成功发布到自定义频道 "
					+ c.getTitle() + " 中.");
			++oper_count;
		}
		addActionMessage("共发布了 " + oper_count + " 个资源到自定义频道 " + c.getTitle()
				+ ".");
		return SUCCESS;
	}

	private String save_resource_cate() {
		int resourceId = param_util.getIntParam("resourceId");
		Resource resource = res_svc.getResource(resourceId);
		if (resource == null) {
			addActionError("指定标识的资源不存在");
			return ERROR;
		}
		String zyk_url = getServletContext().getInitParameter("reslib_url");
		setRequestAttribute("zyk_url", zyk_url);
		setRequestAttribute("resourceId", resourceId);
		return "select_cate2";
	}

	/**
	 * 得到资源的分类,（学科资源群）
	 * 
	 * @return
	 */
	private String select_resource_cate() {
		String reslibCId = "";
		int resourceId = param_util.getIntParam("resourceId");
		Resource resource = res_svc.getResource(resourceId);
		if (resource == null) {
			addActionError("指定标识的资源不存在");
			return ERROR;
		}
		try {

			Subject subject = null;
			if (resource.getSubjectId() != null
					&& resource.getGradeId() != null) {
				subject = subj_svc.getSubjectByMetaData(
						resource.getSubjectId(), resource.getGradeId());
			}
			if (subject != null) {
				reslibCId = String.valueOf(subject.getReslibCId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				addActionError("发布失败，错误描述：" + e.getMessage());
				return ERROR;
			} catch (Exception se) {
			}
			e.printStackTrace();
		}
		if (reslibCId == "") {
			addActionError("资源对应的学科分类不存在");
			return ERROR;
		}

		String zyk_url = getServletContext().getInitParameter("reslib_url");

		setRequestAttribute("zyk_url", zyk_url);
		setRequestAttribute("resourceId", resourceId);
		setRequestAttribute("reslibCId", reslibCId);
		return "select_cate";
	}

	/**
	 * 发布资源到资源库
	 * 
	 * @return
	 */
	private String publishtozyk() {
		int resourceId = param_util.getIntParam("resourceId");
		Resource resource = res_svc.getResource(resourceId);
		if (resource == null) {
			addActionError("指定标识的资源不存在");
			setRequestAttribute("error", "指定标识的资源不存在");
			return "publish_result";
		}
		try {
			String urlAddr = getServletContext().getInitParameter("reslib_url");
			String reslibCId = "";

			// Subject subject = null;
			// if (resource.getSubjectId() != null && resource.getGradeId() !=
			// null) {
			// subject = subj_svc.getSubjectByMetaData(resource.getSubjectId(),
			// resource.getGradeId());
			// }
			// if (subject != null) {
			// reslibCId = String.valueOf(subject.getReslibCId());
			// }

			reslibCId = param_util.safeGetStringParam("CID");
			String reslibCId2 = param_util.safeGetStringParam("CID2");

			HttpClient client = new HttpClient();
			HttpState initialState = new HttpState();
			String UserTicket = "";
			Cookie[] cookie = request.getCookies();
			org.apache.commons.httpclient.Cookie postCookie = null;
			for (int i = 0; i < cookie.length; i++) {
				if (cookie[i].getName().equals("UserTicket")) {
					UserTicket = cookie[i].getValue();
				}
				postCookie = new org.apache.commons.httpclient.Cookie();
				postCookie.setName(cookie[i].getName());
				postCookie.setValue(cookie[i].getValue());
				postCookie.setPath("/");
				initialState.addCookie(postCookie);
			}
			client.setState(initialState);

			UTF8PostMethod method = new UTF8PostMethod(urlAddr
					+ "reslib/res/addresource.aspx");
			method.setParameter("CID", reslibCId); // 资源所属学科资源库分类ID
			method.setParameter("CID2", reslibCId2); // 资源所属学科资源库分类ID---框架分类ID
			if (reslibCId == null || reslibCId.length() <= 0) {
				this.addActionError("发布失败,没有选择资源库相对应的学科分类!");
				setRequestAttribute("error", "发布失败,没有选择资源库相对应的学科分类!");
				return "publish_result";
			}
			if (reslibCId2 == null || reslibCId2.length() <= 0) {
				this.addActionError("发布失败,没有选择资源库相对应的学科框架分类!");
				setRequestAttribute("error", "发布失败,没有选择资源库相对应的学科框架分类!");
				return "publish_result";
			}

			method.setParameter("username", getLoginUser().getLoginName()); // 当前用户
			User resUser = user_svc.getUserById(resource.getUserId());
			method.setParameter("UserLoginName", resUser.getLoginName()); // 资源上载者的用户登录名
			method.setParameter("UserTrueName",
					URLEncoder.encode(resUser.getTrueName(), "UTF-8")); // 资源上载者的用户名
			String userPassword = user_svc.getPassword(resUser.getLoginName());
			if (userPassword.equals("")) {
				this.addActionError("发布失败,资源的所有者是" + resUser.getLoginName()
						+ ",没能找到该用户的密码!");
				setRequestAttribute("error",
						"发布失败,资源的所有者是" + resUser.getLoginName()
								+ ",没能找到该用户的密码!");
				return "publish_result";
			}
			method.setParameter("UserPassword", userPassword); // 资源上载者的密码
			method.setParameter("Title",
					URLEncoder.encode(resource.getTitle(), "UTF-8")); // 资源标题
			method.setParameter("UserTicket", "");
			method.setParameter("ip", request.getRemoteAddr());
			if (resource.getResTypeId() == null) {
				method.setParameter("ResTypeID", "14"); // 资源类型ID
			} else {
				method.setParameter("ResTypeID", resource.getResTypeId()
						.toString()); // 资源类型ID
			}
			if (resource.getAuthor() != null) {
				method.setParameter("Author",
						URLEncoder.encode(resource.getAuthor(), "UTF-8")); // 资源作者姓名
			} else {
				method.setParameter("Author", "");
			}
			if (resource.getSummary() != null) {
				method.setParameter("Comments",
						URLEncoder.encode(resource.getSummary(), "UTF-8")); // 资源描述
			} else {
				method.setParameter("Comments", "");
			}
			method.setParameter("FileSize", String.valueOf(resource.getFsize())); // 资源文件字节数
			if (resource.getHref() != null) {
				method.setParameter("Href", resource.getHref()); // 资源Href,不包含http://Host:Post
			} else {
				method.setParameter("Href", "");
			}
			if (resource.getHref() == null || resource.getHref().length() <= 0) {
				this.addActionError("发布失败,资源文件路径不能为空！");
				setRequestAttribute("error", "发布失败,资源文件路径不能为空！");
				return "publish_result";
			}
			if (resource.getObjectUuid() != null) {
				method.setParameter("GUID", resource.getObjectUuid()); // 资源GUID
			} else {
				method.setParameter("GUID", "");
			}
			method.setParameter("submit", "OK");
			client.executeMethod(method);
			String result = method.getResponseBodyAsString();
			if (result.equals("ok")) {
				method.releaseConnection();
				resource.setPublishToZyk(true);
				res_svc.updateResource(resource); // 更新资源，发布成功后不允许再次发送
				setRequestAttribute("success", "发布成功");
				this.addActionMessage("发布成功");
				return "publish_result";
			} else {
				method.releaseConnection();
				addActionError("发布失败，返回信息不正确,返回信息为：" + result);
				setRequestAttribute("error", "发布失败，返回信息不正确,返回信息为：" + result);
				return "publish_result";
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				addActionError("发布失败，错误描述：" + e.getMessage());
				setRequestAttribute("error", "发布失败，错误描述：" + e.getMessage());
				return "publish_result";
			} catch (Exception se) {
			}
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 列出用户资源
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String userresource() {
		Pager pager = getCurrentPager();
		pager.setPageSize(20);
		pager.setItemNameAndUnit("资源", "个");
		pager.setUrlPattern(param_util.generateUrlPattern());

		ResourceQueryParam param = new ResourceQueryParam();

		int userId = param_util.getIntParam("userId");
		if (userId == 0) {
			// 如果没有传入参数userId则将显示当前用户的资源
			if (getLoginUser() != null) {
				userId = getLoginUser().getUserId();
			}
		}
		param.userId = userId;
		param.k = param_util.safeGetStringParam("k", null);
		param.userCateId = param_util.getIntParamZeroAsNull("ucid");
		param.retrieveUserCategory = true;
		param.retrieveSystemCategory = true;
		param.shareMode = null;
		param.auditState = 0;

		// 进行查询.
		List<ResourceModelEx> resource_list = res_svc.getResourceList(param,
				pager);

		setRequestAttribute("resource_list", resource_list);
		setRequestAttribute("pager", pager);
		setRequestAttribute("ucid", param.userCateId);
		setRequestAttribute("k", param.k);

		// 获得用户参与的协作组
		putJoinedGroupToRequest(group_svc);

		// 获得系统资源分类树
		this.putResourceSystemCategory();
		this.putResourceUserCategory();

		return "List_UserResource";
	}

	/**
	 * 列出用户资源
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String list() {
		Pager pager = getCurrentPager();
		pager.setPageSize(20);
		pager.setItemNameAndUnit("资源", "个");
		pager.setUrlPattern(param_util.generateUrlPattern());

		ResourceQueryParam param = new ResourceQueryParam();
		param.userId = getLoginUser().getUserId();
		param.k = param_util.safeGetStringParam("k", null);
		param.userCateId = param_util.getIntParamZeroAsNull("ucid");
		param.retrieveUserCategory = true;
		param.retrieveSystemCategory = true;
		param.shareMode = null;
		param.auditState = null; // 显示全部资源
		param.delState = null;

		// 进行查询.
		List<ResourceModelEx> resource_list = res_svc.getResourceList(param,
				pager);

		setRequestAttribute("resource_list", resource_list);
		setRequestAttribute("pager", pager);
		setRequestAttribute("ucid", param.userCateId);
		setRequestAttribute("k", param.k);

		String zyk_url = getServletContext().getInitParameter("reslib_url");
		setRequestAttribute("zyk_url", zyk_url); // 是否发布到资源库
		setRequestAttribute("isHaszykUrl", "false"); // 是否发布到资源库
		// 当资源库路径为空时，不进行操作
		if (zyk_url != null && zyk_url.length() > 0) {
			setRequestAttribute("isHaszykUrl", "true");
		}
		String kbm_url = getServletContext().getInitParameter("kbm_url");
		setRequestAttribute("zskUrl", kbm_url); // 设置知识库路径
		// 当知识库路径为空时，不进行操作
		if (kbm_url != null && kbm_url.length() > 0) {
			try {
				String content = getSourceContent(kbm_url
						+ "public/catatoryJson.jsp");
				JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
				Iterator it = jsonObject.keySet().iterator();
				HashMap<String, String> kbmcatatory = new HashMap<String, String>();
				while (it.hasNext()) {
					String CategoryId = it.next().toString();
					String CategoryName = jsonObject.get(CategoryId).toString();
					kbmcatatory.put(CategoryId, CategoryName);
				}
				setRequestAttribute("kbmcatatory", kbmcatatory);
				setRequestAttribute("isHaszskUrl", "true"); // 发布到知识库
			} catch (Exception e) {
				setRequestAttribute("isHaszskUrl", "false"); // 不显示'发布到知识库'
			}
		}

		// 获得用户参与的协作组
		putJoinedGroupToRequest(group_svc);

		// 获得系统资源分类树
		this.putResourceSystemCategory();
		this.putResourceUserCategory();

		// 当前登录用户加入的自定义频道列表
		putUserChannelList();

		return LIST_SUCCESS;
	}

	/**
	 * 获取网页页面内容
	 * 
	 * @param URLStr
	 * @return
	 */
	private String getSourceContent(String URLStr) {
		StringBuffer sb = new StringBuffer();
		try {
			URL newURL = new URL(URLStr);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					newURL.openStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			return new String(sb.toString().getBytes("UTF-8"), "UTF-8");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 查看一个资源的属性
	 * 
	 * @return
	 */
	private String view() {
		int resourceId = param_util.getIntParam("resourceId");
		Resource resource = res_svc.getResource(resourceId);
		if (resource == null) {
			addActionError("指定标识的资源不存在");
			return ERROR;
		}

		// 得到资源拥有者者
		User resource_owner = user_svc.getUserById(resource.getUserId());
		setRequestAttribute("owner", resource_owner);

		// 包装资源, 放到 request 中
		ResourceModel res_model = ResourceModel.wrap(resource);

		setRequestAttribute("resource", res_model);

		// 得到系统分类和用户分类
		if (resource.getSysCateId() != null) {
			Category sysCate = getCategoryService().getCategory(
					resource.getSysCateId());
			setRequestAttribute("sysCate", sysCate);
		}
		if (resource.getUserCateId() != null) {
			Category userCate = getCategoryService().getCategory(
					resource.getUserCateId());
			setRequestAttribute("userCate", userCate);
		}

		viewcount_svc.incViewCount(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(), resource.getResourceId(), 1);

		return "View_Resource";
	}

	/**
	 * 发表评论
	 * 
	 * @return
	 */
	private String pub_comment() throws IOException {
		// 得到要发表评论的资源
		if (this.getCurrentResource() == false) {
			return ERROR;
		}

		UserComments userComments = UserComments.getInstance();
		String content = param_util.safeGetStringParam("content");
		String commentContent = CommonUtil.eraseHtml(content).trim();
		if (commentContent.length() == 0) {
			addActionError("请输入评论内容。");
			return ERROR;
		}

		if (getLoginUser() != null) {
			boolean enableComment = userComments.CanAddComment(getLoginUser()
					.getUserId(), ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(),
					2);
			if (enableComment == false) {
				addActionError("请2分钟后再发评论");
				return ERROR;
			}
			boolean CommentEqual = userComments.CommentIsEqual(getLoginUser()
					.getUserId(), ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(),
					commentContent);
			if (CommentEqual) {
				addActionError("有创意些吧，重复的内容毋需再发");
				return ERROR;
			}
		}

		String title = param_util.safeGetStringParam("title");
		if (title.length() == 0) {
			title = "Re:" + cur_resource.getTitle();
		}
		// 得到用户提交的评论对象
		Comment comment = new Comment();
		comment.setAboutUserId(cur_resource.getUserId());
		comment.setAudit(true);
		comment.setContent(param_util.safeGetStringParam("content"));
		comment.setCreateDate(new Date());
		comment.setIp(request.getRemoteAddr());
		comment.setObjId(cur_resource.getResourceId());
		comment.setObjType(ObjectType.OBJECT_TYPE_RESOURCE.getTypeId());
		comment.setStar(param_util.safeGetIntParam("star", 1));
		comment.setTitle(title);
		if (this.isUserLogined()) {
			comment.setUserId(getLoginUser().getUserId());
			comment.setUserName(getLoginUser().getNickName());
		} else {
			comment.setUserId(null);
			comment.setUserName(param_util.safeGetStringParam("userName",
					"匿名用户"));
		}

		cmt_svc.saveComment(comment);

		// 更新资源的评论数量
		res_svc.incResourceCommentCount(cur_resource.getResourceId());

		if (getLoginUser() != null) {
			userComments
					.AddCommentStatus(getLoginUser().getUserId(),
							ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(),
							commentContent);
		}

		// 发表成功之后重定向回到资源显示页面
		response.sendRedirect("../showResourcePy.action?resourceId="
				+ cur_resource.getResourceId());

		return NONE;
	}

	/**
	 * 保存资源到评课
	 * 
	 * @param resource
	 */
	private void saveRespurceToEvaluation(Resource resource) {
		int evaluationId = param_util.getIntParam("evaluationId");
		if (evaluationId > 0) {
			this.es_svc.insertResourceToEvaluation(evaluationId,
					resource.getResourceId(), resource.getTitle(),
					resource.getHref());
		}
	}

	/**
	 * 保存资源到备课
	 * 
	 * @param resource
	 */
	private void saveResourceToPrepareCourseStage(Resource resource) {
		PrepareCourseStage prepareCourseStage = getPrepareCourseStage();
		if (prepareCourseStage != null) {
			this.pc_svc.saveResourceToPrepareCourseStage(prepareCourseStage,
					resource);
		}
	}

	/**
	 * @return
	 */
	private PrepareCourseStage getPrepareCourseStage() {
		int prepareCourseStageId = param_util
				.getIntParam("prepareCourseStageId");
		PrepareCourseStage prepareCourseStage = this.pc_svc
				.getPrepareCourseStage(prepareCourseStageId);
		if (prepareCourseStage != null) {
			setRequestAttribute("prepareCourseStage", prepareCourseStage);
		}
		return prepareCourseStage;
	}

	/**
	 * 备课
	 * 
	 * @return
	 */
	private String beike() throws IOException {
		PrintWriter out = response.getWriter();
		if (isUserLogined() == false)
			return LOGIN;
		/** 是否显示当前用户的'剩余空间' */

		// 检查今天是否还允许上载资源
		int num = this.userPowerService.getUploadResourceNum(getLoginUser()
				.getUserId());
		int todaynum = 0;
		if (num != -1) {
			todaynum = this.userPowerService.getTodayResources(getLoginUser()
					.getUserId());
			if (!this.userPowerService.AllowUploadResource(getLoginUser()
					.getUserId(), num, todaynum)) {
				this.addActionError("您今天不能再上载资源了！您每天允许上载资源的数量是" + num);
				return ERROR;
			}
		}
		if (num > 0) {
			setRequestAttribute("num", num);
			setRequestAttribute("todaynum", todaynum);
		}

		/** 是否显示当前用户的'剩余空间' */
		if (this.config.getBoolValue(Configure.USER_RESOURCE_UPLOAD_LIMIT,
				false) == true) {
			limit = "true";
			int sSize = this.userPowerService.getUploadDiskNum(getLoginUser()
					.getUserId(), false);
			if (sSize != -1) {
				if (sSize == 0) {
					out.append(PageContent.PAGE_UTF8);
					out.println("<script>alert('您所在的角色组不允许上载资源！');window.location.href='"
							+ PageContent.getAppPath()
							+ "/manage/resource.action?cmd=list';</script>");
					out.flush();
					out.close();
					return NONE;

				} else {
					String totalSize = "" + sSize;
					long longTotalSize = Integer.valueOf(totalSize) * 1024 * 1024;

					/** 计算当前用户的空间总和 */
					long resSize = res_svc.totalResourceSize(getLoginUser()
							.getUserId());
					setRequestAttribute("resSize",
							calculate(totalSize, longTotalSize, resSize));

					if (resSize > longTotalSize) {
						out.append(PageContent.PAGE_UTF8);
						out.println("<script>alert('您已经使用了超过 "
								+ totalSize
								+ "M 的空间！ 目前已经无法再上传资源！');window.location.href='"
								+ PageContent.getAppPath()
								+ "/manage/resource.action?cmd=list';</script>");
						out.flush();
						out.close();
						return NONE;
					}
				}
			}
		} else {
			limit = "false";
		}

		getPrepareCourseStage();

		Integer typeid = param_util.getIntParamZeroAsNull("typeid");
		if (typeid == null) {
			typeid = 1;
		}
		setRequestAttribute("typeid", typeid);

		User loginUser = getLoginUser();
		Resource resource = new Resource();
		resource.setSubjectId(loginUser.getSubjectId());
		resource.setGradeId(loginUser.getGradeId());
		setRequestAttribute("resource", resource);

		// 学段列表
		setRequestAttribute("resTypeList", restype_svc.getResTypes());
		putResourceCategoryAndSubjectList();
		putGradeList();

		// 将个人加入的协作组放到'request'中
		putJoinedGroupToRequest(group_svc);

		int DocID = 0;
		setRequestAttribute("DocID", DocID);

		String newFileDocName = ""; // 要保存的文件名
		newFileDocName = UUID.randomUUID().toString().toLowerCase();
		newFileDocName = newFileDocName + ".doc";
		String FileSavePath = ""; // 要保存的文件路径
		FileSavePath = "user/" + getLoginUser().getLoginName() + "/resource/";
		setRequestAttribute("FileSavePath", FileSavePath);
		setRequestAttribute("newFileDocName", newFileDocName);

		String templetFile = ""; // 文件的模板
		if (typeid == 1) {
			// templetFile= servlet_ctxt.getRealPath("bk_1.doc");
			templetFile = "/bk_1.doc";
		} else {
			// templetFile= servlet_ctxt.getRealPath("bk_2.doc");
			templetFile = "/bk_2.doc";
		}
		// System.out.print(templetFile);
		setRequestAttribute("templetFile", templetFile);

		return "Upload_BeikeResource";
	}

	/** 计算群组资源树分类名字 */
	private String getGroupResourceCategoryItemType(int groupId) {
		return CommonUtil.toGroupResourceCategoryItemType(groupId);
	}

	private void putUserChannelList() {
		User loginUser = super.getLoginUser();
		if (loginUser != null) {
			List<Channel> channel_List = this.channelPageService
					.getChannelListByUserId(loginUser.getUserId());
			if (channel_List != null && channel_List.size() > 0)
				setRequestAttribute("channel_List", channel_List);
		}
	}

	/**
	 * 显示'资源上传'页面
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	private String upload() throws IOException {
		PrintWriter out = response.getWriter();

		if (isUserLogined() == false) {
			return LOGIN;
		}

		// 检查今天是否还允许上载资源
		int num = this.userPowerService.getUploadResourceNum(getLoginUser()
				.getUserId());
		int todaynum = 0;
		if (num != -1) {
			todaynum = this.userPowerService.getTodayResources(getLoginUser()
					.getUserId());
			if (!this.userPowerService.AllowUploadResource(getLoginUser()
					.getUserId(), num, todaynum)) {
				this.addActionError("您今天不能再上载资源了！您每天允许上载资源的最大数量是：" + num);
				return ERROR;
			}
		}
		if (num > 0) {
			setRequestAttribute("num", num);
			setRequestAttribute("todaynum", todaynum);
		}

		/** 是否显示当前用户的'剩余空间' */
		if (this.config.getBoolValue(Configure.USER_RESOURCE_UPLOAD_LIMIT,
				false) == true) {
			limit = "true";
			// 获得系统规定的个人空间大小单位是：M
			// String totalSize = (String)
			// this.config.getValue("resource.uploadMaximumSize");
			// log.info("系统规定的个人空间大小单位是：" + totalSize + "M");
			//
			// long longTotalSize = Integer.valueOf(totalSize) * 1024 * 1024;
			// log.info("系统规定的个人空间大小单位是：" + longTotalSize + "B");

			int sSize = this.userPowerService.getUploadDiskNum(getLoginUser()
					.getUserId(), false);
			if (sSize != -1) {
				if (sSize == 0) {
					out.append(PageContent.PAGE_UTF8);
					out.println("<script>alert('您所在的角色组不允许上载资源！');window.location.href='"
							+ PageContent.getAppPath()
							+ "/manage/resource.action?cmd=list';</script>");
					out.flush();
					out.close();
					return NONE;

				} else {
					String totalSize = "" + sSize;
					long longTotalSize = Integer.valueOf(totalSize) * 1024 * 1024;

					/** 计算当前用户的空间总和 */
					long resSize = res_svc.totalResourceSize(getLoginUser()
							.getUserId());
					setRequestAttribute("resSize",
							calculate(totalSize, longTotalSize, resSize));

					if (resSize > longTotalSize) {
						out.append(PageContent.PAGE_UTF8);
						out.println("<script>alert('您已经使用了超过 "
								+ totalSize
								+ "M 的空间！ 目前已经无法再上传资源！');window.location.href='"
								+ PageContent.getAppPath()
								+ "/manage/resource.action?cmd=list';</script>");
						out.flush();
						out.close();
						return NONE;
					}
				}
			}
		} else {
			limit = "false";
		}

		Integer groupId = param_util.getIntParamZeroAsNull("groupId");
		// 传递来的参数。如果是1，是其他模块调用的 ,需要设置 window.returnvalve=resourceid
		int needreturn = param_util.getIntParam("needreturn");
		setRequestAttribute("needreturn", needreturn);

		getPrepareCourseStage();

		if (groupId != null) {
			setRequestAttribute("groupId", groupId);
		} else {
			setRequestAttribute("groupId", 0);
		}

		CategoryTreeModel res_cate2 = null;
		if (groupId != null) {
			res_cate2 = cate_svc
					.getCategoryTree(getGroupResourceCategoryItemType(groupId));
		}
		setRequestAttribute("res_cate2", res_cate2);

		User loginUser = getLoginUser();
		Resource resource = new Resource();
		resource.setSubjectId(loginUser.getSubjectId());
		resource.setGradeId(loginUser.getGradeId());
		setRequestAttribute("resource", resource);

		// 学段列表
		setRequestAttribute("resTypeList", restype_svc.getResTypes());
		putResourceCategoryAndSubjectList();
		putGradeList();

		// 将个人加入的协作组放到'request'中
		putJoinedGroupToRequest(group_svc);
		putUserChannelList();

		// 是否允许大文件上载
		Configure conf = JitarContext.getCurrentJitarContext()
				.getConfigService().getConfigure();
		boolean largefileupload = conf.getBoolValue(Configure.LARGEFILE_UPLOAD,
				false);
		if (largefileupload) {
			setRequestAttribute("is_largefileupload", "true");
		} else {
			setRequestAttribute("is_largefileupload", "false");
		}

		// 判断上载资源的类型及大小限制
		String path = request
				.getRealPath("/WEB-INF/classes/configuration/struts_resource.xml");
		Document x = XmlUtil.loadXml(path);
		String sFileType = XmlUtil
				.getNodeText(
						x,
						"struts//package[@name='resource']//action[@name='resource']//interceptor-ref[@name='fileUpload']//param[@name='allowedTypes']");
		String sSize = XmlUtil
				.getNodeText(
						x,
						"struts//package[@name='resource']//action[@name='resource']//interceptor-ref[@name='fileUpload']//param[@name='maximumSize']");
		int s = Integer.parseInt(sSize) / 1024;
		s = s / 1024;
		setRequestAttribute("filesize", s);
		String sFileFormat = "";
		boolean b = false;
		if (sFileType.indexOf("audio/mpeg") != -1
				|| sFileType.indexOf("audio/x-mpeg") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.abs;*.mp3";
			else
				sFileFormat += ";*.abs;*.mp3";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("audio/basic") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.au";
			else
				sFileFormat += ";*.au";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("video/x-msvideo") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.avi";
			else
				sFileFormat += ";*.avi";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("image/bmp") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.bmp";
			else
				sFileFormat += ";*.bmp";

		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("application/octet-stream") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.rar;*.gsp";
			else
				sFileFormat += ";*.rar;*.gsp";

		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("image/gif") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.gif";
			else
				sFileFormat += ";*.gif";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("image/jpg") != -1
				|| sFileType.indexOf("image/jpeg") != -1
				|| sFileType.indexOf("image/pjpeg") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.jpeg;*.jpg";
			else
				sFileFormat += ";*.jpeg;*.jpg";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("image/x-png") != -1
				|| sFileType.indexOf("image/png") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.png";
			else
				sFileFormat += ";*.png";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("image/tiff") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.tif";
			else
				sFileFormat += ";*.tif";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("audio/x-midi") != -1
				|| sFileType.indexOf("audio/midi") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.mid;*.midi";
			else
				sFileFormat += ";*.mid;*.midi";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("video/quicktime") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.mov";
			else
				sFileFormat += ";*.mov";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("video/mp4") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.mp4";
			else
				sFileFormat += ";*.mp4";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("video/mpeg") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.mpeg;*.mpg";
			else
				sFileFormat += ";*.mpeg;*.mpg";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("audio/x-mpeg") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.mpega";
			else
				sFileFormat += ";*.mpega";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("application/x-shockwave-flash") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.swf";
			else
				sFileFormat += ";*.swf";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("text/plain") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.txt";
			else
				sFileFormat += ";*.txt";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("application/pdf") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.pdf";
			else
				sFileFormat += ";*.pdf";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("audio/x-wav") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.wav";
			else
				sFileFormat += ";*.wav";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("video/x-ms-wmv") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.wmv";
			else
				sFileFormat += ";*.wmv";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("application/vnd.rn-realmedia") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.rm";
			else
				sFileFormat += ";*.rm";

		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("application/zip") != -1
				|| sFileType.indexOf("application/x-zip-compressed") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.zip";
			else
				sFileFormat += ";*.zip";

		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("text/html") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.htm;*.html";
			else
				sFileFormat += ";*.htm;*.html";

		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("application/xml") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.xml";
			else
				sFileFormat += ";*.xml";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("application/msword") != -1
				|| sFileType.indexOf("application/vnd.ms-word") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.doc";
			else
				sFileFormat += ";*.doc";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("application/powerpoint") != -1
				|| sFileType.indexOf("application/vnd.ms-powerpoint") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.ppt";
			else
				sFileFormat += ";*.ppt";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("application/vnd.ms-excel") != -1
				|| sFileType.indexOf("application/msexcel") != -1)
			if (sFileFormat == "")
				sFileFormat = "*.xls";
			else
				sFileFormat += ";*.xls";
		if (b == false && sFileFormat.length() > 80) {
			b = true;
			sFileFormat += "\n\r<br/>";
		}
		if (sFileType.indexOf("application/java-archive") != -1)
			if (sFileFormat == "") {
				sFileFormat = "*.jar";
			} else {
				sFileFormat += ";*.jar";
			}
		if (sFileFormat == "") {
			sFileFormat = "不限制";
		}
		setRequestAttribute("filetype", sFileFormat);
		return "Upload_Resource";
	}

	private String uploadify() throws Exception {
		PrintWriter out = response.getWriter();
		User user = user_svc.getUserByLoginName(username);
		int num = this.userPowerService.getUploadResourceNum(user.getUserId());
		int todaynum = 0;
		if (-1 != num) {
			todaynum = this.userPowerService
					.getTodayResources(user.getUserId());
			if (!this.userPowerService.AllowUploadResource(user.getUserId(),
					num, todaynum)) {
				out.print("a");
				out.flush();
				out.close();
				return NONE;
			}
		}

		if (null != file || "".equals(fileFileName)) {
			if (fileFileName.length() > 4) {
				if (!".exe".equals(fileFileName.substring(
						fileFileName.length() - 4).toLowerCase())
						&& !".bat".equals(fileFileName.substring(
								fileFileName.length() - 4).toLowerCase())) {
					if (null != user) {
						ServletContext sc = request.getSession()
								.getServletContext();
						String fileConfigPath = sc.getInitParameter("userPath");
						File dest = null;
						String href = "user/" + user.getLoginName()
								+ "/resource/";

						if (null != fileConfigPath
								&& !"".equals(fileConfigPath)) {
							String fileName = createFileName(file, fileFileName);
							String lastString = fileConfigPath + File.separator
									+ href + File.separator + fileName;
							dest = new File(lastString.replaceAll("\\\\", "/"));
							href = href + fileName;
						} else {
							FileStorage store = sto_mgr
									.getUserFileStorage(user);
							File user_root = store.getRootFolder();
							String fileName = createFileName(file, fileFileName);
							href = href + fileName;
							dest = new File(user_root, "/resource/" + fileName);

							if (this.config.getBoolValue(Configure.USER_RESOURCE_UPLOAD_LIMIT, false) == true) {
								int sSize = this.userPowerService.getUploadDiskNum(user.getUserId(), false);
								String totalSize = "" + sSize;
								long longTotalSize = Integer.valueOf(totalSize) * 1024 * 1024;
								long resSize = res_svc.totalResourceSize(user.getUserId());
								if (resSize > longTotalSize) {
									out.print("b");
									out.flush();
									out.close();
									return NONE;
								}
							}

						}
						dest.getParentFile().mkdirs();

						// For Linux
						FileUtils.copyFile(file, dest);

						// 使用转换服务
						if (dest.getAbsolutePath().endsWith(".doc") || dest.getAbsolutePath().endsWith(".docx") || dest.getAbsolutePath().endsWith(".pdf") || dest.getAbsolutePath().endsWith(".txt") || dest.getAbsolutePath().endsWith(".xlsx") || dest.getAbsolutePath().endsWith(".ppt") || dest.getAbsolutePath().endsWith(".pptx") || dest.getAbsolutePath().endsWith(".xls")) {
							String tar = dest.toString().substring(0, dest.toString().lastIndexOf("."));
							TaskVo taskVo = new TaskVo(fileFileName, dest.toString(), tar + ".swf", ObjectEnum.TASK_OBJECT_OFFICE);
							taskVo.setDeleteSrcEnum(DeleteSrcEnum.NOT_DELETE);
							new BiServiceImpl().send(taskVo);
						}						

						Resource resource = new Resource();
						resource.setUserId(user.getUserId());
						//String result = formatTitle(fileFileName);
					    resource.setTitle(FilenameUtils.getBaseName(fileFileName));
						resource.setHref(href);
						int unitId = -1;
						if (null != user.getUnitId()) {
							unitId = user.getUnitId();
						}
						Configure conf = JitarContext.getCurrentJitarContext()
								.getConfigService().getConfigure();
						boolean autoPush = false;
						if (conf.containsKey("auto_push_up")) {
							autoPush = conf.getBoolValue("auto_push_up", false);
						}
						boolean bCheck = conf.getBoolValue(
								Configure.BEFAULE_PUBLISH_CHECK, false);
						if (bCheck) {
							resource.setAuditState(Resource.AUDIT_STATE_WAIT_AUDIT);
							if (autoPush) {
								resource.setUnitPathInfo(user.getUnitPathInfo());
							} else {
								resource.setUnitPathInfo("/" + unitId + "/");
							}
						} else {
							resource.setAuditState(Resource.AUDIT_STATE_OK);
							if (autoPush) {
								resource.setApprovedPathInfo(user
										.getUnitPathInfo());
								resource.setUnitPathInfo(user.getUnitPathInfo());
							} else {
								resource.setApprovedPathInfo("/" + unitId + "/");
								resource.setUnitPathInfo("/" + unitId + "/");
							}
						}
						resource.setFsize((int) dest.length());
						resource.setGradeId(user.getGradeId());
						resource.setSubjectId(user.getSubjectId());
						resource.setAddIp(request.getRemoteAddr());
						resource.setShareMode(1000);
						resource.setUnitId(unitId);
						resource.setSummary("");
						resource.setAuthor("");
						resource.setPublisher("");
						resource.setOrginPathInfo(user.getUnitPathInfo());
						// resource.setChannelId(user.getChannelId());
						if (filterString(resource, resource.getTitle())) {
							out.print("c");
							out.flush();
							out.close();
							return NONE;
						}
						res_svc.createResource(resource);
						publishResourceToGroups(resource);
						saveResourceToPrepareCourseStage(resource);
						// saveRespurceToEvaluation(resource);
						out.print(resource.getResourceId());
						out.flush();
						out.close();
					}
				} else {
					out.print("d");
					out.flush();
					out.close();
					return NONE;
				}
			}
		}
		return NONE;
	}

	// 格式化标题
	private String formatTitle(String fileFileName) {
		int _min_title_len = JitarConst.RESOURCE_MIN_TITLE;
		String k = (fileFileName.lastIndexOf(".") > -1 ? fileFileName.substring(0, fileFileName.lastIndexOf(".")) : fileFileName);
		if (k.length() < _min_title_len) {
			request.setAttribute("morelong", true);
			return null;
		} else {
			return k;
		}
	}

	private String save() throws IOException {
		if (null == resourceId || "".equals(resourceId)
				|| resourceId.length() < 0) {
			this.addActionError("没有上传文件！");
			return ERROR;
		}
		Resource resource = collectResourceObject();
		if (null == resource) {
			return ERROR;
		}
		if (screenResource(resource)) {
			return ERROR;
		}
		Resource origin_resource = res_svc
				.getResource(resource.getResourceId());
		if (origin_resource == null) {
			return ERROR;
		}
		if (origin_resource.getUserId() != getLoginUser().getUserId()) {
			return ERROR;
		}
		boolean nameAsTitle = param_util.getBooleanParam("nameAsTitle");
		String title = param_util.safeGetStringParam("title");
		if (nameAsTitle && title.length() <= 0) {
			resource.setTitle(origin_resource.getTitle());
		}
		resource.setUserId(origin_resource.getUserId());
		resource.setFsize(origin_resource.getFsize());
		resource.setPublishToZyk(origin_resource.getPublishToZyk());
		resource.setUnitPathInfo(origin_resource.getUnitPathInfo());
		resource.setOrginPathInfo(origin_resource.getOrginPathInfo());
		resource.setApprovedPathInfo(origin_resource.getApprovedPathInfo());
		resource.setRcmdPathInfo(origin_resource.getRcmdPathInfo());
		resource.setUnitId(origin_resource.getUnitId());
		resource.setPushState(origin_resource.getPushState());
		resource.setPushUserId(origin_resource.getPushUserId());
		res_svc.updateResource(resource);
		publishResourceToGroups(resource);
		saveResourceToPrepareCourseStage(resource);
		this.addActionMessage("文件保存成功！");
		this.userIndexHtmlService.genResourceList(getLoginUser());

		Boolean saveNew = param_util.existParam("saveNew");
		if (saveNew) {
			publishResourceToChannel(resource);
		}
		int needreturn = param_util.getIntParam("needreturn");
		if (needreturn == 1) {
			setRequestAttribute("returnresourceId",
					"" + resource.getResourceId());
			return "Upload_Resource_ReturnId";
		} else {
			Integer returnGroupId = param_util
					.getIntParamZeroAsNull("returnGroupId");
			if (!(returnGroupId == null || returnGroupId == 0)) {
				this.addActionLink("小组资源管理",
						"groupResource.action?cmd=list&groupId="
								+ returnGroupId + "&returnPage=group");
			}
			return SUCCESS;
		}
	}

	// /**
	// * 保存上传的资源
	// *
	// * @return
	// * @throws IOException
	// */
	// private String save() throws IOException {
	// PrintWriter out = response.getWriter();
	//
	// // 判断文件是否是RAR
	// boolean bDelete = false;
	// for (int i = 0; i < this.file.size(); ++i) {
	// // 得到上传过来的文件
	// File f = this.file.get(i);
	// log.info("文件类型:" + this.fileContentType.get(i));
	// if (this.fileContentType.get(i).equals("application/octet-stream")) {
	// String sFileName = this.fileFileName.get(i);
	// //log.info("文件后缀：" + sFileName.substring(sFileName.length() -
	// 4).toLowerCase());
	// if(sFileName.length() > 4) {
	// if (!".rar".equals(sFileName.substring(sFileName.length()
	// -4).toLowerCase()) &&
	// !".gsp".equals(sFileName.substring(sFileName.length() -
	// 4).toLowerCase())) {
	// f.delete();
	// bDelete = true;
	// log.info("删除文件！");
	// }
	// } else {
	// addActionError("文件名：" + sFileName + " 的长度小于5。");
	// return ERROR;
	// }
	// }
	// }
	// if (bDelete) {
	// addActionError("上传文件的类型不合法！");
	// return ERROR;
	// }
	//
	// // 获得其它参数
	// String title = param_util.safeGetStringParam("title");
	// if (title == null || title.length() < 0) {
	// title = "";
	// }
	// boolean nameAsTitle = param_util.getBooleanParam("nameAsTitle");
	// String summary = param_util.safeGetStringParam("summary");
	// String tags = param_util.safeGetStringParam("tags");
	// Integer subjectId = param_util.getIntParamZeroAsNull("subjectId");
	// Integer resTypeID = param_util.getIntParamZeroAsNull("resTypeID");
	// Integer sysCateId = param_util.getIntParamZeroAsNull("sysCateId");
	// Integer userCateId = param_util.getIntParamZeroAsNull("userCateId");
	// String author = param_util.safeGetStringParam("author");
	// String publisher = param_util.safeGetStringParam("publisher");
	// int shareMode = param_util.getIntParam("shareMode");
	// Integer gradeId = param_util.getIntParamZeroAsNull("gradeId");
	// String addIp = request.getRemoteAddr();
	// String channelCate = param_util.getStringParam("channelCate");
	// if (null == channelCate || "".equals(channelCate)) {
	// channelCate = null;
	// }
	//
	// // 准备工作
	// FileStorage store =
	// sto_mgr.getUserFileStorage(getLoginUser()._getUserObject());
	// File user_root = store.getRootFolder();
	//
	// // 创建资源, 每一个上传文件一个资源
	// int oper_count = 0;
	// for (int i = 0; i < this.file.size(); ++i) {
	// // 得到上传过来的文件
	// File f = this.file.get(i);
	// // 将上传的文件移动到用户存储目录下, 如 '/resource/xxx.doc'
	// String fileName = createFileName(f, this.fileFileName.get(i));
	//
	// File dest = new File(user_root, "/resource/" + fileName);
	//
	// log.info("最后的全路径：" + dest);
	//
	// dest.getParentFile().mkdirs();
	// //log.info("上传的文件: " + f + " 移动到目标: " + dest);
	//
	// // log.info("上传的文件大小：" + (int) dest.length());
	//
	// if (f.renameTo(dest) == false)
	// throw new IOException("不能把上传的文件: " + f + " 移动到目标: " + dest);
	//
	// /** 计算当前用户的空间总和 */
	//
	// // 获得系统规定的个人空间大小单位是：M
	// String totalSize = (String)
	// this.config.getValue("resource.uploadMaximumSize");
	// long longTotalSize = Integer.valueOf(totalSize) * 1024 * 1024;
	// long resSize = res_svc.totalResourceSize(getLoginUser().getUserId());
	// long allSize = resSize + (int) dest.length();
	// log.info("现有空间大小加上当前上传的大小之和：" + allSize);
	//
	// if (this.config.getBoolValue(Configure.USER_RESOURCE_UPLOAD_LIMIT,
	// false) == true) {
	// if (allSize > longTotalSize) {
	// out.append(PageContent.PAGE_UTF8);
	// out.println("<script>alert('您已经使用的空间和当前文件的大小总和已经超过：" + totalSize +
	// "M的空间，因此无法上传当前资源！');window.history.go(-1);</script>");
	// out.flush();
	// out.close();
	// return NONE;
	// }
	// }
	//
	// // 计算出访问它的路径, files 文件夹不实际存在, 只是用来区分 url.注意: 此路径相对于
	// String href = "user/" + getLoginUser().getLoginName() + "/resource/" +
	// fileName;
	//
	// // 构造资源对象
	// Resource resource = new Resource();
	// resource.setUserId(getLoginUser().getUserId());
	// resource.setTitle(title);
	// // 使用文件名作为标题
	// if (nameAsTitle || title.length() == 0) {
	// resource.setTitle(this.fileFileName.get(i));
	// }
	// resource.setSummary(summary);
	// resource.setTags(tags);
	// resource.setHref(href);
	// int unitId = -1;
	// if(this.getLoginUser().getUnitId() != null) unitId =
	// this.getLoginUser().getUnitId();
	//
	// // 根据系统配置：是否先审核后发布
	// // 是否允许大文件上载
	// Configure conf =
	// JitarContext.getCurrentJitarContext().getConfigService().getConfigure();
	// //根据配置获取是否自动推送内容
	// boolean autoPush = false;
	// if(conf.containsKey("auto_push_up")) {
	// autoPush = conf.getBoolValue("auto_push_up", false);
	// }
	//
	// boolean bCheck = conf.getBoolValue(Configure.BEFAULE_PUBLISH_CHECK,
	// false);
	// if(bCheck) {
	// resource.setAuditState(Resource.AUDIT_STATE_WAIT_AUDIT);
	// if(autoPush) {
	// resource.setUnitPathInfo(this.getLoginUser().getUnitPathInfo());
	// } else {
	// resource.setUnitPathInfo("/" + unitId + "/");
	// }
	// } else {
	// resource.setAuditState(Resource.AUDIT_STATE_OK);
	// if(autoPush) {
	// resource.setApprovedPathInfo(this.getLoginUser().getUnitPathInfo());
	// resource.setUnitPathInfo(this.getLoginUser().getUnitPathInfo());
	// } else {
	// resource.setApprovedPathInfo("/"+unitId +"/");
	// resource.setUnitPathInfo("/" + unitId + "/");
	// }
	// }
	//
	// resource.setFsize((int) dest.length()); // 文件大小
	// resource.setSubjectId(subjectId);
	// resource.setSysCateId(sysCateId); // 系统分类标识
	// resource.setUserCateId(userCateId); // 用户分类标识
	// resource.setAuthor(author);
	// resource.setPublisher(publisher);
	// resource.setShareMode(shareMode);
	// resource.setGradeId(gradeId);
	// resource.setResTypeId(resTypeID);
	// resource.setAddIp(addIp);
	// resource.setUnitId(unitId);
	// resource.setOrginPathInfo(this.getLoginUser().getUnitPathInfo());
	// resource.setChannelId(getLoginUser().getChannelId());
	// resource.setChannelCate(channelCate);
	//
	// // 过滤资源中出现的非法词汇
	// if (screenResource(resource)) {
	// return ERROR;
	// }
	//
	// // 保存资源
	// res_svc.createResource(resource);
	//
	// // 发布到协作组
	// publishResourceToGroups(resource);
	//
	// // 备课
	// saveResourceToPrepareCourseStage(resource);
	//
	// // 设置消息
	// addActionMessage("资源：" + resource.getTitle() + " 上传成功！");
	// ++oper_count;
	// }
	// addActionMessage("共成功上传：" + oper_count + "个资源！");
	// return SUCCESS;
	// }

	/**
	 * 过滤资源中出现的非法词汇
	 * 
	 * @param resource
	 * @return
	 */
	private boolean screenResource(Resource resource) {
		String title = resource.getTitle(); // 资源标题
		String tags[] = tagService.parseTagList((String) resource.getTags()); // 资源标签
		String author = resource.getAuthor(); // 资源作者
		String publisher = resource.getPublisher(); // 资源出版单位
		String summary = resource.getSummary(); // 资源说明
		String[] screen_keyword = tagService.parseTagList((String) this.config
				.getValue("site.screen.keyword")); // 系统中定义的需要屏蔽的非法词汇数组
		String screen_enalbed = (String) this.config
				.getValue("site.screen.enalbed"); // 是否屏蔽
		String screen_replace = (String) this.config
				.getValue("site.screen.replace"); // 替换的字符

		// 是否屏蔽系统中出现的非法词汇
		if ("true".equals(screen_enalbed)) {
			for (int i = 0; i < screen_keyword.length; ++i) {
				// 1，检查'资源标题'中是否含有'非法词汇'
				if (title.equals(screen_keyword[i])) {
					this.addActionError("您输入的'资源标题'与'非法词汇'完全相同！");
					return true;
				}
				if (title.indexOf(screen_keyword[i]) != -1) {
					title = title.replaceAll(screen_keyword[i], screen_replace);
					resource.setTitle(title);
				}

				// 2，检查'资源作者'中是否含有'非法词汇'
				if (author.equals(screen_keyword[i])) {
					this.addActionError("您输入的'资源作者'与'非法词汇'完全相同！");
					return true;
				}
				if (author.indexOf(screen_keyword[i]) != -1) {
					author = author.replaceAll(screen_keyword[i],
							screen_replace);
					resource.setAuthor(author);
				}

				// 3，检查'资源出版单位'中是否含有'非法词汇'
				if (publisher.equals(screen_keyword[i])) {
					this.addActionError("您输入的'资源出版单位与'非法词汇'完全相同！");
					return true;
				}
				if (publisher.indexOf(screen_keyword[i]) != -1) {
					publisher = publisher.replaceAll(screen_keyword[i],
							screen_replace);
					resource.setPublisher(publisher);
				}

				// 4，检查'资源说明'中是否含有'非法词汇'
				if (summary.equals(screen_keyword[i])) {
					this.addActionError("您输入的'资源说明与'非法词汇'完全相同！");
					return true;
				}
				if (summary.indexOf(screen_keyword[i]) != -1) {
					summary = summary.replaceAll(screen_keyword[i],
							screen_replace);
					resource.setSummary(summary);
				}
			}

			// 检查'资源标签'中是否含有'非法词汇'
			for (int i1 = 0; i1 < screen_keyword.length; ++i1) {
				for (int j = 0; j < tags.length; j++) {
					if (tags[j].indexOf(screen_keyword[i1]) != -1) {
						tags[j] = tags[j].replaceAll(screen_keyword[i1],
								screen_replace);
						resource.setTags(CommonUtil.standardTagsString(tags));
					}
				}
			}
		}
		return false;
	}

	private boolean filterString(Resource resource, String title) {
		String[] screen_keyword = tagService.parseTagList((String) this.config
				.getValue("site.screen.keyword"));
		String screen_enalbed = (String) this.config
				.getValue("site.screen.enalbed");
		String screen_replace = (String) this.config
				.getValue("site.screen.replace");
		if ("true".equals(screen_enalbed)) {
			for (int i = 0; i < screen_keyword.length; ++i) {
				if (title.equals(screen_keyword[i])) {
					return true;
				}
				if (title.indexOf(screen_keyword[i]) != -1) {
					title = title.replaceAll(screen_keyword[i], screen_replace);
					resource.setTitle(title);
				}
			}
		}
		return false;
	}

	/**
	 * 保存一个远程上传的资源（服务于大文件上传组件）
	 * 
	 * @return
	 * @throws IOException
	 */
	private String remote_save() throws IOException {
		// 获得其它参数
		String user = param_util.safeGetStringParam("user");
		String userid = param_util.safeGetStringParam("userid");
		String filename = param_util.safeGetStringParam("filename");
		String title = param_util.safeGetStringParam("title");
		if (title.lastIndexOf(".") > 0) {
			title = title.substring(0, title.lastIndexOf("."));
		}
		String summary = param_util.safeGetStringParam("summary");
		String tags = param_util.safeGetStringParam("tag");
		Integer subjectId = param_util.getIntParamZeroAsNull("subjectId");
		Integer resTypeID = param_util.getIntParamZeroAsNull("resTypeID");
		Integer sysCateId = param_util.getIntParamZeroAsNull("sysCateId");
		Integer userCateId = param_util.getIntParamZeroAsNull("userCateId");
		String author = param_util.safeGetStringParam("author");
		String publisher = param_util.safeGetStringParam("publisher");
		int shareMode = param_util.getIntParam("shareMode");
		Integer gradeId = param_util.getIntParamZeroAsNull("gradeId");
		String addIp = request.getRemoteAddr();
		Resource resource = new Resource();
		resource.setUserId(Integer.valueOf(userid));
		resource.setTitle(title);
		resource.setSummary(summary);
		resource.setTags(tags);
		resource.setHref("user/" + user + "/resource/" + filename + "");
		resource.setAuditState(Resource.AUDIT_STATE_OK);
		resource.setFsize(Integer.valueOf(param_util
				.safeGetStringParam("uploadFileSize"))); // 文件大小
		resource.setSubjectId(subjectId);
		resource.setSysCateId(sysCateId); // 系统分类标识
		resource.setUserCateId(userCateId); // 用户分类标识
		resource.setAuthor(author);
		resource.setPublisher(publisher);
		resource.setShareMode(shareMode);
		resource.setGradeId(gradeId);
		resource.setResTypeId(resTypeID);
		resource.setAddIp(addIp);
		// 保存资源
		res_svc.createResource(resource);
		publishResourceToGroups(resource);
		response.getWriter().println("ok");
		response.getWriter().flush();
		return null;
	}

	/**
	 * 保存一个上传的备课
	 * 
	 * @return
	 * @throws IOException
	 */
	private String beikeSave() throws IOException {
		// 获得其它参数

		// 检查今天是否还允许上载资源
		int num = this.userPowerService.getUploadResourceNum(getLoginUser()
				.getUserId());
		int todaynum = 0;
		if (num != -1) {
			todaynum = this.userPowerService.getTodayResources(getLoginUser()
					.getUserId());
			if (!this.userPowerService.AllowUploadResource(getLoginUser()
					.getUserId(), num, todaynum)) {
				this.addActionError("您今天不能再上载资源了！您每天允许上载资源的数量是" + num);
				return ERROR;
			}
		}

		String title = param_util.safeGetStringParam("title");
		if (title == null || title.length() < 0) {
			addActionError("没有输入标题信息");
			return ERROR;
		}
		String summary = param_util.safeGetStringParam("summary");
		String tags = param_util.safeGetStringParam("tag");
		Integer subjectId = param_util.getIntParamZeroAsNull("subjectId");
		Integer resTypeID = param_util.getIntParamZeroAsNull("resTypeID");
		Integer sysCateId = param_util.getIntParamZeroAsNull("sysCateId");
		Integer userCateId = param_util.getIntParamZeroAsNull("userCateId");
		String author = param_util.safeGetStringParam("author");
		String publisher = param_util.safeGetStringParam("publisher");
		int shareMode = param_util.getIntParam("shareMode");
		Integer gradeId = param_util.getIntParamZeroAsNull("gradeId");
		String addIp = request.getRemoteAddr();

		// 准备工作
		FileStorage store = sto_mgr.getUserFileStorage(getLoginUser()
				._getUserObject());
		File user_root = store.getRootFolder();

		// 创建资源
		String fileName = param_util.safeGetStringParam("filename");
		String href = "user/" + getLoginUser().getLoginName() + "/resource/"
				+ fileName;
		File dest = new File(user_root, "/resource/" + fileName);
		dest.getParentFile().mkdirs();
		// 构造资源对象
		Resource resource = new Resource();
		resource.setUserId(getLoginUser().getUserId());
		resource.setUnitId(getLoginUser().getUserId());
		resource.setUnitPathInfo(getLoginUser().getUnitPathInfo());
		resource.setOrginPathInfo(getLoginUser().getUnitPathInfo());
		resource.setTitle(title);
		resource.setSummary(summary);
		resource.setTags(tags);
		resource.setHref(href);
		resource.setAuditState(Resource.AUDIT_STATE_OK);
		resource.setFsize((int) dest.length()); // 文件大小
		resource.setSubjectId(subjectId);
		resource.setSysCateId(sysCateId); // 系统分类标识
		resource.setUserCateId(userCateId); // 用户分类标识
		resource.setAuthor(author);
		resource.setPublisher(publisher);
		resource.setShareMode(shareMode);
		resource.setGradeId(gradeId);
		resource.setResTypeId(resTypeID);
		resource.setAddIp(addIp);
		// 保存资源
		res_svc.createResource(resource);
		// 发布到协作组
		publishResourceToGroups(resource);

		// 备课
		saveResourceToPrepareCourseStage(resource);

		// 设置消息
		addActionMessage("资源: " + resource.getTitle() + " (href="
				+ resource.getHref() + ") 已经成功创建！");
		addActionMessage("成功上传备课资源");
		return SUCCESS;
	}

	/**
	 * 同时发布资源到协作组中
	 * 
	 */
	private void publishResourceToGroups(Resource resource) {
		Integer _groupIndex = param_util.safeGetIntParam("_groupIndex");
		if (_groupIndex == 0)
			_groupIndex = 1;

		// 进行循环操作
		for (int i = 1; i <= _groupIndex; i++) {
			Integer groupId = param_util.getIntParamZeroAsNull("gpId" + i);
			Integer groupCateId = param_util
					.getIntParamZeroAsNull("groupCateId" + i);
			if (groupId == null) {
				continue;
			}
			// 得到协作组并验证
			Group group = group_svc.getGroupMayCached(groupId);
			if (group == null) {
				addActionError("指定标识的协作组不存在 groupId = " + groupId);
				continue;
			}
			if (checkGroupState(group._getGroupObject()) == false) {
				// System.out.println("该组不允许增加内容");
				continue;
			}

			// 得到成员关系并验证
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(
					groupId, getLoginUser().getUserId());
			if (this.checkGroupMemberState(gm, group._getGroupObject(),
					getLoginUser()._getUserObject()) == false) {
				continue;
			}

			// 是否已经发布了
			GroupResource gr = group_svc.getGroupResourceByGroupAndResource(
					groupId, resource.getResourceId());
			if (gr != null) {
				addActionError("您已经将资源：" + resource.toDisplayString()
						+ "，发布到了协作组：" + group.toDisplayString() + ", 不需再次发布！");
				continue;
			}

			gr = new GroupResource();
			gr.setGroupId(groupId);
			gr.setGroupCateId(groupCateId);
			gr.setResourceId(resource.getId());
			gr.setUserId(resource.getUserId());
			gr.setPubDate(new Date());
			gr.setIsGroupBest(false);
			group_svc.publishResourceToGroup(gr);
			addActionMessage("资源：" + resource.toDisplayString() + "，成功发布到了协作组："
					+ group.toDisplayString());
		}
	}

	/**
	 * 修改并保存资源
	 * 
	 * @return
	 */
	private String update() throws Exception {
		Resource resource = collectResourceObject();
		if (resource == null) {
			return ERROR;
		}
		// 过滤资源中出现的非法词汇
		if (screenResource(resource)) {
			return ERROR;
		}

		Resource origin_resource = res_svc
				.getResource(resource.getResourceId());
		if (origin_resource == null) {
			return ERROR;
		}
		if (origin_resource.getUserId() != getLoginUser().getUserId()) {
			return ERROR;
		}
		resource.setUserId(origin_resource.getUserId());
		resource.setFsize(origin_resource.getFsize());
		resource.setPublishToZyk(origin_resource.getPublishToZyk());
		resource.setUnitPathInfo(origin_resource.getUnitPathInfo());
		resource.setOrginPathInfo(origin_resource.getOrginPathInfo());
		resource.setApprovedPathInfo(origin_resource.getApprovedPathInfo());
		resource.setRcmdPathInfo(origin_resource.getRcmdPathInfo());
		resource.setUnitId(origin_resource.getUnitId());
		resource.setPushState(origin_resource.getPushState());
		resource.setPushUserId(origin_resource.getPushUserId());
		// 修改资源
		res_svc.updateResource(resource);
		addActionMessage("更新资源属性成功完成！");
		return SUCCESS;
	}

	/**
	 * 从提交的数据中创建资源对象
	 * 
	 * @return
	 */
	private Resource collectResourceObject() {
		int resourceId = param_util.getIntParam("resourceId");
		Resource resource = new Resource();
		resource.setResourceId(resourceId);
		resource.setTitle(param_util.safeGetStringParam("title"));
		resource.setSysCateId(param_util.getIntParamZeroAsNull("sysCateId"));
		resource.setUserCateId(param_util.getIntParamZeroAsNull("userCateId"));
		resource.setSubjectId(param_util.getIntParamZeroAsNull("subjectId"));
		resource.setResTypeId(param_util.getIntParamZeroAsNull("resTypeID"));
		resource.setTags(param_util.safeGetStringParam("tags"));
		resource.setShareMode(param_util.getIntParam("shareMode"));
		resource.setAuthor(param_util.safeGetStringParam("author"));
		resource.setPublisher(param_util.safeGetStringParam("publisher"));
		resource.setSummary(param_util.safeGetStringParam("summary"));
		resource.setGradeId(param_util.getIntParamZeroAsNull("gradeId"));
		// resource.setChannelCate(param_util.safeGetStringParam("channelCate").equals("")?null:param_util.safeGetStringParam("channelCate"));
		if(resource.getTitle().trim().length() == 0){
		    resource.setTitle("未命名的资源");
		}
		return resource;
	}

	/**
	 * 发布资源到组
	 * 
	 * @return
	 */
	private String pub_res() {
		if (getResourceIds() == false) {
			return ERROR;
		}

		// 得到参数 groupId - 目标协作组; groupCateId - 目标协作组分类
		int groupId = param_util.getIntParam("groupId");
		Integer groupCateId = param_util.getIntParamZeroAsNull("groupCateId");
		if (groupId == 0) {
			addActionError("未选择要发布的目标协作组.");
			return ERROR;
		}

		// 验证组的存在性于合法性
		Group group = group_svc.getGroup(groupId);
		if (super.checkGroupState(group) == false) {
			return ERROR;
		}

		// 验证当前用户是这个组的成员，并且有权限发布资源到该组
		GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(
				group.getGroupId(), getLoginUser().getUserId());
		if (super.checkGroupMemberState(gm, group, getLoginUser()
				._getUserObject()) == false) {
			return ERROR;
		}

		// 循环操作
		int oper_count = 0;
		for (Integer resourceId : res_ids) {
			Resource resource = res_svc.getResource(resourceId);
			if (resource == null) {
				addActionError("未找到标识为 " + resourceId + " 的资源！");
				continue;
			}
			// 验证是这个用户的, (权限验证), 以及资源不是私有的
			if (resource.getUserId() != getLoginUser().getUserId()) {
				addActionError("不能发布他(她)人的资源！");
				continue;
			}

			if (resource.getShareMode() < Resource.SHARE_MODE_GROUP) {
				addActionError("资源 " + resource.toDisplayString()
						+ " 是私有的, 至少要设置为协作组共享的资源才能发布到协作组.");
				continue;
			}

			// 验证是否已经发布过了
			Tuple<Resource, GroupResource> gr_t = group_svc
					.getGroupResourceByResourceId(group.getGroupId(),
							resource.getResourceId());
			if (gr_t != null) {
				addActionError("资源：" + resource.toDisplayString()
						+ "，已经发布到了协作组：" + group.getGroupTitle());
				continue;
			}

			// 发布资源到这个组
			GroupResource gr = new GroupResource();
			gr.setGroupId(group.getGroupId());
			gr.setResourceId(resource.getResourceId());
			gr.setUserId(getLoginUser().getUserId());
			gr.setGroupCateId(groupCateId);
			gr.setIsGroupBest(false);
			gr.setPubDate(new Date());

			group_svc.publishResourceToGroup(gr);

			// 消息
			addActionMessage("资源：" + resource.toDisplayString() + "，成功发布到了协作组："
					+ group.getGroupTitle());
			++oper_count;
		}
		addActionMessage("共发布了：" + oper_count + " 个资源到协作组："
				+ group.getGroupTitle());
		return SUCCESS;
	}

	/**
	 * 从 resource list 页面 publish resource 对话框中 ajax 回调到这里来得到指定协作组的资源分类
	 * 
	 * @return
	 */
	private String dest_cate() {
		int groupId = param_util.getIntParam("groupId");
		String itemType = CommonUtil.toGroupResourceCategoryItemType(groupId);
		CategoryTreeModel gres_categories = getCategoryService()
				.getCategoryTree(itemType);
		setRequestAttribute("gres_categories", gres_categories);
		return "Dest_Cate";
	}

	private String channel_cate() {
		int channelId = param_util.getIntParam("channelId");
		String itemType = CategoryService.CHANNEL_RESOURCE_PREFIX + channelId;
		CategoryTreeModel gres_categories = getCategoryService()
				.getCategoryTree(itemType);
		setRequestAttribute("gres_categories", gres_categories);
		return "Channel_Cate";
	}

	/**
	 * 为上传上来的指定文件生成服务器端名字
	 * 
	 * @param file
	 *            上传上来的文件
	 * @param oldName
	 *            用户客户端该文件的名字
	 * @return
	 */
	private String createFileName(File file, String oldName) {
		String extension = CommonUtil.getFileExtension(oldName);
		String newName = UUID.randomUUID().toString().toLowerCase();
		return newName + "." + extension;
	}

	/**
	 * 发布到自定义频道
	 * 
	 * @param photo
	 */
	private void publishResourceToChannel(Resource resource) {
		if (resource == null)
			return;
		User loginedUser = this.getLoginUser();
		if (loginedUser == null)
			return;
		// 如果没有频道，就不处理了。
		if (!param_util.existParam("channelTotalCount"))
			return;
		int channelTotalCount = param_util.safeGetIntParam("channelTotalCount");
		for (int i = 0; i < channelTotalCount + 1; i++) {
			Integer channelId = param_util.getIntParamZeroAsNull("channelId"
					+ i);
			String channelCate = param_util.getStringParam("channelCate" + i,
					null);
			String channelCateId = null;
			if (channelId != null) {
				// 检查是否已经存在，这里是新加，一般不存在，可以不用检查。
				ChannelResource channelResource = this.channelPageService
						.getChannelResourceByChannelIdAndResourceId(channelId,
								resource.getResourceId());
				if (channelResource != null) {
					continue;
				}
				if (channelCate != null) {
					String[] cateArray = channelCate.split("/");
					if (cateArray.length > 1) {
						channelCateId = cateArray[cateArray.length - 1];
					}
					if (CommonUtil.isInteger(channelCateId) == false) {
						channelCateId = null;
					}
				}
				if (channelCateId == null) {
					channelCate = null;
				}
				ChannelResource cr = new ChannelResource();
				cr.setChannelCate(channelCate);
				cr.setChannelCateId(channelCateId == null ? null : Integer
						.valueOf(channelCateId));
				cr.setChannelId(channelId);
				cr.setResourceId(resource.getResourceId());
				cr.setUnitId(loginedUser.getUnitId());
				cr.setUserId(loginedUser.getUserId());
				this.channelPageService.saveOrUpdateChannelResource(cr);
			}
		}

	}

	/**
	 * 列出资源有关的评论
	 * 
	 * @return
	 */
	private String comment_list() {
		Integer resourceId = param_util.getIntParamZeroAsNull("resourceId");
		if (resourceId != null) {
			this.cur_resource = res_svc.getResource(resourceId);
			if (this.cur_resource == null) {
				resourceId = null;
			} else if (this.cur_resource.getUserId() != getLoginUser()
					.getUserId()) {
				return ERROR;
			}
		}

		// 构造查询参数, 类型为资源, 可选有资源标识, 关于当前用户
		CommentQueryParam param = new CommentQueryParam();
		param.objectType = ObjectType.OBJECT_TYPE_RESOURCE;
		param.objectId = resourceId;
		param.aboutUserId = getLoginUser().getUserId();
		param.audit = null;
		Pager pager = getCurrentPager();
		pager.setItemNameAndUnit("评论", "条");
		pager.setUrlPattern(param_util.generateUrlPattern());

		// 查询数据.
		List<Object[]> data_list = cmt_svc.getUserResourceCommentList(param,
				pager);
		setRequestAttribute("resource", this.cur_resource);
		setRequestAttribute("data_list", data_list);
		setRequestAttribute("pager", pager);
		setRequestAttribute("my", 0);
		return "Comment_List";
	}

	private String comment_list_my() {
		Integer resourceId = param_util.getIntParamZeroAsNull("resourceId");
		if (resourceId != null) {
			this.cur_resource = res_svc.getResource(resourceId);
			if (this.cur_resource == null) {
				resourceId = null;
			} else if (this.cur_resource.getUserId() != getLoginUser()
					.getUserId()) {
				return ERROR;
			}
		}

		// 构造查询参数, 类型为资源, 可选有资源标识, 关于当前用户
		CommentQueryParam param = new CommentQueryParam();
		param.objectType = ObjectType.OBJECT_TYPE_RESOURCE;
		param.objectId = resourceId;
		param.userId = getLoginUser().getUserId();
		param.audit = null;
		Pager pager = getCurrentPager();
		pager.setItemNameAndUnit("评论", "条");
		pager.setUrlPattern(param_util.generateUrlPattern());

		// 查询数据.
		List<Object[]> data_list = cmt_svc.getUserResourceCommentList(param,
				pager);
		setRequestAttribute("resource", this.cur_resource);
		setRequestAttribute("data_list", data_list);
		setRequestAttribute("pager", pager);
		setRequestAttribute("my", 1);
		return "Comment_List";
	}

	/**
	 * 回复一个用户评论
	 * 
	 * @return
	 */
	private String reply_comment() {
		if (getCommentAndCheck() == false) {
			return ERROR;
		}
		setRequestAttribute("__referer", getRefererHeader());
		return "Reply_Comment";
	}

	/**
	 * 编辑一个评论
	 * 
	 * @return
	 */
	private String edit_comment() {
		if (getCommentAndCheck() == false) {
			return ERROR;
		}
		setRequestAttribute("__referer", getRefererHeader());
		return "Edit_Comment";
	}

	/**
	 * 保存一个编辑了的评论
	 * 
	 * @return
	 */
	private String save_edit_comment() {
		if (getCommentAndCheck() == false) {
			return ERROR;
		}

		// 得到提交的参数
		String commentContent = param_util.safeGetStringParam("commentContent",
				"");
		if (commentContent == null) {
			commentContent = "";
		}
		commentContent = commentContent.trim();
		if (commentContent.length() == 0) {
			addActionError("没有填写评论内容");
			return ERROR;
		}

		String[] screen_keyword = tagService.parseTagList((String) this.config
				.getValue("site.screen.keyword")); // 系统中定义的需要屏蔽的非法词汇数组
		String screen_enalbed = (String) this.config
				.getValue("site.screen.enalbed"); // 是否屏蔽
		String screen_replace = (String) this.config
				.getValue("site.screen.replace"); // 替换的字符

		// 是否屏蔽系统中出现的非法词汇
		if ("true".equals(screen_enalbed)) {
			for (int i = 0; i < screen_keyword.length; ++i) {
				if (commentContent.indexOf(screen_keyword[i]) != -1) {
					commentContent = commentContent.replaceAll(
							screen_keyword[i], screen_replace);
				}
			}
		}

		// 构造回复
		comment.setContent(commentContent);

		// 保存回复
		cmt_svc.saveComment(comment);
		addActionMessage("修改评论成功完成");

		// 删除首页缓存
		FileCache fc = new FileCache();
		fc.deleteUserAllCache(getLoginUser().getLoginName());
		fc = null;
		return SUCCESS;
	}

	/**
	 * 保存评论的回复
	 * 
	 * @return
	 */
	private String save_comment_reply() {
		// 得到提交的参数
		String commentReply = param_util.safeGetStringParam("commentReply", "");
		if (commentReply == null) {
			commentReply = "";
		}
		commentReply = commentReply.trim();
		if (commentReply.length() == 0) {
			addActionError("没有给出回复内容");
			return ERROR;
		}
		String[] screen_keyword = tagService.parseTagList((String) this.config
				.getValue("site.screen.keyword")); // 系统中定义的需要屏蔽的非法词汇数组
		String screen_enalbed = (String) this.config
				.getValue("site.screen.enalbed"); // 是否屏蔽
		String screen_replace = (String) this.config
				.getValue("site.screen.replace"); // 替换的字符

		// 是否屏蔽系统中出现的非法词汇
		if ("true".equals(screen_enalbed)) {
			for (int i = 0; i < screen_keyword.length; ++i) {
				if (commentReply.indexOf(screen_keyword[i]) != -1) {
					commentReply = commentReply.replaceAll(screen_keyword[i],
							screen_replace);
				}
			}
		}

		// 得到评论和文章对象
		if (getCommentAndCheck() == false) {
			return ERROR;
		}

		// 构造回复
		String content = comment.getContent();
		if (content == null) {
			content = "";
		}
		content += "<br/><div class='commentReply'><div>以下为："
				+ getLoginUser().getNickName() + " 的回复：</div><div>"
				+ commentReply + "</div></div>";
		comment.setContent(content);
		// 保存回复
		cmt_svc.saveComment(comment);

		// 删除首页缓存
		FileCache fc = new FileCache();
		fc.deleteUserAllCache(getLoginUser().getLoginName());
		fc = null;
		return SUCCESS;
	}

	/**
	 * 审核通过一个或多个评论
	 * 
	 * @return
	 */
	private String audit_comment() {
		// 获得参数
		List<Integer> ids = param_util.safeGetIntValues("commentId");
		if (ids == null || ids.size() == 0) {
			addActionError("没有选择要审核通过的评论");
			return ERROR;
		}

		// 循环每一个要审核的评论
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到评论
			if (id == null) {
				continue;
			}
			Comment comment = cmt_svc.getComment(id);
			if (comment == null) {
				addActionError("未能找到标识为 " + id + " 的评论");
				continue;
			}

			// 得到该评论的资源, 并验证是否有权限删除该资源的评论
			if (comment.getObjType() != ObjectType.OBJECT_TYPE_RESOURCE
					.getTypeId()) {
				addActionError("评论：" + id + " 不是资源的评论！");
				continue;
			}
			Resource resource = res_svc.getResource(comment.getObjId());
			if (resource == null) {
				addActionError("评论对应的资源已不存在");
				continue;
			} else if (resource.getUserId() != getLoginUser().getUserId()) {
				addActionError("权限不足, 不能删除他人资源的评论");
				continue;
			}
			// 操作评论
			cmt_svc.auditComment(comment);
			++oper_count;
		}
		// 删除首页缓存
		FileCache fc = new FileCache();
		fc.deleteUserAllCache(getLoginUser().getLoginName());
		fc = null;
		addActionMessage("共审核通过了：" + oper_count + " 条评论！");
		return SUCCESS;
	}

	/**
	 * 取消一个或多个评论的审核
	 * 
	 * @return
	 */
	private String unaudit_comment() {
		// 获得参数
		List<Integer> ids = param_util.safeGetIntValues("commentId");
		if (ids == null || ids.size() == 0) {
			addActionError("没有选择要取消审核的评论！");
			return ERROR;
		}

		// 循环每一个要审核的评论
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到评论
			if (id == null) {
				continue;
			}
			Comment comment = cmt_svc.getComment(id);
			if (comment == null) {
				addActionError("未能找到标识为：" + id + " 的评论！");
				continue;
			}

			// 得到该评论的资源, 并验证是否有权限删除该资源的评论
			if (comment.getObjType() != ObjectType.OBJECT_TYPE_RESOURCE
					.getTypeId()) {
				addActionError("评论：" + id + "，不是资源的评论！");
				continue;
			}
			Resource resource = res_svc.getResource(comment.getObjId());
			if (resource == null) {
				addActionError("评论对应的资源已不存在！");
				continue;
			} else if (resource.getUserId() != getLoginUser().getUserId()) {
				addActionError("权限不足, 不能取消审核他人资源的评论！");
				continue;
			}

			// 操作评论
			cmt_svc.unauditComment(comment);
			++oper_count;
		}
		// 删除首页缓存
		FileCache fc = new FileCache();
		fc.deleteUserAllCache(getLoginUser().getLoginName());
		fc = null;
		addActionMessage("共取消审核了：" + oper_count + " 条评论！");

		return SUCCESS;
	}

	/**
	 * 删除一个或多个评论
	 * 
	 * @return
	 */
	private String delete_comment() {
		// 获得参数
		List<Integer> ids = param_util.safeGetIntValues("commentId");
		if (ids == null || ids.size() == 0) {
			addActionError("没有选择要操作的评论！");
			return ERROR;
		}

		// 循环每一个要删除的评论
		int oper_count = 0;
		for (Integer id : ids) {
			// 得到评论
			if (id == null) {
				continue;
			}
			Comment comment = cmt_svc.getComment(id);
			if (comment == null) {
				addActionError("未能找到标识为：" + id + " 的评论！");
				continue;
			}

			// 得到该评论的文章, 并验证是否有权限删除该文章的评论.
			if (comment.getObjType() != ObjectType.OBJECT_TYPE_RESOURCE
					.getTypeId()) {
				addActionError("评论：" + id + " 不是资源的评论！");
				continue;
			}
			Resource resource = res_svc.getResource(comment.getObjId());
			if (resource == null) {
				addActionError("评论对应的资源已不存在，评论将被删除！");
			} else if (resource.getUserId() != getLoginUser().getUserId()) {
				if (comment.getUserId() != getLoginUser().getUserId()) {
					addActionError("权限不足，不能删除他人资源的评论！");
					continue;
				}
			}

			// 删除对评论的记忆
			UserComments userComments = UserComments.getInstance();
			String commentContent = comment.getContent();
			if (getLoginUser() != null) {
				boolean CommentEqual = userComments.CommentIsEqual(
						getLoginUser().getUserId(),
						ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(),
						commentContent);
				if (CommentEqual) {
					userComments.DeleteComment(getLoginUser().getUserId(),
							ObjectType.OBJECT_TYPE_RESOURCE.getTypeId(),
							commentContent);
				}
			}

			// 删除评论
			cmt_svc.deleteComment(comment);
			++oper_count;
			// 更新资源评论数量
			// stat_writer.incArticleCommentCount(resource.getId(), -1);
		}

		// 删除首页缓存
		FileCache fc = new FileCache();
		fc.deleteUserAllCache(getLoginUser().getLoginName());
		fc = null;

		addActionMessage("共删除了：" + oper_count + " 条评论！");

		return SUCCESS;
	}

	/**
	 * 重新统计资源的评论数
	 * 
	 * @return
	 */
	private String comment_stat() {
		this.res_svc.statResourceComment(getLoginUser()._getUserObject());
		return SUCCESS;
	}

	/**
	 * 得到当前提交的单个评论, 并验证对应的article是否正确
	 * 
	 * @return
	 */
	private boolean getCommentAndCheck() {
		// 得到评论
		int commentId = param_util.getIntParam("commentId");
		this.comment = cmt_svc.getComment(commentId);
		if (comment == null) {
			addActionError("未找到指定标识的评论！");
			return false;
		}

		// 验证用户有权限修改此评论
		if (comment.getObjType() != ObjectType.OBJECT_TYPE_RESOURCE.getTypeId()) {
			addActionError("不支持的评论目标对象！");
			return false;
		}
		this.comment_resource = res_svc.getResource(comment.getObjId());
		if (comment_resource == null) {
			addActionError("被评论的资源已不存在！");
			return false;
		}
		if ((comment_resource.getUserId() != getLoginUser().getUserId())
				&& (comment.getUserId() != getLoginUser().getUserId())) {
			addActionError("权限不足, 被评论的资源不是当前登录用户的,或评论人不是当前登录用户！");
			return false;
		}

		setRequestAttribute("comment", comment);
		setRequestAttribute("resource", comment_resource);

		return true;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/** 用户服务的set方法 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}

	/** 用户文件存储服务的set方法 */
	public void setStoreManager(StoreManager sto_mgr) {
		this.sto_mgr = sto_mgr;
	}

	/** 协作组服务的set方法 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}

	/** 评论服务的set方法 */
	public void setCommentService(CommentService cmt_svc) {
		this.cmt_svc = cmt_svc;
	}

	/** 设置点击率服务的set方法 */
	public void setViewCountService(ViewCountService viewcount_svc) {
		this.viewcount_svc = viewcount_svc;
	}

	public void setPrepareCourseService(PrepareCourseService pc_svc) {
		this.pc_svc = pc_svc;
	}

	public void setEvaluationService(EvaluationService es_svc) {
		this.es_svc = es_svc;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	/** 标签服务的set方法 */
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	/** 配置服务的set方法 */
	public void setConfigService(ConfigService cfg_svc) {
		this.cfg_svc = cfg_svc;
	}

	/** 角色权限服务 */
	public void setUserPowerService(UserPowerService userPowerService) {
		this.userPowerService = userPowerService;
	}

	public void setChannelPageService(ChannelPageService channelPageService) {
		this.channelPageService = channelPageService;
	}

	public void setUserIndexHtmlService(
			UserIndexHtmlService userIndexHtmlService) {
		this.userIndexHtmlService = userIndexHtmlService;
	}

	public void setAccessControlService(
			AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}
}
