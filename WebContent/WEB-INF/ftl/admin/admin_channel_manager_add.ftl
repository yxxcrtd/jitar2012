<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
 </head>
 <body>
 <form method='POST'>
<h3>请先选择用户：</h3>
 <select name='controlType'>
 <option value=''>请选择管理员类别</option>
 <option value='11'>频道系统管理员</option>
 <option value='12'>频道用户管理员</option>
 <option value='13'>频道内容管理员</option>
 </select>
 <input type='button' value='选择用户' onclick='window.open("${SiteUrl}manage/common/user_select.action?type=multi&idTag=uid&titleTag=utitle","_blank","width=800,height=600,resizable=1,scrollbars=1")' />
 </td>
 <td>
 <div id='utitle' style="padding:10px 0;color:red"></div>
 <input type='hidden' id='uid' name='uid' />
 </td>
 </tr>
 </table>
 <input type='submit' value='添加管理员' />
 </form>
</body>
</html>