<div style="height:10px;"></div>
<table border="0" cellpadding="0" cellspacing="0" style="width:100%">
<tr style="vertical-align:top;">
<td class="left">
<div class="leftcontainer">
<link rel="stylesheet" type="text/css" href="${SiteUrl}dtree.css" />  
<script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
<script type="text/javascript">
var JITAR_ROOT = "${SiteUrl}";
d = new dTree("d");
d.add(0,-1,"<b>相册分类</b>","channel.action?cmd=photolist&type=${type!}&channelId=${channel.channelId}");
<#if photo_category??>
<#list photo_category.all as c>
<#if c.parentId??>
d.add(${c.id},${c.parentId},"${c.name}","channel.action?cmd=photolist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}");      	
<#else>
d.add(${c.id},0,"${c.name}","channel.action?cmd=photolist&type=${type!}&channelCateId=${c.id}&channelId=${channel.channelId}");
</#if>
</#list>
</#if>
document.write(d);
d.openAll();
</script>
</div>
</td>
<td class="right">
<div class="rightcontainer">
<#if photo_list?? && photo_list.size() &gt; 0 >
    <#-- 定义要显示的列数 columnCount -->
    <#assign columnCount = 5>
    <#-- 计算显示当前记录集需要的表格行数 rowCount -->
    <#if photo_list.size() % columnCount == 0>
    <#assign rowCount = (photo_list.size() / columnCount) - 1>
    <#else>
    <#assign rowCount = (photo_list.size() / columnCount)>
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
        <#if photo_list[row * columnCount + cell]??>                     
            <#assign photo = photo_list[row * columnCount + cell]>                  
            <img onload="CommonUtil.reFixImg(this,128,128)" src="${Util.thumbNails(photo.href!'images/default.gif')}" vspace='4' /><br />
            <a href='${SiteUrl}go.action?photoId=${photo.photoId}'>${photo.title!?html}</a>
        <#else>
            &nbsp;
        </#if>
        </td>
    </#list>
    </tr>
    </#list>
    </table>
<#if pager??>
<div class='pager'><#include '/WEB-INF/ftl/inc/pager.ftl' ></div>
</#if>
</#if>
</div>
</td>
</tr>
</table>