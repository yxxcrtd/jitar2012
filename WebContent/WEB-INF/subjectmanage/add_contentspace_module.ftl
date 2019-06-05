<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />  
</head>
<body>
<h2>
添加自定义分类内容模块
</h2>
<form method='post' style='padding-left:20px' action='add_contentspace_module.py'>
<input type='hidden' name='webpartId' value='${subjectWebpart.subjectWebpartId!0}' />
<input type='hidden' name='id' value='${subject.subjectId}' />
<table class='listTable' cellspacing='1'>
<tr>
<td style='width:100px'><strong>模块名称：</strong></td><td>
<input name='moduleName' style='width:50%' value='${subjectWebpart.moduleName!}' />
</td>
</tr>
<tr>
<td><strong>模块区域：</strong></td><td>
<select name='webpartZone'>
<option value='1'<#if subjectWebpart.webpartZone?? && subjectWebpart.webpartZone == 1> selected='selected'</#if>>顶部</option>
<option value='2'<#if subjectWebpart.webpartZone?? && subjectWebpart.webpartZone == 2> selected='selected'</#if>>下部</option>
<option value='3'<#if subjectWebpart.webpartZone?? && subjectWebpart.webpartZone == 3> selected='selected'</#if>>左</option>
<option value='4'<#if subjectWebpart.webpartZone?? && subjectWebpart.webpartZone == 4> selected='selected'</#if>>中</option>
<option value='5'<#if subjectWebpart.webpartZone?? && subjectWebpart.webpartZone == 5> selected='selected'</#if>>右</option>
</select>
</td>
</tr>
<tr>
<td><strong>文章分类：</strong></td><td>
<select name='sysCateId'>
    <#if catelist?? >
      <#list catelist as c >
        <#if subjectWebpart.sysCateId??>
        <#if c.contentSpaceId==subjectWebpart.sysCateId>
        <option selected='selected' value='${c.contentSpaceId}'>${c.spaceName}</option>
        <#else>
        <option value='${c.contentSpaceId}'>${c.spaceName}</option>
        </#if>
        <#else>
        <option value='${c.contentSpaceId}'>${c.spaceName}</option>
        </#if>
      </#list>
    </#if>
   </select>
</td>
</tr>
<tr>
<td><strong>显示条数：</strong></td><td>
<select name='showCount'>
	<#list 1..20 as i>
	<option value='${i}'<#if subjectWebpart.showCount == i> selected='selected'</#if>>${i}</option>
	</#list>
</select>
(注意：Flash 图片显示方式只能显示6个图片)
</td>
</tr>
<tr>
<td><strong>显示方式：</strong></td><td>
<select name='showType'>
 <option value='1'<#if subjectWebpart.showType == 1> selected='selected'</#if>>列表显示</option>
 <option value='2'<#if subjectWebpart.showType == 2> selected='selected'</#if>>Flash 图片显示</option>
 <option value='3'<#if subjectWebpart.showType == 3> selected='selected'</#if>>列表+Flash 图片混合显示</option>
</select>
(注意：Flash 图片显示方式只能显示6个图片)
</td>
</tr>
</table>
<div style='padding:6px'>
<input class='button' type='submit' value=' 保  存 ' />
</div>
</form>
</body>
</html>
