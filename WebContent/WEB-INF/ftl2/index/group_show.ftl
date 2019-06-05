 <!--推荐协作 start-->
<div class="rightWidth teamWrap border">
    <h3 class="h3Head">
        <a href="groups.action?type=rcmd" class="more">更多</a>
        <a href="groups.action?type=best" class="more none">更多</a>
        <a href="groups.action?type=rcmd" class="sectionTitle active">推荐协作组<span></span></a>
        <a href="groups.action?type=best" class="sectionTitle">优秀团队<span></span></a>
    </h3>
      
      <div class="team">
      <#if (rcmd_group_list??) && (rcmd_group_list?size > 0) >
      <#assign g = rcmd_group_list[0] >
          <ul class="imgList teamImgList">
              <li>
                  <a href="go.action?groupId=${g.groupId}" class="imgLi"><img width='116' height='116' src="${Util.url(g.groupIcon!'images/group_default.gif')}" onerror="this.src='images/group_default.gif'"/></a>
                  <a href="go.action?groupId=${g.groupId}" title="${g.groupTitle!}">${Util.getCountedWords(g.groupTitle!?html,7)}</a>
                  <div class ="imgListBg">
                     	<a class="imgLi" href="go.action?groupId=${g.groupId}"></a>
                  </div>
              </li>
              <#list rcmd_group_list as k>
               <#if k_index &lt; 4 && k_index &gt;0>
	                <li class="tag teamTag">
	                    <a href="go.action?groupId=${k.groupId}" title="${k.groupTitle!}">${Util.getCountedWords(k.groupTitle!?html,7)}</a>
	                </li>
               </#if>
              </#list>
          </ul>
      </#if>
      </div>
    
      <div class="team none">
      <#if best_group_list?? &&(best_group_list?size>0)>
      <#assign g = best_group_list[0] >
          <ul class="imgList teamImgList">
              <li>
                  <a href="go.action?groupId=${g.groupId}" class="imgLi"><img width='116' height='116' src="${g.groupIcon}" /></a>
                  <a href="go.action?groupId=${g.groupId}" title="${g.groupTitle!}">${Util.getCountedWords(g.groupTitle!?html,7)}</a>
                  <div class ="imgListBg">
                     	<a class="imgLi" href="go.action?groupId=${g.groupId}"></a>
                  </div>
              </li>
              <#list best_group_list as g >
	               <#if g_index &gt; 0 && g_index &lt; 4>
	                <li class="tag teamTag">
	                    <a href="go.action?groupId=${g.groupId}" title="${g.groupTitle!}">${Util.getCountedWords(g.groupTitle!?html,5)}</a>
	                </li>
               </#if>
              </#list>
          </ul>
      </#if>
      </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize1" /></div>
</div>
<!--推荐协作 End-->