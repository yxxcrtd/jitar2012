<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<title>视频</title>
	<link rel="stylesheet" type="text/css" href="../css/manage.css" />
	<link type="text/css" rel="styleSheet" href="../css/uploadify.css" />
	<script type="text/javascript" src='../js/jitar/core.js'></script>
	<script type="text/javascript" src="../js/jquery.js"></script>
	<script type="text/javascript" src="../js/swfobj.js"></script>
	<script type="text/javascript" src="../js/uploadify.js"></script>
	<script type="text/javascript">
	var groupIndex=1;
	var currentIndex=1;
	var groupOptions="<option value='0'>选择一个协作组</option>";
	var typeOptions="<option value=''>选择一个协作组视频分类</option>";
	<#if joined_groups??>
	<#list joined_groups as g >
	groupOptions=groupOptions+"<option value='${g.groupId}' ${(groupId == g.groupId)?string(' selected=\'selected\'','')}>${g.groupTitle!?html}</option>";
	</#list>
	</#if>
	<#if res_cate??>
	    <#list res_cate.all as category>
	        typeOptions=typeOptions+"<option value='${category.id}'>${category.treeFlag2 + category.name!?html}</option>";
	    </#list>
	</#if>
	
	$(function() {
		$('#upload').uploadify({
		   	'uploader'			: '${SiteUrl}images/uploadify.swf',
			'script'			: 'video.action',
			'scriptData'		: {
				'cmd'			: 'uploadify',
				'username'		: '${loginUser.loginName}'
			},
			'buttonImg'			: '${SiteUrl}images/browse.gif',
			'cancelImg'			: '${SiteUrl}images/dele.gif',
			'height'			: '20',
			'width'				: '80',
			'auto'				: true,
			'sizeLimit'			: 1024 * 1024 * 400,
			'fileDesc'			: 'avi;mpg;wmv;3gp;mov;mp4;asf;asx;flv;hlv;f4v;m4v;mpeg;mpeg4;mpe;mkv;rm;rmvb',
			'fileExt'			: '*.avi;*.mpg;*.wmv;*.3gp;*.mov;*.mp4;*.asf;*.asx;*.flv;*.hlv;*.f4v;*.m4v;*.mpeg;*.mpeg4;*.mpe;*.mkv;*.rm;*.rmvb',
			onComplete		: function(event, queueId, fileObj, response, data) {
				var strVideoId = $("#videoId");
				strVideoId.attr("value", response);
			}
		});
	});
	</script>
</head>
<body>
<#if video??>
<#if video.videoId = 0><h2>上传视频</h2><#else><h2>修改视频</h2></#if>
<#else>
<h2>上传视频</h2>
</#if>
<table align="center">
	<tr>
		<td>
			<h3><@s.actionerror cssClass="actionError" /></h3>
		</td>
	</tr>
</table>

<table class="listTable" cellSpacing="1" style="border-bottom:0">
	<tbody>
		<#if videoId = 0>
			<tr>
                <td style="text-align: right; width: 15%;">
					<strong>文件要求：</strong>
				</td>
				<td style="line-height: 20px; color: #FF0000;">
					文件大小：&lt; <strong>400 M</strong>&nbsp;&nbsp;&nbsp;&nbsp;(视频不占用个人空间容量)
					<br />
					文件类型：<strong>avi、mpg、wmv、3gp、mov、mp4、asf、asx、flv、hlv、f4v、m4v、mpeg、mpeg4、mpe、mkv、rm、rmvb</strong>
				</td>
			</tr>
            <tr>
                <td style="text-align: right;">
                    <font style="color: #FF0000;">＊</font><b>选择视频：</b>
                </td>
                <td>
                	<@s.file id="upload" name="file" />
                </td>
            </tr>            
        </#if>
  </tbody>
