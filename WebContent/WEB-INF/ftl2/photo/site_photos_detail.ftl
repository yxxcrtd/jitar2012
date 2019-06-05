<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - ${photo.title!""}</title>
<#include "/WEB-INF/ftl2/common/favicon.ftl" />
<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
<link rel="stylesheet" href="${SiteThemeUrl}css/content.css" type="text/css">
<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/jquery.sgallery.js" type="text/javascript"></script>
<script src="${ContextPath}js/new/show_pictureCont.js" type="text/javascript"></script>
<script>
	var JITAR_ROOT = "${ContextPath}";
	var SSOServerUrl = "${SSOServerUrl}";
	//公共对象
	<#if loginUser??>
	var loginUser = {userId: "${loginUser.userId}", userName:"${loginUser.trueName}", loginName:"${loginUser.loginName}",userIcon:"${SSOServerUrl +'upload/' + loginUser.userIcon!ContextPath+"images/default.gif"}"};
	<#else>
	var loginUser = {userId: "0", userName:"匿名用户", loginName:"",userIcon:"${ContextPath+"images/default.gif"}"};
	</#if>
	var objectId = ${photo.photoId}; //为了评论共用变量
	var operateUrl = "photos.action?photoId=${photo.photoId}&t=" + (new Date()).getTime() + "&cmd=";
	var pageUrl = "${ContextPath}photos.action?photoId="+${photo.photoId};
</script>

</head>
<body>
<#include "/WEB-INF/ftl2/detailpage_head.ftl" />
<!--公用头部 End-->
<div class="secMain">
<!--详情页 Start-->
  <div class="content clearfix mt75">
   <div id="nowPhotoId"></div>
      <!--详情页左侧 Start-->
    	<div class="contentLeft">
	        	<h1><span class="uploadTime" id="upTime">上传时间:${photo.createDate!?string('yyyy-MM-dd HH:mm')}</span><span id="imgTit">${Util.getCountedWords(photo.title!?html,20)}</span></h1>
	            <div class="imageContent">
	            	<div class="imageBig">
	                    <div class="imageBigimg"  id="big-pic">
	                    </div>
	                    <div class="photoPrev">
	                        <a class="photoPBtn"  onclick="showpic('pre')"></a>
	                    </div>
	                    <div class="photoNext">
	                        <a href="javascript:;" class="photoNBtn photoNBtn_on" onclick="showpic('next')"></a>
	                    </div>
	                </div>
	                <div class="photoBtnWrap">
	                    <div class="photoBtn">
	                        <a href="javascript:;" class="photoBtnLeft"  onclick="showpic('pre')" ></a>
	                        <div class="photoBtnList img_c">
	                            <ul class="img_ul" id="pictureurls">
	                                 <li photoId = "${photo.photoId!}" photoTitle="${Util.getCountedWords(photo.title!?html,20,1)}" photoTime="${photo.createDate?string('yyyy-MM-dd HH:mm')}" class="on"><img src="${Util.GetimgUrl(photo.href!,'70x70')}" rel="${Util.GetimgUrl(photo.href!,'665x500')}" onerror="javascript:this.src='images/s11_default.jpg'"/><div class="imgBorder"></div></li>
	                              <#list photo_details as photo_detail>  <!--幻灯片图片列表,这里把图片的标题和图片的创建时间放到li元素中后面写js取-->
	                                 <li photoId = "${photo_detail.photoId!}" photoTitle="${Util.getCountedWords(photo_detail.title!?html,20,1)}" photoTime="${photo_detail.createDate?string('yyyy-MM-dd HH:mm')}"><img src="${Util.GetimgUrl(photo_detail.href!,'70x70')}" rel="${Util.GetimgUrl(photo_detail.href!,'665x500')}" onerror="javascript:this.src='images/s11_default.jpg'"/><div class="imgBorder"></div></li>
	                              </#list>  
	                            </ul>
	                        </div>
	                        <a href="javascript:;" class="photoBtnRight photoBtnRight_on"  onclick="showpic('next')"></a>
	                    </div>
	                </div>
	                <div id="num"></div>
	            </div>
            <#include 'operate.ftl'>
            <!--文档操作区 End-->
            <!--简介 Start-->
            <#if photo.summary?? && photo.summary?size &gt; 0>
            <div id="summary_introduce" class="summary mt13">
            	<h4>图片说明：</h4>
        	    <p class="summaryCont mt10">${Util.getCountedWords(photo.summary!?html,120,1)}</p>
        	    <#if (photo.summary?size &gt; 120) >
	                <p class="summaryMore">
	                	<a href="javascript:;" class="summaryBtn">查看全部说明</a>
	                	<a href="javascript:;" class="summaryBtn1">收起说明</a>
	                </p>
                </#if>
            </div>
            </#if>
            
            <!--简介 End-->
            <!--评论-->
            <#include 'comment.ftl'>
            <!--评论 End-->
        </div>
        <!--详情页左侧 End-->
        <!--详情页右侧 Start-->
        <div class="contentRight">
        	<!--头像-->
        	<#include 'icon.ftl'>
            <!--头像End-->
            <!--相关标签-->
            <#include 'category.ftl'>
            <!--相关标签End-->
            <!--热门图片-->
            <#include 'hot.ftl'>
            <!--热门图片End-->
            <!--最新图片-->
            <#include 'new.ftl'>
            <!--最新图片End-->
            <!--看过该图片的人-->
            <#include 'viewhistory.ftl'> 
            <!--看过该图片的人End-->
        </div>
        <!--详情页右侧 End-->
  </div>
</div>
</div>
<#include '/WEB-INF/ftl2/footer.ftl'>
<!--公共尾部 End-->
<!--警告弹出　Start-->
<#include '/WEB-INF/ftl2/report.ftl'>
<script>
var isHacked = false;//IE11检查
try{
isHacked = '-ms-scroll-limit' in document.documentElement.style && '-ms-ime-align' in document.documentElement.style;
}catch(e){}
if(isHacked || navigator.userAgent.indexOf("MSIE")> -1){
  $(function(){
  $(".photoPBtn_on").css("cursor","url('${SiteThemeUrl}images/cursor_left.cur'), pointer");
  $(".photoNBtn_on").css("cursor","url('${SiteThemeUrl}images/cursor_right.cur'), pointer");
  });
}
</script>
<script src="${ContextPath}js/new/comment_photo.js" type="text/javascript"></script>
<!--警告弹出　End-->
<!--[if IE 6]>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
  DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder,.coopTag,.comma1,.comma2,.specialAdd');
</script>
<![endif]-->
</body>
</html>