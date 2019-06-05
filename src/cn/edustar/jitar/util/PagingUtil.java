package cn.edustar.jitar.util;

public class PagingUtil {
	
	public static String ShowPage(Integer recCount, Integer curPage,
			Integer pageItem, String PageUrl) {
		return ShowPage(recCount, curPage, pageItem, PageUrl, 10);
	}

	public static String ShowPage(Integer recCount, Integer curPage,
			Integer pageItem, String PageUrl, Integer offsetStep) {
		int LeftNum = 0;
		int RightNum = 0;
		int PageCount = (int) Math.ceil((double) recCount / (double) pageItem);
		if (curPage > PageCount) {
			curPage = PageCount;
		}

		if (curPage - offsetStep < 1) {
			LeftNum = 1;
		} else {
			LeftNum = curPage - offsetStep;
		}

		if (curPage + offsetStep > PageCount) {
			RightNum = PageCount;
		} else {
			RightNum = curPage + offsetStep;
		}
		String OutPut = "";
		for (Integer i = LeftNum; i <= RightNum; i++) {
			if (i.toString().equals(curPage.toString())) {
				OutPut += "<font color=red>" + i.toString() + "</font> ";
			} else {
				OutPut += "<a href=\"" + PageUrl + "page=" + i.toString()
						+ "\">" + i.toString() + "</a> ";
			}
		}
		if (curPage > 1) {

			OutPut = "<a href=\"" + PageUrl + "page=1\">首页</a> <a href=\""
					+ PageUrl + "page=" + (curPage - 1) + "\">上一页</a> "
					+ OutPut;
		}

		if (curPage < PageCount) {
			OutPut = OutPut + " <a href=\"" + PageUrl + "page=" + (curPage + 1)
					+ "\">下一页</a> <a href=\"" + PageUrl + "page=" + PageCount
					+ "\">末页</a>";
		}

		OutPut = "共 "
				+ String.valueOf(PageCount)
				+ " 页 " + recCount + " 条 "
				+ OutPut
				+ " 转到：<input id=\"GoPage\" value=\"\"/><input type=\"button\" value=\"GO\" onclick=\"window.location='"
				+ PageUrl
				+ "page=' + document.getElementById('GoPage').value;\"/>";
		return OutPut;
	}
}
