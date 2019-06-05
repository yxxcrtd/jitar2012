<div class="contentRelation">
<h3><a href="${ContextPath}video_list.action?type=hot" class="more">更多</a>热门视频排行</h3>
 <#if hot_video_list?? && hot_video_list?size &gt; 0 >
<#assign video = hot_video_list[0]>
<dl class="hotVideo">
    <dt><a href="${ContextPath}manage/video.action?cmd=show&videoId=${video.videoId}">
    <img border=0 src="${video.flvThumbNailHref!}" width="70" height="70"/>
    </a></dt>
    <dd>
        <p style="word-break: break-all;"><a href="${ContextPath}manage/video.action?cmd=show&videoId=${video.videoId}">${Util.getCountedWords(video.summary, 38, 1)}</a></p>
       <span></span>
    </dd>
</dl>
 <#if hot_video_list?size &gt; 1 >
  <#assign video = hot_video_list[1]>  
  <dl class="hotVideo">
        <dt><a href="${ContextPath}manage/video.action?cmd=show&videoId=${video.videoId}">
            <img border=0 src="${video.flvThumbNailHref!}" width="70" height="70"/>
            </a>
        </dt>
        <dd>
            <p style="word-break: break-all;"><a href="${ContextPath}manage/video.action?cmd=show&videoId=${video.videoId}">${Util.getCountedWords(video.summary, 38, 1)}</a></p>
            <span></span>
        </dd>
  </dl>        
 </#if>
 <#if hot_video_list?size &gt; 2 >
  <#assign video = hot_video_list[2]>  
  <dl class="hotVideo">
        <dt><a href="${ContextPath}manage/video.action?cmd=show&videoId=${video.videoId}">
        <img border=0 src="${video.flvThumbNailHref!}" width="70" height="70"/>
        </a></dt>
        <dd>
            <p style="word-break: break-all;"><a href="${ContextPath}manage/video.action?cmd=show&videoId=${video.videoId}">${Util.getCountedWords(video.summary, 35, 1)}</a></p>
            <span></span>
        </dd>
  </dl>        
 </#if>
 <#if hot_video_list?size &gt; 3 >
    <ul class="hotVideoNews">
        <#assign video = hot_video_list[3]>
        <li><a href="${ContextPath}manage/video.action?cmd=show&videoId=${video.videoId}">${Util.getCountedWords(video.title,14)}</a></li>
        <#if hot_video_list?size &gt; 4 >
            <#assign video = hot_video_list[4]>
            <li><a href="${ContextPath}manage/video.action?cmd=show&videoId=${video.videoId}">${Util.getCountedWords(video.title,14)}</a></li>
        </#if>
        <#if hot_video_list?size &gt; 5 >
            <#assign video = hot_video_list[5]>
            <li><a href="${ContextPath}manage/video.action?cmd=show&videoId=${video.videoId}">${Util.getCountedWords(video.title,14)}</a></li>
        </#if>
    </ul>
 </#if>
 </#if> 
</div>
