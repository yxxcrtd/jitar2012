<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>个人公告管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  
  <script type="text/javascript">
  	//检查是否选择了数据.
   function has_item_selected() {
  	var ids = document.getElementsByName('placard');
  	if (ids == null) return false;
  		for (var i = 0; i < ids.length; ++i) {
    		if (ids[i].checked) return true;
 		}
  		return false;
	}
  //删除记录.
  function delete_placard(list_form) {
  	if(has_item_selected() == false) {
  		alert("请选择您想要操作的公告");
  		return false;
  	} else {
  		if(confirm("确定要删除当前公告么?") == false) {
  			return false;
  		}
  	}
  	list_form.cmd.value = 'del_placard';
  	list_form.submit();
  }
  //隐藏.
  function hide_placard() {
  	submit_command('hide');
  }
  //显示.
  function show_placard() {
  	submit_command('show');
  }
  function submit_command(cmd) {
  	if(has_item_selected() == false) {
  		alert("请选择记录");
  		return false;
  	} 
  	document.forms.list_form.cmd.value = cmd;
  	document.forms.list_form.submit();
  }
  function confirm_delete() {
    return confirm('确定删除当前公告吗?');
  }
    
  //全部选择和取消全选.
  var blnIsChecked = true;
  function sellect_all(list_form) {
	  	for (var i = 0; i < list_form.elements.length; i++) {
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
  </script>
</head>
<body>
<h2>个人公告管理</h2>
<div class='funcButton'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='userPlacard.py?cmd=list'>个人公告管理</a>
</div>

<form name="list_form" action='userPlacard.py' method='post'>
	<input type='hidden' name='cmd' value='' /> 
<table class='listTable' cellspacing='1'>
	<thead>
		<tr>
			<th width='6%'>选择</th>
			<th width='50%'>标题</th>
			<th width='10%'>发表时间</th>
			<th width='5%'>隐藏</th>
			<th width='6%'>操作</th>
		</tr>
	</thead>
	<tbody>
	<#if placard_list??>
	<#list placard_list as placard>
		<tr>
  		 <td><input type='checkbox' name='placard' value='${placard.id}'/> ${placard.id}</td>
		 <td>${placard.title!?html}</td>
		 <td align='center'>${placard.createDate?string("MM-dd   HH:mm")}</td>
		 <td align='center'>${placard.hide?string('隐藏','')}</td>
		 <td>
		 	<a href='userPlacard.py?cmd=edit_placard&placardId=${placard.id}'>修改</a>
		 	<a onclick='return confirm_delete();' href='userPlacard.py?cmd=del_placard&placard=${placard.id}'>删除</a>
		 </td>
		</tr>
	</#list>
	<#else>
	<tr>
	<td colspan='5'>没有公告</td>
	</tr>
	</#if>
	</tbody>
</table>
	<div class='funcButton'>
	  <input class="button" type='button' value='全部选择' id ="selAll" onclick='sellect_all(list_form);' />&nbsp;&nbsp;
		<input class="button" type='button' value='添加公告' onClick="javascript:document.add_placard_form.submit();" />&nbsp;&nbsp;
		<input class="button" type='button' value=' 删 除 ' onclick='delete_placard(list_form);' />&nbsp;&nbsp;
		<input class="button" type='button' value=' 显 示 ' onclick='show_placard();' />&nbsp;&nbsp;
		<input class="button" type='button' value=' 隐 藏 ' onclick='hide_placard();' />&nbsp;&nbsp;
	</div>
</form>

<div style='display:none'>
	<form name="add_placard_form" action="userPlacard.py" method="get" >
		<input type='hidden' name='cmd' value='addPlacard' />
	</form>	
</div>
</body>
</html>
