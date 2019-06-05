<div class='b3'>    
  <div class='b1_head_c'>
    <div class='b1_head_right'><a href='blogList.action?type=5'>更多…</a></div>
    <div class='b1_head_left'> ${Util.typeIdToName(5)}</div>
  </div>
  <div class='b1_content'>
   <#if teacher_star?? >
	   <#if teacher_star[0]?? >
	    <table border='0' cellspacing='0' cellpadding='0' width='99%'>
	    <tr>
	    <td style='vertical:top' width='68'>
	    <span class='img_container'><a href='${SiteUrl}go.action?loginName=${teacher_star[0].loginName}'><img src="${SSOServerUrl +'upload/'+teacher_star[0].userIcon!'images/default.gif'}" onerror="this.src='${SiteUrl}images/default.gif'" class='icon64' width='64' border='0' /></a></span></td>
	    <td class='blog_top' valign='top' style='padding-left:4px;'>
	      <a href='${SiteUrl}go.action?loginName=${teacher_star[0].loginName}'><span>${teacher_star[0].blogName!?html}</span></a><br/>
	      ${teacher_star[0].blogIntroduce!}<br/>
	    </td>
	    </tr>
	    </table>
	    <#else>
	    暂时没有取得数据
	    </#if>
	<#else>
	暂时没有取得数据
	</#if>    
  </div>
</div>
