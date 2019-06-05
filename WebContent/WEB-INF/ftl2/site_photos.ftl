<!doctype html>
<html>
<head>
<meta charset="utf-8">
  <title><#include '/WEB-INF/ftl2/common/site_title.ftl' /> - 图片</title>
  <#include "/WEB-INF/ftl2/common/favicon.ftl" />
	<link rel="stylesheet" href="${SiteThemeUrl}css/common.css" type="text/css">
	<link rel="stylesheet" href="${SiteThemeUrl}css/section.css" type="text/css">
	<script src="${ContextPath}js/new/jquery.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/index.js" type="text/javascript"></script>
	<script src="${ContextPath}js/new/show_photo.js" type="text/javascript"></script>
</head>
 <body>
<#include "site_head.ftl" />
<!--图片第一屏 Start-->
<div class="secMain mt25 clearfix">
    <div class="left secRightW border">
	    	<div class="imageScroll">
	    	  <#if new_photo_list??&&(new_photo_list?size &gt; 0)>
	    	    <#assign photo = new_photo_list[0]>
	        	<h1><span class="uploadTime" id="wUpTime">上传者:${photo.loginName}&nbsp;&nbsp;&nbsp;&nbsp;上传时间:${photo.createDate?string('yyyy-MM-dd HH:mm')}</span><span id="wImgTit">${Util.getCountedWords(photo.title!?html,20)}</span></h1>
	            <div class="imgBig">
		            <div class="imageScrollCont"  id="big-pic"> <!--默认显示第一张图-->
		               <img src=${Util.GetimgUrl(Util.url(photo.href!),'690x400')}  onerror="javascript:this.src='images/s6_default.jpg'"/>
		            </div>
		            <div class="photoPrev">
	                        <a href="javascript:;" class="photoPBtn photoPBtn_on" onclick="showpic('pre')"></a>
	                </div>
	                <div class="photoNext">
	                        <a href="javascript:;" class="photoNBtn photoNBtn_on" onclick="showpic('next')"></a>
	                </div>
	            </div>
	            <div class="imageScrollBtn">
	            	<a href="javascript:;" class="imageBtnLeft" onclick="showpic('pre')"></a>
	                <div class="imageBtnList img_c">
		                  <ul class="img_ul" id="pictureurls">
		                	  <#list new_photo_list as photo> <!--on鼠标放上去边框变蓝-->
		                    	<li photoTitle="${Util.getCountedWords(photo.title!?html,20,1)}" photoLogin = "上传者:${photo.loginName}&nbsp;&nbsp;&nbsp;&nbsp;${photo.createDate?string('yyyy-MM-dd HH:mm')}">
	                                   <a>
	                                    <img src=${Util.GetimgUrl(Util.url(photo.href!),'70x70')}  rel=${Util.GetimgUrl(Util.url(photo.href!),'690x400')} rela="photos.action?cmd=detail&photoId=${photo.photoId}" onerror="javascript:this.src='images/s11_default.jpg'"/>
	                                   </a>
			                    	  <div class="imgBg"/>
		                    	</li>
		                     </#list>
		                  </ul>
	                </div>
	                <a href="javascript:;" class="imageBtnRight imageBtnRight_on" onclick="showpic('next')"></a>
	            </div>
	              </#if>
	        </div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
    </div>
    
	    <div class="right secLeftW border">
	    	<h3 class="h3Head textIn">图片排行</h3>
	    	<#if hot_photo_list??&&(hot_photo_list?size &gt; 0)>
	        <div class="imageTop">
	        	<ul class="ulList">
	        	   <#list hot_photo_list as ph >
	        	      <#if ph_index < 3>
		            	<li><span class="numIcon numOrange">${ph_index + 1}</span><a href='photos.action?cmd=detail&photoId=${ph.photoId}' title="${ph.title!}"  target="_blank">${Util.getCountedWords(ph.title!?html,11)}</a></li>
		              <#elseif (20 &gt; ph_index && ph_index &gt; 2)>
		                <li><span class="numIcon">${ph_index + 1}</span><a  href='photos.action?cmd=detail&photoId=${ph.photoId}' title="${ph.title!}" target="_blank">${Util.getCountedWords(ph.title!?html,11)}</a></li>
		              </#if>
	               </#list>
	            </ul>
	        </div>
	        </#if>
	        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
	    </div>
