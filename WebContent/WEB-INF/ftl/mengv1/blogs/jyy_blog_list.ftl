<div class='b1'>    
  <div class='b1_head_d'>
    <div class='b1_head_right'><a href='blogList.action?type=4'>更多…</a></div>
    <div class='b1_head_left'> ${Util.typeIdToName(4)}工作室</div>
  </div>
  <div style='padding:6px;'>
	  <#if comissioner_list?? >	  	    
	    <table border='0' width='100%' cellpadding='0' cellspacing='0'>
	    <#assign columnCount = 3>
	    <#assign rowCount = 2>
	    <#list 0..rowCount -1 as row>
		    <tr valign='top'>
		    <#list 0..columnCount -1 as cell >   
			    <td style='text-align:center;width:33%;padding-bottom:4px;'>
				    <#if comissioner_list[row * columnCount + cell]?? >
				    <#assign u = comissioner_list[row * columnCount + cell] >
				      <div style='height:64px;'>
				      <table border='0' cellpadding='0' cellspacing='0' class='mimg_container_a'>
				      <tr><td><a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" 
				            width='56' height='56' border='0' onerror="this.src='${SiteUrl}images/default.gif'" /></a></td></tr>
				      </table>
				      </div>
				      <div style='text-align:center'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName!?html}</a></div>
				      <#else>
				      &nbsp;
				      </#if>
			      </td>
		      </#list>
	    </tr>
	    </#list>
	    </table>
	    </#if>
  </div>
</div>