package cn.edustar.jitar.action;

import java.io.IOException;
import java.util.List;

import net.zdsoft.passport.service.client.PassportClient;
import cn.edustar.jitar.data.Command;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Plugin;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.PluginService;
import cn.edustar.jitar.service.UserQuery;
/**
 * 
 * @author baimindong
 *
 */
public class UsermanageAction extends ManageBaseAction {
	private static final long serialVersionUID = 8925990872752232434L;
	private AccessControlService accessControlService;
	private PluginService pluginService;
	
	@Override
    public String execute(String cmd) throws Exception {
        if(getLoginUser() == null){
            response.sendRedirect(request.getServletContext().getContextPath() + "/login.jsp");
            return NONE;
        }
        
        
        if(canVisitUser(getLoginUser()) == false){
            return ERROR;
        }
        //// 判断是否有整站内容管理权限    
        boolean canManage = false;
        User user = getLoginUser();
        if(!accessControlService.isSystemAdmin(user)){
        	List<AccessControl> control_list = accessControlService.getAllAccessControlByUser(user);
            if(control_list != null){
                for(AccessControl ac : control_list){
                    if(ac.getObjectType() == AccessControl.OBJECTTYPE_SUPERADMIN || ac.getObjectType() == AccessControl.OBJECTTYPE_SYSTEMUSERADMIN || ac.getObjectType() == AccessControl.OBJECTTYPE_SYSTEMCONTENTADMIN){
                        canManage = true;
                    }
                }
            }
        }else{
            canManage = true;
        }  
        if(canManage){
            request.setAttribute("canManage", "true");
        }else{
            request.setAttribute("canManage", "false");
        }
            
        if("nav".equals(cmd)){
            return nav();
        }else if("main".equals(cmd)){
            return main();
        }else{
            return frame();
        }
  	}

    // 显示框架页.
    public String frame(){
        String url = param_util.getStringParam("url");
        if(url == null || "".equals(url)){ url = "?cmd=main";}
        
        Integer specialSubjectId = param_util.getIntParam("specialSubjectId");
        if(specialSubjectId == null){ specialSubjectId = 0;}
        
        request.setAttribute("specialSubjectId", specialSubjectId);
        if(url.indexOf("?") >= 0){
            url = url + "&specialSubjectId=" + specialSubjectId;
        }else{
            url = url + "?specialSubjectId=" + specialSubjectId;
        }
        request.setAttribute("url", url);
        return "index";
    }
    // 显示导航菜单.
    public String nav(){
    	List<Plugin> plugin_list = pluginService.getPluginList();
    	List<AccessControl> aclist = accessControlService.getAllAccessControlByUserAndObjectType(getLoginUser(), AccessControl.OBJECTTYPE_CUSTORMCOLUMNADMIN);
        request.setAttribute("aclist", aclist);
        request.setAttribute("plugin_list", plugin_list);
        request.setAttribute("passportURL", "");
        if(request.getServletContext().getServletRegistration("passportClientInit")!=null){
            String passportURL = PassportClient.getInstance().getPassportURL();
            if (passportURL == null){
                passportURL = "";
            }
            if (passportURL == "http://"){
                passportURL = "";
            }
            if(passportURL != ""){
                if(!passportURL.endsWith("/")){
                    passportURL += "/";
                }
            }
            request.setAttribute("passportURL", passportURL);
        }else if(null != request.getServletContext().getFilterRegistration("vlidationFilter")){
        	String casUserServerUrl =request.getServletContext().getFilterRegistration("vlidationFilter").getInitParameter("casUserServerUrl");
        	request.setAttribute("passportURL", casUserServerUrl);
        }
        return "left";
    }
    // 显示欢迎页.
    public String main(){
        request.setAttribute("user", getLoginUser());
    
        // TODO: 下面的查询用 XxxQuery 改写, 现在统计不对.
        String hql = " select count(*) as groupCount FROM GroupMember gm  where gm.userId = " + getLoginUser().getUserId();
        Command cmd = new Command(hql);    
        int groupCount = cmd.int_scalar();
        request.setAttribute("groupCount", groupCount);
        
        hql = "select count(*) as messageCount FROM Message m where m.receiveId = " + getLoginUser().getUserId();
        cmd = new Command(hql);    
        int messageCount = cmd.int_scalar();
        request.setAttribute("messageCount", messageCount);
        
        hql = " select count(*) as friendCount FROM Friend f where f.userId = " + getLoginUser().getUserId();
        cmd = new Command(hql);    
        int friendCount = cmd.int_scalar();
        request.setAttribute("friendCount", friendCount);
        return "main"; 
    }
	public AccessControlService getAccessControlService() {
		return accessControlService;
	}

	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}

	public PluginService getPluginService() {
		return pluginService;
	}

	public void setPluginService(PluginService pluginService) {
		this.pluginService = pluginService;
	}
}
