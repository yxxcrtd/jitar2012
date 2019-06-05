<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>上传资源</title>
<link rel="stylesheet" type="text/css" href="../css/manage.css" />
<link type="text/css" rel="styleSheet" href="../css/uploadify.css" />
<script src='../js/jitar/core.js'></script>
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/swfobj.js"></script>
<script type="text/javascript" src="../js/uploadify.js"></script>
<script type="text/javascript">
<!--
jQuery(function() {
	if ("不限制" == "${filetype?replace('<br/>','')?replace('\n\r','')}") {
		$('#upload').uploadify({
	    	'uploader'			: '${SiteUrl}images/uploadify.swf',
			'script'			: 'resource.action',
			'scriptData'		: {
				'cmd'			: 'uploadify',
				'username'		: '${loginUser.loginName}'
			},
			'buttonImg'			: '${SiteUrl}images/browse.gif',
			'cancelImg'			: '${SiteUrl}images/dele.gif',
			//'hideButton'		: true,
			'height'			: '20',
			'width'				: '80',
			'auto'				: true,
			'sizeLimit'			: 1024 * 1024 * ${filesize},
			onComplete			: function(event, queueId, fileObj, response, data) {
				var strResourceId = jQuery("#resourceId");
				$('#file_title').val(fileObj.name);
				strResourceId.attr("value", response);
			}
		});
	} else {
		jQuery('#upload').uploadify({
	    	'uploader'			: '${SiteUrl}images/uploadify.swf',
			'script'			: 'resource.action',
			'scriptData'		: {
				'cmd'			: 'uploadify',
				'username'		: '${loginUser.loginName}'
			},
			'buttonImg'			: '${SiteUrl}images/browse.gif',
			'cancelImg'			: '${SiteUrl}images/dele.gif',
			//'hideButton'		: true,
			'height'			: '20',
			'width'				: '80',
			'auto'				: true,
			'sizeLimit'			: 1024 * 1024 * ${filesize},
			'fileDesc'			: '支持的文件类型是：${filetype?replace('<br/>','')?replace('\n\r','')}',
			'fileExt'			: '${filetype?replace('<br/>','')?replace('\n\r','')}',
			onComplete			: function(event, queueId, fileObj, response, data) {
				var strResourceId = jQuery("#resourceId");
				jQuery('#file_title').val(fileObj.name);
				strResourceId.attr("value", response);
			}
		});
	}
});
//-->
</script>
</head>

<body>
<h2>上传资源</h2>
<#if num??>[您每天允许上载${num}个资源,今天已经上载${todaynum}个]</#if>
<table align="center">
	<tr>
		<td>
			<#if limit == "true">
				<font style="color: #FF0000; font-size: 15px;">${resSize!}</font>
			</#if>
		</td>
		<td colspan="2">
			<@s.actionerror cssStyle="color:#FF0000; font-weight:bold;" />
		</td>
	</tr>
</table>

