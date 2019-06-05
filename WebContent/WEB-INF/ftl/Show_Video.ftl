<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="icon" href="${SiteUrl}images/favicon.ico" />
        <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
        <title><#include ('webtitle.ftl') ></title>
        <link rel="stylesheet" type="text/css" href="css/common/common.css" />
		<link rel="styleSheet" type="text/css" href="../css/manage.css">	
        <link rel="stylesheet" type="text/css" href="css/tooltip/tooltip.css" />
        <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}index.css" />
        <script src='${SiteUrl}js/jitar/core.js' type="text/javascript"></script>
        <script src='js/jitar/tooltip.js' type="text/javascript"></script>
        <script type="text/javascript" src="js/jitar/login.js"></script>
        <script type="text/javascript">
            var JITAR_ROOT = ' ${SiteUrl}';
            //var USERMGR_ROOT = '{UserMgrClientUrl}'; 此处变量应该可以不用了，不用再改了。
        </script>
        <script type="text/javascript">
        	function showCommentSummary(id)
        	{
        		window.open("${SiteUrl}showCommentSummary.py?id="+id,"","left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1")
        	}
        </script>
    </head>
	
	<body>
		<#include "site_head.ftl">
		<div style='height:8px;font-size:0'></div>
        <div class="containter">
        <h2 style="text-align:center;padding-right:250px">${video.title!}</h2>
            <table class="listTable" cellSpacing="1">
                <tr>
                    <td width="750" align="right">
						<object type="application/x-shockwave-flash" data="${SiteUrl}images/palyer.swf" width="750" height="600" id="v3">
							<param name="movie" value="${SiteUrl}images/palyer.swf"/> 
							<param name="allowFullScreen" value="true" />
							<param name="FlashVars" value="xml=
							<vcastr>
								<channel>
									<item>
										<source>${flvHref?lower_case?html}</source>
										<title>${video.name!}</title>
									</item>
								</channel>
							</vcastr>
							"/>
						</object>
                    </td>
                    <td style="text-align: left; padding-left: 10px;" valign="top">
                    	<#if video.tags != ''>
                    		<b>视频标签</b>：
                    		<#list video.tags.split(",") as t>
								<a href='${SiteUrl}showTag.action?tagName=${t?url('UTF-8')}'>${t}</a>&nbsp;
                    		</#list><br /><br />
                    	</#if>
                    	<b>视频分类</b>：<#if video.categoryId?? && video.sysCate??><a href='${SiteUrl}videos.action?categoryId=${video.categoryId}'>${video.sysCate.name!?html}</a></#if><br /><br />
                    	<b>所属学段</b>：${gradeName!?html}<br /><br />
                    	<b>所属学科</b>：${Util.subjectById(video.subjectId!).msubjName!?html}<br /><br />
                    	<b>上传用户</b>：<a href='${SiteUrl}go.action?loginName=${loginName!}'>${userName!}</a><br /><br />
                    	<b>所在单位</b>：<#if video.unitId??><#assign unit=Util.unitById(video.unitId)><#if unit??><a href='${SiteUrl}go.action?unitName=${unit.unitName!?html}'>${unit.unitTitle!?html}</a></#if></#if><br /><br />
                    	<b>上传时间</b>：${video.createDate!}<br /><br />
                    	<table border='0' cellSpacing="0" width="100%" style="text-align: left; padding-left: 10px;">
                    	<tr>
                    		<td>
                    			<b>播放</b>：${video.viewCount!}
                    		</td>
                    		<td>	
                    		<b>评论</b>：${video.commentCount!}
                    		</td>
                    		<td><b>视频说明</b>:</td>	
                    	<tr>
                    	<tr>
                    		<td colspan='3'>
                    			<div>
                    			<#if video.getSummary()??>
					          	<#if (video.getSummary().length() > 200) >
					          		${video.getSummary().substring(0,200)}......
					          	<#else>
					          		${video.getSummary()}
					          	</#if>
                    			</div>
                    			<#if (video.getSummary().length() > 100) >
                    			<a href="#" style="cursor:hand" onclick="showCommentSummary(${video.videoId})">全部说明</a>
                    			</#if>
                    			</#if>
                    		</td>
                    	</tr>
                    	</table>
                    </td>
                </tr>
			</table>
			
            <#if score?? >
            <div class='article_footer' style="text-align:left;padding-right:20px;">
            <img border="0" src="${SiteUrl}images/verygood.gif"/>&nbsp;&nbsp;${scoreCreateUserName!?html}&nbsp;&nbsp;于:&nbsp;${scoreDate?string('yyyy-MM-dd HH:mm:ss')}&nbsp;&nbsp;对此内容加${score}分，理由：${scoreReason!?html}
            </div>
            </#if>
			
		</div>
		
		
		<iframe id="videoCommentFrame" frameBorder="0" src="${SiteUrl}manage/video.action?cmd=comment&videoId=${video.videoId}" scrolling="no" width="100%" height="100"></iframe>
		
        <#include "footer.ftl">
	</body>
</html>


