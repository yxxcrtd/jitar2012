<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>添加个人资源分类</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
  <h2>添加/修改个人资源分类</h2>
<div class='funcButton'>
  您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
  &gt;&gt; <a href='resource.action?cmd=list'>资源管理</a>
  &gt;&gt; <a href='rescate.action?cmd=list'>个人资源分类管理</a>
  &gt;&gt; 添加/修改个人资源分类
</div>

<form action='rescate.action' method='post'>
  <input type='hidden' name='cmd' value='save' />
  <input type='hidden' name='categoryId' value='${category.categoryId}' />
  <input type="hidden" name="old_parentId" value="${category.parentId!}" />
<#if __referer??>
  <input type='hidden' name='__referer' value='${__referer}' />
</#if>
  <table class='listTable' cellspacing='1'>
    <tr>
      <td align="right" width="20%"><b>所属分类：</b></td>
      <td>
        <select name="parentId">
          <option value=''>(做为一级分类)</option>
      <#list category_tree.all as c>
          <option value='${c.categoryId}' ${(c.categoryId == category.parentId!0)?string('selected', '')}>${c.treeFlag2} ${c.name?html}</option>
      </#list>
        </select>
        <#if category.categoryId != 0>(选择新的父分类就能够移动分类)</#if>
      </td>
    </tr>
    <tr>
      <td align="right"><b>分类名称：</b></td>
      <td>
        <input type="text" name="name" value="${category.name?html}" onMouseOver="this.select();" />
        <font color='red'>*</font> 必须填写分类名称.
      </td>
    </tr>
    <tr>
      <td align="right"><b>分类说明：</b></td>
      <td>
        <textarea name="description" cols="48" rows="5">${category.description!?html}</textarea>
        * 此分类的详细说明
      </td>
    </tr>
    <tr>
      <td></td>
      <td>
        <input class="button" type="submit" value="  ${(category.categoryId == 0)?string('添加分类', '修改/移动分类')}  " />
        <input class="button" type="button" value="取消返回"
          onclick="window.history.back()" />
      </td>
    </tr>
  </table>
</form>
    
</body>
</html>
