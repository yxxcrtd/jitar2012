<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>机构管理</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
		<script type="text/javascript">
		<!--
		var isChecked = true;
		function selectAll(oForm) {
			for (var i = 0; i < oForm.elements.length; i++) {
				if (oForm.elements[i].type == "checkbox" && !oForm.elements[i].disabled) {
				}
				oForm.elements[i].checked = isChecked;
			}
			if (oForm.elements["selAll"]) {
				if (isChecked) {
					oForm.elements["selAll"].value = "全部不选";
				} else {
					oForm.elements["selAll"].value = "全部选择";
				}
			}
			isChecked = !isChecked;
		}
    	function delSel(unit_form) {
    		if (hasChecked(unit_form) == false) {
    			alert("您还没有选择任何信息！");
    			return false;
    		} else {
	  			if (confirm("确定要删除选择的信息吗？") == false) {
	  				return false;
	  			}
  			}
  			unit_form.cmd.value = "del";
  			unit_form.submit();
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
		//-->
		</script>
	</head>
	
	<body style="margin-top: 20px;">
	
		<#include "admin_header.ftl" >
	
		<h2>机构管理</h2>
				
		<div class="pager">
			<@s.form name="searchForm" action="admin_unit" method="get">
				<input type="hidden" name="cmd" value="list" />
				关键字：
				<input type="text" size="16" name="k" value="${k!?html}" onMouseOver="this.select();" />                
                     
		  		<input type="submit" class="button" value="检  索" />
			</@s.form>
		</div>
		
		<table align="center">
			<tr>
				<td colspan="2">
					<@s.actionerror cssStyle="color:#FF0000; font-weight:bold;" />
				</td>
			</tr>
		</table>
		
		<@s.form name="unit_form">
			<@s.hidden name="cmd" value="list" />
			<table class="listTable" cellSpacing="1">
				<thead>
					<tr>
						<th width="10%">选&nbsp;&nbsp;择</th>

						<th width="25%" style="text-align: left; padding-left: 50px;">机构名称</th>
						<th width="25%" style="text-align: left; padding-left: 50px;">机构类型</th>
						<th width="15%">操&nbsp;&nbsp;作</th>
					</tr>
				</thead>
				
				<tbody>
					<#if unitList?size == 0>
						<tr>
							<td colSpan="5" style="color: #FF0000; font-weight: bold; text-align: center; padding: 10px;">
								没有符合条件的机构信息！
							</td>
						</tr>
					</#if>
					
					<#list unitList as unit>
						<tr>
							<td style="text-align: center;">
								<input type="checkbox" name="unitId" value="${unit.unitId}" />
								${unit.unitId}
							</td>

							<td style="padding-left: 52px;">
								<strong>${unit.unitName!}</strong>
							</td>
							<td style="padding-left: 52px;">
								${unit.unitType!}
							</td>
							<td style="text-align: center;">
								<a href="?cmd=upd&unitId=${unit.unitId}">编辑</a>&nbsp;&nbsp;
								<a href="?cmd=del&unitId=${unit.unitId}" onClick="return confirm('确定要删除当前信息吗？');">删除</a>
							</td>
						</tr>
					</#list>
				</tbody>
			</table>

			<div class="pager">
				<#if pager??>
					<#include "../inc/pager.ftl">
				</#if>
			</div>
	          
	        <div class="funcButton">
	            <#if unitList?size != 0>
	                <input class="button" type="button" value="全部选择" onClick="selectAll(unit_form)" id="selAll" />
	                <input class="button" type="button" value="删除选择" onClick="delSel(unit_form)" />
	            </#if>
	            <input class="button" type="button" value="添加机构" onClick="javascript:window.location='?cmd=add'" />
	        </div>
        </@s.form>
		
		<#include "admin_footer.ftl">
		
	</body>
</html>
