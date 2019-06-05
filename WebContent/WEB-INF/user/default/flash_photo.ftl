<#if photo_list?? >
<#assign pics = ''>
<#assign links = ''>
<#assign texts = ''>
<#list photo_list as p >
<#if UserUrlPattern??>
  <#if p_has_next>
	  <#assign pics = pics + Util.url(p.href) + '|' >
	  <#assign links = links + UserUrlPattern.replace('{loginName}',user.loginName) + 'photo/' + p.photoId + '.html|' >
	  <#assign texts = texts + p.title + '|' >
	 <#else>
	  <#assign pics = pics + Util.url(p.href) >
	  <#assign links = links + UserUrlPattern.replace('{loginName}',user.loginName) + 'photo/' + p.photoId + '.html' >
	  <#assign texts = texts + p.title >
	 </#if>
  <#else>
	 <#if p_has_next>
	  <#assign pics = pics + Util.url(p.href) + '|' >
	  <#assign links = links + SiteUrl + user.loginName + '/photo/' + p.photoId + '.html|' >
	  <#assign texts = texts + p.title + '|' >
	 <#else>
	  <#assign pics = pics + Util.url(p.href) >
	  <#assign links = links + SiteUrl + user.loginName + '/photo/' + p.photoId + '.html' >
	  <#assign texts = texts + p.title >
  </#if>
</#if> 
</#list>
<div style='text-align:center;'>
<object type="application/x-shockwave-flash" data="${SiteUrl}images/slide.swf" width="${fwidth}" height="${fheight}">
<param name="movie" value="${SiteUrl}images/slide.swf" />
<param name="allowScriptAcess" value="sameDomain" />
<param name="quality" value="best" />
<param name="bgcolor" value="${fbgcolor}" />
<param name="scale" value="noScale" />
<param name="menu" value="false" />
<param name="wmode" value="opaque" />
<param name="FlashVars" value="playerMode=embedded&amp;pics=${pics}&amp;links=${links}&amp;texts=${texts}&amp;borderwidth=${fwidth}&amp;borderheight=${fheight}&amp;textheight=${ftxtheight}" />
</object>
</div>
</#if>