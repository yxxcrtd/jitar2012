<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type='text/javascript'>
  function add(news_type)
  {
  	window.location.href='add_news.py?type=' + news_type + '&unitId=${unit.unitId}'
  }
  function openUpload()
  {
    var url = "${SiteUrl}manage/userfm/index.jsp?Type=Image"
    window.open(url,'_blank','width=800,height=600,resizable=1,scrollbars=1')
  }
  
  function SetUrl( url, width, height )
  {
    if(url)
    {
        document.forms[0].logo.value = url;
    }
  }
  function check_all(o)
  {
   ele = document.getElementsByName('guid')
   for(i = 0;i<ele.length;i++)
   {
    ele[i].checked=o.checked;
   }
  }
  </script>
</head>
<body>
<h2>
新闻、公告、动态管理
</h2>
<form method='post' style='padding-left:20px'>
<table class='listTable' cellspacing='1'>
<tr>
<td style='width:17px'><input type='checkbox' onclick='check_all(this)' /></td>
<td><strong>标题</strong></td>
<td><strong>类型</strong></td>
<td><strong>发布日期</strong></td>
<td>&nbsp;</td>
</tr>
<tr>
<#list unit_news_list as news>
<tr>
<td><input type='checkbox' name='guid' value='${news.unitNewsId}' /></td>
<td><a href='${UnitRootUrl}py/show_news.py?unitId=${unit.unitId}&unitNewsId=${news.unitNewsId}' target='_blank'>${news.title}</a></td>
<td>
 <#if news.itemType == 2 >最新动态
 <#elseif news.itemType == 1 >机构公告
 <#elseif news.itemType == 0 >图片新闻
 <#else>
 未定义
 </#if>
</td>
<td>${news.createDate}</td>
<td><a href='${SiteUrl}units/manage/add_news.py?unitId=${unit.unitId}&unitNewsId=${news.unitNewsId}'>修改</a></td>
</tr>
</#list>
</table>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
<input class='button' type='submit' value=' 删  除 ' onclick='return confirm("你真的要删除吗？")' />
</div>
</form>
</body>
</html>