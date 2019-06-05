
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
            <#if (new_wr_list??) && (new_wr_list?size > 0) >
            <div class="stadio">
                <ul class="imgList">
                   <#assign news_wr = new_wr_list[0..4]>
                   <#list news_wr as wr>
	                    <li>
	                        <a href="go.action?loginName=${wr.loginName}" class="imgLi"><img width='116' height='116' src="${SSOServerUrl +'upload/'+wr.userIcon!'images/default.gif'}" onerror="this.src='images/default.gif'"/></a>
	                        <a href="go.action?loginName=${wr.loginName}">
	                        <#if (wr.blogName.length() > 10) >${wr.blogName.substring(0,5)}...<#else>${wr.blogName}</#if>
	                        </a>
	                        <div class ="imgListBg">
	                           	<a class="imgLi" href="go.action?loginName=${wr.loginName}"></a>
	                        </div>
	                    </li>
                   </#list>
                </ul>
            </div>
          </#if>
          <#if (hot_wr_list??) && (hot_wr_list?size > 0) >
            <div class="stadio none">
                <ul class="imgList">
                   <#assign hot_wr = hot_wr_list[0..4]>
                   <#list hot_wr as wr>
	                    <li>
	                        <a href="go.action?loginName=${wr.loginName}" class="imgLi"><img width='116' height='116' src="${SSOServerUrl +'upload/'+wr.userIcon!'images/default.gif'}" onerror="this.src='images/default.gif'"/></a>
	                        <a href="go.action?loginName=${wr.loginName}">
	                        <#if (wr.blogName.length() > 10) >${wr.blogName.substring(0,5)}...<#else>${wr.blogName}</#if>
	                        </a>
	                        <div class ="imgListBg">
	                           	<a class="imgLi" href="go.action?loginName=${wr.loginName}"></a>
	                        </div>
	                    </li>
                   </#list>
                </ul>
            </div>
          </#if>
          <#if (rcmd_wr_list??) && (rcmd_wr_list?size > 0) >
            <div class="stadio none">
                <ul class="imgList">
                   <#assign rcmd_wr = rcmd_wr_list[0..4]>
                   <#list rcmd_wr as wr>
	                    <li>
	                        <a href="go.action?loginName=${wr.loginName}" class="imgLi"><img width='116' height='116' src="${SSOServerUrl +'upload/'+wr.userIcon!'images/default.gif'}" onerror="this.src='images/default.gif'"/></a>
	                        <a href="go.action?loginName=${wr.loginName}">
	                        <#if (wr.blogName.length() > 10) >${wr.blogName.substring(0,5)}...<#else>${wr.blogName}</#if>
	                        </a>
	                        <div class ="imgListBg">
	                           	<a class="imgLi" href="go.action?loginName=${wr.loginName}"></a>
	                        </div>
	                    </li>
                   </#list>
                </ul>
            </div>
          </#if>
           <#if (teacher_show??) >
            <div class="stadio none">
                <ul class="imgList">
                 <#list teacher_show[0..4] as wr>
	                    <li>
	                        <a href="go.action?loginName=${wr.loginName}" class="imgLi"><img width='116' height='116' src="${SSOServerUrl +'upload/'+wr.userIcon!'images/default.gif'}" onerror="this.src='images/default.gif'"/></a>
	                        <a href="go.action?loginName=${wr.loginName}">
	                       		 ${wr.trueName!?html}
	                        </a>
	                        <div class ="imgListBg">
	                           	<a class="imgLi" href="go.action?loginName=${wr.loginName}"></a>
	                        </div>
	                    </li>
                   </#list>
                </ul>
            </div>
          </#if>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize2" /></div>
        </div>
        <!--最新工作室 End-->
    </div>