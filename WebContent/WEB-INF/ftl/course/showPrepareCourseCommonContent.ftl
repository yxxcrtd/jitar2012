<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>集体备课 <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}precourse.css" />
</head>
<body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<table style='width:100%'>
<tr valign='top'>
<td style='width:75%;padding:4px'>
<div class="box">
  <div class="box_head">
    <div class="box_head_right"></div>
    <div class="box_head_left">&nbsp;<img src="../../${ContextPath}css/index/j.gif">&nbsp;共案内容</div>
  </div>
  <div class="box_content">  
	<div class='course_title'>【${prepareCourse.title!}】的共案</div>
	<#assign u = Util.userById(prepareCourse.leaderId) >
	<div style='text-align:center'>主备人：<a target='_blank' href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></div>
	<div style='clear:both;height:8px;font-size:0'></div>
	<div style='padding:10px 20px'>
	${prepareCourse.commonContent!}
	</div>
  </div>
</div>

</td>
<td style='width:75%;padding:4px;'>
<div class="box">
  <div class="box_head">
    <div class="box_head_right"></div>
    <div class="box_head_left">&nbsp;<img src="../../${ContextPath}css/index/j.gif">&nbsp;编辑历史</div>
  </div>
  <div class="box_content">
    <#if prepareCourseEdit_list?? >
    <table class="pc_table" cellspacing="1">
    <#list prepareCourseEdit_list as e>
    <#assign u = Util.userById(e.editUserId) >
    <#if e.prepareCourseEditId == prepareCourse.prepareCourseEditId>
     <tr class='curbackground'>
    <#else>
     <tr>
    </#if>   
    <td><a target='_blank' href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></td>
    <td>${e.editDate?string('MM-dd HH:mm')}</td>
    <td>
     <a target='_blank' href='showHistoryContent.py?prepareCourseId=${prepareCourse.prepareCourseId}&amp;prepareCourseEditId=${e.prepareCourseEditId}'>查看</a>  
    </td>
    </tr>
    </#list>
    </table>
    </#if>
  </div>
</div>

<div style='height:8px;font-size:0;'></div>
<div class="box">
  <div class="box_head">
    <div class="box_head_right"></div>
    <div class="box_head_left">&nbsp;<img src="../../${ContextPath}css/index/j.gif">&nbsp;查看个案</div>
  </div>
  <div class="box_content">
    <#if user_list?? >
    <table class="pc_table" cellspacing="1">
    <#list user_list as u>
    <tr>
    <td><a target='_blank' href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></td>
    <td>
     <a target='_blank' href='showUserCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}&amp;userId=${u.userId}'>查看</a>  
    </td>
    </tr>
    </#list>
    </table>
    </#if>
  </div>
</div>


</td>
</tr>
</table>


<div style='padding:10px;text-align:center'><a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/0/'>返回备课首页</a></div>
<div style='clear:both;'></div>
<#include ('/WEB-INF/ftl/footer.ftl') >
</body>
</html>