package cn.edustar.jitar.tags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;

import com.opensymphony.xwork2.ActionContext;

/**
 * 执行 HQL (SELECT,FROM)语句并将数据放到页面环境中的标签.
 *
 * <jtr:hql hql='FROM Article' var='article_list' xxx='3' yyy='4'>
 *   <s:iterator name='article' list='article_list'>
 *     <s:value value='article.title' />
 *   </s:iterator>
 * </jtr:hql>
 */
public class HqlTag extends AbstractBodyTag implements DynamicAttributes {
	/** serialVersionUID */
	private static final long serialVersionUID = 4537939186114437594L;
	
	/** 可变属性的集合 */
	private Map<String, Object> attr_map = new HashMap<String, Object>();
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.DynamicAttributes#setDynamicAttribute(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public void setDynamicAttribute(String uri, String localName, Object value)
			throws JspException {
		attr_map.put(localName, value);
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() {
		prepareData();
		
		return TagSupport.EVAL_BODY_INCLUDE;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	public int doEndTag() throws JspTagException {
		try {
			return TagSupport.EVAL_PAGE;
		} finally {
			this.attr_map = new HashMap<String, Object>();
		}
	}

	/**
	 * 准备数据.
	 */
	@SuppressWarnings("unchecked")
	private void prepareData() {
		// 得到参数.
		String hql = safeGetStringParam("hql");
		String var = safeGetStringParam("var", "list");
		
		// 得到执行器.
		HibernateHqlExecutor hql_e = (HibernateHqlExecutor)super.getSprintContext().getBean("hqlExecutor");
		@SuppressWarnings("unchecked")
		List obj = hql_e.find(hql);
		
		// 设置到环境中.
		pageContext.getRequest().setAttribute(var, obj);
		Map ctxt = ActionContext.getContext().getValueStack().getContext();
		ctxt.put(var, obj);
	}
	
	public String safeGetStringParam(String key) {
		return safeGetStringParam(key, "");
	}
	
	public String safeGetStringParam(String key, String def) {
		if (this.attr_map.containsKey(key) == false) return def;
		Object v = attr_map.get(key);
		if (v == null) return def;
		
		return v.toString();
	}
}

// for (Map.Entry<String, Object> entry : attr_map.entrySet()) {
// 	writer.write("<li>key=" + entry.getKey() + ", value=" + entry.getValue());
// }
