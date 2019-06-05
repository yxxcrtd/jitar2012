<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
 </head>
 <body>
 <form target='bottom' method='GET' action='user_select_bottom.action'>
 <input type='hidden' name='type' value='${type}' />
 <input type='hidden' name='idTag' value='${idTag}' />
 <input type='hidden' name='titleTag' value='${titleTag}' />
 <input type='hidden' name='back' value='${back}' />
 关键字：<input name='k' /> 
 <select name='f' />
 <option value='trueName'>真实姓名</option>
 <option value='loginName'>登录名</option>
 <option value='unitTitle'>机构名称</option>
 </select> 
 <input type='submit' value=' 查  找 ' />
 <input type='button' value='确认返回' onclick='window.parent.frames["bottom"].getUser()' />
 <input type='button' value='关闭返回' onclick='window.parent.close();' />
 </form>
</body>
</html>