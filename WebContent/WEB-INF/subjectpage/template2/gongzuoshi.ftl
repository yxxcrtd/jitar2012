<div class='tab'> 
	<div id="blog_" class='tab2'>
		<label class='tab_label_1'><img src='${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/j.gif' />&nbsp;${webpart.displayName!?html}</label>
		<div class="cur" onmouseover="TabUtil.changeTab('blog_',0)"><a href='${SubjectRootUrl}py/blogList.py?type=new&id=${subject.subjectId}&unitId=${unitId!}&title=最新${webpart.displayName!?url}'><span>最新</span></a></div>
		<div class="" onmouseover="TabUtil.changeTab('blog_',1);"><a href='${SubjectRootUrl}py/blogList.py?type=hot&id=${subject.subjectId}&unitId=${unitId!}&title=最热${webpart.displayName!?url}'><span>最热</span></a></div>
		<div class="" onmouseover="TabUtil.changeTab('blog_',2)"><a href='${SubjectRootUrl}py/blogList.py?type=rcmd&id=${subject.subjectId}&unitId=${unitId!}&title=推荐${webpart.displayName!?url}'><span>推荐</span></a></div>
		<div class="" style='font-size:0;'></div>
	</div>
	<div class='tab_content'>
		<div id="blog_0"  style="display: block">		
		<!-- 最新 -->
		<#if new_blog_list?? >          
	    <table border='0' width='100%' cellpadding='0' cellspacing='0'>
	    <#assign columnCount = 4>
	    <#assign rowCount = 1>
	    <#list 0..rowCount -1 as row>
	        <tr valign='top'>
	        <#list 0..columnCount -1 as cell >   
	        <td style='text-align:center;width:33%;padding-bottom:4px;'>
	            <#if new_blog_list[row * columnCount + cell]?? >
	            <#assign u = new_blog_list[row * columnCount + cell] >
	              <div style='height:104px;'>
	              <table border='0' cellpadding='0' cellspacing='0' style='border:1px solid #bdcbd5;padding:1px;margin:auto'>
	              <tr><td><a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${ContextPath}images/default.gif'"  width='96' height='96' border='0' /></a></td></tr>
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
		<div id="blog_1" style="display: none;">
		<!-- 热门 -->
		<#if hot_blog_list?? >          
	    <table border='0' width='100%' cellpadding='0' cellspacing='0'>
	    <#assign columnCount = 4>
	    <#assign rowCount = 1>
	    <#list 0..rowCount -1 as row>
	        <tr valign='top'>
	        <#list 0..columnCount -1 as cell >   
	        <td style='text-align:center;width:33%;padding-bottom:4px;'>
	            <#if hot_blog_list[row * columnCount + cell]?? >
	            <#assign u = hot_blog_list[row * columnCount + cell] >
	              <div style='height:104px;'>
	              <table border='0' cellpadding='0' cellspacing='0' style='border:1px solid #bdcbd5;padding:1px;margin:auto'>
	              <tr><td><a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${ContextPath}images/default.gif'"  width='96' height='96' border='0' /></a></td></tr>
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
    
	    <div id="blog_2" style="display: none;">   
	    <!-- 推荐 -->
		<#if rcmd_blog_list?? >          
	    <table border='0' width='100%' cellpadding='0' cellspacing='0'>
	    <#assign columnCount = 4>
	    <#assign rowCount = 1>
	    <#list 0..rowCount -1 as row>
	        <tr valign='top'>
	        <#list 0..columnCount -1 as cell >   
	        <td style='text-align:center;width:33%;padding-bottom:4px;'>
	            <#if rcmd_blog_list[row * columnCount + cell]?? >
	            <#assign u = rcmd_blog_list[row * columnCount + cell] >
	              <div style='height:104px;'>
	              <table border='0' cellpadding='0' cellspacing='0' style='border:1px solid #bdcbd5;padding:1px;margin:auto'>
	              <tr><td><a href='${SiteUrl}go.action?loginName=${u.loginName}'><img src="${SSOServerUrl +'upload/'+ (u.userIcon!'')}" onerror="this.src='${ContextPath}images/default.gif'" width='96' height='96' border='0' /></a></td></tr>
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
	   <div id="blog_3" style="display: none;"></div>
   </div>   
</div>