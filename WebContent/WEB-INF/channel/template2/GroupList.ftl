<#if group_list?? >
<#assign columnCount = 2 >
<#if group_list.size() % columnCount == 0>
<#assign rowCount = group_list.size() / columnCount >
  <#else>
<#assign rowCount = (group_list.size() / columnCount)?int + 1 >
  </#if>  
<table border='0' align='center' cellspacing='2' cellpadding='0' width='100%'>        
<#list 0..rowCount-1 as row>
 <tr valign='top' align='center'>
    <#list 0..columnCount-1 as cell>
    <td width='50%'>
    <#if group_list[(row ) * columnCount + cell]?? >
    <#assign group = group_list[(row) * columnCount + cell]>
      <div style='height:64px'>
      <table border='0' cellpadding='0' cellspacing='0' class='border_img_a'>
      <tr><td>
      <img src='${Util.url(group.groupIcon!SiteUrl + "images/group_default.gif")}' width='48' height='48' border='0' />
      
      </td></tr>
      </table>
      </div>
      <div style='text-align:center'><a href='${SiteUrl!}g/${group.groupName}' target='_blank' title='${group.groupIntroduce!?html}'>${group.groupTitle!}</a></div>
    <#else>
    &nbsp;
    </#if>
    </td>
  </#list>
</tr>
</#list>
</table>
<#else>
没有数据
</#if>