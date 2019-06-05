package cn.edustar.jitar.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.manage.UserStatManage;
import cn.edustar.jitar.pojos.Group;
import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.pojos.UserStat;
import cn.edustar.jitar.query.UserStatQueryParam;
import cn.edustar.jitar.service.GroupQueryParam;
import cn.edustar.jitar.service.GroupService;
import cn.edustar.jitar.service.UnitQueryParam;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.util.ConfigurationFileIni;

/**
 * 根据 ranklist.ini 文件的定义，来实现各种类型(个人排行,教研员排行,协作组排行,学校排行)的排行榜
 * 
 * @author baimindong
 *
 */
public class RankListAction extends ManageBaseAction {
	
	private static final long serialVersionUID = 5702813362747627031L;
	
	/** 群组服务 */
	private GroupService groupService;
	/** 单位服务 */
	private UnitService unitService;
	/** 用户统计 */
	private UserStatManage userStatManage;
	
	@Override
	public String execute(String cmd) throws Exception {
		//获得配置文件ranklist.ini
		String iniFilePath = request.getServletContext().getRealPath("/ranklist.ini");
		File f = new File(iniFilePath);
		if(!f.exists()){
			addActionError("配置文件ranklist.ini不存在！");
			return ERROR;
		}
		
		String type = param_util.safeGetStringParam("type");
		String list_type;
		if(null == type || type.length() == 0){
			type = "user";
		}
		if(type.equals("user")){
			list_type = "个人排行";
			get_user_list(iniFilePath);
		}else if(type.equals("ts")){
			list_type = "教研员排行";
			get_ts_list(iniFilePath);
		}else if(type.equals("group")){
			list_type = "协作组排行";
			get_group_list(iniFilePath);
		}else if(type.equals("school")){
			list_type = "学校排行";
			get_school_list(iniFilePath);
		}else{
			type = "user";
			list_type = "个人排行";
			get_user_list(iniFilePath);
		}
		setRequestAttribute("type", type);
		setRequestAttribute("list_type", list_type);
		setRequestAttribute("iniFilePath", iniFilePath);
	    setRequestAttribute("head_nav", "rank");
		return LIST_SUCCESS;
	}
	
