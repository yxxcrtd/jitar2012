package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.AccessControl;
import cn.edustar.jitar.pojos.Channel;
import cn.edustar.jitar.pojos.ChannelUser;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.ChannelUserQuery;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.service.UserService;

/**
 * 自定义频道用户管理
 * @author baimindong
 *
 */
public class ChannelUserAction extends BaseChannelManage{

	private static final long serialVersionUID = -1008261666737300956L;
	private UserService userService;
	private UnitService unitService;
	private Channel channel = null;
	protected String execute(String cmd) throws Exception{
        Integer channelId = param_util.safeGetIntParam("channelId");
        this.channel = channelPageService.getChannel(channelId);
        if (channel == null){
            addActionError("不能加载频道对象。");
            return ERROR;
        }
        if(isSystemAdmin() == false && isChannelSystemAdmin(channel) == false && isChannelUserAdmin(channel) == false){
            addActionError("你无权管理本频道。");
            return ERROR;		
        }
        String admin_type = "";
        if (isChannelUserAdmin(channel)){
            admin_type = "user_admin";
        }
        if (isSystemAdmin() || isChannelSystemAdmin(channel)){
            admin_type = "sys_admin";
        }
        
        request.setAttribute("cmd", cmd);
        request.setAttribute("admin_type", admin_type);
        if("POST".equals(request.getMethod())){
            save_post(cmd);
        }
        get_list();		
		return "list";
	}
	private void save_post(String cmd){
        List<Integer> guids = param_util.safeGetIntValues("guid");
        for(Integer g : guids){
            User user = userService.getUserById(g);
            if(user != null){
                if("remove".equals(cmd)){
                    set_admin(user, "remove");
                }else if ("content_admin".equals(cmd)){
                    set_admin(user, "content");
                }else if ("uncontent_admin".equals(cmd)){       
                    set_admin(user, "uncontent");
                }else if ("user_admin".equals(cmd)){
                    set_admin(user, "user");
                }else if ("unuser_admin".equals(cmd)){
                    set_admin(user, "unuser");
                }else if ("add".equals(cmd)){
                    set_admin(user, "add");
                }
            }
        }
	}
	private void set_admin(User user,String admin_type){
        if(admin_type.equals("content")){
            if(accessControlService.checkUserAccessControlIsExists(user.getUserId(), AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN, channel.getChannelId()) == false){
            	AccessControl accessControl = new AccessControl();
                accessControl.setUserId(user.getUserId());
                accessControl.setObjectType(AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN);
                accessControl.setObjectId(channel.getChannelId());
                accessControl.setObjectTitle(channel.getTitle());
                accessControlService.saveOrUpdateAccessControl(accessControl);
            }
        }else if(admin_type.equals("uncontent")){
            if(accessControlService.checkUserAccessControlIsExists(user.getUserId(), AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN, channel.getChannelId()) == true){
                accessControlService.deleteAccessControlByUserIdObjectTypeObjectId(user.getUserId(), AccessControl.OBJECTTYPE_CHANNELCONTENTADMIN, channel.getChannelId());
            }
        }else if(admin_type.equals("user")){
            if(accessControlService.checkUserAccessControlIsExists(user.getUserId(), AccessControl.OBJECTTYPE_CHANNELUSERADMIN, channel.getChannelId()) == false){
            	AccessControl accessControl = new AccessControl();
                accessControl.setUserId(user.getUserId());
                accessControl.setObjectType(AccessControl.OBJECTTYPE_CHANNELUSERADMIN);
                accessControl.setObjectId(channel.getChannelId());
                accessControl.setObjectTitle(channel.getTitle());
                accessControlService.saveOrUpdateAccessControl(accessControl);
            }
        }else if(admin_type.equals("unuser")){
            if(accessControlService.checkUserAccessControlIsExists(user.getUserId(), AccessControl.OBJECTTYPE_CHANNELUSERADMIN, channel.getChannelId()) == true){
                accessControlService.deleteAccessControlByUserIdObjectTypeObjectId(user.getUserId(), AccessControl.OBJECTTYPE_CHANNELUSERADMIN, channel.getChannelId());
            }
        }else if(admin_type.equals("remove")){
            channelPageService.deleteChannelUserByUserIdAndChannelId(channel.getChannelId(), user.getUserId());
        }else if(admin_type.equals("add")){
        	ChannelUser channelUser = channelPageService.getChannelUserByUserIdAndChannelId(user.getUserId(), channel.getChannelId());
            if(channelUser == null){
                if (user.getUnitId() != null){
                    Unit unit = unitService.getUnitById(user.getUnitId());
                    if(unit != null){
                    	channelUser = new ChannelUser();
                        channelUser.setChannelId(channel.getChannelId());
                        channelUser.setUserId(user.getUserId());
                        channelUser.setUnitId(unit.getUnitId());
                        channelUser.setUnitTitle(unit.getUnitTitle());
                        channelPageService.saveOrUpdateChannelUser(channelUser);
                    }
                }
            }
        }
     }
	private void get_list(){
        String k = param_util.safeGetStringParam("k");
        String f = param_util.safeGetStringParam("f");
        String ustate = param_util.safeGetStringParam("ustate");
        if(ustate.equals("")){
            ustate = "-1";
        }
        if(f.length() == 0){f = "name";}
        ChannelUserQuery qry = new ChannelUserQuery("cu.channelUserId, cu.userId, user.loginName, user.trueName, user.userStatus, user.createDate, user.userType, cu.unitId, cu.unitTitle");
        qry.setRequest(request);
        qry.orderType = 0;
        qry.channelId = channel.getChannelId();
        if(ustate.equals("0")){
            qry.userStatus = 0;
        }else if(ustate.equals("1")){
            qry.userStatus = 1;
        }else if(ustate.equals("2")){
            qry.userStatus = 2;
        }else if(ustate.equals("3")){
            qry.userStatus = 3;
        }else{
            qry.userStatus = null;
        }
        if(k.length() > 0){
            qry.f = f;
            qry.k = k;
            request.setAttribute("k", k);
            request.setAttribute("f", f);
        }
		Pager pager = param_util.createPager();
		pager.setItemName("用户");
		pager.setItemUnit("个");
		pager.setPageSize(10);
        pager.setTotalRows(qry.count());
        List user_list = (List)qry.query_map(pager);
        request.setAttribute("ustate", ustate);
        request.setAttribute("pager", pager);    
        request.setAttribute("user_list" , user_list);
        request.setAttribute("channel" , channel); 		
	}
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
}
