<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script src='${SiteUrl}js/subject/util.js' type='text/javascript'></script>
  <script>
  function doPost(arg)
  {
  	document.getElementById('oForm').cmd.value=arg;
  	document.getElementById('oForm').submit();
  }
  
function doFilter()
{
    var f = document.getElementById('oForm'); 
    var sch = f.schannel.options[f.schannel.selectedIndex].value;
    var qs = "schannel=" + sch + "&type=filter"
    var url = "channelvideo.action?channelId=${channel.channelId}&";
    window.location.href = url + qs;    
}

  function checkSelectedItem()
  {
    g = document.getElementsByName("guid")
    for(i=0;i<g.length;i++){
     if(g[i].checked) return true;
    }
    alert("请选择一个视频。");
    return false;
  }
  </script>
</head>
<body>
<h2>频道视频管理</h2>
<form action="channelvideo.action" method="get">
<input type="hidden" name="channelId" value="${channel.channelId}" />
<div style="text-align: right; width: 100%;">
关键字：
<input type="text" size="30" name="k" value="${k!?html}" />
<#if !(f??)><#assign f = '0'></#if>
<select name="f">
<option value="title" ${(f == 'title')?string('selected', '')}>视频标题</option>
<option value="uname" ${(f == 'uname')?string('selected', '')}>上传用户</option>
<option value='unitTitle'${(f=='unitTitle')?string(' selected="selected"','')}>用户所属机构</option>
</select>
<select name="schannel">
<option value="">频道视频分类</option>
<#if channel_video_categories??>
<#list channel_video_categories.all as c >
<#assign cp = Util.convertIntFrom36To10(c.parentPath) + c.id?string + "/" >
<option value='${cp}' ${(schannel == cp)?string('selected', '')}>${c.treeFlag2} ${c.name!?html}</option>
</#list>
</#if>
</select>
<input type="submit" class="button" value="检  索" />
</div>
</form>
<form id="oForm" action="?channelId=${channel.channelId}" method="post">
<input type="hidden" name="cmd" value="" />
<table class="listTable" cellSpacing="1">
<thead>
<tr>
<th style="width:17px"><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");SetRowColorByName();' id='chk' /></th>
<th>视&nbsp;&nbsp;频</th>
<th>视频标题</th>
<th>上传者</th>
<th>所在机构</th>
<th>上传日期</th>
<th>
<select name='schannel' onchange='doFilter()'>
<option value=''>频道视频分类</option>
<#if channel_video_categories??>
<#list channel_video_categories.all as c >
<#assign cp = Util.convertIntFrom36To10(c.parentPath) + c.id?string + "/" >
<option value='${cp}' ${(schannel == cp)?string('selected', '')}>${c.treeFlag2} ${c.name!?html}</option>
</#list>
</#if>
</select>
</th>
</tr>
</thead>
<tbody>
<#list videoList as video>
<tr>
<td style="text-align: center;">
<input type="checkbox" name="guid" value="${video.channelVideoId}" onclick='SetRowColor(event)' />
</td>
<td style="text-align: center;">
<a href="${SiteUrl}manage/video.action?cmd=show&amp;videoId=${video.videoId}" target="_blank"><img src="${video.flvThumbNailHref!}" /></a>
</td>
<td style="padding-left: 10px;">
${video.title!?html}
</td>
<td style="text-align: center;">
<a href="${SiteUrl}go.action?loginName=${video.loginName!}" target="_blank">${video.trueName!}</a>
</td>
<td style="text-align: center;"><nobr><a href='${SiteUrl}go.action?unitName=${video.unitName!}' target='_blank'>${video.unitTitle!}</a></nobr></td>
<td style="text-align: center;">
${video.createDate?string("yyyy-MM-dd HH:mm:ss")}
</td>
<td style="text-align: center;">
<#if video.channelCate??>
<#assign cate = Util.getCategory(video.channelCate)>
<#if cate??>
${cate.name!?html}
</#if>
</#if>
</td>
</tr>
</#list>
</tbody>
</table>		
<div style="width: 100%; text-align: right; margin: 3px auto 3px;">
<#include "/WEB-INF/ftl/inc/pager.ftl">
</div>
<div class="funcButton">
<input class="button" type="button" value="全部选择" onClick="${"chk"}.click();SetRowColorByName();" />
<input class="button" type="button" value="从频道中移除" onClick="if(checkSelectedItem()){doPost('remove');}" />
<select name="newCate">
  <option value="">取消分类</option>
  <#if channel_video_categories??>
    <#list channel_video_categories.all as c >
    <#assign cp = Util.convertIntFrom36To10(c.parentPath) + c.id?string + "/" >
    <option value='${cp}'>${c.treeFlag2} ${c.name!?html}</option>
    </#list>
    </#if>
  </select>
  <input class='button' type='button' value='转移到选定分类' onclick="if(checkSelectedItem() && confirm('真的要对这些视频进行重新分类吗？')){doPost('recate');}" />
</div>
</form>
</body>
</html>