package cn.edustar.jitar.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.data.Command;
import cn.edustar.jitar.pojos.UFavorites;
import cn.edustar.jitar.service.UFavoritesService;

public class FavoriteAction  extends ManageBaseAction{
	
	private static final long serialVersionUID = -6639325596448620935L;
	private UFavoritesService uFavoritesService;
	@Override
	protected String execute(String cmd) throws Exception {
	    if(cmd == null || "".equals(cmd)){ cmd = "list";}
	    if("list".equals(cmd)){
	      return list();
	    }
	    if("add".equals(cmd)){
	      return save();
	    }
	    if("del".equals(cmd)){
	      return Del();
	    }
		return "list";
	}
	
	private String list() throws IOException{
		PrintWriter writer = response.getWriter();
	      if(getLoginUser() == null){
	        writer.write("logon");
	        return NONE;
	      }
	      int favUser=getLoginUser().getUserId();
	      List<UFavorites> fav_list=uFavoritesService.getUFavoritesList(favUser);
	      
		Pager pager = param_util.createPager();
		pager.setItemName("收藏");
		pager.setItemUnit("条");
		pager.setPageSize(25);
	      if(fav_list==null){
	        pager.setTotalRows(0);
	      }else{  
	    	  pager.setTotalRows(fav_list.size());
	      }
	      
	      String hql=" from UFavorites Where favUser=:favUser ORDER BY favId DESC";
	      Command cmd = new Command(hql);
	      cmd.setInteger("favUser", favUser);
	      fav_list = cmd.open(pager);
	      request.setAttribute("pager", pager);
	      request.setAttribute("fav_list", fav_list);	
	   return "list";   
	}
	private String save() throws IOException{
		PrintWriter writer = response.getWriter();
	      if(getLoginUser() == null){
		        writer.write("logon");
		        return NONE;
		      }
	      int favUser=getLoginUser().getUserId();
	      Integer objectId = param_util.getIntParam("objectId");
	      Integer objectType = param_util.getIntParam("objectType");
	      String objectUuid = param_util.getStringParam("objectUuid");
	      String favHref= param_util.getStringParam("url");
	      String favTitle= param_util.getStringParam("title");
	      //#encodeTitle = URLEncoder.encode(favTitle, "UTF-8")
	      int favTypeID=0;
	      String favInfo="";
	      boolean b=uFavoritesService.Exists(favUser,objectType,objectId,favHref);
	      if (b){
	        writer.write("exist");
	        return NONE;
	      }
	      uFavoritesService.Save(favUser, objectType, objectUuid, objectId, favTitle, favInfo, favTypeID, favHref);
	      writer.write("ok");
	      return NONE;		
	}
	private String Del() throws IOException{
	    Integer favId = param_util.getIntParam("favId");
	    if(favId!=null){
	    	uFavoritesService.Del(favId);
	    }
	    return list();
	}
	
	public void setUFavoritesService (UFavoritesService uFavoritesService){
		this.uFavoritesService = uFavoritesService;
	}
	public UFavoritesService getUFavoritesService(){
		return this.uFavoritesService;
	}
}
