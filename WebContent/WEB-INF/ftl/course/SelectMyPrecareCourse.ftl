<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="alternate" type="application/rss+xml" title="Recent Changes" href="/rss.py?type=article" />
  <title></title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script type="text/javascript">
  function VideoToPC(pcid,vid,stateFlag,objRadio)
  {
  	tr = objRadio.parentNode;
  	while(tr && tr.tagName.toUpperCase() != "TR")
  	{
  		tr = tr.parentNode;
  	}
  	if(tr.tagName.toUpperCase() != "TR") return;  	
  	tr.cells[2].innerHTML = "<span style='display:none'></span><span style='color:red'>正在" + (stateFlag == "Add"?"推送":"删除") + "</span>";
  	
  	var xmlhttp = window.XMLHttpRequest?new XMLHttpRequest():new ActiveXObject("MSXML2.XMLHTTP");
  	url = "${SiteUrl}VideoToPC.py?pcid="+pcid + "&videoId=" + vid + "&state="+ stateFlag +"&" + Date.parse(new Date());
  	xmlhttp.open("GET",url,true);
  	
  	xmlhttp.onreadystatechange = function()
  	{  	
  	  if(xmlhttp.readyState == 4 )
  	  {  	  
  	  if( xmlhttp.status == 200)
  	  {  
  	   if(xmlhttp.responseText.indexOf("Added") >-1)
  	   tr.cells[2].innerHTML = "<span style='display:none'></span><span style='color:red'>已经推送</span>";
  	   else if(xmlhttp.responseText.indexOf("Removed") >-1)
  	   tr.cells[2].innerHTML = "<span style='display:none'></span><span style='color:red'>已经删除</span>";
  	   else
  	   tr.cells[2].innerHTML = "<span style='display:none'></span><span style='color:red'>"+xmlhttp.responseText+"</span>";  	   
  	   }
  	  }
  	}
  	xmlhttp.send(null);
  }
  
  </script>
 </head> 
 <body>
 <div style='padding:0px'>
 <#if preparecourse_list?? >
<form method='post'>
<input type='hidden' name='cmd' value='' />
<table class="listTable">
<thead>
<tr>
<th style="width:100px;"></th>
<th>备课名称</th>
<th>推送状态</th>
<th>学段</th>
<th>学科</th>
</tr>
</thead>
<tbody>
<#list preparecourse_list as pc>
<tr>
<td align='center'>
<label><input type='radio' name='guid${pc.prepareCourseId}' onclick="VideoToPC(${pc.prepareCourseId},${videoId},'Add',this)" />推送</label>
<label><input type='radio' name='guid${pc.prepareCourseId}' onclick="VideoToPC(${pc.prepareCourseId},${videoId},'Remove',this)" />删除</label>
</td>
<td>
<a href='${SiteUrl}p/${pc.prepareCourseId}/0/' target='_blank'>${pc.title}</a>
</td>
<td>
<#assign pushState = Util.checkVideoInPrepareCourse(pc.prepareCourseId,videoId) >
<#if pushState == "1">
已推送
<#else>
<font style='color:red'>未推送</font>
</#if>
</td>
<#assign grad = Util.gradeById(pc.gradeId) >
<td>${grad.gradeName}</td>
<td>${Util.subjectById(pc.metaSubjectId!).msubjName!?html}</td>
</tr>
</#list>
</tbody>
</table>
</form>
</#if>
<#if pager??>
<div style="text-align:center;padding:10px 0">
<#include '/WEB-INF/ftl/inc/pager.ftl' >
</div>
</#if>
<div>
   <input type="button" class='button' value=" 关  闭  页  面 " onclick="window.close()" />
</div>   
</body>
</html>