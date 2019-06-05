<#-- article_new.ftl, admin_article_edit.ftl 中公共包含此 ftl -->
<link type="text/css" rel="styleSheet" href="../css/uploadify.css" />
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/swfobj.js"></script>
<script type="text/javascript" src="../js/uploadify.js"></script>
<!-- 配置上载路径 -->
<script type="text/javascript">
    window.UEDITOR_UPLOAD_URL = "${SiteUrl}";
    window.UEDITOR_USERLOGINNAME = "<#if loginUser??>${loginUser.loginName!?js_string}</#if>";
</script>
<!-- 配置文件 -->
<script type="text/javascript" src="../manage/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="../manage/ueditor/ueditor.all.js"></script>
<!-- 语言包文件(建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败) -->
<script type="text/javascript" src="../manage/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">

<!--
$(function() {
        $('#upload').uploadify({
            'uploader'          : '${SiteUrl}images/uploadify.swf',
            'script'            : 'article.action',
            'scriptData'        : {
                'cmd'           : 'uploadify',
                'username'      : '${loginUser.loginName}'
            },
            'buttonImg'         : '${SiteUrl}images/browse.gif',
            'cancelImg'         : '${SiteUrl}images/dele.gif',
            //'hideButton'      : true,
            'height'            : '20',
            'width'             : '80',
            'auto'              : true,
            'sizeLimit'         : 1024 * 1024 * 100,
            'fileDesc'          : '支持的文件类型是：doc,docx',
            'fileExt'           : '*.doc;*.docx',
            onComplete          : function(event, queueId, fileObj, response, data) {
                //alert(response);
                var strWordHref= $("#wordHref");
                strWordHref.attr("value", response);
            }
        });
});
//-->
</script>
<form method="post" name="blogForm" id="BlogArticle" action="?">
<#if prepareCourseStage??>
<input type='hidden' name='prepareCourseStageId' value='${prepareCourseStage.prepareCourseStageId}' />
</#if>
<input type="hidden" name="userid" id="userid" value="${userid!?html}"/>
<input type='hidden' name='_groupIndex' value='1' />
<input type='hidden' name='wordHref' id='wordHref' value='${article.wordHref!?string}' />
<input type='hidden' name='cmd' value='save' />
<input type="hidden" id="articleId" name="articleId" value="${article.articleId}" />
<input type="hidden" id="draftState" name="draftState" value="${article.draftState?string('true', 'false')}" />
<input type='hidden' name='title_min_length' value='${Util.JitarConst.MIN_TITLE_LENGTH}'/>
<input type='hidden' name='title_max_length' value='${Util.JitarConst.MAX_TITLE_LENGTH}'/>
<input type='hidden' name='article_max_length' value='${Util.JitarConst.MIN_ARTICLE_LENGTH}'/>
<input type='hidden' name='flag1' value=""/>
<input type='hidden' name='flag2' value=""/>
<input type='hidden' id='flag_submit'/>
<#if returnPage??><input type='hidden' name='returnPage' value='${returnPage}' /></#if>
<#if returnGroupId??><input type='hidden' name='returnGroupId' value='${returnGroupId}' /></#if>
<table class="listTable" cellSpacing="1" cellPadding="0" style="table-layout:fixed">
<tbody>
  <tr>
      <td style="text-align:right;width:100px">
          <strong>文章格式：</strong>
      </td>
      <td>
          <span style="display:inline;">                    
          <label><input type="radio" name="articleFormat" value="0" <#if article.articleFormat??><#if article.articleFormat != 1>checked</#if></#if> onClick="setFormat(0);"/>网页格式</label>
          <label><input type="radio" name="articleFormat" value="1" <#if article.articleFormat??><#if article.articleFormat == 1>checked</#if></#if> onClick="setFormat(1);" />Word格式</label>
          <label id="wordDownloadId" style="visibility:<#if article.articleFormat??><#if article.articleFormat != 1>hidden<#else>visible</#if><#else>hidden</#if>"><input type="checkbox" id="wordDownload" name="wordDownload" value="1" <#if article.wordDownload??><#if article.wordDownload == true>checked</#if></#if> />允许下载Word文档</label>
          </span>
      </td>
  </tr>
