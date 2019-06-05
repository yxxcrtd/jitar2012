package cn.edustar.jitar.dao;

import java.util.List;
import java.util.Map;

import cn.edustar.jitar.pojos.Staple;

/**
 * 用户博文分类数据访问接口定义 * 
 * @author mengxianhui
 * @deprecated - 不使用了.
 */
public interface StapleDao {
	/**
	 * 
	 * @param stapleId
	 * @return
	 */
	public Staple getStaple(Integer stapleId);
	
	/**
	 * 
	 * @return
	 */
	public List<Staple> getAll();
	
	/**
	 * 
	 * @param staple
	 */
	public void addStaple(Staple staple);
	
	/**
	 * 
	 * @param staple
	 */
	public void updateStaple(Staple staple);
	
	/**
	 * 
	 * @param staple
	 */
	public void deleteStaple(Staple staple);
	
	/**
	 * 判定指定的用户博文分类是否存在。
	 * ?? 应该加上用户标识限定参数才对。
	 * @param stapleName
	 * @return
	 */
	public boolean stapleNameIsExist(String stapleName);
	
	/**
	 * 得到指定的一组分类标识的对应的分类名称。
	 * @param stapleIds
	 * @return 返回为 Map&lt;Integer, String&gt;, 其中 Integer Key 为分类表示，String Value 为分类名称。
	 */
	public Map<Integer, String> getStapleNames(List<Integer> stapleIds);
	
	/**
	 * 得到指定用户的所有用户博文分类信息。
	 * @param userId - 用户标识。
	 * @return - 返回 List&lt;Staple&gt; 分类列表，按照用户指定的顺序。
	 */
	public List<Staple> getUserStapleList(int userId);
}
