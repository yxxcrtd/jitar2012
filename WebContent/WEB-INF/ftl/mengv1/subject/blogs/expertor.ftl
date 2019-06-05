<div class='c'>
  <div class='c_head'>
  <div class='c_head_right'><a href='blogList.action?type=expert&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}' class='blog_more'>更多…</a></div>
    <div class='c_head_left titlecolor'> 学科带头人工作室</div>
  </div>
  <div class='c_content'>
<#if (expert_list??) && (expert_list?size > 0) >
<#list expert_list as u >
    <div>
     <span class='border_img'><a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width='48' height='48' border='0' /></a></span>
     用户：<a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName!?html}</a><br />
     工作室：<a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.blogName!?html}</a><br />
     简介：${u.blogIntroduce!}
  </div>
  <#if u_has_next >
  <div class='linebar'></div>
  </#if>
 </#list>
  <div style='display:none'>
	  <div style='font-weight:bold;clear:both;padding-top:4px;'>个人文章</div>
	 <#list 1..6 as i>
	   <div>TODO: 学科带头人工作室个人文章</div>
	 </#list>
	</div>
</#if>
  </div>
</div>
