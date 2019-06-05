package cn.edustar.jitar.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.pojos.UGroup;
import cn.edustar.jitar.pojos.UGroupUser;
import cn.edustar.jitar.pojos.UGroupPower;
import cn.edustar.jitar.pojos.UCondition2;
import cn.edustar.jitar.pojos.UCondition1;
import cn.edustar.jitar.pojos.Article;
import cn.edustar.jitar.pojos.Resource;
import cn.edustar.jitar.service.UserPowerService;
import cn.edustar.jitar.service.ArticleService;
import cn.edustar.jitar.service.ResourceService;
import cn.edustar.jitar.dao.UGroupUserDao;
import cn.edustar.jitar.dao.UGroupPowerDao;
import cn.edustar.jitar.dao.UGroupDao;
import cn.edustar.jitar.dao.UCondition1Dao;
import cn.edustar.jitar.dao.UCondition2Dao;

public class UserPowerServiceImpl implements UserPowerService {
	private UGroupUserDao uGroupUserDao;
	private UGroupPowerDao uGroupPowerDao;
	private UGroupDao uGroupDao;
	private UCondition1Dao uCondition1Dao;
	private UCondition2Dao uCondition2Dao;
	private ArticleService articleService;
	private ResourceService resourceService;
	
	public void setArticleService(ArticleService articleService){
		this.articleService=articleService;
	}
	public void setResourceService(ResourceService resourceService){
		this.resourceService=resourceService;
	}
	public void setUGroupUserDao(UGroupUserDao uGroupUserDao){
		this.uGroupUserDao=uGroupUserDao;
	}
	public void setUGroupPowerDao(UGroupPowerDao uGroupPowerDao){
		this.uGroupPowerDao=uGroupPowerDao;
	}
	public void setUGroupDao(UGroupDao uGroupDao){
		this.uGroupDao=uGroupDao;
	}
	public void setUCondition1Dao(UCondition1Dao uCondition1Dao){
		this.uCondition1Dao=uCondition1Dao;
	}
	public void setUCondition2Dao(UCondition2Dao uCondition2Dao){
		this.uCondition2Dao=uCondition2Dao;
	}
	
	/**
	 * 今天是否允许上载文章
	 * @param userId
	 * @return
	 */
	public boolean AllowUploadArticle(int userId){
		int num=getUploadArticleNum(userId);
		if(num==-1){
			return true;
		}
		if(num==0){
			return false;
		}
		int todayNum=getTodayArticles(userId);
		return (num>todayNum);
	}
	public boolean AllowUploadArticle(int userId,int maxNum){
		int num=maxNum;
		if(num==-1){
			return true;
		}
		if(num==0){
			return false;
		}
		int todayNum=getTodayArticles(userId);
		return (num>todayNum);
	}	
	public boolean AllowUploadArticle(int userId,int maxNum,int todayNum){
		int num=maxNum;
		if(num==-1){
			return true;
		}
		if(num==0){
			return false;
		}
		return (num>todayNum);
	}		
	/**
	 * 今天发表的文章数量
	 * @param userId
	 * @return
	 */
	public int getTodayArticles(int userId){
		List<Article> list=this.articleService.getTodayArticles(userId);
		if(list==null){
			return 0;
		}
		else{
			return list.size();
		}
	}
	
	/**
	 * 是否允许创建视频会议
	 */
	public boolean AllowVideoConferenceCreate(int userId){
		Integer[] groupId =getUserPowerGroup(userId);
		if(groupId==null){
			return false;
		}else{
			return uGroupPowerDao.getAllowVideoConferenceCreate(groupId);
		}
	}
	/**
	 * 今天是否允许上载资源
	 * @param userId
	 * @return
	 */
	public boolean AllowUploadResource(int userId){
		int num=getUploadResourceNum(userId);
		if(num==-1){
			return true;
		}
		if(num==0){
			return false;
		}
		int todayNum=getTodayResources(userId);
		return (num>todayNum);
	}
	public boolean AllowUploadResource(int userId,int maxNum){
		int num=maxNum;
		if(num==-1){
			return true;
		}
		if(num==0){
			return false;
		}
		int todayNum=getTodayResources(userId);
		return (num>todayNum);
	}	
	public boolean AllowUploadResource(int userId,int maxNum,int todayNum){
		int num=maxNum;
		if(num==-1){
			return true;
		}
		if(num==0){
			return false;
		}
		return (num>todayNum);
	}	
	/**
	 * 今天上载的资源数量
	 * @param userId
	 * @return
	 */
	public int getTodayResources(int userId){
		List<Resource> list=this.resourceService.getTodayResources(userId);
		if(list==null){
			return 0;
		}
		else{
			return list.size();
		}
	}
	
