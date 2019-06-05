package cn.edustar.jitar.action;

import cn.edustar.jitar.pojos.Placard;
import cn.edustar.jitar.service.PlacardService;

/**
 * 查看公告
 * @author mxh
 *
 */
public class SiteShowPlacardAction extends AbstractBasePageAction {

    /**
     * 
     */
    private static final long serialVersionUID = 4255200314447682884L;

    private PlacardService placardService;

    @Override
    protected String execute(String cmd) throws Exception {

        Integer placardId = params.getIntParam("placardId");
        if (null == placardId || placardId == 0) {
            this.addActionError("没有标识。");
            this.addActionLink("返回首页", request.getContextPath() + "/");
        }

        Placard placard = this.placardService.getPlacard(placardId);

        if (null == placard) {
            this.addActionError("不能加载对象。");
            this.addActionLink("返回首页", request.getContextPath() + "/");
        }
        request.setAttribute("placard", placard);
        return SUCCESS;
    }
    public void setPlacardService(PlacardService placardService) {
        this.placardService = placardService;
    }
}
