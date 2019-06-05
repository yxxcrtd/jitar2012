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

  </script>
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  <script src='${SiteUrl}js/subject/util.js' type='text/javascript'></script>
 </head> 
<body>
<h3>专题管理</h3> 
<#if ss_list?? >
<form method='post'>
<table class="listTable">
<thead>
<tr>
<th width='1%'><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid");SetRowColorByName();' id='chk' /></th>
<th>专题名称</th>
<th>创建人</th>
<th>专题Logo</th>
<th>创建时间</th>
<th>有效时间</th>
<th>管理</th>
</tr>
</thead>                           
<tbody>
<#list ss_list as ss >
<tr>
<td align='center'>
<input type='checkbox' name='guid' value='${ss.specialSubjectId}' onclick='SetRowColor(event)' />
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
<a href='specialsubject_article_list.py?id=${subject.subjectId}&specialSubjectId=${ss.specialSubjectId}'>文章</a> | 
<a href='specialsubject_photo_list.py?id=${subject.subjectId}&specialSubjectId=${ss.specialSubjectId}'>图片</a> | 
<a href='${SiteUrl}mod/topic/admin_topic_list_by_object.py?guid=${ss.objectGuid}&type=specialsubject'>讨论</a> | 
<a href='${SiteUrl}mod/vote/manage_list.action?guid=${ss.objectGuid}&type=specialsubject' target='_blank'>投票</a> | 
<a href='createspecialsubject.py?id=${subject.subjectId}&specialSubjectId=${ss.specialSubjectId}'>修改</a>
</nobr></td>
</tr>
</#list>
</tbody>
</table>
<div>
<input class='button' type='button' value='全部选中' onclick='${"chk"}.click();SetRowColorByName();' />
<input type='submit' class='button' value='删除专题' />
</div>
</form>
</#if>
 <div style='text-align:center'>
 <#include "/WEB-INF/ftl/pager.ftl">
 </div>
</body>
</html>