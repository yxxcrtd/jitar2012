package cn.edustar.jitar.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.model.SiteUrlModel;

/**
 * @author 孟宪会
 * 
 */
public class UnitServlet extends HttpServlet {

    private static final long serialVersionUID = 4945581764249382425L;

    private static final Log log = LogFactory.getLog(UnitServlet.class);

    public void init() {
    }
    public void destroy() {
        log.info("UnitServlet 销毁了。");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // log.info("执行机构页面……");
        String context_path = request.getRequestURI();
        String virtualPath = request.getContextPath();
        if (!virtualPath.endsWith("/")) {
            // 兼容虚拟目录
            context_path = context_path.replaceFirst(virtualPath, "");
        }

        // log.info("context_path = " + context_path);
        if (!context_path.startsWith("/d/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String trimedUrl = context_path.substring(3); // 去掉 /d/三个字符
        // log.info("context_path = " + context_path + ", trimedUrl = " +
        // trimedUrl);

        if (trimedUrl.endsWith("/"))
            trimedUrl = trimedUrl.substring(0, trimedUrl.length() - 1);

        String unitName = trimedUrl;
        if (unitName.indexOf("/") > -1) {
            unitName = unitName.substring(0, unitName.indexOf("/"));
        }
        String configUnitSiteRoot = request.getSession().getServletContext().getInitParameter("unitUrlPattern");
        String unitRootUrl;
        if (configUnitSiteRoot == null || configUnitSiteRoot.equals("")) {
            unitRootUrl = UnitServlet.getCurrentSiteUrl(request) + "d/" + unitName + "/";
        } else {
            unitRootUrl = configUnitSiteRoot.replaceAll("\\{unitName\\}", unitName);
        }
        if (request.getAttribute("UnitRootUrl") == null) {
            request.setAttribute("UnitRootUrl", unitRootUrl);
        }

        // log.info("执行机构页面： unitName = " + unitName);
        // log.info("执行机构页面： trimedUrl = " + trimedUrl);
        if (request.getAttribute("unitName") == null) {
            request.setAttribute("unitName", unitName);
        }
        if (trimedUrl.indexOf("/") == -1) {
            // log.info("访问机构首页");
            this.getServletContext().getRequestDispatcher("/WEB-INF/program/unit/index.py").forward(request, response);
            return;
        } else {
            String[] urlSlice = trimedUrl.split("/");
            String slice1 = urlSlice[1];
            if (slice1.equals("article")) {
                this.getServletContext().getRequestDispatcher("/WEB-INF/program/unit/unit_article.py").forward(request, response);
                return;
            } else if ("resource".equals(slice1)) {
                this.getServletContext().getRequestDispatcher("/WEB-INF/program/unit/unit_resource.py").forward(request, response);
                return;
            } else if ("photo".equals(slice1)) {
                this.getServletContext().getRequestDispatcher("/WEB-INF/program/unit/unit_photo.py").forward(request, response);
                return;
            } else if ("video".equals(slice1)) {
                this.getServletContext().getRequestDispatcher("/WEB-INF/program/unit/unit_video.py").forward(request, response);
                return;
            } else if ("blog".equals(slice1)) {
                this.getServletContext().getRequestDispatcher("/WEB-INF/program/unit/unit_user.py").forward(request, response);
                return;
            } else if ("py".equals(slice1)) {
                String fileName = trimedUrl.substring(trimedUrl.lastIndexOf("/") + 1);
                // py转action的临时处理
                if (fileName.equals("home.py")) {
                    response.sendRedirect(SiteUrlModel.getSiteUrl());
                    return;
                }
                this.getServletContext().getRequestDispatcher("/WEB-INF/program/unit/" + fileName).forward(request, response);
                return;
            } else if ("manage".equals(slice1)) {
                String fileName = "/units/unitsmanage/" + trimedUrl;
                this.getServletContext().getRequestDispatcher(fileName).forward(request, response);
                return;
            } else if (slice1.equals("html")) {
                String fileName = trimedUrl.substring(trimedUrl.lastIndexOf("/") + 1);
                this.getServletContext().getRequestDispatcher("/html/unit/" + unitName + "/" + fileName).forward(request, response);
                return;
            } else if (slice1.equals("index.html")) {
                this.getServletContext().getRequestDispatcher("/html/unit/" + unitName + "/index.html").forward(request, response);
                return;
            } else {
                String _requestUrl = request.getRequestURI().replaceFirst("/d/" + unitName + "/", "/");
                if (request.getQueryString() != null) {
                    _requestUrl += "?" + request.getQueryString();
                }
                if (_requestUrl.startsWith("/"))
                    _requestUrl = _requestUrl.substring(1);
                String newUrl = request.getSession().getServletContext().getInitParameter("siteUrl") + _requestUrl;
                response.sendRedirect(newUrl);
                // response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }
    }

    /** 计算 根路径 */
    private static final String getCurrentSiteUrl(HttpServletRequest request) {
        String root = request.getScheme() + "://" + request.getServerName() + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort())
                + request.getContextPath() + "/";
        return root;
    }
}