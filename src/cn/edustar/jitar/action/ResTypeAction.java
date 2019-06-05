package cn.edustar.jitar.action;

import java.util.Iterator;
import java.util.List;

import cn.edustar.jitar.pojos.ResType;
import cn.edustar.jitar.service.ResTypeService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 资源元数据类型
 */
public class ResTypeAction extends AbstractServletAction {
	
	/** srialVersionUID */
	private static final long serialVersionUID = -2891479971339145325L;
	
	/** 获取参数辅助对象 */
	private ParamUtil param_util;
	
	/**
	 * 资源分类服务接口.
	 */
	private ResTypeService resTypeService;
	
	private StringBuilder strTre = new StringBuilder();
	
	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		/** 得到上下文对象*/
		this.param_util = new ParamUtil(getActionContext().getParameters());
		
		/** 得到页面的参数 */
		String cmd = param_util.safeGetStringParam("cmd");
		if(cmd == null || cmd.length() == 0)
			cmd = "tree";
		
		if("tree".equalsIgnoreCase(cmd)) {
			return tree();
		} 
		return super.unknownCommand(cmd);
	}

	private String tree() throws Exception {
		strTre.append("d.add(0,-1,\'元数据\');\n");
		setResTypeTree(0);
		setRequestAttribute("resType_js", strTre.toString());
		return LIST_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private void setResTypeTree(int ParentID)
	{
		List list= resTypeService.getChildResTypes(ParentID);
		ResType resType;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			resType = (ResType) iterator.next();	
			//strTre.append("d.add("+resType.getTcId()+","+ParentID+",'"+resType.getTcTitle()+"','','_self');\n");
			strTre.append("d.add("+resType.getTcId()+","+ParentID+",'"+resType.getTcTitle()+"','javascript:void(0)','_self');\n");
			setResTypeTree(resType.getTcId());
		}
	}
	
	public ResTypeService getResTypeService() {
		return resTypeService;
	}

	public void setResTypeService(ResTypeService resTypeService) {
		this.resTypeService = resTypeService;
	}
}
