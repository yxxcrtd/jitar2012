<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - ${video.title?html}</title>
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/content.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jscroll.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/content.js" type="text/javascript"></script>
<script>
var JITAR_ROOT = "${ContextPath}";
var SSOServerUrl = "${SSOServerUrl}";
//公共对象
<#if loginUser??>
var loginUser = {userId: "${loginUser.userId}", userName:"${loginUser.trueName}", loginName:"${loginUser.loginName}",userIcon:"${SSOServerUrl +'upload/' + loginUser.userIcon!ContextPath+"images/default.gif"}"};
<#else>
var loginUser = {userId: "0", userName:"匿名用户", loginName:"",userIcon:"${ContextPath+"images/default.gif"}"};
</#if>
var videoId = ${video.videoId};
var objectId = videoId; //为了评论共用变量
var operateUrl = "video.action?videoId="+ videoId +"&t=" + (new Date()).getTime() + "&cmd=";
var pageUrl = "${ContextPath}manage/video.action?videoId=${video.videoId}";

function showCommentSummary(id)
{
 window.open("${SiteUrl}showCommentSummary.py?id="+id,"","left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1")
}
</script>
</head>
<body>
<#include "/WEB-INF/ftl2/detailpage_head.ftl" />
<div class="main">
    <!--详情页 Start-->
    <div class="content clearfix mt75">
        <!--详情页左侧 Start-->
        <div class="contentLeft">
            <h1 title="${video.title!?html}"><span class="uploadTime">上传时间： ${video.createDate?string('yyyy-MM-dd HH:mm')}</span>${Util.getCountedWords(video.title!?html,28)}</h1>
            <div class="detail">
            <#if error??>
              <div style="padding:200px;text-align:center;color:#f00;">${error}</div>
            <#else>
                <!--视频显示阅读区-->
                <object type="application/x-shockwave-flash" data="${SiteUrl}images/palyer.swf" width="664" height="498" id="v3" zIndex="-1">
                    <param name="movie" value="${SiteUrl}images/palyer.swf"/> 
                    <param name="allowFullScreen" value="true" />
                    <param name="wmode" value="opaque" /> 
                    <param name="FlashVars" value="xml=
                    <vcastr>
                        <channel>
                            <item>
                                <source>${video.flvHref?lower_case?html}</source>
                                <title>${video.name!}</title>
                            </item>
                        </channel>
                    </vcastr>
                    "/>
                </object>
              </#if>               
            </div>
            <!--文档操作区 Start-->
            <#include "/WEB-INF/ftl2/video/operate.ftl" />
            <!--文档操作区 End-->
            <!--评论 Start-->
            <#include "/WEB-INF/ftl2/video/comment.ftl" />
            <!--评论 End-->
        </div>
        <!--详情页左侧 End-->
        <!--详情页右侧 Start-->
        <div class="contentRight">
            <!--头像-->
            <#include "/WEB-INF/ftl2/video/icon.ftl" />
            <!--头像End-->
            <!--相关标签-->
            <#include "/WEB-INF/ftl2/video/category.ftl" />
            <!--相关标签End-->
            <!--热门视频排行-->
            <#include "/WEB-INF/ftl2/video/hot.ftl" />
            <!--热门视频排行End-->
            <!--最新视频-->
            <#include "/WEB-INF/ftl2/video/new.ftl" />
            <!--最新视频End-->
            <!--看过该视频的人-->
            <#include "/WEB-INF/ftl2/video/viewhistory.ftl" />
            <!--看过该视频的人End-->
        </div>
        <!--详情页右侧 End-->
    </div>
    <!--详情页 End-->
</div>

<!--公共尾部 Start-->
<#include '/WEB-INF/ftl2/footer.ftl'>
<#include '/WEB-INF/ftl2/report.ftl'>
<!--公共尾部 End-->
<script src="${ContextPath}js/new/comment.js"></script>

<!--[if IE 6]>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
  DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder,.coopTag,.comma1,.comma2,.specialAdd');
</script>
<![endif]-->
</body>
</html>