<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>上传备课资源</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
	</head>

	<body>
		<h2>备课</h2>

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

		<form name="theForm" action="resource.action?cmd=bksave" method="post" enctype="multipart/form-data">
			<#if prepareCourseStage??>
				<input type="hidden" name="prepareCourseStageId" value="${prepareCourseStage.prepareCourseStageId}" />
			</#if>
			
			<table class="listTable" cellspacing="1">
				<tbody>				
                    <tr>
                        <td style="text-align: right;">
                            <b>资源标题：</b>
                        </td>
                        <td>
                            <input type="text" name="title" value="" style="width: 268px;" />
                            <input type="hidden" name="filename" id="filename" value="${newFileDocName}"/>
                            <input type="hidden" name="file" id="uploadfile" value=""/>
                            &nbsp;&nbsp;<a href="../xkzs.exe">下载Office学科助手</a>&nbsp;&nbsp;如果页面嵌入的模版不能显示，<a href="../WordDoc.htm">请点击</a>
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
							</select>
                            <select name="userCateId">
								<option value="">请选择个人资源分类</option>
								<#if user_res_cate??>
									<#list user_res_cate.all as category>
										<option value="${category.id}">${category.treeFlag2} ${category.name}</option>
									</#list>
								</#if>
							</select>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">
							<b>学科/学段：</b>
						</td>
						<td>
							<select name="subjectId">
								<option value="">请选择资源所属学科</option>
								<#if subject_list??>
									<#list subject_list as subject>
										<option value="${subject.msubjId}" ${(subject.msubjId == (resource.subjectId!0))?string('selected', '')}>${subject.msubjName!?html}</option>
									</#list>
								</#if>
							</select>
							<select name="gradeId">
								<option value="">请选择资源所属学段</option>
								<#if gradeList??>
									<#list gradeList as grade>
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
							<select name="shareMode">
								<option value="1000">完全公开</option>
								<option value="500">组内公开</option>
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
							<textarea name="summary" cols="50" rows="6"></textarea>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">
							<b>同时发布到组：</b>
						</td>
						<td>
							<#if joined_groups??>
								<#list joined_groups as g>
									<input type="checkbox" name="groupIds" id="pub_group_${g.groupId}" value="${g.groupId}">
									<label for="pub_group_${g.groupId}">${g.groupTitle!?html}</label>
									<#if (g_index % 4 == 3)>
										<br/>
									</#if>
								</#list>
							</#if>
						</td>
					</tr>
					<tr>
						<td colspan="2">
<table Border=0 width="100%">
	<tr>
		<td>	
			<OBJECT id="WebOffice" type="text/x-scriptlet" height="550" width="100%" data="/showWordDoc.htm">
			</OBJECT>
			
			<#if (templetFile=="")>
			<SCRIPT event="onreadystatechange" for="WebOffice" type="text/javascript">
				function afterload()
				{
					if(isloadok()==true)
					{
						SetDocUser("");
						SetMarkModify(false);// true:留痕迹   false 不留痕
						ShowRevisions(false);//不显示痕迹
					}
					else
					{
					
						setTimeout(afterload,100)
					}
				}
				if(WebOffice.readyState==4)
				{
						<#if (DocID>0)>
							LoadWebDocument("ReadWordDoc.action?docID=${DocID}");
						<#else>
							LoadDocument("",".doc");
						</#if>
						SetfilenameValue("${newFileDocName}");
						SetFileSavePath("${FileSavePath}");
						SetSubCodeValue("")
						SetDocUser("");//文档的当前用户
						afterload();//判断文档加载完毕，并执行相关设置
				}
			</SCRIPT>    		
			
			<#else>
			<SCRIPT event="onreadystatechange" for="WebOffice" type="text/javascript">
				function afterload()
				{
					if(isloadok()==true)
					{
						SetDocUser("");
						SetMarkModify(false);// true:留痕迹   false 不留痕
						ShowRevisions(false); //不显示痕迹
					}
					else
					{
					
						setTimeout(afterload,100)
					}
				}
				if(WebOffice.readyState==4)
				{
						LoadWebDocument("${templetFile}");
						SetfilenameValue("${newFileDocName}");
						SetFileSavePath("${FileSavePath}");
						SetSubCodeValue("")
						SetDocUser("");//文档的当前用户
						afterload();//判断文档加载完毕，并执行相关设置
				}
			</SCRIPT>    		
			
			</#if>
			
		</td>
	</tr>
</table>						
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="hidden" name="groupId" value="" />
							<input type="button" class="button" onclick="checkFile();" value=" 上   传 " />&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="button" value=" 返   回 " onClick="window.history.back(-1)" />
						</td>
					</tr>
				</tbody>
			</table>
		</form>

<script>
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
function checkFile()
{
	//必须输入标题
	if(document.theForm.title.value=="")
	{
		alert("必须输入标题");
		return;
	}
	if(SaveWordDoc())
	{
		
		document.theForm.submit();
	}
	else
	{
		alert("保存Word文件失败");
	}
}

function ShowSize(files) 
{ 
	return true;
} 
 
function SaveWordDoc()
{
	return document.getElementById("WebOffice").SaveDocument();
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
oClickContent.value = obj.innerText;
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
