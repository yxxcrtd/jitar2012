<#if userList??>
	<#list userList as u>
		<a href="#" onclick="selectUser('${u.userId!}','${u.userName!}');return false;" title="${u.userName!}">${u.userName!}</a>
		<br/>
	</#list> 
</#if>
	