</table>
<div id="fileDetail">
<@s.form name="editForm" action="video">
<input type="hidden" name="admin" value="${admin!}"/>
<table class="listTable" cellSpacing="1">
<tbody>
    <tr>
		<td style="text-align: right; width: 15%;">
			<b>视频类型：</b>
        </td>
        <td>
        	<#if video??>
        		<#if video.typeState=true>
        			<@s.radio name="typeState" value="true" list=r'#{"true" : "原创", "false" : "转载"}' required="true" />
        		<#else>
        			<@s.radio name="typeState" value="false" list=r'#{"true" : "原创", "false" : "转载"}' required="true" />
        		</#if>
        	<#else>
        		<@s.radio name="typeState" value="false" list=r'#{"true" : "原创", "false" : "转载"}' required="true" />
        	</#if>
		</td>
	</tr>
    <tr>
        <td style="text-align: right;">
            <b>视频标题：</b>
        </td>
        <td>
        	<#if video??>
        		<input type="text" name="title" value="${video.title}" style="width: 268px;" maxlength="125" />
        	<#else>
        		<input type="text" name="title" value="" style="width: 268px;" maxlength="125" />
        	</#if>
            
            <#if videoId = 0 >
            	<input type="checkbox" name="nameAsTitle" value="true" checked="checked" />使用文件名作为标题
            </#if>
        </td>
    </tr>
	<tr>
		<td style="text-align: right;">
			<b>视频标签：</b>
		</td>
		<td>
			<#if video??>
				<input type="text" name="tags" value="${video.tags}" style="width: 268px;" />&nbsp;(多个标签以英文的逗号分隔)
			<#else>
				<input type="text" name="tags" value="" style="width: 268px;" />&nbsp;(多个标签以英文的逗号分隔)
			</#if>
		</td>
	</tr>
	<tr>
	<td style="text-align: right;"><b>专题分类：</b></td>
	<td>
	<select name="specialSubjectId">
	<option value="">请选择专题名称</option>
	<#if specialSubjectList??>
	<#list specialSubjectList as sl>
	<option value="${sl.specialSubjectId!}"<#if video?? && video.specialSubjectId?? && (video.specialSubjectId == sl.specialSubjectId)> selected="selected"</#if>>${sl.title!}</option>
	</#list>
	</#if>
	</select>
	</td>
</tr>					
<tr>
    <td style="text-align: right;">
		<b>视频分类：</b>
    </td>
    <td>
    	<#if video??>
			<select name="categoryId">
				<option value="">请选择系统视频分类</option>
					<#list videoCategory.all as vc>
						<#if video.categoryId?? &&  vc.categoryId==video.categoryId>
							<option value="${vc.categoryId}" selected>${vc.treeFlag2} ${vc.name}</option>
						<#else>
							<option value="${vc.categoryId}">${vc.treeFlag2} ${vc.name}</option>
						</#if>
					</#list>
			</select>
       <#else>
			<select name="categoryId">
				<option value="">请选择系统视频分类</option>
					<#list videoCategory.all as vc>
						<option value="${vc.categoryId}">${vc.treeFlag2} ${vc.name}</option>
					</#list>
			</select>
		</#if>
        <select name="userCateId">
            <option value="">请选择个人视频分类</option>
            <#if user_video_cate??>  
                <#list user_video_cate.all as category>
                    <#if video??>
                        <option value="${category.id}" ${(category.id == (video.userCateId!0))?string(' selected="selected"', '')}>${category.treeFlag2} ${category.name}</option>
                    <#else>
                        <option value="${category.id}">${category.treeFlag2} ${category.name}</option>
                    </#if>            
                </#list>
            </#if>
        </select>
	</td>
</tr>
<tr>
	<td style="text-align: right;">
		<b>学段/学科：</b>
	</td>
	<td>
		<select name="gradeId" onchange='grade_changed(this)'>
			<option value="">请选择视频所属学段</option>
			<#if video??>
			<#if grade_list??>
				<#list grade_list as grade>
					<option value="${grade.gradeId}" ${(grade.gradeId == (video.gradeId!0))?string('selected', '')} >
						<#if grade.isGrade>
							${grade.gradeName}
						<#else>
							&nbsp;&nbsp;${grade.gradeName}
						</#if>
					</option>
				</#list>
			</#if>
			<#else>
			<#if grade_list??>
				<#list grade_list as grade>
					<option value="${grade.gradeId}" ${(grade.gradeId == (userGradeId!0))?string('selected', '')}>
						<#if grade.isGrade>
							${grade.gradeName}
						<#else>
							&nbsp;&nbsp;${grade.gradeName}
						</#if>
					</option>
				</#list>
			</#if>
			</#if>
		</select>
	
		<select name="subjectId">
			<option value="">请选择视频所属学科</option>
			<#if video??>
			<#if subject_list??>
				<#list subject_list as subject>
					<option value="${subject.msubjId}" ${(subject.msubjId == (video.subjectId!0))?string('selected', '')}>${subject.msubjName!?html}</option>
				</#list>
			</#if>
			<#else>
				<#list subject_list as subject>
					<option value="${subject.msubjId}" ${(subject.msubjId == (userSubjectId!0))?string('selected', '')}>${subject.msubjName!?html}</option>
				</#list>
			</#if>
		</select>
		<span id='subject_loading' style='display:none'>
			<img src='images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...
		</span>  选择您的视频所属的学科和年级, 如果不属于或没有可以不选择.
		
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
                          <option value="">选择一个协作组视频分类</option>
                            <#if res_cate??>
                                <#list res_cate.all as category>
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
        <input type="hidden" name="channelTotalCount" value="1" />
        <input type="hidden" name="saveNew" value="1" />
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
        </select><select name="channelCate0" id="channelCate0">
        <option value="">选择自定义频道视频分类</option>
        </select>  
        </div>
     </td>
     <td>
        <input type="button" value="添加其它频道" onclick="addChannelInput()" />
     </td>
     </table>
    </td>
