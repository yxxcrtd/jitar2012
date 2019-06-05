package cn.edustar.jitar.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import cn.edustar.jitar.pojos.Config;
import cn.edustar.jitar.pojos.User;
import cn.edustar.jitar.service.AccessControlService;
import cn.edustar.jitar.service.ConfigService;
import cn.edustar.jitar.util.WebUtil;

/**
 * @author Yang XinXin
 * @version 1.0.0, 2008-08-19 09:40:11
 */
public class AdminSysConfigAction extends BaseUserAction {
	private static final long serialVersionUID = 1209582032260225055L;
	private ConfigService cfg_svc;
	private AccessControlService accessControlService;

	public void setConfigService(ConfigService cfg_svc) {
		this.cfg_svc = cfg_svc;
	}

	public void setAccessControlService(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }

    private String value;
	private String name;
	private List<Config> sysConfigList = new ArrayList<Config>();

	@Override
	protected String execute(String cmd) throws Exception {
		User user = WebUtil.getLoginUser(getSession());
		//System.out.println("测试：user=" + user);
		//System.out.println("测试：this.accessControlService.isSystemAdmin(user)=" + this.accessControlService.isSystemAdmin(user));
		if (this.accessControlService.isSystemAdmin(user) == false) {
			this.addActionError("只有超级管理员才能进行管理。");
			return ERROR;
		}
		if ("list".equalsIgnoreCase(cmd) || cmd == null || cmd.length() == 0) {
			return list();
		} else if ("saveAudit".equalsIgnoreCase(cmd)) {
			return saveAudit();
		} else
			return this.unknownCommand(cmd);
	}

	private String list() throws Exception {
		sysConfigList = cfg_svc.getSysConfigList();
		return LIST_SUCCESS;
	}

	private String saveAudit() throws Exception {
		Config cfg = cfg_svc.getConfigByItemTypeAndName("jitar", name);
		cfg.setValue(value);
		cfg.setDefval(value);
		cfg_svc.updateConfig(cfg);
		cfg_svc.reloadConfig();
		if ("site.verifyCode.enabled".equals(name)) {
			updateUser("isShowVerifyCode", value);
		}
		if ("site.auto.html".equals(name)) {
			//删除静态文件
			this.deleteStaticIndexHtmlFile();
		}
		if ("false".equals(value)) {
			this.addActionError(cfg.getTitle() + " 已成功设置为：否 ！");
		} else if ("true".equals(value)) {
			this.addActionError(cfg.getTitle() + " 已成功设置为：是 ！");
		}		
		return list();
	}

	
	private void deleteStaticIndexHtmlFile()
	{
		//注意：deleteQuietly可以删除文件夹，传递参数时要小心
		String staticIndexHtmlFile = request.getServletContext().getRealPath("/");
		if(!staticIndexHtmlFile.endsWith(File.separator)){
			staticIndexHtmlFile += File.separator;
		}		
		staticIndexHtmlFile += "index.html";
		File f = new File(staticIndexHtmlFile);
		if(f.exists() && f.isFile())
		{
			FileUtils.deleteQuietly(f);
		}
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Config> getSysConfigList() {
		return sysConfigList;
	}

}
