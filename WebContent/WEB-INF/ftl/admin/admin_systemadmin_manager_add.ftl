<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
 </head>
 <body>
 <form method='POST'>
<h3>请先选择用户：</h3>
 <div id='utitle'></div>
 <input type='hidden' id='uid' name='uid' />
 <input type='button' value='选择用户' onclick='window.open("${SiteUrl}manage/common/user_select.action?type=multi&idTag=uid&titleTag=utitle","_blank","width=800,height=600,resizable=1,scrollbars=1")' />
 <input type='submit' value='添加管理员' />
 </form>
</body>
</html>