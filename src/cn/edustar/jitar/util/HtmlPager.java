package cn.edustar.jitar.util;

public class HtmlPager {

    public static String render(int pageCount, int offsetCount, int current) {
        String preLabel = "<";
        String nextLabel = ">";
        String h = "";
        if (pageCount <= 1)
            return h;
        int MaxStart = pageCount - offsetCount * 2;
        if (MaxStart < 1)
            MaxStart = 1;
        int MinEnd = offsetCount * 2 + 1;

        int start = current - offsetCount;
        int end = current + offsetCount;
        if (start < 1) {
            start = 1;
            end = MinEnd;
        }
        if (end > pageCount) {
            end = pageCount;
            start = MaxStart;
        }
        boolean showGoInput = false;
        h = "<a href='javascript:void(0)' class='listPagePre'>" + preLabel + "</a> ";
        if (current == 1)
            h = "";
        if (start > 1)
            h += "<a href='javascript:void(0)' class='listPageC'>1</a> ";
        if (start > 2) {
            showGoInput = true;
            h += "<span>...</span>";
        }

        for (int i = start; i < end; i++) {
            if (i == current) {
                h += "<a href='javascript:void(0)' class='listPageC active'>" + i + "</a> ";
            } else {
                h += "<a href='javascript:void(0)' class='listPageC'>" + i + "</a> ";
            }
        }
        if (start < MaxStart) {
            showGoInput = true;
            h += "<span>...</span>";
        }
        if (current == pageCount) {
            h += " <a href='javascript:void(0)' class='listPageC active'>" + current + "</a>";
        } else {
            h += " <a href='javascript:void(0)' class='listPageC'>" + pageCount + "</a>";
        }
        if (current != pageCount) {
            h += "<a href='javascript:void(0)' class='listPagePre'>" + nextLabel + "</a> ";
        }
        if (showGoInput) {
            h += " <span class='listPageText'>跳转至</span><span class='listPageInput'><input minValue='1' maxValue='" + pageCount + "' value='" + current
                    + "' type='text'></span><span class='listPageBtn'><input type='button' value='GO'></span>";
        }
        return h;

    }
}
