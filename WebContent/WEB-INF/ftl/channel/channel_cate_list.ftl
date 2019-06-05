<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>文章分类管理</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
</head>
<body>
<#if type?index_of('channel_article_') == 0>
  <#assign typeName = '文章'>
<#elseif type?index_of('channel_resource_') == 0>
  <#assign typeName = '资源'>
<#elseif type?index_of('channel_photo_') == 0>
  <#assign typeName = '图片'>
<#elseif type?index_of('channel_video_') == 0>
  <#assign typeName = '视频'>
<#else>
  <#assign typeName = type + '(未知)' > 
</#if>
<h2>${typeName!}分类管理</h2>
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
		  <a href='?cmd=list&amp;type=${type}&amp;parentId=${category.categoryId}&channelId=${channel.channelId}'>${category.name}</a>
		  <#if (category.childNum > 0)>(${category.childNum}) </#if>
		</td>
		<td>
			<a href='?cmd=add&amp;type=${type}&amp;parentId=${category.categoryId}&channelId=${channel.channelId}'>添加子分类</a>
			<a href="?cmd=edit&amp;type=${type}&amp;categoryId=${category.categoryId}&channelId=${channel.channelId}">修改</a>
		<#if (category.childNum == 0)>
			<a href="?cmd=delete&amp;type=${type}&amp;categoryId=${category.categoryId}&channelId=${channel.channelId}"
				onclick="return confirm('确定要删除分类 [${category.name!?js_string}] 吗??\n\n警告: 分类删除后不可恢复，该分类的内容将变成无分类的.');">删除</a>
		</#if>
		</td>
	</tr>
	</#list>
	</tbody>
</table>

<div class='funcButton'>
<form action='?' method='get'>
  <input type='hidden' name='cmd' value='add' />
  <input type='hidden' name='channelId' value='${channel.channelId}' />
  <input type='hidden' name='type' value='${type}' />
  <input type='submit' class='button' value='添加一级分类' />
  <input type='hidden' name='parentId' value='${parentId!}' />
  <input type='button' class='button' value='分类排序' onclick='cateOrder();' />
</form>
</div>

<script>
function confirm_delete() {
  return confirm('您是否确定要删除该分类??\n\n警告: 删除分类之后该分类的所有文章都将被设置为无分类的.')
}
</script>
<form name='setOrderForm' action='channelcate.action' method='post' style='display:none'>
  <input type='hidden' name='cmd' value='orderlist' />
  <input type='hidden' name='channelId' value='${channel.channelId}' />
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
