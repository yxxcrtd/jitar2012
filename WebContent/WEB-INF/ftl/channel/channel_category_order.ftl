<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>分类排序管理</title>
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
      <a href='?cmd=list&amp;type=${itemType}&amp;parentId=${category.categoryId}&channelId=${channel.channelId}'>${category.name}</a>
    </td>
    <td>
    　　<input type="text" name="ordernum" catid="${category.categoryId}" size=5 value="${category.orderNum}"/>
    </td>
  </tr>
  </#list>
  </tbody>
</table>
</form>

<form name='addSubmitForm' action='channelcate.action' method='post' style='display:none'>
  <input type='hidden' name='cmd' value='ordersave' />
  <input type='hidden' name='channelId' value='${channel.channelId!}' />
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
    document.location.href="channelcate.action?cmd=list&parentId=${parentparentId!}&type=${itemType!}&channelId=${channel.channelId!}";
  }
  function saveOrderData()
  {
    var s1;
    var s2;
    var i;
    s1="";
    s2="";
    for(i=0;i<document.getElementsByName("ordernum").length;i++)
    {
        if(s1=="")
        {
          s1=document.getElementsByName("ordernum")[i].getAttribute("catid");
        }
        else
        {
          s1=s1+","+document.getElementsByName("ordernum")[i].getAttribute("catid");
        }
        
        if(s2=="")
        {
          s2=document.getElementsByName("ordernum")[i].value;
        }
        else
        {
          s2=s2+","+document.getElementsByName("ordernum")[i].value;
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
