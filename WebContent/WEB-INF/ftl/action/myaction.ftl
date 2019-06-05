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
	     alert("请选择一个活动。")
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
  
  </script>
 </head> 
 <body>
 <div style='padding:2px'>
 <div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='${SiteUrl}manage/action/myaction.py'>我参与的活动</a>
</div>


<script>
function selAll(o)
{
 var guids = document.getElementsByName("guid")
 for(i = 0;i<guids.length;i++)
 {
  guids[i].checked = o.checked;
 }
}


</script>


<#if user_action?? >
<form method='post'>
<input type='hidden' name='cmd' value='' />
<table class="listTable">
<thead>
<tr>
<th width='1%'><input type='checkbox' onclick='selAll(this)' /></th>
<th>活动名称</th>
<th>状态</th>
<th>类型</th>
<th>活动时间</th>
<th>我的状态</th>
</tr>
</thead>
<tbody>
<#list user_action as u>
<tr>
<td align='center'>
<#if u.actionStatus ==0 >
<input type='checkbox' name='guid' value='${u.actionUserId}' />
<#else>
&nbsp;
</#if>
</td>
<td><a target='_blank' href='${SiteUrl}showAction.action?actionId=${u.actionId}'>${u.title}</a></td>
<td>
<#if u.actionStatus == 0 >
正常
<#elseif u.actionStatus ==1 >
待审批
<#elseif u.actionStatus ==2 >
已经关闭
<#elseif u.actionStatus ==3 >
锁定
<#elseif u.actionStatus ==4 >
名单已经打印
<#else>
未定义
</#if>
</td>
<td>
<#if u.ownerType == 'user' ><a target='_blank' href='${SiteUrl}go.action?userId=${u.ownerId}'>个人</a>
<#elseif u.ownerType == 'group' ><a target='_blank' href='${SiteUrl}go.action?groupId=${u.ownerId}'>群组</a>
<#elseif u.ownerType == 'preparecourse' ><a target='_blank' href='${SiteUrl}go.action?courseId=${u.ownerId}'>集备</a>
<#elseif u.ownerType == 'subject' >
<a target='_blank' href='${SiteUrl}go.action?id=${u.ownerId}'>学科</a>
<#else>未知
</#if>
</td>
<td>${u.startDateTime?string("yyyy-MM-dd HH:mm")}</td>
<td>
<#if u.actionUserStatus == 0 >
<span style='color:#F00'>
未回复
</span>
<#elseif u.actionUserStatus ==1 >
已参加
<#elseif u.actionUserStatus ==2 >
已退出
<#elseif u.actionUserStatus ==3 >
已请假
<#else>
未定义
</#if>
</td>
</tr>
</#list>
</tbody>
</table>
<div>
<input type='button' class='button' value='答复（参加）活动' onclick='replay("attend")' />
<input type='button' class='button' value='退出活动' onclick='replay("quit")' />
<input type='button' class='button' value=' 请  假 ' onclick='replay("leave")' />
</div>
</form>
</#if>
 <div style='text-align:center'>
 <#include "pager.ftl">
 </div>
<div style='padding:6px 0;color:#f00;'>说明：名单已经打印的活动，不能再更改状态，请电话联系或者现场进行修改。</div>
</body>
</html>