	//得到用户手工设置的特殊组
	private Integer[] getGroupsByManage(int userId){
		List<UGroupUser> list= this.uGroupUserDao.getUGroupUserByUserId(userId,1);
		if(list==null) return null;
		if(list.size()==0) return null;
		Integer[] groups=new Integer[list.size()];
		for(int i=0;i<list.size();i++){
			groups[i]=list.get(i).getGroupId();
		}
		return groups;
	}
	
	//根据条件得到用户的组(不再使用UGroupUser)
	private Integer[] getGroupsByCondition(int userId){
		ArrayList<Integer> al=new ArrayList<Integer>(); 
		List<UCondition1> list1=getUCondition1();
		if(list1!=null){
			for(int i=0;i<list1.size();i++){
				UCondition1 condition1=list1.get(i);
				int score1=condition1.getScore1();
				int score2=condition1.getScore2();
				int groupId=condition1.getGroupId();
				
				UGroup group=this.uGroupDao.getUGroup(groupId);
				if(group!=null){
					if(this.uGroupUserDao.isExistUser1(userId,group, score1, score2,condition1.getConditionType())){
						//System.out.println("符合条件1:groupId="+groupId+"  score1="+score1+"  score2="+score2+"  condition1.getConditionType="+condition1.getConditionType());
						if(!al.contains(group.getGroupId())){
							al.add(group.getGroupId());
						}
					}
				}
			}
		}
		
		//执行Condition2
		List<UCondition2> list2=getUCondition2();
		if(list2!=null){
			for(int i=0;i<list2.size();i++){
				UCondition2 condition2=list2.get(i);
				String hql=condition2.getSqlCondition();
				int groupId=condition2.getGroupId();
				UGroup group=this.uGroupDao.getUGroup(groupId);
				if(group!=null){
					if(hql.indexOf(" User ")>0){
						List<User> users=this.uGroupUserDao.getUser(hql);
						for(int j=0;j<users.size();j++){
							User user=users.get(j);
							if(user.getUserId()==userId){
								//System.out.println("符合条件2:groupId="+groupId+"  hql="+hql);
								if(!al.contains(group.getGroupId())){
									al.add(group.getGroupId());
								}
							}
						}
					}					
				}
			}
		}
		
		if(al.size()==0){
			return null;
		}
		else{
			Integer[] groups=new Integer[al.size()];
			for(int i=0;i<al.size();i++){
				groups[i]=al.get(i);
			}
			return groups;
		}
	}
	
	private Integer[] getUserPowerGroup(int userId){
		Integer[] groupId =getGroupsByCondition(userId);
		Integer[] groupIdmanaged= getGroupsByManage(userId);
		Integer[] myGroup=null;
		if((groupId==null) && (groupIdmanaged==null)){
			return null;
		}
		
		if((groupId!=null) && (groupIdmanaged!=null)){
			myGroup=new Integer[groupId.length+groupIdmanaged.length]; 
		}
		else if(groupId!=null){
			myGroup=new Integer[groupId.length];
		}
		else{
			myGroup=new Integer[groupIdmanaged.length];
		}
		int iNum=0;
		if(groupId!=null){
			for(int i=0;i<groupId.length;i++){
				myGroup[i]=groupId[i];
			}
			iNum=groupId.length;
		}
		if(groupIdmanaged!=null){
			for(int i=0;i<groupIdmanaged.length;i++){
				myGroup[i+iNum]=groupIdmanaged[i];
			}
		}
		//System.out.println("userId="+userId);
		//if(groupId!=null){
		//	System.out.println("改用户符合条件1和2");
		//}
		//if(groupIdmanaged!=null){
		//	System.out.println("用户是特殊用户");
		//}
		//System.out.println("改用户属于下列组:");
		//for(int i=0;i<myGroup.length;i++){
		//	System.out.println("groupid="+myGroup[i]);
		//}
		return myGroup;
	}
	
