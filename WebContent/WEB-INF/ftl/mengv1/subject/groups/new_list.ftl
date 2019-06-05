<div class='c'>
  <div class='c_head'>
    <div class='c_head_right'><a href='subjectGroups.py?type=new&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}' class='blog_more'>更多…</a></div>
    <div class='c_head_left titlecolor'> 最新协作组</div>
  </div>
  <div class='c_content'>
<#if (new_list??) && (new_list?size > 0) >
  <#assign g = new_list[0] >
  <#assign u = Util.userById(g.createUserId) >
    <table border='0'>
      <tr>
	      <td align='center'><span class='border_img'><a href='${SiteUrl}go.action?groupId=${g.groupId}'><img src='${Util.url(g.groupIcon!'images/group_default.gif')}' 
	         width='48' height='48' border='0' /></a></span></td>
	      <td width='170'>
	        <div><b><a href='${SiteUrl}go.action?groupId=${g.groupId}'>${g.groupTitle!?html}</a></b></div>
	        <div>组长：<a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.nickName}</a></div>
	        <div>创建时间：${g.createDate?string('yyyy-MM-dd') }</div>
	      </td>
      </tr>
     </table>
  <#list new_list as g >
    <#if (g_index > 0) >
      <div><a href='${SiteUrl}go.action?groupId=${g.groupId}'>${g.groupTitle!?html}</a></div>
    </#if>
  </#list>
</#if>
  </div>
</div>
