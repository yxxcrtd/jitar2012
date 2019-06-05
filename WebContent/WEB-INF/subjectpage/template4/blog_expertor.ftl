<div class='panel'>
  <div class='panel_head'>
  <div class='panel_head_right'><a href='${SubjectRootUrl}py/blogList.py?type=expert&unitId=${unitId!}' class='blog_more'>更多…</a></div>
    <div class='panel_head_left titlecolor'>学科带头人工作室</div>
  </div>
  <div class='panel_content'>
<#if (expert_list??) && (expert_list?size > 0) >
<#list expert_list as u >
    <div>
     <span class='border_img'><a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${ContextPath}images/default.gif'" width='48' height='48' border='0' /></a></span>
     用户：<a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName!?html}</a><br />
     工作室：<a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.blogName!?html}</a><br />
     简介：${u.blogIntroduce!}
  </div>
  <#if u_has_next >
  <div class='linebar' style='clear:both'></div>
  </#if>
 </#list>
</#if >
  </div>
</div>