<tr>
	<td style="text-align: right;width:100px">
		<strong>文章类型：</strong>
	</td>
	<td>					
		<label><input type="radio" name="articleType" value="false" <#if article.id != 0><#if article.typeState == false>checked</#if></#if> title="鼓励原创" />原创</label>
		<label><input type="radio" name="articleType" value="true" <#if article.typeState == true>checked</#if> />转载</label>
		<font style="color: #FF0000;">* 鼓励原创，转载请注意版权。</font>
	</td>
</tr>
<tr>
	<td align="right">
		<strong>文章标题：</strong>
	</td>
	<td>
		<input id="articleTitle" name="articleTitle" value="${article.title!?html}" style="width: 90%;" maxLength="128" /> <font style="color: #FF0000; font-weight: bold;">*</font>
	</td>
</tr>
<tr>
	<td align="right">
		<strong>学段/学科：</strong>
	</td>
	<td>         
		<select name="gradeId" onchange='grade_changed(this)'>
			<option value=''>选择所属学段</option>
				<#if grade_list??>
					<#list grade_list as grade>
						<option value="${grade.gradeId}" ${(grade.gradeId == (article.gradeId!0))?string('selected', '') } >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
					</#list>
				</#if>
		</select>         
		<select name="subjectId">
			<option value=''>选择所属学科</option>
				<#if subject_list??>
					<#list subject_list as msubj>
						<option value="${msubj.msubjId}" ${(msubj.msubjId == (article.subjectId!0))?string('selected', '')}>${msubj.msubjName!?html}</option>
					</#list>
				</#if>
		</select>
		<span id='subject_loading' style='display:none'>
			<img src='images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...
		</span> <font style="color: #FF0000;">* 文章所属的学科和学段必选。</font>
	</td>
</tr>
<tr>
  <td align="right"><b>所属分类：</b></td>
  <td>
<select name="sysCate" id="sysCate">
  <option value="">请选择系统分类</option>
<#if article_categories??>
  <#list article_categories.all as c>
    <option value="${c.categoryId}" ${(c.categoryId == (article.sysCateId!0))?string('selected', '') }>
      ${c.treeFlag2} ${c.name!?html}
    </option>
  </#list>
</#if>
</select> <font style="color: #FF0000;">* 文章所属的系统分类必选。</font>
  <#if usercate_tree??>
<select name="userCate" id="userCate">
  <option value="null">请选择个人分类</option>
  <#list usercate_tree.all as category>
  <option value="${category.id}" ${(category.categoryId == (article.userCateId!0))?string('selected', '') }>
    ${category.treeFlag2} ${category.name!?html}
  </option>
  </#list>
</select>
  </#if>
  </td>
</tr>
<tr>
  <td align="right"><b>发布到专题：</b></td>
  <td>
  <select name="specialSubjectId">
  <option value="">选择专题名称</option>
  <#if specialsubject_list??>
  <#list specialsubject_list as sl>
    <#if specialSubjectId == sl.specialSubjectId >
    <option value="${sl.specialSubjectId}" selected="selected">${sl.title}</option>
    <#else>
    <option value="${sl.specialSubjectId}">${sl.title}</option>
    </#if>
  </#list>
  </#if>
	</select>
  </td>
</tr>
    <#assign showinput=1>
    <#if article.articleFormat??>
        <#if article.articleFormat == 1>
            <!--原来是Word上载的，不允许再上载-->
            <#assign showinput=0>
        <#else>
        </#if>
    <#else>
        
    </#if>    
    <#if showinput==1>
            <tr style="display:none;" id="wordDiv">
                <td style="text-align: right;">
                    <strong>上传文章：</strong>
                </td>
                <td>   
                <input type="file" name="file" value="" id="upload"/><font color="red">*</font>(只能上载Word文档)
                <#if wordmessage??>
                  <br/>${wordmessage!?html}
                </#if>
                </td>            
            </tr> 
    <#else>
            <tr id="wordDiv">
                <td style="text-align: right;width:100px">
                    <strong>重新上传：</strong>
                </td>
                <td>   
                <input type="file" name="file" value="" id="upload"/><font color="red">*</font>(只能上载Word文档)
                <#if wordmessage??>
                  <br/>${wordmessage!?html}
                </#if>
                </td>            
            </tr>
    </#if>
    </tbody>
