<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>集体备课 <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}precourse.css" />
  <script src='${SiteUrl}js/jitar/core.js'></script>
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
<#include '/WEB-INF/ftl/site_head.ftl'>
<div class='course_title'>
<#if prepareCourse??>
修改备课
<#else>
发起备课
</#if>
</div>

<form method="post" name='pc'<#if prepareCourse??> action='manage_createPrepareCourse.py?prepareCourseId=${prepareCourse.prepareCourseId!0}'</#if>>
<table style="width:100%">
<tr>
<td style="width:100px">备课题目(<span style='color:#F00;'>*</span>)：</td><td>
<#if prepareCourse??>
<input name="pcTitle" value='${prepareCourse.title!}' style='width:600px;' />
<#else>
<input name="pcTitle" value='' style='width:600px;' />
</#if>
</td>
</tr>
<tr>
<td style="width:100px">主备人(<span style='color:#F00;'>*</span>)：</td><td>

<#if prepareCourse??>
<#assign u = Util.userById(prepareCourse.leaderId) >
<input id='pcLeader' name="pcLeader" value='${prepareCourse.leaderId!}' type='hidden' />
<input id="pcLeaderName" value='${u.trueName?html}' readonly='readonly' />
<#else>
<input id='pcLeader' name="pcLeader" value='' type='hidden' />
<input id="pcLeaderName" value='' readonly='readonly' />
</#if>
<input type='button' onclick='selectUser()' value='选择用户' />
(不填写则设置主备人为创建者自己)
</td>
</tr>
<tr>
<td>开始时间(<span style='color:#F00;'>*</span>)：</td><td>
<#if prepareCourse??>
<input name="pcStartDate" value='${prepareCourse.startDate?string('yyyy-M-d')}' />
<#else>
<input name="pcStartDate" value='' />
</#if>
(格式：年年年年-月-日)
</td>
</tr>
<tr>
<td>结束时间(<span style='color:#F00;'>*</span>)：</td><td>
<#if prepareCourse??>
<input name="pcEndDate" value='${prepareCourse.endDate?string('yyyy-M-d')}' />
<#else>
<input name="pcEndDate" value='' />
</#if>
(格式：年年年年-月-日)
</td>
</tr>
<tr>
<td>选择学段(<span style='color:#F00;'>*</span>)：</td><td>

<#if prepareCourse?? >
    <select name='pcGrade' onchange='grade_changed(this)'>
    <option value="">全部学段</option>
    <#if grade_list?? >
    <#list grade_list as grade> 
    <option value="${grade.gradeId}" <#if grade.gradeId==prepareCourse.gradeId >selected='selected'</#if>>
    <#if grade.isGrade>
      ${grade.gradeName!?html}
    <#else>
      &nbsp;&nbsp;${grade.gradeName!?html}
    </#if>
      </option>

    </#list>
    </#if>
    </select>
    
<#else>

	<select name='pcGrade' onchange='grade_changed(this)'>
	<option value="">全部学段</option>
	<#if grade_list?? >
	<#list grade_list as grade>
	
	<option value="${grade.gradeId}" <#if grade.gradeId==(gradeId!0)>selected</#if>>
	<#if grade.isGrade>
	  ${grade.gradeName!?html}
	<#else>
	  &nbsp;&nbsp;${grade.gradeName!?html}
	</#if>
	  </option>
	</#list>
	</#if>
	</select>
</#if>
</td>
</tr>
<tr>
<td>选择学科(<span style='color:#F00;'>*</span>)：</td><td>
<select name='pcMetaSubject'>
  <#if subject_list?? >
   <#list subject_list as subj > 
<#if subjectId?? >
<#if subj.msubjId == subjectId >
<option selected value='${subj.msubjId}'>${subj.msubjName}</option>
<#else >
<option value='${subj.msubjId}'>${subj.msubjName}</option>
</#if>
<#else >
<option value='${subj.msubjId}'>${subj.msubjName}</option>
</#if>
   </#list>
  </#if>
</select>
<span id='subject_loading' style='display:none'><img src='images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...</span>
</td>
</tr>
<tr>
<td>备课标签：</td><td>
<#if prepareCourse??>
<input name="pcTags" value='${prepareCourse.tags!}' />
<#else>
<input name="pcTags" value='' />
</#if>
(格式：以逗号隔开。)
</td>
</tr>
<tr style="vertical-align:top">
<td>备课任务(<span style='color:#F00;'>*</span>)：</td><td>
    <script id="DHtml" name="pcDescription" type="text/plain" style="width:880px;height:500px;">
    <#if prepareCourse??>${prepareCourse.description!}</#if>
    </script>
    <script type="text/javascript">
        var editor = UE.getEditor('DHtml');
    </script> 
</td>
</tr>
<tr>
<td></td><td>
<input type="submit" value="保存备课" />
<input type='button' value=' 返  回 ' onclick='window.location="${SiteUrl}p/${prepareCourse.prepareCourseId}/0/"' />
</td>
</tr>
</table>
</form>
<div style='clear:both;'></div>
<#include ('/WEB-INF/ftl/footer.ftl') >
<script type="text/javascript">
function grade_changed(sel)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.pc.pcMetaSubject;
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
  sel.options.add(opt);
}

<#if prepareCourse?? >
function init()
{
  // 得到所选学科.
  var sel = document.pc.elements["pcGrade"];
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.pc.pcMetaSubject;
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
        for (var i = 0; i < options.length; ++i)
        {
          add_option(subject_sel, options[i][0], options[i][1]);
          if( options[i][0] == ${prepareCourse.metaSubjectId} )
          {
            subject_sel.options[i].selected = true
          }
         }
        img.style.display = 'none';
        subject_sel.disabled = false;
      }
  });
}
init();
</#if>

function selectUser()
{
  url = '${SiteUrl}selectUser.py?showgroup=1&singleuser=1&inputUser_Id=pcLeader&inputUserName_Id=pcLeaderName'
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
}
</script>
</body>
</html>



 