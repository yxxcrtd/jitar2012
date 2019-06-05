package cn.edustar.jitar.model;

import javax.servlet.ServletContext;

import org.jasig.cas.client.util.CasConst;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

/**
 * 表示当前站点地址(站点地址被用在模板中，在 Filter 中会设置其正确的值)
 * 
 * 
 */
public class SiteUrlModel implements TemplateScalarModel, TemplateModelObject {

    /**
     * 得到站点地址, 首先获取当前线程绑定的地址, 如果没有则返回缺省地址.
     * 
     * @return
     */
    public static final String getSiteUrl() {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        ServletContext sc = wac.getServletContext();
        String url = sc.getInitParameter("siteUrl");
        if (url == null || url.length() == 0) {
            String SUrl = CasConst.getInstance().getSiteServerUrl();
            if (SUrl == null || SUrl.length() == 0) {
                url = sc.getContextPath();
            } else {
                url = SUrl;
            }

        }
        if (url.endsWith("/") == false) {
            url += "/";
        }
        return url;
    }

    /*
     * (non-Javadoc)
     * 
     * @see freemarker.template.TemplateScalarModel#getAsString()
     */
    public String getAsString() throws TemplateModelException {
        return getSiteUrl();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.edustar.jitar.model.TemplateModelObject#getVariableName()
     */
    public String getVariableName() {
        return "SiteUrl";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getSiteUrl();
    }

}
