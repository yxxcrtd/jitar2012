package cn.edustar.jitar.action;

public class UserSelectTopAction  extends ManageBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4224595899601552467L;

	@Override
	protected String execute(String cmd) throws Exception {
        //# 选择类型，单选还是多选？type:1单选,0多选
        String type = param_util.safeGetStringParam("type");
        String idTag = param_util.safeGetStringParam("idTag");
        String titleTag = param_util.safeGetStringParam("titleTag");
        String backType = param_util.safeGetStringParam("back");
        
        request.setAttribute("type", type);
        request.setAttribute("idTag", idTag);
        request.setAttribute("titleTag", titleTag);
        request.setAttribute("back", backType);
		
		if(cmd == null || cmd.length() == 0){cmd="list";}
		return cmd;
		
		
	}
}
