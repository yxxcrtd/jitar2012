package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.service.GroupQuery;
import cn.edustar.jitar.service.UnitQuery;
import cn.edustar.jitar.service.UserQuery;
import cn.edustar.jitar.service.UserQuery2;

/**
 * 选择用户功能
 * 
 * selectUser.py selectUser0.py selectUser1.py 三个文件合成
 * 
 * @author baimindong
 *
 */
public class SelectUserAction extends ManageBaseAction {

	private static final long serialVersionUID = -4095666649327785436L;
	private ConfigService configService;

	@Override
	protected String execute(String cmd) throws Exception {
		if(cmd == null || cmd.length() == 0){cmd="show";}
		if(cmd.equals("show")){
			return show();	
		}else if(cmd.equals("list")){
			return list();
		}else if(cmd.equals("glist")){
			return glist();
		}else{
			return show();
		}
	}
	
	private String glist(){
		//群组
		groupList();
	    List user_list=null;
	    Integer singleuser= param_util.getIntParam("singleuser");//  #1 用户单选 2 用户多选
	    if(singleuser==null){
	      singleuser=1;
	    }
	    Integer closewindow  =param_util.getIntParam("closewindow");
	    if(closewindow==null){
	      closewindow=0;
	    }
	    String groupids=param_util.getStringParam("groupids");
	    if(groupids==null){
	      closewindow=0;
	    }else{
	      if(closewindow==1){
	    	 UserQuery userqry = new UserQuery("u.userId as userId,u.gender, u.loginName as loginName, u.trueName as trueName,gm.userId as gmuserId");

	    	 userqry.gm_groupIds =  groupids;
	         user_list = userqry.query_map();
	      }
	    }
	    request.setAttribute("closewindow", closewindow);
	    request.setAttribute("user_list", user_list);
	    String inputUser_Id= param_util.getStringParam("inputUser_Id");
	    String inputUserName_Id= param_util.getStringParam("inputUserName_Id");
	    String inputLoginName_Id = param_util.getStringParam("inputLoginName_Id");
	    request.setAttribute("singleuser", singleuser);
	    request.setAttribute("inputUser_Id", inputUser_Id);
	    request.setAttribute("inputUserName_Id", inputUserName_Id);
	    request.setAttribute("inputLoginName_Id", inputLoginName_Id);
	    response.setContentType("text/html; charset=UTF-8");
	    return "glist";		
	}
	
	/**
	 * 列出协作组
	 */
	private void groupList(){
		GroupQuery qry = new GroupQuery(" g.groupId, g.groupName, g.groupTitle, g.groupIcon, g.createDate, g.subjectId, g.gradeId,"+ 
	            "g.createUserId, g.groupTags, g.groupIntroduce, g.groupState, g.userCount, g.visitCount,"+ 
	            "g.isBestGroup, g.isRecommend, u.trueName, u.loginName, subj.subjectName, sc.name as categoryName");
	      qry.groupState = 0;
	      qry.kk = param_util.getStringParam("k");
	      request.setAttribute("k", qry.kk);
	      
	      //# 构造分页并查询数据.
	      
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("协作组", "个");
		pager.setPageSize(10);
	    pager.setTotalRows(qry.count());
        List group_list =(List) qry.query_map(pager);
      request.setAttribute("group_list", group_list);
      request.setAttribute("pager", pager);
	}
	
	private String list(){
		//查询用户 
		userList();
		//机构列表
		get_units_list();
		
	    Integer singleuser = param_util.getIntParam("singleuser"); //  #1 用户单选 2 用户多选
	    if(singleuser == null){
	      singleuser = 1;
	    }
	      
	    String inputUser_Id = param_util.getStringParam("inputUser_Id");
	    String inputUserName_Id = param_util.getStringParam("inputUserName_Id");
	    String inputLoginName_Id = param_util.getStringParam("inputLoginName_Id");
	    request.setAttribute("singleuser", singleuser);
	    request.setAttribute("inputUser_Id", inputUser_Id);
	    request.setAttribute("inputUserName_Id", inputUserName_Id);
	    request.setAttribute("inputLoginName_Id", inputLoginName_Id);
	    response.setContentType("text/html; charset=UTF-8");
	    return "list";
	}
	
	private void get_units_list(){
		//searchUser0.py原来此处是hql直接查询，改为 UnitQuery方式
		UnitQuery qry = new UnitQuery(" unit.unitId as unitId, unit.unitTitle as unitTitle ");
		qry.setMaxResults(-1);
		List unit_list = qry.query_map();
        request.setAttribute("unit_list", unit_list);		
	}
	
	private void userList(){
	    Integer unitId = param_util.getIntParamZeroAsNull("unitId");
	    String userName = param_util.getStringParam("userName");
	    UserQuery2 qry = new UserQuery2(" u.userId, u.loginName, u.userIcon, u.gender, u.blogName, u.trueName, u.unitId,  unit.unitName, unit.unitTitle ");

	    qry.userStatus = 0;
	    qry.kk = userName;
	    qry.unitId = unitId;
	    
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("用户", "个");
		pager.setPageSize(10);
	    pager.setTotalRows(qry.count());
	    List user_list = (List)qry.query_map(pager); 
	    request.setAttribute("unitId", unitId);
	    request.setAttribute("user_list", user_list);
	    request.setAttribute("userName", userName);
	    request.setAttribute("pager", pager);
		
	}
	private String show(){
		String inputUser_Id = param_util.getStringParam("inputUser_Id");
	    if(inputUser_Id==null){
	      inputUser_Id="";
	    }
	    String inputUserName_Id = param_util.getStringParam("inputUserName_Id");
	    if(inputUserName_Id==null){
	      inputUserName_Id="";
	    }
	    String inputLoginName_Id = param_util.getStringParam("inputLoginName_Id");
	    if(inputLoginName_Id==null){
	      inputLoginName_Id="";
	    }
	    String userUrl = param_util.getStringParam("userUrl");
	    if(userUrl==null){
	      userUrl="";
	    }
	    String roomid = param_util.getStringParam("roomid");
	    if(roomid==null){
	      roomid="";
	    }
	    Integer singleuser = param_util.getIntParam("singleuser");//  #1 用户单选 0 用户多选
	    Integer showgroup = param_util.getIntParam("showgroup");
	    if(singleuser==null){
	      singleuser=0;
	    }
	    if(showgroup==null){
	      showgroup=0;
	    }
	    request.setAttribute("showgroup", showgroup);
	    request.setAttribute("singleuser", singleuser);
	    request.setAttribute("inputUser_Id", inputUser_Id);
	    request.setAttribute("inputUserName_Id", inputUserName_Id);
	    request.setAttribute("inputLoginName_Id", inputLoginName_Id);
	    request.setAttribute("userUrl", userUrl);
	    request.setAttribute("roomid", roomid);
	    response.setContentType("text/html; charset=UTF-8");	
	    return "show";		
	}
	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
}