<form method="post" name="theForm" action="?">
<input type='hidden' name='cmd' value='save' />
<input type="hidden" id="resourceLen" value='${Util.JitarConst.RESOURCE_MIN_TITLE}'/>
<input type='hidden' name='_groupIndex' value='1' />
<input type='hidden' id='file_title' />
<#if prepareCourseStage??>
<input type="hidden" name="prepareCourseStageId" value="${prepareCourseStage.prepareCourseStageId}" />
</#if>
<#if (groupId??) && (groupId > 0)>
<input type="hidden" name="returnGroupId" value="${groupId}" />
</#if>
<table class="listTable" cellspacing="1">
	<tbody>
		<tr>
      <td style="text-align: right; width: 20%;">
				<strong>文件要求：</strong>
			</td>
			<td style="line-height: 20px; color: #FF0000;">
				文件大小：&lt; <strong>${filesize} M</strong>
				<br />
				<#--文件类型：<strong>${filetype}</strong>-->
			</td>
		</tr>
        <tr>
            <td style="text-align: right;">
                <font style="color: #FF0000;">*</font><b>上传文件：</b>
            </td>
            <td>
            	<@s.file id="upload" name="file" />
            </td>
        </tr>
        <tr>
            <td style="text-align: right; width: 20%;">
                <b>资源标题：</b>
            </td>
            <td>
                <input type="text" name="title" value="" style="width: 268px;" />
                <input id="_checkbox_" type="checkbox" name="nameAsTitle" value="true" checked="true" />使用文件名作为标题
            </td>
        </tr>
        <tr>
			<td style="text-align: right;">
				<b>所属分类：</b>
            </td>
            <td>
				<select name="sysCateId">
					<option value="">请选择系统资源分类</option>
					<#if res_cate??>
						<#list res_cate.all as category>
							<option value="${category.id}">${category.treeFlag2} ${category.name}</option>
						</#list>
					</#if>
				</select><font style="color: #FF0000;">*资源分类必选</font>
                <select name="userCateId">
					<option value="">请选择个人资源分类</option>
					<#if user_res_cate??>
						<#list user_res_cate.all as category>
							<option value="${category.id}">${category.treeFlag2} ${category.name}</option>
						</#list>
					</#if>
				</select>
				<#if channel_resource_categories??>
				<select name="channelCate">
					<option value="">请选择自定义频道分类</option>						
						<#list channel_resource_categories.all as category>
						  <#assign cp = Util.convertIntFrom36To10(category.parentPath) + category.id?string + "/" >
						  <option value="${cp}"${(cp == (resource.channelCate!""))?string(' selected="selected"', '')}>${category.treeFlag2} ${category.name!?html}</option>  
						</#list>
				</select>							
				</#if>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				<b>学段/学科：</b>
			</td>
			<td>
				<select name="gradeId" onchange='grade_changed(this)'>
					<option value="">请选择资源所属学段</option>
					<#if grade_list??>
						<#list grade_list as grade>
							<option value="${grade.gradeId}" ${(grade.gradeId == (resource.gradeId!0))?string('selected', '')} >
								<#if grade.isGrade>
									${grade.gradeName}
								<#else>
									&nbsp;&nbsp;${grade.gradeName}
								</#if>
							</option>
						</#list>
					</#if>
				</select>
			
				<select name="subjectId">
					<option value="">请选择资源所属学科</option>
					<#if subject_list??>
						<#list subject_list as subject>
							<option value="${subject.msubjId}" ${(subject.msubjId == (resource.subjectId!0))?string('selected', '')}>${subject.msubjName!?html}</option>
						</#list>
					</#if>
				</select>
				<span id='subject_loading' style='display:none'>
					<img src='images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...
				</span>  
				 <font style="color: #FF0000;">*学段、学科必选</font>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				<b>资源标签：</b>
			</td>
			<td>
				<input type="text" name="tags" value="" style="width: 268px;" />&nbsp;(以 ',' 逗号分隔多个标签)
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				<b>元数据类型：</b>
			</td>
			<td>
				<input type="text" name="restype" value="" style="width: 268px;" onClick="alert('请在后面文件夹中选择！')" />
				<input type="hidden" name="resTypeID" value="" />
				<a href="JavaScript:" onClick="showResTypeTree()">选 择</a>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				<b>发布方式：</b>
			</td>
			<td>
				<select name="shareMode" onchange="setMode(this)">
					<option value="1000">完全公开</option>
					<#if joined_groups??><option value="500">组内公开</option></#if>
					<option value="0">私有</option>
				</select>
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				<b>资源作者：</b>
			</td>
			<td>
				<input type="text" name="author" value="" style="width: 268px;" />
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				<b>资源出版单位：</b>
			</td>
			<td>
				<input type="text" name="publisher" value="" style="width: 268px;" />
			</td>
		</tr>
		<tr>
			<td style="text-align: right;">
				<b>资源说明：</b>
			</td>
			<td>
				<textarea name="summary" cols="80" rows="2"></textarea>
			</td>
		</tr>
