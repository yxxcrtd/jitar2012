<link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" />  
<script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
<script type="text/javascript">
d = new dTree("d");
d.add(0,-1,"<b>图片分类</b>","channel.action?cmd=photolist&type=${type!}&channelId=${channel.channelId}");
<#if photo_category??>
<#list photo_category.all as c>
<#if c.parentId??>
d.add(${c.id},${c.parentId},"${c.name}","channel.action?cmd=photolist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}");      	
<#else>
d.add(${c.id},0,"${c.name}","channel.action?cmd=photolist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}");
</#if>
</#list>
</#if>
document.write(d);
d.openAll();
</script>