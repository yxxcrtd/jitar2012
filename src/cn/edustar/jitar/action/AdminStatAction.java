package cn.edustar.jitar.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edustar.data.Pager;
import cn.edustar.jitar.manage.UserStatManage;
import cn.edustar.jitar.pojos.MetaSubject;
import cn.edustar.jitar.pojos.UserStat;
import cn.edustar.jitar.pojos.UserSubjectGrade;
import cn.edustar.jitar.pojos.UserType;
import cn.edustar.jitar.query.UserStatQueryParam;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.UPunishScoreService;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UserService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.PageContent;

/**
 * 个人统计，管理员后台、单位管理后台使用
 * 
 */
public class AdminStatAction extends ManageBaseAction {
	private Logger log = LoggerFactory.getLogger(AdminStatAction.class);
	private static final long serialVersionUID = 6224153951544090386L;
	private UnitService unitService;
	private UserService userService;	
	private InputStream excelStream;
	private UserStatManage userStatManage;
	private SubjectService subjectService;
	/** 罚分服务 */
	protected UPunishScoreService pun_svc;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private List<UserStat> userList = new ArrayList<UserStat>();

	public String execute(String cmd) throws Exception {
		if (isUserLogined() == false) {
			return LOGIN;
		}
		if ("".equals(cmd) || null == cmd || "list".equals(cmd)) {
			return list();
		} else if ("stat".equals(cmd)) {
			return stat();
		} else if ("reload".equals(cmd)) {
			return reload();
		} else if (cmd.equals("init")) {
			return init_query();
		}
		return unknownCommand(cmd);
	}

	private String reload() throws Exception {
		showResult();
		return LIST_SUCCESS;
	}

	/**
	 * 初始化查询页面，只是第一次进入到本页面的时候执行一次
	 * 功能：设置本次查询的标识，初始化参数
	 * @return
	 */
	private String init_query() {
		String statGuid = this.param_util.safeGetStringParam("statGuid", UUID.randomUUID().toString());
		setRequestAttribute("init", "");
		setRequestAttribute("statGuid", statGuid);
		setRequestAttribute("teachertype", 0);
		Integer unitId = param_util.getIntParamZeroAsNull("unitId");
		String from = param_util.getStringParam("from", null);
		if (unitId != null) {
			setRequestAttribute("unitId", unitId);
		}
		if (from != null) {
			setRequestAttribute("from", from);
		}
		putMetaSubjectListToRequest();
		putGradeListToRequest();
		putUnitListToRequest();
		List<UserType> UserType_List = this.userService.getAllUserType();
		if(UserType_List != null && UserType_List.size() > 0){
			setRequestAttribute("UserType_List", UserType_List);
		}
		return LIST_SUCCESS;
	}

	/**
	 * 分页显示查询出来的结果
	 * @throws Exception
	 */
	private void showResult() throws Exception {
		Integer teacherType = param_util.safeGetIntParam("teachertype");
		String statGuid = param_util.safeGetStringParam("statGuid", null);
		Integer unitId = param_util.getIntParamZeroAsNull("unitId");
		String from = param_util.getStringParam("from", null);
		if (unitId != null) {
			setRequestAttribute("unitId", unitId);
		}
		if (from != null) {
			setRequestAttribute("from", from);
		}
		putMetaSubjectListToRequest();
		putGradeListToRequest();
		putUnitListToRequest();
		Pager pager = super.getCurrentPager();
		pager.setPageSize(30);
		pager.setItemNameAndUnit("用户", "个");
		UserStatQueryParam param = new UserStatQueryParam();
		
		/*param.k = param_util.safeGetStringParam("k", null);		
		param.teacherType = teacherType;
		param.subjectId = param_util.getIntParamZeroAsNull("subjectId");
		param.gradeId = param_util.getIntParamZeroAsNull("gradeId");
		param.unitId = param_util.getIntParamZeroAsNull("unitId");*/
		param.statGuid = statGuid;
		setRequestAttribute("k", param_util.safeGetStringParam("k", ""));
		setRequestAttribute("teachertype", teacherType);
		setRequestAttribute("statGuid", statGuid);
		setRequestAttribute("subjectId", param_util.safeGetIntParam("subjectId"));
		setRequestAttribute("gradeId", param_util.safeGetIntParam("gradeId"));
		setRequestAttribute("unitId", param_util.safeGetIntParam("unitId"));
		setRequestAttribute("pager", pager);
		setRequestAttribute("beginDate",param_util.safeGetStringParam("beginDate", ""));
		setRequestAttribute("endDate",param_util.safeGetStringParam("endDate", ""));
		userList = userStatManage.getUserStatList(param, pager);
		List<UserType> UserType_List = this.userService.getAllUserType();
		if(UserType_List != null && UserType_List.size() > 0){
			setRequestAttribute("UserType_List", UserType_List);
		}
		setRequestAttribute("userList", userList);		
	}

