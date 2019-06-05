<#if resource_list?? >
document.writeln("<style>");
document.writeln(".news_new_item_ul {margin:0;padding:0;list-style-type:none;}");
document.writeln(".news_new_item_ul li{padding-left:6px;}");
document.writeln("</style>");
document.writeln("<ul class='news_new_item_ul'>");
<#list resource_list as r >
document.write("<li><a target='_blank' href='${SiteUrl}showResource.action?resourceId=${r.resourceId}'>${Util.getCountedWords(r.title!?js_string,ShowCount)}</a><#if ShowAuthor??>[<a href='${SiteUrl}go.action?userId=${r.userId}'>${r.trueName!?html}</a>] </#if><#if ShowDate??>[${r.createDate?string("MM-dd HH:mm")}]</#if></li>");
</#list>
document.writeln("</ul>");
</#if> 