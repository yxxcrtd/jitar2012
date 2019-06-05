<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>${(photoId==0)?string('上传照片', '编辑照片信息')}</title>
<link rel="styleSheet" type="text/css" href="../css/manage.css" />
<link rel="stylesheet" type="text/css" href="../css/msgbox.css" />
<script type="text/javascript" src="../js/msgbox.js"></script>
<script type="text/javascript" src="../js/jitar/core.js"></script>        
<script type="text/javascript">
<!--
var groupIndex=1;
var currentIndex=1;
var groupOptions="<option value='0'>选择一个协作组</option>";
var typeOptions="<option value=''>选择一个协作组图片分类</option>";
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
	function resizeimg(ImgD, iwidth, iheight, photoId) {
 		var image = new Image();
 		image.src = ImgD.src;
 		     		
 		if (image.width > 0 && image.height > 0) {
    		if (image.width / image.height >= iwidth / iheight) {
				if (image.width > iwidth) {
					ImgD.width = iwidth;
           			ImgD.height = (image.height * iwidth) / image.width;
       			} else {
					ImgD.width = image.width;
					ImgD.height = image.height;
				}
				ImgD.alt = image.width + "×" + image.height;
 		   	} else {
				if (image.height > iheight) {
					ImgD.height = iheight;
					ImgD.width = (image.width * iheight) / image.height;
				} else {
					ImgD.width = image.width;
					ImgD.height = image.height;
				}
				ImgD.alt = image.width + "×" + image.height;
			}
　　　　　	ImgD.style.cursor = "pointer"; //改变鼠标指针
　　　　　	ImgD.onclick = function() {
				window.open(this.src);
			}
			//点击打开原始图片
			ImgD.title = "点击可在新窗口打开原始图片";
		}
	}
	function addMore() {
		var td = document.getElementById("more");	
		var br = document.createElement("br");
		var input = document.createElement("input");
		var button = document.createElement("input");
		
		input.type = "file";
		input.name = "file";
		input.size = "35";
		
		button.type = "button";
		button.value = " 取  消 ";
		
		button.onclick = function() {
			td.removeChild(br);
			td.removeChild(input);
			td.removeChild(button);
		}				
		td.appendChild(br);
		td.appendChild(input);
		td.appendChild(button);	
	}
	
	// 添加一个分类
	function add_new_cate()
	{
		var cate_name = document.getElementById("new_cate_name")
		if(cate_name == null)
		{
		 alert("error,无效的输入框。")
		 return;
		}
		
		var cate_name_value = cate_name.value;
		if(cate_name_value == '')
		{
			alert("请输入分类名称。")
		 	return;	
		}			
		
		//title,stapleDescribe,isHide
		var postData = 'title=' + encodeURIComponent(cate_name_value) + '&stapleDescribe=&isHide=false';
		var url = '${SiteUrl}manage/photostaple.action?cmd=save&refer=msgbox';
    	new Ajax.Request(url, { 
          method: 'post',
          parameters:postData,
          onSuccess:function(xhr){
          	if(xhr.responseText.indexOf("200 OK") > -1)
          	{
          		newStapleId = xhr.responseText.split("||")[1]
          		selectStaple = document.list_form.userStapleId;
          		selectStaple.options[selectStaple.options.length] = new Option(cate_name_value,newStapleId,true,true);
          		MessageBox.Close();
   		   	}
          	else
          	{
          		if(window.confirm("新分类添加失败，你需要转到分类页面进行添加分类吗？"))
          		{
          		  window.location.href='${SiteUrl}manage/photostaple.action?cmd=add'
          		}
          	}          	
          	},
          onFailure:function(xhr){alert('修改数据失败。' + xhr.responseText);	}
        }
      );	
	}
	
	// 显示分类对话框
	function add_cate()
	{
		MessageBox.Show('MessageTip');
	}
	function close_dialog(event) {
	  if (event.keyCode == 27) {
	    MessageBox.Close();
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
    add_option(groupCateForm, '', '选择一个协作组图片分类');
    var url = "${SiteUrl}manage/photo.action?cmd=dest_cate&groupId=" + gradeId + '&tmp=' + Math.random();
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
    alert('该协作组尚未建立图片分类');
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
  document.list_form.channelTotalCount.value = channelCount;  
}

function GetChannelCate(evt)
{
 var sobj = window.event?window.event.srcElement:evt.target;
 var channelIndex = sobj.name.replace("channelId",""); 
 var formobj = sobj.form;
 var cateobj = formobj.elements["channelCate" + channelIndex];
 clear_options(cateobj);
 add_option(cateobj,"", "选择自定义频道图片分类");
 var channelId = sobj.options[sobj.selectedIndex].value;
 if(channelId != "")
 {
     var url = "${SiteUrl}manage/photo.action?cmd=channel_cate&channelId=" + channelId + '&tmp=' + (new Date()).valueOf();
     var myAjax = new Ajax.Request(
     url, {
            method: 'get',
            onComplete: function(xhr){            
            var gres_categories = eval(xhr.responseText);
              if (gres_categories == null || gres_categories.length == null || gres_categories.length == 0) {
                alert('该自定义频道尚未建立图片分类');
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
</#if>
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
            alert("一张图片只能发布到一个自定义频道，请修改后再提交。");
            return false;
        }
        s = s + v + ",";
  }
 }
 return true;
}

function setDisabled(flag){
    var gt = document.getElementById("groupTable");
    var cc = document.getElementById("channelContainer");
    var ssc = document.getElementById("specialSubjectContainer");
    var slts;
    if(gt){
     slts = gt.getElementsByTagName("select");
     for(i=0;i<slts.length;i++){
      slts[i].disabled = flag;
     }    
    }
    
    if(cc){
     slts = cc.getElementsByTagName("select");
     for(i=0;i<slts.length;i++){
      slts[i].disabled = flag;
     }    
    }
    
   if(ssc){
     slts = ssc.getElementsByTagName("select");
     for(i=0;i<slts.length;i++){
      slts[i].disabled = flag;
     }    
    }
}
// -->
</script>
</head>
<body style="margin-top: 20px;" onLoad="javascript:list_form.title.focus();">
<h2>
	<#if photoId == 0>
		上传照片
	<#else>
		编辑照片信息
	</#if>
</h2>

<table align="center">
	<tr>
		<td colspan="2">
			<@s.actionerror cssClass="actionError" />
		</td>
	</tr>
</table>

<form name="list_form" action="photo.action" theme="simple" method="post" enctype="multipart/form-data">
	<table class="listTable" align="center" width="100%" class='listTable' cellSpacing='1'>
		<#if photoId != 0>
		<tr>
			<td colSpan="2" style="text-align: center;">
				<img onload="CommonUtil.reFixImg(this, 150, 200)" src="${Util.url(href!)}" /><br />
			</td>
		</tr>
		</#if>
		<tr>
			<td style="text-align: right; width: 20%;">
				<b>
					照片标题：
				</b>
			</td>
			<td>
				<@s.textfield name="title" value="${title!?html}" maxlength=100 cssStyle="width: 377px;" onfocus="this.select();" onmouseover="this.focus();" />
				<font style="color: #FF0000; font-size: 15px; font-weight: bold;">
				 * 
				</font>
				照片标题不能为空！
			</td>
		</tr>
		<tr>
			<td style="text-align: right; width: 20%;">
				<b>
					发布选项：
				</b>
			</td>
			<td>
				<#if specialSubjectId?? && photoId == 0 && specialSubjectId != 0 >
				<label><input type="radio" name="isPrivateShow" value="true" onclick="setDisabled(true)" />只发布到个人空间</label>
				<label><input type="radio" name="isPrivateShow" value="false" checked = 'checked' onclick="setDisabled(false)" />不只发布到个人空间</label>
				<#else>
				<label><input type="radio" name="isPrivateShow" value="true" <#if isPrivateShow == true>checked</#if>  onclick="setDisabled(true)" />只发布到个人空间</label>
				<label><input type="radio" name="isPrivateShow" value="false" <#if isPrivateShow == false >checked</#if>  onclick="setDisabled(false)" />不只发布到个人空间</label>
				</#if>
			</td>
		</tr>
		<#if loginUser.userId == userId>
		<tr>
			<td align="right">
				<b>
					系统/相册分类：
				</b>
			</td>
			<td>
				<select name="sysCateId">
					<option value="">
						&nbsp;请选择一个相册的系统分类&nbsp;
					</option>
					<#list syscate_tree.all as category>
					<option value="${category.id}" <#if category.id==sysCateId>selected</#if>>
						${category.treeFlag2} ${category.name}
					</option>
					</#list>
				</select>
				&nbsp;
				<select name="userStapleId">
					<option value="">
                        &nbsp;请选择一个相册的个人分类&nbsp;
					</option>
					<#list photoStapleList as photoStaple>
					<option value="${photoStaple.id}" <#if photoStaple.id==userStapleId>selected</#if>>
						${photoStaple.title}
					</option>
					</#list>
				</select>
				<input type='button' value='添加新分类' style='font-weight:bold' onclick='add_cate()' />
			</td>
		</tr>
		</#if>	
		<tr>
			<td align="right">
				<b>
					发布到专题：
				</b>
			</td>
			<td id="specialSubjectContainer">
			<select name="specialSubjectId">
			<option value=''>选择专题名称</option>
			<#if specialsubject_list?? >
			<#list specialsubject_list as sl>
				<#if specialSubjectId?? && specialSubjectId == sl.specialSubjectId >
				<option value='${sl.specialSubjectId}' selected='selected'>${sl.title?html}</option>
				<#else>
				<option value='${sl.specialSubjectId}'>${sl.title?html}</option>
				</#if>
			</#list>
			</#if>
			</select>
			</td>
		</tr>		
		<tr>
			<td align="right">
				<b>
					照片标签：
				</b>
			</td>
			<td>
				<@s.textfield name="tags" cssStyle="width: 377px;" maxLength="125" />(照片标签以逗号隔开)
			</td>
		</tr>
		<tr>
			<td align="right">
				<b>
					照片描述：
				</b>
			</td>
			<td>
				<textarea rows="2" style="width:90%" name="summary">${summary!?html}</textarea>
			</td>
		</tr>
<#if channel_List??>
<tr>
    <td style="text-align: right;">
        <b>发布到自定义频道：</b>        
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
        <option value="">选择自定义频道图片分类</option>
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
                          <option value="">选择一个协作组图片分类</option>
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
<#if photoId == 0>
<tr>
	<td align="right">
		<b>
			照片文件：
		</b>
	</td>
	<td id="more">上传照片的文件类型和图片大小：(<font style="color: #FF0000;">bmp,png,gif,jpg,jpeg, 单张图片大小<6M</font>)<br>
		<input type="file" name="file" size='35' value="" /><br />
		<input type="file" name="file" size='35' value="" /><br />
		<input type="file" name="file" size='35' value="" /><br />
		<input type="file" name="file" size='35' value="" />						
	</td>
</tr>
</#if>
<tr>
	<td colSpan="2" align="center">
		<@s.hidden name="cmd" value="save" />
		<@s.hidden name="photoId" value="${photoId}" />
		<#if photoId == 0>
		<input class="button" type="button" style='color:#f00' value="点击添加更多图片" onClick="addMore()">&nbsp;&nbsp;
		</#if>
		<input class="button" type="submit" value="${(photoId==0)?string(' 上 传 图 片', ' 修 改 图 片 ')}" onclick="return validateInputData()" />&nbsp;&nbsp;
		<input class="button" type="button" value=" 返  回 " onClick="window.history.back();" />
	</td>
</tr>
</table>
<input type="hidden" name="groupId" value="${groupId!}" />
</form>
<div id="blockUI" onclick="return false" onmousedown="return false" onmousemove="return false" onmouseup="return false" ondblclick="return false">
  &nbsp;
</div>

<#-- 对话框 -->
<div id='MessageTip' class='message_frame' style='width:260px;' onkeydown='close_dialog(event)'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='../images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)"><img src='images/dialog.gif' align='absmiddle' hspace='3' />请输入分类名称</div>
  </div>
  <div style='padding:20px;'>
<input id='new_cate_name' value='' style='width:200px;' />     
  </div> 
  <div style='text-align:center;clear:both;padding:10px'>
<input type='button' class='button' value = ' 确  定 ' onclick='add_new_cate();' />
<input type='button' class='button' value = ' 取  消 ' onclick="return MessageBox.Close();" />
  </div>
</div>
<#if specialSubjectId?? && photoId == 0 && specialSubjectId != 0 >
<#else>
<#if isPrivateShow?? && isPrivateShow == true><script>setDisabled(true);</script></#if>
</#if>
</body>
</html>
