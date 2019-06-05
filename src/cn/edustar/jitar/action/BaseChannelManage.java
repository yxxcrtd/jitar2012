package cn.edustar.jitar.action;

import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.ChannelPageService;
import cn.edustar.jitar.util.CommonUtil;

/**
 * 自定义频道后台管理的新基类
 * 
 * @author baimindong
 *
 */
public abstract class BaseChannelManage extends ManageBaseAction  {
	
	private static final long serialVersionUID = 81039722590779603L;

	protected ChannelPageService channelPageService;
	protected AccessControlService accessControlService;
	
    public boolean isSystemAdmin(){
    	if (!isUserLogined()){return false;}
        return accessControlService.isSystemAdmin(getLoginUser());
    }
    
    public boolean isChannelSystemAdmin(Channel channel){
    	if (!isUserLogined()){return false;}
        if (accessControlService.isSystemAdmin(getLoginUser())){return true;}
        return accessControlService.checkUserAccessControlIsExists(getLoginUser().getUserId(), AccessControl.OBJECTTYPE_CHANNELSYSTEMADMIN, channel.getChannelId());
    }
    
    public boolean isChannelUserAdmin(Channel channel){
    	if (!isUserLogined()){return false;}
    	if (accessControlService.isSystemAdmin(getLoginUser())){return true;}
        return accessControlService.checkUserAccessControlIsExists(getLoginUser().getUserId(), AccessControl.OBJECTTYPE_CHANNELUSERADMIN, channel.getChannelId());
    }
    
    public boolean isChannelContentAdmin(Channel channel){
    	if (!isUserLogined()){return false;}
    	if (accessControlService.isSystemAdmin(getLoginUser())){return true;}
        return accessControlService.checkUserAccessControlIsExists(getLoginUser().getUserId(), AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN, channel.getChannelId());
    }
    
    public String GetAdminType(Channel channel){
        String AdminType = "|";
        if(accessControlService.isSystemAdmin(getLoginUser())){
            AdminType = AdminType + "SystemSuperAdmin|";
        }
        if(accessControlService.isSystemUserAdmin(getLoginUser())){
            AdminType = AdminType + "SystemUserAdmin|";
        }
        if(accessControlService.isSystemContentAdmin(getLoginUser())){
            AdminType = AdminType + "SystemContentAdmin|";
        }
        if(accessControlService.getAccessControlByUserAndObject(getLoginUser().getUserId(), AccessControl.OBJECTTYPE_CHANNELSYSTEMADMIN, channel.getChannelId()) != null){
            AdminType = AdminType + "ChannelSystemAdmin|";
        }
        if(accessControlService.getAccessControlByUserAndObject(getLoginUser().getUserId(), AccessControl.OBJECTTYPE_CHANNELUSERADMIN, channel.getChannelId()) != null){
            AdminType = AdminType + "ChannelUserAdmin|";
        }
        if(accessControlService.getAccessControlByUserAndObject(getLoginUser().getUserId(), AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN, channel.getChannelId()) != null){
            AdminType = AdminType + "ChannelContentAdmin|";
        }
        if(AdminType.equals("|")){AdminType = "";}
        return AdminType;
    }
    
    public String getSiteUrl(){
        return CommonUtil.getSiteUrl(request);
    }
        		
	public ChannelPageService getChannelPageService() {
		return channelPageService;
	}

	public void setChannelPageService(ChannelPageService channelPageService) {
		this.channelPageService = channelPageService;
	}

	public AccessControlService getAccessControlService() {
		return accessControlService;
	}

	public void setAccessControlService(AccessControlService accessControlService) {
		this.accessControlService = accessControlService;
	}
}
