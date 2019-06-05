<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理协作组</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}js/datepicker/calendar.css" />  
  <script src='${SiteUrl}js/datepicker/calendar.js' type="text/javascript"></script>
  <script src='${SiteUrl}js/jitar/core.js' type="text/javascript"></script>
      <!-- 配置上载路径 -->
    <script type="text/javascript">
        window.UEDITOR_UPLOAD_URL = "${SiteUrl}";
        window.UEDITOR_USERLOGINNAME = "<#if loginUser??>${loginUser.loginName!?js_string}</#if>";
    </script>
    <!-- 配置文件 -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.all.js"></script>
    <!-- 语言包文件(建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败) -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/lang/zh-cn/zh-cn.js"></script>  
      
</head>
<body>
<#include '/WEB-INF/ftl/group/group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='${SiteUrl}manage/group.py?cmd=home&amp;groupId=${group.groupId}'>协作组管理首页</a>
  &gt;&gt; <span>制定集备计划</span>
</div>
<div style='color:#f00'>注意：以下所有条目都必须填写。</div>
<form method="post" id='f'>
<table class='listTable' cellspacing="1">
<tr>
<td class='boldright' style='width:120px'>集体备课计划标题：</td><td><input name='plantitle' style='width:98%' /></td>
</tr>
<tr>
<td class='boldright'>计划开始时间：</td><td><input name='planstartdate' id='start-date' />(格式：yyyy-MM-dd)</td>
</tr>
<tr>
<td class='boldright'>计划结束时间：</td><td><input name='planenddate' id='end-date' />(格式：yyyy-MM-dd)</td>
</tr>
<tr>
<td class='boldright'>学段/年级：</td><td>
<select name="gradeId" onchange='grade_changed(this)'>
<option value=''>选择所属学段</option>
	<#if grade_list??>
		<#list grade_list as grade>
		    <#if prepareCoursePlan??>
			<option value="${grade.gradeId}" ${(grade.gradeId == (prepareCoursePlan.gradeId!0))?string('selected', '') } >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
			<#else>
			<option value="${grade.gradeId}" ${(grade.gradeId == (group.gradeId!0))?string('selected', '') } >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
			</#if>
		</#list>
	</#if>
</select>

</td>
</tr>
<tr>
<td class='boldright'>学科：</td><td>
<select name="subjectId">
  <option value=''>选择所属学科</option>
	<#if subject_list??>
		<#list subject_list as msubj>
		<#if prepareCoursePlan?? >
			<option value="${msubj.msubjId}" ${(msubj.msubjId == (prepareCoursePlan.subjectId!0))?string('selected', '')}>${msubj.msubjName!?html}</option>
		<#else>
			<option value="${msubj.msubjId}" ${(msubj.msubjId == (group.subjectId!0))?string('selected', '')}>${msubj.msubjName!?html}</option>
		</#if>
		</#list>
	</#if>
</select>
<span id='subject_loading' style='display:none'>正在加载……</span>
</td>
</tr>
<tr>
<td class='boldright'>参与人员：</td><td>组内所有成员</td>
</tr>
<tr>
<td class='boldright'>计划内容：</td><td>

    <script id="DHtml" name="plancontent" type="text/plain" style="width:880px;height:500px;">
    </script>
    <script type="text/javascript">
        var editor = UE.getEditor('DHtml');
    </script> 
</td>
</tr>
<tr>
<td>&nbsp;</td><td><input name='plandefault' type='checkbox' value='1' checked='checked' />设置为当前组内默认计划（注意：将同时取消其他备课计划设置的默认备课计划。）</td>
</tr>
</table>
<div style='text-align:right;padding:2px;'>
<input type='submit' value='创建集备计划' class='button' />
</div>
</form>

<script type='text/javascript'>
function grade_changed(sel)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.getElementById('f').subjectId;

  if (gradeId == null || gradeId == '' || gradeId == 0) {
    clear_options(subject_sel);
    add_option(subject_sel, '', '选择学科');
    return;
  } 
  subject_sel.disabled = true;
  var img = document.getElementById('subject_loading');
  img.style.display = '';
  
  // 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
  url = '${SiteUrl}manage/admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + Math.random();
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
    sel.options[sel.options.length] = new Option(text,val)
    
}
calendar.set("start-date");
calendar.set("end-date");
</script>
</body>
</html>