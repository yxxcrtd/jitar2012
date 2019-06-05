<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}article.css" />
  <style>
  #t1 tr td{background:#fff;vertical-align:top;}
  .txt {width:80%;}
  .txt:hover{outline:solid orange 1px}
  </style>
  <title>评课 <#include '/WEB-INF/ftl/webtitle.ftl'></title>
  <script type="text/javascript" src="${SiteUrl}js/jitar/core.js"></script>
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
  <script>
  function checkInput(ff)
  {
   if(ff.titleName.value =="")
   {
    alert("请输入评课名称。");
    return false;
   }
   if(ff.teacherName.value=="")
   {
    alert("请输入授课人");
    return false;
   }
   return true;
  }
  </script>
 </head>
 <body>
<#include '/WEB-INF/ftl/site_head.ftl'>
<div style='height:8px;font-size:0;'></div>

<div>
<#if template??>
<#if evaluationPlan??>
<h2 style="text-align:center">${evaluationPlan.evaluationYear}学年${(evaluationPlan.evaluationSemester==0)?string("上学期","下学期")}第${evaluationPlan.evaluationTimes}次评课</h2>
</#if>
<form method="post">
<table id="t1" border="0" cellspacing="1" cellpadding="2" style="background:#B0BEC7;margin:auto;width:800px;">
<tr>
<td>评课名称：</td><td><input name="titleName" class="txt"/><font style="color:red">*</font></td>
</tr>
<tr>
<td>授课人：</td><td><input name="teacherName" class="txt"/><font style="color:red">*</font></td>
</tr>
<tr>
<td>学段/学科</td><td>
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
  <span id='subject_loading' style='display:none'><img src='${SiteUrl}manage/images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...</span> 

</td>
</tr>
<#list template.templateFields?split("$$$") as t >
<tr>
<td>${t!?html}</td>
<td><input type="hidden" name="fieldcount" value="${t_index}" />
<input type="hidden" name="fieldname${t_index}" value="${t!?html}"/>

    <script id="DHtml${t_index}" name="fieldcontent${t_index}" type="text/plain" style="width:880px;height:500px;">
    </script>
    <script type="text/javascript">
        var editor = UE.getEditor('DHtml${t_index}');
    </script>  
    
</td>
</tr>
</#list>
</table>
<div style="text-align:Center"><input type="submit" value="提交评课" onclick="return checkInput(this.form)" /></div>
</form>
<#else>
没有选择模板，无法进行评课。
</#if>

</div>

<#include '/WEB-INF/ftl/footer.ftl' >
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