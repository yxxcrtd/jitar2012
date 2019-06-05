<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>列表</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}js/datepicker/calendar.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script type="text/javascript" src='${SiteUrl}js/datepicker/calendar.js'></script>
  <script language='javascript'>
  
function grade_changed(sel)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.forms[0].subjectId;

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
  sel.options[sel.options.length] = new Option(text.replace(/&nbsp;/g," "),val)    
}
  </script>
</head>
<body>
<h2>评课轮次设置</h2>
<form method="post">
<#if evaluationPlan??>
<table class='listTable'>
<tr>
 <td align='right' style='width:132px'><b>轮次：</b></td>
 <td><input name="times" value="${evaluationPlan.evaluationTimes}" /><font style="color:red">请输入阿拉伯数字。</font></td>
</tr>
<tr>
 <td align='right'><b>学年：</b></td>
 <td>
 <select name="year">
 <#list 2012..currentYear+2 as y>
 <option value="${y}"<#if evaluationPlan.evaluationYear==y> selected="selected"</#if>>${y}学年</option>
 </#list>
 </select>
 </td>
 </tr>
 <tr>
 <td align='right'><b>学期：</b></td>
 <td>
 <select name="semester">
 <option value="0" ${(evaluationPlan.evaluationSemester==0)?string(" selected='selected'","")}>上学期</option>
 <option value="1" ${(evaluationPlan.evaluationSemester!=1)?string(" selected='selected'","")}>下学期</option>
 </select>
 </td>
</tr>
<#--
<tr>
 <td align='right'><b>学段/学科：</b></td>
 <td>
  <select name="gradeId" onchange='grade_changed(this)'>
  <option value=''>选择所属学段</option>
  <#if grade_list??>
	<#list grade_list as grade>
		<option value="${grade.gradeId}" ${(grade.gradeId == (evaluationPlan.metaGradeId!0))?string('selected', '') } >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
	</#list>
  </#if>				
  </select>
  <select name="subjectId">
  <option value=''>选择所属学科</option>
  <#if subject_list??>
	<#list subject_list as msubj>
		<option value="${msubj.msubjId}" ${(msubj.msubjId == (evaluationPlan.metaSubjectId!0))?string('selected', '')}>${msubj.msubjName!?html}</option>
	</#list>
  </#if>
  </select>
  <span id='subject_loading' style='display:none'><img src='images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...</span> 
 </td>
</tr>-->
<tr>
 <td align='right'><b>开始时间：</b></td>
 <td>
 <input name="startDate" style="width:66px" id="start-date" value="${evaluationPlan.startDate?string("yyyy-MM-dd")}" readonly="readonly" /><select name="startTime"><#list 6..23 as t><option value="${t}"<#if evaluationPlan.startDate?string("H") == t?string> selected="selected"</#if>>${t}</option></#list></select>点
 </td>
</tr>
<tr>
 <td align='right'><b>结束时间：</b></td>
 <td>
  <input name="endDate" style="width:66px" id="end-date" value="${evaluationPlan.endDate?string("yyyy-MM-dd")}" readonly="readonly" /><select name="endTime"><#list 6..23 as t><option value="${t}"<#if evaluationPlan.endDate?string("H") == t?string> selected="selected"</#if>>${t}</option></#list></select>点
 </td>
</tr>
<tr>
 <td align='right'><b>启用：</b></td>
 <td>
  <input type="checkbox" name="enabled" value="1" ${evaluationPlan.enabled?string('checked="checked"',"")} />
 </td>
</tr>
</table>
<input type="submit" value="保存修改" class="button" />
<input type="button" value="返回评课计划" class="button" onclick="window.location.href='evaluation_plan.py'"/>
<#else>
<table class='listTable'>
<tr>
 <td align='right' style='width:120px'><b>轮次：</b></td>
 <td><input name="times" /><font style="color:red">请输入阿拉伯数字。</font></td>
</tr>
<tr>
 <td align='right'><b>学年：</b></td>
 <td>
 <select name="year">
 <#list currentYear-1..currentYear+2 as y>
 <option value="${y}"<#if currentYear==y> selected="selected"</#if>>${y}学年</option>
 </#list>
 </select>
 </td>
 </tr>
 <tr>
 <td align='right'><b>学期：</b></td>
 <td>
 <select name="semester">
 <option value="0">上学期</option>
 <option value="1">下学期</option>
 </select>
 </td>
</tr>
<#--
<tr>
 <td align='right'><b>学段/学科：</b></td>
 <td>
  <select name="gradeId" onchange='grade_changed(this)'>
  <option value=''>选择所属学段</option>
  <#if grade_list??>
	<#list grade_list as grade>
		<option value="${grade.gradeId}" >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
	</#list>
  </#if>				
  </select>
  <select name="subjectId">
  <option value=''>选择所属学科</option>
  <#if subject_list??>
	<#list subject_list as msubj>
		<option value="${msubj.msubjId}" >${msubj.msubjName!?html}</option>
	</#list>
  </#if>
  </select>
  <span id='subject_loading' style='display:none'><img src='images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...</span> 
 </td>
</tr>
-->
<tr>
 <td align='right'><b>开始时间：</b></td>
 <td>
 <input name="startDate" style="width:66px" id="start-date" value="" readonly="readonly" /><select name="startTime"><#list 6..23 as t><option value="${t}">${t}</option></#list></select>点
 </td>
</tr>
<tr>
 <td align='right'><b>结束时间：</b></td>
 <td>
  <input name="endDate" style="width:66px" id="end-date" value="" readonly="readonly" /><select name="startTime"><#list 6..23 as t><option value="${t}">${t}</option></#list></select>点
 </td>
</tr>
<tr>
 <td align='right'><b>总参评人数：</b></td>
 <td>
  <input name="userCount" />
 </td>
</tr>
<tr>
 <td align='right'><b>启用：</b></td>
 <td>
  <input type="checkbox" name="enabled" value="1" checked="checked" />
 </td>
</tr>
</table>
<input type="submit" value="保存设置" class="button" />
<input type="button" value="返回评课计划" class="button" onclick="window.location.href='evaluation_plan.py'"/>
</#if>

</form>
<script>
calendar.set("start-date");
calendar.set("end-date");
</script>
</body>
</html>