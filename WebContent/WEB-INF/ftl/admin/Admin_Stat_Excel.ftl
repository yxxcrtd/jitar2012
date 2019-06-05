<#if user_list??>
<table border="1">
<tr>
<th>用户ID</th>
<th>登录名</th>
<th>真实姓名</th>
<th>工作室名称</th>
<th>学段</th>
<th>学科</th>
<th>机构</th>
<th>教师类型</th>
<th>工作室访问量</th>
<th>我的文章被访问量</th>
<th>我的资源被访问量</th>
<th>原创文章数</th>
<th>转载文章数</th>
<th>推荐文章数</th>
<th>我的文章被评论数</th>
<th>我发出的文章评论数</th>
<th>资源数</th>
<th>推荐资源数</th>
<th>资源被评论数</th>
<th>评论资源数</th>
<th>资源下载数</th>
<th>创建协作组数</th>
<th>加入协作组数</th>
<th>照片数</th>
<th>视频数</th>
<th>文章得分</th>
<th>资源得分</th>
<th>照片得分</th>
<th>视频得分</th>
<th>评论得分</th>
<th>文章删除罚分</th>
<th>资源删除罚分</th>
<th>评论删除罚分</th>
<th>图片删除罚分</th>
<th>视频删除罚分</th>
<th>总积分</th>
<th>用户状态</th>
</tr>

<#list user_list as user>
<tr>
<td>${user.userId}</td>
<td>${user.loginName}</td>
<td>${user.trueName!}</td>
<td>${user.blogName!}</td>
<td >
  <nobr>
  <#assign sg = ""/>
  <#assign ss = ""/>
  <#assign usgList = Util.getSubjectGradeListByUserId(user.userId!)>
    <#if usgList?? && (usgList?size> 0) >
    <#list usgList as usg>
    <#if usg.gradeId??><#assign sg= sg+ Util.gradeById(usg.gradeId).gradeName!?html + "<br/>"><#else><#assign sg = sg + "未设置<br/>"/></#if>
    <#if usg.subjectId??><#assign ss= ss+ Util.subjectById(usg.subjectId).msubjName!?html + "<br/>"><#else><#assign ss = ss+ "未设置<br/>" /></#if>
    </#list>
    </#if>
    ${sg!}
 </nobr>
</td>
<td style="text-align: center;">
  <nobr>${ss!}</nobr>
</td>
<td>
<#if user.unitId??>
${Util.unitById(user.unitId).unitTitle!?html}
</#if>
</td>
<td>
<#if user.userType??>
<#assign showTypeName = Util.typeIdToName(user.userType) >
    <#if showTypeName??>
        <#list showTypeName?split("/") as x>
        <#if (x?length) &gt; 0 >${x} </#if>
        </#list> 
    </#if>
</#if>
 </td>
 <td>${user.visitCount}</td>
 <td>${user.visitArticleCount}</td>
 <td>${user.visitResourceCount}</td>
 <td>${user.myArticleCount}</td>
 <td>${user.otherArticleCount}</td>
 <td>${user.recommendArticleCount}</td>
 <td>${user.articleCommentCount}</td>
 <td>${user.articleICommentCount}</td>
 <td>${user.resourceCount}</td>
 <td>${user.recommendResourceCount}</td>
 <td>${user.resourceCommentCount}</td>
 <td>${user.resourceICommentCount}</td>
 <td>${user.resourceDownloadCount}</td>
 <td>${user.createGroupCount}</td>
 <td>${user.jionGroupCount}</td>
 <td>${user.photoCount}</td>
 <td>${user.videoCount}</td>
<td>${user.articleScore}</td>
<td>${user.resourceScore}</td>
<td>${user.photoScore}</td>
<td>${user.videoScore}</td>
<td>${user.commentScore}</td>
<td>${user.articlePunishScore}</td>
<td>${user.resourcePunishScore}</td>
<td>${user.commentPunishScore}</td>
<td>${user.photoPunishScore}</td>
<td>${user.videoPunishScore}</td>
<td>${user.userScore}</td>
<td>
<#if user.userStatus == 0>正常
<#elseif user.userStatus == 1><font style="color: #0000FF;">待审核</font>
<#elseif user.userStatus == 3><font style="color: #FF0000;">已锁定</font>
<#elseif user.userStatus == 2><font style="color: #00FF00;">已删除</font>
<#else><font style="color: #FF0000;">未知状态</font>
</#if>
</td>
</tr>
</#list>
</table>
</#if>