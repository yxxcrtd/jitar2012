<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <title>添加/修改群组公告</title>
    <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  </head>
  
  <body style="margin-top: 20px;">
    <h2>创建讨论室</h2>

    <form action='groupTalkRoom.action' method='post'>
    <#if __referer??>
		<input type='hidden' name='__referer' value='${__referer}' />
 	</#if>
      <table class='listTable' cellspacing='1'>
        <tr>
          <td align="right"><b>讨论室名称：</b></td>
          <td>
            <input type="text" name="roomName" size="30"/>
          </td>
        </tr>
        <tr>
          <td align="right" valign="top"><b>讨论室简介：</b></td>
          <td>
            <textarea name="roomInfo" cols='50' rows='10'></textarea>
          </td>
        </tr>
        <tr>
          <td></td>
          <td>
            <input type='hidden' name='cmd' value='saveRoom' />
            <input type='hidden' name='groupId' value='${group.groupId!}' />
            <input class="button" type="submit" value=" 确  定 " />&nbsp;&nbsp;
            <input class="button" type="button" value="取消返回" onclick="window.history.back()" />
          </td>
        </tr>
      </table>
    </form>
    
  </body>
  
</html>
