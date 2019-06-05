 <!--推荐协作 start-->
        <div class="rightWidth teamWrap border">
            <h3 class="h3Head">
                <a href="groups.action?type=rcmd" class="more">更多</a>
                <a href="groups.action?type=best" class="more none">更多</a>
                <a href="groups.action?type=rcmd" class="sectionTitle active">推荐协作组<span></span></a>
                <a href="groups.action?type=best" class="sectionTitle">优秀团队<span></span></a>
            </h3>
            <#if (rcmd_group_list??) && (rcmd_group_list?size > 0) >
			<#assign g = rcmd_group_list[0] >
			<#assign t = rcmd_group_list[1..3]>
	            <div class="team">
	                <ul class="imgList teamImgList">
	                    <li>
	                        <a href="go.action?groupId=${g.groupId}" class="imgLi"><img width='116' height='116' src="${Util.url(g.groupIcon!'images/group_default.gif')}" onerror="this.src='images/group_default.gif'"/></a>
	                        <a href="go.action?groupId=${g.groupId}">${g.groupTitle!?html}</a>
	                        <div class ="imgListBg">
	                           	<a class="imgLi" href="go.action?groupId=${g.groupId}"></a>
	                        </div>
	                    </li>
	                    <#list t as k>
		                    <li class="tag teamTag">
		                        <a href="go.action?groupId=${k.groupId}">${k.groupTitle!?html}</a>
		                    </li>
	                    </#list>
	                </ul>
	            </div>
            </#if>
            
            <#if best_group_list?? >
            <#assign best_3_group_list = best_group_list[1..3]>
	            <div class="team none">
	                <ul class="imgList teamImgList">
	                    <li>
	                        <a href="go.action?groupId=${g.groupId}" class="imgLi"><img width='116' height='116' src="${g.groupIcon}" /></a>
	                        <a href="go.action?groupId=${g.groupId}">${Util.getCountedWords(g.groupTitle!?html,14)}</a>
	                        <div class ="imgListBg">
	                           	<a class="imgLi" href="go.action?groupId=${g.groupId}"></a>
	                        </div>
	                    </li>
	                    <#list best_3_group_list as g >
		                    <li class="tag teamTag">
		                        <a href="go.action?groupId=${g.groupId}" title="${Util.getCountedWords(g.groupTitle!?html,15)}">${Util.getCountedWords(g.groupTitle!?html,5)}</a>
		                    </li>
	                    </#list>
	                </ul>
	            </div>
            </#if>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize1" /></div>
        </div>
        <!--推荐协作 End-->