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
            <#if UserUrlPattern??>
            	<a href='${UserUrlPattern.replace('{loginName}',user.loginName)}photo/${photo.photoId}.html'>
	            <img onload="CommonUtil.reFixImg(this,240,240)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' border='0' /></a><br />
	            <a href='${UserUrlPattern.replace('{loginName}',user.loginName)}photo/${photo.photoId}.html'>${photo.title!?html}</a><br /><br />
	            <div style='text-align:left;'>上传时间:${photo.createDate}</div>
	            <div style='text-align:left;'>所属相册:<a href='${UserUrlPattern.replace('{loginName}',user.loginName)}photocate/${photo.userStaple!}.html'>${photo.stapleTitle!}</a></div>
	            <div style='text-align:left;'>评论:${photo.commentCount}</div>
	            <div style='text-align:left;'>浏览:${photo.viewCount}</div>
            <#else>            
	          	<a href='${SiteUrl}${user.loginName}/photo/${photo.photoId}.html'>
	            <img onload="CommonUtil.reFixImg(this,240,240)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' border='0' /></a><br />
	            <a href='${SiteUrl}${user.loginName}/photo/${photo.photoId}.html'>${photo.title!?html}</a><br /><br />
	            <div style='text-align:left;'>上传时间:${photo.createDate}</div>
	            <div style='text-align:left;'>所属相册:<a href='${SiteUrl}${user.loginName}/photocate/${photo.userStaple!}.html'>${photo.stapleTitle!}</a></div>
	            <div style='text-align:left;'>评论:${photo.commentCount}</div>
	            <div style='text-align:left;'>浏览:${photo.viewCount}</div>
            </#if>
            
        <#else>
            &nbsp;
        </#if>
        </td>
    </#list>
    </tr>
    </#list>
    </table>
</#if>