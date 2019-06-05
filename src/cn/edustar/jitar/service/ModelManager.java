package cn.edustar.jitar.service;

import java.util.List;

/**
 * 教研系统中使用的共享的数据模型的管理器，数据模型用在 struts,freemarker 模板中
 * 
 * 方式一: struts action 根据 result 调用到 freemarker, 其中一些全局共享数据模型
 *   可以被模板方便的访问，如 $SiteUrl; 这些数据模型将放在 servlet context 里面
 *   
 * 方式二: freemarker template processor (模板合成器) 在合成模板的时候也用一部分
 *   共享的数据模型，其原来被放在 TemplateProcessor 里面; 当前都放在这里以方便统一
 *   管理 
 * 
 *
 *
 */
public interface ModelManager {
	/**
	 * 得到共享模型对象集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSharedModels();
}