</table>
<div style="display:<#if showinput==1><#else>none</#if>;border-left: 1px solid #E6DBC0;border-right: 1px solid #E6DBC0;position:relative" id="inputDiv">
<div style="width:104px;padding-top:8px;padding-right:8px;text-align:right;height:580px"><b>文章内容：</b></div>
<div style="padding-left:108px;position:absolute;top:0">
  <script id="HtmlEditor" name="articleContent" type="text/plain" style="width:1000px;height:500px;">
    ${article.articleContent!}
  </script>
  <script type="text/javascript">
    var editor = UE.getEditor('HtmlEditor');
  </script>
  <style>
  .edui-editor{width:1000px;}
  </style>
</div>
</div>
<table class="listTable" cellSpacing="1" cellPadding="0" style="table-layout:fixed">
<tbody>
<tr>
  <td align="right" style="width:100px"><b>关键字(标签)：</b></td>
  <td>
<input name="articleTags" id="articleTags" value="${article.tags!?html}" size='80' maxlength="128" />
<span style="display:inline;visibility:<#if article.articleFormat??><#if article.articleFormat != 1>hidden<#else>visible</#if><#else>hidden</#if>" id="ts1"><font color="red"> * 关键字必须填写</font></span>
 (请以 ',' 逗号分隔标签，关键字有利于你的文章被更多的人阅读。)
  </td>
</tr>
<tr>
  <td align="right"><b>文章摘要：</b></td>
  <td>
<textarea name="articleAbstract" id="articleAbstract" rows="2" style="width:90%">${article.articleAbstract!?html}</textarea>
<span style="display:inline;visibility:<#if article.articleFormat??><#if article.articleFormat != 1>hidden<#else>visible</#if><#else>hidden</#if>" id="ts2"><font color="red"> * 文章摘要必须填写</font></span>
  </td>
</tr>
<tr>
  <td align="right"><b>其他属性：</b></td>
  <td>
<input type="checkbox" id="commentState" name="commentState" value="true" ${article.commentState?string('checked','')} />允许评论
<input type="checkbox" id="hideState" name="hideState" value="1" ${(article.hideState==1)?string('checked', '')}/>设为隐藏
<#if !(manageMode??)><#assign manageMode = "" ></#if>
<#if manageMode == "admin">
<#if !(auditState??) ><#assign auditState = 0></#if>
<input type="checkbox" id="audit" name="audit" value="0" ${(article.auditState == auditState?int)?string('checked', '')} />审核通过
<input type="checkbox" id="rcmd" name="rcmd" value="true" ${article.recommendState?string('checked', '')} />设为推荐

</#if>
  </td>
</tr>
<#if joined_groups??>
<#if !(groupId??) ><#assign groupId = 0></#if>
<tr>
  <td align='right'><b>同时发布到组：</b></td>
  <td>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0" style="width:100%" id="groupTable">
					<tr>
						<td>
						  <select name="groupId1" id="groupId1" onchange="GetGroupCate(this,1)">
							  <option value="">选择一个协作组</option>
							  <#list joined_groups as g >
							  	<option value='${g.groupId}' ${(groupId == g.groupId)?string(' selected="selected"','')}>${g.groupTitle!?html}</option>
							  </#list>
						  </select>
						  
						  <select name="groupCateId1" id="groupCateId1">
						  	<option value="">选择一个协作组文章分类</option>
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
        <option value="">选择自定义频道文章分类</option>
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
</tbody>
<tfoot>
    <tr>
        <td colspan="2" align="center" height="30">
            <input type="button" class='button' value="${(article.articleId==0)?string('发表文章','修改文章')}" onclick="validateInputData();" id="btn_submit" />&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" class="button" value="取消返回" onClick="javascript:history.back();" />
            <#if article.articleId == 0>
            <input type="button" class='button' value="存为草稿" onclick="save_draft()" />
            </#if>
        </td>
    </tr>
