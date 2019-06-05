package cn.edustar.push;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author 孟宪会
 *
 */
public class MashupServiceImpl implements MashupService {

	private static final Logger log = LoggerFactory.getLogger(MashupServiceImpl.class);
	private MashupDao mashupDao;

	public void setMashupDao(MashupDao mashupDao) {
		this.mashupDao = mashupDao;
	}
	
	/** 加载 Mashup 内容 */
	public MashupContent getMashupContent(int mashupContentId)
	{
		return this.mashupDao.getMashupContent(mashupContentId);
	}

	/** 保存 Mashup 内容 */
	public void saveMashupContent(MashupContent mashupContent) {
		this.mashupDao.saveMashupContent(mashupContent);
	}

	/** 删除 Mashup 内容 */
	public void deleteMashupContent(MashupContent mashupContent) {
		this.mashupDao.deleteMashupContent(mashupContent);
	}

	/** 更新 Mashup 内容 */
	public void updateMashupContent(MashupContent mashupContent) {
		this.mashupDao.updateMashupContent(mashupContent);
	}

	/** 审核 */
	public void approveMashupContent(MashupContent mashupContent) {
		mashupContent.setMashupContentState(1);
		this.mashupDao.updateMashupContent(mashupContent);
	}

	/** 取消审核 */
	public void unApproveMashupContent(MashupContent mashupContent) {
		mashupContent.setMashupContentState(0);
		this.mashupDao.updateMashupContent(mashupContent);
	}
	
	/** 检查所推送的资源是否已经存在 */
	public boolean mashupContentIsExist(MashupContent mashupContent)
	{
		return this.mashupDao.mashupContentIsExist(mashupContent);
	}

