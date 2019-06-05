<#if group_list??>
	<table border="1">
		<tr>
			<th>协作组ID</th>
			<th>协作组名称</th>
			<th>创建者</th>
			<th>创建日期</th>
			<th>协作组分类</th>
			<th>学科</th>
			<th>学段</th>
			<th>访问量</th>
            <th>成员数</th>
            <th>文章数</th>
            <th>资源数</th>
            <th>主题数</th>
            <th>讨论数</th>
            <th>活动数</th>
            <th>协作组状态</th>
		</tr>
		
		<#list group_list as group>
	 	<tr>
	 		<td>
	 		    ${group.groupId}
            </td>
            <td>
                ${group.groupTitle!}
            </td>
            <td>
                ${Util.userById(group.createUserId).trueName!?html}
            </td>
            <td>
                ${group.createDate?string('yyyy-MM-dd HH:mm:ss')}
            </td>
            <td>
                <#if group_catetory_list??>
                    <#list group_catetory_list.all as c>
                        <#if group.categoryId??>
                            <#if c.categoryId == group.categoryId>
                                ${c.name}
                            </#if>
                        </#if>
                    </#list>
                </#if>
            </td>
            <td>
                <#if group.subjectId??>
                    ${Util.subjectById(group.subjectId).msubjName!?html}
                </#if>
            </td>
            <td>
                <#if group.gradeId??>
                    ${Util.gradeById(group.gradeId).gradeName!?html}
                </#if>
            </td>
            <td>
                ${group.visitCount}
            </td>
            <td>
                ${group.userCount}
            </td>
            <td>
                ${group.articleCount}
            </td>
            <td>
                ${group.resourceCount}
            </td>
            <td>
                ${group.topicCount}
            </td>
            <td>
                ${group.discussCount}
            </td>
            <td>
                ${group.actionCount}
            </td>
            <td>
                <#if group.groupState == 0>正常
                    <#elseif group.groupState == 1><font style="color: #FF0000;">待审核</font>
                    <#elseif group.groupState == 2><font style="color: #FF0000;">已锁定</font>
                    <#elseif group.groupState == 3><font style="color: #FF0000;">已删除</font>
                    <#elseif group.groupState == 4><font style="color: #FF0000;">已隐藏</font>
                    <#else><font style="color: #FF0000;">未知状态</font>
                </#if>
            </td>
		</tr>
		</#list>
	</table>
</#if>
