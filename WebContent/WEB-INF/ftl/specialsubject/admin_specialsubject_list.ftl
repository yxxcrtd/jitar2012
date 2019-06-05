<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script type="text/javascript">
    function doPost(oF,m)
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
        
     oF.cmd.value = m;
     oF.submit();
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
  &gt;&gt; 专题管理
</div>
<#if ss_list?? >
<form method='post'>
<table class="listTable">
<thead>
<tr>
<th width='1%'><input name='x' type='checkbox' onclick='selAll(this)' /></th>
<th>专题名称</th>
<th>创建人</th>
<th>Logo标识</th>
<th>创建时间</th>
<th>有效时间</th>
<th>管理</th>
</tr>
</thead>                           
<tbody>
<#list ss_list as ss >
<tr>
<td align='center'>
<input type='checkbox' name='guid' value='${ss.specialSubjectId}' />
</td>
<td><a href='${SiteUrl}specialSubject.action?specialSubjectId=${ss.specialSubjectId}' target='_blank'>${ss.title?html}</a></td>
<#assign c = Util.userById(ss.createUserId)>
<td><a href='${SiteUrl}go.action?loginName=${c.loginName!}' target='_blank'>${c.trueName!?html}</a></td>
<td>
<#if ss.logo??>
<a href='${ss.logo}' target='_blank'>查看</a>
</#if>
</td>
<td>${ss.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
<td>${ss.expiresDate?string("yyyy-MM-dd")}</td>
<td><nobr>
<a href='${SiteUrl}manage/specialsubject/admin_specialsubject_article_list.py?specialSubjectId=${ss.specialSubjectId}' target='main'>管理专题文章</a> | 
<a href='${SiteUrl}manage/specialsubject/admin_specialsubject_photo_list.py?specialSubjectId=${ss.specialSubjectId}' target='main'>管理专题图片</a> | 
<a href='${SiteUrl}manage/specialsubject/admin_specialsubject_video_list.py?specialSubjectId=${ss.specialSubjectId}' target='main'>管理专题视频</a> | 
<a href='${SiteUrl}manage/specialsubject/admin_specialsubject_edit.py?specialSubjectId=${ss.specialSubjectId}' target='_blank'>修改</a>
</nobr></td>
</tr>
</#list>
</tbody>
</table>
<div>
<input type='button' class='button' value='全部选择' onclick='this.form.x.click()' />
<input type='submit' class='button' value='删除专题' />
</div>
</form>
</#if>
 <div style='text-align:center'>
 <#include "/WEB-INF/ftl/pager.ftl">
 </div>
</body>
</html>