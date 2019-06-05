package cn.edustar.jitar.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.CategoryModel;
import cn.edustar.jitar.model.CategoryTreeModel;
import cn.edustar.jitar.pojos.Category;
import cn.edustar.jitar.pojos.Photo;
import cn.edustar.jitar.pojos.PhotoStaple;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.PhotoService;
import cn.edustar.jitar.service.PhotoStapleQueryParam;
import cn.edustar.jitar.service.PhotoStapleService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.PageContent;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 照片分类管理.
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Apr 28, 2008 11:02:45 AM
 */
public class PhotoStapleAction extends AbstractServletAction {

	/** serialVersionUID */
	private static final long serialVersionUID = -2519357566771659918L;

	/** 分类名称 */
	private String title;

	/** 分类描述 */
	private String stapleDescribe;

	/** 是否隐藏(0:不隐藏,1:隐藏) */
	private boolean isHide;

	/** 获取参数辅助对象 */
	private ParamUtil param_util;

	/** 用户服务 */
	private UserService userService;

	/** 照片分类对象 */
	private PhotoStapleService photoStapleService;
	
	/** 相册服务 */
	private PhotoService photoService;
	
	/** 相册分类集合 */
	private List<Integer> photoStapleIds;
	
	/** 相册列表 */
	private List<Photo> photoList = new ArrayList<Photo>();

	/** 分类服务 */
	private CategoryService categoryService;
	
	private final String LIST_TREE_SUCCESS = "ListTree_Success";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		// 登录验证
		if (isUserLogined() == false)
			return LOGIN;
		
		// 是否是自己管理自己的分类
		if (canVisitUser(getLoginUser()) == false)
			return ERROR;

		/** 获得上下文对象 */
		this.param_util = new ParamUtil(getActionContext().getParameters());

		/** 获得页面的参数 */
		String cmd = param_util.safeGetStringParam("cmd");

		if ("list".equalsIgnoreCase(cmd) || null == cmd || "".equalsIgnoreCase(cmd)) {
			return list();
		} else if ("add".equalsIgnoreCase(cmd)) {
			return add();
		} else if ("save".equalsIgnoreCase(cmd)) {
			return save();
		} else if ("del".equalsIgnoreCase(cmd)) {
			return delete();
		} else if ("upd".equalsIgnoreCase(cmd)) {
			return update();
		} else if ("updsave".equalsIgnoreCase(cmd)) {
			return updateSave();
		}

