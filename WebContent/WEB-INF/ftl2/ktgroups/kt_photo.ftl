<!--课题图片 Start-->
<div class="specialLeft border">
    <h3 class="h3Head textIn"><a href="ktgroups.action?act=all&type=photo" class="more">更多</a>课题成果-图片</h3>
    <div class="specialList clearfix">
    <#if (photo_list??) && (photo_list?size > 0) >
      <ul class="ulList">
        <#list photo_list as photo >
          <li><a title="${photo.title!?html}" href='${ContextPath}photos.action?cmd=detail&photoId=${photo.photoId}' target='_blank'>${Util.getCountedWords(photo.title!?html,15,1)}</a></li>
        </#list>
      </ul>
    </#if>   
    </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
</div>
<!--课题图片 End-->