	/**
	 * 得到用户每天上载文章的数量限制
	 */
	public int getUploadArticleNum(int userId){
		//initGroupUser(userId);	//先初始化用户权限表
		//List<UGroupUser> groupUsers=this.uGroupUserDao.getUGroupUserByUserId(userId);
		//if(groupUsers==null){
		//	return 0;
		//}
		//Integer[] groupId=this.uGroupUserDao.getGroupIdByUserId(userId);
		
		Integer[] groupId =getUserPowerGroup(userId);
		if(groupId==null){
			return 0;
		}else{
			return uGroupPowerDao.getMaxUploadArticleNum(groupId);
		}
		
	}
	/**
	 * 得到用户每天上载资源的数量限制
	 */
	public int getUploadResourceNum(int userId){
		//initGroupUser(userId);	//先初始化用户权限表
		//List<UGroupUser> groupUsers=this.uGroupUserDao.getUGroupUserByUserId(userId);
		//if(groupUsers==null){
		//	return 0;
		//}
		//Integer[] groupId=this.uGroupUserDao.getGroupIdByUserId(userId);
		
		Integer[] groupId =getUserPowerGroup(userId);
		if(groupId==null){
			return 0;
		}else{
			return uGroupPowerDao.getMaxUploadResourceNum(groupId);
		}
	}
	/**
	 * 得到用户上载空间的数量限制
	 */
	public int getUploadDiskNum(int userId,boolean initgroupuser){
		//if(initgroupuser){
			//initGroupUser(userId);	//先初始化用户权限表
		//}
		//List<UGroupUser> groupUsers=this.uGroupUserDao.getUGroupUserByUserId(userId);
		//if(groupUsers==null){
		//	return 0;
		//}
		//Integer[] groupId=this.uGroupUserDao.getGroupIdByUserId(userId);
		Integer[] groupId = getUserPowerGroup(userId);
		if(groupId==null || groupId.length < 1){
			return 0;
		}else{
			return uGroupPowerDao.getMaxUploadDiskNum(groupId);
		}
	}
	
	public UGroupPower getUGroupPower(int groupId){
		return uGroupPowerDao.getUGroupPower(groupId);
	}
	public void SaveUGroupPower(UGroupPower uGroupPower){
		uGroupPowerDao.Save(uGroupPower);
	}
	
	/**
	 * 更新用户组(根据条件向用户组中更新用户),不使用
	 */
	public void initGroupUser(int userId){
		//先执行Condition1
		this.uGroupUserDao.DeleteUser2(userId); 
		List<UCondition1> list1=getUCondition1();
		if(list1!=null){
			for(int i=0;i<list1.size();i++){
				UCondition1 condition1=list1.get(i);
				int score1=condition1.getScore1();
				int score2=condition1.getScore2();
				int groupId=condition1.getGroupId();
				UGroup group=this.uGroupDao.getUGroup(groupId);
				if(group!=null){
					this.uGroupUserDao.InSertUser1(group, score1, score2,condition1.getConditionType());
				}
			}
		}
		
		//执行Condition2
		List<UCondition2> list2=getUCondition2();
		if(list2!=null){
			for(int i=0;i<list2.size();i++){
				UCondition2 condition2=list2.get(i);
				String hql=condition2.getSqlCondition();
				int groupId=condition2.getGroupId();
				UGroup group=this.uGroupDao.getUGroup(groupId);
				if(group!=null){
					if(hql.indexOf(" User ")>0){
						List<User> users=this.uGroupUserDao.getUser(hql);
						for(int j=0;j<users.size();j++){
							User user=users.get(j);
							UGroupUser ogu=this.uGroupUserDao.Find(groupId, user.getUserId());
							if(ogu==null){
								UGroupUser uGroupUser=new UGroupUser();
								uGroupUser.setGroupId(groupId);
								uGroupUser.setUserId(user.getUserId());
								uGroupUser.setManaged(0);
								this.uGroupUserDao.Save(uGroupUser);
							}
						}
					}					
				}
			}
		}
	
		//执行其他条件
		
	}
	
	public List<UCondition1> getUCondition1(){
		return this.uCondition1Dao.getUCondition1();
	}
	
