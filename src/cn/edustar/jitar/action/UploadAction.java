package cn.edustar.jitar.action;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.util.CasConst;
import com.chinaedustar.fcs.bi.enums.DeleteSrcEnum;
import com.chinaedustar.fcs.bi.enums.ObjectEnum;
import com.chinaedustar.fcs.bi.service.BiServiceImpl;
import com.chinaedustar.fcs.bi.vo.TaskVo;
import cn.edustar.jitar.model.UserMgrModel;
import cn.edustar.jitar.pojos.PrepareCourse;
import cn.edustar.jitar.pojos.PrepareCourseEdit;
import cn.edustar.jitar.pojos.PrepareCourseMember;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.PrepareCourseService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.EncryptDecrypt;
import cn.edustar.jitar.util.WebUtil;

import java.util.Date;

public class UploadAction extends ManageBaseAction {
	private static final long serialVersionUID = 8010990816230436776L;
	private File doc;
	private String fileName;
	private String contentType;
	private String prepareCourseId;
	private String saveLockStatus;
	private String returnType;
	private PrepareCourseService prepareCourseService;
	private String canUploadFileExts = ".doc|.docx|.ppt|.pptx";

	public void setPrepareCourseService(
			PrepareCourseService prepareCourseService) {
		this.prepareCourseService = prepareCourseService;
	}

	public String getPrepareCourseId() {
		return prepareCourseId;
	}

	public void setPrepareCourseId(String prepareCourseId) {
		this.prepareCourseId = prepareCourseId;
	}