<#if joined_groups??>
<tr>
	<td style="text-align: right;">
		<b>同时发布到组：</b>
	</td>
	<td>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0" style="width:100%" id="groupTable">
					<tr>
						<td>
						<select name="gpId1" id="gpId1" onchange="GetGroupCate(this,1)">
						  <option value="0">选择一个协作组</option>
						  <#list joined_groups as g >
						  <option value='${g.groupId}' ${(groupId == g.groupId)?string(' selected="selected"','')}>${g.groupTitle!?html}</option>
						  </#list>
						  </select>
						  <select name="groupCateId1" id="groupCateId1">
						  <option value="">选择一个协作组资源分类</option>
                  <#if res_cate2??>
                      <#list res_cate2.all as category>
                          <option value='${category.id}'>${category.treeFlag2 + category.name!?html}</option>
                      </#list>
                  </#if>
						  </select>
	  					</td>
					</tr>
				</table>
			</td>
			<td style="width:100px;text-valign:bottom">
				<input type="button" id="addButton" value="推送到其他组" onclick="appendGroup()">
			</td>
		</tr>
	</table>
	</td>
</tr>
</#if>
<#if channel_List??>
<tr>
    <td style="text-align: right;">
        <b>发布到频道：</b>
        <input type="hidden" name="saveNew" value="1" />
        <input type="hidden" name="channelTotalCount" value="1" />
    </td>
    <td style="vertical-align:bottom">
    <table border="0" cellpadding="0" cellspacing="0">
    <tr style="vertical-align:bottom">
    <td id="channelContainer">
        <div>    
        <select name="channelId0" id="channelId0" onchange="GetChannelCate(event)">
        <option value="">选择自定义频道名称</option>
        <#list channel_List as c>
        <option value="${c.channelId}">${c.title!?html}</option>
        </#list>
        </select><select name="channelCate0">
        <option value="">选择自定义频道资源分类</option>
        </select>  
        </div>
     </td>
     <td>
        <input type="button" value="添加其它频道" onclick="addChannelInput()" id="add_channle_button" />
     </td>
     </table>
    </td>
</tr>
</#if>
<tr>
	<td></td>
	<td>
		<@s.hidden id="resourceId" name="resourceId" />
		<input type="hidden" name="groupId" value="${groupId!}" />
		<input type="hidden" name="needreturn" value="${needreturn!}" />
		<input type="button" class="button" value=" 保   存 " onclick="validateInputData();" id="btn_submit" />&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" class="button" value=" 返   回 " onClick="window.history.back(-1)" />
	</td>
</tr>
</tbody>
</table>
</form>
<script>
function validateInputData()
{
 if(!(document.getElementById('_checkbox_').checked)){
    var text = $('#_checkbox_').prev().val();
    var min_len = $('#resourceLen').val();
    //修改为不为空即可发布。
    if(text.length < 1){
       alert('资源标题不能为空');
       return;
    }
 }else{
    var title = $('#file_title').val()
    var real = title.substring(0,title.lastIndexOf('.'))
    var len = real.length;
    var min_len = $('#resourceLen').val();
	if(len < 1){
	   $('#_checkbox_').attr({"disabled":"disabled"}).removeAttr("checked");
	   alert("资源标题长度不够,请输入资源标题...");
	   $('#_checkbox_').focus();
	   return;
	}
 }
 if(SetgroupIndexValue() == false)
 {
  return;
 }
 <#if channel_List??>
 if(CheckChannelData() == false)
 {
  return;
 }
 </#if>
 checkFile();
}

var groupIndex=1;
var currentIndex=1;
var groupOptions="<option value='0'>选择一个协作组</option>";
var typeOptions="<option value=''>选择一个协作组资源分类</option>";
<#if joined_groups??>
<#list joined_groups as g >
groupOptions=groupOptions+"<option value='${g.groupId}' ${(groupId == g.groupId)?string(' selected=\'selected\'','')}>${g.groupTitle!?html}</option>";
</#list>
</#if>
<#if res_cate2??>
    <#list res_cate2.all as category>
        typeOptions=typeOptions+"<option value='${category.id}'>${category.treeFlag2 + category.name!?html}</option>";
    </#list>