	public List<UCondition1> getUCondition1(Integer score1,Integer score2,Integer conditionType){
		return this.uCondition1Dao.getUCondition1(score1,score2,conditionType);
	}
	
	public void SaveUCondition1(List<UCondition1> list){
		this.uCondition1Dao.Save(list);
	}
	
	public List<UCondition2> getUCondition2(){
		return this.uCondition2Dao.getUCondition2();
	}
	
	public UCondition2 getUCondition2(String teacherTypeKeyword){
		return this.uCondition2Dao.getUCondition2(teacherTypeKeyword);
	}
	
	public void SaveUCondition2(UCondition2 uCondition2){
		this.uCondition2Dao.Save(uCondition2);
	}
	
	public UGroup getUGroup(int groupId){
		return this.uGroupDao.getUGroup(groupId);
	}
	public List<UGroup> getUGroups(){
		return this.uGroupDao.getUGroups();
	}
	
	public void DeleteGroup(UGroup uGroup){
		//先删除组其他地方的引用
		this.uGroupUserDao.Delete(uGroup.getGroupId());
		this.uGroupPowerDao.Delete(uGroup.getGroupId());
		//删除组
		this.uGroupDao.Delete(uGroup);
	}
	
	public List<UGroupUser> getUGroupUser(){
		return this.uGroupUserDao.getUGroupUser();
	}
	public List<UGroupUser> getUGroupUser(int groupId,int managed){
		return this.uGroupUserDao.getUGroupUser(groupId,managed);
	}
	public List<UGroupUser> getUGroupUserByGroupId(int groupId){
		return this.uGroupUserDao.getUGroupUserByGroupId(groupId);
	}
	public List<UGroupUser> getUGroupUserByUserId(int userId){
		return this.uGroupUserDao.getUGroupUserByUserId(userId);
	}
	public Integer[] getGroupIdByUserId(int userId){
		return this.uGroupUserDao.getGroupIdByUserId(userId);
	}
	
	/**
	 * 删除特定的组用户
	 */
	public void DeleteUGroupUser(int managed){
		this.uGroupUserDao.DeleteGroupUser(managed);
	}
	
	/**
	 * 删除组用户
	 */
	public void DeleteUGroupUser(UGroupUser uGroupUser){
		//删除组用户需要按照条件检查和手工设置检查
		if(uGroupUser==null) return;
		if(uGroupUser.getManaged()==1){	//如果是删除手工设置的用户
			int groupId=uGroupUser.getGroupId();
			int userId=uGroupUser.getUserId();
			boolean b1=this.uCondition1Dao.FindUser(groupId, userId);
			if(b1){
				//条件1中还存在该用户,需要把原来的用户状态由手工设置为条件自动
				uGroupUser.setManaged(0);
				SaveUGroupUser(uGroupUser);
				return;
			}
			boolean b2=this.uCondition2Dao.FindUser(groupId, userId);
			if(b2){
				//条件2中还存在该用户,不允许删除
				uGroupUser.setManaged(0);
				SaveUGroupUser(uGroupUser);
				return;
			}	
			this.uGroupUserDao.Delete(uGroupUser);
			return;
		}
		else
		{
			//如果根据普通条件的删除组用户,则手工已经设置了则不允许删除
			//检查条件中是否还允许该用户存在
			int groupId=uGroupUser.getGroupId();
			int userId=uGroupUser.getUserId();
			boolean b1=this.uCondition1Dao.FindUser(groupId, userId);
			if(b1){
				//条件1中还存在该用户,不允许删除
				return;
			}
			boolean b2=this.uCondition2Dao.FindUser(groupId, userId);
			if(b2){
				//条件2中还存在该用户,不允许删除
				return;
			}
			UGroupUser groupuser=this.uGroupUserDao.getGroupUserManaged(groupId,userId);
			if(groupuser==null){//手工已经设置了则不允许删除,否则可以删除
				this.uGroupUserDao.Delete(uGroupUser);
			}
		}
	}
	
	public void SaveUGroupUser(UGroupUser uGroupUser){
		this.uGroupUserDao.Save(uGroupUser);
	}
	public UGroupUser FinduGroupUser(int groupId,int userId){
		return this.uGroupUserDao.Find(groupId, userId);
	}
	
}
