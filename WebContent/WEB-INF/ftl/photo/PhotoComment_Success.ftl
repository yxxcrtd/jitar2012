<#if redUrl??>
	<div>提交成功。<a href='${SiteUrl}${redUrl}'>返回照片页面</a></div>
	<script>
		window.location.href='${SiteUrl}${redUrl}'
	</script>
<#else>
	<div>无返回页面。</div>
</#if>