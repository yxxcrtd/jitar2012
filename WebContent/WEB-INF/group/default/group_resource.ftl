<div id="t${guid}" class='tab_div'>
	<div class="cur" onmouseover="TabUtil.changeTab('t${guid}',0)"><span>最新资源</span></div>
	<#--<div onmouseover="TabUtil.changeTab('t${guid}',1)"><span>热门资源</span></div>-->
	<div onmouseover="TabUtil.changeTab('t${guid}',1)"><span>精华资源</span></div>
	<div class=""></div>
</div>
<div class='tab_content'>
	<div id="t${guid}0" style='display:block;'>
	<ul class='listul'>
	 <#list new_list as resource>
	  <li><span style='float:right'>${resource.createDate?string('MM-dd')}</span><a href='${SiteUrl}showResource.action?resourceId=${resource.resourceId}' 
	    target='_blank'><img src='${Util.iconImage(resource.href!)}' border='0' align='absmiddle' /> 
	    ${resource.title!?html}</a> (${resource.downloadCount})</li>
	 </#list>
	</ul>
	</div><#--
	<div id="t${guid}1" style='display:none;'>
	<ul class='listul'>
	 <#list hot_list as resource>
	 	<li><span style='float:right'>${resource.createDate?string('MM-dd')}</span><a href='${SiteUrl}showResource.action?resourceId=${resource.resourceId}' 
	 	  target='_blank'><img src='${Util.iconImage(resource.href!)}' border='0' align='absmiddle' />
	 	  ${resource.title!?html}</a> (${resource.downloadCount})</li>
	  </#list>
	</ul>
	</div>-->
	<div id="t${guid}1" style='display:none;'>
	<ul class='listul'>
		<#list best_list as resource>
		 <li><span style='float:right'>${resource.createDate?string('MM-dd')}</span><a href='${SiteUrl}showResource.action?resourceId=${resource.resourceId}' 
		   target='_blank'><img src='${Util.iconImage(resource.href!)}' border='0' align='absmiddle' />
		   ${resource.title!?html}</a> (${resource.downloadCount})</li>
	 	</#list>
	</ul>
	</div>
	<div id="t${guid}2" style='display:none;'>
	</div>
</div>
<div style="clear:both; text-align:right; margin-top:6px;"><a href='${SiteUrl}g/${group.groupName}/rescate/0.html'>&gt;&gt; 全部资源</a></div>
