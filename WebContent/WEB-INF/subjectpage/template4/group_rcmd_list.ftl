<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_right'><a href='groupList.py?type=rcmd&id=${subject.subjectId}&unitId=${unitId!}' class='blog_more'>更多…</a></div>
    <div class='panel_head_left titlecolor'>推荐协作组</div>
  </div>
  <div class='panel_content'>
<#if (rcmd_list??) && (rcmd_list?size > 0) >
  <#assign g = rcmd_list[0]>
    <table border='0'>
      <tr>
      <td align='center'><span class='border_img'><a href='${ContextPath}go.action?groupId=${g.groupId}'><img src='${Util.url(g.groupIcon!'images/group_default.gif')}'
        width='64' height='64' border='0' /></a></span></td>
      <td width='160'>
        <div><b><a href='${ContextPath}go.action?groupId=${g.groupId}'>${g.groupTitle!?html}</a></b></div>
        <div>创建时间：${g.createDate?string('yyyy-MM-dd')}</div>
        <div>简介：${g.groupIntroduce!}</div>
      </td>
      </tr>
     </table>
   <#list rcmd_list as g >
     <#if (g_index > 0) >
       <div><a href='${ContextPath}go.action?groupId=${g.groupId}'>${g.groupTitle!?html}</a></div>
     </#if >
   </#list >
</#if >
  </div>
</div>