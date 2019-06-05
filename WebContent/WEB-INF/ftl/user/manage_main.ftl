<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <style>
  body {

  font-family: '宋体', arial, sans-serif, verdana;
  font-size: 12px;
  background-color: white;
  margin: 8px 8px 40px 8px;
  padding: 0;
  }

.listTable {
  width:100%;
  border: 1px solid #E6DBC0;
  font-family: verdana, arial, sans-serif, '宋体';
    background-color: #E6DBC0;
  }
 .listTable td {background:#FFF}
 .td_left{font-weight:bold;width:20%;vertical-align:top; text-align: right;}
 h4{margin-bottom:6px;padding-bottom:2px;width:90%;font-size:1.2em;}
  </style>
  </head>
<body>
<h2>后台管理首页</h2>
<h4>基本信息：</h4>
<table border='0' class="listTable" cellspacing='1' cellpadding='4'>
<tr>
  <td class='td_left'>用户ID：</td><td>${user.userId}</td>
</tr>
<tr>
  <td class='td_left'>登录名：</td><td>${user.loginName}</td>
</tr>
<tr>
  <td class='td_left'>真实姓名：</td><td>${user.trueName}</td>
</tr>
<tr>
  <td class='td_left'>职业身份：</td>
  <td>
	<#if user.positionId == 1>系统管理员</font>
	<#elseif user.positionId == 2>机构管理员</font>
	<#elseif user.positionId == 3>教师</font>
	<#elseif user.positionId == 4>教育局职工</font>
	<#elseif user.positionId == 5>学生</font>
	<#else>未知
	</#if>
	</td>
</tr>
<tr>
  <td class='td_left'>电子邮件：</td><td>${user.email!''}</td>
</tr>
<tr>
  <td class='td_left'>头像：</td><td><img src='${SSOServerUrl}upload/${user.userIcon!""}' onerror="this.src='${SiteUrl}images/default.gif'"/></td>
</tr>
<tr>
  <td class='td_left'>工作室名称：</td><td>${user.blogName}</td>
</tr>
<tr>
  <td class='td_left'>学段/学科：</td><td>  
    <#assign usgList = Util.getSubjectGradeListByUserId(user.userId)>
		<#if usgList?? && (usgList?size> 0) >
		<#list usgList as usg>		
		<#if usg.gradeId??>${Util.gradeById(usg.gradeId).gradeName!?html}<#else>未设置</#if>/<#if usg.subjectId??>${Util.subjectById(usg.subjectId).msubjName!?html}<#else>未设置</#if>
		<#if usg_has_next><br/></#if>
		</#list>
		</#if>
</tr>
<tr>
  <td class='td_left'>所属机构：</td><td>${user.unitTitle!''}</td>
</tr>
<tr>
  <td class='td_left'>个人简介：</td><td>${user.blogIntroduce!''}</td>
</tr>
</table>
<h4>您的活动信息：</h4>
<table border='0' class="listTable" cellspacing='1' cellpadding='4'>
<tr>
  <td class='td_left'>原创文章数量：</td><td>${user.myArticleCount + user.historyMyArticleCount}</td>
</tr>
<tr>
  <td class='td_left'>转载文章数量：</td><td>${user.otherArticleCount + user.historyOtherArticleCount}</td>
</tr>
<tr>
  <td class='td_left'>资源数量：</td><td>${user.resourceCount}</td>
</tr>
<tr>
  <td class='td_left'>图片数量：</td><td>${user.photoCount}</td>
</tr>
<tr>
  <td class='td_left'>评论数量：</td><td>${user.commentCount}</td>
</tr>
<tr>
  <td class='td_left'>消息数量：</td><td>${messageCount}</td>
</tr>
<tr>
  <td class='td_left'>好友数量：</td><td>${friendCount!''}</td>
</tr>
<tr>
  <td class='td_left'>加入的协作组数量：</td><td>${groupCount!''}</td>
</tr>
<tr>
  <td class='td_left'>个人空间被访次数：</td><td>${user.visitCount}</td>
</tr>
</table>
</body>
</html>