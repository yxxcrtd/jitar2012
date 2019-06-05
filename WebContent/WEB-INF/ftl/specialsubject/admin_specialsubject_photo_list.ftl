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
<#if photo_list?? >
<form method='post'>
<table class="listTable">
<thead>
<tr>
<th width='1%'><input name='x' type='checkbox' onclick='selAll(this)' /></th>
<th>图片标题</th>
<th>创建人</th>
</tr>
</thead>                           
<tbody>
<#list photo_list as p >
<#assign c = Util.userById(p.userId)>
<tr>
<td align='center'>
<input type='checkbox' name='guid' value='${p.photoId}' />
</td>
<td><a href='${SiteUrl}go.action?photoId=${p.photoId}' target='_blank'>${p.title?html}</a></td>
<td><a href='${SiteUrl}go.action?loginName=${c.loginName}' target='_blank'>${c.trueName?html}</a></td>
</tr>
</#list>
</tbody>
</table>
<div>
<input type='button' class='button' value='全部选择' onclick='this.form.x.click()' />
<input type='submit' class='button' value='删除专题图片' />
</div>
</form>
</#if>
 <div style='text-align:center'>
 <#include "/WEB-INF/ftl/pager.ftl">
 </div>
</body>
</html>