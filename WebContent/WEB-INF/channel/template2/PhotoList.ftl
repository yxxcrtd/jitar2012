<#if photo_list??>
<script src="${SiteUrl}js/jitar/marquee.js"></script>
<#assign id=Util.uuid()?string?replace("-","")>
<!-- CSS 样式的设置可能会影响显示效果，注意进行调整。 -->
<div id="scroll${id}" style="width:256px;overflow: hidden;text-align:center;">
<table border="0" cellpadding="0" cellspacing="0" id="content${id}" align="center">
<tr>
<#list photo_list as p>
<td>
<#if UserUrlPattern??>
<div style="text-align:center;overflow:hidden;padding:6px;">
<a href='${UserUrlPattern.replace('{loginName}',p.loginName)}photo/${p.photoId}.html'>
<img src="${Util.thumbNails(p.href!)}" border="0" width="${pw}"/>
</a>
<nobr><a style="display:block;padding-top:6px;overflow:hidden;width:${pw}px;" href='${UserUrlPattern.replace('{loginName}',p.loginName)}photo/${p.photoId}.html' title="${p.title!?html}">${p.title}</a></nobr>
</div>
<#else>
<div style="text-align:center;overflow:hidden;padding:6px;">
<a href='${SiteUrl}${p.loginName}/photo/${p.photoId}.html'><img src="${Util.thumbNails(p.href!)}" border="0" width="${pw}" /></a>
<nobr><a style="display:block;padding-top:6px;overflow:hidden;width:${pw}px;" href='${SiteUrl}${p.loginName}/photo/${p.photoId}.html' title="${p.title!?html}">${p.title}</a></nobr>
</div>
</#if>
</td>
</#list>
</tr>
</table>
</div>
<script>
window.setTimeout(function(){new MarqueeScroll("scroll${id}", "content${id}", 1, 10);},1000);
</script>
</#if>