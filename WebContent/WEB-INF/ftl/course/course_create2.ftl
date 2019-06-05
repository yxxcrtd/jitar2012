<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>集体备课 <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}precourse.css" />
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  <script type='text/javascript'>
  //<![CDATA[
  function selAll(o)
  {
    for(i=0;i<document.getElementsByName('guid').length;i++)
    {
        document.getElementsByName('guid')[i].checked = o.checked;
    }
  }  
  
function selectUser()
{
  url = '${SiteUrl}selectUser.py?showgroup=1&singleuser=0&inputUser_Id=memberId&inputUserName_Id=memberName'
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
}

  //]]>
  </script>
 </head>
 <body>
<#include '/WEB-INF/ftl/site_head.ftl'>

<div class='course_title'><a href='${SiteUrl}p/${prepareCourseId}/${prepareCourseStageId?default('0')}/'>${prepareCourse.title!?html}</a></div>

<div class="box">
  <div class="box_head">
    <div class="box_head_left">&nbsp;<img src="../../${ContextPath}css/index/j.gif">&nbsp;【${prepareCourse.title!}】备课参与人员管理</div>
  </div>
  <div class="box_content" id='div1'> 
<form method="post" name='pc' action='createPreCourse2.py?prepareCourseId=${prepareCourseId}'>
<input type='hidden' name='cmd' value='' />
<table style="width:100%" cellspacing='0'>
<tr valign='top'>
<td style="width:110px" class='fontbold'>已参加人员：</td><td>
<#if user_list?? >
<table border='0' style='background:#EEE;width:90%' cellspacing='1' cellpadding='2'>
<tr>
<td style='width:17px;'><input type='checkbox' onclick='selAll(this)' /></td>
<td class='fontbold'>所在机构</td>
<td class='fontbold'>真实姓名</td>
<td class='fontbold'>登录名</td>
</tr>
<#list user_list as u>
<tr style='background:#FFF'>
<td><input type='checkbox' name='guid' value='${u.userId}' /></td>
<td>${u.unitTitle!}</td>
<td><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></td>
<td><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.loginName}</a></td>
</tr>
</#list>
</table>
</#if>
</td>
</tr>
<tr>
<td class='fontbold' style='padding-top:10px;'></td><td style='padding-top:10px;'>
<input type='button' value='删除所选用户' onclick='this.form.cmd.value="delete";this.form.submit();' />
<input type="button" value=" 返  回 " onclick="window.location.href='${SiteUrl}p/${prepareCourseId}/${prepareCourseStageId?default('0')}/'" />
</td>
</tr>

<tr>
<td class='fontbold' style='padding-top:10px;'>添加新用户：</td><td style='padding-top:10px;'>
<input name='memberId' id='memberId' type='hidden' />
<input id='memberName' style='width:88%' readonly='readonly' />
<br/>
<input type='button' value='选择用户' onclick='selectUser()' />
<input type='button' value='添加用户' onclick='this.form.cmd.value="add";if(this.form.memberId.value == ""){alert("请输入用户登录名。");return false; }else{this.form.submit();}' />
<input type="button" value=" 返  回 " onclick="window.location.href='${SiteUrl}p/${prepareCourseId}/${prepareCourseStageId?default('0')}/'" />

</td>
</tr>
</table>

</form>
</div>
</div>
<div style='clear:both;'></div>
<#include ('/WEB-INF/ftl/footer.ftl') >
</body>
</html>