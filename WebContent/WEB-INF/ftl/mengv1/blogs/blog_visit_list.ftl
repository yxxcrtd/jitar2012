<div class='b3'>    
  <div class='b1_head b1_bg1'>
    <div class='b1_head_right'><a href='blogList.action?type=hot'>更多…</a></div>
    <div class='b1_head_left'> 工作室访问排行</div>
  </div>
  <div class='b1_content'>
      <div class='tc1'>
        <!-- 博客访问排行 -->
          <#if blog_visit_charts??>
          <table  cellpadding='1' cellpadding='1' width='240'>
              <#list blog_visit_charts as user>
              	<tr valign='top'>
              	<td class="rank_left">${user_index + 1}</td>
              	<td class="rank_right">
              	<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.blogName}</a>
              	</td>
              	<td align='right'>${user.visitCount}
              	</td>
              	</tr>
              </#list>
          </table>
          </#if>
      </div>
  </div>
</div>
