package cn.edustar.jitar.action;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.service.UserQuery;

public class UserSelectBottomAction  extends ManageBaseAction{

	private static final long serialVersionUID = -4303701931898626284L;

	@Override
	protected String execute(String cmd) throws Exception {
        //# 选择类型，单选还是多选？type:1单选,0多选
        String type = param_util.safeGetStringParam("type");
        String idTag = param_util.safeGetStringParam("idTag");
        String titleTag = param_util.safeGetStringParam("titleTag");
        String backType = param_util.safeGetStringParam("back");
        String k = param_util.safeGetStringParam("k");
        String f = param_util.safeGetStringParam("f");
        
        request.setAttribute("type", type);
        request.setAttribute("idTag", idTag);
        request.setAttribute("titleTag", titleTag);
        request.setAttribute("back", backType);
        request.setAttribute("k", k);
        request.setAttribute("f", f);
		
        UserQuery qry = new UserQuery(" u.userId, u.loginName, u.trueName, u.createDate, unit.unitName, unit.unitTitle ");
      
        qry.k = k;
        qry.f = f;
        qry.orderType = 0;
        qry.userStatus = 0;
        
		Pager pager = param_util.createPager();
		pager.setItemName("用户");
		pager.setItemUnit("个");
		pager.setPageSize(20);
		pager.setTotalRows(qry.count());
        List user_list = (List)qry.query_map(pager);
        request.setAttribute("pager", pager);        
        request.setAttribute("user_list" , user_list);
        
		if(cmd == null || cmd.length() == 0){cmd="list";}
		return cmd;
		
		
	}
}
