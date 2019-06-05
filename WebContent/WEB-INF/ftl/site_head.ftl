<script type='text/javascript'>
var JITAR_ROOT = window.JITAR_ROOT || '${SiteUrl}';
function goLoginPage(){
window.location.href='${SiteUrl}login.jsp?redUrl=' + encodeURIComponent(window.location.href);
}
</script>
<#if Site.getSiteAutoHtml()><#include '/html/subject_nav.html' parse=false><#else><#include '/WEB-INF/ftl/site_subject_nav.ftl'></#if>
<div class='help'>
 <span style='float:right;'>
 <img src='${SiteThemeUrl}home_2.gif' align='absmiddle' /> <a href='${SiteUrl}addtodeskop.jsp'>保存桌面快捷方式</a>&nbsp;
 <img src='${SiteUrl}images/rss2.gif' align='absmiddle' /> <a href='${SiteUrl}rss.py'>订阅本站</a>&nbsp;
 <img src='${SiteThemeUrl}home_1.gif' align='absmiddle' /> <a href='#' onclick='CommonUtil.setHomepage("${SiteUrl}");return false;'>设为首页</a>&nbsp;
 <img src='${SiteThemeUrl}home_2.gif' align='absmiddle' /> <a href='#' onclick='CommonUtil.AddFav("${SiteUrl}", document.title);return false;'>加入收藏</a>&nbsp;
 </span> 
 <div style='float:left;padding-left:20px;text-align:left;' id="loginstatus_head"></div>
 <script>document.write('<iframe src="${SiteUrl}loginstatus_head.py?uuid=${Util.uuid()}&'+ (new Date()).valueOf() +'" style="display:none"></iframe>');</script>
</div>
<div class='logo'><#if SiteConfig["site.logo"]?? && SiteConfig["site.logo"]?string != ""><#assign x = SiteConfig["site.logo"]?string><#assign x = x?split("|")><#if x[2] == ""><img src='${SiteThemeUrl}logo.jpg' /><#else><#if x[2]?lower_case?ends_with(".swf")><embed src='${x[2]}' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width="${x[0]}" height="${x[1]}"></embed><#else><img src='${x[2]}' /></#if></#if><#else><img src='${SiteThemeUrl}logo.jpg' /></#if></div>
<div class='navbar'>
<div>
<#if SiteNavList??>
<#if head_nav??><#assign crtnav=head_nav><#else><#assign crtnav=''></#if>
<#list SiteNavList as SiteNav><#if SiteNav.isExternalLink ><a href='${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a><#if SiteNav_has_next> | </#if><#else><#if SiteNav.currentNav?? && SiteNav.currentNav == crtnav><a href='${SiteUrl}${SiteNav.siteNavUrl}'><span>${SiteNav.siteNavName}</span></a><#if SiteNav_has_next> | </#if><#else><a href='${SiteUrl}${SiteNav.siteNavUrl}'>${SiteNav.siteNavName}</a><#if SiteNav_has_next> | </#if></#if></#if></#list>
<#else>
配置错误，无法显示导航信息。
</#if>
</div>
</div>