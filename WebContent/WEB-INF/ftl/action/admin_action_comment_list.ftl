<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  <script type="text/javascript">
  var JITAR_ROOT = "${SiteUrl}";
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
     alert("请选择一个讨论。")
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
  
  function deleAll()
  {
     c = document.getElementById("fForm")
     if(c.loginname.value == '')
     {
        alert('请输入要删除人员的登录名。')
        return;
     }
     
     //检查用户是否存在     
    var url = JITAR_ROOT + 'manage/action/admin_validate_user.py?loginName=' + c.loginname.value + '&tmp=' + Math.random();
    new Ajax.Request(url, { 
          method: 'get',
          onSuccess:getPageListSuccess,
          onFailure: getModuleListFailed
        }
      );
  }   
  
   function getPageListSuccess(xhrObject)
   {
     if(xhrObject.responseText == "OK")
     {
     if(window.confirm('您真的要删除 ' + c.loginname.value + ' 的所有回复吗？'))
     {
       c.cmdtype.value='0';
       c.submit();
     }
     }
     else
     {
      alert('操作结果：' + xhrObject.responseText);
     }
   }
    
   function getModuleListFailed(xhrObject)
   {
      alert('错误：' + xhrObject.responseText);
   }
  </script>
 </head> 
 <body>
 <div style='padding:2px'>
 <div class='pos'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='${SiteUrl}manage/action/admin_action_list.py'>活动讨论管理</a>
</div>
<#if action_comment_list??>
<form method='post' id='fForm' action='${SiteUrl}manage/action/admin_delete_action_comment.py'>
<input type='hidden' name='cmdtype' value='' />
输入用户登录名：<input type='text' name='loginname' value='' /><input type='button' value='删除该用户的所有讨论' onclick='deleAll()' class='button' />
<table class="listTable">
<thead>
 <tr>
 <th width='1%'><input type='checkbox' id='_s' onclick='selAll(this)' /></th>
 <th>活动讨论话题</th>
 <th>活动名称</th>
 <th>讨论发布人（真实姓名/登录名）</th>
 <th>参与讨论时间</th>
 <th>IP来源</th>
</tr>
</thead>
<tbody>
 <#list action_comment_list as a>
 <tr style='background:#EEE'>
 <td align='center'><input type='checkbox' name='guid' value='${a.actionReplyId}' /></td>
 <td><a target='_blank' href='${SiteUrl}showAction.action?actionId=${a.actionId}'>${a.topic}</a></td>
 <td><a target='_blank' href='${SiteUrl}showAction.action?actionId=${a.actionId}'>${a.title!?html}</a></td>
 <td onclick='document.getElementById("fForm").loginname.value = "${a.loginName}"' style='cursor:pointer'><a target='_blank' href='${SiteUrl}go.action?loginName=${a.loginName}'>${a.trueName}</a>（<span onclick='document.getElementById("fForm").loginname.value = "${a.loginName}"'>${a.loginName}</span>）</td>
 <td>${a.createDate!?string('yyyy-MM-dd HH:mm')}</td>
 <td>${a.addIp!}</td>
 </tr>
 <tr>
 <td colspan='6'>${a.content!}</td>
 </tr>
 </#list>
 </tbody>
 </table>
 <div>
 <input class='button' type='button' value='全部选中' onclick='selAll(document.getElementById("_s"));document.getElementById("_s").click();' />
 <input class='button' type='button' value='删除讨论' onclick='doPost("1")' />
 </div>
 </form>
 </#if>
 <div style='text-align:center'>
 <#include "pager.ftl">
 </div>
 </div>
 </body>
</html>