package cn.edustar.jitar.model;

import org.springframework.web.context.ContextLoader;

import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

/**
 * 得到网站的上下文路径
 * @author mxh
 *
 */
public class ContextPathModel implements TemplateScalarModel, TemplateModelObject {

    @Override
    public String getVariableName() {
        return "ContextPath";
    }

    @Override
    public String getAsString() throws TemplateModelException {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath() + "/";
    }

}