</tr>
</#if> 
<tr>
<td style="text-align: right;">
	<b>视频描述：</b>
</td>
<td>
	<#if video??>
	  <textarea name="summary" style="width:100%;height:80px">${video.summary!?html}</textarea>
	<#else>
		<textarea name="summary" style="width:100%;height:80px"></textarea>
	</#if>      
</td>
</tr>
<tr>
<td></td>
<td>
	<@s.hidden name="cmd" value="save" />
	<@s.hidden name="videoId" id="videoId" value="${videoId!}" />
	<@s.hidden name="needreturn" id="needreturn" value="${needreturn!}" />
	<input type="submit" class="button" value=" 保   存 " onclick="return validateInputData()" />&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" class="button" value=" 返   回 " onClick="window.history.back(-1)" />
</td>
</tr>
</tbody>
</table>
<input type="hidden" name="groupId" value="${groupId!}" />
</@s.form>
</div>
	</body>
</html>

<script>
function grade_changed(sel) {
  // 得到所选学科
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.editForm.subjectId;

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
    add_option(groupCateForm, '', '选择一个协作组视频分类');
    var url = "${SiteUrl}manage/video.action?cmd=dest_cate&groupId=" + gradeId + '&tmp=' + Math.random();
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
    alert('该协作组尚未建立视频分类');
    //document.getElementById("gpId"+currentIndex).selectedIndex=0;
    return;
  }
  var groupCateForm = document.getElementById("groupCateId"+currentIndex);
  for (var i = 0; i < gres_categories.length; ++i) {
    c = gres_categories[i];
    add_option(groupCateForm, c.id, c.treeFlag2 + ' ' + c.name);
  }
}
function clear_options(sel) {
  sel.options.length = 0;
}
function add_option(sel, val, text) {
    sel.options[sel.options.length] = new Option(text.replace(/&nbsp;/g," "),val)
    
}


function validateInputData()
{
 <#if channel_List??>
 if(CheckChannelData() == false)
 {
  return false;
 }
 </#if>
 return true;
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
  c_cate_select.id = "channelCate" + channelCount;
  c_cate_select.options[c_cate_select.options.length] = new Option("选择自定义频道视频分类","");
  c_div.appendChild(c_cate_select);
  c_container.appendChild(c_div);
  document.editForm.channelTotalCount.value = channelCount;  
}

function GetChannelCate(evt)
{
 var sobj = window.event?window.event.srcElement:evt.target;
 var channelIndex = sobj.name.replace("channelId",""); 
 var cateobj = document.getElementById("channelCate" + channelIndex);
 clear_options(cateobj);
 add_option(cateobj,"", "选择自定义频道视频分类");
 var channelId = sobj.options[sobj.selectedIndex].value;
 if(channelId != "")
 {
     var url = "${SiteUrl}manage/video.action?cmd=channel_cate&channelId=" + channelId + '&tmp=' + (new Date()).valueOf();
     var myAjax = new Ajax.Request(
     url, {
            method: 'get',
            onComplete: function(xhr){            
            var gres_categories = eval(xhr.responseText);
              if (gres_categories == null || gres_categories.length == null || gres_categories.length == 0) {
                alert('该自定义频道尚未建立视频分类');
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
</#if>

</script>