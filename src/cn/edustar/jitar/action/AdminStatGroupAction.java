package cn.edustar.jitar.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.CategoryService;
import cn.edustar.jitar.service.GroupQueryParam;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.util.PageContent;

/**
 * 群组统计
 * 
 * @author Yang XinXin
 * @version 1.0.0 Sep 18, 2008 10:11:39 AM
 */
public class AdminStatGroupAction extends ManageBaseAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 9036879619148529322L;

	/** 日志 */
	private transient static final Log log = LogFactory.getLog(AdminStatGroupAction.class);
	
	/** 日期类型 */
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/** 群组列表 */
	private List<Group> groupList = new ArrayList<Group>();
	
	/** 群组服务 */
	private GroupService groupService;

	/** 群组服务的set方法 */
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	/** 分类服务 */
	private CategoryService categoryService;
	
	/** 分类服务的set方法 */
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/** 学科服务 */
	private SubjectService subjectService;

	/** 学科服务的set方法 */
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

	/** Excel 流 */
	private InputStream excelStream;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edustar.jitar.action.ManageBaseAction#execute(java.lang.String)
	 */
	@Override
	public String execute(String cmd) throws Exception {
		
		/** 登录验证 */
		if (isUserLogined() == false) {
			return LOGIN;
		}
				
		if ("".equals(cmd) || null == cmd || "list".equals(cmd)) {
			return list();
		} else if ("stat".equals(cmd)) {
			return stat();
		} else if ("reload".equals(cmd)) {
			return reload();
		}
		else if(cmd.equals("init"))
		{
			return clearOldData();
		}
		return unknownCommand(cmd);
	}
	
	/**
	 * 重新加载
	 * 
	 * @return
	 * @throws Exception
	 */
	private String reload() throws Exception {
		did();
		return LIST_SUCCESS;
	}
	
	String clearOldData()
	{
		setRequestAttribute("init", "");
		putMetaSubjectListToRequest();
		putGradeListToRequest();
		putGroupCategoryToRequest();
		return LIST_SUCCESS;
	}

	/**
	 * 获取群组列表
	 * 
	 * @throws Exception
	 */
	private void did() throws Exception {
		putMetaSubjectListToRequest(); // 元学科列表
		putGradeListToRequest(); // 学段列表
		putGroupCategoryToRequest(); // 群组分类列表
		
		Pager pager = super.getCurrentPager();
		pager.setPageSize(20);
		pager.setItemNameAndUnit("群组", "个");
		GroupQueryParam param = new GroupQueryParam();
		param.k = param_util.safeGetStringParam("k", null);		
		param.subjectId = param_util.getIntParamZeroAsNull("subjectId");
		param.gradeId = param_util.getIntParamZeroAsNull("gradeId");
		param.categoryId = param_util.getIntParamZeroAsNull("categoryId");
		param.audit_state = null; // 默认显示所有的群组
		setRequestAttribute("k", param.k);
		setRequestAttribute("subjectId", param.subjectId);
		setRequestAttribute("gradeId", param.gradeId);
		setRequestAttribute("categoryId", param.categoryId);
		setRequestAttribute("pager", pager);
		setRequestAttribute("beginDate", param_util.safeGetStringParam("beginDate", ""));
		setRequestAttribute("endDate", param_util.safeGetStringParam("endDate", ""));
		groupList = groupService.getGroupList(param, pager);
		setRequestAttribute("groupList", groupList);
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
		String beginDate = param_util.safeGetStringParam("beginDate", "");
		String endDate = param_util.safeGetStringParam("endDate", "");
		
		// 不能输入单引号
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

				groupList = groupService.getGroupList();
				if (groupList.size() > 0) {
					for (int i = 0; i < groupList.size(); i++) {
						int groupId = groupList.get(i).getGroupId();
						groupService.updateGroupStat(groupId, beginDate, endDate);
					}
				}				
			} else {
				out.println("<script>alert('请输入正确的日期格式！(eg: 2008-01-01)');window.history.go(-1);</script>");
				out.flush();
				out.close();
				return NONE;
			}
		}
		did();
		return "List_Success1";		
	}

	/**
	 * 导出
	 * 
	 * @return
	 * @throws Exception
	 */
	private String stat() throws Exception {
		Pager pager = super.getCurrentPager();
		pager.setPageSize(65535);
		GroupQueryParam param = new GroupQueryParam();
		param.k = param_util.safeGetStringParam("k", null);		
		param.subjectId = param_util.getIntParamZeroAsNull("subjectId");
		param.gradeId = param_util.getIntParamZeroAsNull("gradeId");
		param.categoryId = param_util.getIntParamZeroAsNull("categoryId");
		param.audit_state = null; // 默认显示所有的群组
		groupList = groupService.getGroupList(param, pager);
		StringBuffer sb = new StringBuffer();
		sb.append("协作组ID").append("\t")
		.append("协作组名称").append("\t")
		.append("创建者").append("\t")
		.append("创建日期").append("\t")
		.append("协作组分类").append("\t")
		.append("学段学科").append("\t")
		.append("访问量").append("\t")
		.append("成员数").append("\t")
		.append("文章数").append("\t")
		.append("资源数").append("\t")
		.append("主题数").append("\t")
		.append("讨论数").append("\t")
		.append("活动数").append("\t")
		.append("协作组状态").append("\t").append("\n");

		for (int i = 0; i < groupList.size(); i++) {
			sb.append(groupList.get(i).getGroupId()).append("\t")
			.append(groupList.get(i).getGroupTitle()).append("\t")
			.append(groupList.get(i).getCreateUserId()).append("\t")
			.append(groupList.get(i).getCreateDate()).append("\t")
			.append(groupList.get(i).getCategoryId()).append("\t")
			.append(groupList.get(i).GetXKXDNameEx()).append("\t")
			.append(groupList.get(i).getVisitCount()).append("\t")
			.append(groupList.get(i).getUserCount()).append("\t")
			.append(groupList.get(i).getArticleCount()).append("\t")
			.append(groupList.get(i).getResourceCount()).append("\t")
			.append(groupList.get(i).getTopicCount()).append("\t")
			.append(groupList.get(i).getDiscussCount()).append("\t")
			.append(groupList.get(i).getActionCount()).append("\t")
			.append(groupList.get(i).getGroupState()).append("\t").append("\n");
		}
		String excelString = sb.toString();
		log.info(excelString);
      excelStream = new ByteArrayInputStream(excelString.getBytes(), 0, excelString.length());
      response.setContentType("application/vnd.ms-excel");
      response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GB2312");
      response.addHeader("Content-Disposition", "attachment;filename=group_stat.xls");
      setRequestAttribute("group_list", groupList);
		putGroupCategoryToRequest(); // 群组分类列表
      return "List_Excel_Success";
	}
		
	/**
	 * 验证日期格式
	 *
	 * @param str
	 * @return
	 */
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

	// Get and set
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
	/** 将元学科列表放到'request'中 */
	public void putMetaSubjectListToRequest() {
		super.setRequestAttribute("subject_list", subjectService.getMetaSubjectList());
	}

	/** 将学段列表放到'request'中 */
	public void putGradeListToRequest() {
		super.setRequestAttribute("grade_list", subjectService.getGradeList());
	}
	
	/** 群组分类 */
	public void putGroupCategoryToRequest() {
		super.setRequestAttribute("group_catetory_list", categoryService.getCategoryTree("group"));
	}

}
