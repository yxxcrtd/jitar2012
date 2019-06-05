<link rel="stylesheet" type="text/css" href="${SiteUrl}css/index/dtree.css" />
<script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
<script type="text/javascript">
dRes = new dTree("dRes");
dRes.add(0,-1,"<b>资源分类</b>","channel.action?cmd=resourcelist&type=${type!}&channelId=${channel.channelId}");
<#if resource_category??>
<#list resource_category.all as c>
<#if c.parentId??>
dRes.add(${c.id},${c.parentId},"${c.name}","channel.action?cmd=resourcelist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}");      	
<#else>
dRes.add(${c.id},0,"${c.name}","channel.action?cmd=resourcelist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}");
</#if>
</#list>
</#if>
document.write(dRes);
dRes.openAll();
</script>