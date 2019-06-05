<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${subject.subjectName!?html} - <#include '/WEB-INF/ftl/webtitle.ftl' ></title>
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/dtree.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/index.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script> 
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
 </head>
<body>
<#include "/WEB-INF/subjectpage/subject_header.ftl">
<#include "/WEB-INF/subjectpage/subject_nav.ftl">

<div style="height:8px;font-size:0"></div>
<div class="orange_border" style="padding: 4px;">
<form method='get'>
<input type='hidden' name='id' value='${subject.subjectId}' />
<input type="hidden" name="unitId" value="${unitId!}"/>
<table align='center' border='0'>
<tr valign='top'>
<td>
 关键字：<input class="s_input2" name='k' value="${k!?html}" style='width:200px;' />   
 搜索类别：<select name='filter'>
         <option value='title'>活动名称</option>
         <option value='description'>活动描述</option>
         <option value='place'>活动地点</option>
         <option value='loginName'>活动发起人登录名</option>
         <option value='trueName'>活动发起人真实姓名</option>
         </select>
   </td>
   <td><input type='image' src='${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/b_s.gif' /></td>
  </tr>
  </table>
</form>
</div>
<div style='height:8px;font-size:0'></div>

<div class='tab'>        
	<div class='tab2' style='font-weight:bold;'>
	<#if !(type??)><#assign type="new" ></#if >
	<div style='border-left:0px' class="${(type == 'new')?string('cur','') }"><a href='${SubjectRootUrl}py/activity.py?id=${subject.subjectId}&type=new&ownerType=subject&unitId=${unitId!}'>正在报名的活动</a></div>
	<div class="${(type == 'running')?string('cur','') }"><a href='${SubjectRootUrl}py/activity.py?id=${subject.subjectId}&type=running&ownerType=subject&unitId=${unitId!}'>正在进行的活动</a></div>
	<div class="${(type == 'finish')?string('cur','')} "><a href='${SubjectRootUrl}py/activity.py?id=${subject.subjectId}&type=finish&ownerType=subject&unitId=${unitId!}'>已经结束的活动</a></div>
	<div class='spacer'></div>
	</div>
	<div class='tab_content'>
        <table border="0" cellspacing='0' class='res_table'>
        <thead>
        <tr>
        <td class='td_left' style='padding-left:10px;width:100%'>活动名称</td>
        <td class='td_middle'><nobr>发起人</nobr></td>                
        <td class='td_middle'><nobr>报名截止日期</nobr></td>
        </tr>
        </thead>
        <tbody>
        <tr>
        <td colspan='5' style='padding:4px;'></td>
        </tr>
    <#list action_list! as a>
         <tr>
            <td style='padding-left:10px'>
            <#if a.ownerType == 'course' >
            <a target='_blank' href='${SiteUrl}p/${a.ownerId}/0/py/show_preparecourse_action_content.py?actionId=${a.actionId}'>${a.title!?html}</a>
            <#else>
            <a target='_blank' href='${SiteUrl}manage/action/showAction.py?actionId=${a.actionId}'>${a.title!?html}</a>
            </#if>                          
            </td>
            <td><nobr><a href='${SiteUrl}go.action?loginName=${a.loginName}' target='_blank'>${a.trueName!?html}（${a.loginName!?html}）</a></nobr></td>
            <td><nobr>${a.attendLimitDateTime!?string('yyyy-MM-dd HH:mm')}</nobr></td>
            </tr>
         </#list>
        </tbody>
      </table>
	  <div class='pgr'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>            
	</div>
</div>          

<div style='clear:both;'></div>
<#include "/WEB-INF/subjectpage/subject_footer.ftl">
</body>
</html>