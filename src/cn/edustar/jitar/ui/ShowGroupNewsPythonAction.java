package cn.edustar.jitar.ui;

import cn.edustar.jitar.pojos.GroupNews;

/**
 * 显示一个群组新闻的 action 基类.
 *
 *
 */
public class ShowGroupNewsPythonAction extends ShowGroupPythonAction {
	/** serialVersionUID */
	private static final long serialVersionUID = 806927332718714492L;
	
	/** 要显示的新闻 */
	private GroupNews group_news;
	
	/**
	 * 构造.
	 */
	public ShowGroupNewsPythonAction() {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jitar.ui.ShowGroupPythonAction#beforeExecute()
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected String beforeExecute() {
		String result = super.beforeExecute();
		if (result != null) return result;
		
		int newsId = param.getIntParam("newsId");
		this.group_news = getGroupService().getGroupNews(newsId);
		if (this.group_news == null) {
			addActionError("未找到指定标识的协作组新闻, newsId = " + newsId);
			return ERROR;
		}
		
		getGroupService().incGroupNewsViewCount(group_news, 1);
		setData("group_news", group_news);
		return null;
	}
}
