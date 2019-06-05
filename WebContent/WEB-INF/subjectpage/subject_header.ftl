<#if subject.headerContent??>
${subject.headerContent}
<#else>
	<#if subject.logo??>	
	<#if subject.logo?substring(0,6)=='/user/'>
	<img src='${SiteUrl}${subject.logo?substring(1)}' style='margin:3px 0;' />
	<#else>
	<img src='${subject.logo}' style='margin:3px 0;' />
	</#if>
	<#else>
	<div class='logo' style='margin:3px 0;'>
		<div>${subject.subjectName}</div>
	</div>
	</#if>
</#if>