<#-- 研修之星 -->
<div class='m1' style="height:126px;">
  <div class='m1_head'>
    <div class='m1_head_right'><a href='blogList.action?type=5'>更多…</a></div>
    <div class='m1_head_left'>&nbsp;<img src='${ContextPath}css/index/j.gif' /> ${Util.typeIdToName(5)}</div>
  </div>
  <div class='m1_content'>
<#if teacher_star??>
<#if teacher_star[0]?? >  
<table border='0' cellspacing='0' cellpadding='0' width='99%'>
<tr>
<td style='vertical-align:top' width='68'>
<span class='article_border'><a href='go.action?userId=${teacher_star[0].userId}'><img src="${SSOServerUrl +'upload/'+teacher_star[0].userIcon!'images/default.gif'}" onerror="this.src='images/default.gif'" class='icon64' width='64' border='0' /></a></span></td>
<td class='blog_top' valign='top'>
  <a href='go.action?userId=${teacher_star[0].userId}'><span>${teacher_star[0].blogName!?html}</span></a><br/>
  ${teacher_star[0].blogIntroduce!}<br/>
</td>
</tr>
</table>
</#if>
</#if>
  </div>        
</div>
