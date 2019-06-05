<#if comment_list?? && (comment_list?size != 0) >
<table border='0' width='100%' cellspacing='0' cellpadding='3'>
	<#list comment_list as comment>
	    <tr valign='top'>		   
		   <td width='100%' colspan='2' class='comment_list'>		    
			   <#if comment.objType == 3 >  <#--文章-->
			   <a href="${SiteUrl}showArticle.action?articleId=${comment.objId}">${comment.title!}</a>
			   <#elseif comment.objType == 11>  <#--相片-->
			   <a href="${SiteUrl}showPhoto.py?photoId=${comment.objId}">${comment.title!}</a>
			   <#elseif comment.objType == 12> <#--资源-->
			   <a href="${SiteUrl}showResource.action?resourceId=${comment.objId}">${comment.title!}</a>
			   <#elseif comment.objType == 17> <#--视频-->
			   <a href="${SiteUrl}manage/video.action?cmd=show&videoId=${comment.objId}">${comment.title!}</a>
			   <#else> 
			   <a href="${SiteUrl}showArticle.action?articleId=${comment.objId}">${comment.title!}</a>
			   </#if>
		   </td>
		   </tr>
		   <tr>
		   <td style='border-bottom:1px dotted #999'><nobr>[${comment.userName!}]:</nobr></td>
		   <td align='right' style='border-bottom:1px dotted #999'><nobr>${comment.createDate?string('MM-dd HH:mm')}</nobr></td>
	    </tr>
	</#list>
</table>
<#else>
  当前没有任何评论。
</#if>