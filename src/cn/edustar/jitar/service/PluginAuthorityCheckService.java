package cn.edustar.jitar.service;

import cn.edustar.jitar.model.CommonObject;
import cn.edustar.jitar.pojos.User;

/**
 * 
 * @author 孟宪会
 * 插件权限验证服务接口
 *
 */
public interface PluginAuthorityCheckService {
	public boolean canManagePluginInstance(CommonObject commonObject, User currentLoginUser);
}
