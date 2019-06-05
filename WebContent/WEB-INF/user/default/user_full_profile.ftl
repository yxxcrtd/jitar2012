<div class="profile">
	<div class="profilehead">基本信息：</div>
	<#if user??>
	<table border="0">
		<tr>
			<td class="info_entry">ID：</td>
			<td class="info_detail">${user.userId}</td>
		</tr>
		<#--
		<tr>
			<td class="info_entry">登录名：</td>
			<td class="info_detail">${user.loginName}</td>
		</tr>
		-->
		<tr>
			<td class="info_entry">真实姓名：</td>
			<td class="info_detail">${user.trueName!?html}</td>
		</tr>
		<#if user.qq??>
		<#if user.qq!="">
		<tr>
			<td class="info_entry">QQ联系：</td>
			<td class="info_detail"><a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${user.qq!?html}&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:${user.qq!?html}:41" alt="点击这里给我发消息" title="点击这里给我发消息"></a></td>
		</tr>
		</#if>
		</#if>
		<#if user.userType??>
		<tr style="vertical-align:top">
            <td class="info_entry">头衔：</td>
            <td class="info_detail">
            <#assign showTypeName = Util.typeIdToName(user.userType) >
            <#if showTypeName??>
                <#list showTypeName?split("/") as x>
                <#if (x?length) &gt; 0 ><div style="padding:2px 0">${x}</div></#if>
                </#list> 
            </#if>
            </td>
        </tr>
        </#if>
        <tr style="vertical-align:top">
            <td class="info_entry">身份：</td>
            <td class="info_detail">
                <#if user.positionId == 1>系统管理员
                <#elseif user.positionId == 2>机构管理员
                <#elseif user.positionId == 3>教师
                <#elseif user.positionId == 4>教育局职工
                <#elseif user.positionId == 5>学生
                <#else>未设定
                </#if>
            </td>
        </tr>
		<tr>
			<td class="info_entry">性别：</td><td class="info_detail">
				<#if user.gender??>
					<#if user.gender == 1>男
					<#else>女
					</#if>
				<#else>
				未填写
				</#if>
			</td>
		</tr>
		<tr>
			<td class="info_entry">所在机构：</td>
			<td class="info_detail">
			<#if user.unitId??>
			<#assign unit = Util.unitById(user.unitId)>
			<#if unit??>
			<a href='${SiteUrl}go.action?unitName=${unit.unitName!}'>${unit.unitTitle!?html}</a>
			</#if>
			</#if>
			</td>
		</tr>
		<tr style="vertical-align:top">
			<td class="info_entry">学段/学科：</td>
			<td class="info_detail">
			<#assign usgList = Util.getSubjectGradeListByUserId(user.userId)>
			<#if usgList?? && (usgList?size> 0) >
			<#list usgList as usg>
			<#if usg.gradeId??>${Util.gradeById(usg.gradeId).gradeName!?html}<#else>未设置</#if>/<#if usg.subjectId??>${Util.subjectById(usg.subjectId).msubjName!?html}<#else>未设置</#if>
			<#if usg_has_next><br/></#if>
			</#list>
			</#if>
			</td>
		</tr>		
		<tr style="vertical-align:top">
			<td class="info_entry">工作室名称：</td>
			<td class="info_detail">${user.blogName!?html}</td>
		</tr>
		<tr style="vertical-align:top">
			<td class="info_entry">工作室介绍：</td>
			<td class="info_detail">${user.blogIntroduce!?default('&nbsp;')}</td>
		</tr>
	</table>
<#else>
没有加载到该用户的信息。
</#if>
</div>