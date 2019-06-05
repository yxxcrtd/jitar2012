<#if video_list?? >
document.writeln("<style>");
document.writeln(".news_new_item_ul {margin:0;padding:0;list-style-type:none;}");
document.writeln(".news_new_item_ul li{padding-left:6px;}");
document.writeln("</style>");
document.writeln("<ul class='news_new_item_ul'>");
<#list video_list as video >
document.write("<li><a target='_blank' href='${SiteUrl}manage/video.action?cmd=show&videoId=${video.videoId}'>${Util.getCountedWords(video.title!?js_string,ShowCount)}</a></li>");
</#list>
document.writeln("</ul>");
</#if>