package cn.edustar.jitar.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edustar.jitar.JitarConst;
import cn.edustar.jitar.util.ImgUtil;

/**
 * 获得处理过的图像
 * 
 * @author renliang
 */

@WebServlet(name = "getThumbnailImageUrl", urlPatterns = {"/manage/getThumbnailImageUrl"})
public class GetThumbnailImageUrl extends HttpServlet {
    private static final long serialVersionUID = -6224148415676072098L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String thumbnailPrefix = JitarConst.IMG_WIDTH_CONTROL + "x" + JitarConst.IMG_HEIGHT_CONTROL + "_";
        //getThumbnailImageUrl?cmd=/jitar2/user/administrator/edustar/20130916112817311516.jpg
        String cmd = req.getParameter("cmd");
        String virtualUrl = cmd.substring(0, cmd.lastIndexOf("/") + 1);
        String fileName = cmd.substring(cmd.lastIndexOf("/") + 1);
        //if (fileName.indexOf("_") > -1) {
        //    fileName = fileName.substring(fileName.indexOf("_") + 1, fileName.length());
        //}
        
        cmd = cmd.replaceFirst(req.getContextPath(), "");
        cmd = req.getServletContext().getRealPath(cmd);
        //如果文件已经存在。则不进行剪切
        if(fileName.startsWith(thumbnailPrefix)){
            File f = new File(cmd);
            if(f.exists()){
                resp.setContentType("text/plain");
                resp.getWriter().write(req.getParameter("cmd"));
                return;
            }
        }
        String outFile = cmd.substring(0, cmd.lastIndexOf(File.separator) + 1) + thumbnailPrefix + fileName;
        virtualUrl += thumbnailPrefix + fileName;
        File file = new File(outFile);
        if (!file.exists()) {
            try {
                ImgUtil.saveImageAsJpg(cmd, outFile, JitarConst.IMG_WIDTH_CONTROL, JitarConst.IMG_HEIGHT_CONTROL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        file = null;
        resp.setContentType("text/plain");
        resp.getWriter().write(virtualUrl);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
