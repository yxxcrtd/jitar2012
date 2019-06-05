<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>集体备课 <#include ('/WEB-INF/ftl/webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}precourse.css" />
  <script src='${SiteUrl}js/jitar/core.js'></script>
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
<#if prepareCourse?? >
	<div class='course_title'><a href='showPrepareCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}'>${prepareCourse.title!}</a> 备课阶段导航</div>
	<table style='width:100%' border='0' cellspacing='1' class='pc_table'>
	<tr valign='top'>
	<td id='leftnav'>
	<#if precoursestage_list?? >
	<#list precoursestage_list as pcs>
	<#if prepareCourseStageId == pcs.prepareCourseStageId >
	<div style='padding:4px;' class='highlightnav'><a href='showPrepareCourseStage.py?stageId=${pcs.prepareCourseStageId}&prepareCourseId=${prepareCourse.prepareCourseId}'>${pcs.title}</a></div>
	<#else>
	<div style='padding:4px;'><a href='showPrepareCourseStage.py?stageId=${pcs.prepareCourseStageId}&prepareCourseId=${prepareCourse.prepareCourseId}'>${pcs.title}</a></div>
	</#if>
	</#list>
	</#if>
	</td>
	<td style='padding:6px;'>
	<h3>关于 ${prepareCourseStage.title} 阶段的讨论</h3>
	<div style='padding:6px'><strong>本阶段任务：</strong>${prepareCourseStage.description!}</div>
	<form method='post' action='showPrepareCourseStage.py?stageId=${prepareCourseStageId}&prepareCourseId=${prepareCourseId}'>
	<#if pcs_rpl_list ?? >
	<#list pcs_rpl_list as pr>
	<#assign u = Util.userById(pr.userId)>
	<div style='border:1px solid #B0BEC7;'>
	 <div style='background:#F9F9F9;padding:6px;border-bottom:1px solid #B0BEC7;'>
	 <span style='float:right'><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.trueName}</a> [<a href='showUserCourse.py?prepareCourseId=${prepareCourseId}&userId=${pr.userId}' target='_blank'>查看个案</a>] 发表于 ${pr.createDate}</span>
	 <span style='float:left'>${pr.title!}</span><span>&nbsp;</span>
	 </div>
	 <div style='padding:2px 6px;'>${pr.content!}</div>
	</div>
	<div style='clear:both;height:8px;font-size:0'></div>
	</#list>
	<#else>
	<h4>暂无讨论</h4>
	</#if>
	<div style='clear:both;height:8px;font-size:0'></div>
	标题：<input name='stagePostTitle' style='width:100%' value='关于【${prepareCourseStage.title?html}】的讨论' /><br />
	发表评课：<br />

            <script id="DHtml" name="stagePostComment" type="text/plain" style="width:980px;height:500px;">
            </script>
            <script type="text/javascript">
                var editor = UE.getEditor('DHtml');
            </script> 
            	
	<input type='submit' value='发表讨论' />
	<input type='button' value=' 返  回 ' onclick='window.location.href="showPrepareCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}"' />
	</form>
	</td>
	</tr>
	</table>
</#if>
<div style='clear:both;'></div>
<#include ('/WEB-INF/ftl/footer.ftl') >
</body>
</html>