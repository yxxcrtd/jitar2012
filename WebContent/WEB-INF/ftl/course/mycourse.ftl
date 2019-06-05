<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="alternate" type="application/rss+xml" title="Recent Changes" href="/rss.py?type=article" />
  <title></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script type="text/javascript">
    function replay(s)
        {
    
        var hasSelected = false;
        c = document.getElementsByName("guid")
         for(i = 0;i<c.length;i++)
         {
           if(c[i].checked) hasSelected = true
         }
         
        if(hasSelected == false)
        {
         alert("请选择一个备课。")
         return;
        }
        
        
     document.forms[0].cmd.value = s;
     document.forms[0].submit();
    }

  
  function selAll(obj)
  {
     c = document.getElementsByName("guid")
     for(i = 0;i<c.length;i++)
     {
      c[i].checked = obj.checked;
     }
  }
  
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
 <div style='padding:2px'>
 <div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='${SiteUrl}manage/course/mycourse.py'>我发起的备课</a>
</div>


<#if preparecourse_list?? >
<form method='post'>
<input type='hidden' name='cmd' value='' />
<table class="listTable">
<thead>
<tr>
<th width='1%'><input type='checkbox' onclick='selAll(this)' /></th>
<th>备课名称</th>
<th>创建日期</th>
<th>学段</th>
<th>学科</th>
<th>修改</th>
</tr>
</thead>
<tbody>
<#list preparecourse_list as pc>
<tr>
<td align='center'>
<input type='checkbox' name='guid' value='${pc.prepareCourseId}' />
</td>
<td>
<a href='${SiteUrl}p/${pc.prepareCourseId}/0/' target='_blank'>${pc.title}</a>
</td>
<td>${pc.createDate?string("yyyy-MM-dd HH:mm")}</td>
<#assign grad = Util.gradeById(pc.gradeId) >
<td>${grad.gradeName}</td>
<td>${Util.subjectById(pc.metaSubjectId!).msubjName!?html}</td>
<td>
<a href='${SiteUrl}manage/course/manage_createPrepareCourse.py?prepareCourseId=${pc.prepareCourseId}' target='main'>修改</a>
<#--
<a href='createPreCourse.py?prepareCourseId=${pc.prepareCourseId}' target='_blank'>修改</a>
-->
<a href='manage_pc.py?prepareCourseId=${pc.prepareCourseId}' target='_top'>管理</a>
</td>
</tr>
</#list>
</tbody>
</table>
<div>
<input type='submit' class='button' value='删除备课' />
</div>
</form>
</#if>
 <div style='text-align:center'>
 <#include "../action/pager.ftl">
 </div>
</body>
</html>