package cn.edustar.jitar.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.JitarConst;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.FileStorage;
import cn.edustar.jitar.service.StoreManager;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.ImgUtil;

/**
 * 用户文件管理, 提供给 FCKeditor browser.html, fileman/index.jsp 使用的接口
 * 
 * @remark 其被 browser.html 通过 ajax 方式调用, 返回一个 XML 做为响应
 * @see 相当于实现了如下描述的接口 : http://docs.fckeditor.net/FCKeditor_2.x/Developers_Guide/Server_Side_Integration
 */
@SuppressWarnings("serial")
public class UserFileManageAction extends ManageBaseAction {

	/** 日志 */
	private static final Log log = LogFactory.getLog(UserFileManageAction.class);

	/** 存储服务 */
	private StoreManager sto_mgr;

	/** 用户服务 */
	@SuppressWarnings("unused")
	private UserService user_svc;

	/** 当前登录用户 */
	private User user;

	/** 上传上来的文件，由 struts 注入, fckeditor 使用的上传的文件框名字为 'NewFile'. */
	private List<File> newFile;
	private List<String> newFileFileName; // 由属性file+Filename固定组成
	@SuppressWarnings("unused")
	private List<String> newFileContentType; // 由属性file+ContentType固定组成

	/**
	 * 构造
	 */
	public UserFileManageAction() {

	}

	/** 存储服务 */
	public void setStoreManager(StoreManager sto_svc) {
		this.sto_mgr = sto_svc;
	}

	/** 用户服务 */
	public void setUserService(UserService user_svc) {
		this.user_svc = user_svc;
	}

	/** 上传上来的文件，由 struts 注入. */
	public void setNewFile(List<File> file) {
		this.newFile = file;
	}

	/** 原始文件名, 由 struts 注入 */
	public void setNewFileFileName(List<String> fileFileName) {
		this.newFileFileName = fileFileName;
	}

