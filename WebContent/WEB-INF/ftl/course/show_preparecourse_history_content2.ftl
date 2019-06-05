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
</head> 
<body>
    <#include 'func.ftl' >
    <div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
    <div id='header'>
      <div id='blog_name'><span>${prepareCourse.title!?html}</span></div>
    </div>
    <div id='nav'><#include 'navbar.ftl' ></div>
    
<div id='wraper'>
	<table id='container' border='0' cellpadding='0' cellspacing='0'>
	<tr>
	<td id='column_1' class='col'>
	
<div class="widgetWindow"><div class="widgetFrame">
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
</div>
</#if>
</td><td class="widgetContent c_rt"></td></tr><tr><td class="widgetFoot f_lt"></td><td class="widgetFoot f_mt"></td><td class="widgetFoot f_rt"></td></tr></tbody></table>
</div></div>
 
<div class="widgetWindow"><div class="widgetFrame">
<table class="widgetTable" cellpadding="0" cellspacing="0" border="0"><thead><tr><td class="widgetHead h_lt"></td><td class="widgetHead h_mt"><div class="widgetHeader"><div class="ico"><img class="mod_icon" src="${SiteUrl}images/pixel.gif" height="16" width="16" border="0"></div><div id="webpart_1_h" class="title">共案编辑历史</div></div></td><td class="widgetHead h_rt"></td></tr></thead><tbody><tr class="widgetEditor" style="display: none;"><td class="widgetEdit e_lt"></td><td class="widgetEdit e_mt"></td><td class="widgetEdit e_rt"></td></tr><tr><td class="widgetContent c_lt"></td><td class="widgetContent c_mt">
<#if prepareCourseEdit_list?? >
<#include 'history_content.ftl' >
</#if>
</td><td class="widgetContent c_rt"></td></tr><tr><td class="widgetFoot f_lt"></td><td class="widgetFoot f_mt"></td><td class="widgetFoot f_rt"></td></tr></tbody></table>
</div></div>


<div class="widgetWindow"><div class="widgetFrame">
<table class="widgetTable" cellpadding="0" cellspacing="0" border="0"><thead><tr><td class="widgetHead h_lt"></td><td class="widgetHead h_mt"><div class="widgetHeader"><div class="ico"><img class="mod_icon" src="${SiteUrl}images/pixel.gif" height="16" width="16" border="0"></div><div id="webpart_1_h" class="title">查看个案</div></div></td><td class="widgetHead h_rt"></td></tr></thead><tbody><tr class="widgetEditor" style="display: none;"><td class="widgetEdit e_lt"></td><td class="widgetEdit e_mt"></td><td class="widgetEdit e_rt"></td></tr><tr><td class="widgetContent c_lt"></td><td class="widgetContent c_mt">
<#include 'user_content.ftl' >
</td><td class="widgetContent c_rt"></td></tr><tr><td class="widgetFoot f_lt"></td><td class="widgetFoot f_mt"></td><td class="widgetFoot f_rt"></td></tr></tbody></table>
</div></div>


	</td>
	<td id='column_2' class='col'>
	
	
	<div class="article_title" style='text-align:center'>${prepareCourse.title!}</div>
		<#assign u = Util.userById(prepareCourseEdit.editUserId) >
		<div style='text-align:center;padding:4px'><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName?html}</a> 于 ${prepareCourseEdit.editDate} 编写</div>
		<div class="article_content">		
		
		<#if prepareCourse.contentType == 2 || prepareCourse.contentType == 3 || prepareCourse.contentType == 4 || prepareCourse.contentType == 5 || prepareCourse.contentType == 100>
		<#if !(swfUrl??)>
		<div style="padding:20px 0;text-align:center;color:#f00;font-weight:bold;">共案文件不存在，无法进行显示。</div>
		<#else>
				
		<script type="text/javascript" src="${courseFileServer}js/flashpaper/flexpaper_flash.js"></script>
		<a id="viewerPlaceHolder" style="width:100%;height:500px;display:block"></a>

		<script type="text/javascript">
		var fp = new FlexPaperViewer('${courseFileServer}js/flashpaper/FlexPaperViewer',
				'viewerPlaceHolder', {
					config : {
					  SwfFile : '${swfUrl}?' + (new Date()).valueOf(),
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
		${prepareCourseEdit.content!}
		</#if>
		
		
		
		
		</div>
	
	</td>
	</tr>
	</table>
</div>  
    
    
    

    <div id='page_footer'></div>
    <script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
    <#-- 原来这里是 include loginui.ftl  -->
    <script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
</body>
</html>