<!--最新资源 start-->
<div class="rightWidth naturalWrap border mt3">
    <h3 class="h3Head">
        <a href="resources.action?type=new" class="more">更多</a>
        <a href="resources.action?type=hot" class="more none">更多</a>
        <a href="resources.action?type=new" class="sectionTitle active">最新资源<span></span></a>
        <a href="resources.action?type=hot" class="sectionTitle">最热资源<span></span></a>
    </h3>
      <div class="natural"<#if isKuanDianUser??> style="height:160px;"</#if>>
      <#if new_resource_list?? && (new_resource_list?size > 0)>
          <ul class="ulList">
            <#list new_resource_list as r>
             <#if r_index &lt; 10>
              <li class="${Util.iconCss(r.href!)}" >
                <em class="emDate">${r.createDate?string("MM-dd")}</em>
                <a title="${r.title!}"  href="showResource.action?resourceId=${r.resourceId}">${Util.getCountedWords(r.title!?html,14)}</a>
              </li>
             </#if>
            </#list>
          </ul>
          </#if>
      </div>
      <div class="natural none"<#if isKuanDianUser??> style="height:160px;"</#if>>
      <#if hot_resource_list?? && (hot_resource_list?size>0)>
          <ul class="ulList">
       		<#list hot_resource_list as r>
       		  <#if r_index &lt; 10>
                <li class="${Util.iconCss(r.href!)}" >
                  <em class="emDate">${r.createDate?string("MM-dd")}</em>
                  <a title="${r.title!}" href="showResource.action?resourceId=${r.resourceId}">${Util.getCountedWords(r.title!?html,14)}</a>
                </li>
              </#if>
              </#list>
          </ul>
           </#if>
      </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize1" /></div>
</div>
<!--最新资源 End-->