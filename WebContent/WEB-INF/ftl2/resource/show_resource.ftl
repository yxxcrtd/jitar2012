<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - ${resource.title!?html}</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
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
var resourceId = ${resource.resourceId};
var objectId = resourceId; //为了评论共用变量
var operateUrl = "showResource.action?resourceId="+ resourceId +"&t=" + (new Date()).getTime() + "&cmd=";
var pageUrl = "${ContextPath}showResource.action?resourceId=${resource.resourceId}";
</script>
</head>
<body>
<#include "/WEB-INF/ftl2/detailpage_head.ftl" />
<div class="main">
    <!--详情页 Start-->
    <div class="content clearfix mt75">
        <!--详情页左侧 Start-->
        <div class="contentLeft">
            <h1 title="${resource.title!?html}"<#if error??> style="border-bottom:1px solid #dbdbdb"</#if>><span class="uploadTime">上传时间： ${resource.createDate?string('yyyy-MM-dd HH:mm')}</span>${Util.getCountedWords(resource.title!?html,28)}</h1>
            
            <div class="detail" style="min-height:256px">
            <#if error??>
              <div style="color:#3f7496;padding:100px 10px;font-size:16px;line-height:33px;">${error}</div>
            <#else>
              <#if resType??>
                <#if resType == "image">
                <img id="showImage" src="${resourceFile}" style="max-width:666px" onload="resizeImage()" />
                <#elseif resType == "rar">
                  <div class="rarCont">
                    <div class="rar"></div>
                  </div>
                <#elseif resType == "zip">
                  <div class="rarCont">
                    <div class="zip"></div>
                  </div>
                <#elseif resType == "text">
                  ${content!?html}
                <#elseif resType == "swf">
                  <#if orginIsSwf??>
                  <embed src='${resourceFile}' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width="100%" height="480"></embed>
                  <br/><a href="${resourceFile}" target="_blank">打开新浏览器窗口播放</a>
                  <#else>
                  <script type="text/javascript" src="${ContextPath}js/flashpaper/flexpaper_flash.js"></script>
                  <a id="viewerPlaceHolder" style="width:100%;height:510px;display:block">您的浏览器不支持 Flash或者Flash版本太低。<span onclick="window.open('http://get.adobe.com/cn/flashplayer/','_blank')" style="cursor:pointer;">点击下载最新版本</span></a>
                  
                  <script type="text/javascript">
                  var fp = new FlexPaperViewer('${ContextPath}js/flashpaper/FlexPaperViewer',
                    'viewerPlaceHolder', {
                      config : {
                        SwfFile : '${resourceFile}?' + (new Date()).valueOf(),
                        Scale : 0.8, 
                        ZoomTransition : "easeOut",
                        ZoomTime : 0.5,
                        ZoomInterval : 0.1,
                        FitPageOnLoad : false,
                        FitWidthOnLoad : true,
                        PrintEnabled : true,
                        FullScreenAsMaxWindow : false,
                        ProgressiveLoading : true,
                        
                        PrintToolsVisible : true,
                        ViewModeToolsVisible : true,
                        ZoomToolsVisible : true,
                        FullScreenVisible : true,
                        NavToolsVisible : true,
                        CursorToolsVisible : true,
                        SearchToolsVisible : true,
                        localeChain : 'zh_CN'
                      }
                    });
                  </script>
                 </#if>
                 <#else>
                 <div style="color:#3f7496;padding:100px 10px;font-size:16px;line-height:33px;">该资源属于不可预览的资源，请下载后观看。</div>
                </#if>
              <#else>
                <div style="color:#3f7496;padding:100px 10px;font-size:16px;line-height:33px;">该资源属于不可预览的资源，请下载后观看。</div>
              </#if>
            </#if>
            </div>
              <!--文档操作区 Start-->
              <#include "/WEB-INF/ftl2/resource/operate.ftl" />
              <!--文档操作区 End-->
            <!--评论 Start-->
            <#include "/WEB-INF/ftl2/resource/comment.ftl" />
            <!--评论 End-->
        </div>
        <!--详情页左侧 End-->
        <!--详情页右侧 Start-->
        <div class="contentRight">
            <!--头像-->
            <#include "/WEB-INF/ftl2/resource/icon.ftl" />
            <!--头像End-->
            <!--相关标签-->
            <#include "/WEB-INF/ftl2/resource/category.ftl" />
            <!--相关标签End-->
            <!--热门资源排行-->
            <#include "/WEB-INF/ftl2/resource/hot.ftl" />
            <!--热门资源排行End-->
            <!--最新资源-->
            <#include "/WEB-INF/ftl2/resource/new.ftl" />
            <!--最新资源End-->
            <!--看过该资源的人-->
            <#include "/WEB-INF/ftl2/resource/viewhistory.ftl" />
            <!--看过该资源的人End-->
        </div>
        <!--详情页右侧 End-->
    </div>
    <!--详情页 End-->
</div>

<!--公共尾部 Start-->
<#include '/WEB-INF/ftl2/footer.ftl'>
<#include '/WEB-INF/ftl2/report.ftl'>
<script>
function resizeImage(){
  if($("#showImage").width() > 666){
  $("#showImage").css("width","666px");
  }
}
$(resizeImage);
</script>
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