package cn.edustar.jitar.action;

import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Subject;
import cn.edustar.jitar.pojos.SubjectStat;
import cn.edustar.jitar.service.SubjectService;
import cn.edustar.jitar.service.impl.SubjectStatServiceImpl;
import cn.edustar.jitar.util.PageContent;

public class AdminStatSubjectAction extends ManageBaseAction  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5250619672527432213L;

	/** 日志 */
	private transient static final Log log = LogFactory.getLog(AdminStatSubjectAction.class);
	
	/** 日期类型 */
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/** 学科列表 */
	private List<SubjectStat> subjectList = new ArrayList<SubjectStat>();
	
	/** 学科服务 */
	private SubjectService subjectService;
	
	/**学科统计服务 */
	private SubjectStatServiceImpl subjectStatService;
	
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
		}else if(cmd.equals("init")){
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
		String from = param_util.getStringParam("from", "").trim();
		if(from.equals(""))
		{
			from = null;
		}
		if(from != null)
		{
			setRequestAttribute("from", from);
		}
		putSubjectListToRequest();
		return LIST_SUCCESS;
	}
	/**
	 * 统计操作获取列表
	 * 
	 * @throws Exception
	 */
	private void did() throws Exception {
		String from = param_util.getStringParam("from", "").trim();
		if(from.equals(""))
		{
			from = null;
		}
		if(from != null)
		{
			setRequestAttribute("from", from);
		}
		
		
		String k = param_util.safeGetStringParam("k", "");
		
		setRequestAttribute("k", k);
		String sbeginDate = param_util.safeGetStringParam("beginDate", "");
		String sendDate = param_util.safeGetStringParam("endDate", "");
		setRequestAttribute("beginDate",sbeginDate);
		setRequestAttribute("endDate",sendDate);
		
		subjectStatService.setSearchKey(k);
		Date beginDate;
		Date endDate;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
		if(sbeginDate.length() > 0){
			beginDate = new Date();
			beginDate = sdf.parse(sbeginDate);
			subjectStatService.setBeginDate(beginDate);
		}else{
			subjectStatService.setBeginDate(null);
		}
		if(sendDate.length() > 0){
			endDate = new Date();
			endDate = sdf.parse(sendDate);
			subjectStatService.setEndDate(endDate);
		}else{
			if(sbeginDate.length() > 0){
				Calendar calendar = Calendar.getInstance();
				endDate = calendar.getTime();
				subjectStatService.setEndDate(endDate);
			}else{
				subjectStatService.setEndDate(null);
			}
		}
		
		subjectList = subjectStatService.StatSubjects();
		
		String key="excelsubject_"+k.replace(" ","_") + sbeginDate.replace(" ", "_").replace(":", "_") + sendDate.replace(" ", "_").replace(":", "_");
				
		SaveInSession(subjectList,key);
		
		setRequestAttribute("subjectList", subjectList);
	}
	
	/**
	 * 将统计结果保存在Session,为了导出
	 * @param list
	 */
	private void SaveInSession(List<SubjectStat> list,String sessionKey){
		/*
		StringBuffer sb = new StringBuffer();		
		sb.append("学科ID").append("\t")
		.append("学科名称").append("\t")
		.append("工作室数").append("\t")
		.append("原创文章数").append("\t")
		.append("原创文章积分").append("\t")
		.append("转载文章数").append("\t")
		.append("转载文章积分").append("\t")
		.append("推荐文章数").append("\t")
		.append("推荐文章积分").append("\t")
		.append("资源数").append("\t")
		.append("资源积分").append("\t")
		.append("推荐资源数").append("\t")
		.append("推荐资源积分").append("\t")
		.append("评论数").append("\t")
		.append("评论积分").append("\t")
		.append("协作组数").append("\t")
		.append("集备数").append("\t")
		.append("活动数").append("\t").append("\n");
    
		for (int i = 0; i < list.size(); i++) {
			SubjectStat sub = list.get(i);
			sb.append(sub.getSubjectId()).append("\t")
			.append(sub.getSubjectName()).append("\t")
			.append(sub.getUserCount()).append("\t")
			.append(sub.getOriginalArticleCount()).append("\t")
			.append(sub.getOriginalArticleScore()).append("\t")
			.append(sub.getReferencedArticleCount()).append("\t")
			.append(sub.getReferencedArticleScore()).append("\t")
			.append(sub.getRecommendArticleCount()).append("\t")
			.append(sub.getRecommendArticleScore()).append("\t")
			.append(sub.getResourceCount()).append("\t")
			.append(sub.getResourceScore()).append("\t") 
			.append(sub.getRecommendResourceCount()).append("\t")
			.append(sub.getRecommendResourceScore()).append("\t")
			.append(sub.getCommentCount()).append("\t")
			.append(sub.getCommentScore()).append("\t")
			.append(sub.getGroupCount()).append("\t")
			.append(sub.getPrepareCourseCountCount()).append("\t")
			.append(sub.getActionCount()).append("\t").append("\n");
		}
		String excelString = sb.toString();	
		*/
		HttpSession session = request.getSession(true);
		session.setAttribute(sessionKey,list);
		
	}
	
	/**
	 * 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	private String list() throws Exception {
		String from = param_util.getStringParam("from", "").trim();
		if(from.equals(""))
		{
			from = null;
		}
		if(from != null)
		{
			setRequestAttribute("from", from);
		}
		
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
		if (beginDate.length() > 0 ){
			if (!checkDate(beginDate)){
				out.println("<script>alert('请输入正确的日期格式！(eg: 2008-01-01)');window.history.go(-1);</script>");
				out.flush();
				out.close();
				return NONE;
			}
		}
		if (endDate.length() > 0 ){
			if (!checkDate(endDate)){
				out.println("<script>alert('请输入正确的日期格式！(eg: 2008-01-01)');window.history.go(-1);</script>");
				out.flush();
				out.close();
				return NONE;
			}
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
			}
		}
		did();
		//return "List_Success1";
		return LIST_SUCCESS;
	}
	
	/**
	 * 导出
	 * 
	 * @return
	 * @throws Exception
	 */
	private String stat() throws Exception {
		String from = param_util.getStringParam("from", "").trim();
		if(from.equals(""))
		{
			from = null;
		}
		if(from != null)
		{
			setRequestAttribute("from", from);
		}
		String k = param_util.safeGetStringParam("k", "");
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
		if (beginDate.length() > 0 ){
			if (!checkDate(beginDate)){
				out.println("<script>alert('请输入正确的日期格式！(eg: 2008-01-01)');window.history.go(-1);</script>");
				out.flush();
				out.close();
				return NONE;
			}
		}
		if (endDate.length() > 0 ){
			if (!checkDate(endDate)){
				out.println("<script>alert('请输入正确的日期格式！(eg: 2008-01-01)');window.history.go(-1);</script>");
				out.flush();
				out.close();
				return NONE;
			}
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
			}
		}
	  
	  String key="excelsubject_"+k.replace(" ","_") + beginDate.replace(" ", "_").replace(":", "_") + endDate.replace(" ", "_").replace(":", "_");
	  HttpSession session = request.getSession();
	  boolean existExcelData = false;
	  if (session!=null){
		  if (session.getAttribute(key)!=null){
			  existExcelData = true;
		  }
	  }
	  if(!existExcelData){
		  did();
	  }else{
		  List<SubjectStat> list = (List<SubjectStat>)session.getAttribute(key);
		  setRequestAttribute("subjectList", list);
	  }
	  response.setContentType("application/vnd.ms-excel");
      response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GB2312");
      response.addHeader("Content-Disposition", "attachment;filename=subject_stat.xls");
      
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
	
	
	
	/** 将机构列表放到'request'中 */
	public void putSubjectListToRequest() {
		
	}

	public SubjectStatServiceImpl getSubjectStatService() {
		return subjectStatService;
	}

	public void setSubjectStatService(SubjectStatServiceImpl subjectStatService) {
		this.subjectStatService = subjectStatService;
	}
}