	/** 文件 content-type, 由 struts 注入 */
	public void setNewFileContentType(List<String> fileContentType) {
		this.newFileContentType = fileContentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	protected String execute(String cmd) throws Exception {
		// 得到当前登录用户, 如果未登录则要求登录
		if (isUserLogined() == false)
			return LOGIN;
		this.user = super.getLoginUser();

		// 得到命令参数, fckeditor 和我们用的参数 key 不一样, 所以要重新获取
		cmd = request.getParameter("Command");
		
		if (log.isDebugEnabled()) {
			log.debug("UserFileMan user = " + user + ", cmd = " + cmd);
		}
		setRequestAttribute("Command", cmd);

		try {
			if ("GetFolders".equals(cmd))
				return getFolders();
			else if ("GetFiles".equals(cmd))
				return getFiles();
			else if ("GetFoldersAndFiles".equals(cmd))
				return getFoldersAndFiles();
			else if ("CreateFolder".equals(cmd))
				return createFolder();
			else if ("DeleteFile".equals(cmd))
				return deleteFile();
			else if ("DeleteFolder".equals(cmd))
				return deleteFolder();
			else if ("FileUpload".equals(cmd))
				return fileUpload();
		} catch (IOException ex) {
			return error(ERROR_CUSTOM_ERROR, "系统文件操作错误 (IOException)");
		} catch (Exception ex) {
			return error(ERROR_CUSTOM_ERROR, "系统未知错误 (Exception)");
		}

		return error(ERROR_CUSTOM_ERROR, "未知协议命令: " + cmd);
	}

	/** fckeditor/fkfinder error number : NO_ERROR = 0 */
	public static final int ERROR_NO_ERROR = 0;

	/**
	 * fckeditor/fkfinder error number : CUSTOM_ERROR = 1, Custom error. Message
	 * placed in the "text" attribute.
	 */
	public static final int ERROR_CUSTOM_ERROR = 1;

	/** fckeditor/fkfinder error number : 101 - 文件夹已经存在 */
	public static final int ERROR_FOLDER_ALREADY_EXISTS = 101;

	/** fckeditor/fkfinder error number : 102 - 非法的文件夹名字 */
	public static final int ERROR_INVALID_FOLDER_NAME = 102;

	/** fckeditor/fkfinder error number : 103 - 没有创建文件夹的权限 */
	public static final int ERROR_ACCESS_DENIED = 103;

	/** fckeditor/fkfinder error number : 103 - 在创建文件夹的时候发生未知错误 */
	public static final int ERROR_UNKNOWN_ERROR_CREATING_FOLDER = 110;

	/**
	 * 返回错误描述信息
	 * 
	 * @param number 错误编号
	 * @param errorMessage 错误消息
	 * @return
	 */
	private String error(int number, String errorMessage) {
		setRequestAttribute("ErrorNumber", number);
		setRequestAttribute("ErrorMessage", errorMessage);
		return ERROR;
	}

	/**
	 * 得到文件夹列表(XML response)
	 * 
	 * @return
	 */
	private String getFolders() throws IOException {
		return getFoldersAndFiles(true, false);
	}

	/**
	 * 得到文件列表 (XML response)
	 * 
	 * @return
	 * @throws IOException
	 */
	private String getFiles() throws IOException {
		return getFoldersAndFiles(false, true);
	}

	/**
	 * 得到文件夹 + 文件列表 (XML response)
	 * 
	 * @return
	 */
	private String getFoldersAndFiles() throws IOException {
		return getFoldersAndFiles(true, true);
	}

	/**
	 * 得到文件夹 + 文件列表(XML response)
	 * 
	 * @param includeFolder 是否包含文件夹
	 * 
	 * @param includeFile 是否包含文件
	 * @return
	 * @throws IOException
	 * @remark URL sample: http://localhost:8080/Groups/manage/userfm/fileman.action?
	 *         Command=GetFoldersAndFiles&Type=File&CurrentFolder=%2F&uuid=1207822586867
	 *         OR:
	 *         Command=GetFoldersAndFiles&Type=File&CurrentFolder=%2F%E9%A2%98%E5%BA%93%2F&uuid=1207823958192
	 */
	private String getFoldersAndFiles(boolean includeFolder, boolean includeFile) throws IOException {
		// 得到参数 ResourceType 和 CurrentFolder.
		String resourceType = request.getParameter("Type");
		setRequestAttribute("ResourceType", resourceType);
		String currentFolder = request.getParameter("CurrentFolder");
		if (currentFolder == null || currentFolder.length() == 0)
			currentFolder = "/"; // root
		setRequestAttribute("CurrentFolder", currentFolder);

		FileStorage store = sto_mgr.getUserFileStorage(user._getUserObject());
		File cur_folder = store.getFile(currentFolder);
		if (cur_folder == null)
			return error(ERROR_CUSTOM_ERROR, "未能找到用户的文件夹, 可能是配置不正确");
		if (cur_folder.exists() == false || cur_folder.isDirectory() == false)
			return error(ERROR_CUSTOM_ERROR, "系统错误: 用户文件夹非法");

		// 计算 rel_path, 根据 rel_path 计算出 url
		String currentFolderUrl = calcFileUrl(store, cur_folder);
		setRequestAttribute("CurrentFolderUrl", currentFolderUrl);

		// 列出所有文件
		File[] child_files = cur_folder.listFiles();
		List<File> file_list = new ArrayList<File>(child_files == null ? 1
				: child_files.length);
		List<File> folder_list = new ArrayList<File>();

		if (child_files != null && child_files.length > 0) {
			for (File f : child_files) {
				if (isShowFile(f) == false)
					continue;
				if (includeFile && f.isFile())
					file_list.add(f);
				else if (includeFolder && f.isDirectory())
					folder_list.add(f);
			}
		}

		setRequestAttribute("file_list", file_list);
		setRequestAttribute("folder_list", folder_list);

		return "FoldersAndFiles";
	}

	/**
	 * 创建文件夹 (XML response)
	 * 
	 * @return
	 */
	private String createFolder() throws IOException {
		// 得到当前文件夹参数
		String currentFolder = request.getParameter("CurrentFolder");
		if (currentFolder == null || currentFolder.length() == 0)
			currentFolder = "/"; // root
		setRequestAttribute("CurrentFolder", currentFolder);

		// 得到要创建的新文件夹名字参数.
		String new_folder_name = request.getParameter("NewFolderName");
		if (new_folder_name == null || new_folder_name.trim().length() == 0)
			return error(ERROR_INVALID_FOLDER_NAME, null);
		//对文件夹名称进行规范，禁止使用xxx.jsp之类的文件夹名称
		if(CommonUtil.isValidName(new_folder_name) == false)
		{
			return error(ERROR_CUSTOM_ERROR, "无效的文件夹名称，文件夹名称必须是：英文、数字、下划线做为名字字符，第一个字符必须是英文。");
		}
		
		// 现在该 store service 大显身手了吧.
		FileStorage store = sto_mgr.getUserFileStorage(user._getUserObject());
		File cur_folder = store.getFile(currentFolder);
		if (cur_folder == null)
			return error(ERROR_CUSTOM_ERROR, "未能找到用户的文件夹, 可能是配置不正确");
		if (cur_folder.exists() == false || cur_folder.isDirectory() == false)
			return error(ERROR_CUSTOM_ERROR, "系统错误: 用户文件夹非法");

		// 创建文件夹.
		File new_folder = new File(cur_folder + File.separator + new_folder_name);
		if (new_folder.exists())
			return error(ERROR_FOLDER_ALREADY_EXISTS, null);
		try {
			if (new_folder.mkdir() == false)
				return error(ERROR_INVALID_FOLDER_NAME, null);
		} catch (SecurityException sex) {
			return error(ERROR_ACCESS_DENIED, null);
		}

		// 创建成功, 准备返回数据.
		String currentFolderUrl = calcFileUrl(store, cur_folder);
		setRequestAttribute("CurrentFolderUrl", currentFolderUrl);

		return "CreateFolderSuccess";
	}

	/**
	 * 删除文件. ?Command=DeleteFile&type=Files&currentFolder=%2F&FileName=Test.jpg
	 * 
	 * @return
	 * @throws IOException Sample Response - 当前未按照此实现. <?xml version="1.0"
	 *            encoding="utf-8"?> <Connector resourceType="Files"> <Error
	 *            number="0" /> <CurrentFolder path="/" url="/userfiles/files/"
	 *            acl="255" /> <DeletedFile name="Test.jpg" /> </Connector>
	 */
	private String deleteFile() throws IOException {
		// 得到参数.
		String currentFolder = param_util.safeGetStringParam("CurrentFolder");
		if (currentFolder == null || currentFolder.length() == 0)
			currentFolder = "/";
		String fileName = param_util.safeGetStringParam("FileName");

		// 得到存储及其位置.
		FileStorage store = sto_mgr.getUserFileStorage(user._getUserObject());
		File cur_folder = store.getFile(currentFolder);
		if (cur_folder == null)
			return error(ERROR_CUSTOM_ERROR, "未能找到用户的文件夹, 可能是配置不正确");
		if (cur_folder.exists() == false || cur_folder.isDirectory() == false)
			return error(ERROR_CUSTOM_ERROR, "系统错误: 用户文件夹非法");

		// 得到文件.
		File user_f = store.getFile(currentFolder + fileName);
		if (user_f == null)
			return error(ERROR_CUSTOM_ERROR, "无法找到用户文件: " + currentFolder
					+ fileName);
		if (user_f.isDirectory())
			return error(ERROR_CUSTOM_ERROR, "不能用 DeleteFile 命令删除文件夹.");

		if (user_f.isHidden() || user_f.canWrite() == false)
			return error(ERROR_CUSTOM_ERROR, "不能删除系统文件或只读文件.");

		// 删除该文件.
		user_f.delete();

		return error(0, fileName);
	}

	/**
	 * 删除文件夹. 注意: 和 fck 要求规范参数上稍微有差别.
	 * ?Command=DeleteFolder&type=Files&CurrentFolder=%2FTest%2F&FolderName=xxx
	 * 
	 * @return
	 * @throws IOException
	 *            Sample Response: - 当前未按照此实现. <?xml version="1.0"
	 *            encoding="utf-8"?> <Connector resourceType="Files"> <Error
	 *            number="0" /> <CurrentFolder path="/Test/"
	 *            url="/userfiles/files/Test/" acl="255" /> </Connector>
	 */
	private String deleteFolder() throws IOException {
		// 得到参数.
		String currentFolder = param_util.safeGetStringParam("CurrentFolder");
		if (currentFolder == null || currentFolder.length() == 0)
			currentFolder = "/";
		String folderName = param_util.safeGetStringParam("FolderName");
		//System.out.println("currentFolder = " + currentFolder + ", folderName = "	+ folderName);

		// 得到存储及其位置.
		FileStorage store = sto_mgr.getUserFileStorage(user._getUserObject());
		File cur_folder = store.getFile(currentFolder);
		if (cur_folder == null)
			return error(ERROR_CUSTOM_ERROR, "未能找到用户的文件夹, 可能是配置不正确");
		if (cur_folder.exists() == false || cur_folder.isDirectory() == false)
			return error(ERROR_CUSTOM_ERROR, "系统错误: 用户文件夹非法");

		// 得到待删除的文件夹.
		File user_f = store.getFile(currentFolder + folderName + "/");
		if (user_f.exists() == false)
			return error(ERROR_CUSTOM_ERROR, "文件夹 " + folderName + " 不存在.");
		if (user_f.isDirectory() == false)
			return error(ERROR_CUSTOM_ERROR, "名字 " + folderName + " 不是一个文件夹.");
		if (user_f.isHidden() || user_f.canWrite() == false)
			return error(ERROR_CUSTOM_ERROR, "不能删除系统文件夹或只读文件夹.");

		// 删除该文件夹.
		if (user_f.delete() == false)
			return error(ERROR_CUSTOM_ERROR, "不能删除该文件夹, 可能该文件夹里面有子文件或子文件夹.");

		return error(0, folderName);
	}

	/**
	 * 上传文件. (HTML response)
	 * @return
	 */
	private String fileUpload() throws IOException {
		String currentFolder = param_util.safeGetStringParam("CurrentFolder");
		String type = param_util.safeGetStringParam("Type");
		log.debug("fileUpload type = " + type + ", currentFolder = " + currentFolder);
		if (currentFolder == null || currentFolder.length() == 0)
			currentFolder = "/";
		// return upload_complete(1, "非法的上传文件夹.");

		// 验证是否上传了文件上来.
		if (this.newFile == null || this.newFile.size() == 0)
			return upload_complete(1, "未选择上传文件或服务器配置不正确, 无法获得上传文件对象.");

		// 计算目标路径.
		FileStorage store = sto_mgr.getUserFileStorage(user._getUserObject());
		File cur_folder = store.getFile(currentFolder);
		if (cur_folder.exists() == false || cur_folder.isDirectory() == false)
			return upload_complete(1, "非法的上传文件夹: 文件夹不存在或者不是一个文件夹.");

		File new_file = this.newFile.get(0);
		String fileName = this.newFileFileName.get(0);
		String fileExt = CommonUtil.getFileExtension(fileName);
		// 根据后缀判定文件是否允许上传.
		if (isAllowUpload(fileExt) == false)
			return upload_complete(1, "服务器配置为不允许上传 " + fileExt + " 类型的文件.");

		//System.out.println("cur_folder = " + cur_folder.getAbsolutePath());
		//System.out.println("fileName = " + fileName);
		
		fileName = createFileName(cur_folder,fileName);
		File user_f = new File(cur_folder,fileName);
		// 判定同名文件夹是否存在.
		if (user_f.isDirectory())
			return upload_complete(1, "同名文件或文件夹已经存在, 上传文件不能覆盖该文件或文件夹.");

		boolean name_changed = false;
		if (user_f.exists()) {
			// 同名文件存在, 我们给它改名.
			user_f = uniqueUserFile(user_f);
			fileName = CommonUtil.getFileNameFromHref(user_f.getAbsolutePath());
			name_changed = true;
		}
		String fileUrl = calcFileUrl(store, user_f);

		//System.out.println("new_file = " + new_file.getAbsolutePath());
		//System.out.println("user_f = " + user_f.getAbsolutePath());
		// 移动文件到该位置.
		//new_file.renameTo();
		CommonUtil.renameTo(new_file, user_f);
		//判断违禁的类型
		log.info("用户 " + user.toDisplayString() + " 上传了一个文件到 " + user_f);
		return upload_complete(name_changed ? 201 : 0, fileName, fileUrl, "");
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

	/** 给 f 后面添加数字以保证其不和现有文件重名 */
	private File uniqueUserFile(File f) {
		String fileName = f.getAbsolutePath();
		String extension = CommonUtil.getFileExtension(fileName);
		if (extension != null && extension.length() > 0) {
			extension = "." + extension;
			fileName = fileName.substring(0, fileName.length()
					- extension.length());
		}

		int i = 1;
		while (true) {
			File nf = new File(fileName + "_" + i + extension);
			if (nf.exists() == false)
				return nf;
			++i;
		}
	}

	/**
	 * 判断是否允许上传指定后缀种类的文件.
	 * 
	 * @param fileExt
	 * @return
	 */
	protected boolean isAllowUpload(String fileExt) {
		if ("py".equals(fileExt) || "jsp".equals(fileExt)
				|| "jspx".equals(fileExt) || "js".equals(fileExt)|| "php".equals(fileExt))
			return false;

		return true;
	}

	/** 使用 script 脚本提示用户上传成功或失败信息, 用于 fileUpload() */
	private String upload_complete(int result, String message)
			throws IOException {
		return upload_complete(result, "", "", message);
	}

	/** 使用 script 脚本提示用户上传成功或失败信息, 用于 fileUpload() */
	private String upload_complete(int result, String fileName, String fileUrl,
			String message) {
		request.setAttribute("__Result", result);
		request.setAttribute("__FileName", fileName);
		request.setAttribute("__FileUrl", fileUrl);
		request.setAttribute("__Message", message);
		return "Upload_Success";
		
	}

	/**
	 * 在这里写入逻辑, 哪些文件显示, 哪些文件不显示.
	 * 
	 * @param file
	 * @return
	 */
	protected boolean isShowFile(File file) {
		return true;
	}

	/**
	 * 得到当前的用户对象.
	 * 
	 * @return
	 */
	protected User getCurrentUser() {
		return this.user;
	}

	/** 得到指定用户的文件/文件夹的 HTTP 访问 URL 地址 */
	private String calcFileUrl(FileStorage store, File file) {
		String rel_path = store.getRelativePath(file);
		if (rel_path == null)
			return null;

		String ctx_path = servlet_ctxt.getContextPath();
		if (ctx_path == null)
			ctx_path = "/";
		if (!ctx_path.endsWith("/"))
			ctx_path += "/";

		// 这里的规则放在: Context + 'user/$loginName/'
		String file_url = ctx_path + "user/" + user.getLoginName() + rel_path;
		if (file.isDirectory() && file_url.endsWith("/") == false)
			file_url += "/";
		return file_url;
	}
	
}