	public String execute(String cmd) throws Exception {
		boolean _debug = false;
				
		if(CommonUtil.GetPrepareCourseFolder(request)[0].equals(""))
		{
			response.setContentType("text/plain;charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("服务器没有配置文件存储路径 preparecoursefolder，不能采用这种方法进行备课。");
			return NONE;
		}
		
		if (cmd.equals("uploadpreparecourse")) {
			if(saveLockStatus == null) saveLockStatus = "";
			if((contentType == null || contentType.equals("")) && !saveLockStatus.equals("1"))
			{
				response.setContentType("text/plain;charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("无内容");
				return NONE;
			}
			
			User user = WebUtil.getLoginUser(getSession());
			if (user == null) {
				//response.setHeader("X1", "当前用户为 null");
				response.setContentType("text/plain;charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("当前用户为 null");
				return NONE;
			}
			if (user.getUserStatus() != 0) {
				//response.setHeader("X1", "当前用户状态不正常");
				response.setContentType("text/plain;charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("当前用户状态不正常");
				return NONE;
			}
			if (prepareCourseId.equals("")) {
				//response.setHeader("X1", "missing prepareCourseId");
				response.setContentType("text/plain;charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("missing prepareCourseId");
				return NONE;
			}
			EncryptDecrypt ed = new EncryptDecrypt();
			prepareCourseId = ed.decrypt(prepareCourseId);
			ed = null;
			String[] temp = prepareCourseId.split("\\|");
			if(temp.length != 2)
			{
				//response.setHeader("X1", "集背标识格式不正确。");
				response.setContentType("text/plain;charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("集背标识格式不正确。");
				return NONE;
			}
			prepareCourseId = temp[0];
			String ownerFlag = temp[1]; //0:是个案，1：共案
			String fileExt = "";
			if(fileName.indexOf(".") > -1){
			    fileExt = fileName.substring(fileName.lastIndexOf("."));
			}
			fileExt = fileExt.toLowerCase();
			if(ownerFlag.equals("0"))
			{
			    //是否显示正确的ContentType，跟客户端注册表配置有关系，如果客户端注册表中的扩展名没有配置Content Type键，则会出现 application/octet-stream 的问题。
			    //改用验证扩展名：
				//if (!contentType.equalsIgnoreCase("application/msword")	&& !contentType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
				 //       && !contentType.equalsIgnoreCase("application/vnd.ms-powerpoint")  && !contentType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.presentationml.presentation")
				//        ) {
				    
				    if (!this.canUploadFileExts.contains(fileExt)) {
					//response.setHeader("X1", "上传的文档类型不正确，必须是 word 文档。");
					response.setContentType("text/plain;charset=UTF-8");
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().write("上传的文档类型不正确，必须是 word 或者  PowerPoint 文档。");
					return NONE;
				}
			}
			PrepareCourse pc = prepareCourseService.getPrepareCourse(Integer.valueOf(prepareCourseId));
			if (pc == null) {
				//response.setHeader("X1", "PrepareCourse is null");
				response.setContentType("text/plain;charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("PrepareCourse is null");
				return NONE;
			}

			//System.out.println("PrepareCourse=" + pc.getPrepareCourseEditId());
			if (pc.getStatus() != 0) {
				//response.setHeader("X1", pc.getTitle() + " status is unnormal");
				response.setContentType("text/plain;charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write(pc.getTitle() + " status is unnormal");
				return NONE;
			}
			//System.out.println("计算路径");
			String safeFilePath = getSavePath();
			if (!safeFilePath.endsWith(File.separator)) {
				safeFilePath += File.separator;
			}
			safeFilePath += pc.getPrepareCourseId() + File.separator;
			//System.out.println("safeFilePath=" + safeFilePath);
			File f = new File(safeFilePath);
			if (!f.exists()) {
				f.mkdirs();
			}
			
			String docFileName = "";
			if(ownerFlag.equals("0"))
			{
				//System.out.println("个案文件名是固定的，无需更新");
				docFileName = pc.getObjectGuid() + "_"	+ user.getUserGuid() + this.fileName.substring(this.fileName.lastIndexOf("."));
				PrepareCourseMember m = prepareCourseService.getPrepareCourseMemberByCourseIdAndUserId(pc.getPrepareCourseId(), user.getUserId());
				m.setContentLastupdated(new Date());
				m.setPrivateContent(docFileName);
				prepareCourseService.updatePrepareCourseMember(m);
			}
			else
			{				
				if(pc.getLockedUserId() != 0 && pc.getLockedUserId() != user.getUserId())
				{
					//response.setHeader("X1", "该备课已被id=" + pc.getLockedUserId() + "的用户签出，你不能进行保存。");
					response.setContentType("text/plain;charset=UTF-8");
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().write("该备课已被id=" + pc.getLockedUserId() + "的用户签出，你不能进行保存。");
					return NONE;
				}
				if(saveLockStatus != null && saveLockStatus.equals("1"))
				{
					//System.out.println("只是保存下签出状态而已");
					//只是保存下签出状态而已
					pc.setLockedUserId(0);
					prepareCourseService.updatePrepareCourse(pc);
					//response.setHeader("X1", "success");
					response.setContentType("text/plain;charset=UTF-8");
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().write("success");
					return NONE;
				}
				else
				{
					docFileName = user.getUserGuid() + "_" + UUID.randomUUID().toString().toUpperCase() + this.fileName.substring(this.fileName.lastIndexOf("."));
					PrepareCourseEdit prepareCourseEdit = new PrepareCourseEdit(); 
		            prepareCourseEdit.setContent(docFileName);
		            prepareCourseEdit.setEditDate(new Date());
		            prepareCourseEdit.setLockStatus(0);
		            prepareCourseEdit.setEditUserId(user.getUserId());
		            prepareCourseEdit.setPrepareCourseId(Integer.parseInt(prepareCourseId));
		            //prepareCourseEdit.setContentType(pc.getContentType());//与共案类型一致
		            prepareCourseService.updatePrepareCourseEdit(prepareCourseEdit);
		            //System.out.println("prepareCourseEdit.getPrepareCourseEditId()=" + prepareCourseEdit.getPrepareCourseEditId());
		            pc.setLockedUserId(0);
		            pc.setCommonContent(docFileName);
		            pc.setPrepareCourseEditId(prepareCourseEdit.getPrepareCourseEditId());
		            prepareCourseService.updatePrepareCourse(pc);
		            //System.out.println("共案更新完毕");
				}
	
			}
			File destFile = new File(safeFilePath, docFileName);

			FileUtils.copyFile(this.doc, destFile);
			
			// 新的转换服务
			String src = safeFilePath + docFileName;
			String tar = src.substring(0, src.lastIndexOf(".")) + ".swf";
			TaskVo taskVo = new TaskVo(pc.getTitle(), src, tar, ObjectEnum.TASK_OBJECT_OFFICE);
			taskVo.setDeleteSrcEnum(DeleteSrcEnum.NOT_DELETE);
			new BiServiceImpl().send(taskVo);
			
			if(returnType == null || returnType.equals(""))
			{
				response.setContentType("text/plain;charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("success");
			}
			else if(returnType.equalsIgnoreCase("page"))
			{
				this.addActionLink("返回个案页面", "../p/" + pc.getPrepareCourseId() + "/0/py/show_preparecourse_user_content.py?userId=" + user.getUserId());
				return SUCCESS;				
			}
			else if(returnType.equalsIgnoreCase("page_common"))
			{
				this.addActionLink("返回共案页面", "../p/" + pc.getPrepareCourseId() + "/0/py/show_preparecourse_common_content.py");
				return SUCCESS;				
			}
			
			return NONE;
		} else if (cmd.equals("verify")) {
			//System.out.println("验证用户开始");
			User user = WebUtil.getLoginUser(getSession());
			//System.out.println("user=" + user);
			if (user == null) {
				//response.setHeader("X1", "notlogined");
				response.setContentType("text/plain;charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("notlogined");
			} else {
				//response.setHeader("X1", "logined");
				response.setContentType("text/plain;charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("logined");
			}
			return NONE;
		} else if (cmd.equals("verifyusernameandpassword")) {
			String usermgrurl = UserMgrModel.getUserMgrUrl();
			if(usermgrurl.length() == 0){
//				usermgrurl = CommonUtil.getLocalHostSiteIP(request) + JitarConst.USERMGR_SITENAME +  "/";
				usermgrurl=request.getServletContext().getFilterRegistration("CASValidationFilter").getInitParameter("casServerUrlPrefix");
				if(usermgrurl==null || usermgrurl.length()==0){
					usermgrurl=CasConst.getInstance().getCasServerUrlPrefix();
				}
			}
			usermgrurl += "VerifyUser";
			//response.setHeader("X1", usermgrurl);
			response.setContentType("text/plain;charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write(usermgrurl);
			return NONE;
		} else {
			//response.setHeader("X1", "error");
			response.setContentType("text/plain;charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("error");
			return NONE;
		}
	}

	public void setDoc(File file) {
		this.doc = file;
	}

	public void setDocFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setDocContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getSavePath() {
		return CommonUtil.GetPrepareCourseFolder(ServletActionContext.getRequest())[0];
	}

	public void setSaveLockStatus(String saveLockStatus) {
		this.saveLockStatus = saveLockStatus;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}
