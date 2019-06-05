
<!--排行榜 Start-->
<div class="secLeftW border resourcesWrap mt3">
    <h3 class="h3Head h3Style1">
        <a href="javascript:;" class="sectionTitle active">上传排行榜<span></span></a>
        <a href="javascript:;" class="sectionTitle">下载排行榜</a>
    </h3>
    <div class="secTop resourcesTop">
        <ul class="ulList">
               <!-- 资源上载排行 -->
              <#if upload_user_list??>
                <#list upload_user_list as user>
                  <li>
                  <#if user_index &lt;3>
                  <span class="numIcon numOrange">${user_index + 1}</span>
                  <#else>
                  <span class="numIcon">${user_index + 1}</span>
                  </#if>
                  <a href="${SiteUrl}go.action?loginName=${user.loginName}">${user.trueName!?html}</a><!--[${user.resourceCount!}]-->
                  </li>  
                </#list>
              </#if>     
        </ul>
    </div>
    <div class="secTop resourcesTop none">
        <ul class="ulList">
           <!-- 资源下载排行 -->
          <#if download_resource_list??>
                <#list download_resource_list as r>
                  <li>
                  <#if r_index &lt;3>
                  <span class="numIcon numOrange">${r_index + 1}</span>
                  <#else>
                  <span class="numIcon">${r_index + 1}</span>
                  </#if>
                  <a title="${r.title!?html}" href="showResource.action?resourceId=${r.resourceId}">${Util.getCountedWords(r.title!?html,12,1)}</a><!--${r.downloadCount!}-->
                  </li>  
                </#list>
          </#if>
        </ul>
    </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
</div>
<!--排行榜 End-->