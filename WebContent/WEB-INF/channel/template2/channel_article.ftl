<div style="height:10px;"></div>
<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
<tr style="vertical-align:top;">
<td class="left">
<div class="leftcontainer">
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/index/dtree.css" />  
<script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
<script type="text/javascript">
if(!window.JITAR_ROOT){var JITAR_ROOT = "${SiteUrl}";}
d = new dTree("d");
var rootCount = 0;
<#if article_category??>
<#list article_category.all as c>
<#if c.parentId??>
<#else>
     rootCount++;
</#if>
</#list>
</#if>
//alert(rootCount);
//add的参数:id, pid, name, url, title, target, icon, iconOpen, open
if(rootCount != 1){
    d.add(0,-1,"<b>文章分类</b>","channel.action?cmd=articlelist&type=${type!}&channelId=${channel.channelId}&categoryId=${categoryId}");
}
<#if article_category??>
<#list article_category.all as c>
<#if c.parentId??>
    d.add(${c.id},${c.parentId},"${c.name}","channel.action?cmd=articlelist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}&categoryId=${categoryId}");      	
    //d.add(${c.id},${c.parentId},"${c.name}","channel.action?cmd=articlelist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}&categoryId=${categoryId}","${c.name}","","","",false);
<#else>
    if(rootCount!=1){
        d.add(${c.id},0,"${c.name}","channel.action?cmd=articlelist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}&categoryId=${categoryId}");
    }else{
        d.add(${c.id},-1,"<b>${c.name}</b>","channel.action?cmd=articlelist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}&categoryId=${categoryId}");
    }
</#if>
</#list>
</#if>
document.write(d);
//d.openAll();
//d.closeAll();
//document.getElementById("dd0").style.display = "none";
//document.getElementById("id0").style.display = "none";
//document.getElementById("sd0").style.display = "none";
</script>
</div>
</td>
<td class="right">
<div class="rightcontainer">
<table border="0" cellspacing='0' class="datalist">
<thead>
<tr>
<th style="text-align:center">标题</th>
<th nowrap='nowrap'>作者</th>
<th nowrap='nowrap'>发表日期</th>
</tr>
</thead>
<#if article_list??>
<tbody>
<#list article_list! as a>
 <tr style="vertical-align:top">
  <td style="width:100%">
  	<#if a.typeState == false>[原创]<#else>[转载]</#if>
    <a href='${SiteUrl}showArticle.action?articleId=${a.articleId}'>${Util.getCountedWords(a.title!?html,42)}</a>
  </td>
  <td nowrap='nowrap'><a href='${SiteUrl}go.action?loginName=${a.loginName}' target='_blank'>${a.userTrueName!?html}</a></td>
  <td nowrap='nowrap'>${a.createDate?string('yyyy-MM-dd HH:mm:ss') }</td>
  </tr>
 </#list>
</tbody>
</#if>
</table>
</div>
<#if pager??>
<div class='pager'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>
</#if>
</td>
</tr>
</table>