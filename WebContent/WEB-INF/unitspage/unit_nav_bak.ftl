<div class='navbar'>
	<div style='float:left;padding-left:20px'>
	<a href='${SiteUrl}index.action'><@nav_item 'index' '总站首页' /></a> | 
	<a href='${SiteUrl}units/index.py?unitId=${unit.unitId}'><@nav_item 'unit' '机构首页' /></a> | 
	<a href='${SiteUrl}units/unit_article.py?unitId=${unit.unitId}'><@nav_item 'unit_article' '机构文章' /></a> | 
	<a href='${SiteUrl}units/unit_resource.py?unitId=${unit.unitId}'><@nav_item 'unit_resource' '机构资源' /></a> | 
	<a href='${SiteUrl}units/unit_photo.py?unitId=${unit.unitId}'><@nav_item 'unit_photo' '机构图片' /></a> | 
	<a href='${SiteUrl}units/unit_video.py?unitId=${unit.unitId}'><@nav_item 'unit_video' '机构视频' /></a> | 
	<a href='${SiteUrl}units/unit_user.py?unitId=${unit.unitId}'><@nav_item 'unit_user' '机构工作室' /></a>	
	</div>
	<div style='float:right;padding-right:20px'>	
	<#if loginUser??>
	<a href='${SiteUrl}${loginUser.loginName}'>我的工作室</a> |
	<a href='${SiteUrl}units/manage/index.py?unitId=${unit.unitId}'>机构管理</a> |
	<a href='${SiteUrl}logout.jsp'>注销登录</a>
	<#else>
	<a href='${SiteUrl}login.jsp'>登录系统</a>
	</#if>
	</div>
</div>
<#macro nav_item item_name item_text>
  <#if !(head_nav??) >
    ${item_text}
  <#elseif head_nav == item_name>
    <span>${item_text}</span>
  <#else>
    ${item_text}
  </#if>
</#macro>