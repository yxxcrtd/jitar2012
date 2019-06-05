package cn.edustar.jitar.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 群组系统中 Action 共同基类, 其在 ActionSupport 的基础上还提供 ActionLink 支持
 * 
 *
 */
public abstract class BaseAction extends ActionSupport implements ActionInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1449820105757931945L;

	public static List<Integer> NORMAL_USER_GROUPS = new ArrayList<Integer>();
	
	/** 所有链接地址 */
	private List<ActionLink> action_links = new ArrayList<ActionLink>();

	/** 所有链接地址 */
	public List<ActionLink> getActionLinks() {
		return this.action_links;
	}

	/**
	 * 判定是否有至少一个链接 ActionLink
	 * 
	 * @return
	 */
	public boolean hasActionLinks() {
		return action_links.isEmpty() == false;
	}

	/** 所有链接地址 */
	public void setActionLinks(List<ActionLink> action_links) {
		this.action_links = action_links;
	}

	/**
	 * 添加一个链接地址
	 * 
	 * @param text 链接文字
	 * @param link 链接地址
	 */
	public void addActionLink(String text, String link) {
		this.action_links.add(new ActionLink(text, link));
	}

	/**
	 * 添加一个链接地址
	 * 
	 * @param text 链接文字
	 * @param link 链接地址
	 * @param target 目标窗口
	 */
	public void addActionLink(String text, String link, String target) {
		this.action_links.add(new ActionLink(text, link, target));
	}

	/**
	 * 添加一个链接地址
	 * 
	 * @param action_link
	 */
	public void addActionLink(ActionLink action_link) {
		this.action_links.add(action_link);
	}
	
}
