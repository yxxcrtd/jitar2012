<#if group_list??>
    <#-- 定义要显示的列数 columnCount -->
    <#assign columnCount = 2>
    <#-- 计算显示当前记录集需要的表格行数 rowCount -->
    <#if group_list.size() % columnCount == 0>
    <#assign rowCount = (group_list.size() / columnCount) - 1>
    <#else>
    <#assign rowCount = (group_list.size() / columnCount)>
    </#if>
    <#-- 外层循环输出 -->
    <table  cellSpacing="5" align="center" style='width:100%' border="0">  
    <#list 0..rowCount as row >
        <#-- 内层循环输出  -->
        <tr>
        <#list 0..columnCount - 1 as cell >
            <#if group_list[row * columnCount + cell]??>                     
                <#assign group = group_list[row * columnCount + cell]>
                <td align="center" style='padding:4px'>
                <a href='${SiteUrl!}g/${group.groupName}' target='_blank' title='${group.groupIntroduce!?html}'><img src='${SiteUrl}${group.groupIcon!}' width='48' height='48' border='0' /></a>
                <br/>
                <a href='${SiteUrl!}g/${group.groupName}' target='_blank' title='${group.groupIntroduce!?html}'>${group.groupTitle!}</a>
                </td>
            </#if>
        </#list>
        </tr>
    </#list> 
    </table>   
</#if>