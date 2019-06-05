package cn.edustar.jitar.model;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

public class SSOServerUrlModel implements TemplateScalarModel, TemplateModelObject {

    /**
     * 得到SSO站点地址根目录, 首先获取当前线程绑定的地址, 如果没有则返回缺省地址.
     * 
     * @return
     */
    public static final String getSSOServerUrl() {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        ServletContext sc = wac.getServletContext();
        String ssoServerUrl = sc.getInitParameter("SSOServerURL");
        if(ssoServerUrl==null){
        	return "";
        }
        String SSOServerURL1 = ssoServerUrl;
        String SSOServerURL2 = ssoServerUrl;
        if(ssoServerUrl.indexOf(";")>-1){
        	String[] arrayUrl = ssoServerUrl.split("\\;");
        	SSOServerURL1 = arrayUrl[0];
        	SSOServerURL2 = arrayUrl[1];
        } 
        
        if (SSOServerURL1 == null || SSOServerURL1.length() == 0) {
            ssoServerUrl = sc.getContextPath();
        }else{
        	ssoServerUrl = SSOServerURL1;
        }
        if (ssoServerUrl.endsWith("/") == false) {
            ssoServerUrl += "/";
        }
        return ssoServerUrl;
    }

 
    public String getAsString() throws TemplateModelException {
        return getSSOServerUrl();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.model.TemplateModelObject#getVariableName()
     */
    public String getVariableName() {
        return "SSOServerUrl";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getSSOServerUrl();
    }

}