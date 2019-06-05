<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${prepareCourse.title?html}</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
  <#include 'common_script.ftl' >
  <script src='${SiteUrl}js/jitar/core.js'></script>
  <script src='${SiteUrl}js/jitar/lang.js'></script>
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
    <#include 'func.ftl' >
    <div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
    <div id='header'>
      <div id='blog_name'><span>${prepareCourse.title!?html}</span></div>
    </div>
    <div id='nav'><#include 'navbar.ftl' ></div>
    <div id='wraper' style="padding-top:10px">
	<table id='container' border='0' cellpadding='0' cellspacing='0' style="table-layout:fixed">
	<tr>
	<td id='column_1' class='col'>
	
	
<div id="webpart_placerholder2" class="widgetWindow"><div class="widgetFrame">
  <table class="widgetTable" cellpadding="0" cellspacing="0" border="0"><thead><tr><td class="widgetHead h_lt"></td><td class="widgetHead h_mt"><div class="widgetHeader"><div class="ico"><img class="mod_icon" src="${SiteUrl}images/pixel.gif" height="16" width="16" border="0"></div><div id="webpart_1_h" class="title">备课基本信息</div></div></td><td class="widgetHead h_rt"></td></tr></thead><tbody><tr class="widgetEditor" style="display: none;"><td class="widgetEdit e_lt"></td><td class="widgetEdit e_mt"></td><td class="widgetEdit e_rt"></td></tr><tr><td class="widgetContent c_lt"></td><td class="widgetContent c_mt">
  <#if prepareCourse?? >
<div>
  <div><b>${prepareCourse.title!}</b></div>
  <#assign u = Util.userById(prepareCourse.leaderId) >
  <div>主备人:<a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.trueName?html}</a></div>
  <#assign uu = Util.userById(prepareCourse.createUserId) >
  <div>发起人:<a href='${SiteUrl}go.action?loginName=${uu.loginName}' target='_blank'>${uu.trueName?html}</a></div>
  <div>成员:${prepareCourse.memberCount!?default('0')}人</div>
  <div>文章:${prepareCourse.articleCount!?default('0')}篇</div>
  <div>资源:${prepareCourse.resourceCount!?default('0')}个</div>
  <div>学段:<#if gradeLevel??>${gradeLevel.gradeName!?html}</#if></div>
  <div>年级:<#if grade??>${grade.gradeName!?html}</#if></div>
  <div>学科:<#if subject??>${subject.msubjName!?html}</#if></div>
  <div>创建日期:${prepareCourse.createDate!?string('yyyy年M月d日')}</div>
  <div>开始日期:${prepareCourse.startDate!?string('yyyy年M月d日')}</div>
  <div>结束日期:${prepareCourse.endDate!?string('yyyy年M月d日')}</div>
  <div>标签:<#list Util.tagToList(prepareCourse.tags!) as tag>
            <a href='${SiteUrl}showTag.action?tagName=${tag?url("UTF-8")}'>${tag!?html}</a>
            </#list>
  </div>
  <div>备课任务: ${prepareCourse.description!}</div>
  <div>
	<#if ("0" != Util.isOpenMeetings())>
		<#if (1 == Util.IsVideoJibei(prepareCourse.prepareCourseId))>
			<br/>
			<a class="linkButton icoVideo" href="${Util.isOpenMeetings()}&amp;jibeiid=${prepareCourse.prepareCourseId!}" target="_blank">进入备课会议室</a>
		</#if>
	</#if>
  </div>
  <div>
  </div>
  </div>
</div>
</#if>
</td><td class="widgetContent c_rt"></td></tr><tr><td class="widgetFoot f_lt"></td><td class="widgetFoot f_mt"></td><td class="widgetFoot f_rt"></td></tr></tbody></table>
</div></div>


<div id="webpart_placerholder2" class="widgetWindow"><div class="widgetFrame">
  <table class="widgetTable" cellpadding="0" cellspacing="0" border="0"><thead><tr><td class="widgetHead h_lt"></td><td class="widgetHead h_mt"><div class="widgetHeader"><div class="ico"><img class="mod_icon" src="${SiteUrl}images/pixel.gif" height="16" width="16" border="0"></div><div id="webpart_1_h" class="title">个案列表</div></div></td><td class="widgetHead h_rt"></td></tr></thead><tbody><tr class="widgetEditor" style="display: none;"><td class="widgetEdit e_lt"></td><td class="widgetEdit e_mt"></td><td class="widgetEdit e_rt"></td></tr><tr><td class="widgetContent c_lt"></td><td class="widgetContent c_mt">
