<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>组织机构管理</title>
  <link rel="stylesheet" href="${SiteUrl}css/manage.css" type="text/css" />
</head>
<body>
<h2>父机构名称：${parentUnit.unitTitle}</h2>
<form method='post'>
<table class='listTable' cellspacing='1'>
<tr>
	<td align="right" style='width:60px'><b>机构名称:</b></td>
	<td>
		<input type='text' name='title' value="<#if unit??>${unit.unitTitle?html}</#if>" size='80' maxLength="128" />
		<font color='red'>* 最长128个字符。</font>
	</td>
</tr>
<tr>
	<td align="right" style='width:60px'><b>英文名称:</b></td>
	<td>
		<input type='text' name='enname' value='<#if unit??>${unit.unitName}</#if>' size='80' maxLength="20" />
		<font color='red'>* 最长20个字符，必填且必须系统内唯一,只能输入数字或英文字母且以英文字母开头。</font>
	</td>
</tr>
<tr>
	<td align="right" style='width:60px'><b>网站名称:</b></td>
	<td>
        <input type='text' name='siteTitle' value="<#if unit??>${unit.siteTitle?html}</#if>" size='80' maxLength="128" />
		<font color='red'>* 最长128个字符。</font>
	</td>
</tr>
<tr>
    <td align="right" style='width:60px'><b>机构属性:</b></td>
    <td>
        <#if unitTypeList?? && unitTypeList?size &gt; 0>
        <select name="unitType" style="width:150px">
	        <#list unitTypeList as ut>
	          <option value='${ut}'<#if unit??><#if unit.unitType!?html== ut?split("_")[1] > selected</#if></#if>>${ut?split("_")[1]!}</option>
	        </#list>
        </select>  
        <#else>
        没有得到机构属性配置，请到统一用户系统里面配置机构属性。
        </#if>      
        <font color='red'>*</font>
    </td>
</tr>
<tr>
	<td></td>
	<td>
		<input class="button" type="submit" value="<#if unit??>修改机构<#else>添加机构</#if>" />
		<input class="button" type="button" value=" 返  回 " onclick="window.location='admin_unit_main.py?unitId=${parentUnit.unitId}'" />
	</td>
</tr>
</table>
</form>
</body>
</html>