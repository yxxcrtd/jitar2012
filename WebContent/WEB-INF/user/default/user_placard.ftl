<#if (placard_list??) && (placard_list?size > 0) >
 <#-- 当前只显示第一条 -->
 <#assign p = placard_list[0] >
 <div>${p.title!?html} (${p.createDate?string('yy-MM-dd')})</div>
 <div>${p.content!}</div>
<#else >
  当前没有公告.
</#if>
