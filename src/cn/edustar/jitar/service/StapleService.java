package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.jitar.pojos.Staple;

/**
 * 用户博文分类服务接口定义. * 
 * @author mengxianhui
 * @deprecated - 不使用了.
 */
public interface StapleService {
	/**
	 * 得到指定标识的用户博文分类。
	 * @param stapeId
	 * @return
	 */
	public Staple getStaple(Integer stapeId);
	
	/**
	 * 添加/创建一个用户博文分类。
	 * @param staple
	 */
	public void addStaple(Staple staple);
	
	/**
	 * ？？什么地方使用？ 获得所有博文分类。
	 * @return
	 */
	public List<Staple> getAll();
	
	/**
	 * 更新/修改一个用户博文分类。
	 * @param staple
	 */
	public void updateStaple(Staple staple);
	
	/**
	 * 删除一个用户博文分类。
	 * @param staple
	 */
	public void deleteStaple(Staple staple);
	
	/**
	 * 判定指定名称的用户博文分类是否存在。
	 * @param stapleName
	 * @return
	 */
	public Boolean stapleNameIsExist(String stapleName);
	
	/**
	 * 得到指定用户的所有用户博文分类信息。
	 * @param userId - 用户标识。
	 * @return - 返回 List&lt;Staple&gt; 分类列表，按照用户指定的顺序。
	 */
	public List<Staple> getUserStapleList(int userId);
}
