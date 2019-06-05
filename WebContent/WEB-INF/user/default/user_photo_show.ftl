<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>${user.blogName!?html}</title>
    <#if SiteConfig??><meta name="keywords" content="${SiteConfig.site.keyword!}" /></#if>
    <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
    <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'skin1'}/skin.css" />
    <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
    <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
    <#include '../user_script.ftl' >
    <script src='${SiteUrl}js/jitar/core.js'></script>
    <script src='${SiteUrl}js/jitar/lang.js'></script>
    <script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
    <script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>    
  </head> 
  <body>
  <#-- 无功能按钮的子页面 -->
  <#include ('childpage.ftl') >
  <div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
  <div id='header'>
    <div id='blog_name'><span>${user.blogName!?html}</span></div>
  </div>
  <#include ('../../layout/layout_2.ftl') >
<#if photo?? >
<div id='placerholder1' title='${photo.title?html}' style='display:none'>
<div style='text-align:center;overflow:hidden;width:600px'>
<a href='${Util.url(photo.href)}' target='_blank'><img onload="CommonUtil.reFixImg(this,480,480)" src="${Util.url(photo.href)}" vspace='4' border='0' /></a>
</div>
<table cellpadding='3' cellspacing='1' style='width:100%;background:#DDD'>
<tr style='background:#FFF'>
<td colspan='2' style='font-weight:bold;'><span style='float:right;font-weight:normal;padding-right:10px'><a href='${Util.url(photo.href)}' target='_blank'>查看原图</a></span>照片信息：</td>
</tr>
<tr style='background:#FFF'>
<td style='width:12%'>图片尺寸：</td><td style='width:905%'>${photo.width} × ${photo.height} 像素</td>
</tr>
<tr style='background:#FFF'>
<td>图片大小：</td><td>${Util.fsize(photo.size)}</td>
</tr>
<tr style='background:#FFF'>
<td>浏览次数：</td><td>${photo.viewCount}</td>
</tr>
<tr style='background:#FFF'>
<td>评论次数：</td><td>${photo.commentCount}</td>
</tr>
<#if photoStaple?? >
<tr style='background:#FFF'>
<td>图片分类：</td><td>
<#if photoStaple??>
	<#if UserUrlPattern??>
	<a href='${UserUrlPattern.replace('{loginName}',user.loginName)}photocate/${photoStaple.id}.html'>${photoStaple.title?html}</a>
	<#else>
	<a href='${SiteUrl}${user.loginName}/photo?userStapleId=${photoStaple.id}'>${photoStaple.title?html}</a>
	</#if>
</#if>
</td>
</tr>
</#if>
<#if photo.tags?? >
<tr style='background:#FFF'>
<td>图片标签：</td><td><#list photo.tags.split(",") as t>
    					<a href='${SiteUrl}show_tag.action?tagName=${t?url('UTF-8')}'>${t}</a>&nbsp;
     					</#list></td>
</tr>
</#if>
</table>
<table cellpadding='3' cellspacing='1' style='width:100%;background:#DDD'>
<tr style='background:#FFF'>
<td style='font-weight:bold;'>照片评论：</td>
</tr>
</table>
<a name='top'></a>
<#if photo_comment_list??>
<#list photo_comment_list as comment>
<table border="0" cellspacing="1" cellpadding="3" class="commentTable">
	<tr>
		<td class="commentLeft">
			<#if comment.userId??>
        <#assign u = Util.userById(comment.userId)>
        <#if (u.trueName??)><#assign userName = u.trueName >
        <#elseif (u.nickName??)><#assign userName = u.nickName >
        <#elseif (u.loginName??)><#assign userName = u.loginName >
        </#if>
        <img src="${SSOServerUrl +'upload/'+u.userIcon!"images/default.gif"}" width='48' height='48' border='0' />
        <a onmouseover="ToolTip.showUserCard(event,'${u.loginName!}','${userName}', '${SSOServerUrl +"upload/"+u.userIcon!"images/default.gif"}')" href="${SiteUrl}${u.loginName!}" target="_blank">${u.nickName!?html}</a>
			<#else>  
        <img src="${SiteUrl}images/default.gif" width='48' height='48' border='0' />
         匿名用户
			</#if>
		</td>
		<td class="commentRight">
			<div class="commentHeader">
				&nbsp;&nbsp;<span class="commentTitle">${comment.title?html}</span>
			</div>
			<div class="commentHeader">
				<span  style="float: left">&nbsp;&nbsp;用户评论星级：<font color="#FF0000"><#if comment.star &gt; 0><#list 1..comment.star as i>☆</#list></#if></font></span><span style="float: right">发表时间：${comment.createDate} <a href="#top">回到顶部</a></span>
				<span class="commentTitle">&nbsp;</span>
			</div>
			<div class="commentContent">
				${comment.content}
			</div>
		</td>
	</tr>
</table>
</#list>
</#if>
<#include 'paper.ftl' >
<div style='clear:both'></div>
<#if loginUser??>
<#--
	<#if SiteConfig?? >
	    <#assign enabedComment = SiteConfig.user.site.comment.enabled >
	<#else>
	    <#assign enabedComment = false >
	</#if>
	<#if enabedComment >
		<#if loginUser?? >
		-->
		<form action="${SiteUrl}jython/object_comment.py" method="post">
		<div>
		标题：<input name="title" value="回复：${photo.title?html}" style='width:500px;' /><font style='color:red'>*</font>
		</div>
		<input type='hidden' name='photoUser' value='${user.loginName}' />
		<input type='hidden' name='objtype' value='11' />
		<input type="hidden" name="photoId" value="${photo.photoId}" />
		<input type="hidden" name="aboutUserId" value="${photo.userId}" />
		<input type="hidden" name="userName" value="${loginUser.nickName!?html}" />
		  评论星级：<input type="radio" name="star" value="1"/><font color="#FF0000">☆</font>
		  <input type="radio" name="star" value="2" /><font color="#FF0000">☆☆</font>
		  <input type="radio" name="star" value="3" /><font color="#FF0000">☆☆☆</font>
		  <input type="radio" name="star" value="4" /><font color="#FF0000">☆☆☆☆</font>
		  <input type="radio" name="star" value="5" /><font color="#FF0000">☆☆☆☆☆</font>&nbsp;&nbsp;(<font color="#FF0000">☆</font>越多，评价越高。)
		  <br />
		  <textarea name="content" style="width:100%;height:80px" value=""></textarea><br/>
	  
		  <input type="submit" value="发表评论" />
		</form>
		<#--
		</#if>
	<#else>
		 <div style='padding:4px;font-weight:bold;'>系统已经设置不允许匿名评论。请登录参与评论。</div>
	</#if>
	-->
	
</#if>
</div>
<#else>
<div id='placerholder1' title='照片信息' style='display:none'>
 无法加载照片信息。
</div>
</#if>
<script>
App.start();
</script>
<div id="subMenuDiv"></div>
<script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script> 
  </body>
</html>