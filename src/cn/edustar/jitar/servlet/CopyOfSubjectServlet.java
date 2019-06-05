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
 *         学科处理服务
 * 
 */
public class CopyOfSubjectServlet extends HttpServlet {

    private static final long serialVersionUID = 4394698374990673727L;
    private static final Log log = LogFactory.getLog(CopyOfSubjectServlet.class);  

    public void init() {
    }

    public void destroy() {
        log.info("SubjectServlet 销毁了");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // log.info("执行学科页面……");
        String context_path = request.getRequestURI();
        String virtualPath = request.getContextPath();
        if (!virtualPath.endsWith("/")) {
            // 兼容虚拟目录
            context_path = context_path.replaceFirst(virtualPath, "");
        }

        if (!context_path.startsWith("/k/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String trimedUrl = context_path.substring(3); // 去掉 /d/三个字符
        // log.info("context_path = " + context_path + ", trimedUrl = " +
        // trimedUrl);

        if (trimedUrl.endsWith("/"))
            trimedUrl = trimedUrl.substring(0, trimedUrl.length() - 1);

        String subjectCode = trimedUrl;
        if (subjectCode.indexOf("/") > -1) {
            subjectCode = subjectCode.substring(0, subjectCode.indexOf("/"));
        }
        String configSubjectSiteRoot = request.getSession().getServletContext().getInitParameter("subjectUrlPattern");
        String subjectRootUrl;
        if (configSubjectSiteRoot == null || configSubjectSiteRoot.equals("")) {
            subjectRootUrl = CopyOfSubjectServlet.getCurrentSiteUrl(request) + "k/" + subjectCode + "/";
        } else {
            subjectRootUrl = configSubjectSiteRoot.replaceAll("\\{subjectCode\\}", subjectCode);
        }
        if (request.getAttribute("SubjectRootUrl") == null) {
            request.setAttribute("SubjectRootUrl", subjectRootUrl);
        }

        // log.info("执行学科页面： subjectName = " + subjectCode);
        // log.info("执行学科页面： trimedUrl = " + trimedUrl);
        if (request.getAttribute("subjectCode") == null) {
            request.setAttribute("subjectCode", subjectCode);
        }

        if (trimedUrl.indexOf("/") == -1) {
            // log.info("访问学科首页");
            this.getServletContext().getRequestDispatcher(/*"/WEB-INF/program/subject/index.py"*/"/subjectIndex.action").forward(request, response);
            return;
        } else {
            String[] urlSlice = trimedUrl.split("/");
            String slice1 = urlSlice[1];          
            if (slice1.equals("article")) {
                request.getRequestDispatcher("/subjectArticle.action").forward(request, response);
                return;
            } else if ("resource".equals(slice1)) {
                request.getRequestDispatcher("/subjectResource.action").forward(request, response);
                return;
            } else if ("photo".equals(slice1)) {
                request.getRequestDispatcher("/photo.action").forward(request, response);
                return;
            } else if ("video".equals(slice1)) {
            	request.getRequestDispatcher("/subject_video.action").forward(request, response);
                return;
            } else if ("blog".equals(slice1)) {
                request.getRequestDispatcher("/blog.action").forward(request, response);
                return;
            } else if ("groups".equals(slice1)) {
                request.getRequestDispatcher("/subjectGroup.action").forward(request, response);
                return;
            } else if ("preparecourse".equals(slice1)) {
                request.getRequestDispatcher("/subjectPrepareCourse.action").forward(request, response);
                return;
            } else if ("activity".equals(slice1)) {
                request.getRequestDispatcher("/activity.action").forward(request, response);
                return;
            } else if ("specialsubject".equals(slice1)) {
                request.getRequestDispatcher("/specialsubject.action").forward(request, response);
//                this.getServletContext().getRequestDispatcher("/WEB-INF/program/subject/specialsubject.py").forward(request, response);
                return;
            } else if ("py".equals(slice1)) {
                String fileName = trimedUrl.substring(trimedUrl.lastIndexOf("/") + 1);
                //py转action的临时处理
                if(fileName.equals("home.py")){
                    response.sendRedirect(SiteUrlModel.getSiteUrl());
                    return;
                }
                this.getServletContext().getRequestDispatcher("/WEB-INF/program/subject/" + fileName).forward(request, response);
                return;
            } else if ("manage".equals(slice1)) {
                String fileName = "/subject/subjectmanage/" + trimedUrl;
                this.getServletContext().getRequestDispatcher(fileName).forward(request, response);
                return;
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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