<link rel="stylesheet" type="text/css" href="${SiteUrl}css/index/dtree.css" />
<script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
<script type="text/javascript">
dArticle = new dTree("dArticle");
dArticle.add(0,-1,"<b>文章分类</b>","channel.action?cmd=articlelist&type=${type!}&channelId=${channel.channelId}");
<#if ArticleCategory??>
<#list ArticleCategory.all as c>
<#if c.parentId??>
dArticle.add(${c.id},${c.parentId},"${c.name}","channel.action?cmd=articlelist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}");      	
<#else>
dArticle.add(${c.id},0,"${c.name}","channel.action?cmd=articlelist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}");
</#if>
</#list>
</#if>
document.write(dArticle);
dArticle.openAll();
</script>