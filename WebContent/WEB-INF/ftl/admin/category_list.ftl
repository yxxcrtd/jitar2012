<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<title>分类管理</title>
	<link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>

<body>
<#if type == 'default'>
  <#assign typeName = '文章'>
<#elseif type == 'blog'>
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
<h2>${typeName!}分类管理</h2>

<#if parentCategory??>
<div class='funcButton'>
${parentCategory.name!?html} 的子分类.
</div>
</#if>
			
<table class='listTable' cellspacing="1">
  <thead>
	<tr>
		<th width='64'><nobr>标识</nobr></th>
		<th width='58%'>分类名称(子分类数)</th>
		<th width='38%'>分类操作</th>
	</tr>
	</thead>
	<tbody>
	<#if category_list?size == 0>
	<tr>
	 <td colspan='3' style='padding:12px;' align='center' valign='center'>
	   <#if parentCategory??>${parentCategory.name!?html} 没有子分类<#else>尚未建立${typeName!}分类, 点击添加分类创建分类.</#if>
	 </td>
	</tr>
	</#if>
	<#list category_list as category>
	<tr>
		<td align='right' style='padding:4px;'>${category.categoryId}</td>
		<td>
		  <a href='?cmd=list&amp;type=${type}&amp;parentId=${category.categoryId}'>${category.name}</a>
		  <#if (category.childNum > 0)>(${category.childNum}) </#if>
		</td>
		<td>
		  <#if category.isSystem==false>
			<a href='?cmd=add&amp;type=${type}&amp;parentId=${category.categoryId}'>添加子分类</a>
			<a href="?cmd=edit&amp;type=${type}&amp;categoryId=${category.categoryId}">修改</a>
		<#if (category.childNum == 0)>
			<a href="?cmd=delete&amp;type=${type}&amp;categoryId=${category.categoryId}"
				onclick="return confirm('确定要删除分类 [${category.name!?js_string}] 吗??\n\n警告: 分类删除后不可恢复，该分类的所有工作室或文章等将变成无分类的.');">删除</a>
		</#if>
		</#if>
		</td>
	</tr>
	</#list>
	</tbody>
</table>

<form name='addSubmitForm' action='?' method='get' style='display:none'>
  <input type='hidden' name='cmd' value='add' />
  <input type='hidden' name='type' value='${type!}' />
  <input type='hidden' name='parentId' value='${parentId!}' />
</form>
<div class='funcButton'>
  <input type='button' class='button' value=' 添加分类 ' onclick='document.addSubmitForm.submit();' />
<#if category_list?size == 0>

<#else>  
  <input type='button' class='button' value=' 分类排序 ' onclick='cateOrder();' />
</#if>  
<#if parentCategory??>
  <input type='button' class='button' value='返回上级分类' onclick="document.backParentForm.submit();" />
  <input type='button' class='button' value='返回根分类' onclick='document.backRootForm.submit();' />

<form name='backParentForm' action='?' method='get' style='display:none'>
  <input type='hidden' name='cmd' value='list' />
  <input type='hidden' name='type' value='${type!}' />
  <input type='hidden' name='parentId' value='${parentCategory.parentId!}' />
</form>
<form name='backRootForm' action='?' method='get' style='display:none'>
  <input type='hidden' name='cmd' value='list' />
  <input type='hidden' name='type' value='${type!}' />
</form>
 
</#if>
</div>
<form name='setOrderForm' action='category_Order.py' method='post' style='display:none'>
  <input type='hidden' name='cmd' value='list' />
  <input type='hidden' name='type' value='${type}' />
  <input type='hidden' name='parentId' value='${parentId!}' />
</form>
<script language="javascript">
  function cateOrder()
  {
    document.setOrderForm.submit();
  }
</script>
</body>
</html>
