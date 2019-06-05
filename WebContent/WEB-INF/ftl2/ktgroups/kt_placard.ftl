 <!--课题公告 Start-->
<div class="specialLeft border">
    <h3 class="h3Head textIn"><a href="ktgroups.action?act=all&type=placard" class="more">更多</a>课题公告</h3>
    <div class="specialList clearfix">
    <#if (placard_list??) && (placard_list?size > 0) >
      <ul class="ulList">
        <#list placard_list as p >
          <li><em class="emDate">${p.createDate?string('yyyy/MM/dd')}</em><a title="${p.title!?html}" href="${ContextPath}showPlacard.action?placardId=${p.id!}">${Util.getCountedWords(p.title!?html,9,1)}</a></li>
        </#list>
      </ul>
    </#if>   
    </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
</div>
<!--课题公告 End-->