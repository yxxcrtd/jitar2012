<#if video_list??>

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
</#if>
<div style="text-align: right;">
<a href="${UserSiteUrl}videocatelist/${userCateId}.html?title=${title}">&gt;&gt; 查看更多</a>
</div>