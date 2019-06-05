package cn.edustar.jitar.action;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;

import cn.edustar.jitar.pojos.Staple;
import cn.edustar.jitar.service.StapleService;
import cn.edustar.jitar.service.impl.StapleServiceImpl;


/**
 * 用户博文分类管理
 */
@Deprecated
public class StapleAction extends BaseAction implements ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(StapleAction.class);
	private static String DUPLICATE_STAPLENAME = "该分类名称已经存在，请换一个名称。";
	private String cmd;
	private String newName;
	private String oldStapleId;
	public String destStaple;
	public String orderList;
	public String operType;

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	
	public void setNewName(String newName) {
		this.newName = newName;
	}
	
	public void setOldStapleId(String oldStapleId)
	{
		this.oldStapleId = oldStapleId;
	}

	public void setDestStaple(String destStaple) {
		this.destStaple = destStaple;
	}

	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}

	private javax.servlet.http.HttpServletRequest request;
	private StapleService stapleService;
	public Staple staple;
	public Integer stapleId;
	public List<Staple> StapleList;

	public void setServletRequest(javax.servlet.http.HttpServletRequest req) {
		this.request = req;
	}
	

	@Override
	public String execute() throws Exception {		
		log.info("cmd = " + cmd);
		if ("list".equalsIgnoreCase(cmd))
			return list();
		else if ("add".equalsIgnoreCase(cmd))
			return add();
		else if ("edit".equalsIgnoreCase(cmd)) {
			return edit();
		} else if ("delete".equalsIgnoreCase(cmd)) {
			return delete();
		} else if ("movecopy".equalsIgnoreCase(cmd)) {
			return movecopy();
		} else if ("order".equalsIgnoreCase(cmd)) {
			return order();
		} else {
			return list();
		}
	}

	/*
	 * 分类列表。不必分页
	 */
	private String list() throws Exception {
		StapleList = this.stapleService.getAll();
		return SUCCESS;
	}

	/*
	 * 移动或者复制分类
	 */
	private String movecopy() throws Exception {
		log.debug("destStaple = " + destStaple);
		log.debug("operType = " + operType);
		if (operType.equalsIgnoreCase("copy")) {

		} else if (operType.equalsIgnoreCase("move")) {
		} else {
			// 啥也不做
		}
		return SUCCESS;
	}

	/*
	 * 分类排序
	 */
	private String order() throws Exception {
		log.debug("orderList = " + orderList);
		String[] oList = orderList.split(",");
		for (int i = 0; i < oList.length; i++) {
			staple = this.stapleService.getStaple(Integer.valueOf(oList[i]));
			staple.setOrderNum(i + 1);
			stapleService.updateStaple(staple);
		}
		org.apache.struts2.ServletActionContext.getResponse().sendRedirect("staple.action?cmd=lists");
		return SUCCESS;
	}

	/*
	 * 删除分类
	 * 
	 */
	private String delete() throws Exception {
		String[] Guids = request.getParameterValues("guid");
		if(Guids == null) return SUCCESS;
		// TODO:删除分类，如何处理分类下的文章？默认设置为0（未分类）
		for (int i = 0; i < Guids.length; i++) {
			staple = this.stapleService.getStaple(Integer.valueOf(Guids[i]));
			this.stapleService.deleteStaple(staple);
		}
		org.apache.struts2.ServletActionContext.getResponse().sendRedirect("staple.action?cmd=lists");
		return SUCCESS;
	}

	/*
	 * 修改分类名称
	 */
	private String edit() throws Exception {
		if (request.getMethod().equalsIgnoreCase("POST")) {		
			if (oldStapleId != null) {
				
				if(this.newName.length() > 50)
				{
					request.setAttribute("msg", "分类名称不能大于 50 个字符。");	
					return "edit_success";
				}
				
				if(this.newName.length() <1)
				{
					request.setAttribute("msg", "分类名称不能为空。");	
					return "edit_success";
				}
				if (this.stapleService.stapleNameIsExist(newName) == false) {
					staple = this.stapleService.getStaple(Integer.valueOf(oldStapleId));
					staple.setStapleName(newName);
					this.stapleService.updateStaple(staple);					
					org.apache.struts2.ServletActionContext.getResponse().sendRedirect("staple.action?cmd=lists");
					return SUCCESS;
				} else {
					staple = this.stapleService.getStaple(Integer.valueOf(oldStapleId));
					request.setAttribute("msg", DUPLICATE_STAPLENAME);		
					return "edit_success";
				}				
			}

		} else {
			staple = this.stapleService.getStaple(this.stapleId);
			return "edit_success";
		}
		org.apache.struts2.ServletActionContext.getResponse().sendRedirect("staple.action?cmd=lists");
		return SUCCESS;
	}

	/*
	 * 添加新分类
	 */
	@SuppressWarnings("deprecation")
	private String add() throws Exception {
		if (request.getMethod().equalsIgnoreCase("POST")) {
			try {
				if (staple != null) {
					if(staple.getStapleName().length() > 50)
					{
						request.setAttribute("msg", "分类名称不能大于 50 个字符。");	
						return "add_success";
					}
					
					if(staple.getStapleName().length() < 1)
					{
						request.setAttribute("msg", "分类名称不能为空。");	
						return "add_success";
					}
					
					if (this.stapleService
							.stapleNameIsExist(staple.getStapleName())) {
						request.setAttribute("msg", "该分类名称已经存在。请更换一个名称。");	
						return "add_success";
					}
				
					Calendar c = Calendar.getInstance();
					staple.setCreateDate(c.getTime());
					staple.setInvisible(false);
					staple.setOrderNum(0);
					staple.setStapleType(0);
					staple.setBlogNum(0);
					staple.setUserId(1);
					staple.setViews(0);
					stapleService.addStaple(staple);
				}
				org.apache.struts2.ServletActionContext.getResponse().sendRedirect("staple.action?cmd=lists");
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return "add_success";
		}

	}

	public StapleService getStapleService() {
		return stapleService;
	}

	public void setStapleService(StapleServiceImpl stapleService) {
		this.stapleService = stapleService;
	}

	public Integer getStapleId() {
		return stapleId;
	}

	public void setStapleId(Integer stapleId) {
		this.stapleId = stapleId;
	}

	public Staple getStaple() {
		return staple;
	}

	public void setStaple(Staple staple) {
		this.staple = staple;
	}

	public void setStapleService(StapleService stapleService) {
		this.stapleService = stapleService;
	}

}