</div>
<#if photo_cates??&&(photo_cates?size &gt; 0)>
  <#list photo_cates as pc >
   <#if (pc_index%3==0)>
		<div class="main clearfix mt3">
			<div class="imageSecond border">
		        <h3 class="h3Head textIn"><a href='showPhotoList.action?categoryId=${pc.categoryId}' target="_blank" class="more">更多</a>${pc.categoryName!?html}分类图片</h3>
		        <#if pc.photo_list?? && pc.photo_list?size &gt; 0>
	            <#assign photo_list = pc["photo_list"]>
			        <div class="imageShow clearfix">
				          <#list photo_list as p>
				              <#if p_index==0>
				                 <div class="imageShowC mr14">
					                <div class="imageShowC1">
					                    <a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" >
					                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x250')}  onerror="javascript:this.src='images/s7_default.jpg'"/>
					                       <!--<img src="skin/default/images/demoimg/230_250.jpg" />-->
					                    </a>
					                    <p class="imageShowBg"></p>
					                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}">${Util.getCountedWords(p.title!?html,15)}</a></p>
					                </div>
					            </div>
				              </#if>
				              <#if p_index==1||p_index==2>
						            <#if p_index==1>
						                <div class="imageShowC mr14">
								                <div class="imageShowC2">
								                    <a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank"  >
								                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x136')}  onerror="javascript:this.src='images/s8_default.jpg'"/>
								                       <!--<img src="skin/default/images/demoimg/230_136.jpg" />-->
								                    </a>
								                    <p class="imageShowBg"></p>
								                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}" >${Util.getCountedWords(p.title!?html,15)}</a></p>
								                </div>
						               </#if>
						               <#if p_index==2>
							                <div class="imageShowC3 mt14">
							                    <a  href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" >
							                    	<img src=${Util.GetimgUrl(Util.url(p.href!),'230x100')}  onerror="javascript:this.src='images/s9_default.jpg'"/>
							                    	<!--<img src="skin/default/images/demoimg/230_100.jpg" />-->
							                    </a>
							                    <p class="imageShowBg"></p>
							                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}">${Util.getCountedWords(p.title!?html,15)}</a></p>
							                </div>
							              </div>
						               </#if>
				              </#if>
						       <#if p_index ==3>
						            <div class="imageShowC mr14">
						                <div class="imageShowC1">
						                    <a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" >
						                    	<img src=${Util.GetimgUrl(Util.url(p.href!),'230x250')}  onerror="javascript:this.src='images/s7_default.jpg'"/>
						                    	<!--<img src="skin/default/images/demoimg/230_250.jpg" />-->
						                    </a>
						                    <p class="imageShowBg"></p>
						                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank"  title="${p.title!}">${Util.getCountedWords(p.title!?html,15)}</a></p>
						                </div>
						            </div>
						        </#if>
						        <#if p_index==4||p_index==5>
					                <#if p_index == 4>
					                  <div class="imageShowC">
						                <div class="imageShowC3">
						                    <a  href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" >
						                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x100')}  onerror="javascript:this.src='images/s9_default.jpg'"/>
						                       <!--<img src="skin/default/images/demoimg/230_100.jpg" />-->
						                    </a>
						                    <p class="imageShowBg"></p>
						                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}" >${Util.getCountedWords(p.title!?html,15)}</a></p>
						                </div>
					                </#if>
					                <#if p_index == 5>
						                <div class="imageShowC2 mt14">
						                    <a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" >
						                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x136')}  onerror="javascript:this.src='images/s8_default.jpg'"/>
						                       <!--<img src="skin/default/images/demoimg/230_136.jpg" />-->
						                    </a>
						                    <p class="imageShowBg"></p>
						                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}">${Util.getCountedWords(p.title!?html,15)}</a></p>
						                </div>
						             </div>
					               </#if>
						        </#if>
				           </#list>
			         </div>
		           <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
		      </#if>
		    </div>
		 </div>
    <#elseif (pc_index%3==1)>
         <div class="main clearfix mt3">
			<div class="imageSecond border">
		        <h3 class="h3Head textIn"><a href='showPhotoList.action?categoryId=${pc.categoryId}' target="_blank"  class="more">更多</a>${pc.categoryName!?html}分类图片</h3>
		        <#if pc.photo_list?? && pc.photo_list?size &gt; 0 >
	            <#assign photo_list = pc["photo_list"]>
			        <div class="imageShow clearfix">
				          <#list photo_list as p>
					         <#if p_index==0>
					                <div class="imageShowC mr14">
						                <div class="imageShowC2">
						                    <a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank"  >
						                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x136')}  onerror="javascript:this.src='images/s8_default.jpg'"/>
						                       <!--<img src="skin/default/images/demoimg/230_250.jpg" />-->
						                    </a>
						                    <p class="imageShowBg"></p>
						                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}" >${Util.getCountedWords(p.title!?html,15)}</a></p>
						                </div>
				                <div class="imageShowC3 mt14">
				                    <ul class="ulList secList">
					            </#if>
					            
		                        <#if (p_index &gt; 0 && p_index &lt;4)>
		                    	   <li><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}">${Util.getCountedWords(p.title!?html,15)}</a></li>
		                        </#if>
				                <#if p_index == 4>
				                </ul>
			                   </div>
					        </div>
					        </#if>
					             <#if p_index==5||p_index==6>
					                 <div class="imageShowC mr14">
						                <div class="imageShowC1">
						                    <a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank"  >
						                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x250')}  onerror="javascript:this.src='images/s7_default.jpg'"/>
						                       <!--<img src="skin/default/images/demoimg/230_250.jpg" />-->
						                    </a>
						                    <p class="imageShowBg"></p>
						                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}">${Util.getCountedWords(p.title!?html,15)}</a></p>
						                </div>
						            </div>
					            </#if>
					            <#if p_index == 7>
					                 <div class="imageShowC">
						                <div class="imageShowC1">
						                    <a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank"  >
						                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x250')}  onerror="javascript:this.src='images/s7_default.jpg'"/>
						                       <!--<img src="skin/default/images/demoimg/230_250.jpg" />-->
						                    </a>
						                    <p class="imageShowBg"></p>
						                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}">${Util.getCountedWords(p.title!?html,15)}</a></p>
						                </div>
						            </div>
					            </#if>
				           </#list>
			         </div>
		           <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
		      </#if>
		    </div>
		 </div>
    <#else>
        <div class="main clearfix mt3">
			<div class="imageSecond border">
		        <h3 class="h3Head textIn"><a href='showPhotoList.action?categoryId=${pc.categoryId}' target="_blank" class="more">更多</a>${pc.categoryName!?html}分类图片</h3>
		        <#if pc.photo_list?? && pc.photo_list?size &gt; 0>
	            <#assign photo_list = pc["photo_list"]>
			        <div class="imageShow clearfix">
				          <#list photo_list as p>
				              <#if p_index==0||p_index==1>
						            <#if p_index==0>
						                <div class="imageShowC mr14">
								                <div class="imageShowC2">
								                    <a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank"  >
								                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x136')}  onerror="javascript:this.src='images/s8_default.jpg'"/>
								                       <!--<img src="skin/default/images/demoimg/230_136.jpg" />-->
								                    </a>
								                    <p class="imageShowBg"></p>
								                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}">${Util.getCountedWords(p.title!?html,15)}</a></p>
								                </div>
						               </#if>
						               <#if p_index==1>
							                <div class="imageShowC3 mt14">
							                    <a  href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" >
							                    	<img src=${Util.GetimgUrl(Util.url(p.href!),'230x100')}  onerror="javascript:this.src='images/s9_default.jpg'"/>
							                    	<!--<img src="skin/default/images/demoimg/230_100.jpg" />-->
							                    </a>
							                    <p class="imageShowBg"></p>
							                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}" >${Util.getCountedWords(p.title!?html,15)}</a></p>
							                </div>
							              </div>
						               </#if>
				              </#if>
				              
				              <#if p_index==2||p_index==3>
					                <#if p_index == 2>
					                  <div class="imageShowC mr14">
						                <div class="imageShowC3">
						                    <a  href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" >
						                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x100')}  onerror="javascript:this.src='images/s9_default.jpg'"/>
						                       <!--<img src="skin/default/images/demoimg/230_100.jpg" />-->
						                    </a>
						                    <p class="imageShowBg"></p>
						                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank"  title="${p.title!}">${Util.getCountedWords(p.title!?html,15)}</a></p>
						                </div>
					                </#if>
					                <#if p_index == 3>
						                <div class="imageShowC2 mt14">
						                    <a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" >
						                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x136')}  onerror="javascript:this.src='images/s8_default.jpg'"/>
						                       <!--<img src="skin/default/images/demoimg/230_136.jpg" />-->
						                    </a>
						                    <p class="imageShowBg"></p>
						                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}">${Util.getCountedWords(p.title!?html,15)}</a></p>
						                </div>
						             </div>
					               </#if>
						        </#if>
						        
						        <#if p_index==4||p_index==5>
						            <#if p_index==4>
						                <div class="imageShowC mr14">
								                <div class="imageShowC2">
								                    <a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank"  >
								                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x136')}  onerror="javascript:this.src='images/s8_default.jpg'"/>
								                       <!--<img src="skin/default/images/demoimg/230_136.jpg" />-->
								                    </a>
								                    <p class="imageShowBg"></p>
								                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}">${Util.getCountedWords(p.title!?html,15)}</a></p>
								                </div>
						               </#if>
						               <#if p_index==5>
							                <div class="imageShowC3 mt14">
							                    <a  href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" >
							                    	<img src=${Util.GetimgUrl(Util.url(p.href!),'230x100')}  onerror="javascript:this.src='images/s9_default.jpg'"/>
							                    	<!--<img src="skin/default/images/demoimg/230_100.jpg" />-->
							                    </a>
							                    <p class="imageShowBg"></p>
							                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}">${Util.getCountedWords(p.title!?html,15)}</a></p>
							                </div>
							              </div>
						               </#if>
				              </#if>
				               <#if p_index==6>
				                 <div class="imageShowC">
					                <div class="imageShowC1">
					                    <a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank"  >
					                       <img src=${Util.GetimgUrl(Util.url(p.href!),'230x250')}  onerror="javascript:this.src='images/s7_default.jpg'"/>
					                       <!--<img src="skin/default/images/demoimg/230_250.jpg" />-->
					                    </a>
					                    <p class="imageShowBg"></p>
					                    <p class="imageShowText"><a href='photos.action?cmd=detail&photoId=${p.photoId}' target="_blank" title="${p.title!}">${Util.getCountedWords(p.title!?html,15)}</a></p>
					                </div>
					            </div>
				              </#if>
				           </#list>
			         </div>
		           <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize9" /></div>
		      </#if>
		    </div>
		 </div>
    </#if>
 </#list>
</#if>
</div>
</div>
</div>
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
<script src="${ContextPath}js/new/show_picture.js" type="text/javascript"></script>
<!--[if IE 6]>
<script src="${ContextPath}js/new/DD_belatedPNG.js" type="text/javascript"></script>
<script type="text/javascript">
  DD_belatedPNG.fix('.topSearch,.login a,.videoPlay,.videoPlayBg,.tx,.liW,.liX,.liP,.leftNavS li,.leftNavIcon,.leftNavIconH,.folder,.liFolder');
</script>
<![endif]-->
<#include 'footer.ftl'>
</body>
</html>

</html>