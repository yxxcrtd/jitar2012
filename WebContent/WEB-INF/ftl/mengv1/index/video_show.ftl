<!--视频 start-->
        <div class="leftWidth videoWrap border mt3">
            <h3 class="h3Head">
                <a href="videos.action?type=hot" class="more">更多</a>
                <a href="videos.action?type=new" class="more none">更多</a>
                <a href="videos.action?type=hot" class="sectionTitle active">最热视频<span></span></a>
                <a href="videos.action?type=new" class="sectionTitle">最新视频<span></span></a>
            </h3>
            <#if hot_video_list??>
	            <div class="video" id="video_show_value">
	                <ul>
	                    <#list hot_video_list as v>
	                       <input type="hidden" id="video${v_index}" value="${v.flvThumbNailHref!?html}"/>
		                    <li>
		                        <a id="addVideo${v_index}" href="${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}" class="videoImg">
		                            <!--<img src="${SiteUrl}manage/showImage?flvThumbNailHref=${v.flvThumbNailHref!?html}" alt='${v.title!?html}'/>-->
		                            <script type="text/javascript">
		                            	$.setImgAddr(${v_index},'video','addVideo','video');<!--第一个参数下标-->
		                            </script>
		                        </a>
		                        <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}">${Util.getCountedWords(v.title!?html,10)}</a>
		                        <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}" class="videoPlay"></a>
		                        <div class="videoPlayBg"></div>
		                    </li>
	                    </#list>
	                </ul>
	            </div>
            </#if>
            
            <#if new_video_list??>
	            <div class="video none">
	                <ul>
	                   <#list new_video_list as v> 
	                   <input type="hidden" id="video_new${v_index}" value="${v.flvThumbNailHref!?html}"/>
		                   <li>
					            <a id="addVideo_new${v_index}" href="${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}" class="videoImg">
					            	<!--<img src="${SiteUrl}manage/showImage?flvThumbNailHref=${v.flvThumbNailHref!?html}" alt='${v.title!?html}'/>-->
					            	<script type="text/javascript">
		                            	$.setImgAddr(${v_index},'video_new','addVideo_new','video');<!--第一个参数下标-->
		                            </script>
					            </a>
		                        <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}">${Util.getCountedWords(v.title!?html,10)}</a>
		                        <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${v.videoId}" class="videoPlay"></a>
		                        <div class="videoPlayBg"></div>
		                    </li>
	                   </#list>
	                </ul>
	            </div>
            </#if>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize2" /></div>
        </div>
<!--视频 End-->