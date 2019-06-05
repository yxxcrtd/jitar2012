<!--最新资源 start-->
        <div class="rightWidth naturalWrap border mt3">
            <h3 class="h3Head">
                <a href="resources.action?type=new" class="more">更多</a>
                <a href="resources.action?type=hot" class="more none">更多</a>
                <a href="resources.action?type=new" class="sectionTitle active">最新资源<span></span></a>
                <a href="resources.action?type=hot" class="sectionTitle">最热资源<span></span></a>
            </h3>
              <#if new_resource_list??>
	            <div class="natural">
	                <ul class="ulList">
	                  <#assign rslist = new_resource_list[0..9]>
	                  <#list rslist as r>
	                    <li class="" >
	                        <img src="${Util.iconImage(r.href!)}" align="absmiddle"/>
		                    <em class="emDate">${r.createDate?string("MM-dd")}</em>
		                    <a href="showResource.action?resourceId=${r.resourceId}">${Util.getCountedWords(r.title!?html,14)}</a>
	                    </li>
	                  </#list>
	                </ul>
	            </div>
            </#if>
	        <#if rcmd_resource_list??>
	            <div class="natural none">
	                <ul class="ulList">
	                	<#assign rcmdlist = rcmd_resource_list[0..9]>
	             		<#list rcmdlist as r>
		                    <li class="" >
		                        <img src="${Util.iconImage(r.href!)}" align="absmiddle"/>
			                    <em class="emDate">${r.createDate?string("MM-dd")}</em>
			                    <a href="showResource.action?resourceId=${r.resourceId}">${Util.getCountedWords(r.title!?html,14)}</a>
		                    </li>
	                    </#list>
	                </ul>
	            </div>
            </#if>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize1" /></div>
        </div>
        <!--最新资源 End-->