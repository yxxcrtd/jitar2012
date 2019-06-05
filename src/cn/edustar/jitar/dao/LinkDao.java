package cn.edustar.jitar.dao;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.pojos.Link;
import cn.edustar.jitar.service.LinkQueryParam;

/**
 * 用于链接的数据访问接口定义.
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 */
public interface LinkDao { 
	/***
	 * 保存一个链接.	 * @param link
	 */
	public void saveLink(Link link);

	/**
	 * 得到指定条件下的链接列表.
	 * @param param
	 * @param pager
	 * @return
	 */
	public List<Link> getLinkList(LinkQueryParam param, Pager pager); 
	
	/**
	 * 通过标识linkId得到指定链接.
	 * @param linkId
	 * @return
	 */
	public Link getLinkById(int linkId);

	/***
	 * 删除链接.
	 * @param link
	 */
	public void deleteLink(Link link);


	/**
	 * 删除指定对象类型和标识的对象的所有友情链接, 一般用于将要删除该对象.
	 * @param objectType
	 * @param objectId
	 */
	public void deleteLinkByObject(int objectType, int objectId);
}