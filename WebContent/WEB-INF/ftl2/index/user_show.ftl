<!--最新工作室 start-->
<div class="leftWidth stadioWrap border mt3">
    <h3 class="h3Head">
        <a href="blogList.action?type=new" class="more">更多</a>
        <a href="blogList.action?type=hot" class="more none">更多</a>
        <a href="blogList.action?type=2" class="more none">更多</a>
        <a href="blogList.action?type=6" class="more none">更多</a>
        <a href="" class="more none">更多</a>
        <a href="blogList.action?type=new" class="sectionTitle active">最新工作室<span></span></a>
        <a href="blogList.action?type=hot" class="sectionTitle">&nbsp;&nbsp;热门&nbsp;&nbsp;<span></span></a>
        <a href="blogList.action?type=2" class="sectionTitle">&nbsp;&nbsp;推荐&nbsp;&nbsp;<span></span></a>
        <a href="blogList.action?type=6" class="sectionTitle">教师风采<span></span></a>
    </h3>
    <div class="stadio">
    <#if (new_wr_list??) && (new_wr_list?size > 0) >
        <ul class="imgList">
           <#if new_wr_list?size &lt; 5>           
           		<#assign news_wr = new_wr_list[0..new_wr_list?size-1]>
           <#else>
           		<#assign news_wr = new_wr_list[0..4]>
           </#if>
           <#list news_wr as wr>
              <li>
                  <a href="go.action?loginName=${wr.loginName}" class="imgLi"><img width='116' height='116' src="${(SSOServerUrl +'upload/'+ wr.userIcon)!"images/default.gif"}" onerror="this.src='images/default.gif'"/></a>
                  <a href="go.action?loginName=${wr.loginName}" title="${wr.blogName}">
                  <#if (wr.blogName.length() > 8) >${wr.blogName.substring(0,7)}...<#else>${wr.blogName}</#if>
                  </a>
                  <div class ="imgListBg">
                     	<a class="imgLi" href="go.action?loginName=${wr.loginName}"></a>
                  </div>
              </li>
           </#list>
        </ul>
        </#if>
    </div>
    <div class="stadio none">
    <#if (hot_wr_list??) && (hot_wr_list?size > 0) >
        <ul class="imgList">
           <#if hot_wr_list?size &lt; 5>
           <#assign hot_wr = hot_wr_list[0..hot_wr_list?size-1]>
           <#else>
           <#assign hot_wr = hot_wr_list[0..4]>
           </#if>
           <#list hot_wr as wr>
              <li>
                  <a href="go.action?loginName=${wr.loginName}" class="imgLi"><img width='116' height='116' src="${(SSOServerUrl +'upload/'+ wr.userIcon)!"images/default.gif"}" onerror="this.src='images/default.gif'"/></a>
                  <a href="go.action?loginName=${wr.loginName}" title="${wr.blogName}">
                  <#if (wr.blogName.length() > 8) >${wr.blogName.substring(0,7)}...<#else>${wr.blogName}</#if>
                  </a>
                  <div class ="imgListBg">
                     	<a class="imgLi" href="go.action?loginName=${wr.loginName}"></a>
                  </div>
              </li>
           </#list>
        </ul>
        </#if>
    </div>
    <div class="stadio none">
    <#if (rcmd_wr_list??) && (rcmd_wr_list?size > 0) >
        <ul class="imgList">
           <#list rcmd_wr_list as wr>
             <#if wr_index &lt; 5>
              <li>
                  <a href="go.action?loginName=${wr.loginName}" class="imgLi"><img width='116' height='116' src="${(SSOServerUrl +'upload/'+ wr.userIcon)!"images/default.gif"}" onerror="this.src='images/default.gif'"/></a>
                  <a href="go.action?loginName=${wr.loginName}" title="${wr.blogName}">
                  <#if (wr.blogName.length() > 8) >${wr.blogName.substring(0,7)}...<#else>${wr.blogName}</#if>
                  </a>
                  <div class ="imgListBg">
                     	<a class="imgLi" href="go.action?loginName=${wr.loginName}"></a>
                  </div>
              </li>
             </#if>
           </#list>
        </ul>
        </#if>
    </div>   
    <div class="stadio none">
    <#if teacher_show?? && (teacher_show?size > 0) >
        <ul class="imgList">
           <#if teacher_show?size &lt; 5>
           <#assign teacher_show = teacher_show[0..teacher_show?size-1]>
           <#else>
           <#assign teacher_show = teacher_show[0..4]>
           </#if>
           
         <#list teacher_show as wr>
              <li>
                  <a href="go.action?loginName=${wr.loginName}" class="imgLi"><img width='116' height='116' src="${(SSOServerUrl +'upload/'+ wr.userIcon)!"images/default.gif"}" onerror="this.src='images/default.gif'"/></a>
                  <a href="go.action?loginName=${wr.loginName}" title="${wr.trueName}">
                 		 ${wr.trueName!?html}
                  </a>
                  <div class ="imgListBg">
                     	<a class="imgLi" href="go.action?loginName=${wr.loginName}"></a>
                  </div>
              </li>
           </#list>
        </ul>
        </#if>
    </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize2" /></div>
</div>
<!--最新工作室 End-->
</div>