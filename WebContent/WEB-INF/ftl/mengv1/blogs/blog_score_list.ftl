<div class='b3'>    
  <div class='b1_head'>
    <div class='b1_head_right'><a href='blogList.action?type=score'>更多…</a></div>
    <div class='b1_head_left'> 积分排行榜</div>
  </div>
  <div class='b1_content'>
      <div class='tc1'>
          <#if blog_score_charts??>
          <table  cellpadding='1' cellpadding='1' width='240'>
              <#list blog_score_charts as user>
              	<tr valign='top'>
              	<td class="rank_left">${user_index + 1}</td>
              	<td class="rank_right">
              	<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.blogName}</a>
              	</td>
              	<td align='right'>${user.userScore}
              	</td>
              	</tr>
              </#list>
          </table>
          </#if>
      </div>
  </div>
</div>
