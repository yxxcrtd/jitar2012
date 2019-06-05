<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>集体备课 <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}precourse.css" />
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
<#include '/WEB-INF/ftl/site_head.ftl'>
<div class='course_title'>【${prepareCourse.title!}】- ${showUser.trueName}的个案</div>
<div style='clear:both;height:8px;font-size:0'></div>
<div style='padding:10px 20px'>
${prepareCourseMember.privateContent!}
</div>
<div style='height:8px;font-size:0;'></div>
<div class="box">
  <div class="box_head">
    <div class="box_head_right"></div>
    <div class="box_head_left">&nbsp;<img src="../../${ContextPath}css/index/j.gif">&nbsp;对本个案的评论</div>
  </div>
  <div class="box_content">
    <#if comment_list?? >
    <#list comment_list as cmt>
    <#assign u = Util.userById(cmt.commentUserId) >
    <div class='box' style='padding:1px'>
    	<div style='background:#EEE;padding:4px'>
    	<span style='float:right'><a target='_blank' href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a> 发表于：${cmt.createDate?string('yyyy-MM-dd HH:mm:ss')}</span>
    	<span style='font-weight:bold'>${cmt.title}</span>
    	</div>
	   	<div style='padding:6px'>
	   	${cmt.content}
	   	</div>	    
    </div>
    <div style='height:8px;font-size:0;'></div>
    </#list>
	<div class='pager'>
	<#include 'pager.ftl'>
	</div>
  </#if>
<#if prepareCourseMember?? && loginUser?? >
<form method='post'>
评论标题(<span style='color:#F00'>*</span>)：<input style='width:400px' name='commentTitle' value='' />
<br/>
评论内容(<span style='color:#F00'>*</span>)：<br/>

    <script id="DHtml" name="content" type="text/plain" style="width:880px;height:300px;">
    </script>
    <script type="text/javascript">
        var editor = UE.getEditor('DHtml');
    </script>  
    
<input name="commentedUserId" value="${prepareCourseMember.userId}" type="hidden">
<div style='text-align:center'>
<input value="发表评论" type="submit" />
</div>
</form>
</#if>
  </div>
</div>
<div style='padding:10px;text-align:center'><a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/0/'>返回备课首页</a></div>
<div style='clear:both;'></div>
<#include ('/WEB-INF/ftl/footer.ftl') >
<#if error_msg?? >
${error_msg}
</#if>
</body>
</html>