package cn.edustar.jitar.action;

/**
 * 判断是否登录的简单实现。
 * @author mxh
 *
 */
public class CheckLoginAction extends AbstractServletAction{
    private static final long serialVersionUID = -1830607862379517066L;
    public final String execute() throws Exception {
        response.setContentType("text/html");
        if(this.getLoginUser() == null){
            response.getWriter().write("0");
        }
        else{
            response.getWriter().write("1");
        }
        return NONE;
    }
}