</tfoot>
</table>
</form>
<script>
function validateTitle(k,t){
	var min_length = $('input[name=title_min_length]').val();
    var max_length =  $('input[name=title_max_length]').val();
    var text = $('#articleTitle').val();
    //标题修改为不能为空
    if(text.length < 1 || text.length > max_length){
       $('input[name=flag1]').val('false');
       if(t){
       	  alert('文章标题不能为空，并且不能大于'+max_length+'个汉字');
       	  return false;
       }
    }else{
       $('input[name=flag1]').val('true');
        return true;
    }
}

function invokeFlag(){
  var fflag1 = typeof($('input[name=flag1]').val());
  var fflag2 = typeof($('input[name=flag2]').val());
  var flag1 = 'undefined'!=fflag1?$('input[name=flag1]').val():false;
  var flag2 = 'undefined'!=fflag2?$('input[name=flag2]').val():false;
  var k1 = (flag1=='true')?true:false;
  var k2 = (flag2=='true')?true:false;
  return k1&&k2;
}

//统计编辑器中内容的字数
	function getLength(){
    	   var content = editor.getContent();
	       return content.length;
	}

function validateArticle(t){
		   var text_length = getLength();
		   var max_article_length = $('input[name=article_max_length]').val();
		   if(text_length < max_article_length){
			   $('input[name=flag2]').val('false');
			   if(t){
				   alert("文章内容不能少于" + max_article_length + "字!");
				   return false;
			   } else {
				   $('input[name=flag2]').val('true');
				   return true;
			   }
		   }else{
			   $('input[name=flag2]').val('true');
			   return true;
			   //alert('文章设置:'+'flag2:'+ $('input[name=flag2]').val());
		   }
}

function validateArt(){
	var i = $("input[name='articleFormat']:checked").val();
	if (0 == i) {
		return validateArticle(true);
	} else if (1 == i) {
		return validateArticle(false);
	} else {
		return validateArticle(true);
	}
}

function validateInputData()
{
 //document.getElementById("articleTags").focus();
 var flag1 = validateArt();//验证文章字数 
 var flag = validateTitle($('#articleTitle'),true)
 if(SetgroupIndexValue() == false || flag1 == false || flag == false)
 {
  return;
 }
 <#if channel_List??>
 if(CheckChannelData() == false)
 {
  return;
 }
 </#if>
 
 submit_article();
}

var groupIndex=1;
var currentIndex=1;
var groupOptions="<option value=''>选择一个协作组</option>";
var typeOptions="<option value=''>选择一个协作组文章分类</option>";
<#if joined_groups??>
<#list joined_groups as g >
groupOptions=groupOptions+"<option value='${g.groupId}'${(groupId == g.groupId)?string(' selected=\'selected\'','')}>${g.groupTitle!?html}</option>";
</#list>
</#if>
<#if res_cate??>
    <#list res_cate.all as category>
        typeOptions=typeOptions+"<option value='${category.id}'>${category.treeFlag2 + category.name!?html}</option>";
    </#list>
