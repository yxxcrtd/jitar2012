package cn.edustar.jitar.data;

import cn.edustar.data.Pager;
import cn.edustar.jitar.util.ParamUtil;

/**
 * 能够方便配置的 pager 对象.
 *
 *
 */
public class PagerBean extends Pager {
	/**
	 * 设置当前页.
	 * @param pageString
	 */
	public void setCurrPage(String pageString) {
		int page = 1;
		if (ParamUtil.isBlankString(pageString)) {
			super.setCurrentPage(page);
		} else if (ParamUtil.isInteger(pageString)) {
			super.setCurrentPage(Integer.parseInt(pageString));
		}
	}
}
