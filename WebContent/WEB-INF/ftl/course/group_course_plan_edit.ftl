<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
    function checkData2(frm)
    {
        if(frm.pctitle.value=="")
        {
            alert("请输入集备标题");
            return false;
        }
       
        if(frm.startDate.value=="")
        {
            alert("请输入集备开始时间");
            return false;
        }
        if(frm.endDate.value=="")
        {
            alert("请输入集备结束时间");
            return false;
        }
      var datePattern = /^(\d{4})-(\d{1,2})-(\d{1,2})$/; 
      
      if (! datePattern.test(frm.startDate.value)) { 
        window.alert("请填写正确的开始时间"); 
        return false; 
      }
      if (! datePattern.test(frm.endDate.value)) { 
        window.alert("请填写正确的结束时间"); 
        return false; 
      }    
      var d1 = new Date(frm.startDate.value.replace(/-/g, "/")); 
      var d2 = new Date(frm.endDate.value.replace(/-/g, "/"));
    
      if (Date.parse(d2) - Date.parse(d1) < 0) { 
        window.alert("开始日期必须早于结束日期"); 
        return false;
      }  
      
      var pcpStartDate =  new Date("${prepareCoursePlan.startDate?string('yyyy/MM/dd')}");
      var pcpEndDate =  new Date("${prepareCoursePlan.endDate?string('yyyy/MM/dd')}");
      if (Date.parse(d1) - Date.parse(pcpStartDate) < 0) { 
        window.alert("开始日期必须晚于计划的开始日期"); 
        return false;
      }       
       if (Date.parse(d2) - Date.parse(pcpEndDate) > 0) { 
        window.alert("结束日期必须早于计划的结束日期"); 
        return false;
      }    
      
      return true;       
    }
</script>    
  <script type="text/javascript">
  function SetForm(cmdvalue)
  {
    if(window.confirm('你真的要这么做吗？'))
    {
  	  document.getElementById('ff').cmd.value = cmdvalue;
  	  document.getElementById('ff').submit();
  	}
  }
  </script>  
</head>
<body>
<#include '/WEB-INF/ftl/group/group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='${SiteUrl}manage/group.py?cmd=home&amp;groupId=${group.groupId}'>协作组管理首页</a>
  &gt;&gt; <span>管理集备计划</span>
</div>
<h3 style='border-bottom:1px solid #f00;padding-bottom:2px'>备课计划编辑</h3>
<div style='color:#f00'>以下各项所有项目需必须填写。</div>
<#if prepareCoursePlan?? >
<#assign course_count = 0>
<form method="post" id='ff' onsubmit="return checkData(this);">
<input type='hidden' name='cmd' value='edit' />
<table class='listTable' cellspacing="1" style="width:1024px">
<tr>
<td class='boldright' style='width:150px'>集体备课计划标题(<font color="red">*</font>)：</td><td><input name='plantitle' value='${prepareCoursePlan.title?html}' style='width:98%'/></td>
</tr>
<tr>
<td class='boldright'>计划开始时间(<font color="red">*</font>)：</td><td><input id='start-date' disabled name='planstartdate' value='${prepareCoursePlan.startDate?string('yyyy-MM-dd')}' onClick="WdatePicker()" class="Wdate"/>(格式：年年年年-月月-日日)</td>
</tr>
<tr>
<td class='boldright'>计划结束时间(<font color="red">*</font>)：</td><td><input id='end-date' name='planenddate' value='${prepareCoursePlan.endDate?string('yyyy-MM-dd')}' onClick="WdatePicker()" class="Wdate"/>(格式：年年年年-月月-日日)</td>
</tr>
<tr>
<td class='boldright'>学段/年级(<font color="red">*</font>)：</td><td>
<select id='_gradeId' name="gradeId" onchange='grade_changed(this)'>
<option value=''>选择所属学段</option>
	<#if grade_list??>
		<#list grade_list as grade>
		    <#if prepareCoursePlan??>
			<option value="${grade.gradeId}" ${(grade.gradeId == (prepareCoursePlan.gradeId!0))?string('selected', '') } >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
			<#else>
			<option value="${grade.gradeId}" >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
			</#if>
		</#list>
	</#if>
