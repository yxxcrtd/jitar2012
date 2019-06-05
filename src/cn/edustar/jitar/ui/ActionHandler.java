package cn.edustar.jitar.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Struts Action 处理器.
 * 
 * 
 */
public interface ActionHandler {

    /**
     * 执行指定请求并返回一个字符串结果.
     * 
     * @param request
     * @param response
     * @return
     */
    public String execute(HttpServletRequest request, HttpServletResponse response);

}
