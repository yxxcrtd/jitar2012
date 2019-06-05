<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title><#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
 </head>
 <body>
<#include 'site_head.ftl'>
<div style='height:8px;font-size:0;'></div>

<div class='head_nav'>
  <a href='index.action'>首页</a> &gt; 教研专题
 </div>

<#if specialsubject_list??>
    <ul class='news_new_item_ul'>
      <#list specialsubject_list as ss>
      <#if ss.objectType == 'subject'>
        <#if SubjectUrlPattern??>
        <#assign subject = Util.subjectBySubjectId(ss.objectId)>
        <#if subject??>
           <li><span>${ss.createDate?string('yyyy-MM-dd HH:mm:ss')}</span><a href='${SubjectUrlPattern.replace('{subjectCode}',subject.subjectCode?string)}py/specialsubject.action?id=${ss.objectId}&specialSubjectId=${ss.specialSubjectId}'>${ss.title!?html}</a></li>
         </#if> 
        <#else>
          <li><span>${ss.createDate?string('yyyy-MM-dd HH:mm:ss')}</span><a href='${SiteUrl}k/czyw/py/specialsubject.action?id=${ss.objectId}&specialSubjectId=${ss.specialSubjectId}'>${ss.title!?html}</a></li>
        </#if>
      <#else>
        <li><span>${ss.createDate?string('yyyy-MM-dd HH:mm:ss')}</span><a href='specialSubject.action?specialSubjectId=${ss.specialSubjectId}'>${ss.title!?html}</a></li>
      </#if>   
      </#list>
    </ul>
  </#if>
  
  <div class='pgr'><#include 'inc/pager.ftl' ></div>
  
<div style="clear: both;"></div>   
<#include 'footer.ftl'>
 </body>
</html>