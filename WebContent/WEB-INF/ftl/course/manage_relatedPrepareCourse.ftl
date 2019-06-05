<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>相关集备管理</title>
  <link rel="stylesheet" type="text/css" href="../../css/manage.css" />
  <script type="text/javascript">
function selAll(o)
{
 var guids = document.getElementsByName("guid")
 for(i = 0;i<guids.length;i++)
 {
  guids[i].checked = o.checked;
 }
}  
  </script>
</head>
<body>
<#include '/WEB-INF/ftl/course/preparecourse_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='manage_pc.py?prepareCourseId=${prepareCourse.prepareCourseId}'>【${prepareCourse.title}】管理首页</a>
  &gt;&gt; <span>相关集备管理</span> 
</div>

<form name='theForm' method='post'>
<table class='listTable' cellspacing="1">
  <thead>
  <tr>
    <th width='32'><input name='x' type='checkbox' onclick='selAll(this)' /></th>
    <th width='35%'>备课名称</th>
    <th>创建人</th>    
    <th>主备人</th>
    <th>学科</th>
    <th>学段</th>
    <th>开始时间</th>
  </tr>
  </thead>
  <tbody>
<#list preparecourse_list as pc>
  <tr>
    <td><input type='checkbox' name='guid' value='${pc.prepareCourseId}' /></td>
    <td><a href='${SiteUrl}p/${pc.prepareCourseId}/0/' target='_blank'>${pc.title}</a></td>
<#assign c = Util.userById(pc.createUserId)>
<td><a href='${SiteUrl}go.action?loginName=${c.loginName}' target='_blank'>${c.trueName?html}</a></td>
<#assign leader = Util.userById(pc.leaderId)>
<td><a href='${SiteUrl}go.action?loginName=${leader.loginName}' target='_blank'>${leader.trueName}</a></td>
<#assign grad = Util.gradeById(pc.gradeId) >
<td>${grad.gradeName}</td>
<td>${Util.subjectById(pc.metaSubjectId!).msubjName!?html}</td>
<td>${pc.startDate?string("yyyy-MM-dd")}</td>
  </tr>
  </#list>
  </tbody>
</table>
<div class='pager'>
<#include 'pager.ftl'>
</div>

<div class='funcButton'>
  <input type='hidden' name='cmd' value='' />
  <input type='button' class='button' value=' 添 加 ' onclick='searchAdd()' />
  <input type='button' class='button' value=' 全 选 ' onclick='this.form.x.click()' />
  <input type='button' class='button' value='删除相关集备' onclick='deletecourse(this.form)' />
</div>
</form>
<script type="text/javascript">
function deletecourse(oForm){
	var bChoose=false;
	var guids = document.getElementsByName("guid");
	 for(i = 0;i<guids.length;i++)
	 {
	   if(guids[i].checked)
	   {
	     bChoose=true;
	     break;
	   }
	 }
	 if(!bChoose){
	 	alert("请选择要删除的相关集备");
	 	return;
	 }
    if(confirm('你真的要删除相关集备吗?'))
    {
        oForm.cmd.value="delete";
        oForm.submit();
    }
}

function searchAdd()
{
	window.open('${SiteUrl}manage/course/searchRelatedPrepareCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}','_blank','left=100,top=50,height=450,width=800,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
}
</script>
</body>
</html>
