<#if preparecourse_list?? >
<table border='1'>
<tr>
<th>备课名称</th>
<th>学段</th>
<th>学科</th>
<th>创建人</th>
<th>主备人</th>
<th>成员数</th>
<th>个案数</th>
<th>共案编辑次数</th>
<th>文章数</th>
<th>资源数</th>
<th>活动数</th>
<th>讨论数</th>
<th>讨论回复数</th>
<th>是否推荐</th>
<th>状态</th>
<th>创建人机构</th>
<th>主备人机构</th>
<th>集备开始时间</th>
<th>集备结束时间</th>
</tr>
<#list preparecourse_list as pc>
<#assign grad = Util.gradeById(pc.gradeId) >
 <tr>
<td>${pc.title}</td>
<#if grad??>
    <td>${grad.gradeName}</td>
    <#else>
    <td></td>
</#if>
<td>${Util.subjectById(pc.metaSubjectId!).msubjName!?html}</td>
<td>${pc.createUserName?html}</td>
<td>${pc.leaderUserName?html}</td>
<td>${pc.memberCount}</td>
<td>${pc.privateCount!}</td>
<td>${pc.editCount!}</td>
<td>${pc.articleCount}</td>
<td>${pc.resourceCount}</td>
<td>${pc.actionCount}</td>
<td>${pc.topicCount}</td>
<td>${pc.topicReplyCount}</td>
<td>
    <#if pc.recommendState??>
        <#if pc.recommendState>
        推荐
        </#if>
    </#if>
</td>
<td>
    <#if pc.status == 0 >
    正常
    <#elseif pc.status == 1>
     待审核
    <#elseif pc.status == 2>
     锁定
    <#else>
    未知
    </#if>
</td>
<td>${pc.createUserUnitTitle?html}</td>
<td>${pc.leaderUserUnitTitle?html}</td>
<td>${pc.startDate?string("yyyy-MM-dd")}</td>
<td>${pc.endDate?string("yyyy-MM-dd")}</td>
</tr>
</#list>
 </table>
</#if>