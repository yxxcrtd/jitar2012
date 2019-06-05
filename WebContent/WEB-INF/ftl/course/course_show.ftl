<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>集体备课：${prepareCourse.title!} <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}precourse.css" />
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  <script type='text/javascript'>
  //<![CDATA[
  function showHide(divID)
  {
   var d = document.getElementById('div' + divID);
   if(d)
   {
    d.style.display = d.style.display == 'none'?'':'none';
   }
  }
  //]]>
  </script>
 </head>
 <body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div class='course_title'>${prepareCourse.title!}</div>

<div class="box">
  <div class="box_head">
    <div class="box_head_right"><span style='cursor:pointer' onclick='showHide(1)'>[显示/隐藏]</span></div>
    <div class="box_head_left">&nbsp;<img src="../../${ContextPath}css/index/j.gif">&nbsp;【${prepareCourse.title!}】备课基本信息：</div>
  </div>
  <div class="box_content" id='div1'>
    <#assign u = Util.userById(prepareCourse.createUserId) >
	<table cellspacing='1' class='pc_table'>
	<tr>
	<td class='left'>主备人：</td><td class='right'><a target='_blank' href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName!?html}（${u.loginName}）</a></td>	
	<td class='left'>创建时间：</td><td class='right'>${prepareCourse.createDate!}</td>
	</tr>
	<tr>
	<td class='left'>开始时间：</td><td>${prepareCourse.startDate!?string('yyyy-MM-dd')}</td>
	<td class='left'>结束时间：</td><td>${prepareCourse.endDate!?string('yyyy-MM-dd')}</td>
	</tr>
	<tr>
	<td class='left'>所属学段：</td><td>${grade.gradeName}</td>	
	<td class='left'>所属学科：</td><td>${metaSubject.msubjName}</td>
	</tr>
	<tr>
    <td colspan='4' style='padding:0;'>
       <table cellspacing='0' class='pc_table' border='0' style='border-width:0px'>
       <tr>
        <td style='width:20px;text-align:center;font-weight:bold;'>任<br/>务<br/>描<br/>述</td><td class='pc_left_border'>${prepareCourse.description!}</td> 
       </tr>
       </table>
    </td>
    </tr>
    <#if canAdmin=='true' || loginUser?? >
	<tr>
	  <td colspan='4'>
	  <#if canAdmin=='true'>
	  <a href='createPreCourse2.py?prepareCourseId=${prepareCourse.prepareCourseId}'>参与人员管理</a> 
	  <a href='createPreCourse3.py?prepareCourseId=${prepareCourse.prepareCourseId}'>备课阶段管理</a>
	  <a href='createPreCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}'>修改此次备课</a>
	  <a href='editCommonPreCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}'>编写此次备课共案</a>
	  <a href='coEditCommonPreCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}'>（或者共同编写。只选其一）</a>
	  </#if>
	  <#if canVisitCourse == 'true' >
	  <a href='showCommonPreCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}'>查看此次备课共案</a>
	  <a href='${SiteUrl}createAction.action?ownerId=${prepareCourse.prepareCourseId}&ownerType=course'>发起教研活动</a>
	  </#if>
	  </td>
	</tr>
	</#if>
	</table>
	
  </div>        
</div>
<div style='clear:both;height:8px;font-size:0'></div>

<#if action_list?? >
<div class="box">
  <div class="box_head">
    <div class="box_head_right"><span style='cursor:pointer' onclick='showHide(8)'>[显示/隐藏]</span></div>
    <div class="box_head_left">&nbsp;<img src="../../${ContextPath}css/index/j.gif">&nbsp;【${prepareCourse.title!}】教研活动：</div>
  </div>
  <div class="box_content" id='div8'>
  
    <#if user_list?? >
    <table cellspacing='1' class='pc_table'>
    <tr>
    <td class='fontbold'>活动名称</td>
    <td class='fontbold' style='width:80px'>发起人</td>
    <td class='fontbold'>活动地点</td>
    <td class='fontbold' style='width:108px'>创建时间</td>
    <td class='fontbold' style='width:108px'>开始时间</td>
    <td class='fontbold' style='width:108px'>报名截止时间</td>
    </tr>
    <#list action_list as a>
    <#assign uu = Util.userById(a.createUserId) >
    <tr style='background:#FFF' valign='top'>
    <td><a href='${SiteUrl}showAction.action?actionId=${a.actionId}'>${a.title!?html}</a></td>    
    <td><a href='${SiteUrl}go.action?loginName=${uu.loginName}'>${uu.trueName!?html}</a></td>
    <td>${a.place}</td>
    <td>${a.createDate?string('yyyy-MM-dd HH:mm')}</td>
    <td>${a.startDateTime?string('yyyy-MM-dd HH:mm')}</td>
    <td>${a.attendLimitDateTime?string('yyyy-MM-dd HH:mm')}</td>
    </tr>
    </#list>
    </table>
    </#if>
  </div>
</div>
<div style='clear:both;height:8px;font-size:0'></div>
</#if>

<div class="box">
  <div class="box_head">
    <div class="box_head_right"><span style='cursor:pointer' onclick='showHide(2)'>[显示/隐藏]</span></div>
    <div class="box_head_left">&nbsp;<img src="../../${ContextPath}css/index/j.gif">&nbsp;【${prepareCourse.title!}】备课参与人员：</div>
  </div>
  <div class="box_content" id='div2'>
  
	<#if user_list?? >
	<table cellspacing='1' class='pc_table'>
	<tr>
	<td class='fontbold'>所在机构</td>
	<td class='fontbold'>真实姓名</td>
	<td class='fontbold'>登录名</td>
	<td class='fontbold'>参与讨论数</td>
	<td class='fontbold'>个案</td>
	</tr>
	<#list user_list as u>
	<tr style='background:#FFF' valign='top'>
	<td>${u.unitTitle!}</td>
	<td><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></td>
	<td><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.loginName}</a></td>
	<td>${u.replyCount}</td>
	<td><a href='showUserCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}&userId=${u.userId}' target='_blank'>查看个案</a> <#if loginUser.userId == u.userId ><a href='editUserCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}'>修改个案</a></#if></td>
	</tr>
	</#list>
	</table>
	</#if>
  </div>
</div>

<div style='clear:both;height:8px;font-size:0'></div>

<div class="box">
  <div class="box_head">
    <div class="box_head_right"><span style='cursor:pointer' onclick='showHide(3)'>[显示/隐藏]</span></div>
    <div class="box_head_left">&nbsp;<img src="../../${ContextPath}css/index/j.gif">&nbsp;【${prepareCourse.title!}】备课阶段：</div>
  </div>
  <div class="box_content" id='div3'> 
  
  	<#if precoursestage_list?? >
	<table cellspacing='1' class='pc_table'>
	<#list precoursestage_list as pcs>
	<tr style='background:#FFF' valign='top'>
	<td>${pcs_index + 1} <a href='showPrepareCourseStage.py?stageId=${pcs.prepareCourseStageId}&prepareCourseId=${prepareCourse.prepareCourseId}' target='_blank'>${pcs.title}</a></td>
	<td>${pcs.beginDate!?string('yyyy-M-d')}</td>
	<td>${pcs.finishDate!?string('yyyy-M-d')}</td>
	<td>${pcs.description!}</td>
	</tr>
	</#list>
	</table>
	</#if>
  </div>        
</div>

<div style='clear:both;'></div>
<#include ('/WEB-INF/ftl/footer.ftl') >
</body>
</html>