</#if>
function setMode(sel){
	if(sel.selectedIndex==-1) return;
	var v=sel.options[sel.selectedIndex].value;
	var oTable = document.getElementById("groupTable");
	if(""+v=="0"){
		oTable.disabled=true;
		SetGroupDisable(true);
		document.getElementById("addButton").disabled=true;
		<#if channel_List??>SetChannelSelectDisable(true);</#if>
	}
	else{
		oTable.disabled=false;
		SetGroupDisable(false);
		<#if channel_List??>SetChannelSelectDisable(false);</#if>
		document.getElementById("addButton").disabled=false;
	}
}

function SetGroupDisable(bDisable)
{
	for(i=1;i<=groupIndex;i++)
	{
		var g=document.getElementById("gpId"+i);
		g.disabled=bDisable;
	}
}
function SetgroupIndexValue()
{
	//检查分组
	//alert(groupIndex);
	var s=",";
	for(i=1;i<=groupIndex;i++)
{
	var g=document.getElementById("gpId"+i);
	if(g.selectedIndex>0){
			var vue=g.options[g.selectedIndex].value;
			if(s.indexOf(","+vue+",")>=0)
			{
				alert("选择的协助组重复.不能发布到相同的组");
				return false;
			}
			s=s+vue+",";
		}
	}
	if(document.theForm.shareMode.options[document.theForm.shareMode.selectedIndex].value=="500")
	{
	 if(s == ",")
	 {
	  alert("要设置组内共享，请选择一个协作组。");
	  return false;
	 }
	}
	document.forms.theForm._groupIndex.value=groupIndex;
	
	var sid = document.theForm.sysCateId.options[document.theForm.sysCateId.selectedIndex].value;
	if(sid == "")
	{
	 alert("请选择系统分类。")
	 return false;
	}
	
	var gid = document.theForm.gradeId.options[document.theForm.gradeId.selectedIndex].value;
	if(gid == "")
	{
	 alert("请选择学段。")
	 return false;
	}
	
	var ssid = document.theForm.subjectId.options[document.theForm.subjectId.selectedIndex].value;
	if(ssid == "")
	{
	 alert("请选择学科。")
	 return false;
	}
	
}
function appendGroup()
{
	groupIndex=groupIndex+1;
	
		var sHtml;
		var oRow1;
		var oTable;
		var oCell1;
		oTable = document.getElementById("groupTable");
		oRow1 = oTable.insertRow(oTable.rows.length);
		//document.createElemnt
		oCell1 = oRow1.insertCell(0);
		sHtml= "<select name='gpId"+ groupIndex +"' id='gpId"+ groupIndex +"' onchange='GetGroupCate(this,"+ groupIndex +")'>";
sHtml=sHtml+groupOptions;
sHtml=sHtml+"</select>";
sHtml=sHtml+"<select name='groupCateId"+groupIndex+"' id='groupCateId"+groupIndex+"'>"+typeOptions+"</select>";
		oCell1.innerHTML = sHtml;
		//alert(oCell1.innerHTML);
}
function showDiv(obj,id){
var oDiv = document.getElementById(id);
	oDiv.style.display = "block";
	oDiv.style.border = "1px dashed #0099FF";
	obj.onmouseout = function(){
		try{
			var initDiv = event.toElement;
				if(initDiv.id == id){
					oDiv.onmouseover = function(){
						oDiv.style.display = "block";
						oDiv.style.border = "1px dashed #0099FF";
					}
					oDiv.onmouseout = function(){
						oDiv.style.display = "none";
					}
				}
				else if(initDiv.id != id){
					oDiv.style.display = "none";
				}
		}catch(e){ }
	}
}
function isDigit(s) { 
	var patrn=/^[0-9]{1,20}$/; 
	if (!patrn.exec(s))
		return false 
	return true 
} 
function checkFile() {
var strResourceId = $("#resourceId").val();
if (strResourceId != "" && isDigit(strResourceId) || "f"==strResourceId) {
    
	if (ShowSize(document.getElementById('upload').value)==false) {
		
	}
	else
	{  
		document.theForm.submit();
		window.document.getElementById("btn_submit").disabled = "disabled"
		window.document.getElementById("btn_submit").value = "正在提交数据...";
	}
  } else {
  	alert("没有上传文件！");
  }
}

