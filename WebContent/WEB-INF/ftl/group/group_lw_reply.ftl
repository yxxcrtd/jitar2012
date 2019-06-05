<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>回复留言</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <style>
  .leaveword_reply {
    border: 1px solid #dddddd;
  }
  .leaveword_reply_title {
    margin-top: 4px;
  }
  .leaveword_reply_content {
    margin-bottom: 4px;
  }
  </style>
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>协作组管理首页</a>
  &gt;&gt; <a href='groupLeaveword.action?cmd=list&amp;groupId=${group.groupId}'>协作组留言管理</a>
  &gt;&gt; <span>回复留言: '${leaveword.title!?html}'</span> 
</div>
<br/>

<form action='groupLeaveword.action' method='post'>
  <table class='listTable' cellspacing='1'>
    <tr>
      <td>
        <div>
        <#if leaveword.userId??>
          <a href='${SiteUrl}go.action?loginName=${leaveword.loginName!}' target='_blank'>${leaveword.nickName!?html}</a>
        <#else>
          ${leaveword.nickName!} 
        </#if>
          : ${leaveword.title!?html} (留言于: ${leaveword.createDate})
        </div>
        <div style='margin:4px 20px 4px 20px;background-color: #ececec; border:1px solid gray'>
          ${leaveword.content!}
        </div>
        
      <#if leaveword.reply??>
        <div>当前回复为:</div>
        <div style='margin:4px 20px 4px 20px;background-color: #ececec; border:1px solid gray'>
          ${leaveword.reply!}
        </div>
      <#else>
        <div>(当前没有对此留言的回复)</div>
      </#if>
      </td>
    </tr>
    <tr>
      <td>
          您对此留言的回复:<br/>
        <textarea name='reply' cols='90' rows='6'></textarea>
      </td>
    </tr>
    <tr>
      <td>
        <input type='hidden' name='cmd' value='save_reply' />
        <input type='hidden' name='groupId' value='${group.groupId}' />
        <input type='hidden' name='leavewordId' value='${leaveword.id}' />
        <input class="button" type="submit" value="  确 定  " />
        <input class="button" type="button" value=" 返 回 "
          onclick="window.history.back()" />
      </td>
    </tr>
  </table>
</form>
   
</body>
</html>
