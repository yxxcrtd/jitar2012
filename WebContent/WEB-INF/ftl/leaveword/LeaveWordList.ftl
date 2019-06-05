<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html>
	<head>
		<title><@s.text name="groups.message.message" /></title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css">
		<script type="text/javascript">
		<!--
		var blnIsChecked = true;
		function on_checkAll(oForm) {
			for (var i = 0; i < oForm.elements.length; i++) {
				if (oForm.elements[i].type == "checkbox" && !oForm.elements[i].disabled) {
				}
				oForm.elements[i].checked = blnIsChecked;
			}
			if (oForm.elements["selAll"]) {
				if (blnIsChecked) {
					oForm.elements["selAll"].value = "<@s.text name="groups.public.unSelectAll" />";
				} else {
					oForm.elements["selAll"].value = "<@s.text name="groups.public.selectAll" />";
				}
			}
			blnIsChecked = !blnIsChecked;
		}
    	function delSel(list_form) {
    		if (hasChecked(list_form) == false) {
    			alert("<@s.text name="groups.public.noSelect" />");
    			return false;
    		} else {
	  			if (confirm("<@s.text name="groups.public.delSelConfirm" />") == false) {
	  				return false;
	  			}
  			}
  			list_form.cmd.value = "del";
  			list_form.submit();
   	 	}   	 	
		function hasChecked(vform) {
			for (var i = 0; i < vform.elements.length; i++) {
				var e = vform.elements[i];
				if (e.checked) {
					return true;
				}
			}
			return false;
		}
		function delSel(list_form){
			if (hasChecked(list_form) == false) {
    			alert("没有选择要删除的留言");
    			return false;
    		} else {
	  			if (confirm("确定删除当前选择的留言吗?") == false) {
	  				return false;
	  			}
  			}
  			list_form.cmd.value = "del";
  			list_form.submit();
		
		
		
		}
		function show_hide(_obj) {
			var obj = document.getElementById(_obj);
			if(obj.style.display == "none") {
				obj.style.display = "block";
			}
			else if(obj.style.display != "none") {
				obj.style.display = "none";
			}
		}
		function isRead(id) {
			list_form.cmd.value = "read";
			document.list_form.action = "message.action?unreadId=" + id;
			document.list_form.submit();
		}
		//-->
		</script>
	</head>

<body>
<h2>留言管理</h2>
<div class='funcButton'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='leaveword.action?cmd=list'>留言管理</a>
</div>

<form name="list_form" action="leaveword.action" method="post">
  <input type="hidden" name="cmd" value="" />
	<table class="listTable" cellspacing="1" >
			<tr>
				<th>选择</th>
				<th>发送者</th>
				<th width="50%">留言内容</th>
				<th>留言时间</th>
				<th>回复次数</th>
				<th>操作</th>
			</tr>
			<#list leaveword_list as leaveword>
			<tr>
				<td align="center">
					<input type="checkbox" id="MId" name="leavewordId" value="${leaveword.id}" />${leaveword.id!}
				</td>
				<td align="center">
					<a href="${SiteUrl}go.action?loginName=${leaveword.loginName!}" target="_blank">
						${leaveword.loginName!}
					</a>
				</td>		
				<td>
					<div><b>标题:</b> ${leaveword.title!}</div>
					<div><b>内容:</b> ${leaveword.content!}</div>
				</td>
				<td align="center">
					${leaveword.createDate?string("MM-dd HH:mm")}
				</td>
				<td align="center">
                    ${leaveword.replyTimes}
                </td>
				<td align="center">
					<a href="?cmd=reply_leaveword&amp;leavewordId=${leaveword.id}" >回复</a>
					<a onclick="return confirm('确定删除当前留言吗?');" href="?cmd=del&leavewordId=${leaveword.id!}" >删除</a>
				</td>
			</tr>
			</#list>
	</table>

	<div class="pager">
		<#include "../inc/pager.ftl">
	</div>
	<div class='funcButton'>
	<#if leaveword_list?size != 0>
		<input class="button" id="selAll" name="sel_All" onClick="on_checkAll(list_form, 1)" type="button" value="全部选择">&nbsp;&nbsp;
		<input class="button" id="DelAll" name="Del_All" onClick="delSel(list_form)" type="button" value="删除所选">&nbsp;&nbsp;</#if>
	</div>
</form>
</body>
</html>