function ShowSize(files) 
{ 
	var fso, f; 
	try
	{
		fso=new ActiveXObject("Scripting.FileSystemObject"); 
		f=fso.GetFile(files); 
		if(f.size > 1024*1024*5){
			alert("页面上载文件的大小不能超过15M");
			return false; 
		}
		return true;
	}
	catch(ex) {
		return true;
	}
} 
 


function showResTypeTree(){
    var toolbarSty = "dialogwidth=720px;dialogheight=540px;center=yes;scrolling:no;border=thick;status=no;help=no";
    var url = "../resTypeTree.action?tmp=" + Date.parse(new Date());
    var url = "../js/admin/main.htm"
    
    if (/Firefox[\/\s](\d+\.\d+)/.test(navigator.userAgent)){
	 var ffversion=new Number(RegExp.$1)
	 if(ffversion < 3)
	 {
	  window.open(url,'_blank','width=800,height=600,resizable=1,scrollbars=1')
	  return;
	 }
	}
    

	var res = window.showModalDialog(url,null,toolbarSty);
	if(res==undefined){
		res="";
		return;
	}
	var arr=res.split("|");
	document.theForm.restype.value=arr[0];
	document.theForm.resTypeID.value=arr[1];
}

function getClickContent(obj, id) {
var oClickContent = document.getElementById(id);
var oHiddenContent = document.getElementById("resTypeID");
oClickContent.value = obj.innerHTML;
oHiddenContent.value = obj.name;
}
</script>

<style>
.searchClass{
	width:100px;
	margin:-223px auto auto 204px;
	background-color:white;
	border:1px dashed green;
	text-align:left;
	float:left;
}
.searchClass ul{
	margin:3px auto auto 5px;
}
.searchClass ul li{
	list-style-type:none;
	text-align:left;
	line-height:25px;
}
</style>

<div id="search" class="searchClass" style="display:none">
	<ul>
		<#if resTypeList??>
			<#list resTypeList as resType>
				<li>
					<a href="javascript:" name="${resType.tcId!?html}" onClick="getClickContent(this,'restype')">${resType.tcTitle!?html}</a>
				</li>
			</#list>
		</#if>
	</ul>
</div>
</body>
</html>
<script>

function GetGroupCate(sid,index)
{
	if(sid.selectedIndex==-1 || sid.selectedIndex==0 ){
		var groupCateForm = document.getElementById("groupCateId"+index);
		clear_options(groupCateForm);
		return;
	}
	currentIndex=index;
	
	var gradeId = sid.options[sid.selectedIndex].value;
	var groupCateForm = document.getElementById("groupCateId"+index);
	clear_options(groupCateForm);
	add_option(groupCateForm, '', '选择一个协作组资源分类');
	var url = "${SiteUrl}manage/resource.action?cmd=dest_cate&groupId=" + gradeId + '&tmp=' + Math.random();
	var myAjax = new Ajax.Request(
	url, {
		method: 'get',
		onComplete: fill_gres_cate,    // 指定回调函数.
		asynchronous: true             // 是否异步发送请求.
	});
}

function fill_gres_cate(request)
{
  var gres_categories = eval(request.responseText);
  if (gres_categories == null || gres_categories.length == null || gres_categories.length == 0) {
    alert('该协作组尚未建立资源分类');
    //document.getElementById("gpId"+currentIndex).selectedIndex=0;
    return;
  }
  var groupCateForm = document.getElementById("groupCateId"+currentIndex);
  for (var i = 0; i < gres_categories.length; ++i) {
    c = gres_categories[i];
    add_option(groupCateForm, c.id, c.treeFlag2 + ' ' + c.name);
  }
}


