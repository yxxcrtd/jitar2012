<#if link_list??>
<ul>
<#list link_list as lnk>
  <li><a href='${lnk.linkAddress!}' target='_blank' title="${lnk.title!?html}"><#if lnk.linkIcon?? && lnk.linkIcon!="" && lnk.linkIcon!="http://"><img src='${lnk.linkIcon!}' border="0" /><#else>${lnk.title!?html}</#if></a></li>
</#list>
</ul>
<div style="clear:both; text-align:right; margin-top:6px;"><a href='${SiteUrl}g/${group.groupName}/py/show_more_group_links.py'>&gt;&gt; 更多</a></div>
</#if>