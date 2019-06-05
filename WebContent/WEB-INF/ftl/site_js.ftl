<#if user_list?? >
    document.writeln("<div class='container'>")
    <#list user_list as u >
       document.write("<div><a target='_blank' href='${SiteUrl}go.action?loginName=${u.loginName}'>${Util.getCountedWords(u.trueName!?js_string,ShowCount)}</a></div>")
    </#list>
    document.writeln("</div>")
</#if> 

<#if article_list?? >
	document.writeln("<div class='container'>")
	<#list article_list as a >
	   document.write("<div><a target='_blank' href='${SiteUrl}showArticle.action?articleId=${a.articleId}'>${Util.getCountedWords(a.title!?js_string,ShowCount)}</a> [<a href='${SiteUrl}go.action?userId=${a.userId}'>${a.nickName!?html}</a>] [${a.createDate?string("MM-dd HH:mm")}]</div>")
	</#list>
	document.writeln("</div>")
</#if> 