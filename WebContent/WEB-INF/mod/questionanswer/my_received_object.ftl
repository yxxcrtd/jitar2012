<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>问题与解答管理</title>
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
<script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<h2>问题与解答管理</h2>
<div class='tab_outer'>
  <div id="article_" class='tab2'>
    <div><a href='my_created_object.py'>我提出的问题</a></div> 
    <div class="cur"><a href='my_received_object.py'>我收到的问题</a></div> 
  </div>
  <div style="padding:10px">

	<#if q_list?? >
	<form method="post">
	<table id="ListTable" class="listTable">
	<thead>
	<tr>
	<th width="17px"><input type='checkbox' id='cxx' onclick="CommonUtil.SelectAll(this,'guid')" /></th>
	<th width="100%">问题名称</th>
	<th><nobr>解答</nobr></th>
	<th><nobr>提问时间</nobr></th>
	</tr>
	</thead>
	<tbody>
	<#list q_list as q>
	<tr>
	<td><input type='checkbox' name='guid' value='${q.questionId}' /></td>
	<td><a target='_blank' href='${SiteUrl}mod/questionanswer/question_getcontent.action?guid=${q.parentGuid}&type=${q.parentObjectType}&qid=${q.questionId}'>${q.topic}</a></td>
	<td><nobr><a target='_blank' href='${SiteUrl}mod/questionanswer/question_getcontent.action?guid=${q.parentGuid}&type=${q.parentObjectType}&qid=${q.questionId}'>解答</a></nobr>
	</td>
	<td><nobr>${q.createDate?string('yyyy/MM/dd')}</nobr></td>
	</tr>
	</#list>
	</tbody>
	</table>
	<div style='padding:4px'>
	<input class='button' type='button' value='全部选中' onclick='document.getElementById("cxx").click();CommonUtil.SelectAll(document.getElementById("cxx"),"guid")' />
	<input class='button' type='submit' value='删除选中' />
	</div>
	<#include ('../pager.ftl')>
	</form>
	</#if>
  </div>
</div>
</body>
</html>