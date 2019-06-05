<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - ${article.title?html}</title>
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
var articleId = ${article.articleId};
var objectId = articleId; //为了评论共用变量
var operateUrl = "${ContextPath}showArticle.action?articleId=${article.articleId}&t=" + (new Date()).getTime() + "&cmd=";
var pageUrl = "${ContextPath}showArticle.action?articleId=${article.articleId}";
</script>
</head>
<body>
<#include "/WEB-INF/ftl2/detailpage_head.ftl" />
<!--公用头部 End-->
<div class="main" id="Main">
  <!--详情页 Start-->
  <div class="content clearfix mt75">
      <!--详情页左侧 Start-->
      <div class="contentLeft">
        <!--标题-->
        <h1 title="${article.title!?html}">${Util.getCountedWords(article.title,40)}</h1>
        <div class="info">
          <div style="float:right"><div style="float:left;">分享到：</div>
          <div class="bshare-custom icon-medium" style="display:inline"><a title="分享到QQ空间" class="bshare-qzone"></a><a title="分享到新浪微博" class="bshare-sinaminiblog"></a><a title="分享到腾讯微博" class="bshare-qqmb"></a><a title="分享到网易微博" class="bshare-neteasemb"></a><a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a></div><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=&amp;pophcol=3&amp;lang=zh"></script><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
<#-- Baidu Button BEGIN
<div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare">
<a class="bds_qzone"></a>
<a class="bds_tqq"></a>
<a class="bds_tsina"></a>
<a class="bds_t163"></a>
<span class="bds_more"></span>
</div>
<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=6833853" ></script>
<script type="text/javascript" id="bdshell_js"></script>
<script type="text/javascript">
document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
</script>
Baidu Button END -->
          
          
          </div>
          <div style="float:left"><span class="from"><#if article.typeState == false>原创<#else>转载</#if></span>发布时间： ${article.createDate?string("yyyy-MM-dd HH:mm:ss")}</div>
        </div>
        <div style="clear:both"></div>       
        <div class="detail hardBreak" style="overflow:hidden">
        <#if canViewContent>
        <#if article.articleFormat?? && article.articleFormat == 1 && article.wordDownload && article.wordHref?? && article.wordHref?size &gt; 0>
        <#assign docHref=article.wordHref>
        <#assign swf=docHref?substring(0,docHref?last_index_of(".")) + ".swf">
        <script src="${ContextPath}js/flashpaper/flexpaper_flash.js?version1.1"></script>
            <a id="viewerPlaceHolder" style="width:100%;height:800px;display:block">您的浏览器不支持 Flash或者Flash版本太低。<span onclick="window.open('http://get.adobe.com/cn/flashplayer/','_blank')" style="cursor:pointer;">点击下载最新版本</span></a>
            <script>
            var fp = new FlexPaperViewer('${ContextPath}js/flashpaper/FlexPaperViewer',
                'viewerPlaceHolder', {
                    config : {
                      SwfFile : '${ContextPath}${swf}?' + (new Date()).valueOf(),
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
         <#else>
         ${article.articleContent}
         </#if>
         <#else>
         <div style="color:red;padding:20px 0;text-align:center">该文章只能登录用户才能查看内容，您还没有登录。</div>
         </#if>
        </div>        
        <div class="relation">
          <span class="relationNext">下一篇：<#if nextArticle??><a href="${ContextPath}showArticle.action?articleId=${nextArticle.articleId}" title="${nextArticle.title?html}">${Util.getCountedWords(nextArticle.title,20)}</a><#else>没有下一篇了</#if></span>
          <span class="relationPrev">上一篇：<#if prevArticle??><a href="${ContextPath}showArticle.action?articleId=${prevArticle.articleId}" title="${prevArticle.title?html}">${Util.getCountedWords(prevArticle.title,20)}</a><#else>没有上一篇了</#if></span>
        </div>
        
          <!--文档操作区 Start-->
          <#include "/WEB-INF/ftl2/article/operate.ftl" />
          <!--文档操作区 End-->
          <!--简介 Start-->
          <div class="summary mt13">
            <h4>文章摘要：</h4>
            <p class="mt10 hardBreak">
            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<#if (article.articleAbstract?? && 0 < article.articleAbstract?length)>
	            	${article.articleAbstract!}
	            <#else>
	            	该文章没有摘要！
            	</#if>
            </p>
          </div>
          <!--简介 End-->
          <#include "/WEB-INF/ftl2/article/comment.ftl" />
        </div>
        <!--详情页左侧 End-->
        <!--详情页右侧 Start-->
        <div class="contentRight">
          <!--头像-->
          <#include "/WEB-INF/ftl2/article/icon.ftl" />
            <!--头像End-->
            <!--相关标签-->
          <#include "/WEB-INF/ftl2/article/category.ftl" />
            <!--相关标签End-->
            <!--相关信息-->
            <!-- know文件 -->
            <!--相关信息End-->
            <!--热门文章排行-->
            <#include "/WEB-INF/ftl2/article/hot.ftl" />            
            <!--热门文章排行End-->
            <!--最新文章-->
            <#include "/WEB-INF/ftl2/article/new.ftl" />             
            <!--最新文章End-->
            <!--看过该文章的人-->
            <#include "/WEB-INF/ftl2/article/viewhistory.ftl" />
            <!--看过该文章的人End-->
        </div>
        <!--详情页右侧 End-->
    </div>
    <!--详情页 End-->
</div>

<!--公共尾部 Start-->
<#include '/WEB-INF/ftl2/footer.ftl'>
<!--公共尾部 End-->
<!--警告弹出　Start-->
<#include '/WEB-INF/ftl2/report.ftl'>
<!--警告弹出　End-->
<script src="${ContextPath}js/new/comment.js"></script>
<script>
//纠正大图显示
$(function(){
$(".detail img").each(function(){
  var w = parseInt($(this).css("width"),10);
   if(w > 665){
    $(this).removeAttr("width");
    $(this).removeAttr("height");
    $(this).css("width","665px");
    $(this).css("height","");
   }
  });
});
</script>
<#include "/WEB-INF/ftl2/common/ie6.ftl">
</body>
</html>