	/**
	 * 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	private String list() throws Exception {
		PrintWriter out = response.getWriter();
		out.append(PageContent.PAGE_UTF8);
		String statGuid = param_util.safeGetStringParam("statGuid", "");
		String beginDate = param_util.safeGetStringParam("beginDate", "");
		String endDate = param_util.safeGetStringParam("endDate", "");
		if ("'".equals(beginDate) || "'".equals(endDate)) {
			out.println("<script>alert('请输入正确的日期格式！');window.history.go(-1);</script>");
			out.flush();
			out.close();
			return NONE;
		}
		if (beginDate.length() > 0 && endDate.length() > 0) {
			if (checkDate(beginDate) && checkDate(endDate)) {
				if (sdf.parse(beginDate).compareTo(sdf.parse(endDate)) == 1
						|| sdf.parse(beginDate).compareTo(sdf.parse(endDate)) == 0) {
					out.println("<script>alert('开始日期不能大于等于结束日期！');window.history.go(-1);</script>");
					out.flush();
					out.close();
					return NONE;
				}
				setRequestAttribute("beginDate", beginDate);
				setRequestAttribute("endDate", endDate);
				String kString = param_util.safeGetStringParam("k");
				int subjectId = param_util.safeGetIntParam("subjectId");
				int gradeId = param_util.safeGetIntParam("gradeId");
				int unitId = param_util.safeGetIntParam("unitId");
				int teacherType = param_util.safeGetIntParam("teachertype");
				userStatManage.updateUserStat(kString, beginDate, endDate,subjectId, gradeId, unitId, teacherType, statGuid);
				response.sendRedirect("admin_stat.action?cmd=reload&statGuid=" + statGuid + "&k=" + CommonUtil.urlUtf8Encode(kString) + "&subjectId=" + subjectId + "&gradeId=" + gradeId + "&unitId=" + unitId + "&beginDate=" + CommonUtil.urlUtf8Encode(beginDate) + "&endDate=" + CommonUtil.urlUtf8Encode(endDate) + "&teachertype=" + teacherType);
				return NONE;
			} else {
				out.println("<script>alert('请输入正确的日期格式！(eg: 2008-01-01)');window.history.go(-1);</script>");
				out.flush();
				out.close();
				return NONE;
			}
		}
		//did();
		//System.out.print("应该干啥?????");
		return init_query();
	}

	private String stat() throws Exception {
		Pager pager = super.getCurrentPager();
		pager.setPageSize(65535);
		UserStatQueryParam param = new UserStatQueryParam();
		param.k = param_util.safeGetStringParam("k", null);
		param.subjectId = param_util.getIntParamZeroAsNull("subjectId");
		param.gradeId = param_util.getIntParamZeroAsNull("gradeId");
		param.unitId = param_util.getIntParamZeroAsNull("unitId");
		param.statGuid = param_util.getStringParam("statGuid",null);
		Integer teachertype = param_util.safeGetIntParam("teachertype");
		param.teacherType = teachertype;
		setRequestAttribute("teachertype", teachertype);
		userList = userStatManage.getUserStatList(param, pager);
		StringBuffer sb = new StringBuffer();
		sb.append("用户ID").append("\t").append("登录名").append("\t")
				.append("真实姓名").append("\t").append("工作室名称").append("\t")
				.append("学科").append("\t").append("学段").append("\t")
				.append("机构").append("\t")
				.append("教师类型").append("\t").append("工作室访问量").append("\t")
				.append("我的文章被访问量").append("\t").append("我的资源被访问量")
				.append("\t").append("原创文章数").append("\t").append("转载文章数")
				.append("\t").append("推荐文章数").append("\t").append("文章被评论数")
				.append("\t").append("评论文章数").append("\t").append("资源数")
				.append("\t").append("推荐资源数").append("\t").append("资源被评论数")
				.append("\t").append("评论资源数").append("\t").append("资源下载数")
				.append("\t").append("创建协作组数").append("\t").append("加入协作组数")
				.append("\t").append("照片数").append("\t").append("视频数")
				.append("\t").append("文章得分").append("\t").append("资源得分")
				.append("\t").append("照片得分").append("\t").append("视频得分")
				.append("\t").append("评论得分").append("\t").append("文章奖罚分")
				.append("\t").append("资源奖罚分").append("\t").append("评论奖罚分")
				.append("\t").append("图片奖罚分").append("\t").append("视频奖罚分")
				.append("\t").append("总积分").append("\t").append("用户状态")
				.append("\t").append("\n");
		
		List<UserType> ls = this.userService.getAllUserType();
		for (int i = 0; i < userList.size(); i++) {
			String sTeacherType = "";
			String userType = userList.get(i).getUserType();
			if(ls != null && ls.size() > 0)
			{
				if(userType != null && userType.length() > 0 && userType.contains("/"))
				{
					for(UserType u : ls)
					{
						if(userType.contains("/" + u.getTypeId() + "/"))
						{
							userType = userType.replaceAll("/" + u.getTypeId() + "/", "/" + u.getTypeName() + "/");
						}
					}
					if(userType.startsWith("/")) userType = userType.substring(1);
					if(userType.endsWith("/")) userType = userType.substring(0,userType.length() - 1);
					userType = userType.replaceAll("/", ",");					
				}
			}
			sTeacherType = userType;					
			List<UserSubjectGrade> usg_list = this.userService.getAllUserSubjectGradeListByUserId(userList.get(i).getUserId());
			String s_g = "";
			String s_s = "";
			if(usg_list != null && usg_list.size() > 0){
				for(UserSubjectGrade usg : usg_list)
				{
					if (usg.getGradeId() == null){
						s_g += "未设置<br/>";						
					} 
					else
					{
						s_g +=  this.subjectService.getGrade(usg.getGradeId()).getGradeName() + "<br/>";		
					}
					
					if (usg.getSubjectId() == null){
						s_s += "未设置<br/>";						
					} 
					else
					{
					    MetaSubject metaSubj = this.subjectService.getMetaSubjectById(usg.getSubjectId());
					    if(null == metaSubj){
					        s_s += "未设置<br/>"; 
					    }
					    else{
					        s_s +=  this.subjectService.getMetaSubjectById(usg.getSubjectId()).getMsubjName() + "<br/>";       
					    }						
					}					
			  }
			}
			if(s_g.endsWith(",")) s_g = s_g.substring(0,s_g.length() - 1);
			sb.append(userList.get(i).getUserId()).append("\t")
					.append(userList.get(i).getLoginName()).append("\t")
					.append(userList.get(i).getTrueName()).append("\t")
					.append(userList.get(i).getBlogName()).append("\t")
					.append(s_g).append("\t")
					.append(s_s).append("\t")
					.append(userList.get(i).getUnitId()).append("\t")
					.append(sTeacherType).append("\t")
					.append(userList.get(i).getVisitCount()).append("\t")
					.append(userList.get(i).getVisitArticleCount())
					.append("\t")
					.append(userList.get(i).getVisitResourceCount())
					.append("\t").append(userList.get(i).getMyArticleCount())
					.append("\t")
					.append(userList.get(i).getOtherArticleCount())
					.append("\t")
					.append(userList.get(i).getRecommendArticleCount())
					.append("\t")
					.append(userList.get(i).getArticleCommentCount())
					.append("\t")
					.append(userList.get(i).getArticleICommentCount())
					.append("\t").append(userList.get(i).getResourceCount())
					.append("\t")
					.append(userList.get(i).getRecommendResourceCount())
					.append("\t")
					.append(userList.get(i).getResourceCommentCount())
					.append("\t")
					.append(userList.get(i).getResourceICommentCount())
					.append("\t")
					.append(userList.get(i).getResourceDownloadCount())
					.append("\t").append(userList.get(i).getCreateGroupCount())
					.append("\t").append(userList.get(i).getJionGroupCount())
					.append("\t").append(userList.get(i).getPhotoCount())
					.append("\t").append(userList.get(i).getVideoCount())
					.append("\t").append(userList.get(i).getArticleScore())
					.append("\t").append(userList.get(i).getResourceScore())
					.append("\t").append(userList.get(i).getPhotoScore())
					.append("\t").append(userList.get(i).getVideoScore())
					.append("\t").append(userList.get(i).getCommentScore())
					.append("\t")
					.append(userList.get(i).getArticlePunishScore()*-1)
					.append("\t")
					.append(userList.get(i).getResourcePunishScore()*-1)
					.append("\t")
					.append(userList.get(i).getCommentPunishScore()*-1)
					.append("\t").append(userList.get(i).getPhotoPunishScore()*-1)
					.append("\t").append(userList.get(i).getVideoPunishScore()*-1)
					.append("\t").append(userList.get(i).getUserScore())
					.append("\t").append(userList.get(i).getUserStatus())
					.append("\t").append("\n");
		}
		String excelString = sb.toString();
		log.info(excelString);
		excelStream = new ByteArrayInputStream(excelString.getBytes(), 0,excelString.length());
		response.setCharacterEncoding("GB2312");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Type","application/vnd.ms-excel; charset=GB2312");
		response.addHeader("Content-Disposition","attachment;filename=stat.xls");
		setRequestAttribute("user_list", userList);
		return "List_Excel_Success";
	}

	private boolean checkDate(String str) {
		//System.out.println("str="+str);
		boolean bol = true;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false);
			bol = df.format(df.parse(str)).equals(str);
			log.info("bol="+bol);
		} catch (ParseException e) {
			System.out.println("e="+e.getMessage());
			bol = false;
		}
		log.info("return bol="+bol);
		return bol;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}


	public void putUnitListToRequest() {
		super.setRequestAttribute("unit_list",
				unitService.getAllUnitOrChildUnitList(null));
	}

	public void putMetaSubjectListToRequest() {
		super.setRequestAttribute("subject_list",subjectService.getMetaSubjectList());
	}

	public void putGradeListToRequest() {
		super.setRequestAttribute("grade_list", subjectService.getGradeList());
	}

	public void setUserStatManage(UserStatManage userStatManage) {
		this.userStatManage = userStatManage;
	}

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/** 罚分服务 */
	public void setUPunishScoreService(UPunishScoreService pun_svc) {
		this.pun_svc = pun_svc;
	}
}
