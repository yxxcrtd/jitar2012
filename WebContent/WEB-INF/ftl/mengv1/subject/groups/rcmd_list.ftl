<div class='c'>
  <div class='c_head'>
  	<#if subject??>
    <div class='c_head_right'><a href='subjectGroups.py?type=rcmd&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}' class='blog_more'>更多…</a></div>
    <#else>
    <div class='c_head_right'><a href='subjectGroups.py?type=rcmd&gradeId=${gradeId!}' class='blog_more'>更多…</a></div>
    </#if>
    <div class='c_head_left titlecolor'> 推荐协作组</div>
  </div>
  <div class='c_content'>
<#if (rcmd_list??) && (rcmd_list?size > 0) >
  <#assign g = rcmd_list[0]>
    <table  border='0'>
      <tr>
      <td align='center'><span class='border_img'><a href='${SiteUrl}go.action?groupId=${g.groupId}'><img src='${Util.url(g.groupIcon!'images/group_default.gif')}'
        width='64' height='64' border='0' /></a></span></td>
      <td width='160'>
        <div><b><a href='${SiteUrl}go.action?groupId=${g.groupId}'>${g.groupTitle!?html}</a></b></div>
        <div>创建时间：${g.createDate?string('yyyy-MM-dd')}</div>
        <div>简介：${g.groupIntroduce!}</div>
      </td>
      </tr>
     </table>
   <#list rcmd_list as g >
     <#if (g_index > 0) >
       <div><a href='${SiteUrl}go.action?groupId=${g.groupId}'>${g.groupTitle!?html}</a></div>
     </#if>
   </#list>
</#if>
  </div>
</div>
