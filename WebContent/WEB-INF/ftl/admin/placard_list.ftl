<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>系统公告管理</title>
 <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type="text/javascript">
  //删除.
  function delete_placard(list_form) {
	  if(has_item_selected() == false) {
	  	alert("请选择您要操作的公告");
	  	return false;
	  } else {
			if(confirm("确定要删除当前系统公告么?") == false) {
				return false;
			}				  		
	  }
	  list_form.cmd.value = 'del';
	  list_form.submit();
  }
  // 检查是否选择了记录.
  function has_item_selected() {
 	var ids = document.getElementsByName('placard');
 	if (ids == null) return false;
 		for(var i = 0; i < ids.length; ++i) {
 			if(ids[i].checked) return true;
 		}
 		return false;
  }
  //隐藏公告.
  function hide_placard(list_form) {
  	if(has_item_selected() == false) {
  		alert("请选择您要操作的公告");
  		return false;
  	} else {
  		if(confirm("确定要隐藏当前系统公告么?") == false) {
  			return false;
  		}
  	}
  	list_form.cmd.value = 'hide';
  	list_form.submit();
  }
  //显示公告.
  function show_placard(list_form) {
  	if(has_item_selected() == false) {
  		alert("请选择您要操作的公告");
  		return false;
  	} else {
  		if(confirm("确定要显示当前系统公告么?") == false) {
  			return false;
  		}
  	}
  	list_form.cmd.value = 'show';
  	list_form.submit();
  }
  function delete_confirm() {
  	return confirm('确定删除当前公告么');
  }
  var blnIsChecked = true;
  function select_all(list_form) {
  	for(var i = 0; i < list_form.elements.length; i++) {
  		if (list_form.elements[i].type == "checkbox" && !list_form.elements[i].disabled) {
  			list_form.elements[i].checked = blnIsChecked;
  		}
  	}
  	if(list_form.elements["selAll"]) {
  		if(blnIsChecked) {
  			list_form.elements["selAll"].value = "取消全选";
  		} else {
  			list_form.elements["selAll"].value = "全部选择";
  		}
  	} 
  	blnIsChecked = !blnIsChecked;
  	}
  	
  	function sel_subPlacard() {
  		document.list_form.cmd.value = "list";
  		document.list_form.submit();
  	}
  	
  </script>
</head>
<body>

	<#if (subjectId>0)>
  		<h2>${subject.subjectName!}学科公告管理</h2>
  	<#else>
  		<h2>系统公告管理</h2>	
  	</#if>
<form name="list_form" action='admin_placard.action' method='post'>
	<input type='hidden' name='cmd' value='' /> 
	<div class="pager">
	  <a href='?cmd=list&amp;subjectId=-1'>所有公告</a> |
		<a href='?cmd=list&amp;subjectId=0'>主页公告</a>
		<select name="subjectId" onChange="sel_subPlacard();">
			<option value="">选择学科公告</option>
			<#if subject_list??>
			<#list subject_list as s>
			<option value="${s.subjectId}" >${s.subjectName}学科公告</option>
			</#list>
			</#if>
		</select>
	</div>
<table class='listTable' cellspacing='1'>
	<thead>
		<tr>
			<th width='6%'>选择</th>
			<th width='50%'>标题</th>
			<th width='10%'>来源</th>
			<th width='10%'>发表时间</th>
			<th width='5%'>隐藏</th>
			<th width='8%'>操作</th>
		</tr>
	</thead>
	<tbody>
	<#list placard_list as placard>
		<tr>
  	 <td><input type='checkbox' name='placard' value='${placard.id}'/></td>
		 <td><a href="${ContextPath}showPlacard.action?placardId=${placard.id}" target="_blank">${placard.title!?html}</a></td>
		 <td align='center'>${Util.styleNameById(placard.objType,placard.objId)}</td>
		 <td align='center'>${placard.createDate?string("MM-dd HH:mm")}</td>
		 <td align='center'>${placard.hide?string('隐藏','')}</td>
		 <td>
		 	<a href='admin_placard.action?cmd=edit&amp;placardId=${placard.id}&amp;subjectId=${subjectId}'>修改</a>
		 	<a onclick='return delete_confirm();' href='admin_placard.action?cmd=del&amp;placard=${placard.id}'>删除</a>
		 </td>
		</tr>
	</#list>
	</tbody>
</table> 
	<div class='pager'>
  		<#include '../inc/pager.ftl' >  
	</div>
	<div class='funcButton'>    <#--javascript:window.location='admin_placard.action?cmd=add'-->
		<input type='button' class='button' value='全部选择' id="selAll" onclick="select_all(list_form);">
		<input type='button' class='button' value='添加公告' onclick="javascript:document.placardForm.submit()" />&nbsp;&nbsp;
		<input type='button' class='button' value='删   除' onclick='delete_placard(list_form);' />&nbsp;&nbsp;
		<input type='button' class='button' value='显   示' onclick='show_placard(list_form);' />&nbsp;&nbsp;
		<input type='button' class='button' value='隐   藏' onclick='hide_placard(list_form);' />&nbsp;&nbsp;
	</div>
</form> 
	<div style="display:none">
		<form name="placardForm" action="admin_placard.action" method="get">
			<input type="hidden" name="cmd" value="add"/>
		</form>
	<div>
</body>