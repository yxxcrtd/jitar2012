package cn.edustar.jitar.action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.UserStat;
import cn.edustar.jitar.util.PageContent;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.manage.UserStatManage;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.query.UserStatQueryParam;

/**
 * 个人统计
 * 
 * @author Yang Xinxin
 * @version 1.0.0 Sep 18, 2008 10:07:32 AM
 */
@SuppressWarnings("serial")
public class AdminStatResultAction extends ManageBaseAction {
	private UnitService unitService;
	private InputStream excelStream;
	private UserStatManage userStatManage;
	private SubjectService subjectService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private List<UserStat> userList = new ArrayList<UserStat>();
	
	public String execute(String cmd) throws Exception {
		if (isUserLogined() == false) {
			return LOGIN;
		}
		if ("".equals(cmd) || null == cmd || "list".equals(cmd)) {
			return list();
		}
		return unknownCommand(cmd);
	}

	private String list() throws Exception {
		PrintWriter out = response.getWriter();
		out.append(PageContent.PAGE_UTF8);
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
				if (sdf.parse(beginDate).compareTo(sdf.parse(endDate)) == 1 || sdf.parse(beginDate).compareTo(sdf.parse(endDate)) == 0) {
					out.println("<script>alert('开始日期不能大于等于结束日期！');window.history.go(-1);</script>");
					out.flush();
					out.close();
					return NONE;
				}
				setRequestAttribute("beginDate", beginDate);
				setRequestAttribute("endDate", endDate);
				String kString = param_util.safeGetStringParam("k");
				
				int subjectId=param_util.safeGetIntParam("subjectId");
				int gradeId=param_util.safeGetIntParam("gradeId");
				int unitId =param_util.safeGetIntParam("unitId");
				int teacherType=param_util.safeGetIntParam("teachertype");
				
				//执行新的存储过程
				userStatManage.updateUserStat(kString, beginDate, endDate, subjectId,  gradeId,  unitId ,  teacherType);
				
				/*
				List<UserStat> user_list = userStatManage.getUserListByLoginName(kString);
				UserStat user_id = null;
				if (!"".equals(kString) && isNumeric(kString)) {
					user_id = userStatManage.getUserStatById(Integer.valueOf(kString));
				}
				userStatManage.reNewUserStat();
				if (user_list.size() == 0 && user_id == null) {
					log.info("没有匹配的用户！");
					userList = userStatManage.getUserStatList();
					if (userList.size() > 0) {
						for (int i = 0; i < userList.size(); i++) {
							int userId = userList.get(i).getUserId();
							userStatManage.updateUserStat(userId, beginDate, endDate);
						}
					}
				} else {
					if (user_list.size() > 0) {
						for (int i = 0; i < user_list.size(); i++) {
							int userId = user_list.get(i).getUserId();
							userStatManage.updateUserStat(userId, beginDate, endDate);
						}
					}
					if (user_id != null) {
						log.info("如果是登录ID，则登录ID：" + user_id.getUserId());
						userStatManage.updateUserStat(user_id.getUserId(), beginDate, endDate);
					}
				}
				*/
			} else {
				out.println("<script>alert('请输入正确的日期格式！(eg: 2008-01-01)');window.history.go(-1);</script>");
				out.flush();
				out.close();
				return NONE;
			}
			return "stat_html";
		}
		putMetaSubjectListToRequest();
		putGradeListToRequest();
		putUnitListToRequest();
		Pager pager = super.getCurrentPager();
		pager.setPageSize(50);
		pager.setItemNameAndUnit("用户", "个");
		UserStatQueryParam param = new UserStatQueryParam();
		param.k = param_util.safeGetStringParam("k", null);
		Integer teachertype = param_util.safeGetIntParam("teachertype");
		param.teacherType = teachertype;
		setRequestAttribute("teachertype", teachertype);
		param.subjectId = param_util.getIntParamZeroAsNull("subjectId");
		param.gradeId = param_util.getIntParamZeroAsNull("gradeId");
		param.unitId = param_util.getIntParamZeroAsNull("unitId");
		setRequestAttribute("k", param.k);
		setRequestAttribute("subjectId", param.subjectId);
		setRequestAttribute("gradeId", param.gradeId);
		setRequestAttribute("unitId", param.unitId);
		setRequestAttribute("pager", pager);
		setRequestAttribute("beginDate", param_util.safeGetStringParam("beginDate", ""));
		setRequestAttribute("endDate", param_util.safeGetStringParam("endDate", ""));
		userList = userStatManage.getUserStatList(param, pager);
		setRequestAttribute("userList", userList);
		return SUCCESS;
	}

	private boolean checkDate(String str) {
		boolean bol = true;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false);
			bol = df.format(df.parse(str)).equals(str);
		} catch (ParseException e) {
			bol = false;
		}
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
		super.setRequestAttribute("unit_list", unitService.getAllUnitOrChildUnitList(null));
	}
	
	public void putMetaSubjectListToRequest() {
		super.setRequestAttribute("subject_list", subjectService.getMetaSubjectList());
	}

	public void putGradeListToRequest() {
		super.setRequestAttribute("grade_list", subjectService.getGradeList());
	}
	
	public void setUserStatManage(UserStatManage userStatManage) {
		this.userStatManage = userStatManage;
	}
	
	
	
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

}
