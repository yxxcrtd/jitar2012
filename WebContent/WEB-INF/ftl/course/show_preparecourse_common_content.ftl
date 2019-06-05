<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${prepareCourse.title?html}</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
  <#include ('common_script.ftl') >
  <script src='${SiteUrl}js/jitar/core.js'></script>
  <script src='${SiteUrl}js/jitar/lang.js'></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/Drag.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
 <!-- 配置上载路径 -->
    <script type="text/javascript">
        window.UEDITOR_UPLOAD_URL = "${SiteUrl}";
        window.UEDITOR_USERLOGINNAME = "<#if loginUser??>${loginUser.loginName!?js_string}</#if>";
    </script>
    <!-- 配置文件 -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/ueditor.all.js"></script>
    <!-- 语言包文件(建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败) -->
    <script type="text/javascript" src="${ContextPath}manage/ueditor/lang/zh-cn/zh-cn.js"></script>   
</head> 
<body>
    <#include ('func.ftl') >
    <div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
    <div id='header'>
      <div id='blog_name'><span>${prepareCourse.title!?html}</span></div>
    </div>
    <div id='nav'><#include ('navbar.ftl') ></div>
    <#include ('/WEB-INF/layout/layout_2.ftl') >
    <div id='placerholder1' title='共案内容' style='display:none;padding:1px;'>
    <a name="top"></a>
		<div class="article_title" style='text-align:center'>${prepareCourse.title!}</div>
		<#assign u = Util.userById(prepareCourse.leaderId) >
		<div style='text-align:center;padding:4px'>主备人：<a target='_blank' href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></div>
		<div class="article_content">${prepareCourse.commonContent!}</div>
		
		<div style="font-weight:bold;padding-top:20px">以下是对本共案的评论：</div>
		<hr/>
		<#if comment_list??>
		<div>
		<#list comment_list as cl>
		<div style="padding:5px 0;border:1px solid #AAA;">
		<div class="commentHeader">
				<span style="float: left;">&nbsp;&nbsp;<a href='${SiteUrl}go.action?userId=${cl.userId}' target='_blank'>${cl.userName!?html}</a> ：</span><span style="float: right;">发表时间：${cl.createDate?string("yyyy-MM-dd HH:mm:ss")}
				<a href="#cc" onclick="replyComment(${cl.id});">回复</a>
				 <a href="#top">回到顶部</a>&nbsp;&nbsp;&nbsp;&nbsp;</span><span>&nbsp;</span>
			</div>
			<div class="commentContent">${cl.content}</div>
		</div>
		<div style="padding:5px;"></div>
		</#list>
		<div style='text-align:center'>
		<#include '/WEB-INF/ftl/inc/pager.ftl' >
		</div>
		</div>
		
		</#if>
		
		<div style='font-weight:bold;padding:6px 0'>对本共案发表评论：</div>
		<form method="POST" id="comment_form">
		<input name="id" type="hidden" value=""/>
		<a name="cc"></a>
		<#if loginUser??>

            <script id="comment" name="comment" type="text/plain" style="width:880px;height:500px;">
            </script>
            <script type="text/javascript">
                var editor = UE.getEditor('comment');
            </script>  
            
		<input type='submit' value='提交评论' />
		<#else>
		<div style="color:red;font-weight:bold">请登录发表评论。<a href='${SiteUrl}login.jsp' donclick='LoginUI.showLogin();return false;'>通行证登录</a></div>
		</#if>
		</form>	
    </div>
  	
    <div id='placerholder2' title='编辑历史' style='display:none;'>    
		<#include ('history_content.ftl') >
    </div>
    <div id='placerholder3' title='查看个案' style='display:none;'>
     	<#include ('user_content.ftl') >
    </div>
    <div id='page_footer'></div>
    <script>App.start();</script>
    <div id="subMenuDiv"></div>
    <script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
    <#-- 原来这里是 include loginui.ftl  -->
    <script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
</body>
</html>