package cn.edustar.jitar.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.ServletContextAware;

import com.caucho.hessian.client.HessianProxyFactory;
import com.octopus.system.service.UnitManageService;

import cn.edustar.jitar.pojos.Unit;
import cn.edustar.jitar.service.UnitService;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 平台初始化的站点配置，需要设置一个根机构。
 * 
 * @author mxh
 * 
 */
public class AddRootUnitAction extends BaseAction implements ServletRequestAware, ServletResponseAware, ServletContextAware {

    /**
     * 
     */
    private static final long serialVersionUID = 2100450927762532299L;
    /** Web 应用环境 */
    protected ServletContext servlet_ctxt;

    /** 请求对象 */
    protected HttpServletRequest request;

    /** 响应对象 */
    protected HttpServletResponse response;

    private UnitService unitService;

    @Override
    public String execute() throws Exception {
        Unit rootUnit = this.unitService.getRootUnit();
        if (null != rootUnit) {
            this.addActionError("根机构已经存在，一个平台只能有一个根机构。");
            this.addActionLink("返回首页", "index.action");
            return ERROR;
        }

        ParamUtil params = new ParamUtil(request);
        String cmd = params.safeGetStringParam("cmd");

        if (cmd == null || cmd.length() == 0) {
            return INPUT;
        } else if ("inputId".equals(cmd)) {
            int unitId = params.safeGetIntParam("unitId");
            String unitCode = params.safeGetStringParam("unitCode","");
            if (unitId == 0 && unitCode.length() == 0) {
                this.addActionError("请输入正确的机构Id 或者机构编码。");
                this.addActionLink("重新输入", "addRootUnit.action");
                return ERROR;
            }

            HessianProxyFactory factory = new HessianProxyFactory();
            factory.setConnectTimeout(5000L); // 毫秒
            com.octopus.system.model.Unit ssoUnit = null;

            // 临时使用，需要从配置文件中读取
            String ssoServerURL = request.getServletContext().getInitParameter("SSOServerURL");
            if (null == ssoServerURL){
                ssoServerURL = "";
            }
            String SSOServerURL1 = ssoServerURL;
            String SSOServerURL2 = ssoServerURL;
            if(ssoServerURL.indexOf(";")>-1){
            	String[] arrayUrl = ssoServerURL.split("\\;");
            	SSOServerURL1 = arrayUrl[0];
            	SSOServerURL2 = arrayUrl[1];
            }             
            if (SSOServerURL1.endsWith("/")) {
            	SSOServerURL1 = SSOServerURL1.substring(0, SSOServerURL1.length() - 1);
            }
            if (SSOServerURL2.endsWith("/")) {
            	SSOServerURL2 = SSOServerURL2.substring(0, SSOServerURL2.length() - 1);
            }
            String unitManageServiceUrl = SSOServerURL2
                    + request.getServletContext().getFilterRegistration("ssoUserFilter").getInitParameter("unitManageServiceUrl");
           
            if (unitManageServiceUrl == null || unitManageServiceUrl.length() == 0) {
                this.addActionError("在 web.xml 中没有配置 SSOServerURL和unitManageServiceUrl 地址。");
                this.addActionLink("重新输入", "addRootUnit.action");
                return ERROR;
            }
            
            try {
                UnitManageService unitManageService = (UnitManageService) factory.create(UnitManageService.class, unitManageServiceUrl);
                ssoUnit = unitManageService.getById(String.valueOf(unitId));
                if(ssoUnit == null){
                    ssoUnit = unitManageService.getByUnitCode(unitCode);
                }
            } catch (Exception ex) {
                System.out.println("传输错误");
            }
            if (null == ssoUnit) {
                this.addActionError("不能从 " + unitManageServiceUrl + " 获得机构信息。返回值为 null。");
                this.addActionLink("重新输入", "addRootUnit.action");
                return ERROR;
            }
            rootUnit = null;
            rootUnit = new Unit();
            rootUnit.setHasChild(false);
            rootUnit.setParentId(0);
            rootUnit.setSiteTitle(ssoUnit.getWebSiteName());
            rootUnit.setUnitId(ssoUnit.getUnitId());
            rootUnit.setUnitName(ssoUnit.getUnitEName());
            rootUnit.setUnitTitle(ssoUnit.getUnitName());
            rootUnit.setUnitPathInfo("/" + ssoUnit.getUnitId() + "/");
            //System.out.println("rootUnit=" + rootUnit);
            this.unitService.saveOrUpdateUnit(rootUnit);
            this.addActionMessage("根机构保存成功。目前系统没有任何用户，请注册一个超级管理员，默认情况下，系统第一个注册的帐号将作为超级管理员。");
            this.addActionLink("注册超级管理员", "register.action");
            this.addActionLink("返回首页", "index.action");
            return SUCCESS;
        }
        return INPUT;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    @Override
    public void setServletContext(ServletContext servlet_ctxt) {
        this.servlet_ctxt = servlet_ctxt;

    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;

    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;

    }

}
