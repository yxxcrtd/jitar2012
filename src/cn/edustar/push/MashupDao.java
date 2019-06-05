package cn.edustar.push;

import java.util.List;

/**
 * @author 孟宪会
 *
 */
public interface MashupDao {

	/** 保存 Mashup 内容 */
	public void saveMashupContent(MashupContent mashupContent);
	
	/** 加载 Mashup 内容 */
	public MashupContent getMashupContent(int mashupContentId);
	
	/** 删除 Mashup 内容 */
	public void deleteMashupContent(MashupContent mashupContent);
	
	/** 更新 Mashup 内容 */
	public void updateMashupContent(MashupContent mashupContent);
	
	/** 检查所推送的资源是否已经存在 */
	public boolean mashupContentIsExist(MashupContent mashupContent);

	/** 添加一个登录用户 */
	public void saveOrUpdateMashupUser(MashupUser mashupUser);
	
	/** 加载一个登录用户 */
	public MashupUser getMashupUserByGuid(String userGuid);
	
	/** 删除所有登录过期的用户 */
	public void deleteAllInValidMashupUser();
	
	/** 得到所有的机构和域名 */
	public List<MashupPlatform> getAllMashupPlatform(Boolean isApproved);
	
	/** 得到机构和域名 */
	public MashupPlatform getMashupPlatformByGuid(String platformGuid);
	
	/** 得到机构和域名 */
	public MashupPlatform getMashupPlatformById(int mashupPlatformId);
	
	/** 更新机构信息 */
	public void saveOrUpdateMashupPlatform(MashupPlatform mashupPlatform);
	
	/** 删除机构信息 */
	public void deleteMashupPlatform(MashupPlatform mashupPlatform);
	
	/** 更新机构的信息 */
	public void updateMashupContentByPlatform(MashupPlatform mashupPlatform);
	
	/** 更新原来文章的推送状态 0:未设置，1:已经推送，2:待推送 */
	public void setOrginObjectPushed(String documentType,int pushState);

	/** 检查资源是否已经存在 */
	public boolean mashupBlogGroupIsExist(MashupBlogGroup mashupBlogGroup);
	
	/** 保存对象 */
	public void saveMashupBlogGroup(MashupBlogGroup mashupBlogGroup);
	
	/** 更新对象 */
	public void updateMashupBlogGroup(MashupBlogGroup mashupBlogGroup);
	
	/** 加载对象 */
	public MashupBlogGroup getMashupBlogGroupById(int mashupBlogGroupId);
	
	/** 删除对象 */
	public void deleteMashupBlogGroup(MashupBlogGroup mashupBlogGroup);
	
	}
