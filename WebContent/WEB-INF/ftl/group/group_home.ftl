<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>协作组管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
</head>
<body>
<#if isKtGroup=="1">
    <#assign grpName="课题组"> 
    <#assign grpShowName="课题">
<#elseif isKtGroup=="2">
    <#assign grpName="备课组"> 
    <#assign grpShowName="小组">
<#else>
    <#assign grpName="协作组">
    <#assign grpShowName="小组">
</#if>
<#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
</div>
<br/>

<#-- 最新小组成员 -->
<table class='listTable' cellspacing='1' border='0'>
  <thead>
    <tr><th colspan='16' align='left' style='padding:4px'>最新${grpShowName}成员</th></tr>
  </thead>
  <#if member_list??>
  <tbody>
   <tr>
  <#list member_list as member>
      <td align='center' style='padding:12px;' valign='top'>
      <a href='${SiteUrl}go.action?loginName=${member.loginName}' target='_blank'><img 
        src="${SSOServerUrl +'upload/'+ member.userIcon!''}" width='64' height='64' border='0' vspace='3' onerror="this.src='${ContextPath}images/default.gif'"/></a>
      <br /><a href='${SiteUrl}go.action?loginName=${member.loginName}' target='_blank'>${member.nickName!?html}</a>
      <br />(${member.joinDate?string("yy-MM-dd")})
      </td>
  </#list>
     <td width='100%' valign='bottom' align='right'><a href='groupMember.action?cmd=list&amp;groupId=${group.groupId}'>more &gt;&gt;&gt;</a></td>
   </tr>
  </tbody>
  </#if>
</table>
<br />

<table border='0' cellspacing='0' cellpadding='0'>
	<tr>
		<td width="50%" valign='top'>
		  <#-- 最新公告列表 -->
			<table class='listTable' style='width:100%' border='0' cellspacing='1'>
			  <thead>
				  <tr><th align='left' style='padding:4px'>最新公告列表</th></tr>
				</thead>
				<#if placard_list??>
				<tbody>
		    <#list placard_list as placard> 
			    <tr>
			      <td>
			          <a href='../showPlacard.action?placardId=${placard.id}' target='_blank'>${placard.title!?html}</a>
			          (${placard.createDate?string("yy-MM-dd")})
			      </td>
			    </tr>
		    </#list>
			  </tbody>
			  <tfoot>
		    <tr>
		    	<td align="right"><a href='groupPlacard.action?cmd=list&amp;groupId=${group.groupId}'> more >>> </a></td>
		    </tr>
		    </tfoot>
		    </#if>
		  </table>

      <br/>
      <#-- 最新文章列表 -->
      <table class='listTable' style='width:100%' border='0' cellspacing='1'>
        <thead>
          <tr><th align='left' style='padding:4px'>最新文章列表</th></tr>
        </thead>
        <#if article_list??>
        <tbody>
        <#list article_list as article>
          <tr>
            <td>
              <a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a>
              (${article.createDate?string('yy-MM-dd')})
            </td>
          </tr>
        </#list>
        </tbody>
        <tr>
          <td align="right"><a href="groupArticle.action?cmd=list&amp;groupId=${group.groupId}">more >>></a></td>
        </tr>
        </#if>
      </table>
      <br/>
      <#-- 最新图片列表 -->
      
      <table class='listTable' style='width:100%' border='0' cellspacing='1'>
        <thead>
          <tr><th align='left' style='padding:4px'>最新图片列表</th></tr>
        </thead>
        <#if photo_list??>
        <tbody>
          <tr>
            <td>
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
                    <td align="center" width='${100 / columnCount}%' style='background:#FFF;padding:8px'><br />
                    <#-- 判断是否存在当前对象：存在就输出；不存在就输出空格 -->
                    <#if photo_list[row * columnCount + cell]??>                     
                        <#assign photo = photo_list[row * columnCount + cell]>
                        <img onload="CommonUtil.reFixImg(this,120,100)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' border='0' /><br />
                         <a href='${SiteUrl}go.action?photoId=${photo.photoId}' target='_blank'>${photo.title!?html}</a>
                    </#if>
                    </td>
                </#list>
                </tr>
                </#list>
                </table>
            </#if>
            </td>
          </tr>
        </tbody>
        <tr>
          <td align="right"><a href="groupPhoto.action?cmd=list&amp;groupId=${group.groupId}">more >>></a></td>
        </tr>
        </#if>
      </table>  
          
    </td>
	<td width='8'>&nbsp;&nbsp;</td>
	<td width="50%" valign='top'>
	  <#-- 最新主题列表 -->
		<table class='listTable' style='width:100%' border='0' cellspacing='1'>
			<thead>
			<tr><th align='left' style='padding:4px'>最新主题列表</th></tr>
			</thead>
			<#if topic_list??>
			<tbody>
			<#list topic_list as topic>
				<tr>
					<td>
						<a href="groupBbs.action?cmd=reply_list&amp;topicId=${topic.topicId}&amp;groupId=${group.groupId}" target='_blank'>${topic.title!?html}</a>
						(${topic.createDate?string("yy-MM-dd")})
					</td>
				</tr>
			</#list>
			</tbody>
			<tr>
	      	<td align="right"><a href='groupBbs.action?cmd=topic_list&amp;groupId=${group.groupId}'>more >>></a></td>
		  </tr>
		  </#if>
		</table>
		<br/>
      <table class='listTable' style='width:100%' border='0' cellspacing='1'>
        <thead>
          <tr><th align='left' style='padding:4px'>最新资源列表</th></tr>
        </thead>
        <#if resource_list??>
        <tbody>
        <#list resource_list as resource>
          <tr>
            <td>
              <img src='${Util.iconImage(resource.href!)}' border='0' hspace='3'
                align='absmiddle' /><a href='../showResource.action?resourceId=${resource.resourceId}' 
                target='_blank'>${resource.title!?html}</a> (${resource.createDate?string('yy-MM-dd')})
            </td>
          </tr>
        </#list>
        </tbody>
        <tr>
          <td align="right"  colspan="2"><a href="groupResource.action?cmd=list&amp;groupId=${group.groupId}">more >>></a></td>
        </tr>
        </#if>
      </table>
      
        <br/>
      <table class='listTable' style='width:100%' border='0' cellspacing='1'>
        <thead>
          <tr><th align='left' style='padding:4px'>最新视频列表</th></tr>
        </thead>
        <#if video_list??>
        <tbody>
          <tr>
            <td>
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
           </td>
          </tr>
        </tbody>
        <tr>
          <td align="right"  colspan="2"><a href="groupVideo.action?cmd=list&amp;groupId=${group.groupId}">more >>></a></td>
        </tr>
        </#if>
      </table>
            
		</td>
	</tr> 
</table>
</body>
</html>