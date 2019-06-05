<!--课题资源 Start-->
<div class="specialLeft border">
    <h3 class="h3Head textIn"><a href="ktgroups.action?act=all&type=resource" class="more">更多</a>课题成果-资源</h3>
    <div class="specialList clearfix">
    <#if (resource_list??) && (resource_list?size > 0) >
      <ul class="ulList">
        <#list resource_list as resource >
          <li class="${Util.iconCss(resource.href!)}"><a title="${resource.title!?html}" href='${ContextPath}showResource.action?resourceId=${resource.resourceId}' target='_blank'>${Util.getCountedWords(resource.title!?html,12,1)}</a></li>
        </#list>
      </ul>
    </#if>
    </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
</div>
<!--课题资源 End-->