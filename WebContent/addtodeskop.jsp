<%@page language="java" pageEncoding="UTF-8"%><%@page import="cn.edustar.jitar.service.impl.JitarContextImpl"%><%@page import="cn.edustar.jitar.service.ProductConfigService"%><%@page import="cn.edustar.jitar.service.ConfigService"%><%@page import="cn.edustar.jitar.pojos.Config"%><%@page import="cn.edustar.jitar.util.CommonUtil"%><%
	ProductConfigService pcs = (ProductConfigService) JitarContextImpl.getCurrentJitarContext().getSpringContext().getBean("ProductConfigService");
	String title = "", productName = "";
	if (pcs != null && productName != null) {
		pcs.isValid();
		title = pcs.getProductName();
	}
	if(title == null || title.trim().length() == 0)
	{		
		ConfigService configService = (ConfigService) JitarContextImpl.getCurrentJitarContext().getSpringContext().getBean("configService");
		if(configService != null)
		{
			Config configTitle = configService.getConfigByItemTypeAndName("jitar", "site.title");
			if(configTitle != null)
			{
				title = configTitle.getValue();
			}
		}
	}
	if(title == null || title.trim().length() == 0)
	{
		title = "中教启星网络教研平台";
	}
	String siteUrl = CommonUtil.getSiteUrl(request);
	String content = "[{000214A0-0000-0000-C000-000000000046}]\r\n";
	content += "Prop3=19,2\r\n";
	content += "[InternetShortcut]\r\n";
	content += "URL=" + siteUrl + "\r\n";
	content += "IconFile=" + siteUrl + "favicon.ico\r\n";
	content += "IconIndex=1";
	response.setContentType("application/octet-stream");
	response.addHeader("Content-Disposition","attachment; " + CommonUtil.encodeContentDisposition(request, title + ".url"));
	response.setContentLength(content.getBytes().length);
	response.getWriter().write(content);
%>
