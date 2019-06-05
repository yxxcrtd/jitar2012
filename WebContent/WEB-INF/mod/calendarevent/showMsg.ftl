	${year}年${month}月${day}日
	<hr style="height:1px">
	<#if calendars?? >
		<#list calendars as calendar>
			<li><a href='${calendar.url}' target="_blank">${calendar.title}</a>
		</#list>
	</#if>