<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>新闻列表</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  
  <script language='javascript'>
	function sel_subNews() {
		var frm=document.getElementById("newsForm");
		frm.cmd.value = "list";
		frm.submit();
	}
  </script>
  
</head>
<body>
<#if jitarColumn??>
<h2>${jitarColumn.columnName!} 栏目内容管理</h2>
<#else>
<h2>栏目内容管理</h2>	
</#if>
  	
<form name='newsForm' id='newsForm' action='?' method='post'>  
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th style="width:20px"></th>
      <th>新闻标题</th>
      <th style="width:100px">发布时间</th>
      <th style="width:40px">是否发布</th>
      <th style="width:60px">阅读数</th>
      <th style="width:100px">操作</th>
    </tr>
  </thead>
  <tbody>
  <#list jc_list as news>
    <tr>
      <td><input type='checkbox' name='newsId' value='${news.columnNewsId}' /></td>
      <td><#if news.picture?? && news.picture !=""><span style="color:#f00">[图]</span> </#if><a target="_blank" href="${SiteUrl}showColumnNews.py?columnNewsId=${news.columnNewsId}&from=preview">${news.title!?html}</a></td>
      <td>${news.createDate?string("yyyy-MM-dd")}</td>
      <td>${news.published?string("发布","<span style='color:red'>未发布</spam>")}</td>
      <td style="text-align:right;">${news.viewCount}</td>
      <td>
        <a href='column_news_edit.py?columnId=${jitarColumn.columnId}&newsId=${news.columnNewsId}'>修改</a> | <a href='?cmd=delete&columnId=${jitarColumn.columnId}&newsId=${news.columnNewsId}'>删除</a>
      </td>
    </tr>
  </#list>
  </tbody>
</table>

<div class='pager'>
  <#include '../inc/pager.ftl' >  
</div>

<div class='funcButton'>
  <input name="cmd" type="hidden" value="" />
  <input name="columnId" type="hidden" value="${jitarColumn.columnId}" />
  <input type='button' class='button' value=' 全 选 ' onclick='select_all();' />
  <input type='button' class='button' value='发布${jitarColumn.columnName!}' onclick='add_a();' />
  <input type='button' class='button' value=' 删 除 ' onclick='delete_a();' />
</div>
</form>

<script>
function select_all() {
  var ids = document.getElementsByName('newsId');
  if (ids == null || ids.length == 0) return;
  var set_checked = false;
  for (var i = 0; i < ids.length; ++i) {
    if (ids[i].checked == false) {
      ids[i].checked = true;
      set_checked = true;
    }
  }
  
  if (set_checked == false) {
    for (var i = 0; i < ids.length; ++i) {
      ids[i].checked = false;
    }
  }
}
function add_a() {
  window.location.href="column_news_edit.py?columnId=${jitarColumn.columnId}";
}
function delete_a() {
  submit_command('delete');
}
function submit_command(cmd) {
  var the_form = document.forms['newsForm'];
  if (the_form == null) {
    alert('Can\'t find form.');
    return;
  }
  the_form.cmd.value = cmd;
  the_form.submit();
}
</script>
</body>
</html>
