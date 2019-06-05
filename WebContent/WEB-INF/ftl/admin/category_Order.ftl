<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>分类排序管理</title>
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
<#elseif type == 'photo'>
  <#assign typeName = '图片'>
<#elseif type == 'video'>
  <#assign typeName = '视频'>
<#elseif type == 'user'>
  <#assign typeName = '用户文章'>
<#elseif type == 'user_res'>
  <#assign typeName = '用户资源'>
<#elseif type == 'user_video'>
  <#assign typeName = '用户视频'>  
<#elseif type == 'gres'>
  <#assign typeName = '协助组资源'>
<#elseif type == 'gart'>
  <#assign typeName = '协助组文章'>
<#elseif type == 'gpho'>
  <#assign typeName = '协助组图片'>
<#elseif type == 'gvid'>
  <#assign typeName = '协助组视频'>
<#else>
  <#assign typeName = type + '(未知)' > 
</#if>
<h2>${typeName!}分类管理</h2>

<#if parentCategory??>
<div class='funcButton'>
${parentCategory.name!?html} 的子分类.
</div>
</#if>
 <form name='listForm'>     
<table class='listTable' cellspacing="1">
  <thead>
  <tr>
    <th width='64'><nobr>标识</nobr></th>
    <th width='60%'>分类名称</th>
    <th width='30%'>分类序号</th>
  </tr>
  </thead>
  <tbody>
  <#if category_list?size == 0>
  <tr>
   <td colspan='3' style='padding:12px;' align='center' valign='center'>
     <#if parentCategory??>${parentCategory.name!?html} 没有子分类<#else>尚未建立${typeName!}分类.</#if>
   </td>
  </tr>
  </#if>
  <#list category_list as category>
  <tr>
    <td align='right' style='padding:4px;'>${category.categoryId}</td>
    <td>
      <a href='?cmd=list&amp;type=${itemType}&amp;parentId=${category.categoryId}'>${category.name}</a>
    </td>
    <td>
    　　<input type="text" name="ordernum" catid="${category.categoryId}" size=5 value="${category.orderNum}"/>
    </td>
  </tr>
  </#list>
  </tbody>
</table>
</form>

<form name='addSubmitForm' action='?' method='get' style='display:none'>
  <input type='hidden' name='cmd' value='save' />
  <input type='hidden' name='parentId' value='${parentId!}' />
  <input type='hidden' name='itemType' value='${itemType!}' />
  <input type='hidden' name='type' value='${itemType!}' />
  <input type='hidden' name='cateid' value='' />
  <input type='hidden' name='orderNo' value='' />
</form>
<div class='funcButton'>
  <input type='button' class='button' value=' 保存排序结果 ' onclick='saveOrderData();' />
  <input type='button' class='button' value=' 返回 ' onclick='hback();' />
</div>
<script language="javascript">
  function hback()
  {
  	//以下判断要注意顺序才能保证返回正确!
    var itemType = "${itemType!}",returnUrl="";
    if(itemType.indexOf("user_") > -1) 
    {
    	returnUrl = "usercate.action?cmd=list";
    }
    if(itemType.indexOf("blog") > -1) 
    {
    	returnUrl = "admin_category.py?cmd=list&type=blog";
    }
    if(itemType.indexOf("video") > -1) 
    {
    	returnUrl = "admin_category.py?cmd=list&type=video";
    }
    if(itemType.indexOf("user_res_") > -1) 
    {
    	returnUrl = "rescate.action?cmd=list";
    }
    if(itemType.indexOf("user_video_") > -1) 
    {
    	returnUrl = "videocate.action?cmd=list";
    }
    if(itemType.indexOf("default") > -1) 
    {
    	returnUrl = "admin_category.py?cmd=list&type=default";
    }
    if(itemType.indexOf("resource") > -1) 
    {
    	returnUrl = "admin_category.py?cmd=list&type=resource";
    }    
    if(itemType.indexOf("photo") > -1) 
    {
    	returnUrl = "admin_category.py?cmd=list&type=photo";
    }
    if(itemType.indexOf("gres_") > -1) 
    {
    	returnUrl = "group_resource_category.py?cmd=list&groupId=" + itemType.substr("gres_".length);
    }
    if(itemType.indexOf("gart_") > -1) 
    {
    	returnUrl = "group_article_category.py?cmd=list&groupId=" + itemType.substr("gart_".length);
    }
    if(itemType.indexOf("gpho_") > -1) 
    {
    	returnUrl = "group_photo_category.py?cmd=list&groupId=" + itemType.substr("gpho_".length);
    }
    
    if(itemType.indexOf("gvid_") > -1) 
    {
    	returnUrl = "group_video_category.py?cmd=list&groupId=" + itemType.substr("gvid_".length);
    }
    
    
    document.location.href=returnUrl;
  }
  function saveOrderData()
  {
    var s1;
    var s2;
    var i;
    s1="";
    s2="";
    for(i=0;i<document.listForm.elements.length;i++)
    {
      if (document.listForm.elements[i].name=="ordernum")
      {
        if(s1=="")
        {
          s1=document.listForm.elements[i].getAttribute("catid");
        }
        else
        {
          s1=s1+","+document.listForm.elements[i].getAttribute("catid");
        }
        
        if(s2=="")
        {
          s2=document.listForm.elements[i].value;
        }
        else
        {
          s2=s2+","+document.listForm.elements[i].value;
        }
      }
    }
    document.addSubmitForm.cateid.value=s1;
    document.addSubmitForm.orderNo.value=s2;
    //alert(document.addSubmitForm.cateid.value);
    //alert(document.addSubmitForm.orderNo.value);
    document.addSubmitForm.submit();
  }
</script>
</body>
</html>