<#if user_list?? >
<table class="pc_table" cellspacing="1">
<#list user_list as u>
<#if u.userId == showUser.userId>
<tr class='curbackgroundred'>
<#else>
<tr>
</#if>
<td><a target='_blank' href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></td>
<td>
 <a href='show_preparecourse_user_content.py?userId=${u.userId}'>查看</a>  
</td>
</tr>
</#list>
</table>
</#if>
</td><td class="widgetContent c_rt"></td></tr><tr><td class="widgetFoot f_lt"></td><td class="widgetFoot f_mt"></td><td class="widgetFoot f_rt"></td></tr></tbody></table>
</div></div>

</td>
<td id='column_2' class='col'>
	
	<div id='placerholder1' title='查看个案内容' style='padding:1px;'>
		<div class="article_title" style='text-align:center'>${showUser.trueName}的个案</div>
		<div class="article_content">
		<#if error??>
		${error}
		<#else>
			<#if prepareCourseMember.contentType == 2 || prepareCourseMember.contentType == 3 || prepareCourseMember.contentType == 4 || prepareCourseMember.contentType == 5 || prepareCourseMember.contentType == 100>
			<#if !(swfUrl??)>
			<div style="padding:20px 0;text-align:center;color:#f00;font-weight:bold;">个案文件不存在，无法进行显示。</div>
			<#else>
			<#if loginUser??>
			<div style="text-align:right;padding:4px 0"><a target="_blank" href="${SiteUrl}manage/DownloadCourseFile?p=${prepareCourseMember.prepareCourseMemberId}&t=doc&f=0">下载原始文件</a> <a target="_blank" href="${SiteUrl}manage/DownloadCourseFile?p=${prepareCourseMember.prepareCourseMemberId}&t=pdf&f=0">下载 PDF 格式</a> </div>
			</#if>
			<script type="text/javascript" src="${courseFileServer}js/flashpaper/flexpaper_flash.js"></script>
			<a id="viewerPlaceHolder" style="width:100%;height:500px;display:block"></a>
	
			<script type="text/javascript">
			var fp = new FlexPaperViewer('${courseFileServer}js/flashpaper/FlexPaperViewer',
					'viewerPlaceHolder', {
						config : {
						  SwfFile : '${swfUrl}?' + (new Date()).valueOf(),
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
			${prepareCourseMember.privateContent!}
			</#if>
		</#if>
		</div>
		
		<#include '../../user/default/score.ftl'>
		
   <a name='top'></a>
   <#if comment_list?? >
    <#list comment_list as comment>
    <#assign u = Util.userById(comment.commentUserId) >
    <#if (u.trueName??)><#assign userName = u.trueName >
    <#elseif (u.nickName??)><#assign userName = u.nickName >
    <#elseif (u.loginName??)><#assign userName = u.loginName >
    </#if>
    <table border="0" cellspacing="1" cellpadding="3" class="commentTable">
	<tr>
		<td class="commentLeft">
        <img src="${SSOServerUrl +'upload/'+ u.userIcon!"images/default.gif"}" onerror="this.src='${SiteUrl}images/default.gif'" width='48' height='48' border='0' />
        <a onmouseover="ToolTip.showUserCard(event,'${u.loginName!}','${userName}', '${SSOServerUrl +"upload/"+ u.userIcon!"images/default.gif"}')" href="${SiteUrl}go.action?loginName=${u.loginName!}" target="_blank">${u.nickName!?html}</a>		
		</td>
		<td class="commentRight">
			<div class="commentHeader">				
			<span style="float: right">${comment.createDate} <a href="#top">[TOP]</a></span>
			<span style='clear:both;font-weight:bold;'>${comment.title?html}</span>
			</div>
			<div class="commentContent">${comment.content}</div>
		</td>
		</tr>
	</table>
    </#list>
	<div class='pager'>
	<#include 'pager.ftl'>
	</div>
  </#if>
  
	<#if prepareCourseMember?? && loginUser?? >
	<form method='post'>
	评论标题(<span style='color:#F00'>*</span>)：<input style='width:400px' name='commentTitle' value='RE:${showUser.trueName?html}的个案' />
	<br/>
	评论内容(<span style='color:#F00'>*</span>)：<br/>
            <script id="DHtml" name="content" type="text/plain" style="width:780px;height:300px;">
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
	
	</td>
	</tr>
	</table>
</div>
    
    
    <div id='page_footer'></div>
    <script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
    <script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
</body>
</html>