<!--视频 start-->
<div class="leftWidth videoWrap border mt3">
  <h3 class="h3Head">
  	<a href="video_list.action?type=new" class="more">更多</a>
    <a href="video_list.action?type=hot" class="more none">更多</a>
    <a href="video_list.action?type=new" class="sectionTitle active">最新视频<span></span></a>
    <a href="video_list.action?type=hot" class="sectionTitle">最热视频<span></span></a>
  </h3>
  <div class="video">
  <#if new_video_list??>
      <ul>
         <#list new_video_list as v> 
           <li>
          <a  href="${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}" class="videoImg">
          	<img src="${v.flvThumbNailHref!?html}" onerror="this.src='images/default_video.png'" alt='${v.title!?html}'/>
          </a>
                <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}" title="${v.title!}">${Util.getCountedWords(v.title!?html,10)}</a>
                <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}" class="videoPlay"></a>
                <div class="videoPlayBg"></div>
            </li>
         </#list>
      </ul>
  </#if>
  </div>
  
  <div class="video none" id="video_show_value">
  	<#if hot_video_list??>
      <ul>
          <#list hot_video_list as v>
            <li>
                <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}" class="videoImg">
                    <img src="${v.flvThumbNailHref!?html}" onerror="this.src='images/default_video.png'" alt='${v.title!?html}' style="width:150px;height:120px;" />
                </a>
                <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}" title="${v.title!}">${Util.getCountedWords(v.title!?html,10)}</a>
                <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}" class="videoPlay"></a>
                <div class="videoPlayBg"></div>
            </li>
          </#list>
      </ul>
	</#if>
 </div>
 <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize2" /></div>
</div>
<!--视频 End-->