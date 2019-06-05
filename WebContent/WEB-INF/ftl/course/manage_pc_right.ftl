<html>
   <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>后台管理</title>
        <link rel="stylesheet" type="text/css" href="../../css/manage.css" />
    </head>
    <body>
        <h2><a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/0/' target='_blank'>${prepareCourse.title} 首页</a></h2>        
        <table class="listTable" cellSpacing="1">
            <thead>
                <tr>
                    <td style="height: 30px; font-weight: bold;" colSpan="2">
                        <b>&nbsp;&nbsp;&nbsp;&nbsp;当前备课信息：</b>
                    </td>
                </tr>
            </thead>
            <tbody>
            <tr>
                <td style="width: 20%; height: 30px; text-align: right; padding-right: 10px;">发起人：</td>
                <td style="padding-left: 10px; color: #FF0000; font-weight: bold;">
                <#assign c = Util.userById(prepareCourse.createUserId)>
                <a href='${SiteUrl}go.action?loginName=${c.loginName}' target='_blank'>${c.trueName?html}</a> 
                </td>                    
            </tr>
            <tr>
                <td style="height: 30px; text-align: right; padding-right: 10px;">主备人：</td>
                <td style="padding-left: 10px;">
                    <#assign leader = Util.userById(prepareCourse.leaderId)>
                    <a href='${SiteUrl}go.action?loginName=${leader.loginName}' target='_blank'>${leader.trueName}</a>
                </td>                    
            </tr>
            <tr>
                <td style="height: 30px; text-align: right; padding-right: 10px;">状态：</td>
                <td style="padding-left: 10px;">
                <#if prepareCourse.status == 0 >
					正常
					<#elseif prepareCourse.status == 1>
					<font color='red'>待审核</font>
					<#elseif prepareCourse.status == 2>
					<font color='red'>锁定</font>
					<#else>
					未知
					</#if>
				  </td>
                </tr>
                <#if prepareCourse.lockedUserId != 0 >
                <#assign u = Util.userById(prepareCourse.lockedUserId)>
                <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">锁定人：</td>
                    <td style="padding-left: 10px;">
                    <#if u.trueName??>
                    	<a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.trueName?html}</a>
                    <#else>
                    	<a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.loginName?html}</a>
                    </#if> 
                    </td>                    
                </tr>
                </#if>                
                <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">浏览数：</td>
                    <td style="padding-left: 10px;">${prepareCourse.viewCount}</td>                    
                </tr>
                <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">成员数：</td>
                    <td style="padding-left: 10px;">${prepareCourse.memberCount}</td>                    
                </tr>
                <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">文章数：</td>
                    <td style="padding-left: 10px;">${prepareCourse.articleCount}</td>                    
                </tr>
                <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">资源数：</td>
                    <td style="padding-left: 10px;">${prepareCourse.resourceCount}</td>                    
                </tr>
                <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">活动数：</td>
                    <td style="padding-left: 10px;">${prepareCourse.actionCount}</td>                    
                </tr>
                <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">讨论数：</td>
                    <td style="padding-left: 10px;">${prepareCourse.topicCount}</td>                    
                </tr>
                <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">讨论回复数：</td>
                    <td style="padding-left: 10px;">${prepareCourse.topicReplyCount}</td>                    
                </tr>
                <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">创建日期：</td>
                    <td style="padding-left: 10px;">${prepareCourse.createDate?string("yyyy-MM-dd HH:mm")}</td>                    
                </tr>
                 <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">开始日期：</td>
                    <td style="padding-left: 10px;">${prepareCourse.startDate?string("yyyy-MM-dd")}</td>                    
                </tr>
                 <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">结束日期：</td>
                    <td style="padding-left: 10px;">${prepareCourse.endDate?string("yyyy-MM-dd")}</td>                    
                </tr>
                 <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">学段：</td>
                    <td style="padding-left: 10px;">
                    ${Util.gradeById(prepareCourse.gradeId!).gradeName!?html}
                   </td>                    
                </tr>
                 <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">学科：</td>
                    <td style="padding-left: 10px;">${Util.subjectById(prepareCourse.metaSubjectId!).msubjName!?html}</td>                    
                </tr>
                <tr>
                    <td style="height: 30px; text-align: right; padding-right: 10px;">备课任务：</td>
                    <td style="padding-left: 10px;">${prepareCourse.description!}</td>                    
                </tr>
            </tbody>
        </table>
    </body>
</html>