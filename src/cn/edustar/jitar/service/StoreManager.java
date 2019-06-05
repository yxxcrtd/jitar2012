package cn.edustar.jitar.service;

import java.io.IOException;

import cn.edustar.jitar.pojos.User;

/**
 * 定义为每用户、群组提供文件存储的服务。存储分为用户私有 private 存储和系统为每用户的存储 system 存储的内容不能被直接访问
 *    
 *
 */
public interface StoreManager {
	
	/**
	 * 得到指定用户的文件存储服务对象
	 * 
	 * @param user
	 * @return
	 * @throws IOException 
	 */
	public FileStorage getUserFileStorage(User user) throws IOException;
	public void DeleteUserDir(User user) throws IOException;
}