</select>
</td>
</tr>
<tr>
<td class='boldright'>学科(<font color="red">*</font>)：</td><td>
<select name="subjectId">
  <option value=''>选择所属学科</option>
	<#if subject_list??>
		<#list subject_list as msubj>
		<#if prepareCoursePlan?? >
			<option value="${msubj.msubjId}" ${(msubj.msubjId == (prepareCoursePlan.subjectId!0))?string('selected', '')}>${msubj.msubjName!?html}</option>
		<#else>
			<option value="${msubj.msubjId}" >${msubj.msubjName!?html}</option>
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
<td>&nbsp;</td><td><input name='plandefault' type='checkbox' value='1' ${prepareCoursePlan.defaultPlan?string("checked='checked'","")} />设置为当前组内默认计划（将同时取消所设置的其他默认）</td>
</tr>
</table>
<div style="border-left: 1px solid #E6DBC0;border-right: 1px solid #E6DBC0;">
<div style="font-weight:bold;padding:8px 0;padding-left:6px">计划任务：</div>
<script id="DHtml" name="plancontent" type="text/plain" style="width:1024px;height:200px;">
${prepareCoursePlan.planContent!}
</script>
<script type="text/javascript">
 var editor = UE.getEditor('DHtml');
</script> 
</div>

<div style='padding:2px 0 10px 0'>
<input type='submit' value='保存集备计划' class='button' />
</div>
</form>
<#if pc_list?? >
<h3 style='border-bottom:1px solid #f00;padding-bottom:2px'>该备课计划下的所有备课</h3>
<form method='post'>
	<input type='hidden' name='cmd' value='itemorder' />
	<table class='listTable' cellspacing="1" style="width:1024px;">
	<thead>
	<tr>
	<td style='font-weight:bold'>课题</td>
	<td style='font-weight:bold'>主备人</td>
	<td style='font-weight:bold'>集备开始时间</td>
	<td style='font-weight:bold'>集备结束时间</td>
	<td style='font-weight:bold'>共案内容类型</td>
	<td style='font-weight:bold'>排序</td>
	<td style='font-weight:bold'>操作</td>	
	</tr>
	</thead>
