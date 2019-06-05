<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
 <title>管理</title>
 <link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
  <script type="text/javascript">
    function checkData(frm){
        if(frm.space_name.value == ""){
            alert("请输入分类名称");
            return false;
        }
        return true;
    }
 </script>
</head>
<body>
<form method='post' id='oForm' action="content_space_edit.py" onsubmit="return checkData(this);">
<input type="hidden" name="contentSpaceId" value="${contentSpace.contentSpaceId!0}"/>
<input type="hidden" name="id" value="${subject.subjectId}"/>
<#if contentSpace.contentSpaceId??>
    <#if (contentSpace.contentSpaceId!=0) >
    <h2>修改学科网站自定义内容分类</h2>
    <#else>
    <h2>增加学科网站自定义内容分类</h2>
    </#if>
<#else>
    <h2>增加学科网站自定义内容分类</h2>
</#if>
<table class="listTable" cellSpacing="1">
<tr>
    <td align="right" style="width:200px;">选择一个上级分类：</td>
    <td>
 <select name="parentId">
  <option value='0'>(做为一级分类)</option>
  <#list category_tree.all as c>
    <#if contentSpace.parentId??>
            <option value='${c.categoryId}' ${(c.categoryId == contentSpace.parentId!0)?string('selected', '')}>${c.treeFlag2} ${c.name?html}</option>
    <#else>
        <option value='${c.categoryId}'>${c.treeFlag2} ${c.name?html}</option>
    </#if>  
  </#list>
</select>   
    </td>
</tr>

<tr>
    <td align="right">分类名称：</td>
    <td>
    <input name='space_name' value='${contentSpace.spaceName!?html}' style='width:300px' />    
    </td>
</tr>
<tr>
<td colspan="2">
<div style='padding-top:6px'>
<#if contentSpace.contentSpaceId??>
    <#if (contentSpace.contentSpaceId !=0) >
    <input type='submit' value='修改分类' class='button' />
    <#else>
    <input type='submit' value='增加分类' class='button' />
    </#if>
<#else>
    <input type='submit' value='增加分类' class='button' />
</#if>
<input type='button' value=' 返  回 ' class='button' onclick='window.location.href="contentspace_list.py?id=${subject.subjectId}"' />
</div>
</td>
</tr>
</table>
</form>
</body>
</html>