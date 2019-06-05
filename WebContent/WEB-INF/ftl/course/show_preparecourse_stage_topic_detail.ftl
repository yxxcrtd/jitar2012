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
    <a href='#top'></a>
    <div id='placerholder1' title='${currentStage.title!?html}的讨论内容' style='display:none;padding:1px;'>
    <#if prepareCourseTopic?? >
	    <#assign u = Util.userById(prepareCourseTopic.userId)>
	    <#if (u.trueName??)><#assign userName = u.trueName >
	    <#elseif (u.nickName??)><#assign userName = u.nickName >
	    <#elseif (u.loginName??)><#assign userName = u.loginName >
	    </#if>
	                
	    <h4>${prepareCourseTopic.title!?html}</h4>
	    <div>${userName} 发表于：${prepareCourseTopic.createDate?string('yyyy-MM-dd HH:mm:ss')}</div>
	    <div>${prepareCourseTopic.content!}</div>
	    <hr/>
	    
		<#if topic_reply_list?? >
		<#list topic_reply_list as comment>
		<table border="0" cellspacing="1" cellpadding="3" class="commentTable">
		    <tr>
		        <td class="commentLeft">
		        <#if comment.userId??>
		        <#assign u = Util.userById(comment.userId)>
		        <#if (u.trueName??)><#assign userName = u.trueName >
		        <#elseif (u.nickName??)><#assign userName = u.nickName >
		        <#elseif (u.loginName??)><#assign userName = u.loginName >
		        </#if>
		        <img src="${SSOServerUrl +'upload/'+ u.userIcon!"images/default.gif"}" width='48' height='48' border='0' onerror="this.src='${ContextPath}images/default.gif'"/>
		        <a onmouseover="ToolTip.showUserCard(event,'${u.loginName!}','${userName}', '${SSOServerUrl +"upload/"+ u.userIcon!"images/default.gif"}')" href="${SiteUrl}go.action?loginName=${u.loginName!}" target="_blank">${u.nickName!?html}</a>
		            <#else>  
		        <img src="${SiteUrl}images/default.gif" width='48' height='48' border='0' />
		         匿名用户
		            </#if>
		        
		        </td>
		        <td class="commentRight">
		            <div class="commentHeader">
		                <span style="float: right">发表时间：${comment.createDate} <a href="#top">回到顶部</a></span>
		                <span class="commentTitle">${comment.title}</span>
		            </div>
		            <div class="commentContent">
		                ${comment.content}
		            </div>
		        </td>
		    </tr>
		</table>
		  </#list>
		</#if>
	    <#include ('pager.ftl') >    
		<form method='post' action='show_preparecourse_stage_topic_detail.py?prepareCourseTopicId=${prepareCourseTopicId}'>
		标题：<input name='replyTitle' style='width:90%' value='回复:${prepareCourseTopic.title!?html}' /> <span style='color:red'>*</span> <br/>
		内容：<br/>
        <script id="DHtml" name="replyContent" type="text/plain" style="width:640px;height:300px;">
        </script>
        <script type="text/javascript">
            var editor = UE.getEditor('DHtml');
        </script>  
        		
		<input type='submit' value='发表讨论' />
		</form>
	</#if>
    </div>    
    <div id='page_footer'></div>
    <script>App.start();</script>
    <div id="subMenuDiv"></div>
    <script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
    <#-- 原来这里是 include loginui.ftl  -->
    <script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
</body>
</html>