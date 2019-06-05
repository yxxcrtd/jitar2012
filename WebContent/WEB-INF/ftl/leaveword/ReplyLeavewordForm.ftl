<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
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
<body style="margin-top: 20px;">
    <h2>回复留言</h2>
    <form name="list_form" action="leaveword.action" medthod="post">
      <input type='hidden' name='cmd' value='save_reply' />
        <table class="listTable" cellspacing="1" >
        <#if leaveword.reply??>
             <tr>
                <td align="right" valign="top"><b>当前回复：</b></td>
                <td>
                <div style='margin:4px 20px 4px 0px;background-color: #ececec; border:1px solid gray'>
                ${leaveword.reply}
                </div>
                </td>
            </tr>
        </#if>
            <tr>
                <td align="right" valign="top"><b>回复内容:</b></td>
                <td><textarea name="LeavewordContent" cols="50" rows="6" ></textarea></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input class="button" type='hidden' name='leavewordId' value='${leaveword.id}' />   
                    <input class="button" type="submit" value=" 回   复 " />&nbsp;&nbsp;
                    <input class="button"type="button" value="返回列表" onclick="window.location='leaveword.action?cmd=list';" />
                </td>
            </tr>
        </table>
    </form>
</body>