<#if pc_list?? >
<#list pc_list as pc>
<tr style='background:#eee'>
<#assign u=Util.userById(pc.leaderId)>
<#assign course_count = pc.itemOrder + 1>
	<td><a href='${SiteUrl}p/${pc.prepareCourseId}/0/' target='_blank'>${pc.title?html}</a></td>
	<td><a href='${SiteUrl}go.action?userId=${u.userId}' target='_blank'>${u.trueName}</a></td>
	<td>${pc.startDate?string("yyyy-MM-dd")}</td>
	<td>${pc.endDate?string("yyyy-MM-dd")}</td>
	<td>
	<#if pc.contentType == 1>
	直接在线编辑 HTML 格式
	<#elseif pc.contentType == 2>
	直接从网站打开 Word 2003 文档格式(.doc)进行编辑
	<#elseif pc.contentType == 3>
	直接从网站打开直接编辑 Word 2007 文档格式(.docx)进行编辑
	<#elseif pc.contentType == 4>
	直接从网站打开直接编辑 Word 2010 文档格式(.docx)进行编辑
	<#elseif pc.contentType == 5>
	直接从网站打开直接编辑 Word 2013 文档格式(.docx)进行编辑
	<#elseif pc.contentType == 100>
	从网站下载文件，编辑完成后再上传(支持 .doc、.docx、ppt、pptx文件）
	<#else>
	未设置，需<a style="color:red" href='manage_createPrepareCourse.py?prepareCourseId=${pc.prepareCourseId}&groupId=${group.groupId}&prepareCoursePlanId=${prepareCoursePlan.prepareCoursePlanId}'>修改</a>
	</#if>
	</td>
	<td><input type='hidden' name='pcid' value='${pc.prepareCourseId}' /><input name='itemorder${pc.prepareCourseId}' value='${pc.itemOrder}' size='3' /></td>
	<td>
	<#if pc.prepareCourseGenerated>
	<a href='manage_createPrepareCourse.py?prepareCourseId=${pc.prepareCourseId}&groupId=${group.groupId}&prepareCoursePlanId=${prepareCoursePlan.prepareCoursePlanId}'>修改</a> | 
	<a href='group_course_delete.py?prepareCourseId=${pc.prepareCourseId}&groupId=${group.groupId}&prepareCoursePlanId=${prepareCoursePlan.prepareCoursePlanId}'>删除</a>
	<#else>
	<a href='group_course_genernate.py?prepareCourseId=${pc.prepareCourseId}&groupId=${group.groupId}&prepareCoursePlanId=${prepareCoursePlan.prepareCoursePlanId}'>生成备课</a> | 
	<a href='group_course_delete.py?prepareCourseId=${pc.prepareCourseId}&groupId=${group.groupId}&prepareCoursePlanId=${prepareCoursePlan.prepareCoursePlanId}'>删除</a>
	</#if>
	<#if pc.status == 1> 
	| <a href='group_course_approve.py?prepareCourseId=${pc.prepareCourseId}&groupId=${group.groupId}&prepareCoursePlanId=${prepareCoursePlan.prepareCoursePlanId}&status=0'>审核</a>
	<#else>
		<#if pc.status == 0>
			| <a href='group_course_approve.py?prepareCourseId=${pc.prepareCourseId}&groupId=${group.groupId}&prepareCoursePlanId=${prepareCoursePlan.prepareCoursePlanId}&status=1'>撤销审核</a>
		</#if>	
	</#if>
    </td>
	</tr>
	<tr>
	<td colspan='7' style='padding:0'>
	 <table style='width:100%' cellpadding='2' cellspacing='0'><tr><td style='font-weight:bold'>备课任务</td><td style='width:100%'>${pc.description}</td></tr></table>
	</td>
	</tr>
</#list>
</#if>
</table>
<input type='submit' value='保存集备顺序' class='button' />
</form>
</#if>
<a name='bottom'></a>
<h3 style='border-bottom:1px solid #f00;padding-bottom:2px'>添加集备</h3>
<form method='post' onsubmit="return checkData2(this);">
<input type='hidden' name='cmd' value='add' />
<table class='listTable' cellspacing="1" style="width:1024px;">
<tr>
<td class='boldright' style='width:150px;'>课题（集备标题）(<font color="red">*</font>)：</td><td><input name='pctitle' style='width:98%' /></td>
</tr>
<tr>
<td class='boldright'>集备开始时间(<font color="red">*</font>)：</td><td><input id='pcs-date' name='startDate'  readonly="readonly"  onClick="WdatePicker()" class="Wdate"/>(格式：年年年年-月月-日日)
<div style='color:#f00;display:inline'>一旦保存，则开始时间不能更改</div>
</td>
</tr>
<tr>
<td class='boldright'>集备结束时间(<font color="red">*</font>)：</td><td><input id='pce-date' name='endDate' readonly="readonly"  onClick="WdatePicker()" class="Wdate"/>(格式：年年年年-月月-日日)</td>
</tr>
<tr>
<td class='boldright'>主备人：</td><td><input name='pcleaderName' id='pc_name' readonly="readonly" /><input type='hidden' name='pcLeader' id='pc_id' /> <input type='button' value='选择主备人' onclick='selectGroupUser(${group.groupId},"pc_id","pc_name")' />（如果不选，则创建者是主备人）</td>
</tr>
	<tr>
	<td class='boldright'>共案内容格式：</td><td>
	<select name='contentType'>
	<option value="1">直接在线编辑 HTML 格式</option>
	<option value="2">直接编辑 Word 2003 文档格式(.doc)</option>
	<option value="3">直接编辑 Word 2007 文档格式(.docx)</option>
	<option value="4">直接编辑 Word 2010 文档格式(.docx)</option>
	<option value="100" selected="selected">直接上传Microsoft Word或者Microsoft PowerPoint文件</option>
	</select>
	<span style="color:red">一旦保存，共案内容格式不能更改</span>
	</td>
	</tr>
<tr>
<td class='boldright'>标签：</td><td><input name='pctags' /></td>
</tr>
<tr>
<td class='boldright'>备课任务：</td>
<td></td>
</tr>
</table>
<div style="border-left: 1px solid #E6DBC0;border-right: 1px solid #E6DBC0;">
<script id="DHtml2" name="pcdesc" type="text/plain" style="width:1027px;height:200px;"></script>
<script type="text/javascript">
  var editor2 = UE.getEditor('DHtml2');
</script> 
</div>
<br/>
<input name='pc_itemorder' type='hidden' value='${course_count}' />
<input type='submit' value='保存集备' class='button' />
<#--<input type='button' value='返回协作组' class='button' onclick="window.top.href='${SiteUrl}go.action?groupName=${group.groupName}'" />-->
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
//grade_changed(document.getElementById('_gradeId'))

function selectUser()
{
  url = '${SiteUrl}selectUser.py?showgroup=1&singleuser=1&inputUser_Id=pcLeader&inputUserName_Id=pcLeaderName'
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
}

function selectGroupUser(gid,uid,uname)
{
  url = '${SiteUrl}manage/course/get_group_member.py?groupId=' + gid + '&inputUser_Id=' + uid + '&inputUserName_Id=' + uname
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
}
</script>
<#else>
请选择一个备课计划。
</#if>
</body>
</html>