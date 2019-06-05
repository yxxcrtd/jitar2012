<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="expires" content="0" />
<title>${group.groupTitle!?html}</title>
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
<link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
<!-- 布局模板 -->
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
<!-- ToolTip -->
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
<script language='javascript'>
var JITAR_ROOT = '${SiteUrl}';
//var USERMGR_ROOT = '{UserMgrClientUrl}'; 此处变量应该可以不用了，不用再改了。    
  <#if loginUser?? >
var visitor = { id: ${loginUser.userId}, name: '${loginUser.loginName!?js_string}', nickName: '${loginUser.nickName!?js_string}', role: '${visitor_role!"guest"}' };
  <#else>
    var visitor = { id: null, name: null, nickName: null, role: 'guest' };
  </#if>
var user = {};
var group = {
  id: ${group.groupId}, name: '${group.groupName!?js_string}', title: '${group.groupTitle!?js_string}'
};
var page_ctxt = {
  owner: group,
  isSystemPage: <#if page.isSystemPage??>${page.isSystemPage?string('true', 'false')}<#else>false</#if>,
  pages: [{id: ${page.pageId}, title:'${page.title!?js_string}' }],
  widgets: [
    {id: "placerholder1", page:${page.pageId}, column:2, title:'', module:'placeholder', ico:'', data:{} },
    <#list widget_list as widget>          
      {id: '${widget.id}', page:${widget.pageId}, column:${widget.columnIndex}, 
        title:'${widget.title?js_string}', module:'${widget.module}', ico:'', 
        data:{ ${widget.data!} } }<#if widget_has_next>, </#if>
    </#list>
  ]
};
</script>
<script src='${SiteUrl}js/jitar/core.js'></script>
<script src='${SiteUrl}js/jitar/lang.js'></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/Drag.js"></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
</head>
<body>
<#include 'func.ftl' >
<div id = 'progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
<div id='header'>
  <div id='blog_name'><span>${group.groupTitle!?html}</span></div>
</div> 
<div id='placerholder1' title='${widget.title}' style='display:none;padding:1px;'>
<#if article_list??>
文章：
<ul class="listul">
<table border="0" style="width:100%">
<#list article_list as article>
    <tr>
    <td>
    <a href="${SiteUrl}showArticle.action?articleId=${article.articleId}" target="_blank">${article.title!?html}</a>
    &nbsp;&nbsp;<#if article.typeState==true>[转载]<#elseif  article.typeState==false>[原创]</#if>
    </td>
    <td style="width:80px">
        ${article.createDate?string("yyyy-MM-dd")}
    </td>
    </tr>
</#list>
</table>
</ul>
</#if>
<#if resource_list??>
资源：
<ul class="listul">
<table border="0" style="width:100%">
<#list resource_list as resource>
    <tr>
    <td>
    <a href='../showResource.action?resourceId=${resource.resourceId}' target='_blank'><img src='${Util.iconImage(resource.href!)}' border='0' align='absmiddle' hspace='3' />${resource.title!?html}</a>
    </td>
    <td style="width:80px">
        ${resource.createDate?string("yyyy-MM-dd")}
    </td>
    </tr>
</#list>
</table>
</ul>
</#if>
<#if photo_list??>
图片：
  <ul class="listul">
  <#if photo_list.size() &gt; 0 >
    <#-- 定义要显示的列数 columnCount -->
    <#assign columnCount = 3>
    <#-- 计算显示当前记录集需要的表格行数 rowCount -->
    <#if photo_list.size() % columnCount == 0>
    <#assign rowCount = (photo_list.size() / columnCount) - 1>
    <#else>
    <#assign rowCount = (photo_list.size() / columnCount)>
    </#if>
     
    <#-- 输出表格 -->
    <table  cellSpacing="10" align="center" style='width:100%'>                            
    <#-- 外层循环输出表格的 tr -->
    <#list 0..rowCount as row >
    <tr>
    <#-- 内层循环输出表格的 td  -->
    <#list 0..columnCount - 1 as cell >
        <td align="center" width='${100 / columnCount}%' style='padding:8px'><br />
        <#-- 判断是否存在当前对象：存在就输出；不存在就输出空格 -->
        <#if photo_list[row * columnCount + cell]??>                     
            <#assign photo = photo_list[row * columnCount + cell]>
            <#if photo.isPrivateShow==false>
                <a href="${SiteUrl}photos.action?cmd=detail&photoId=${photo.photoId}"><img onload="CommonUtil.reFixImg(this,120,100)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' border='0' /></a><br />
                <a href="${SiteUrl}photos.action?cmd=detail&photoId=${photo.photoId}">${photo.title!?html}</a>
            <#else>
                <#assign photouser = Util.userById(photo.userId)>
                <a href='${SiteUrl}${photouser.loginName}/photo/${photo.photoId}.html'><img onload="CommonUtil.reFixImg(this,120,100)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' border='0' /></a><br />
                <a href='${SiteUrl}${photouser.loginName}/photo/${photo.photoId}.html'>${photo.title!?html}</a>
            </#if>        
        </#if>
        </td>
    </#list>
    </tr>
    </#list>
    </table>
</#if>
</ul>
</#if>
<#if video_list??>
视频：
<ul class="listul">
 <#if video_list.size() &gt; 0 >
    <#-- 定义要显示的列数 columnCount -->
    <#assign columnCount = 3>
    <#-- 计算显示当前记录集需要的表格行数 rowCount -->
    <#if video_list.size() % columnCount == 0>
    <#assign rowCount = (video_list.size() / columnCount) - 1>
    <#else>
    <#assign rowCount = (video_list.size() / columnCount)>
    </#if>
     
    <#-- 输出表格 -->
    <table  cellSpacing="10" align="center">                            
    <#-- 外层循环输出表格的 tr -->
    <#list 0..rowCount as row >
    <tr valign='top'>
    <#-- 内层循环输出表格的 td  -->
    <#list 0..columnCount - 1 as cell >
        <td align="center" width='${100 / columnCount}%'><br />
        <#-- 判断是否存在当前对象：存在就输出；不存在就输出空格 -->
        <#if video_list[row * columnCount + cell]??>                     
            <#assign video = video_list[row * columnCount + cell]>                  
            <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${video.videoId}'><img border=0 src="${video.flvThumbNailHref!?html}"/></a><br />
            <a href='${SiteUrl}manage/video.action?cmd=show&videoId=${video.videoId}'>${video.title!?html}</a>
        <#else>
            &nbsp;
        </#if>
        </td>
    </#list>
    </tr>
    </#list>
    </table>
</#if>
</ul>
</#if>
</div>
<#-- 调用页面指定的布局模式 -->
<#include ('../../layout/layout_' + (page.layoutId!'1') + '.ftl') >

<div id='page_footer'></div>
<script>App.start();</script>
<div id="subMenuDiv"></div>
<script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
<#-- 原来这里是 include loginui.ftl  -->
<script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
</body>
</html>