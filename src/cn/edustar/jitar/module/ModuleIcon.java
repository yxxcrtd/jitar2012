package cn.edustar.jitar.module;

import java.util.HashMap;

public class ModuleIcon {

	private HashMap<String, String> iconMap = null;

	public ModuleIcon() {
		// 缺省的系统图标,路径应该总是以网站的根目录开始。
		
		iconMap = new HashMap<String, String>();
		iconMap.put("rss", "js/jitar/moduleicon/ico_rss.gif");
		iconMap.put("profile", "js/jitar/moduleicon/ico_profile.gif");
		iconMap.put("user_stats", "js/jitar/moduleicon/ico_stats.gif");
		iconMap.put("joined_groups", "js/jitar/moduleicon/ico_joinedgroup.gif");
		iconMap.put("user_placard", "js/jitar/moduleicon/ico_placard.gif");
		iconMap.put("user_cate", "js/jitar/moduleicon/ico_cate.gif");
		iconMap.put("user_rcate", "js/jitar/moduleicon/ico_cate.gif");
		iconMap.put("friendlinks", "js/jitar/moduleicon/ico_friend.gif");
		iconMap.put("entries", "js/jitar/moduleicon/ico_article.gif");
		iconMap.put("user_leaveword", "js/jitar/moduleicon/ico_leaveword.gif");
		iconMap.put("article_comments", "js/jitar/moduleicon/ico_comment.gif");
		iconMap.put("placeholder", "js/jitar/moduleicon/content.gif");
		iconMap.put("questionanswer", "js/jitar/moduleicon/help.gif");
		iconMap.put("vote", "js/jitar/moduleicon/ico_stats.gif");
		iconMap.put("calendarevent", "js/jitar/moduleicon/datetime.gif");
	}

	public String getModuleIcon(String key) {
		String icon = "";
		if (this.iconMap.containsKey(key))
			icon = this.iconMap.get(key);
		if (icon == null || icon.trim().equals(""))
			icon = null;
		if (iconMap != null) {
			this.iconMap.clear();
			iconMap = null;
		}
		return icon;		
	}
}
