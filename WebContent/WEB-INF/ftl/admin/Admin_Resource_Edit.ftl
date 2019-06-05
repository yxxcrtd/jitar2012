<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
        <title>编辑资源属性</title>
        <link rel="styleSheet" type="text/css" href="../css/manage.css" />
    </head>

    <body style="margin-top: 20px;">
        <h2>
            编辑资源属性
        </h2>

        <form name="theForm" action="admin_resource.py?cmd=save" method="post">
        <input type="hidden" name="x" value="${x!?html}">
        <input type="hidden" name="subId" value="${subId!?html}">
            <table class="listTable" cellspacing="1">
                <tbody>
                    <tr>
                        <td style="text-align: right;">
                            <b>
                                    资源标题：
                            </b>
                        </td>
                        <td>
                            <input type="text" name="title" value="${resource.title!?html}" size="40" />
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: right;">
                            <b>
                                    所属分类：
                            </b>
                        </td>
                        <td>
                            <select name="sysCateId">
                                <option value="">请选择系统资源分类</option>
                                <#list resource_categories.all as c>
                                <#assign selected = (c.categoryId == resource.sysCateId!0)?string('selected', '')>
                                <option value="${c.categoryId}" ${selected}>${c.treeFlag2!} ${c.name!}</option>
                                </#list>
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
                            <b>
                                    学段/学科：
                            </b>
                        </td>
                        <td>
                            <select name="gradeId" onchange='grade_changed(this)'>
                                <option value="">请选择资源所属学段</option>
                                <#if grade_list??>
                                <#list grade_list as grade>
                                <option value="${grade.gradeId}" ${(grade.gradeId == (resource.gradeId!0))?string('selected', '') } >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
                                </#list>
                                </#if>
                            </select>
                            <select name="subjectId">
                                <option value="">请选择资源所属学科</option>
						        <#if subject_list?? >
						          <#list subject_list as msubj>
						            <option value="${msubj.msubjId}" ${(msubj.msubjId == (resource.subjectId!0))?string('selected', '')}>
						            ${msubj.msubjName!?html}
						            </option>
						          </#list>
						        </#if>
                            </select>
							<span id='subject_loading' style='display:none'>
								<img src='images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...
							</span>  选择资源所属的年级和学科, 如果不属于或没有可以不选择.
                        </td>
                    </tr>
                <tr>
                
      <td style="text-align: right;"><b>资源标签：</b></td>
      <td>
        <input type="text" name="tags" value="${resource.tags!?html}" size="32" />
      </td>
    </tr>
     <tr>
      <td style="text-align: right;"><b>元数据类型：</b></td>
      <td>
        <input type="text" name="restype" value="${resType!?html}" size="20" onclick="alert('请在后面文件夹中选择！')" />
        <input type="hidden" name="resTypeID" value="${resource.resTypeId!?html}"/>
		<a href="javascript:"  onclick="showResTypeTree()">选 择</a>
      </td>
    </tr>
    <tr>
      <td style="text-align: right;"><b>发布方式：</b></td>
      <td>
        <select name="shareMode">
			    <option <#if resource.shareMode==1000>selected</#if> value="1000">完全公开</option>
			    <option <#if resource.shareMode==500>selected</#if>  value="500">组内公开</option>
			    <option <#if resource.shareMode==0>selected</#if> value="0">私有</option>
        </select>
      </td>
    </tr>
    <tr>
      <td style="text-align: right;"><b>资源作者：</b></td>
      <td>
        <input type="text" name="author" value="${resource.author!?html}" size="40" />
      </td>
    </tr>
    <tr>
      <td style="text-align: right;"><b>资源出版单位：</b></td>
      <td>
        <input type="text" name="publisher" value="${resource.publisher!?html}" size="40" />
      </td>
    </tr>
    <tr>
      <td style="text-align: right;"><b>资源说明：</b></td>
      <td>
        <textarea name="summary" cols="90" rows="6">${resource.summary!?html}</textarea>
      </td>
    </tr>
    <tr>
      <td></td>
      <td>
        <input type="hidden" name="resourceId" value="${resource.resourceId}" />
        <input type="submit" class="button" value=" 修  改 " />&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" class="button" value=" 返 回 " onClick="window.history.go(-1);" />
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
function showResTypeTree(){
	var toolbarSty = "dialogwidth=430px;dialogheight=450px;center=yes;scrolling:no;border=thick;status=no;help=no";
	var url = "../resTypeTree.action";
	var url = "../js/admin/main.htm";
	var res = window.showModalDialog(url,null,toolbarSty);
	if(res==undefined){
		res="";
		return;
	}
	var arr=res.split("|");
	document.theForm.restype.value=arr[0];
	document.theForm.resTypeID.value=arr[1];
}

function getClickContent(obj,id){
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
</body>
</html>
<script src='../js/jitar/core.js'></script>

<script>
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
    sel.options[sel.options.length] = new Option(text,val)
    
}
</script>