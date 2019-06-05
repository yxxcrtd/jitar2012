<!doctype html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理协作组</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}js/datepicker/calendar.css" />  
  <script src='${SiteUrl}js/datepicker/calendar.js' type="text/javascript"></script>
  <script src='${SiteUrl}js/jitar/core.js' type="text/javascript"></script>
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
      
  <script type="text/javascript">
     function checkData(frm)
    {
        if(frm.plantitle.value=="")
        {
            alert("请输入集体备课计划标题");
            return false;
        }
       
        if(frm.planstartdate.value=="")
        {
            alert("请输入计划开始时间");
            return false;
        }
        if(frm.planenddate.value=="")
        {
            alert("请输入计划结束时间");
            return false;
        }
      var datePattern = /^(\d{4})-(\d{1,2})-(\d{1,2})$/; 
      
      if (! datePattern.test(frm.planstartdate.value)) { 
        window.alert("请填写正确的开始时间"); 
        return false; 
      }
      if (! datePattern.test(frm.planenddate.value)) { 
        window.alert("请填写正确的结束时间"); 
        return false; 
      }    
      var d1 = new Date(frm.planstartdate.value.replace(/-/g, "/")); 
      var d2 = new Date(frm.planenddate.value.replace(/-/g, "/"));
    
      if (Date.parse(d2) - Date.parse(d1) < 0) { 
        window.alert("开始日期必须早于结束日期"); 
        return false;
      }  
      
      if (frm.gradeId.selectedIndex <= 0){
        window.alert("请选择学段/年级"); 
        return false;
      }   
      if (frm.subjectId.selectedIndex <= 0){
        window.alert("请选择学科"); 
        return false;
      }           
        return true;   
    }  
</script>    
</head>
<body>
<#include '/WEB-INF/ftl/group/group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='${SiteUrl}manage/group.py?cmd=home&amp;groupId=${group.groupId}'>协作组管理首页</a> &gt;&gt; <span>制定集备计划</span>
</div>
<div style='color:#f00'>注意：以下所有条目都必须填写，填写完成后一定要点击保存全部按钮。</div>
<form method="post" id='f' style='text-align:left' onsubmit="return checkData(this);">

<table class='listTable' cellspacing="1" style='width:1000px;'>
<tr>
<td class='boldright'>集体备课计划标题(<font color="red">*</font>)：</td><td colspan='3'><input name='plantitle' style='width:90%' /></td>
</tr>
<tr>
<td class='boldright' style='width:160px'>计划开始时间(<font color="red">*</font>)：</td><td><input name='planstartdate' id='start-date' style='width:120px' readonly="readonly"  onClick="WdatePicker()" class="Wdate"/>(格式：yyyy-MM-dd)<span style="color:red">创建之后开始时间不能更改</span></td>
<td class='boldright' style='width:160px'>计划结束时间(<font color="red">*</font>)：</td><td><input name='planenddate' id='end-date'  style='width:120px' readonly="readonly"  onClick="WdatePicker()" class="Wdate"/>(格式：yyyy-MM-dd)</td>
</tr>
<tr>
<td class='boldright'>学段/年级(<font color="red">*</font>)：</td><td>
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
<td class='boldright'>学科(<font color="red">*</font>)：</td><td>
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
<td><input name='plandefault' type='checkbox' value='1' checked='checked' />当前默认计划</td><td>注意：将同时取消其他备课计划设置的默认备课计划。</td>
<td class='boldright'>参与人员：</td><td>组内所有成员</td>
</tr>
</table>
<div style="border-left: 1px solid #E6DBC0;border-right: 1px solid #E6DBC0;">
<div style="padding:6px 0;"><b>计划安排：</b><font style='color:#f00'>（请在右边输入框填写总体集备要求等内容；请用下表填写具体课、人、时间、要求等安排）</font></div>
<script id="DHtml" name="plancontent" type="text/plain" style="width:1000px;height:480px;">
</script>
<script type="text/javascript">
    var editor = UE.getEditor('DHtml');
</script> 
</div>

