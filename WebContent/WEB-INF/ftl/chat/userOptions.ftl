	<#if userList??>
		<#list userList as u>
				<option value=${u.userId!}>${u.userName!}</option>
		</#list> 
	</#if>
	
