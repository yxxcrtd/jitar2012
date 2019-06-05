package cn.edustar.jitar.service;

import cn.edustar.jitar.model.ThemeModel;

/**
 * 模板方案服务接口定义。
 * 
 *
 *
 */
public interface ThemeService {
	/**
	 * 获得为产生用户个人网站页面使用的缺省模板方案。
	 * 个人网站缺省模板方案存放在 '/WEB-INF/user/default/' 下面。
	 * 
	 * @return
	 */
	public ThemeModel getUserDefaultTheme();
	
	/**
	 * 清除所有缓存的 theme，可能用户修改之后刷新显示使用。
	 */
	public void clearCache();
}