<div style='padding:10px 2px;'>
<input type='submit' value='保存集备计划' class='button' />
</div>
<!--
<br/>
<table class='listTable' cellspacing="1" style='width:940px;' id='course_table'>
<thead>
<tr>
<td style='font-weight:bold;width:300px;'>课题</td>
<td style='font-weight:bold;width:200px;'>主备人</td>
<td style='font-weight:bold;width:120px;'>开始时间</td>
<td style='font-weight:bold;width:120px;'>结束时间</td>
<td style='font-weight:bold;width:100px;'>排序</td>
<td style='font-weight:bold;width:100px;'>标签</td>
</tr>
</thead>
<tbody>
<tr>
<td><input name='title_1' style='width:98%' /></td>
<td><input id='leaderName_1' style='width:120px' readonly='readonly' /><input style='display:none;' id='leader_1' name='leader_1' /><input type='button' value='选择…' onclick='selectGroupUser(${group.groupId},"leader_1","leaderName_1")' /></td>
<td><input id='start_1' name='start_1' style='width:98%'  readonly="readonly" /></td>
<td><input name='end_1' id='end_1' style='width:98%' readonly="readonly" /></td>
<td><input name='order_1' value='1' style='width:98%' /></td>
<td><input name='tag_1' value='' style='width:98%' /></td>
</tr>
<tr>
<td colspan='6'>
    <script id="dhtml_1" name="content_1" type="text/plain" style="width:880px;height:200px;">
    </script>
    <script type="text/javascript">
        var editor = UE.getEditor('dhtml_1');
    </script> 
</td>
</tr>
</tbody>
</table>

<div style='padding:2px;'>
<input type='button' value='添加课题' class='button' onclick='addItem()' />

<input type='hidden' name='course_count' value='1' />
</div>
<div style='color:#f00'>保存完成后若要修改请点击 管理集备计划 里的该计划名称。</div>
<div style='color:#f00'>为了方便督导，集备课题一旦保存，则该集备的开始时间不能改变.若是误设了开始时间，请在<a href='group_course_plan_manage.py?groupId=${group.groupId}'>管理集备计划</a>中找到该集备计划，点击集备计划名称，打开集备计划，将误设了开始时间的集备删除，重新再添加。</div>
-->
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

function addItem()
{
  var num = parseInt(document.getElementById('f').course_count.value)
  if(isNaN(num))
  {
   alert('非法操作。');
   return;
  }
  num++  

  var t = document.getElementById('course_table')

  var r = document.createElement('tr')
  var c = document.createElement('td');
  c.innerHTML = "<input name='title_"+num+"' style='width:98%' />"
  r.appendChild(c)
  
  c = document.createElement('td');
  c.innerHTML = "<input id='leaderName_"+num+"' style='width:120px' readonly='readonly' /><input style='display:none;' id='leader_"+num+"' name='leader_"+num+"' />"
  btn = document.createElement('input');
  btn.setAttribute('onclick','selectGroupUser(${group.groupId},"leader_'+num+'","leaderName_'+num+'")') 
  btn.setAttribute('type','button')
  btn.setAttribute('value','选择…') //<input type='button' value='选择…' onclick='selectGroupUser(${group.groupId},\"leader_"+num+"\",\"leaderName_"+num+"\")' />
  btn.onclick=function(){var a = eval(num);selectGroupUser(${group.groupId},'leader_'+a ,'leaderName_'+a )}
  c.appendChild(btn)
  r.appendChild(c)
  
  c = document.createElement('td');
  c.innerHTML = "<input id='start_"+num+"' name='start_"+num+"' style='width:98%' readonly='readonly' />"
  r.appendChild(c)
  
  
  c = document.createElement('td');
  c.innerHTML = "<input id='end_"+num+"' name='end_"+num+"' style='width:98%' readonly='readonly' />"
  r.appendChild(c)
  
  
  c = document.createElement('td');
  c.innerHTML = "<input name='order_"+num+"' value='"+num+"' style='width:98%' />"
  r.appendChild(c)
  
  c = document.createElement('td');
  c.innerHTML = "<input name='tag_"+num+"' value='' style='width:98%' />"
  r.appendChild(c)
  
  t.tBodies[0].appendChild(r)
  r = document.createElement('tr')
  c = document.createElement('td');
  c.colSpan = 6;
  
  var hml = "<textarea id='dhtml_" + num +"' name='content_"+num+"' style='display:none;'></textarea>";
  c.innerHTML =  hml;
  
  createEditor(num);
  
  r.appendChild(c)
  t.tBodies[0].appendChild(r)
  calendar.set("start_"+num);
  calendar.set("end_"+num);
  document.getElementById('f').course_count.value = num
  
}

function selectGroupUser(gid,uid,uname)
{
  url = '${SiteUrl}manage/course/get_group_member.py?groupId=' + gid + '&inputUser_Id=' + uid + '&inputUserName_Id=' + uname
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
}

//calendar.set("start-date");
//calendar.set("end-date");
//calendar.set("start_1");
//calendar.set("end_1");

function createEditor(num){
    var editor = UE.getEditor('dhtml_"+num+"');
}
</script>
</body>
</html>