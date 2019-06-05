package cn.edustar.jitar.action;

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
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.service.UnitQueryParam;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.util.PageContent;

/**
 * 机构统计
 * 
 * @author Yang XinXin
 * @version 1.0.0 Sep 18, 2008 10:20:08 AM
 */
public class AdminStatUnitAction extends ManageBaseAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 1288742820715684280L;

	/** 日志 */
	private transient static final Log log = LogFactory.getLog(AdminStatUnitAction.class);
	
	/** 日期类型 */
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/** 机构列表 */
	private List<Unit> unitList = new ArrayList<Unit>();
	
	/** 区县、机构服务 */
	private UnitService unitService;
	
	
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
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
		}else if(cmd.equals("init"))
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
		Integer unitId = param_util.getIntParamZeroAsNull("unitId");
		String from = param_util.getStringParam("from", "").trim();
		if(from.equals(""))
		{
			from = null;
		}
		if(unitId != null)
		{
			setRequestAttribute("unitId", unitId);
		}
		if(from != null)
		{
			setRequestAttribute("from", from);
		}
		putUnitListToRequest();
		return LIST_SUCCESS;
	}
	/**
	 * 获取机构列表
	 * 
	 * @throws Exception
	 */
	private void did() throws Exception {
		Integer unitId = param_util.getIntParamZeroAsNull("unitId");
		String from = param_util.getStringParam("from", "").trim();
		if(from.equals(""))
		{
			from = null;
		}
		if(unitId != null)
		{
			setRequestAttribute("unitId", unitId);
		}
		if(from != null)
		{
			setRequestAttribute("from", from);
		}
		
		//putDistrictListToRequest(); // 区县列表
		Pager pager = super.getCurrentPager();
		pager.setPageSize(20);
		pager.setItemNameAndUnit("机构", "个");
		UnitQueryParam param = new UnitQueryParam();
		param.k = param_util.safeGetStringParam("k", null);
		param.unitId = unitId;
		//param.districtId = param_util.getIntParamZeroAsNull("districtId");
		setRequestAttribute("k", param.k);
		//setRequestAttribute("districtId", param.districtId);
		setRequestAttribute("pager", pager);
		setRequestAttribute("beginDate", param_util.safeGetStringParam("beginDate", ""));
		setRequestAttribute("endDate", param_util.safeGetStringParam("endDate", ""));
		if(unitId == null)
		{
			unitList = unitService.getUnitList(param, pager);
		}
		else
		{
			unitList = unitService.getAllUnitByUnitId(unitId);
		}
		setRequestAttribute("unitList", unitList);
	}
	
	/**
	 * 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	private String list() throws Exception {
		Integer unitIdFromUnitManage = param_util.getIntParamZeroAsNull("unitId");
		String from = param_util.getStringParam("from", "").trim();
		if(from.equals(""))
		{
			from = null;
		}
		if(unitIdFromUnitManage != null)
		{
			setRequestAttribute("unitId", unitIdFromUnitManage);
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
				if (unitIdFromUnitManage != null)
				{
					unitService.updateUnitStat(unitIdFromUnitManage, beginDate, endDate);
				}
				else
				{
					unitList = unitService.getAllUnitOrChildUnitList(null);
					if (unitList.size() > 0) {
						for (int i = 0; i < unitList.size(); i++) {
							int unitId = unitList.get(i).getUnitId();
							unitService.updateUnitStat(unitId, beginDate, endDate);
						}
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
		Integer unitIdFromUnitManage = param_util.getIntParamZeroAsNull("unitId");		
		Pager pager = super.getCurrentPager();
		pager.setPageSize(65535);
		Unit unit = null;
		if(unitIdFromUnitManage != null)
		{
			unit = unitService.getUnitById(unitIdFromUnitManage);
			unitList.add(unit);
		}
		else
		{
			UnitQueryParam param = new UnitQueryParam();
			param.k = param_util.safeGetStringParam("k", null);
			param.unitId = unitIdFromUnitManage;
			
			//param.districtId = param_util.getIntParamZeroAsNull("districtId");
			unitList = unitService.getAllUnitOrChildUnitList(null);
		}
		
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("机构ID").append("\t")			
		.append("区县名称").append("\t")
		.append("机构名称").append("\t")
		.append("创建日期").append("\t")
		.append("工作室数").append("\t")
		.append("文章数").append("\t")
		.append("推荐文章数").append("\t")
		.append("资源数").append("\t")
		.append("推荐资源数").append("\t")
		.append("图片数").append("\t")
		.append("视频数").append("\t")
		.append("当前积分").append("\t").append("\n");
		
		/*
		if(unit != null)
		{
			
			sb.append(unit.getUnitId()).append("\t")
			.append(unit.getUnitTitle()).append("\t")
			.append(unit.getUserCount()).append("\t")
			.append(unit.getArticleCount()).append("\t")
			.append(unit.getRecommendArticleCount()).append("\t")
			.append(unit.getResourceCount()).append("\t")
			.append(unit.getRecommendResourceCount()).append("\t")
			.append(unit.getPhotoCount()).append("\t")
			.append(unit.getVideoCount()).append("\t")
			.append(unit.getTotalScore()).append("\t").append("\n");
		}
		else
		{*/
			for (int i = 0; i < unitList.size(); i++) {
				sb.append(unitList.get(i).getUnitId()).append("\t")
				.append(unitList.get(i).getUnitTitle()).append("\t")
				.append(unitList.get(i).getUserCount()).append("\t")
				.append(unitList.get(i).getArticleCount()).append("\t")
				.append(unitList.get(i).getRecommendArticleCount()).append("\t")
				.append(unitList.get(i).getResourceCount()).append("\t")
				.append(unitList.get(i).getRecommendResourceCount()).append("\t")
				.append(unitList.get(i).getPhotoCount()).append("\t")
				.append(unitList.get(i).getVideoCount()).append("\t")
				.append(unitList.get(i).getTotalScore()).append("\t").append("\n");
			}
		/*}*/
	  String excelString = sb.toString();
	  log.info(excelString);
      //excelStream = new ByteArrayInputStream(excelString.getBytes(), 0, excelString.length());    
      response.setContentType("application/vnd.ms-excel");
      response.setHeader("Content-Type", "application/vnd.ms-excel; charset=GB2312");
      response.addHeader("Content-Disposition", "attachment;filename=unit_stat.xls");
      setRequestAttribute("unit_list", unitList);
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
	public void putUnitListToRequest() {
		super.setRequestAttribute("unit_list",unitService.getAllUnitOrChildUnitList(null));
	}

}
