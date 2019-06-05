<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>集体备课 <#include '/WEB-INF/ftl/webtitle.ftl' ></title>
  <link rel="stylesheet" type="text/css" href="../../css/manage.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}js/datepicker/calendar.css" />  
  <script src='${SiteUrl}js/jitar/core.js' type='text/javascript'></script>
  
  <script src="${SiteUrl}js/calendar/WdatePicker.js" type="text/javascript"></script>
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
      
  <script type='text/javascript'>
  function checkForm(oForm)
  {
    if(oForm.pcTitle.value=='')
    {
     alert("请输入备课标题")
     return false;
    }
    
    if(oForm.pcStartDate.value=='')
    {
     alert("请输入开始日期")
     return false;
    }
    
    if(oForm.pcEndDate.value=='')
    {
     alert("请输入结束日期")
     return false;
    }
    
      var datePattern = /^(\d{4})-(\d{1,2})-(\d{1,2})$/; 
      
      if (! datePattern.test(oForm.pcStartDate.value)) { 
        window.alert("请填写正确的开始日期"); 
        return false; 
      }
      if (! datePattern.test(oForm.pcEndDate.value)) { 
        window.alert("请填写正确的结束日期"); 
        return false; 
      }    
      var d1 = new Date(oForm.pcStartDate.value.replace(/-/g, "/")); 
      var d2 = new Date(oForm.pcEndDate.value.replace(/-/g, "/"));
    
      if (Date.parse(d2) - Date.parse(d1) < 0) { 
        window.alert("开始日期必须早于结束日期"); 
        return false;
      } 
          
    //if(oForm.pcGrade.options[oForm.pcGrade.selectedIndex].value == "")
    //{
    // alert("请选择学段")
    // return false;
    //}
    
   //if(oForm.pcMetaSubject.options[oForm.pcMetaSubject.selectedIndex].value == "")
   // {
   //  alert("请选择学科")
   //  return false;
   // }
  }
  </script>
 </head>
 <body>
<#include '/WEB-INF/ftl/course/preparecourse_title.ftl' >
<h3>
<#if prepareCourse??>
修改备课
<#else>
发起备课
</#if>
</h3>

<form method="post" name='pc'<#if prepareCourse??> action='manage_createPrepareCourse.py?prepareCourseId=${prepareCourse.prepareCourseId!0}'</#if>>
<table style="width:1024px">
<tr>
<td style="width:100px">备课题目(<span style='color:#F00;'>*</span>)：</td><td>
<#if prepareCourse??>
<input name="pcTitle" value="${prepareCourse.title!?html}" style='width:600px;' />
<#else>
<input name="pcTitle" value='' style='width:600px;' />
</#if>
</td>
</tr>
<tr>
<td style="width:100px">主备人：</td><td>
<#if prepareCourse??>
<#assign u = Util.userById(prepareCourse.leaderId) >
<input id='pcLeader' name="pcLeader" value='${prepareCourse.leaderId!}' type='hidden' />
<input id="pcLeaderName" value='${u.trueName?html}' readonly='readonly' />
<#else>
<input id='pcLeader' name="pcLeader" value='' type='hidden' />
<input id="pcLeaderName" value='' readonly='readonly' />
</#if>
<#if group??>
<input type='button' onclick='selectGroupUser(${group.groupId})' value='选择用户' />
<#else>
<input type='button' onclick='selectUser()' value='选择用户' />
</#if>

(<span style='color:#F00;'>不填写则设置主备人为创建者自己</span>)
</td>
</tr>
<tr>
<td>开始时间(<span style='color:#F00;'>*</span>)：</td><td>
<#if prepareCourse??>
<input name="pcStartDate" id="start_date" disabled value='${prepareCourse.startDate?string('yyyy-MM-dd')}' />
<#else>
<input name="pcStartDate" id="start_date" value='' readonly="readonly" onClick="WdatePicker()" class="Wdate" />
</#if>
</td>
</tr>
<tr>
<td>结束时间(<span style='color:#F00;'>*</span>)：</td><td>
<#if prepareCourse??>
<input name="pcEndDate" id="end_date" value='${prepareCourse.endDate?string('yyyy-MM-dd')}' readonly="readonly" onClick="WdatePicker()" class="Wdate" />
<#else>
<input name="pcEndDate" id="end_date" value='' readonly="readonly" onClick="WdatePicker()" class="Wdate" />
</#if>
</td>
</tr>
<#if prepareCourse.contentType == 0>
<tr>
<td>共案内容类型(<span style='color:#F00;'>*</span>)：</td>
<td>
<select name="contentType">
<option value="1">直接在线编辑 HTML 格式</option>
<option value="2">直接编辑 Word 2003 文档格式(.doc)</option>
<option value="3">直接编辑 Word 2007 文档格式(.docx)</option>
<option value="4">直接编辑 Word 2010 文档格式(.docx)</option>
<option value="100">直接上传Microsoft Word或者Microsoft PowerPoint文件</option>
</select>
</td>
</tr>
</#if>
<#--  
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
-->
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
</table>



<div style="padding:6px;">备课任务(<span style='color:#F00;'>*</span>)：</div>

  <script id="DHtml" name="pcDescription" type="text/plain" style="width:980px;height:500px;">
  <#if prepareCourse??>${prepareCourse.description!}</#if>
  </script>
  <script type="text/javascript">
      var editor = UE.getEditor('DHtml');
  </script>   
  


<table style="width:1024px">
<tr>
<td></td>
<td>
<input type="submit" value="保存备课"  class='button' onclick='return checkForm(this.form)' />
<input type="button" value="返回协作组"  class='button' onclick='window.top.location.href="${SiteUrl}go.action?groupName=${group.groupName}"' />
<#if prepareCourse??><input type="button" value="返回备课计划"  class='button' onclick='window.location.href="group_course_plan_edit.py?groupId=${group.groupId}&prepareCoursePlanId=${prepareCourse.prepareCoursePlanId}"' /></#if>
</td>
</tr>
</table>
</form>
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
  //var sel = document.pc.elements["pcGrade"];
  //var gradeId = sel.options[sel.selectedIndex].value;
  //var subject_sel = document.pc.pcMetaSubject;
  //subject_sel.disabled = true;
  //var img = document.getElementById('subject_loading');
  //img.style.display = '';
  
  //// 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
  //url = '${SiteUrl}manage/admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + Math.random();
  //new Ajax.Request(url, {
  //  method: 'get',
  //  onSuccess: function(xport) { 
  //      var options = eval(xport.responseText);
  //      clear_options(subject_sel);
  //      for (var i = 0; i < options.length; ++i)
  //      {
  //        add_option(subject_sel, options[i][0], options[i][1]);
  //        if( options[i][0] == ${prepareCourse.metaSubjectId} )
  //        {
  //          subject_sel.options[i].selected = true
  //        }
  //       }
  //      img.style.display = 'none';
  //      subject_sel.disabled = false;
  //    }
  // });
}
init();
</#if>

function selectUser()
{
  url = '${SiteUrl}selectUser.py?showgroup=1&singleuser=1&inputUser_Id=pcLeader&inputUserName_Id=pcLeaderName'
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
}
function selectGroupUser(gid)
{
  url = '${SiteUrl}manage/course/get_group_member.py?groupId=' + gid + '&inputUser_Id=pcLeader&inputUserName_Id=pcLeaderName'
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');	
}


</script>
</body>
</html>



 