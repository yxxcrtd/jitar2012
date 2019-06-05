<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title></title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
		<script src="../js/jitar/core.js"></script>
	</head>
	<body>
	<h2>修改学段/学科信息</h2>
	<div class='pos'>
	 您现在的位置：<a href="${SiteUrl}manage/" target="_top">个人控制面板</a> &gt;&gt; 修改学段/学科
	</div>
	<div style="padding:10px">
	<#if user??>
	  <b>您目前设置了如下的学段、学科：</b>
      <table class="listTable" cellspacing="1" cellpadding="0" style="width:auto">
      <thead>
      <tr style="text-align:left;"><th>学段</th><th>学科</th><th>操作</th></tr>
      </thead>
<#assign usgList = Util.getSubjectGradeListByUserId(user.userId)>
<#if usgList?? && (usgList?size> 0) >
<#list usgList as usg>
<#if usg.userSubjectGradeId == 0>
<tr>
<td>
<select id="first_gradeId"  onchange='grade_changed(this)'>
<option value="">所属年级</option>
  <#if grade_list??>
<#list grade_list as grade>
  <option value="${grade.gradeId}" <#if grade.gradeId==(user.gradeId!0)>selected</#if>>
    <#if grade.isGrade>
      ${grade.gradeName!?html}
    <#else>
      &nbsp;&nbsp;${grade.gradeName!?html}
    </#if>
  </option>
</#list>
  </#if>
</select>
</td>
<td>
<select id="first_subjectId">
<option value="">所属学科</option>
<#if subject_list?? >
  <#list subject_list as msubj>
    <option value="${msubj.msubjId}" ${(msubj.msubjId == (user.subjectId!0))?string('selected', '')}>
    ${msubj.msubjName!?html}
    </option>
  </#list>
</#if>
</select>
</td>
<td><input type="button" class="button" value="修改主学段/学科" onclick="edit_subject_grade('edit');" /></td></tr>
<#else>
<tr>
<td><#if usg.gradeId??>${Util.gradeById(usg.gradeId).gradeName!?html}<#else>未设置</#if></td>
<td><#if usg.subjectId??>${Util.subjectById(usg.subjectId).msubjName!?html}<#else>未设置</#if></td>
<td><input type="button" class="button" value="删除学段/学科" onclick="edit_subject_grade('delete_${usg.userSubjectGradeId}');" /></td></tr>
</#if>
</#list>
</#if>
<tr>
<td>
<select id="sel_gradeId"  onchange='grade_changed(this)'>
<option value="">所属年级</option>
  <#if grade_list??>
    <#list grade_list as grade>
      <option value="${grade.gradeId}">
        <#if grade.isGrade>
          ${grade.gradeName!?html}
        <#else>
          &nbsp;&nbsp;${grade.gradeName!?html}
        </#if>
      </option>
    </#list>
  </#if>
  </select>
  </td>
  <td>
  <select id="sel_subjectId">
    <option value="">所属学科</option>
    <#if subject_list?? >
      <#list subject_list as msubj>
        <option value="${msubj.msubjId}">
        ${msubj.msubjName!?html}
        </option>
      </#list>
    </#if>
  </select>
  </td>
  <td><input type="button" value="添加一个学段/学科" class="button" onclick="edit_subject_grade('add');" /></td>
</tr>
</table>
      
<span id='subject_loading' style='display:none'>
  <img src='images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...
</span>
<br/><font style="color: #FF0000;">
<b>说明：</b><br/>
1，不要重复添加学段，如高中和高二就是一个大学段了，不要重复添加；<br/>
2，主学段/学科是指在注册用户时选择的学段/学科，是在发布内容时常用的选项；
<#if subject == "true" || grade == "true">
<br/>3，如果修改了学段/学科，则需要管理员审核通过才能登录，请不要频繁更改！
</#if>
</font> 


<script>
function edit_subject_grade(cmd)
{
	var g_id = cmd=="edit"?document.getElementById("first_gradeId"):document.getElementById("sel_gradeId");
	var s_id = cmd=="edit"?document.getElementById("first_subjectId"):document.getElementById("sel_subjectId");
	var gid = g_id.options[g_id.selectedIndex].value;
	var sid = s_id.options[s_id.selectedIndex].value;
	url = 'user_subject_grade_edit.py?cmd=' + cmd + '&gradeId=' + gid + '&subjectId=' + sid + '&tmp=' + Math.random();
  	new Ajax.Request(url, {
    method: 'get',
    onSuccess: function(xport) { 
    	if(xport.responseText.indexOf("success") > -1)
    	{
    	 strText = "操作成功。";
    	 if(cmd == "add") strText = "添加成功。"
    	 if(cmd.indexOf("delete_")>-1) strText = "删除成功。"
    	 if(cmd == "edit") strText = "修改成功。"
    	 alert(strText);
    	 window.location.reload();
    	 return;
    	}
    	else
    	{
    	 alert(xport.responseText);
    	}
      }
  });
}
function grade_changed(sel)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = sel.id.indexOf("first_")> -1?document.getElementById("first_subjectId"):document.getElementById("sel_subjectId");
  subject_sel.disabled = true;
  var img = document.getElementById('subject_loading');
  img.style.display = '';
  
  // 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
  url = 'admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + Math.random();
  new Ajax.Request(url, {
    method: 'get',
    onSuccess: function(xport) { 
        var options = eval(xport.responseText);
        clear_options(subject_sel);
        add_option(subject_sel, '', '选择学科');
        for (var i = 0; i < options.length; ++i)
          add_option(subject_sel, options[i][0], options[i][1]);
        img.style.display = 'none';
        subject_sel.disabled = false;
      }
  });
}
function clear_options(sel) {
  sel.options.length = 0;
}
function add_option(sel, val, text) {
  opt = document.createElement("OPTION");
  opt.value = val;
  opt.text = text;
  if (val == "${user.unitId!}")
    opt.selected = true;
  sel.options.add(opt);
}
</script>
<#else>
用户被删除或者未登录。
</#if>
</div>
</body>
</html>
