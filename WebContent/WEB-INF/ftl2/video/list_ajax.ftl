<!--最热视频 Start-->
<div class="secRightW border mt3">
    <h3 class="h3Head textIn"><a href="video_list.action?type=hot&categoryId=${categoryId!}" class="more">更多</a>最热视频</h3>
    <div class="secVideo clearfix">
        <div class="secVideoLeft">
            <div class="secVideoBigImg">
                <#if hot_video_list??>
                    <#if hot_video_list.size() &gt; 0 >
                        <#assign p = hot_video_list[0]>
                        <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}">
                        <img border=0 src="${p.flvThumbNailHref!?html}" width="240" height="170" onerror="this.src='${ContextPath}images/default_video.png'"/>
                        </a>
                        <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}">${Util.getCountedWords(p.title!?html,13,1)}</a>
                    </#if>
                </#if>
            </div>
            <ul class="ulList secList">
                <#if hot_video_list??>
                    <#if hot_video_list.size() &gt; 1 >
                        <#assign p = hot_video_list[1]>
                        <li><a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}">${Util.getCountedWords(p.title!?html,12,1)}</a></li>
                    </#if>
                    <#if hot_video_list.size() &gt; 2 >
                        <#assign p = hot_video_list[2]>
                        <li><a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}">${Util.getCountedWords(p.title!?html,12,1)}</a></li>
                    </#if>
                    <#if hot_video_list.size() &gt; 3 >
                        <#assign p = hot_video_list[3]>
                        <li><a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}">${Util.getCountedWords(p.title!?html,12,1)}</a></li>
                    </#if>
                    <#if hot_video_list.size() &gt; 4 >
                        <#assign p = hot_video_list[4]>
                        <li><a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}">${Util.getCountedWords(p.title!?html,12,1)}</a></li>
                    </#if>
                    <#if hot_video_list.size() &gt; 5 >
                        <#assign p = hot_video_list[5]>
                        <li><a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}">${Util.getCountedWords(p.title!?html,12,1)}</a></li>
                    </#if>
                </#if>
            </ul>
        </div>
        <div class="secVideoRight">
            <ul class="secVideoList">
                <#if hot_video_list??>
                  <#list hot_video_list as p>
                  <#if p_index &gt; 6>
                        <#if p_index &lt; 15>
                            <li>
                                <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}"><img border=0 src="${p.flvThumbNailHref!?html}" width="140" height="90" onerror="this.src='${ContextPath}images/default_video.png'"/></a>
                                <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}">${Util.getCountedWords(p.title!?html,9,1)}</a></a>
                                <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}" class="secVideoPlay"></a>
                                <div class="secVideoBg"></div>
                            </li>
                        </#if>
                  </#if>
                  </#list>
                </#if>
            </ul>
        </div>
    </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
</div>
<!--最热视频 End-->
<!--最新视频 Start-->
<div class="secRightW border mt3">
    <h3 class="h3Head textIn"><a href="video_list.action?type=new&categoryId=${categoryId!}" class="more">更多</a>最新视频</h3>
    <div class="secVideo clearfix">
        <div class="secVideoLeft">
            <div class="secVideoBigImg secVideoHot">
                <#if new_video_list??>
                    <#if new_video_list.size() &gt; 0 >
                        <#assign p = new_video_list[0]>
                        <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}">
                        <img border=0 src="${p.flvThumbNailHref!?html}" width="240" height="170" onerror="this.src='${ContextPath}images/default_video.png'"/>
                        </a>
                        <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}">${Util.getCountedWords(p.title!?html,13,1)}</a>
                        <p>上传: ${p.trueName}&nbsp;${p.createDate?string('yyyy/MM/dd')}</p>
                    </#if>
                </#if>
            </div>
        </div>
        <div class="secVideoRight">
            <ul class="secVideoList">
                <#if new_video_list??>
                  <#list new_video_list as p>
                  <#if p_index &gt; 0>
                        <#if p_index &lt; 7>
                            <li>
                                <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}"><img border=0 src="${p.flvThumbNailHref!?html}" width="140" height="90" onerror="this.src='${ContextPath}images/default_video.png'"/></a>
                                <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}">${Util.getCountedWords(p.title!?html,9,1)}</a></a>
                                <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}" class="secVideoPlay"></a>
                                <div class="secVideoBg"></div>
                            </li>
                        </#if>
                  </#if>
                  </#list>
                </#if>

            </ul>
        </div>
    </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
</div>
<!--最新视频 End-->