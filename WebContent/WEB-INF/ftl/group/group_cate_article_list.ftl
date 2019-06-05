<#assign grpName="协作组">
<#assign grpShowName="小组">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign grpName="课题组"> 
        <#assign grpShowName="课题">
    <#else>
        <#assign grpName="协作组">
        <#assign grpShowName="小组">
    </#if>
</#if>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>${grpName}文章分类管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
  &gt;&gt; <span>${grpName}文章分类管理</span> 
</div>
<br/>


<table class='listTable' cellspacing="1">
  <thead>
  <tr>
    <th width='5%'>标识</th>
    <th width='55%'>分类名称(子分类数)</th>
    <th width='35%'>分类操作</th>
  </tr>
  </thead>
  <tbody>
<#list category_tree.all as category>
  <tr>
    <td>${category.categoryId}</td>
    <td>
      ${category.treeFlag2!} ${category.name!?html}
      <#if (category.childNum > 0)>(${category.childNum}) </#if>
    </td>
    <td>
      <#if category.isSystem!=true>  
      <a href='?cmd=add&amp;groupId=${group.groupId}&amp;parentId=${category.categoryId}'>添加子分类</a>
      <a href='?cmd=edit&amp;groupId=${group.groupId}&amp;categoryId=${category.categoryId}'>修改</a>
    <#if (category.childNum == 0)>
      <a href='?cmd=delete&amp;groupId=${group.groupId}&amp;categoryId=${category.categoryId}' onclick='return confirm_delete();'>删除</a>
    </#if>
    </#if>
    </td>
  </tr>
</#list>
  </tbody>
</table>

<div class='funcButton'>
<form action='?' method='get'>
  <input type='hidden' name='cmd' value='add' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
  <input type='submit' class='button' value='添加一级分类' />
  <input type='button' class='button' value='分类排序' onclick='cateOrder();' />
</form>
</div>

<script>
function confirm_delete() {
  return confirm('您是否确定要删除该分类??\n\n警告: 删除分类之后该分类的所有文章都将被设置为无分类的.')
}
</script>
<form name='setOrderForm' action='category_Order.py' method='post' style='display:none'>
  <input type='hidden' name='cmd' value='list' />
  <input type='hidden' name='type' value='gart_${group.groupId}' />
  <input type='hidden' name='parentId' value='' />
</form>
<script language="javascript">
  function cateOrder()
  {
    document.setOrderForm.submit();
  }
</script>
</body>
</html>
