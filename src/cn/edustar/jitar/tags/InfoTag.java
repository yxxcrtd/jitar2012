package cn.edustar.jitar.tags;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 显示系统一些基本信息, 可用于调试.
 *
 *
 */
public class InfoTag extends TagSupport {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6470115330780771735L;

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() {
		return TagSupport.EVAL_BODY_INCLUDE;
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	public int doEndTag() throws JspTagException {
		try {
			Writer writer = pageContext.getOut();
			writer.write("<ul>");
			writer.write("<li>Hello, Jitar InfoTag is present");
			writer.write("</ul>");
			return TagSupport.EVAL_PAGE;
		} catch (IOException ex) {
			throw new JspTagException(ex);
		} 
	}
}