</#if>
function SetgroupIndexValue()
{
	<#if article??>
	<#if article.articleId==0>
	//检查分组
	var s=",";
	for(i=1;i<=groupIndex;i++)
	{
		var g=document.getElementById("groupId"+i);
		if(g.selectedIndex>0){
			var vue=g.options[g.selectedIndex].value;
			if(s.indexOf(","+vue+",")>=0)
			{
				alert("选择的协助组出现重复.不能发布到相同的组,请重新选择");
				return false;
			}
			s=s+vue+",";
		}
	}
	document.forms.blogForm._groupIndex.value=groupIndex;
	</#if>
	</#if>
	return true;
}
function appendGroup()
{
	groupIndex=groupIndex+1;
	
		var sHtml;
		var oRow1;
		var oTable;
		var oCell1;
		oTable = document.getElementById("groupTable");
		//alert("rows.length=" + oTable.rows.length);
		oRow1 = oTable.insertRow(oTable.rows.length);
		oCell1 = oRow1.insertCell(0);
		//oCell1.height = "25";
		sHtml= "<select name='groupId"+ groupIndex +"' id='groupId"+ groupIndex +"' onchange='GetGroupCate(this,"+ groupIndex +")'>";
		sHtml=sHtml+groupOptions;
		sHtml=sHtml+"</select>";
		sHtml=sHtml+"<select name='groupCateId"+groupIndex+"' id='groupCateId"+groupIndex+"'>"+typeOptions+"</select>";
		oCell1.innerHTML = sHtml;
}
function GetGroupCate(s_id,index)
{
	if(s_id.selectedIndex==-1 || s_id.selectedIndex==0 ){
		var groupCateForm = document.getElementById("groupCateId"+index);
		clear_options(groupCateForm);
		return;
	}
	currentIndex=index;
	var gradeId = s_id.options[s_id.selectedIndex].value;
	var groupCateForm = document.getElementById("groupCateId"+index);
	clear_options(groupCateForm);
	add_option(groupCateForm, '', '选择一个协作组文章分类');
	var url = "${SiteUrl}manage/article.action?cmd=dest_cate&groupId=" + gradeId + '&tmp=' + Math.random();
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
    // 自动化测试的需要，注释了提示信息，有问题找佳佳
    //alert('该协作组尚未建立文章分类');
    //document.getElementById("groupId"+currentIndex).selectedIndex=0;
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
  var subject_sel = document.blogForm.subjectId;

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
<#if joined_groups??>
<#if groupId?? && groupId &gt; 0>
GetGroupCate(document.getElementById("groupId"+groupIndex),groupIndex);
</#if>
</#if>

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
  c_cate_select.options[c_cate_select.options.length] = new Option("选择自定义频道文章分类","");
  c_div.appendChild(c_cate_select);
  c_container.appendChild(c_div);
  document.blogForm.channelTotalCount.value = channelCount;  
}

function GetChannelCate(evt)
{
 var sobj = window.event?window.event.srcElement:evt.target;
 var channelIndex = sobj.name.replace("channelId",""); 
 var formobj = sobj.form;
 var cateobj = formobj.elements["channelCate" + channelIndex];
 clear_options(cateobj);
 add_option(cateobj,"", "选择自定义频道文章分类");
 var channelId = sobj.options[sobj.selectedIndex].value;
 if(channelId != "")
 {
     var url = "${SiteUrl}manage/article.action?cmd=channel_cate&channelId=" + channelId + '&tmp=' + (new Date()).valueOf();
     var myAjax = new Ajax.Request(
     url, {
            method: 'get',
            onComplete: function(xhr){            
            var gres_categories = eval(xhr.responseText);
              if (gres_categories == null || gres_categories.length == null || gres_categories.length == 0) {
                alert('该自定义频道尚未建立文章分类');
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

function setFormat(v){
    if(v==0)
    {
        document.getElementById("wordDownloadId").style.visibility="hidden";
        document.getElementById("wordDiv").style.display="none";
        document.getElementById("inputDiv").style.display="";
        document.getElementById("ts1").style.visibility="hidden";
        document.getElementById("ts2").style.visibility="hidden";
    }
    if(v==1)
    {
        document.getElementById("wordDownloadId").style.visibility="visible";
        document.getElementById("wordDownload").checked=true;
        document.getElementById("wordDiv").style.display="";
        document.getElementById("inputDiv").style.display="none";
        document.getElementById("ts1").style.visibility="visible";
        document.getElementById("ts2").style.visibility="visible";
    }
}
</script>
</body>
</html>