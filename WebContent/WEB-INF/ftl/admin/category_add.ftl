<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<title>添加分类</title>
	<link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<#if type == 'default'>
  <#assign typeName = '工作室'>
<#elseif type == 'group'>
  <#assign typeName = '协作组'>
<#elseif type == 'resource'>
  <#assign typeName = '资源'>
<#elseif type == 'video'>
  <#assign typeName = '视频'>
<#elseif type == 'photo'>
  <#assign typeName = '图片'>
<#else>
  <#assign typeName = type + '(未知)' > 
</#if>
<h2>添加${typeName!}分类</h2>
<#assign don="0">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign don="1">
        <li>不允许在本系统分类下增加子分类
        <br/><br/>
            <input class="button" type="button" value="返回"
                onclick="window.history.back()" />        
    </#if>
</#if>
<#if don=="0">
<form action='?' method='post'>
  <input type='hidden' name='cmd' value='save' />
	<input type='hidden' name='categoryId' value='${category.categoryId}' />
	<input type='hidden' name='type' value='${category.itemType}' />
<table class='listTable' cellspacing='1'>
	<tr>
		<td align="right" width="30%">所属分类：</td>
		<td>
			<select name="parentId" >
			  <option value=''>(做为一级分类)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
		  <#list category_tree.all as c>
	      <option value='${c.categoryId}' ${(c.categoryId == (category.parentId!0))?string('selected','')}>${c.treeFlag2} ${c.name!?html}</option>
		  </#list>
			</select>
		<#if category.categoryId != 0>
		  <font color='blue'>Tip</font> <span>选择另一个父分类, 就可以移动分类.</span> 
		</#if>
		</td>
	</tr>
	<tr>
		<td align="right">分类名称：</td>
		<td>
			<input type="text" name="name" value='${category.name!?html}' size='40' />
			<font color='red'>*</font> <span>必须填写分类名称, 名称中不要出现 &lt;&gt;'", 等特殊字符.</span>
		</td>
	</tr>
	<tr>
		<td align="right">分类说明：</td>
		<td>
			<textarea name="description" cols="48" rows="5">${category.description!?html}</textarea>
			<span>此分类的详细说明</span>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<input class="button" type="submit" value="  ${(category.categoryId == 0)?string('添加分类','修改/移动分类')} " />
			<input class="button" type="button" value="取消返回"
				onclick="window.history.back()" />
		</td>
	</tr>
</table>
</form>
</#if>
</body>
</html>
