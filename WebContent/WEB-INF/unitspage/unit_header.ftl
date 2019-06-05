<script type='text/javascript'>
var JITAR_ROOT = '${SiteUrl}';
</script>
<#if unit.headerContent??>
${unit.headerContent}
<#else>
	<#if unit.unitLogo??>
	<#if unit.unitLogo?substring(0,6)=='/user/'>
	<img src='${SiteUrl}${unit.unitLogo?substring(1)}' style='margin:3px 0;' />
	<#else>
	<img src='${unit.unitLogo}' style='margin:3px 0;' />
	</#if>
	<#else>
	<div class='logo'>
		<div>${unit.siteTitle}</div>
	</div>
	</#if>
</#if>