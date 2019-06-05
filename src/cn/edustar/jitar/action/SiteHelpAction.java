package cn.edustar.jitar.action;

/**
 * 网站帮助
 * @author mxh
 *
 */
public class SiteHelpAction extends ManageBaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -7399379458148445169L;

    @Override
    protected String execute(String cmd) throws Exception {
        if (this.isUserLogined() == false) {
            this.addActionError("请先登录系统查看帮助手册。");
            this.addActionLink("返回首页", request.getContextPath() + "/");
            return ERROR;
        }
        return SUCCESS;
    }

}
