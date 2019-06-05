package cn.edustar.jitar.servlet.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletException;

import cn.edustar.jitar.model.SiteUrlModel;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.util.CommonUtil;
import cn.edustar.jitar.util.DateUtil;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 简单数据获取 API.
 * 
 *
 */
public class SimpleDataApiBean extends ServletBeanBase {
	/** 方法名 */
	private String methodName = "";
	
	private ParamUtil param_util;
	
	/** 群组服务 */
	private GroupService group_svc;
	
	/** 群组服务 */
	public void setGroupService(GroupService group_svc) {
		this.group_svc = group_svc;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.servlet.handler.ServletBeanBase#handleRequst()
	 */
	@Override
	protected void handleRequst() throws IOException, ServletException {
		// 解析 path info.
		if (parsePathInfo() == false) {
			bad_request();
			return;
		}
		
		this.param_util = new ParamUtil(request.getParameterMap());
		
		// 根据不同方法名执行不同方法.
		if ("getGroupById".equals(methodName))
			getGroupById();
		else if ("getGroupByName".equals(methodName))
			getGroupByName();
	}

	/** 根据所给 id 获得群组信息 */
	private void getGroupById() throws IOException {
		int groupId = param_util.getIntParam("groupId");
		Group group = group_svc.getGroup(groupId);
		
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlWriteGroup(writer, group);
		writer.flush();
	}

	/** 根据所给 groupName 获得群组信息 */
	private void getGroupByName() throws IOException {
		String groupName = param_util.safeGetStringParam("groupName");
		Group group = group_svc.getGroupMayCached(groupName);

		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlWriteGroup(writer, group._getGroupObject());
		writer.flush();
	}
	
	/** 用 XML 写出 group 的信息 */
	private static void xmlWriteGroup(Writer writer, Group group) throws IOException {
		writer.write("<group>");
		// 群组标识.
		writer.write(" <id>" + group.getGroupId() + "</id>");
		// 群组英文名.
		writer.write(" <name>" + CommonUtil.xmlEncode(group.getGroupName()) + "</name>");
		// 群组标题.
		writer.write(" <title>" + CommonUtil.xmlEncode(group.getGroupTitle()) + "</title>");
		// 群组访问地址.
		writer.write(" <href>" + SiteUrlModel.getSiteUrl() + "g/" + group.getGroupName() + "</href>");
		// 全局唯一标识.
		writer.write(" <uuid>" + group.getGroupGuid() + "</uuid>");
		// 创建日期.
		writer.write(" <createDate>" + DateUtil.toStandardString(group.getCreateDate()) + "</createDate>");
		// create user id
		writer.write(" <creator>" + group.getCreateUserId() + "</creator>");
		// 标签.
		writer.write(" <tags>" + CommonUtil.xmlEncode(group.getGroupTags()) + "</tags>");
		writer.write("</group>");
	}
	
	/** 解析 path info */
	private boolean parsePathInfo() {
		String path_info = request.getPathInfo();	// '/sdapi/$methodName'
		// '/sdapi/$methodName'
		String[] parts = path_info.split("/");		// ['', 'sdapi', '$methodName']
		// ['', 'tag', '$tagname', 'module', '$modname']
		if (parts.length < 3) return false;
		if ("sdapi".equals(parts[1]) == false) return false;

		this.methodName = parts[2];
		if (methodName == null || methodName.length() == 0) return false;

		return true;
	}
	
}
