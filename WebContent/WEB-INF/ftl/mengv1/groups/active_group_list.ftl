
<#-- 当前未实现活跃度算法 -->
<div class='b1'>
  <div class='b1_head2'>
    <div class='b1_head_right' style='display:none'><a href=''>更多…</a></div>
    <div class='b1_head_left'> 协作组活跃度排行</div>
  </div>
  <div class='b1_content'>
      <div class='tc1'>
      <#if group_activity_list?? >
      <table border='0' cellpadding='1' cellpadding='1' width='246'>
      <#list group_activity_list as group>
        <tr valign='top'>
        <td class="rank_left">${group_index + 1}</td>
        <td class="rank_right">
            <a href='${SiteUrl}go.action?groupId=${group.groupId}'>${group.groupTitle}</a>
        </td>
        </tr>
      </#list>
       </table>
      <#else>
       暂无排行
      </#if>
    </div>
  </div>
</div>

