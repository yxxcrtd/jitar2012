<!--课题视频 Start-->
<div class="specialLeft border">
  <h3 class="h3Head textIn"><a href="ktgroups.action?act=all&type=video" class="more">更多</a>课题成果-视频</h3>
  <div class="specialList clearfix">
  <#if (video_list??) && (video_list?size > 0) >
    <ul class="ulList">
      <#list video_list as video >
        <li><a title="${video.title!?html}" href='${ContextPath}manage/video.action?cmd=show&videoId=${video.videoId}' target='_blank'>${Util.getCountedWords(video.title!?html,15,1)}</a></li>
      </#list>
    </ul>
  </#if>   
  </div>
  <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
</div>
<!--课题视频 End-->