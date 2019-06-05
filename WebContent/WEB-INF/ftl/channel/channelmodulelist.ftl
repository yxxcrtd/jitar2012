<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
</head>
<script>
function AddModule()
{
 mtObject = document.getElementById("moduleType")
 mt = mtObject.options[mtObject.selectedIndex].value;
 if(mt=="")
 {
  alert("请先选择一个模块类型。");
  return;
 }
 window.location.href='channel.action?cmd=addmodule&channelId=${channel.channelId}&mt=' + mt;
}

function ModuleShowCount()
{
 document.getElementById("mForm").act.value="showCount";
 document.getElementById("mForm").submit();
}

function checkSelectedItem()
{
  gs = document.getElementsByName("guid");
  for(i=0;i<gs.length;i++)
  {
   if(gs[i].checked) return true;
  }
  
  alert("请选择一个模块。");
  return false;
}
</script>
<h2>[${channel.title!?html}]频道模块管理</h2>
<form method="POST" id="mForm" action="channel.action">
<input type="hidden" name="cmd" value="savemodulelist" />
<input type="hidden" name="act" value="delete" />
<input type="hidden" name="channelId" value="${channel.channelId}" />
<table class='listTable' cellspacing='1'>
<thead>
<tr style="text-align:left;">
<th style="width:17px"></th>
<th>模块名称</th>
<th>首页列表显示条数(<span style="color:red">只对列表模块有效</span>)</th>
</tr>
</thead>
<#if moduleList??>
<#list moduleList as m>
<tr>
<td><input type="checkbox" name="guid" value="${m.moduleId}" /></td>
<td><a href="channel.action?cmd=addmodule&channelId=${channel.channelId}&moduleId=${m.moduleId}">${m.displayName!?html}</a></td>
<td><input name="md_${m.moduleId}" value="${m.showCount!?html}"/><input type="hidden" name="md_id" value="${m.moduleId}"/></td>
</tr>
</#list>
</#if>
</table>

<select id="moduleType" style="font-size:13.7px;">
<option value="ArticleList">系统模块-文章列表</option>
<option value="ArticleCategory">系统模块-文章分类列表</option>
<option value="ResourceList">系统模块-上传资源列表</option>
<option value="ResourceCategory">系统模块-资源分类列表</option>
<option value="Bulletin">系统模块-网站公告</option>
<option value="Search">系统模块-搜索</option>
<option value="SubjectNav">系统模块-学科导航</option>
<option value="ChannelNav">系统模块-频道导航</option>
<option value="SiteNav">系统模块-总站导航</option>
<option value="Stat">系统模块-频道统计</option>
<option value="VideoList">系统模块-视频列表</option>
<option value="VideoCategory">系统模块-视频分类</option>
<option value="PhotoList">系统模块-图片列表</option>
<option value="PhotoCategory">系统模块-图片分类</option>
<option value="UserList">系统模块-工作室列表</option>
<option value="GroupList">系统模块-协作组列表</option>
<option value="Logo">系统模块-频道Logo</option>
<option value="Custorm">自定义内容</option>
<option value="CustomCategory">自定义分类模块</option>
<option value="UnitShow">机构展示模块</option>
</select>

<input type="button" value="添加模块" onclick="AddModule()" />
<input type="submit" value="删除模块" onclick="return checkSelectedItem() && confirm('注意：删除模块也将同时删除模板中使用的该模块的标签。\r\n\r\n你真的要删除吗？？')" />
<input type="button" value="保存模块显示条数" onclick="ModuleShowCount()" />
</form>
</body>
</html>