<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${article.title!} - ${user.blogName?js_string}</title>
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
<link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />   

<!-- 自定义界面 -->
<#if (customSkin??) >
<style type='text/css'>
<#if ((customSkin.logo.length() > 0) || (customSkin.logoheight.length() > 0) ) >
#header { 
    <#if (customSkin.logo??) && (customSkin.logo.length() > 0)  >
background:url('${customSkin.logo!}') repeat-x top center;
</#if>
<#if (customSkin.logoheight??) && (customSkin.logoheight.length() > 0) >
height:${customSkin.logoheight!}px;
</#if>
}
</#if>
<#if (customSkin.bgcolor??) && (customSkin.bgcolor.length() > 0) >
html,body{ background:${customSkin.bgcolor!} }
</#if>
<#if (customSkin.titleleft??) && (customSkin.titleleft.length() > 0) >
#blog_name { padding-left:${customSkin.titleleft!}px; }
</#if>
<#if (customSkin.titletop??) && (customSkin.titletop.length() > 0) >
#blog_name { padding-top:${customSkin.titletop!}px; }
</#if>
<#if (customSkin.titledisplay??) && (customSkin.titledisplay.length() > 0) >
#blog_name {display:none;}
</#if>
</style>
</#if>

<script type='text/javascript'>
var JITAR_ROOT = '${SiteUrl}';  
//var USERMGR_ROOT = '{UserMgrClientUrl}'; 此处变量应该可以不用了，不用再改了。;
<#if UserUrlPattern??>
  var HasDomain = "";
</#if>
<#if loginUser?? >
var visitor = { id: ${loginUser.userId}, name: '${loginUser.loginName!?js_string}', nickName: '${loginUser.nickName!?js_string}', role: 'guest' };
<#else>
var visitor = { id: null, name: null, nickName: null, role: 'guest' };
</#if>
  var article = {
    id: ${article.id},
title: '${article.title!?js_string}'
  };
  var user = {
    id: ${user.userId}, 
name: '${user.loginName}'
  };
  var page_ctxt = {
    isSystemPage: ${page.isSystemPage?string('true', 'false')},
owner: user,
pages : [ {id: 2, title: '${user.blogName?js_string}'} ],
    widgets: [
  <#list widget_list as widget>
{id: ${widget.id}, page:${widget.pageId}, column:${widget.columnIndex!}, 
  title:'${widget.title!?js_string}', module:'${widget.module!}', ico:'', 
  data:{} } 
<#if widget_has_next>,</#if>
  </#list>
    ]
  };
	</script>
