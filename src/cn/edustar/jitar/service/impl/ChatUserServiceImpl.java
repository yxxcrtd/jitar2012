package cn.edustar.jitar.service.impl;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.edustar.jitar.JitarContext;
import cn.edustar.jitar.dao.ChatUserDao;
import cn.edustar.jitar.pojos.ChatUser;
import cn.edustar.jitar.service.CacheService;
import cn.edustar.jitar.service.ChatUserService;

/**
 * 聊天室里的用户放到缓存中
 * 
 * @author baimindong
 *
 */
@SuppressWarnings("unchecked")
public class ChatUserServiceImpl  implements ChatUserService{
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(ChatUserServiceImpl.class);

	/** 缓存服务。 */
	private CacheService cache_svc;
	
	/** 数据访问对象 */
	private ChatUserDao chatUserDao;
	
	
	/** 数据访问对象. */
	public void setChatUserDao(ChatUserDao chatUserDao) {
		this.chatUserDao = chatUserDao;
	}
	
	public ChatUserServiceImpl()
	{
		
	}

	/** 缓存服务。 */
	public void setCacheService(CacheService cache_svc) {
		this.cache_svc = cache_svc;
	}
	
	public ChatUser getChatUser(int id)
	{
		return chatUserDao.getChatUser(id);
	}
	
	public List<ChatUser> getCacheChatUsers(int roomId){
		String key="ChatUserRoom"+roomId;
		List<ChatUser> chatusers = (List<ChatUser>)this.cache_svc.get(key);
		if (chatusers == null)
		{
			chatusers=getChatUsers(roomId);
			this.cache_svc.put("ChatUserRoom"+roomId,chatusers);
		}
		return chatusers;
	}
	public ChatUser getCacheChatUser(int roomId,int userId){
		String key="ChatUserRoom"+roomId;
		List<ChatUser> chatusers = (List<ChatUser>)this.cache_svc.get(key);
		if (chatusers == null)
		{
			return chatUserDao.getChatUser(roomId, userId);
		}
		else
		{
			for(int i=0;i<chatusers.size();i++){
				ChatUser cu=chatusers.get(i);
				if(cu.getUserId()==userId){
					return cu;
				}
			}
			return chatUserDao.getChatUser(roomId, userId);
		}
	}
	public ChatUser getChatUser(int roomId,int userId)
	{
		return chatUserDao.getChatUser(roomId, userId);
	}
	public List<ChatUser> getChatUsers(int roomId)
	{
		return chatUserDao.getChatUsers(roomId);
	}
	public int updateChatUserIsSay(int roomId,int userId, boolean isSay)
	{
		return chatUserDao.updateChatUserIsSay(roomId, userId, isSay);
	}
	public int updateChatUserIsLeave(int roomId,int userId, boolean isLeave)
	{
		return chatUserDao.updateChatUserIsLeave(roomId, userId, isLeave);
	}
	public void updateChatUserCurrentDate(int roomId,int userId)
	{
		//chatUserDao.updateChatUserCurrentDate(roomId, userId);
		String key="ChatUserRoom"+roomId;
		List<ChatUser> chatusers = (List<ChatUser>)this.cache_svc.get(key);
		if(chatusers!=null)
		{
		for(int i=0;i<chatusers.size();i++){
			ChatUser cu=chatusers.get(i);
			if(cu.getUserId()==userId){
				cu.setactTime(new Date());
				cu.setIsLeave(false);
				cu.setIsActived(true);
			}
			else{
				if(!cu.getIsLeave()){
					Date d=cu.getactTime();
					Date dd=new Date();
					if(d!=null)
					{
						long between=(dd.getTime()-d.getTime())/1000;	//除以1000是为了转换成秒
						if(between>120){
							cu.setIsLeave(true);
							cu.setIsActived(false);
							chatUserDao.updateChatUserIsLeave(roomId, userId, true);
						}
					}
				}
			}
		}
	  }
	}
	public void saveChatUser(ChatUser chatUser)
	{
		//聊天室用户保存到数据库
		chatUserDao.saveChatUser(chatUser);
		
		//聊天室用户增加到缓存中
		String key="ChatUserRoom"+chatUser.getRoomId();
		List<ChatUser> chatusers = (List<ChatUser>)this.cache_svc.get(key);
		if (chatusers != null){
			chatusers.add(chatUser);
		}
	}
	public void saveChatUserFontColor(int roomId,int userId,String fontColor)
	{
		chatUserDao.saveChatUserFontColor(roomId,userId,fontColor);
	}
	public List getFaceList()
	{
		List list=new ArrayList();
      //File dir = new File("c:\\java\\");
		String path=JitarContext.getCurrentJitarContext().getServletContext().getRealPath("/");
		String imgPath=path+"images\\face\\";
		//log.info("----------------------------------"+imgPath);
		File dir = new File(imgPath);
      File file[] = dir.listFiles();
      for (int i = 0; i < file.length; i++) {
      	String sFile=file[i].getName();
      	//log.info(sFile);
      	if(sFile.endsWith(".gif") || sFile.endsWith(".jpg"))
      	{
      		list.add("images/face/"+sFile);
      		//log.info("images/face/"+sFile);
      	}
      }
		return list;
		
	}
}
