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
  &gt;&gt; <span>共案编辑历史管理</span> 
</div>
<form name='theForm' method='post'>
<table class='listTable' cellspacing="1">
  <thead>
  <tr>
    <th width='32'>&nbsp;</th>
    <th>查看</th> 
    <th width='50%'>撰写人</th>
    <th>撰写时间</th>
    <th>操作</th>
  </tr>
  </thead>
<#if edit_list??>
<tbody>
<#list edit_list as e>
  <#assign u = Util.userById(e.editUserId) >
  <#if prepareCourse.prepareCourseEditId == e.prepareCourseEditId >
  <tr style='font-weight:bold;color:#F00;background:#eee'>
  <#else>
  <tr>
  </#if>
    <td><input type='checkbox' name='prepareCourseEditId' value='${e.prepareCourseEditId}' /></td>
    <td><a target='_blank' href='${SiteUrl}p/${prepareCourse.prepareCourseId}/0/py/show_preparecourse_history_content.py?prepareCourseEditId=${e.prepareCourseEditId}'>查看历史内容</a></td>
    <td><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}（${u.loginName}）</a></td>
    <td>${e.editDate?string('yyyy-MM-dd HH:mm:ss')}</td>
    <td>
    <#if prepareCourse.prepareCourseEditId != e.prepareCourseEditId >
    <a href='manage_createPrepareCourse_commoncourse.py?prepareCourseId=${prepareCourse.prepareCourseId}&amp;editId=${e.prepareCourseEditId}'>恢复为共案</a>
    </#if>
    </td>
  </tr>
  </#list>
  </tbody>
</#if>
</table>
<div class='funcButton'>
  <input type='hidden' name='cmd' value='' />
  <input type='button' class='button' value=' 全 选 ' onclick='select_all()' />
  <input type='button' class='button' value=' 删 除 ' onclick='doPost(this.form,1)' />
</div>
</form>
<script>
var checkedStatus=true;
function select_all() {
  var ids = document.getElementsByName('prepareCourseEditId');
  for(i = 0;i<ids.length;i++)
  {
    ids[i].checked = checkedStatus
  }
  checkedStatus =!checkedStatus  
}

function doPost(oForm,m)
{
if(m==1)
{
 if(!hasSelectdItem())
 {
  alert("请选择一个共案历史。");
  return;
 }
}
    if(confirm('你真的要删除吗?'))
    {
        oForm.cmd.value=m
        oForm.submit()
    }
}

function hasSelectdItem()
{
 var ids = document.getElementsByName('prepareCourseEditId');
  for(i = 0;i<ids.length;i++)
  {
    if(ids[i].checked) return true;
  }
  return false;
}


</script>
</body>
</html>
