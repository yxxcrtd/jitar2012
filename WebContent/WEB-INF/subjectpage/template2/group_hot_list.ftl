<div class='panel'>
  <div class='panel_head'>
    <div class='panel_head_right'><a href='groupList.py?type=hot&id=${subject.subjectId}&unitId=${unitId!}' class='blog_more'>更多…</a></div>    
    <div class='panel_head_left titlecolor'>热门协作组</div>
  </div>
  <div class='panel_content'>
<#if (hot_list??) && (hot_list?size > 0) >
  <#assign g = hot_list[0] >
  <#assign u = Util.userById(g.createUserId) >
    <table  border='0'>
      <tr>
        <td align='center'><span class='border_img'><a href='${ContextPath}go.action?groupId=${g.groupId}'><img src='${Util.url(g.groupIcon!'images/group_default.gif')}' 
            width='64' height='64' border='0' /></a></span></td>
	      <td width='170'>
	        <div><b><a href='${ContextPath}go.action?groupId=${g.groupId}'>${g.groupTitle!?html}</a></b></div>
	        <div>组长：<a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.nickName}</a></div>
	        <div>创建时间：${g.createDate?string('yyyy-MM-dd')}</div>
	      </td>
      </tr>
     </table>
    <div>简介：${g.groupIntroduce!}</div>
</#if >
  </div>
</div>