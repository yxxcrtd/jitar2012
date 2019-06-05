<div style="height:10px;"></div>
<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
<tr style="vertical-align:top;">
<td class="left">
<div class="leftcontainer">
<link rel="stylesheet" type="text/css" href="${SiteUrl}dtree.css" />  
<script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
<script type="text/javascript">
var JITAR_ROOT = "${SiteUrl}";
d = new dTree("d");
d.add(0,-1,"<b>资源分类</b>","channel.action?cmd=resourcelist&type=${type!}&channelId=${channel.channelId}");
<#if resource_category??>
<#list resource_category.all as c>
<#if c.parentId??>
d.add(${c.id},${c.parentId},"${c.name}","channel.action?cmd=resourcelist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}");      	
<#else>
d.add(${c.id},0,"${c.name}","channel.action?cmd=resourcelist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}");
</#if>
</#list>
</#if>
document.write(d);
d.openAll();
</script>
</div>
</td>
<td class="right">
<#if resource_list??>
<div class="rightcontainer">
<table border="0" cellspacing='0' class="datalist">
<tr>
<th style="text-align:center"><nobr>资源名称</nobr></th> 
<th><nobr>上传人</nobr></th>   
<th><nobr>上传日期</nobr></th>
<th><nobr>资源大小</nobr></th>
<th><nobr>查看次数</nobr></th>
</tr>
<#list resource_list as resource>
  <tr style="vertical-align:top">
	 <td style="width:100%"><img src='${Util.iconImage(resource.href!)}' border='0' align='absmiddle' /> <a href='${SiteUrl}showResource.action?resourceId=${resource.resourceId}'>${resource.title!?html}</a></td>
	 <td><nobr><a href='${SiteUrl}go.action?loginName=${resource.loginName}'>${resource.userTrueName}</a></nobr></td> 	
	 <td><nobr>${resource.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
	 <td style="text-align:right"><nobr>${Util.fsize(resource.fsize!)}</nobr></td>
	 <td style="text-align:right"><nobr>${resource.viewCount}</nobr></td> 
  </tr>
</#list>
</table>
</div>
<#if pager??>
<div class='pager'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>
</#if>
</#if>
</td>
</tr>
</table>