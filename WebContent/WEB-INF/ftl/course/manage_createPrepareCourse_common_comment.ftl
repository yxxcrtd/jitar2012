<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理</title>
  <link rel="stylesheet" type="text/css" href="../../css/manage.css" />
</head>
<body>
<#include '/WEB-INF/ftl/course/preparecourse_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='manage_pc.py?prepareCourseId=${prepareCourse.prepareCourseId}'>【${prepareCourse.title}】管理首页</a>
  &gt;&gt; <span>共案评论管理</span> 
</div>
<form name='theForm' method='post'>
<table class='listTable' cellspacing="1">
  <thead>
  <tr>
    <th width='17'>&nbsp;</th>
    <th>发表人</th>
    <th>发表时间</th>
  </tr>
  </thead>
  <tbody>
<#list comment_list as r>
  <#assign u = Util.userById(r.userId) >
  <tr style="background:#EEE">
    <td><input type='checkbox' name='commentId' value='${r.id}' /></td>
    <td><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.trueName!}</a></td>    
    <td>${r.createDate?string('yyyy-MM-dd HH:mm:ss')}</td>    
  </tr>
  <tr>
  <td colspan='3'>${r.content!}</td>
  </tr>
  </#list>
  </tbody>
</table>
<div class='pager'>
<#include 'pager.ftl'>
</div>
<div class='funcButton'>
  <input type='hidden' name='cmd' value='' />
  <input type='button' class='button' value=' 全 选 ' onclick='select_all()' />
  <input type='button' class='button' value='删除评论' onclick='delete_post(this.form)' />
</div>
</form>
<script>
var checkedStatus=true;
function select_all() {
  var ids = document.getElementsByName('commentId');
  for(i = 0;i<ids.length;i++)
  {
    ids[i].checked = checkedStatus
  }
  checkedStatus =!checkedStatus  
}

function delete_post(oForm)
{
	if(!hasSelectdItem())
	{
		alert("请删除一个评论。");
		return;
	}

    if(confirm('你真的要删除吗?'))
    {
        oForm.cmd.value="delete"
        oForm.submit()
    }
}

function hasSelectdItem()
{
 var ids = document.getElementsByName('commentId');
  for(i = 0;i<ids.length;i++)
  {
    if(ids[i].checked) return true;
  }
  return false;
}

</script>
</body>
</html>