function grade_changed(sel)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.theForm.subjectId;

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

<#if channel_List??>
var c_id_array = new Array(${channel_List?size});
var c_title_array = new Array(${channel_List?size});
<#list channel_List as c>
c_id_array[${c_index}] = "${c.channelId}";c_title_array[${c_index}] = "${c.title!?js_string}";
</#list>
var channelCount = 0;
function addChannelInput()
{    
  var c_container = document.getElementById("channelContainer");
  //判断频道是否已经超过；  
  var select_count = c_container.getElementsByTagName("select");
  if(select_count.length / 2 >= c_id_array.length)
  {
   alert("不能再添加自定义频道了，已经超过可选的频道数量了。");
   return;
  }
  channelCount++;
  var c_div = document.createElement("div");
  c_div.style.paddingTop = "4px";
  var c_select = document.createElement("select");
  c_select.name = "channelId" + channelCount;
  c_select.id = "channelId" + channelCount;
  c_select.options[c_select.options.length] = new Option("选择自定义频道名称","");
  for(i=0;i<c_id_array.length;i++)
  {
    c_select.options[c_select.options.length] = new Option(c_title_array[i],c_id_array[i]);
  }
  if( c_select.addEventListener ) // W3C, FF  
  {        
    c_select.addEventListener('change', GetChannelCate, false);  
  }  
  else if( c_select.attachEvent ) // IE  
  {  
   c_select.attachEvent('onchange', GetChannelCate);  
  }  
  c_div.appendChild(c_select);
  
  var c_cate_select = document.createElement("select");
  c_cate_select.name = "channelCate" + channelCount;
  c_cate_select.options[c_cate_select.options.length] = new Option("选择自定义频道图片分类","");
  c_div.appendChild(c_cate_select);
  c_container.appendChild(c_div);
  document.theForm.channelTotalCount.value = channelCount;  
}

function GetChannelCate(evt)
{
 var sobj = window.event?window.event.srcElement:evt.target;
 var channelIndex = sobj.name.replace("channelId",""); 
 var formobj = sobj.form;
 var cateobj = formobj.elements["channelCate" + channelIndex];
 clear_options(cateobj);
 add_option(cateobj,"", "选择自定义频道资源分类");
 var channelId = sobj.options[sobj.selectedIndex].value;
 if(channelId != "")
 {
     var url = "${SiteUrl}manage/resource.action?cmd=channel_cate&channelId=" + channelId + '&tmp=' + (new Date()).valueOf();
     var myAjax = new Ajax.Request(
     url, {
            method: 'get',
            onComplete: function(xhr){            
            var gres_categories = eval(xhr.responseText);
              if (gres_categories == null || gres_categories.length == null || gres_categories.length == 0) {
                alert('该自定义频道尚未建立资源分类');
                return;
              }
              for (var i = 0; i < gres_categories.length; ++i) {
                c = gres_categories[i];
                add_option(cateobj, c.path, c.treeFlag2 + ' ' + c.name);
              }
            },   
            asynchronous: true             // 是否异步发送请求.
      });
  }
}

function CheckChannelData()
{
 var s=",";
 for(i=0;i<=channelCount;i++)
 {
  var c = document.getElementById("channelId" + i);
  if(c.selectedIndex > 0)
  {
        var v = c.options[c.selectedIndex].value;
        if(s.indexOf("," + v + ",") > -1)
        {
            alert("一篇文章只能发布到一个自定义频道，请修改后再提交。");
            return false;
        }
        s = s + v + ",";
  }
 }
 return true;
}

function SetChannelSelectDisable(state){
 var c_container = document.getElementById("channelContainer");
  var select_count = c_container.getElementsByTagName("select");
  for(i=0;i<select_count.length;i++)
  {
   select_count[i].disabled = state;
  }
   document.getElementById("add_channle_button").disabled = state;
}
</#if>
</script>
