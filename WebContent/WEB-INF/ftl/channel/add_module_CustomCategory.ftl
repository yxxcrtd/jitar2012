<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script src="${SiteUrl}js/jitar/gettemplate.js?20110222"></script>
  <script type="text/javascript">
    function  enableSelect(selname){
        document.getElementsByName('articleCategoryId')[0].disabled='disabled';
        document.getElementsByName('resourceCategoryId')[0].disabled='disabled';
        document.getElementsByName('photoCategoryId')[0].disabled='disabled';
        document.getElementsByName('videoCategoryId')[0].disabled='disabled';
        document.getElementsByName(selname)[0].disabled='';
    }
  </script>
</head>
<body>
<form method="POST">
<#if module??>
<table class='listTable' cellspacing='1' id='listTable'>
<tr>
<th style="width:80px">模块名称：</th><td><input name="moduleDisplayName" value="${module.displayName!?html}" />（模块名称是唯一标识，不能与其它模块名称重复。若修改模块名称，请同时修改频道模板中使用的相应名称。）</td>
</tr>
<tr>
<td>选择分类：</td>
<td>
   <input type="radio" name="typeName" value="articleCategory" <#if module.cateItemType!?string=='channel_article_'+channel.channelId>checked</#if>  onClick="enableSelect('articleCategoryId')">文章分类:
          <select name='articleCategoryId' <#if module.cateItemType!?string!='channel_article_'+channel.channelId>disabled="disabled"</#if>>
                <option value='0'>请选择文章分类</option>
                <#if article_category_list?? >
                  <#list article_category_list.all as c >
                    <#if module.cateId==c.categoryId>
                        <option value='${c.categoryId}' selected>${c.treeFlag2 + c.name}</option>
                    <#else>
                        <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
                    </#if> 
                  </#list>
                </#if>
        </select>    
   <br/>
   <input type="radio" name="typeName" value="resourceCategory" <#if module.cateItemType!?string=='channel_resource_'+channel.channelId>checked</#if> onClick="enableSelect('resourceCategoryId')">资源分类:
          <select name='resourceCategoryId' <#if module.cateItemType!?string!='channel_resource_'+channel.channelId>disabled="disabled"</#if>>
                <option value='0'>请选择资源分类</option>
                <#if resource_category_list?? >
                  <#list resource_category_list.all as c >
                    <#if module.cateId==c.categoryId>
                        <option value='${c.categoryId}' selected>${c.treeFlag2 + c.name}</option>
                    <#else>
                        <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
                    </#if> 
                  </#list>
                </#if>
        </select>       
    <br/>
   <input type="radio" name="typeName" value="photoCategory" <#if module.cateItemType!?string=='channel_photo_'+channel.channelId>checked</#if> onClick="enableSelect('photoCategoryId')">图片分类:
          <select name='photoCategoryId' <#if module.cateItemType!?string!='channel_photo_'+channel.channelId>disabled="disabled"</#if>>
                <option value='0'>请选择图片分类</option>
                <#if photo_category_list?? >
                  <#list photo_category_list.all as c >
                    <#if module.cateId==c.categoryId>
                        <option value='${c.categoryId}' selected>${c.treeFlag2 + c.name}</option>    
                    <#else>
                        <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
                    </#if> 
                  </#list>
                </#if>
        </select>    
   <br/>
   <input type="radio" name="typeName" value="videoCategory" <#if module.cateItemType!?string=='channel_video_'+channel.channelId>checked</#if> onClick="enableSelect('videoCategoryId')">视频分类:
          <select name='videoCategoryId' <#if module.cateItemType!?string!='channel_video_'+channel.channelId>disabled="disabled"</#if>>
                <option value='0'>请选择视频分类</option>
                <#if video_category_list?? >
                  <#list video_category_list.all as c >
                     <#if module.cateId==c.categoryId>
                        <option value='${c.categoryId}' selected>${c.treeFlag2 + c.name}</option>
                     <#else>   
                        <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
                     </#if>
                  </#list>
                </#if>
        </select>     
   <br/>
   
</td>
</tr>
<tr>
<td>显示条数：</td><td><input name="showCount" value="${module.showCount!?html}" /></td>
</tr>
<tr style="vertical-align:top">
<td>显示模板：</td><td><textarea style="width:100%;height:400px;" name="template">${module.template!?html}</textarea></td>
</tr>
<tr>
<td>&nbsp;</td><td>
<input type="submit" value="保存模块" />
<input type="button" value="加载默认模板" onclick="GetTemplate('${moduleType!?js_string}',${channel.channelId})" />
</td>
</tr>
</table>
<#else>
<table>
<tr>
<td style="width:60px;">模块名称：</td><td><input name="moduleDisplayName" value="自定义分类模块" />（模块名称是唯一标识，不能与其它模块名称重复。若修改模块名称，请同时修改频道模板中使用的相应名称。）</td>
</tr>
<tr>
<td>选择分类：</td>
<td>
   <input type="radio" name="typeName" value="articleCategory" onClick="enableSelect('articleCategoryId')">文章分类:
          <select name='articleCategoryId' disabled="disabled">
                <option value='0'>请选择文章分类</option>
                <#if article_category_list?? >
                  <#list article_category_list.all as c >
                     <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
                  </#list>
                </#if>
        </select>    
   <br/>
   <input type="radio" name="typeName" value="resourceCategory" onClick="enableSelect('resourceCategoryId')">资源分类:
          <select name='resourceCategoryId' disabled="disabled">
                <option value='0'>请选择资源分类</option>
                <#if resource_category_list?? >
                  <#list resource_category_list.all as c >
                     <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
                  </#list>
                </#if>
        </select>       
    <br/>
   <input type="radio" name="typeName" value="photoCategory" onClick="enableSelect('photoCategoryId')">图片分类:
          <select name='photoCategoryId' disabled="disabled">
                <option value='0'>请选择图片分类</option>
                <#if photo_category_list?? >
                  <#list photo_category_list.all as c >
                     <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
                  </#list>
                </#if>
        </select>    
   <br/>
   <input type="radio" name="typeName" value="videoCategory" onClick="enableSelect('videoCategoryId')">视频分类:
          <select name='videoCategoryId' disabled="disabled">
                <option value='0'>请选择视频分类</option>
                <#if video_category_list?? >
                  <#list video_category_list.all as c >
                     <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
                  </#list>
                </#if>
        </select>     
   <br/>
   
</td>
</tr>
<tr>
<td>显示条数：</td><td><input name="showCount" value="20" /></td>
</tr>
<tr style="vertical-align:top">
<td>显示模板：</td><td><textarea style="width:100%;height:400px;" name="template"></textarea></td>
</tr>
<tr>
<td>&nbsp;</td><td>
<input type="submit" value="添加模块" />
<input type="button" value="加载默认模板" onclick="GetTemplate('${moduleType!?js_string}',${channel.channelId})" />
</td>
</tr>
</table>
</#if>
</form>
</body>
</html>