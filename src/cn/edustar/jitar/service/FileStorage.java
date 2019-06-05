package cn.edustar.jitar.service;

import java.io.File;
import java.io.IOException;

/**
 * 表示一个用户或群组的文件存储信息，此对象由 StoreManager 创建出来
 * 
 *
 */
public interface FileStorage {
	
	/**
	 * 得到此文件夹的拥有对象，可能是 user 或者是 group
	 * 
	 * @return
	 */
	public Object getOwner();

	/**
	 * 判断一个文件是否存在
	 * 
	 * @param relativePath - 相对于用户存储的数据的根 的相对路径
	 * @return
	 */
	public boolean isFileExist(String relativePath) throws IOException;

	/**
	 * 得到根文件夹位置
	 * 
	 * @return
	 * @throws IOException
	 */
	public File getRootFolder() throws IOException;
	
	/**
	 * 得到指定路径的文件或文件夹对象，其表示为一个规范化的路径
	 * 
	 * @param relativePath - 相对于用户存储的数据的根 的相对路径
	 * @return 返回一个文件对象，如果 == null 表示路径非法；该文件不一定存在，返回的可能是文件也可能是文件夹 getFile(null) 可以用于得到根路径
	 */
	public File getFile(String relativePath) throws IOException;

	/**
	 * 计算文件的相对于用户根的路径
	 * 
	 * @param file
	 * @return 如果文件不在用户目录下则返回 null
	 */
	public String getRelativePath(File file);
	
}
