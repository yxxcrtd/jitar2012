<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="alternate" type="application/rss+xml" title="Recent Changes" href="/rss.py?type=article" />
  <title></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script type="text/javascript">
  function doPost(strCmdType)
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
    document.getElementById("fForm").cmdtype.value = strCmdType;
    document.getElementById("fForm").submit();
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
  &gt;&gt; <a href='${SiteUrl}manage/action/actionlist.py?type=owner'>活动管理</a>
</div>
<#if action_list??>
<form method='post' id='fForm' action='${SiteUrl}manage/action/modi_action_status.py'>
<input type='hidden' name='cmdtype' value='' />
<table class="listTable">
<thead>
 <tr>
 <th width='1%'><input type='checkbox' onclick='selAll(this)' /></th>
 <th>活动标题</th>
 <th>活动限制人数</th>
 <th>报名人数</th>
 <th>状态</th>
 <th>类型</th>
 <th>创建时间</th>
<th>活动开始时间</th>
<th>活动结束时间</th>
<th>报名截止时间</th>
<th></th>
</tr>
</thead>
<tbody>
 <#list action_list as a>
 <tr>
 <td align='center'><input type='checkbox' name='guid' value='${a.actionId}' /></td>
 <td>
 <#if a.ownerType == 'course' >
 <a target='_blank' href='${SiteUrl}p/${a.ownerId}/0/py/show_preparecourse_action_content.py?actionId=${a.actionId}'>${a.title!?html}</a>
 <#else>
 <a target='_blank' href='${SiteUrl}showAction.action?actionId=${a.actionId}'>${a.title!?html}</a>
 </#if>
 </td>
 <td>${a.userLimit}</td>
 <td>${a.attendCount}</td>
 <td>
<#if a.status == 0 >
正常
<#elseif a.status ==1 >
待审批
<#elseif a.status ==2 >
已经关闭
<#elseif a.status ==3 >
锁定
<#elseif a.status ==4 >
名单已经打印
<#else>
未定义
</#if>
</td>
<td>
<#if a.ownerType == 'user' ><a target='_blank' href='${SiteUrl}go.action?userId=${a.ownerId}'>个人</a>
<#elseif a.ownerType == 'group' ><a target='_blank' href='${SiteUrl}go.action?groupId=${a.ownerId}'>群组</a>
<#elseif a.ownerType == 'preparecourse' >
<a target='_blank' href='${SiteUrl}go.action?courseId=${a.ownerId}'>集备</a>
<#elseif a.ownerType == 'subject' >
<a target='_blank' href='${SiteUrl}go.action?id=${a.ownerId}'>学科</a>
<#else>未知
</#if>
</td>
 <td>${a.createDate!?string('yyyy-MM-dd HH:mm')}</td>
 <td>${a.startDateTime!?string('yyyy-MM-dd HH:mm')}</td>
 <td>${a.finishDateTime!?string('yyyy-MM-dd HH:mm')}</td>
 <td>${a.attendLimitDateTime!?string('yyyy-MM-dd HH:mm')}</td>
 <td><a href='${SiteUrl}actionEdit.action?actionId=${a.actionId}' target='_blank'>管理</a></td>
 </tr> 
 </#list>
 </tbody>
 </table>
 <div>
 <input class='button' type='button' value='删除活动' onclick='doPost("-2")' />
 <input class='button' type='button' value='锁定活动' onclick='doPost("3")' />
 <input class='button' type='button' value='关闭活动' onclick='doPost("2")' />
 <input class='button' type='button' value='设为已打印状态' onclick='doPost("4")' />
 <input class='button' type='button' value='设为正常' onclick='doPost("0")' />
 </div>
 </form>
 </#if>
 <div style='text-align:center'>
 <#include "pager.ftl">
 </div>
 </div>
 </body>
</html>