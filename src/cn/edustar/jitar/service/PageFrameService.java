package cn.edustar.jitar.service;

import java.io.IOException;
import java.io.Writer;

/**
 * 
 * 为组件提供框架页面服务
 * 
 * @author 孟宪会
 * 
 */
public interface PageFrameService {

	/**
	 * 提供页面框架服务
	 * 
	 * @param objectGuid
	 * @param objectType
	 * @return
	 */
	public String getFramePage(String objectGuid, String objectType);

	/**
	 * 进行模板的转换
	 * 
	 * @param root_map
	 * @param template_name
	 * @return
	 */
	public String transformTemplate(Object root_map, String template_name);

	/**
	 * 实际进行的转换方法
	 * 
	 * @param root_map
	 * @param writer
	 * @param template_name
	 * @param encoding
	 * @throws IOException
	 */
	public void processTemplate(Object root_map, Writer writer, String template_name, String encoding) throws IOException;

	/**
	 * 辅助函数，给py页面提供网站根目录路径。
	 * 
	 * @return
	 */
	public String getSiteUrl();

	/**
	 * 辅助函数，给py页面提供统一用户的地址信息。
	 * @return
	 */
	public String getUserMgrUrl();
	
	/**
	 * 辅助函数，给py页面提供统一用户的地址信息。
	 * @return
	 */
	public String getUserMgrClientUrl();	
	
}
