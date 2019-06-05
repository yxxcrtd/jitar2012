package cn.edustar.xmlrpc.servlet;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;

public class XmlRpcServer extends HttpServlet {
	private static final long serialVersionUID = 4351067199682220824L;
	private XmlRpcServletServer server;
 
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		try {
			server = new XmlRpcServletServer();
			PropertyHandlerMapping phm = new PropertyHandlerMapping();
			phm.addHandler("UserInfo", XmlRpcUserServiceImpl.class);
			server.setHandlerMapping(phm);
			XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) server.getConfig();
			serverConfig.setEnabledForExtensions(true);
			serverConfig.setContentLengthOptional(false);
		} catch (XmlRpcException e) {
			throw new ServletException(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		server.execute(request, response);
	}
}
