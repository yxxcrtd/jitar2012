package cn.edustar.jitar.action;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edustar.data.Pager;
import cn.edustar.data.Tuple;
import cn.edustar.jitar.JitarConst;
import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.model.Configure;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.ChannelPhoto;
import cn.edustar.jitar.pojos.ChannelUser;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.GroupMember;
import cn.edustar.jitar.pojos.GroupPhoto;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.pojos.SpecialSubject;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.query.sitefactory.UserIndexHtmlService;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.ChannelPageService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.FileStorage;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.PhotoQueryParam;
import cn.edustar.jitar.service.PhotoService;
import cn.edustar.jitar.service.PhotoStapleQueryParam;
import cn.edustar.jitar.service.PhotoStapleService;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.service.SpecialSubjectService;
import cn.edustar.jitar.service.StoreManager;
import cn.edustar.jitar.service.TagService;
import cn.edustar.jitar.service.UserPowerService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ImgUtil;

/**
 * 相册管理
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 29, 2008 10:22:42 AM
 */
public class PhotoAction extends ManageDocBaseAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 3601851347127857914L;

	/** 日志 */
	private transient static final Logger log = LoggerFactory.getLogger(PhotoAction.class);

	/** 定义一个'照片'对象 */
	private Photo photo;

	/** 相册服务 */
	private PhotoService photoService;

	/** 协作组服务 */
	private GroupService group_svc;
	
	/** 分类服务对象 */
	private CategoryService categoryService;

	/** 相册分类服务 */
	private PhotoStapleService photoStapleService;

	/** 用户文件存储服务 */
	private StoreManager storeManager;

	/** 配置对象 */
	private Configure config;

	/** 配置服务 */
	private ConfigService configService;
	
	/** 标签服务 */
	private TagService tagService;
	
	/** 资源服务 */
	protected ResourceService res_svc;
	
	/**用户角色权限服务*/
	private UserPowerService userPowerService;
	
	private SpecialSubjectService specialSubjectService;
	
	private ChannelPageService channelPageService;
	
	private UserIndexHtmlService userIndexHtmlService;
	
	private AccessControlService accessControlService;
	
	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}

	private List<File> file;
	private List<String> fileFileName; // 由属性file+Filename固定组成
	private List<String> fileContentType; // 由属性file+ContentType固定组成

	/** 图片名称 */
	private String pictureName;

	/** 照片Id */
	private int photoId;

	/** 照片标题 */
	private String title;

	/** 默认的照片标题 */
	private static String DEFAULT_TITLE = "请在此输入照片标题！";
	
	/** 是否只在自己的空间里显示。true-只显示在自己的空间里，false 可以显示在总站 */
	private boolean isPrivateShow;

	/** 系统分类，可以为空。 */
	private Integer sysCateId;

	/** 用户相册分类，如果没有选择分类则为：null */
	private Integer userStapleId;

	/** 照片标签 */
	private String tags;

	/** 照片描述 */
	private String summary;

	/** 照片地址 */
	private String href;

	/** 默认相册照片地址 */
	private String defaultHref;

	/** 用户相册照片地址 */
	private String userHref;

	/** 照片列表 */

	private List photoList = new ArrayList();

	/** 相册分类列表 */
	@SuppressWarnings("unchecked")
	private List photoStapleList = new ArrayList();

	/** 相册分类统计列表 */
	private List<Photo> photoAndStapleList = new ArrayList<Photo>();

	/** 相册分类列表中的'用户分类' */
	private Integer userStaple;

	/** 每个默认相册分类中的照片个数 */
	private int default_photo_int;

	/** 用户相册分类中的照片个数 */
	private int count;

	/** 默认相册分类列表(userStaple is NULL) */
	private List defaultPhotoList = new ArrayList();

	/** 开始ID */
	private int beginId;

	/** 结束ID */
	private int endId;
	
	/** userId */
	private int userId;

	private static final long FSIZE_K = 1024L; // 1K
	private static final long FSIZE_M = 1024L * 1024L; // 1M
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	public String execute(String cmd) throws Exception {
		
		//System.out.println("cmd="+cmd);
		// 得到配置对象
		this.config = configService.getConfigure();
		
		// 登录验证
		if (isUserLogined() == false)
			return LOGIN;
		
		// 是否能够显示指定用户
		if (canVisitUser(getLoginUser()) == false)
			return ERROR;

		if ("list".equalsIgnoreCase(cmd)) {
			return list();
		} else if ("upload".equalsIgnoreCase(cmd)) {
			return upload();
		} else if ("edit".equalsIgnoreCase(cmd)) {
			return edit();
		} else if ("save".equalsIgnoreCase(cmd)) {
			return save();
		} else if ("dest_cate".equals(cmd)) {
			return dest_cate();
		}else if ("channel_cate".equals(cmd)) {
			return channel_cate();
		}  else if ("del".equalsIgnoreCase(cmd)) {
			return delete();
		} else if ("delete".equalsIgnoreCase(cmd)) {
			return deletes();
		} else if ("view".equalsIgnoreCase(cmd)) {
			return view();
		} else if ("pub_group".equalsIgnoreCase(cmd)) {
			return pub_group();			
		} else if ("allStaple".equalsIgnoreCase(cmd)) {
			return allStaple();
		} else if ("tools".equals(cmd)) {
			return tools();
		}else if ("pub_to_channel".equals(cmd)) {
            return pub_to_channel();
        } else
			return unknownCommand(cmd);
	}

	private String dest_cate() {
		int groupId = param_util.getIntParam("groupId");
		String itemType = CommonUtil.toGroupPhotoCategoryItemType(groupId);
		CategoryTreeModel gres_categories = this.categoryService.getCategoryTree(itemType);
		setRequestAttribute("gres_categories", gres_categories);
		return "Dest_Cate";
	}	
	
	private String channel_cate() {
		int channelId = param_util.getIntParam("channelId");
		String itemType = CategoryService.CHANNEL_PHOTO_PREFIX + channelId;
		CategoryTreeModel gres_categories = this.categoryService.getCategoryTree(itemType);
		setRequestAttribute("gres_categories", gres_categories);
		return "Channel_Cate";
	}	
	/**
	 * 显示所有的用户相册分类
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String allStaple() throws Exception {

		/** 默认相册分类列表 */
		defaultPhotoList = photoService.getDefaultPhotoList(this.getLoginUser().getUserId());

		/** 每个默认相册分类中的照片个数 */
		default_photo_int = defaultPhotoList.size();

		// 得到'第一个对象'
		Photo firstPhoto = null;
		if (default_photo_int > 0) {
			firstPhoto = (Photo) defaultPhotoList.get(0);
			defaultHref = firstPhoto.getHref();
		}

		// 相册分类列表.
		List<Map> userPhotoStapleList = photoStapleService.getPhotoAndStapleList(this.getLoginUser().getUserId());

		if (userPhotoStapleList.size() > 0) {
			for (int i = 0; i < userPhotoStapleList.size(); i++) {
				userStaple = (Integer) userPhotoStapleList.get(i).get("userStaple");
				if (userStaple == null)
					userStaple = 0;

				// 根据'用户Id'和'userStaple'得到相册列表.
				photoAndStapleList = photoService.getPhotoList(this.getLoginUser().getUserId(), userStaple);
				Photo photo = null;
				if (photoAndStapleList.size() > 0) {
					photo = (Photo) photoAndStapleList.get(0);
					userHref = photo.getHref();
				} else
					userHref = "";
				userPhotoStapleList.get(i).put("userHref", userHref);
			}
		} else {
			userStaple = 0;
		}

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("title", "默认分类");
		map.put("count", default_photo_int);
		map.put("userStaple", "0");
		map.put("userHref", defaultHref);
		userPhotoStapleList.add(0, map);

		this.setRequestAttribute("userPhotoStapleList", userPhotoStapleList);

		return "List_Staple_Success";
	}

	/**
	 * 专题分类
	 */
	private void putSpecialSubject()
	{			
		List<SpecialSubject> ss = this.specialSubjectService.getValidSpecialSubjectList();
		if(ss != null)
		{			
			setRequestAttribute("specialsubject_list", ss);
		}				
	}
	
	
	/**
	 * 相册列表.
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String list() throws Exception {

		/** 默认相册分类列表 */
		defaultPhotoList = photoService.getDefaultPhotoList(this.getLoginUser().getUserId());

		/** 每个默认相册分类中的照片个数 */
		default_photo_int = defaultPhotoList.size();

		// 得到'第一个对象'
		Photo firstPhoto = null;
		if (default_photo_int > 0) {
			firstPhoto = (Photo) defaultPhotoList.get(0);
			defaultHref = firstPhoto.getHref();
		}

		// 个人分类的标题,第一个照片的连接地址和统计.
		PhotoStapleQueryParam ps_param = new PhotoStapleQueryParam();
		ps_param.userId = this.getLoginUser().getUserId();

		Pager ps_pager = new Pager();
		ps_pager.setPageSize(3);

		// 相册分类列表.
		List<Map> userPhotoStapleList = photoStapleService.getPhotoAndStapleList(this.getLoginUser().getUserId());
		this.setRequestAttribute("userPhotoStapleList", userPhotoStapleList);

		if (userPhotoStapleList.size() > 0) {
			for (int i = 0; i < userPhotoStapleList.size(); i++) {
				userStaple = (Integer) userPhotoStapleList.get(i).get("userStaple");
				if (userStaple == null)
					userStaple = 0;

				// 根据'用户Id'和'userStaple'得到相册列表.
				photoAndStapleList = photoService.getPhotoList(this.getLoginUser().getUserId(), userStaple);
				Photo photo = null;
				if (photoAndStapleList.size() > 0) {
					photo = (Photo) photoAndStapleList.get(0);
					userHref = photo.getHref();
				} else
					userHref = "";
				userPhotoStapleList.get(i).put("userHref", userHref);
			}
		} else {
			userStaple = 0;
		}

		// 相册查询参数.
		PhotoQueryParam param = new PhotoQueryParam();
		param.auditState = null;
		param.userId = this.getLoginUser().getUserId();
		param.userStaple = userStapleId;

		Pager pager = getCurrentPager();
		pager.setPageSize(20);
		pager.setItemNameAndUnit("图片", "张");
		pager.setUrlPattern("photo.action?cmd=list&amp;page={page}");

		// 得到照片列表.
		photoList = photoService.getPhotoListEx(param, pager);
		setRequestAttribute("pager", pager);

		// 获得用户参与的协作组
		putJoinedGroupToRequest(group_svc);
		
		this.putUserChannelList();
		return LIST_SUCCESS;
	}

	/** 计算群组图片树分类名字 */
	private String getGroupPhotoCategoryItemType(int groupId) {
		return CommonUtil.toGroupPhotoCategoryItemType(groupId);
	}		
	/**
	 * 显示'上传照片'页面.
	 * 
	 * @return
	 * @throws Exception
	 */
	private String upload() throws Exception {

		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;

		photoId = 0;
		isPrivateShow = true;
		setRequestAttribute("isPrivateShow", isPrivateShow);
		userId = super.getLoginUser().getUserId();

		//判断空间
		if (this.config.getBoolValue(Configure.USER_RESOURCE_UPLOAD_LIMIT, true) == true) {
			int sSize=this.userPowerService.getUploadDiskNum(userId,false);
			if(sSize!=-1){
				if(sSize==0){
					this.addActionError("您所在的角色组不允许上载照片！");
					return ERROR;
				}
				else{
					String totalSize=""+sSize;
					long longTotalSize = Integer.valueOf(totalSize) * 1024 * 1024;
					
					/** 计算当前用户的空间总和 */
					long resSize = res_svc.totalResourceSize(userId);
					setRequestAttribute("resSize", calculate(totalSize, longTotalSize, resSize));
		
					if (resSize > longTotalSize) {
						this.addActionError("您已经使用了超过 " + totalSize + "M 的空间！ 目前已经无法再上传照片！");
						return ERROR;
					}
				}
			}
		}
		
		Integer groupId = param_util.getIntParamZeroAsNull("groupId");  //上载到协助组
		if (groupId != null) {
			setRequestAttribute("groupId", groupId);
		} else {
			setRequestAttribute("groupId", 0);
		}
		
		CategoryTreeModel res_cate=null;
		if (groupId != null) {
			res_cate = categoryService.getCategoryTree(getGroupPhotoCategoryItemType(groupId));
		}
		setRequestAttribute("res_cate", res_cate);
		
		String newtitle = param_util.safeGetStringParam("title");
		if (DEFAULT_TITLE.equals(newtitle)) {
			title = DEFAULT_TITLE;
		} else {
			if (newtitle == null || newtitle.length() == 0) {
				title = DEFAULT_TITLE;
			} else
				title = this.getTitle();
		}

		tags = this.getTags();

		if (this.getSysCateId() == null) {
			sysCateId = 0;
		} else {
			sysCateId = this.getSysCateId();
		}

		if (this.getUserStapleId() == null) {
			userStapleId = 0;
		} else {
			userStapleId = this.getUserStapleId();
		}

		summary = this.getSummary();
		file = this.getFile();

		// 提供照片分类列表
		photoStapleList = photoStapleService.getPhotoStapleList(getLoginUser().getUserId());

		// 将'系统分类树'放到'request'中
		putSysCategoryToRequest();
		this.putSpecialSubject();
		putUserChannelList();
		
		// 获得用户参与的协作组
		putJoinedGroupToRequest(group_svc);		
		
		int specialSubjectId = this.param_util.getIntParam("specialSubjectId");
		if(specialSubjectId>0)
		{
			setRequestAttribute("specialSubjectId",specialSubjectId);
		}
		return "Edit_Success";
	}

	
	private void putUserChannelList()
	{
		User loginUser = super.getLoginUser();
		if(loginUser != null)
		{
			List<Channel> channel_List = this.channelPageService.getChannelListByUserId(loginUser.getUserId());
			if(channel_List != null && channel_List.size() > 0) setRequestAttribute("channel_List", channel_List);
		}
	}
	/**
	 * 显示'编辑照片'页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	private String edit() throws Exception {

		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;

		Photo photo = photoService.findById(photoId);

		/** 权限验证 */
		if (photo != null) {
			if (!this.accessControlService.isSystemAdmin(getLoginUser())) {
				if (photo.getUserId() != this.getLoginUser().getUserId()) {
					this.addActionError("请不要试图修改他人的照片！");
					return ERROR;
				}
			}
		}
		userId = photo.getUserId();
		title = photo.getTitle();
		isPrivateShow = photo.getIsPrivateShow();
		setRequestAttribute("isPrivateShow", isPrivateShow);
		summary = photo.getSummary();
		tags = photo.getTags();
		sysCateId = photo.getSysCateId() == null ? 0 : photo.getSysCateId();
		href = photo.getHref();
		userStapleId = photo.getUserStaple() == null ? 0 : photo.getUserStaple();
		photoStapleList = photoStapleService.getPhotoStapleList(getLoginUser().getUserId());
		putSysCategoryToRequest();
		this.putSpecialSubject();
		/*User loginUser = super.getLoginUser();
		if(loginUser != null && loginUser.getChannelId() != null)
		{
			Channel channel = this.channelPageService.getChannel(loginUser.getChannelId());
			if(channel != null) putChannelPhotoCategoryToRequest(channel);
		}*/
		
		setRequestAttribute("specialSubjectId",photo.getSpecialSubjectId());
		setRequestAttribute("photo",photo);
		return "Edit_Success";
	}
	
	private String calculate(String totalSize, long longTotalSize, long resSize) {
		log.info("已经上传的资源总量：" + resSize);
		log.info("剩余的空间：" + (longTotalSize - resSize));

		// 小于1K
		if (resSize < FSIZE_K) {
			if (resSize == 0) {
				return "您还没有上传任何资源！您的空间是：<b>" + totalSize + "M</b> ！";
			} else {
				return "您已经使用了 <b><1K</b> 空间！ 目前还剩余：<b>" + (int) ((longTotalSize - resSize) / FSIZE_M) + "M</b> 空间！";
			}
		}

		// 大于1K，小于1M
		if (resSize < FSIZE_M) {
			return "您已经使用了 <b>>" + (resSize / FSIZE_K) + "K</b> 空间！目前还剩余：<b>" + (int) ((longTotalSize - resSize) / FSIZE_M) + "M</b> 空间！";
		}

		// 大于1M，小于系统的设置的？M
		if (resSize < longTotalSize) {
			return "您已经使用了 <b>>" + (resSize / FSIZE_M) + "M</b> 空间！目前还剩余：<b>" + (int) ((longTotalSize - resSize) / FSIZE_M) + "M</b> 空间！";
		} else
			return "您已经使用了超过 <b>" + totalSize + "M</B> 的空间！目前已经无法再上传资源！";
	}
	/**
	 * 保存上传的照片.
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	private String save() throws Exception {

		// 验证照片标题是否为空.
		if (null == title || title.length() == 0 || "".equalsIgnoreCase(title) || DEFAULT_TITLE.equalsIgnoreCase(title)) {
			this.addActionError("请输入照片标题！");
			return this.upload();
		}

		// 根据'photoId'判断是'上传'还是'修改'
		if (photoId > 0) {
			Photo photo = photoService.findById(photoId);
			// 权限验证
			if (!this.accessControlService.isSystemAdmin(getLoginUser())) {
				if (photo.getUserId() != this.getLoginUser().getUserId()) {
					this.addActionError("请不要试图修改他人的照片！");
					return ERROR;
				}
			}
			
			photo.setTitle(title); // 不能为空
			photo.setIsPrivateShow(param_util.getBooleanParam("isPrivateShow"));
			photo.setSpecialSubjectId(param_util.getIntParam("specialSubjectId"));
			photo.setSysCateId(sysCateId); // 系统分类，可以为空
			photo.setUserStaple(userStapleId); // 用户相册分类，可以为空
			photo.setTags(tags.replaceAll("，", ",")); // 照片标签
			photo.setSummary(summary); // 照片描述
			photo.setUnitId(super.getLoginUser().getUnitId());
			Calendar c = Calendar.getInstance();
			photo.setLastModified(c.getTime()); // 更新最后修改时间
			photoService.updatePhoto(photo);
			publishPhotoToGroups(photo);
		} else {
			// 验证有没有选择上传文件
			if (this.file == null || this.file.size() == 0) {
				this.addActionError("请选择照片地址！");
				return this.upload();
			}

			// 准备工作.
			FileStorage store = storeManager.getUserFileStorage(getLoginUser()._getUserObject());
			File user_root = store.getRootFolder();
			
			for (int i = 0; i < this.file.size(); ++i) {
				photo = new Photo();

				// 收集'照片对象'
				collectionPhotoObject();
				
				// 过滤上传照片中出现的非法词汇
				if (screenPhoto(photo)) {
					return ERROR;
				}

				// 得到上传的照片.
				File f = this.file.get(i);
				
				// 将上传的照片移动到用户存储目录下, 如 '/user/'登录用户名'/photo/***.jpg'
				String fileName = createFileName(f, this.fileFileName.get(i));
				File destFile = new File(user_root, "/photo/" + fileName);
				
				// 判断上传的文件夹是否存在，如果不存在就创建一个
				if (destFile.getParentFile().exists() == false) {
					destFile.getParentFile().mkdirs();
				}
				
				// For Linux
				FileUtils.copyFile(f, destFile);
				
				//if (f.renameTo(destFile) == false)
					//throw new IOException("不能将照片：" + f + " 移动到：" + destFile);

				String href = "user/" + this.getLoginUser().getLoginName() + "/photo/" + fileName;

				photo.setHref(href);
				photo.setSize((int) destFile.length());
				try {
					Image image = ImageIO.read(destFile);
					int width = image.getWidth(null);
					int height = image.getHeight(null);
					photo.setWidth(width);
					photo.setHeight(height);
					//ServletContext sc = request.getSession().getServletContext();
					// 参数1(原照片文件), 参数2(生成的目标缩略图文件), 参数3(宽度), 参数4(高度)
					ImgUtil.saveImageAsJpg(destFile.toString(), destFile.getParentFile() + File.separator + "320x240_" + fileName, JitarConst.DEFAULT_IMG_WIDTH,JitarConst.DEFAULT_IMG_HEIGHT);
					ImgUtil.saveImageAsJpg(destFile.toString(), destFile.getParentFile() + File.separator + "200x240_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_1,JitarConst.DEFAULT_IMG_HEIGHT_1);
					ImgUtil.saveImageAsJpg(destFile.toString(), destFile.getParentFile() + File.separator + "200x120_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_2,JitarConst.DEFAULT_IMG_HEIGHT_2);
					ImgUtil.saveImageAsJpg(destFile.toString(), destFile.getParentFile() + File.separator + "160x120_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_3,JitarConst.DEFAULT_IMG_HEIGHT_3);
					ImgUtil.saveImageAsJpg(destFile.toString(), destFile.getParentFile() + File.separator + "565x280_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_4, JitarConst.DEFAULT_IMG_HEIGHT_4);
					ImgUtil.saveImageAsJpg(destFile.toString(), destFile.getParentFile() + File.separator + "70x70_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_5, JitarConst.DEFAULT_IMG_HEIGHT_5);
					ImgUtil.saveImageAsJpg(destFile.toString(), destFile.getParentFile() + File.separator + "690x400_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_6, JitarConst.DEFAULT_IMG_HEIGHT_6);
					ImgUtil.saveImageAsJpg(destFile.toString(), destFile.getParentFile() + File.separator + "230x250_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_7, JitarConst.DEFAULT_IMG_HEIGHT_7);
					ImgUtil.saveImageAsJpg(destFile.toString(), destFile.getParentFile() + File.separator + "230x136_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_8, JitarConst.DEFAULT_IMG_HEIGHT_8);
					ImgUtil.saveImageAsJpg(destFile.toString(), destFile.getParentFile() + File.separator + "230x100_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_9, JitarConst.DEFAULT_IMG_HEIGHT_9);
					ImgUtil.saveImageAsJpg(destFile.toString(), destFile.getParentFile() + File.separator + "665x500_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_10, JitarConst.DEFAULT_IMG_HEIGHT_10);
					ImgUtil.saveImageAsJpg(destFile.toString(), destFile.getParentFile() + File.separator + "125x100_" + fileName, JitarConst.DEFAULT_IMG_WIDTH_11, JitarConst.DEFAULT_IMG_HEIGHT_11);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//根据配置,是否先审核后发布
				// 是否允许大文件上载
				Configure conf = JitarContext.getCurrentJitarContext().getConfigService().getConfigure();
				boolean bCheck = conf.getBoolValue(Configure.BEFAULE_PUBLISH_CHECK, false);
				if(bCheck)
					photo.setAuditState(Resource.AUDIT_STATE_WAIT_AUDIT);
				else
					photo.setAuditState(Resource.AUDIT_STATE_OK);
				
				photoService.savePhoto(photo);
				
				publishPhotoToChannel(photo);
				publishPhotoToGroups(photo);
				
				// 上传提示.
				log.info("用户" + this.getLoginUser().getLoginName() + "上传的照片名称为：" + href);
			}
		}
		// 在返回页面中添加连接.manage/photo.action?cmd=list
		this.addActionLink("上传照片", "photo.action?cmd=upload");
		this.addActionLink("我的相册管理", "photo.action?cmd=list");
		Integer groupId = param_util.safeGetIntParam("groupId");
		if(groupId>0){
			this.addActionLink("小组相册管理", "groupPhoto.action?cmd=list&groupId="+groupId);
		}		
		userIndexHtmlService.genPhotoList(getLoginUser());
		return SUCCESS;
	}
	/**
	 * 发布到组
	 * 
	 * @return
	 */
	private String pub_group() {
		List<Integer> photo_ids = param_util.safeGetIntValues("pId");
		if (photo_ids == null || photo_ids.size() == 0 || 
				(photo_ids.size() == 1 && photo_ids.get(0).intValue() == 0)) {
			addActionError("未选择或给出要操作的图片");
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
		GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(group.getGroupId(), getLoginUser().getUserId());
		if (super.checkGroupMemberState(gm, group, getLoginUser()._getUserObject()) == false) {
			return ERROR;
		}

		// 循环操作
		int oper_count = 0;
		for (Integer photoId : photo_ids) {
			//System.out.println("photoId="+photoId);	
			Photo photo = photoService.findById(photoId);
			if (photo == null) {
				addActionError("未找到标识为 " + photoId + " 的图片！");
				continue;
			}
			// 验证是这个用户的, (权限验证), 以及不是私有的
			if (photo.getUserId() != getLoginUser().getUserId()) {
				addActionError("不能发布他(她)人的图片！");
				continue;
			}

			// 验证是否已经发布过了
			Tuple<Photo, GroupPhoto> gr_t = group_svc.getGroupPhotoByPhotoId(group.getGroupId(), photo.getPhotoId());
			if (gr_t != null) {
				addActionError("图片：" + photo.toDisplayString() + "，已经发布到了协作组：" + group.getGroupTitle());
				continue;
			}

			GroupPhoto gp = new GroupPhoto();
			gp.setGroupId(groupId);
			gp.setGroupCateId(groupCateId);
			gp.setPhotoId(photo.getId());
			gp.setUserId(photo.getUserId());
			gp.setPubDate(new Date());
			gp.setIsGroupBest(false);
			group_svc.publishPhotoToGroup(gp);
			addActionMessage("图片 ：" + photo.toDisplayString() + "，成功发布到了协作组：" + group.toDisplayString());
			
			++oper_count;
		}
		addActionMessage("共发布了：" + oper_count + " 个图片到协作组：" + group.getGroupTitle());
		return SUCCESS;
	}

	/**
	 * 发布到自定义频道
	 * @param photo
	 */
	private void publishPhotoToChannel(Photo photo)
	{
		if(photo == null) return;
		User loginedUser =  this.getLoginUser();
		if(loginedUser == null) return;
		//如果没有频道，就不处理了。
		if(!param_util.existParam("channelTotalCount")) return;
		int channelTotalCount = param_util.safeGetIntParam("channelTotalCount");
		for(int i = 0;i<channelTotalCount + 1; i++)
		{
			Integer channelId = param_util.getIntParamZeroAsNull("channelId" + i);
			String channelCate = param_util.getStringParam("channelCate" + i, null);
			String channelCateId = null;
			if(channelId != null)
			{
				//检查是否已经存在，这里是新加，一般不存在，可以不用检查。
				ChannelPhoto channelPhoto = this.channelPageService.getChannelPhoto(channelId, photo.getPhotoId());
				if(channelPhoto != null){
					continue;
				}
				if(channelCate != null){
					 String[] cateArray = channelCate.split("/");
					 if(cateArray.length > 1)
					 {
						 channelCateId = cateArray[cateArray.length - 1];
					 }
					 if(CommonUtil.isInteger(channelCateId) == false){
						 channelCateId = null;
					 }
				}
				if(channelCateId == null){
					channelCate = null;
				}
				ChannelPhoto cp = new ChannelPhoto();
				cp.setChannelCate(channelCate);
				cp.setChannelCateId(channelCateId == null?null:Integer.valueOf(channelCateId));
				cp.setChannelId(channelId);
				cp.setPhotoId(photo.getPhotoId());
				cp.setUserId(loginedUser.getUserId());
				cp.setUnitId(loginedUser.getUnitId());
				this.channelPageService.saveOrUpdateChannelPhoto(cp);
			}			
		}
	}
	/**
	 *同时发布图片到协作组中 
	 * @param photo
	 */
	private void publishPhotoToGroups(Photo photo) {
		Integer _groupIndex = param_util.safeGetIntParam("_groupIndex");
		if(_groupIndex==0)
			_groupIndex=1;

		// 进行循环操作
		for (int i=1;i<=_groupIndex;i++) {
			Integer groupId=param_util.getIntParamZeroAsNull("gpId"+i);
			Integer groupCateId=param_util.getIntParamZeroAsNull("groupCateId"+i);	
			if (groupId == null){
				continue;
			}
			// 得到协作组并验证
			Group group = group_svc.getGroupMayCached(groupId);
			if (group == null) {
				addActionError("指定标识的协作组不存在 groupId = " + groupId);
				continue;
			}
			if (checkGroupState(group._getGroupObject()) == false) {
				System.out.println("该组不允许增加内容");
				continue;
			}

			// 得到成员关系并验证
			GroupMember gm = group_svc.getGroupMemberByGroupIdAndUserId(groupId, getLoginUser().getUserId());
			if (this.checkGroupMemberState(gm, group._getGroupObject(), getLoginUser()._getUserObject()) == false) {
				continue;
			}

			// 是否已经发布了
			GroupPhoto gp = group_svc.getGroupPhotoByGroupAndPhoto(groupId, photo.getPhotoId());
			if (gp != null) {
				addActionError("您已经将图片：" + photo.getTitle() + "，发布到了协作组：" + group.toDisplayString() + ", 不需再次发布！");
				continue;
			}

			gp = new GroupPhoto();
			gp.setGroupId(groupId);
			gp.setGroupCateId(groupCateId);
			gp.setPhotoId(photo.getId());
			gp.setUserId(photo.getUserId());
			gp.setPubDate(new Date());
			gp.setIsGroupBest(false);
			group_svc.publishPhotoToGroup(gp);
			addActionMessage("图片 ：" + photo.toDisplayString() + "，成功发布到了协作组：" + group.toDisplayString());
		}
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	private String delete() throws Exception {
		Photo photo = photoService.findById(photoId);
		if (photo == null) {
			this.addActionError("照片不存在！");
			return list();
		}
		if (photo.getUserId() != getLoginUser().getUserId()) {
			this.addActionError(this.getText("groups.public.tip.delete"));
			return list();
		}
		group_svc.deleteGroupPhotoByPhoto(photo);
		photoService.delPhoto(photo);
		
		this.addActionError("删除成功！");
		return list();
	}

	private String deletes() throws Exception {
		
		List<Integer> photo_ids = param_util.safeGetIntValues("pId");
		if (photo_ids == null || photo_ids.size() == 0 || 
				(photo_ids.size() == 1 && photo_ids.get(0).intValue() == 0)) {
			addActionError("未选择或给出要操作的图片");
			return ERROR;
		}		

		// 循环操作
		int oper_count = 0;
		for (Integer photoId : photo_ids) {
			Photo photo = photoService.findById(photoId);
			if (photo == null) {
				addActionError("未找到标识为 " + photoId + " 的图片！");
				continue;
			}
			if (photo.getUserId() != getLoginUser().getUserId()) {
				this.addActionError(this.getText("groups.public.tip.delete"));
				return list();
			}
			group_svc.deleteGroupPhotoByPhoto(photo);
			photoService.delPhoto(photo);
			addActionMessage("成功删除图片 ：" + photo.toDisplayString() + "");
			++oper_count;
		}
		addActionMessage("共删除了：" + oper_count + " 个图片");
		return SUCCESS;		
	}
	/**
	 * 浏览次数
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	private String view() throws Exception {
		Photo photo = photoService.findById(photoId);
		int oldViewCount = photo.getViewCount();
		photoService.increaseViewCount(photoId, oldViewCount + 1);
		return list();
	}

	/**
	 * 将照片生成缩略图的工具
	 * 
	 * @return
	 * @throws Exception
	 */
	private String tools() throws Exception {
		PrintWriter out = response.getWriter();
		response.reset();
		response.resetBuffer();
		out.println("OK");
		out.flush();
		out.close();
		return NONE;
	}

	/**
	 * 收集一些公共的 照片对象
	 * 
	 * @return
	 */
	private Photo collectionPhotoObject() {
		photo.setTitle(title); // 不能为空
		photo.setIsPrivateShow(param_util.getBooleanParam("isPrivateShow"));
		photo.setSpecialSubjectId(param_util.getIntParam("specialSubjectId"));
		photo.setSysCateId(sysCateId); // 系统分类，可以为空
		photo.setUserStaple(userStapleId); // 用户相册分类，可以为空
		photo.setTags(tags); // 照片标签
		photo.setSummary(summary);
		photo.setUserId(this.getLoginUser().getUserId()); // 得到上传用户Id
		photo.setUserNickName(null==this.getLoginUser().getNickName()?"":this.getLoginUser().getNickName()); // 得到上传用户的'呢称'
		photo.setUserTrueName(this.getLoginUser()._getUserObject().getTrueName()); // 得到上传用户的'真实姓名'
		photo.setAddIp(request.getRemoteAddr());
		photo.setUnitId(super.getLoginUser().getUnitId());
		//photo.setChannelCate(param_util.safeGetStringParam("channelCate").equals("")?null:param_util.safeGetStringParam("channelCate"));
		
		return photo;
	}

	/**
	 * 过滤上传照片中出现的非法词汇
	 * 
	 * @param photo
	 * @return
	 */
	private boolean screenPhoto(Photo photo) {
		String title = photo.getTitle(); // 照片标题
		String tags[] = tagService.parseTagList((String) photo.getTags()); // 照片标签
		String summary = photo.getSummary(); // 照片描述
		String[] screen_keyword = tagService.parseTagList((String) this.config.getValue("site.screen.keyword")); // 系统中定义的需要屏蔽的非法词汇数组
		String screen_enalbed = (String) this.config.getValue("site.screen.enalbed"); // 是否屏蔽
		String screen_replace = (String) this.config.getValue("site.screen.replace"); // 替换的字符
		
		// 是否屏蔽系统中出现的非法词汇
		if ("true".equals(screen_enalbed)) {
			for (int i = 0; i < screen_keyword.length; ++i) {
				// 1，检查'照片标题'中是否含有'非法词汇'
				if (title.equals(screen_keyword[i])) {
						this.addActionError("您输入的'照片标题'与'非法词汇'完全相同！");
					return true;
				}
				if (title.indexOf(screen_keyword[i]) != -1) {
					title = title.replaceAll(screen_keyword[i], screen_replace);
					photo.setTitle(title);
				}
				
				// 2，检查'照片描述'中是否含有'非法词汇'
				if (summary.equals(screen_keyword[i])) {
					this.addActionError("您输入的'照片描述与'非法词汇'完全相同！");
					return true;
				}
				if (summary.indexOf(screen_keyword[i]) != -1) {
					summary = summary.replaceAll(screen_keyword[i], screen_replace);
					photo.setSummary(summary);
				}
			}
			
			// 检查'照片标签'中是否含有'非法词汇'
			for (int i1 = 0; i1 < screen_keyword.length; ++i1) {
					for (int j = 0; j < tags.length; j++) {
					if (tags[j].indexOf(screen_keyword[i1]) != -1) {
						tags[j] = tags[j].replaceAll(screen_keyword[i1], screen_replace);
						photo.setTags(CommonUtil.standardTagsString(tags));
					}
				}
			}
		}
		return false;
	}

	/**
	 * 为上传上来的指定文件生成服务器端名字
	 * 
	 * @param file 上传过来的照片
	 * @param oldName 用户实际的文件名
	 * @return
	 */
	private String createFileName(File file, String oldName) {

		// 得到上传照片的后缀.
		String extension = CommonUtil.getFileExtension(oldName);

		// 约定一个时间的格式.
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fd = sdf.format(c.getTime());

		// 用Java产生一个六位的随机数.
		int i = (int) (Math.random() * 1000000);
		String newName = fd + String.valueOf(i);

		// 合并返回.
		return newName + "." + extension.toLowerCase();
	}

	protected List<Integer> photo_ids;
    protected final boolean getPhotoIds() {
        this.photo_ids = param_util.safeGetIntValues("pId");
        if (photo_ids == null || photo_ids.size() == 0 || (photo_ids.size() == 1 && photo_ids.get(0).intValue() == 0)) {
            this.addActionError("未选择或给出要操作的图片");
            return false;
        }
        return true;
    }
	private String pub_to_channel(){
        // 获得标识参数
        if (this.getPhotoIds() == false){
            return ERROR;
        }
        // 得到参数 channelId:要发布的自定义频道; channelCateId：自定义频道视频分类
        int channelId = param_util.safeGetIntParam("channelId");
        if (channelId == 0) {
            addActionError("选择的自定义频道错误。");
            return ERROR;
        }
        
        Channel c = this.channelPageService.getChannel(channelId);
        if(null == c){
            addActionError("不能加载所选择的自定义频道信息。");
            return ERROR;
        }
        
        //检查协作组成员关系
        ChannelUser cu = this.channelPageService.getChannelUserByUserIdAndChannelId(getLoginUser().getUserId(), channelId);
        if(cu == null){
            addActionError("您不是该自定义频道的成员。");
            return ERROR;
        }
        
        //得到自定义频道文章分类 /xx/xxx/这样的格式
        String channelCate = param_util.safeGetStringParam("channelCateId",null);       
        
        if(null == channelCate){
            addActionError("没有选择自定义频道图片分类。");
            return ERROR;
        }
        String channelCateId = null;
        String[] cateArray = channelCate.split("/");
        if(cateArray.length > 1) {
            channelCateId = cateArray[cateArray.length - 1];
        }
        if(CommonUtil.isInteger(channelCateId) == false){
            channelCateId = null;
        }
        if(channelCateId == null){
            channelCate = null;
        }
        // 对选中的进行操作，需要先判断是否在这个分类中
        int oper_count = 0;
        for (int pId : this.photo_ids) {
                // 获得要操作的文章
                Photo p = this.photoService.findById(pId);
                if (p == null) {
                    addActionError("未找到标识为 " + pId + " 的图片");
                    continue;
                }
                // 验证是这个用户的, (权限验证)
                if (p.getUserId() != getLoginUser().getUserId()) {
                    addActionError("权限被拒绝, 试图操作他人的图片 [" + p.getTitle() + "]");
                    continue;
                }
                
                if(p.getIsPrivateShow() == true){
                    addActionError("图片 [" + p.getTitle() + "] 只在个人空间显示，不能发布到自定义频道。");
                    continue;
                }
                
                Boolean toUpdated = false;
                ChannelPhoto cp = this.channelPageService.getChannelPhoto(channelId, pId);
                if(null != cp ){
                    //更新文章分类
                    //为了方便比较
                    if(cp.getChannelCate() == null) cp.setChannelCate("");
                    if(cp.getChannelCateId() == null) cp.setChannelCateId(0);
                    
                    if(channelCateId == null){
                        if(!cp.getChannelCate().equals(channelCate) || cp.getChannelCateId() != null)
                        {
                            toUpdated = true;           
                            cp.setChannelCateId(null);
                        }
                    }
                    else
                    {
                        if(!cp.getChannelCate().equals(channelCate) || !cp.getChannelCateId().equals(Integer.valueOf(channelCateId))){
                            toUpdated = true;
                            cp.setChannelCateId(Integer.valueOf(channelCateId));
                        }
                    }
                    if(toUpdated){
                        cp.setChannelCate(channelCate);
                        if(cp.getChannelCate() == null || cp.getChannelCate().equals("")) cp.setChannelCate(null);
                        if(cp.getChannelCateId() == null || cp.getChannelCateId().equals(0)) cp.setChannelCateId(null);
                        
                        this.channelPageService.saveOrUpdateChannelPhoto(cp);
                        addActionMessage("图片 " + p.getTitle() + " 更新了视频分类.");
                    }
                    else{
                        addActionError("图片 " + p.getTitle() + " 已经发布到了频道  " + c.getTitle());
                    }                   
                    continue;
                }
                
                // 发布文章到频道
                ChannelPhoto channelPhoto = new ChannelPhoto();
                channelPhoto.setChannelCate(channelCate);
                channelPhoto.setChannelId(channelId);
                channelPhoto.setChannelCateId(channelCateId == null?null:Integer.valueOf(channelCateId));
                channelPhoto.setUnitId(this.getLoginUser().getUnitId());
                channelPhoto.setUserId(p.getUserId());
                channelPhoto.setPhotoId(pId);
                this.channelPageService.saveOrUpdateChannelPhoto(channelPhoto); 
                // 消息.
                addActionMessage("图片 " + p.getTitle() + " 成功发布到自定义频道 " + c.getTitle() + " 中.");
                ++oper_count;
            }
        addActionMessage("共发布了 " + oper_count + " 个图片到自定义频道 " + c.getTitle() + ".");
        return SUCCESS;
    }
	
	/**
	 * 将'系统分类树：syscate_tree'放到'request'中.
	 */
	private void putSysCategoryToRequest() {
		Object syscate_tree = categoryService.getCategoryTree(CategoryService.PHOTO_CATEGORY_TYPE);
		setRequestAttribute("syscate_tree", syscate_tree);
	}

	private void putChannelPhotoCategoryToRequest(Channel  channel)
	{
		Object cate_tree = categoryService.getCategoryTree("channel_photo_" + channel.getChannelId());
		setRequestAttribute("channel_photo_categories", cate_tree);
	}
	
	// Get and set
	/** 相册分类服务的set方法 */
	public void setPhotoStapleService(PhotoStapleService photoStapleService) {
		this.photoStapleService = photoStapleService;
	}

	/** 相册服务的set方法 */
	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}

	/** 用户文件存储服务的set方法 */
	public void setStoreManager(StoreManager storeManager) {
		this.storeManager = storeManager;
	}

	/** 协作组服务的set方法 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}

	
	/** 分类服务对象 */
	public void setCategoryService(CategoryService cat_svc) {
		this.categoryService = cat_svc;
	}

	/** 配置服务的'set'方法 */
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	/** 标签服务的'set'方法 */
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public List<String> getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	public List<String> getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	@SuppressWarnings("unchecked")
	public List getPhotoStapleList() {
		return photoStapleList;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Integer getSysCateId() {
		return sysCateId;
	}

	public void setSysCateId(Integer sysCateId) {
		this.sysCateId = sysCateId;
	}

	public Integer getUserStapleId() {
		return userStapleId;
	}

	public void setUserStapleId(Integer userStapleId) {
		this.userStapleId = userStapleId;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	@SuppressWarnings("unchecked")
	public List getPhotoList() {
		return photoList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getDefault_photo_int() {
		return default_photo_int;
	}

	public void setDefault_photo_int(int default_photo_int) {
		this.default_photo_int = default_photo_int;
	}

	@SuppressWarnings("unchecked")
	public List getDefaultPhotoList() {
		return defaultPhotoList;
	}

	public Integer getUserStaple() {
		return userStaple;
	}

	public void setUserStaple(Integer userStaple) {
		this.userStaple = userStaple;
	}

	public List<Photo> getPhotoAndStapleList() {
		return photoAndStapleList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDefaultHref() {
		return defaultHref;
	}

	public void setDefaultHref(String defaultHref) {
		this.defaultHref = defaultHref;
	}

	public String getUserHref() {
		return userHref;
	}

	public void setUserHref(String userHref) {
		this.userHref = userHref;
	}

	public int getBeginId() {
		return beginId;
	}

	public void setBeginId(int beginId) {
		this.beginId = beginId;
	}

	public int getEndId() {
		return endId;
	}

	public void setEndId(int endId) {
		this.endId = endId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isPrivateShow() {
		return isPrivateShow;
	}

	public void setPrivateShow(boolean isPrivateShow) {
		this.isPrivateShow = isPrivateShow;
	}

	public SpecialSubjectService getSpecialSubjectService() {
		return specialSubjectService;
	}

	public void setSpecialSubjectService(SpecialSubjectService specialSubjectService) {
		this.specialSubjectService = specialSubjectService;
	}
	/**角色权限服务*/
	public UserPowerService getUserPowerService(){
		return this.userPowerService;
	}
	/**角色权限服务*/
	public void setUserPowerService(UserPowerService userPowerService){
		this.userPowerService=userPowerService;
	}
	/** 资源服务 */
	public void setResourceService(ResourceService res_svc) {
		this.res_svc = res_svc;
	}

	public void setChannelPageService(ChannelPageService channelPageService) {
		this.channelPageService = channelPageService;
	}

	public void setUserIndexHtmlService(UserIndexHtmlService userIndexHtmlService) {
		this.userIndexHtmlService = userIndexHtmlService;
	}

}
