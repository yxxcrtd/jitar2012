<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>视频频道</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script type="text/javascript">
    function getVideoList(categoryId){
         var url="videos.action?cmd=ajax&categoryId="+categoryId+"&t=" + (new Date()).getTime();
         $.ajax({url: url}).done(function(html) {
            $("#listshow").html(html);
            }); 
    }
  
    function resetSearchData(){
        $("#k").val("");
        $("#secInput2").val("");
    } 
</script>
</head>
<body>
<#include '/WEB-INF/ftl2/site_head.ftl'>
<!--正文 Start-->
<!--正文 Start-->
<div class="secMain mt25 clearfix" style="min-height:600px;  _height:600px;">
    <div class="left">
        <!--左侧导航 Start-->
        <div class="secLeftW border">
        <#include '/WEB-INF/ftl2/video/tree.ftl'>
        </div>
        <!--左侧导航 End-->
    </div>
    <!--页面右栏 Start-->
    <div class="right">
        <!--搜索 Start-->
        <div class="secRightW border">     
            <div class="secSearch">
                <form  method='POST' action="video_list.action" style="display:inline" id="searchForm">
                <input type="hidden" name="categoryId" value="${categoryId!}"/>
                <input type="hidden" name="type" value="search"/>
                <input type="hidden" name="f" id="fkey" value="${f!}"/>
                <b>关键词</b>
                <div class="secSearchBg secSearchBgW3">
                    <span class="secSearchBgR"></span>
                    <input type="text" class="secSearchInput" name="k" id="k" value="${k!?html}" placeholder="关键词" />
                </div>
                <b>搜索类别</b>
                <div class="secSearchBg secSearchBgW4">
                    <span class="secSearchBgR"></span>
                    <span class="arrow4 secSearchBgArrow"></span>
                    <input type="text" class="secSearchText" value="<#if f?? && f=='0'>标题名、标签名</#if><#if f?? && f=='1'>描述</#if><#if f?? && f=='2'>发布人</#if>" placeholder="搜索类别" name="f" id="secInput2" />
                    <div class="secSearchSelectWrap" id="secSelectWrap2">
                        <ul class="secSearchSelect">
                             <li value='0'><a href="javascript:void(0);" onclick="document.getElementById('fkey').value=0;">标题名、标签名</a></li>
                             <li value='1'><a href="javascript:void(0);" onclick="document.getElementById('fkey').value=1;">描述</a></li>
                             <li value='2'><a href="javascript:void(0);" onclick="document.getElementById('fkey').value=2;">发布人</a></li>  
                        </ul>
                    </div>
                </div>
                <b><a href="javascript:void(0);" onclick="document.getElementById('searchForm').submit();" class="secSearchBtn">搜索</a></b>
                </form>
            </div>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
        </div>
        <!--搜索 End-->
        <span id="listshow">
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
                                <img border=0 src="${p.flvThumbNailHref!}" width="240" height="170" onerror="this.src='${ContextPath}images/default_video.png'"/>
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
                          <#if p_index &gt; 5>
                                <#if p_index &lt; 15>
                                    <li>
                                        <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${p.videoId}"><img border=0 src="${p.flvThumbNailHref!}" width="140" height="90" onerror="this.src='${ContextPath}images/default_video.png'"/></a>
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
                                <img border=0 src="${p.flvThumbNailHref!}" width="240" height="170" onerror="this.src='${ContextPath}images/default_video.png'"/>
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
                                        <a href="#"><img border=0 src="${p.flvThumbNailHref!}" width="140" height="90" onerror="this.src='${ContextPath}images/default_video.png'"/></a>
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
        </span>
    </div>
    <!--页面右栏 End-->
</div>
<!--工作室 End-->
<#include '/WEB-INF/ftl2/footer.ftl'>
<!--[if IE 6]>
<script src="js/ie6.js" type="text/javascript"></script>
<script src="js/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
    DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder,.secList li,.secVideoPlay');
</script>
<![endif]-->
</body>
</html>
