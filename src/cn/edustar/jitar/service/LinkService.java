package cn.edustar.jitar.service;

import java.util.List;

import cn.edustar.data.Pager;
import cn.edustar.jitar.model.ObjectType;
import cn.edustar.jitar.pojos.Link;

/***
 * 链接服务.
 * @author Yang XinXin
 * @version 1.0.0 Mar 17, 2008 9:06:49 AM
 *
 */
public interface LinkService {
	/***
	 * 保存一个链接.	 * @param link
	 */
	public void saveLink(Link link);
	
	/***
	 * 所有链接的列表.
	 * @param pager
	 * @return
	 */
	public List<Link> getLinkList(LinkQueryParam param, Pager pager); 
	
	/***
	 * 通过LinkId得到一个链接.	 * @param linkId
	 * @return
	 */
	public Link getLinkById(int linkId);
	
	/***
	 * 删除指定的链接.	 * @param linkId
	 */
	public void deleteLink(Link link);

	/**
	 * 删除指定对象类型和标识的对象的所有友情链接, 一般用于将要删除该对象.
	 * @param objectType
	 * @param objectId
	 */
	public void deleteLinkByObject(ObjectType objectType, int objectId);
}