	/**
	 * 得到用户统计查询参数
	 * @param iniFilePath 排行榜的配置文件
	 * @param sectionname 配置文件中的sectionname部分
	 * @return
	 */
	private UserStatQueryParam getUserStatQueryParam(String iniFilePath,String sectionname){
		String userOrder = null;
		try {
			userOrder = ConfigurationFileIni.getValue(iniFilePath, sectionname,"userOrder","");
		} catch (IOException e) {
			e.printStackTrace();
		}
		UserStatQueryParam param = new UserStatQueryParam("User"); 
	    if(null == userOrder || userOrder.length() ==0){
	        param.orderType = param.ORDER_TYPE_SCORE_DESC;
	    }else if(userOrder.equals("trueName")){
	        param.orderType= param.ORDER_TYPE_trueName_ASC;
	      }else if(userOrder.equals("userScore")){
	        param.orderType= param.ORDER_TYPE_userScore_DESC;
	      }else if(userOrder.equals("visitCount")){
	        param.orderType= param.ORDER_TYPE_visitCount_DESC;
	      }else if(userOrder.equals("visitArticleCount")){
	        param.orderType= param.ORDER_TYPE_visitArticleCount_DESC;
	      }else if(userOrder.equals("visitResourceCount")){
	        param.orderType= param.ORDER_TYPE_visitResourceCount_DESC;
	      }else if(userOrder.equals("myArticleCount")){
	        param.orderType= param.ORDER_TYPE_myArticleCount_DESC;
	      }else if(userOrder.equals("otherArticleCount")){
	        param.orderType= param.ORDER_TYPE_otherArticleCount_DESC;
	      }else if(userOrder.equals("recommendArticleCount")){
	        param.orderType= param.ORDER_TYPE_recommendArticleCount_DESC;
	      }else if(userOrder.equals("articleCommentCount")){
	        param.orderType= param.ORDER_TYPE_articleCommentCount_DESC;
	      }else if(userOrder.equals("articleICommentCount")){
	        param.orderType= param.ORDER_TYPE_articleICommentCount_DESC;
	      }else if(userOrder.equals("resourceCount")){
	        param.orderType= param.ORDER_TYPE_resourceCount_DESC;
	      }else if(userOrder.equals("recommendResourceCount")){
	        param.orderType= param.ORDER_TYPE_recommendResourceCount_DESC;
	      }else if(userOrder.equals("resourceCommentCount")){
	        param.orderType= param.ORDER_TYPE_resourceCommentCount_DESC;
	      }else if(userOrder.equals("resourceICommentCount")){
	        param.orderType= param.ORDER_TYPE_resourceICommentCount_DESC;
	      }else if(userOrder.equals("resourceDownloadCount")){
	        param.orderType= param.ORDER_TYPE_resourceDownloadCount_DESC;
	      }else if(userOrder.equals("prepareCourseCount")){
	        param.orderType= param.ORDER_TYPE_prepareCourseCount_DESC;
	      }else if(userOrder.equals("createGroupCount")){
	        param.orderType= param.ORDER_TYPE_createGroupCount_DESC;
	      }else if(userOrder.equals("jionGroupCount")){
	        param.orderType= param.ORDER_TYPE_jionGroupCount_DESC;
	      }else if(userOrder.equals("photoCount")){
	        param.orderType= param.ORDER_TYPE_photoCount_DESC;
	      }else if(userOrder.equals("videoCount")){
	        param.orderType= param.ORDER_TYPE_videoCount_DESC;
	      }else if(userOrder.equals("articlePunishScore")){
	        param.orderType= param.ORDER_TYPE_articlePunishScore_DESC;
	      }else if(userOrder.equals("resourcePunishScore")){
	        param.orderType= param.ORDER_TYPE_resourcePunishScore_DESC;
	      }else if(userOrder.equals("commentPunishScore")){
	        param.orderType= param.ORDER_TYPE_commentPunishScore_DESC;
	      }else if(userOrder.equals("photoPunishScore")){
	        param.orderType= param.ORDER_TYPE_photoPunishScore_DESC;
	      }else if(userOrder.equals("videoPunishScore")){
	        param.orderType= param.ORDER_TYPE_videoPunishScore_DESC;
	      }else{
	        param.orderType=param.ORDER_TYPE_SCORE_DESC;
	      }	
	    return param;
	}
	
	/**个人排行*/
	private void get_user_list(String iniFilePath){
		UserStatQueryParam param = getUserStatQueryParam(iniFilePath, "个人排行");
		if(null == param){
			return ;
		}
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("用户", "个");
		pager.setPageSize(30);
		List<UserStat> userList = this.userStatManage.getUserStatList(param, pager);
	    request.setAttribute("userList", userList);
	    request.setAttribute("pager", pager);
	}
	
	/**教研员排行 */
	private void get_ts_list(String iniFilePath){
		UserStatQueryParam param = getUserStatQueryParam(iniFilePath, "教研员排行");
		if(null == param){
			return ;
		}		
	    param.teacherType = 4;
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("用户", "个");
		pager.setPageSize(30);
		List<UserStat> userList = this.userStatManage.getUserStatList(param, pager);
		setRequestAttribute("userList", userList);
		setRequestAttribute("pager", pager);
	}
	
