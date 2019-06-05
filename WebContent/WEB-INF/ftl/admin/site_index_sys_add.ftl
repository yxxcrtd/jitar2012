<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理</title>
 <link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<form method='post' id='oForm' action='site_index_sys_add.py'>
<h2>网站首页定制管理（系统分类模块）</h2>
<#if siteIndexPart??>
<input name='partId' type='hidden' value='${siteIndexPart.siteIndexPartId}' />
	<table class="listTable" cellSpacing="1">
	<tr>
	<td style='width:120px'>模块名称：</td><td><input name='moduleName' value='${siteIndexPart.moduleName?html}' style='width:300px' /></td>
	</tr>
	<tr>
	<td>显示位置：</td><td>
	<#include "site_index_define.ftl" />
	<select name='moduleZone'>
	<#list position as p>
	<option value='${p_index+1}'<#if siteIndexPart.moduleZone== (p_index+1)> selected='selected'</#if>>${p}</option>
	</#list>
	</select> <a href='images/indexzone.jpg' target='_blank'>位置是指标准模块下的，查看位置定义说明，自己增删模块，扔按标准位置设置。</a>
	</td>
	</tr>
	<tr>
	<td>是否显示：</td><td>
	<label>
	<input type='radio' name='moduleDisplay' <#if siteIndexPart.moduleDisplay==1>checked='checked' </#if>value='1' />显示
	</label>
	<label>
	<input type='radio' name='moduleDisplay' <#if siteIndexPart.moduleDisplay==0>checked='checked' </#if>value='0' />隐藏
	</label>
	</td>
	</tr>
  <tr>
  <td>是否显示边框：</td><td>
  <label>
  <input type='radio' name='showBorder' <#if siteIndexPart.showBorder==1>checked='checked' </#if>value='1' />显示
  </label>
  <label>
  <input type='radio' name='showBorder' <#if siteIndexPart.showBorder==0>checked='checked' </#if>value='0' />不显示
  </label>
  </td>
  </tr>
	<tr>
	<td>显示顺序：</td><td><input name='moduleOrder' value='${siteIndexPart.moduleOrder}' /></td>
	</tr>
	<tr>
	<td>模块高度：</td><td><input name='moduleHeight' value='${siteIndexPart.moduleHeight}' />（单位：像素，如果值为0则为不限制。）</td>
	</tr>
	<tr>
  <td>模块宽度：</td><td><input name='moduleWidth' value='${siteIndexPart.moduleWidth}' />（单位：像素，如果值为0则为不限制。如果独立一行显示。此属性将被忽略。）</td>
  </tr>
  <tr>
  <td>列表显示每行字数：</td><td><input name='textLength' value='${siteIndexPart.textLength}' />（仅适用于列表显示，多于此数显示省略号，0为不限制。）</td>
  </tr>
	<tr>
	<td>文章分类：</td><td>
	<select name='sysCateId'>
    <#if article_cates?? >
      <#list article_cates.all as c >
        <#if siteIndexPart.sysCateId??>
        <#if c.categoryId==siteIndexPart.sysCateId>
        <option selected='selected' value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
        <#else>
        <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
        </#if>
        <#else>
        <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
        </#if>
      </#list>
    </#if>
   </select>
	</td>
	</tr>
	<tr>
	<td>显示条数：</td><td>
	<select name='showCount'>
	<#list 1..20 as i>
	<option value='${i}'<#if siteIndexPart.showCount == i> selected='selected'</#if>>${i}</option>
	</#list>
	</select>
	</td>
	</tr>
	</table>
<#else>
	对象生成错误。
</#if>
<div style='padding:4px 0'>
<input type='submit' value=' 保  存 ' class='button' />
<input type='button' value=' 返  回 ' class='button' onclick='window.location.href="site_index.py"' />
</div>
<div>注意：可根据布局需要调整内容的宽度和高度，默认情况下，无需设置宽度。显示效果需要根据设置微调。</div>
</form>
</body>
</html>