		return ERROR;
	}

	/**
	 * 相册分类列表
	 * 
	 * @return
	 * @throws Exception
	 */
	private String list() throws Exception {
		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;

		/*
		String username = super.getLoginUserName();
		User _UserModel = userService.getUserByLoginName(username);
		int curLoginUserId = _UserModel.getUserId();
		
		int page = param_util.safeGetIntParam("page", 1);
		PhotoStapleQueryParam param = new PhotoStapleQueryParam();
		param.userId = curLoginUserId;
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(15);
		pager.setItemNameAndUnit("分类", "个");
		 */
		/** 查询并设置数据到 request 中 */
		/*
		Object photostaple_list = this.photoStapleService.getPhotoStapleDataTable(param, pager);
		setRequestAttribute("photostaple_list", photostaple_list);
		setRequestAttribute("pager", pager);

		return LIST_SUCCESS;
		*/
		/** 查询并设置树状的分类数据到 request 中 */
		SetCategoryTree();
		setRequestAttribute("user", getLoginUser());
		return LIST_TREE_SUCCESS;
	}
	/**
	 * 查询并设置树状的分类数据
	 */
	private void SetCategoryTree(){
		 List<Category> list = this.photoStapleService.getPhotoStapleTreeList(getLoginUser().getUserId());  //将查询到的分类结果转换为 List<Category>
		CategoryTreeModel category_tree = this.categoryService.getCategoryTree(list);			//将 List<Category>转换为树结构的CategoryTreeModel
		setRequestAttribute("category_tree", category_tree);
	}
	/**
	 * 显示'相册分类的添加'页面
	 * 
	 * @return
	 */
	private String add() {
		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;
		/** 查询并设置树状的分类数据到 request 中 */
		SetCategoryTree();
		return ADD_SUCCESS;
	}

	/**
	 * 保存一个'相册分类'
	 * 
	 * @return
	 * @throws IOException
	 */
	private String save() throws IOException {
		PrintWriter out = response.getWriter();

		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;

		String username = super.getLoginUserName();
		User _UserModel = userService.getUserByLoginName(username);
		int curLoginUserId = _UserModel.getUserId();

		if (title.length() == 0 && "".equalsIgnoreCase(title)) {
			this.addFieldError("title", "分类名称不能为空！");
			return ADD_SUCCESS;
		} else {
			PhotoStaple photoStaple = SavePhotoStaple(curLoginUserId);
			photoStapleService.savePhotoStaple(photoStaple);
			String refer = this.param_util.safeGetStringParam("refer");
			if(refer != null)
			{
				if(refer.equals("msgbox"))
				{
					out.print("200 OK||" + photoStaple.getId());
					out.close();
					return NONE;
				}
			}
		}
		response.setContentType("text/html");
		out.append(PageContent.PAGE_UTF8);
		out.println("<!doctype html><script>alert('相册分类添加成功！');window.location.href='" + PageContent.getAppPath() + "/manage/photostaple.action?cmd=list';</script>");
		out.flush();
		out.close();
		return NONE;
	}

	/**
	 * 删除相册分类
	 * 
	 * @return
	 * @throws Exception
	 */
	private String delete() throws Exception {
		photoStapleIds = param_util.safeGetIntValues("photoStapleId");
		for (Integer photoStapleId : photoStapleIds) {
            List<PhotoStaple> childList = photoStapleService.getPhotoStapleChilds(photoStapleId);
            if(null != childList){
                if (childList.size() > 0)
                {
                	this.addActionError("分类下存在子分类，不允许删除该分类！");
                	return ERROR;
                }
            }
			// 先将'Photo'表中所有包含该'photoStapleId'的值都设为'NULL'
			// 根据'用户Id'和'userStaple'得到相册列表.
			photoList = photoService.getPhotoList(this.getLoginUser().getUserId(), photoStapleId);
			
			if (photoList.size() > 0) {
				for (Photo photo : photoList) {					
					Integer userStaple = photo.getUserStaple();					
					if (userStaple == null) {
						userStaple = 0;
					} else {
						photo.setUserStaple(null);
						photoService.updatePhoto(photo);
					}
				}
			}			
			// 然后删除'PhotoStaple'记录
			photoStapleService.delPhotoStaple(photoStapleId);
		}
		this.addActionError("删除成功！");
		return list();
	}

	/**
	 * 显示修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	private String update() throws Exception {
		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;

		String myPhotoStapleId = param_util.safeGetStringParam("photoStapleId");
		if (myPhotoStapleId == "" || "".equals(myPhotoStapleId) || myPhotoStapleId.length() == 0) {
			this.addActionError("没有找着ID！");
			return ERROR;
		}
		
		PhotoStaple photoStaple = photoStapleService.findById(Integer.parseInt(myPhotoStapleId));
		title = photoStaple.getTitle();
		stapleDescribe = photoStaple.getStapleDescribe();
		isHide = photoStaple.getIsHide();
		
		/** 查询并设置树状的分类数据到 request 中 */
		SetCategoryTree();
		setRequestAttribute("photoStaple", photoStaple);
		setRequestAttribute("myPhotoStapleId", myPhotoStapleId);
		setRequestAttribute("stapleDescribe", stapleDescribe);
		setRequestAttribute("isHide", isHide);

		return UPDATE_SUCCESS;
	}

	/**
	 * 修改相册分类
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	private String updateSave() throws java.lang.Exception {
		PrintWriter out = response.getWriter();

		/** 登录验证 */
		if (!isUserLogined())
			return LOGIN;

		String username = super.getLoginUserName();
		User _UserModel = userService.getUserByLoginName(username);
		int curLoginUserId = _UserModel.getUserId();
		
		String title = param_util.safeGetStringParam("title");
		String stapleDescribe = param_util.safeGetStringParam("stapleDescribe");
		isHide = param_util.safeGetBooleanParam("isHide");

		//String myPhotoStapleId = param_util.safeGetStringParam("myPhotoStapleId");
		int myPhotoStapleId = param_util.getIntParam("myPhotoStapleId");
		Integer parentId = param_util.safeGetIntParam("parentId");
		
		if(parentId.equals(myPhotoStapleId)){
			this.addActionError("不允许将自己设置为自己的上级分类！");
			return ERROR;
		}
		if(photoStapleService.isInChildPath(myPhotoStapleId, parentId)){
			this.addActionError("不允许将自己设置为自己的下级分类！");
			return ERROR;
		}
		PhotoStaple photoStaple = photoStapleService.findById(myPhotoStapleId);
		photoStaple.setUserId(curLoginUserId);
		photoStaple.setParentId(parentId);
		// photoStaple.setOrderNum(0); // 暂不做排序.
		photoStaple.setIsHide(isHide);
		photoStaple.setTitle(title);
		photoStaple.setStapleDescribe(stapleDescribe);		
		photoStapleService.savePhotoStaple(photoStaple);
		response.setContentType("text/html");
		out.append(PageContent.PAGE_UTF8);
		out.println("<!doctype html><script>alert('相册分类修改成功！');window.location.href='" + PageContent.getAppPath() + "/manage/photostaple.action?cmd=list';</script>");
		out.flush();
		out.close();
		return NONE;
	}

	/**
	 * 保存一个相册分类
	 * 
	 * @param curLoginUserId
	 * @return
	 */
	private PhotoStaple SavePhotoStaple(int curLoginUserId) {
		PhotoStaple photoStaple = new PhotoStaple();
		photoStaple.setUserId(curLoginUserId);
		photoStaple.setOrderNum(0);
		photoStaple.setParentId(param_util.safeGetIntParam("parentId"));
		photoStaple.setIsHide(param_util.safeGetBooleanParam("isHide"));
		photoStaple.setTitle(title);
		photoStaple.setStapleDescribe(stapleDescribe);
		return photoStaple;		
	}
	
	// Get and set
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStapleDescribe() {
		return stapleDescribe;
	}

	public void setStapleDescribe(String stapleDescribe) {
		this.stapleDescribe = stapleDescribe;
	}

	public boolean isHide() {
		return isHide;
	}

	public void setHide(boolean isHide) {
		this.isHide = isHide;
	}

	/** 用户服务的set方法 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/** 照片分类对象的set方法 */
	public void setPhotoStapleService(PhotoStapleService photoStapleService) {
		this.photoStapleService = photoStapleService;
	}
	
	/** 相册服务的set方法 */
	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}
	/**
	 * 分类服务
	 * @param categoryService
	 */
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}	
}
