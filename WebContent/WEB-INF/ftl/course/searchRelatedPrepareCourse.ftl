<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="alternate" type="application/rss+xml" title="Recent Changes" href="/rss.py?type=article" />
  <title></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script src="${SiteUrl}js/calendar/WdatePicker.js" type="text/javascript"></script>
  <script type="text/javascript">
    function doPost(oF,m)
        {
    
        var hasSelected = false;
        c = document.getElementsByName("guid")
         for(i = 0;i<c.length;i++)
         {
           if(c[i].checked) hasSelected = true
         }
         
        if(hasSelected == false)
        {
         alert("请选择一个备课。")
         return;
        }
        
        
     oF.cmd.value = m;
     oF.submit();
    }

    function doPost2(oF,m)
   {
     oF.cmd.value = m;
     oF.submit();
    }
  
function selAll(o)
{
 var guids = document.getElementsByName("guid")
 for(i = 0;i<guids.length;i++)
 {
  guids[i].checked = o.checked;
 }
}
function addRelated()
{
 var bChoose=false;
 var guids = document.getElementsByName("guid")
 for(i = 0;i<guids.length;i++)
 {
  	if(guids[i].checked)
  	{
  		bChoose=true;
  		break;
  	}
 }
 if(!bChoose)
 {
 	alert("请选择备课");
 	return;
 }
 document.getElementById("searchForm").cmd.value="2";
 document.getElementById("searchForm").submit();
}
function search()
{
	document.getElementById("searchForm").cmd.value="9";
	document.getElementById("searchForm").submit();
}
  </script>
 </head> 
 <body>
 <div style='padding:2px'>
<form name="searchForm" id="searchForm" method="POST">
<input type="hidden" name="cmd" value="9" />
<div id="searchDiv" style="text-align: right; width: 100%;display:block">
		关键字:
		<input type="text" size="16" name="k" value="${k!?html}" onMouseOver="this.select();" />
		<select name="ktype">
			<option value="1" ${(ktype=="1")?string('selected', '')}>集备标题</option>
			<option value="2" ${(ktype=="2")?string('selected', '')}>发起人</option>
			<option value="3" ${(ktype=="3")?string('selected', '')}>主备人</option>
			<option value="4" ${(ktype=="4")?string('selected', '')}>主备人所属机构</option>
		</select>
		集备开始时间:
		从<input type="text" size="10" onclick="WdatePicker()" id="course_BeginDate" name="course_BeginDate" value="${course_BeginDate?html}" />
		到<input type="text" size="10" onclick="WdatePicker()" id="course_EndDate" name="course_EndDate" value="${course_EndDate?html}" />
		<#if subject_list?? >
		  <#if !(subjectId??)><#assign subjectId = 0></#if>
		  <select name='subjectId'>
		    <option value=''>所属学科</option>
		  <#list subject_list as subj>
		    <option value='${subj.msubjId}' ${(subjectId==subj.msubjId)?string('selected', '')}>
		    	${subj.msubjName!?html}
		    </option>
		  </#list>
		  </select>
		</#if>
		<select name="gradeId">
		        <option value=''>所属学段</option>
		<#if grade_list?? >
		        <#list grade_list as grade>
		            <option value="${grade.gradeId}" <#if grade.gradeId==(gradeId!0)>selected</#if>>
		                <#if grade.isGrade>
		                  ${grade.gradeName!?html}
		                <#else>
		                  &nbsp;&nbsp;${grade.gradeName!?html}
		                </#if>
		              </option>
		        </#list>
		</#if>
		    </select>		
  		<input type="button" class="button" onclick="search()" value="检  索" />
</div>
<#if preparecourse_list?? >
<table class="listTable">
<thead>
<tr>
<th width='1%'><input name='x' type='checkbox' onclick='selAll(this)' /></th>
<th>备课名称</th>
<th>创建人</th>
<th>主备人</th>
<!--
<th>成员数</th>
<th>访问数</th>
<th>个案数</th>
<th>共案编辑次数</th>
<th>文章数</th>
<th>资源数</th>
<th>活动数</th>
<th>讨论数</th>
<th>讨论回复数</th>
<th>状态</th>
<th>创建日期</th>
-->
<th>学段</th>
<th>学科</th>
<th>开始时间</th>
</tr>
</thead>                           
<tbody>
<#list preparecourse_list as pc>
<tr>
<td align='center'>
<input type='checkbox' name='guid' value='${pc.prepareCourseId}' />
</td>
<td>
	<a href='${SiteUrl}p/${pc.prepareCourseId}/0/' target='_blank'>${pc.title}</a>
	<#if pc.recommendState??>
	<#if pc.recommendState>
	<img border="0" src="${SiteUrl}manage/images/ico_rcmd.gif">
	</#if>
	</#if>
</td>
<#assign c = Util.userById(pc.createUserId)>
<td><a href='${SiteUrl}go.action?loginName=${c.loginName}' target='_blank'>${c.trueName?html}</a></td>
<#assign leader = Util.userById(pc.leaderId)>
<td><a href='${SiteUrl}go.action?loginName=${leader.loginName}' target='_blank'>${leader.trueName}</a></td>
<!--
<td>${pc.memberCount}</td>
<td>${pc.viewCount}</td>
<td>${privateCountList[pc_index]}</td>
<td>${editCountList[pc_index]}</td>
<td>${pc.articleCount}</td>
<td>${pc.resourceCount}</td>
<td>${pc.actionCount}</td>
<td>${pc.topicCount}</td>
<td>${pc.topicReplyCount}</td>
<td>
<#if pc.status == 0 >
正常
<#elseif pc.status == 1>
<font color='red'>待审核</font>
<#elseif pc.status == 2>
<font color='red'>锁定</font>
<#else>
未知
</#if>
</td>
<td>${pc.createDate?string("yyyy-MM-dd")}</td>
-->
<#assign grad = Util.gradeById(pc.gradeId) >
<td>${grad.gradeName}</td>
<td>${Util.subjectById(pc.metaSubjectId!).msubjName!?html}</td>
<td>${pc.startDate?string("yyyy-MM-dd")}</td>
</tr>
</#list>
</tbody>
</table>
<div>
<input type='button' class='button' value='全部选择' onclick='this.form.x.click()' />
<input type='button' class='button' value='添加到相关集备' onclick='addRelated()' />
<input type='button' class='button' value=' 关 闭 ' onclick='window.close()' />
</div>
</#if>
</form>
 <div style='text-align:center'>
 <#include "../action/pager.ftl">
 </div>
 </body>
</html>