	/** 协作组排行 */
	private void get_group_list(String iniFilePath){
		String userOrder = "";
		try {
			//原py文件中先在这里进行一次 统计精华,注释掉??
			//this.groupService.UpdateBestGroupArticleAndResource();
			userOrder = ConfigurationFileIni.getValue(iniFilePath,"协作组排行","userOrder","");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GroupQueryParam param = new GroupQueryParam();
	    if(userOrder.equals("groupTitle")){
	        param.orderType = param.ORDER_BY_GROUPTITLE_ASC;
	    }else if(userOrder.equals("visitCount")){
	        param.orderType = param.ORDER_BY_VISITCOUNT_DESC;
	    }else if(userOrder.equals("userCount")){
	        param.orderType = param.ORDER_BY_USERCOUNT_DESC;
	    }else if(userOrder.equals("articleCount")){
	        param.orderType = param.ORDER_BY_ARTICLECOUNT_DESC;
	    }else if(userOrder.equals("bestArticleCount")){
	        param.orderType = param.ORDER_BY_BESTARTICLECOUNT_DESC; 
	    }else if(userOrder.equals("resourceCount")){
	        param.orderType = param.ORDER_BY_RESOURCECOUNT_DESC;
	    }else if(userOrder.equals("bestResourceCount")){
	        param.orderType = param.ORDER_BY_BESTRESOURCECOUNT_DESC;
	    }else if(userOrder.equals("topicCount")){
	        param.orderType = param.ORDER_BY_TOPICCOUNT_DESC;
	    }else if(userOrder.equals("discussCount")){
	        param.orderType = param.ORDER_BY_DISCUSSCOUNT_DESC;
	    }else if(userOrder.equals("actionCount")){
	        param.orderType = param.ORDER_BY_ACTIONCOUNT_DESC;
	    }else if(userOrder.equals("ALLCCOUNT")){    
	        param.orderType = param.ORDER_BY_ALLCCOUNT_DESC;
	    }else{
	        param.orderType = param.ORDER_BY_ALLCCOUNT_DESC;
	    }
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("组", "个");
		pager.setPageSize(30);

		List<Group> groupList = this.groupService.getGroupList(param, pager); 
		setRequestAttribute("groupList", groupList);
	    setRequestAttribute("pager", pager);
	}
	
	/** 学校排行  */
	private void get_school_list(String iniFilePath){
		//原来的py中先对学校的数据进行了如下的处理
		/*
	    //计算学校的平均分
	    hql = """UPDATE Unit SET aveScore = totalScore/userCount Where userCount>0 """ 
	    Command(hql).update()
	    hql="""UPDATE Unit SET aveScore = 0  Where userCount<=0"""
	    Command(hql).update()
	    */
		
		UnitQueryParam param = new UnitQueryParam();
	    String userOrder = "";
		try {
			userOrder = ConfigurationFileIni.getValue(iniFilePath,"学校排行","userOrder","");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    if(userOrder.equals("unitName")){
	      param.orderType = param.ORDER_NAME_ASC;
	    }else if(userOrder.equals("studioCount")){
	      param.orderType = param.ORDER_studioCount_DESC;
	    }else if(userOrder.equals("articleCount")){
	      param.orderType = param.ORDER_articleCount_DESC;
	    }else if(userOrder.equals("recommendArticleCount")){
	      param.orderType = param.ORDER_recommendArticleCount_DESC;
	    }else if(userOrder.equals("resourceCount")){
	      param.orderType = param.ORDER_resourceCount_DESC;
	    }else if(userOrder.equals("recommendResourceCount")){
	      param.orderType = param.ORDER_recommendResourceCount_DESC;
	    }else if(userOrder.equals("photoCount")){
	      param.orderType = param.ORDER_photoCount_DESC;
	    }else if(userOrder.equals("videoCount")){
	      param.orderType = param.ORDER_videoCount_DESC;
	    }else if(userOrder.equals("totalScore")){
	      param.orderType = param.ORDER_totalScore_DESC;
	    }else if(userOrder.equals("aveScore")){
	      param.orderType = param.ORDER_aveScore_DESC;
	    }else{
	      param.orderType = param.ORDER_aveScore_DESC; //按照平均分排序
	    }
		Pager pager = super.getCurrentPager();
		pager.setItemNameAndUnit("机构", "个");
		pager.setPageSize(30);
		
		List<Unit> unitList = this.unitService.getUnitList(param, pager); 
		setRequestAttribute("unitList", unitList);
	    setRequestAttribute("pager", pager);
	}
	
	public GroupService getGroupService() {
		return groupService;
	}
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	public UnitService getUnitService() {
		return unitService;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public UserStatManage getUserStatManage() {
		return userStatManage;
	}
	public void setUserStatManage(UserStatManage userStatManage) {
		this.userStatManage = userStatManage;
	}
}
