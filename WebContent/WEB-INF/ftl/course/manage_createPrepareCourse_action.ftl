<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>备课管理</title>
  <link rel="stylesheet" type="text/css" href="../../css/manage.css" />
</head>
<body>
<#include '/WEB-INF/ftl/course/preparecourse_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='manage_pc.py?prepareCourseId=${prepareCourse.prepareCourseId}'>【${prepareCourse.title}】管理首页</a>
  &gt;&gt; <span>活动管理</span> 
</div>
<form  method='post' id='fForm'>
<input type='hidden' name='cmdtype' value='' />
<table class="listTable">
<thead>
 <tr>
 <th width='1%'><input id='_s' type='checkbox' onclick='selAll(this)' /></th>
 <th>活动标题</th>
 <th>发起人</th>
 <th>状态</th>
 <th>创建时间</th>
  <th>活动限制人数</th>
 <th>报名人数</th>
</tr>
</thead>
<tbody>
 <#list action_list as a>
 <tr>
 <td align='center'><input type='checkbox' name='guid' value='${a.actionId}' /></td>
 <td><a target='_blank' href='${SiteUrl}p/${prepareCourse.prepareCourseId}/0/py/show_preparecourse_action_content.py?actionId=${a.actionId}'>${a.title!?html}</a></td>
 <td><a href='${SiteUrl}go.action?loginName=${a.loginName}'>${a.trueName}（${a.loginName}）</a></td>
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
 <td>${a.createDate!?string('yyyy-MM-dd HH:mm')}</td>
  <td>${a.userLimit}</td>
 <td>${a.attendCount}</td>
 </tr> 
 </#list>
 </tbody>
 </table>
 <div>
 <input class='button' type='button' value='全部选中' onclick='selAll(document.getElementById("_s"));document.getElementById("_s").click();' />
 <input class='button' type='button' value='删除活动' onclick='doPost("-2")' />
 <input class='button' type='button' value='锁定活动' onclick='doPost("3")' />
 <input class='button' type='button' value='关闭活动' onclick='doPost("2")' />
 <input class='button' type='button' value='设为已打印状态' onclick='doPost("4")' />
 <input class='button' type='button' value='设为正常' onclick='doPost("0")' />
 </div>
</form>
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
</body>
</html>
