<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>分类维护</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
</head>
<body>
<form action='?' method='post'>
  <input type='hidden' name='cmd' value='save' />
  <input type='hidden' name='type' value='${type}' />
  <input type='hidden' name='channelId' value='${channel.channelId}' />
  <input type='hidden' name='categoryId' value='${category.categoryId}' />
<table class='listTable' cellspacing='1'>
  <tr>
    <td align="right" width="20%"><b>所属分类：</b></td>
    <td>
      <select name="parentId" style="width: 155px;">
        <option value=''>(做为一级分类)</option>
    <#list category_tree.all as c>
        <option value='${c.categoryId}' ${(c.categoryId == category.parentId!0)?string("selected=''", "")}>${c.treeFlag2} ${c.name!?html}</option>
    </#list>
      </select>
    </td>
  </tr>
  <tr>
    <td align="right"><b>分类名称：</b></td>
    <td>
      <input type="text" name="name" value='${category.name?html}' />
      <font color='red'>*</font> <span>必须填写分类名称, 名称中不要出现 &lt;&gt;'", 等特殊字符.</span>
    </td>
  </tr>
  <tr>
    <td align="right"><b>分类说明：</b></td>
    <td>
      <textarea name="description" cols="48" rows="5">${category.description!?html}</textarea>
    </td>
  </tr>
  <tr>
    <td></td>
    <td>
      <input class="button" type="submit" value="  ${(category.categoryId == 0)?string('添加分类','修改/移动分类')} " />
      <input class="button" type="button" value=" 返 回 "  onclick="window.history.back()" />
    </td>
  </tr>
</table>
</form>
</body>
</html>