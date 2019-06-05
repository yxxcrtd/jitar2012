<#-- 暂时未实现 -->
<div class='c'>
  <div class='c_head'>
    <div class='c_head_right'></div>
    <div class='c_head_left titlecolor'> 协作组活跃度排行</div>
  </div>
  <div class='c_content'>
    <#if group_activity_list?? >
      <table border='0' cellpadding='1' cellpadding='1' width='100%'>
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