<script src='${SiteUrl}js/jitar/core.js'></script>
<script src='${SiteUrl}js/jitar/lang.js'></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>		
</head>
<body>
	<#-- 无功能按钮的子页面 -->
	<#include 'childpage.ftl'>	
	
	<div id='_article_content_preload' style='display:none;'>
		<#if UserCate??>
			<#if UserUrlPattern??>
			  [<a class='categroy' title='个人分类：${UserCate.name!}' href='${UserUrlPattern.replace('{loginName}',user.loginName)}category/${UserCate.categoryId}.html'>${UserCate.name!}</a>]
			<#else>
			  [<a class='categroy' title='个人分类：${UserCate.name!}' href='${SiteUrl}${user.loginName}/category/${UserCate.categoryId}.html'>${UserCate.name!}</a>]
			</#if>			
		</#if>
		<font style="color: #FF0000; font-weight: bold;"><#if article.typeState == false>[原创]<#else>[转载]</#if></font>&nbsp;&nbsp;
		<span class='article_title'>${article.title!?html}</span>
		<br /><br />
		<#if SysCate??>
			<span>系统分类：<a href='${SiteUrl}articles.py?type=new&categoryId=${SysCate.categoryId}'>${SysCate.name!?html}</a></span>
		</#if>
		<#if article.articleTags != "">
			<span class='article_tags'>
				标签：
				<#list article.articleTags.split(",") as t>
					<a href='${SiteUrl}showTag.action?tagName=${t?url('UTF-8')}'>${t}</a>&nbsp;
				</#list>
			</span>
		</#if>
		<br />
		<div class='article_content'>
		<#if SiteConfig.user.site.article_show?? && SiteConfig.user.site.article_show>
		<#assign loginShow="1">			
		<#else>
		<#assign loginShow="0">
		</#if>
	   <#assign swf="">	
      <#if article.articleFormat??>
          <#if article.articleFormat==1>
              <#assign docHref=article.wordHref>
              <#assign swf=docHref?substring(0,docHref?last_index_of(".")) + ".swf">
          </#if>    
       </#if>
		<#if loginShow=="1">
		<#if loginUser??>
		      <!--显示的是文章内容-->
		      <#if article.articleFormat??>
		          <#if article.articleFormat==1>
		              <!--显示Word文档的SWF-->
		                  <br/>
                            <script type="text/javascript" src="${SiteUrl}js/flashpaper/flexpaper_flash.js"></script>
                            <a id="viewerPlaceHolder" style="width:100%;height:800px;display:block">您的浏览器不支持 Flash或者Flash版本太低。<span onclick="window.open('http://get.adobe.com/cn/flashplayer/','_blank')" style="cursor:pointer;">点击下载最新版本</span></a>
                            <script type="text/javascript">
                            var fp = new FlexPaperViewer('${SiteUrl}js/flashpaper/FlexPaperViewer',
                                'viewerPlaceHolder', {
                                    config : {
                                      SwfFile : '${SiteUrl}${swf}?' + (new Date()).valueOf(),
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
		              
                      <#if article.wordDownload>
		                  <br/>下载文档:<a href="${SiteUrl}${docHref}">点这里下载</a>
		              </#if>
		          <#else>
		              ${article.articleContent!}
		          </#if>
		      <#else>
		          ${article.articleContent!}
		      </#if>
		      
		<#else>
		<div style="color:red;font-weight:bold;padding:50px 0">
		本文章只有登录后才能看到内容。
		</div>
		</#if>
		<#else>
              <!--显示的是文章内容-->
              <#if article.articleFormat??>
                  <#if article.articleFormat==1>
                      <!--显示Word文档的SWF-->
                        <br/>
                            <script type="text/javascript" src="${SiteUrl}js/flashpaper/flexpaper_flash.js"></script>
                            <a id="viewerPlaceHolder" style="width:100%;height:800px;display:block">您的浏览器不支持 Flash或者Flash版本太低。<span onclick="window.open('http://get.adobe.com/cn/flashplayer/','_blank')" style="cursor:pointer;">点击下载最新版本</span></a>
                            <script type="text/javascript">
                            var fp = new FlexPaperViewer('${SiteUrl}js/flashpaper/FlexPaperViewer',
                                'viewerPlaceHolder', {
                                    config : {
                                      SwfFile : '${SiteUrl}${swf}?' + (new Date()).valueOf(),
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
                      
                     <#if article.wordDownload>
                                                          <br/>下载文档:<a href="${SiteUrl}${docHref}">点这里下载</a>
                    </#if>                                                          
                  <#else>
                      ${article.articleContent!}
                  </#if>
              <#else>
                  ${article.articleContent!}
              </#if>
        </#if>
		</div>
		<div style="text-align: center;">
			<table cellspacing='20' cellpadding='0' border='0' align='center'>
				<tr>
    <#if article.commentState><td class='comment'><a href='#comment' style='display:block;width:100%;height:44px;margin-top:-12px'></a></td></#if>
    <td class='digg' id='digg${article.articleId}' onclick='CommonUtil.diggTrample("digg","${article.articleId}")'>${article.digg}</td>
    <td class='trample' id='trample${article.articleId}' onclick='CommonUtil.diggTrample("trample","${article.articleId}")'>${article.trample}</td>
    </tr>
    </table>
 
 </div>
<#include 'score.ftl'> 
<#include 'share.ftl'>
<div class='article_footer'>
 评论：${article.commentCount} | 阅读：${article.viewCount} | 发表于：${article.createDate} | 最后修改：${article.lastModified} | <a href="javascript:void(0)" onclick="javascript:add_favorites(${article.articleId},'',3,'${article.title}');">收藏</a> | <a href="javascript:copyToClipBoard();">复制本文地址</a>    
</div>
<#if AveStar??>
<div class='article_footer'>
 	用户对本文章的评论星级 评论总星级数:${StarCount} | 参与评论人数：${StarNumber} | 综合评论星级：<font color="#FF0000"><#list 1..AveStar as i >☆</#list></font>
</div>
</#if>

<div class='prev_next_article'>后一篇: <#if next_article??><#if next_article.typeState == false>[原创]<#else>[转载]</#if><a href='${SiteUrl}showArticle.action?articleId=${next_article.articleId}'>${next_article.title!?html}</a><#else>(无)</#if></div>
<div class='prev_next_article'>前一篇: <#if prev_article??><#if prev_article.typeState == false>[原创]<#else>[转载]</#if><a href='${SiteUrl}showArticle.action?articleId=${prev_article.articleId}'>${prev_article.title!?html}</a><#else>(无)</#if></div>
<br />
<#if similarArticleList??>
    <div class='prev_next_article'>
	    <fieldset>
			<legend>相关文章: </legend>
			<div style="padding-top: 12px; padding-bottom: 12px; padding-left: 12px;">
				<table border="0" width="100%">
					<#assign columnCount = 2>
					<#if similarArticleList.size() % columnCount == 0>
						<#assign rowCount = similarArticleList.size() / columnCount>
					<#else>
						<#assign rowCount = (similarArticleList.size() / columnCount)?int + 1>
					</#if>
					<#list 0..rowCount - 1 as row>
						<tr>
							<#list 0..columnCount - 1 as cell>	   
								<td width="50%" style="padding-left: 15px;">
									<#if similarArticleList[row * columnCount + cell]??>
										<#assign sa = similarArticleList[row * columnCount + cell]>
										<li><#if sa.typeState == false>[原创]<#else>[转载]</#if> <a href="${SiteUrl}showArticle.action?articleId=${sa.articleId}" title="文章摘要：${sa.articleAbstract!}&#10;发表时间：${sa.createDate?string('yyyy-MM-dd HH:mm:ss')}">${sa.title!?html}</a>
									</#if>
								</td>
								<#if cell < columnCount - 1>
									<td></td>
								</#if>
							</#list>
						</tr>
					</#list>
				</table>
			</div>
		</fieldset>
    </div>
</#if>
  </div>
  
  
	<div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载......</div>
<div id='header'>
	<div id='blog_name'><span>${user.blogName!?html}</span></div>
</div>
<div id='navbar'><#include 'navbar.ftl' ></div>
<#include '../../layout/layout_2.ftl'>

<script>
App.start();
</script>

<div id="subMenuDiv"></div>
<script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
<#-- 原来这里是 include loginui.ftl  -->
<script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
<input type="text" style="position:absolute;left:-100px;width:1px;"  id="clipboardInput" value="">
</body>
</html>