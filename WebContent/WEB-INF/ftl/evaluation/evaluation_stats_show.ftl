<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>列表</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}js/datepicker/calendar.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
  <script type="text/javascript" src='${SiteUrl}js/datepicker/calendar.js'></script>
</head>
<body>
<h2>评课活动统计</h2>
<form method="GET" action="?" style="text-align:right">
<input name="evaluationPlanId" type="hidden" value="${evaluationPlanId}"/>
<select name="gradeId" onchange='grade_changed(this)'>
  <option value=''>选择所属学段</option>
  <#if grade_list??>
	<#list grade_list as grade>
		<option value="${grade.gradeId}" ${(grade.gradeId == (gradeId!0))?string('selected="selected"', '')}>${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
	</#list>
  </#if>				
  </select>
  <select name="subjectId" onchange="this.form.submit();">
  <option value=''>选择所属学科</option>
  <#if subject_list??>
	<#list subject_list as msubj>
		<option value="${msubj.msubjId}"  ${(msubj.msubjId == (subjectId!0))?string('selected="selected"', '')}>${msubj.msubjName!?html}</option>
	</#list>
  </#if>
  </select>
<span id='subject_loading' style='display:none'><img src='${SiteUrl}manage/images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...</span> 
<input type="submit" value="查询"/>
</form>
<#if evaluationPlan??>
<table class='listTable' cellspacing='1'>
  <thead>
    <tr style="text-align:left">
      <th width="10%">学年</th>
      <th width="10%">学期</th>
      <th width="10%">轮次</th>
      <th width="10%">学段</th>
      <th width="10%">学科</th>
      <th width="10%">需要参加评课的人数 </th>
      <th width="10%">实际参与人数</th>
    </tr>
  </thead>
    <tbody>
    <tr>
       <td>${evaluationPlan.evaluationYear}</td>
       <td>${(evaluationPlan.evaluationSemester==0)?string("上学期","下学期")}</td>
       <td>${evaluationPlan.evaluationTimes}</td>
       <td><#if gradeId??><#assign grade = Util.gradeById(gradeId) ><#if grade??>${grade.gradeName!}</#if><#else>全部学段</#if></td>
       <td><#if subjectId??><#assign subject = Util.subjectById(subjectId) ><#if subject??>${subject.msubjName!}</#if><#else>全部学科</#if></td>
   	   <td>${evaluationPlan.userCount}</td>
       <td>${count}</td>
    </tr>
  </thead>
  </table>
<#else>
无法加载评课活动。
</#if>  

<script type="text/javascript">
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
</body>
</html>