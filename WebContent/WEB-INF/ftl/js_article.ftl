<#if article_list?? >
document.writeln("<style>");
document.writeln(".news_new_item_ul {margin:0;padding:0;list-style-type:none;}");
document.writeln(".news_new_item_ul li{padding-left:6px;}");
document.writeln("</style>");
document.writeln("<ul class='news_new_item_ul'>");
<#list article_list as a >
document.write("<li><a target='_blank' href='${SiteUrl}showArticle.action?articleId=${a.articleId}'>${Util.getCountedWords(a.title!?js_string,ShowCount)}</a><#if ShowAuthor??>[<a href='${SiteUrl}go.action?userId=${a.userId}'>${a.trueName!?html}</a>] </#if><#if ShowDate??>[${a.createDate?string("MM-dd HH:mm")}]</#if></li>");
</#list>
document.writeln("</ul>");
</#if>