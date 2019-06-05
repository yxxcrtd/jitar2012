<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <#include "/WEB-INF/ftl2/common/favicon.ftl" />
  <link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
	<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
	<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/gradesubject_blogs.js" type="text/javascript"></script>
 </head>
 <body>
<#include 'site_head.ftl'>
<div style='height:20px;font-size:0;'></div>
<div class="rightWidth border" style="width:1000px;margin:0 auto">
    <h3 class="h3Head textIn"><a class="more"></a>教研专题</h3>
    <div class="listCont">
      <#if specialsubject_list??>
         <ul class="ulList">
          <#list specialsubject_list as ss>
	        <#if ss.objectType == 'subject'>
	        <#if SubjectUrlPattern??>
	        <#assign subject = Util.subjectBySubjectId(ss.objectId)>
	        <#if subject??>
	             <li style="line-height:35px;" <#if (ss_index%2)!=0>class='liBg'</#if> ><div style="float:right;padding-right:10px;">${ss.createDate?string('yyyy-MM-dd HH:mm:ss')}</div>
	                <a style="margin-left:15px;" href='${SubjectUrlPattern.replace('{subjectCode}',subject.subjectCode?string)}py/specialsubject.action?id=${ss.objectId}&specialSubjectId=${ss.specialSubjectId}'>
	                	${Util.getCountedWords(ss.title!?html,20)}
	                </a>
	             </li>
	        </#if> 
        <#else>
             <li style="line-height:35px;" <#if (ss_index%2)!=0>class='liBg'</#if> ><div style="float:right;padding-right:10px;">${ss.createDate?string('yyyy-MM-dd HH:mm:ss')}</div>
                <a style="margin-left:15px;" href='${SiteUrl}k/czyw/py/specialsubject.action?id=${ss.objectId}&specialSubjectId=${ss.specialSubjectId}'>
                	${Util.getCountedWords(ss.title!?html,20)}
                </a>
             </li>
        </#if>
      <#else>
         <li style="line-height:35px;" <#if (ss_index%2)!=0>class='liBg'</#if> ><div style="float:right;padding-right:10px;">${ss.createDate?string('yyyy-MM-dd HH:mm:ss')}</div>
            <a style="margin-left:15px;" style="margin-left:15px;" href='specialSubject.action?specialSubjectId=${ss.specialSubjectId}'>
            	${Util.getCountedWords(ss.title!?html,20)}
            </a>
         </li>
      </#if> 
          </#list> 
        </ul>
     </#if>
    </div>
    <#include 'pager.ftl' >
    <div style='height:15px;font-size:0;' style="background-color:white;"></div>
    <div class="imgShadow"><img  src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
</div>
<#include 'footer.ftl'>
 </body>
</html>