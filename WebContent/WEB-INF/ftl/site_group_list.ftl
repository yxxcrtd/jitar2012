<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>协作组列表 - <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="css/common/common.css" />
  <link rel="stylesheet" type="text/css" href="css/index.css" />
 </head>
 <body>
 <#include ('header.ftl') >
 <#include ('navbar.ftl') >
 <div class='containter'>
 <br />
 <h3 style='text-align:center;'>协作组列表</h3>
  <div style='clear:both;margin:12px 10px 10px 10px;'>	
  <#if group_list??>
    <table class='lastlist' cellspacing='1'>
    <tr>
    <th><nobr>协作组名称</nobr></th> 
    <th><nobr>创建者</nobr></th>   
    <th><nobr>创建日期</nobr></th>
    <th><nobr>查看次数</nobr></th>
    </tr>    
    <#list group_list as group>
      <#assign u = Util.userById(group.createUserId)>
      <tr>
 		 <td class='list_title'>
 		   <table width='99%' border='0'><tr>
 		     <td width='54'>
 		       <img src='${Util.url(group.groupIcon!SiteUrl + "images/group_default.gif")}' width='48' height='48' border='0' />
 		     </td>
 		     <td align='left' valign='top'>
 		       <div>
		 		     <#if group.subject??>[<a href='showSubject.py?subjectId=${group.subject.subjectId}'>${group.subject.subjectName}</a>]</#if>
		 		     <b><a href='${SiteUrl}go.action?groupId=${group.groupId}'>${group.groupTitle!?html}</a></b></div>
		 		   <div>&nbsp;&nbsp;${group.groupIntroduce!}</div>
	 		   </td>
 		   </tr></table>
 		 </td>
 		 <td><nobr><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.nickName}</a></nobr></td> 		
 		 <td><nobr>${group.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr></td>
 		 <td><nobr>${group.visitCount}</nobr></td>
 		</tr>
 	  </#list>
    </table>  
    
	    <div class='pager'>
	      <#include ('inc/pager.ftl') >
	    </div>
	  </#if>  
	 </div>
 </div>
<div style='clear:both'></div>
<#include ('footer.ftl') >
</body>
</html>