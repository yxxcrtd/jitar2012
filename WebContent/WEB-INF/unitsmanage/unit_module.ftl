<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
</head>
<body>
<h2>机构页面模块管理</h2>
<form method='post' style='padding-left:20px'>
<div><label><input type='checkbox' name='module' value='最新动态' <#if unit.moduleName?? && (unit.moduleName?index_of('最新动态')>-1) >checked='checked' </#if>/>最新动态</label></div>
<div><label><input type='checkbox' name='module' value='图片新闻' <#if unit.moduleName?? && (unit.moduleName?index_of('图片新闻')>-1) >checked='checked' </#if>/>图片新闻</label></div>
<div><label><input type='checkbox' name='module' value='本站公告' <#if unit.moduleName?? && (unit.moduleName?index_of('本站公告')>-1) >checked='checked' </#if>/>本站公告</label></div>
<div><label><input type='checkbox' name='module' value='机构文章' <#if unit.moduleName?? && (unit.moduleName?index_of('机构文章')>-1) >checked='checked' </#if>/>机构文章</label></div>
<div><label><input type='checkbox' name='module' value='机构资源' <#if unit.moduleName?? && (unit.moduleName?index_of('机构资源')>-1) >checked='checked' </#if>/>机构资源</label></div>
<div><label><input type='checkbox' name='module' value='机构图片' <#if unit.moduleName?? && (unit.moduleName?index_of('机构图片')>-1) >checked='checked' </#if>/>机构图片</label></div>
<div><label><input type='checkbox' name='module' value='机构视频' <#if unit.moduleName?? && (unit.moduleName?index_of('机构视频')>-1) >checked='checked' </#if>/>机构视频</label></div>
<div><label><input type='checkbox' name='module' value='机构简介' <#if unit.moduleName?? && (unit.moduleName?index_of('机构简介')>-1) >checked='checked' </#if>/>机构简介</label></div>
<div><label><input type='checkbox' name='module' value='校长寄语' <#if unit.moduleName?? && (unit.moduleName?index_of('校长寄语')>-1) >checked='checked' </#if>/>校长寄语</label></div>
<div><label><input type='checkbox' name='module' value='统计信息' <#if unit.moduleName?? && (unit.moduleName?index_of('统计信息')>-1) >checked='checked' </#if>/>统计信息</label></div>
<div><label><input type='checkbox' name='module' value='调查投票' <#if unit.moduleName?? && (unit.moduleName?index_of('调查投票')>-1) >checked='checked' </#if>/>调查投票</label></div>
<div><label><input type='checkbox' name='module' value='友情链接' <#if unit.moduleName?? && (unit.moduleName?index_of('友情链接')>-1) >checked='checked' </#if>/>友情链接</label></div>
<div><label><input type='checkbox' name='module' value='机构协作组' <#if unit.moduleName?? && (unit.moduleName?index_of('机构协作组')>-1) >checked='checked' </#if>/>机构协作组</label></div>
<div><label><input type='checkbox' name='module' value='机构集备' <#if unit.moduleName?? && (unit.moduleName?index_of('机构集备')>-1) >checked='checked' </#if>/>机构集备</label></div>

<div style='padding:6px'><input class='button' type='submit' value=' 保  存 ' /></div>
</form>
</body>
</html>