	/** 根据 xml 创建一个 MashupContent */
	public MashupContent createMashupContentFromXml(String xml) {
		try {
			MashupContent mashupContent = new MashupContent();
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			Node node;
			node = root.selectSingleNode("/root/title");
			mashupContent.setTitle(safeGetString(node));
			
			node = root.selectSingleNode("/root/documentType");
			mashupContent.setDocumentType(safeGetString(node));
			
			node = root.selectSingleNode("/root/href");
			mashupContent.setHref(safeGetString(node));
			
			node = root.selectSingleNode("/root/author");
			mashupContent.setAuthor(safeGetString(node));
			
			node = root.selectSingleNode("/root/unitName");
			mashupContent.setUnitName(safeGetString(node));
			
			node = root.selectSingleNode("/root/unitTitle");
			mashupContent.setUnitTitle(safeGetString(node));
			
			node = root.selectSingleNode("/root/platformGuid");
			mashupContent.setPlatformGuid(safeGetString(node));
			
			node = root.selectSingleNode("/root/platformName");
			mashupContent.setPlatformName(safeGetString(node));			
			
			node = root.selectSingleNode("/root/orginId");
			mashupContent.setOrginId(Integer.valueOf(safeGetString(node)));
			
			node = root.selectSingleNode("/root/pushUserName");
			mashupContent.setPushUserName(safeGetString(node));
			
			node = null;
			
			mashupContent.setMashupContentState(1);		
			return mashupContent;
			
		} catch (Exception e) {
			log.info("错误:" + e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/** 添加一个登录用户 */
	public void saveOrUpdateMashupUser(MashupUser mashupUser)
	{
		this.mashupDao.saveOrUpdateMashupUser(mashupUser);
	}
	
	/** 加载一个登录用户 */
	public MashupUser getMashupUserByGuid(String userGuid)
	{
		return this.mashupDao.getMashupUserByGuid(userGuid);
	}
	
	/** 删除所有登录过期的用户 */
	public void deleteAllInValidMashupUser()
	{
		this.mashupDao.deleteAllInValidMashupUser();
	}
	
	
	/** 得到机构和域名 */
	public MashupPlatform getMashupPlatformByGuid(String platformGuid)
	{
		return this.mashupDao.getMashupPlatformByGuid(platformGuid);
	}
	
	/** 得到机构和域名 */
	public MashupPlatform getMashupPlatformById(int mashupPlatformId){
		return this.mashupDao.getMashupPlatformById(mashupPlatformId);
	}
	
	/** 得到所有的机构和域名 */
	public List<MashupPlatform> getAllMashupPlatform(Boolean isApproved)
	{
		return this.mashupDao.getAllMashupPlatform(isApproved);
	}
	
	/** 更新机构信息 */
	public void saveOrUpdateMashupPlatform(MashupPlatform mashupPlatform){
		this.mashupDao.saveOrUpdateMashupPlatform(mashupPlatform);
	}
	
	/** 删除机构信息 */
	public void deleteMashupPlatform(MashupPlatform mashupPlatform){
		this.mashupDao.deleteMashupPlatform(mashupPlatform);
	}
	
	/** 更新机构的信息 */
	public void updateMashupContentByPlatform(MashupPlatform mashupPlatform)
	{
		this.mashupDao.updateMashupContentByPlatform(mashupPlatform);
	}
	
	/** 更新原来文章的推送状态 0:未设置，1:已经推送，2:待推送 */
	public void setOrginObjectPushed(String documentType,int pushState)
	{
		this.mashupDao.setOrginObjectPushed(documentType, pushState);
	}
	
	/** 根据提交的数据构造 createMashupBlogGroupFromXml  */
	public MashupBlogGroup createMashupBlogGroupFromXml(String xml)
	{
		try {
			MashupBlogGroup mashupBlogGroup = new MashupBlogGroup();
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			Node node;
			node = root.selectSingleNode("/root/orginId");
			mashupBlogGroup.setOrginId(Integer.valueOf(safeGetString(node)));
			
			node = root.selectSingleNode("/root/contentType");
			mashupBlogGroup.setContentType(safeGetString(node));
			
			node = root.selectSingleNode("/root/href");
			mashupBlogGroup.setHref(safeGetString(node));
						
			node = root.selectSingleNode("/root/trueName");
			mashupBlogGroup.setTrueName(safeGetString(node));
			
			node = root.selectSingleNode("/root/description");
			mashupBlogGroup.setDescription(safeGetString(node));
			 
			node = root.selectSingleNode("/root/icon");
			mashupBlogGroup.setIcon(safeGetString(node));
			
			node = root.selectSingleNode("/root/subjectName");
			mashupBlogGroup.setSubjectName(safeGetString(node));
			
			node = root.selectSingleNode("/root/gradeName");
			mashupBlogGroup.setGradeName(safeGetString(node));
			
			node = root.selectSingleNode("/root/unitName");
			mashupBlogGroup.setUnitName(safeGetString(node));
			
			node = root.selectSingleNode("/root/unitTitle");
			mashupBlogGroup.setUnitTitle(safeGetString(node));
			
			node = root.selectSingleNode("/root/unitId");
			mashupBlogGroup.setUnitId(Integer.valueOf(safeGetString(node)));
			
			node = root.selectSingleNode("/root/gradeId");
			mashupBlogGroup.setGradeId(Integer.valueOf(safeGetString(node)));
			
			node = root.selectSingleNode("/root/metaSubjectId");
			mashupBlogGroup.setMetaSubjectId(Integer.valueOf(safeGetString(node)));
							
			node = root.selectSingleNode("/root/platformGuid");
			mashupBlogGroup.setPlatformGuid(safeGetString(node));
			
			node = root.selectSingleNode("/root/platformName");
			mashupBlogGroup.setPlatformName(safeGetString(node));		
			
			node = root.selectSingleNode("/root/pushUserName");
			mashupBlogGroup.setPushUserName(safeGetString(node));
			
			node = null;
			
			mashupBlogGroup.setMashupBlogGroupState(1);		
			return mashupBlogGroup;
			
		} catch (Exception e) {
			log.info("错误:" + e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/** 检查资源是否已经存在 */
	public boolean mashupBlogGroupIsExist(MashupBlogGroup mashupBlogGroup)
	{
		return this.mashupDao.mashupBlogGroupIsExist(mashupBlogGroup);
	}
	
	/** 保存对象 */
	public void saveMashupBlogGroup(MashupBlogGroup mashupBlogGroup)
	{
		this.mashupDao.saveMashupBlogGroup(mashupBlogGroup);
	}
	
	/** 更新对象 */
	public void updateMashupBlogGroup(MashupBlogGroup mashupBlogGroup)
	{
		this.mashupDao.updateMashupBlogGroup(mashupBlogGroup);
	}
	
	/** 加载对象 */
	public MashupBlogGroup getMashupBlogGroupById(int mashupBlogGroupId)
	{
		return this.mashupDao.getMashupBlogGroupById(mashupBlogGroupId);
	}
	
	/** 删除对象 */
	public void deleteMashupBlogGroup(MashupBlogGroup mashupBlogGroup){
		this.mashupDao.deleteMashupBlogGroup(mashupBlogGroup);
	}
	
	private String safeGetString(Node node)
	{
		if(null==node)
		{
			return "";
		}
		else
		{
			return node.getText();
		}
	}
	
}
