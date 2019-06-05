package cn.edustar.jitar.action;

/**
 * 表示一个页面上的连接, 用于 Action.addActionLink()
 */
public class ActionLink {

	/** 使用 window.history.back() 方式返回的 ActionLink 实例. */
	public static final ActionLink HISTORY_BACK = new ActionLink("返回", "javascript:window.history.back();");

	/** 链接地址 */
	private String link;
	
	/** 链接文字 */
	private String text;
	
	/** 目标窗口, 默认为当前窗口 */
	private String target = "";

	/**
	 * 缺省构造.
	 */
	public ActionLink() {
		
	}
	
	/**
	 * 通过指定文字构造 ActionLink
	 * @param text
	 */
	public ActionLink(String text) {
		this.text = text;
	}

	/**
	 * 通过指定文字、链接地址构造 ActionLink
	 */
	public ActionLink(String text, String link) {
		this.text = text;
		this.link = link;
	}
	
	/**
	 * 通过指定文字、链接地址、目标窗口 构造 ActionLink
	 */
	public ActionLink(String text, String link, String target) {
		this.text = text;
		this.link = link;
		this.target = target;
	}
	
	/**
	 * 得到通过 text, link, target 产生的 html 代码.
	 * @return
	 */
	public String getHtml() {
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("<a href='").append(this.link).append("' class='action_link'");
		if (this.target != null && this.target.length() > 0)
			strbuf.append(" target='").append(this.target).append("'");
		strbuf.append(">").append(this.text).append("</a>");
		return strbuf.toString();
	}
	
	/** 链接地址 */
	public String getLink() {
		return link;
	}

	/** 链接地址 */
	public void setLink(String link) {
		this.link = link;
	}

	/** 链接文字 */
	public String getText() {
		return text;
	}

	/** 链接文字 */
	public void setText(String text) {
		this.text = text;
	}

	/** 目标窗口, 默认为当前窗口 */
	public String getTarget() {
		return target;
	}

	/** 目标窗口, 默认为当前窗口 */
	public void setTarget(String target) {
		this.target = target;
	}
	
}
